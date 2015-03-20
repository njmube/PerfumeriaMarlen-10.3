all: cloud-update data-update

code-update: pom.xml
	git pull
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P cloud-test
	sudo service tomcat7 stop
	mvn clean install -P cloud-test
	sudo service tomcat7 start
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud-test

data-update: pom.xml
	make -C pmarlen-development-tasks all

