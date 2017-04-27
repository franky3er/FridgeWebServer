package vs.fridgewebserver.http.exception;

import vs.fridgewebserver.http.response.HTTPResponse;

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

    /**
     * Creates a http response out of the exception params.
     *
     * @return HTTPResponse
     */
    public HTTPResponse getHttpResponse() {
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(this.statusCode);
        httpResponse.setStatusReasonPhrase(this.statusReasonPhrase);
        httpResponse.setContentType("text/html");

        String messageBody = String.format("%s%s%s",
                getHTMLHeader(), errorMessage, getHTMLFooter());

        httpResponse.setMessageBody(messageBody);

        return httpResponse;
    }

    private String getHTMLHeader() {
        return "<html>\n" +
                "<head>\n" +
                "\t<title>ERROR</title>\n" +
                "<head>\n" +
                "<body>\n";
    }

    private String getHTMLFooter() {
        return "</body>\n" +
                "</html>";
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
