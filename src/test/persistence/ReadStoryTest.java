package persistence;

import model.Answer;
import model.Prompt;
import model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReadStoryTest {

    private ReadStory reader;
    private List<String> promptsString;
    private List<String> answersString;

    @BeforeEach
    void setUp() {
        promptsString.addAll(Arrays.asList("Choose a name", "Choose a food", "Choose a course", "Choose an animal"));
        answersString.addAll(Arrays.asList("John Doe", "pizza", "CPSC 210", "dog"));
    }

    @Test
    void testReadNonExistentFile() {
        try {
            reader = new ReadStory("data/JSONtests/nonExistentFile.json");
            Story str = reader.read();
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReadNoAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStoryNoAnswer");
            Story str = reader.read();
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 0);
            assertTrue(str.getAnswers().isEmpty());

        } catch (IOException e) {
            System.out.println("caught IOException");
        }
    }

    @Test
    void testReadSomeAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStorySomeAnswer");
            Story str = reader.read();
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 2);
            checkAnswers(str.getAnswers(), 2);

        } catch (IOException e) {
            System.out.println("caught IOException");
        }
    }

    @Test
    void testReadAllAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStoryAllAnswer");
            Story str = reader.read();
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            assertTrue(str.getPrompts().isEmpty());
            checkAnswers(str.getAnswers(), str.getAnswers().size());

        } catch (IOException e) {
            System.out.println("caught IOException");
        }
    }

    // EFFECTS: checks that the skeleton is correct
    private void checkSkeleton(List<String> skeleton) {
        assertEquals(7, skeleton.size());
        assertEquals("Here's a name, ", skeleton.get(0));
        assertEquals(" and a food, ", skeleton.get(1));
        assertEquals(" and the name again, ", skeleton.get(2));
        assertEquals(" and a course, ", skeleton.get(3));
        assertEquals(" and the food again, ", skeleton.get(4));
        assertEquals(" and an animal, ", skeleton.get(5));
        assertEquals(" end.", skeleton.get(6));
    }

    // EFFECTS: checks that the locations are correct
    private void checkLocations(List<Integer> locations) {
        assertEquals(6, locations.size());
        assertEquals(0, locations.get(0));
        assertEquals(1, locations.get(1));
        assertEquals(0, locations.get(2));
        assertEquals(2, locations.get(3));
        assertEquals(1, locations.get(4));
        assertEquals(3, locations.get(5));
    }

    // EFFECTS: checks the prompts starting at 0 and ending at maxIndex
    private void checkPrompts(List<Prompt> prompts, int startIndex) {
        for (int i = startIndex; i < prompts.size(); i++) {
            String actual = prompts.get(i).getPrompt();
            String expected = promptsString.get(i);
            assertEquals(expected, actual);
        }
    }

    // EFFECTS: checks the answers starting at 0 and ending at maxIndex
    private void checkAnswers(List<Answer> prompts, int maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            String actual = prompts.get(i).getAnswer();
            String expected = answersString.get(i);
            assertEquals(expected, actual);
        }
    }

}
