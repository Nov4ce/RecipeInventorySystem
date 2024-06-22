import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.sql.*;
import javax.swing.JList;
import javax.swing.ImageIcon;

public class LogIn extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField; // Changed to JPasswordField
    static LogIn frame1 = new LogIn();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame1.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LogIn() {
    	setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 746, 518);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(118, 146, 36));
        panel.setForeground(new Color(255, 255, 255));
        panel.setBounds(0, 0, 732, 126);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel titleName = new JLabel("RECIPE INVENTORY SYSTEM");
        titleName.setBounds(103, 21, 533, 70);
        titleName.setVerticalAlignment(SwingConstants.BOTTOM);
        titleName.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleName);
        titleName.setForeground(new Color(255, 255, 255));
        titleName.setFont(new Font("Tahoma", Font.BOLD, 36));

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(216, 234, 156));
        panel_1.setBounds(0, 122, 732, 359);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("\r\n");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setIcon(new ImageIcon(LogIn.class.getResource("/images/cutefoodpic (2).png")));
        lblNewLabel.setBounds(289, 10, 469, 338);
        panel_1.add(lblNewLabel);
        
                JLabel usernameLabel = new JLabel("Username");
                usernameLabel.setBounds(10, 66, 117, 25);
                panel_1.add(usernameLabel);
                usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
                
                        usernameField = new JTextField();
                        usernameField.setBounds(123, 66, 171, 28);
                        panel_1.add(usernameField);
                        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        usernameField.setColumns(10);
                        
                                JLabel passwordLabel = new JLabel("Password");
                                passwordLabel.setBounds(10, 117, 96, 28);
                                panel_1.add(passwordLabel);
                                passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
                                
                                        passwordField = new JPasswordField(); // Use JPasswordField for password input
                                        passwordField.setBounds(123, 117, 171, 28);
                                        panel_1.add(passwordField);
                                        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
                                        
                                                JButton logIn = new JButton("Log In");
                                                logIn.setBounds(124, 181, 155, 28);
                                                panel_1.add(logIn);
                                                logIn.setForeground(new Color(255, 255, 255));
                                                logIn.setBackground(new Color(118, 146, 36));
                                                logIn.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        String username = usernameField.getText();
                                                        String password = new String(passwordField.getPassword()); // Get password from JPasswordField

                                                        // Perform login validation
                                                        if (validateLogin(username, password)) {
                                                            RecipeSystem inventory = new RecipeSystem();
                                                            Gui frame = new Gui(inventory);
                                                            frame.setVisible(true);
                                                            frame1.setVisible(false);
                                                        } else {
                                                            // Handle invalid login
                                                            JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                });
                                                
                                                        logIn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                                                        
                                                                JLabel signUp = new JLabel("Don't Have Account? Sign Up");
                                                                signUp.setBounds(81, 232, 221, 28);
                                                                panel_1.add(signUp);
                                                                signUp.addMouseListener(new MouseAdapter() {
                                                                    @Override
                                                                    public void mouseClicked(MouseEvent e) {
                                                                        SignUp signUpForm = new SignUp();
                                                                        signUpForm.setVisible(true);
                                                                        frame1.setVisible(false);
                                                                    }
                                                                    @Override
                                                                    public void mouseEntered(MouseEvent e) {
                                                                    	signUp.setForeground(Color.ORANGE); // Change to red on hover
                                                                    }

                                                                    @Override
                                                                    public void mouseExited(MouseEvent e) {
                                                                    	Color originalColor = null;
				signUp.setForeground(originalColor); // Change back to original color
                                                                    }
                                                                });
                                                                
                                                                        signUp.setHorizontalAlignment(SwingConstants.CENTER);
                                                                        signUp.setFont(new Font("Tahoma", Font.PLAIN, 16));
    }

    /**
     * Validate the login credentials against the database.
     */
    private boolean validateLogin(String username, String password) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM login WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next(); // Return true if a match is found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
