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
        /* Chargement du driver JDBC pour MySQL */
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch ( ClassNotFoundException e ) {
            System.out.println("Erreur lors du chargement du driver BDD : "+e.getMessage());
        }
        /* Connexion à la base de données */
        String url = "jdbc:mariadb://127.0.0.1:3307/DBCaserne";
        String utilisateur = "root";
        String motDePasse = "";
        try {
            this.connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            /* Création de l'objet gérant les requêtes */
            this.statement = this.connexion.createStatement();
        }catch ( SQLException e ) {
            System.out.println("erreur de connexion à la BDD");
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
            req = "SELECT * FROM Capteur WHERE valeur > 0";
            resultat = this.statement.executeQuery( req );
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                //pour chaque objet retourné par la BDD on crée un objet feu qu'on remplit avec un objet coordonnées créé avant
                //on récupère les valeurs ligne par ligne avec leur nom de colonne (il faut que j'ajoute les valeurs de la table incendie je n'avais pas vu)
                Coordonees tempCoord = new Coordonees( resultat.getFloat("x"), resultat.getFloat( "y" ));
                Feu feuTemp = new Feu(resultat.getInt( "id" ),resultat.getInt( "valeur" ),tempCoord);
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

    public List<Intervention> getListeIntervention() {
        //On crée la liste qui va contenir tous les feux récupérés (qui sont donc identifiés par les capteurs)
        List<Intervention> listIntervention = new ArrayList<>();
        /* Exécution d'une requête de lecture */
        ResultSet resultat ;
        String req = "";
        try {
            req = "SELECT * FROM Incendie";
            resultat = this.statement.executeQuery( req );
            /* Récupération des données du résultat de la requête de lecture */
            while ( resultat.next() ) {
                //pour chaque objet retourné par la BDD on crée un objet feu qu'on remplit avec un objet coordonnées créé avant
                //on récupère les valeurs ligne par ligne avec leur nom de colonne (il faut que j'ajoute les valeurs de la table incendie je n'avais pas vu)
                req = "SELECT * FROM Capteur WHERE id = " + resultat.getInt( "NumCapteur" );
                ResultSet resultat1 ;
                resultat1 = this.statement.executeQuery( req );
                if (resultat1.next()){
                    Coordonees tempCoord = new Coordonees(resultat1.getFloat( "x" ), resultat1.getFloat( "y" ));
                    Feu feuTemp = new Feu(resultat1.getInt( "id" ),resultat1.getInt( "valeur" ),tempCoord);
                    req = "SELECT * FROM Intervention WHERE id = " + resultat.getInt( "idIntervention" );
                    ResultSet resultat2 ;
                    resultat2 = this.statement.executeQuery( req );
                    if (resultat2.next()){
                        req = "SELECT * FROM Camion WHERE id = " + resultat2.getInt( "numCamion" );
                        ResultSet resultat3;
                        resultat3 = this.statement.executeQuery( req );
                        if (resultat3.next()){
                            Coordonees tempCoord1 = new Coordonees(resultat3.getFloat( "x" ),resultat3.getFloat( "y" ));
                            Camion camtemp = new Camion(resultat3.getInt( "id" ), resultat3.getInt( "capacite" ) , tempCoord1,  true );
                            Intervention camint = new Intervention(camtemp, feuTemp);
                            System.out.println(camint);
                            //on ajoute chaque feu récupéré à notre liste de feux
                            listIntervention.add(camint);
                        }
                    }
                }
            }
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return(listIntervention);
    }

    public Intervention createIntervention(String FireEtat, Feu feu, Camion camion) {
        /* Exécution d'une requête de lecture */
        String req = "";
        ResultSet res ;
        try {
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            req = "INSERT INTO Intervention (HeureDebut, numCamion) Values (" + format.format(date) + "," + camion.getNumero() +")";
            int resultat = this.statement.executeUpdate( req );
            System.out.println("[INSERT] nb mise à jour"+resultat);
            req = "SELECT * FROM Intervention WHERE HeureDebut = " + format.format(date);
            res = this.statement.executeQuery(req);
            if (res.next()){
                req = "INSERT INTO Incendie (Etat, idIntervention, NumCapteur) VALUES (" + FireEtat + "," + res.getInt( "id" ) + "," + feu.getId() + ")";
                resultat = this.statement.executeUpdate( req );
                System.out.println("[INSERT] nb mise à jour"+resultat);
            }
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return (new Intervention(camion,feu)) ;
    }

    void updateCapteur(Feu feu){
        /* Exécution d'une requête de lecture */
        String req = "";
        try {
            req = "UPDATE Capteur set valeur=" + feu.getIntensite() + "WHERE id=" + feu.getId() ;
            this.statement.executeUpdate(req);
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
    }

    Camion moveCamion(Camion camion){
        String req = "";
        try {
            req = "UPDATE Camion set x=" + camion.getCoord().getX() + ", y=" + camion.getCoord().getY() + "WHERE id=" + camion.getNumero() ;
            this.statement.executeUpdate(req);
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
        return camion;
    }

}
