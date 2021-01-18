# Tree radius

Original task description can be found [here](/docs/task-description.md)

## Run from source code 
Pre-requirements
* JDK 11
* Maven

To run execute a command from source code root folder
```
mvn spring-boot:run
```

## REST service
Data can be retrieved using POST request.
```
POST /search HTTP/1.1
Accept: application/json
Content-Type: application/json;charset=UTF-8

{
	x:-73.84421522,
	y:40.72309177,
	radius:10.0
}
```

Also, for convenience implemented UI page which performs Ajax requests to `REST service`
```
http://localhost:8080/
```
![UI.png](/docs/UI.png)
