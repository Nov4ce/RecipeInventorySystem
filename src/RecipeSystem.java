import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/dbrecipe";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}

class RecipeInventory {
    String name;
    ArrayList<String> ingredients;
    String description;
    String procedure;

    public RecipeInventory(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
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
}

public class RecipeSystem {
    Map<String, RecipeInventory> recipes;

    public RecipeSystem() {
        recipes = new HashMap<>();
        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            String createDishTable = "CREATE TABLE IF NOT EXISTS Dish (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "ingredientName VARCHAR(255) NOT NULL," +
                    "recipeDescription TEXT," +
                    "recipeProcedure TEXT," +
                    "image BLOB)";  // Add image column
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

    public void addRecipe(String name, ArrayList<String> ingredients, String description, String procedure) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            // Insert into Dish table
            String insertDish = "INSERT INTO Dish (ingredientName, recipeDescription, recipeProcedure) VALUES (?, ?, ?)";
            PreparedStatement dishStmt = conn.prepareStatement(insertDish, Statement.RETURN_GENERATED_KEYS);
            dishStmt.setString(1, name);
            dishStmt.setString(2, description);
            dishStmt.setString(3, procedure);
            dishStmt.executeUpdate();

            // Get the generated dish ID
            ResultSet rs = dishStmt.getGeneratedKeys();
            if (rs.next()) {
                int dishId = rs.getInt(1);

                // Insert into DishIngredients table
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

    public void deleteRecipe(String name) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            // Delete from DishIngredients table
            String deleteIngredients = "DELETE FROM dishingredients WHERE ingredientName = ?";
            PreparedStatement ingredientStmt = conn.prepareStatement(deleteIngredients);
            ingredientStmt.setString(1, name);
            ingredientStmt.executeUpdate();
            
            // Delete from Dish table
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
    public RecipeInventory getRecipeByName(String name) {
        RecipeInventory recipe = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String queryDish = "SELECT ingredientName, recipeDescription, recipeProcedure FROM Dish WHERE ingredientName = ?";
            String queryIngredients = "SELECT ingredient FROM DishIngredients WHERE ingredientName = ?";
            
            PreparedStatement dishStmt = conn.prepareStatement(queryDish);
            dishStmt.setString(1, name);
            ResultSet dishRs = dishStmt.executeQuery();
            
            if (dishRs.next()) {
                String recipeName = dishRs.getString("ingredientName");
                String description = dishRs.getString("recipeDescription");
                String procedure = dishRs.getString("recipeProcedure");

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public ArrayList<RecipeInventory> getRecipes() {
        ArrayList<RecipeInventory> recipeList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT d.ingredientName, d.recipeDescription " +
                    "FROM Dish d ORDER BY d.ingredientName";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String recipeName = rs.getString("ingredientName");
                String description = rs.getString("recipeDescription");
                recipeList.add(new RecipeInventory(recipeName, new ArrayList<>(List.of(description))));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}