import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import javax.swing.WindowConstants;

/*EC GLC : Shortest path - 05-2014
 * Classe principale du PC 
 * Mise en place de la carte
 * Lancement de l'interface graphique
 * lancement du dikjstra
 * construction de la mission
 * lancement de la mission sur le robot
 */


public class ShortestPath {

    public DataInputStream dis;
    public DataOutputStream dos;
    public Socket conn;
    private int noeud_actuel=0;
    public Point[] resul_point;
    public Map Map;
    public Point noeud_init; 
    public Point noeud_fin;
    public Point[] tab_point;
    public int angle = 0;
    public int distance = 0;
    public int speed = 0;
    public int ancien_angle_x;
    public int angle_x;
    public boolean defini;
    public InterfaceDialogue monCadre;
    public Vector obj;
    private Point noeud_obj;
    public ArrayList<Order> listOfOrders;
    
    
    public static void main(String[] args) {
        ShortestPath BuildPath = new ShortestPath();
        //NE PAS MODIFIER//
        BuildPath.SetMap();  //déclaration carte
        BuildPath.Selection();  //interface graphique
        //NE PAS MODIFIER//
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        // Algorithme permettant d'obtenir une liste de noeud soit un chemin
       // System.out.println("debut Dij");
        BuildPath.ComputeDijkstra();
        System.out.println("Fin Dij");
        //NE PAS MODIFIER//
        try {
			BuildPath.ComputeOrder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MissionPC mission = new MissionPC(BuildPath.listOfOrders);
    	mission.start();
    }

    // création de la carte, création des arcs, affichage de la carte
    public void SetMap(){
    	int nombre_noeud_map = 20;
    	// Création des points souhaités avec leurs coordonnées
        tab_point = new Point[nombre_noeud_map];
        int nb = 0;
        ancien_angle_x = 0;
        angle_x = 0;

        Map = new Map(nombre_noeud_map);
        // crétaion d'un point dans la carte
        // Point NomDuPoint = new Point("NomDuPoint", coordonneesEnX, coordonnees en Y);
        // distance données en mm 
        Point A = new Point("A", 250, 160);
        tab_point[nb] = A;
        nb++;
        Point B = new Point("B", 600, 160);
        tab_point[nb] = B;
        nb++;
        Point C = new Point("C", 1380, 180);
        tab_point[nb] = C;
        nb++;
        Point D = new Point("D", 1880, 180);
        tab_point[nb] = D;
        nb++;
        Point E = new Point("E", 2350, 170);
        tab_point[nb] = E;
        nb++;
        Point F = new Point("F", 2610, 600);
        tab_point[nb] = F;
        nb++;
        Point G = new Point("G", 1960, 620);
        tab_point[nb] = G;
        nb++;
        Point H = new Point("H", 1780, 470);
        tab_point[nb] = H;
        nb++;
        Point I = new Point("I", 1420, 630);
        tab_point[nb] = I;
        nb++;
        Point J = new Point("J", 1180, 340);
        tab_point[nb] = J;
        nb++;
        Point K = new Point("K", 760, 650);
        tab_point[nb] = K;
        nb++;
        Point L = new Point("L", 370, 450);
        tab_point[nb] = L;
        nb++;
        Point M = new Point("M", 130, 730);
        tab_point[nb] = M;
        nb++;
        Point N = new Point("N", 330, 930);
        tab_point[nb] = N;
        nb++;
        Point O = new Point("O", 530, 790);
        tab_point[nb] = O;
        nb++;
        Point P = new Point("P", 1020, 840);
        tab_point[nb] = P;
        nb++;
        Point Q = new Point("Q", 1380, 1100);
        tab_point[nb] = Q;
        nb++;
        Point R = new Point("R", 1950, 900);
        tab_point[nb] = R;
        nb++;
        Point S = new Point("S", 2470, 890);
        tab_point[nb] = S;
        nb++;
        Point T = new Point("T", 580, 1100);
        tab_point[nb] = T;


        // Creation des arcs
        //Map.SetArc(PointDeDépart, PointDArrivee, vitesseSurArc);
        // vitesseSurArc en pourcentage 
        Map.SetArc(A, B, 360);
        Map.SetArc(A, L, 360);
        Map.SetArc(B, C, 60);
        Map.SetArc(B, K, 360);
        Map.SetArc(C, D, 360);
        Map.SetArc(C, J, 360);
        Map.SetArc(C, H, 360);
        Map.SetArc(D, E, 360);
        Map.SetArc(D, H, 360);
        Map.SetArc(E, F, 360);
        Map.SetArc(F, G, 360);
        Map.SetArc(F, S, 360);
        Map.SetArc(G, H, 360);
        Map.SetArc(G, I, 360);
        Map.SetArc(G, R, 360);
        Map.SetArc(I, J, 360);
        Map.SetArc(I, Q, 360);
        Map.SetArc(J, K, 360);
        Map.SetArc(K, O, 360);
        Map.SetArc(K, P, 360);
        Map.SetArc(L, M, 360);
        Map.SetArc(L, O, 360);
        Map.SetArc(M, N, 360);
        Map.SetArc(N, O, 360);
        Map.SetArc(N, T, 360);
        Map.SetArc(P, Q, 360);
        Map.SetArc(P, T, 360);
        Map.SetArc(Q, R, 360);
        Map.SetArc(R, S, 360);
  
        Map.Affiche();
    }
        
    
    /* Calcul du Dikjstra
     * 
     */
    public void ComputeDijkstra() {
        
        System.out.println("======== Calcul du Dikjstra ======");
        Dijkstra My_Dijkstra = new Dijkstra(Map, noeud_init, noeud_fin);
        My_Dijkstra.Run_Algo();

        int[] resul = My_Dijkstra.Tab_final();
        resul_point = My_Dijkstra.Tab_final_point(resul, tab_point);

        System.out.println("nombre de points visités: " + resul.length);
        for (int i = 0; i < resul.length; i++) {
            System.out.println("valeur: " + this.recupPoint(resul[i]));
        }
        
    }
    
    /* Construction des ordres à envoyer au robot
     * Ordre : angle ou (distance + vitesse)
     * 
     */
    public void ComputeOrder() throws IOException{
    	listOfOrders = new ArrayList<Order>(); 
    	
    	 boolean fini = false;
         noeud_actuel = 0;
         
         while( fini== false) {
        	 
        	 angle_x =- (int) Map.getAngle(resul_point[noeud_actuel], resul_point[noeud_actuel + 1]);
             distance = (int) Map.getDistance(resul_point[noeud_actuel], resul_point[noeud_actuel + 1]);
             speed = Map.getSpeed(resul_point[noeud_actuel], resul_point[noeud_actuel + 1]);

             angle = angle_x - ancien_angle_x;
             ancien_angle_x = angle_x;
             if (angle > 180) {
                 angle = angle - 360;
             }
             if (angle < -180) {
                 angle = angle + 360;
             }

             listOfOrders.add(new Order(angle, distance, speed));
        	 if ((resul_point[noeud_actuel + 1]) == noeud_fin) {
                 fini = true;
             }
        	 noeud_actuel++;
         }
    	 


         

    }
    
    // Selection : lance l'interface graphique, choix des points de départ, arrivée et objectifs intermédiaires
    void Selection() {
        this.monCadre = new InterfaceDialogue(this);
        monCadre.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        monCadre.pack();
        monCadre.setLocationRelativeTo(null);
        monCadre.setVisible(true);
        while (monCadre.isVisible()) {
        	System.out.println("");
            }
    
    }

    public Point recupPoint(int i) {
        return tab_point[i];
    }
}
