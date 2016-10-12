package implementation;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

public class StudentCourseManager {

	private String csvPath;
	private static Connection conn = null;
	private static Statement stmt = null;
	private String lol = new File("").getAbsolutePath();
	private static Vector<String> allBanners;
	private static Vector<String> allCodes;
	private static Vector<String> allCIs;
	private static Vector<String> allSCTs;

	public StudentCourseManager(String CSV) {
		if (CSV.equals("big")) { csvPath = "C:/Users/CPU8/Google Drive/Software Engineering/Project 1 Local backups/cs374_anon.csv";
			System.out.println("big");
		}
		else csvPath = lol + "/tests/" + CSV;
		
		allBanners = new Vector<String>();
		allCodes = new Vector<String>();
		allCIs = new Vector<String>();
		allSCTs = new Vector<String>();
	}

	public void parseCRN() {
		try {
			// default is: String csvFile = "C:/Users/CPU8/Google Drive/Software
			// Engineering/workspace/Beginnings/src/CSVParsing/cs374_anon.csv";
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			// for the csv
			try {
				br = new BufferedReader(new FileReader(csvPath));
				int i = 1;
				FileWriter studentCSV = new FileWriter(
						"C:/ProgramData/MySQL/MySQL Server 5.6/Data/students/studentTable.csv");
				FileWriter codeCSV = new FileWriter(
						"C:/ProgramData/MySQL/MySQL Server 5.6/Data/students/codeTable.csv");
				FileWriter ciCSV = new FileWriter("C:/ProgramData/MySQL/MySQL Server 5.6/Data/students/ciTable.csv");
				FileWriter sctCSV = new FileWriter("C:/ProgramData/MySQL/MySQL Server 5.6/Data/students/sctTable.csv");

				while ((line = br.readLine()) != null) {
					String[] stuff = StudentCourseManager.newSplit(line);
					if (i == 1) {
						// System.out.println("Creating database...");
						createDatabases(lol + "/tests/StudentsSetup.sql");
					} else {
						stmt = conn.createStatement();
						if (!(stuff[40].isEmpty() || stuff[42].isEmpty() || stuff[35].isEmpty()
								|| stuff[56].isEmpty())) {
							stuff[50] = stuff[50].substring(1, stuff[50].length() - 1);

							if (!allBanners.contains(stuff[56])) {
								String writeStudent = "";

								for (int l = 56; l < 61; l++) {
									writeStudent += stuff[l] + ",";
								}

								writeStudent += stuff[33] + "\n";

								allBanners.add(stuff[56]);
								studentCSV.append(writeStudent);
							}
							if (!allCodes.contains(stuff[40] + stuff[42])) {
								String writeCode = "";
								writeCode += stuff[40] + stuff[42] + "," + stuff[47] + "," + 0.0 + ",FR," + 0 + "\n";
								allCodes.add(stuff[40] + stuff[42]);
								codeCSV.append(writeCode);
							}
							if (!allCIs.contains(stuff[35] + stuff[40] + stuff[42])) {
								String writeCI = "";
								//System.out.println(stuff[50].substring(0, stuff[50].indexOf(",")));
								if (stuff[50].contains(",")) {
									writeCI += stuff[35] + "," + stuff[40] + stuff[42] + ","
											+ stuff[50].substring(stuff[50].indexOf(",") + 2) + " "
											+ stuff[50].substring(0, stuff[50].indexOf(",")) + "\n";
								} else {
									writeCI += stuff[35] + "," + stuff[40] + stuff[42] + "," + stuff[50] + "\n";
								}
								allCIs.add(stuff[35] + stuff[40] + stuff[42]); // CRN
																				// +
																				// code
								ciCSV.append(writeCI);
							}
							if (!allSCTs.contains(stuff[35] + stuff[40] + stuff[42] + stuff[56])) {
								String writeSCT = "";
								writeSCT += stuff[56] + "," + stuff[35] + "," + stuff[40] + stuff[42] + "," + stuff[48]
										+ "," + stuff[55] + "\n";

								allSCTs.add(stuff[35] + stuff[40] + stuff[42] + stuff[56]);
								sctCSV.append(writeSCT);
							}

						}
					}
					if (i % 10000 == 0) System.out.println(i);
					i++;
				}
				studentCSV.close();
				codeCSV.close();
				ciCSV.close();
				sctCSV.close();

				insertStudent("studentTable.csv");
				insertCourse("codeTable.csv");
				insertCourseInstance("ciTable.csv");
				insertStudentCourseTaken("sctTable.csv");

				setPrereqs();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	private void setPrereqs() throws SQLException {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		// for the prereqs
		try {
			br = new BufferedReader(new FileReader(lol + "/tests/PrereqCourses1.csv"));
			while ((line = br.readLine()) != null) {
				String[] stuff = StudentCourseManager.newSplit(line);
				insertPrereqCourses(stuff);
			}
			br = new BufferedReader(new FileReader(lol + "/tests/CourseTablePrereqs1.csv"));
			while ((line = br.readLine()) != null) {
				String[] stuff = StudentCourseManager.newSplit(line);
				insertPrereqOtherInfo(stuff);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createDatabases(String file) throws FileNotFoundException, IOException, SQLException {

		final String DB_URL = "jdbc:mysql://localhost/";

		// Database credentials
		final String USER = "root";
		final String PASS = ""; // insert your password here

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

	/*
	 * private static void insertAll(String[] stuff) throws SQLException {
	 * //String sqlTransaction = "BEGIN TRANSACTION";
	 * 
	 * if (!allBanners.contains(stuff[56])) { String writeStudent += "";
	 * 
	 * for (int l = 56; l < 61; l++) { writeStudent += stuff[l] + ","; }
	 * 
	 * writeStudent += stuff[33];
	 * 
	 * allBanners.add(stuff[56]);
	 * 
	 * //insertStudent(stuff); } if (!allCodes.contains(stuff[40] + stuff[42]))
	 * { sqlTransaction += "INSERT INTO course VALUES('" + stuff[40] + stuff[42]
	 * + "', '" + stuff[47] + "', " + 0.0 + ", 'FR', " + 0 + "); ";
	 * 
	 * allCodes.add(stuff[40] + stuff[42]); //insertCourse(stuff); } if
	 * (!allCIs.contains(stuff[35] + stuff[40] + stuff[42])) { sqlTransaction +=
	 * "INSERT INTO courseInstances VALUES('" + stuff[35] + "', '" + stuff[40] +
	 * stuff[42] + "', '" + stuff[50] + "'); ";
	 * 
	 * allCIs.add(stuff[35] + stuff[40] + stuff[42]); // CRN + code
	 * 
	 * //insertCourseInstance(stuff); } if (!allSCTs.contains(stuff[35] +
	 * stuff[40] + stuff[42] + stuff[56])) { sqlTransaction +=
	 * "INSERT INTO studentCoursesTaken VALUES('" + stuff[56] + "', '" +
	 * stuff[35] + "', '" + stuff[40] + stuff[42] + "', '" + stuff[48] + "', '"
	 * + stuff[55] + "'); ";
	 * 
	 * allSCTs.add(stuff[35] + stuff[40] + stuff[42] + stuff[56]);
	 * 
	 * //insertStudentCourseTaken(stuff); }
	 * 
	 * sqlTransaction += "COMMIT";
	 * 
	 * stmt.executeUpdate(sqlTransaction); }
	 */

	private static void insertStudent(String csvIn) throws SQLException {
		String sqlStudent = "LOAD DATA INFILE '" + csvIn + "' INTO TABLE student " + "FIELDS TERMINATED BY ','"
				+ "LINES TERMINATED BY '\n';";

		// System.out.println(sqlStudent);

		stmt.executeUpdate(sqlStudent);
	}

	public boolean studentExists(String banner) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM student WHERE banner = '" + banner + "';");
		rs.next();
		// System.out.println("num: " + rs.getInt("total"));
		return rs.getInt("total") == 1;
	}

	public String studentClass(String banner) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT classification FROM student WHERE banner = '" + banner + "';");
		rs.next();
		return rs.getString("classification");
	}

	public String getFullStudentName(String banner) throws SQLException {
		ResultSet rs = stmt
				.executeQuery("SELECT fn, ln, mn, pre FROM student WHERE banner = '" + banner + "' LIMIT 1;");
		rs.next();
		return rs.getString("pre") + " " + rs.getString("fn") + " " + rs.getString("mn") + " " + rs.getString("ln");
	}

	private static void insertCourse(String csvIn) throws SQLException {
		String sqlCourse = "LOAD DATA INFILE '" + csvIn + "' INTO TABLE course " + "FIELDS TERMINATED BY ','"
				+ "LINES TERMINATED BY '\n';";

		// System.out.println(sqlCourse);

		// System.out.println("SELECT COUNT(*) AS total FROM course
		// WHERE code = " + stuff[40] + stuff[42]);

		stmt.executeUpdate(sqlCourse);
	}

	private static void insertCourseInstance(String csvIn) throws SQLException {
		String sqlCourseInstance = "LOAD DATA INFILE '" + csvIn + "' INTO TABLE courseInstances "
				+ "FIELDS TERMINATED BY ','" + "LINES TERMINATED BY '\n';";

		// System.out.println(sqlCourseInstance);

		stmt.executeUpdate(sqlCourseInstance);
	}

	public String getInstructor(String crn) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT instructor FROM courseInstances WHERE CRN = '" + crn + "';");
		rs.next();
		// return rs.getString("instructor").substring(1,
		// rs.getString("instructor").length() - 1);
		return rs.getString("instructor");
	}

	public String getCodeFromCRN(String crn) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT code FROM courseInstances WHERE CRN = '" + crn + "';");
		rs.next();
		return rs.getString("code");
	}

	private static void insertStudentCourseTaken(String csvIn) throws SQLException {
		String sqlSCT = "LOAD DATA INFILE '" + csvIn + "' INTO TABLE studentCoursesTaken " + "FIELDS TERMINATED BY ','"
				+ "LINES TERMINATED BY '\n';";

		// System.out.println(sqlSCT);

		stmt.executeUpdate(sqlSCT);
	}

	public boolean studentTakingCourse(String banner, String crn) throws SQLException {
		ResultSet rs = stmt.executeQuery(
				"SELECT grade FROM studentCoursesTaken WHERE CRN = '" + crn + "' and banner = '" + banner + "';");
		boolean taking = false;
		while (rs.next()) {
			if (rs.getString("grade").equals(""))
				taking = true;
		}
		return taking;
	}

	public int earnedHoursOfStudent(String banner) throws SQLException {
		int totalHours = 0;
		Map<String, String> bestPassingCodesAndGrades = new HashMap<String, String>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM studentCoursesTaken WHERE banner = '" + banner + "';");
		Statement lnestmt = conn.createStatement();
		ResultSet lne = null;
		// System.out.println("student: " + banner);
		while (rs.next()) {
			String tempGrade = rs.getString("grade");
			String tempCourse = rs.getString("code");
			if (!bestPassingCodesAndGrades.containsKey(tempCourse)) {
				if (!tempGrade.equals("") && tempGrade.charAt(0) < 'F') {
					bestPassingCodesAndGrades.put(tempCourse, tempGrade);

					lne = lnestmt.executeQuery("SELECT hours FROM course WHERE code = '" + tempCourse + "';");
					lne.next();

					totalHours += lne.getInt("hours");
				}
			} else if (bestPassingCodesAndGrades.get(tempCourse).charAt(0) > tempGrade.charAt(0)) {
				bestPassingCodesAndGrades.put(tempCourse, tempGrade);
				// ResultSet rst = stmt.executeQuery("SELECT COUNT(CRN) FROM
				// studentCoursesTaken WHERE banner = '" + banner + "' AND code
				// = '" + tempCode + "'AND grade < '" + tempGrade + "';");
			}
		}
		return totalHours;
	}

	public double earnedGradePointsOfStudent(String banner) throws SQLException {
		double totalPoints = 0;
		Map<String, String> bestPassingCodesAndGrades = new HashMap<String, String>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM studentCoursesTaken WHERE banner = '" + banner + "';");
		Statement lnestmt = conn.createStatement();
		ResultSet lne = null;
		while (rs.next()) {
			String tempGrade = rs.getString("grade");
			String tempCourse = rs.getString("code");

			lne = lnestmt.executeQuery("SELECT hours FROM course WHERE code = '" + tempCourse + "';");
			lne.next();

			int tempHours = lne.getInt("hours");
			if (!tempCourse.equals("")) {
				if (bestPassingCodesAndGrades.containsKey(tempCourse)
						&& bestPassingCodesAndGrades.get(tempCourse).charAt(0) < tempGrade.charAt(0)
						&& tempGrade.charAt(0) < 'F' && tempGrade.charAt(0) >= 'A') {
					totalPoints -= convertGrade(bestPassingCodesAndGrades.get(tempCourse)) * tempHours;
					bestPassingCodesAndGrades.put(tempCourse, tempGrade);
					totalPoints += tempHours * convertGrade(tempGrade);
				} else {
					bestPassingCodesAndGrades.put(tempCourse, tempGrade);
					totalPoints += tempHours * convertGrade(tempGrade);
				}
			}
		}
		return totalPoints;
	}

	public double getGPAOfStudent(String banner) throws SQLException {
		double val = earnedGradePointsOfStudent(banner) / earnedHoursOfStudent(banner);
		if (Double.toString(val).length() > 4)
			return Double.parseDouble(Double.toString(val).substring(0, 4));
		return val;
	}

	private static void insertPrereqCourses(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[0] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 1) {
				String sqlPre = "INSERT INTO prereqCourse VALUES('" + stuff[1] + "', '" + stuff[0] + "', '" + stuff[2]
						+ "')";

				// System.out.println(sqlPre);

				stmt.executeUpdate(sqlPre);
			}
		}
	}

	public String getPrereqsOfCRN(String crn) throws SQLException {
		String allPrereqs = "";
		ResultSet rs = stmt.executeQuery(
				"SELECT codePre, grade FROM prereqCourse WHERE codePost = (SELECT code FROM courseInstances WHERE CRN = '"
						+ crn + "');");
		while (rs.next()) {
			allPrereqs += rs.getString("codePre") + "," + rs.getString("grade") + ",";
		}
		return allPrereqs;
	}

	private static void insertPrereqOtherInfo(String[] stuff) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM course WHERE code = " + "'" + stuff[0] + "';");
		if (rs.next()) {
			if (rs.getInt("total") == 1) {
				String sqlPre = "UPDATE course SET prereqGPA = '" + stuff[2] + "', prereqClass = '" + stuff[3]
						+ "', prereqEarnedHours = '" + stuff[1] + "' WHERE code = '" + stuff[0] + "';";

				// System.out.println(sqlPre);

				stmt.executeUpdate(sqlPre);
			}
		}
	}

