import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSimu {
    Connection connexion = null;
    Statement statement = null;

    public DatabaseSimu () {
        /* Connexion à la base de données */
        String url = "jdbc:mariadb://127.0.0.1:3307/simulation";
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

    public List<Feu> getListeFeux() {
        //On crée la liste qui va contenir tous les feux récupérés (qui sont donc identifiés par les capteurs)
        List<Feu> listFeux = new ArrayList<>();
        /* Exécution d'une requête de lecture */
        ResultSet resultat;
        String req = "";
        try {
            req = "SELECT * FROM capteur";
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

    void updateCapteur(Feu feu){
        /* Exécution d'une requête de lecture */
        String req = "";
        ResultSet res ;
        try {
            req = "SELECT * FROM capteur WHERE id = " + feu.getId();
            res = this.statement.executeQuery(req);
            if (res.next()){
                req = "UPDATE capteur set valeur=" + feu.getIntensite() + " WHERE id=" + feu.getId() ;
            }else{
                req = "INSERT INTO capteur (id,x,y,valeur) VALUES (" +  feu.getId() + "," + feu.getCoord().getX() + "," + feu.getCoord().getY() + "," + feu.getIntensite() + ")";
            }
            this.statement.executeUpdate(req);
            System.out.println("[CAPT] "+ req);
        }catch (SQLException e){
            System.out.println("[ERREUR] " + req );
            System.out.println(e.getMessage());
        }
    }

}
