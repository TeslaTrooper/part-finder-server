#!/bin/bash

echo "Deploying web application remotely"
scp -r target/part-finder-server.war pi@raspberrypi:/var/lib/tomcat8/webapps