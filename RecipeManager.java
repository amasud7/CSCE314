/*
Ayad Masud
CSCE 314
Spring 2025
Homework 5
*/
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeManager {
    private List<Recipe> recipes;

    public RecipeManager() {
        this.recipes = new ArrayList<>();
    }

    // Loads recipes from a JSON file using custom parsing
    public boolean loadRecipesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            
            // Read entire file into string
            while ((line = br.readLine()) != null) {
                jsonContent.append(line.trim()).append(" ");
            }
            
            // Extract JSON array content
            String json = jsonContent.toString().trim();
            if (json.startsWith("//")) { // Remove comment if present
                json = json.substring(json.indexOf("["));
            }
            json = json.substring(json.indexOf("[") + 1, json.lastIndexOf("]")).trim();
            
            // Split into individual recipe records
            String[] records = json.split("},");
            
            for (String record : records) {
                // Clean up the record string
                if (record.endsWith("}")) {
                    record = record.substring(0, record.length() - 1);
                }
                record = record.replace("{", "").trim();
                
                // Parse recipe attributes
                String name = "", url = "", description = "", author = "", method = "";
                Set<String> ingredients = new HashSet<>();
                
                // Split into key-value pairs
                String[] parts = splitJsonParts(record);
                
                for (String part : parts) {
                    String[] keyValue = part.split(":", 2);
                    if (keyValue.length < 2) continue;
                    
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim();
                    
                    // Handle string values
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    } 
                    // Handle array values
                    else if (value.startsWith("[") && value.endsWith("]")) {
                        value = value.substring(1, value.length() - 1);
                        if (key.equals("Ingredients")) {
                            String[] items = splitArrayItems(value);
                            for (String item : items) {
                                item = item.trim();
                                if (item.startsWith("\"") && item.endsWith("\"")) {
                                    item = item.substring(1, item.length() - 1);
                                }
                                ingredients.add(item);
                            }
                            continue;
                        } else if (key.equals("Method")) {
                            String[] steps = splitArrayItems(value);
                            StringBuilder methodBuilder = new StringBuilder();
                            for (String step : steps) {
                                step = step.trim();
                                if (step.startsWith("\"") && step.endsWith("\"")) {
                                    step = step.substring(1, step.length() - 1);
                                }
                                methodBuilder.append(step).append("\n");
                            }
                            method = methodBuilder.toString().trim();
                            continue;
                        }
                    }
                    
                    // Assign values to corresponding attributes
                    switch (key) {
                        case "Name": name = value; break;
                        case "url": url = value; break;
                        case "Description": description = value; break;
                        case "Author": author = value; break;
                    }
                }
                
                // Create and add the recipe
                Recipe recipe = new Recipe(name, url, description, author, ingredients, method);
                recipes.add(recipe);
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error loading recipes: " + e.getMessage());
            return false;
        }
    }
    
    // Helper method to split JSON parts while respecting nested structures
    private String[] splitJsonParts(String json) {
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        int bracketDepth = 0;
        boolean inQuotes = false;
        
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '"' && (i == 0 || json.charAt(i-1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '[' || c == '{') {
                    bracketDepth++;
                } else if (c == ']' || c == '}') {
                    bracketDepth--;
                } else if (c == ',' && bracketDepth == 0) {
                    parts.add(currentPart.toString().trim());
                    currentPart = new StringBuilder();
                    continue;
                }
            }
            
            currentPart.append(c);
        }
        
        if (currentPart.length() > 0) {
            parts.add(currentPart.toString().trim());
        }
        
        return parts.toArray(new String[0]);
    }
    
    // Helper method to split array items while respecting quoted strings
    private String[] splitArrayItems(String array) {
        List<String> items = new ArrayList<>();
        StringBuilder currentItem = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);
            
            if (c == '"' && (i == 0 || array.charAt(i-1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                items.add(currentItem.toString().trim());
                currentItem = new StringBuilder();
                continue;
            }
            
            currentItem.append(c);
        }
        
        if (currentItem.length() > 0) {
            items.add(currentItem.toString().trim());
        }
        
        return items.toArray(new String[0]);
    }
    
    // Search recipes by ingredient
    public List<Recipe> searchByIngredient(String ingredient) {
        String searchTerm = ingredient.toLowerCase();
        return recipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .anyMatch(ing -> ing.toLowerCase().contains(searchTerm)))
                .collect(Collectors.toList());
    }

    // Search recipes by author    
    public List<Recipe> searchByAuthor(String author) {
        String searchTerm = author.toLowerCase();
        return recipes.stream()
                .filter(recipe -> recipe.getAuthor().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    // Search recipes by keyword in name
    public List<Recipe> searchByName(String keyword) {
        String searchTerm = keyword.toLowerCase();
        return recipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    // Search recipes by cooking method 
    public List<Recipe> searchByCookingMethod(String method) {
        String searchTerm = method.toLowerCase();
        return recipes.stream()
                .filter(recipe -> recipe.getMethod().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    // Search for a recipe by ID
    public Recipe searchById(int id) {
        return recipes.stream()
                .filter(recipe -> recipe.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Print a recipe in a formatted view
    public void printRecipe(Recipe recipe) {
        if (recipe == null) {
            System.out.println("Recipe not found");
            return;
        }

        System.out.println("=".repeat(80));
        System.out.println("RECIPE #" + recipe.getId() + ": " + recipe.getName());
        System.out.println("=".repeat(80));
        System.out.println("By: " + recipe.getAuthor());
        System.out.println("URL: " + recipe.getUrl());
        System.out.println("-".repeat(80));
        
        System.out.println("DESCRIPTION:");
        System.out.println(recipe.getDescription());
        System.out.println("-".repeat(80));
        
        System.out.println("INGREDIENTS:");
        for (String ingredient : recipe.getIngredients()) {
            System.out.println("• " + ingredient);
        }
        System.out.println("-".repeat(80));
        
        System.out.println("METHOD:");
        String[] steps = recipe.getMethod().split("\n");
        for (int i = 0; i < steps.length; i++) {
            System.out.println((i + 1) + ". " + steps[i]);
        }
        System.out.println("=".repeat(80));
    }

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipes);
    }

    // main method
    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();
        
        // Load recipes from file
        if (manager.loadRecipesFromFile("recipes.json")) {
            System.out.println("Successfully loaded " + manager.getAllRecipes().size() + " recipes");
            
            boolean keepRunning = true;
            while (keepRunning) {
                // check if user wants to search for a recipe
                System.out.println("\nSelect an option: ");
                System.out.println("1. Search by ingredient");
                System.out.println("2. Search by author");
                System.out.println("3. Search by name");
                System.out.println("4. Search by cooking method");
                System.out.println("5. Print specific recipe details by ID");
                System.out.println("6. Print all recipes");
                System.out.println("7: Summary");
                System.out.println("8. Exit");
                System.out.println();
                System.out.print("Enter your choice: ");
                System.out.println("\n");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        System.out.print("\nEnter ingredient: ");
                        String ingredient = scanner.nextLine();
                        List<Recipe> ingredientRecipes = manager.searchByIngredient(ingredient);
                        if (ingredientRecipes.isEmpty()) {
                            System.out.println("No recipes found with the ingredient: " + ingredient + "\n");
                        } else {
                            for (Recipe r : ingredientRecipes) {
                                System.out.println("- " + r.getName() + " (ID: " + r.getId() + ")");
                            }
                        }
                        break;
                    case 2:
                        System.out.print("\nEnter author: ");
                        String author = scanner.nextLine();
                        List<Recipe> authorRecipes = manager.searchByAuthor(author);
                        if (authorRecipes.isEmpty()) {
                            System.out.println("No recipes found for the author: " + author + "\n");
                        } else {
                            for (Recipe r : authorRecipes) {
                                System.out.println("- " + r.getName() + " (ID: " + r.getId() + ")");
                            }
                        }
                        break;
                    case 3:
                        System.out.print("\nEnter name keyword: ");
                        String nameKeyword = scanner.nextLine();
                        List<Recipe> nameRecipes = manager.searchByName(nameKeyword);
                        for (Recipe r : nameRecipes) {
                            System.out.println("- " + r.getName() + " (ID: " + r.getId() + ")");
                        }
                        break;
                    case 4:
                        System.out.print("\nEnter cooking method keyword: ");
                        String methodKeyword = scanner.nextLine();
                        List<Recipe> methodRecipes = manager.searchByCookingMethod(methodKeyword);
                        for (Recipe r : methodRecipes) {
                            System.out.println("- " + r.getName() + " (ID: " + r.getId() + ")");
                        }
                        break;
                    case 5:
                        System.out.print("\nEnter recipe ID: ");
                        int id = scanner.nextInt();
                        Recipe recipe = manager.searchById(id);
                        manager.printRecipe(recipe);
                        break;
                    case 6:
                        List<Recipe> allRecipes = manager.getAllRecipes();
                        for (Recipe r : allRecipes) {
                            System.out.println("- " + r.getName() + " (ID: " + r.getId() + ")");
                        }
                        break;
                    case 7:
                        // how many recipes there are
                        System.out.println("\nTotal number of unique recipes: " + manager.getAllRecipes().size());
                        // how many authors there are
                        Set<String> authors = new HashSet<>();
                        for (Recipe r : manager.getAllRecipes()) {
                            authors.add(r.getAuthor());
                        }
                        System.out.println("\nTotal number of unique authors: " + authors.size());
                        // how many ingredients there are
                        Set<String> ingredients = new HashSet<>();
                        for (Recipe r : manager.getAllRecipes()) {
                            ingredients.addAll(r.getIngredients());
                        }
                        System.out.println("\nTotal number of unique ingredients: " + ingredients.size());
                        break;
                    case 8:
                        keepRunning = false;
                        continue;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
        } else {
            System.out.println("Failed to load recipes");
        }
    }
}