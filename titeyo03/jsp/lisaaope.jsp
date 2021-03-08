<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*" %> 
<%@page import="fi.tty.pori.vo.Opettaja" %> 

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
    
    
    private boolean talletaTiedot( Opettaja opettaja) {
	try{
		Connection connection = getConnection();
		String sqlStr = "INSERT INTO OPETTAJA (SUKUNIMI,ETUNIMI, EMAIL,HUONE) "
			+ "VALUES(?,?,?,?)";

			PreparedStatement ps =  connection.prepareStatement(sqlStr);
			ps.setString(1,opettaja.getSukunimi());
			ps.setString(2,opettaja.getEtunimi());
			ps.setString(3,opettaja.getEmail());
			ps.setString(4,opettaja.getHuone());
			ps.executeUpdate();
			ps.close();
			connection.close();
		}catch(SQLException se){
			return false;
		}
		return true;
    }
    
    private boolean update( Opettaja opettaja) {
	try{
		Connection connection = getConnection();
		String sqlStr = "UPDATE OPETTAJA SET SUKUNIMI=?,ETUNIMI=?, EMAIL=?,HUONE=? "
			+ "WHERE OPENRO=?";

			PreparedStatement ps =  connection.prepareStatement(sqlStr);
			ps.setString(1,opettaja.getSukunimi());
			ps.setString(2,opettaja.getEtunimi());
			ps.setString(3,opettaja.getEmail());
			ps.setString(4,opettaja.getHuone());
                        ps.setInt(5,opettaja.getOpenro());
			ps.executeUpdate();
			ps.close();
			connection.close();
		}catch(SQLException se){
			return false;
		}
		return true;
    }
    
    private Opettaja haeOpettaja( int openro) {
	Opettaja opettaja = null;
        try{
		Connection connection = getConnection();
		String sqlStr = "SELECT OPENRO, SUKUNIMI,ETUNIMI, EMAIL,HUONE "
			+ "FROM OPETTAJA WHERE OPENRO=?";

			PreparedStatement ps =  connection.prepareStatement(sqlStr);
                        ps.setInt(1,openro);
			ResultSet rs = ps.executeQuery();
                        
                        while(rs.next()) {
                            opettaja = new Opettaja();
                            opettaja.setOpenro(rs.getInt("OPENRO"));
                            opettaja.setSukunimi(rs.getString("SUKUNIMI"));
                        }
                        
			ps.close();
			connection.close();
		}catch(SQLException se){
			return null;
		}
		return opettaja;
    }
    
    
    
    
    
%>

   
   
<%
    Opettaja opettaja = new Opettaja();
    opettaja.setSukunimi(request.getParameter("sukunimi"));
    opettaja.setEtunimi(request.getParameter("etunimi"));
    opettaja.setEmail(request.getParameter("email"));
    opettaja.setHuone(request.getParameter("room"));
    
    // request.getParameter("openro")
    // muunnos STRING -> int
    // opettaja.setOpenro(nro);
    boolean tiedotOK = true;
    if(opettaja.getSukunimi() == null || opettaja.getSukunimi().equalsIgnoreCase(""))  {
        tiedotOK = false;
        opettaja.setSukunimi("");
    }
    if(opettaja.getEtunimi() == null || opettaja.getEtunimi().equalsIgnoreCase("")){
        tiedotOK = false;
        opettaja.setEtunimi("");
    }
    if(opettaja.getEmail() == null)
	opettaja.setEmail("");
    if(opettaja.getHuone() == null)
	opettaja.setHuone("");
    
    if (tiedotOK) {
        talletaTiedot(opettaja);
    }
%>
   
   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
   
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
     <body bgcolor="#f2f2f2">
     <center>
<%  if (tiedotOK) {%>     
    Tiedot talletettu
<% }else {  %>  
    Tarkista antamasi tiedot!!!!
    </center>
<% } %>            
    </body>
</html>
