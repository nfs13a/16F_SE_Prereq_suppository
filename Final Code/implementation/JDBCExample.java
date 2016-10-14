package implementation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class JDBCExample {

	/*
	 * Fields we care about:
	 * 
	 * Column | Number in stuff array | CRN: 5 digit number | 35 Subject_Code:
	 * 2-4 string all caps | 40 Course_Number: 100-599? | 42 Instructor_Name:
	 * literally "Last, First" | 50 Grade_Code: only A-D, F, W, and maybe WF |
	 * 55 Banner_ID: 9 digit number but usually 3-4 leading 0's | 56 First_Name
	 * | 57 Last_Name | 58 Middle_Name | 59 Prefix | 60 Class_Code: FR, SO, JR,
	 * SR, GR, SU | 33 Billing_Hours: hours the course is worth | 47
	 * Credit_Hours: 0 if not passed, Billing_Hours if is | 48
	 */

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Bhi4472!";

	static Connection conn = null;
	static Statement stmt = null;

	private static String sqlFilename;
	private static String csvFilename;

	public JDBCExample(String sql, String csv) {
		this.sqlFilename = "C:/Users/CPU8/Documents/Github/suppository/cucumber/tests/" + sql;
		csvFilename = "C:/Users/CPU8/Documents/Github/suppository/cucumber/tests/" + csv;
		setUp();
	}

	/**
	 * 
	 * @param str
	 *            - a String of the row taken from the CSV
	 * @return - a String array of all fields
	 */
	private static String[] newSplit(String str) {
		// guaranteed to have 147 columns; any more are a mistake and/or not
		// meaningful
		String newStrings[] = new String[147]; // array of all fields to be
												// filled and returned
		int i = 0; // counter for number of fields inserted into the array; used
					// for counting and indexing into newStrings
		while (i < 147) {
			String temp; // will be filled and inserted into the ith index of
							// newStrings
			/*
			 * as seen near the end of this loop, we are removing from str (the
			 * String argument) whatever we insert into newStrings (the String
			 * array to be returned). str should always have a comma until the
			 * last field is reached. the else is necessary because an error is
			 * thrown creating a substring from 0 to -1, which happened here if
			 * the index of a comma could not be found
			 */
			if (str.contains(","))
				temp = str.substring(0, str.indexOf(',', 0));
			else
				temp = str;
			// if temp contains a quote, it may be the name of the instructor.
			// This field is surrounded by double quotes and, more importantly,
			// has a comma between the first and last name
			if (temp.contains("\"")) {
				// to find out if the field is the instructor, we search for a
				// comma. It is the instructor if a comma is found before
				// another quote
				int j = 1;
				while (true) {
					if (str.charAt(j) == '\"') {
						// since a quote is found before a comma, this must not
						// be the instructor field, so we do not have to worry
						// about splitting the data. break to resume reading
						// fields.
						break;
					} else if (str.charAt(j) == ',') {
						/*
						 * since a comma is found before a quote, this must be
						 * the instructor field. However, since we parse on
						 * commas, temp only contains the last name of the
						 * instructor and a following space (" "). This code
						 * gets the last name of the professor and the ending
						 * quote of the field and saves it to temp.
						 */
						String another = str.substring(str.indexOf(','), str.length());
						temp = str.substring(0, str.indexOf(',')) + another.substring(0, another.indexOf("\"") + 1);
						break;
					}
					j++;
				}
			}

			// removes the new insert plus the comma which succeeds it from str
			// so that we can find the first instance of a comma in str in the
			// next iteration easily
			if (!temp.equals(str))
				str = str.substring(temp.length() + 1, str.length());

			// adds temp into newStrings and increments i
			newStrings[i++] = temp;
		}
		return newStrings;
	}

	/*
	 * this method creates the database by running our students sql file in the
	 * future, we might want to add paramenters for the path of an sql file and
	 * the credentials
	 */
	private static void createDatabases(String file) throws FileNotFoundException, IOException, SQLException {

		final String DB_URL = "jdbc:mysql://localhost/";

		// Database credentials
		final String USER = "root";
		final String PASS = "Bhi4472!";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
			System.err.println("Unable to connect to server: " + e);
		}
		ScriptRunner runner = new ScriptRunner(conn, false, false);
		runner.runScript(new BufferedReader(new FileReader(file)));
	}

	/**
	 * Summary - if a student is not in the student table yet, inserts the
	 * student
	 * 
	 * @param stuff
	 *            - a String array of a line parsed by the newSplit method (see
	 *            above)
	 * @throws SQLException
	 */
	private static void insertStudent(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM student WHERE banner = " + stuff[56] + ";");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {

				String sqlStudent = "INSERT INTO student VALUES('";
				for (int l = 56; l < 61; l++) {
					sqlStudent += stuff[l] + "', '";
				}
				sqlStudent += stuff[33] + "')";

				System.out.println(sqlStudent);

				stmt.executeUpdate(sqlStudent);
			}
		}
	}

	private static void insertCourse(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery(
				"SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[40] + stuff[42] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlCourse = "INSERT INTO course VALUES('" + stuff[40] + stuff[42] + "', '" + stuff[47] + "', "
						+ 0.0 + ", 'FR', " + 0 + ")";

				System.out.println(sqlCourse);

				System.out.println("SELECT COUNT(*) AS total FROM course WHERE code = " + stuff[40] + stuff[42]);

				stmt.executeUpdate(sqlCourse);
			}
		}
	}

	private static void insertCourseInstance(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM courseInstances WHERE CRN = " + "'" + stuff[35]
				+ "'" + " and code = " + "'" + stuff[40] + stuff[42] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlCourseInstance = "INSERT INTO courseInstances VALUES('" + stuff[35] + "', '" + stuff[40]
						+ stuff[42] + "', '" + stuff[50] + "')";

				System.out.println(sqlCourseInstance);

				System.out.println("Here");
				stmt.executeUpdate(sqlCourseInstance);
			}
		}
	}

	private static void insertStudentCourseTaken(String[] stuff) throws SQLException {
		ResultSet rs = stmt
				.executeQuery("SELECT COUNT(*) AS total FROM studentCoursesTaken WHERE CRN = " + "'" + stuff[35] + "'"
						+ " and code = " + "'" + stuff[40] + stuff[42] + "' and banner = '" + stuff[56] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlSCT = "INSERT INTO studentCoursesTaken VALUES('" + stuff[56] + "', '" + stuff[35] + "', '"
						+ stuff[40] + stuff[42] + "', '" + stuff[48] + "', '" + stuff[55] + "')";

				System.out.println(sqlSCT);

				stmt.executeUpdate(sqlSCT);
			}
		}
	}

	private static void insertPrereqCourses(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[0] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 1) {
				String sqlPre = "INSERT INTO studentCoursesTaken VALUES('" + stuff[1] + "', '" + stuff[0] + "', '"
						+ stuff[2] + "')";

				System.out.println(sqlPre);

				stmt.executeUpdate(sqlPre);
			}
		}
	}

	private static void insertPrereqOtherInfo(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[0] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 1) {
				String sqlPre = "UPDATE course SET prereqGPA = '" + stuff[2] + "', prereqClass = '" + stuff[3]
						+ "', prereqEarnedHours = '" + stuff[2] + "' WHERE code = '" + stuff[0] + "';";

				System.out.println(sqlPre);

				stmt.executeUpdate(sqlPre);
			}
		}
	}

	private static void setUp() {
		try{
			//default is: String csvFile = "C:/Users/CPU8/Google Drive/Software Engineering/workspace/Beginnings/src/CSVParsing/cs374_anon.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			
			//for the csv
			try {
				br = new BufferedReader(new FileReader(csvFilename));
				int i = 1;
				while ((line = br.readLine()) != null) {
					String[] stuff = JDBCExample.newSplit(line);
					System.out.println("Before if");
					if (i == 1) {
						System.out.println("Creating database...");
						createDatabases("C:/Users/CPU8/Documents/GitHub/suppository/implementation/StudentsSetup.sql");
					} else {
							stmt = conn.createStatement();
						System.out.println("what is it: " + i + " " + stuff[56]);
						System.out.println("");
						if (!(stuff[40].isEmpty() || stuff[42].isEmpty() || stuff[35].isEmpty() || stuff[56].isEmpty())) {
							insertStudent(stuff);
							insertCourse(stuff);
							insertCourseInstance(stuff);
							insertStudentCourseTaken(stuff);
						}
					}
					i++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//for the prereqs
				try {
					br = new BufferedReader(new FileReader("C:/Users/CPU8/Documents/GitHub/suppository/Text Tests/PrereqCourses1.csv"));
					while ((line = br.readLine()) != null) {
						String[] stuff = JDBCExample.newSplit(line);
						insertPrereqCourses(stuff);
					}
					br = new BufferedReader(new FileReader("C:/Users/CPU8/Documents/GitHub/suppository/Text Tests/CourseTablePrereqs1.csv"));
					while ((line = br.readLine()) != null) {
						String[] stuff = JDBCExample.newSplit(line);
						insertPrereqOtherInfo(stuff);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		 }catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		 }catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		 }
		}

	public String checkCRN(String crn) {
		return crn;
	}

	public static void main(String[] args) {
		try{
			
			
			//default is: String csvFile = "C:/Users/CPU8/Google Drive/Software Engineering/workspace/Beginnings/src/CSVParsing/cs374_anon.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			
			//for the csv
			try {
			
			//STEP 4: Execute a query
			//System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();
			
			String sql4 = "SELECT Banner_ID, First_Name, Last_Name, Middle_Name, Prefix FROM INFO";
			ResultSet rs = stmt.executeQuery(sql4);
			//STEP 5: Extract data from result set
			while(rs.next()){
				 //Retrieve by column name
				 String id= rs.getString("Banner_ID");
				 String first = rs.getString("First_Name");
				 String last = rs.getString("Last_Name");
				 String middle = rs.getString("Middle_Name");
				 String prefix = rs.getString("Prefix");

				 //Display values
				 System.out.print("Banner: " + id);
				 System.out.print(", First: " + first);
				 System.out.print(", Last: " + last);
				 System.out.print(", Middle: " + middle);
				 System.out.println(", Prefix: " + prefix);
			}
			rs.close();
			
		 } catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		 } catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		 }finally{
			//finally block used to close resources
			try{
				 if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				 if(conn!=null)
					conn.close();
			}catch(SQLException se){
				 se.printStackTrace();
			}//end finally try
		 }//end try
		}catch (Exception e) {
			
		}
		 System.out.println("Goodbye!");
}//end main
}// end JDBCExample