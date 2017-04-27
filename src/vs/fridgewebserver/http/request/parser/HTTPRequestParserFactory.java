package vs.fridgewebserver.http.request.parser;

import vs.fridgewebserver.http.request.HTTPRequestMethod;

/**
 * Created by franky3er on 27.04.17.
 */
public class HTTPRequestParserFactory {
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
