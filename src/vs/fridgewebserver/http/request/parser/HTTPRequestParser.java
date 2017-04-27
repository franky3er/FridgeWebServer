package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

/**
 * Created by franky3er on 27.04.17.
 */
public abstract class HTTPRequestParser {
    protected HTTPRequest httpRequest;

    public void parse(HTTPRequest httpRequest) throws HTTPRequestException {
        this.httpRequest = httpRequest;
        extractHost();
        extractUserAgent();
    }

    public void extractHost() throws HTTPRequestException {
        if (httpRequest.getHttpRequest().size() < 2) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        String[] hostLineElements = httpRequest.getHttpRequest().get(1).split(" ");
        if (hostLineElements.length != 2 || !hostLineElements[0].equals("Host:")) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        httpRequest.setHost(hostLineElements[1]);
    }

    private void extractUserAgent() throws HTTPRequestException {
        if (httpRequest.getHttpRequest().size() < 3) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        String[] userAgentLineElements = httpRequest.getHttpRequest().get(2).split(" ");
        if (userAgentLineElements.length != 2 || !userAgentLineElements[0].equals("User-Agent:")) {
            throw new HTTPBadRequestException("Invalid Request");
        }
        httpRequest.setUserAgent(userAgentLineElements[1]);
    }
}
