
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

import org.springframework.http.HttpHeaders
import org.springframework.restdocs.operation.OperationRequest
import org.springframework.restdocs.operation.OperationRequestFactory
import org.springframework.restdocs.operation.OperationResponse
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor


class AuthHeaderPreprocessor implements OperationPreprocessor {
    @Override
    OperationRequest preprocess(OperationRequest request) {
        HttpHeaders headers = new HttpHeaders()
        headers.putAll(request.getHeaders())
        headers.set('Authorization', 'Bearer 12345')

        return new OperationRequestFactory().create(
            request.getUri(),
            request.getMethod(),
            request.getContent(),
            headers,
            request.getParameters(),
            request.getParts()
        )

    }


    @Override
    OperationResponse preprocess(OperationResponse operationResponse) {
        return operationResponse
    }
}
