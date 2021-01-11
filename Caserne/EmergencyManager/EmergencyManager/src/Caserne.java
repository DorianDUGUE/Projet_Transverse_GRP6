import java.util.List;

public class Caserne {
    private Coordonees coord;
    private Camion camion;

    public Caserne(Coordonees coord, Camion camion) {
        this.coord = coord;
        this.camion = camion;
    }

    public Coordonees getCoord() {
        return coord;
    }

    public void setCoord(Coordonees coord) {
        this.coord = coord;
    }

    public Camion getCamions() {
        return camion;
    }

    public void setCamions(List<Camion> camions) {
        this.camion = camion;
    }



}
