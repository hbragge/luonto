/* Verkko-ohjelmiston toteutus
 * Etusivu
 *
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

public class etusivu extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws
		ServletException, IOException {

		String dbDriver = "org.gjt.mm.mysql.Driver";
	    String dbUrl = "jdbc:mysql://localhost/saatiedot?user=root&password=";
	   	Connection con = null;
	    PreparedStatement s = null;
	    PreparedStatement s2 = null;
	    PreparedStatement s3 = null;
	    ResultSet rs = null;
	    ResultSet rs2 = null;
	    ResultSet rs3 = null;
		StringBuffer ulos = new StringBuffer();  
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String hakusana = "florida";
		String str = null;
		int nimiend = 0;
		double celsius,mps;
		String title;
		
		ulos.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n");
		ulos.append("<html>\n");
		ulos.append("<head><script type=\"text/javascript\"> function flip(rid) { current=(document.getElementById(rid).style.display == 'none') ? 'block' : 'none'; document.getElementById(rid).style.display = current; } </script></head>\n");
		ulos.append("<body>\n");
		ulos.append("<h3>Florida statistics</h3>\n");
		ulos.append("<table border=\"1\">\n");
		ulos.append("<tr>\n");
		ulos.append("<th>Hottest place</th>\n");
		ulos.append("<th>Windiest place</th>\n");
		ulos.append("<th>Wettest place</th>\n");
		ulos.append("</tr>\n");
		ulos.append("<tr>\n");
		
		
		try {
				    	
			    Class.forName(dbDriver);
			    con = DriverManager.getConnection(dbUrl);
			    
			    
				// haetaan 5 uutista joissa halutut sanat esiintyivät useimmin
				//  WHERE description LIKE \"%" + hakusana + "%\"
				
				s = con.prepareStatement("SELECT chantitle,lampotila FROM rss ORDER BY lampotila DESC LIMIT 1");
				rs = s.executeQuery();
				
				
				// tulostetaan
				
				
				while(rs.next()){
					
					title = rs.getString("chantitle");
					
					for(int i=0;i<title.length();i++){
	                	if(title.charAt(i) == '-'){
	                		nimiend = i-5;
	                		}
	        		}
	        		celsius = java.lang.Math.round((rs.getInt("lampotila")-32)/1.8);
					ulos.append("<td>"+title.substring(17,nimiend)+" ("+celsius+" &#176;C)</td>\n");
					
								
				}
				
				s = con.prepareStatement("SELECT chantitle,tuuli FROM rss ORDER BY tuuli DESC LIMIT 1");
				rs = s.executeQuery();
				
				while(rs.next()){
					
					title = rs.getString("chantitle");
					
					for(int i=0;i<title.length();i++){
	                	if(title.charAt(i) == '-'){
	                		nimiend = i-5;
	                		}
	        		}
	        		mps = java.lang.Math.round(2.2369*rs.getInt("tuuli"));
					ulos.append("<td>"+title.substring(17,nimiend)+" ("+mps+" m/s)</td>\n");
					
								
				}
				
				s = con.prepareStatement("SELECT chantitle,kosteus FROM rss ORDER BY kosteus DESC LIMIT 1");
				rs = s.executeQuery();
				
				while(rs.next()) {
					
					title = rs.getString("chantitle");
					
					for(int i=0;i<title.length();i++){
	                	if(title.charAt(i) == '-'){
	                		nimiend = i-5;
	                		}
	        		}
					ulos.append("<td>"+title.substring(17,nimiend)+" ("+rs.getInt("kosteus")+"%)</td>\n");
					
								
				}
			
			} catch (Exception e) {
				
		             ulos.append(e);
		             out.println(ulos);
		            	
		           }
		
		
		
		
	
		ulos.append("</tr>\n");
		ulos.append("</table>\n");
		ulos.append("<h3>News concerning recent hazards</h3>\n");
		
		
		try {
				    	
			    Class.forName(dbDriver);
			    con = DriverManager.getConnection(dbUrl);
			    
			    
				// haetaan 5 uutista joissa halutut sanat esiintyivät useimmin
				//  WHERE description LIKE \"%" + hakusana + "%\"
				
				s = con.prepareStatement("SELECT title,link,description FROM uutiset ORDER BY osumat DESC LIMIT 5");
				rs = s.executeQuery();
				
				// tulostetaan
				int i = 1;
				
				while(rs.next()){
					ulos.append("<a href='"+rs.getString("link")+"'>"+rs.getString("title")+"</a>\n");
					
					ulos.append("<div>&nbsp;&nbsp;<a href=\"#\" onClick=\"flip('kuvaus"+i+"');return false;\">more</a></div><div id=\"kuvaus"+i+"\"><script type=\"text/javascript\">flip('kuvaus"+i+"');</script>\n");
					
					ulos.append("<blockquote>\n");
					ulos.append(rs.getString("description"));

					ulos.append("</blockquote>\n");
					ulos.append("</div><br />\n");
					
					i++;
								
				}
			
			} catch (Exception e) {
				
		             ulos.append(e);
		             out.println(ulos);
		            	
		           }
		           
	    ulos.append("</body>\n</html>");	
		out.println(ulos);
		out.flush();
		out.close();
	           
		}
}