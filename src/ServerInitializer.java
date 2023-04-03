import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.*;
import java.util.List;

public class ServerInitializer {

    public static void main(String[] args) {
        int port = 60000;
        System.out.println("SERVER ON :: " + port);

        Reactor reactor = new Reactor(port);

        try {
            Serializer serializer = new Persister();
            File source = new File("HandlerList.xml");
            ServerListData serverListData = serializer.read(ServerListData.class, source);

            for (HandlerListData handlerListData : serverListData.getServer()) {
                if ("server1".equals(handlerListData.getName())) {
                    List<HandleData> handleData = handlerListData.getHandler();
                    for(HandleData handler : handleData) {
                        try {
                            reactor.registerHandler(handler.getHeader(), (EventHandler) Class.forName(handler.getHandler()).newInstance());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reactor.startServer();
    }
}
