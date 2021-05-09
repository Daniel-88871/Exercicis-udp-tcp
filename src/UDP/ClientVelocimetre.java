package UDP;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ClientVelocimetre {
    private int portDesti;
    private String ipServer;
    private InetAddress adrecaDesti;
    private MulticastSocket multicastSocket;
    private InetAddress multicastIP;
    InetSocketAddress groupMulticast;
    NetworkInterface netIf;
    boolean continueRunning = true;
    List<Integer> lista = new ArrayList<>();

    public ClientVelocimetre(String ip, int port) {
        this.portDesti = port;
        ipServer=ip;
        try {
            multicastSocket = new MulticastSocket(5557);
            multicastIP = InetAddress.getByName("224.0.2.12");
            groupMulticast = new InetSocketAddress(multicastIP,5557);
            netIf = NetworkInterface.getByName("fe80");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runClient()throws IOException{
      byte []  receivedData = new byte[1024];
      multicastSocket.joinGroup(groupMulticast,netIf);
      while(continueRunning){
          DatagramPacket datagramPacket = new DatagramPacket(receivedData,1024);
          multicastSocket.receive(datagramPacket);
          ByteBuffer bytebuffer = ByteBuffer.wrap(datagramPacket.getData());
          int numero = bytebuffer.getInt();
          System.out.println(numero);
          lista.add(numero);
          if(lista.size()==5){
              int primer = lista.get(0);
              int segon = lista.get(1);
              int tercer = lista.get(2);
              int cuart = lista.get(3);
              int cinque = lista.get(4);
              int velocitatMitjana = (primer+segon+tercer+cuart+cinque)/5;
              System.out.println("Velocitat Mitjana: "+velocitatMitjana);
              lista.clear();
          }
      }
        multicastSocket.leaveGroup(groupMulticast,netIf);
        multicastSocket.close();
    }

    public static void main(String[] args) {
        String ipSrv = "192.168.1.12";

        ClientVelocimetre clientVelocimetre = new ClientVelocimetre(ipSrv, 5556);

        try {
            clientVelocimetre.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}