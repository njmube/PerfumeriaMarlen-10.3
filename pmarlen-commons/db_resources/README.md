PRODUCTION DATABAASE NAME

	PMDB102_PROD
	PMDB102_PROD_USR
	PMDB102_PROD_PWD
	
DEVELOPMENT DATABASE NAME
	
	PMDB102_DEVE
	PMDB102_DEVE_USR
	PMDB102_DEVE_PWD

java -cp model/jpa-builder-0.9.5.jar com.tracktopell.dao.builder.jpa.UpdateResourceBoundleForBeans model/exported/project.xml {all}

mysql --default-character-set=utf8 -u PMARLEN_DB_USER -pPMARLEN_DB_PASSWORD PMARLEN_BACKEND_DB


mysql --default-character-set=utf8 -u PMDB103_DEVE_USR -pPMDB103_DEVE_PWD PMDB103_DEVE
