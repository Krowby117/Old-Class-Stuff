# Discussion 10 - "Practical Small-scale Testing"
#### Readings from Software Engineering
- Software Engineering, Ch.8, 8.1-8.2 (15 pages)


#### Define unit testing and explain some of the best practices for choosing tests and using them 
***Unit Testing** (Ch.8 ph 232)*
- *Def:* Where individual program units or object classes are tested. 
- Unit testing should focus on testing the functionality of objects or methods. *(Ch.8 ph 232)*

***Two strateies for choosing test cases** (Ch.8 ph 234)*
- *Partition Testing* : where you identify groups of inputs that have common characteristics and should be processed in the same way. You should choose tests from within each of these groups
- *Guideline Testing* : where you use testing guidelines to choose test cases. These guidelines reflect previous experience of the kinds of errors that programmers often make when developing components2


#### Discuss how unit testing fits within the overall strategy of development testing 
There are three stages of development testing: *(Ch.8 ph 232)*
- ***Unit testing***
  - Focus on individual program units or object classes are tested.
- ***Component testing***
  - Focus on intergrating units to create components and testing the component interfaces that provide access to their functions.
- ***System testing***
  - Focuses on looking at the system as a whole and testing component interactions. 


#### Explain the key features of the test-driven development paradigm 
***Test-driven development (TDD)** (Ch. 8 pg. 242)* : an approach to program  development in which you interleave testing and code development. You develop the code incrementally, along with a set of tests for that increment. You don’t start working  on the next increment until the code that you have developed passes all of its tests.

***TDD Process Steps** (Ch. 8 pg. 243)* :
1. You start by identifying the increment of functionality that is required.
2. You write a test for this functionality and implement it as an automated test.  
3. You then run the test, along with all other tests that have been implemented.  
4. You then implement the functionality and re-run the test.
5. Once all tests run successfully, you move on to implementing the next chunk of 
functionality.

#### Identify the potential benefits and costs of test-driven development, and give specific examples of projects that might (and might not) benefit from its use 
***Some benefits of the test-driven development** (Ch. 8 pg. 244)* :
- *Code coverage* : Every code segment that you write should have at 
least one associated test and code is tested as it is written, so defects are discovered early in the development process.
- *Regression testing* : A test suite is developed incrementally as a program is developed. You can always run regression tests to check that changes to the program have not introduced new bugs.
- *Simplified debugging* : When a test fails, it should be obvious where the problem lies. The newly written code needs to be checked and modified.
- *System documentation* : The tests themselves act as a form of documentation that describe what the code should be doing. Reading the tests can make it easier to understand the code.

#### Stuff from outside sources
**From IBM's [TDD Article](https://www.ibm.com/think/topics/test-driven-development)**
- Programming in this style strengthens the relationship between coding, testing, and code design. While test-driven development might increase upfront development time, it has been demonstrated to improve code functionality and dexterity and save time overall.
- Developers write enough code to pass each test, then both the test and code are refined before moving onto a new test and then a new feature.
- The test driven development cycle follows a repeatable loop called the red-green-refactor cycle:
  - Red: Write a failing test for the intended software behavior. 
  - Green: Write enough extra to pass the test.
  - Refactor: Refine the code to meet simplicity standards as much as possible while still passing the test.

**From AWS' [Unit Testing Article](https://aws.amazon.com/what-is/unit-testing/)**
***When to not use Unit Testing***
- *When time is constrained* :
  - Writing new unit tests takes a significant amount of time. While input and output-based unit tests may be easy to generate, logic-based checks are more difficult.
  - When writing tests, refactoring opportunities also pop up
- *Design focussed applications* :
  - When the main system is concerned with look and feel rather than logic, there may not be many unit tests to run. Other types of testing, such as manual testing, are a better strategy than unit testing in these cases.
- *Rapidly evolving requirements* :
  - If requirements are likely to change often, there's not much reason to write unit tests each time a block of code is developed.
