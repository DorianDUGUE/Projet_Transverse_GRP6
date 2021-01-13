import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {
    Connection connexion = null;
    Statement statement = null;
    public Database () {
        /* Connexion à la base de données */
        String url = "jdbc:mariadb://127.0.0.1:3306/DBCaserne";
        String utilisateur = "simu";
        String motDePasse = "simu";
        try {
            this.connexion = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/DBCaserne?user=simu&password=simu");
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

    public List<Feu> getListeFeux() {
        //On crée la liste qui va contenir tous les feux récupérés (qui sont donc identifiés par les capteurs)
        List<Feu> listFeux = new ArrayList<>();
        /* Exécution d'une requête de lecture */
        ResultSet resultat;
        String req = "";
        try {
            req = "SELECT * FROM Capteur";
            resultat = this.statement.executeQuery( req );
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                //pour chaque objet retourné par la BDD on crée un objet feu qu'on remplit avec un objet coordonnées créé avant
                //on récupère les valeurs ligne par ligne avec leur nom de colonne (il faut que j'ajoute les valeurs de la table incendie je n'avais pas vu)
                Coordonees tempCoord = new Coordonees( resultat.getFloat("x"), resultat.getFloat( "y" ));
                Feu feuTemp = new Feu(resultat.getInt( "id" ),resultat.getInt( "valeur" ),tempCoord);
                System.out.println(feuTemp);
                //on ajoute chaque feu récupéré à notre liste de feux
                listFeux.add(feuTemp);
            }
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return(listFeux);
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

    void updateCapteur(Feu feu){
        /* Exécution d'une requête de lecture */
        String req = "";
        try {
            req = "UPDATE Capteur set valeur=" + feu.getIntensite() + " WHERE id=" + feu.getId() ;
            this.statement.executeUpdate(req);
            System.out.println("[UPDT] "+ req);
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
    }

    Camion moveCamion(Camion camion){
        String req = "";
        ResultSet res ;
        try {
            req = "SELECT * FROM Camion WHERE id = " + camion.getNumero();
            res = this.statement.executeQuery(req);
            if (res.next()){
                req = "UPDATE Camion set x=" + camion.getCoord().getX() + ", y=" + camion.getCoord().getY() + " WHERE id=" + camion.getNumero() ;
            }else{
                req = "INSERT INTO Camion (id,x,y,capacite) VALUES (" +  camion.getNumero() + "," + camion.getCoord().getX() + "," + camion.getCoord().getY() + "," + camion.getPouvoirDArret() + ")";
            }
            this.statement.executeUpdate(req);
            System.out.println("[CAM] "+ req);
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return camion;
    }

}
