package persistence;


import model.Story;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReadStoryTest extends StoryJsonTest {

    private ReadStory reader;

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
            checkSkeleton(str.getSkeleton());
            checkLocations(str.getLocations());
            assertTrue(str.getPrompts().isEmpty());
            checkAnswers(str.getAnswers(), str.getAnswers().size());

        } catch (IOException e) {
            fail("caught IOException");
        }
    }



}
