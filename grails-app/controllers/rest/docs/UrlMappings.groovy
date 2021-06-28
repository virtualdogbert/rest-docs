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

class UrlMappings {

    static mappings = {


        "/api/$controller"(method: "GET", action: "index")
        "/api/$controller/$id(.$format)?"( action: "show", method: "GET")

        "/api/$controller/$id/$action(.$format)?"(method: "GET")
        "/api/$controller/$id/$action(.$format)?"(method: "POST")
        "/api/$controller/$id/$action(.$format)?"(method: "PUT")
        "/api/$controller/$id/$action(.$format)?"(method: "DELETE")

        "/api/$controller/$id(.$format)?"(action: "delete", method: "DELETE")
        "/api/$controller/$id(.$format)?"(action: "update", method: "PUT")
        "/api/$controller(.$format)?"(action: "save", method: "POST")

        "/api/$controller/$action(.$format)?"(method: "POST")
        "/api/$controller/$action(.$format)?"(method: "PUT")
        "/api/$controller/$action(.$format)?"(method: "GET")
        "/api/$controller/$action(.$format)?"(method: "PATCH")
        "/api/$controller/$action(.$format)?"(method: "DELETE")

        "/"(controller: 'application', action: 'index')
    }
}
