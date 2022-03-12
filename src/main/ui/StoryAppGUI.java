package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;
import persistence.WriteStory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// represents a class to run a GUI representation of the app
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
    private JPanel bottomPanel;
    private JPanel listPanel;
    private JPanel barPanel;

    private ReadStory reader;
    private WriteStory writer;
    private Story story;
    private List<Prompt> promptsToRemove;

    // MODIFIES: this
    // EFFECTS: sets up GUI for initializing app
    public StoryAppGUI() {
        super("Story App");
        setMinimumSize(new Dimension(1000, 800));
        menu = new JComboBox(menuItems);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createBottomPanel();
        add(menu, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.PAGE_END);
        menu.addActionListener(e -> {
            int index = menu.getSelectedIndex();
            choose(index);
        });
        listPanel = new JPanel();
        barPanel = new JPanel();
        add(barPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
        setVisible(true);

        reader = new ReadStory(StoryApp.STORE);
        writer = new WriteStory(StoryApp.STORE);
        promptsToRemove = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds prompt to remove when save is selected
    public void addToRemove(Prompt p) {
        promptsToRemove.add(p);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for active prompt, answer input, and buttons
    private void createBottomPanel() {
        bottomPanel = new JPanel();
        input = new JTextField();
        input.setPreferredSize(new Dimension(500, 30));
        input.setFont(new Font("Arial", Font.PLAIN, 15));
        input.setEditable(true);
        setButtons();
        activePrompt = new JLabel();
        bottomPanel.add(activePrompt);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(input);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(submit);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(update);
    }

    // MODIFIES: this
    // EFFECTS: sets buttons for app
    private void setButtons() {
        submit = new JButton("Submit");
        update = new JButton("Update");
        submit.setActionCommand("Submit");
        update.setActionCommand("Update");
    }

    // MODIFIES: this
    // EFFECTS: chooses template/action based on user choice
    private void choose(int index) {
        try {
            if (0 <= index && index <= 3) {
                clearTopPanelsAndPrompts();
                input.setEditable(true);
                reader.readTemplateFile(TEMPLATES[index], true);
                story = reader.getStoryToApp();
                new AnswerStoryGUI(this, story);
            } else if (index == 4) {
                clearTopPanelsAndPrompts();
                input.setEditable(true);
                story = reader.read();
                new AnswerStoryGUI(this, story);
            } else { // TODO: add a message that says story has been saved and exit the app
                List<Prompt> currentPrompts = story.getPrompts();
                currentPrompts.removeAll(promptsToRemove);
                writer.write(story);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the bar and list panels
    public void clearTopPanelsAndPrompts() {
        barPanel.removeAll();
        listPanel.removeAll();
        promptsToRemove.clear();
    }

    // getters

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

    public JPanel getBarPanel() {
        return barPanel;
    }

    public JPanel getListPanel() {
        return listPanel;
    }

    public WriteStory getWriter() {
        return writer;
    }

    public List<Prompt> getPromptsToRemove() {
        return promptsToRemove;
    }

    // setters

    public void setActivePrompt(String str) {
        activePrompt.setText(str);
    }

    public static void main(String[] args) {
        new StoryAppGUI();
    }



}
