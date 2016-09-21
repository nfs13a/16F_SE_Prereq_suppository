package step_definitions;

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
		cg.add(courseDesignation, prereqList);
	}

	@Then("^the prerequesites for \"([^\"]*)\" are \"([^\"]*)\"$")
	public void thePrerequesitesForAre(String courseDesignation, String prereqList) throws Throwable {
		assertEquals(prereqList, cg.getPrereqs(courseDesignation));
	}
	
	@Given("^student \"([^\"]*)\"$")
	public void student(String banner) throws Throwable {
		ts = new Transcript();
	}

	@Given("^course \"([^\"]*)\" grade \"([^\"]*)\"$")
	public void courseGrade(String des, String grd) throws Throwable {
	    ts.takeClass(des,grd,3);
	}

	@Then("^their transcript should read \"([^\"]*)\"$")
	public void theirTranscriptShouldRead(String check) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		assertEquals(check, ts.getTranscript());
	}
}