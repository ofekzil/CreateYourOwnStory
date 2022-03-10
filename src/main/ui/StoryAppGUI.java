package ui;

import javax.swing.*;
import java.awt.*;

public class StoryAppGUI extends JFrame {

    private JComboBox menu;
    private String[] menuItems = {"New Crazy Morning (male)", "New Crazy Morning (female)", "New In The Kingdom (male)",
            "New In The Kingdom (female)", "Load Story", "Save & Quit"};
    private JTextField input;
    private JButton submit;
    private JButton update;
    private JLabel activePrompt;
    private JPanel panel;
    private String chosen;

    // MODIFIES: this
    // EFFECTS: sets up GUI for initializing app
    public StoryAppGUI() {
        super("Story App");
        setMinimumSize(new Dimension(800, 600));
        menu = new JComboBox(menuItems);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createPanel();
        add(menu, BorderLayout.NORTH);
        add(panel, BorderLayout.PAGE_END);
        menu.addActionListener(e -> { // will change to proper item if selected here, so can implement menu choices
            chosen = (String) menu.getSelectedItem();
            //activePrompt.setText(chosen);
        });
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for prompt, answer input, and buttons
    private void createPanel() {
        panel = new JPanel();
        input = new JTextField();
        input.setPreferredSize(new Dimension(500, 30));
        Font font = new Font("Arial", Font.PLAIN, 15);
        input.setFont(font);
        submit = new JButton("Submit");
        update = new JButton("Update");
        activePrompt = new JLabel("Prompt");
        panel.add(activePrompt);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(input);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(submit);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(update);
    }

    public static void main(String[] args) {
        new StoryAppGUI();
    }

}
