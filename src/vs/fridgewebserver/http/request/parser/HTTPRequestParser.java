package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

/**
 * Created by franky3er on 27.04.17.
 */
public interface HTTPRequestParser {
    void parse(HTTPRequest httpRequest) throws HTTPRequestException;
}
