package persistence;

import model.Answer;
import model.Prompt;
import org.junit.jupiter.api.BeforeEach;
import ui.TemplateReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryJsonTest extends TemplateReader {
    protected List<String> promptsString;
    protected List<String> answersString;

    @BeforeEach
    void setUp() {
        promptsString = new ArrayList<>();
        answersString = new ArrayList<>();
        promptsString.addAll(Arrays.asList("Choose a name", "Choose a food", "Choose a course", "Choose an animal"));
        answersString.addAll(Arrays.asList("John Doe", "pizza", "CPSC 210", "dog"));
    }

    // EFFECTS: checks the prompts starting at startIndex and ending at prompts.size()
    protected void checkPrompts(List<Prompt> prompts, int startIndex) {
        for (int i = startIndex; i < prompts.size(); i++) {
            String actual = prompts.get(i).getPrompt();
            String expected = promptsString.get(i);
            assertEquals(expected, actual);
        }
    }

    // EFFECTS: checks the answers starting at 0 and ending at maxIndex
    protected void checkAnswers(List<Answer> answers, int maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            String actual = answers.get(i).getAnswer();
            String expected = answersString.get(i);
            assertEquals(expected, actual);
        }
    }

    // EFFECTS: checks that the skeleton is correct
    protected void checkSkeleton(List<String> skeleton) {
        assertEquals(7, skeleton.size());
        assertEquals("Here's a name,", skeleton.get(0));
        assertEquals(" and a food,", skeleton.get(1));
        assertEquals(" and the name again,", skeleton.get(2));
        assertEquals(" and a course,", skeleton.get(3));
        assertEquals(" and the food again,", skeleton.get(4));
        assertEquals(" and an animal,", skeleton.get(5));
        assertEquals(" end.", skeleton.get(6));
    }

    // EFFECTS: checks that the locations are correct
    protected void checkLocations(List<Integer> locations) {
        assertEquals(6, locations.size());
        assertEquals(0, locations.get(0));
        assertEquals(1, locations.get(1));
        assertEquals(0, locations.get(2));
        assertEquals(2, locations.get(3));
        assertEquals(1, locations.get(4));
        assertEquals(3, locations.get(5));
    }
}
