Getting Started
# quasar
Api Rest created in Java for MELI

Requeriments

- Java 8 or higher
- Maven

## Controller Methods
- GetLocation: Returns a Response object, in this Response we found the concated message and Position of the
Ship using the coordinates of the Sats received in the method signature.

To calculate the Position of the Ship I used how base this https://www.i-ciencias.com/pregunta/4109/-trilateracion-usando-3-puntos-de-latitud-y-longitud-y-3-distancias-

## Uri´s
- POST - /topsecret: Returns  the Position of the Ship and message received to the Sats
- POST - /topsecret_split: Save the Satellites
- GET - /topsecret_split:  Try to get the Postion and message

## How To Exec
1. Must to be installed jdk-11.0.8 or higher

2. Clone the project
```
git clone https://github.com/AlexanderIglesias/quasar.git
```

3. Execute the following command for create the JAR file, the jar file will be created at the target folder
```
mvn package
```

4. Execute Java -jar, the quasar-0.0.1-SNAPSHOT.jar are in the target folder
```
java -jar project\quasar\target\quasar-0.0.1-SNAPSHOT.jar
```

5. If you want to excecute using an IDE, you can run as SpringBoot 

6. You can prove the Verbs (GET, POST)

8. the properties are in the application.yml, you can find this in quasar\src\main\resources\application.yml

9. To test i used an H2 DB, if you want to see the console you can use http://localhost:8080/h2-console
the JDBC is jdbc:h2:mem:quasarDB

## How Test

To get an best idea in the test, i used this tool, https://www.geogebra.org/m/Wk7Y7N6V using this i get a best satellites positions and the possible position of the Ship
The API Rest hosted in http://quasar-env.eba-cgtaezc3.us-east-2.elasticbeanstalk.com/



json to test

```
{
    "satellites": [
        {
            "name": "kenobi",
            "distance": 603.2,
            "message": ["este", "", "", "mensaje", ""]
        },
        {
            "name": "skywalker",
            "distance": 385.16,
            "message": ["", "es", "", "", "secreto"]
        },
        {
            "name": "sato",
            "distance": 601.06,
            "message":  ["este", "", "un", "", ""]
        }
    ]
}
```


## Author

#### Fabian Alexander Iglesias Montalvo
