package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnswerStoryGUI implements ListSelectionListener {

    private StoryAppGUI storyApp;
    private JProgressBar progressBar;
    private JList answers;
    private DefaultListModel answersModel;

    private Story story;
    private List<Prompt> promptsToRemove;

    // EFFECTS: constructs an object to display GUI when answering questions
    public AnswerStoryGUI(StoryAppGUI storyApp, Story story) {
        this.storyApp = storyApp;
        promptsToRemove = new ArrayList<>();
        this.story = story;
      //  setStory();
        JPanel barPanel = setBar();
        JPanel listPanel = setList();
        this.storyApp.add(barPanel, BorderLayout.WEST);
        this.storyApp.add(listPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for progress bar
    private JPanel setBar() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Progress:");
        panel.add(label);
        panel.add(Box.createHorizontalStrut(3));
        progressBar = new JProgressBar(0, story.getPrompts().size());
        progressBar.setBorderPainted(true);
        progressBar.setMaximumSize(new Dimension(30, 20));
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: constructs a panel for list of answers
    private JPanel setList() {
        JPanel panel = new JPanel();
        answersModel = new DefaultListModel<>();
        List<Prompt> prompts = story.getPrompts();
        answers = new JList(answersModel);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.setBackground(storyApp.getBackground());
        panel.add(answers);
        collectAnswer(prompts);
        return panel;
    }
/*
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

 */

    // MODIFIES: this
    // EFFECTS: collects answer from user input and chooses whether to answer current prompt or update one
    private void collectAnswer(List<Prompt> prompts) {
        JButton submit = storyApp.getSubmit();
        JButton update = storyApp.getUpdate();
        Prompt initial = prompts.get(0);
        storyApp.setActivePrompt(initial.getPrompt());
        storyApp.addToRemove(initial);
        submit.addActionListener(new SubmitListener(prompts));
        update.addActionListener(new UpdateListener());
    }

    // MODIFIES: this
    // EFFECTS: updates progress bar after answer is added
    private void updateBar() {
        int current = progressBar.getValue();
        progressBar.setValue(current + 1);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    public static void main(String[] args) {
        ReadStory reader = new ReadStory(StoryApp.STORE);
        try {
            reader.readTemplateFile("data/testTemplate.txt", true);
            Story story = reader.getStoryToApp();
            new AnswerStoryGUI(new StoryAppGUI(), story);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SubmitListener implements ActionListener {

        private List<Prompt> prompts;
        private int index;

        // EFFECTS: constructs an action listener for submit button
        public SubmitListener(List<Prompt> prompts) {
            this.prompts = prompts;
            index = 0;
        }

        // MODIFIES: this
        // EFFECTS: collects answer from input when submit is pressed, and adds it to story accordingly
        // TODO: remove isEmpty condition. Instead add it to collectAnswer, and call makeStory then
        @Override
        public void actionPerformed(ActionEvent e) {
            if (index < prompts.size()) {
                index++;
                String answer = storyApp.getInput().getText();
                answersModel.addElement(answer);
                story.addAnswer(answer);
                storyApp.getInput().setText("");
                Prompt current = prompts.get(index);
                storyApp.setActivePrompt(current.getPrompt());
                storyApp.addToRemove(current);
                updateBar();
            }
        }
    }

    private class UpdateListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: updates selected answer to user input
        @Override
        public void actionPerformed(ActionEvent e) {
            String answer = storyApp.getInput().getText();
            int index = answers.getSelectedIndex();
            if (index != -1) {
                answersModel.remove(index);
                answersModel.add(index, answer);
                storyApp.getInput().setText("");
            }
        }
    }

}
