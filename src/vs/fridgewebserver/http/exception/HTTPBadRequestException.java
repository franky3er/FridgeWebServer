package vs.fridgewebserver.http.exception;

/**
 * Created by franky3er on 26.04.17.
 */
public class HTTPBadRequestException extends HTTPRequestException {
    public HTTPBadRequestException(String errorMessage) {
        super(String.format("400 : Bad Request - %s", errorMessage));
        super.setStatusCode(400);
        super.setStatusReasonPhrase("Bad Request");
        super.setErrorMessage(errorMessage);
    }
}
