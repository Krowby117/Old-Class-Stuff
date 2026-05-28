# Discussion 10 - "Practical Small-scale Testing"
#### Readings from Software Engineering at Google
- [Software Engineering at Google](https://abseil.io/resources/swe-book/html/ch10.html) - ~24 Pages

What Qualifies as Documentation?
- When we refer to “documentation,” we’re talking about every  supplemental text that an engineer needs to write to do their job: not only standalone documents, but code comments as well.

#### Explain how to tell the difference between good and bad documentation 
*The Parameters of Good Documentation*:
- There are usually three aspects of good documentation: completeness, accuracy, and clarity.
- A “good document” is defined as the document that is doing its intended job. 
  - A reference document wants to be complete and cover all aspects, but may lose some clarity. 

*Documentation Philosophy*:
- Answers three main questions:
  - Who is this for?
  - What is this for?
  - Why was it written?

*Simple descriptors of bad documentation*:
- Documentation that is out of date and/or hasn't been reviewed in a while.
- Too wordy and poorly organized.
- It mixes types and/or is trying to do too much within one document.

#### Give at least three benefits of maintaining quality documentation for a software project 
*Sanity Check*:
- Often, writing of the documentation itself leads engineers to reevaluate design decisions that other wise wouldn't be questioned.
- If you can'y exlain and/or define it, you probably haven't designed it well enough.

*Increased Professionalism*:
- Developers will naturally assume that a well-documented API is a better-designed API.
- Whether a product has good documentation is usally a pretty good indicator of how well a product will be maintained.

*Code rationale*:
- Good comments help out a great deal when you're staring at code you wrote two years ago, trying to figure out whats wrong.

#### Discuss at least four high-level principles that can help keep the quality and usefulness of documentation high 
***Know Your Adience***:
- In some cases, different audiences require different writing styles, but in most cases, the trick is to write in a way that applies as broadly to your different audience groups as possible.
- Chances are, you have multiple audiences based on one or more of the following criteria:
  - Experience level
  - Domain knowledge
  - Purpose
- Another important audience distinction is based on how a user encounters a document:
  - Seekers : they know what they want and want to know if what they are looking at fits the bill. A key pedagogical device for this audience is consistency.
  - Stumblers : might not know exactly what they want. They might have only a vague idea of how to implement what they are working with. The key for this audience is clarity.

***Treat Documentation like Code***:
- Like a programming language, it has rules, a particular syntax, and style decisions, often to accomplish a similar purpose as that within code: enforce consistency, improve clarity, and avoid (comprehension) errors.
- *Docs as Code*:
  - Documentation should follow the same workflows as development. This enables a culture where writers and developers both feel ownership of documentation, and work together to make it as good as possible.

***Single-purpose documentation***:
- A document should have a singular purpose that it sticks to. A reference document should not attempt to also be a tutorial.
- If adding something to the page doesn't make sense, you probably want to find or create another document for that purpose.

#### Give at least four examples of “best practices” for writing documentation within code 
- *Keep things together*:
  - Place documentation close to the code that it is documenting.
  - Makes it easier to find documentation for code you are looking at.
- *Consistent Formatting and Style*:
  - Keeping everything within the same style makes it easier to scan through code and documentation to find exactly what you are looking for.
- *Comment the Why*:
  - Document they why in code comments not just in the file documentation.
  - Doing this can help future code maintainers or reviewers to better understand your code.
- *Keep it fresh*:
  - Keep everything up to date, when code is updated so should its comments/documentation.
  - Adding a note for when the code was last reviewed to ensure its freshness.


#### Discuss how much personal responsibility to build technical writing skills for the purpose of creating good documentation developers should take on

- Technical writers and project managers may help, but software engineers will always need to write most documentation themselves. Engineers, therefore, need the proper tools and incentives to do so effectively.
  
- Because they are a limited resource, technical writers should generally focus on tasks that software engineers don’t need to do as part of their normal duties. Usually, this involves writing documents that cross API boundaries. 
- A technical writer is better able to stand in as a person unfamiliar with the domain. In fact, it’s one of their critical roles: to challenge the assumptions your team makes about the utility of your project.

- *(Personal Opinion)* : I think everyone needs atleast a basic level of understanding when it comes to documentation. Especially for commenting code and sharing details with peers. This is ussually why group projects SUCK.