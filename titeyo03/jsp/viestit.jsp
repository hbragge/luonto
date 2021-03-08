<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.sql.*, java.util.Vector" %> 
<%@page import="fi.tty.pori.vo.Viesti, fi.tty.pori.dao.*" %> 

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

    <h1>Viestit</h1>
    
    <% 	
    ViestiDAO dao = new ViestiDAO(getConnection());
    Vector viestiVector = dao.haeViestit();

    
    Viesti viesti = new Viesti();
	for(int i = 0; i < viestiVector.size(); i++) {
		viesti = (Viesti)viestiVector.elementAt(i); 

%>
<table>    
<tr>

<td width="10" align="center" valign="middle" bgcolor="#DDDDDD">
 <a href="delviesti.jsp?poista=<%=viesti.getViestinro()%>">
 <img width="12" height="13" src="images/button_remove.png" alt="Delete" title="Poista" border="0" /></a>
</td>
</td>
    <td valign="top"  bgcolor="#DDDDDD"><%=viesti.getVastaanottaja()%></td>
    <td valign="top"  bgcolor="#DDDDDD"><%=viesti.getViesti()%></td>

</tr>
<% }%>   
</table>


    
    </body>
</html>
