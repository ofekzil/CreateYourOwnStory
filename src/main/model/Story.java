package model;

import java.util.ArrayList;
import java.util.List;

// represents a story with a basic skeleton (text not controlled by the user),
// a list of prompts (the questions and later answers that correspond to the story)
// and a list of integers representing the locations of each prompt in the story
public class Story {

    private List<String> skeleton;
    private List<Prompt> prompts;
    private List<Integer> locations;

    // REQUIRES: no duplicate prompts AND locations.size + 1 = skeleton.size
    // EFFECTS: constructs a Story with a skeleton, prompts, and locations
    public Story(List<String> skeleton, List<Prompt> prompts, List<Integer> locations) {
        this.skeleton = skeleton;
        this.prompts = prompts;
        this.locations = locations;
    }

    // REQUIRES: all prompts have been turned into answers
    // MODIFIES: this
    // EFFECTS: sets the answers in the appropriate order according to locations
    public void setPromptsInOrder() {
        int originalSize = prompts.size();
        List<Prompt> answers = new ArrayList<Prompt>();
        for (int i = 0; i < locations.size(); i++) {
            int location = locations.get(i);
            if (i != location) {
                if (i >= originalSize) {
                    answers.add(prompts.get(location));
                } else {
                    answers.add(i, prompts.get(location));
                }
            } else {
                answers.add(prompts.get(i));
            }
        }
        prompts = answers;
    }

    // REQUIRES: all prompts have been turned into answers
    // AND prompts.size + 1 = skeleton.size AND no list is empty
    // EFFECTS: appends story skeleton and prompt answers in appropriate order,
    // producing the full story
    public String createStory() {
        String complete = "";
        for (int i = 0; i < skeleton.size(); i++) {
            if (i >= prompts.size()) {
                complete += skeleton.get(i);
            } else {
                complete += skeleton.get(i) + prompts.get(i).getPrompt();
            }
        }
        return complete;
    }

    // REQUIRES: str is a COMPLETE story
    // MODIFIES: str
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
}
