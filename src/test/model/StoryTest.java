package model;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// TODO: create remaining example. Could perhaps not have everything in the BeforeEach, but have each one in its
//  appropriate method. If so, might have to create different methods for different cases of the same method.
//  mainly thinking of setPromptsInOrder. The different cases for it could be base case
//  (skeleton size 2, prompts size 1), more prompts that each go in a unique place (in order),
//  one where there's only one prompt that goes in multiple locations, and a mix of all. Could also perhaps create a
//  list of skeletons, prompts, locations and stories, so that in each test case I will get only the one that's
//  necessary. Or just have a general field for each and construct it within each separate test.

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
        skeleton1.addAll(Arrays.asList("This is my character, ", ", who is smart."));
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
        assertEquals(", who is smart.", story1.getSkeleton().get(1));
        assertEquals("Choose a character name", story1.getPrompts().get(0).getPrompt());
        assertEquals(0, story1.getLocations().get(0));

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
    }

    @Test
    void testSetPromptsInOrderAllInOrder() {
        setAnswers();
        story1.setPromptsInOrder();
        checkAnswers(prompts1, locations1);

        story2.setPromptsInOrder();
        checkAnswers(prompts2, locations2);
    }

    @Test
    void testSetPromptsInOrderOnePromptFewPlaces() {
        setAnswers();
        story3.setPromptsInOrder();
        assertEquals(3, story3.getPrompts().size());
        assertEquals("John Doe", story3.getPrompts().get(0).getPrompt());
        assertEquals("John Doe", story3.getPrompts().get(1).getPrompt());
        assertEquals("John Doe", story3.getPrompts().get(2).getPrompt());
    }

    @Test
    void testSetPromptInOrderOnePromptAnother() {
        Prompt answer = new Prompt("pizza");
        List<String> skel = new ArrayList<String>();
        skel.addAll(Arrays.asList("Sentence ", " another sentence ", " yet another ", " one more ", " finally."));
        List<Prompt> lop = new ArrayList<Prompt>();
        lop.add(answer);
        List<Integer> loi = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
        Story st = new Story(skel, lop, loi);

        st.setPromptsInOrder();
        assertEquals(4, st.getPrompts().size());
        assertEquals("pizza", st.getPrompts().get(0).getPrompt());
        assertEquals("pizza", st.getPrompts().get(1).getPrompt());
        assertEquals("pizza", st.getPrompts().get(2).getPrompt());
        assertEquals("pizza", st.getPrompts().get(3).getPrompt());
    }

    @Test
    void testSetPromptsInOrderMix() {
        setAnswers();
        story4.setPromptsInOrder();
        assertEquals(6, story4.getPrompts().size());
        assertEquals("John Doe", story4.getPrompts().get(0).getPrompt());
        assertEquals("pizza", story4.getPrompts().get(1).getPrompt());
        assertEquals("John Doe", story4.getPrompts().get(2).getPrompt());
        assertEquals("CPSC 210", story4.getPrompts().get(3).getPrompt());
        assertEquals("pizza", story4.getPrompts().get(4).getPrompt());
        assertEquals("John Doe", story4.getPrompts().get(5).getPrompt());
    }

    @Test
    void testCreateStory() {
        setAnswers();
        assertEquals("This is my character, John Doe, who is smart." ,story1.createStory());
        assertEquals("I like to eat pizza while John Doe thinks it is mandatory you do homework for " +
                "CPSC 210 before anything else.", story2.createStory());
        story3.setPromptsInOrder();
        assertEquals("My character's name is John Doe. John Doe likes to eat pasta. " +
                "John Doe also likes to sleep.", story3.createStory());
        story4.setPromptsInOrder();
        assertEquals("Here's a name John Doe and a food pizza and the name again John Doe " +
                "and a course CPSC 210 and a food again, pizza and the name John Doe, end.", story4.createStory());
    }

    // EFFECTS: changes prompts to answers
    void setAnswers() {
        Prompt name = new Prompt("John Doe");
        Prompt food = new Prompt("pizza");
        Prompt course = new Prompt("CPSC 210");

        prompts1.clear();
        prompts1.add(name);

        prompts2.clear();
        prompts2.addAll(Arrays.asList(food, name, course));

        prompts3.clear();
        prompts3.add(name);

        prompts4.clear();
        prompts4.addAll(Arrays.asList(name, food, course));
    }

    // EFFECTS: checks that each prompt corresponds to the location in order
    void checkAnswers(List<Prompt> prompts, List<Integer> locations) {
        for (int i = 0; i < prompts.size(); i++) {
            Prompt expect = prompts.get(i);
            Prompt actual = prompts.get(locations.get(i));
            assertEquals(expect, actual);
        }
    }

}
