import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewRecipe extends JFrame {
    private JPanel contentPane;
    private RecipeInventory recipe;
    private JFrame mainFrame;

    public ViewRecipe(JFrame mainFrame, RecipeInventory recipe) {
        this.recipe = recipe;
        this.mainFrame = mainFrame;
        mainFrame.setVisible(false);
        
        setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 360);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(216, 234, 156));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 10, 378, 234);
        textArea.setEditable(false);
        textArea.setText("Recipe: " + recipe.getName() + "\n\n" +
                         "Ingredients: " + String.join(", ", recipe.getIngredients()) + "\n\n" +
                         "Description: " + recipe.getDescription() + "\n\n" +
                         "Procedure: " + recipe.getProcedure());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 378, 234);
        contentPane.add(scrollPane);
        
        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewRecipe.this.dispose();
                mainFrame.setVisible(true);
            }
        });
        btnNewButton.setBounds(20, 259, 116, 37);
        contentPane.add(btnNewButton);
        
        JPanel panel = new JPanel();
        panel.setBounds(410, 53, 166, 150);
        panel.setBackground(Color.LIGHT_GRAY); // Placeholder for picture
        contentPane.add(panel);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setIcon(new ImageIcon(ViewRecipe.class.getResource("/images/bundlefood.png")));
        lblNewLabel.setBounds(146, -104, 493, 536);
        contentPane.add(lblNewLabel);
        
        setVisible(true);
    }
}
