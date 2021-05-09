package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SrvTcpAdivina {
	int port;
	NombreSecret ns;
	
	public SrvTcpAdivina(int port ) {
		this.port = port;
	}
	
	public void listen() {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
			while(true) { //esperar connexió del client i llançar thread
				clientSocket = serverSocket.accept();
				System.out.println("Despres del accept");
				ThreadServidorAdivina FilServidor = new ThreadServidorAdivina(clientSocket);
				Thread client = new Thread(FilServidor);
				client.start();
			}
		} catch (IOException ex) {
			Logger.getLogger(SrvTcpAdivina.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) {
        SrvTcpAdivina srv = new SrvTcpAdivina(5558);
        srv.listen();
	}
}