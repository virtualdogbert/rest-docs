package rest.docs


class BootStrap {

    UserService userService

    def init = { servletContext ->
        userService.initUsers()
    }

    def destroy = {
    }
}
