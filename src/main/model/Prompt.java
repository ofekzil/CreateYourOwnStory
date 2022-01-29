package model;

// represents a prompt for a specific part of a story, to be turned into an answer
public class Prompt {

    private String prompt;

    // EFFECTS: constructs a prompt with given string
    public Prompt(String prompt) {
        this.prompt = prompt;
    }

    // MODIFIES: this
    // EFFECTS: replaces the prompt with the appropriate answer
    public void changePromptToAnswer(String answer) {
        prompt = answer;
    }


    // getters

    public String getPrompt() {
        return prompt;
    }
}
