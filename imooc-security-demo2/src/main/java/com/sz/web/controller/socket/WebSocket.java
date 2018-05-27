package com.sz.web.controller.socket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;


@ServerEndpoint("/websocket/{username}") 
@Component
public class WebSocket {  
  
    private static int onlineCount = 0;  
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>(); 
    public Session session;  
    private String username;  
      
    @OnOpen  
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {  
        this.username = username;  
        this.session = session;  
        //addOnlineCount();  
        clients.put(username, this);  
        System.out.println(username+":已连接");
    }  
  
    @OnClose  
    public void onClose() throws IOException {  
        clients.remove(username);  
        //subOnlineCount();  
        System.out.println(username+":已下线");
    }  
  
    @OnMessage  
    public void onMessage(String message) throws IOException {  
  
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode readTree = mapper.readTree(message);
        if (!readTree.get("To").equals("All")){  
            sendMessageTo("给一个人", readTree.get("To").toString());  
        }else{  
            sendMessageAll("给所有人");  
        }  
    }  
  
    @OnError  
    public void onError(Session session, Throwable error) {  
        error.printStackTrace();  
    }  
  
    public void sendMessageTo(String message, String To) throws IOException {  
       /* for (WebSocket item : clients.values()) {  
            if (item.username.equals(To) )  
                item.session.getAsyncRemote().sendText(message);  
        }*/
        WebSocket webSocket = clients.get(To);
        if(webSocket!=null) {
          	webSocket.session.getAsyncRemote().sendText(message);
        }
    }  
      
    public void sendMessageAll(String message) throws IOException {  
        for (WebSocket item : clients.values()) {  
            item.session.getAsyncRemote().sendText(message);  
        }  
    }  
      
      
  
    public static synchronized int getOnlineCount() {  
        return onlineCount;  
    }  
  
    public static synchronized void addOnlineCount() {  
        WebSocket.onlineCount++;  
    }  
  
    public static synchronized void subOnlineCount() {  
        WebSocket.onlineCount--;  
    }  
  
    public static synchronized Map<String, WebSocket> getClients() {  
        return clients;  
    }  
}  