package vs.fridgewebserver.http;

import java.net.Socket;
import java.util.Queue;

/**
 * Created by franky3er on 25.04.17.
 */
public class HTTPRequestHandler extends Thread {
    private Queue<Socket> clients;

    public HTTPRequestHandler(Queue<Socket> clients) {
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
            handle(clients.remove());
        }
    }

    private void handle(Socket client) {
        //TODO handle client
    }
}
