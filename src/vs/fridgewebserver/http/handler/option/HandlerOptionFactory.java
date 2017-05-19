package vs.fridgewebserver.http.handler.option;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.products.Product;
import vs.products.iohandler.ProductIOHandler;
import vs.products.iohandler.database.ProductDatabaseHandler;
import vs.shopservice.ShopService;

import java.sql.Connection;
import java.util.Map;

/**
 * Factory to build a HandlerOption from the specified optionName
 */
public class HandlerOptionFactory {
    private Connection connection;
    private Map<String, ShopService.Client> shops;
    private String address;

    public HandlerOptionFactory(Connection connection, Map<String, ShopService.Client> shops, String address) {
        this.connection = connection;
        this.shops = shops;
        this.address = address;
    }

    public HandlerOption build(String optionName) throws HTTPRequestException {
        switch (optionName) {
            case ("history"): {
                return new HandlerOptionShowProductHistory(new ProductDatabaseHandler(connection));
            }
            case ("order"): {
                return new HandlerOptionOrderProduct(shops, connection, address);
            }
            default: {
                throw new HTTPBadRequestException(String.format("Show Option not implemented: '%s'", optionName));
            }
        }
    }
}
