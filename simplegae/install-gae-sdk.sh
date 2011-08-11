echo "Going to install AppEngine and unpack it."
echo "Press any key to continue..."; read

# STEP 1: Installation of AppEngine dependencies (version 1.4.3)
export M2_REPO=~/.m2/repository
export GAE_VERSION=1.4.3

# Installing Google AppEngine dependencies

# Install appengine-java-sdk with the following command and unzip the installed file (which is under ${M2_REPO}/com/google/appengine/appengine-java-sdk/${GAE_VERSION}/ directory)
mvn install:install-file -DgroupId=com.google.appengine -DartifactId=appengine-java-sdk -Dversion=${GAE_VERSION} -Dpackaging=zip -Dfile=./files/appengine-java-sdk-${GAE_VERSION}.zip
unzip -d ~/.m2/repository/com/google/appengine/appengine-java-sdk/${GAE_VERSION} ~/.m2/repository/com/google/appengine/appengine-java-sdk/${GAE_VERSION}/appengine-java-sdk-${GAE_VERSION}.zip

echo "Going to install AppEngine dependencies."
echo "Press any key to continue..."; read

# Install appengine-local-runtime
mvn install:install-file -DgroupId=com.google.appengine -DartifactId=appengine-local-runtime -Dversion=${GAE_VERSION} -Dpackaging=jar -Dfile=${M2_REPO}/com/google/appengine/appengine-java-sdk/${GAE_VERSION}/appengine-java-sdk-${GAE_VERSION}/lib/impl/appengine-local-runtime.jar

# Now the application is ready to be compiled and run.

echo "Now is the time to compile the application."

