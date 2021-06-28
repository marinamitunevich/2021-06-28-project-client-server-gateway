package gateway;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final String CONFIG_PATH = "config.props";

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        properties.load(new FileReader(CONFIG_PATH));
        ServerDataManipulation serverData = new ServerDataManipulation();

        int updPort = Integer.parseInt(properties.getProperty("UPD_PORT"));
        int tcpPort = Integer.parseInt(properties.getProperty("TCP_PORT"));
        int size = Integer.parseInt(properties.getProperty("DATA_SIZE"));

        TcpConnection tcpConnection = new TcpConnection(serverData, tcpPort);
        UdpConnection udpConnection = new UdpConnection(serverData, updPort, size);

        Thread threadTCP = new Thread(tcpConnection);
        Thread threadUDP = new Thread(udpConnection);
        threadTCP.start();
        threadUDP.start();


    }
}
