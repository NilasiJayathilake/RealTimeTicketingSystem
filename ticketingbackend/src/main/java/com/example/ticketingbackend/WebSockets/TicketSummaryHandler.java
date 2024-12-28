package com.example.ticketingbackend.WebSockets;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class TicketSummaryHandler extends TextWebSocketHandler {
    private List<WebSocketSession> sessionsList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessionsList.add(session);
    }
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        session.sendMessage(new TextMessage("{\"ticketsAdded\": 0, \"ticketsPurchased\": 0, \"revenue\": 0}"));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionsList.remove(session);
    }
    public void broadCastSummary(String summary) throws IOException{
        for (WebSocketSession session: sessionsList){
            if (session.isOpen()){
                session.sendMessage(new TextMessage(summary));
            }
        }
    }
}
