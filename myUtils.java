/*
Ayad Masud
CSCE 314
Spring 2025
*/

public class myUtils {
    public static void getArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java test -type anything-");
            return;
        }
        for (String word : args) {
            System.out.println(word);
        }
    }
}
