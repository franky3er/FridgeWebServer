package vs.fridgewebserver.main;

import vs.fridgewebserver.http.handler.HTTPClientHandler;
import vs.fridgewebserver.http.HTTPServer;
import vs.products.iohandler.ProductIOHandler;
import vs.products.iohandler.database.sqlite.ProductSQLiteHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    private static List<HTTPClientHandler> workers;
    private static HTTPServer boss;
    private static BlockingQueue<Socket> clients = new ArrayBlockingQueue<Socket>(1024);
    private static ProductIOHandler productIOHandler;

    private static int numberOfWorkers;
    private static int port;
    private static String sqLiteFileSource;
    private static String sqLiteDriver;

    public static void main(String[] args) {
        try {
            loadConfig();
            initialize();
            run();
        } catch (IOException e) {
            System.err.println("ERROR : Initialization failed");
            e.printStackTrace();
            System.out.flush();
        }
    }

    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(PROJECT_CONFIG));
        port = Integer.parseInt(properties.getProperty(FRIDGEWEBSERVER_LISTENING_PORT));
        numberOfWorkers = Integer.parseInt(properties.getProperty(FRIDGEWEBSERVER_WORKERS_NUMBEROF));
        sqLiteFileSource = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSQLITE_FILESOURCE);
        sqLiteDriver = properties.getProperty(FRIDGEWEBSERVER_PRODUCTSQLITE_DRIVER);
    }

    private static void run() {
        System.out.println("INFO : Running");
        runWorkers();
        runBoss();
    }

    private static void initialize() throws IOException {
        System.out.println("INFO : Initializing");
        initializeProductIOHandler();
        initializeWorkers();
        initializeBoss();
    }

    private static void initializeProductIOHandler() {
        productIOHandler = new ProductSQLiteHandler(sqLiteDriver, sqLiteFileSource);
    }

    private static void initializeBoss() throws IOException {
        boss = new HTTPServer(port, clients);
    }

    private static void initializeWorkers() {
        workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(new HTTPClientHandler(clients, productIOHandler));
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
