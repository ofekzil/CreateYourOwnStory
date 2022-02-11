package model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Story class
public class StoryTest {

    private Story story1;
    private Story story2;
    private Story story3;
    private Story story4;
    private List<String> skeleton1;
    private List<String> skeleton2;
    private List<String> skeleton3;
    private List<String> skeleton4;
    private List<Prompt> prompts1;
    private List<Prompt> prompts2;
    private List<Prompt> prompts3;
    private List<Prompt> prompts4;
    private List<Integer> locations1;
    private List<Integer> locations2;
    private List<Integer> locations3;
    private List<Integer> locations4;

    @BeforeEach
    void setup() {
        skeleton1 = new ArrayList<String>();
        skeleton1.addAll(Arrays.asList("This is my character, ", ", who is very smart."));
        prompts1 = new ArrayList<Prompt>();
        Prompt p1 = new Prompt("Choose a character name");
        prompts1.add(p1);
        locations1 = new ArrayList<Integer>();
        locations1.add(0);

        skeleton2 = new ArrayList<String>();
        skeleton2.addAll(Arrays.asList("I like to eat ", " while ", " thinks it is mandatory you do homework for ",
                " before anything else."));
        prompts2 = new ArrayList<Prompt>();
        Prompt p2a = new Prompt("Choose a food");
        Prompt p2b = new Prompt("Choose a name");
        Prompt p2c = new Prompt("Choose a course");
        prompts2.addAll(Arrays.asList(p2a, p2b, p2c));
        locations2 = new ArrayList<Integer>();
        locations2.addAll(Arrays.asList(0, 1, 2));

        skeleton3 = new ArrayList<String>();
        skeleton3.addAll(Arrays.asList("My character's name is ", ". ", " likes to eat pasta. ",
                " also likes to sleep."));
        prompts3 = new ArrayList<Prompt>();
        prompts3.add(p1);
        locations3 = new ArrayList<Integer>();
        locations3.addAll(Arrays.asList(0, 0, 0));

        skeleton4 = new ArrayList<String>();
        skeleton4.addAll(Arrays.asList("Here's a name ", " and a food ", " and the name again ", " and a course ",
                " and a food again, ", " and the name ", ", end."));
        prompts4 = new ArrayList<Prompt>();
        prompts4.addAll(Arrays.asList(p2a, p2b, p2c));
        locations4 = new ArrayList<Integer>();
        locations4.addAll(Arrays.asList(0, 1, 0, 2, 1, 0));

        story1 = new Story(skeleton1, prompts1, locations1);
        story2 = new Story(skeleton2, prompts2, locations2);
        story3 = new Story(skeleton3, prompts3, locations3);
        story4 = new Story(skeleton4, prompts4, locations4);
    }

    @Test
    void testConstructor() {
        assertEquals("This is my character, ", story1.getSkeleton().get(0));
        assertEquals(", who is very smart.", story1.getSkeleton().get(1));
        assertEquals("Choose a character name", story1.getPrompts().get(0).getPrompt());
        assertEquals(0, story1.getLocations().get(0));
        assertTrue(story1.getAnswers().isEmpty());

        assertEquals("I like to eat ", story2.getSkeleton().get(0));
        assertEquals(" while ", story2.getSkeleton().get(1));
        assertEquals(" thinks it is mandatory you do homework for ", story2.getSkeleton().get(2));
        assertEquals(" before anything else.", story2.getSkeleton().get(3));
        assertEquals("Choose a food", story2.getPrompts().get(0).getPrompt());
        assertEquals("Choose a name", story2.getPrompts().get(1).getPrompt());
        assertEquals("Choose a course", story2.getPrompts().get(2).getPrompt());
        assertEquals(0, story2.getLocations().get(0));
        assertEquals(1, story2.getLocations().get(1));
        assertEquals(2, story2.getLocations().get(2));
        assertTrue(story2.getAnswers().isEmpty());
    }

    @Test
    void testAddAnswer() {
        story2.addAnswer("pizza");
        assertEquals("pizza", story2.getAnswers().get(0).getAnswer());
        story2.addAnswer("John Doe");
        assertEquals("John Doe", story2.getAnswers().get(1).getAnswer());
    }

    @Test
    void testSetAnswersInOrderAllInOrder() {
        setAnswers();
        story1.setAnswersInOrder();
        checkAnswers(story1.getAnswers(), locations1);

        story2.setAnswersInOrder();
        checkAnswers(story2.getAnswers(), locations2);
    }

