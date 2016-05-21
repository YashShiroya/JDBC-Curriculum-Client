import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UniversityCourseManagement {
	Connection con;
	static int _exit = 0;
	//static int faculty_id_given = -1;
	public UniversityCourseManagement() {
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		}
		catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		try {
			con =
					DriverManager.getConnection( "jdbc:oracle:thin:@claros.cs.purdue.edu:1524:strep","yshiroya", "gbjnvB91" );
		}
		catch ( SQLException e ){
			e.printStackTrace();
		}
	}

	public void insertNewCourse(int mode, int faculty_id_given) {
		int begin = 1; int flag_id = 0;

		while(begin == 1) {
			try {
				try {
					flag_id = 0;

					//System.out.println(faculty_id_given);
					Scanner scanner = new Scanner(System.in);
					System.out.print("Enter integer only Course id:");
					int course_id;
					try{
						course_id = scanner.nextInt();
						scanner.nextLine();
					}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
					System.out.print("Enter Course name:");
					String course_name = scanner.nextLine();
					System.out.print("Enter meeting location:");
					String course_location = scanner.nextLine();
					System.out.print("Enter room name:");
					String course_room = scanner.nextLine();
					System.out.print("Enter semester, eg. Spring:");
					String semester = scanner.nextLine();
					System.out.print("Input Integer only, Enter year:");
					int year;
					try{
						year = scanner.nextInt();
						scanner.nextLine();
					}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}

					//System.out.println("Your Faculty_id: " + faculty_id); 

					if(mode == 0) {
						int faculty_id = execute("Enter valid Faculty_id: ", 0, Integer.MAX_VALUE);
						String insertCourse = "insert into Course " + "values (?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement stmt = con.prepareStatement(insertCourse);
						stmt.setInt(1, course_id);
						stmt.setString(2, course_name);
						stmt.setString(3, semester);
						stmt.setInt(4, year);
						stmt.setString(5, course_location);
						stmt.setString(6, course_room);
						stmt.setInt(7, faculty_id);
						stmt.executeUpdate();
						stmt.close();
						begin = -1;
					}
					else if(mode == 1) {
						int check_course = 0;
						String query = "Select faculty_id from course where course_id=" + course_id ;
						try {
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery( query );
							while ( rs.next() ) {
								check_course++;
								int faculty_loop = rs.getInt( "faculty_id" );
								if(faculty_loop != faculty_id_given) {
									System.out.println("\nError, This course is not created by same faculty...\nTry Again...\n");
									begin = 1; flag_id =1;
									break;
									//insertNewCourse(mode, faculty_id_given);

								}
							}
							rs.close();
							stmt.close();

							if(flag_id == 1) { System.out.println("Wrong Faculty id"); continue;}
							if(check_course == 0) {System.out.println("No entires for this key, try again..."); continue;}
						}
						catch ( SQLException e ) {
							e.printStackTrace();
						}

						//if(check_course == 0) 

						String insertCourse = "update course set course_name=?, semester=?, year=?, meets_at=?, room=?"
								+ " where course_id=?";
						PreparedStatement stmt = con.prepareStatement(insertCourse);
						//stmt.setInt(1, course_id);
						stmt.setString(1, course_name);
						stmt.setString(2, semester);
						stmt.setInt(3, year);
						stmt.setString(4, course_location);
						stmt.setString(5, course_room);
						//stmt.setInt(6, faculty_id);
						stmt.setInt(6, course_id);
						//stmt.setInt(7, faculty_id_given);
						stmt.executeUpdate();
						stmt.close();
						begin = -1;
					}
				}
				catch(SQLException e){
					//e.printStackTrace();

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Primary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:foreign key constraint violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
					//insertNewCourse(mode, faculty_id_given);
				}
			} catch(Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	int check_format(String s) {
		if(s.length() != 10) return 0;
		if(s.charAt(2) != '/') return 0;
		if(s.charAt(5) != '/') return 0;

		return 1;
	}

	public void create_evaluation(int mode) {

		int begin = 1; int flag_id = 0;

		while(begin == 1) {
			try {
				try {
					Scanner scanner = new Scanner(System.in);

					System.out.println("Enter Evaluation Type(eg. Midterm, HW1...):");
					String eval_type = scanner.nextLine();

					System.out.println("Enter course_id:");
					int course_id = scanner.nextInt();
					scanner.nextLine();

					System.out.println("Enter weightage(Only integer, no fractions...):");
					int w = scanner.nextInt();
					scanner.nextLine();

					int correct_format = 0;

					String date = "11/11/2014";
					while(correct_format != 1) {
						System.out.println("Enter Due Date as String:\n Format for date: <MM/DD/YYYY>\n IMPORTANT: Must be Separated by Slash('/')");
						date = scanner.nextLine();
						if(check_format(date) == 1) {
							correct_format = 1; break; 
						}
						else {System.out.println("\nTry date input again...");}
					}
					
					System.out.println("Enter location/room(Eg. LWSN B146):");
					String location = scanner.nextLine();
					
					if(mode == 0) {
						System.out.println("Enter eval_id:");
						int eval_id = scanner.nextInt();
						scanner.nextLine();

						String insertEval = "insert into Evaluation " + "values (?, ?, ?, ?, ?, ?)";
						PreparedStatement stmt = con.prepareStatement(insertEval);
						stmt.setInt(1, eval_id);
						stmt.setString(2, eval_type);
						stmt.setInt(3, course_id);
						stmt.setInt(4, w);
						stmt.setString(5, date);
						stmt.setString(6, location);
						stmt.executeUpdate();
						stmt.close();
						begin = -1;
					}
					
					if(mode == 1) {
						
						System.out.println("Enter eval_id:");
						int eval_id = scanner.nextInt();
						scanner.nextLine();
						
						String stmt_1 = "Select due_date from evaluation where eval_id=?";
						PreparedStatement ps = con.prepareStatement( stmt_1 );
						ps.setInt( 1, eval_id );
						ResultSet rs = ps.executeQuery();
						
						
						while ( rs.next() ) {
							String due_date = rs.getString( "due_date" );
							
							String DateString = due_date;
						    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
						    
						    try {
						    	Date check_date = df.parse(DateString);
						        //String newDateString = df.format(check_date);
						        
						        String curr_date_string = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
								Date curr_date = df.parse(curr_date_string);
								
								if(!curr_date.before(check_date)) {
									System.out.println("\nError: Due date passed\n");
									continue;
								}
						        //System.out.println("Date:" + date);
						        //System.out.println(newDateString);
						    } catch (ParseException e) {
						        //e.printStackTrace();
						    	System.out.println("Date format did not work, try again...");
						    	begin = 1;
						    	continue;
						    }
							
							//if(){;}
						}
						
						
						rs.close();
						ps.close();
						
						String updateEval ="update evaluation set eval_type=?, course_id=?, weightage=?, due_date=?, meeting_room=?"
								+ "where eval_id=?";
						
						PreparedStatement stmt = con.prepareStatement(updateEval);
						stmt.setString(1, eval_type);
						stmt.setInt(2, course_id);
						stmt.setInt(3, w);
						stmt.setString(4, date);
						stmt.setString(5, location);
						stmt.setInt(6, eval_id);
						stmt.executeUpdate();
						stmt.close();
						begin = -1;
					}
				}
				catch(SQLException e){
					e.printStackTrace();

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Primary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:foreign key constraint violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
					//insertNewCourse(mode, faculty_id_given);
				}

			} catch(Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}

		}

	}

	public void updateEval() {
		
		int faculty_id_in = execute("Enter valid Faculty_id: ", 0, Integer.MAX_VALUE);
		
		String stmt = "select eval_id, eval_type, course.course_id, weightage, due_date, meeting_room from evaluation, course where course.faculty_id=? and evaluation.course_id=course.course_id";

		try {
			PreparedStatement ps = con.prepareStatement( stmt );
			ps.setInt( 1, faculty_id_in );
			ResultSet rs = ps.executeQuery();
			System.out.println("You can only modify the following evaluations with the following keys:\n");
			System.out.printf("%20s | %20s |  %20s |  %20s |  %20s |  %20s \n", "eval_id", "eval_type", "course_id", "Weightage", "due_date", "room");
			while ( rs.next() ) {
				String eval_type_2 = rs.getString( "eval_type" );
				int eval_id_2 = rs.getInt("eval_id");
				System.out.printf("%20d | %20s |  %20d |  %20d |  %20s |  %20s \n" ,eval_id_2, eval_type_2, rs.getInt( "course_id" ), rs.getInt("weightage") , rs.getString("due_date"), rs.getString( "meeting_room" ));
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		
		create_evaluation(1);
	}
	
	public void updateCourse() {

		int faculty_id_in = execute("Enter valid Faculty_id: ", 0, Integer.MAX_VALUE);
		//int faculty_id_in = scan_in.nextInt();
		//scan_in.nextLine();

		String stmt = "Select * from course where faculty_id=?";

		try {
			PreparedStatement ps = con.prepareStatement( stmt );
			ps.setInt( 1, faculty_id_in );
			ResultSet rs = ps.executeQuery();
			System.out.println("You can modify the following courses with the following keys:\n");
			System.out.printf("%20s | %20s |  %20s |  %20s |  %20s |  %20s |  %20s | \n", "course_id", "course_name", "semester", "year", "meets_at", "room", "faculty_id" );
			while ( rs.next() ) {
				String course_name = rs.getString( "course_name" );
				int course_id = rs.getInt("course_id");
				int year = rs.getInt( "year" );
				System.out.printf("%20d | %20s |  %20s |  %20d |  %20s |  %20s |  %20d | \n" ,course_id, course_name, rs.getString( "semester" ), year , rs.getString( "meets_at" ), rs.getString( "room" ), rs.getInt( "faculty_id" ));
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e){
			//e.printStackTrace();
		}

		insertNewCourse(1, faculty_id_in);

	}

	public void removeCourse() {

		int begin = 1; int flag_id = 0;

		while(begin == 1) {

			try { 
				flag_id = 0;
				System.out.println("Enter your faculty_id: ");
				Scanner scan_in = new Scanner(System.in);
				int faculty_id_in = scan_in.nextInt();
				scan_in.nextLine();

				String stmt = "Select * from course where faculty_id=?";

				try {
					PreparedStatement ps = con.prepareStatement( stmt );
					ps.setInt( 1, faculty_id_in );
					ResultSet rs = ps.executeQuery();
					System.out.println("You can remove the following courses with the following keys:\n");
					System.out.printf("%20s | %20s |  %20s |  %20s |  %20s |  %20s |  %20s | \n", "course_id", "course_name", "semester", "year", "meets_at", "room", "faculty_id" );
					while ( rs.next() ) {
						String course_name = rs.getString( "course_name" );
						int course_id = rs.getInt("course_id");
						int year = rs.getInt( "year" );
						System.out.printf("%20d | %20s |  %20s |  %20d |  %20s |  %20s |  %20d | \n" ,course_id, course_name, rs.getString( "semester" ), year , rs.getString( "meets_at" ), rs.getString( "room" ), rs.getInt( "faculty_id" ));
					}
					rs.close();
					ps.close();

					System.out.print("Enter Course id to remove (Enter an int):");
					int course_id = scan_in.nextInt();
					scan_in.nextLine();

					//Check faculty_id

					String query = "Select faculty_id from course where course_id=" + course_id ;

					Statement stmt_1 = con.createStatement();
					ResultSet rs_1 = stmt_1.executeQuery( query );
					while ( rs_1.next() ) {
						int faculty_loop = rs_1.getInt( "faculty_id" );
						if(faculty_loop != faculty_id_in) {
							System.out.println("\nError, This course is not created by same faculty...\nTry Again...\n");
							//insertNewCourse(mode, faculty_id_given);
							begin = 1; flag_id = 1;
							break;

						}
					}
					rs_1.close();
					stmt_1.close();

					if(flag_id == 1) continue;

					//_________________________

					String removeCourse = "delete from course where course_id=?";
					PreparedStatement statement = con.prepareStatement(removeCourse);
					statement.setInt(1, course_id);
					statement.executeUpdate();
					statement.close();
					begin = -1;
					System.out.println("Success!\n\n");

				}
				catch (SQLException e){
					e.printStackTrace();

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Primary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:foreign key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, key constraint--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}

			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
			//insertNewCourse(1, faculty_id_in);
		}


	}

	public void enroll_student() {
		int begin = 1; int flag_id = 0;
		while(begin == 1){
			flag_id = 0;
			try {
				try {
					System.out.println("Enter your faculty_id: ");
					Scanner scan_in = new Scanner(System.in);
					int faculty_id_in = scan_in.nextInt();
					scan_in.nextLine();

					System.out.print("Enter Course id to enroll into (Enter an int):");
					int course_id = scan_in.nextInt();
					scan_in.nextLine();

					//Check faculty_id

					String query = "Select faculty_id from course where course_id=" + course_id ;

					Statement stmt_1 = con.createStatement();
					ResultSet rs_1 = stmt_1.executeQuery( query );
					while ( rs_1.next() ) {
						int faculty_loop = rs_1.getInt( "faculty_id" );
						if(faculty_loop != faculty_id_in) {
							System.out.println("\nError, This course is not created by same faculty...\nTry Again...\n");
							//insertNewCourse(mode, faculty_id_given);
							begin = 1; flag_id = 1;
							break;

						}
					}
					rs_1.close();
					stmt_1.close();

					if(flag_id == 1) continue;

					//_________________________

					System.out.print("Enter student_id to enroll (Enter an int):");
					int stud_id = scan_in.nextInt();
					scan_in.nextLine();

					String enroll = "insert into enrolled values (?, ?)";
					PreparedStatement statement = con.prepareStatement(enroll);
					statement.setInt(1, course_id);
					statement.setInt(2, stud_id);
					statement.executeUpdate();
					statement.close();
					begin = -1;
					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					e.printStackTrace();

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Primary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:foriegn key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	public void enter_grades() {

		int begin = 1; int flag_id = 0;
		while(begin == 1){
			flag_id = 0;
			try {
				try {
					System.out.println("Enter your faculty_id: ");
					Scanner scan_in = new Scanner(System.in);
					int faculty_id_in = scan_in.nextInt();
					scan_in.nextLine();

					System.out.print("Enter Course id related to\n the following evaluation (Enter an int):");
					int course_id = scan_in.nextInt();
					scan_in.nextLine();

					//Check faculty_id

					String query = "Select faculty_id from course where course_id=" + course_id ;

					Statement stmt_1 = con.createStatement();
					ResultSet rs_1 = stmt_1.executeQuery( query );
					while ( rs_1.next() ) {
						int faculty_loop = rs_1.getInt( "faculty_id" );
						if(faculty_loop != faculty_id_in) {
							System.out.println("\nError, This course is not created by same faculty...\nTry Again...\n");
							//insertNewCourse(mode, faculty_id_given);
							begin = 1; flag_id = 1;
							break;

						}
					}
					rs_1.close();
					stmt_1.close();

					if(flag_id == 1) continue;

					//_________________________

					System.out.print("Enter Eval_id related to\n the following evaluation (Enter an int):");
					int eval_id = scan_in.nextInt();
					scan_in.nextLine();

					System.out.print("Enter student_id for\n the following evaluation (Enter an int):");
					int stud_id = scan_in.nextInt();
					scan_in.nextLine();

					System.out.print("Enter a grade out of 100 (Enter an int):");
					int grade = scan_in.nextInt();
					scan_in.nextLine();



					String grading = "insert into grades values (?, ?, ?, ?)";
					PreparedStatement statement = con.prepareStatement(grading);
					statement.setInt(1, eval_id);
					statement.setInt(2, stud_id);
					statement.setInt(3, course_id);
					statement.setInt(4, grade);
					statement.executeUpdate();
					statement.close();
					begin = -1;

					System.out.println("Success!\n\n");

				}
				catch (SQLException e){
					//e.printStackTrace();

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Grade for this student evaluation already exists,\nPrimary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:Student Not enrolled in this course\nforeign key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	/*public void report_of_classes() {
		int begin = 1; int flag_id = 0;
		while(begin == 1){
			flag_id = 0;

			Scanner scan_in = new Scanner(System.in);
			System.out.print("Enter Course id for reports (Enter an int):");
			int course_id = scan_in.nextInt();
			scan_in.nextLine();

			String stmt = "Select course_name, meets_at, room from course where course_id=? order by course_id";
			String stmt_1 = "Select COUNT(*) AS \"total_students\" from enrolled where course_id=? order by course_id";
			//String stmt_2 = "Select course_name, meets_at, room from course where course_id=?";
			try {


				PreparedStatement ps = con.prepareStatement( stmt );
				ps.setInt( 1, course_id );
				ResultSet rs = ps.executeQuery();

				PreparedStatement ps_1 = con.prepareStatement( stmt_1 );
				ps_1.setInt( 1, course_id );
				ResultSet rs_1 = ps_1.executeQuery();

				while ( rs.next() && rs_1.next() ) {
					String course_name = rs.getString( "course_name" );
					int course_id_loop = rs.getInt("course_id");
					int year = rs.getInt( "year" );

				}
				rs.close();
				ps.close();
				rs_1.close();
				ps_1.close();
				begin = -1;

				System.out.println("Success!\n\n");
			}
			catch (SQLException e){
				e.printStackTrace();

				int err_code = e.getErrorCode();
				if(err_code == 1) System.out.println("*--Error:Primary key constraint violation--*");
				else if(err_code == 2291) System.out.println("*--Error:foriegn key constraint violation--*");
				else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
				else {System.out.println("*--Error:SQL Query not executed, false query");}
				System.out.println("Error, Try again...");
				begin = 1;
			}

		}
	}*/

	public void stud_courses() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			flag_id = 0;
			try {

				System.out.println("Enter your student_id: ");
				Scanner scan_in = new Scanner(System.in);
				int stud_id_in = scan_in.nextInt();
				scan_in.nextLine();
				int counter_loop = 0;

				String stmt = "Select enrolled.course_id, course.course_name from enrolled JOIN course ON course.course_id=enrolled.course_id where student_id=?";
				try {
					PreparedStatement ps = con.prepareStatement( stmt );
					ps.setInt( 1, stud_id_in );
					ResultSet rs = ps.executeQuery();
					System.out.println("\nYour registered courses:");
					while ( rs.next() ) {
						String course_l_id = rs.getString( "course_id" );
						String course_l_name = rs.getString( "course_name" );
						System.out.println( course_l_id + " " + course_l_name );
						counter_loop++;
					}
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No such student Id found.\n");
						continue;
					}

					begin = -1;

					System.out.println("Success!\n\n");
				}
				catch (SQLException e){

					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Grade for this student evaluation already exists,\nPrimary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:Student Not enrolled in this course\nforeign key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	public void stud_eval() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			try {
				flag_id = 0;
				System.out.println("Enter your student_id: ");
				Scanner scan_in = new Scanner(System.in);
				int stud_id_in = scan_in.nextInt();
				scan_in.nextLine();
				int counter_loop = 0;

				String stmt = "Select course.course_name, evaluation.eval_type, evaluation.due_date from"
						+ " course JOIN evaluation ON course.course_id=evaluation.course_id "
						+ "JOIN enrolled ON enrolled.course_id=evaluation.course_id where student_id=?";

				try {
					PreparedStatement ps = con.prepareStatement( stmt );
					ps.setInt( 1, stud_id_in );
					ResultSet rs = ps.executeQuery();
					System.out.println("\nYour current evaluations:\n");
					System.out.printf("%20s | %20s | %20s\n", "course_name", "eval_type", "due_date" );
					while ( rs.next() ) {
						String course_l_name = rs.getString( "course_name" );
						String eval_l_type = rs.getString( "eval_type" );
						String eval_date = rs.getString("due_date");
						System.out.printf("%20s | %20s | %20s\n", course_l_name, eval_l_type, eval_date );
						counter_loop++;
					}
					System.out.println();
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No such student Id found.\n");
						continue;
					}

					begin = -1;

					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					e.printStackTrace();
					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Grade for this student evaluation already exists,\nPrimary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:Student Not enrolled in this course\nforeign key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	public void stud_grades() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			flag_id = 0;
			try {
				System.out.println("Enter your student_id: ");
				Scanner scan_in = new Scanner(System.in);
				int stud_id_in = scan_in.nextInt();
				scan_in.nextLine();
				int counter_loop = 0;

				String stmt = "Select distinct course.course_name, evaluation.eval_type, grades.grade_100, (grades.grade_100 * evaluation.weightage)/100 AS final_grade from course JOIN evaluation ON course.course_id=evaluation.course_id JOIN enrolled ON enrolled.course_id=evaluation.course_id JOIN grades ON grades.eval_id=evaluation.eval_id where grades.student_id=?";

				try {
					PreparedStatement ps = con.prepareStatement( stmt );
					ps.setInt( 1, stud_id_in );
					ResultSet rs = ps.executeQuery();
					System.out.println("\nYour current grades:\n");
					System.out.printf("%20s | %20s | %20s | %20s\n", "course_name", "eval_type", "Grade(out of 100)", "Final Grade" );
					while ( rs.next() ) {
						String course_l_name = rs.getString( "course_name" );
						String eval_l_type = rs.getString( "eval_type" );
						int grade_l = rs.getInt("grade_100");
						double final_grade_l = rs.getDouble("final_grade");
						System.out.printf("%20s | %20s | %20d | %20f\n", course_l_name, eval_l_type, grade_l , final_grade_l );
						counter_loop++;
					}
					System.out.println();
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No such student Id found. Try Again...\n");
						continue;
					}

					begin = -1;

					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					e.printStackTrace();
					int err_code = e.getErrorCode();
					if(err_code == 1) System.out.println("*--Error:Grade for this student evaluation already exists,\nPrimary key constraint violation--*");
					else if(err_code == 2291) System.out.println("*--Error:Student Not enrolled in this course\nforeign key constraint violation--*");
					else if(err_code == 2292)	System.out.println("*--Error:Integrity constraint, child key violation--*");
					else {System.out.println("*--Error:SQL Query not executed, false query--*");}
					System.out.println("Error, Try again...");
					begin = 1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	public void admin_dept_report() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			flag_id = 0;
			try {
				int counter_loop = 0;

				String stmt = "Select dept_name, head_of_dept from department";
				try {
					//
					PreparedStatement ps = con.prepareStatement( stmt );
					ResultSet rs = ps.executeQuery();

					System.out.println("\nDepartment Heads:\n");
					System.out.printf("%-20s | %-20s \n", "Dept_name", "Head of Dept.");
					while ( rs.next() ) {
						String dept_name = rs.getString( "dept_name" );
						String head_of_dept = rs.getString( "head_of_dept" );
						System.out.printf("%-20s | %-20s \n", dept_name, head_of_dept );
						counter_loop++;
					}
					System.out.println();
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No entries found.\n");
						//continue;
					}
					//
					begin = -1;
					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					e.printStackTrace();
					System.out.println("*--Error:SQL Query not executed, false query--*");
					System.out.println("Try again...");
					begin = -1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}

	public void faculty_class_report() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			flag_id = 0;
			try {
				int counter_loop = 0;

				String stmt = "with numS AS (select count(enrolled.student_id) as count, course_id from enrolled GROUP BY course_id), numE as (select count(evaluation.eval_id) as eval_count, course_id from evaluation group by course_id) select course.course_id, course_name, meets_at, room, count, eval_count from course, numS, numE where course.course_id=numS.course_id AND course.course_id=numE.course_id ORDER BY course.course_id";
				try {
					//
					PreparedStatement ps = con.prepareStatement( stmt );
					ResultSet rs = ps.executeQuery();

					System.out.println("\nReport of classes:\n");
					System.out.printf("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s |  \n", "course_id", "course_name"
							, "meets_at", "Room", "num_Students", "num_Evaluations");
					while ( rs.next() ) {
						int course_id = rs.getInt( "course_id" );
						String course_name = rs.getString( "course_name" );
						String meets_at = rs.getString( "meets_at" );
						String room = rs.getString( "room" );
						int num_Students = rs.getInt( "count" );
						int num_Evaluations = rs.getInt( "eval_count" );

						System.out.printf("%-20d | %-20s | %-20s | %-20s | %-20d | %-20d |  \n", course_id, course_name,
								meets_at, room, num_Students, num_Evaluations); 
						counter_loop++;
					}
					System.out.println();
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No entries found.\n");
						//continue;
					}
					//
					begin = -1;
					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					e.printStackTrace();
					System.out.println("*--Error:SQL Query not executed, false query--*");
					System.out.println("Try again...");
					begin = -1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}

		}
	}

	public void admin_faculty_report() {
		int begin = 1; int flag_id = 0;

		while(begin == 1){
			try {
				flag_id = 0;
				int counter_loop = 0;

				String stmt = "Select faculty.faculty_name, department.dept_name FROM faculty, department WHERE faculty.dept_id=department.dept_id";
				try {
					//
					PreparedStatement ps = con.prepareStatement( stmt );
					ResultSet rs = ps.executeQuery();

					System.out.println("\nFaculty Report:\n");
					System.out.printf("%-20s | %-20s \n", "Faculty_Name", "Department_Name");
					while ( rs.next() ) {
						String faculty_name = rs.getString( "faculty_name" );
						String dept_name = rs.getString( "dept_name" );
						System.out.printf("%-20s | %-20s \n", faculty_name, dept_name );
						counter_loop++;
					}
					System.out.println();
					rs.close();
					ps.close();

					if(counter_loop == 0) {
						System.out.println("\nError: No entries found.\n");
						//continue;
					}
					//
					begin = -1;
					System.out.println("Success!\n\n");
				}
				catch (SQLException e){
					//e.printStackTrace();
					System.out.println("*--Error:SQL Query not executed, false query");
					System.out.println("Try again...");
					begin = -1;
				}
			}catch (Exception e) {System.out.println("\nEnter integer properly... try again\n"); continue;}
		}
	}



	public int execute(String prompt, int l_limit, int u_limit) {

		//Choose between Admin, Faculty, Student_____________________________________________

		String classification = "";
		int choice = -1;
		Scanner scan_in = new Scanner(System.in);
		while(true) {
			choice = -1;
			System.out.print(prompt);
			classification = scan_in.next();
			try {
				choice = Integer.parseInt(classification);
			}
			catch(NumberFormatException e){
				System.out.println("\nError, try Again...");
				continue;
			}
			if(choice <= l_limit || choice >= u_limit) System.out.println("\nError, try Again...");
			else break;
		}
		//scan_in.close();

		//_______________________________________________________________________________________

		return choice;
	}

	/* Main() */
	public static void main( String [] args ) {
		UniversityCourseManagement ucm = new UniversityCourseManagement();

		while(_exit == 0) {
			System.out.println("Welcome to the University Course Management System\n");
			String prompt = "What is your classification?\n1. Student\n2. Faculty\n3. Admin\n4. Exit\n\n Enter an integer from above choices:";
			int choice_category = ucm.execute(prompt, 0, 5);
			//System.out.println("\nYou have chosen : ");
			if(choice_category == 1) {
				System.out.println("\nYou have chosen : ");
				System.out.println("Student\n");
				Student student = new Student();
				student.select_task();

				System.out.println("\nPress any key to start new task...\n");
				Scanner temp = new Scanner(System.in);
				temp.nextLine();
			}
			else if(choice_category == 2) {
				System.out.println("\nYou have chosen : ");
				System.out.println("Faculty\n");			
				Faculty faculty = new Faculty();
				faculty.select_task();

				System.out.println("\nPress any key to start new task...\n");
				Scanner temp = new Scanner(System.in);
				temp.nextLine();
			}
			else if(choice_category == 3) {
				System.out.println("\nYou have chosen : ");
				System.out.println("Admin\n");
				Admin admin = new Admin();
				admin.select_task();

				System.out.println("\nPress any key to start new task...\n");
				Scanner temp = new Scanner(System.in);
				temp.nextLine();

			}
			else if(choice_category == 4) { 
				System.out.println("\n****You have exited the University Course Management System****");
				_exit = -1;
			}

		}
	}
}


class Admin {
	public int select_task(){
		//change
		String prompt = "\nChoose appropriate number from one of the\n following tasks to perform:\n\n"
				+ "1. Department Report\n"
				+ "2. Faculty Report\n";
		UniversityCourseManagement ucm_admin = new UniversityCourseManagement();

		int result = ucm_admin.execute(prompt, 0, 3);

		switch(result) {
		case 1: {
			ucm_admin.admin_dept_report();
			break;
		}
		case 2: {
			ucm_admin.admin_faculty_report();
			break;
		}
		}


		return 0;
	}
}

class Faculty {
	public void select_task(){

		UniversityCourseManagement ucm_faculty = new UniversityCourseManagement();

		//change
		String prompt = "\nChoose appropriate number from one of the\n following tasks to perform:\n\n"
				+ "1. Create/Modify a course\n"
				+ "2. Assign students to a course\n"
				+ "3. Create/Modify an evaluation\n"
				+ "4. Enter Grades\n"
				+ "5. Report of Classes\n"
				//+ "6. Report of Students and Grades\n\n"
				+ "Your choice: ";
		int result = ucm_faculty.execute(prompt, 0, 6);
		//change


		String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		System.out.println("Date:" + date);


		switch(result) {
		case 1: {
			int curr_choice = ucm_faculty.execute("\nChoose between:\n\n1. Create\n2. Modify\n3. Remove", 0, 4);
			if(curr_choice == 1)ucm_faculty.insertNewCourse(0, -1);
			else if(curr_choice == 2) ucm_faculty.updateCourse();
			else if(curr_choice == 3) ucm_faculty.removeCourse();
			break;
		}
		case 2: {
			System.out.println("You have chosen: 2. Assign students to a course\n");
			ucm_faculty.enroll_student();
			break;
		}

		case 3: {
			System.out.println("You have chosen: 3. Create/Modify an evaluation\n");
			int curr_choice = ucm_faculty.execute("\nChoose between:\n\n1. Create\n2. Modify", 0, 3);
			if(curr_choice == 1) ucm_faculty.create_evaluation(0);
			else if(curr_choice == 2) ucm_faculty.updateEval();
			//else if(curr_choice == 3) ;

			break;
		}
		case 4: {
			System.out.println("You have chosen: 4. Enter Grades\n");
			ucm_faculty.enter_grades();
			break;
		}
		case 5: {
			System.out.println("You have chosen: 5. Report of Classes\n");
			ucm_faculty.faculty_class_report();
			break;
		}
		case 6: {
			System.out.println("You have chosen: 6. Report of Students and Grades\n");
			break;
		}

		}


		//return result;

	}
}

class Student {
	public int select_task(){
		//change
		UniversityCourseManagement ucm_stud = new UniversityCourseManagement();

		//change
		String prompt = "\nChoose appropriate number from one of the\n following tasks to perform:\n\n"
				+ "1. Calendar\n"
				+ "2. My Courses\n"
				+ "3. My Grades\n\n"
				+ "Your choice: ";

		int result = ucm_stud.execute(prompt, 0, 4);


		switch(result) {
		case 1: {
			ucm_stud.stud_eval();
			break;
		}
		case 2: {

			ucm_stud.stud_courses();
			break;
		}

		case 3: {
			//System.out.println("You have chosen: 3. Create/Modify an evaluation\n");
			ucm_stud.stud_grades();
			break;
		}
		}

		return 0;
	}
}

//select table_name from user_tables;