package vs.fridgewebserver.http;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by franky3er on 25.04.17.
 */
public class HTTPRequestHandler extends Thread {
    private BlockingQueue<Socket> clients;

    public HTTPRequestHandler(BlockingQueue<Socket> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {
        System.out.println(String.format("INFO : HTTPRequestHandler [%s] running", getId()));
        System.out.flush();
        while (true) {
            try {
                handleRequest(clients.take());
            } catch (InterruptedException e) {
            }
        }
    }

    private void handleRequest(Socket client) {
        System.out.println(String.format("INFO : HTTPRequestHandler [%s] handle client [%s]", getId(), client));
        System.out.flush();
        //TODO handle request
    }
}
