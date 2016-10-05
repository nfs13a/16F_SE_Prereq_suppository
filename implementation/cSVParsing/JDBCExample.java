package cSVParsing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//STEP 1. Import required packages
import java.sql.*;

public class JDBCExample {
	
	/*
	 * Fields we care about:
	 * 
	 * Column													|	Number in stuff array
	 * 															|
	 * CRN: 5 digit number										|	35
	 * Subject_Code: 2-4 string all caps						|	40
	 * Course_Number: 100-599?									|	42
	 * Instructor_Name: literally "Last, First"					|	50
	 * Grade_Code: only A-D, F, W, and maybe WF					|	55
	 * Banner_ID: 9 digit number but usually 3-4 leading 0's	|	56
	 * First_Name												|	57
	 * Last_Name												|	58
	 * Middle_Name												|	59
	 * Prefix													|	60
	 * Class_Code: FR, SO, JR, SR, GR, SU						|	33
	 * Billing_Hours: hours the course is worth					|	47
	 * Credit_Hours: 0 if not passed, Billing_Hours if is		|	48
	 */
	
	private static String[] newSplit(String str) {
		String newStrings[] = new String[147];
		int i = 0;
		while (i < 147) {
			String temp;
			if (str.contains(","))
				temp = str.substring(0, str.indexOf(',', 0));
			else
				temp = str;
			//System.out.println(i + "th: " + temp);
			if (temp.contains("\"")) {
				int j = 1;
				while (true) {
					if (str.charAt(j) == '\"') {
						break;
					} else if (str.charAt(j) == ',') {
						//System.out.println(str.substring(1, temp.length() - 1));
						String another = str.substring(str.indexOf(','), str.length());
						temp = str.substring(0, str.indexOf(',')) + another.substring(0, another.indexOf("\"") + 1);
						break;
					}
					j++;
				}
			}
			if (!temp.equals(str))
				str = str.substring(temp.length() + 1, str.length());
			//System.out.println("new str: " + str);
			/*for (int j = 0; j < i; j++)
				if (newStrings[j].equals(temp) && newStrings[j] != "" && newStrings[j] != " ")
					temp += "_";*/
			newStrings[i++] = temp;
			//System.out.println(i + ": " + temp);
		}
		return newStrings;
	}
	
