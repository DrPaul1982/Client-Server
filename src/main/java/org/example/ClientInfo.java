package org.example;

import lombok.Getter;

import java.net.Socket;
import java.time.LocalDateTime;

@Getter
public class ClientInfo {
    private final String clientName;
    private final LocalDateTime localDateTime;
    private final Socket socket;

    public ClientInfo(String clientName, LocalDateTime connectionTime, Socket socket) {
        this.socket = socket;
        this.localDateTime = connectionTime;
        this.clientName = clientName;
    }
}
