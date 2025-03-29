import java.util.Set;

public class Recipe {
    private String name;
    private String url;
    private String description;
    private String author;
    private Set<String> ingredients;
    private String method;
    private int id;
    static int countRecipe;

    public Recipe(String name, String url, String description, String author, Set<String> ingredients, String method) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.author = author;
        this.ingredients = ingredients;
        this.method = method;
        this.id = ++countRecipe; // use count recipe as id
        countRecipe++;
    }

    // constructor with id
    public Recipe(String name, String url, String description, String author, Set<String> ingredients, String method, int id) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.author = author;
        this.ingredients = ingredients;
        this.method = method;
        this.id = id;
        countRecipe++;
    }

    // getters
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public String getMethod() {
        return method;
    }

    public int getId() {
        return id;
    }

    public static int getCountRecipe() {
        return countRecipe;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setId(int id) {
        this.id = id;
    }
}
