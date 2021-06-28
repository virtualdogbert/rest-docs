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
package rest.docs.snippets

import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet

import static org.springframework.restdocs.payload.PayloadDocumentation.*

class LoginSnippets {

    static final ResponseFieldsSnippet loginResponseFieldsSnippet = responseFields(
            fieldWithPath("access_token").type(JsonFieldType.STRING).description('The access token to use for authentication on REST requests.'),
            fieldWithPath("username").type(JsonFieldType.STRING).description('The username that was used to get an access token for.'),
            fieldWithPath("roles").type(JsonFieldType.ARRAY).description('The roles that the user has, giving them access to the application.'),
            fieldWithPath("token_type").type(JsonFieldType.STRING).description('The type of token which is always Bearer.'),
            fieldWithPath("expires_in").type(JsonFieldType.NUMBER).description('How long the access token in good for in seconds.'),
            fieldWithPath("refresh_token").type(JsonFieldType.STRING).description('Another token that can be used to get a new token.')
    )


    static final RequestFieldsSnippet LoginRequestSnippet = requestFields(
            fieldWithPath('username').type(JsonFieldType.STRING).description('The users username for login.'),
            fieldWithPath('password').type(JsonFieldType.STRING).description('The users password.')
    )

}
