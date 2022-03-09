package ui;

import javax.swing.*;
import java.awt.*;

public class StoryAppGUI extends JFrame {

    private JComboBox menu;
    private String[] menuItems = {"New Crazy Morning (male)", "New Crazy Morning (female)", "New In The Kingdom (male)",
            "New In The Kingdom (female)", "Load Story", "Save & Quit"};
    private JTextField input;

    public StoryAppGUI() {
        super("Story App");
        menu = new JComboBox(menuItems);
        input = new JTextField();
        //input.setSize(20, 30);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(menu, BorderLayout.NORTH);
        add(input, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new StoryAppGUI();
    }

}
