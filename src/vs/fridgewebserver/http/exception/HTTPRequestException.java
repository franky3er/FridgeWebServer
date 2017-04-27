package vs.fridgewebserver.http.exception;

/**
 * Created by franky3er on 26.04.17.
 */
public abstract class HTTPRequestException extends Exception {
    private int statusCode;
    private String statusReasonPhrase;
    private String errorMessage;

    public HTTPRequestException(String message) {
        super(message);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReasonPhrase() {
        return statusReasonPhrase;
    }

    public void setStatusReasonPhrase(String statusReasonPhrase) {
        this.statusReasonPhrase = statusReasonPhrase;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
