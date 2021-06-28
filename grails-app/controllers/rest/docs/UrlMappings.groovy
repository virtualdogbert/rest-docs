package rest.docs

class UrlMappings {

    static mappings = {


        "/api/$controller"(method: "GET", action: "index")
        "/api/$controller/$id(.$format)?"( action: "show", method: "GET")

        "/api/$controller/$id/$action(.$format)?"(method: "GET")
        "/api/$controller/$id/$action(.$format)?"(method: "POST")
        "/api/$controller/$id/$action(.$format)?"(method: "PUT")
        "/api/$controller/$id/$action(.$format)?"(method: "DELETE")

        "/api/$controller/$id(.$format)?"(action: "delete", method: "DELETE")
        "/api/$controller/$id(.$format)?"(action: "update", method: "PUT")
        "/api/$controller(.$format)?"(action: "save", method: "POST")

        "/api/$controller/$action(.$format)?"(method: "POST")
        "/api/$controller/$action(.$format)?"(method: "PUT")
        "/api/$controller/$action(.$format)?"(method: "GET")
        "/api/$controller/$action(.$format)?"(method: "PATCH")
        "/api/$controller/$action(.$format)?"(method: "DELETE")

        "/"(controller: 'application', action: 'index')
    }
}
