# WebServer
This is a custom web server that will be used for a school project in SOFTENG2.
This project aims to immitate an http protocol server.

## Building
to build the project, just use the wrapper gradle file:

on Linux:
```
./gradlew build
```

on Windows:
```
gradle.bat build
```

## Running 
The output classes are found in app/build/classes/java/main. Therefore, you may
configure the IDE to run files that are in here, or just rin this in the
terminal:
```
java -cp app/build/classes/java/main/ com.webserver.Main
```

---

## Totally Valid Questions
> Why make this when you could just use an existing http server?
- Cuz yes. jk, this allows the backend person (me) to have more control over
  how the flow of data works. Also I have no experience with http yet so I'll
  just make this instead.

