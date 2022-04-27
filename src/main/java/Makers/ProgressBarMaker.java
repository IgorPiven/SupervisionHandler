package Makers;

import javax.swing.*;
import java.awt.*;


public class ProgressBarMaker extends JFrame {

    public final JProgressBar pb = new JProgressBar();
    public JFrame frame;


    public ProgressBarMaker(JFrame frame) {
        this.frame = frame;
    }


    public void prepareBar(int maxValue) {


        // creates progress bar
        pb.setMinimum(0);
        pb.setMaximum(maxValue);
        pb.setStringPainted(true);

        // add progress bar
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(pb);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }

    public void launchBar(int inputValue) {

        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    pb.setValue(inputValue);
                }
            });
            java.lang.Thread.sleep(1);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }

    }

    public void closeBar() {

        frame.setVisible(false);
        frame.dispose();

    }

}

