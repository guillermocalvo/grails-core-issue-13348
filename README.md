## grails-core issue 13348

- [Issue](https://github.com/grails/grails-core/issues/13348)

### Test

1. Start MongoDB server:

```
docker compose up
```

2. Run the demo app:

```
./gradlew bootRun
```

3. Create some new `Foo` records:

- <http://localhost:8080/foo/create>

4. Show all `Foo` records stored in MongoDB:

- <http://localhost:8080/foo/index>

5. A message like this should be logged for each record after it's loaded:

```
AFTER LOAD -- FOO [x, y, z]
```
