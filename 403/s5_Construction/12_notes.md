# Discussion 10 - "Practical Small-scale Testing"
#### Readings from User Stories Applied
- User Stories Applied, Ch. 8 (7 pages)
- User Stories Applied, Ch. 9 (9 pages)
- User Stories Applied, Ch. 10 (6 pages)

#### Story Point notes from Chapter 8 
**Learning Objectives** :
1. Discuss the process of estimating stories with story points and recommended best  practices for making estimations 
2. Explain what should (and shouldn’t) be inferred from story point estimates
   
**Estimating Process** :
- *Estimating (pg. 89)* :
   - A story is chosen at random by the customer and developers ask as many questions as possible.
   - After all questions are asked each developer secretly writes an estimate down.
   - The estimates are revealed and disucssed as a team until a single estimate is chosen for the story.
- *Triangulating (pg. 90-91)* :
  - Estimating a story based on its relationship to one or more other stories.
  - A story of size 2 should be bigger than a size 1 and smaller than a size 3.
  - Sort story cards based on their size to help visualize the process.

**Best Practices** :
- When the programmers estimate a story, they  should  include  everything  they’ll need to do to complete the story. They need to factor in such things as testing their code, talking to the customer, perhaps helping the customer plan or automate acceptance tests, and so on. *(Ch 8, pg. 89)*
- Since story estimates are owned by the team it is important to have a reason-
able portion of the team involved in coming up with the estimates. *(Ch 8, pg. 88)*

**Other Notes** :
- *Estimates are different from team to team. They are not an ideal one size fits all.*
  - A nice feature of story points is that each team defines them as they see fit. One team may decide to define a story point as an ideal day of work. Another team may define a story point as an ideal week of work. Yet another team may define a story point as a measure of the complexity of the story. *(Ch 8, pg. 87)*
- *They are developer focussed estimates* 
  - The  customer  participates while  the  programmers  estimate,  but  she  isn’t  allowed  to  contribute  her  personal estimates or editorialize when she hears an estimate she disapproves of. *(Ch 8, pg. 88)*
- *They do not convert directly to a time measurement*
  - One team may decide to define a story point as an ideal day of work. Another team may define a story point as an ideal week of work. Yet another team may define a story point as a measure of the complexity of the story. Because  of  the  wide  variety  of  meanings  for  story  points,  Joshua Kerievsky has suggested that story points represent Nebulous Units of Time, or NUTs. *(Ch 8, pg. 87)*
- *They are not meant to be perfect representations*
  - The  goal  is  for  the  estimators  to  converge  on  a  single  estimate  that  can  be used for the story. The  point  is  reasonableness  not  absolute  precision.Yes, the developers could probably talk longer and reach consensus on three or four story points, but the time spent doing so isn’t worth it. *(Ch 8, pg. 90)*

#### Software release scheduling notes from Chapter 9
**Learning Objectives** :
1. Explain how the desired scope, priorities of stories, and expected time are taken into account when planning a software release

**When do we want the release?** *(Ch. 9, pg. 98)*
- Ideally, the developers and the customer can talk about a range of dates, rather
than  a  specific  date.
- If a team can start release planning with a range of acceptable dates they will have more flexibility  in  timing  releases.  For  example,  starting  with  a  date  range  in  mind enables a team to make statements like “After six or seven iterations we should have the minimum functionality and maybe ten to twelve before we have everything on the 1.0 wish list.”
- In some cases the date truly is fixed. If  this  is  the  case,  release  planning  is  actually  a  bit  easier  as  there  are fewer  variables  to  consider.  However,  the  decisions  about  which  stories  to include will usually be more difficult.

**What would you like in it?** *(Ch. 9, pg. 98)*
- In order to plan a release, the customer must prioritize the stories.
- A tighter schedule means more stories that cannot be implemented.
- *MoSCoW* :
  - Must have
  - Should have
  - Could have
  - Won't have the time

**Prioritizing the Stories?** *(Ch. 9, pg. 99)*
- *Technical factors* :
  - The risk that the story cannot be completed as desired
  - The impact the story will have on other stories if deffered

- *Customer and user factors* :
  - Desirability of the story to a broad base of users or customers
  - The desirability of the story to a small number of important users or customers
  - The cohesiveness of the story in relationship to other stories

- Collectively,  the  developers  have  a  sequence  in  which  they  would  like  to implement the stories, as will the customer. When there is a disagreement to the sequence, the customer wins. Every time

- However,  customers  cannot  prioritize  without  some  information  from  the development  team.  Minimally,  a  customer  needs  to  know  approximately  how long  each  story  will  take. She uses the estimates, along with her own assessment of the value of each story, to sort the stories so that they maximize the value delivered to the organization.

#### Iteration planning notes from Chapter 10
**Learning Objectives** :
1. Discuss the activities involved in iteration planning 
2. Explain the kinds of adjustments a team might need to make during an iteration or release when reality deviates from the expected plan 

**Iteration planning activities**
1. *Discuss a story (Ch. 10, pg. 110)*
   - The customer starts with her highest priority story and reads it to the developers. The developers then ask questions until they understand the story sufficiently to disaggregate it into constituent tasks.
   - It is not necessary  to  understand  every  detail  of  the  story. The developers will still be able to work out the fine details of the stories with the customer after the planning meeting.
2. *Disaggregate the story into its constituent tasks (Ch. 10, pg. 111)*
   - Even though stories are small enough to serve as units of work, projects are generally well served by disaggregating them into even smaller tasks.
   - Suppose  we  have  the story  “A  user  can  search  for  a  hotel  on  various  fields.”  That  story  might  be turned into the following tasks:
        - code basic search screen
        - code advanced search screen
        - code results screen
        - write and tune SQL to query the database for basic searches
        - write and tune SQL to query the database for advanced searches
        - document new functionality in help system and user’s guide
3. *One developer accepts responsibility for each task (Ch. 10, pg. 113)*
   - Once all the tasks for a story have been identified, someone on the team needs to volunteer to perform each task.
   - This person assumes responsibility for completing the task. If he needs additional information from the customer, he gets it. If he chooses to pair program, he solicits a pair. Ultimately, though, it is his responsibility to make sure the task gets completed during the iteration.
   - As  the  team progresses  through  the  iteration,  learning  more  about  the  tasks,  finding  some work easier than planned but some harder than planned, commitments need to change.
4. *Developers individually estimate the tasks they've accepted (Ch. 10, pg. 113)*
   - Each developer is responsible for estimating the amount of work she has accepted responsibility for. The best way to do this is still to estimate in ideal time.
   - Once a developer has estimated each of her tasks, she needs to add them up and make a realistic assessment about whether they can all be completed during the  iteration.
   - Three options if overswamped :
     - keep all the tasks and hope
     - request someone take some of the tasks
     - talk with the customer about dropping a story and/or feature

**Possible Adjustments**

- *Re-distrubute Tasks (Ch. 10, pg. 113)*
    - If near  the  end  of  the  iteration,  one  developer  is  not  going  to  complete  all  the tasks she accepted, then others on the team are expected to take on that work to the extent possible.
    - At the end of an iteration no one can say “I finished my work, but Tom
    still had a few tasks left.”

- *Removal of features (Ch. 10, pg. 114)*
  - If a developer is overswamped with work, they can talk to the customer about pushing back a story and/or feature back.
  - talk  with  the  customer  about  dropping  a  story  (or  splitting  a  story  and dropping part of it)
