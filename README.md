# orbital22-wemeet

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
- Settings &rarr; Editor &rarr; Code Style &rarr; Java &rarr; Wrapping and braces &rarr; Chained method calls &rarr; Wrap always

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
- Run &rarr; Check `localhost:5000`

## Deployment
Heroku's Github integration is currently broken. Please deploy manually.
```shell
git push heroku-dev main # to dev
git push heroku main # to staging
```