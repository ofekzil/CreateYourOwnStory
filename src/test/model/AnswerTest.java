package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerTest {

    private Answer a1;
    private Answer a2;

    @BeforeEach
    void setUp() {
        a1 = new Answer("John Doe");
        a2 = new Answer("pasta");
    }

    @Test
    void testConstructor() {
        assertEquals("John Doe", a1.getAnswer());
        assertEquals("pasta", a2.getAnswer());
    }
}
