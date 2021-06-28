package rest.docs

import grails.plugin.json.view.JsonViewTemplateEngine
import groovy.text.Template
import org.springframework.beans.factory.annotation.Autowired
//@CompileStatic
class JsonTemplateService {

    @Autowired
    JsonViewTemplateEngine templateEngine

    String toString(String view, Map data) {
        Template t = templateEngine.resolveTemplate(view)
        def writable = t.make(data)
        Writer writer = new StringWriter()
        return writable.writeTo(writer)
    }

    void write(String view, Writer writer, Map data) {
        Template t = templateEngine.resolveTemplate(view)
        Writable writable = t.make(data)
        writable.writeTo(writer)
    }
}
