<h2>Instructions</h2>
Use the following commands from the project root directory to run the application.

<h3>1) Build the application:</h3>

    mvn clean package

<h3>2a) Run it using Docker:</h3>

    docker build -t nearby-earthquakes . && docker run -it nearby-earthquakes

OR

<h3>2b) Run the jar file directly:</h3>

    java -jar target/nearby-earthquakes.jar

    