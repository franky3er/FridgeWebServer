package vs.fridgewebserver.http;

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
        while (true) {
            try {
                handle(clients.take());
            } catch (InterruptedException e) {
            }
        }
    }

    private void handle(Socket client) {
        //TODO handle client
    }
}
