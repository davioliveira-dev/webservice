package webservice

class BootStrap {

    def init = { servletContext ->
        User.withTransaction {
            Role role = new Role(authority: "ROLE_USER").save(flush: true, failOnError:true)
            User user = new User("DEFAULT_USER").save(flush: true, failOnError: true)
            UserRole.create(user, role)
        }
    }
    def destroy = {
    }
}
