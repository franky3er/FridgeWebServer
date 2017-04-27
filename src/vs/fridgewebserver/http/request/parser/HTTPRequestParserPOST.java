package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPMethodNotImplementedException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * Concrete implementation to parse a POST HTTP request
 */
public class HTTPRequestParserPOST extends HTTPRequestParser {
    @Override
    public void parse(HTTPRequest httpRequest) throws HTTPMethodNotImplementedException {
        throw new HTTPMethodNotImplementedException(HTTPRequestMethod.POST);
    }
}
