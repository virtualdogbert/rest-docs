package rest.docs

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.authentication.*
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestAwareAuthenticationEntryPoint implements AuthenticationEntryPoint {
    MessageSource       messageSource
    JsonTemplateService jsonTemplateService

    @Override
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Writer writer = response.writer
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        String errorMessage = ''

        if (exception) {
            if (exception instanceof AccountExpiredException) {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.expired", null, LocaleContextHolder.locale)
            } else if (exception instanceof InsufficientAuthenticationException) {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.fail", null, LocaleContextHolder.locale)
            } else if (exception instanceof CredentialsExpiredException) {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.passwordExpired", null, LocaleContextHolder.locale)
            } else if (exception instanceof DisabledException) {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.disabled", null, LocaleContextHolder.locale)
            } else if (exception instanceof LockedException) {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.locked", null, LocaleContextHolder.locale)
            } else {
                errorMessage = messageSource.getMessage("springSecurity.errors.login.fail", null, LocaleContextHolder.locale)
            }
        }

        jsonTemplateService.write('/common/mapAsJson', writer, [
                data: [
                        path: request.servletPath,
                        message: errorMessage
                ]
        ])

        writer.flush()
        writer.close()
    }
}
