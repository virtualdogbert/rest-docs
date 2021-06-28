package rest.docs

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovy.json.JsonSlurper
import io.restassured.specification.RequestSpecification
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType

import static org.springframework.restdocs.headers.HeaderDocumentation.*
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*

@Integration
@Rollback
class AppVersionControllerSpec extends APIControllerSpec {
    static final String App_Version_Endpoint = '/api/appVersion'


    void "Test and document AppVersion request (GET request, index action) to end-point: /api/appVersion"() {
        given: "A rest request for the Users"

            RequestSpecification requestSpecification = setupAynotomosRequestSpecification()
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-app-version-list',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    requestHeaders(
                                            headerWithName("Accept").description("This should always be application/json.")
                                    ),

                                    responseFields(
                                            fieldWithPath("version").type(JsonFieldType.STRING).description('The version of the application.')
                                    )
                            )
                    )
        when: "GET request is made to end-point for the Users"
            def response = requestSpecification
                    .when()
                    .get("$App_Version_Endpoint")

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.version == '1.0'
    }
}