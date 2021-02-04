package me.mouamle.cs50_polls;

import com.google.gson.Gson;
import me.mouamle.cs50_polls.events.VoteEvent;
import me.mouamle.cs50_polls.util.VotesService;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends WebSocketServer {

    private final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ConcurrentLinkedQueue<WebSocket> connections = new ConcurrentLinkedQueue<>();

    public Server(InetSocketAddress address) {
        super(address);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVote(VoteEvent event) {
        for (WebSocket connection : connections) {
            connection.send(gson.toJson(event));
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        logger.info("New Connection");
        connections.add(webSocket);
        webSocket.send(gson.toJson(VotesService.getAllVotes()));
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        connections.remove(webSocket);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        logger.warn("Caught exception: ", e);
        connections.remove(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) { }

    @Override
    public void onStart() {
        logger.info("WS Server started.");
    }

}
