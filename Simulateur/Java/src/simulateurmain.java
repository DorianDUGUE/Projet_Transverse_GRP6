import javax.xml.crypto.Data;
import java.util.Scanner;
public class simulateurmain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scId = new Scanner(System.in);
        Scanner scCodX = new Scanner(System.in);
        Scanner scCodY = new Scanner(System.in);
        Scanner PvAr = new Scanner(System.in);
        Scanner Fueg = new Scanner(System.in);

        Database DB = new Database();
        DatabaseSimu dbSimu = new DatabaseSimu();


        System.out.println("Bienvenue dans l'emergency manager");

        Runnable r = new Runnable() {
            public void run() {
                DatabaseCaserne DbCaserne = new DatabaseCaserne();
                DbCaserne.getListeCamions();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r).start();


        int choix, Id, PvdArr, Fuego;
        float CodX, CodY;
        System.out.println("1- Déplacer un camion");
        System.out.println("2- Modifier un feu");
        System.out.println("3- Quitter");
        System.out.print("Saisissez votre choix: ");
        choix= sc.nextInt();




        System.out.println("Vous avez choisi le: " + choix);
        switch (choix){
            case 1:
                System.out.print("Saisissez l'id du Camion: ");
                Id = scId.nextInt();
                System.out.print("Saisissez le pouvoir d'arret: ");
                PvdArr= PvAr.nextInt();
                System.out.print("Saisissez les coordonées X: ");
                CodX = scCodX.nextFloat();
                System.out.print("Saisissez les coordonées Y: ");
                CodY = scCodY.nextFloat();

                Coordonees TempcordCam =new Coordonees(CodX,CodY);
                Camion Camcam =new Camion(Id,PvdArr,TempcordCam,true);
                Camcam.deplacer(TempcordCam);

                DB.moveCamion(Camcam);
                break;
            case 2:
                System.out.print("Saisissez l'id du feu: ");
                Id = scId.nextInt();
                System.out.print("Saisissez l'intensité du feu: ");
                Fuego=Fueg.nextInt();
                System.out.print("Saississez les coordonées X: ");
                CodX=scCodX.nextFloat();
                System.out.print("Saississez les coordonées Y: ");
                CodY=scCodY.nextFloat();

                Coordonees Tempcord =new Coordonees(CodX,CodY);
                Feu Feufeu = new Feu(Id,Fuego,Tempcord);
                Feufeu.setIntensite(Fuego);

                dbSimu.updateCapteur(Feufeu);

                break;
            case 3:
                System.out.println("Merci d'avoir utilisé l'application");
                break;


        }
    }
}
