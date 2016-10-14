package implementation;

import java.util.Scanner;
import java.sql.*;

public class TestSCM {
	public static void main(String[] args) throws SQLException {
		StudentCourseManager scm = new StudentCourseManager("big");
		scm.parseCRN();
		
		String crn;
		String code;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a code (e.g. ACCT324): ");
		code = input.next();

		String CRNs = scm.getAllCRNs(code);
		System.out.print("Enter a CRN (" + CRNs + "): ");
		crn = input.next();
		
		input.close();
		
		System.out.println(scm.getAllStudentsThatDoNotMeetPrereqs(crn,code));
		
	}
}