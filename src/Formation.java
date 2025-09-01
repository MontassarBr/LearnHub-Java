public class Formation {
    private static int nombre_formations = 0;// nombre de formations
    private String titre;// le titre de la formation
    private String description;// Une description détaillée de la formation
    private Formateur formateur;// Le formateur qui dispense la formation
    private double prix;// le prix de la formation
    private int id;// le id de la formation
    public Formation(String titre, String description, Formateur formateur, double prix) {
        this.titre = titre;
        this.description = description;
        this.formateur = formateur;
        this.prix = prix;
        this.id = ++nombre_formations;
    }

    // Getter de titre de la formation
    public String getTitre() {
        return titre;
    }
   // Setter de titre de la formation
    public void setTitre(String titre) {
        this.titre = titre;
    }
// Getter de la description de la formation
    public String getDescription() {
        return description;
    }
// Setter de la description de la formation
    public void setDescription(String description) {
        this.description = description;
    }
// Getter du formateur qui dispense la formation
    public Formateur getFormateur() {
        return formateur;
    }
// Setter du formateur qui dispense la formation
    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
// Getter du prix de la formation
    public double getPrix() {
        return prix;
    }
//  Setter du prix de la formation
    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Getter pour l'id de la formation
    public int getId() {
        return id;
    }
    // Setter pour l'id de la formation
    public void setId(int id) {
        this.id = id;
    }
// Getter du nombre des formations
    public int getNombre_formations() {
        return nombre_formations;
    }
// Setter du nombre des formations
    public void setNombre_utilisateurs(int nombre_formations){
        Formation.nombre_formations = nombre_formations;
    }

}
