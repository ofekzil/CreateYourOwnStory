package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class AnswerStoryGUI {

    private StoryAppGUI storyApp;
    private JProgressBar progressBar;
    private JList answers;
    private DefaultListModel answersModel;

    private Story story;

    // EFFECTS: constructs an object to display GUI when answering questions
    public AnswerStoryGUI(StoryAppGUI storyApp, Story story) {
        this.storyApp = storyApp;
        this.story = story;
        setBar();
        setList();
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for progress bar
    private void setBar() {
        JPanel panel = storyApp.getBarPanel();
        JLabel label = new JLabel("Progress:");
        panel.add(label);
        panel.add(Box.createHorizontalStrut(3));
        progressBar = new JProgressBar(0, story.getPrompts().size());
        progressBar.setBorderPainted(true);
        progressBar.setMaximumSize(new Dimension(30, 20));
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
    }

    // MODIFIES: this
    // EFFECTS: constructs a panel for list of answers
    private void setList() {
        JPanel panel = storyApp.getListPanel();
        answersModel = new DefaultListModel<>();
        List<Prompt> prompts = story.getPrompts();
        answers = new JList(answersModel);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.setBackground(storyApp.getBackground());
        panel.add(answers);
        collectAnswer(prompts);
    }

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
        // TODO: add some condition that will call  new DisplayStoryGUI when all has been answered
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
