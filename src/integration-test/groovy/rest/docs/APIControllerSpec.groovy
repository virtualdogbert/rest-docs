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

import geb.spock.GebSpec
import groovy.json.JsonSlurper
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration

class APIControllerSpec extends GebSpec {

    static final String Login_URL = '/api/login'

    static String Rest_Token

    @Value('${local.server.port}')
    protected int port

    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/docs/generated-snippets')

    RequestSpecification documentationSpec

    def setup() {
        //set documentation specification
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(this.restDocumentation))
                .build()

        //This sets up the security token to be used by the tests
        if (!Rest_Token) {
            Rest_Token = login()
        }
    }

    String login(String username = 'me', String password = 'password') {
        RequestSpecification requestSpecification = RestAssured
                .given(this.documentationSpec)
                .accept(MediaType.APPLICATION_JSON_VALUE)

        def response = requestSpecification
                .when()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("""{ "username":$username, "password":$password }""")
                .port(this.port)
                .post(Login_URL)

        def responseJsonObject = new JsonSlurper().parseText(response.body().asString())

        return responseJsonObject.access_token
    }

    void resetLogin() {
        Rest_Token = null
        Rest_Token = login()
    }

    RequestSpecification setupRequestSpecification(String authToken = null) {
        RestAssured
                .given(this.documentationSpec)
                .headers([
                        "Authorization": "Bearer ${authToken ?: Rest_Token}"
                ])
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .port(this.port)
    }

    RequestSpecification setupAynotomosRequestSpecification() {
        RestAssured
                .given(this.documentationSpec)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .port(this.port)
    }
}