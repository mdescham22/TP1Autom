import java.util.ArrayList;

public class test_BasNiveau {
    
    public static void main(String[] args){
    	
    
    	ArrayList<Order> listOfOrders = new ArrayList<Order>();
    	//le robot tourne dans le sens horaire
    	
    	//test sur la ligne courbe
    	//listOfOrders.add(new Order(0, 2000, 300)); // tester le suivi de ligne
    	
    	
//    	test qui fait A, B, K, P, Q
    	listOfOrders.add(new Order(0, 360, 360)); //AB
    	listOfOrders.add(new Order(-74, 520, 360)); //BK
		listOfOrders.add(new Order(35, 330, 360)); //KP
		listOfOrders.add(new Order(0, 440, 360)); //PQ
		
		
    	MissionPC mission = new MissionPC(listOfOrders);
    	mission.start();
    }
   
}
