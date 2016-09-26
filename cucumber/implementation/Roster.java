package implementation;

import java.util.*;
import implementation.Transcript;

public class Roster {
	private Map<String, Transcript> ts;
	
	public Roster() {
		ts = new HashMap<String, Transcript>();
	}
	
	public void addStudentInfo(String student, String course, String grade, int hours) {
		if (!ts.containsKey(student)) {
			ts.put(student, new Transcript());
		}
		
		ts.get(student).takeClass(course, grade, hours);
	}
	
	public String getStudentInfo(String student) {
		return ts.get(student).getTranscript();
	}
	
	public int getStudentClassCount(String student) {
		return ts.get(student).classCount();
	}
	
	public double getStudentGPA(String student) {
		return ts.get(student).gpa();
	}
	
	public Vector<String> getTranscriptData(String student) {
		return ts.get(student).getTranscriptData();
	}
	
	public String getGrade(String student, String course) {
		return ts.get(student).getGrade(course);
	}
}