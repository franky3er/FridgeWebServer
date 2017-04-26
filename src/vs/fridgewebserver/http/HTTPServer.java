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
        this.server.setReuseAddress(true);
        this.clients = clients;
    }

    public void run() {
        System.out.println(String.format("INFO : %s running", this.getClass().getSimpleName()));
        System.out.flush();
        while(true) {
            try {
                Socket client = this.server.accept();
                clients.put(client);
                System.out.println(String.format("INFO : New Client [%s] connection put to Queue", client));
                System.out.flush();
            } catch (InterruptedException e) {
            } catch (IOException e) {
                System.err.println("ERROR : server.accept() failed");
                e.printStackTrace();
                System.out.flush();
            }
        }
    }
}
