package implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Lukkedoerendunandurraskewdylooshoofermoyportertooryzooysphalnabortansporthaokansakroidverjkapakkapuk
 * 9/21 - Nevan and Stephen: created implementation to store course data - designation and prerequesites
 */

public class Catalogue {
	//key - course designation; value - string of prereqs, comma  
	private Map<String, Vector<String>> coursesData;
	
	public Catalogue() {
		coursesData = new HashMap<String, Vector<String>>();
	}
	
	public void add(String courseName, String prereqList) {
		Vector<String> temp = new Vector<String>();
		int i = 0;
		for (String str : prereqList.split(",")) {
			//adds a D for minimum grade required if none given
			if (i % 2 == 1 && str.length() > 1) {
				temp.add("D");
				i++;
			}
			temp.add(str);
			i++;
		}
		coursesData.put(courseName, temp);
	}
	
	public String getPrereqs(String courseName) {
		String fullList = "";
		for (String str : coursesData.get(courseName))
			fullList += str + ",";
		fullList = fullList.substring(0, fullList.length() - 1);
		return fullList;
	}
}