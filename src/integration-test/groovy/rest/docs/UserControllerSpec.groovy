
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
 */package rest.docs

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovy.json.JsonSlurper
import io.restassured.specification.RequestSpecification
import org.springframework.http.HttpStatus
import org.springframework.restdocs.operation.preprocess.Preprocessors
import rest.docs.snippets.GlobalSnippets
import rest.docs.snippets.UserDocSnippets

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*

@Integration
@Rollback
class UserControllerSpec extends APIControllerSpec {
    UserService userService

    static final String User_Endpoint = '/api/user'
    static final String Domain        = 'User'

    static Long User_Id_To_Delete


    void "Test and document Users request (GET request, index action) to end-point: /api/User"() {
        given: "A rest request for the Users"

            RequestSpecification requestSpecification = setupRequestSpecification()
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-list',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    UserDocSnippets.getUserFilterSnippet(),
                                    UserDocSnippets.UserListResponseFieldsSnippet()
                            )
                    )
        when: "GET request is made to end-point for the Users"
            def response = requestSpecification
                    .when()
                    .get("$User_Endpoint?username=me&enabled=true")

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.total
            responseJsonObject.data
    }


    void "Test and document Users request (GET request, show action) to end-point: /api/User/{{id}}"() {
        given: "A rest request for an User"


            RequestSpecification requestSpecification = setupRequestSpecification()
                    .filter(
                            //This is what does the documentation if you miss something the test will fail. The preprocessors cleanup the output for the docs.
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-fetch',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    GlobalSnippets.getIdSnippet(Domain, 'fetch'),
                                    UserDocSnippets.UserResponseFieldsSnippet()
                            )
                    )
        when: "GET request is made to end-point for the User"
            User user = User.first()
            def response = requestSpecification
                    .when()
                    .get("$User_Endpoint/{id}", user.id,)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())

            responseJsonObject
            responseJsonObject instanceof Map

            responseJsonObject.id
            responseJsonObject.username
            responseJsonObject.enabled
            !responseJsonObject.accountExpired
            !responseJsonObject.accountLocked
            !responseJsonObject.passwordExpired
            responseJsonObject.dateCreated
            responseJsonObject.lastUpdated
    }


    void "Test and document User request (POST request, save action) to end-point: /api/User"() {
        given: "A rest request for the creating a User"
            RequestSpecification requestSpecification = setupRequestSpecification()
                    .body('''{
                    "username":"Test_User",
                    "password":"password",
                    "enabled":true,
                    "accountExpired":false,
                    "accountLocked":false,
                    "passwordExpired":false
                }''')
                    .filter(
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-create',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    UserDocSnippets.UserRequestSnippet,
                                    UserDocSnippets.UserResponseFieldsSnippet()
                            )
                    )
        when: "POST request is made to end-point for the User"
            def response = requestSpecification
                    .when()
                    .post(User_Endpoint)

            def responseJsonObject = new JsonSlurper().parseText(response.body().asString())
            User_Id_To_Delete = responseJsonObject.id

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value())

            responseJsonObject instanceof Map

            responseJsonObject.id
            responseJsonObject.username
            responseJsonObject.enabled
            !responseJsonObject.accountExpired
            !responseJsonObject.accountLocked
            !responseJsonObject.passwordExpired
            responseJsonObject.dateCreated
            responseJsonObject.lastUpdated

        when: "Making an update request using (PUT request, update action) to end-point: /api/User/{{id}}"

            requestSpecification = setupRequestSpecification()
                    .body("""{
                    "username":"Test User",
                    "password":"password1",
                    "enabled":true,
                    "accountExpired":false,
                    "accountLocked":false,
                    "passwordExpired":false
                }""")
                    .filter(
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-update',
                                    preprocessRequest(Preprocessors.modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    GlobalSnippets.getIdSnippet(Domain, 'update'),
                                    UserDocSnippets.UserRequestSnippet,
                                    UserDocSnippets.UserResponseFieldsSnippet()
                            )
                    )

            response = requestSpecification
                    .when()
                    .put("$User_Endpoint/{id}", User_Id_To_Delete)

            responseJsonObject = new JsonSlurper().parseText(response.body().asString())
            User_Id_To_Delete = responseJsonObject.id

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())

            responseJsonObject instanceof Map


            responseJsonObject.id
            responseJsonObject.username
            responseJsonObject.enabled
            !responseJsonObject.accountExpired
            !responseJsonObject.accountLocked
            !responseJsonObject.passwordExpired
            responseJsonObject.dateCreated
            responseJsonObject.lastUpdated


        when: "DELETE request is made to end-point for the User using (DELETE request, delete action) to end-point: /api/User/{{id}}"

            requestSpecification = setupRequestSpecification()
                    .filter(
                            RestAssuredRestDocumentationWrapper.document(
                                    'api-user-delete',
                                    preprocessRequest(modifyUris().host('api.restdocs.com').removePort(), new AuthHeaderPreprocessor(), prettyPrint()),
                                    preprocessResponse(prettyPrint()),

                                    GlobalSnippets.getHeaders(),
                                    GlobalSnippets.getIdSnippet(Domain, 'delete')
                            )
                    )

            response = requestSpecification
                    .when()
                    .delete("$User_Endpoint/{id}", User_Id_To_Delete)

        then: "status is OK"
            response.then()
                    .assertThat()
                    .statusCode(HttpStatus.NO_CONTENT.value())

            !response.body().asString()
    }


}