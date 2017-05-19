package vs.fridgewebserver.http.handler.option;

import vs.fridgewebserver.http.exception.HTTPBadRequestException;
import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;
import vs.fridgewebserver.http.response.HTTPResponse;
import vs.products.ScannedProduct;
import vs.products.iohandler.ProductIOHandler;
import vs.products.iohandler.wrapper.ProductHistoryHandler;

import java.util.List;

/**
 * HTTP Handler to show the product history.
 */
public class HandlerOptionShowProductHistory implements HandlerOption {
    private ProductIOHandler productIOHandler;

    public HandlerOptionShowProductHistory(ProductIOHandler productIOHandler) {
        this.productIOHandler = productIOHandler;
    }

    @Override
    public HTTPResponse handle(HTTPRequest httpRequest) throws HTTPRequestException {
        String productName = httpRequest.getParams().get("product");
        if (productName == null || productName.isEmpty()) {
            throw new HTTPBadRequestException("Invalid Parameter - must contain product");
        }
        ProductHistoryHandler productHistoryHandler = new ProductHistoryHandler(this.productIOHandler);
        String html = getProductHistoryInHTML(productHistoryHandler.getProductHistoryASC(productName));

        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
        httpResponse.setStatusCode(200);
        httpResponse.setStatusReasonPhrase("OK");
        httpResponse.setContentType("text/html");
        httpResponse.setMessageBody(getHTMLHeader() + html + getHTMLFooter());

        return httpResponse;
    }

    private String getHTMLHeader() {
        return "<html>\n" +
                "<head>\n" +
                "\t<title>Product History</title>\n" +
                "<head>\n" +
                "<body>\n";
    }

    private String getHTMLFooter() {
        return "\n</body>\n" +
                "</html>";
    }

    private String getProductHistoryInHTML(List<ScannedProduct> scannedProducts) {
        String html = "\n<h3>Product History: </h3>\n";
        if (scannedProducts.isEmpty()) {
            return html +
                    "\t<p>No Product found with this name.</p>\n";
        }

        html += "<table border=\"1\">\n";
        html += "\t<tr>\n";
        html += "\t\t<th>Time Stamp</th>\n";
        html += "\t\t<th>Product</th>\n";
        html += "\t\t<th>Ammount</th>\n";
        html += "\t\t<th>Unit</th>\n";

        for (ScannedProduct scannedProduct : scannedProducts) {
            html += "\t<tr>\n";

            html += "\t\t<td>" + ScannedProduct.SCANNED_PRODUCT_DATE_FORMAT.format(scannedProduct.getTimeStamp()) + "</td>\n";
            html += "\t\t<td>" + scannedProduct.getName() + "</td>\n";
            html += "\t\t<td>" + scannedProduct.getAmmount() + "</td>\n";
            html += "\t\t<td>" + scannedProduct.getUnit() + "</td>\n";

            html += "\t</tr>\n";
        }

        html += "</table>";

        return html;
    }
}