	private static void createDatabases() throws FileNotFoundException, IOException, SQLException {
		//Connection mConnection = null;
		
		 final String DB_URL = "jdbc:mysql://localhost/";

		 //  Database credentials
		 final String USER = "";	//insert whatever your username is here
		 final String PASS = "";	//insert whatever your password is here
		   
		   
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
		    System.err.println("Unable to get mysql driver: " + e);
		} catch (SQLException e) {
		    System.err.println("Unable to connect to server: " + e);
		}
		ScriptRunner runner = new ScriptRunner(conn, false, false);
		String file = "C:/Users/CPU8/Documents/GitHub/suppository/implementation/StudentsSetup.sql";
		runner.runScript(new BufferedReader(new FileReader(file)));
	}
	
	private static void insertStudents(String[] stuff) throws SQLException {
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
	
	private static void insertCourses(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[40] + stuff[42] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlCourse = "INSERT INTO course VALUES('" + stuff[40] + stuff[42] + "', '" + stuff[47] + "')";
		
				System.out.println(sqlCourse);
		
				System.out.println("SELECT COUNT(*) AS total FROM course WHERE code = " + stuff[40] + stuff[42]);
		
				stmt.executeUpdate(sqlCourse);
			}
		}
	}
	
	private static void insertCourseInstances(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM courseInstances WHERE CRN = " + "'" + stuff[35] + "'" + " and code = " + "'" + stuff[40] + stuff[42] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlCourseInstance = "INSERT INTO courseInstances VALUES('" + stuff[35] + "', '" + stuff[40] + stuff[42] + "', '" + stuff[50] + "')";
		
				System.out.println(sqlCourseInstance);
		
				System.out.println("Here"); stmt.executeUpdate(sqlCourseInstance);
			}
		}
	}
	
	private static void insertStudentCoursesTaken(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM studentCoursesTaken WHERE CRN = " + "'" + stuff[35] + "'" + " and code = " + "'" + stuff[40] + stuff[42] + "' and banner = '" + stuff[56] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 0) {
				String sqlSCT = "INSERT INTO studentCoursesTaken VALUES('" + stuff[56] + "', '" + stuff[35] + "', '" + stuff[40] + stuff[42] + "', '" + stuff[48] + "', '" + stuff[55] + "')";
		
				System.out.println(sqlSCT);
	
				stmt.executeUpdate(sqlSCT);
				
				
				//add inserts to a Roster here to keep track of GPA
			}
		}
	}
	
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 static final String DB_URL = "jdbc:mysql://localhost/";

 //Database credentials
 static final String USER = "";	//insert whatever your username is here
 static final String PASS = "";	//insert whatever your password is here
 
 static Connection conn = null;
 static Statement stmt = null;
 public static void main(String[] args) {
 //Connection conn = null;
 //Statement stmt = null;
 try{
	//STEP 2: Register JDBC driver
	/*Class.forName("com.mysql.jdbc.Driver");

	//STEP 3: Open a connection
	System.out.println("Connecting to database...");
	conn = DriverManager.getConnection(DB_URL, USER, PASS);

	//STEP 4: Execute a query
	System.out.println("Creating database...");
	stmt = conn.createStatement();
	
	String sql = "DROP DATABASE IF EXISTS STUDENTS";
	stmt.executeUpdate(sql);
	System.out.println("Database created successfully...");
	sql = "CREATE DATABASE STUDENTS";
	stmt.executeUpdate(sql);
	stmt.executeUpdate("USE STUDENTS");*/
	//STEP 5: Execute a query	
	
	String csvFile = "";	//insert the location of your CSV here
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	int save = 0;
	//TestScriptRunner t = new TestScriptRunner();
	try {

		br = new BufferedReader(new FileReader(csvFile));
		int i = 1;
		while ((line = br.readLine()) != null) {

			String[] stuff = JDBCExample.newSplit(line);
			//System.out.println(stuff.length);
			//System.out.print(i + ": ");
			if (i == 1) {
				/*int k = 0;
				for (String str : stuff) {
					System.out.print(k++ + ": " + str + "\t");
				}
				System.out.println("Creating table in given database...");
				stmt = conn.createStatement();
			
				String sql2 = "CREATE TABLE INFO (";
				
				k = 65;
				for (String str : stuff) {
					str = str.replace(" ", "_");
					if (str.equals(""))
						str = (char) k++ + "";
					if (str.equals("Banner ID"))
						sql2 = sql2 + str + " VARCHAR(127) not NULL, ";
					else
		 				sql2 = sql2 + str + " VARCHAR(127), ";
				}		
				
				sql2 = sql2 + " PRIMARY KEY ( Banner_ID ))";
				System.out.println(sql2);
				stmt.executeUpdate(sql2);
				System.out.println("Created table in given database...");*/
				createDatabases();
			} else {
				/*int k = 0;
				String sql3 = "INSERT INTO INFO VALUES (";
				for (String str : stuff) {
					sql3 += "\'" + str + "\',";
					k++;
				}
				//System.out.println(k);
				sql3 += ")";
				sql3 = sql3.substring(0, sql3.lastIndexOf(',')) + sql3.substring(sql3.lastIndexOf(',') + 1, sql3.length());
				//System.out.println(sql3);
				stmt = conn.createStatement();
				stmt.executeUpdate(sql3);
				k = 0;
				for (String str : stuff) {
					//System.out.print(k++ + ": " + str + "\t");
				}*/
				//if (i < 99350) {i++; continue; }
				//String sqlStudent = "INSERT INTO student VALUES (";
				stmt = conn.createStatement();
				//stmt.executeUpdate("");	//for students
				String sqlCourse = "";
				String sqlCInstance;
				String sqlSTC;
				System.out.println("what is it: " + i + " " + stuff[56]);
				int k = 0;
				for (String str : stuff) {
					System.out.print(k++ + ": " + str + "\t");
				}
				System.out.println("");
				if (!(stuff[40].isEmpty() || stuff[42].isEmpty() || stuff[35].isEmpty() || stuff[56].isEmpty())) {
					insertStudents(stuff);
					insertCourses(stuff);
					insertCourseInstances(stuff);
					insertStudentCoursesTaken(stuff);
				}
				//if (stuff[57].equals("Luis")) break;
				
				//stmt.executeQuery("INSERT INTO student VALUES ('" + stuff[56] + "', '" + );
				
				
			}
			//for (String str : stuff)
				//System.out.print(str + " ");
			i++;
			//System.out.println("");
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
	
	//STEP 4: Execute a query
	//System.out.println("Inserting records into the table...");
	/*stmt = conn.createStatement();
	
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
	rs.close();*/
	
 }catch(SQLException se){
	//Handle errors for JDBC
	se.printStackTrace();
 }catch(Exception e){
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
 System.out.println("Goodbye!");
}//end main
}//end JDBCExample