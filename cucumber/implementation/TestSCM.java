package implementation;

import java.util.Scanner;
import java.sql.*;

public class TestSCM {
	public static void main(String[] args) throws SQLException {
		StudentCourseManager scm = new StudentCourseManager();
		String crn;
		String code;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a CRN (--put options here--): ");
		crn = input.next();
		System.out.print("Enter a code (--put options here--): ");
		code = input.next();
		input.close();
		
		System.out.println("Banners that should not belong: " + scm.getAllStudentsThatDoNotMeetPrereqs(crn,code));
		
	}
}