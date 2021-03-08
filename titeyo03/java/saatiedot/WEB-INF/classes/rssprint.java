/* Verkko-ohjelmiston toteutus
 * RSS-tulostus
 * Tulostaa s‰‰tietoja lentokentilt‰
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

public class rssprint extends HttpServlet {

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
		out.println("<body>");
		out.println("<h3>Select airports:</h3>");

		try {
		
	        Class.forName(dbDriver);
	        con = DriverManager.getConnection(dbUrl);
	        s = con.createStatement();
	        rs = s.executeQuery("SELECT id,chantitle FROM rss");
	        String id,title,nimi;
			int nimiend = 0;
	             
			out.println("<form method=\"post\" action=\"http://localhost:8080/saatiedot/rssprint\">");
			
			// tulostetaan tietokannan lentokent‰t listaan
			
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
	        
	        out.println("</select><br />");
	        out.println("Method: ");
	        out.println("<input type=\"radio\" name=\"toiminto\" value=0 checked>Open</input>");
	        out.println("<input type=\"radio\" name=\"toiminto\" value=1>Save</input><br /><br />");
	        
	        out.println("<input type=\"submit\" value=\"Get\">");
	      
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
		String strpar = req.getParameter("toiminto");
		int toiminto = Integer.parseInt(strpar);
		
		//ulos.append("<?xml version=\"1.0\"?>");
		//ulos.append("<?xml-stylesheet type=\"text/xsl\" href=\"rsstyyli.xsl\"?>");
		ulos.append("<rss version=\"2.0\">\n<channel>\n<title>Weather information</title>\n");
		
		if(lentokentat != null) {
		
		// tulostetaan lentokent‰n s‰‰stiedot rss-muodossa
		
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
					
					ulos.append( rs.getString("description") );;
								
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
	    if(toiminto == 1){
					
					res.setContentType("text/html");
					
					if(saveFile("./webapps/saatiedot/saatiedot.rss",ulos)==false) {
							
								out.println("<html><body>Save failed.<br /><br /><a href=\"javascript:history.back()\">Back</a></body></html>");
								
							} else {
							
						out.println("<html><body>Click 2nd button and save or open in your favorite RSS reader<br /><a href=\"http://localhost:8080/saatiedot/saatiedot.rss\">saatiedot.rss</a><br /><br /><a href=\"javascript:history.back()\">Back</a></body></html>");
						}
				} else {
		out.println(ulos);
		}
		out.flush();
		out.close();
	           
		}
		
		
	static boolean saveFile( String path, StringBuffer buffer){
			File file = new File(path);
			StringBuffer err = new StringBuffer();
			
			try {
				file.createNewFile();
				} catch(IOException ex) {
				err.append("Failed to open file for writing: " + ex);
				return false;
			}
			
			OutputStream os = null;
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
			} catch(Exception ex) {
				err.append("Failed to open file for writing: " + ex);
				return false;
			}
			try {
				os.write(buffer.toString().getBytes());
			} catch(Exception ex) {
				err.append("Failed to write " + buffer.length() + " byte buffer: " + ex);
				return false;
			} finally {
				try {
					if(os != null) os.close();
				} catch(Exception ex) {
					err.append("Failed to close output stream: " + ex);
					return false;
				}
			}
			return true;
		}
}