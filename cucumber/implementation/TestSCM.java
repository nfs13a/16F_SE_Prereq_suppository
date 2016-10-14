package implementation;

import java.util.Scanner;
import java.sql.*;

public class TestSCM {
	public static void main(String[] args) throws SQLException {
		StudentCourseManager scm = new StudentCourseManager();
		String crn;
		String code;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a code: ");
		code = input.next();

		String CRNs = scm.getAllCRNs(code);
		System.out.print("Enter a CRN (" + CRNs + "): ");
		crn = input.next();
		
		input.close();
		
		System.out.println(scm.getAllStudentsThatDoNotMeetPrereqs(crn,code));
		
	}
}