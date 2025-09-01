import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;
public class App {

    public static void main(String[] args) {
        playSound("src/pen_voice.wav"); // Replace with your sound file path
        showSplashScreen();

        SwingUtilities.invokeLater(() -> createLoginPage());
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();
        splash.setSize(500, 300);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splash.setLocation((screenSize.width - splash.getSize().width) / 2,
                (screenSize.height - splash.getSize().height) / 2);

        JPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());

        ImageIcon logoIcon = new ImageIcon("src/logo.png"); // Adjust path as needed
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);

        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
        JLabel welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);

        panel.add(logoLabel, BorderLayout.NORTH);
        panel.add(welcomeLabel, BorderLayout.CENTER);
        splash.add(panel);

        splash.setVisible(true);

        String phrase = "Welcome to LearnHub!  ";
        int totalDuration = 6000;
        int delay = totalDuration / phrase.length();

        Timer timer = new Timer(delay, null);
        final int[] index = {0};

        timer.addActionListener(e -> {
            if (index[0] < phrase.length()) {
                welcomeLabel.setText(welcomeLabel.getText() + phrase.charAt(index[0]));
                index[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();

        try {
            Thread.sleep(totalDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.dispose();
    }

    private static void createLoginPage() {
        ImageIcon icon = new ImageIcon("src/logo.png");
        JFrame loginFrame = new JFrame("Login - LearnHub");
        loginFrame.setIconImage(icon.getImage());
        loginFrame.getContentPane().setBackground(new Color(0x123456));
        loginFrame.setSize(400, 300); // Reduced size for tighter spacing
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
    
        JLabel loginLabel = new JLabel("Login", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Serif", Font.BOLD, 32));
        loginLabel.setForeground(Color.WHITE);
    
        JPanel loginPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Further reduced spacing
        loginPanel.setBackground(Color.BLACK);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tightened padding
    
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        JTextField emailField = createRoundedTextField("Enter your email");
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = createRoundedPasswordField("Enter your password");
    
        JButton loginButton = createRoundedButton("Login");
        JButton createAccountButton = createRoundedButton("Create Account");
    
        loginButton.addActionListener(e -> handleLogin(emailField, passwordField, loginFrame));
        createAccountButton.addActionListener(e -> {
            loginFrame.dispose();
            createRegistrationPage();
        });
    
        emailLabel.setLabelFor(emailField);
        passwordLabel.setLabelFor(passwordField);
    
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);
        loginPanel.add(new JLabel());
        loginPanel.add(createAccountButton);
    
        JLabel copyrightLabel = new JLabel("© 2024 LearnHub By MontaBr  ", SwingConstants.RIGHT);
        copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        copyrightLabel.setForeground(Color.LIGHT_GRAY);
    
        mainPanel.add(loginLabel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        mainPanel.add(copyrightLabel, BorderLayout.SOUTH);
    
        loginFrame.add(mainPanel);
    
        // Remove initial focus on the input fields (applies to all pages)
        removeInitialFocus(loginFrame);
    
        loginFrame.setVisible(true);
    }
    private static void handleLogin(JTextField emailField, JPasswordField passwordField, JFrame loginFrame) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
    
        // SQL query to verify user credentials
        String query = "SELECT nom, typeUtilisateur FROM utilisateurs WHERE email = ? AND motDePasse = ?";
    
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Set query parameters
            statement.setString(1, email);
            statement.setString(2, password);
    
            // Execute query
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                // User exists, retrieve user details
                String userName = resultSet.getString("nom");
                String userType = resultSet.getString("typeUtilisateur");
    
                // Close the login frame and open the home page
                loginFrame.dispose();
                createHomePage(userName, userType,email);
            } else {
                // Invalid credentials
                JOptionPane.showMessageDialog(null, "Invalid credentials, please try again.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }
    
    private static String getWelcomeText() {
        return "LearnHub is an innovative platform that connects students and instructors.\n" +
               "Our mission is to provide high-quality educational content and help students\n" +
               "reach their learning goals. Whether you are looking to enhance your skills or\n" +
               "start a new career path, LearnHub has a variety of courses tailored to your needs.\n" +
               "Explore our offerings and start learning today!";
    }
    
    private static void createHomePage(String userName, String userType,String userEmailAddress) {
        JFrame homeFrame = new JFrame("Home - LearnHub");
        homeFrame.setSize(800, 500);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
    
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
    
        // Sidebar panel on the left (fixed)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 5, 10)); // Vertical layout for options
        sidebar.setBackground(Color.DARK_GRAY);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Sidebar buttons
        JButton homeButton = createRoundedButton("Home");
        JButton browseFormationsButton = createRoundedButton("Browse Formations");
        JButton addFormationButton = createRoundedButton("Add Formation");
        JButton inscriptionsButton = createRoundedButton("Add Inscription");
        JButton myAccountButton = createRoundedButton("My Account");
        JButton logoutButton = createRoundedButton("Logout");
    
        // Sidebar button actions
        homeButton.addActionListener(e -> showHomeContent(mainPanel));
        browseFormationsButton.addActionListener(e -> openBrowseFormationsPage());
        addFormationButton.addActionListener(e -> openAddFormationPage());
        inscriptionsButton.addActionListener(e -> openInscriptionsPage());
        myAccountButton.addActionListener(e -> showAccountDetails(mainPanel, userName, userType,userEmailAddress));
        logoutButton.addActionListener(e -> {
            homeFrame.dispose();
            createLoginPage();
        });
    
        // Add general buttons to sidebar
        sidebar.add(new JLabel("MENU", SwingConstants.CENTER)); // Sidebar Title
        sidebar.add(homeButton);
    
        // Conditional options based on user type
        if (userType.equalsIgnoreCase("admin") && userName.equalsIgnoreCase("montassar")) {
            sidebar.add(addFormationButton);
            JButton viewUsersButton = new JButton("Manage Users");
            viewUsersButton.setFont(new Font("Serif", Font.PLAIN, 16));
            viewUsersButton.setBackground(Color.BLACK);
            viewUsersButton.setForeground(Color.WHITE);
            viewUsersButton.addActionListener(e -> openUserManagementPage());
            sidebar.add(viewUsersButton);

        }
        else if (userType.equalsIgnoreCase("Etudiant")) {
            sidebar.add(browseFormationsButton);
            sidebar.add(inscriptionsButton);
        } 
    
        sidebar.add(myAccountButton);
        sidebar.add(logoutButton);
    
        // Content area (center of the page)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
    
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to LearnHub!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
    
        // Description paragraph
        JTextArea descriptionArea = new JTextArea(getWelcomeText());
        descriptionArea.setFont(new Font("Serif", Font.PLAIN, 21));
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setBackground(Color.BLACK);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setCaretPosition(0);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Add components to the content panel
        contentPanel.add(welcomeLabel, BorderLayout.NORTH);
        contentPanel.add(descriptionArea, BorderLayout.CENTER);
    
        // Add sidebar and content panel to the main panel
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
    
        homeFrame.add(mainPanel);
        homeFrame.setVisible(true);
    }

    private static void openUserManagementPage() {
        JFrame userManagementFrame = new JFrame("Manage Users - LearnHub");
        userManagementFrame.setSize(600, 400);
        userManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userManagementFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
    
        JLabel titleLabel = new JLabel("List of Users", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
    
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBackground(Color.BLACK);
        
        // Fetch users from database
        String query = "SELECT id, nom, email, motdepasse, typeutilisateur FROM Utilisateurs";
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("motdepasse");
                String userType = resultSet.getString("typeutilisateur");
    
                // Create panel for each user
                JPanel userPanel = new JPanel();
                userPanel.setLayout(new GridLayout(1, 5));
                userPanel.setBackground(Color.DARK_GRAY);
    
                JLabel nameLabel = new JLabel(name);
                JLabel idLabel = new JLabel(String.valueOf(id));
                JLabel emailLabel = new JLabel(email);
                JLabel passwordLabel = new JLabel(password);
                JLabel typeLabel = new JLabel(userType);
    
                JButton deleteButton = new JButton("Delete");
                deleteButton.setFont(new Font("Serif", Font.PLAIN, 18));
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setBackground(Color.RED);
                deleteButton.addActionListener(e -> {
                    try {
                        deleteUserFromDatabase(id);
                        JOptionPane.showMessageDialog(userManagementFrame, "User deleted successfully.");
                        userManagementFrame.dispose();  // Close the window after deletion
                        openUserManagementPage();  // Reopen the user management page
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(userManagementFrame, "Error deleting user.");
                    }
                });
    
                userPanel.add(idLabel);
                userPanel.add(nameLabel);
                userPanel.add(emailLabel);
                userPanel.add(passwordLabel);
                userPanel.add(typeLabel);
                userPanel.add(deleteButton);
    
                usersPanel.add(userPanel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(userManagementFrame, "Error fetching users.");
        }
    
        JScrollPane scrollPane = new JScrollPane(usersPanel);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        userManagementFrame.add(mainPanel);
        userManagementFrame.setVisible(true);
    }
    
    private static void deleteUserFromDatabase(int userId) throws SQLException {
        String deleteQuery = "DELETE FROM Utilisateurs WHERE id = ?";
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }
    
    private static void openBrowseFormationsPage() {
        JFrame browseFrame = new JFrame("Available Formations - LearnHub");
        browseFrame.setSize(600, 400);
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
    
        JLabel titleLabel = new JLabel("Available Formations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
    
        JTextArea formationList = new JTextArea();
        formationList.setEditable(false);
        formationList.setBackground(Color.DARK_GRAY);
        formationList.setForeground(Color.WHITE);
        formationList.setFont(new Font("Serif", Font.PLAIN, 18));
    
        // Fetch formations from database
        StringBuilder formations = new StringBuilder();
        String query = "SELECT titre, description, id FROM Formations"; // Include id in the query
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id"); // Get the formation ID
                String title = resultSet.getString("titre");
                String description = resultSet.getString("description");
                formations.append("ID: ").append(id).append("\n") // Append the ID
                          .append("Title: ").append(title).append("\n")
                          .append("Description: ").append(description).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            formations.append("Error fetching formations.");
        }
    
        formationList.setText(formations.toString());
    
        JScrollPane scrollPane = new JScrollPane(formationList);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        browseFrame.add(mainPanel);
        browseFrame.setVisible(true);
    }
    
    private static void openInscriptionsPage() {
        JFrame inscriptionsFrame = new JFrame("Add Inscription - LearnHub");
        inscriptionsFrame.setSize(400, 300);
        inscriptionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inscriptionsFrame.setLocationRelativeTo(null);
    
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel formationLabel = new JLabel("Formation ID:");
        formationLabel.setForeground(Color.WHITE);
        JTextField formationField = createRoundedTextField("Enter Formation ID");
    
        JButton submitButton = createRoundedButton("Submit");
        submitButton.addActionListener(e -> {
            // Example logic for inserting an inscription
            int formationId = Integer.parseInt(formationField.getText());
            int etudiantId = 1; // Replace with the actual logged-in student's ID
            try {
                connectionBD.insertInscription(etudiantId, formationId);
                JOptionPane.showMessageDialog(null, "Inscription successful!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
    
        panel.add(formationLabel);
        panel.add(formationField);
        panel.add(new JLabel());
        panel.add(submitButton);
    
        inscriptionsFrame.add(panel);
        inscriptionsFrame.setVisible(true);
    }
    
    // Show "Home" Content
    private static void showHomeContent(JPanel mainPanel) {
        // Only update the center (content) area without removing the sidebar
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
        JLabel welcomeLabel = new JLabel("Welcome to LearnHub!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
    
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
    
        // Get the current layout of the main panel
        mainPanel.remove(1); // Remove the old content panel (center area)
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // Show Account Details based on User Type
    private static void showAccountDetails(JPanel mainPanel, String userName, String userType, String userEmailAddress) {
        // Only update the center (content) area without removing the sidebar
        JPanel accountPanel = new JPanel(new BorderLayout());
        accountPanel.setBackground(Color.BLACK);
    
        JLabel titleLabel = new JLabel("My Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
    
        JPanel infoPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        infoPanel.setBackground(Color.BLACK);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setForeground(Color.WHITE);
    
        JLabel typeLabel = new JLabel("Type: " + userType);
        typeLabel.setForeground(Color.WHITE);
    
        JLabel emailLabel = new JLabel("Email: " + userEmailAddress);
        emailLabel.setForeground(Color.WHITE);
    
        infoPanel.add(nameLabel);
        infoPanel.add(emailLabel);
        infoPanel.add(typeLabel);
    
        if (userType.equalsIgnoreCase("Formateur")) {
            // If user is Formateur, show the formations they are linked to
            JLabel formationLabel = new JLabel("Formations you are teaching: ");
            formationLabel.setForeground(Color.WHITE);
            infoPanel.add(formationLabel);
    
            // Query for Formateur's formations
            String query = "SELECT f.titre FROM Formations f INNER JOIN Utilisateurs u ON f.idformateur = u.id WHERE u.nom = ?";
            try (Connection connection = connectionBD.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userName);
                ResultSet resultSet = statement.executeQuery();
    
                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    String formationTitle = resultSet.getString("titre");
                    JLabel formationItem = new JLabel("- " + formationTitle);
                    formationItem.setForeground(Color.LIGHT_GRAY);
                    infoPanel.add(formationItem);
                }
    
                if (!hasResults) {
                    JLabel noFormationsLabel = new JLabel("No formations found.");
                    noFormationsLabel.setForeground(Color.RED);
                    infoPanel.add(noFormationsLabel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JLabel errorLabel = new JLabel("Error fetching formations.");
                errorLabel.setForeground(Color.RED);
                infoPanel.add(errorLabel);
            }
    
        } else if (userType.equalsIgnoreCase("Etudiant")) {
            // If user is Etudiant, show the formations they are enrolled in
            JLabel inscriptionLabel = new JLabel("Formations you are enrolled in: ");
            inscriptionLabel.setForeground(Color.WHITE);
            infoPanel.add(inscriptionLabel);
    
            // Query for Etudiant's enrolled formations
            String query = "SELECT f.titre FROM Formations f INNER JOIN Inscriptions i ON f.id = i.idformation " +
                           "INNER JOIN Utilisateurs u ON i.idetudiant = u.id WHERE u.email = ?";
            try (Connection connection = connectionBD.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userEmailAddress);
                ResultSet resultSet = statement.executeQuery();
    
                boolean hasResults = false;
                while (resultSet.next()) {
                    hasResults = true;
                    String formationTitle = resultSet.getString("titre");
                    JLabel formationItem = new JLabel("- " + formationTitle);
                    formationItem.setForeground(Color.LIGHT_GRAY);
                    infoPanel.add(formationItem);
                }
    
                if (!hasResults) {
                    JLabel noFormationsLabel = new JLabel("Not enrolled in any formations.");
                    noFormationsLabel.setForeground(Color.RED);
                    infoPanel.add(noFormationsLabel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JLabel errorLabel = new JLabel("Error fetching your inscriptions.");
                errorLabel.setForeground(Color.RED);
                infoPanel.add(errorLabel);
            }
        }
    
        accountPanel.add(titleLabel, BorderLayout.NORTH);
        accountPanel.add(infoPanel, BorderLayout.CENTER);
    
        // Get the current layout of the main panel
        mainPanel.remove(1); // Remove the old content panel (center area)
        mainPanel.add(accountPanel, BorderLayout.CENTER);
    
        mainPanel.revalidate();
        mainPanel.repaint();
    }
        
    private static void openAddFormationPage() {
        JFrame addFormationFrame = new JFrame("Add Formation - LearnHub");
        addFormationFrame.setSize(400, 300);
        addFormationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFormationFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Updated grid layout to accommodate the new fields
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel nameLabel = new JLabel("Formation Name:");
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = createRoundedTextField("Enter formation name");
    
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.WHITE);
        JTextField descriptionField = createRoundedTextField("Enter description");
    
        JLabel formateurLabel = new JLabel("Formateur:");
        formateurLabel.setForeground(Color.WHITE);
        JComboBox<String> formateurComboBox = new JComboBox<>(); // Drop-down to select formateur
        // Populate the combo box with formateurs (query the database)
        try (Connection connection = connectionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, nom FROM Utilisateurs WHERE typeUtilisateur = 'Formateur'");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                formateurComboBox.addItem(resultSet.getInt("id") + ": " + resultSet.getString("nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        JLabel prixLabel = new JLabel("Price:");
        prixLabel.setForeground(Color.WHITE);
        JTextField prixField = createRoundedTextField("Enter price");
    
        JButton saveButton = createRoundedButton("Save");
        saveButton.addActionListener(e -> {
            String formationName = nameField.getText();
            String description = descriptionField.getText();
            String prixText = prixField.getText();
    
            // Validate input
            if (formationName.isEmpty() || description.isEmpty() || prixText.isEmpty()) {
                JOptionPane.showMessageDialog(addFormationFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // Get the selected formateur's ID
                    String selectedFormateur = (String) formateurComboBox.getSelectedItem();
                    int formateurId = Integer.parseInt(selectedFormateur.split(":")[0]);
    
                    // Get the price (convert to double)
                    double prix = Double.parseDouble(prixText);
    
                    // Create a Formateur object
                    Formateur formateur = new Formateur("", "",""); // You may want to fetch the formateur's name or other details
    
                    // Create a Formation object
                    Formation formation = new Formation(formationName, description, formateur, prix);
    
                    // Insert formation into database
                    connectionBD.insertFormation(formation);
                    JOptionPane.showMessageDialog(addFormationFrame, "Formation Saved!");
                    addFormationFrame.dispose(); // Close the frame after saving
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(addFormationFrame, "Error saving formation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addFormationFrame, "Invalid price input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionField);
        mainPanel.add(formateurLabel);
        mainPanel.add(formateurComboBox);
        mainPanel.add(prixLabel);
        mainPanel.add(prixField);
        mainPanel.add(new JLabel()); // Empty space
        mainPanel.add(saveButton);
    
        addFormationFrame.add(mainPanel);
        addFormationFrame.setVisible(true);
    }
    

    private static void createRegistrationPage() {
        JFrame registrationFrame = new JFrame("Create Account - LearnHub");
        registrationFrame.setSize(400, 400);
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
    
        JLabel titleLabel = new JLabel("Create Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
    
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        formPanel.setBackground(Color.BLACK);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = createRoundedTextField("Enter your name");
    
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        JTextField emailField = createRoundedTextField("Enter your email");
    
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = createRoundedPasswordField("Enter your password");
    
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setForeground(Color.WHITE);
    
        JRadioButton etudiantButton = new JRadioButton("Etudiant");
        etudiantButton.setForeground(Color.WHITE);
        etudiantButton.setBackground(Color.BLACK);
    
        JRadioButton formateurButton = new JRadioButton("Formateur");
        formateurButton.setForeground(Color.WHITE);
        formateurButton.setBackground(Color.BLACK);
    
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(etudiantButton);
        userTypeGroup.add(formateurButton);
    
        JPanel userTypePanel = new JPanel();
        userTypePanel.setBackground(Color.BLACK);
        userTypePanel.add(etudiantButton);
        userTypePanel.add(formateurButton);
    
        JButton registerButton = createRoundedButton("Register");
        JButton backButton = createRoundedButton("Back to Login");
    
        backButton.addActionListener(e -> {
            registrationFrame.dispose();
            createLoginPage();
        });
    
        // Register button action
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String userType = etudiantButton.isSelected() ? "Etudiant" : (formateurButton.isSelected() ? "Formateur" : null);
    
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || userType == null) {
                JOptionPane.showMessageDialog(null, "Please fill all fields and select a user type.");
            } else {
                handleRegistration(name, email, password, userType, registrationFrame);
            }
        });
    
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(userTypeLabel);
        formPanel.add(userTypePanel);
        formPanel.add(backButton);
        formPanel.add(registerButton);
    
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
    
        registrationFrame.add(mainPanel);
        removeInitialFocus(registrationFrame);
    
        registrationFrame.setVisible(true);
    }
    private static void handleRegistration(String name, String email, String password, String userType, JFrame registrationFrame) {
    String checkEmailQuery = "SELECT email FROM utilisateurs WHERE email = ?";
    String insertUserQuery = "INSERT INTO utilisateurs (nom, email, motDePasse, typeUtilisateur) VALUES (?, ?, ?, ?)";

    try (Connection connection = connectionBD.getConnection()) {
        // Check if email already exists
        try (PreparedStatement checkStatement = connection.prepareStatement(checkEmailQuery)) {
            checkStatement.setString(1, email);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Email already exists. Please use a different email.");
                return; // Stop the method
            }
        }

        // Insert new user
        try (PreparedStatement insertStatement = connection.prepareStatement(insertUserQuery)) {
            insertStatement.setString(1, name);
            insertStatement.setString(2, email);
            insertStatement.setString(3, password);
            insertStatement.setString(4, userType);

            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registration successful! Please login.");
            registrationFrame.dispose();
            createLoginPage();
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
    }
}
    
    private static void removeInitialFocus(JFrame frame) {
        // Remove initial focus on the input fields
        frame.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                frame.requestFocus(); // Request focus on the frame itself, not on any specific input
            }
        });
    }

    private static JTextField createRoundedTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setForeground(Color.GRAY);
        textField.setBackground(Color.BLACK);
        textField.setCaretColor(Color.WHITE);

        // Apply the custom rounded border
        textField.setBorder(new RoundedBorder(20)); // Set the radius here

        // Add a focus listener to handle placeholder text removal
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });

        return textField;
    }

    private static JPasswordField createRoundedPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(placeholder);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setForeground(Color.GRAY);
        passwordField.setBackground(Color.BLACK);
        passwordField.setCaretColor(Color.WHITE);

        // Apply the custom rounded border
        passwordField.setBorder(new RoundedBorder(20)); // Set the radius here

        // Add a focus listener to handle placeholder text removal
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                }
            }
        });

        return passwordField;
    }

    private static JButton createRoundedButton(String text) {
        JButton button = new RoundedButton(text, 20);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    private static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setPaint(new GradientPaint(0, 0, Color.BLACK, getWidth(), getHeight(), Color.BLACK));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        }
    }

    // Custom RoundedBorder class for the input fields
    static class RoundedBorder extends AbstractBorder {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(3, 3, 3, 3); // Tighter padding inside the border
        }
    }

    // RoundedButton class to create buttons with rounded corners
    static class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 40); // Adjust size as needed
        }
    }
}
