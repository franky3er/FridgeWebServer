package vs.fridgewebserver.http.exception;

import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * Created by franky3er on 26.04.17.
 */
public class HTTPMethodNotImplementedException extends HTTPRequestException {
    public HTTPMethodNotImplementedException(HTTPRequestMethod method) {
        super(String.format("501 : Method not implemented - Method %s not implemented", method.name()));
        super.setStatusCode(501);
        super.setStatusReasonPhrase("Method not implemented");
        super.setErrorMessage(String.format("Method %s not implemented", method.name()));
    }
}