    @Test
    void testSetAnswersInOrderOnePromptFewPlaces() {
        setAnswers();
        story3.setAnswersInOrder();
        assertEquals(3, story3.getAnswers().size());
        assertEquals("John Doe", story3.getAnswers().get(0).getAnswer());
        assertEquals("John Doe", story3.getAnswers().get(1).getAnswer());
        assertEquals("John Doe", story3.getAnswers().get(2).getAnswer());
    }

    @Test
    void testSetAnswersInOrderOnePromptAnother() {
        Prompt p = new Prompt("Name a food");
        List<String> skel = new ArrayList<String>();
        skel.addAll(Arrays.asList("Sentence ", " another sentence ", " yet another ", " one more ", " finally."));
        List<Prompt> lop = new ArrayList<Prompt>();
        lop.add(p);
        List<Integer> loi = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        Story st = new Story(skel, lop, loi);
        st.addAnswer("pizza");

        st.setAnswersInOrder();
        assertEquals(4, st.getAnswers().size());
        assertEquals("pizza", st.getAnswers().get(0).getAnswer());
        assertEquals("pizza", st.getAnswers().get(1).getAnswer());
        assertEquals("pizza", st.getAnswers().get(2).getAnswer());
        assertEquals("pizza", st.getAnswers().get(3).getAnswer());
    }

    @Test
    void testSetAnswersInOrderMix() {
        setAnswers();
        story4.setAnswersInOrder();
        assertEquals(6, story4.getAnswers().size());
        assertEquals("John Doe", story4.getAnswers().get(0).getAnswer());
        assertEquals("pizza", story4.getAnswers().get(1).getAnswer());
        assertEquals("John Doe", story4.getAnswers().get(2).getAnswer());
        assertEquals("CPSC 210", story4.getAnswers().get(3).getAnswer());
        assertEquals("pizza", story4.getAnswers().get(4).getAnswer());
        assertEquals("John Doe", story4.getAnswers().get(5).getAnswer());
    }

    @Test
    void testCreateStory() {
        setAnswers();
        assertEquals("This is my character, John Doe, who is very smart." ,story1.createStory());
        assertEquals("I like to eat pizza while John Doe thinks it is mandatory you do homework for " +
                "CPSC 210 before anything else.", story2.createStory());
        story3.setAnswersInOrder();
        assertEquals("My character's name is John Doe. John Doe likes to eat pasta. " +
                "John Doe also likes to sleep.", story3.createStory());
        story4.setAnswersInOrder();
        assertEquals("Here's a name John Doe and a food pizza and the name again John Doe " +
                "and a course CPSC 210 and a food again, pizza and the name John Doe, end.", story4.createStory());
    }

    @Test
    void testBreakLinesMinLength() {
        setAnswers();
        story1.setAnswersInOrder();
        String s1 = story1.createStory();
        assertEquals(s1, story1.breakLines(s1));
    }

    @Test
    void testBreakLinesMidWord() {
        setAnswers();
        story2.setAnswersInOrder();
        String s2 = story2.createStory();
        assertEquals("I like to eat pizza while John Doe thinks it is ma-\nndatory you do homework for CPSC "
                + "210 before anythi-\nng else.", story2.breakLines(s2));
    }

    @Test
    void testBreakLinesSpace() {
        setAnswers();
        story3.setAnswersInOrder();
        String s3 = story3.createStory();
        assertEquals("My character's name is John Doe. John Doe likes to\n eat pasta. " +
                "John Doe also likes to sleep.", story3.breakLines(s3));
    }

    @Test
    void testBreakLinesMultipleBreaks() {
        setAnswers();
        story4.setAnswersInOrder();
        String s4 = story4.createStory();
        assertEquals("Here's a name John Doe and a food pizza and the na-\nme again John Doe " +
                "and a course CPSC 210 and a food\n again, pizza and the name John Doe, end.", story4.breakLines(s4));
    }

    // EFFECTS: changes adds appropriate answers to each story
    void setAnswers() {
        String name = "John Doe";
        String food = "pizza";
        String course = "CPSC 210";

        story1.addAnswer(name);

        story2.addAnswer(food);
        story2.addAnswer(name);
        story2.addAnswer(course);

        story3.addAnswer(name);

        story4.addAnswer(name);
        story4.addAnswer(food);
        story4.addAnswer(course);
    }

    // EFFECTS: checks that each answer corresponds to the location in order
    void checkAnswers(List<Answer> answers, List<Integer> locations) {
        for (int i = 0; i < answers.size(); i++) {
            Answer expect = answers.get(i);
            Answer actual = answers.get(locations.get(i));
            assertEquals(expect, actual);
        }
    }

}
