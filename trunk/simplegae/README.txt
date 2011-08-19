Run install-gae-sdk.sh in order to install appengine-local-runtime
Run mvn clean install to compile the application
Run run-gae.sh to run the application locally (it requires a Sun SDK and might not work with OpenJDK, what a shame)
Create a new AppEngine application (http://appengine.google.com/)
Modify ./simplegae-appengine/src/main/webapp/WEB-INF/appengine-web.xml and put in your application name
Run deploy-gae.sh to deploy the application

