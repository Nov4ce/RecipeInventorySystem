import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SignUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField textField_2;
    private JTextField emailField;
    private JTextField phoneField;
    private JLabel genderLabel;
    private JComboBox<String> genderComboBox;
    private JCheckBox termsCondition;
    private JPasswordField passwordField;
    private JTextField usernameField; // Add this field
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SignUp() {
    	setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 632, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(216, 234, 156));
        panel_1.setBounds(0, 0, 608, 452);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JButton signUpButton = 	new JButton("Sign Up");
        signUpButton.setBounds(29, 377, 353, 40);
        panel_1.add(signUpButton);
        signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    insertDataIntoDatabase();
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        phoneField = new JTextField();
        phoneField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        phoneField.setBounds(222, 185, 160, 30);
        panel_1.add(phoneField);
        phoneField.setColumns(10);

        emailField = new JTextField();
        emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        emailField.setBounds(29, 185, 160, 30);
        panel_1.add(emailField);
        emailField.setColumns(10);

        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        firstNameField.setBounds(29, 114, 160, 30);
        panel_1.add(firstNameField);
        firstNameField.setColumns(10);

        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lastNameField.setBounds(222, 114, 160, 30);
        panel_1.add(lastNameField);
        lastNameField.setColumns(10);

        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setBounds(222, 154, 127, 21);
        panel_1.add(phoneLabel);
        phoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel lnameLabel = new JLabel("Last Name");
        lnameLabel.setBounds(222, 83, 97, 21);
        panel_1.add(lnameLabel);
        lnameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(29, 83, 86, 21);
        panel_1.add(firstNameLabel);
        firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setBounds(29, 154, 113, 21);
        panel_1.add(emailLabel);
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(29, 225, 86, 21);
        panel_1.add(usernameLabel);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        usernameField.setBounds(29, 256, 160, 30);
        panel_1.add(usernameField);
        usernameField.setColumns(10);

        genderLabel = new JLabel("Gender");
        genderLabel.setBounds(222, 225, 66, 21);
        panel_1.add(genderLabel);
        genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(29, 292, 86, 21);
        panel_1.add(passwordLabel);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setBounds(29, 323, 160, 30);
        panel_1.add(passwordField);

        termsCondition = new JCheckBox("<html>By selecting Sign Up Account, you read and agree to our <br>Terms and Conditions</html>");
        termsCondition.setFont(new Font("Tahoma", Font.PLAIN, 9));
        termsCondition.setBounds(222, 295, 146, 58);
        panel_1.add(termsCondition);
        termsCondition.setBackground(new Color(216, 234, 156));
        termsCondition.setVerticalAlignment(SwingConstants.TOP);

        genderComboBox = new JComboBox<>(new String[]{"None", "Prefer not to say", "Male", "Female"});
        genderComboBox.setBounds(222, 256, 160, 30);
        panel_1.add(genderComboBox);
        genderComboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
        lblNewLabel_2 = new JLabel("Registration");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel_2.setBounds(10, 10, 265, 40);
        panel_1.add(lblNewLabel_2);
        
        lblNewLabel_3 = new JLabel("New label");
        lblNewLabel_3.setIcon(new ImageIcon(SignUp.class.getResource("/images/foood.png")));
        lblNewLabel_3.setBounds(141, -22, 467, 495);
        panel_1.add(lblNewLabel_3);

    }

    // Method to insert data into database
    private void insertDataIntoDatabase() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String emailAddress = emailField.getText();
        String username = usernameField.getText(); // Get username from usernameField
        // Get password as char array from JPasswordField
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars); // Convert char array to String
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
                // Optionally, update UI or perform other actions upon success
            }

            conn.commit();
        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of exception
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
                    conn.setAutoCommit(true); // Restore auto-commit behavior
                    conn.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    // Method to validate all fields are filled
    private boolean validateFields() {
        if (firstNameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            usernameField.getText().isEmpty() ||
            passwordField.getPassword().length == 0 ||
            emailField.getText().isEmpty() ||
            phoneField.getText().isEmpty() ||
            genderComboBox.getSelectedItem().toString().equals("None")) {
            return false;
        }
        return true;
    }
}
