package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

/**
 * Created by franky3er on 27.04.17.
 */
public class HTTPRequestParserGET extends HTTPRequestParser {

    @Override
    public void parse(HTTPRequest httpRequest) throws HTTPRequestException {
        super.parse(httpRequest);
        extractParams();
    }

    private void extractParams() throws HTTPRequestException {
        String[] uriElements = this.httpRequest.getURI().split("\\?"); //We are only looking for params
        //TODO implement...
        if (uriElements.length == 2) {
            String[] params = uriElements[1].split("&");
            for (String param : params) {
                extractParam(param);
            }
        }
    }

    private void extractParam(String param) throws HTTPRequestException {
        String[] paramPair = param.split("=");
        if (paramPair.length != 2) {
            throw new HTTPBadRequestException("Invalid Parameter");
        }
        this.httpRequest.getParams().put(paramPair[0], paramPair[1]);
    }
}
