package com.diyHttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicHttpServer {

    public void start() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new SocketProcessor(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicHttpServer server = new BasicHttpServer();
        server.start();
    }
}
