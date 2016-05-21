create table Student(
	Student_id integer not null,
	Student_name varchar(30),
	primary key(Student_id)
	);

create table Department(
	Dept_id integer,
	Dept_name varchar(30),
	head_of_dept varchar(30),
	primary key(Dept_id)
	);

create table Faculty(
	Faculty_id integer not null,
	Faculty_name varchar(30) null,
	Dept_id integer,
	primary key(Faculty_id),
	foreign key(Dept_id) references Department(Dept_id) ON DELETE CASCADE
	);
	
create table Course(
	Course_id integer not null,
	Course_name varchar(30) NULL,
	Semester varchar(20) NULL,
	Year integer NULL,
	Meets_at varchar(20) NULL,
	Room varchar(10) NULL,
	Faculty_id integer NULL REFERENCES Faculty(Faculty_id) ON DELETE CASCADE,
	primary key(Course_id)
	);
	

create table Evaluation(
	Eval_id integer not null,
	Eval_type varchar(30) null,
	Course_id integer references Course(Course_id) ON DELETE CASCADE,
	Weightage integer NULL,
	Due_date varchar(20) NULL,
	Meeting_room varchar(20) NULL,
	primary key(Eval_id)         
);

create table Enrolled(
	Course_id integer NOT NULL references Course(Course_id) ON DELETE CASCADE,
	Student_id integer NOT NULL references Student(Student_id) ON DELETE CASCADE,
	primary key(Course_id, Student_id)
);

create table Grades(
	Eval_id integer NOT NULL REFERENCES Evaluation(Eval_id) ON DELETE CASCADE,
	Student_id integer NOT NULL REFERENCES Student(Student_id) ON DELETE CASCADE,
	--Student_id integer NOT NULL REFERENCES Enrolled(Student_id),--
	Course_id integer NOT NULL REFERENCES Course(Course_id) ON DELETE CASCADE,
	foreign key(Course_id, Student_id) REFERENCES Enrolled(Course_id, Student_id) ON DELETE CASCADE,
	Grade_100 integer NULL, 
	primary key(Eval_id, Student_id)
);


	

