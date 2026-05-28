# Discussion 13 - "Verification and Validation"

#### Define both verification and validation, explaining how they are complementary but distinct ideas 
**Two phrases commonly used to distinguish verification and validation are** :
- *Verification* : Are we building the product right? That is, according to the specification we received.
- *Validation* : Are we building the right product? That is, the one that meets the customer or market needs.

Verification ensures a system conforms to its specifications, while validation checks if the system fulfills the customer’s needs. The two concepts are distinct because specifications may at times fail to meet customers’ needs.

#### Compare the benefits and costs of testing, then discuss whether or not it is worth the time and effort 
**Less Debugging**
- Tested code has fewer defects when it is submitted.
- Changes to a project that break a test can be quickly detected by test infrastructure and rolled back before the problem is ever released to production.

**Improved Documentation**
- Clear, focused tests that exercise one behavior at a time function as executable documentation. If you want to know what the code does in a particular case, look at the test for that case.
- Even better, when requirements change and new code breaks an existing test, we get a clear signal that the “documentation” is now out of date.

**Simpler Reviews**
- A code reviewer spends less effort verifying the code works as expected if the code review includes thorough tests that demonstrate code correctness, edge cases, and error conditions
- Instead of the tedious effort needed to mentally walk each case through the code, the reviewer can verify that each case has a passing test

**Fast, high-quality releases**
- With a healthy automated test suite, teams can release new versions of their application with confidence.
- Many projects at Google release a new version to production every day—even large projects with hundreds of engineers and thousands of code changes submitted every day. This would not be possible  without  automated testing.

#### Discuss at least five “big picture” project qualities testing seeks to improve (usability, for example) 

**Why is Importance of Software Testing? *Geeks_for_Geeks [article](https://www.geeksforgeeks.org/software-testing/software-testing-basics/)***

- *Defects can be Identified Early* : 
  - Software testing is important because if there are any bugs they can be identified early and can be fixed before the delivery of the software.
- *Improves Quality of Software* : 
  - Software Testing uncovers the defects in the software, and fixing them improves the quality of the software.
- *Increased Customer Satisfaction* : 
  - Software testing ensures reliability, security, and high performance which results in saving time, costs, and customer satisfaction. Helps with Scalability: Software testing type non-functional testing helps to identify the scalability issues and the point where an application might stop working.
- *Saves Time and Money* : 
  - After the application is launched it will be very difficult to trace and resolve the issues, as performing this activity will incur more costs and time. Thus, it is better to conduct software testing at regular intervals during software development.


#### List at least three different kinds of tests and discus when each should be used 
**Types of Software Testing? *Geeks_for_Geeks [article](https://www.geeksforgeeks.org/software-testing/automation-testing-software-testing/)***

1. *Unit tests*
- Unit testing is automated and is run each time the code is changed to ensure that new code does not break existing functionality. 
- Unit tests are designed to validate the smallest possible unit of code, such as a function or a method, and test it in isolation from the rest of the system.

2. *Integration tests*
- Integration testing is the process of testing the interface between two software units or modules. It focuses on determining the correctness of the interface. 
- The purpose of integration testing is to expose faults in the interaction between integrated units. Once all the modules have been unit-tested, integration testing is performed.

3. *Performance tests*
- Performance Testing is a type of software testing that ensures software applications perform properly under their expected workload. I
- t is a testing technique carried out to determine system performance in terms of sensitivity, reactivity, and stability under a particular workload.


#### Explain why DRY is often less desirable than DAMP in test code and vice versa in production code 

**DRY — "Don’t Repeat Yourself."** : 
- Software is easier to maintain if every concept is canonically represented in one place and code duplication is kept to a minimum. 
- This approach is especially valuable in making changes easier because an engineer needs to update only one piece of code rather than tracking down multiple references. 
- The downside to such consolidation is that it can make code unclear, requiring readers to follow chains of references to understand what the code is doing.

**DAMP — "Descriptive And Meaningful Phrases."** :
- A little bit of duplication is OK in tests so long as that duplication makes the test simpler and clearer. 
- These tests have more duplication, and the test bodies are a bit longer, but the extra verbosity is worth it. Each individual test is far more meaningful and can be understood entirely without leaving the test body.

In normal production code, that downside is usually a small price to pay for making code easier to change and work with. But this cost/benefit analysis plays out a little differently in the context of test code.

DAMP is not a replacement for DRY; it is complementary to it. Helper methods and test infrastructure can still help make tests clearer by factoring out repetitive steps whose details aren’t relevant to the particular behavior being tested. The important point is that such refactoring should be done with an eye toward making tests more descriptive and meaningful, and not solely in the name of reducing repetition.