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
