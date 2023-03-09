package com.mark.http.server.factories;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HiContextFactory {
    public HttpHandler create() {
        return exchange -> {
            displayRequest(exchange);

            String content = "Hi, World!";
            sendContent(exchange, content);
        };
    }

    private void displayRequest(HttpExchange exchange) throws IOException {
        System.out.println("method" + exchange.getRequestMethod());
        System.out.println("path" + exchange.getRequestURI().getPath());

        var headers = exchange.getRequestHeaders();

        for (String key : headers.keySet()) {
            var values = headers.get(key);
            System.out.println(key + ": " + values);
        }

        String inputBody = new String(exchange.getRequestBody().readAllBytes());
        System.out.println(inputBody);
    }

    private void sendContent(HttpExchange exchange, String content) throws IOException {
        var contentBytes = content.getBytes();

        exchange.sendResponseHeaders(200, contentBytes.length);

        var outputStream = exchange.getResponseBody();
        outputStream.write(contentBytes);
        outputStream.flush();
    }
}
