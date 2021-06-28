/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
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