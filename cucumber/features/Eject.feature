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
		#Given course "ACCT211" has prerequisites "ACCT210,C"
		#And course "ACCT324" has prerequisites "ACCT210,C,BUSA120,C"
		#And student "000000001" has taken courses "ACCT210" with grade "C"
		#Then student "000000001" has taken 1 course
		#And student "000000001" may take "ACCT211"
		#And student "000000001" may not take "ACCT324"

	#Scenario:
		#Given course "ACCT211" has prerequisites "ACCT210,C"
		#And a student "000000004" has taken courses "ACCT210" with grade "B"
		#And a student "000000002" has taken courses "ACCT209" with grade "A"
		#Then course "ACCT211" has 2 prerequisites
		#And student "000000004" may take "ACCT211"
		#And student "000000002" may not take "ACCT211"

	Scenario: info about ACCT211
		Given course "ACCT211" has prerequisites "ACCT210,C"
		Then the prerequisites for "ACCT211" are "ACCT210,C"

	Scenario: info about ACCT324
		Given course "ACCT324" has prerequisites "ACCT210,C,BUSA120,C"
		Then the prerequisites for "ACCT324" are "ACCT210,C,BUSA120,C"

	Scenario: info about fake class DET410
		Given course "DET410" has prerequisites "DET000,DET100,C"
		Then the prerequisites for "DET410" are "DET000,D,DET100,C"

	Scenario: info about IT110
		Given course "IT110" has prerequisites ""
		Then the prerequisites for "IT110" are ""

	Scenario: info about student
		Given student "000000001" has taken course "ACCT210" with grade "A"
		Then "000000001" transcript should read "ACCT210,A"
		
	Scenario: multiple classes with multiple prerequisites
		Given course "ACCT210" has prerequisites "ACCT209,C,ACCT208,D"
		Given course "CS377" has prerequisites "CS120,C,CS220,A"
		Then the prerequisites for "ACCT210" are "ACCT209,C,ACCT208,D"
		Then the prerequisites for "CS377" are "CS120,C,CS220,A"
	
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

	Scenario: info about ACCT211
		Given course "ACCT211" has prerequisites "ACCT210"
		And course "ACCT211" has prerequisites "ACCT210,C"
		Then the prerequisites for "ACCT211" are "ACCT210,C"

	#should not need to test how many courses have been passed, since that will not be useful for our final product.

	Scenario: 3 classes
		Given course "CS130" has prerequisites ""
		And course "CS230" has prerequisites "CS130,C"
		And course "CS324" has prerequisites "CS230,IT220,D"
		Then the prerequisites for "CS130" are ""
		And the prerequisites for "CS324" are "CS230,D,IT220,D"
		And the prerequisites for "CS230" are "CS130,C"

	#9/26 - Nevan and Stephen implemented first test of Registrar
	Scenario: test comparisons of prereqs and transcripts
		Given course "ACCT211" has prerequisites "ACCT210,C"
		And student "000000004" has taken course "ACCT210" with grade "B"
		And student "000000002" has taken course "ACCT209" with grade "A"
		Then course "ACCT211" has prerequisites "ACCT210,C"
		And student "000000004" may take "ACCT211" is "true"
		But student "000000002" may take "ACCT211" is "false"
	
	#9/27 - Preston completed testing for Registrar with the following 3 Scenarios:
	Scenario: can students take classes
		Given course "ACCT211" has prerequisites "ACCT210,C"
		And course "CS220" has prerequisites "CS120,D"
		And course "IT221" has prerequisites "IT120,D,IT220,C"
		And student "000000001" has taken course "ACCT210" with grade "D"
		And student "000000002" has taken course "CS120" with grade "C"
		And student "000000003" has taken course "IT120" with grade "D"
		And student "000000003" has taken course "IT220" with grade "D"
		And student "000000004" has taken course "IT120" with grade "A"
		And student "000000004" has taken course "IT220" with grade "B"
		Then student "000000001" may take "ACCT211" is "false"
		And student "000000002" may take "CS220" is "true"
		And student "000000003" may take "IT221" is "false"
		And student "000000004" may take "IT221" is "true"
		
	Scenario: can students take classes
		Given course "ACCT211" has prerequisites "ACCT210,C"
		And course "CS220" has prerequisites "CS120,D"
		And course "IT221" has prerequisites "IT120,D,IT220,C"
		And student "000000001" has taken course "ACCT210" with grade "D"
		And student "000000002" has taken course "CS120" with grade "C"
		And student "000000003" has taken course "IT120" with grade "D"
		And student "000000003" has taken course "IT220" with grade "D"
		And student "000000004" has taken course "IT120" with grade "A"
		And student "000000004" has taken course "IT220" with grade "B"
		Then student "000000001" may take "ACCT211" is "false"
		And student "000000002" may take "CS220" is "true"
		And student "000000003" may take "IT221" is "false"
		And student "000000004" may take "IT221" is "true"
		
	Scenario: can students take classes
		Given course "ACCT211" has prerequisites "ACCT210,C,ACCT209,D,ACCT208,A"
		And course "CS220" has prerequisites "CS120,D,CS110,C"
		And course "IT221" has prerequisites "IT120,D,IT220,C,IT130,D,IT110,A"
		And student "000000001" has taken course "ACCT210" with grade "D"
		And student "000000001" has taken course "ACCT209" with grade "D"
		And student "000000001" has taken course "ACCT208" with grade "A"
		And student "000000002" has taken course "CS120" with grade "C"
		And student "000000002" has taken course "CS110" with grade "A"
		And student "000000003" has taken course "IT120" with grade "D"
		And student "000000003" has taken course "IT220" with grade "C"
		And student "000000003" has taken course "IT130" with grade "C"
		And student "000000003" has taken course "IT110" with grade "B"
		And student "000000003" has taken course "IT110" with grade "C"
		And student "000000004" has taken course "IT120" with grade "A"
		And student "000000004" has taken course "IT220" with grade "B"
		And student "000000004" has taken course "IT220" with grade "A"
		And student "000000004" has taken course "IT130" with grade "F"
		And student "000000004" has taken course "IT130" with grade "A"
		And student "000000004" has taken course "IT110" with grade "A"
		Then student "000000001" may take "ACCT211" is "false"
		And student "000000002" may take "CS220" is "true"
		And student "000000003" may take "IT221" is "false"
		And student "000000004" may take "IT221" is "true"
		And student "000000004" may take "CS220" is "false"

	Scenario: simple CRN
		Given CRN "testCSV1.csv"
		Then student "000817979" exists is "true"
		And student "000817979" is classification "SR"
		And student "000817979" is taking CRN "14788" is "false"
		And student "000817979" has name "Ms. Bernard Bonnie Duffy"
		And student "000817979" has 27 earned hours
		And student "000817979" has 99 grade points
		And student "000817979" has GPA "3.66"
		And student "000817979" can meet requirement of course "ACCT211" with grade of "C"
		And instructor "Clements, Curtis" teaches CRN "14788"
		And CRN "14788" is a course of code "ACCT324"
		And CRN "14788" has prereq of minimum earned hours 24
		And CRN "14788" has prereq of GPA "0.0"
		And CRN "14788" has prereq of classification "FR"
		And CRN "14788" has prereq of "ACCT211" with minimum grade of "C"
		And student "000817979" meets the prereqs for CRN "14788" is "true"

	#Scenario:
		#Given database "studentTestData1.sql"
		#Then if CRN "56789" is entered "000000001" may not take the course
		