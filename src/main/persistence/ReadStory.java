package persistence;

import model.Story;
import org.json.*;
import ui.TemplateReader;

import java.io.IOException;

// Represents a class to read a story (i.e. the answered prompts) from file
public class ReadStory extends TemplateReader {

    // EFFECTS: constructs a ReadStory object to read the file given as string
    public ReadStory(String file) {

    }

    // TODO: method will call readFile() in the TemplateReader abstract class w/ the first parameter being the
    //  file name from the JSONObject, and "load" as the second parameter
    // EFFECTS: reads and returns Story from file
    public Story read() throws IOException {
        return null;
    }

    // EFFECTS: scans the JSON file and returns it as a string
    private String scanFile() {
        return null;
    }

    // MODIFIES: s
    // EFFECTS: adds saved answers to story
    private void addAnswers(Story s, JSONObject jo) {

    }

}
