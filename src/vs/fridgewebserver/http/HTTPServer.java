package vs.fridgewebserver.http;

import vs.fridgewebserver.http.request.HTTPRequest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by franky3er on 26.04.17.
 */
public class HTTPServer {
    private ServerSocket server;
    private BlockingQueue<Socket> clients;

    public HTTPServer(int port, BlockingQueue<Socket> clients) throws IOException {
        this.server = new ServerSocket(port);
        this.clients = clients;
    }

    public void run() {
        while(true) {
            try {
                clients.put(this.server.accept());
            } catch (InterruptedException e) {
            } catch (IOException e) {
                System.err.println("ERROR : server.accept() failed");
                e.printStackTrace();
            }
        }
    }
}
