package implementation;

import java.util.Scanner;
import java.sql.*;

public class TestSCM {
	public static void main(String[] args) throws SQLException {
		StudentCourseManager scm = new StudentCourseManager();
		String crn;
		String code;
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a code (ACCT211, ACCT324): ");
		code = input.next();

		if (code.equals("ACCT211")) System.out.print("11024, 11032, 11060, 11074, 14206, 14213, 14440, 14710, 14716, 14787, 14796, 20467, 20474, 30078");
		else if (code.equals("ACCT324")) System.out.print("Enter a CRN (11033, 11073, 14207, 14208, 14715, 14788, 14789, 15440, 20468, 20469): ");
		else System.out.print("Anything else: ");
		crn = input.next();
		
		input.close();
		
		System.out.println("Banners that should not belong: " + scm.getAllStudentsThatDoNotMeetPrereqs(crn,code));
		
	}
}