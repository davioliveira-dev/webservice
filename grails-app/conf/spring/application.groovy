package spring

grails.plugin.springsecurity.securityConfigType = 'InterceptUrlMap'
grails.plugin.springsecurity.rest.token.storage.jwt.useEncryptedJwt = true
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true

grails {
    plugin {
            rest {
                token {
                    validation {
                        enableAnonymousAccess = true
                    }
                    storage {
                        jwt {
                            secret = 'pleaseChangeThisSecretForANewOne'
                        }
                    }
                }
            }
            springsecurity {
                filterChain {
                    chainMap = [
                            [pattern: '/listaRegistroEntradaPlaca/**', filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'],
                            [pattern: '/listaRegistroSaidaPlaca/**',       filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
                            [pattern: '/**',           filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
                    ]
                }
        }
    }
}