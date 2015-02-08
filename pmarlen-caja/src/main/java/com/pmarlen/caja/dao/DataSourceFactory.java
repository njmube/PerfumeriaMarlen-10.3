// DataSource factory for the MiniConnectionPoolManager test programs.
package com.pmarlen.caja.dao;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import javax.sql.ConnectionPoolDataSource;

public class DataSourceFactory {

	public static ConnectionPoolDataSource createDataSource() throws Exception {

		MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();

		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		dataSource.setUser("system");
		dataSource.setPassword("x");

		return dataSource;
	}

} // end class DataSourceFactory
