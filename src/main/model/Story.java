package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// represents a story with a basic skeleton (text not controlled by the user),
// a list of prompts (the questions that correspond to the story),
// a list of integers representing the locations of each answer in the story
// and an empty list to be filled with answers
public class Story {

    private List<String> skeleton;
    private List<Prompt> prompts;
    private List<Integer> locations;
    private List<Answer> answers;

    // REQUIRES: no duplicate prompts AND locations.size + 1 = skeleton.size
    // EFFECTS: constructs a Story with a skeleton, prompts, locations and an empty list of answers
    public Story(List<String> skeleton, List<Prompt> prompts, List<Integer> locations) {
        this.skeleton = skeleton;
        this.prompts = prompts;
        this.locations = locations;
        answers = new ArrayList<>();
    }

    // MODIFIES: this, str
    // EFFECTS: turns given string into answer and adds to end of answers
    public void addAnswer(String str) {
        Answer a = new Answer(str);
        answers.add(a);
    }

    // REQUIRES: all prompts have been answered
    // MODIFIES: this
    // EFFECTS: sets the answers in the appropriate order according to locations
    public void setAnswersInOrder() {
        int originalSize = answers.size();
        List<Answer> inOrder = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            int location = locations.get(i);
            if (i != location) {
                if (i >= originalSize) {
                    inOrder.add(answers.get(location));
                } else {
                    inOrder.add(i, answers.get(location));
                }
            } else {
                inOrder.add(answers.get(i));
            }
        }
        this.answers = inOrder;
    }

    // REQUIRES: all prompts have been answered AND are in proper order according to locations
    // AND answers.size + 1 = skeleton.size AND no list is empty
    // EFFECTS: appends story skeleton and prompt answers in appropriate order,
    // producing the full story
    public String createStory() {
        String complete = "";
        for (int i = 0; i < skeleton.size(); i++) {
            if (i >= answers.size()) {
                complete += skeleton.get(i);
            } else {
                complete += skeleton.get(i) + answers.get(i).getAnswer();
            }
        }
        return complete;
    }

    // REQUIRES: str is a COMPLETE story
    // EFFECTS: adds line breaks to a complete story
    public String breakLines(String str) {
        String broken = "";
        for (int i = 0; i < str.length(); i += 50) {
            if (i >= str.length() - 50) {
                broken += str.substring(i);
            } else {
                if (str.charAt(i + 50) == ' ') { //|| str.charAt(i + 50) == ',' || str.charAt(i + 50) == '.'
                    broken += str.substring(i, i + 50) + "\n";
                } else {
                    broken += str.substring(i, i + 50) + "-\n";
                }
            }
        }
        return broken;
    }

    // EFFECTS: returns a JSON representation of story w/ given name
    public JSONObject toJson(String name) {
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("prompts", promptsToJson());
        jo.put("answers", answersToJson());
        return jo;
    }

    // EFFECTS: turns prompts to JSONArray
    private JSONArray promptsToJson() {
        JSONArray ja = new JSONArray();
        for (Prompt p : prompts) {
            JSONObject j = new JSONObject();
            j.put("prompt", p.getPrompt());
            ja.put(j);
        }
        return ja;
    }

    // EFFECTS: turns answers to JSONArray
    private JSONArray answersToJson() {
        JSONArray ja = new JSONArray();
        for (Answer a : answers) {
            JSONObject j = new JSONObject();
            j.put("answer", a.getAnswer());
            ja.put(j);
        }
        return ja;
    }

    // getters

    public List<String> getSkeleton() {
        return skeleton;
    }

    public List<Prompt> getPrompts() {
        return prompts;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    // setters

    public void setPrompts(List<Prompt> prompts) {
        this.prompts = prompts;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
