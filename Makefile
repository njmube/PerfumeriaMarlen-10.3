

code-update: pom.xml
	git pull

cloud-prod-redeploy:
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P cloud_prod
	mvn clean install -P cloud_prod
	sudo service tomcat7 stop
	sudo service tomcat7 start
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud_prod

cloud-prod-deploy:
	mvn clean install -P cloud_prod
	sudo service tomcat7 stop
	sudo service tomcat7 start
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud_prod

cloud-test-deploy:
	mvn clean install -P cloud_test
	~/tomcat7_test/bin/shutdown.sh
	~/tomcat7_test/bin/startup.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud_test

cloud-test-redeploy:
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P cloud_test
	mvn clean install -P cloud_test
	~/tomcat7_test/bin/shutdown.sh
	~/tomcat7_test/bin/startup.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P cloud_test

#-------------------------------------------------------------------

local-dev-deploy:
	mvn clean install -P local_dev
	~/tomcat7_test/bin/shutdown.sh
	~/tomcat7_test/bin/startup.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P local_dev
	mvn -pl pmarlen-rest-services tomcat7:deploy -P local_dev

local-dev-redeploy:
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P local_dev
	mvn -pl pmarlen-rest-services tomcat7:undeploy -P local_dev
	mvn clean install -P cloud_test
	~/tomcat7_test/bin/shutdown.sh
	~/tomcat7_test/bin/startup.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P local_dev
	mvn -pl pmarlen-rest-services tomcat7:deploy -P local_dev

local-stage-redeploy:
	mvn -pl pmarlen-web-ligth tomcat7:undeploy -P local_stage
	mvn -pl pmarlen-rest-services tomcat7:undeploy -P local_stage
	mvn clean install -P cloud_stage
	~/tomcat7_stage/bin/shutdown.sh
	~/tomcat7_stage/bin/startup.sh
	mvn -pl pmarlen-web-ligth tomcat7:deploy -P local_stage
	mvn -pl pmarlen-rest-services tomcat7:deploy -P cloud_stage

cloud_test_data-update: pom.xml
	make -C pmarlen-development-tasks resetDB_test MYSQL_ROOT_PASSWORD=root

#cloud_prod_data-update: pom.xml
#	make -C pmarlen-development-tasks resetDB_prod MYSQL_ROOT_PASSWORD=root

local_test-data-update: pom.xml
	make -C pmarlen-development-tasks resetDB_test MYSQL_ROOT_PASSWORD=root

local_prod-stage_update: pom.xml
	make -C pmarlen-development-tasks resetDB_prod MYSQL_ROOT_PASSWORD=root

