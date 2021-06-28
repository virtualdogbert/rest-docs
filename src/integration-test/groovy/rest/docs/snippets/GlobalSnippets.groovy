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