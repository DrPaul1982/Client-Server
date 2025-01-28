package org.example;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final Socket clientSocket;
    @Getter
    private final String clientName;
    private final Server server;


    public ClientHandler(Socket socket, String clientName, Map<String, ClientInfo> onlineClients, Server server) {
        this.clientSocket = socket;
        this.clientName = clientName;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader clientMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter write = new PrintWriter(clientSocket.getOutputStream(), true);
            String outcomingMessage;
            while ((outcomingMessage = clientMessage.readLine()) != null) {
                logger.info("Send message from {} {}", clientName, outcomingMessage);
                write.println("Your message " + outcomingMessage + " was sent.");

                if ("exit".equalsIgnoreCase(outcomingMessage)) {
                    write.println("Goodbye, " + clientName + "!");
                    server.removeClient(clientName);
                    logger.info("[{}] disconnected.", clientName);
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error handling client: {}", e.getMessage());
        } finally {
            try {
                clientSocket.close();
                logger.info("Client connection closed: {}", clientSocket.getInetAddress());
            } catch (IOException e) {
                logger.error("Error closing client socket: {}", e.getMessage());
            }
        }
    }
}