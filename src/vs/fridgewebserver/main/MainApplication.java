package vs.fridgewebserver.main;

import org.apache.thrift.transport.TTransportException;
import org.json.simple.parser.ParseException;
import vs.fridgewebserver.http.handler.HTTPClientHandler;
import vs.fridgewebserver.http.HTTPServer;
import vs.fridgewebserver.http.handler.option.HandlerOptionFactory;
import vs.shopservice.ShopService;
import vs.shopservice.ShopServiceClientFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MainApplication {
    private final static String PROJECT_NAME = "FridgeWebServer";
    private final static String PROJECT_CONFIG = System.getProperty("user.dir") + File.separator +
            "config" + File.separator + PROJECT_NAME + ".properties";

    private final static String FRIDGEWEBSERVER_PRODUCTSQLITE_FILESOURCE = "FridgeWebServer.ProductSQLiteHandler.FileSource";
    private final static String FRIDGEWEBSERVER_PRODUCTSQLITE_DRIVER = "FridgeWebServer.ProductSQLiteHandler.Driver";
    private final static String FRIDGEWEBSERVER_WORKERS_NUMBEROF = "FridgeWebServer.Workers.NumberOf";
    private final static String FRIDGEWEBSERVER_LISTENING_PORT = "FridgeWebServer.Listening.Port";
    private final static String FRIDGEWEBSERVER_PRODUCTSHOPS_JSONSOURCE = "FridgeWebServer.ProductShops.JSONSource";
    private final static String FRIDGEWEBSERVER_PRODUCTSHOPS_DELIVERYADDRESS = "FridgeWebServer.ProductShops.DeliveryAddress";

    private static List<HTTPClientHandler> workers;
    private static HTTPServer boss;
    private static BlockingQueue<Socket> clients = new ArrayBlockingQueue<>(1024);
    private static Map<String, ShopService.Client> shops;
    private static HandlerOptionFactory handlerOptionFactory;
    private static Connection connection;

    private static int numberOfWorkers;
    private static int port;
    private static String sqLiteFileSource;
    private static String sqLiteDriver;
    private static String productShopsJsonSource;
    private static String deliveryAddress;

    public static void main(String[] args) {
        try {
            loadConfig();
            initialize();
            run();
        } catch (IOException e) {
            System.err.println("ERROR : Initialization failed");
            e.printStackTrace();
            System.out.flush();
        } catch (TTransportException e) {
            System.err.println("ERROR : Connection to shop failed");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println(String.format("ERROR : Parsing JSON file %s failed", productShopsJsonSource));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println(String.format("ERROR : Driver not found: %s", sqLiteDriver));
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(String.format("ERROR : Connection to product DB failed : %s", sqLiteFileSource));
            e.printStackTrace();
        }
    }

    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(PROJECT_CONFIG));
        port = Integer.parseInt(properties.getProperty(FRIDGEWEBSERVER_LISTENING_PORT));
        numberOfWorkers = Integer.parseInt(properties.getProperty(FRIDGEWEBSERVER_WORKERS_NUMBEROF));
        sqLiteFileSource = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSQLITE_FILESOURCE);
        sqLiteDriver = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSQLITE_DRIVER);
        productShopsJsonSource = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSHOPS_JSONSOURCE);
        deliveryAddress = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSHOPS_DELIVERYADDRESS);
    }

    private static void run() {
        System.out.println("INFO : Running");
        runWorkers();
        runBoss();
    }

    private static void initialize() throws IOException, ParseException, TTransportException, ClassNotFoundException, SQLException {
        System.out.println("INFO : Initializing");
        initializeDBConnection();
        initializeShops();
        initializeHandlerOptionFactory();
        initializeWorkers();
        initializeBoss();
    }

    private static void initializeDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName(sqLiteDriver);
        connection = DriverManager.getConnection("jdbc:sqlite:" + sqLiteFileSource);
    }

    private static void initializeShops() throws IOException, ParseException, TTransportException {
        shops = ShopServiceClientFactory.createClientsFromJSON(productShopsJsonSource);
    }

    private static void initializeHandlerOptionFactory() {
        handlerOptionFactory = new HandlerOptionFactory(connection, shops, deliveryAddress);
    }

    private static void initializeBoss() throws IOException {
        boss = new HTTPServer(port, clients);
    }

    private static void initializeWorkers() {
        workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(new HTTPClientHandler(clients, handlerOptionFactory));
        }
    }

    private static void runWorkers() {
        for (HTTPClientHandler worker : workers) {
            worker.start();
        }
    }

    private static void runBoss() {
        boss.run();
    }
}
