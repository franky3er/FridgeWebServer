package vs.fridgewebserver.http;

import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

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
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            HTTPRequest httpRequest = new HTTPRequest();
            httpRequest.parseRequest(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HTTPRequestException e) {
            e.printStackTrace();
        }
    }
}
