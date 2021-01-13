public class Feu {
    private final int id;
    private int intensite;
    private Coordonees coord;
    private boolean brule;

    public Feu(int idCapt, int intensite, Coordonees coord) {
        this.id = idCapt;
        this.intensite = intensite;
        this.coord = coord;
    }

    public boolean isBrule() {
        return brule;
    }

    public void setBrule(boolean brule) {
        this.brule = brule;
    }

    public int getId() {
        return id;
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
