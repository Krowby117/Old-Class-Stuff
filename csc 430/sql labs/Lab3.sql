/*Write the queries under each comment. Do NOT remove the comments. Leave a single blank line between the query and the next comment.*/

/*1. Write a trigger to create a default department location in Bellaire every time new department is inserted into database.*/
DELIMITER $$
create trigger new_department
before insert on department
for each row
begin
    insert into dept_locations (dno, dlocation)
    values (NEW.dnumber, "Bellaire");
end $$
DELIMITER ;

/*2. Write a trigger to enforce the following constraint when updating employee salary: employee salary must not be higher than 1.5 times the salary of his/her direct supervisor. If it is, then display message – "Supervisee salary is much higher than supervisor salary".*/
DELIMITER $$
create trigger max_pay
before update on employee
for each row
begin
    declare mgr_salary DECIMAL(10,2);
    declare msg VARCHAR(255);

    select salary into mgr_salary 
    from employee
    where ssn = NEW.super_ssn;

    if (new.salary > (mgr_salary * 1.5))
    then
        SET MSG = 'Supervisee salary is much higher than supervisor salary.';
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = MSG;
    end if;
end $$
DELIMITER ;

/*3. Write a trigger to update supervisor SSN of an employee with the SSN of the department manager where he/she works BEFORE INSERT(ing) the record into employee table IF the supervisor SSN attribute is empty or NULL.*/
DELIMITER $$
create trigger new_sup
before insert on employee
for each row 
begin
    if (NEW.super_ssn = "" OR NEW.super_ssn is null)
    then
        set NEW.super_ssn = (select mgr_ssn
                             from department
                             where dnumber = new.dno);
    end if;
end $$
DELIMITER ;

/*4. Create a view that displays first name, last name, SSN, salary, and department name for each department manager.*/
create view mgr_info as
    select fname, lname, ssn, salary, dname
    from employee e, department d
    where e.ssn = d.mgr_ssn;

/*5. Create a view that displays project number, project name, controlling department number, controlling department name, total number of employees, total salary paid, and average hours worked for each project.*/
create view proj_info as
    select p.pnumber, p.pname, p.dnum, d.dname, info.num_workers, info.tot_salary, info.avg_hours
    from project p, department d, (
        select pno, count(essn) as num_workers, sum(salary) as tot_salary, avg(hours) as avg_hours
        from works_on, employee
        where essn = ssn
        group by pno
    ) as info
    where info.pno = p.pnumber and p.dnum = d.dnumber;

/*Submit this sql file containing the five queries to Canvas*/
