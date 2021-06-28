package rest.docs

import grails.compiler.GrailsCompileStatic
import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

@GrailsCompileStatic
@Secured(['permitAll'])
class ApplicationController implements PluginManagerAware {
    static allowedMethods = [index: 'GET']

    GrailsApplication   grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
