import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public List<Feu> getListeFeux() {
        /* Chargement du driver JDBC pour MySQL */
        try {
            Class.forName( "org.mariadb.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            System.out.println("Erreur lors du chargement du driver BDD"+e.getMessage());
        }
        /* Connexion à la base de données */
        String url = "jdbc:mariadb://localhost:3307/dbcaserne";
        String utilisateur = "root";
        String motDePasse = "";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();
            /* Exécution d'une requête de lecture */
            resultat = statement.executeQuery( "SELECT * FROM capteur" );
            //On crée la liste qui va contenir tous les feux récupérés (qui sont donc identifiés par les capteurs)
            List<Feu> listFeux = new ArrayList<Feu>();
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                //pour chaque objet retourné par la BDD on crée un objet feu qu'on remplit avec un objet coordonnées créé avant
                //on récupère les valeurs ligne par ligne avec leur nom de colonne (il faut que j'ajoute les valeurs de la table incendie je n'avais pas vu)
                Coordonees tempCoord = new Coordonees(resultat.getDouble( "x" ),resultat.getDouble( "y" ));
                Feu feuTemp = new Feu(resultat.getInt( "id" ),resultat.getInt( "valeur" ),tempCoord);
                System.out.println(feuTemp);
                //on ajoute chaque feu récupéré à notre liste de feux
                listFeux.add(feuTemp);
            }
            return(listFeux);

        } catch ( SQLException e ) {
            System.out.println("erreur de connexion à la BDD");
        } finally {
            if ( connexion != null )
                try {
                    /* Fermeture de la connexion */
                    connexion.close();
                } catch ( SQLException ignore ) {
                    /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
                }
        }
        return null;
    }
    /*
    requêtes à faire :

    aide pour les faire: (notamment les update et insert into étant donné que j'en ai pas encore fait)
    https://openclassrooms.com/fr/courses/626954-creez-votre-application-web-avec-java-ee/624392-communiquez-avec-votre-bdd

    list<Camion> getListeCamions()
    Récupère tous les camions en base et les renvoie sous forme de liste
    return new Arraylist<Camion>



    list<intervention> getListeIntervention()
    Récupère toutes les interventions en base et les renvoie sous forme de liste
    return new Arraylist<Camion>



    Intervention createIntervention(Feu feu,Camion camion)
    Crée une intervention avec le camion passé en paramètre sur le feu passé en paramètre (pour ses coordonnées utiliser feu.getCoordonnees.getx() ou gety();
    return new Intervention(id, HeureDebut, numCamion)



    void updateCapteur(Feu feu)
    insérer en base avec l'Id du capteur et la nouvelle valeur de l'intensité: Feu.getIntensite()


    Camion moveCamion(Camion camion)
    modifie les X et Y du camion en question et select le même camion pour récupérer les nouvelles valeurs

    return new Camion



    */

}
