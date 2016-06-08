## Build

`mvn clean install`

## Heroku

- [X] Web
- [X] Database
- [X] Worker

Living at https://polynome-cloud-reference-app.herokuapp.com

For local dev:
`heroku local web`

To deploy:
`git push heroku`


## Google App Engine

- [ ] Web
- [ ] Database
- [ ] Worker

## Amazon Elastic Beanstalk

- [ ] Web
- [ ] Database
- [ ] Worker

## Pivotal Web Services (Cloud Foundry)

- [X] Web
- [X] Database
- [X] Worker

Living at http://cloud-reference-app-web.cfapps.io/

To deploy:
`cf push`

Cloud Foundry makes it difficult to send command-line arguments to an
app. To switch between web and worker, we set the enviornmental
variable SPRING_PROFILES_ACTIVE to the appropriate type.