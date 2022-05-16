# orbital22-wemeet

## Database
- [PostgresSQL 14](https://www.postgresql.org/download/) (Comes with `pgAdmin4`)
- [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
- Outgoing connection to port 5432 should be open (blocked by NUS, use VPN or external)
```shell
heroku login
heroku ps:psql  # Check connection
heroku run env -a orbital22-wemeet-staging # FIXME
```
- Create local `.env` file in `/`
- Append `JDBC_DATABASE_URL=` with value from command above

## IDE and Code Style
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) 
- OpenJDK 11
- Settings &rarr; Editor &rarr; Code Style &rarr; Wrapping and braces &rarr; Chained method calls &rarr; Wrap always

# Setup
```shell
git clone https://github.com/xumarcus/orbital22-wemeet
heroku git:remote -a orbital22-wemeet-dev -r heroku-dev
heroku git:remote -a orbital22-wemeet-staging -r heroku
```
- View &rarr; Maven &rarr; Lifecycle &rarr; Install
- Settings &rarr; Plugins &rarr; `EnvFile 3.2` &rarr; Install
- `Main` Configuration:
  - `com.orbital22.Main`
  - Enable `EnvFile` and add local `.env`

## Deployment
To staging:
```shell
git push heroku main
```