package step_definitions;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: added basic steps for courses and students, implemented Catalogue and Transcript
 */

import cucumber.api.java.en.*;
import cucumber.api.PendingException;
import implementation.Catalogue;
import implementation.Transcript;
import static org.junit.Assert.*;

public class EjectionTest {
	Catalogue cg = new Catalogue();
	Transcript ts;
	
	@Given("^course \"([^\"]*)\" has prerequesites \"([^\"]*)\"$")
	public void courseHasPrerequesites(String courseDesignation, String prereqList) throws Throwable {
		//allows for multiple courses per test
		cg.add(courseDesignation, prereqList);
	}

	@Then("^the prerequesites for \"([^\"]*)\" are \"([^\"]*)\"$")
	public void thePrerequesitesForAre(String courseDesignation, String prereqList) throws Throwable {
		assertEquals(prereqList, cg.getPrereqs(courseDesignation));
	}
	
	@Given("^student \"([^\"]*)\"$")
	public void student(String banner) throws Throwable {
		//does not yet allow for multiple students per test
		ts = new Transcript();
	}

	@Given("^course \"([^\"]*)\" grade \"([^\"]*)\"$")
	public void courseGrade(String des, String grd) throws Throwable {
		ts.takeClass(des,grd,3);
	}

	@Then("^their transcript should read \"([^\"]*)\"$")
	public void theirTranscriptShouldRead(String check) throws Throwable {
		assertEquals(check, ts.getTranscript());
	}
}