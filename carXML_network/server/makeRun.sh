#! /bin/bash

javac carXMLserver.java
jar cfe carXMLserver.jar carXMLserver *.class
java -jar carXMLserver.jar
exit 0
