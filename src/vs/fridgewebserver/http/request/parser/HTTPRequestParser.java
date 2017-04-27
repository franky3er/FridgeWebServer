package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

/**
 * Abstract to parse a http request in the concrete implementation to fill the given HTTPRequest object.
 */
public abstract class HTTPRequestParser {
    protected HTTPRequest httpRequest;

    /**
     * Parses the initial lines every http request should contain and should be extended in the concrete
     * implementation.
     *
     * @param httpRequest
     * @throws HTTPRequestException
     */
    public void parse(HTTPRequest httpRequest) throws HTTPRequestException {
        this.httpRequest = httpRequest;
        extractHost();
        extractUserAgent();
    }

    /**
     * Extracts the host out of the http request.
     *
     * @throws HTTPRequestException
     */
    protected void extractHost() throws HTTPRequestException {
        String[] hostLineElements = findRequestLine("Host:").split(" ");
        if (hostLineElements.length != 2) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        httpRequest.setHost(hostLineElements[1]);
    }

    /**
     * Extracts the User-Agent of the http request.
     *
     * @throws HTTPRequestException
     */
    protected void extractUserAgent() throws HTTPRequestException {
        String[] userAgentLineElements = findRequestLine("User-Agent:").split(" ");
        if (userAgentLineElements.length < 2) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        httpRequest.setUserAgent("");
        for (int i = 1; i < userAgentLineElements.length; i++) {
            httpRequest.setUserAgent(httpRequest.getUserAgent() + userAgentLineElements[i] + " ");
        }
    }

    /**
     * Finds a line in the http request which starts with the given String pattern.
     *
     * @param startsWith
     * @return String
     * @throws HTTPRequestException
     */
    protected String findRequestLine(String startsWith) throws HTTPRequestException {
        for (String line : httpRequest.getHttpRequest()) {
            if (line.startsWith(startsWith)) {
                return line;
            }
        }
        throw new HTTPBadRequestException("Invalid Request");
    }
}
