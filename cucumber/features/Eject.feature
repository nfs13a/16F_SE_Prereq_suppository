Feature: people take a class

#he seems to only ever teaach these two classes, but could be more
#sometimes teaches more than one section

#for fall 2016: 
	#211 - 10902 Prerequisite: ACCT 210 with a grade of "C" or better.
	#324 - 10903 Prerequisites: ACCT 210, BUSA 120, both with grade of "C" or better.

	#Scenario: Dr. Clements enters ACCT 211 's CRN
		#Given the user inputs 10902
		#Then students "000174094,000795431,000154209,000266038,000349651,000586124,000325258,000825441,000634288" should not be in the class

	#Scenario: Dr. Clements enters ACCT 324 's CRN

	#Scenario: student takes a class
		#Given course "ACCT211" has prerequesites "ACCT210,C"
		#And course "ACCT324" has prerequesites "ACCT210,C,BUSA120,C"
		#And student "000000001" has taken courses "ACCT210" with grade "C"
		#Then student "000000001" has taken 1 course
		#And student "000000001" may take "ACCT211"
		#And student "000000001" may not take "ACCT324"

	#Scenario:
		#Given course "ACCT211" has prerequesites "ACCT210,C"
		#And a student "000000004" has taken courses "ACCT210" with grade "B"
		#And a student "000000002" has taken courses "ACCT209" with grade "A"
		#Then course "ACCT211" has 2 prerequesites
		#And student "000000004" may take "ACCT211"
		#And student "000000002" may not take "ACCT211"

	Scenario: info about ACCT211
		Given course "ACCT211" has prerequesites "ACCT210,C"
		Then the prerequesites for "ACCT211" are "ACCT210,C"

	Scenario: info about ACCT211
		Given course "ACCT324" has prerequesites "ACCT210,C,BUSA120,C"
		Then the prerequesites for "ACCT324" are "ACCT210,C,BUSA120,C"

	Scenario: info about fake class DET410
		Given course "DET410" has prerequesites "DET000,DET100,C"
		Then the prerequesites for "DET410" are "DET000,D,DET100,C"

	Scenario: info about IT110
		Given course "IT110" has prerequesites ""
		Then the prerequesites for "IT110" are ""

	Scenario: info about student
		Given student "000000001" has taken course "ACCT210" with grade "A"
		Then "000000001" transcript should read "ACCT210,A"
		
	Scenario: multiple classes with multiple prerequisites
	  Given course "ACCT210" has prerequesites "ACCT209,C,ACCT208,D"
	  Given course "CS377" has prerequesites "CS120,C,CS220,A"
	  Then the prerequesites for "ACCT210" are "ACCT209,C,ACCT208,D"
	  Then the prerequesites for "CS377" are "CS120,C,CS220,A"
	
	Scenario: Students with transcripts
	  Given student "000000001" has taken course "CS120" with grade "C"
	  Given student "000000001" has taken course "CS220" with grade "B"
	  Given student "000000002" has taken course "IT210" with grade "A"
	  Then "000000001" transcript should read "CS120,C,CS220,B"
	  And "000000002" transcript should read "IT210,A"
	  
	Scenario: Students with class counts
	  Given student "000000001" has taken course "CS120" with grade "C"
	  Given student "000000001" has taken course "CS220" with grade "B"
	  Given student "000000002" has taken course "IT210" with grade "A"
	  Given student "000000002" has taken course "IT211" with grade "C"
	  Given student "000000002" has taken course "MATH377" with grade "A"
	  Given student "000000003" has taken course "IS411" with grade "F"
	  Then "000000001" class count should be 2
	  And "000000002" class count should be 3
	  And "000000003" class count should be 1

	Scenario: Students with gpas
		Given student "000000001" has taken course "CS120" with grade "C"
		Given student "000000001" has taken course "CS220" with grade "B"
		Given student "000000002" has taken course "IT210" with grade "A"
		Given student "000000002" has taken course "IT211" with grade "C"
		Given student "000000002" has taken course "MATH377" with grade "A"
		Given student "000000003" has taken course "IS411" with grade "F"
		Then "000000001" gpa should be "2.5"
		And "000000002" gpa should be "3.33"
		And "000000003" gpa should be "0"

	Scenario: Student retakes class 5 times, for some reason
		Given student "000675309" has taken course "IT110" with grade "F"
		And student "000675309" has taken course "IT110" with grade "F"
		And student "000675309" has taken course "IT110" with grade "D"
		And student "000675309" has taken course "IT110" with grade "A"
		And student "000675309" has taken course "IT110" with grade "C"
		Then "000675309" transcript should read "IT110,A"
		And "000675309" class count should be 1
		And "000675309" gpa should be "4.0"