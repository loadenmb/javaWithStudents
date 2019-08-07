#! /bin/bash

javac carXMLclient.java
jar cfe carXMLclient.jar carXMLclient *.class
java -jar carXMLclient.jar
exit 0
