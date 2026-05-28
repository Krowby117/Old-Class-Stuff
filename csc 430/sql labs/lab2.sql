/*Write the queries under each comment. Do NOT remove the comments. Leave a single blank line between the query and the next comment.*/

/*1. Query to retrieve first name and last name of employees who have a birthday in January.*/
select fname, lname
from employee
where bdate like "____-01-__";

/*2. Query to show the resulting salaries if every employee working on the ‘ProductX’ project with a salary between $20000 and $40000 is given a 15% raise.*/
select salary*1.15
from employee, works_on, project
where salary between 20000 and 40000 and ssn = essn and pno = pnumber and pname = "ProductX";

/*3. Query to retrieve first name, last name and SSN of employees whose salary is less than the salary of any of the employees in department 4.*/
select fname, lname, ssn
from employee as e
where e.salary < any(
    select salary
    from employee
    where dno = 4
);

/*4. Query to retrieve SSNs of all female employees who work on project numbers 10, 20, or 30.*/
select ssn
from employee, works_on
where sex = 'f' and ssn = essn and pno in (10, 20, 30);

/*5. For each project on which less than three employees work, retrieve the project number, the project name, and the average salary of employees who work on the project.*/
select pnumber, pname, avg(salary) as avg_salary
from employee, project, (
        select count(essn) as num_workers, pno
        from works_on
        group by pno
) as cworks_on
where cworks_on.pno = pnumber and cworks_on.num_workers < 3
group by pnumber;

/*Submit this sql file containing the five queries to Canvas*/