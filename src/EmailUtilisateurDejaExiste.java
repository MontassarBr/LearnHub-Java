// Exeption en cas de la redendance d'un email d'utulisateur
public class EmailUtilisateurDejaExiste extends Exception {
        public EmailUtilisateurDejaExiste(String message) {
            super(message);
        }
}
