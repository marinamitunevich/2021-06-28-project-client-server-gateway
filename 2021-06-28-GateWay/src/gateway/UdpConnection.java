package gateway;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpConnection implements Runnable {

    private DatagramSocket udpSocket;
    private ServerDataManipulation serverData;
    private int size;

    public UdpConnection(ServerDataManipulation serverData, int updPort, int size) throws SocketException {
        this.serverData = serverData;
        this.udpSocket = new DatagramSocket(updPort);
        this.size = size;
    }

    @Override
    public void run() {
        byte[] dataIn = new byte[size];
        DatagramPacket pocketFromLB = new DatagramPacket(dataIn, dataIn.length);

        while (true) {
            try {
                udpSocket.receive(pocketFromLB);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String messageFromLB = new String(dataIn, 0, pocketFromLB.getLength());

            String host = messageFromLB.split(":")[0];
            int port = Integer.parseInt(messageFromLB.split(":")[1]);

            ServiceData newServiceData = new ServiceData(host, port);
            serverData.renewData(newServiceData);


        }


    }
}
