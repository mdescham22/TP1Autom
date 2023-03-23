

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;


/* Gestion de la communication bluetooth avec le PC
 * 
 */
public class CommBTRobot {
	
	
	private BufferedReader br;
	private BufferedWriter bw;
	private Socket connection;
	private ServerSocket server;
	private boolean verbose = true;
	
	/**
	 * Creation de la communication via Bluetooth (fonction bloquante)
	 */ 
	
	public void open() throws IOException {
		LCD.drawString("    Attente", 0, 1);
		LCD.drawString("       de", 0, 2);
		LCD.drawString("    connexion", 0, 3);
		
			try {
				server = new ServerSocket(1111);
			} catch (IOException e) {
				//System.out.println("Could not listen on port 4321");
				System.exit(-1);
			}
		 	try{
			    connection = server.accept();
			  } catch (IOException e) {
			    //System.out.println("Accept failed: 4321");
			    System.exit(-1);
			  }	
		
			this.br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			this.bw = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream()));
		
		LCD.clear();
		LCD.drawString("Connecte", 0, 3);
		
		
	}
	public void close() {
		try {
			this.br.close();
			this.bw.close();

			LCD.clear();
			LCD.drawString("Connexion terminee", 0, 1);

			this.connection.close();
			LCD.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Reception de chaine de caracteres (fonction bloquante)
	 */
	public String receive() throws IOException {
		String st = "";
		st = br.readLine();
		if (verbose) {
			LCD.clear();
			LCD.drawString("recu : " + st, 0, 1);
		}
		return st;
	}
	/**
	 * Envoi d'une chaine de caractere
	 * 
	 * @param st
	 */
	public void send(String st)throws IOException {
		try {
			if (verbose) {
				LCD.clear();
				LCD.drawString("envoi : " + st, 0, 1);
			}
			bw.write(st);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CommBTRobot bt = new CommBTRobot();
		bt.testComm(bt);		
	}
	
	private void testComm(CommBTRobot bt) {
		String st = "";

		try {
			bt.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		while (st != "stop") {
			st = bt.toString();
			try {
				bt.send(st);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bt.close();
	}
}
