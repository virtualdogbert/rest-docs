package rest.docs

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import rest.docs.command.user.IndexCommand
import rest.docs.command.user.SaveCommand
import rest.docs.command.user.UpdateCommand

import static org.springframework.http.HttpStatus.*

@GrailsCompileStatic
@Secured(['ROLE_ADMIN'])
class UserController implements ControllerTrait {

    UserService userService

    static allowedMethods = [
            index : 'GET',
            show  : 'GET',
            save  : 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    @Secured(['ROLE_USER'])
    def index(IndexCommand command) {
        respond userService.list(command), model: [userCount: userService.count()]
    }

    @Secured(['ROLE_USER'])
    def show(Long id) {
        respond userService.get(id)
    }

    def save(SaveCommand command) {
        if (command == null) {
            return render(status: NOT_FOUND)
        }

        if (!validateCommandObject(command)) {
            return

        }

        User user = userService.save(command.username, command.password, command.enabled, command.accountExpired, command.accountLocked, command.passwordExpired)


        respond user, [status: CREATED, view: "show"]
    }

    def update(Long id, UpdateCommand command) {
        if (command == null) {
            render status: NOT_FOUND
            return
        }

        if (command.hasErrors()) {
            return respond(command.errors, statusCode: BAD_REQUEST, formats: ['json'])

        }

        User user = userService.update(id, command.toMap())


        respond user, [status: OK, view: "show"]
    }

    def delete(Long id) {
        if (id == null || userService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
