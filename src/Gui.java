import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Gui extends JFrame {
    private RecipeSystem inventory;
    private JFrame mainFrame;
    private JTable table;
    private DefaultTableModel model;

    public Gui(RecipeSystem inventory) {
        this.inventory = inventory;
        this.mainFrame = this;
        mainFrame.setTitle("Recipe Inventory System");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JButton addButton = new JButton("Add Recipe");
        addButton.addActionListener(e -> openAddRecipeFrame());

        JButton deleteButton = new JButton("Delete Recipe");
        deleteButton.addActionListener(e -> deleteSelectedRecipe());

        JButton viewButton = new JButton("View Recipe");
        viewButton.addActionListener(e -> viewSelectedRecipe());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        this.add(buttonPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Recipe Name");
        model.addColumn("Description");

        updateTableModel();

        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 50, 10));
        scrollPane.setPreferredSize(new Dimension(500, 250));
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel placeholderPanel = new JPanel();
        placeholderPanel.setBackground(Color.lightGray);
        placeholderPanel.setPreferredSize(new Dimension(150, 200));
        this.add(placeholderPanel, BorderLayout.EAST);

        this.pack();
        mainFrame.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    // updates the table after adding another Recipe
    private void updateTableModel() {
        ArrayList<RecipeInventory> recipes = inventory.getRecipes();
        model.setRowCount(0);
        for (RecipeInventory recipe : recipes) {
            model.addRow(new Object[]{recipe.getName(), recipe.getIngredients().toString().replace("[", "").replace("]", "")});
        }
    }

    private void deleteSelectedRecipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String recipeName = (String) model.getValueAt(selectedRow, 0);
            inventory.deleteRecipe(recipeName);
            updateTableModel();
            JOptionPane.showMessageDialog(mainFrame, "Recipe deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please select a recipe to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSelectedRecipe() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String recipeName = (String) model.getValueAt(selectedRow, 0);
            RecipeInventory recipe = inventory.getRecipeByName(recipeName);
            if (recipe != null) {
                new ViewRecipe(mainFrame, recipe);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Please select a recipe to view.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddRecipeFrame() {
        mainFrame.setVisible(false);

        JFrame addFrame = new JFrame("Recipe Inventory System");
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setLayout(new GridBagLayout());

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
        JButton backButton = new JButton("Back");

        DefaultListModel<String> ingredientListModel = new DefaultListModel<>();
        JList<String> ingredientList = new JList<>(ingredientListModel);
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientList);
        ingredientScrollPane.setPreferredSize(new Dimension(250, 150));

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

        finishButton.addActionListener(e -> {
            String recipeName = recipeNameTextbox.getText();
            String description = descriptionTextbox.getText();
            String procedure = procedureTextbox.getText();
            if (recipeName != null && !recipeName.isEmpty()) {
                inventory.addRecipe(recipeName, ingredients, description, procedure);
                ingredients.clear();
                ingredientListModel.clear();
                recipeNameTextbox.setText("");
                descriptionTextbox.setText("");
                procedureTextbox.setText("");
                JOptionPane.showMessageDialog(addFrame, "Recipe added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateTableModel(); // Update the table model after adding a recipe
            } else {
                JOptionPane.showMessageDialog(addFrame, "Please enter a recipe name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            addFrame.dispose();
            mainFrame.setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        addFrame.add(new JLabel("Enter recipe name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        addFrame.add(recipeNameTextbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addFrame.add(new JLabel("Enter ingredient:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        addFrame.add(ingredientTextbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addFrame.add(new JLabel("Enter description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        addFrame.add(descriptionScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addFrame.add(new JLabel("Enter procedure:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        addFrame.add(procedureScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        addFrame.add(submitIngredientButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        addFrame.add(finishButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        addFrame.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        addFrame.add(backButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 7;
        gbc.fill = GridBagConstraints.BOTH;
        addFrame.add(ingredientScrollPane, gbc);

        addFrame.pack();
        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
    }

    public static void main(String[] args) {
        RecipeSystem inventory = new RecipeSystem();
        new Gui(inventory);
    }
}
