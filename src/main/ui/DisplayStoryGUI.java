package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayStoryGUI {

    private StoryAppGUI storyApp;
    private JTextArea display;

    private Story story;

    public DisplayStoryGUI() {
        storyApp = new StoryAppGUI();
        setStory();
        answerStory();
        String complete = story.createStory();
        storyApp.getInput().setEditable(false);
        storyApp.getSubmit().setEnabled(false);
        storyApp.getUpdate().setEnabled(false);
        display = new JTextArea();
        display.setText(complete);
        display.setFont(new Font("Arial", Font.PLAIN, 15));
        display.setEditable(false);
        display.setLineWrap(true);
        display.setWrapStyleWord(true);
        display.setVisible(true);
        //storyApp.add(Box.createVerticalStrut(20));
        storyApp.add(display);
    }

    // MODIFIES: this
    // EFFECTS: assigns value to story (temporary)
    private void setStory() {
        ReadStory reader = new ReadStory(StoryApp.STORE);
        try {
            reader.readTemplateFile("data/testTemplate.txt", true);
            story = reader.getStoryToApp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // MODIFIES: this
    // EFFECTS: answers the story's prompts
    private void answerStory() {
        int i = 1;
        for (Prompt p : story.getPrompts()) {
            story.addAnswer("answer #                     " + i);
            i++;
        }
    }


    public static void main(String[] args) {
        new DisplayStoryGUI();
    }
}
