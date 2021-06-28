package rest.docs.snippets

import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.PathParametersSnippet

import static org.springframework.restdocs.headers.HeaderDocumentation.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.request.RequestDocumentation.*

class GlobalSnippets {

    static PathParametersSnippet getIdSnippet(String domain, String action, idName= 'id') {
        return pathParameters(
            parameterWithName(idName).description("The ${idName} of the $domain to ${action}.".toString())
        )
    }

    static RequestHeadersSnippet getHeaders() {
        requestHeaders(
            headerWithName("Authorization").description("Bearer token, from login endpoint."),
            headerWithName("Accept").description("This should always be application/json.")
        )
    }

    static ResponseFieldsSnippet getDeletedSnippet(String domain) {
        responseFields(
            fieldWithPath('deleted').type(JsonFieldType.NUMBER).description("The number of ${domain}s deleted.".toString())
        )
    }
}