/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author USER
 */
//@WebServlet(name = "websocket", urlPatterns = {"/websocket"})
@ServerEndpoint("/websocket")

public class websocket  {
    
    

    private static Set<Session> clients
            = Collections.synchronizedSet(new HashSet<Session>());

    
    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException {

        synchronized (clients) {
            // Iterate over the connected sessions
            // and broadcast the received message
            for (Session client : clients) {
                if (!client.equals(session)) {
                    client.getBasicRemote().sendText(message);
                }
            }
        }

    }

    @OnOpen
    public void onOpen(Session session
    ) {
        // Add session to the connected sessions set
        clients.add(session);
    }

    @OnClose
    public void onClose(Session session
    ) {
        // Remove session from the connected sessions set
        clients.remove(session);
    }


}
