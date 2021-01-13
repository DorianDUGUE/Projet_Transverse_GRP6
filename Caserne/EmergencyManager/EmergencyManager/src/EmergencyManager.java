import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmergencyManager {


    public static void main(String[] args) {
        EmergencyManager main = new EmergencyManager();
        System.out.println("Bienvenue dans l'emergency manager");
        Database db = new Database();

            main.createInterventionForEachFeu(db);



        db.CloseCo();

    }

    public double calculeDistance(Coordonees coordUn, Coordonees coordDeux) {
        return Math.sqrt(((coordUn.getX() - coordDeux.getX()) * (coordUn.getX() - coordDeux.getX())) + ((coordUn.getY() - coordDeux.getY()) * (coordUn.getY() - coordDeux.getY())));
    }

    public void updateInterventions(Database db){
        List<Intervention> interList = db.getListeIntervention();
        for (uneIntervention:interList) {
            uneIntervention.
        }
    }





    public void createInterventionForEachFeu(Database db) {

        boolean result = false;
        List<Feu> feuList = db.getListeFeux();
        List<Feu> feuWithIntervention = new ArrayList<Feu>();
        List<Intervention> interList = db.getListeIntervention();
        Coordonees tempCoord = new Coordonees(0, 0);
        List<Camion> camionList = db.getListeCamions();
        Camion bestCamion = new Camion(-1, 0, tempCoord, false);
        double distance = 999999999;
        boolean needToBreak = false;
        boolean breakOutOfIntervention = false;

        for (Feu unFeu : feuList) {
            for (Intervention uneIntervention : interList) {
                if (uneIntervention.getFeu().getId() != unFeu.getId() && !feuWithIntervention.contains(unFeu) ) {
                    for (Camion unCamion : camionList) {
                        if (!unCamion.getEnIntervention()) {
                            double tempDistance = calculeDistance(unFeu.getCoord(), unCamion.getCoord());
                            if (tempDistance < distance) {
                                distance = tempDistance;
                                bestCamion = unCamion;
                                System.out.println(bestCamion);
                            }
                            System.out.println(unCamion);
                        }
                    }
                    if (distance == 999999999)
                    {
                        System.out.println("Aucun Camion n'est actuellement disponible, ils sont tous en intervention :/ ");
                        needToBreak = true;
                        break;
                    }
                    bestCamion.setEnIntervention(true);
                    db.createIntervention("En feu !", unFeu, bestCamion);
                }
                else{
                    feuWithIntervention.add(unFeu);
                    breakOutOfIntervention = true;
                }
                feuWithIntervention.add(unFeu);
                System.out.println(unFeu);
                if(breakOutOfIntervention) break;
            }
            if(needToBreak) break;
        }

    }
}



