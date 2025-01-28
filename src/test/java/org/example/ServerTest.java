package org.example;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import java.net.Socket;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @Test
    public void testClientInfoConstructor() {

        String clientName = "clientName";
        LocalDateTime connectionTime = LocalDateTime.now();
        Socket mockSocket = mock(Socket.class);  // Мокируем Socket


        ClientInfo clientInfo = new ClientInfo(clientName, connectionTime, mockSocket);


        assertNotNull(clientInfo);
        assertEquals(clientName, clientInfo.getClientName());
        assertEquals(connectionTime, clientInfo.getLocalDateTime());
        assertEquals(mockSocket, clientInfo.getSocket());
    }
}
