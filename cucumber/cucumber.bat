rem how do we make this box agnostic if we have to deal with "jars/*:." vs "jars/*;." (latter for windows)???

java -cp "jars/*;." cucumber.api.cli.Main -p pretty
