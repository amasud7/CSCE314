/*
 * Ayad Masud
 * 4/9/25
 */
import javax.swing.*;
import java.awt.event.*;

public class multiClicker extends JFrame{
    private int count = 0;
    private JLabel label;
    private JButton clickButton;
    private JButton exitButton;

    public multiClicker(String title) {
        super(title);

        // create count label
        label = new JLabel("Clicks: " + count);

        // create click button
        clickButton = new JButton("Click");
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                label.setText("Clicks: " + count);
            }
        });

        // create exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // exit the program
                dispose(); // close the window
            }
        });

        // layout components
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(clickButton);
        panel.add(exitButton);

        // set up the frame
        add(panel);
        setSize(350, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main (String[] args) {
        // number of counter to launch
        final int NUM_OF_INSTANCES = 4;

        // launch guis
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < NUM_OF_INSTANCES; i++) {

                    multiClicker clicker = new multiClicker("Cookie Clicker " + (i + 1));
                    clicker.setVisible(true);
                }
            }
        });
    }

}
