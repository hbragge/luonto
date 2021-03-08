<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>



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

    <h1>OPETTAJAT</h1>
    
    <% 	
    OpeDAO dao = new OpeDAO(getConnection());
    Vector opettajaVector = dao.haeOpettajat();

    
    Opettaja opettaja = new Opettaja();
	for(int i = 0; i < opettajaVector.size(); i++) {
		opettaja = (Opettaja)opettajaVector.elementAt(i); 

%>
<table>    
<tr>
<td width="10" align="center" valign="middle" bgcolor="#DDDDDD">
 <a href="editope.jsp?id=<%=opettaja.getOpenro()%>">
 <img width="12" height="13" src="images/button_edit.png" alt="Edit" title="Muokkaa" border="0" /></a>
</td>
<td width="10" align="center" valign="middle" bgcolor="#DDDDDD">
 <a href="delope.jsp?poista=<%=opettaja.getOpenro()%>">
 <img width="12" height="13" src="images/button_remove.png" alt="Delete" title="Poista" border="0" /></a>
</td>
</td>
    <td valign="top"  bgcolor="#DDDDDD"><%=opettaja.getSukunimi()%></td>
    <td valign="top"  bgcolor="#DDDDDD"><%=opettaja.getEtunimi()%></td>
    <td valign="top"  bgcolor="#DDDDDD"><%=opettaja.getEmail()%></td>
    <td valign="top"  bgcolor="#DDDDDD"><%=opettaja.getHuone()%></td>

</tr>
<% }%>   
</table>


    
    </body>
</html>
