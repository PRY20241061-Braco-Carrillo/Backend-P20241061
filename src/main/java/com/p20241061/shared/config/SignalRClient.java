package com.p20241061.shared.config;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SignalRClient {
    private HubConnection hubConnection;

    public SignalRClient() {
        hubConnection = HubConnectionBuilder
                .create("https://websocket-signalr-dbhdhja5g5d9htah.eastus-01.azurewebsites.net/order-hub")
                .build();

        hubConnection.start().blockingAwait();
    }

    public void sendMessage(String user, String message) {
        hubConnection
                .invoke("SendMessage", user, message)
                .doOnError(err -> log.error(err.getMessage()));
    }
}
