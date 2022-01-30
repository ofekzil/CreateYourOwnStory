package ui;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.Prompt;
import model.Story;

public class StoryApp {

    public static final List<String> SKELETON1_MALE = new ArrayList<>();
    public static final List<String> SKELETON1_FEMALE = new ArrayList<>();
    public static final List<Prompt> PROMPTS1 = new ArrayList<>();
    public static final List<Integer> LOCATIONS1 = new ArrayList<>();

    public static final List<String> SKELETON2_MALE = new ArrayList<>();
    public static final List<String> SKELETON2_FEMALE = new ArrayList<>();
    public static final List<Prompt> PROMPTS2 = new ArrayList<>();
    public static final List<Integer> LOCATIONS2 = new ArrayList<>();

    private List<Story> stories;
    private Scanner user;

    // EFFECTS: sets up list of stories and runs the app
    public StoryApp() {
        SKELETON1_MALE.addAll(Arrays.asList("It was Monday morning and ", " went to work. He drove his new ",
                " which on the way got into a massive crash. ", " began to cry, but then got up and went to ",
                " in order to get breakfast. At ", ", ", " ordered two ", ", his favorite food."));
        SKELETON1_FEMALE.addAll(Arrays.asList("It was Monday morning and ", " went to work. She drove her new ",
                " which on the way got into a massive crash. ", " began to cry, but then got up and went to ",
                "in order to get breakfast. At ", ", ", "ordered two ", ", her favorite food."));
        Prompt p0 = new Prompt("Choose the protagonist's name");
        Prompt p1 = new Prompt("Choose a car brand");
        Prompt p2 = new Prompt("Choose a restaurant");
        Prompt p3 = new Prompt("Choose a food (in plural)");
        PROMPTS1.addAll(Arrays.asList(p0, p1, p2, p3));
        LOCATIONS1.addAll(Arrays.asList(0, 1, 0, 2, 2, 0, 3));

        SKELETON2_MALE.addAll(Arrays.asList("It was ", " years ago today. ", " was born on a ", ", in the heart of ",
                ". It actually isn’t even his favourite season, ", ". His favourite one doesn’t matter, ",
                " doesn’t care much about it. All he likes are ", ", lots and lots of ", ". End story."));
        SKELETON2_FEMALE.addAll(Arrays.asList("It was ", " years ago today. ", " was born on a ", ", in the heart of ",
                ". It actually isn’t even her favourite season, ", ". Her favourite one doesn’t matter, ",
                " doesn’t care much about it. All she likes are ", ", lots and lots of ", ". End story."));
        Prompt p4 = new Prompt("Choose a number (type in words)");
        Prompt p5 = new Prompt("Choose a day of the week");
        Prompt p6 = new Prompt("Choose a season");
        PROMPTS2.addAll(Arrays.asList(p4, p0, p5, p6, p3));
        LOCATIONS2.addAll(Arrays.asList(0, 1, 2, 3, 3, 1, 4, 4));

        Story story1Male = new Story(SKELETON1_MALE, PROMPTS1, LOCATIONS1);
        Story story1Female = new Story(SKELETON1_FEMALE, PROMPTS1, LOCATIONS1);
        Story story2Male = new Story(SKELETON2_MALE, PROMPTS2, LOCATIONS2);
        Story story2Female = new Story(SKELETON2_FEMALE, PROMPTS2, LOCATIONS2);

        stories = new ArrayList<Story>();
        stories.addAll(Arrays.asList(story1Male, story2Male, story1Female, story2Female));

        chooseStory();
    }

    // EFFECTS: chooses story template and male/female story based on user input
    public void chooseStory() {
        user = new Scanner(System.in);
        System.out.println("Welcome! Please select a story template and type the number. Options are 1 or 2 "
                + "(please type a number, e.g. 1)");
        int template = user.nextInt();
        user.nextLine();
        System.out.println("Please type either \"male\" or \"female\" in order to choose your desired template for your"
                + " protagonist.");
        String chosenTemplate = user.nextLine();
        if (chosenTemplate.equalsIgnoreCase("male")) {
            makeStory(stories.get(template - 1));
        } else if (chosenTemplate.equalsIgnoreCase("female")) {
            makeStory(stories.get((stories.size() / 2) + (template - 1)));
        } else {
            System.out.println("Please enter appropriate input.");
        }
        user.close();
    }

    // EFFECTS: prints the prompts and collects the user's answers, then print the finished story
    public void makeStory(Story story) {
        user = new Scanner(System.in);
        List<Prompt> prompts = story.getPrompts();
        for (Prompt p : prompts) {
            System.out.println(p.getPrompt());
            String answer = user.nextLine();
            p.changePromptToAnswer(answer);
        }
        story.setPromptsInOrder();
        String fullStory = story.createStory();
        System.out.println(fullStory);
    }
}
