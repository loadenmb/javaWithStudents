#! /bin/bash

javac -cp .:lib/junit4.12.jar:lib/hamcrest-core-1.3.jar carXMLtest.java
java -cp .:lib/junit4.12.jar:lib/hamcrest-core-1.3.jar carXMLtest
exit 0
