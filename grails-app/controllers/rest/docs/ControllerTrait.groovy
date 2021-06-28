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

import grails.validation.ValidationException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.validation.ObjectError

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

trait ControllerTrait {
    MessageSource messageSource

    boolean validateCommandObject(Object commandObject) {

        if (!commandObject.validate()) {
            renderErrors(commandObject.errors.allErrors)
            return false
        }

        return true
    }

    void handleValidationException(ValidationException v) {
        renderErrors(v.errors.allErrors)
    }

    private void renderErrors(List<ObjectError> objectErrors) {
        response.status = UNPROCESSABLE_ENTITY.value()

        render(view: '/common/listAsJson', model: [
                data: convertErrors(objectErrors)
        ])
    }

    List<Map> convertErrors(List<ObjectError> objectErrors) {
        return objectErrors.collect { ObjectError error ->
            [
                    field         : error.field,
                    bindingFailure: error.bindingFailure,
                    rejectedValue : error.rejectedValue,
                    message       : messageSource.getMessage(error, LocaleContextHolder.locale)
            ]
        }
    }

    void renderNotFound(String object, Long id) {
        render(view: '/common/mapAsJson', model: [
                data: [
                        path   : request.servletPath,
                        message: messageSource.getMessage('default.not.found.message', [object, id] as Object[] , LocaleContextHolder.locale)
                ]
        ])
    }

    void exceptionHandler(Exception e) {
        handleException(e, e.message, HttpStatus.INTERNAL_SERVER_ERROR, true)
    }

    /**
     * Used by the various Exception Handler methods to appropriately respond to exceptions
     *
     * @param e - the Exception that was returned back to the controller
     * @param errors - the list of error message(s) to display.
     * @param dumpStacktrace - a flag to dump the stacktrace to the log (default false)
     * @param status - the http status to set for the return response.
     */
    private void handleException(Exception e, String message, HttpStatus status = HttpStatus.BAD_REQUEST, Boolean dumpStacktrace = false) {
        log.debug "handleException(${e.getClass().getName()}) called"

        response.status = status.value()

        if (dumpStacktrace || (e instanceof java.lang.IllegalArgumentException)) {
            log.error e.message, e
        }

        if (!message) {
            message = [e.message] ?: [e.getClass().getName()]
        }

        render(view: '/common/listAsJson', model: [data: [message]])
    }


}