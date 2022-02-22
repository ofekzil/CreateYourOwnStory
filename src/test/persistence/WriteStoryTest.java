package persistence;

import model.Answer;
import model.Prompt;
import model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for WriteStory class
public class WriteStoryTest extends StoryJsonTest {

    private ReadStory reader;
    private WriteStory writer;

    @BeforeEach
    void setStory() {
        readTemplateFile("data/testTemplate.txt", true);
    }

    @Test
    void testWriteNonExistentFile() {
        try {
            writer = new WriteStory("data/JSONtests/nonExistentFile\n.json");
            writer.write(storyToApp);
            fail("Should catch file not found exception");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriteNoAnswers() {
        try {
            writer = new WriteStory("data/JSONtests/testWriteStoryNoAnswers.json");
            writer.write(storyToApp);

            reader = new ReadStory("data/JSONtests/testWriteStoryNoAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 0);
            assertTrue(str.getAnswers().isEmpty());
        } catch (IOException e){
            fail("should've caught file not found exception");
        }
    }

    @Test
    void testWriteSomeAnswers() {
        try {
            List<Prompt> prompts = turnToPrompts(Arrays.asList("Choose a course", "Choose an animal"));
            List<Answer> answers = turnToAnswers(Arrays.asList("John Doe", "pizza"));
            storyToApp.setPrompts(prompts);
            storyToApp.setAnswers(answers);
            writer = new WriteStory("data/JSONtests/testWriteStorySomeAnswers.json");
            writer.write(storyToApp);

            reader = new ReadStory("data/JSONtests/testWriteStorySomeAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 2);
            checkAnswers(str.getAnswers(), 2);
        } catch (IOException e){
            fail("should've caught file not found exception");
        }
    }

    @Test
    void testWriteAllAnswers() {
        try {
            List<Prompt> prompts = new ArrayList<>();
            List<Answer> answers = turnToAnswers(Arrays.asList("John Doe", "pizza", "CPSC 210", "dog"));
            storyToApp.setPrompts(prompts);
            storyToApp.setAnswers(answers);
            writer = new WriteStory("data/JSONtests/testWriteStoryAllAnswers.json");
            writer.write(storyToApp);

            reader = new ReadStory("data/JSONtests/testWriteStoryAllAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            assertTrue(str.getPrompts().isEmpty());
            checkAnswers(str.getAnswers(), str.getAnswers().size());
        } catch (IOException e){
            fail("should've caught file not found exception");
        }
    }

}
