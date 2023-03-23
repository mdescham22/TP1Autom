import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class CommBTPC {
	
	public int PORT;
	public String IP;
	public Socket nxtComm;
	
	private BufferedReader br;
	private BufferedWriter bw;
	
	private Boolean verbose = true;
	
	public CommBTPC() {
		super();
		this.PORT = 1111;
		this.IP = "10.0.1.1"; // Adress IP du robot EV3
	}
	
	/**
	 * Constructeur
	 * 
	 * @param name
	 * @param address
	 */
	public CommBTPC(int PORT, String IP) {
		super();
		this.IP = IP;
		this.PORT = PORT;
		
	}
	public void setVerbose(Boolean bool) {
		verbose = bool;
	}

	/**
	 * Ouverture d'une connexion bluetooth
	 * @return 
	 */
	
	public void open(){
		try{
		nxtComm = new Socket(IP, PORT);

		this.br = new BufferedReader(new InputStreamReader(
				nxtComm.getInputStream()));
		this.bw = new BufferedWriter(new OutputStreamWriter(
				nxtComm.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Envoi d'un message de type angle
	 * @param angle
	 */
	public void sendAngle(int angle) {
		String st = "a=";
		st += String.valueOf(angle);
		send(st);
	}

	/**
	 * Envoi d'un message de type distance
	 * @param distance
	 */
	public void sendDistance(int distance, int speed) {
		String st = "d=";
		st += String.valueOf(distance);
		st += ", s=";
		st += String.valueOf(speed);
		send(st);
	}

	/**
	 * Envoi d'une cha”ne de caracteres
	 * 
	 * @param st
	 */
	public void send(String st) {
		try {
			if (verbose) System.out.println("envoi : " + st);
			bw.write(st);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * RŽception d'une chaine de caracteres
	 * 
	 * @return
	 */
	public String receive() {
		String st = "";
		try {
			st = br.readLine();
			if (verbose) System.out.println("recu : " + st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st;
	}

}
