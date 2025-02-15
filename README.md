<h2>Build</h2>
.\gradlew.bat build in project directory is enough. 
Alternatively, there is a pre-built binary in Releases.

<h2>Execution</h2>
java -jar [JAR] launches the built binary.

<h2>Example usage</h2>
http://localhost:8080/nthMax?path=test.xlsx&n=0
Returns 77, as 77 is the largest value in the test file
http://localhost:8080/nthMax?path=test.xlsx&n=1
Returns 12, as 12 is the second largest value in the test file

<h2>Swagger</h2>
Swagger is available at http://localhost:8080/swagger-ui.html

<h2>Algorithm</h2>
To find the Nth largest number, a 1-pivot QuickSelect algorithm is used
