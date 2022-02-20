package ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.Prompt;
import persistence.ReadStory;
import persistence.WriteStory;

// along with user input, this class writes the story accordingly
public class StoryApp extends TemplateReader {

    public static final List<String> TEMPLATES = new ArrayList<>();
    public static final List<String> TEMPLATE_NAMES = new ArrayList<>();
    public static final String STORE = "data/story.json";

    private Scanner user;
    private String chosenTemplateFile;
    private ReadStory reader;
    private WriteStory writer;

    // REQUIRES: the strings in TEMPLATES have to be in order of all male stories first,
    // then all female stories in the same order, where the order of the stories is the same as TEMPLATE_NAMES
    // MODIFIES: this
    // EFFECTS: sets up list of story file names, a list of template options, and runs the app
    public StoryApp() {
        TEMPLATES.addAll(Arrays.asList("data/templates/CrazyMorningMale.txt", "data/templates/KingdomMale.txt",
                "data/templates/CrazyMorningFemale.txt", "data/templates/KingdomFemale.txt"));
        TEMPLATE_NAMES.addAll(Arrays.asList("Crazy Morning", "In the Kingdom"));
        reader = new ReadStory(STORE);
        writer = new WriteStory(STORE);

        start();
    }

    // MODIFIES: this
    // EFFECTS: presents the user with initial options
    public void start() {
        System.out.println("Welcome! Please select one of the following options:\n n = new story\n l = load story\n "
                + "q = quit the application");
        user = new Scanner(System.in);
        while (true) {
            if (user.nextLine().equalsIgnoreCase("n")) {
                chooseStory();
                break;
            } else if (user.nextLine().equalsIgnoreCase("l")) {
                loadStory();
                break;
            } else if (user.nextLine().equalsIgnoreCase("q")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Please enter appropriate input");
            }
        }
        user.close();
    }

    // MODIFIES: this
    // EFFECTS: chooses story template based on user input
    public void chooseStory() {
        user = new Scanner(System.in);
        String names = TEMPLATE_NAMES.toString().replace("[", "").replace("]", "");
        System.out.println("Welcome! Please select a story template and type the number. Options are 1 through "
                + TEMPLATES.size() / 2 + ",\nwhich correspond to the following templates: " + names
                + ". (please type a number, e.g. 1).\n You may also choose to quit by typing -1");

        while (true) {
            int template = user.nextInt();
            if (template >= 1 && template <= TEMPLATES.size() / 2) {
                user.nextLine();
                chooseMaleOrFemale(template);
                break;
            } else if (template == -1) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Please enter appropriate input.");
            }
        }
        user.close();
    }

    // MODIFIES: this
    // EFFECTS: chooses male/female template based on user input
    public void chooseMaleOrFemale(int template) {
        System.out.println("Please type either \"male\" or \"female\" (no quotes) in order to choose the "
                + "template for your protagonist. \nYou will then be presented with a series of prompts to fill out, "
                + "which will complete the story accordingly.\n If you wish to quit the application at any point, "
                + "you may do so by typing \"q\" (no quotes). \nThis will save your answers so far, "
                + "allowing you to continue some other time");

        user = new Scanner(System.in);
        while (true) {
            String chosenTemplate = user.nextLine();
            if (chosenTemplate.equalsIgnoreCase("male")) {
                chosenTemplateFile = TEMPLATES.get(template - 1);
                readTemplateFile(chosenTemplateFile, true);
                makeStory();
                break;
            } else if (chosenTemplate.equalsIgnoreCase("female")) {
                chosenTemplateFile = TEMPLATES.get((TEMPLATES.size() / 2) + (template - 1));
                readTemplateFile(chosenTemplateFile, true);
                makeStory();
                break;
            } else if (chosenTemplate.equalsIgnoreCase("q")) {
                saveStory();
                System.out.println("Your story has been saved.\nGoodbye!");
                break;
            } else {
                System.out.println("Please enter appropriate input.");
            }
        }
        user.close();
    }



    // MODIFIES: this, fullStory
    // EFFECTS: prints the prompts and collects the user's answers, then prints the finished story
    public void makeStory() {
        user = new Scanner(System.in);
        List<Prompt> prompts = toApp.getPrompts();
        List<Prompt> toRemove = new ArrayList<>();
        for (Prompt p : prompts) {
            System.out.println(p.getPrompt());
            String answer = user.nextLine();
            if (answer.equalsIgnoreCase("q")) {
                prompts.removeAll(toRemove);
                saveStory();
                System.out.println("Your story has been saved.\nGoodbye!");
                return;
            } else {
                toRemove.add(p);
                toApp.addAnswer(answer);
            }
        }
        toApp.setAnswersInOrder();
        String fullStory = toApp.createStory();
        String brokenStory = toApp.breakLines(fullStory);
        System.out.println(brokenStory);
        user.close();
    }

    // MODIFIES: this
    // EFFECTS: loads story from file system
    public void loadStory() {
        try {
            toApp = reader.read();
            System.out.println("You will now be presented with the prompts left in your story to fill out");
            makeStory();
        } catch (IOException e) {
            System.out.println("Could not load requested file");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves story to file system
    public void saveStory() {
        try {
            writer.write(toApp, chosenTemplateFile);
        } catch (IOException e) {
            System.out.println("Could not save requested file");
        }
    }

}
