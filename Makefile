allt: pom.xml
	~/apache-tomcat-6.0.37_test/bin/shutdown.sh
	~/apache-tomcat-6.0.37_test/bin/startup.sh
	mvn  clean install -Ptest -e -Dmaven.test.skip
	mvn  tomcat:deploy -Ptest -Dmaven.test.skip

uallt: pom.xml
	mvn  tomcat:undeploy -P test
	~/apache-tomcat-6.0.37_test/bin/shutdown.sh
	~/apache-tomcat-6.0.37_test/bin/startup.sh
	mvn  clean install -Ptest -e -Dmaven.test.skip 
	mvn  tomcat:deploy -Ptest -Dmaven.test.skip


allp: pom.xml
	~/apache-tomcat-6.0.37_prod/bin/shutdown.sh
	~/apache-tomcat-6.0.37_prod/bin/startup.sh
	mvn  clean install -Pprod -e -Dmaven.test.skip
	mvn  tomcat:deploy -Pprod -Dmaven.test.skip

uallp: pom.xml
	mvn  tomcat:undeploy -Pprod
	~/apache-tomcat-6.0.37_prod/bin/shutdown.sh
	~/apache-tomcat-6.0.37_prod/bin/startup.sh
	mvn  clean install -Pprod -e -Dmaven.test.skip 
	mvn  tomcat:deploy -Pprod -Dmaven.test.skip

# ===============================================================
#	~/apache-tomcat-6.0.37/bin/shutdown.sh
#	~/apache-tomcat-6.0.37/bin/startup.sh

pallt: pom.xml
	sudo /etc/init.d/tomcat6_hudson stop
	sudo /etc/init.d/tomcat6_hudson start
	mvn  clean install -Ptest -e -Dmaven.test.skip
	mvn  tomcat:deploy -Ptest -Dmaven.test.skip

puallt: pom.xml
	mvn  tomcat:undeploy -Ptest
	sudo /etc/init.d/tomcat6_hudson stop
	sudo /etc/init.d/tomcat6_hudson start
	mvn  clean install -Ptest -e -Dmaven.test.skip 
	mvn  tomcat:deploy -Ptest -Dmaven.test.skip


pallp: pom.xml
	sudo /etc/init.d/tomcat6 stop
	sudo /etc/init.d/tomcat6 start
	mvn  clean install -Pprod -e -Dmaven.test.skip 
	mvn  tomcat:deploy -Pprod -Dmaven.test.skip

puallp: pom.xml
	mvn  tomcat:undeploy -Pprod
	sudo /etc/init.d/tomcat6 stop
	sudo /etc/init.d/tomcat6 start
	mvn  clean install -Pprod -e -Dmaven.test.skip 
	mvn  tomcat:deploy -Pprod -Dmaven.test.skip
