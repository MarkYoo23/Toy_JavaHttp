package com.mark.http.client;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;

public class App {
    final int readBufferSize = 1000000;

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.run();
    }

    private void run() throws IOException {
        String host = "example.com";
        int port = 80;

        try (Socket socket = new Socket()){
            System.out.println("Connect");

            String message = """
                GET/ HTTP/1.1
                Host:example.com
                
                """;

            boolean isUseWriter = true;

            OutputStream outputStream = socket.getOutputStream();

            if(isUseWriter){
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(message);
                outputStreamWriter.flush();
            }
            else {
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
                outputStream.flush();
            }

            System.out.println("Request");

            InputStream inputStream = socket.getInputStream();

            boolean isUseReader = true;

            if(isUseReader){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                CharBuffer charBuffer = CharBuffer.allocate(readBufferSize);
                int readSize = inputStreamReader.read(charBuffer);

                charBuffer.flip();

                System.out.println("Read Size : " + readSize);
                System.out.println(charBuffer.toString());
            }
            else{
                byte[] readBytes = new byte[readBufferSize];
                int readSize = inputStream.read(readBytes);

                System.out.println("Read Size : " + readSize);

                // TODO : (dh) byte to string
            }

            System.out.println("Response");

            socket.close();

            System.out.println("Close");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
