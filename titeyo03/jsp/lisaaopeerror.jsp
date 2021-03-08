<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="fi.tty.pori.vo.Opettaja" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    Opettaja opettaja = (Opettaja)request.getAttribute("opettaja");

%>
   
   
<html>
  <head>
    <title>???</title>
  </head>
<body>
  

<center>
<h2>TARKISTA ANTAMASI TIEDOT</h2>
<table width="70%" border="0" cellspacing="2" cellpadding="2">
<tbody><tr><td colspan="2">&nbsp;</td></tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Sukunimi</b></font></div></td>
	<td width="60%"><%= opettaja.getSukunimi()%></td>
</tr>
<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Etunimi</b></font></div></td>
	<td width="60%"><%= opettaja.getEtunimi()%></td>
</tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Sähköposti</b></font></div></td>
	<td width="60%"><%= opettaja.getEmail()%></td>
</tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Työhuone</b></font></div></td>
	<td width="60%"><%= opettaja.getHuone()%></td>
</tr>

</table>
</form>

  
  </body>
</html>

