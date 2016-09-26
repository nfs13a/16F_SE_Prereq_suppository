package step_definitions;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: added basic steps for courses and students, implemented Catalogue and Transcript
 * 9/22 - Preston: Made test work for multiple students
 */

import cucumber.api.java.en.*;
import cucumber.api.PendingException;
import implementation.Catalogue;
import implementation.Transcript;
import java.util.Map;
import java.util.HashMap;
import static org.junit.Assert.*;

public class EjectionTest {
	private Catalogue cg = new Catalogue();
	private Map<String, Transcript> ts = new HashMap<String, Transcript>();
	
	@Given("^course \"([^\"]*)\" has prerequisites \"([^\"]*)\"$")
	public void courseHasPrerequisites(String courseDesignation, String prereqList) throws Throwable {
		//allows for multiple courses per test
		cg.add(courseDesignation, prereqList);
	}

	@Then("^the prerequisites for \"([^\"]*)\" are \"([^\"]*)\"$")
	public void thePrerequisitesForAre(String courseDesignation, String prereqList) throws Throwable {
		assertEquals(prereqList, cg.getPrereqs(courseDesignation));
	}
	
	@Given("^student \"([^\"]*)\" has taken course \"([^\"]*)\" with grade \"([^\"]*)\"$")
	public void studentHasTakenCourseWithGrade(String banner, String course, String grade) throws Throwable {
		//I feel like this check should not be done in our tests, but if the expansion of our scope does not make this ugly and annoying then it should be fine
		if (!ts.containsKey(banner)) {
			ts.put(banner, new Transcript());
		}
		ts.get(banner).takeClass(course, grade, 3);
	}
	
	@Then("^\"([^\"]*)\" transcript should read \"([^\"]*)\"$")
	public void transcriptShouldRead(String banner, String transcript) throws Throwable {
		assertEquals(ts.get(banner).getTranscript(), transcript);
	}
	
	@Then("^\"([^\"]*)\" class count should be (\\d+)$")
	public void classCountShouldBe(String banner, int classCount) throws Throwable {
		assertEquals(ts.get(banner).classCount(), classCount);
	}
	
	@Then("^\"([^\"]*)\" gpa should be \"([^\"]*)\"$")
	public void gpaShouldBe(String banner, String gpa) throws Throwable {
		assertEquals(ts.get(banner).gpa(), Double.parseDouble(gpa), .01);
	}
}