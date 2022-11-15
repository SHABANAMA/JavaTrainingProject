# JavaTrainingProject
# studentmonitoringapplication

create database studentmonitoringapp;
use studentmonitoringapp;

Enter Admin details:-
insert into user_details(dob,email,first_name,last_name,pass_word,phone,user_name) 
values('AdminDOB','admin@gmail.com','AdminName','AdminLastName', 'AdminPassword', 
'AdminPhone', 'adminUserName');

select * from user_details;


select * from student_academic_details;
select * from course_details;
select * from registered_course_details;

Sample inputs in json:

format of admin entering data student update data:-

{
	"user":{
        "firstName" : "Athira",
        "lastName" : "K S",
        "email" : "athira@gmail.com",
        "phone" : "8281556448",
        "dob" : "10-05-1997",
    },
    "studentAcademic": {
        "qualification" : "MTech",
        "college" : "Sample College",
        "university" : "KTU",
        "passoutYear" : "2018"
    }
}


format of admin entering course details:-

{
	"course":{
        "courseName" : "Angular and React",
        "description" : "Welcome to attractive angular+React designing", 
        "fees" : "30000.0"
    }
}

format of student regitering for a course:-

{
    "courseName" : "Angular and React",
    "feesPaid" : "7000.0",
    "feesPaidDate" : "09-11-2022"
}

format of authentication data:-

{
    "userName" : "usernamehere",
    "passWord" : "passwordhere"
}

trigger and function created to calculate balance and save:-

trigger:-

Delimiter //
create trigger set_balance_fee
before insert on registered_course_details
for each row
set new.bal_fees_to_pay = get_course_fee(new.course_id) - new.fees_paid ;
//

function:-

CREATE DEFINER=`root`@`localhost` FUNCTION `get_course_fee`(course_id int) RETURNS double
    DETERMINISTIC
BEGIN
DECLARE 
total_fee double;
select fees into total_fee from course_details 
where course_details.course_id=course_id;
RETURN total_fee;
END
