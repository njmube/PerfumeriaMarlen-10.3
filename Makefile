all: cloud-update data-update

code-update: pom.xml
	git pull
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P cloud-test
	mvn -pl pmarlen-rest-services tomcat7:undeploy -P cloud-test
	sudo service tomcat7 stop
	mvn clean install -P cloud-test
	sudo service tomcat7 start
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud-test
	mvn -pl pmarlen-rest-services tomcat7:deploy -P cloud-test

code-update_0: pom.xml
	git pull
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P srv1-test
	mvn -pl pmarlen-rest-services tomcat7:undeploy -P srv1-test
	~/tomcat7/bin/shutdown.sh
	mvn clean install -P cloud-test
	~/tomcat7/bin/start.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P srv1-test
	mvn -pl pmarlen-rest-services tomcat7:deploy -P srv1-test

data-update: pom.xml
	make -C pmarlen-development-tasks all

data-update_0: pom.xml
	make -C pmarlen-development-tasks all_0

