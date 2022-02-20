package ui;

import model.Answer;
import model.Prompt;
import model.Story;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// represents a class to read a story template from txt file
public abstract class TemplateReader {

    protected Story toApp;
    protected Story toRead;

    // REQUIRES: file must have the text in the following order: first all prompts (each on new line),
    // then all locations (as ints in a single line), then the rest of the skeleton with line breaks every time a prop
    // needs to be inserted
    // MODIFIES: this
    // EFFECTS: read story from txt file and sort information into appropriate lists, depending on it being a new
    // story or loaded one. True for new, False for load
    public void readTemplateFile(String name, boolean state) {
        try {
            File file = new File(name);
            Scanner sc = new Scanner(file);
            List<String> promptsString = new ArrayList<>();
            List<Integer> locations = new ArrayList<>();
            List<String> skeleton = new ArrayList<>();

            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    locations.add(sc.nextInt());
                } else if (locations.size() == 0) {
                    promptsString.add(sc.nextLine());
                } else {
                    skeleton.add(sc.nextLine());
                }
            }
            sc.close();
            skeleton.remove(skeleton.get(0));
            List<Prompt> prompts = turnToPrompts(promptsString);
            determineState(state, locations, skeleton, prompts);
        } catch (FileNotFoundException e) {
            System.out.println("could not find template file " + name);
        }
    }

    // MODIFIES: this
    // EFFECTS: determines whether to assign story to app or read
    private void determineState(boolean state, List<Integer> locations, List<String> skeleton, List<Prompt> prompts) {
        if (state) {
            toApp = new Story(skeleton, prompts, locations);
        } else {
            toRead = new Story(skeleton, prompts, locations);
        }
    }

    // EFFECTS: turns list of strings from file into prompts
    public List<Prompt> turnToPrompts(List<String> los) {
        List<Prompt> result = new ArrayList<>();
        for (String s : los) {
            Prompt p = new Prompt(s);
            result.add(p);
        }
        return result;
    }

    // EFFECTS: turns list of strings from file into prompts
    public List<Answer> turnToAnswers(List<String> los) {
        List<Answer> result = new ArrayList<>();
        for (String s : los) {
            Answer a = new Answer(s);
            result.add(a);
        }
        return result;
    }



}
