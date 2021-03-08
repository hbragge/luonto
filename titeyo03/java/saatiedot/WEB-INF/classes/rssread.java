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

public class rssread extends HttpServlet {
		
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws
		ServletException, IOException {
		
        StringBuffer ulos = new StringBuffer();     
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbUrl = "jdbc:mysql://localhost/saatiedot?user=root&password=";
        Connection con = null;
        PreparedStatement s = null;
        PreparedStatement s2 = null;
        ResultSet rs = null;
        Vector feedit = new Vector();
        Vector feedit2 = new Vector();
        String feed,lastbuilddate,rssid,title,description,link,chantitle;
        int ttl;
        int lampotila = 0;
        int tuuli = 0;
        int kosteus = 0;
        Document doc;
        NodeList nlchan,nl;
        XPath haku = null;
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        int lbdd,lbdhh,oldlbdd,oldlbdhh;
		String[] temp1 = null;
		String[] temp2 = null;
		String oldlastbuilddate = null;
        
        // s‰‰tiedot
        
        feedit.add("http://www.weather.gov/data/current_obs/KNRB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/K40J.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KAAF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KAPF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KBKV.rss");
      /*  feedit.add("http://www.weather.gov/data/current_obs/KBOW.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KCOF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KCRG.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KCTY.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KDAB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFLL.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFMY.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFPR.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KFXE.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KGNV.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KHST.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KJAX.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KLAL.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KLEE.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMCF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMCO.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMIA.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KMLB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KNIP.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KOCF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KOPF.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KOGS.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KORL.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPBI.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPGD.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPIE.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KPMP.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KRSW.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSFB.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSGJ.rss");
        feedit.add("http://www.weather.gov/data/current_obs/KSPG.rss");*/
        
        // s‰‰uutiset
        
        feedit2.add("http://www.airport-technology.com/news-rss.xml");
        feedit2.add("http://rss.cnn.com/rss/cnn_topstories.rss");
        feedit2.add("http://rss.cnn.com/rss/cnn_us.rss");
        feedit2.add("http://rss.cnn.com/rss/cnn_space.rss");
        feedit2.add("http://www.sciencedaily.com/rss/newsfeed.xml");
        feedit2.add("http://earthobservatory.nasa.gov/eo.rss");
        feedit2.add("http://feeds.feedburner.com/sun-sentinel/news/local/caribbean");
        feedit2.add("http://www.firstcoastnews.com/rss/rss_weather.aspx");
        feedit2.add("http://www.npr.org/rss/rss.php?id=1001");
        feedit2.add("http://www.talkr.com/app/cast_pods.app?feed_id=13157");
        feedit2.add("http://www.miami.com/mld/miamiherald/news/weather/environment/rss.xml");
        feedit2.add("http://www.miami.com/mld/miamiherald/news/rss.xml");
        feedit2.add("http://rss.msnbc.msn.com/id/3032524/device/rss/rss.xml");
        feedit2.add("http://www.wusf.usf.edu/Podcast/floridamatters.xml");
        feedit2.add("http://rss.msnbc.msn.com/id/3032117/device/rss/rss.xml");
        
        try {
        	
        	// k‰y l‰pi s‰‰tietofeedit
        	
        	for(int i=0;i<feedit.size();i++){
        	
	        	feed = (String)feedit.elementAt(i);
	        	rssid =feed.substring(feed.length()-8,feed.length()-4);
	        	
	        	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        doc = dBuilder.parse(feed);

        		haku = XPathFactory.newInstance().newXPath();
        		
        		
        		
        		nl = (NodeList)haku.evaluate("//item",doc,XPathConstants.NODESET);
        		nlchan = (NodeList)haku.evaluate("/rss/channel",doc,XPathConstants.NODESET);
        		
        		// hae feedin kanavakohtaiset tiedot
        		
        		chantitle = haku.evaluate("title",(Element)nlchan.item(0));
	   			lastbuilddate = haku.evaluate("lastBuildDate",(Element)nlchan.item(0));
	   			link = haku.evaluate("link",(Element)nlchan.item(0));
	   			ttl = Integer.parseInt(haku.evaluate("ttl",(Element)nlchan.item(0)));
	   			
	   			// pura p‰iv‰ys p‰iviksi ja tunneiksi
	   			
	   			temp1 = lastbuilddate.split(" ");
	   			temp2 = temp1[4].split(":");
	   			lbdd = Integer.parseInt(temp1[1]);
	   			lbdhh = Integer.parseInt(temp2[0]);
	   			
	   			
	   			Class.forName(dbDriver);
            	con = DriverManager.getConnection(dbUrl);
	   				
	   			// hae feedin itemin tiedot
	   				
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
	            
	            // parseta l‰mpˆtilat, tuulennopeudet ja kosteudet
	            
	            int p = 0;
	            	for(int k=0;k<description.length()-1;k++){
	 					
	                	if(description.charAt(k) == '.' && p < 5){
	    					
	    					try {
		                		if(p==0){ tuuli = Integer.parseInt(description.substring(k-6,k-4).trim()); }
		                		
		                		else if(p==3){ kosteus = Integer.parseInt(description.substring(k-3,k-1).trim()); }
		                		
		                		else if(p==4){lampotila = Integer.parseInt(description.substring(k-3,k).trim());p++;break; }
		                		
		                		p++;
	                			} catch (Exception e) {
	                		}
	                	}
	        		} 
	            
	            // jos tietoa ei lˆydy, tallenna
	            
	            if(ok==0){  
	            	
	            	
	                s = con.prepareStatement("INSERT INTO rss (id,chantitle,link,lastbuilddate,ttl,title,description,tuuli,kosteus,lampotila) VALUES(?,?,?,?,?,?,?,?,?,?)");
	                
	                s.setString(1, rssid);     
	                s.setString(2, chantitle);
	                s.setString(3, link);
	                s.setString(4, lastbuilddate);
	                s.setInt(5, ttl);
	                s.setString(6, title);
	                s.setString(7, description);
	                s.setInt(8, tuuli);
	               	s.setInt(9, kosteus);
	                s.setInt(10, lampotila);
	                
	                s.executeUpdate();


	                ulos.append(feed + ": " + title + "\n" + description + "\n\n");
	     		}	else {
	     			
	     				temp1 = oldlastbuilddate.split(" ");
	   					temp2 = temp1[4].split(":");
	   					oldlbdd = Integer.parseInt(temp1[1]);
	   					oldlbdhh = Integer.parseInt(temp2[0]);
						
						// jos tieto on vanha, niin p‰ivit‰
						
			     		if(lbdd > oldlbdd || lbdhh > oldlbdhh) {
			     			
			     				s.executeUpdate("UPDATE rss SET title='"+title+"',description='"+description+"',lastbuilddate='"+lastbuilddate+"',lampotila="+lampotila+",tuuli="+tuuli+",kosteus="+kosteus+" WHERE id='"+rssid+"'");
	
			     			} 
	            		} 		
        			}
     			}

     			// lue s‰‰uutiset
     			
     			for(int i=0;i<feedit2.size();i++) {
        	
		        	feed = (String)feedit2.elementAt(i);
		        	
		        	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			        doc = dBuilder.parse(feed);
	
	        		haku = XPathFactory.newInstance().newXPath();
	        	
	        		nl = (NodeList)haku.evaluate("//item",doc,XPathConstants.NODESET);
	        		
	
		   			Class.forName(dbDriver);
	            	con = DriverManager.getConnection(dbUrl);
		   				
		            for (int j = 0; j < nl.getLength(); j++) {
		            	
		                Element currItem = (Element)nl.item(j);
		                title = haku.evaluate("title",currItem);
		                link = haku.evaluate("link",currItem);
		                description = haku.evaluate("description",currItem);
		                
		                s = con.prepareStatement("SELECT count(title) as luku FROM uutiset WHERE title=?");     
		                s.setString(1, title);
		                rs = s.executeQuery();
		                int ok=0;
		                
		                while(rs.next()){
		                ok=rs.getInt("luku");
		            }
		            
		            // jos uutista ei ole ennest‰‰n, tallenna
		            
		            if(ok==0){  
		                s = con.prepareStatement("INSERT INTO uutiset (title,link,description,osumat) VALUES(?,?,?,?)");
		                
		                s.setString(1, title);     
		                s.setString(2, link);
		                s.setString(3, description);
		                s.setInt(4, 0);
		                s.executeUpdate();
	
		                ulos.append(feed + ": " + title + "\n" + description + "\n\n");
		     		}	else{
		     			
		            		} 		
	        			}
     			}
     			
     			// tarkasta mist‰ uutisista lˆytyy eniten haluttuja sanoja
     			
				s = con.prepareStatement("SELECT description FROM uutiset");		
				rs = s.executeQuery();
				ArrayList rssDescription = new ArrayList();
				ArrayList rssTitle = new ArrayList();
				ArrayList rssWords = new ArrayList();
				ArrayList rssWordCount = new ArrayList();
				rssWords.add("storm");
				rssWords.add("hurricane");
				rssWords.add("tornado");
				rssWords.add("weather");
				rssWords.add("hazard");
				rssWords.add("katarina");
				rssWords.add("wilma");
				rssWords.add("nina");
				rssWords.add("rita");
				rssWords.add("florida");

				int id = 1;
				s2 = con.prepareStatement("UPDATE uutiset SET osumat=0");
				s2.execute();
				while(rs.next()){
					
						String sisaltoValmis = rs.getString("description").toLowerCase();				
					
						rssDescription.add(	sisaltoValmis );
	
						while(sisaltoValmis.length()>0){
						int tyhja = sisaltoValmis.indexOf(" ");
						int pilkku = sisaltoValmis.indexOf(",");
						int piste = sisaltoValmis.indexOf(".");
						int kaksois = sisaltoValmis.indexOf(":");
	
						if(tyhja==-1){
							tyhja=100;
						}
						if(pilkku==-1){
							pilkku=100;
						}
						if(piste==-1){
							piste=100;
						}
						if(kaksois==-1){
							kaksois=100;
						}
						int paikka = Math.min(tyhja,pilkku);
						paikka = Math.min(paikka,piste);
						paikka = Math.min(paikka,kaksois);
						String wordNow="";
						
						if(paikka < 99){
							wordNow = sisaltoValmis.substring(0,paikka) + "";
							sisaltoValmis = sisaltoValmis.substring(paikka+1,sisaltoValmis.length()) + "";
						}else{
							wordNow = sisaltoValmis.substring(0,sisaltoValmis.length()) + "";
							sisaltoValmis = "";
						}
						
						// lis‰‰ uutiset osumat-kentt‰‰ aina kun siit‰ lˆytyy haluttu sana
						
						if(wordNow.length()>1){

							for (int i = 0; i < rssWords.size(); i++){
								String keyword = (String)rssWords.get(i);
								if(wordNow.equals( keyword )){
									s2 = con.prepareStatement("UPDATE uutiset SET osumat=osumat+1 WHERE id='"+id+"'");
									s2.execute();	
								}
							}
							
						}		
							
					}				
					id++;	
				}
     					
    	} catch (Exception e) {
             ulos.append(e);
             out.println(ulos);
           }
           
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><html><body>Feeds read.</body></html>"); 
        out.flush();
		out.close();
            
	}
}
