package vs.fridgewebserver.http.handler.option;

import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.response.HTTPResponse;

/**
 * Created by Frank on 27.04.2017.
 */
public class HandlerOptionShowProductHistory implements HandlerOption {
    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) throws HTTPRequestException {
        return null;
    }
}
