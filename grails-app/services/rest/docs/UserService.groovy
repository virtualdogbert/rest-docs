package rest.docs

import grails.gorm.PagedResultList
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import rest.docs.command.user.IndexCommand

@Service(User)
abstract class UserService {

    abstract User get(Serializable id)

    PagedResultList<User> list(IndexCommand command) {

        User.where {
            if (command.username) {
                username =~ "%$command.username%"
            }

            if (command.enabled != null) {
                enabled == command.enabled
            }


            if (command.accountLocked != null) {
                accountLocked == command.accountLocked
            }

            if (command.accountExpired != null) {
                accountExpired == command.accountExpired
            }

            if (command.passwordExpired != null) {
                passwordExpired == command.passwordExpired

            }

        }.list(max: command.max, offset: command.offset, sort: command.sort, order: command.order)
    }

    abstract Long count()

    abstract User delete(Serializable id)

    abstract User save(String username, String password, boolean enabled, boolean accountExpired, boolean accountLocked, boolean passwordExpired)

    abstract User update(Long id, Map args)

    @Transactional
    void initUsers() {
        Role userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        Role adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        Role actuator = new Role(authority: 'ROLE_ACTUATOR').save(flush: true, failOnError: true)

        User testUser = new User(username: 'me', password: 'password').save(flush: true, failOnError: true)
        User testUser2 = new User(username: 'me2', password: 'password').save(flush: true, failOnError: true)

        UserRole.create testUser, adminRole, true
        UserRole.create testUser, actuator, true
        UserRole.create testUser, userRole, true

        UserRole.create testUser2, userRole, true
    }

}

