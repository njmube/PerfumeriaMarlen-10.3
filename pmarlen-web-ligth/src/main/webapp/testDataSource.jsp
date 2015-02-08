<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="java.util.*" %>


<%
	String connectPressed     = request.getParameter("connectPressed");

	System.err.println("-->>connectPressed="+connectPressed);

	String driverClassName = request.getParameter("driverClassName");
	if(driverClassName == null) {
		driverClassName="";
	}
	
	
	String jndiDataSourceName = request.getParameter("jndiDataSourceName");
	if(jndiDataSourceName == null) {
		jndiDataSourceName="";
	}

	String sqlTestQuery       = request.getParameter("sqlTestQuery");
	if(sqlTestQuery == null) {
		sqlTestQuery="";
	}

	String resultMessage = "";

	List<Hashtable<String,String>> rsMetadataContent = null;
	List<List<String>> rsContent = null;

	if(connectPressed != null) {

		Connection conn = null;
		Context ctx = null;
		DataSource ds = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			if(driverClassName .trim().length()>6) {			
				try {
					resultMessage += "OK, try to laod the Class Driver: "+driverClassName+"<br/>";
					Class driverClass = Class.forName(driverClassName);
					resultMessage += "OK, Class Driver found, try to get new Instance.<br/>";
					Driver driver = (Driver) driverClass.newInstance();
					resultMessage += "OK, Driver:"+driver+"<br/>";
				} catch (Throwable e) {
					throw new SQLException(e.getMessage());
				}
            }
			
			
			
			resultMessage += "OK, try to get Initial context<br/>";
			ctx = new InitialContext();
			resultMessage += "OK, try to get Datasource , lookup(\""+jndiDataSourceName+"\");<br/>";
			ds = (DataSource) ctx.lookup(jndiDataSourceName);
			resultMessage += "OK, try to get Connection.<br/>";
			conn = ds.getConnection();

			if(sqlTestQuery.trim().length()>6) {
				resultMessage += "OK, try to Prepare Statement.<br/>";
				ps = conn.prepareStatement(sqlTestQuery);
				resultMessage += "OK, try to Execute Query.<br/>";
				rs = ps.executeQuery();
				resultMessage += "OK, try to get ResultSetMetaData.<br/>";
				ResultSetMetaData rsm = rs.getMetaData();
				int columnCount = rsm.getColumnCount();

				resultMessage += "OK, try to render ResultSetMetaData (columnCount = "+columnCount+").<br/>";

				rsMetadataContent = new ArrayList<Hashtable<String,String>> ();


				for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++ ){

					Hashtable<String,String> columnMetadata = new Hashtable<String,String>();

					columnMetadata.put("ColumnClassName", rsm.getColumnClassName(columnIndex));
					columnMetadata.put("ColumnLabel"    , rsm.getColumnLabel(columnIndex));
					columnMetadata.put("ColumnName"     , rsm.getColumnName(columnIndex));
					columnMetadata.put("ColumnType"     , String.valueOf(rsm.getColumnType(columnIndex)));
					columnMetadata.put("ColumnTypeName" , rsm.getColumnTypeName(columnIndex));

					rsMetadataContent.add(columnMetadata);
				}

				resultMessage += "OK, try to render ResultSet (columnCount = "+columnCount+").<br/>";
				rsContent = new ArrayList<List<String>> ();
				while(rs.next()) {
					List<String> rsContentcolumn = new ArrayList<String> ();
					for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++ ){
						rsContentcolumn.add(rs.getString(columnIndex));
					}
					rsContent.add(rsContentcolumn);
				}

				resultMessage += "OK, try to close ResultSet and PreparedStatement.<br/>";

				rs.close();

				ps.close();

			}
			resultMessage += "OK, try to close Connection.<br/>";
			conn.close();

			resultMessage += "END SUCCEFUL TEST !!!.<br/>";
		} catch( Exception ex) {
			ex.printStackTrace(System.err);
			resultMessage += "<br/>Exception:"+ex.toString()+"<br/>";
		}
	}
%>

<html>
	<head>
		<title> TEST DATASOURCE </title>
	</head>

	<body>	
		<h3>TEST DATASOURCE AND REALM SECURITY V11.1</h3>
		<a href="pages/testSecurity.jsp">test security, test login</a> <br/>
		
		<form name="f1" action ="testDataSource.jsp" method="POST">
			<table width="80%" align="center" border="1">
				<tr>
					<td align="right" width="40%">
						DRIVER CLASS:
					</td>
					<td align="left"  width="60%">
						<input type="text" name="driverClassName" value="<%=driverClassName%>" size="35"/> <br/>
						classpath:<%=System.getProperty("java.class.path").replace(",","<br/>")%> 
					</td>
				</tr>
			
				<tr>
					<td align="right" width="40%">
						JNDI DATASOURCE ( java:comp/env/ ):
					</td>
					<td align="left"  width="60%">
						<input type="text" name="jndiDataSourceName" value="<%=jndiDataSourceName%>" size="35"/>
					</td>
				</tr>

				<tr>
					<td align="right">
						SQL TEST QUERY:
					</td>
					<td align="left">						
						<textarea rows="4" cols="50"  name="sqlTestQuery"><%=sqlTestQuery%></textarea>
					</td>
				</tr>
				
				<tr>
					<td align="center" colspan="2">
						<input type="submit" value="CONNECT,TEST >" name="connectPressed"/>
					</td>
					
				</tr>				
			</table>
		</form>
		
		<%=resultMessage%>
<%
	if (rsMetadataContent != null && rsContent!= null) {	
%>		
		<table width="90%" align="center" border="1">
			<tr>
<%
		for (Hashtable<String,String> columnMetadata: rsMetadataContent) {			
%>		
			
				<td>
<%
			for(String key : columnMetadata.keySet()) {
				String value = columnMetadata.get(key);
%>			
				<%=key%> = <%=value%> <br/> 
<%
			}
%>				
				</td>			
<%
		}
%>
			</tr>
<%		
		for(List<String> columnContest: rsContent ) {

%>

			<tr>
<%
			for(String columnValue: columnContest){
%>			
				<td><%=columnValue%></td>
<%
			}
%>				
			</tr>
<%
		}
%>			
		</table>
<%
	}
%>					
		
	</body>	
</html>