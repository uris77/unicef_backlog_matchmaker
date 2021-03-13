# Digital Public Goods Alliance Skills Matchmaker
This is a proof of concept (POC) of a simple matchmaking app based on skills required by projects from the DPGA and 
skills available in a pool of volunteers.

## Structure

This POC is made up of a backend and frontend component. They are built & deployed independently.

The backend provides an API for interacting with the matchmaking engine, and retrieving the results
of a matchmaking "run" from the database. 

The frontend is a minimal React App that interacts with the backend to get a set of projects that
matches a set of skills. The persona for the frontend is any person volunteering their time and skills.

<img src="https://storage.googleapis.com/openstep_public/poc_architecture.png" />


### Creating Stubbed Data
You need to stub some data in `Firebase` for the following collections:
- organizations: {name, repository, website}
- projects: {name, organization (the id of the pertinent organization)}
- projectSkills: {skill, project (the id of the pertinent project)}
- volunteers: {email: volunteer1@email.com, name: "Any Name"}. Note that the email has to be `volunteer1@email.com`. You can add skills via the `matchmaker` API
using the `/updateSkills` endpoint.

## Matchmaking Engine

The Matchmaking Engine is a JVM application that uses [Drools](https://drools.org) as its `Rules Engine`.
It is deployed as a docker container to `Cloud Run`. This service makes deploying workloads that need to scale down
to 0 very affordable and convenient.

### Structure
- __dao__: Contains the data model for marshalling and unmarshalling data from Firebase.
This is necessary because the data stored in Firebase does not follow the same structure as the models used by Rules Engine.
- __domain__: These are the domain models used internally by the `Rules Engine`.
- __engine__: Is responsible for instantiating a `Rules Engine` and inserting the facts before firing all the rules.
- __repository__: A firebase repository for interacting with the data in Firebase.

### Building
__Requirements__:
- [Docker](https://docker.com)
- Java 8
- A GCP account and project with permissions to deploy to `Cloud Run`
- gcloud
- A service account in GCP that has permissions to deploy to `Cloud Run` and `Firebase` access.
- The key for the service account.
- Make
- Create a Firebase Project

1. Change to the `matchmaker` directory. From now on all commands for the backend will be run from this directory.
2. Build the application: `./gradlew shadowJar`
3. Copy the key for your service account to the `matchmaker` directory as `perms.json`.
4. Build the docker image: `docker build -t matchmaker-1.0 .`
5. Submit the image to Google Cloud Run: `make buildGCR`
6. Deploy to Cloud Run: `make deployCloudRun`
7. If it deploys successfully, a url will be printed that is the endpoint for this service.

Run the `matchmaker` locally with: `docker run -p 8080:8080 -e GOOGLE_APPLICATION_CREDENTIALS=/app/creds.json  matchmaker-1.0`.
This will create a docker container and expose port `8080`.


### Endpoints
1. __POST /updateSkills__ : post a list of strings that represent the skills you are volunteering for.
2. __POST /matchmake__ : does not require a body. It pulls the data from Firebase and instantiates the `Rules Engine` with this data. The matches found are pushed back to Firebase.
3. __GET /matched__ : retrieves the projects that match your sets of skills.

## Web Frontend
The frontend is a simple React application. It's purpose is to exercise the `Rules Engine`. It is an isolated use case of how volunteers would 
register and provide their skills. The `Rules Engine` will then use these skills to `match` the volunteer with a project. However, in reality,
this would be automated at a periodic interval. The `matchmaking engine` will be triggered via a cronjob and generating a set of matches,
which would then be handed over to a `notification service` that would send an email with a list of projects to the volunteers.

### Structure
- __api__: contains data models and utilities used to interact with the `matchmaker`.
- __components__: React components used by `pages`.
- __pages__: the actual screens rendered
- __providers__: state that is passed down from the root component to its children.
- __models__ : a stubbed list of data that would otherwise be available in some database.

### Running Locally
- Start the `matchmaker` locally.
- Bootup the frontend locally with `yarn start`.
- You can now view the application on `http://localhost:3000`.

### Deploying
You can deploy to `Firebase Hosting`. Create a `web application` in your `Firebase` project and follow the instructions they provide
for initializing an application.
Edit the `.env.production` file and replace the value for `REACT_APP_API_URL` with the url for your deployment of the `matchmaker`.
Then build the project: `yarn build`.
And deploy: `firebase deploy --only hosting:thep-name-of-the-project-on-firebase`.

