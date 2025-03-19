import java.util.ArrayList;

public class test2 {
    public static void main (String[] args) {
        myUtils.getArgs(args);
        myPeeps mom = new myPeeps("Susie");
        myPeeps goodSon = new myPeeps("Sam");
        myPeeps otherSon = new myPeeps();
        otherSon.setName("Ben");

        System.out.printf("I have %d peeps.\n", myPeeps.getQuantity());
        System.out.printf("The first one is mom, her name is %s.\n", mom.getName()); // %s is lowercase
        System.out.printf("The second one is good son, his name is %S.\n", goodSon.getName()); // %S is for uppercase
        System.out.println("My other son is " + otherSon.getName() + "." + "\nHe is as good as " + goodSon.getName() + ".");

        ArrayList<myPeeps> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new myPeeps("number " + i));
        }

        System.out.printf("I have %d students.\n", myPeeps.getQuantity());
    }

}
