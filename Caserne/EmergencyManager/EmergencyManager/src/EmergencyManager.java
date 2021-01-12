import java.lang.Math;

public class EmergencyManager {


    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'emergency manager");
        Database db = new Database();
        System.out.println(db.getListeFeux());
        db.CloseCo();
        while (true) {

        }

    }

    public double calculeDistance(Coordonees coordUn, Coordonees coordDeux) {
        return Math.sqrt(((coordUn.getX() - coordDeux.getX()) * (coordUn.getX() - coordDeux.getX())) + ((coordUn.getY() - coordDeux.getY()) * (coordUn.getY() - coordDeux.getY())));
    }
/*
    public boolean everyFeuHasIntervention() {

        boolean result = false;

        foreach(capteur in bdd)
        {
            if (intensite > 0) {
                foreach intervention
                {
                    while (!result) {
                        if (intervention.getFeu() == feu) {
                            result = true;
                        }
                    }

                }

            }
        }
    }
*/

}



