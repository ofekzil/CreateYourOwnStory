package persistence;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadStoryTest {

    private ReadStory reader;

    @Test
    void testReadNonExistentFile() {
        try {
            reader = new ReadStory("data/nonExistentFile.json");
            reader.read();
        } catch (IOException e) {
            // expected
        }
    }

}
