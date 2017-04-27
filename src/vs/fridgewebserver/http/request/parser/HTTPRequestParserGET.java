package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

/**
 * Concrete implementation to parse a GET HTTP request
 */
public class HTTPRequestParserGET extends HTTPRequestParser {

    @Override
    public void parse(HTTPRequest httpRequest) throws HTTPRequestException {
        super.parse(httpRequest);
        extractParams();
    }

    /**
     * Extracts the parameter out of the URI.
     *
     * @throws HTTPRequestException
     */
    private void extractParams() throws HTTPRequestException {
        String[] uriElements = this.httpRequest.getURI().split("\\?"); //We are only looking for params
        if (uriElements.length == 2) {
            String[] params = uriElements[1].split("&");
            for (String param : params) {
                extractParam(param);
            }
        }
    }

    /**
     * Extract the parameter out of a key-value pair string.
     *
     * @param param
     * @throws HTTPRequestException
     */
    private void extractParam(String param) throws HTTPRequestException {
        String[] paramPair = param.split("=");
        if (paramPair.length != 2) {
            throw new HTTPBadRequestException("Invalid Parameter");
        }
        this.httpRequest.getParams().put(paramPair[0], paramPair[1]);
    }
}
