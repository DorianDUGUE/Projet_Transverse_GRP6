public class Camion {
    private int id;
    private int pouvoirDArret;
    private Caserne caserne;
    private Coordonees coord;
    private Boolean enIntervention;


    public Camion(int id, int pouvoirDArret, Caserne caserne, Coordonees coord, boolean enIntervention) {
        this.id = id;
        this.pouvoirDArret = pouvoirDArret;
        this.caserne = caserne;
        this.coord = coord;
        this.enIntervention = enIntervention;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPouvoirDArret() {
        return pouvoirDArret;
    }

    public void setPouvoirDArret(int pouvoirDArret) {
        this.pouvoirDArret = pouvoirDArret;
    }

    public Caserne getCaserne() {
        return caserne;
    }

    public void setCaserne(Caserne caserne) {
        this.caserne = caserne;
    }

    public Coordonees getCoord() {
        return coord;
    }

    public void deplacer(Coordonees coord) {
        this.coord = coord;
    }

}
