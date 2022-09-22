# Voting System

@authors : Kyle Bekken, Naviin Vejaya Kumar, John Cullom, Anna Frenz


Directory information:

- src/ : Contains all source files
- testing/ : Contains all unit test files
- testing/JUnitLogs : Log files for all junit test cases
- testing/SystemTestLogs : Log file for all system tests
- misc/ : Location for a media file (resultFile.txt) & an audit file (auditFile.txt)
- documentation/ : Select "index.html" to view javadoc. 
- buglog : List of bugs.

To run program:

1) Place csv files containing election information in "testing/InputFiles" folder.

2) Compile with by navigating to the Project1 folder and do “javac -d . src/*.java”
    - add asterisks before the ".java" if you get errors: github removes them from the README.md on the website
	- if you get errors with the program recognizing class names, do step 4)

3) After compiling, navigate to the Project1 folder:
    - Place all input files in testing/InputFiles folder
    - To run OPL election, enter command "java p1.Main testing/InputFiles".
    - To run IRV election, enter command "java p1.Main testing/InputFiles" or "java p1.Main src/InputFiles S" to turn off the shuffling of ballots.
    
    - To run the Tests, enter command "java p1.TestRunner".

4) To set up the test environment on a Keller 1-250 CSE Labs machine:
    - mkdir ~/junit

    - wget https://github.com/downloads/junit-team/junit/junit-4.10.jar -P ~/junit/

    - export JAVA_HOME=/usr/bin
    - export JUNIT_HOME=~/junit
    - export CLASSPATH=.:$CLASSPATH:$JUNIT_HOME/junit-4.10.jar

    The last three export statements must be done every time you open a new terminal to run the tests on. After that, the TestRunner.java 
    class can be compiled.


