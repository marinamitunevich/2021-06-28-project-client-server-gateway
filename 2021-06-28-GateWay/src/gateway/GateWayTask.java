package gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class GateWayTask implements Runnable {

    private Socket socketFromClient;
    private ServerDataManipulation serverData;

    public GateWayTask(Socket socketFromClient, ServerDataManipulation serverData) {
        this.socketFromClient = socketFromClient;
        this.serverData = serverData;
    }

    @Override
    public void run() {
        Socket gateWay = null;
        try {
            gateWay = new Socket(serverData.getServiceData().getServerHost(), serverData.getServiceData().getServerPort());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (BufferedReader bufferedReaderFromClient = new BufferedReader(new InputStreamReader(socketFromClient.getInputStream()));
             PrintStream printStreamToServer = new PrintStream(gateWay.getOutputStream());
             BufferedReader bufferedReaderFromServer = new BufferedReader(new InputStreamReader(gateWay.getInputStream()));
             PrintStream printStreamToClient = new PrintStream(socketFromClient.getOutputStream())) {

            String messageFromClient = bufferedReaderFromClient.readLine();
            printStreamToServer.println(messageFromClient);

            String messageFromServer = bufferedReaderFromServer.readLine();
            printStreamToClient.println(messageFromServer);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
}
