public class Etudiant extends Utilisateur {
    private Formation inscriptions[];//la liste des formations auxquelles l'étudiant est inscrit
    private int nombreDeInscriptions = 0;//le nobmre des formations auxquelles l'étudiant est inscri
    private static int nombreMaxDeInscriptions = 2;//nombre maximale des inscriptions effectuees par un seul etudiant
// premier constructeur d'etudiant permettant d'initialiser un etudiant avec ses informations(nom,email,motDePasse)
    public Etudiant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
        this.inscriptions= new Formation[nombreMaxDeInscriptions];
    }
// deuxiéme constructeur d'etudiant permettant d'initialiser un etudiant avec ses informations(nom,email,motDePasse,liste des inscriptions)
    public Etudiant(String nom, String email, String motDePasse,Formation inscriptions []) {
        super(nom, email, motDePasse);
        this.inscriptions= inscriptions;
    }
//  Méthode pour verifier l'existance d'une formation dans la liste des formations auxquelles l'étudiant est inscrit
    public boolean inscriptinExisteDeja(Formation formation) {
        for (int i = 0; i < nombreMaxDeInscriptions; i++) {
            if (inscriptions[i] == formation) {
                return true;
            }
        }
        return false;
    }

//  Méthode pour s'inscrire à une formation
    public void sinscrireFormation(Formation formation) throws FormationDejaInscriteException {
        if (inscriptinExisteDeja(formation)) {
            throw new FormationDejaInscriteException(
                    "L'etudiant est deja inscrit a cette formation : " + formation.getTitre());
        }
        if (formation != null && nombreDeInscriptions<nombreMaxDeInscriptions) {
            inscriptions[nombreDeInscriptions] = formation;
            System.out.println("Inscription reussie a la formation : " + formation.getTitre());
            nombreDeInscriptions++;
        } 
        else if(nombreDeInscriptions>=nombreMaxDeInscriptions){
            System.out.println("nomre maximale d'inscriptions formations atteint !");
        }
        else{
            System.out.println("Formation invalide !");
        }
    }

    // Getter pour la liste des formations auxquelles l'étudiant est inscrit
    public Formation[] getInscriptions() {
        return inscriptions;
    }

    // Setter pour la liste des formations auxquelles l'étudiant est inscrit
    public void setInscriptions(Formation inscriptions[]) {
        this.inscriptions = inscriptions;
    }

    // Getter pour le nobmre des formations auxquelles l'étudiant est inscrit
    public int getnombreDeInscriptions() {
        return nombreDeInscriptions;
    }

    // Setter pour le nobmre des formations auxquelles l'étudiant est inscri
    public void setnombreDeInscriptions(int nombreDeInscriptions) {
        this.nombreDeInscriptions = nombreDeInscriptions;
    }

    // Getter pour le nobmre des maximale inscriptions    
    public int getnombreMaxDeInscriptions() {
        return nombreMaxDeInscriptions;
    }

    // Setter pour le nobmre maximale des inscriptions
    public static void setnombreMaxDeInscriptions(int nombreMaxDeInscriptions) {
        Etudiant.nombreMaxDeInscriptions = nombreMaxDeInscriptions;
    }
}
