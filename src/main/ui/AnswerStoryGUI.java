package ui;

import model.Answer;
import model.Prompt;
import model.Story;
import persistence.ReadStory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// represents a class to collect answers from user
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
        List<Prompt> prompts = this.story.getPrompts();
        collectAnswer(prompts);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for progress bar
    private void setBar() {
        if (story.getAnswers().isEmpty()) {
            progressBar = new JProgressBar(0, story.getPrompts().size());
        } else {
            int answersSize = story.getAnswers().size();
            progressBar = new JProgressBar(0, story.getPrompts().size() + answersSize);
            progressBar.setValue(answersSize);
        }
        JPanel panel = storyApp.getBarPanel();
        JLabel label = new JLabel("Progress:");
        panel.add(label);
        panel.add(Box.createHorizontalStrut(3));
        progressBar.setBorderPainted(true);
        progressBar.setMaximumSize(new Dimension(40, 30));
        progressBar.setVisible(true);
        progressBar.setStringPainted(true);
        panel.add(progressBar);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel for list of answers
    private void setList() {
        JPanel panel = storyApp.getListPanel();
        answersModel = new DefaultListModel<>();
        answers = new JList(answersModel);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.setBackground(storyApp.getBackground());
        panel.add(answers);
    }

    // MODIFIES: this
    // EFFECTS: if prompts are empty, proceed to display story w/ full progress bar; otherwise collects answer
    //          from user input and chooses whether to answer current prompt or update one;
    private void collectAnswer(List<Prompt> prompts) {
        if (!prompts.isEmpty()) {
            addCurrentAnswers();
            storyApp.setActivePrompt(prompts.get(0).getPrompt());
            JButton submit = storyApp.getSubmit();
            JButton update = storyApp.getUpdate();
            submit.setEnabled(true);
            update.setEnabled(true);
            submit.addActionListener(new SubmitListener(prompts));
            update.addActionListener(new UpdateListener());
        } else {
            int newMax = story.getAnswers().size();
            progressBar.setMaximum(newMax);
            progressBar.setValue(newMax);
            new DisplayStoryGUI(storyApp, story);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates progress bar after answer is added
    private void updateBar() {
        int current = progressBar.getValue();
        progressBar.setValue(current + 1);
    }

    private void addCurrentAnswers() {
        List<String> answersSoFar = story.getAnswers().stream().map(Answer::getAnswer).collect(Collectors.toList());
        for (String str : answersSoFar) {
            answersModel.addElement(str);
        }
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
        // EFFECTS: if index == prompts.size(), display full story; otherwise, collects answer from input when submit
        //          is pressed, adds it to story and updates all components accordingly
        @Override
        public void actionPerformed(ActionEvent e) {
            Prompt current = prompts.get(index);
            storyApp.setActivePrompt(current.getPrompt());
            storyApp.addToRemove(current);
            if (index < prompts.size()) {
                JTextField input = storyApp.getInput();
                index++;
                String answer = input.getText();
                answersModel.addElement(answer);
                story.addAnswer(answer);
                input.setText("");
                if (index == prompts.size()) {
                    updateBar();
                    prompts.removeAll(storyApp.getPromptsToRemove());
                    storyApp.getSubmit().removeActionListener(this);
                    storyApp.getUpdate().removeActionListener(this);
                    new DisplayStoryGUI(storyApp, story);
                } else {
                    Prompt next = prompts.get(index);
                    storyApp.setActivePrompt(next.getPrompt());
                    updateBar();
                }
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
            List<Answer> storyAnswers = story.getAnswers();
            if (index != -1) {
                answersModel.remove(index);
                answersModel.add(index, answer);
                storyAnswers.remove(index);
                storyAnswers.add(index, new Answer(answer));
                storyApp.getInput().setText("");
            }
        }
    }

}
