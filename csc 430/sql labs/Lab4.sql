/*Write the queries under each comment. Do NOT remove the comments. Leave a single blank line between the query and the next comment.*/

/*1. Create a stored procedure that counts the total number of employees in a given department number.*/
delimiter $$
create procedure getNumEmployees (IN dnum INT, OUT numEmp)
begin
    select count(*) into numEmp
    from employee
    where dnum = dno;
end $$
delimiter ;

/*2. Create a stored procedure to get an employee full name given their SSN.*/
delimiter $$
create procedure getEmployeeName (in essn VARCHAR(9), out ename VARCHAR(50))
begin
    select concat(fname, ' ', lname) into ename
    from employee
    where ssn = essn;
end $$
delimiter ;

/*3. Create a stored procedure to get the max number of hours an employee worked on any of their projects.*/
delimiter $$
create procedure getMaxHours (in ssn VARCHAR(9), out mhours int)
begin
    select max(hours) into mhours
    from works_on
    where essn = ssn;
end $$
delimiter ;

/*4. Create a function to get the name of an employee by their SSN.*/
delimiter $$
create function getEmployeeName (essn VARCHAR(9))
returns VARCHAR(50)
deterministic
reads sql data
begin
    declare ename VARCHAR(50);

    select concat(fname, ' ', lname) into ename
    from employee
    where ssn = essn;

    return ename;
end $$
delimiter ;

/*5. Create a function to calculate the age of an employee given the date of birth.*/
delimiter $$
create function getAge (bday date)
returns int
not deterministic
contains sql
begin
    return timestampdiff(year, bday, curdate());
end $$
delimiter ;


/*Submit this sql file containing the five queries to Canvas*/
