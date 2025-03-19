public class myPeeps {
    String name;
    static int quantity; // static means there is only one instance of quantity for class --> all objects share the same quantity

    public myPeeps() { // default constructor
        quantity++;
    }

    public myPeeps(String name) { // constructor with parameter
        this.name = name;
        quantity++;
    }

    public void setName(String name) { // set name function
        this.name = name;
    }

    public String getName() { // get name
        return name;
    }

    public static int getQuantity() { // get quantity
        return quantity;
    }

}
