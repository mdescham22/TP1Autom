/* classe Map
 * Gestion de la carte
 * Map est un tableau de double qui contient le poids de chaque arc
 * SpeedMap est un tableau de doucle qui contient la valeur de la vitesse sur chaque arc 
 */
public class Map {

    private int Nb_noeuds;
    private double[][] Map;
    public int[][] SpeedMap;
    
    public Map(int nb_de_noeuds) {
        double INF = Double.MAX_VALUE;

        this.Nb_noeuds = nb_de_noeuds;
        this.Map = new double[nb_de_noeuds][nb_de_noeuds];
        this.SpeedMap = new int[nb_de_noeuds][nb_de_noeuds];
        int i = 0;
        int j = 0;
        for (i = 0; i < nb_de_noeuds; i++) {
            for (j = 0; j < nb_de_noeuds; j++) {

                this.Map[i][j] = INF;
                this.SpeedMap[i][j] = 0;
            }
        }
    }
    

    //permet de mettre un poids entre les noeuds 1 et 2 (commence Ã  1)  avec A=1.. et de mettre la vitesse sur l'arc
    public void SetArc(Point noeud_1, Point noeud_2, int vitesse) {

        //On attribue une ligne et une colonne pour le couple de point donné
        String stringcol = noeud_1.getName();
        char car_col = stringcol.charAt(0);
        int col = (int) car_col - 65;

        String stringli = noeud_2.getName();
        char car_li = stringli.charAt(0);
        int li = (int) car_li - 65;

        this.SpeedMap[li][col] = vitesse;
        this.SpeedMap[col][li]= vitesse; 
        // calcul de la distance entre ces deux point:
        double distance = Math.sqrt(Math.pow(Math.abs(noeud_1.getX() - noeud_2.getX()), 2.0) + Math.pow(Math.abs(noeud_1.getY() - noeud_2.getY()), 2.0));
 
       /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // définition du critère du Dijkstra
        this.Map[li][col] = distance;
        this.Map[col][li] = distance;
                

    }

    public void removeArc(Point noeud_1, Point noeud_2) {

        //On attribue une ligne et une colonne pour le couple de point donnÃ©
        String stringcol = noeud_1.getName();
        char car_col = stringcol.charAt(0);
        int col = (int) car_col - 65;

        String stringli = noeud_2.getName();
        char car_li = stringli.charAt(0);
        int li = (int) car_li - 65;


        // mise Ã  l'infini de la distance entre les 2 points:
        double inf = Double.MAX_VALUE;
        this.Map[li][col] = inf;
        this.Map[col][li] = inf;

    }

    //permet d'afficher une matrice d'adjacence
    public void Affiche() {

        int i = 0;
        int j = 0;
        for (i = 0; i < this.Nb_noeuds; i++) {
            for (j = 0; j < this.Nb_noeuds; j++) {
                if (this.Map[i][j] == Double.MAX_VALUE) {
                    System.out.print("*" + "\t");
                } else {
                    System.out.print(this.Map[i][j] + "\t");

                }
            }
            System.out.println("\n");
        }
    }
    
    public void AfficheA() {

        int i = 0;
        int j = 0;
            for (j = 0; j < this.Nb_noeuds; j++) {
                if (this.Map[i][j] == Double.MAX_VALUE) {
                    System.out.print("*" + "\t");
                } else {
                    System.out.print(this.Map[i][j] + "\t");

                }
            }
            System.out.println("\n");
        //}
    }

    // permet de calculer l'angle entre deux points
    public float getAngle(Point A, Point B) {

        // attention on se place en position initial sur l'axe desx en allant vers le +x
        double alpha = Math.atan((double) (B.getY() - A.getY()) / (B.getX() - A.getX()));
        alpha = alpha * 180 / Math.PI;
        // si Delta X < 0 et deltaY >0, il faut ajouter pi Ã  l'angle et si DeltaY,DeltaX <0 alors on enlÃ¨ve pi
        if (B.getX() - A.getX() < 0) {
            if (B.getY() - A.getY() < 0) {
                alpha -= 180.;
            } else {
                alpha += 180.;
            }
        }
        
        return (float) alpha;

    }

    public double[][] getMap() {

        return this.Map;
    }
    
    public int[][] getSpeedMap() {

        return this.SpeedMap;
    }
    
    public int getNbNoeud() {
        return this.Nb_noeuds;
    }

    public double getDistance(Point A, Point B) {
    	double distance = Math.sqrt(Math.pow(Math.abs(A.getX() - B.getX()), 2.0) + Math.pow(Math.abs(A.getY() - B.getY()), 2.0));
    	return distance;
    }
    
    public int getSpeed(Point A, Point B) {
    	String stringA = A.getName();
        char carA = stringA.charAt(0);
        int colA = (int) carA - 65;

        String stringB = B.getName();
        char carB = stringB.charAt(0);
        int colB = (int) carB - 65;

        return this.SpeedMap[colA][colB];
    }
    
    public void remplirMap(Map map_bien){
                int i = 0;
        int j = 0;
        for (i = 0; i < map_bien.getNbNoeud(); i++) {
            for (j = 0; j < map_bien.getNbNoeud(); j++) {

                this.getMap()[i][j] = map_bien.getMap()[i][j];
            }
        }
    }
}
