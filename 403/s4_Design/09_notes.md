# Discussion 08 - "Design Properties and Code Quality"
#### Readings from multiple sources
- Software Engineering: A Modern Approach, Ch.5 (~17 pages) 
- Refactoring: Improving The Design of Existing Code, Ch.3 (8 pages) 

*The most fundamental problem in computer science is problem decomposition: how to take a complex problem and divide it up into pieces that can be solved independently. – **John Ousterhout***

#### Discuss at least five design properties and give a real example of each
**1. Conceptual Integrity** *(Section 5.2)*:
- Idea that software should not be a collection of features lacking coherence and cohesion, but instead should possess conceptual integrity to enhance system usability and comprehension 
- Question: Do you guys think there should be a central authority over what functionalities? Or is it more of a team decision.

**2. Information Hiding / Encapsulation** *(Section 5.3)*:
- Idea that each class should hide its internal design decisions, like data structures and algorithms, and only show what parts are actively used.
  - Public methods allow use of internal private methods and data.
- *Parallel Development:* Devs can work on different parts of the system without breaking eachothers code.
- *Changeability:* Easy to change or optimize a class without changing its overlying structure.
- *Comprehensibility:* Allows easier understanding of the class, can understand the public face of the class without fully knowing how its internals work.

**3. Cohesion** *(Section 5.4)*:
- Every class should have a single responsibility in the system. 
- It simplifies implementation and maintenance of a class.
- Makes it easier for a single developer to maintain a class.

**4. Coupling** *(Section 5.5)*:
- Coupling refers to the strength of the connection between to modules. There are two main types of coupling:
  - *Acceptable Coupling* 
    - Happens when class A only uses the public method of class B
    - Changes to class B rarely effect class A because it's public interface doesn't change much
  - *Poor Coupling*
    - Happens when a change in a class easily breaks another
    - Class A directly accesses files and/or databases used by class B
    - Classes A and B share global variables or data structures that they interact with

**5. You Aren't Gonna Need It (YAGNI)** *[geeksforgeeks](https://www.geeksforgeeks.org/software-engineering/what-is-yagni-principle-you-arent-gonna-need-it/)*
- If it isn't part of the necessary requirements for the software than it probably isn't something you're going to need.
- Put off additional features until they are absolutely needed.


#### Identify a project that did incorporate a specific design property or could have benefited from incorporating one.3
**Apple design properties:** *[Apple Developer](https://developer.apple.com/design/human-interface-guidelines/designing-for-ios/)*
- Information Hiding / Encapsulation
- Cohesion
- Simplicity Consistency
- UX Centered Design

**Microsoft Reseach:** *[Hints and Principles for Computer System Design by Butler Lampson](https://www.microsoft.com/en-us/research/wp-content/uploads/2019/09/Hints-and-Principles-v1-full.pdf)*
- Lampson defines three key principles:
  - *Approximate rather than exact*, perfect or optimal results are almost always good enough, and often much easier and cheaper to achieve. Loose rather than tight specs are more likely to be satisfied, especially when there are failures or changes.
  - *Incremental design*. The most important is to build the system out of independent, isolated parts called modules with interfaces, that you can put together in different ways. Iterating the design rather than deciding everything up front keeps you from getting too far out of touch with customers, and extensibility makes it easy for the system to evolve.
  - *Divide and conquer* is the most important idea. Focuses on breaking a problem into smaller parts and designing the parts so they can be developed and tested independently.


#### Explain what code smells are for (and what they aren’t for) and give at least five examples 
Code smells are basically just vibe checking your code to figure out if you need to refactor. Like if this one part feels off its probably a good idea to refactor it.

**Duplicated Code**: (pg. 63)
- Multiple instances of the same code block within a class.
- Can take it out and create a new method.

**Long Method**: (pg. 64)
- Method that either is trying to do too much, are hard to undrstand, or just has too many lines.
- Change it to be me multiple methods or just have less lines of code.

**Divergent Change**: (pg. 66)
- A class is changed too much for many different reasons.
- Usually a sign that the class is doing too much and could be broken up.

**Speculaitve Generality**: (pg. 68-69)
- Code written for propoposed future needs that aren't required. This adds complexity and overhead that isn't needed.
- Focus on features that are basic requirements and required now.

**Comments** (pg. 71)
-  While commenting code is always a good thing, sometimes comments are used to cover up bad and/or messy code.
-  Too many comments may mean the code is badly written or just messy and could use some refactoring.
