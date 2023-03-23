

import java.io.IOException;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;


/* classe MissionRobot
 * Traite les missions envoyées par le PC sous 
 * la forme d'ordre en angle ou en distance + vitesse 
 * Gestion de la communication par bluetooth*/
	public class MissionRobot {
		private PilotRoberto pilotRoberto;
		private CommBTRobot bt;
		
		public MissionRobot() {
			super();
			pilotRoberto = new PilotRoberto();
			bt = new CommBTRobot();
		}
		
		public void start() throws IOException {
			bt.open();
			String st="";
			bt.send("roberto ok");
			st = bt.receive();
			
			while (!st.equals("mission is finished")) {
	    		Message msg = new Message(st);
	    		String typeOfst = msg.type();
	    		LCD.drawString("le type est: " + typeOfst, 0, 0);
	    		
	    		
	    		
	    		int val = msg.values().get(0);
	    		// si le message est de taille > 1 on prend la valeur suivante, sinon on met 0
	    		int speedval = (msg.values().size()>1)? msg.values().get(1):0;
	    		
				if (typeOfst.equals("angle")) {
					/* si le message est de type angle, on tourne*/
	    		    pilotRoberto.rotate(val);
	    			bt.send("roberto has finished to rotate");
	    		} else if (typeOfst.equals("distance")) {
	    			/* si le message est de type distance, on roule à la vitesse indiquée*/
	    			pilotRoberto.travels(val, speedval);
	    			double distP= pilotRoberto.getDistanceParcourue();
	    			double dist= pilotRoberto.getDist();
	    			bt.send("roberto has finished to move"+ distP +" " +dist);
	    		}
	    		st= bt.receive();
	    	}
			Sound.setVolume(50);
			Sound.twoBeeps();
	    	//bt.close();
		}
	}
