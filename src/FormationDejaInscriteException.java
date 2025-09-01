// Exeption Si un étudiant essaie de s'inscrire à une formation qu'il a déjà suivie
public class FormationDejaInscriteException extends Exception {
    public FormationDejaInscriteException(String message) {
        super(message);
    }
}