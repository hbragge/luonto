/* Verkko-ohjelmiston toteutus
 * SOAP-haku
 * Hakee tietoja lentokentistä Googlella
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
import java.net.URL;
import java.net.HttpURLConnection;

public class soap extends HttpServlet {	
	
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
		out.println("<html>");
		out.println("<script type=\"text/javascript\"> function flip(rid) { current=(document.getElementById(rid).style.display == 'none') ? 'block' : 'none'; document.getElementById(rid).style.display = current; } </script>");
		out.println("</head>");
		out.println("<body>");
		

	try {
		
		String id,title,nimi,hakusana;
		int nimiend = 0;
        Class.forName(dbDriver);
        con = DriverManager.getConnection(dbUrl);
        s = con.createStatement();
        rs = s.executeQuery("SELECT id,chantitle FROM rss");

        out.println("<img src=\"./img/googlelogo.gif\"/><br /><br />");
        out.println("<b>Select airport:</b><br /><br />");
		out.println("<form method=\"post\" action=\"http://localhost:8080/saatiedot/soap\">");

		out.println("<select name=\"hakusanat\">");
		
		// tulostetaan tietokannan lentokentät valikkoon
		
        while (rs.next()) {
        		
        		id = rs.getString("id");
                title = rs.getString("chantitle");
                
                for(int i=0;i<title.length();i++){
                	if(title.charAt(i) == '-'){
                		nimiend = i-5;
                	}		
                		
                }
                
                nimi = title.substring(17,nimiend);
                hakusana = nimi.replace(' ','-');
                out.println("<option value="+hakusana+">"+nimi+"</option>");
        }
        
        out.println("</select><br /><br />");
        out.println("<input type=\"submit\" value=\"Search\">");
        
        // asetusvalikko
        
        out.println("<div><ul><li><a href=\"#\" onClick=\"flip('asetukset');return false;\">Settings:</a></li></ul></div><div id=\"asetukset\"><script type=\"text/javascript\">flip('asetukset');</script>");
        out.println("<blockquote>License key:<br />");
		out.println("<textarea name=\"avain\" cols=\"30\" rows=\"1\">iTogcI5QFHJipkXinL8neoEwv1kntKWf</textarea><br />");
		
		out.println("Number of results:<br />");
		out.println("<select name=\"maara\">");
		
		for(int i=1;i<=9;i++)
			out.println("<option value="+i+">"+i+"</option>");
		out.println("<option value="+10+" selected>"+10+"</option>");
				
		out.println("</select><br /><br />");
		
        out.println("</blockquote></div>");
        
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
        out.flush();
		out.close();
	}
	
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws
		ServletException, IOException {
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter(); 
		
		String hakusanat = req.getParameter("hakusanat");
		String avain = req.getParameter("avain");
		String maara = req.getParameter("maara");
		
		try{
		
		// kutsu soap-metodia
		
		String vaste = gSoap(avain,hakusanat,maara);
		out.println(vaste);
		
		} catch (Exception e) {
			out.println(e);
		}
		out.flush();
		out.close();
           
	}
	
	static String gSoap(String avain,String hakusanat,String maara){

		StringBuffer ulos = new StringBuffer();
		hakusanat = hakusanat.replace('-',' ');
		ulos.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
		ulos.append("<html>");
		ulos.append("<head><title>Weather information</title></head>\n");
		ulos.append("<body>\n");
		ulos.append("<a href=\"javascript:history.back()\">Back</a><br /><br />\n");
		ulos.append("<h3>Results:</h3>\n");

		try {

		    URL url = new URL("http://api.google.com/search/beta2");
		    
		    // kysely
		    String xmlText = "<?xml version='1.0' encoding='UTF-8'?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"><SOAP-ENV:Body><ns1:doGoogleSearch xmlns:ns1=\"urn:GoogleSearch\" SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><key xsi:type=\"xsd:string\">"+avain+"</key><q xsi:type=\"xsd:string\">"+hakusanat+"</q><start xsi:type=\"xsd:int\">0</start><maxResults xsi:type=\"xsd:int\">"+maara+"</maxResults><filter xsi:type=\"xsd:boolean\">true</filter><restrict xsi:type=\"xsd:string\"></restrict><safeSearch xsi:type=\"xsd:boolean\">false</safeSearch><lr xsi:type=\"xsd:string\"></lr><ie xsi:type=\"xsd:string\">latin1</ie><oe xsi:type=\"xsd:string\">latin1</oe></ns1:doGoogleSearch></SOAP-ENV:Body></SOAP-ENV:Envelope>";

		    HttpURLConnection c = (HttpURLConnection) url.openConnection();
		    c.setRequestMethod("POST");
		    c.setRequestProperty("Content-Type", "text/xml; charset=\"utf-8\"");
		    c.setDoOutput(true);
		    OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream(),"UTF8");
		    out.write(xmlText);
		    out.close();	
					
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();						
			Document doc = dBuilder.parse(c.getInputStream());
			XPath haku = XPathFactory.newInstance().newXPath();
				
			NodeList nl = (NodeList)haku.evaluate("//item",doc,XPathConstants.NODESET);
			
			// tulostetaan vasteet
			
			for (int i = 0; i < nl.getLength(); i++) {
				
				Element currItem = (Element)nl.item(i);
				
				ulos.append("<a href="+haku.evaluate("URL",currItem)+">" + haku.evaluate("URL",currItem) + "</a><br />\n");
				ulos.append(haku.evaluate("title",currItem) + "\n");
				ulos.append(haku.evaluate("snippet",currItem) + "<br /><hr /><br />\n");
			}
			ulos.append("<a href=\"javascript:history.back()\">Back</a><br /><br />\n");
			c.disconnect();

		} catch (Exception e) {	ulos.append("<b>Search failed!</b><br />"+e+"<br /><br /><a href=\"javascript:history.back()\">Back</a>"); }
	
		ulos.append("</body>\n");
		ulos.append("</html>");
		return ulos.toString();
	
	}
}

