package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPMethodNotImplementedException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * Concrete implementation to parse a TRACE HTTP request
 */
public class HTTPRequestParserTRACE extends HTTPRequestParser {
    @Override
    public void parse(HTTPRequest httpRequest) throws HTTPRequestException {
        throw new HTTPMethodNotImplementedException(HTTPRequestMethod.TRACE);
    }
}
