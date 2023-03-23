import java.io.IOException;

/*
 * Fonction main de la classe Pilote
 * A lancer sur le robot
 * G. Le Corre - E. Chanthery
 * Juin 2014
 * Soares Danilo revision Septembre 2017
 */
public class Pilote {

	   public static void main(String[] args) throws IOException{
	    	MissionRobot mission = new MissionRobot();
	    	mission.start();
	    }

}
