import java.util.List;

public class Intervention {
    private Coordonees coords;
    private List<Camion> camions;
    private Feu feu;

    public Intervention(Coordonees coords, List<Camion> camions, Feu feu) {
        this.coords = coords;
        this.camions = camions;
        this.feu = feu;
    }

    public Intervention(Coordonees coords,  Feu feu) {
        this.coords = coords;
        this.feu = feu;
    }

    public Intervention CreerIntervention(Coordonees coords, Feu feu)
    {
        Intervention nouvelleIntervention = new Intervention(coords,feu);

        return nouvelleIntervention;
    }


    public Coordonees getCoords() {
        return coords;
    }

    public void setCoords(Coordonees coords) {
        this.coords = coords;
    }

    public List<Camion> getCamions() {
        return camions;
    }

    public void setCamions(List<Camion> camions) {
        this.camions = camions;
    }

    public Feu getFeu() {
        return feu;
    }

    public void assignFeu(Feu feu) {
        this.feu = feu;
    }








}
