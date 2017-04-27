package vs.fridgewebserver.main;

import vs.fridgewebserver.http.HTTPClientHandler;
import vs.fridgewebserver.http.HTTPServer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MainApplication {
    private static List<HTTPClientHandler> workers;
    private static HTTPServer boss;
    private static BlockingQueue<Socket> clients = new ArrayBlockingQueue<Socket>(1024);
    ;

    private static int numberOfWorkers;
    private static int port;

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

    private static void loadConfig() {
        port = 8081;
        numberOfWorkers = 4;
    }

    private static void run() {
        System.out.println("INFO : Running");
        runWorkers();
        runBoss();
    }

    private static void initialize() throws IOException {
        System.out.println("INFO : Initializing");
        initializeWorkers();
        initializeBoss();
    }

    private static void initializeBoss() throws IOException {
        boss = new HTTPServer(port, clients);
    }

    private static void initializeWorkers() {
        workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(new HTTPClientHandler(clients));
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
