package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final int PORT = 8080;
    ExecutorService clientList = Executors.newCachedThreadPool();
    private final Map<String, ClientInfo> onlineClients = new ConcurrentHashMap<>();
    private final AtomicInteger clientCounter = new AtomicInteger(0);



    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server is starting. Waiting for clients...");
            logger.info("Server is listening on port {}", PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientName = "client-" + clientCounter.incrementAndGet();

                logger.info("[SERVER] {} connected", clientName);

                ClientInfo clientInfo = new ClientInfo(clientName, LocalDateTime.now(), clientSocket);
                onlineClients.put(clientName, clientInfo);

                logger.info("[SERVER] {} connected at {}", clientInfo.getClientName(), clientInfo.getLocalDateTime());

                clientList.submit(new ClientHandler(clientSocket, clientName, onlineClients, this));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

public void removeClient(String clientName) {
    onlineClients.remove(clientName);
    logger.info("[SERVER] {} disconnected", clientName);
}
}
