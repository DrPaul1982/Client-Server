package org.example;


import static org.mockito.Mockito.*;
import java.net.Socket;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientHandlerTest {

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
