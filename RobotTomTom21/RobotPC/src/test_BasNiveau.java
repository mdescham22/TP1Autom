import java.util.ArrayList;

public class test_BasNiveau {
    
    public static void main(String[] args){
    	
    
    	ArrayList<Order> listOfOrders = new ArrayList<Order>();
    	//le robot tourne dans le sens horaire
    	
    	//test sur la ligne courbe
    	listOfOrders.add(new Order(0, 2000, 360)); // tester le suivi de ligne
    	
    	
    	//test qui fait A, B, C, J
    	//listOfOrders.add(new Order(0, 350, 360));
    	//listOfOrders.add(new Order(1, 780, 200));
		//listOfOrders.add(new Order(-150, 280, 400));
		
    	MissionPC mission = new MissionPC(listOfOrders);
    	mission.start();
    }
   
}
