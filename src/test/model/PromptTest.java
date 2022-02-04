package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Prompt class
public class PromptTest {

    private Prompt testPrompt;
    private Prompt anotherTestPrompt;

    @BeforeEach
    void setup() {
        this.testPrompt = new Prompt("Choose a name");
        anotherTestPrompt = new Prompt("Choose a food");
    }

    @Test
    void testConstructor() {
        String p1 = testPrompt.getPrompt();
        assertEquals("Choose a name", p1);

        String p2 = anotherTestPrompt.getPrompt();
        assertEquals("Choose a food", p2);
    }

    @Test
    void testChangePromptToAnswer() {
        assertEquals("Choose a name", testPrompt.getPrompt());
        testPrompt.changePromptToAnswer("John Doe");
        assertEquals("John Doe", testPrompt.getPrompt());
        testPrompt.changePromptToAnswer("Mike"); //would ideally change answer only once,
        assertEquals("Mike", testPrompt.getPrompt()); //but for test's sake did twice
    }

    @Test
    void testAnotherChangePromptToAnswer() {
        assertEquals("Choose a food", anotherTestPrompt.getPrompt());
        anotherTestPrompt.changePromptToAnswer("pizza");
        assertEquals("pizza", anotherTestPrompt.getPrompt());
        anotherTestPrompt.changePromptToAnswer("pasta");
        assertEquals("pasta", anotherTestPrompt.getPrompt());
    }

}
