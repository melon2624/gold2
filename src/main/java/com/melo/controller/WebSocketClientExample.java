package com.melo.controller;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zhangxin
 * @date 2025-04-26 10:51
 */
public class WebSocketClientExample {


    private static final String WSS_URL = "wss://quote.wfgroup.com.hk:8083/socket.io/?token=applepieapplepieapplepieapplepie&EIO=3&transport=websocket";

    // WebSocket command constants
    private static final String SERVER_FIRST_MSG = "40";
    private static final String GET_PRICE_START = "40/bquote";
    private static final String KEEPALIVE_REQ = "2";
    private static final String KEEPALIVE_ACK = "3";

    private static int itemsGot = 0;

    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI(WSS_URL)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connected to server");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received message: " + message);

                // Handle handshake response (server sends '40')
                if (message.equals(SERVER_FIRST_MSG)) {
                    System.out.println("Got handshake req, sending start cmd...");
                    send(GET_PRICE_START);
                }

                // Handle keepalive response (server sends '3')
                if (message.equals(KEEPALIVE_ACK)) {
                    System.out.println("Got server keepalive ACK!");
                }

                // Handle price data (messages starting with '42/bquote')
                if (message.startsWith("42/bquote")) {
                    itemsGot++;

                    // Send keepalive every 25 items
                    if (itemsGot > 0 && itemsGot % 25 == 0) {
                        System.out.println("Sending keepalive to WSS server!");
                        send(KEEPALIVE_REQ);
                    }

                    // Extract the JSON part from the message
                    String jsonMessage = message.substring(28, message.length() - 1);

                    // Parse the JSON and print the relevant data
                    JSONObject jsonObject = new JSONObject(jsonMessage);
                    System.out.println(jsonObject.getJSONObject("products").getString("HKD="));
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Closed with exit code: " + code + " additional info: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        };

        // Connect to the WebSocket server
        client.connect();
    }

    // Send a message to the server
    private static void send(String message) {
        WebSocketClient client = null; // Assuming the client instance is available
        if (client != null && client.isOpen()) {
            client.send(message);
        }
    }


}
