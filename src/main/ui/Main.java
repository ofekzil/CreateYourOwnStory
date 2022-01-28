package ui;

import java.util.ArrayList;
import java.util.List;

import model.Story;
import model.Prompt;

public class Main {
    public static void main(String[] args) {
        Prompt p1 = new Prompt("Choose a name");
        Prompt p2 = new Prompt("Name a food");
        List<Prompt> prompts = new ArrayList<Prompt>();
        prompts.add(p1);
        prompts.add(p2);
        List<String> skeleton = new ArrayList<String>();
        skeleton.add("One morning ");
        skeleton.add("got out of bed and went to eat ");
        skeleton.add("for breakfast.");
        List<Integer> locations = new ArrayList<Integer>();
        locations.add(0);
        locations.add(1);
        Story myStory = new Story(skeleton, prompts, locations);
        p1.changePromptToAnswer(" John Doe");
        p2.changePromptToAnswer(" eggs");
        myStory.setPromptsInOrder();
        String complete = myStory.createStory();
        System.out.println(complete);
    }
}
