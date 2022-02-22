package persistence;

import model.Story;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

// represents a class to save information about a story to file
public class WriteStory {

    public static final int INDENT = 2;

    private String dest;
    private PrintWriter writer;

    // EFFECTS: constructs a WriteStory object to write to file w/ given name
    public WriteStory(String dest) {
        this.dest = dest;
    }

    // MODIFIES: this
    // EFFECTS: writes to file the prompts left and answers so far,
    // along w/ story's template file name; throws IOException if it can't find file
    public void write(Story story) throws IOException {
        writer = new PrintWriter(dest);
        JSONObject jo = story.toJson();
        save(jo.toString(INDENT));
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the string to file
    private void save(String str) {
        writer.print(str);
    }



}
