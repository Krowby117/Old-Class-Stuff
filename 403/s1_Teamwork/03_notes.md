# Discussion 03 - "Cooperative Programming"
#### Readings from *multiple sources*
- Software Engineering      : Section 3.2.4 - Pair Programming (2 pages)
- Pro Git                   : Section 1.1   - About version control (4 pages)
- Google Engineering Practices Documentation : 39 pages 

## Learning Outcomes
#### 1. Contrast at least two potential benefits and costs of pair programming

Two programmers working together on the same task can increase the amount of bugs and errors that are cuaght in real time, however it can dramatically increase the amount of time it takes to get a single task done.

Can allow for knowledge sharing and skill discovery between members of the group. It helps devs share the skills that can improve the efficiency of their programming later down the line. However, there is the risk of two members not melding well when working together due to difference in styles or personalities.

#### 2. Defend the use of version control for cooperative programming against potential arguments that it may be too confusing to use or learn to be worth the time

Personal Experience: In the Game Design class last Spring. Version control saved our project after a forced push broke the newest version.

Personal Experience: I find most people tend to be scared of git because they don't want to work out of a terminal, but its a very good skill to have. Also GitHub desktop is really nice and easy to use.

***About Version Control Systems***
- It allows you to revert selected files back to a previous state, revert the entire project back to a previous state, compare changes over time.
- Using a VCS also generally means that if you screw things up or lose files, you can easily recover. In addition, you get all this for very little overhead.

A ***Centralized Cersion Control System*** contains a single server that contains all of the versioned files. This approach allows everyone to know, to a certain degree, what everyone else is doing. It is also far easier to admninistrate a CVCS than it is a local databse since administrators have control over who can do what.

A ***Distributed Version Control System*** doesn't just give users access to the most recent version, but instead the entire repository and its history. This allows user repositories to be copied to be copied back up to the server if there is ever any issues. Every clone is an exact copy.

Keeping any type of version control system alows for easier access to the entire project's files and allows devs to more easily collaborate together. It also ensures that, if the files are ever corrupted or the new version broken, devs and roll back to a previous working version to continue their work.


#### 3. Compare and contrast the responsibilities of the code reviewer and change author in the code review process described in Google Engineering Practices Documentation 

**Responsibilities of the code reviewer:**
- Taken from: [The Standard of Code Review](https://google.github.io/eng-practices/review/reviewer/standard.html)
  - Reviewers should favor approving a CL once it definitely improves the overall code health of the system, even if it isn’t perfect.

- Taken from: [Navigating a CL in Review](https://google.github.io/eng-practices/review/reviewer/navigate.html)
  - Making sure the CL documentation makes sense.
    - Does the change make sense?
    - Is the change well designed and implimented?

Speed of Code Reviews
reviewes should be done in a timely manner (if possible) one business day max
if you are in the middle of a focused task, such as writing code, don’t interrupt yourself to do a code review

**Responsibilities of the change author:**
- Taken from: [Writing Good CL Descriptions](https://google.github.io/eng-practices/review/developer/cl-descriptions.html)
  - The CL must effectively communicate:
    - What changes have been made to the code base. Summarize all the major changes.
    - Why the specified changes were made. 
  - First line should be a complete summary of all the changes made.
  - Body should fill in all the extra details and reasonings behind the changes made. Include any relavent information, like information about why the new approach is better than the old one.
  
- Taken from: [Small CLs](https://google.github.io/eng-practices/review/developer/small-cls.html)
  - The change author needs to be aware of when their work would be better suited as multiple indivudal tasks that can be broken up into small CLs
  - When splitting work into smaller tasks and CLs they need to know the best way to do that; splitting by file, small changes, horizontally, or vertically.
  - Refactoring should take place in its own Cl seperate from new features or code.


#### 4. Decide on 4-5 principles from the Google Engineering Practices Documentation to prioritize to form a simplified code review guide for a fictional team 
**All answers are from this site:** [What to look for in a code review](https://google.github.io/eng-practices/review/reviewer/looking-for.html)

***Design***
-  Does it make sense for the new code to be here? 
-  Does it intergrate well with the rest of the system?

***Complexity***
- Is the CL more complicated than it should be?
- Should be easy to read by other devs.
- Should be easy to modify to work with any new updates.

***Comments***
- Is the CL include well written comments that explain the new code?
- Should explain why the code exists and what it accomplishes.

***Consistency***
- The new code should maintain the same style as the rest of the project.
- If reformatting and adding new code, avoid confusion by:
  - Reformat the existing code base and send in as one CL
  - Then add the new code / functionality as a new CL

***Documentation***
- If the CL changes how users build, test, or interact with the code then the changes need to be well documented.
- If functionality is removed, should it also be removed from the CL.

## General Notes
***CL - Change List***

Small CLs are usually preffered. Its best to document one self-contained change rather than various unrelated changes all in one large CL.

