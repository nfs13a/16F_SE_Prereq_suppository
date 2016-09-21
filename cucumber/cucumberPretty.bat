rem how do we make this box agnostic if we have to deal with "jars/*:." vs "jars/*;." (latter for windows)???

rem java -cp "jars/*;." cucumber.api.cli.Main -p pretty features -g step_definitions

javac -cp "jars/*;." step_definitions/EjectionTest.java
java -cp "jars/*;." cucumber.api.cli.Main -f pretty --snippets camelcase -g step_definitions features
