/* Verkko-ohjelmiston toteutus
 * RSS-luku
 * Hakee s‰‰tietoja lentokentilt‰
 *
 * Henri Bragge 189696
 * titeyo03
 */
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*; 
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class rssprintlocal extends HttpServlet {


public void doGet(HttpServletRequest req, HttpServletResponse res) throws
ServletException, IOException {

Connection con = null;
Statement s = null;
ResultSet rs = null;
String dbDriver = "com.mysql.jdbc.Driver";
String dbUrl = 
"jdbc:mysql://localhost/saatiedot?user=root&password=";
res.setContentType("text/html");
PrintWriter out = res.getWriter(); 
out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
out.println("<html>");
out.println("<head><title>S‰‰tiedot New Yorkin lentokentill‰</title></head>");
out.println("<body>");
out.println("<b>Valitse haluamasi lentokent‰t:</b><br /><br />");

try {
        Class.forName(dbDriver);
        con = DriverManager.getConnection(dbUrl);
        s = con.createStatement();
        rs = s.executeQuery("SELECT id,chantitle FROM rss");

             
		out.println("<form method=\"post\" action=\"http://localhost:8080/harj1/rssprintlocal\">");
		
		out.println("<select name=\"lentokentta\" multiple size=\"10\">");
        while (rs.next()) {
        		
        		id = rs.getString("id");
                title = rs.getString("chantitle");
                
                for(int i=0;i<title.length();i++){
                	if(title.charAt(i) == '-'){
                		nimiend = i-5;
                		}
        		}
        		nimi = title.substring(17,nimiend);
                out.println("<option value="+id+">"+nimi+"</option>");
        }
        
        out.println("</select><br /><br />");
        out.println("<input type=\"submit\" value=\"Hae s‰‰tiedot\">");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
        
        
	} catch (Exception e) {
			out.println(e);
						
			} finally 	{
        				try {
                			if(s != null) {s.close();}
                			if(con != null) {con.close();}
        					} catch (SQLException sqe) {}
        				}
	}
	
	
	
public void doPost(HttpServletRequest req, HttpServletResponse res) throws
ServletException, IOException {

	String dbDriver = "org.gjt.mm.mysql.Driver";
    String dbUrl = "jdbc:mysql://localhost/saatiedot?user=root&password=";
   	Connection con = null;
    PreparedStatement s = null;
    ResultSet rs = null;
	StringBuffer ulos = new StringBuffer();  
	res.setContentType("text/xml");
	PrintWriter out = res.getWriter();
	String[] lentokentat = req.getParameterValues("lentokentta");
	ulos.append("<rss version=\"2.0\">\n<channel>\n<title>S‰‰tiedot</title>\n<category>");
	ulos.append("</category>\n<description>S‰‰tiedot New Yorkin lentokentill‰</description>\n");
	
	if(lentokentat != null){
	
	for(int i=0;i<lentokentat.length;i++){
	    try {
		    Class.forName(dbDriver);
		    con = DriverManager.getConnection(dbUrl);
			s = con.prepareStatement("SELECT chantitle,title,description,lastbuilddate,link FROM rss WHERE id='"+lentokentat[i]+"'");		
			rs = s.executeQuery();
			
			while(rs.next()){
				
				ulos.append("<item>\n<title>");
				ulos.append( rs.getString("title") );
				ulos.append("</title>\n<description>");
				
				ulos.append( rs.getString("description") );
							
				ulos.append("</description>\n<link>");
				ulos.append( rs.getString("link") );
				ulos.append("</link>\n<lastBuildDate>");
							
				ulos.append( rs.getString("lastbuilddate") );
				ulos.append("</lastBuildDate>\n</item>\n");
							
			}
			
			
		
		
		} catch (Exception e) {
	             ulos.append(e);
	             out.println(ulos);
	            	
	           }
    }
    }
    
    ulos.append("</channel>\n</rss>");	
	out.println(ulos);
	out.flush();
	out.close();
           
	}
}