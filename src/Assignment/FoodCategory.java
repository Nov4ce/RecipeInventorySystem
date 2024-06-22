package Assignment;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class FoodItem extends Item{
	String name;
	public FoodItem(String name) {
		super(name);
	}
}

class Category {
	
	char bulletSymbol='\u2023'; 
	static char Lsymbol='\u2514';
	static char tsymbol='\u251C';
	static char Isymbol='\u007C';
	private Map <String, List<FoodItem>> categories = new HashMap<>();
	
	public Category () {
		 categories.put("Fruits", new ArrayList<>());
		 categories.put("Vegetables", new ArrayList<>());
		 categories.put("Meats", new ArrayList<>());
		 categories.put("Dairy Product", new ArrayList<>());
		 categories.put("Beverages", new ArrayList<>());
		 categories.put("Condiments", new ArrayList<>());
		 categories.put("Consumable", new ArrayList<>());
	}
	
	 public void addItem(String category, FoodItem item) {
	        List<FoodItem> itemList = categories.get(category);
	        if (itemList != null) {
	            itemList.add(item);
	        } else {
	            System.out.println("Invalid category: " + category);
	        }
	    }
	 
	 public void printCategory(String category) {
		    List<FoodItem> itemList = categories.get(category);
		    if (itemList != null) {
		    	if (category != "Consumable")
		    		System.out.print("   " + category + "\n");
		    	else 
		    		System.out.print(category + "\n");
		        for (int i = 0; i < itemList.size(); i++) {
		                System.out.print("      "+bulletSymbol);
		            
		            System.out.print(itemList.get(i).getName() + "\n");
		        }
		        System.out.println();
		    } else {
		        System.out.println("Invalid category: " + category);
		    }
		}
	 
	 public  void printHierarchy(Category category) {
	        System.out.println("Food Category Hierarchy:");
	        System.out.println("Consumable");
	        printSubcategory(category, "Egg");
	        printSubcategory(category, "Rice");
	        printSubcategory(category, "Fruits");
	        printSubcategory(category, "Vegetables");
	        printSubcategory(category, "Meats");
	        printSubcategory(category, "Dairy Product");
	        printSubcategory(category, "Beverages");
	        printSubcategory(category, "Condiments");
	        System.out.println();
	    }

	    public static void printSubcategory(Category category, String subcategory) {
	        System.out.println(" " + Lsymbol + subcategory);
	        List<FoodItem> items = category.categories.get(subcategory);
	        int ctr = 0;
	        if (items != null) {
	            for (FoodItem item : items) {
	            		System.out.print(" "+Isymbol);
	                System.out.println("    "+ tsymbol + item.getName());
	            }	            
	        }
	    }
	}




public class FoodCategory {

	public static void main(String[] args) {

		Category category = new Category();
		
		category.addItem("Fruits", new FoodItem("Apple"));
		category.addItem("Fruits", new FoodItem("Banana"));
		category.addItem("Fruits", new FoodItem("Grapes"));
		category.addItem("Fruits", new FoodItem("Pineapple"));
		category.addItem("Fruits", new FoodItem("Cocoa"));
		
		category.addItem("Vegetables", new FoodItem("Spinach"));
		category.addItem("Vegetables", new FoodItem("Green Bean"));
		category.addItem("Vegetables", new FoodItem("Carrots"));
		category.addItem("Vegetables", new FoodItem("Lettuce"));
		category.addItem("Vegetables", new FoodItem("Tomato"));
		
		category.addItem("Meats", new FoodItem("Beef"));
		category.addItem("Meats", new FoodItem("Chicken"));
		category.addItem("Meats", new FoodItem("Pork"));
		category.addItem("Meats", new FoodItem("Poultry"));
		category.addItem("Meats", new FoodItem("Fish"));
		category.addItem("Meats", new FoodItem("Shrimp"));
		
		category.addItem("Dairy Product", new FoodItem("Butter"));
		category.addItem("Dairy Product", new FoodItem("Cheese"));
		category.addItem("Dairy Product", new FoodItem("Yoghurt"));
		category.addItem("Dairy Product", new FoodItem("Milk"));
		
		category.addItem("Beverages", new FoodItem("Milk"));
		category.addItem("Beverages", new FoodItem("Orange Juice"));
		category.addItem("Beverages", new FoodItem("Shake"));
		
		category.addItem("Condiments", new FoodItem("Soy Sauce"));
		category.addItem("Condiments", new FoodItem("Shrimp Paste"));
		category.addItem("Condiments", new FoodItem("Fish Sauce"));
		
		category.addItem("Consumable", new FoodItem("Egg"));
		category.addItem("Consumable", new FoodItem("Rice"));
		
		
		
		
		Scanner scan = new Scanner(System.in);
		int choice;
		System.out.println("Food Category");
		System.out.println("Do you want your list to be");
		do {
			System.out.print("1. Bullet Form\n" + "2. Tree Hierarchy\n" + "3. Exit\n" + "Enter Your Choice: ");
			
			choice = scan.nextInt();
			
			switch(choice) {
				case 1:
					System.out.println("FOOD");
					category.printCategory("Consumable");
					category.printCategory("Fruits");
					category.printCategory("Vegetables");
					category.printCategory("Meats");
					category.printCategory("Dairy Product");
					category.printCategory("Beverages");
					category.printCategory("Condiments");
					
					
					break;
					
				case 2:
					System.out.println("FOOD");
					category.printHierarchy(category);
					break;
					
				case 3:
					System.exit(0);
					break;
					
				default:
					System.err.println("Error! Try Again");
			}
		}while(choice < 3);
	}

}
