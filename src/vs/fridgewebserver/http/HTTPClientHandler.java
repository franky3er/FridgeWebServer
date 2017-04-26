package vs.fridgewebserver.http;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by franky3er on 25.04.17.
 */
public class HTTPClientHandler extends Thread {
    private BlockingQueue<Socket> clients;

    public HTTPClientHandler(BlockingQueue<Socket> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {
        System.out.println(String.format("INFO : %s [%s] running", this.getClass().getSimpleName(), getId()));
        System.out.flush();
        while (true) {
            try {
                handleRequest(clients.take());
            } catch (InterruptedException e) {
            }
        }
    }

    private void handleRequest(Socket client) {
        System.out.println(String.format("INFO : %s [%s] handle client [%s]", this.getClass().getSimpleName(), getId(), client));
        System.out.flush();
        //TODO handle request
    }
}
