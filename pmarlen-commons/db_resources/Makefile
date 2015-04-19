# Makefile instalcaion de ambiente de Base de Datos del Sistema Perfumeria Marlen

JPABUILDER_PROJECT_MVN=~/.m2/repository/com/xpressosystems/util/jpa-builder
JPABUILDER_VERSION=0.10.8
JPABUILDER_JAR_PATH=$(JPABUILDER_PROJECT_MVN)/$(JPABUILDER_VERSION)/jpa-builder-$(JPABUILDER_VERSION).jar
PMDB_VER_NAME=103


# make all MYSQL_ROOT_PASSWORD=xxxxxx
# make init_DB_DEVE MYSQL_ROOT_PASSWORD=xxxxxx
# make init_DB_PROD MYSQL_ROOT_PASSWORD=xxxxxx


all :model_changed init_DB_DEVE init_DB_PROD 


init_DB_DEVE : create_users_DEVE 
	echo import from previous DB to be defined.

create_users_DEVE : create_DB_DEVE sql/CREATE_USER_DEVE.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) --verbose < sql/CREATE_USER_DEVE.MySQL.SQL

create_DB_DEVE : sql/CREATE_PMDB$(PMDB_VER_NAME)_DEVE.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) --verbose < sql/CREATE_PMDB$(PMDB_VER_NAME)_DEVE.MySQL.SQL


init_DB_PROD : create_users_PROD 
	echo import from previous DB to be defined.

create_users_PROD : create_DB_PROD sql/CREATE_USER_PROD.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) --verbose < sql/CREATE_USER_PROD.MySQL.SQL

create_DB_PROD : sql/CREATE_PMDB$(PMDB_VER_NAME)_PROD.MySQL.SQL
	mysql --default-character-set=utf8 -u root -p$(MYSQL_ROOT_PASSWORD) --verbose < sql/CREATE_PMDB$(PMDB_VER_NAME)_PROD.MySQL.SQL

model_changed : $(JPABUILDER_JAR_PATH) run_regenerate_scripts_DB run_regenerate_BackEnd_DTOs run_generate_create_users_4_deve run_generate_create_users_4_prod

run_generate_create_users_4_deve:
	echo "use mysql;" >  sql/CREATE_USER_DEVE.MySQL.SQL
	echo "DELETE FROM user WHERE user='PMDB$(PMDB_VER_NAME)_DEVE_USR';" >> sql/CREATE_USER_DEVE.MySQL.SQL
	echo "GRANT SELECT,INSERT,UPDATE,DELETE ON PMDB$(PMDB_VER_NAME)_DEVE.* TO 'PMDB$(PMDB_VER_NAME)_DEVE_USR'@'localhost' IDENTIFIED BY 'PMDB$(PMDB_VER_NAME)_DEVE_PWD';" >> sql/CREATE_USER_DEVE.MySQL.SQL
	echo "GRANT SELECT,INSERT,UPDATE,DELETE ON PMDB$(PMDB_VER_NAME)_DEVE.* TO 'PMDB$(PMDB_VER_NAME)_DEVE_USR'@'%'         IDENTIFIED BY 'PMDB$(PMDB_VER_NAME)_DEVE_PWD';" >> sql/CREATE_USER_DEVE.MySQL.SQL

run_generate_create_users_4_prod:
	echo "use mysql;" >  sql/CREATE_USER_PROD.MySQL.SQL
	echo "DELETE FROM user WHERE user='PMDB$(PMDB_VER_NAME)_PROD_USR';" >> sql/CREATE_USER_PROD.MySQL.SQL
	echo "GRANT SELECT,INSERT,UPDATE,DELETE ON PMDB$(PMDB_VER_NAME)_PROD.* TO 'PMDB$(PMDB_VER_NAME)_PROD_USR'@'localhost' IDENTIFIED BY 'PMDB$(PMDB_VER_NAME)_PROD_PWD';" >> sql/CREATE_USER_PROD.MySQL.SQL
	echo "GRANT SELECT,INSERT,UPDATE,DELETE ON PMDB$(PMDB_VER_NAME)_PROD.* TO 'PMDB$(PMDB_VER_NAME)_PROD_USR'@'%'         IDENTIFIED BY 'PMDB$(PMDB_VER_NAME)_PROD_PWD';" >> sql/CREATE_USER_PROD.MySQL.SQL

run_regenerate_scripts_DB : $(JPABUILDER_JAR_PATH) model/export_BackEnd/project.xml 
	java -cp $(JPABUILDER_JAR_PATH) com.tracktopell.dao.builder.dbschema.VPModel2SQL model/export_BackEnd/project.xml mysql PMDB$(PMDB_VER_NAME)_DEVE sql/CREATE_PMDB$(PMDB_VER_NAME)_DEVE.MySQL.SQL "{all}" > results_SQL_DEVE.txt 2> debug_SQL_DEVE.txt
	java -cp $(JPABUILDER_JAR_PATH) com.tracktopell.dao.builder.dbschema.VPModel2SQL model/export_BackEnd/project.xml mysql PMDB$(PMDB_VER_NAME)_PROD sql/CREATE_PMDB$(PMDB_VER_NAME)_PROD.MySQL.SQL "{all}" > results_SQL_PROD.txt 2> debug_SQL_PROD.txt

run_regenerate_BackEnd_DTOs : $(JPABUILDER_JAR_PATH) model/export_BackEnd/project.xml 
	java -cp $(JPABUILDER_JAR_PATH) com.tracktopell.dao.builder.jdbc.VPModel2DTO model/export_BackEnd/project.xml  PMARLEN com.pmarlen.backend.model                           ../../pmarlen-commons/src/main/java "{all}" > results_DTO.txt 2> debug_DTO.txt
	java -cp $(JPABUILDER_JAR_PATH) com.tracktopell.dao.builder.jdbc.VPModel2DAO model/export_BackEnd/project.xml  PMARLEN com.pmarlen.backend.model com.pmarlen.backend.dao   ../../pmarlen-commons/src/main/java "{all}" > results_DAO.txt 2> debug_DAO.txt

# sudo aptitude install librsvg2-bin
create_png: /usr/bin/rsvg model/exported/ER1.svg
	rsvg -f png model/exported/ER1.svg model/exported/ER1.png

clean_results :
	rm -f debug*
	rm -f results*

