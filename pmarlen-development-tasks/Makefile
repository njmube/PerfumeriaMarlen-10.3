all: resetDB
	wget http://perfumeriamarlen.dyndns.org:7080/exportPM91-1.0-SNAPSHOT/eb/PM.tgz
	tar xvfz PM.tgz
	mysql --default-character-set=utf8 -u root -proot PMARLEN_DB_PROD < PM.sql
	mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.migration.ImportData -Dexec.args="root root jdbc:mysql://localhost/PMARLEN_DB_PROD?characterEncoding=UTF-8 root root jdbc:mysql://localhost/PMDB103_DEVE?characterEncoding=UTF-8"

resetDB:
	make -C ../pmarlen-commons/db_resources init_DB_DEVE init_DB_PROD MYSQL_ROOT_PASSWORD=root
