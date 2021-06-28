package gateway;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpConnection implements Runnable {
    private ServerSocket gateWay;
    private ServerDataManipulation serverData;

    public TcpConnection(ServerDataManipulation serverData, int tcpPort) throws IOException {
        gateWay = new ServerSocket(tcpPort);
        this.serverData = serverData;
    }

    @Override
    public void run() {
        ExecutorService service = Executors.newFixedThreadPool(50);
        Socket socketFromClient = null;

        while (true) {
            try {
                socketFromClient = gateWay.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("New connection from Client");
            service.execute(new GateWayTask(socketFromClient, serverData));


        }

    }
}
