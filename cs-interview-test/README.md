# Readme

## Prerequisite

- Java version 8/11/+
- Maven

## Requirements

- create a new canvas
- can draw lines, rectangle, fill bucket
- can quit the program

## Assumptions

- when there is any drawing that has part exist inside the canvas, will automatically set the max to canvas max

## How to install

- `mvn install`

## how to run

- after install the project, you can get the jar by executing `java -jar target/cs-interview-test-1.0-SNAPSHOT.jar
  `

## How to run the test cases

- execute `mvn test`
- execute `mvn surefire-report:report` to generate a test report and located at `.\target\site\surefire-report.html`

