// DIJKSTRA 

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Thib
 * E.C - G.LC 2014
 */
public class Dijkstra {
    Map map;
    int noeud_init;
    int noeud_fin;
    int[] tab_noeuds; //tab_noeuds[i] = le noeud precedent de i
    double[] tab_value; //tableau des valeurs cumulées
    boolean[] tab_bool; //tableau pour savoir si on est déjà passé sur le point
    int fini;

    public Dijkstra(Map map, Point noeud_start, Point noeud_end) {

        this.map = map;
        this.noeud_init = noeud_start.recupInt();//On travaille sur noeud_init qui est un int
        this.noeud_fin = noeud_end.recupInt(); //On travaille sur noeud_fin qui est un int
        int NbNoeuds = this.map.getNbNoeud();
        
        this.fini = NbNoeuds - 1; // l'algo se finit si on explore tous les noeuds --> si fini arrive à 0
        this.tab_bool = new boolean[NbNoeuds]; //tableau des noeuds qu'il reste Ã  parcourir
        this.tab_value = new double[NbNoeuds]; //tableau des valeurs les plus petites pour aller de noeud_init au noeud courant
        this.tab_noeuds = new int[NbNoeuds];// tableau des prÃ©cÃ©dents de chaque noeud
       
        // INITIALISATION ----------------------------------------------
        for (int i = 0; i < this.map.getMap().length; i++) {
            this.tab_bool[i] = false;//init du tableau de booleens
            this.tab_value[i] = Double.MAX_VALUE;//init du tableau des chemins les plus courts
            this.tab_noeuds[i] = -1;//init du tableau des noeuds précédents
        }
        this.tab_value[noeud_init] = 0; // le noeud init est Ã  0 de lui-meme
        // FIN INITIATISATION -------------------------------------------
    }

    public void Run_Algo() {
        int noeud = this.noeud_init;
        while (this.fini != 0) {
            noeud = TrouvMin();
            MajCcum(noeud);
        }
    }

  
    public int TrouvMin() { //retourne le sommet le plus proche du sommet initial
     
        int noeud_retenu = -1;
        double[][] carte = this.map.getMap();
        double valmin = Double.MAX_VALUE;
        
        // recherche du noeud de valeur minimale non encore exploré
        for (int i = 0; i < carte[noeud_init].length; i++) {
        	 ////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
        	//A compléter
	
			}
        }

        if (noeud_retenu != -1) {
            tab_bool[noeud_retenu] = true;
            this.fini--; //on dÃ©crÃ©mente notre variable pour savoir quand on aura passÃ© en revue tous les noeuds
           
        } else {
            System.out.println("------->TrouvMin : pas de solution ");
        }

        return noeud_retenu;
    }

    // mise à jour de tab_value : tableau des valeurs de critère cumulées
    public void MajCcum(int noeud_retenu) {
        int i;

        double[][] map = this.map.getMap();

        for (i = 0; i < map[this.noeud_init].length; i++) {
        	// si le critère du noeud i en passant par le noeud retenu est meilleur alors on change de chemin
        	 ////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			//A compléter
      
        	}
        }
    }

    public int[] Tab_final() {
        int nombre_noeuds = 0;
        int boucle = this.noeud_fin;
        
      System.out.println("critere final: " + tab_value[boucle]);
        //on cherche le nombre de noeud que l'on va passer en revue pour arriver Ã  notre point de dÃ©part
        while (boucle != this.noeud_init) {
            boucle = tab_noeuds[boucle];
            nombre_noeuds++;
        }

        int[] tab_resul = new int[nombre_noeuds + 1];
        boucle = this.noeud_fin;

        //on remplit notre tableau final en remontant
        while (nombre_noeuds > -1) {
            //System.out.println("tab_resul[nombre_noeuds]: " + boucle + " est a: " + tab_noeuds[boucle]);
            tab_resul[nombre_noeuds] = boucle;
            boucle = tab_noeuds[boucle];
            nombre_noeuds--;
        }
        return tab_resul;
    }

    //sort un tableau de point Ã  partir d'un tableau de int
    public Point[] Tab_final_point(int[] tab_int, Point[] tab_point) {
        int i;
        Point[] resultat = new Point[tab_int.length];

        for (i = 0; i < tab_int.length; i++) {
            resultat[i] = tab_point[tab_int[i]];
        }
        return resultat;
    }
}
