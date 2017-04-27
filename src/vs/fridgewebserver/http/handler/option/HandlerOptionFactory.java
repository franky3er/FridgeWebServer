package vs.fridgewebserver.http.handler.option;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.products.iohandler.ProductIOHandler;

/**
 * Factory to build a HandlerOption from the specified optionName
 */
public class HandlerOptionFactory {
    public HandlerOption build(String optionName, ProductIOHandler productIOHandler) throws HTTPRequestException {
        switch (optionName) {
            case ("history"): {
                return new HandlerOptionShowProductHistory(productIOHandler);
            }
            default: {
                throw new HTTPBadRequestException(String.format("Show Option not implemented: '%s'", optionName));
            }
        }
    }
}
