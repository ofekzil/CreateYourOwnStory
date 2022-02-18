package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import model.Prompt;
import model.Story;

// TODO: Add an option for quit in the for-loop in makeStory. If quit is selected, then remove the prompts already
//  answered from the story (perhaps remove the prompt each time in the loop). Then save the story as it is with an
//  all the answers answered so far. Also add an option at the beginning to allow the user to reload the previous
//  story state.

// along with user input, this class writes the story accordingly
public class StoryApp {

    public static final List<String> TEMPLATES = new ArrayList<>();
    public static final List<String> TEMPLATE_NAMES = new ArrayList<>();

    private Scanner user;
    private Story story;

    // REQUIRES: the strings in TEMPLATES have to be in order of all male stories first,
    // then all female stories in the same order, where the order of the stories is the same as TEMPLATE_NAMES
    // MODIFIES: this
    // EFFECTS: sets up list of story file names, a list of template options, and runs the app
    public StoryApp() throws FileNotFoundException {
        TEMPLATES.addAll(Arrays.asList("data/templates/CrazyMorningMale.txt", "data/templates/KingdomMale.txt",
                "data/templates/CrazyMorningFemale.txt", "data/templates/KingdomFemale.txt"));
        TEMPLATE_NAMES.addAll(Arrays.asList("Crazy Morning", "In the Kingdom"));

        chooseStory();
    }

    // TODO: will add option for loading story, which will be represented from entering -1
    // MODIFIES: this
    // EFFECTS: chooses story template based on user input
    public void chooseStory() throws FileNotFoundException {
        user = new Scanner(System.in);
        String names = TEMPLATE_NAMES.toString().replace("[", "").replace("]", "");
        System.out.println("Welcome! Please select a story template and type the number. Options are 1 through "
                + TEMPLATES.size() / 2 + ",\nwhich correspond to the following templates: " + names
                + ". (please type a number, e.g. 1)");

        while (true) {
            int template = user.nextInt();
            if (template >= 1 && template <= TEMPLATES.size() / 2) {
                user.nextLine();
                chooseMaleOrFemale(template);
                break;
            } else {
                System.out.println("Please enter appropriate input.");
            }
        }
        user.close();
    }

    // MODIFIES: this
    // EFFECTS: chooses male/female template based on user input
    public void chooseMaleOrFemale(int template) throws FileNotFoundException {
        System.out.println("Please type either \"male\" or \"female\" (no quotes) in order to choose your desired "
                + "template for your protagonist. \nYou will then be presented with a series of prompts to fill out, "
                + "which will complete the story accordingly.");

        user = new Scanner(System.in);
        while (true) {
            String chosenTemplate = user.nextLine();
            if (chosenTemplate.equalsIgnoreCase("male")) {
                readFile(TEMPLATES.get(template - 1));
                break;
            } else if (chosenTemplate.equalsIgnoreCase("female")) {
                readFile(TEMPLATES.get((TEMPLATES.size() / 2) + (template - 1)));
                break;
            } else {
                System.out.println("Please enter appropriate input.");
            }
        }
        user.close();
    }

    // TODO: will come from TemplateReader abstract class
    // REQUIRES: file must have the text in the following order: first all prompts (each on new line),
    // then all locations (as ints in a single line), then the rest of the skeleton with line breaks every time a prop
    // needs to be inserted
    // MODIFIES: this
    // EFFECTS: read story from txt file and sort information into appropriate lists
    public void readFile(String name) throws FileNotFoundException {
        File file = new File(name);
        Scanner sc = new Scanner(file);
        List<String> promptsString = new ArrayList<>();
        List<Integer> locations = new ArrayList<>();
        List<String> skeleton = new ArrayList<>();

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                locations.add(sc.nextInt());
            } else if (locations.size() == 0) {
                promptsString.add(sc.nextLine());
            } else {
                skeleton.add(sc.nextLine());
            }
        }
        sc.close();
        skeleton.remove(skeleton.get(0));
        List<Prompt> prompts = turnToPrompts(promptsString);
        story = new Story(skeleton, prompts, locations);
        makeStory();
    }


    // MODIFIES: this, fullStory
    // EFFECTS: prints the prompts and collects the user's answers, then prints the finished story
    public void makeStory() {
        user = new Scanner(System.in);
        List<Prompt> prompts = story.getPrompts();
        for (Prompt p : prompts) {
            System.out.println(p.getPrompt());
            String answer = user.nextLine();
            story.addAnswer(answer);
        }
        story.setAnswersInOrder();
        String fullStory = story.createStory();
        String brokenStory = story.breakLines(fullStory);
        System.out.println(brokenStory);
        user.close();
    }

    // TODO: will come from TemplateReader abstract class
    // EFFECTS: turns list of strings from file into prompts
    private List<Prompt> turnToPrompts(List<String> los) {
        List<Prompt> result = new ArrayList<>();
        for (String s : los) {
            Prompt p = new Prompt(s);
            result.add(p);
        }
        return result;
    }
}
