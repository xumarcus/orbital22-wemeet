# orbital22-wemeet

# Setup

- Clone [me](https://github.com/xumarcus/orbital22-wemeet)
- View &rarr; Maven &rarr; Lifecycle &rarr; Install
- Settings &rarr; Plugins &rarr; `EnvFile 3.2` &rarr; Install
- `Main` Configuration:
  - `com.orbital22.Main`
  - Enable `EnvFile` and add local `.env`

## Database
- [PostgresSQL 14](https://www.postgresql.org/download/)
- [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
- Outgoing connection to port 5432 should be open (blocked by NUS, use VPN or external)
- Heroku dyno should be up (Open app to confirm)
- Register at Heroku and ping me to add you to team
```shell
heroku login
heroku ps:psql
heroku local env
```
- Create local `.env` file in `/`
- Append `JDBC_DATABASE_URL=` with value from command above 
- Can connect through `heroku ps:psql` or `pgAdmin4` or Heroku Dataclip

## IDE
- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/) 
- JDK 18.0.1
- Settings &rarr; Editor &rarr; Code Style &rarr; Wrapping and braces &rarr; Chained method calls &rarr; Wrap always

## Deployment
```shell
git push heroku main
```