package ui;

import model.Story;
import persistence.WriteStory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// represents a class to display a complete story
public class DisplayStoryGUI {

    private StoryAppGUI storyApp;
    private JTextArea display;
    private Story story;
    private WriteStory writer;

    // REQUIRES: all prompts have been answered
    // EFFECTS: constructs a display for the complete story
    public DisplayStoryGUI(StoryAppGUI storyApp, Story story) {
        this.storyApp = storyApp;
        this.story = story;
        writer = this.storyApp.getWriter();
        setListPanelForDisplay();
        saveStory();
        disableAll();
        displayStory();
    }

    // MODIFIES: this
    // EFFECTS: clears list panel and sets new title
    private void setListPanelForDisplay() {
        storyApp.clearListPanelAndPromptsToRemove();
        storyApp.setListPanelBorder("The Full Story");
    }

    // MODIFIES: this
    // EFFECTS: disables all editable panels
    private void disableAll() {
        storyApp.getInput().setEditable(false);
        storyApp.getSubmit().setEnabled(false);
        storyApp.getUpdate().setEnabled(false);
        storyApp.setActivePrompt("");
    }

    // MODIFIES: this
    // EFFECTS: displays the full story
    private void displayStory() {
        story.setAnswersInOrder();
        String complete = story.createStory();
        JPanel panel = storyApp.getListPanel();
        display = new JTextArea();
        display.setPreferredSize(new Dimension(600, 800));
        display.setText(complete);
        display.setFont(new Font("Arial", Font.PLAIN, 17));
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setVisible(true);
        panel.add(display);
        storyApp.validate();
        storyApp.repaint();
    }

    // MODIFIES: this
    // EFFECTS: saves complete story to file
    private void saveStory() {
        try {
            writer.write(story);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong with loading/saving your data",
                    "Save/Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
