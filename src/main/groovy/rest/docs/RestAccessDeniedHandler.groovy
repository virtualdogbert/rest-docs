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

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Burt Beckwith
 */
@CompileStatic
@Slf4j
class RestAccessDeniedHandler implements AccessDeniedHandler {

    JsonTemplateService jsonTemplateService


    void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        Writer writer = response.writer
        response.status = HttpServletResponse.SC_FORBIDDEN

        if (response.committed) {
            log.trace 'response is committed'
            return
        }

        jsonTemplateService.write('/common/mapAsJson', writer, [
                data: [
                        path : request.servletPath,
                        message: e.message
                ]
        ])

        writer.flush()
        writer.close()
    }
}
