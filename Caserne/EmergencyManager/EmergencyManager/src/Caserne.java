import java.util.List;

public class Caserne {
    private Coordonees coord;
    private List<Camion> camions;

    public Caserne(Coordonees coord, List<Camion> camions) {
        this.coord = coord;
        this.camions = camions;
    }

    public Coordonees getCoord() {
        return coord;
    }

    public void setCoord(Coordonees coord) {
        this.coord = coord;
    }

    public List<Camion> getCamions() {
        return camions;
    }

    public void setCamions(List<Camion> camions) {
        this.camions = camions;
    }

    public void AddCamion(Camion camionToAdd)
    {
        this.camions.add(camionToAdd);
    }


}
