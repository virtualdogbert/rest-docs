package rest.docs

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.beans.factory.annotation.Value

@GrailsCompileStatic
@Secured(['permitAll'])
class AppVersionController {
    static allowedMethods = [index: 'GET']

    @Value('${api.version:1.0.1}')
    String Version


    def index() {
        render(view: '/common/mapAsJson', model: [data: [version: Version]])
    }
}
