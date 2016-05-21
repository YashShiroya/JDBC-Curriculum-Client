insert into STUDENT values (1, 'Monica');
insert into STUDENT values (2, 'Ross');
insert into STUDENT values (3, 'Chandler');
insert into STUDENT values (4, 'Rachel');
insert into STUDENT values (5, 'Phoebe');

insert into Department values (1, 'E. Engineering', 'John');
insert into Department values (2, 'Communication', 'Walter');
insert into Department values (3, 'Computer Science', 'Judy');

insert into Faculty values (1, 'James', 1);
insert into Faculty values (2, 'Catherine', 2);
insert into Faculty values (3, 'Terry', 3);
insert into Faculty values (4, 'Greg', 3);

insert into COURSE values (1,'EE40000', 'Spring',2015,'EE', 'B150', 1);
insert into COURSE values (2,'EE32000', 'Spring',2015,'EE', '1120', 1);
insert into COURSE values (3,'COM10000', 'Fall',2015,'BRNG', '2212', 2);
insert into COURSE values (4,'CS48700', 'Spring',2015,'ME', 'B150', 3);
insert into COURSE values (5,'CS55100', 'Fall',2016,'LWSN', 'B154', 4);

insert into Evaluation values (1, 'Midterm', 1, 20, '09/12/2015', 'EE 1148');
insert into Evaluation values (2, 'Final', 2, 25, '12/13/2015', 'EE 2145');
insert into Evaluation values (3, 'Midterm', 3 , 15, '09/14/2015', 'BRNG B155');
insert into Evaluation values (4, 'Final', 3, 10, '12/15/2015', 'BRNG B155');
insert into Evaluation values (5, 'Midterm', 4, 20, '09/12/2015', 'LWSN B155');
insert into Evaluation values (6, 'Final', 5, 15, '12/10/2015', 'LWSN B158');
insert into Evaluation values (7, 'Midterm', 5, 10, '09/18/2015', 'LWSN 1152');

insert into Enrolled values (1,1);
insert into Enrolled values (1,3);
insert into Enrolled values (2,3);
insert into Enrolled values (1,2);
insert into Enrolled values (2,1);
insert into Enrolled values (2,4);
insert into Enrolled values (2,5);
insert into Enrolled values (3,2);
insert into Enrolled values (4,3);
insert into Enrolled values (5,4);

insert into Grades values (1, 1, 1, 98);
insert into Grades values (1, 2, 1, 90);
insert into Grades values (2, 1, 2, 90);
insert into Grades values (4, 2, 3, 89);


















