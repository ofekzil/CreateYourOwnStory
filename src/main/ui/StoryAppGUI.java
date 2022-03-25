package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;
import persistence.WriteStory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// represents a class to run a GUI representation of the app
public class StoryAppGUI extends JFrame {

    public static final String[] TEMPLATES = {"data/templates/CrazyMorningMale.txt",
            "data/templates/CrazyMorningFemale.txt", "data/templates/KingdomMale.txt",
            "data/templates/KingdomFemale.txt"};
    public static final String STORE = "data/story.json";

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
    private List<Prompt> promptsStory;

    // MODIFIES: this
    // EFFECTS: sets up GUI for initializing app
    public StoryAppGUI() {
        super("Story App");
        setMinimumSize(new Dimension(1050, 800));
        menu = new JComboBox(menuItems);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createBottomPanel();
        listPanel = new JPanel();
        barPanel = new JPanel();
        add(barPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
        setVisible(true);
        reader = new ReadStory(STORE);
        writer = new WriteStory(STORE);
        promptsToRemove = new ArrayList<>();
        promptsStory = new ArrayList<>();
        add(menu, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.PAGE_END);
        menu.addActionListener(e -> {
            int index = menu.getSelectedIndex();
            choose(index);
        });
    }

    // MODIFIES: this
    // EFFECTS: adds prompt to remove when save is selected
    public void addToRemove(Prompt p) {
        promptsToRemove.add(p);
    }

    // MODIFIES: this
    // EFFECTS: clears the list panel and clears promptsToRemove
    public void clearListPanelAndPromptsToRemove() {
        listPanel.removeAll();
        promptsToRemove.clear();
    }

    // MODIFIES: this
    // EFFECTS: sets border of list panel with given title
    public void setListPanelBorder(String title) {
        Border border = BorderFactory.createTitledBorder(title);
        listPanel.setBorder(border);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for active prompt, answer input, and buttons
    private void createBottomPanel() {
        bottomPanel = new JPanel();
        input = new JTextField();
        input.setPreferredSize(new Dimension(500, 35));
        input.setFont(new Font("Arial", Font.PLAIN, 17));
        input.setEditable(true);
        setButtons();
        activePrompt = new JLabel();
        activePrompt.setPreferredSize(new Dimension(240, 20));
        bottomPanel.add(activePrompt);
        bottomPanel.add(input);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(submit);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(update);
    }

    // MODIFIES: this
    // EFFECTS: sets buttons for app
    private void setButtons() {
        submit = new JButton("Submit Answer");
        update = new JButton("Update Answer");
        submit.setActionCommand("Submit Answer");
        update.setActionCommand("Update Answer");
    }

    // REQUIRES: order of menu items must include all templates first in the same order as in TEMPLATES,
    //           then load option then save option
    // MODIFIES: this
    // EFFECTS: chooses template/action based on user choice, and sets panels accordingly
    private void choose(int index) {
        try {
            if (0 <= index && index <= TEMPLATES.length - 1) {
                clearListPanelAndPromptsToRemove();
                setStoryAndPrompts(index);
                barPanel.removeAll();
                input.setEditable(true);
                new AnswerStoryGUI(this, story, promptsStory);
            } else if (index == TEMPLATES.length) {
                clearListPanelAndPromptsToRemove();
                setStoryAndPrompts(index);
                barPanel.removeAll();
                input.setEditable(true);
                new AnswerStoryGUI(this, story, promptsStory);
            } else {
                saveStory();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong with loading/saving your data",
                    "Save/Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // REQUIRES: 0 <= index <= TEMPLATES.length
    // MODIFIES: this
    // EFFECTS: sets story and prompts based off of index
    private void setStoryAndPrompts(int index) throws IOException {
        promptsStory.clear();
        if (index == TEMPLATES.length) {
            story = reader.read();
        } else {
            reader.readTemplateFile(TEMPLATES[index], true);
            story = reader.getStoryToApp();
        }
        promptsStory = story.getPrompts();
    }

    // MODIFIES: this
    // EFFECTS: saves story, prints a confirmation and terminates the app
    private void saveStory() throws IOException {
        if (story != null) {
            List<Prompt> currentPrompts = story.getPrompts();
            currentPrompts.removeAll(promptsToRemove);
            writer.write(story);
            JOptionPane.showMessageDialog(null, "Your story has been saved.\nPress OK to quit",
                    "Save & Quit", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save here.\nPress OK to quit",
                    "Save & Quit", JOptionPane.PLAIN_MESSAGE);
        }
        System.exit(0);
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



}
