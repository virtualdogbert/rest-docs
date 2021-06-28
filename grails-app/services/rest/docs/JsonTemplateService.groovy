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

import grails.plugin.json.view.JsonViewTemplateEngine
import groovy.text.Template
import org.springframework.beans.factory.annotation.Autowired
//@CompileStatic
class JsonTemplateService {

    @Autowired
    JsonViewTemplateEngine templateEngine

    String toString(String view, Map data) {
        Template t = templateEngine.resolveTemplate(view)
        def writable = t.make(data)
        Writer writer = new StringWriter()
        return writable.writeTo(writer)
    }

    void write(String view, Writer writer, Map data) {
        Template t = templateEngine.resolveTemplate(view)
        Writable writable = t.make(data)
        writable.writeTo(writer)
    }
}
