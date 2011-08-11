#!/bin/sh

mvn -Dmaven.test.skip=true install -o
cd simplegae-appengine
mvn gae:deploy -o
cd -

