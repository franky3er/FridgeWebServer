package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPMethodNotImplementedException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * Created by franky3er on 27.04.17.
 */
public class HTTPRequestParserPOST implements HTTPRequestParser {
    @Override
    public void parse(HTTPRequest httpRequest) throws HTTPMethodNotImplementedException {
        throw new HTTPMethodNotImplementedException(HTTPRequestMethod.POST);
    }
}
