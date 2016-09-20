#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>
using namespace std;

int main() {
	string filename;
	cin >> filename;
	//string prefixes[] = {"Math", "CS", "IT", "ACCT", "BUSA", "MGMT"};
	//string suffixes[] = {"101", "110", "111", "120", "210", "211", "301", "304", "310", "324"};

	vector<string> prefixes;
	prefixes.push_back("Math");
	prefixes.push_back("CS");
	prefixes.push_back("IT");
	prefixes.push_back("ACCT");
	prefixes.push_back("BUSA");
	prefixes.push_back("MGMT");

	vector<string> suffixes;
	suffixes.push_back("101");
	suffixes.push_back("110");
	suffixes.push_back("111");
	suffixes.push_back("120");
	suffixes.push_back("210");
	suffixes.push_back("211");
	suffixes.push_back("301");
	suffixes.push_back("304");
	suffixes.push_back("310");
	suffixes.push_back("324");

	ofstream fout;
	fout.open(filename.c_str());
	
	//make 10 students
	for (int j = 0; j < 10; j++) {
		//banner
		string banner = "000";
		for (int k = 0; k < 6; k++) {
			char newC = rand() % 10 + 48;
			cout << "char: " << newC;
			banner = banner + newC;
		}
		cout << endl;
		//classes		
		for (int k = rand() % 5 + 0; k < 10; k++) {
			fout << banner << ",";
			cout << "banner id" << endl;
			fout << prefixes.at(rand() % 5) << suffixes.at(rand() % 9) << ",";
			char c = rand() % 6 + 65;
			while (c == 'E')
				c = rand() % 6 + 65;
			fout << c << "," << rand() % 3 + 1 << endl;
		}
		cout << "class list" << endl;
	}
  	fout.close();
}