// Exeption Si un utilisateur essaie de se connecter avec des informations incorrectes
public class UtilisateurNonTrouveException extends Exception {
    public UtilisateurNonTrouveException(String message) {
        super(message);
    }
}