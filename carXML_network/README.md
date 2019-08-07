# Threaded HTTP & TCP server, XML to HTML XSLT transform + AWT UI TCP desktop JAXB XML client + tests

Threaded HTTP and TCP socket server to handle and display XML "car" data via JAXB with XML to HTML XSLT template transform + AWT desktop UI and TCP socket server to transfer "car" data to server + junit tests.

Short: Desktop UI application + server application to manage cars which work with XML.

[Threaded HTTP & TCP server, XML to HTML XSLT transform + AWT UI TCP desktop JAXB XML client + tests](https://github.com/loadenmb/javaWithStudents/tree/master/carXML_network)

**Warning: student example, do not use in productive environments**

[Student project overview](https://github.com/loadenmb/javaWithStudents)

## Features (technical)
server:
- threaded socket server 
	- to handle multiple xml clients at once 
- threaded HTTP server (not based on own socket server)
	- Sun server because it's well tested, fast and great to use
- xslt transformation xml to html
	- because we can use xslt file as template file for the website

client:
- TCP socket client
- AWT user interface
	- lightweight, fast ui :P
- known issues: 
    - if the variables in form sent have the wrong data type 

## Setup / Usage
```shell
# ...go to carXML_network root folder..

# compile, start server
cd server/ && chmod +x makeRun.sh && ./makeRun.sh 

# compile, start client
cd client/ && chmod +x makeRun.sh && ./makeRun.sh 

# compile, run tests
cd tests/ && chmod +x makeRun.sh && ./makeRun.sh 

```

## Details
| directory     |  description  |
| ------------- | ------------- |
| client/       | AWT desktop client to update server XML data via socket | 
| server/       | HTTP server with XSLT templates and update able XML source data via TCP socket | 
| tests/        | junit tests for server / client classes | 

Shared files between client and server are: *carXML.java* / *car.java*. They do:
- xml (un)-marshalling
- xml file handling
- xml car sorting
