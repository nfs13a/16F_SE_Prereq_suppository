CREATE DATABASE IF NOT EXISTS STUDENTS;
USE STUDENTS;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS courseInstances;
DROP TABLE IF EXISTS studentCoursesTaken;
SET foreign_key_checks = 1;

CREATE TABLE student
( banner varchar(20) NOT NULL,
fn varchar(50) NOT NULL,
ln varchar(50) NOT NULL,
mn varchar(50) NOT NULL,
pre varchar(10) NOT NULL,
classification varchar(10) NOT NULL,w
PRIMARY KEY ( banner )
);

CREATE TABLE course
( code varchar(20) NOT NULL,
num varchar(20) NOT NULL,
hours varchar(50) NOT NULL,
PRIMARY KEY ( code,num)
);

CREATE TABLE courseInstances
( CRN varchar(50) NOT NULL,
instructor varchar(50) NOT NULL,
PRIMARY KEY (CRN,code),
FOREIGN KEY (code) references course(code)
);

CREATE TABLE studentCoursesTaken
( earnedHrs int NOT NULL,
grade varchar(10) NOT NULL,
PRIMARY KEY (CRN,code,banner),
FOREIGN KEY (code) references course(code),
FOREIGN KEY (CRN) references courseInstances(CRN),
FOREIGN KEY (banner) references student(banner)
);
