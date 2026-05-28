/*Write the queries under each comment. Do NOT remove the comments. Leave a single blank line between the query and the next comment.*/

/*1. Query to retrieve first name and last name of all male employees with salary more than 30000.*/
select fname, lname
from employee
where sex = 'm' and salary > 30000;

/*2. Query to retrieve locations of Research department projects.*/
select plocation
from project, department
where dname = "research" and dnumber = dnum;

/*3. Query to retrieve first name, last name, and SSN of all employees who work more than 9 hours on project #2.*/
select fname, lname, ssn
from employee, works_on
where hours > 9 and pno = 2 and essn = ssn;

/*4. Query to retrieve name, date of birth and relationship of all female dependents of employees who work for department #5.*/
select dependent_name, dependent.bdate, relationship
from dependent, employee
where dependent.sex = 'f' and essn = ssn and dno = 5;

/*5. Query to retrieve first name, last name and salary of employees who manage departments with projects located in Houston.*/
SELECT fname, lname, salary
FROM employee, department, project
WHERE project.plocation = 'Houston' and dnum = dnumber and mgr_ssn = ssn;

/*Submit this sql file containing the five queries to Canvas*/