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
import geb.spock.GebSpec
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovy.json.JsonSlurper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import rest.docs.snippets.LoginSnippets

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

@Integration
@Rollback
class LoginSpec extends GebSpec {

    static final String Login_URL = '/api/login'

    @Value('${local.server.port}')
    protected int port

    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/docs/generated-snippets')

    RequestSpecification documentationSpec

    void "Test Login"() {
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
                                    'api-login',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    LoginSnippets.LoginRequestSnippet,
                                    LoginSnippets.loginResponseFieldsSnippet
                            )
                    )
        when: "POST to login and get access token"
            def response = requestSpecification
                    .when()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body('{ "username":"me", "password":"password" }')
                    .port(port)
                    .post(Login_URL)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.access_token
    }

    void "Test Login wrong password"() {
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
                                        'api-login-invalid',
                                        preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), prettyPrint()),
                                        preprocessResponse(prettyPrint()),

                                        LoginSnippets.LoginRequestSnippet
                                )
                        )
            when: "POST to login and get access token"
                def response = requestSpecification
                        .when()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body('{ "username":"me", "password":"12345" }')
                        .port(port)
                        .post(Login_URL)

            then: "status is OK"
                response.then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
        }

}