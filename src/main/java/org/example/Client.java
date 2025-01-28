package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public void start() {
        try {
            Socket socket = new Socket(HOST, PORT);

            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter write = new PrintWriter(socket.getOutputStream(), true);

            logger.info("Client at server {}:{}", HOST, PORT);
            Scanner scanner = new Scanner(System.in);

            Thread readerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = read.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    logger.error("Error reading from server: {}", e.getMessage());
                }
            });
            readerThread.start();

            while (true) {
                String input = scanner.nextLine();
                write.println(input);
                if ("exit".equalsIgnoreCase(input)) {
                    break;
                }
            }

        } catch (IOException e) {
            logger.error("Error connecting to server: {}", e.getMessage());
        }
    }
}
