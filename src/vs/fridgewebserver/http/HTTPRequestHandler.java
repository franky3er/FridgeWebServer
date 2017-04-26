package vs.fridgewebserver.http;

import java.net.Socket;
import java.util.Queue;
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
        while(true) {
            while(clients.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                handle(clients.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Socket client) {
        //TODO handle client
    }
}
