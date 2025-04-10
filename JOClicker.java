/*
 * Ayad Masud
 * 4/9/25
 * 733009045
 */
import javax.swing.JOptionPane;

public class JOClicker {
    public static void main (String[] args) {
        int count = 0;
        // loop until user exits
        while (true) {
            String message = "Cookie Clicker\nClicks: " + count;
            int option = JOptionPane.showOptionDialog(null, message, "Cookie Clicker", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Click", "Exit"}, "Click");

            if (option == JOptionPane.YES_NO_OPTION) {
                // user clicked click
                count++;
            }
            else {
                // user clicked exit
                break;
            }
        }
        // exit the program
        System.exit(0);
    }
}
