package ui;

import model.Prompt;
import model.Story;
import persistence.ReadStory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class AnswerStoryGUI implements ListSelectionListener {

    private StoryAppGUI storyApp;
    private JProgressBar progressBar;
    private JList answers;
    private DefaultListModel answersModel;
    private String currentPrompt;

    private Story story;

    // EFFECTS: constructs an object to display GUI when answering questions
    public AnswerStoryGUI() {
        storyApp = new StoryAppGUI();
        setStory();
        answersModel = new DefaultListModel<>();
        List<Prompt> prompts = story.getPrompts();
        answers = new JList(answersModel);
        collectAnswer(prompts);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.addListSelectionListener(this);
        storyApp.add(answers);
/*
        progressBar = new JProgressBar(0, story.getPrompts().size());
        progressBar.setBorderPainted(true);
        progressBar.setValue(story.getPrompts().size() / 2);
        progressBar.setPreferredSize(new Dimension(2, 30));
        storyApp.add(progressBar);
 */

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
    // EFFECTS:
    private void collectAnswer(List<Prompt> prompts) {
        JButton submit = storyApp.getSubmit();
        JButton update = storyApp.getUpdate();
        storyApp.setActivePrompt(prompts.get(0).getPrompt());
        prompts.remove(0);
        submit.addActionListener(new SubmitListener(prompts));
        update.addActionListener(new UpdateListener());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    public static void main(String[] args) {
        new AnswerStoryGUI();
    }

    private class SubmitListener implements ActionListener {

        private List<Prompt> prompts;

        public SubmitListener(List<Prompt> prompts) {
            this.prompts = prompts;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!prompts.isEmpty()) {
                String answer = storyApp.getInput().getText();
                answersModel.addElement(answer);
                storyApp.getInput().setText("");
                storyApp.setActivePrompt(prompts.get(0).getPrompt());
                prompts.remove(0);
            }
        }
    }

    private class UpdateListener implements ActionListener {

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
