// classe Point

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Thib
 */
public class Point {

    private String name; //nom du point
    private double x; //coordonnées en x
    private double y; //coordonnées en y

    public Point(String name, double x, double y) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
    public int recupInt(){ //recupère un Int à partir d'un Point
        String string_noeud = this.getName();
        char car_noeud = string_noeud.charAt(0);
        return((int) car_noeud - 65);
    }
    public String toString(){
        return this.getName();
    }

}