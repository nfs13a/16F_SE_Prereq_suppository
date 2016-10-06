package step_definitions;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: added basic steps for courses and students, implemented Catalogue and Transcript
 * 9/22 - Preston: Made test work for multiple students
 */

import cucumber.api.java.en.*;
import cucumber.api.PendingException;
import implementation.Registrar;
import java.util.Map;
import java.util.HashMap;
import static org.junit.Assert.*;

public class EjectionTest {
	private Registrar rg = new Registrar();
	
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
	
	@Given("^CRN \"([^\"]*)\" and file \"([^\"]*)\"$")
	public void crnAndFile(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I want to know who doesn't belong$")
	public void iWantToKnowWhoDoesnTBelong() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should know that \"([^\"]*)\" doesn't belong$")
	public void iShouldKnowThatDoesnTBelong(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
}