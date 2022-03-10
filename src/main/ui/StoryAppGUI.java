package ui;

import model.Story;
import persistence.ReadStory;
import persistence.WriteStory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

    private ReadStory reader;
    private WriteStory writer;
    private Story story;

    // MODIFIES: this
    // EFFECTS: sets up GUI for initializing app
    public StoryAppGUI() {
        super("Story App");
        setMinimumSize(new Dimension(1000, 800));
        menu = new JComboBox(menuItems);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createPanel();
        add(menu, BorderLayout.NORTH);
        add(panel, BorderLayout.PAGE_END);
        menu.addActionListener(e -> { // will change to proper item if selected here, so can implement menu choices
            chosen = (String) menu.getSelectedItem();
          //  choose();
            //activePrompt.setText(chosen);
        });
        setVisible(true);

        reader = new ReadStory(StoryApp.STORE);
        writer = new WriteStory(StoryApp.STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for prompt, answer input, and buttons
    private void createPanel() {
        panel = new JPanel();
        input = new JTextField();
        input.setPreferredSize(new Dimension(500, 30));
        Font font = new Font("Arial", Font.PLAIN, 15);
        input.setFont(font);
        setButtons();
        activePrompt = new JLabel("Prompt");
        panel.add(activePrompt);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(input);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(submit);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(update);
    }

    private void setButtons() {
        submit = new JButton("Submit");
        update = new JButton("Update");
        submit.setActionCommand("Submit");
        update.setActionCommand("Update");
    }

    // MODIFIES: this
    // EFFECTS: chooses template/action based on user choice
    // TODO: need to shorten method, make sure templates are gettable (maybe make a constant here),
    //  and add helpers to actually load templates
    private void choose() {
        try {
            switch (chosen) {
                case "New Crazy Morning (male)":
                    reader.readTemplateFile(StoryApp.TEMPLATES.get(0), true);
                    break;
                case "New Crazy Morning (female)":
                    reader.readTemplateFile(StoryApp.TEMPLATES.get(2), true);
                    break;
                case "New In The Kingdom (male)":
                    reader.readTemplateFile(StoryApp.TEMPLATES.get(1), true);
                    break;
                case "New In The Kingdom (female)":
                    reader.readTemplateFile(StoryApp.TEMPLATES.get(3), true);
                    break;
                case "Load Story":
                    story = reader.read();
                    break;
                default:
                    writer.write(story); //placeholder for now
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getUpdate() {
        return update;
    }

    public JTextField getInput() {
        return input;
    }

    public JLabel getActivePrompt() {
        return activePrompt;
    }

    public void setActivePrompt(String str) {
        activePrompt.setText(str);
    }

    public static void main(String[] args) {
        new StoryAppGUI();
    }



}
