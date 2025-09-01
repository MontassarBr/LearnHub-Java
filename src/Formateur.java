public class Formateur extends Utilisateur {
    private Formation formations[];// la liste des formations que ce formatur propose
    private int nombreDeFormations = 0;// le nombre des formations que ce formatur propose
    private static int nombreMaxDeFormations = 5;// le nombre maximale des formations proposees par un seule formateur

    // constructeur de formateur permettant d'initialiser un utilisateur avec ses informations
    public Formateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
        this.formations = new Formation[nombreMaxDeFormations];
    }

    // Méthode pour ajouter une formation
    public void ajouterFormation(Formation formation) {
        if (formation != null && nombreDeFormations < nombreMaxDeFormations) {
            this.formations[nombreDeFormations] = formation;
            System.out.println("Formation ajoute : " + formation.getTitre());
            nombreDeFormations++;
        } else if (nombreDeFormations >= nombreMaxDeFormations) {
            System.out.println("nomre maximale de formations atteint !");
        } else {
            System.out.println("Formation invalide !");
        }
    }

    // Getter pour la liste des formations que ce formatur propose
    public Formation[] getFormations() {
        return formations;
    }

    // Setter pour la liste des formations que ce formatur propose
    public void setFormations(Formation formations[]) {
        this.formations = formations;
    }

    // Getter pour le nobmre actuelle des formations
    public int getnombreDeFormations() {
        return nombreDeFormations;
    }

    // Setter pour le nobmre actuelle des formations
    public void setnombreDeFormations(int nombreDeFormations) {
        this.nombreDeFormations = nombreDeFormations;
    }

    // Getter pour le nobmre maximale des formations possibles
    public int getnombreMaxDeFormations() {
        return nombreMaxDeFormations;
    }

    // Setter pour le nobmre maximale des formations possible
    public static void setnombreMaxDeFormations(int nombreMaxDeFormations) {
        Formateur.nombreMaxDeFormations = nombreMaxDeFormations;
    }

}
