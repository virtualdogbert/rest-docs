package rest.docs

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovy.json.JsonSlurper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import rest.docs.snippets.ErrorHandlingSnippets
import rest.docs.snippets.GlobalSnippets

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

@Integration
@Rollback
class ErrorHandlingSpec extends APIControllerSpec {

    static final String User_Endpoint = '/api/user'

    void "Test no authorization"() {
        given: "A rest request for the events"
            RequestSpecification documentSpec = new RequestSpecBuilder()
                    .addFilter(documentationConfiguration(this.restDocumentation))
                    .build()
            RequestSpecification requestSpecification = RestAssured
                    .given(documentSpec)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'error-handling-unauthorized',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), prettyPrint()),
                                    preprocessResponse(prettyPrint())
                            )
                    )
        when: "GET request is made to end-point for users"
            def response = requestSpecification
                    .when()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .port(this.port)
                    .get("$User_Endpoint")

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is Forbidden"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.message == "This API endpoint requires a valid authentication token."
    }


    void "Test Not for a non existing url."() {
        given: "A rest request for a non existing url"

            RequestSpecification requestSpecification = setupRequestSpecification()
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'error-handling-forbidden',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),
                                    GlobalSnippets.getHeaders(),
                                    ErrorHandlingSnippets.nonExistingUrlSnippet
                            )
                    )
        when: "GET request is made to end-point for the events"
            def response = requestSpecification
                    .when()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .get("${User_Endpoint}1234/")

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is not found"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.path == "${User_Endpoint}1234/"
            responseJsonObject.message == 'Access is denied'
    }

    void "Command object validation errors"() {
        given: "A rest request for the creating a project"
            RequestSpecification requestSpecification = setupRequestSpecification()
                    .body('{}')
                    .filter(
                            RestAssuredRestDocumentationWrapper.document(
                                    'error-validation',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    ErrorHandlingSnippets.commandObjectSnippet
                            )
                    )
        when: "POST request is made to end-point for the project"
            def response = requestSpecification
                    .when()
                    .post(User_Endpoint)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is UNPROCESSABLE_ENTITY"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())

            responseJsonObject instanceof List

            responseJsonObject[0].field == 'username'
            responseJsonObject[0].bindingFailure == false
            responseJsonObject[0].rejectedValue == null
            responseJsonObject[0].message == 'Property [username] cannot be null'

            responseJsonObject[1].field == 'password'
            responseJsonObject[1].bindingFailure == false
            responseJsonObject[1].rejectedValue == null
            responseJsonObject[1].message == 'Property [password] cannot be null'
    }


    void "Test requesting a url that the user doesn't have access to."() {
        given: "A rest request for the creating a project"
            RequestSpecification requestSpecification = setupRequestSpecification(login('me2', 'password')).filter(
                    RestAssuredRestDocumentationWrapper.document(
                            'access-denied-api-user-delete',
                            preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            GlobalSnippets.getHeaders(),
                            GlobalSnippets.getIdSnippet('User', 'delete'),
                            ErrorHandlingSnippets.nonExistingUrlSnippet
                    )
            )
            Long userId = User.list()[0].id

        when: "DELETE request is made to end-point for the User using (DELETE request, delete action) to end-point: /api/User/{{id}}"


            def response = requestSpecification
                    .when()
                    .delete("$User_Endpoint/{id}", userId)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is FORBIDDEN"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.FORBIDDEN.value())

            responseJsonObject instanceof Map
            responseJsonObject.path == "/api/user/$userId"
            responseJsonObject.message == 'Access is denied'
    }

    void "Test and document Users request(invalid id) (GET request, show action) to end-point: /api/User/{{id}}"() {
        given: "A rest request for an User"


            RequestSpecification requestSpecification = setupRequestSpecification()
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-not-found',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    GlobalSnippets.getIdSnippet('user', 'fetch'),
                                    ErrorHandlingSnippets.notFoundSnippet
                            )
                    )
        when: "GET request is made to end-point for the User"
            User user = User.first()
            def response = requestSpecification
                    .when()
                    .get("$User_Endpoint/{id}",364545)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value())

            responseJsonObject
            responseJsonObject instanceof Map
    }
}