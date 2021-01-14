import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCaserne {
    Connection connexion = null;
    Statement statement = null;

    public DatabaseCaserne () {
        /* Connexion à la base de données */
        String url = "jdbc:mariadb://127.0.0.1:3307/dbcaserne";
        String utilisateur = "root";
        String motDePasse = "";
        try {
            this.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            /* Création de l'objet gérant les requêtes */
            this.statement = this.connexion.createStatement();
            System.out.println("[CON] Sucess database connexion");
        }catch ( SQLException e ) {
            System.out.println("[ERREUR] BDD connexion : " + e.getMessage());
        }
    }

    public void CloseCo(){
        if ( this.connexion != null )
            try {
                /* Fermeture de la connexion */
                this.connexion.close();
            } catch ( SQLException ignore ) {
                /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
            }
    }

    public List<Camion> getListeCamions() {
        //On crée la liste qui va contenir tous les feux récupérés (qui sont donc identifiés par les capteurs)
        List<Camion> listCamion = new ArrayList<>();
        /* Exécution d'une requête de lecture */
        ResultSet resultat;
        ResultSet resultat2;
        String req = "";
        try {
            req = "SELECT * FROM Camion";
            resultat = this.statement.executeQuery( req );
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                //pour chaque objet retourné par la BDD on crée un objet feu qu'on remplit avec un objet coordonnées créé avant
                //on récupère les valeurs ligne par ligne avec leur nom de colonne (il faut que j'ajoute les valeurs de la table incendie je n'avais pas vu)
                Coordonees tempCoord = new Coordonees(resultat.getFloat( "x" ), resultat.getFloat( "y" ));
                req = "SELECT * FROM Intervention WHERE numCamion = " + resultat.getDouble( "id" );
                resultat2 = this.statement.executeQuery( req );
                Camion camtemp;
                if (resultat2.next()){
                    camtemp = new Camion(resultat.getInt( "id" ),resultat.getInt( "capacite" ) , tempCoord,  true );
                } else {
                    camtemp = new Camion(resultat.getInt( "id" ),resultat.getInt( "capacite" ) , tempCoord,  false );
                }

                System.out.println(camtemp);
                //on ajoute chaque feu récupéré à notre liste de feux
                listCamion.add(camtemp);
            }
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return(listCamion);
    }
}
