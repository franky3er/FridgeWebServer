package vs.fridgewebserver.http.handler.option;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.response.HTTPResponse;
import vs.products.ProductShoppingAgent;
import vs.shopservice.ShopService;

import java.sql.Connection;
import java.util.Map;

/**
 * Created by franky3er on 19.05.17.
 */
public class HandlerOptionOrderProduct implements HandlerOption {
    private Map<String, ShopService.Client> shops;
    private Connection connection;
    private String address;

    private String productName;
    private String productAmount;

    public HandlerOptionOrderProduct(Map<String, ShopService.Client> shops, Connection connection,
                                     String address) {
        this.shops = shops;
        this.connection = connection;
        this.address = address;
    }

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) throws HTTPRequestException {
        productName = httpRequest.getParams().get("product");
        if (productName == null || productName.isEmpty()) {
            throw new HTTPBadRequestException("Invalid Parameter - must contain product");
        }

        productAmount = httpRequest.getParams().get("amount");
        if (productAmount == null || productAmount.isEmpty()) {
            throw new HTTPBadRequestException("Invalid Parameter - must contain amount");
        }

        boolean orderSuccessful = (new ProductShoppingAgent(connection, shops, null, address)).orderProduct(productName, productAmount);

        String httpResponseContent = getHTMLHeader() + getHTMLContent(orderSuccessful) + getHTMLFooter();

        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(200);
        httpResponse.setStatusReasonPhrase("OK");
        httpResponse.setContentType("text/html");
        httpResponse.setMessageBody(httpResponseContent);

        return httpResponse;
    }

    private String getHTMLHeader() {
        return "<html>\n" +
                "<head>\n" +
                "\t<title>Product Order</title>\n" +
                "<head>\n" +
                "<body>\n";
    }

    private String getHTMLContent(boolean orderSuccessful) {
        if(orderSuccessful) {
            return getHTMLContentOrderSuccessful();
        } else {
            return getHTMLContentOrderFailed();
        }
    }

    private String getHTMLContentOrderSuccessful() {
        String html = "\n<h3>Product Order: </h3>\n";
        return html +String.format(
                    "\t<p>Product Order (%s, %s) successful.</p>\n", productName, productAmount);
    }

    private String getHTMLContentOrderFailed() {
        String html = "\n<h3>Product Order: </h3>\n";
        return html +String.format(
                "\t<p>Product Order (%s, %s) failed.</p>\n", productName, productAmount);
    }

    private String getHTMLFooter() {
        return "\n</body>\n" +
                "</html>";
    }
}
