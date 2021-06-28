package rest.docs.snippets

import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.ResponseFieldsSnippet

import static org.springframework.restdocs.payload.PayloadDocumentation.*

class ErrorHandlingSnippets {

    static final ResponseFieldsSnippet commandObjectSnippet = responseFields(
            fieldWithPath("[].field").type(JsonFieldType.STRING).description('The field that has the roor'),
            fieldWithPath("[].bindingFailure").type(JsonFieldType.BOOLEAN).description('If the error was binding related.'),
            fieldWithPath("[].rejectedValue").type(JsonFieldType.VARIES).description('The value for the field that was rejected'),
            fieldWithPath("[].message").type(JsonFieldType.STRING).description('The error message rendered using i18n mappings.')
    )

    static final ResponseFieldsSnippet nonExistingUrlSnippet = responseFields(
            fieldWithPath("path").type(JsonFieldType.STRING).description("The path that doesn't exist."),
            fieldWithPath("message").type(JsonFieldType.STRING).description('The error message.')

    )


    static final ResponseFieldsSnippet notFoundSnippet = responseFields(
            fieldWithPath("timestamp").type(JsonFieldType.NUMBER).description('The timestamp of the not found error.'),
            fieldWithPath("status").type(JsonFieldType.NUMBER).description('The HTTP status code 404'),
            fieldWithPath("error").type(JsonFieldType.STRING).description('The Not found error.'),
            fieldWithPath("message").type(JsonFieldType.STRING).description('The error message if there is one.'),
            fieldWithPath("path").type(JsonFieldType.STRING).description('The path that was not found.')
    )
}
