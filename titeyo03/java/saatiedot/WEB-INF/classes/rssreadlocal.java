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
import java.lang.String;
import java.lang.Integer;

public class rssreadlocal extends HttpServlet {

public void doGet(HttpServletRequest req, HttpServletResponse res) throws
ServletException, IOException {
		
        StringBuffer ulos = new StringBuffer();     
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost/saatiedot?user=root&password=";
        Connection con = null;
        PreparedStatement s = null;
        ResultSet rs = null;
        Vector feedit = new Vector();
        String feed,lastbuilddate,rssid,title,description,link,chantitle;
        int ttl;
        Document doc;
        NodeList nlchan,nl;
        XPath haku = null;
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        int lbdd,lbdhh,oldlbdd,oldlbdhh;
		String[] temp1 = null;
		String[] temp2 = null;
		String oldlastbuilddate = null;
        
        feedit.add("http://www.weather.gov/data/current_obs/KJFK.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KALB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KART.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KBGM.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KBUF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KDKK.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KDSV.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KELM.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KELZ.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFOK.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFRG.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFZY.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KGFL.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KGTB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KHPN.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KHTO.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KHWV.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KIAG.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KISP.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KITH.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KJHW.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KLGA.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMGJ.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMSS.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMSV.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KNYC.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KOGS.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPLB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPOU.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KROC.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSCH.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSLK.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSWF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSYR.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KUCA.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMTP.rss");
        
        
        
        try {
        	
        	for(int i=0;i<feedit.size();i++){
        	
	        	feed = (String)feedit.elementAt(i);
	        	rssid =feed.substring(feed.length()-8,feed.length()-4);
	        	
	        	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        doc = dBuilder.parse(feed);

        		haku = XPathFactory.newInstance().newXPath();
        	
        		nl = (NodeList)haku.evaluate("//item",doc,XPathConstants.NODESET);
        		nlchan = (NodeList)haku.evaluate("/rss/channel",doc,XPathConstants.NODESET);
        		
        		chantitle = haku.evaluate("title",(Element)nlchan.item(0));
	   			lastbuilddate = haku.evaluate("lastBuildDate",(Element)nlchan.item(0));
	   			link = haku.evaluate("link",(Element)nlchan.item(0));
	   			ttl = Integer.parseInt(haku.evaluate("ttl",(Element)nlchan.item(0)));
	   			
	   			temp1 = lastbuilddate.split(" ");
	   			temp2 = temp1[4].split(":");
	   			lbdd = Integer.parseInt(temp1[1]);
	   			lbdhh = Integer.parseInt(temp2[0]);
	   			
	   			
	   			Class.forName(dbDriver);
            	con = DriverManager.getConnection(dbUrl);
	   				
	            for (int j = 0; j < nl.getLength(); j++) {
	                Element currItem = (Element)nl.item(j);
	                title = haku.evaluate("title",currItem);
	                description = haku.evaluate("description",currItem);
	                
	        
	                s = con.prepareStatement("SELECT count(id) as luku,lastbuilddate FROM rss WHERE id=? GROUP BY lastbuilddate");     
	                s.setString(1, rssid);
	                rs = s.executeQuery();
	                int ok=0;
	                while(rs.next()){
	                ok=rs.getInt("luku");
	                oldlastbuilddate = rs.getString("lastbuilddate");
	            }
	            
					
					
					
					
	            if(ok==0){  
	                s = con.prepareStatement("INSERT INTO rss (id,chantitle,link,lastbuilddate,ttl,title,description) VALUES(?,?,?,?,?,?,?)");
	                
	                s.setString(1, rssid);     
	                s.setString(2, chantitle);
	                s.setString(3, link);
	                s.setString(4, lastbuilddate);
	                s.setInt(5, ttl);
	                s.setString(6, title);
	                s.setString(7, description);
	                s.executeUpdate();

	                ulos.append(feed + ": " + title + "\n" + description + "\n\n");
	     		}	else{
	     			
	     			
	     				
	     				temp1 = oldlastbuilddate.split(" ");
	   					temp2 = temp1[4].split(":");
	   					oldlbdd = Integer.parseInt(temp1[1]);
	   					oldlbdhh = Integer.parseInt(temp2[0]);

			     		if(lbdd > oldlbdd || lbdhh > oldlbdhh){
			     				s.executeUpdate("UPDATE rss SET title='"+title+"',description='"+description+"',lastbuilddate='"+lastbuilddate+"' WHERE id='"+rssid+"'");
			     			} 
			     		
	            		} 
        		}
     		}
     			
    	} catch (Exception e) {
             ulos.append(e);
             out.println(ulos);
            	
           }
           
		out.println("<html><body>Feedit luettu.</body></html>"); 
        out.flush();
		out.close();
            
}
}
