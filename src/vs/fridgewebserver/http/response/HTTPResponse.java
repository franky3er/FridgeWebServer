package vs.fridgewebserver.http.response;

/**
 * Created by franky3er on 25.04.17.
 */
public class HTTPResponse {
    private int statusCode;
    private String statusReasonPhrase;
    private String httpVersion;
    private String contentType;
    private String messageBody;

    public String getHttpResponse() {
        //TODO getHTTPResponse as String
        return null;
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

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}