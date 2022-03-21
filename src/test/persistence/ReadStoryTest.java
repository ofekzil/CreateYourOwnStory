package persistence;


import model.Story;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for ReadStory class
public class ReadStoryTest extends StoryJsonTest {

    private ReadStory reader;

    @Test
    void testReadTemplateFileNonExistent() {
        try {
            readTemplateFile("data/nonExistentFile.txt", false);
            fail("expected file not found exception");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testReadTemplateFileExistsToRead() {
        try {
            readTemplateFile("data/testTemplate.txt", false);
            assertEquals("data/testTemplate.txt", storyToRead.getName());
            checkSkeleton(getStoryToRead().getSkeleton());
            checkLocations(storyToRead.getLocations());
            checkPrompts(storyToRead.getPrompts(), 0);
            assertTrue(storyToRead.getAnswers().isEmpty());
        } catch (FileNotFoundException e) {
            fail("caught file not found exception");
        }
    }

    @Test
    void testReadTemplateFileExistsToApp() {
        try {
            readTemplateFile("data/testTemplate.txt", true);
            assertEquals("data/testTemplate.txt", storyToApp.getName());
            checkSkeleton(getStoryToApp().getSkeleton());
            checkLocations(storyToApp.getLocations());
            checkPrompts(storyToApp.getPrompts(), 0);
            assertTrue(storyToApp.getAnswers().isEmpty());
        } catch (FileNotFoundException e) {
            fail("caught file not found exception");
        }
    }

    @Test
    void testReadNonExistentFile() {
        try {
            reader = new ReadStory("data/JSONtests/nonExistentFile.json");
            Story str = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReadNoAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStoryNoAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 0);
            assertTrue(str.getAnswers().isEmpty());

        } catch (IOException e) {
            fail("caught IOException");
        }
    }

    @Test
    void testReadSomeAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStorySomeAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            checkPrompts(str.getPrompts(), 2);
            checkAnswers(str.getAnswers(), 2);

        } catch (IOException e) {
            fail("caught IOException");
        }
    }

    @Test
    void testReadAllAnswers() {
        try {
            reader = new ReadStory("data/JSONtests/testReadStoryAllAnswers.json");
            Story str = reader.read();
            assertEquals("data/testTemplate.txt", str.getName());
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            assertTrue(str.getPrompts().isEmpty());
            checkAnswers(str.getAnswers(), str.getAnswers().size());

        } catch (IOException e) {
            fail("caught IOException");
        }
    }



}
