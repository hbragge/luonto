


<%@page import="java.sql.*, java.util.Vector" %> 
<%@page import="fi.tty.pori.vo.Opettaja, fi.tty.pori.dao.*" %> 

<%!
    String connectionURL = "";
    String driver ="";
    public void jspInit(){
	ServletContext sc = getServletContext();
        driver = sc.getInitParameter("driver");
        String url =  sc.getInitParameter("ostURL");
        String username = sc.getInitParameter("user");
        String password = sc.getInitParameter("password");
        connectionURL =  "jdbc:mysql://" + url + "?user=" + username + "&password="+password;
    }

    private Connection getConnection() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(connectionURL);
            return connection;
        } catch(ClassNotFoundException cnfe) {
            //
        } catch(java.sql.SQLException se) {
			//
        }
    return null;
}
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>OPETTAJAN TIEDOT</h1>
    
    <%
    String opeNroStr = request.getParameter("id");
    int openro = -1;
    
    try {
        openro = Integer.parseInt(opeNroStr);    
    }catch (NumberFormatException ex) {
        openro=-1;
    }
    
    
    OpeDAO dao = new OpeDAO(getConnection());
    
       
    Opettaja opettaja = dao.haeOpettaja(openro);
    
%>
<form name="formi" action="updateope" method="post" > 

<center>
<table width="70%" border="0" cellspacing="2" cellpadding="2">
<tbody><tr><td colspan="2">&nbsp;</td></tr>
<input type="hidden" name="openro" size="40" value="<%= opettaja.getOpenro()%>" >
<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Sukunimi</b></font></div></td>
	<td width="60%"><input type="text" name="sukunimi" size="40" value="<%= opettaja.getSukunimi()%>" ></td>
</tr>
<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Etunimi</b></font></div></td>
	<td width="60%"><input type="text" name="etunimi" size="25" value="<%= opettaja.getEtunimi()%>"></td>
</tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Sähköposti</b></font></div></td>
	<td width="60%"><input type="text" name="email" size="40" value="<%= opettaja.getEmail()%>"></td>
</tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Työhuone</b></font></div></td>
	<td width="60%"><input type="text" name="room" size="20" value="<%= opettaja.getHuone()%>"></td>
</tr>

<tr>
	<td colspan="2"><div align="center">
        <INPUT type=submit value="Talleta" >
        <INPUT type=reset  value="Tyhjennä" >
	</td>
</tr>
</table>
</form>

  
  </body>
</html>
