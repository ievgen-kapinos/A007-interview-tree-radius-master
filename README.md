# Tree radius

Original task description can be found [here](/docs/task-description.md)

## Run from source code 
* JDK 11
* Maven
 
```
mvn spring-boot:run
```

## REST service
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
![ui.png](/docs/ui.png)
