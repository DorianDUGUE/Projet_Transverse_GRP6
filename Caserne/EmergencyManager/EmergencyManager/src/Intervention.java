public class Intervention {
    private int interventionBDDID;
    private CoordonneeRF capteurAssocie;
    private Date dateDebut;
    private Date dateFin;
    private ArrayList<Caserne> casernesAffectees;
    private ArrayList<Vehicule> vehiculesAffectes;

    public Intervention(CoordonneeRF capteurAssocie, Date dateDebut, Date dateFin) {
        this.capteurAssocie = capteurAssocie;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.casernesAffectees = new ArrayList<Caserne>();
        this.vehiculesAffectes = new ArrayList<Vehicule>();

    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public void setCasernesAffectees(ArrayList<Caserne> casernesAffectees) {
        this.casernesAffectees = casernesAffectees;
    }

    public void setVehiculesAffectes(ArrayList<Vehicule> vehiculesAffectes) {
        this.vehiculesAffectes = vehiculesAffectes;
    }

    public CoordonneeRF getCapteurAssocie() {
        return capteurAssocie;
    }

    public ArrayList<Caserne> getCasernesAffectees() {
        return casernesAffectees;
    }

    public ArrayList<Vehicule> getVehiculesAffectes() {
        return vehiculesAffectes;
    }

    public void addCaserneAffectee(Caserne caserne){
        this.casernesAffectees.add(caserne);
    }

    public void addVehiculeAffecte(Vehicule vehicule){
        this.vehiculesAffectes.add(vehicule);
    }

    public void addCaserneAffectee(ArrayList<Caserne> casernes){
        this.casernesAffectees.addAll(casernes);
    }

    public void addVehiculeAffecte(ArrayList<Vehicule> vehicules){
        this.vehiculesAffectes.addAll(vehicules);
    }

    public void removeCaserneAffectee(Caserne caserne){
        this.casernesAffectees.remove(caserne);
    }

    public void removeVehiculeAffecte(Vehicule vehicule){
        this.vehiculesAffectes.remove(vehicule);
    }

    public void removeCaserneAffectee(ArrayList<Caserne> casernes){
        this.casernesAffectees.removeAll(casernes);
    }

    public void removeVehiculeAffecte(ArrayList<Vehicule> vehicules){
        this.vehiculesAffectes.removeAll(vehicules);
    }

    public int getInterventionBDDID() {
        return interventionBDDID;
    }

    public void setInterventionBDDID(int interventionBDDID) {
        this.interventionBDDID = interventionBDDID;
    }
}