	public int getCRNEarnedHoursPrereq(String crn) throws SQLException {
		// System.out.println("SELECT prereqEarnedHours FROM course WHERE code =
		// '(SELECT code FROM courseInstances WHERE CRN = '" + crn + "');)");
		ResultSet rs = stmt.executeQuery(
				"SELECT prereqEarnedHours FROM course WHERE code = (SELECT code FROM courseInstances WHERE CRN = '"
						+ crn + "');");
		rs.next();
		return rs.getInt("prereqEarnedHours");
	}

	public double getCRNGPAPrereq(String crn) throws SQLException {
		ResultSet rs = stmt.executeQuery(
				"SELECT prereqGPA FROM course WHERE code = (SELECT code FROM courseInstances WHERE CRN = '" + crn
						+ "');");
		rs.next();
		return rs.getFloat("prereqGPA");
	}

	public String getCRNPrereqClass(String crn) throws SQLException {
		ResultSet rs = stmt.executeQuery(
				"SELECT prereqClass FROM course WHERE code = (SELECT code FROM courseInstances WHERE CRN = '" + crn
						+ "');");
		rs.next();
		return rs.getString("prereqClass");
	}

	public boolean studentMeetsCourseAndGradePrereq(String banner, String code, String grade) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM studentCoursesTaken WHERE banner = '" + banner
				+ "' AND code = '" + code + "' AND grade <= '" + grade + "';");
		rs.next();
		return rs.getInt("total") >= 1;
	}

	public boolean studentMeetsPrereqs(String banner, String crn) throws SQLException {
		boolean meetsAllCourseAndGradePrereqs = true;
		Statement thingstmt = conn.createStatement();
		ResultSet rs = thingstmt.executeQuery(
				"SELECT * FROM prereqCourse WHERE codePost = (SELECT code FROM courseInstances WHERE CRN = '" + crn
						+ "');");
		while (rs.next()) {
			meetsAllCourseAndGradePrereqs = meetsAllCourseAndGradePrereqs
					&& studentMeetsCourseAndGradePrereq(banner, rs.getString("codePre"), rs.getString("grade"));
			// System.out.println("for " + rs.getString("codePre") + ": " +
			// studentMeetsCourseAndGradePrereq(banner, rs.getString("codePre"),
			// rs.getString("grade")));
		}

		return earnedHoursOfStudent(banner) >= getCRNEarnedHoursPrereq(crn)
				&& getGPAOfStudent(banner) >= getCRNGPAPrereq(crn)
				&& convertClassification(studentClass(banner)) >= convertClassification(getCRNPrereqClass(crn))
				&& meetsAllCourseAndGradePrereqs;
	}

	public String getAllStudentsThatDoNotMeetPrereqs(String crn) throws SQLException {
		String all = "";
		// ResultSet rs = stmt.executeQuery("SELECT * as total FROM
		// studentCoursesTaken WHERE CRN = '" + crn + "';");
		// System.out.println("###########################################################################################"
		// + rs.getInt("total"));
		Statement thingstmt = conn.createStatement();
		ResultSet rs = thingstmt
				.executeQuery("SELECT banner FROM studentCoursesTaken WHERE grade = '' AND CRN = '" + crn + "';");
		while (rs.next()) {
			// System.out.println(rs.getString("banner"));
			if (!studentMeetsPrereqs(rs.getString("banner"), crn))
				all += rs.getString("banner") + ",";
			// System.out.println("all: " + all);
		}
		return all;
	}

	private static String[] newSplit(String str) {
		// guaranteed to have 147 columns; any more are a mistake and/or not
		// meaningful
		String newStrings[] = new String[65]; // array of all fields to be
												// filled and returned
		int i = 0; // counter for number of fields inserted into the array; used
					// for counting and indexing into newStrings
		while (i < 65) {
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
			/*
			 * System.out.println("old temp: " + temp); if (temp.charAt(0) ==
			 * '\"' && temp.charAt(temp.length() - 1) == '\"' && temp.contains("
			 * ,")) temp = temp.replaceAll("\"", "");
			 * System.out.println("new temp: " + temp);
			 */
			newStrings[i++] = temp;
		}
		return newStrings;
	}

	private double convertGrade(String letter) {
		if (letter.equals("A"))
			return 4.0;
		else if (letter.equals("B"))
			return 3.0;
		else if (letter.equals("C"))
			return 2.0;
		else if (letter.equals("D"))
			return 1.0;
		// assuming that nothing other than A-D, F will be entered
		return 0.0;
	}

	private int convertClassification(String c) {
		if (c.equals("FR"))
			return 1;
		else if (c.equals("SO"))
			return 2;
		else if (c.equals("JR"))
			return 3;
		else if (c.equals("SR"))
			return 4;
		else if (c.equals("GR"))
			return 5;
		return 0;
	}
}