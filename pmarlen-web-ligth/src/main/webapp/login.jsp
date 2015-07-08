<%@page import="com.pmarlen.web.security.managedbean.SystemInfoMB"%>
<%@page import="com.pmarlen.model.Constants"%>
<%@ page pageEncoding="UTF-8" %>
<%
	long to = request.getSession().getMaxInactiveInterval()*1000 + 3000;	
	System.out.println("login.jsp->["+request.getSession().getId()+"]: timeOut="+(to/1000)+" secs. ="+((to/1000/60.0))+" mins.");
%>

<html>
    <head>
		<title>PMARLEN</title>
		<script type="text/JavaScript">
		<!--
		var redirectTime = "<%=to%>";
		var redirectURL  = "<%=request.getContextPath()%>/";
		function timedRedirect() {
			setTimeout("location.href = redirectURL;",redirectTime);
		}
		
		function autoCompletteEmail(txtUserMail){
			var txt=txtUserMail.value.toLowerCase();
			if(!txt.indexOf("@")<0 && txt.indexOf("@perfumeriamarlen.com.mx")<0){
				txtUserMail.value = txtUserMail.value+ "@perfumeriamarlen.com.mx"; 
				return true;
			}
			return false;
		}
		//   -->
		</script>		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default.css">
    </head>

    <body style="background-color: #DCEBF2;" onload="timedRedirect();">	
		<br/>
		<br/>		
        <form action="j_security_check" method="post" id="loginForm">

			<table class="login_table_panel" border="0" align="center">
				<tr>
					<td colspan="3">
						<img src="<%=request.getContextPath()%>/images/PM_NewLogo_1.png"/>
					</td>
				</tr>
				<tr>
					<td colspan="3" valign="middle" align="center" style="background-color:gray;">
						ACCESO A SISTEMA DE PERFUMER&Iacute;A MARLEN
					</td>
				</tr>
				<tr>
					<td width="20%" rowspan="6" valign="middle" align="center" >
						<img src="<%=request.getContextPath()%>/images/login_person.png" />
					</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="20%" align="right">
						<span style="font-size : 10px;">USUARIO :</span>
					</td>
					<td align="left">
						<input type="text" name="j_username" value="" size="30" onblur="return autoCompletteEmail(this);"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span style="font-size : 10px;">PASSWORD :</span>
					</td>
					<td align="left">
						<input type="password" name="j_password" value="" size="15"/>
					</td>
				</tr>

				<tr>
					<td colspan="3">
						<%
							if (request.getParameter("error") != null) {
						%>
							ERROR EN USUARIO O PASSWORD
						<%
							} else {
						%>
						<%    
							}
						%>									
					</td>
				</tr>												

				<tr>
					<td colspan="3" width="100%" align="center">										
						<input type="submit" value="ENTRAR" />
					</td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
		</td>
	</tr>
</table>

<div class="footer-bar-<%=SystemInfoMB.getEnvironmentStaticlay()%>" title="( <%=SystemInfoMB.getEnvironmentStaticlay()%> ) Git RevID: <%=Constants.getGitSHA1()%>">
	Perfumeria Marlen, Sistema de Adminisración de Almacen Ver. <%=Constants.getServerVersion()%> / Construido: <%=Constants.getBuildTimestamp()%>
</div>

</body>	
</html>
