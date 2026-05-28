# Discussion 13 - "Refactoring and Code Evolution"

#### Explain the idea of Lehman’s Laws and how they relate to refactoring
A set of empirical laws concerning aging, internal quality, and the evolution of software systems.

**Lehman's Laws** :
1. A system must be continuously adapted to its environment. This process should continue until it becomes more advantageous to replace the system with a new one.
2. As a system undergoes maintenance, its internal complexity increases unless deliberate efforts are made to reduce this complexity.

"The second law states that a system’s internal quality naturally deteriorates as it undergoes maintenance and evolution. This deterioration can be prevented if specific work is done to maintain internal quality." *Literally refactoring*.

In practice, refactoring allows code to continually adapt, which satisfies Law 1. 

Without refactoring, systems tend to become rigid and brittle over time, making adaptation more costly.

#### Contrast writing code that “works now” versus code that “works indefinitely”

**Works Now** :
- We also find developers of short-lived code in common industry settings. Engineers at an early-stage startup might rightly choose to focus on immediate goals over long-term investments: the company might not live long enough to reap the benefits of an infrastructure investment that pays off slowly. 
- A serial startup developer could very reasonably have 10 years of development experience and little or no experience maintaining any piece of software expected to exist for longer than a year or two.

**Works Indefinitely** :
- "On the other end of the spectrum, some successful projects have an effectively unbounded life span. As their lifetimes grow, these long-lived projects eventually have a different feel to them than programming assignments or startup development."

Kinda just depends on the type of project and scale of the company. 

Both have their purposes, but which one you select depends crucially on the expected life span of the code in question.  We’ve taken to saying, “It’s programming if 'clever' is a compliment, but it’s software engineering if 'clever' is an accusation.”

#### Explain Hyrum’s Law
If you are maintaining a  project that is used by  other engineers, the most important lesson about “it works” versus “it is maintainable” is what we’ve come to call Hyrum’s Law.

**Hyrum's Law** :
- With a sufficient number of users of an API, it does not matter what you promise in the contract: all observable behaviors of your system will be depended on by somebody.
- Given enough time and enough users, even the most innocuous change will break something

*In English*:
- Even if your API doesn't explicitly guarantee certain behaviors, once they become observable your user's will start to depend on them.
- You must assume that users depend on every behavior of your API, not just whats documented.


#### Discuss how Hyrum’s Law applies (or doesn’t) to projects you’ve worked on in the past and your upcoming senior capstone project

This idea that changes often lead to more changes.

I haven't really had any prior projects that feel like Hyrum's rule applied, but with the size and scale of our upcoming capstone projects I know that keeping these things in mind and planning ahead will be a major help.

Another thing that just kinda depends on the scale of the project and the size of the audience is trying to cater to. It's hard to quanitfy if or when Hyrum's Law will apply but I know its good to keep in mind when developing.

#### Describe at least five refactoring strategies, including a summary and example application for each

**Extract Method** :
- It aims to extract some piece of code from a method **A** and moving it to a new method **A**, and having method **A** include a call to method **B**.
- It aims to eliminate **code duplication** and increase readability.
- Example: Taking a l200+ line "onCreate()" method and putting all of its major parts into smaller helper functions.

**Inline Method** :
- Taking a method that only includes 1 or 2 lines of code and just moving that code to wherever the function is being called.
- Gives the example of a "writeContentToFile()" function that includes a single line of code. It's better to just put that single line wherever the function is being called.\
  
**Move Method** :
- Taking a method from class A and moving it to class B. Usually because it uses more services from class B than those in class A.
- Example: Given a Person class and a Car class. Creating a "travel" method makes more sense in the Car class.

**Extract Class** :
- For when class A has too many responsibilities and attributes. Often makes sense to have two serpate class that are connected to eachother.
- Example: A Person class that includes a phone number and area code, to declutter the Person class make a second Phone class and include a reference to it.

**Renaming** :
- *Phil Karlton : "There are only two hard things in computer science: cache invalidation and naming things"*
- An element may need to be renaimed if its original name was bad or if it's purpose has changed and its name outdated.
- Example: Function ButtLicker now licks Shoes instead, should be named ShoeLicker

**Remove Dead Code** :
- Involves deleting any code elements that are no longer being used.
- Example: Literally anything not being used.