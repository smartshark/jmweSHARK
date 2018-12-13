#!/bin/sh
PLUGIN_PATH=$1

cd $PLUGIN_PATH

# Build jar file
mvn package
