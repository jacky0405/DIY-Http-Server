package com.diyHttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketProcessor implements Runnable {

    private Socket socket;

    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        processSocket(socket);
    }

    private void processSocket(Socket socket) {
        // 處理socket連接
        if (socket == null) {
            throw new IllegalArgumentException("socket can't be null.");
        }

        try {
            // Get Socket InputStream
            Request httpRequest = HttpMessageParser.parse2request(socket.getInputStream());
            System.out.println(httpRequest.toString());
            // Socket OutputStream
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream);

            try {
                String result = "DIY-http response succeed";
                String response = HttpMessageParser.buildResponse(httpRequest, result);
                System.out.println("Http Response: ");
                System.out.println(response);
                out.print(response);
            } catch (Exception e) {
                String response = HttpMessageParser.buildResponse(httpRequest, e.toString());
                out.print(response);
            }
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
