package vs.products.show.history.html;

import vs.products.ScannedProduct;
import vs.products.iohandler.database.ProductDatabaseHandler;
import vs.products.show.history.ScannedProductHistory;

import java.util.List;

/**
 * Created by Frank on 27.04.2017.
 */
public class HTMLScannedProductHistory implements ScannedProductHistory {
    private ProductDatabaseHandler databaseHandler;

    public HTMLScannedProductHistory(ProductDatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public List<ScannedProduct> getTimeStampSortedScannedProducts(String productName) {
        String sql = String.format("SELECT %s, %s, %s, %s " +
                        "FROM %s INNER JOIN %S ON %s=%s " +
                        "WHERE %s='%s' " +
                        "ORDER BY %s ASC; ",
                ProductDatabaseHandler.PRODUCT_TABLE_PK_PRODUCTNAME_COMPLETE, ProductDatabaseHandler.SCANNEDPRODUCT_TABLE_AMMOUNT_COMPLETE,
                ProductDatabaseHandler.PRODUCT_TABLE_PRODUCTUNIT_COMPLETE, ProductDatabaseHandler.SCANNEDPRODUCT_TABLE_PK_TIMESTAMP_COMPLETE,
                ProductDatabaseHandler.PRODUCT_TABLE, ProductDatabaseHandler.SCANNEDPRODUCT_TABLE,
                ProductDatabaseHandler.PRODUCT_TABLE_PK_PRODUCTNAME_COMPLETE, ProductDatabaseHandler.SCANNEDPRODUCT_TABLE_PK_FK_PRODUCTNAME_COMPLETE,
                ProductDatabaseHandler.PRODUCT_TABLE_PK_PRODUCTNAME_COMPLETE, productName,
                ProductDatabaseHandler.SCANNEDPRODUCT_TABLE_PK_TIMESTAMP_COMPLETE);
        return databaseHandler.read(sql);
    }

    @Override
    public String getTimeStampSortedScannedProductsAsString(String productName) {
        List<ScannedProduct> scannedProducts = getTimeStampSortedScannedProducts(productName);
        if(scannedProducts.isEmpty()) {
            return "\n<h3>Product History: </h3>\n" +
                    "\t<p>No Product found with this name.</p>\n";
        }

        String html = "<table>\n";
        html += "\t<tr>\n";
        html += "\t\t<th>Time Stamp</th>\n";
        html += "\t\t<th>Product</th>\n";
        html += "\t\t<th>Ammount</th>\n";
        html += "\t\t<th>Unit</th>\n";

        for(ScannedProduct scannedProduct : scannedProducts) {
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

    @Override
    public void printTimeStampSortedScannedProducts(String productName) {
       System.out.println(this.getTimeStampSortedScannedProductsAsString(productName));
    }
}
