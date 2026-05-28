# Discussion 06 - "Prototyping and Product Evolution"
#### Readings from multiple sources
- Software Enfineering, Ch 25 first 10 pages
- SemVer
- Git-Flow
- Trunk-Based Development

**Quotes taken from Software Engineering Ch. 25 page 731:**
- "Configuration  management  (CM)  is  concerned  with  the  policies,  processes,  and  tools for managing changing software systems (Aiello and Sachs 2011). You need to manage evolving systems because it is easy to lose track of what changes and component versions have been incorporated into each system version."
- "Configuration management is useful for individual projects as it is easy for one person to forget what changes have been made. It is essential for team projects where several developers are working at the same time on a software system."
- *Version Control* is a **part** of Configuartion management. The textbook states that  configuration  management  of  a  software  system  product  involves  four  closely related activities. With version control being one of the four, defined as:
    - "Version control involves keeping track of the multiple versions of system components and ensuring that changes made to components by different developers do not interfere with each other."

VIDEO GAMES WOOOOOOOOOOO, they always have the while X.Y.Z version codes
Like Terraria they're on like 1.4.9 right now

**From the *"Why Use Semantic Versioning*" section:**
"Without compliance to some sort of formal specification, version numbers are essentially useless for dependency management. By giving a name and clear definition to the above ideas, it becomes easy to communicate your intentions to the users of your software."

**From the *"Semantic Versioning Specification*" section:**
- A normal version number MUST take the form X.Y.Z where X, Y, and Z are non-negative integers, and MUST NOT contain leading zeroes. X is the major version, Y is the minor version, and Z is the patch version. Each element MUST increase numerically. For instance: 1.9.0 -> 1.10.0 -> 1.11.0.
- Once a versioned package has been released, the contents of that version MUST NOT be modified. Any modifications MUST be released as a new version.

For large systems, there is never just one “working” version of a system; there are always  several  versions  of  the  system  at  different  stages  of  development.  Several teams may be involved in the development of different system versions. 

Figure 25.2 shows situations where three versions of a system are being developed: **insert figure here**
- Version 1.5 of the system has been developed to repair bug fixes and improve 
the performance of the first release of the system. It is the basis of the second 
system release (R1.1).
- Version 2.4 is being tested with a view to it becoming release 2.0 of the system. 
No new features are being added at this stage.
- Version  3  is  a  development  system  where  new  features  are  being  added  in  
response  to  change  requests  from  customers  and  the  development  team.  This  
will eventually be released as release 3.0.

**GitFlow:**
- Focussed on having multiple 'feature' branches that are merged into the main branch upon version release
- Enables explicit versioning and long-term support. 
- Best for large scale projects like desktop apps and libraries

**Trunk-based:**
- Focussed on developers commiting small, frequent changes directly to a main 'trunk' branch
- Enables continuous intergration and allows code to always be releasable
- Best for smaller, quick service projects like web apps

**Distrubuted Version Control: *(Software Engineering Ch. 25 page 738)***
- Main idea is a master repository is created on a server and each developer clones the repository to their local device to make changes.
- "Developers work on the files required and maintain the new versions on their private  repository  on  their  own  computer.  When they have finished  making changes, they “commit” these changes and update their private server repository. They may then “push” these changes to the project repository or tell the integration manager that changed versions are available. He or she may then “pull” these files to the project  repository."
- This model of development has a number of advantages:
  1. It provides a backup mechanism for the repository. If the repository is corrupted, work can continue and the project repository can be restored from local copies.
  2. It allows for offline working so that developers can commit changes if they do not have a network connection.
  3. Project support is the default way of working. Developers can compile and test the entire system on their local machines and test the changes they have made.