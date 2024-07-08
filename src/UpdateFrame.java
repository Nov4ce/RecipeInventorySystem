import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * The UpdateFrame class represents a GUI frame for updating an existing recipe.
 */
public class UpdateFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField RecipeNameField;
    private JTextArea recipeDescriptionField;
    private JTextArea recipeProcedureField;
    private File imageFile;
    private RecipeSystem inventory;
    private RecipeInventory recipe;
    private JLabel imageHolder;
    private byte[] imageBytes;
    private DefaultListModel<String> ingredientsListModel;
    private JList<String> ingredientsList;
    private JTextField IngridientField;

    /**
     * Constructs an UpdateFrame object.
     *
     * @param recipe The RecipeInventory object representing the recipe to update.
     * @param inventory The RecipeSystem object representing the inventory of recipes.
     */
    public UpdateFrame(RecipeInventory recipe, RecipeSystem inventory) {
        this.inventory = inventory;
        this.recipe = recipe; // Fetch recipe directly
        if (recipe == null) {
            JOptionPane.showMessageDialog(this, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 757, 504);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelBackgroundColor = new JPanel();
        panelBackgroundColor.setBackground(new Color(216, 234, 156));
        panelBackgroundColor.setBounds(0, 0, 757, 463);
        contentPane.add(panelBackgroundColor);
        panelBackgroundColor.setLayout(null);

        JLabel titleName = new JLabel("Recipe Update");
        titleName.setForeground(new Color(240, 240, 240));
        titleName.setBounds(10, 10, 294, 28);
        titleName.setFont(new Font("Tahoma", Font.BOLD, 24));
        panelBackgroundColor.add(titleName);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 0, 755, 72);
        panel_1.setBackground(new Color(118, 146, 36));
        panelBackgroundColor.add(panel_1);
        panel_1.setLayout(null);

        JPanel panelUpdate = new JPanel();
        panelUpdate.setBounds(10, 102, 527, 271);
        panelBackgroundColor.add(panelUpdate);
        panelUpdate.setLayout(null);

        JLabel RecipeNameLabel = new JLabel("Recipe Name:");
        RecipeNameLabel.setBounds(10, 10, 120, 25);
        panelUpdate.add(RecipeNameLabel);

        RecipeNameField = new JTextField(recipe.getName());
        RecipeNameField.setBounds(127, 10, 200, 25);
        panelUpdate.add(RecipeNameField);

        JLabel recipeDescriptionLabel = new JLabel("Recipe Description:");
        recipeDescriptionLabel.setBounds(10, 135, 120, 25);
        panelUpdate.add(recipeDescriptionLabel);

        recipeDescriptionField = new JTextArea(recipe.getDescription());
        recipeDescriptionField.setBounds(127, 117, 200, 50);
        panelUpdate.add(recipeDescriptionField);

        JLabel recipeProcedureLabel = new JLabel("Recipe Procedure:");
        recipeProcedureLabel.setBounds(10, 211, 120, 25);
        panelUpdate.add(recipeProcedureLabel);

        recipeProcedureField = new JTextArea(recipe.getProcedure());
        recipeProcedureField.setBounds(127, 195, 200, 50);
        panelUpdate.add(recipeProcedureField);

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setBounds(10, 55, 120, 25);
        panelUpdate.add(ingredientsLabel);

        // Initialize ingredients list
        ingredientsListModel = new DefaultListModel<>();
        for (String ingredient : recipe.getIngredients()) {
            ingredientsListModel.addElement(ingredient);
        }

        ingredientsList = new JList<>(ingredientsListModel);
        JScrollPane scrollPane = new JScrollPane(ingredientsList);
        scrollPane.setBounds(547, 102, 161, 242);
        panelBackgroundColor.add(scrollPane);

        JButton addPictureButton = new JButton("Upload Image");
        addPictureButton.setBounds(363, 206, 135, 34);
        panelUpdate.add(addPictureButton);

        imageHolder = new JLabel();
        imageHolder.setBounds(337, 26, 180, 161);
        if (recipe.getImage() != null) {
            imageBytes = recipe.getImage();
            ImageIcon imageIcon = new ImageIcon(imageBytes);
            Image img = imageIcon.getImage();
            Image scaledImg = img.getScaledInstance(imageHolder.getWidth(), imageHolder.getHeight(), Image.SCALE_SMOOTH);
            imageHolder.setIcon(new ImageIcon(scaledImg));
        }
        panelUpdate.add(imageHolder);
        
        IngridientField = new JTextField();
        IngridientField.setBounds(127, 55, 200, 25);
        panelUpdate.add(IngridientField);

        addPictureButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadImage();
            }
        });

        JButton BackButton = new JButton("Back");
        BackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        BackButton.setBounds(10, 394, 135, 35);
        panelBackgroundColor.add(BackButton);

        JButton SaveButton = new JButton("Save");
        SaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRecipe();
            }
        });
        SaveButton.setBounds(169, 394, 135, 35);
        panelBackgroundColor.add(SaveButton);

        JButton btnAddIngredient = new JButton("Add Ingredient");
        btnAddIngredient.setBounds(553, 366, 155, 35);
        panelBackgroundColor.add(btnAddIngredient);

        JButton btnDeleteIngredient = new JButton("Delete Ingredient");
        btnDeleteIngredient.setBounds(553, 411, 155, 35);
        panelBackgroundColor.add(btnDeleteIngredient);

        btnAddIngredient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addIngredient();
            }
        });

        btnDeleteIngredient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteIngredient();
            }
        });
    }

    /**
     * Opens a file chooser to upload an image and sets it to the image holder.
     */
    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                imageFile = fileChooser.getSelectedFile();
                imageBytes = Files.readAllBytes(imageFile.toPath());
                ImageIcon imageIcon = new ImageIcon(imageBytes);
                Image img = imageIcon.getImage();
                Image scaledImg = img.getScaledInstance(imageHolder.getWidth(), imageHolder.getHeight(), Image.SCALE_SMOOTH);
                imageHolder.setIcon(new ImageIcon(scaledImg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the recipe with the new details provided by the user.
     */
    private void updateRecipe() {
        String oldName = recipe.getName();
        String newName = RecipeNameField.getText();
        String description = recipeDescriptionField.getText();
        String procedure = recipeProcedureField.getText();
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsListModel.getSize(); i++) {
            ingredients.add(ingredientsListModel.getElementAt(i));
        }

        // Validation
        if (newName.isEmpty() || description.isEmpty() || procedure.isEmpty() || ingredients.isEmpty() || imageBytes == null) {
            JOptionPane.showMessageDialog(this, "Enter all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update the recipe in inventory
        inventory.updateRecipe(oldName, newName, ingredients, description, procedure, imageBytes);

        JOptionPane.showMessageDialog(this, "Recipe updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    /**
     * Adds a new ingredient to the ingredients list.
     */
    private void addIngredient() {
        String newIngredient = IngridientField.getText();
        if (newIngredient != null && !newIngredient.trim().isEmpty()) {
            ingredientsListModel.addElement(newIngredient);
            IngridientField.setText("");
        }
    }

    /**
     * Deletes the selected ingredient from the ingredients list.
     */
    private void deleteIngredient() {
        int selectedIndex = ingredientsList.getSelectedIndex();
        if (selectedIndex != -1) {
            ingredientsListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an ingredient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
