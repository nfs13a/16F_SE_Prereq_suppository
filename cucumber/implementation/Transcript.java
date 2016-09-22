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
	private int passed;
	private Map<String, String> coursesAndGrades;
	
	public Transcript() {
		hoursTaken = 0;
		numPoints = 0.0;
		numClasses = 0;
		passed = 0;
		coursesAndGrades = new HashMap<String, String>();
	}
	
	public void takeClass (String aClassname, String aGrade, int inHours) {
		coursesAndGrades.put(aClassname, aGrade);
		hoursTaken += inHours;
		numPoints += (double) (convertGrade(aGrade) * inHours);
		if (!aGrade.equals("F"))
			passed++;
		numClasses++;
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
		//assuming that nothing other than A-D, F will be entered
		return 0.0;
	}
	
	public int classCount() {	//answers the number of classes taken
		return numClasses;
	}
	
	public int hours() { // answers the total hours taken
		return hoursTaken;
	}
	
	public double points() { //answers the total "grade points" earned
		return numPoints;
	}
	
	public double gpa() { //answers the overall GPA
		return points() / hours();
	}
	
	public int getPassed() {
		return passed;
	}
	
	public String getGradeOfClass(String className) {
		return coursesAndGrades.get(className);
	}
	
	public String getTranscript(){
		String fullList = "";
		for (Map.Entry<String,String> entry : coursesAndGrades.entrySet())
			fullList += entry.getKey() + "," + entry.getValue() + ",";
		fullList = fullList.substring(0, fullList.length() - 1);
		return fullList;
		
	}
}