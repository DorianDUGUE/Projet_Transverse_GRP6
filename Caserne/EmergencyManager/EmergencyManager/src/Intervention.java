public class Intervention {
    private Camion camions;
    private Feu feu;

    public Intervention(Camion camions, Feu feu) {
        this.camions = camions;
        this.feu = feu;
    }

    public Intervention(Feu feu) {
        this.feu = feu;
    }

    public Intervention CreerIntervention(Camion camions, Feu feu) {
        return new Intervention(camions,feu);
    }


    public Camion getCamions() {
        return camions;
    }

    public void setCamions(Camion camions) {
        this.camions = camions;
    }

    public Feu getFeu() {
        return feu;
    }

    public void assignFeu(Feu feu) {
        this.feu = feu;
    }








}
