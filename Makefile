#!/usr/bin/env bash
build:
	sudo mvn clean --quiet package --quiet spring-boot:repackage --quiet -Dmaven.test.skip=true
	sudo cp "target/app.jar" "docker/images/java/app.jar"; \
	sudo rm -rf target
	cd docker/ && sudo docker-compose build && sudo docker-compose up -d
