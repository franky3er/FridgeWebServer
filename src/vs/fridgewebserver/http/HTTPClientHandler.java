package vs.fridgewebserver.http;

import vs.fridgewebserver.http.exception.HTTPRequestException;
import vs.fridgewebserver.http.request.HTTPRequest;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * This class works as a thread and handles incoming http requests which are saved from a ServerSocket in a
 * BlockingQueue of clients. This BlockingQueue is thread safe.
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

    /**
     * Handles the from the server socket accepted client, parse the http request and sends the client a http response back.
     *
     * @param client
     */
    private void handleRequest(Socket client) {
        System.out.println(String.format("INFO : %s [%s] handle client [%s]", this.getClass().getSimpleName(), getId(), client));
        System.out.flush();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            HTTPRequest httpRequest = new HTTPRequest();
            System.out.println(String.format("INFO : %s [%s] parse request of client [%s]", this.getClass().getSimpleName(), getId(), client));
            httpRequest.parseRequest(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HTTPRequestException e) {
            e.printStackTrace();
        }
    }
}
