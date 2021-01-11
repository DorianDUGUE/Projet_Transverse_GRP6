public class Feu {
    private int idFeu;
    private int intensite;
    private Coordonees coord;
    private boolean brule;

    public Feu(int idFeu, int intensite, Coordonees coord) {
        this.idFeu = idFeu;
        this.intensite = intensite;
        this.coord = coord;
        this.brule= true;
    }

    public boolean isBrule() {
        return brule;
    }

    public void setBrule(boolean brule) {
        this.brule = brule;
    }

    public int getIntensite() {
        return intensite;
    }

    public void setIntensite(int intensite) {
        this.intensite = intensite;
    }

    public Coordonees getCoord() {
        return coord;
    }

    public void setCoord(Coordonees coord) {
        this.coord = coord;
    }
}
