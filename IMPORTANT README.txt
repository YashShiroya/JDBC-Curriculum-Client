This is the submission of CS 348 Project 1.
Name: Yashkumar Shiroya
username: yshiroya
PUID: 0027217903

I. Running Scripts:

All the scripts for generation of tables are provided in the submission folder.
Order of execution of these sql_scripts:

1. Run @config - This set tab spaces so that the large tables are visible without being merged in window.
2. Run @do - This script performs 3 steps, 
		a)drops existing tables
		b)creates new tables
		c)inserts default values to the tables
3. The other scripts just help @do script to run. If you run @do in SQL> It will do the job of dropping, creating and insertion of tables.


II. Running the project:

1. Please import the entire project into Eclipse IDE.
2. The JDBC driver is already linked to the project and should compile and run immideately. In any, if it does not build due to jdbc driver issues, go to the end of the document to see how to link the JDBC driver to the project.
3. Just Run in eclipse and you should get the project running in the console.

III.After the project starts:
a). Follow the console instructions. Each step allows you to choose an option as a 'int' input.eg 

eg.


What is your classification?
1. Student
2. Faculty
3. Admin
4. Exit

eg. To choose the following option, type the option number without any other character, so to choose option 1. Student, just type 1


----------------------------------------------------------------------
This step is not required by default and should only be performed if the project does not build.

IV. Linking JDBC driver to project:

If the project does not build, it is probably due to the JDBC driver not linked to the project.
	In order to link it:
		a)Go to UniversityCourseManagement in the 'Project Explorer' in eclipse, right click on it and got to 'Build Path'. 
		There you choose 'External Archives' and select ojdbc6.jar.
		b)Go to the java file in src and goto 'Build Path' and 'configure build path' and select the driver from the Libraries.




