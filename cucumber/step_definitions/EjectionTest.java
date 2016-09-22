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
	
	@Given("^course \"([^\"]*)\" has prerequesites \"([^\"]*)\"$")
	public void courseHasPrerequesites(String courseDesignation, String prereqList) throws Throwable {
		//allows for multiple courses per test
		cg.add(courseDesignation, prereqList);
	}

	@Then("^the prerequesites for \"([^\"]*)\" are \"([^\"]*)\"$")
	public void thePrerequesitesForAre(String courseDesignation, String prereqList) throws Throwable {
		assertEquals(prereqList, cg.getPrereqs(courseDesignation));
	}
	
	@Given("^student \"([^\"]*)\" has taken course \"([^\"]*)\" with grade \"([^\"]*)\"$")
	public void studentHasTakenCourseWithGrade(String banner, String course, String grade) throws Throwable {
		if (!ts.containsKey(banner)) {
			ts.put(banner, new Transcript());
		}
		ts.get(banner).takeClass(course, grade, 3);
	}
	
	@Then("^\"([^\"]*)\" transcript should read \"([^\"]*)\"$")
	public void transcriptShouldRead(String banner, String transcript) throws Throwable {
		assertEquals(ts.get(banner).getTranscript(), transcript);
	}
	

}