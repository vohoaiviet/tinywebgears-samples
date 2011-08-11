#!/bin/sh

mvn -Dmaven.test.skip=true install -o
cd simplegae-appengine
mvn -Dmaven.test.skip=true gae:run -o
cd -

