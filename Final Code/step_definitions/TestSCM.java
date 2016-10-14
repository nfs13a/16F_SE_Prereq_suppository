package step_definitions;

import implementation.StudentCourseManager;
import java.util.Scanner;

public class TestSCM {
	public static void main(String[] args) {
		StudentCourseManager scm = null;
		String csv;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a CSV (--put options here--): ");
		csv = input.next();
		input.close();
		
		scm = new StudentCourseManager(csv);
	    scm.parseCRN();
	}
}