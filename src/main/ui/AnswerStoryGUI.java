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
        JPanel panel = new JPanel();
        storyApp = new StoryAppGUI();
        setStory();
        progressBar = new JProgressBar(0, story.getPrompts().size());
        progressBar.setBorderPainted(true);
        progressBar.setMaximumSize(new Dimension(30, 20));
        panel.add(progressBar);
        progressBar.setVisible(true);
        panel.add(Box.createHorizontalStrut(100));
        answersModel = new DefaultListModel<>();
        List<Prompt> prompts = story.getPrompts();
        answers = new JList(answersModel);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.addListSelectionListener(this);
        panel.add(answers);
        collectAnswer(prompts);
        storyApp.add(panel, BorderLayout.WEST);
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
    // EFFECTS: collects answer from user input and chooses whether to answer current prompt or update one
    private void collectAnswer(List<Prompt> prompts) {
        JButton submit = storyApp.getSubmit();
        JButton update = storyApp.getUpdate();
        storyApp.setActivePrompt(prompts.get(0).getPrompt());
        prompts.remove(0);
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
        new AnswerStoryGUI();
    }

    private class SubmitListener implements ActionListener {

        private List<Prompt> prompts;

        public SubmitListener(List<Prompt> prompts) {
            this.prompts = prompts;
        }

        // MODIFIES: this
        // EFFECTS: collects answer from input when submit is pressed, and adds it to story accordingly
        // TODO: remove isEmpty condition. Instead add it to collectAnswer, and call makeStory then
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!prompts.isEmpty()) {
                String answer = storyApp.getInput().getText();
                answersModel.addElement(answer);
                story.addAnswer(answer);
                storyApp.getInput().setText("");
                storyApp.setActivePrompt(prompts.get(0).getPrompt());
                prompts.remove(0);
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
