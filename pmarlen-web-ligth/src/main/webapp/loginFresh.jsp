<%@page import="com.pmarlen.web.security.managedbean.SystemInfoMB"%>
<%@page import="com.pmarlen.model.Constants"%>
<%@ page pageEncoding="UTF-8" %>
<%
	long to = request.getSession().getMaxInactiveInterval() * 1000 + 3000;
	System.out.println("login.jsp->[" + request.getSession().getId() + "]: timeOut=" + (to / 1000) + " secs. =" + ((to / 1000 / 60.0)) + " mins.");
%>
<head>
	<title>PMARLEN:ENTRADA</title>
	<script type="text/JavaScript">
	<!--
	var redirectTime = "<%=to%>";
	var redirectURL  = "<%=request.getContextPath()%>/";
	function timedRedirect() {
		setTimeout("location.href = redirectURL;",redirectTime);
	}
	
	function autoCompletteEmail(txtUserMail){
		var txt=txtUserMail.value.toLowerCase();
		//alert("->txt="+txt);
		if(txt.indexOf("@")<0 && txt.indexOf("@perfumeriamarlen.com.mx")<0){
			txtUserMail.value = txtUserMail.value+ "@perfumeriamarlen.com.mx"; 
			return true;
		}
		return false;
	}	
	//   -->
	</script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/login_css/login_bg.css"     type='text/css'/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/login_css/login.css"     type='text/css'/>
</head>
<body  onload="timedRedirect();">
	<br/>
	<br/>
	<br/>
	<div class="ribbon"></div>
	<div class="login">
		<br/>
		<p>
			<img src="<%=request.getContextPath()%>/images/PM_NewLogo_2.png"/>
		</p>
		<h3>Entrada al Sistema.</h3>
		<p>Introdusca su informaci&oacute;n</p>
		<form action="j_security_check" method="post" id="loginForm">
			<div class="input">
				<div class="blockinput">
					<i class="icon-envelope-alt"></i><input type="mail" name="j_username" placeholder="Correo Electronico" onblur="autoCompletteEmail(this);">
				</div>
				<div class="blockinput">
					<i class="icon-unlock"></i><input type="password"  name="j_password" placeholder="Contraseña">
				</div>		
			</div>
			<button>ENTRAR</button>
						<%
							if (request.getParameter("error") != null) {
						%>
			<br/>
			<p class="error_login">ERROR EN CORREO O CONTRASE&Ntilde;A</p>
						<%
							} else {
						%>
						<%    
							}
						%>									
		</form>
	</div>
	<div class="footer-bar-<%=SystemInfoMB.getEnvironmentStaticlay()%>" title="( <%=SystemInfoMB.getEnvironmentStaticlay()%> ) Git RevID: <%=Constants.getGitSHA1()%>">
		Perfumeria Marlen, Sistema de Adminisraci&oacute;n de Almacen Ver. <%=Constants.getServerVersion()%> / Construido: <%=Constants.getBuildTimestamp()%>
	</div>

</body>