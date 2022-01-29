package model;

import java.util.List;

// represents a story with a basic skeleton (text not controlled by the user),
// a list of prompts (the questions and later answers that correspond to the story)
// and a list of integers representing the locations of each prompt in the story
public class Story {

    // REQUIRES: no duplicate prompts
    // EFFECTS: constructs a Story with a skeleton, prompts, and locations
    public Story(List<String> skeleton, List<Prompt> prompts, List<Integer> locations){}

    // REQUIRES: all prompts have been turned into answers
    // MODIFIES: this
    // EFFECTS: sets the answers in the appropriate order according to locations
    public void setPromptsInOrder() {}

    // REQUIRES: all prompts have been turned into answers
    // AND prompts.size + 1 = skeleton.size AND no list is empty
    // EFFECTS: appends story skeleton and prompt answers in appropriate order,
    // producing the full story
    public String createStory() {
        return "";
    }

    // getters

    public List<String> getSkeleton() {
        return null; //stub
    }

    public List<Prompt> getPrompts() {
        return null; //stub
    }

    public List<Integer> getLocations() {
        return null; //stub
    }
}
