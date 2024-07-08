import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The Gui class represents the main GUI for the Recipe Inventory System.
 */
public class Gui extends JFrame {
    private RecipeSystem inventory;
    private JTable table;
    private DefaultTableModel model;
    private JLabel imageLabel; // To display the recipe image

    /**
     * Constructs the Gui object.
     * 
     * @param inventory The RecipeSystem object managing the recipes.
     */
    public Gui(RecipeSystem inventory) {
        this.inventory = inventory;
        setTitle("Recipe Inventory System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton addButton = new JButton("Add Recipe");
        addButton.addActionListener(e -> openAddRecipeFrame());

        JButton deleteButton = new JButton("Delete Recipe");
        deleteButton.addActionListener(e -> deleteSelectedRecipe());

        JButton viewButton = new JButton("View Recipe");
        viewButton.addActionListener(e -> viewSelectedRecipe());

        JButton updateButton = new JButton("Update Recipe");
        updateButton.addActionListener(e -> updateSelectedRecipe());
        
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(255, 102, 102)); // Lighter red color
        logoutButton.setOpaque(true);
        logoutButton.addActionListener(e -> logoutAdmin());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(118, 146, 36));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Recipe Name");
        model.addColumn("Description");

        updateTableModel();

        table = new JTable(model);
        table.setRowHeight(25);
        table.setDefaultEditor(Object.class, null); // Disable cell editing

        // Add mouse listener to the table to detect row selection
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String recipeName = (String) model.getValueAt(selectedRow, 0);
                    byte[] imageBytes = inventory.getRecipeImage(recipeName);
                    if (imageBytes != null) {
                        ImageIcon imageIcon = new ImageIcon(imageBytes);
                        Image img = imageIcon.getImage();
                        Image scaledImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImg));
                    } else {
                        imageLabel.setIcon(null);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(216, 234, 156));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
        scrollPane.setPreferredSize(new Dimension(500, 250));
        add(scrollPane, BorderLayout.CENTER);

        // Panel for displaying the image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.lightGray);
        imagePanel.setPreferredSize(new Dimension(150, 200));
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 200)); // Set preferred size for the image label
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Logs out the admin and opens the login frame.
     */
    private void logoutAdmin() {
        LogIn login = new LogIn();
        login.setVisible(true);
        dispose();
    }

    /**
     * Updates the table model with the current recipes from the inventory.
     */
    void updateTableModel() {
        ArrayList<RecipeInventory> recipes = inventory.getRecipes();
        model.setRowCount(0); // Clear the table model
        for (RecipeInventory recipe : recipes) {
            model.addRow(new Object[]{recipe.getName(), recipe.getDescription()});
        }
    }

    /**
     * Deletes the selected recipe from the inventory.
     */
    private void deleteSelectedRecipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String recipeName = (String) model.getValueAt(selectedRow, 0);
            inventory.deleteRecipe(recipeName);
            updateTableModel();
            JOptionPane.showMessageDialog(this, "Recipe deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a recipe to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Views the selected recipe details in a new frame.
     */
    private void viewSelectedRecipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String recipeName = (String) model.getValueAt(selectedRow, 0);
            RecipeInventory recipe = inventory.getRecipeByName(recipeName);
            if (recipe != null) {
                new ViewRecipe(this, recipe);
                // Display the image if available
                byte[] imageBytes = inventory.getRecipeImage(recipeName);
                if (imageBytes != null) {
                    ImageIcon imageIcon = new ImageIcon(imageBytes);
                    Image img = imageIcon.getImage();
                    Image scaledImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImg));
                } else {
                    imageLabel.setIcon(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a recipe to view.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the selected recipe details in a new frame.
     */
    private void updateSelectedRecipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String recipeName = (String) model.getValueAt(selectedRow, 0);
            RecipeInventory recipe = inventory.getRecipeByName(recipeName);
            if (recipe != null) {
                UpdateFrame updateFrame = new UpdateFrame(recipe, inventory);
                updateFrame.setVisible(true);

                // Hide the main GUI
                setVisible(false);

                // Add a window listener to reopen the main GUI when the UpdateFrame is closed
                updateFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        setVisible(true);
                        updateTableModel(); // Refresh the table model
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a recipe to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens a new frame to add a recipe.
     */
    private void openAddRecipeFrame() {
        new AddRecipeFrame(this, inventory);
        dispose();
    }
}
