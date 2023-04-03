import java.io.IOException;
import java.net.ServerSocket;

public class Reactor {
    private ServerSocket serverSocket;
    private HandleMap handleMap;

    public Reactor (int port) {
        handleMap = new HandleMap();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
//        ThreadPerDispatcher dispatcher = new ThreadPerDispatcher();
        ThreadPoolDispatcher dispatcher = new ThreadPoolDispatcher();
        dispatcher.dispatch(serverSocket, handleMap);
    }

    public void registerHandler(String header, EventHandler handler) {
        handleMap.put(header, handler);
    }
}
