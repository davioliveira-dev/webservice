# Image to start project and initialize dependencies.

# FOR DEVELOPMENT
# docker-compose up -d backend

# FOR BUILD THE IMAGE TO PRODUCTION
# docker build -t yourdockerhubuser/yourproject:versionnumber .

FROM openjdk:8 AS initializer
ENV GRAILS_VERSION 4.0.6
# Install Grails
WORKDIR /usr/lib/jvm
RUN ls -l
RUN wget https://github.com/grails/grails-core/releases/download/v$GRAILS_VERSION/grails-$GRAILS_VERSION.zip && \
    unzip grails-$GRAILS_VERSION.zip && \
    rm -rf grails-$GRAILS_VERSION.zip && \
    ln -s grails-$GRAILS_VERSION grails
# Setup Grails path.
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH
ENV GRADLE_USER_HOME /app/.gradle
# Create minimal structure to trigger grails build with specified profile.
RUN mkdir /app \
    && mkdir /app/grails-app \
    && mkdir /app/grails-app/conf \
    && echo "grails.profile: rest-api" > /app/grails-app/conf/application.yml
# Set Workdir
WORKDIR /app
# Copy minimun files to trigger grails download of wrapper and dependencies.
COPY gradle.properties build.gradle /app/
# Trigger gradle build
RUN [ "grails", "stats" ]

# Implemented to improve cache in CI
FROM initializer as development
# Add wait-for-it ro wait for database
COPY wait-for-it.sh .
RUN ["chmod", "+x", "./wait-for-it.sh"]
# Copy source code
COPY grails-app /app/grails-app
COPY src /app/src
# Set Default Behavior
ENTRYPOINT ["./wait-for-it.sh", "db:5432", "-t", "30", "--", "grails", "run-app", "--debug-jvm"]
CMD [ "" ]

# Image used to build prod war
FROM development AS builder
# Build project
RUN [ "grails", "prod", "war" ]
RUN ls -l /app/build/libs

# Production image
FROM openjdk:8-jdk AS production
# Set correct timezone
ENV TZ=America/Argentina/Cordoba
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# Add wait-for-it ro wait for database
COPY wait-for-it.sh .
RUN ["chmod", "+x", "/wait-for-it.sh"]
# Copy war inside container
COPY --from=builder /app/build/libs/app-*.war app.war
# Expose default port
EXPOSE 8080
# Wait for database to be available
ENTRYPOINT ["/wait-for-it.sh", "db-service:5432", "-t", "30", "--"]
# War runs directly. (Uses urandom as entropy source for faster startup time)
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.war"]