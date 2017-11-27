## Hack 06 - Proteger webservices

```
blade samples blade.rest
```

Search for CXF Endpoints
Create a new CXFEndpoint publisher configuration providing a context path (e.g., /rest-test).
Go back to System Settings → Foundation and select REST Extender.
Create a new REST extender configuration (i.e., search with rest) providing context paths (e.g., /rest-test) and jaxrs.applications.filters set to (jaxrs.application=true)

```
UsersRestService.java
```

```
curl http://localhost:8080/o/rest-test/blade.users/list/
```

copy snippet

```
curl -v http://localhost:8080/o/rest-test/blade.users/list/
```

```
echo -n 'Algo dificil de advinharname=Andrenote=Hello'|openssl dgst -sha256
```

```
curl -v http://localhost:8080/o/rest-test/blade.users/list/?name=Andre&note=Hello&signature=4eeb410d23cfa57f5e1ced116b9b65256ad3426abfc731c50aec052ae7d28392
```
