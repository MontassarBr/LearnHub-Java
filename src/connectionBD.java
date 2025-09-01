import java.sql.*;
public class connectionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/LearnHub";
    private static final String USER = "root"; // change this based on your setup
    private static final String PASSWORD = "mahdi115"; // change this based on your setup

    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // This ensures the driver is loaded
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found.");
            throw new SQLException("Failed to load MySQL driver", e);
        }
    }

    // Method to insert Formateur
    public static void insertFormateur(Formateur formateur) throws SQLException {
        String query = "INSERT INTO Utilisateurs (nom, email, motDePasse,typeUtilisateur) VALUES (?, ?, ?,'Formateur')";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, formateur.getNom());
            statement.setString(2, formateur.getEmail());
            statement.setString(3, formateur.getMotDePasse());
            statement.executeUpdate();
        }
    }

    // Method to insert Formation
    public static void insertFormation(Formation formation) throws SQLException {
        String query = "INSERT INTO Formations (titre, description, idformateur, prix) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, formation.getTitre());
            statement.setString(2, formation.getDescription());
            statement.setInt(3, formation.getFormateur().getId());
            statement.setDouble(4, formation.getPrix());
            statement.executeUpdate();
        }
    }

    // Method to insert Etudiant
    public static void insertEtudiant(Etudiant etudiant) throws SQLException {
        String query = "INSERT INTO Utilisateurs (nom, email, motDePasse,typeUtilisateur) VALUES (?, ?, ?,'Etudiant')";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getEmail());
            statement.setString(3, etudiant.getMotDePasse());
            statement.executeUpdate();
        }
    }

    // Method to insert Inscription
    public static void insertInscription(int etudiantId, int formationId) throws SQLException {
        String query = "INSERT INTO Inscriptions (idetudiant, idformation) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, etudiantId);
            statement.setInt(2, formationId);
            statement.executeUpdate();
        }
    }
}


