PERFUMERIA AMRLEN DEVELOPMENT TASKS

1) IMOPORT OR CREATE IMAGES FOR PRODUCTO:

    1.1 FROM DIRECTORY

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.CrearImagenesDePruebaEnMultimedio -Dexec.args="-u jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8 PMARLEN_TEST PMARLEN_TEST_PASSWORD ../../pmarlen_imgs PRODUCTO_@PRODUCTO_ID@.jpg"

    1.2 : CREATING DUMMY IMAGES:

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.CrearImagenesDePruebaEnMultimedio -Dexec.args="-u jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8 PMARLEN_TEST PMARLEN_TEST_PASSWORD"

2) IMPORTADOR DE PRODUCTOS

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.TurboImportFromXLSX -Dexec.args="../pmarlen-jpa-entity/db_resources/import/PMarlen_DB_20140107_V1.xlsx   com.mysql.jdbc.Driver jdbc:mysql://localhost/PMARLEN_DB_TEST?characterEncoding=UTF-8 PMARLEN_TEST PMARLEN_TEST_PASSWORD"

3) IMPORTAR SEPOMEX

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.ParseSepomex -Dexec.args="CPdescarga.xml sepomex.sql"

    mvn clean compile exec:java -Dexec.mainClass=com.pmarlen.dev.task.ParseSepomex -Dexec.args=/home/alfredo/Descargas/CPdescarga.xml sepomex.txt false

4) BUILD EXECUTABLE JAR

   mvn clean package -P Build4Run

5) UPDATE PNG FROM SVG FRESH EXPORTED DIAGRAMS

6) MIGRATE TO VERSIN 10.3
	
	mvn exec:java -Dexec.mainClass=com.pmarlen.migration.ImportData -Dexec.args="root root jdbc:mysql://localhost/PMARLEN_DB_PROD?characterEncoding=UTF-8 root root jdbc:mysql://localhost/PMDB103_DEVE?characterEncoding=UTF-8"
