import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The AddRecipeFrame class represents a GUI frame for adding a new recipe.
 */
public class AddRecipeFrame extends JFrame {
    private Gui mainFrame;
    private RecipeSystem inventory;

    /**
     * Constructs the AddRecipeFrame object.
     * 
     * @param mainFrame The main GUI frame.
     * @param inventory The RecipeSystem object managing the recipes.
     */
    public AddRecipeFrame(Gui mainFrame, RecipeSystem inventory) {
        this.mainFrame = mainFrame;
        this.inventory = inventory;

        setTitle("Add Recipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Set the background color of the content pane
        getContentPane().setBackground(new Color(216, 234, 156));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField recipeNameTextbox = new JTextField();
        recipeNameTextbox.setPreferredSize(new Dimension(250, 30));

        JTextField ingredientTextbox = new JTextField();
        ingredientTextbox.setPreferredSize(new Dimension(250, 30));

        JTextArea descriptionTextbox = new JTextArea(3, 20);
        descriptionTextbox.setLineWrap(true);
        descriptionTextbox.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextbox);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JTextArea procedureTextbox = new JTextArea(3, 20);
        procedureTextbox.setLineWrap(true);
        procedureTextbox.setWrapStyleWord(true);
        JScrollPane procedureScrollPane = new JScrollPane(procedureTextbox);
        procedureScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton submitIngredientButton = new JButton("Add Ingredient");
        JButton finishButton = new JButton("Finish Recipe");
        finishButton.setBackground(new Color(144, 238, 144)); // Light green color
        finishButton.setOpaque(true);
        JButton backButton = new JButton("Back");

        DefaultListModel<String> ingredientListModel = new DefaultListModel<>();
        JList<String> ingredientList = new JList<>(ingredientListModel);
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientList);
        ingredientScrollPane.setPreferredSize(new Dimension(250, 75));

        ArrayList<String> ingredients = new ArrayList<>();

        submitIngredientButton.addActionListener(e -> {
            String ingredient = ingredientTextbox.getText();
            if (ingredient != null && !ingredient.isEmpty()) {
                ingredients.add(ingredient);
                ingredientListModel.addElement(ingredient);
                ingredientTextbox.setText("");
            }
        });

        JButton deleteButton = new JButton("Delete Ingredient");
        deleteButton.addActionListener(e -> {
            int selectedIndex = ingredientList.getSelectedIndex();
            if (selectedIndex != -1) {
                ingredients.remove(selectedIndex);
                ingredientListModel.remove(selectedIndex);
            }
        });

        // File chooser for selecting an image
        JButton uploadImageButton = new JButton("Upload Image");
        JLabel uploadImageLabel = new JLabel();
        uploadImageLabel.setPreferredSize(new Dimension(250, 150));
        uploadImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getPath());
                Image img = imageIcon.getImage();
                Image scaledImg = img.getScaledInstance(250, 150, Image.SCALE_SMOOTH);
                uploadImageLabel.setIcon(new ImageIcon(scaledImg));
            }
        });

        finishButton.addActionListener(e -> {
            String recipeName = recipeNameTextbox.getText();
            String description = descriptionTextbox.getText();
            String procedure = procedureTextbox.getText();
            byte[] imageBytes = null;

            if (uploadImageLabel.getIcon() == null || recipeName.isEmpty() || description.isEmpty() || procedure.isEmpty() || ingredients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields and upload an image.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convert uploaded image to bytes
            ImageIcon uploadedIcon = (ImageIcon) uploadImageLabel.getIcon();
            if (uploadedIcon != null) {
                Image img = uploadedIcon.getImage();
                BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D bGr = bImage.createGraphics();
                bGr.drawImage(img, 0, 0, null);
                bGr.dispose();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bImage, "jpg", bos); // You might want to detect the file type and write accordingly
                    imageBytes = bos.toByteArray();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error processing image.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            inventory.addRecipe(recipeName, ingredients, description, procedure, imageBytes);
            ingredients.clear();
            ingredientListModel.clear();
            recipeNameTextbox.setText("");
            descriptionTextbox.setText("");
            procedureTextbox.setText("");
            uploadImageLabel.setIcon(null);
            uploadImageLabel.setText("");
            JOptionPane.showMessageDialog(this, "Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.updateTableModel();
            dispose();
            mainFrame.setVisible(true);
        });

        backButton.addActionListener(e -> {
            dispose();
            mainFrame.setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Enter recipe name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(recipeNameTextbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Enter ingredient:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(ingredientTextbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Enter description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(descriptionScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Enter procedure:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(procedureScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.5;
        add(backButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weightx = 0.5;
        add(uploadImageButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(submitIngredientButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        add(finishButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(deleteButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(ingredientScrollPane, BorderLayout.NORTH);

        JPanel imageHolderPanel = new JPanel();
        imageHolderPanel.setPreferredSize(new Dimension(250, 150));
        imageHolderPanel.add(uploadImageLabel);
        sidePanel.add(imageHolderPanel, BorderLayout.SOUTH);

        add(sidePanel, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
