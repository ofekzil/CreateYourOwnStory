package persistence;

import model.Answer;
import model.Prompt;
import model.Story;
import org.json.*;
import ui.TemplateReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a class to read answers, prompts left and template name from file
public class ReadStory extends TemplateReader {

    private String file;

    // EFFECTS: constructs a ReadStory object to read the file given as string
    public ReadStory(String file) {
        this.file = file;
    }

    // MODIFIES: this
    // EFFECTS: reads and returns Story from file; throws IOException if it can't find json file
    public Story read() throws IOException {
        String jsonData = scanFile(file);
        JSONObject jo = new JSONObject(jsonData);
        return buildStory(jo);
    }

    // EFFECTS: scans the JSON file and returns it as a string; throws IOException if it can't find json file
    private String scanFile(String file) throws IOException {
        File f = new File(file);
        Scanner sc = new Scanner(f);
        String str = "";
        while (sc.hasNext()) {
            str += sc.nextLine();
        }
        return str;
    }

    // MODIFIES: this
    // EFFECTS: builds the story from given sources
    private Story buildStory(JSONObject jo) {
        String name = jo.getString("name");
        readTemplateFile(name, false);
        List<Prompt> prompts = addPrompts(jo);
        List<Answer> answers = addAnswers(jo);
        toRead.setPrompts(prompts);
        toRead.setAnswers(answers);
        return toRead;
    }

    // EFFECTS: creates a list of prompts from jo
    private List<Prompt> addPrompts(JSONObject jo) {
        JSONArray ja = jo.getJSONArray("prompts");
        List<Prompt> result = new ArrayList<>();
        for (Object o : ja) {
            JSONObject nextPrompt = (JSONObject) o;
            String str = nextPrompt.getString("prompt");
            Prompt p = new Prompt(str);
            result.add(p);
        }
        return result;
    }

    // EFFECTS: creates a list of prompts from jo
    private List<Answer> addAnswers(JSONObject jo) {
        JSONArray ja = jo.getJSONArray("answers");
        List<Answer> result = new ArrayList<>();
        for (Object o : ja) {
            JSONObject nextAnswer = (JSONObject) o;
            String str = nextAnswer.getString("answer");
            Answer a = new Answer(str);
            result.add(a);
        }
        return result;
    }

}
