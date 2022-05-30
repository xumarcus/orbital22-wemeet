# orbital22-wemeet (0.2.0)

## Database
- [PostgresSQL 14](https://www.postgresql.org/download/) (Comes with `pgAdmin4`)
- [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
- Outgoing connection to port 5432 should be open (blocked by NUS, use VPN or external)
```shell
heroku login
heroku pg:psql -a orbital22-wemeet-dev # Check connection
heroku run env -a orbital22-wemeet-dev
# Pipe to findstr /b JDBC_DATABASE_URL > .env and set encoding to UTF-8
# Or copy JDBC=... from terminal 
```

## IDE and Code Style
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- OpenJDK 11
- SQL naming is `snake_case`. While Spring magic automatically transforms entity names to snake case, indicate `@Table(name)` to avoid confusion and more importantly, help the IDE.

# Setup
```shell
git clone https://github.com/xumarcus/orbital22-wemeet
heroku git:remote -a orbital22-wemeet-dev -r heroku-dev
heroku git:remote -a orbital22-wemeet-staging -r heroku
```
- Settings &rarr; Plugins &rarr; `EnvFile 3.2` &rarr; Install
- `Main` Configuration:
    - Application
    - `com.orbital22.wemeet.Main`
    - Enable `EnvFile` and add local `.env`
- View &rarr; Maven &rarr; Lifecycle &rarr; Install
- Edit `src/main/application.properties` &rarr; `spring.profiles.active=development`
- Run &rarr; Check `localhost:5000`
- Front-end debugging: `cd src/main/webapp/app` then `npm start`

## Deployment
Heroku's Github integration is currently broken.

To dev server,
```shell
git checkout dev
git push heroku-dev dev:main
```
To staging server,
```shell
git checkout main
git merge dev
git push heroku main
```
To test out database changes, create another app in Heroku.

To run migrations, (run automatically during deployment using `release`)
```shell
liquibase update --changelog-file
  .\src\main\resources\db\changelog\db.changelog-master.yaml
  --username {REDACTED}
  --password {REDACTED}
  --url jdbc:postgresql://{DATABASE_URL}:5432/{DATABASE_NAME}
```

## Debugging
- No idea what magic `src/main/resources/liquibase.properties` is doing, but doing without somehow breaks deployment.
- No idea why validation fails to autoconfigure in Spring Boot `2.6.7` but works in `2.6.3`.
- Whenever possible, use `Set` in models to avoid `MultipleBagFetchException`
- Insert/replace these in `src/main/resources/application.properties` for `npm run start`. This disables CSRF and firewall.
```properties
logging.level.org.springframework=DEBUG
spring.profiles.active=development
server.servlet.session.cookie.http-only=false
server.servlet.session.cookie.secure=false
```