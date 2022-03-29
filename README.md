# My Personal Project - CPSC 210

## Create Your Own Story

For my project this term, I will design an application that would create a unique 
story for each user. Every time, the user will select one of few basic story templates 
(that I will write myself) and then be presented with a series of prompts 
that correlate to that story template. Then, after recording the answers to these prompts, 
the user will be able to read the full story with their answers filling gaps in the story.    
There are many people who will find the application interesting and useful, as 
its main purpose is *entertainment!* Therefore, anybody who is looking to get a little 
creative, wanting to write a story they can make unique to themselves, will find the 
application of good use.

**Reasons the project is of interest to me:**
- I like to read and write (both code and stories), so this is an opportunity to combine both.
- It's a great way to do something creative in a science course, combining different disciplines 
together.
- I see this as something that can bring a good laugh and entertainment to others.

### User Stories

- As a user, I would like to be able to select a story template from a few
available options.
- As a user I would like to be able to choose whether my protagonist is male/female.
- As a user, I would like to be able to answer the prompts that correspond to my chosen template.
- As a user, I would like to be able to add my answers to the prompts into the full story.
- As a user, I would like to be able to read the complete story.
- As a user, I would like to be able to save my answers to the prompts before finishing answering 
(i.e. while still in the middle).
- As a user, I would like to be able to load my answers and continue answering the prompts where I left off.

### Phase 4: Task 2
Tue Mar 29 13:13:10 PDT 2022 <br />Submitted Answer: gold

Tue Mar 29 13:13:14 PDT 2022 <br />Submitted Answer: month

Tue Mar 29 13:13:18 PDT 2022 <br />Submitted Answer: painter

Tue Mar 29 13:13:22 PDT 2022 <br />Submitted Answer: rage

Tue Mar 29 13:13:28 PDT 2022 <br />Updated Answer From: month, To: year

Tue Mar 29 13:13:32 PDT 2022 <br />Submitted Answer: flu

Tue Mar 29 13:13:40 PDT 2022 <br />Updated Answer From: rage, To: happiness

### Phase 4: Task 3

If I were to refactor my project, I would change the relationship between StoryAppGUI 
with AnswerStoryGUI and DisplayStoryGUI from a usage one to an inheritance one.
Meaning, instead of passing a StoryAppGUI object as an argument to AnswerStoryGUI and 
DisplayStoryGUI, I would have AnswerStoryGUI and DisplayStoryGUI both extend StoryAppGUI.
That way they'll be able to directly access and use its fields rather than having to 
use the getters and setters to modify them.
