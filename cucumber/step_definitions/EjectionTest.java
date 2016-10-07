package step_definitions;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: added basic steps for courses and students, implemented Catalogue and Transcript
 * 9/22 - Preston: Made test work for multiple students
 */

import cucumber.api.java.en.*;
import cucumber.api.PendingException;
import implementation.Registrar;
import implementation.StudentCourseManager;
import java.util.Map;
import java.util.HashMap;
import static org.junit.Assert.*;

public class EjectionTest {
	private Registrar rg = new Registrar();
	private StudentCourseManager scm;
	
	@Given("^course \"([^\"]*)\" has prerequisites \"([^\"]*)\"$")
	public void courseHasPrerequisites(String courseDesignation, String prereqList) throws Throwable {
		rg.addCourse(courseDesignation, prereqList);
	}

	@Then("^the prerequisites for \"([^\"]*)\" are \"([^\"]*)\"$")
	public void thePrerequisitesForAre(String courseDesignation, String prereqList) throws Throwable {
		assertEquals(prereqList, rg.getPrereqs(courseDesignation));
	}
	
	@Given("^student \"([^\"]*)\" has taken course \"([^\"]*)\" with grade \"([^\"]*)\"$")
	public void studentHasTakenCourseWithGrade(String banner, String course, String grade) throws Throwable {
		//I feel like this check should not be done in our tests, but if the expansion of our scope does not make this ugly and annoying then it should be fine
		/*if (!ts.containsKey(banner)) {
			ts.put(banner, new Transcript());
		}
		//this might not need to be done here
		ts.get(banner).takeClass(course, grade, 3);*/
		
		rg.addStudentInfo(banner, course, grade);
	}
	
	@Then("^\"([^\"]*)\" transcript should read \"([^\"]*)\"$")
	public void transcriptShouldRead(String banner, String transcript) throws Throwable {
		assertEquals(rg.getStudentInfo(banner), transcript);
	}
	
	@Then("^\"([^\"]*)\" class count should be (\\d+)$")
	public void classCountShouldBe(String banner, int classCount) throws Throwable {
		assertEquals(rg.getStudentClassCount(banner), classCount);
	}
	
	@Then("^\"([^\"]*)\" gpa should be \"([^\"]*)\"$")
	public void gpaShouldBe(String banner, String gpa) throws Throwable {
		assertEquals(rg.getStudentGPA(banner), Double.parseDouble(gpa), .01);
	}
	
	@Then("^student \"([^\"]*)\" may take \"([^\"]*)\" is \"([^\"]*)\"$")
	public void studentMayTakeIs(String banner, String course, String takeable) throws Throwable {
		//get String of prereqs
		//pass prereqs to Roster method
		//method loops through student's Transcript to compare prereqs
		//if a prereq is not met, returns false, else returns true at the end
		assertEquals(rg.canStudentTakeCourse(banner, course), Boolean.parseBoolean(takeable));
	}
	
	/*@Given("^database \"([^\"]*)\" and csv \"([^\"]*)\"$")
	public void databaseAndCsv(String sql, String csv) throws Throwable {
		je = new JDBCExample(sql, csv);
	}

	@Then("^if CRN \"([^\"]*)\" is entered \"([^\"]*)\" may not take the course$")
	public void ifCRNIsEnteredMayNotTakeTheCourse(String crn, String banner) throws Throwable {
		assertEquals(je.checkCRN(crn), banner);
	}*/
	
	@Given("^CSV \"([^\"]*)\"$")
	public void csv(String csv) throws Throwable {
	    scm = new StudentCourseManager(csv);
	    scm.parseCRN();
	}

	@Then("^student \"([^\"]*)\" exists is \"([^\"]*)\"$")
	public void studentExistsIs(String banner, String bool) throws Throwable {
	    assertEquals(scm.studentExists(banner), Boolean.parseBoolean(bool));
	}
	
	@Then("^student \"([^\"]*)\" is classification \"([^\"]*)\"$")
	public void studentIsClassificationIs(String banner, String c) throws Throwable {
	    assertEquals(scm.studentClass(banner), c);
	}
	
	@Then("^student \"([^\"]*)\" is taking CRN \"([^\"]*)\" is \"([^\"]*)\"$")
	public void studentIsTakingCRNIs(String banner, String crn, String bool) throws Throwable {
	    assertEquals(scm.studentTakingCourse(banner, crn), Boolean.parseBoolean(bool));
	}
	
	@Then("^student \"([^\"]*)\" has name \"([^\"]*)\"$")
	public void studentHasName(String banner, String fullName) throws Throwable {
	    assertEquals(scm.getFullStudentName(banner), fullName);
	}
	
