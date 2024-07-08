import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * The SignUp class provides the user interface for registering a new user
 * in the Recipe Inventory System.
 */
public class SignUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JLabel genderLabel;
    private JComboBox<String> genderComboBox;
    private JCheckBox termsCondition;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField usernameField;
    private JLabel RegistrationLabel;
    private JLabel designLabel;
    private JButton btnBack;

    /**
     * Constructs the SignUp frame.
     */
    public SignUp() {
        setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 632, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(216, 234, 156));
        backgroundPanel.setBounds(0, 0, 608, 452);
        contentPane.add(backgroundPanel);
        backgroundPanel.setLayout(null);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(29, 402, 353, 40);
        backgroundPanel.add(signUpButton);
        signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String validationMessage = validateFields();
                if (validationMessage.isEmpty()) {
                    insertDataIntoDatabase();
                } else {
                    JOptionPane.showMessageDialog(null, validationMessage, "Validation Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        phoneField = new JTextField();
        phoneField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        phoneField.setBounds(222, 185, 160, 30);
        backgroundPanel.add(phoneField);
        phoneField.setColumns(10);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        emailField.setBounds(29, 185, 160, 30);
        backgroundPanel.add(emailField);
        emailField.setColumns(10);

        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        firstNameField.setBounds(29, 114, 160, 30);
        backgroundPanel.add(firstNameField);
        firstNameField.setColumns(10);

        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lastNameField.setBounds(222, 114, 160, 30);
        backgroundPanel.add(lastNameField);
        lastNameField.setColumns(10);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setBounds(222, 154, 127, 21);
        backgroundPanel.add(phoneLabel);
        phoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel lnameLabel = new JLabel("Last Name");
        lnameLabel.setBounds(222, 83, 97, 21);
        backgroundPanel.add(lnameLabel);
        lnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(29, 83, 86, 21);
        backgroundPanel.add(firstNameLabel);
        firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setBounds(29, 154, 113, 21);
        backgroundPanel.add(emailLabel);
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(29, 225, 86, 21);
        backgroundPanel.add(usernameLabel);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        usernameField.setBounds(29, 256, 160, 30);
        backgroundPanel.add(usernameField);
        usernameField.setColumns(10);

        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(222, 225, 66, 21);
        backgroundPanel.add(genderLabel);
        genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(29, 292, 86, 21);
        backgroundPanel.add(passwordLabel);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setBounds(29, 323, 160, 30);
        backgroundPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(222, 292, 160, 21);
        backgroundPanel.add(confirmPasswordLabel);
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        confirmPasswordField.setBounds(222, 323, 160, 30);
        backgroundPanel.add(confirmPasswordField);

        termsCondition = new JCheckBox("<html>By selecting Sign Up Account, you read and agree to our <br>Terms and Conditions</html>");
        termsCondition.setFont(new Font("Tahoma", Font.PLAIN, 9));
        termsCondition.setBounds(29, 360, 176, 40);
        backgroundPanel.add(termsCondition);
        termsCondition.setBackground(new Color(216, 234, 156));
        termsCondition.setVerticalAlignment(SwingConstants.TOP);

        genderComboBox = new JComboBox<>(new String[]{"None", "Prefer not to say", "Male", "Female"});
        genderComboBox.setBounds(222, 256, 160, 30);
        backgroundPanel.add(genderComboBox);
        genderComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));

        RegistrationLabel = new JLabel("Registration");
        RegistrationLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        RegistrationLabel.setBounds(10, 10, 265, 40);
        backgroundPanel.add(RegistrationLabel);

        designLabel = new JLabel("New label");
        designLabel.setIcon(new ImageIcon(SignUp.class.getResource("/images/foood.png")));
        designLabel.setBounds(152, -29, 467, 495);
        backgroundPanel.add(designLabel);
        
        btnBack = new JButton("Back");
        btnBack.setBounds(513, 10, 85, 21);
        backgroundPanel.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogIn frame = new LogIn();
                frame.setVisible(true);
                dispose(); 
            }
        });
    }

    /**
     * Inserts the data entered by the user into the database.
     */
    private void insertDataIntoDatabase() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailField.getText();
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        String phoneNumber = phoneField.getText();
        String gender = genderComboBox.getSelectedItem().toString();

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO login (username, password, lname, fname, emailaddress, contactNo, Gender) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, lastName);
            stmt.setString(4, firstName);
            stmt.setString(5, emailAddress);
            stmt.setString(6, phoneNumber);
            stmt.setString(7, gender);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "User registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                LogIn frame = new LogIn();
                frame.setVisible(true);
                dispose();
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    /**
     * Validates all input fields and checks that passwords match.
     * 
     * @return A validation message if any field is invalid, otherwise an empty string.
     */
    private String validateFields() {
        if (firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                usernameField.getText().isEmpty() ||
                passwordField.getPassword().length == 0 ||
                confirmPasswordField.getPassword().length == 0 ||
                emailField.getText().isEmpty() ||
                phoneField.getText().isEmpty() ||
                genderComboBox.getSelectedItem().toString().equals("None") ||
                !termsCondition.isSelected()) {
            return "Please fill out all required fields and accept the terms and conditions.";
        }
        // Check if password and confirm password match
        if (!new String(passwordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))) {
            return "Password and Confirm Password do not match.";
        }
        // Validate email format
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(emailRegex, emailField.getText())) {
            return "Please enter a valid email address.";
        }
        return "";
    }

    /**
     * The main method to launch the application.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LogIn frame = new LogIn();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
