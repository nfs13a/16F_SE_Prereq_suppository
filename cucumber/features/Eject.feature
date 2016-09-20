Feature: people take a class

#he seems to only ever teaach these two classes, but could be more
#sometimes teaches more than one section

#for fall 2016: 
	#211 - 10902 Prerequisite: ACCT 210 with a grade of "C" or better.
	#324 - 10903 Prerequisites: ACCT 210, BUSA 120, both with grade of "C" or better.

	Scenario: Dr. Clements enters ACCT 211 's CRN
		Given the user inputs 10902
		Then students "000174094,000795431,000154209,000266038,000349651,000586124,000325258,000825441,000634288" should not be in the class

	#Scenario: Dr. Clements enters ACCT 324 's CRN