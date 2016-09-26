package implementation;

import java.util.*;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: created implementation to store student data - courses and grades, GPA, and hours 
 */

public class Transcript {
	private int hoursTaken;
	private double numPoints;	//before divided by hours to get true GPA
	private int numClasses;
	private int passed;	//seems unnecessary for this project, so maybe should be deleted
	private Vector<String> classesList;
	private Map<String, String> coursesAndGrades;
	
	public Transcript() {
		hoursTaken = 0;
		numPoints = 0.0;
		numClasses = 0;
		passed = 0;
		classesList = new Vector<String>();
		coursesAndGrades = new HashMap<String, String>();
	}
	
	public void takeClass (String aClassname, String aGrade, int inHours) {
		//if student has not taken this course yet
		if (!classesList.contains(aClassname)) {
			hoursTaken += inHours;
			numPoints += convertGrade(aGrade) * inHours;
			numClasses++;
			if (!aGrade.equals("F"))
				passed++;
			classesList.add(aClassname);
			coursesAndGrades.put(aClassname, aGrade);
		//if student has taken this course but earns a better grade
		} else if (coursesAndGrades.get(aClassname).charAt(0) > aGrade.charAt(0)) {
			//System.out.println("Old: " + coursesAndGrades.get(aClassname) + "\nNew: " + aGrade);
			if (coursesAndGrades.get(aClassname).charAt(0) == 'F')
				passed++;
			numPoints -= convertGrade(coursesAndGrades.get(aClassname)) * inHours;
			numPoints += convertGrade(aGrade) * inHours;
			coursesAndGrades.put(aClassname, aGrade);
		} // else the student earns a non-better grade in a course they have already taken, so its transcript is not affected
		
	}
	
	/* 
	 * returns the numerical equivalent of a given letter grade
	 * returns a double
	 */
	private double convertGrade(String letter) {
		if (letter.equals("A"))
			return 4.0;
		else if (letter.equals("B"))
			return 3.0;
		else if (letter.equals("C"))
			return 2.0;
		else if (letter.equals("D"))
			return 1.0;
		//assuming that nothing other than A-D, F will be entered
		return 0.0;
	}
	
	public int classCount() {	//answers the number of classes taken
		return numClasses;
	}
	
	public int hours() { // answers the total hours taken
		return hoursTaken;
	}
	
	public double points() { // answers the total "grade points" earned
		return numPoints;
	}
	
	public double gpa() { //answers the overall GPA
		return points() / hours();
	}
	
	public int getPassed() { //returns the number of courses passed
		return passed;
	}
	
	//returns the alphabetic grade for a given class 
	public String getGradeOfClass(String className) {
		return coursesAndGrades.get(className);
	}
	
	/*
	 * retrieves all classes taken with their respective grades
	 * returns a String
	 */
	public String getTranscript(){
		String fullList = "";
		for (String str : classesList)
			fullList += str + "," + coursesAndGrades.get(str) + ",";
		fullList = fullList.substring(0, fullList.length() - 1);
		return fullList;
		
	}
}