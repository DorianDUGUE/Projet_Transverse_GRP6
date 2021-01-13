public class Camion {
    private int numero;
    private int pouvoirDArret;
    private Coordonees coord;
    private Boolean enIntervention;

    public Camion(int idCam, int pouvoirDArret, Coordonees coord, boolean enIntervention) {
        this.numero = idCam;
        this.pouvoirDArret = pouvoirDArret;
        this.coord = coord;
        this.enIntervention = enIntervention;
    }

    public int getNumero() {
        return numero;
    }

    public int getPouvoirDArret() {
        return pouvoirDArret;
    }

    public void setPouvoirDArret(int pouvoirDArret) {
        this.pouvoirDArret = pouvoirDArret;
    }

    public Coordonees getCoord() {
        return coord;
    }

    public void deplacer(Coordonees coordonees) {
        this.coord = coordonees;
    }

}
