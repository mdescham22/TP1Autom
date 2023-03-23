import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/* classe MissionPC : gestion de la succession des ordres envoyés au robot
 * 
 */
public class MissionPC {

	private CommBTPC btpc;
	private int state = 0;
	/**
	 * state 0 : en attente du dŽmarrage de roberto
	 * state 1 : envoi d'un ordre
	 * state 2 : roberto suit une ligne
	 * state 3 : roberto tourne
	 * state 4 : terminaison
	 */
	private ArrayList<Order> listOfOrders;

	public MissionPC(ArrayList<Order> listOfMovement) {
		super();
		// communication avec le robot : 
		// A CHANGER SI ROBOT DIFFERENT!!!
		this.btpc = new CommBTPC(1111, "10.0.1.1");
		this.listOfOrders = listOfMovement;
	}

	public void start(){
			btpc.open();
		System.out.println("C'est parti !");
		String st ="";

		st = btpc.receive();
		robertoIsOk();

		Enumeration<Order> e = Collections.enumeration(listOfOrders);
		while(e.hasMoreElements()) {
			Order order = e.nextElement();
			System.out.println(order);
			rotateRoberto(order.getAngle());			
			st = btpc.receive();
			robertoHasFinishedToRotate();
			moveRoberto(order.getDistance(), order.getSpeed());
			st = btpc.receive();
			robertoHasFinishedToMove();
		}
		stopRoberto();
	}

	private void rotateRoberto(int angle){
		if (state == 1) {
			btpc.sendAngle(angle);
			state = 3;
		} else {
			System.out.println("erreur envoi angle");
		}
	}

	private void moveRoberto(int distance, int speed) {
		if (state == 1) {
			btpc.sendDistance(distance, speed);
			state = 2;
		} else {
			System.out.println("erreur envoi distance");
		}
	}

	private void stopRoberto() {
		if (state == 1) {
			btpc.send("mission is finished");
			state =4;
		} else {
			System.out.println("erreur envoi stop");
		}
	}

	private void robertoHasFinishedToMove() {
		if (state == 2) {
			state = 1;
		} else {
			System.out.println("erreur roberto move");
		}

	}

	private void robertoHasFinishedToRotate() {
		if (state == 3) {
			state = 1;
		} else {
			System.out.println("erreur roberto rotate");
		}

	}

	private void robertoIsOk(){
		if (state == 0) {
			state = 1;
		} else {
			System.out.println("erreur roberto ok");
		}
	}

}
