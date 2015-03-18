all:
	wget http://perfumeriamarlen.dyndns.org:7080/exportPM91-1.0-SNAPSHOT/eb/PM.tgz
	tar xvfz PM.tgz
	mysql --default-character-set=utf8 -u root -proot PMARLEN_DB_PROD < PM.sql
