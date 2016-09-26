package implementation;

import java.util.Vector;

import implementation.Catalogue;
import implementation.Roster;

public class Registrar {
	Catalogue cg;
	Roster rs;
	
	public Registrar() {
		cg = new Catalogue();
		rs = new Roster();
	}
	
	public void addCourse(String courseDesignation, String prereqList) {
		cg.add(courseDesignation, prereqList);
	}
	
	public void addStudentInfo(String banner, String course, String grade) {
		//hard-coding in the hours each class is worth here until necessary to change
		rs.addStudentInfo(banner, course, grade, 3);
	}
	
	public String getPrereqs(String courseDesignation) {
		return cg.getPrereqs(courseDesignation);
	}
	
	public String getStudentInfo(String banner) {
		return rs.getStudentInfo(banner);
	}
	
	public int getStudentClassCount(String banner) {
		return rs.getStudentClassCount(banner);
	}
	
	public double getStudentGPA(String banner) {
		return rs.getStudentGPA(banner);
	}
	
	public boolean canStudentTakeCourse(String banner, String course) {
		//get String of prereqs
		//pass prereqs to Roster method
		//method loops through student's Transcript to compare prereqs
		//if a prereq is not met, returns false, else returns true at the end
		
		Vector<String> tempPrereqs = cg.getPrereqData(course);
		Vector<String> tempTaken = rs.getTranscriptData(banner);
		for (int i = 0; i < tempPrereqs.size(); i+=2) {
			boolean foundPrereq = false;
			for (int j = 0; j < tempTaken.size(); j++) {
				
				/*System.out.println("tempPrereqs size: " + tempPrereqs.size());
				System.out.println("tempTaken size: " + tempTaken.size());
				
				System.out.println("course: " + tempTaken.elementAt(j));
				System.out.println("prereq course: " + tempPrereqs.elementAt(i));
				System.out.println("grade: " + rs.getGrade(banner, tempTaken.elementAt(j)).charAt(0));
				System.out.println("prereq grade: " + tempPrereqs.elementAt(i + 1).charAt(0));*/
				
				if (tempTaken.elementAt(j).equals(tempPrereqs.elementAt(i)) && rs.getGrade(banner, tempTaken.elementAt(j)).charAt(0) <= tempPrereqs.elementAt(i + 1).charAt(0)) {
					foundPrereq = true;
					break;
				}
				if (!foundPrereq) return false;
			}
		}
		
		return true;
	}
}