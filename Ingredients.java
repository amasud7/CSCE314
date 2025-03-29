import java.util.HashSet;
import java.util.Set;


public class Ingredients {
    private String name;
    private Set<Integer> recipeIds;

    public Ingredients(String name) {
        this.name = name;
        this.recipeIds = new HashSet<>();
    }

    // Add a recipe ID to the ingredient
    public void addRecipeId(int recipeId) {
        recipeIds.add(recipeId);
    }

    // Get all recipe IDs containing this ingredient
    public Set<Integer> getRecipeIds() {
        return recipeIds;
    }

    // Get the name of the ingredient
    public String getName() {
        return name;
    }

    // Set the name of the ingredient
    public void setName(String name) {
        this.name = name;
    }
}