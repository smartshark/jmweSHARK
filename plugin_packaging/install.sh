#!/bin/sh
PLUGIN_PATH=$1

cd $PLUGIN_PATH

# Build jar file
./gradlew shadowJar

cp build/libs/jmweSHARK*.jar ../build/jmweSHARK.jar
