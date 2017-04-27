package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * This factory builds concrete HTTPRequestParser
 */
public class HTTPRequestParserFactory {
    /**
     * Builds a conctrete HTTPRequestParser from the given method type
     *
     * @param method
     * @return HTTPRequestParser
     */
    public static HTTPRequestParser build(HTTPRequestMethod method) {
        switch(method) {
            case GET: {
                return new HTTPRequestParserGET();
            }
            case POST: {
                return new HTTPRequestParserPOST();
            }
            case OPTIONS: {
                return new HTTPRequestParserOPTIONS();
            }
            case HEAD: {
                return new HTTPRequestParserHEAD();
            }
            case PUT: {
                return new HTTPRequestParserPUT();
            }
            case DELETE: {
                return new HTTPRequestParserDELETE();
            }
            case TRACE: {
                return new HTTPRequestParserTRACE();
            }
            case CONNECT: {
                return new HTTPRequestParserCONNECT();
            }
            default: {
                return null;
            }
        }
    }
}
