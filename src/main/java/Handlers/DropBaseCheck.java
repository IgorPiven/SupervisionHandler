package Handlers;


import javax.swing.*;

public class DropBaseCheck {

    public int showMessage() {

        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to clear existing database?", "Select an option", JOptionPane.YES_NO_CANCEL_OPTION);

        return input;

    }
}
