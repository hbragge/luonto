


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

    <h1>Opettaja poistettu</h1>
    
    <%
    String opeNroStr = request.getParameter("poista");
    
    int openro = Integer.parseInt(opeNroStr);    
    
    
    
    OpeDAO dao = new OpeDAO(getConnection());
    
       
    dao.poista(openro);
    
%>

  
  </body>
</html>
