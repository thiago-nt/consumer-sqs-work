## Preparing the environment

To deploy this application you have to have the Serverless Framework installed. To do so, first install NodeJS. The minimum required version is 6.10.
When the installation is complete, run the command below:

    $ npm install serverless -g

Then you must set your AWS credentials with the following command:

    $ serverless config credentials --provider aws --key <AWS_ACCESS_KEY_ID> --secret <AWS_SECRET_ACCESS_KEY> -o

To build the application, you need to have OpenJDK 8 installed. This application uses Gradle, in its wrapper form, so only the JDK is needed.

## Building

Building is as simple as running this single command, from the project root:

    $ ./gradlew build

## Deploying

After preparing the environment and building the packages, all that there is left is deployment. As always, it is a single command:

    $ serverless deploy -s <environment>

