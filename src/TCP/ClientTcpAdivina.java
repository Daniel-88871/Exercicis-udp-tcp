package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTcpAdivina extends Thread {
	String hostname;
	int port;
	boolean continueConnected;
	int intents;
	Llista serverData;
	List<Integer> numClient = new ArrayList<>();

	Llista request = new Llista("Daniel",numClient);
	public ClientTcpAdivina(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		continueConnected = true;
		intents=0;
	}

	public void run() {
		numClient.add(2);
		numClient.add(6);
		numClient.add(1);
		numClient.add(10);
		numClient.add(10);
		numClient.add(2);

		Socket socket;

		ObjectInputStream in;
		ObjectOutputStream out;
		
		try {
			socket = new Socket(InetAddress.getByName(hostname), port);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			while(continueConnected){

				out.writeObject(request);
				out.flush();

				serverData = (Llista) in.readObject();
				System.out.println(serverData);
			}
		 	close(socket);
		} catch (UnknownHostException ex) {
			System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Error de connexió indefinit: " + ex.getMessage());
		}
	}

	public boolean mustFinish(String dades) {
		if (dades.equals("exit")) return false;
		return true;
	}
	
	private void close(Socket socket){
		try {
			if(socket!=null && !socket.isClosed()){
				if(!socket.isInputShutdown()){
					socket.shutdownInput();
				}
				if(!socket.isOutputShutdown()){
					socket.shutdownOutput();
				}
				socket.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(ClientTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static void main(String[] args) {
        ClientTcpAdivina clientTcp = new ClientTcpAdivina("localhost",5558);
        clientTcp.start();
	}
}