package rest.docs.snippets

import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.RequestParametersSnippet
import rest.docs.command.user.IndexCommand

import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.restdocs.request.RequestDocumentation.*

class UserDocSnippets {

    static final ResponseFieldsSnippet UserResponseFieldsSnippet() {

        return responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description('The id of the User'),
                fieldWithPath("username").type(JsonFieldType.STRING).description('The name of the User'),
                fieldWithPath("enabled").type(JsonFieldType.BOOLEAN).description('Is the user enabled.'),
                fieldWithPath("accountExpired").type(JsonFieldType.BOOLEAN).description('Is the user account expired.'),
                fieldWithPath("accountLocked").type(JsonFieldType.BOOLEAN).description('Is the account locked.'),
                fieldWithPath("passwordExpired").type(JsonFieldType.BOOLEAN).description('Is the users password expired.'),
                fieldWithPath("dateCreated").type(JsonFieldType.STRING).description('The date the User was created in ISO 8601 format.'),
                fieldWithPath("lastUpdated").type(JsonFieldType.STRING).description('The date the User was last updated in ISO 8601 format.')
        )

    }

    static final ResponseFieldsSnippet UserListResponseFieldsSnippet() {

        return responseFields(
                fieldWithPath("total").type(JsonFieldType.NUMBER).description('The name of the User'),
                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description('The id of the User'),
                fieldWithPath("data.[].username").type(JsonFieldType.STRING).description('The name of the User'),
                fieldWithPath("data.[].enabled").type(JsonFieldType.BOOLEAN).description('Is the user enabled.'),
                fieldWithPath("data.[].accountExpired").type(JsonFieldType.BOOLEAN).description('Is the user account expired.'),
                fieldWithPath("data.[].accountLocked").type(JsonFieldType.BOOLEAN).description('Is the account locked.'),
                fieldWithPath("data.[].passwordExpired").type(JsonFieldType.BOOLEAN).description('Is the users password expired.'),
                fieldWithPath("data.[].dateCreated").type(JsonFieldType.STRING).description('The date the User was created in ISO 8601 format.'),
                fieldWithPath("data.[].lastUpdated").type(JsonFieldType.STRING).description('The date the User was last updated in ISO 8601 format.')
        )

    }

    static RequestFieldsSnippet UserRequestSnippet = requestFields(
            fieldWithPath('username').type(JsonFieldType.STRING).description('The name of the User.'),
            fieldWithPath('password').type(JsonFieldType.STRING).description('A description of the User.'),
            fieldWithPath('enabled').optional().type(JsonFieldType.BOOLEAN).description('The color of the User.'),
            fieldWithPath('accountExpired').optional().type(JsonFieldType.BOOLEAN).description('The project associated with the User.'),
            fieldWithPath('accountLocked').optional().type(JsonFieldType.BOOLEAN).description('The name of the User.'),
            fieldWithPath('passwordExpired').optional().type(JsonFieldType.BOOLEAN).description('A description of the User.')
    )

    static RequestParametersSnippet getUserFilterSnippet() {
        requestParameters(
                parameterWithName("max").optional().description("The max number or results ${IndexCommand.Pagination.join(',')}"),
                parameterWithName("offset").optional().description('The offset in pages(page number x max)'),
                parameterWithName("sort").optional().description('The column to sort by id, username, enabled, accountExpired, accountLocked, passwordExpired, dateCreated,  or lastUpdated. Defaults to id'),
                parameterWithName("order").optional().description('The order to sort by ace or desc. Defaults to ace'),
                parameterWithName("username").optional().description('An optional filter by username'),
                parameterWithName("enabled").optional().description('An optional filter by enabled flag.'),
                parameterWithName("accountExpired").optional().description('An optional filter by account expired flag.'),
                parameterWithName("accountLocked").optional().description('An optional filter by account locked flag.'),
                parameterWithName("passwordExpired").optional().description('An optional filter by password expired flag.')
        )
    }
}
