<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="fi.tty.pori.vo.Viesti" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%
    Viesti viesti = (Viesti)request.getAttribute("viesti");

%>
   
   
<html>
  <head>
    <title>???</title>
  </head>
<body>
  

<center>
<h2>Tarkista tiedot</h2>
<table width="70%" border="0" cellspacing="2" cellpadding="2">
<tbody><tr><td colspan="2">&nbsp;</td></tr>

<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Vastaanottaja</b></font></div></td>
	<td width="60%"><%= viesti.getVastaanottaja()%></td>
</tr>
<tr>
	<td width="40%"><div align="right">
	<font color="#008080"face="Arial, Helvetica, sans-serif"><b>Viesti</b></font></div></td>
	<td width="60%"><%= viesti.getViesti()%></td>
</tr>


</table>
</form>

  
  </body>
</html>