	@Then("^student \"([^\"]*)\" has (\\d+) earned hours$")
	public void studentHasEarnedHours(String banner, int hours) throws Throwable {
	    assertEquals(scm.earnedHoursOfStudent(banner), hours);
	}
	
	@Then("^student \"([^\"]*)\" has (\\d+) grade points$")
	public void studentHasGradePoints(String banner, int points) throws Throwable {
	    assertEquals(scm.earnedGradePointsOfStudent(banner), points, .001);
	}
	
	@Then("^student \"([^\"]*)\" has GPA \"([^\"]*)\"$")
	public void studentHasGPA(String banner, String gpa) throws Throwable {
	    assertEquals(scm.getGPAOfStudent(banner), Double.parseDouble(gpa), .001);
	}
	
	@Then("^student \"([^\"]*)\" can meet requirement of course \"([^\"]*)\" with grade of \"([^\"]*)\"$")
	public void studentCanMeetRequirementOfCourseWithGradeOf(String banner, String code, String grade) throws Throwable {
	    assert(scm.studentMeetsCourseAndGradePrereq(banner, code, grade));
	}
	
	@Then("^instructor \"([^\"]*)\" teaches CRN \"([^\"]*)\"$")
	public void instructorTeachesCRN(String instructor, String crn) throws Throwable {
	    assertEquals(scm.getInstructor(crn), instructor);
	}
	
	@Then("^CRN \"([^\"]*)\" is a course of code \"([^\"]*)\"$")
	public void crnIsACourseOfCode(String crn, String courseCode) throws Throwable {
	    assertEquals(scm.getCodeFromCRN(crn), courseCode);
	}
	
	@Then("^CRN \"([^\"]*)\" has prereq of minimum earned hours (\\d+)$")
	public void crnHasPrereqOfMinimumEarnedHours(String crn, int hours) throws Throwable {
	    assertEquals(scm.getCRNEarnedHoursPrereq(crn), hours);
	}
	
	@Then("^CRN \"([^\"]*)\" has prereq of GPA \"([^\"]*)\"$")
	public void crnHasPrereqOfGPA(String crn, String gpa) throws Throwable {
	    assertEquals(scm.getCRNGPAPrereq(crn), Double.parseDouble(gpa), 0.0);
	}
	
	@Then("^CRN \"([^\"]*)\" has prereq of classification \"([^\"]*)\"$")
	public void crnHasPrereqOfClassification(String crn, String c) throws Throwable {
		assertEquals(scm.getCRNPrereqClass(crn), c);
	}
	
	@Then("^CRN \"([^\"]*)\" has prereq of \"([^\"]*)\" with minimum grade of \"([^\"]*)\"$")
	public void crnHasPrereqOfWithMinimumGradeOf(String crn, String pCRN, String pGrade) throws Throwable {
		assert(scm.getPrereqsOfCRN(crn).contains(pCRN + "," + pGrade));
	}
	
	@Then("^student \"([^\"]*)\" meets the prereqs for CRN \"([^\"]*)\" is \"([^\"]*)\"$")
	public void studentMeetsThePrereqsForCRNIs(String banner, String crn, String bool) throws Throwable {
	    assert(Boolean.parseBoolean(bool) == scm.getAllStudentsThatDoNotMeetPrereqs(crn).contains(banner));
	}
	
	@Then("^students \"([^\"]*)\" should be removed from CRN \"([^\"]*)\"$")
	public void studentsShouldBeRemovedFromCRN(String bannerList, String crn) throws Throwable {
	    assertEquals(bannerList, scm.getAllStudentsThatDoNotMeetPrereqs(crn));
	}

	@Then("^course \"([^\"]*)\" has code \"([^\"]*)\"$")
	public void courseHasCode(String crn, String code) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(code,scm.getCodeFromCRN(crn));
	}

	@Then("^course \"([^\"]*)\" has a required GPA of \"([^\"]*)\"$")
	public void courseHasARequiredGPAOf(String crn, String gpa) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(gpa,Double.toString(scm.getCRNGPAPrereq(crn)));
	}

	@Then("^course \"([^\"]*)\" has a required earned hrs of \"([^\"]*)\"$")
	public void courseHasARequiredEarnedHrsOf(String crn, String hrs) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(hrs,Integer.toString(scm.getCRNEarnedHoursPrereq(crn)));
	}

	@Then("^course \"([^\"]*)\" has a required classification of \"([^\"]*)\"$")
	public void courseHasARequiredClassificationOf(String crn, String classification) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(classification,scm.getCRNPrereqClass(crn));
	}

	@Then("^course \"([^\"]*)\" has prerequisite \"([^\"]*)\"$")
	public void courseHasPrerequisite(String crn, String output) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(output,scm.getPrereqsOfCRN(crn));
	}
}