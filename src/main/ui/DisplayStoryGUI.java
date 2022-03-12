package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;
import persistence.WriteStory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
        saveStory();
        this.storyApp.clearTopPanelsAndPrompts();
        disableAll();
        displayStory();
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
        display.setPreferredSize(new Dimension(800, 800));
        display.setText(complete);
        display.setFont(new Font("Arial", Font.PLAIN, 15));
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setVisible(true);
        panel.add(display);
        storyApp.validate();
        storyApp.repaint();
    }

    private void saveStory() {
        try {
            writer.write(story);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadStory reader = new ReadStory(StoryApp.STORE);
        try {
            reader.readTemplateFile("data/testTemplate.txt", true);
            Story story = reader.getStoryToApp();
            int i = 1;
            for (Prompt p : story.getPrompts()) {
                story.addAnswer("answer #                     " + i);
                i++;
            }
            new DisplayStoryGUI(new StoryAppGUI(), story);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
