#!/bin/sh

mvn -Dmaven.test.skip=true install
cd tinywebgears-appengine
mvn -Dmaven.test.skip=true gae:run
cd -

