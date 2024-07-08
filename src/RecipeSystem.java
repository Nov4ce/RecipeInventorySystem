import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 * The DatabaseManager class provides a connection to the MySQL database.
 */
class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/dbrecipe";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    /**
     * Gets the connection to the database.
     * 
     * @return The connection to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}

/**
 * The RecipeInventory class represents a recipe with its details.
 */
class RecipeInventory {
    String name;
    ArrayList<String> ingredients;
    String description;
    String procedure;
    byte[] image;

    /**
     * Constructs a RecipeInventory object.
     * 
     * @param name The name of the recipe.
     * @param ingredients The list of ingredients.
     */
    public RecipeInventory(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    String getName() {
        return name;
    }

    ArrayList<String> getIngredients() {
        return ingredients;
    }

    String getDescription() {
        return description;
    }

    String getProcedure() {
        return procedure;
    }

    byte[] getImage() {
        return image;
    }

    void setImage(byte[] image) {
        this.image = image;
    }
}

/**
 * The RecipeSystem class manages the recipes in the database.
 */
public class RecipeSystem {
    Map<String, RecipeInventory> recipes;

    /**
     * Constructs a RecipeSystem object and initializes the database tables.
     */
    public RecipeSystem() {
        recipes = new HashMap<>();
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            String createDishTable = "CREATE TABLE IF NOT EXISTS Dish (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "ingredientName VARCHAR(255) NOT NULL," +
                    "recipeDescription TEXT," +
                    "recipeProcedure TEXT," +
                    "image BLOB)";
            String createDishIngredientsTable = "CREATE TABLE IF NOT EXISTS DishIngredients (" +
                    "id INT NOT NULL," +
                    "ingredientName VARCHAR(255) NOT NULL," +
                    "ingredient VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY(id) REFERENCES Dish(id))";
            stmt.execute(createDishTable);
            stmt.execute(createDishIngredientsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a recipe to the database.
     * 
     * @param name The name of the recipe.
     * @param ingredients The list of ingredients.
     * @param description The description of the recipe.
     * @param procedure The procedure to make the recipe.
     * @param image The image of the recipe.
     */
    public void addRecipe(String name, ArrayList<String> ingredients, String description, String procedure, byte[] image) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            String insertDish = "INSERT INTO Dish (ingredientName, recipeDescription, recipeProcedure, image) VALUES (?, ?, ?, ?)";
            PreparedStatement dishStmt = conn.prepareStatement(insertDish, Statement.RETURN_GENERATED_KEYS);
            dishStmt.setString(1, name);
            dishStmt.setString(2, description);
            dishStmt.setString(3, procedure);
            dishStmt.setBytes(4, image);
            dishStmt.executeUpdate();

            ResultSet rs = dishStmt.getGeneratedKeys();
            if (rs.next()) {
                int dishId = rs.getInt(1);

                String insertIngredient = "INSERT INTO DishIngredients (id, ingredientName, ingredient) VALUES (?, ?, ?)";
                PreparedStatement ingredientStmt = conn.prepareStatement(insertIngredient);
                for (String ing : ingredients) {
                    ingredientStmt.setInt(1, dishId);
                    ingredientStmt.setString(2, name);
                    ingredientStmt.setString(3, ing);
                    ingredientStmt.addBatch();
                }
                ingredientStmt.executeBatch();

                conn.commit();
                ingredientStmt.close();
            }
            rs.close();
            dishStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a recipe from the database by name.
     * 
     * @param name The name of the recipe to be deleted.
     */
    public void deleteRecipe(String name) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            String deleteIngredients = "DELETE FROM dishingredients WHERE ingredientName = ?";
            PreparedStatement ingredientStmt = conn.prepareStatement(deleteIngredients);
            ingredientStmt.setString(1, name);
            ingredientStmt.executeUpdate();

            String deleteDish = "DELETE FROM dish WHERE ingredientName = ?";
            PreparedStatement dishStmt = conn.prepareStatement(deleteDish);
            dishStmt.setString(1, name);
            dishStmt.executeUpdate();

            conn.commit();
            dishStmt.close();
            ingredientStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a recipe from the database by name.
     * 
     * @param name The name of the recipe to retrieve.
     * @return The RecipeInventory object representing the recipe.
     */
    public RecipeInventory getRecipeByName(String name) {
        RecipeInventory recipe = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String queryDish = "SELECT ingredientName, recipeDescription, recipeProcedure, image FROM Dish WHERE ingredientName = ?";
            String queryIngredients = "SELECT ingredient FROM DishIngredients WHERE ingredientName = ?";

            PreparedStatement dishStmt = conn.prepareStatement(queryDish);
            dishStmt.setString(1, name);
            ResultSet dishRs = dishStmt.executeQuery();

            if (dishRs.next()) {
                String recipeName = dishRs.getString("ingredientName");
                String description = dishRs.getString("recipeDescription");
                String procedure = dishRs.getString("recipeProcedure");
                byte[] image = dishRs.getBytes("image");

                ArrayList<String> ingredients = new ArrayList<>();
                PreparedStatement ingredientsStmt = conn.prepareStatement(queryIngredients);
                ingredientsStmt.setString(1, recipeName);
                ResultSet ingredientsRs = ingredientsStmt.executeQuery();

                while (ingredientsRs.next()) {
                    ingredients.add(ingredientsRs.getString("ingredient"));
                }

                recipe = new RecipeInventory(recipeName, ingredients);
                recipe.description = description;
                recipe.procedure = procedure;
                recipe.setImage(image);

                ingredientsStmt.close();
                ingredientsRs.close();
            }
            dishStmt.close();
            dishRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    /**
     * Retrieves all recipes from the database.
     * 
     * @return A list of RecipeInventory objects representing all recipes.
     */
    public ArrayList<RecipeInventory> getRecipes() {
        ArrayList<RecipeInventory> recipeList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT d.ingredientName, d.recipeDescription FROM Dish d ORDER BY d.ingredientName";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String recipeName = rs.getString("ingredientName");
                String description = rs.getString("recipeDescription");
                RecipeInventory recipe = new RecipeInventory(recipeName, new ArrayList<>());
                recipe.description = description;
                recipeList.add(recipe);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    /**
     * Retrieves the image of a recipe from the database by recipe name.
     * 
     * @param recipeName The name of the recipe.
     * @return The image of the recipe as a byte array.
     */
    public byte[] getRecipeImage(String recipeName) {
        byte[] image = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT image FROM Dish WHERE ingredientName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, recipeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                image = rs.getBytes("image");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Updates a recipe in the database.
     * 
     * @param oldName The old name of the recipe.
     * @param newName The new name of the recipe.
     * @param newIngredients The new list of ingredients.
     * @param newDescription The new description of the recipe.
     * @param newProcedure The new procedure to make the recipe.
     * @param newImage The new image of the recipe.
     */
    public void updateRecipe(String oldName, String newName, ArrayList<String> newIngredients, String newDescription, String newProcedure, byte[] newImage) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            // Update the recipe in the Dish table
            String updateDish = "UPDATE Dish SET ingredientName = ?, recipeDescription = ?, recipeProcedure = ?, image = ? WHERE ingredientName = ?";
            PreparedStatement dishStmt = conn.prepareStatement(updateDish);
            dishStmt.setString(1, newName);
            dishStmt.setString(2, newDescription);
            dishStmt.setString(3, newProcedure);
            dishStmt.setBytes(4, newImage);
            dishStmt.setString(5, oldName);
            dishStmt.executeUpdate();

            // Update the ingredients in the DishIngredients table
            String deleteOldIngredients = "DELETE FROM DishIngredients WHERE ingredientName = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteOldIngredients);
            deleteStmt.setString(1, oldName);
            deleteStmt.executeUpdate();

            String insertIngredient = "INSERT INTO DishIngredients (id, ingredientName, ingredient) VALUES ((SELECT id FROM Dish WHERE ingredientName = ?), ?, ?)";
            PreparedStatement ingredientStmt = conn.prepareStatement(insertIngredient);
            for (String ing : newIngredients) {
                ingredientStmt.setString(1, newName);
                ingredientStmt.setString(2, newName);
                ingredientStmt.setString(3, ing);
                ingredientStmt.addBatch();
            }
            ingredientStmt.executeBatch();

            conn.commit();
            ingredientStmt.close();
            dishStmt.close();
            deleteStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
