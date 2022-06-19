# orbital22-wemeet (0.2.0)

## Database
- [PostgresSQL 14](https://www.postgresql.org/download/) (Comes with `pgAdmin4`)

### Remote connection
- [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
- Open port 5432 for outgoing traffic (use VPN if blocked)
```shell
heroku login
heroku pg:psql -a orbital22-wemeet-dev # Check connection
heroku run env -a orbital22-wemeet-dev
# Pipe to findstr /b JDBC_DATABASE_URL > .env and set encoding to UTF-8
# Or copy JDBC=... from terminal 
```
- Install `EnvFile` plugin for IDEA
- In configuration for `Main`, enable `EnvFile` and add local `.env`

### Local connection
- Open `pgAdmin4` app
- Set (master) password for `postgres` user to `password`

## IDE and Code Style
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
  - Git &rarr; Before Commit &rarr; Reformat, Rearrange, Optimize, Analyze, Check TODO
- Java:
  - OpenJDK 11
  - Plugins &rarr; `google-java-format` &rarr; Enable
- Javascript:
  - Do not format: `*.js*`
  - `npm install` installs `standard` which is run before `npm start`
- SQL naming is `snake_case`. While Spring magic automatically transforms entity names to snake case,
  indicate `@Table(name)` to avoid confusion and more importantly, help the IDE.

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
- View &rarr; Maven &rarr; Lifecycle &rarr; Install
- Check database connection (see above)
- Build &rarr; Check `localhost:5000`

## Debugging

### Frontend
- NodeJS 16.15.0 (comes with NPM)
```shell
cd src/main/webapp/app
npm start
```

### Backend
- Live Reload extension
- HAL explorer (at `localhost:{PORT}/api/explorer/`)

### Configuration
Insert/replace these in `src/main/resources/application.properties`.
```
# More messages
logging.level.org.springframework={TRACE/DEBUG/INFO/ERROR}

# Even more messages (optional)
logging.level.com.zaxxer.hikari={TRACE/DEBUG/INFO/ERROR}

# Disable CORS, CSRF and firewall
spring.profiles.active=development

# Expose cookie to client
server.servlet.session.cookie.http-only=false
server.servlet.session.cookie.secure=false

# Actuator (optional)
management.endpoints.web.exposure.include=*
management.endpoint.shutdown.enabled=true
endpoints.shutdown.enabled=true
```

## Deployment
Profile is always `production`.

To deploy manually to `dev` server,
```shell
git checkout dev
git push heroku-dev dev:main
```
Every merged PR is automatically deployed to `dev`.

To `staging` server
```shell
git checkout main
git merge dev
git push heroku main
```
Release is manual.

To run migrations, (run automatically during deployment using `release`)
```shell
liquibase update --changelog-file
  .\src\main\resources\db\changelog\db.changelog-master.yaml
  --username {REDACTED}
  --password {REDACTED}
  --url jdbc:postgresql://{DATABASE_URL}:5432/{DATABASE_NAME}
```
Review apps will test your migrations from scratch.

## Notes
- No idea what magic `src/main/resources/liquibase.properties` is doing...
- Is Spring Boot LTS `2.6.3`?
- Whenever possible, use `Set` in models to avoid `MultipleBagFetchException`
- `user` is a keyword in Postgres. Breaking change in migration changelog is needed to rectify.
- Even without `release` migrations are run when the application boots. Just for the UI/UX.