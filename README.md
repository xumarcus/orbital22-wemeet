# orbital22-wemeet

# Setup

- Clone [me](https://github.com/xumarcus/orbital22-wemeet)
- View &rarr; Maven &rarr; Lifecycle &rarr; Install
- `Main` Configuration:
  - `com.orbital22.Main`
  - Environmental variables: `JDBC_DATABASE_URL={copy-from-command-below}`
  - TODO: Automate

## Database
- [PostgresSQL 14](https://www.postgresql.org/download/)
- [Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli#install-the-heroku-cli)
- Outgoing connection to port 5432 should be open (blocked by NUS, use VPN or external)
- Heroku dyno should be up (Open app to confirm)
- Register at Heroku and ping me to add you to team
```sh
heroku login
heroku ps:psql
heroku local env
```
- Copy `JDBC_DATABASE_URL` above
- Can connect through `heroku ps:psql` or `pgAdmin4` or Heroku Dataclip

## IDE
- [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/) 
- JDK 1.8
- TODO: bump to the latest JDK
- Settings &rarr; Editor &rarr; Code Style &rarr; Wrapping and braces &rarr; Chained method calls &rarr; Wrap always

- Add `Main` Configuration
