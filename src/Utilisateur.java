public class Utilisateur {
    private static int nombre_utulisateurs=0;
    private String nom;// Le nom de l'utilisateur
    private String email;// L'email de l'utilisateur, unique
    private String motDePasse;// Le mot de passe de l'utilisateur
    private int id; // L'id unique de l'utilisateur 
// Un constructeur permettant d'initialiser un utilisateur avec ses informations
    public Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.id=++nombre_utulisateurs;
    }

    // Getter pour le nom de l'utilisateur
    public String getNom() {
        return nom;
    }
// Setter pour le nom de l'utilisateur
    public void setNom(String nom) {
        this.nom = nom;
    }
// Getter pour l'email de l'utilisateur
    public String getEmail() {
        return email;
    }
// Setter pour l'email de l'utilisateur
    public void setEmail(String email) {
        this.email = email;
    }
//  Getter pour le mot de passe de l'utilisateur
    public String getMotDePasse() {
        return motDePasse;
    }
//  Setter pour le mot de passe de l'utilisateur
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    // Getter pour l'id unique de l'utilisateur
    public int getId() {
        return id;
    }
    // setter de l'id de l'utulisateur
    public void setId(int id) {
        this.id = id;
    }
    // getter de nombre des utulisateur
    public int getNombre_utulisateurs() {
        return nombre_utulisateurs;
    }
    // setter de nombre des utulisateur
    public void setNombre_utilisateurs(int nombre_utulisateurs){
        Utilisateur.nombre_utulisateurs = nombre_utulisateurs;
    }
}
