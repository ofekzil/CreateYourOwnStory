package model;

// represents an answer for a specific prompt
public class Answer {

    private String answer;

    // EFFECTS: constructs an answer from given string
    public Answer(String answer) {
        this.answer = answer;
    }

    // EFFECTS: returns the answer
    public String getAnswer() {
        return answer;
    }
}
