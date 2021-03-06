package TCP;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ThreadServidorAdivina implements Runnable {
	Socket clientSocket = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	Llista msgEntrant;
	boolean acabat;

	public ThreadServidorAdivina(Socket clientSocket) throws IOException {
		this.clientSocket = clientSocket;
		System.out.println("Contructor");
		acabat = false;
		out= new ObjectOutputStream(clientSocket.getOutputStream());
		in =new ObjectInputStream(clientSocket.getInputStream());
		System.out.println("Implementacio");
	}

	@Override
	public void run() {
		System.out.println("ThreadServer");
		try {
			while(!acabat) {

				 msgEntrant = (Llista) in.readObject();
				System.out.println(msgEntrant.toString());
				msgEntrant.getNumberList().sort(new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});
				for(int i =0; i< msgEntrant.getNumberList().size();i++){
					if(msgEntrant.getNumberList().get(i)==msgEntrant.getNumberList().get(i+1)){
						msgEntrant.getNumberList().remove(i);
					}
				}
				out.writeObject(msgEntrant);
				out.flush();
				acabat = true;
			}
		}catch(IOException | ClassNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}