# Discussion 08 - "Software Design Principles"
#### Readings from multiple sources
- Software Enfineering, Ch 5 first 15 pages
- Software Enfineering, Ch 5 first 12 pages

## Discuss the role of system models in the design process Ch. 5 page 139

System modeling is the process of developing abstract models of a system, with each model  presenting  a  different  view  or  perspective  of  that  system.  System  modeling now  usually  means  representing  a  system  using  some  kind  of  graphical  notation based on diagram types in the Unified Modeling Language (UML)

Models of the existing system are used during requirements engineering. They help clarify what the existing system does, and they can be used to focus a stakeholder discussion on its strengths and weaknesses.

Models  of  the  new  system  are  used  during  requirements  engineering  to  help  explain the proposed requirements to other system stakeholders. Engineers use these models to discuss design proposals and to document the system for implementation. 

You  may  develop  different  models  to  represent  the  system  from  different   perspectives. For example:
1.  An  external  perspective,  where  you  model  the  context  or  environment  of  the  
system.
2. An interaction perspective, where you model the interactions between a system 
and its environment, or between the components of a system.
3. A structural perspective, where you model the organization of a system or the 
structure of the data processed by the system.
4. A behavioral perspective, where you model the dynamic behavior of the system 
and how it responds to events.

## Explain how context, interaction, and structural models support the design process and what kinds of boundaries, dependencies, and relationships each identifies 
*Context Models* (pg. 121–123)
- Show the system in its environment — what’s inside vs. outside.
- Identify external entities (people, systems, hardware) that interact with the system.
- Useful for defining the system boundary and high-level dependencies.

*Interaction Models* (pg. 124–128)
- Focus on dynamic behavior — how the system interacts with users, other systems, or its own components.
- Often represented with use case diagrams or sequence diagrams.
- Highlight event ordering, communication flows, and interface boundaries.

*Structural Models* (pg. 129)
- Describe the static structure of the system.
- Show components, classes, data, and their relationships (inheritance, associations, composition).
- Provide the blueprint for organizing the system in design and implementation.

## Compare the concept of “architecture in the large” to “architecture in the small” Ch. 5 Pg. 169

***Architecture in the small*** is concerned with the architecture of individual programs. At this level, we are  concerned  with  the  way  that  an  individual  program is decomposed into components. 

***Architecture  in  the  large***  is  concerned  with  the  architecture  of  complex  enterprise systems that include other systems, programs, and program components. These  enterprise  systems  may  be  distributed  over  different  computers,  which may be owned and managed by different companies.

## List at least five different architecture patterns and briefly describe the key ideas and concepts for each (From CH. 6)

**(MVC) Model-View-Controller** *(page 176)*
- Seperates the system into three logical components that interact with eachother. model to manage the system data and associated operations. View defines and manages how the data is presented. Controller managers user interaction and passes these inputs to the View and Model

**Layered Architecture** *(page 178)*
- Organizes the systems into layers, with related functionality associated with each layer. A layer provides services to the layer above it, so the lowest layers represent core services that are likely to be used througout the system.

**Repository Architecture** *(page 179)*
- All data in a system is managed in a central repository that is accessible to all system components. Components do not interact directly, only through the repository.

**Client-Server Architecture** *(page 178)*
- The system is presented as a set of services, with each service delivered by a seperate server. Clients are users of the services and access servers to make use of them.

**Pipe and Filter Architecture** *(page 178)*
- The processing of the data in a system is organized so that each processing component (filter) is desrecete and carries out one type of data transformation. The data flows (as in a pipe) from one component to another for processing.
