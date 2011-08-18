#!/bin/sh

mvn -Dmaven.test.skip=true install
cd simplegae-appengine
mvn -Dmaven.test.skip=true gae:run
cd -

