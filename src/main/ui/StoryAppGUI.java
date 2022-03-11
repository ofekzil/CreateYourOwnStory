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

    public static final String[] TEMPLATES = {"data/templates/CrazyMorningMale.txt",
            "data/templates/CrazyMorningFemale.txt", "data/templates/KingdomMale.txt",
            "data/templates/KingdomFemale.txt"};

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
            int index = menu.getSelectedIndex();
            choose(index);
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
        input.setEditable(true);
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
    // TODO: add helpers to actually load templates (will probably be a call to new AnswerStoryGUI), which
    //  will set GUI for answering
    private void choose(int index) {
        JLabel label = new JLabel(); // only temporary to see selection works
        try {
            if (0 <= index && index <= 3) {
                label.setText(TEMPLATES[index] + " " + index);
                reader.readTemplateFile(TEMPLATES[index], true);
                story = reader.getStoryToApp();
                //new AnswerStoryGUI(story); // add a parameter of story to constructor
            } else if (index == 4) {
                label.setText(index + " load");
                story = reader.read();
                //new AnswerStoryGUI(story); // add a parameter of story to constructor
            } else {
                label.setText(index + " save");
                writer.write(story);
            }
            add(label);
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
