import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * The ViewRecipe class represents a GUI frame for viewing a recipe's details.
 */
public class ViewRecipe extends JFrame {
    private JPanel contentPane;
    private RecipeInventory recipe;
    private JFrame mainFrame;

    /**
     * Constructs a ViewRecipe object.
     *
     * @param mainFrame The main GUI frame.
     * @param recipe The RecipeInventory object representing the recipe to view.
     */
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
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setBounds(10, 10, 378, 234);
        textArea.setText("Recipe: " + recipe.getName() + "\n\n" +
                "Ingredients: " + String.join(", ", recipe.getIngredients()) + "\n\n" +
                "Description: " + recipe.getDescription() + "\n\n" +
                "Procedure: " + recipe.getProcedure());
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 378, 234);
        contentPane.add(scrollPane);

        JButton BackButton = new JButton("Back");
        BackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewRecipe.this.dispose();
                mainFrame.setVisible(true);
            }
        });
        BackButton.setBounds(20, 259, 116, 37);
        contentPane.add(BackButton);
        
        // Displaying the image
        if (recipe.getImage() != null) {
            try {
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(recipe.getImage()));
                if (img != null) {
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(178, 170, Image.SCALE_SMOOTH));
                    JLabel lblNewLabel = new JLabel(icon);
                    lblNewLabel.setBounds(398, 35, 178, 170);
                    contentPane.add(lblNewLabel);
                } else {
                    System.err.println("Failed to load image for recipe: " + recipe.getName());
                }
            } catch (IOException ex) {
                System.err.println("Error loading image for recipe: " + recipe.getName());
                ex.printStackTrace();
            }
        } else {
            System.err.println("Recipe " + recipe.getName() + " has no image bytes.");
        }

        setVisible(true);
    }
}
