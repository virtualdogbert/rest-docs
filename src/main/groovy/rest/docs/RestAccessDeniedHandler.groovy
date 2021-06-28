package rest.docs

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Burt Beckwith
 */
@CompileStatic
@Slf4j
class RestAccessDeniedHandler implements AccessDeniedHandler {

    JsonTemplateService jsonTemplateService


    void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Writer writer = response.writer
        response.status = HttpServletResponse.SC_FORBIDDEN

        if (response.committed) {
            log.trace 'response is committed'
            return
        }

        jsonTemplateService.write('/common/mapAsJson', writer, [
                data: [
                        path : request.servletPath,
                        message: e.message
                ]
        ])

        writer.flush()
        writer.close()
    }
}
