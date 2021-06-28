/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
grails.databinding.convertEmptyStringsToNull = false
server.servlet.session.timeout = 3600 //60 minute session timeout

api.version ='1.0'


grails.{
    plugin {
        springsecurity {
            filterChain.chainMap = [
                    [pattern: '/api/appVersion', filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'],
                    [pattern: '/api/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],  // Stateless chain
            ]

            adh {
                // errorPage = '/errorHandler/error'
                errorPage = null
                useForward = false
            }
            auth {
                loginFormUrl = '/module/auth/login'
                useForward = false
            }

            scpf.forceEagerSessionCreation= false

            rest {
                token {
                    validation {
                        enableAnonymousAccess = true
                    }
                    storage {
                        jwt {
                            expiration = 3600 // default expiration to 1 hour
                        }
                    }
                }
            }
        }
    }
}

grails.databinding.dateFormats = [
    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    'yyyy-MM-dd'
]

grails.plugin.springsecurity.rest.token.storage.jwt.secret = "IneedToHaveALongEnoughDefaultSecretForThis"


grails.plugin.springsecurity.providerNames = [
        'daoAuthenticationProvider',
        'anonymousAuthenticationProvider'
]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'rest.docs.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'rest.docs.UserRole'
grails.plugin.springsecurity.authority.className = 'rest.docs.Role'
grails.plugin.springsecurity.auth.ajaxLoginFormUrl = null

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
        [pattern: '/actuator/**', access: ['permitAll']]
]