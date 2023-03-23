/* classe InterfaceDialogue
 * Interface graphique pour fixer le point de départ, le point d'arrivee et lancer la mission
 */
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.Vector;

@SuppressWarnings("serial")
class InterfaceDialogue extends JFrame implements ActionListener {

    private List lst;
    private List lst_point;
    private List lst_arr;
    public Vector tab_obj;
    private Point[] tab_point;
    JLabel label = new JLabel("Selectionnez, definissez, validez et lancez..");
    private ShortestPath bt;
    private Point Depart;
    private Point Arrivee;
    private boolean valide;

    InterfaceDialogue(ShortestPath shortestPath) {
        JPanel lesBoutons = new JPanel();
        Container interieur = getContentPane();
        lst = new List(4, false);
        lst_point = new List(4, false);
        lst_arr = new List(4, false);
        this.bt = shortestPath;
        this.tab_point = shortestPath.tab_point;
        this.valide = false;

        lesBoutons.setLayout(new GridLayout(6, 15));

        lesBoutons.add(new Label("Choix du pt de départ"));// choix du Point de dÃ©part
        ajouteListeNoeud(lesBoutons, lst_point);

        lesBoutons.add(new Label("Choix du pt d'arrivée"));// choix du Point d'arrivÃ©e
        ajouteListeNoeud(lesBoutons, lst_arr);


        lesBoutons.add(new Label(" "));
        ajoute("Valider", lesBoutons);

        lesBoutons.add(new Label(" "));
        ajoute("Go go go", lesBoutons);

        interieur.setLayout(new BorderLayout(5, 5));
        interieur.add(lesBoutons, BorderLayout.CENTER);
        interieur.add(label, BorderLayout.SOUTH);
    }

    void ajouteListe(JPanel lesBoutons) {

        int i;
        String j;
        for (i = 0; i < this.tab_point.length; i++) {
            j = Integer.toString(i);
            lst.add(j);
        }

        lesBoutons.add(lst);
    }

    void ajouteListeNoeud(JPanel lesBoutons, List list) {
        int i;
        for (i = 0; i < this.bt.Map.getNbNoeud(); i++) {
            list.add(tab_point[i].getName());
        }
        lesBoutons.add(list);
    }

    void ajoute(String s, JPanel lesBoutons) {
        Button bouton = new Button(s);;

        bouton.setActionCommand(s);
        bouton.addActionListener(this);
        lesBoutons.add(bouton);
    }

    public void actionPerformed(ActionEvent e) {
        String nom = e.getActionCommand();

        if (nom.equals("Definir")) {
            this.valide = false;
            int i;
            int select = lst.getSelectedIndex();
            if (select != 0) {
                tab_obj = new Vector(select);

                for (i = 0; i < select; i++) {
                    String message = "Quel est l'objectif numero " + Integer.toString(i + 1) + " ?";
                    tab_obj.add(i, (Point) JOptionPane.showInputDialog(this, message, null, JOptionPane.QUESTION_MESSAGE, null, this.tab_point, null));
                }
            } else {
                tab_obj = null;
                JOptionPane.showMessageDialog(this, "Vous avez demande 0 objectifs... Aucune definition possible !",
                        "ATTENTION",
                        JOptionPane.ERROR_MESSAGE);
            }


        } else if (nom.equals("Valider")) {
            JOptionPane.showMessageDialog(this, "Points choisis pris en compte",
                    "Validation",
                    JOptionPane.PLAIN_MESSAGE);
            this.valide = true;

            //validation des noeud
            this.bt.noeud_init = this.bt.recupPoint(this.lst_point.getSelectedIndex());
            System.out.println("noeud Depart : " + this.bt.noeud_init.getName());
            this.bt.noeud_fin = this.bt.recupPoint(this.lst_arr.getSelectedIndex());
            System.out.println("noeud Arrivee : " + this.bt.noeud_fin.getName());
           
            if (this.tab_obj != null){
            int index = this.tab_obj.indexOf(this.bt.noeud_init);
            int index2 = this.tab_obj.indexOf(this.bt.noeud_fin);
            if (index != -1) {
                JOptionPane.showMessageDialog(this, "Vous avez selectionne un objectif similaire au point de depart.. Objectif supprime",
                        "ATTENTION",
                        JOptionPane.ERROR_MESSAGE);
                this.tab_obj.remove(index);

            }
            if (index2 != -1) {
                JOptionPane.showMessageDialog(this, "Vous avez selectionne un objectif similaire au point d'arrivee.. Objectif supprime",
                        "ATTENTION",
                        JOptionPane.ERROR_MESSAGE);
                this.tab_obj.remove(index2);

            }}
            if (this.tab_obj != null) {
                //Affichage du tableau d'objectif
                int i;
                System.out.println("------------Avant remise en ordre----------------------");

                this.bt.obj = this.tab_obj;
                for (i = 0; i < tab_obj.size(); i++) {
                    System.out.println("le noeud objectif " + (i + 1) + " est: " + (Point) (this.tab_obj.elementAt(i)));
                }
                System.out.println("-------------------------------------------------------");
            }





        } else if (nom.equals("Go go go")) {

            if (!this.valide) {
                JOptionPane.showMessageDialog(this, "Veuillez valider vos points...",
                        "ATTENTION",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Fermeture de la fenetre et lancement du robot..");
                //this.dispose();
                this.setVisible(false);
            }
        }
    }

    public Vector getTabObj() {
        return this.tab_obj;
    }

    public Point getDepart() {
        return this.Depart;
    }

    public Point getArrivee() {
        return this.Arrivee;
    }

}
