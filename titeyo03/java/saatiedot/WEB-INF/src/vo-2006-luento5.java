/*


	5 luennolla k‰sitell‰‰n esimerkkej‰ harjoitustyˆt‰ varten
	Kuvat + selostukset vain luennolla
	HUOM ao. koodi osin puutteellista, osin vajaak‰ytˆss‰. ƒl‰ kopioi, vaan kehit‰ :-)




	6.4. luento peruttu
	
	
	13.4. SOAP





 	HUOM:
 	II esimerkiss‰ on huomioitava, ett‰ jos HTML lomakkeen joukkoon tulostetaan sellaiai
	merkkej‰, jotka sotkevat syntaksin, on tuloksena ett‰ serveltti ei osaa tulkata 
	lomaketta ja voipi palauttaa vaikka NULL POINTER... (vert. miten luennolla k‰vi)
	
	esim: sana= companie's ==> lomakkeella <input... name='companie's'...> eli ihan v‰‰rin.
	Merkit joko korvattava tai sitten lomake laadittava "..." merkeill‰
	
	
	










*/
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------
	static String rss_yhteenveto(ArrayList kwLista){
		StringBuffer ulos = new StringBuffer();

		ArrayList rssItems = new ArrayList();
		ArrayList itemRank = new ArrayList();
		
		String dbDriver = "org.gjt.mm.mysql.Driver";
		String dbUrl = "jdbc:mysql://localhost/research?user=root";
		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		ulos.append("<rss version=\"2.0\">\n\t<channel>\n\t\t<title> Uutisosumat hakusanan mukaan </title>\n\t\t<category>Yhteenveto</category>\n\t\t<description> Uutisosumat hakusanan mukaan </description>");
		
		try {
			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbUrl);
			for (int i = 0; i < kwLista.size(); i++){	
				String keyword = (String)kwLista.get(i);
				
				ulos.append("\n\t\t<item>\n\t\t\t<title>\n\t\t\t\tOsumat / " + keyword + "\n\t\t\t</title>\n\t\t\t<description>");
				
				s = con.prepareStatement("SELECT count(title) AS lkm, year(time) AS vuosi, month(time) AS kk, dayofmonth(time) AS pv FROM rss WHERE description LIKE ? GROUP BY year(time),month(time),dayofmonth(time)");		
				s.setString(1, "%" + keyword + "%");
				rs = s.executeQuery();
				
				while(rs.next()){
				
					int maara = rs.getInt("lkm");
					int vuosi = rs.getInt("vuosi");
					int kk = rs.getInt("kk");
					int pv = rs.getInt("pv");
					ulos.append("\n\t\t\t\t" + vuosi + "-" + kk + "-" + pv + ": " + maara + " osumaa");
				}
				ulos.append("\n\t\t\t</description>\n\t\t</item>");
			}
						
			ulos.append("\n\t</channel>\n</rss>");	

		} catch (Exception e) {
			ulos.append(e);
		}finally {
			try{
				if (con != null) {con.close();}
			} catch ( SQLException ignored ) {}
		}
		return ulos.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//-------------------------------------------------------------------------------------------	
//-------------------------------------------------------------------------------------------	
//-------------------------------------------------------------------------------------------	
		static String xtm_yhteenveto(ArrayList kwLista){
		StringBuffer ulos = new StringBuffer();

		ArrayList rssDescription = new ArrayList();
		ArrayList rssTitle = new ArrayList();
		ArrayList rssWords = new ArrayList();
		ArrayList rssWordCount = new ArrayList();

		String dbDriver = "org.gjt.mm.mysql.Driver";
		String dbUrl = "jdbc:mysql://localhost/research?user=root";
		Connection con = null;
		PreparedStatement s = null;
		ResultSet rs = null;
		ulos.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ulos.append("\n<topicMap id=\"1\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
		
		try {
			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbUrl);

			for (int i = 0; i < kwLista.size(); i++){	
				String keyword = (String)kwLista.get(i);
				ulos.append("\n\t<topic id=\"001_" + i + "\"><instanceOf><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#concept\"/></instanceOf><baseName><baseNameString><![CDATA[" + keyword + "]]></baseNameString></baseName></topic>");
				
				s = con.prepareStatement("SELECT count(title) AS lkm, year(time) AS vuosi, month(time) AS kk, dayofmonth(time) AS pv FROM rss WHERE description LIKE ? GROUP BY year(time),month(time),dayofmonth(time)");		
				s.setString(1, "%" + keyword + "%");
				rs = s.executeQuery();
				
				while(rs.next()){
				
					int maara = rs.getInt("lkm");
					int vuosi = rs.getInt("vuosi");
					int kk = rs.getInt("kk");
					int pv = rs.getInt("pv");


					ulos.append("\n\t<topic id=\"001_" + i + "_" + vuosi + "_" + kk + "_" + pv + "\"><instanceOf><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#concept\"/></instanceOf><baseName><baseNameString><![CDATA[" + vuosi + "." + kk + "." + pv + ": " + maara + " kpl]]></baseNameString></baseName></topic>");

					ulos.append("\n\t<topic id=\"002_" + i + "_" + i + "_" + vuosi + "_" + kk + "_" + pv + "\"><instanceOf><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#linkingPhrase\"/></instanceOf><baseName><baseNameString><![CDATA[" + maara + "]]></baseNameString></baseName></topic>");
    				ulos.append("\n\t<association id=\"a_003_" + i + "_" + vuosi + "_" + kk + "_" + pv +"\"><instanceOf><topicRef xlink:type=\"simple\" xlink:href=\"#002_" + i + "_" + i + "_" + vuosi + "_" + kk + "_" + pv + "\"/></instanceOf><member><roleSpec><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#incoming\"/></roleSpec><topicRef xlink:type=\"simple\" xlink:href=\"#001_" + i + "\"/></member><member><roleSpec><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#outgoing\"/></roleSpec><topicRef xlink:type=\"simple\" xlink:href=\"#001_" + i + "_" + vuosi + "_" + kk + "_" + pv + "\"/></member></association>"); 
	
					
				}
			}			
			
			
			ulos.append("\n</topicMap>");
		
		} catch (Exception e) {
			ulos.append(e);
		}finally {
			try{
				if (con != null) {con.close();}
			} catch ( SQLException ignored ) {}
		}
		return ulos.toString();
	}
	
	
	
	
	
	
	
	
	





//-------------------------------------------------------------------------------------------	
//-------------------------------------------------------------------------------------------	
//-------------------------------------------------------------------------------------------		
			if(arviointi.equals("sanat")){			
	
				s = con.prepareStatement("SELECT description FROM rss where indexed<1");		
				rs = s.executeQuery();
				
				while(rs.next()){
					
					String sisaltoValmis = rs.getString("description").toLowerCase();				
					
					rssDescription.add(	sisaltoValmis );
	
	// yksitt‰iset sanat ja niiden esiintymism‰‰r‰t
	
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
						if(wordNow.length()>1){
							int osumia=0;
							for (int i = 0; i < rssWords.size(); i++){
								String keyword = (String)rssWords.get(i);
								if(wordNow.equals( keyword )){
									osumia++;
									int lukuNyt = (Integer)rssWordCount.get(i) + 1;
									rssWordCount.set(i,lukuNyt);
								}
							}
							if(osumia==0){
								rssWords.add( wordNow );
								rssWordCount.add( 1 );
							}
						}			
					}				
	
					
				}
				
				s2 = con.prepareStatement("UPDATE rss SET indexed=1");	
				s2.execute();						

	
				for (int i = 0; i < rssWords.size(); i++){
					String keyword = (String)rssWords.get(i);
					int luku = (Integer)rssWordCount.get(i);
	
	
					s = con.prepareStatement("SELECT sana,maara FROM sanat WHERE sana=?");	
					s.setString(1, keyword);	
					rs = s.executeQuery();
					
					if(rs.next()){
						luku = luku + rs.getInt("maara");
						s2 = con.prepareStatement("UPDATE sanat SET maara=? WHERE sana=?");	
						s2.setInt(1, luku);
						s2.setString(2, keyword);	
						s2.execute();						
					}else{
						s2 = con.prepareStatement("INSERT INTO sanat(sana,maara) VALUES (?,?)");	
						s2.setString(1, keyword);
						s2.setInt(2, luku);	
						s2.execute();
					}
					
	
	
	
				}
	
	
				out.println("-- ok --");
			}
				
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	
			if(arviointi.equals("lista")){
				s = con.prepareStatement("SELECT sana,maara FROM sanat WHERE maara>5 AND eitarkea<1 AND tarkea<1 ORDER BY maara DESC");
				rs = s.executeQuery();
				out.println("<html><head><title></title></head><body><form method='post' action='http://localhost:8080/into/tiivistelma'><table>");
				while(rs.next()){
					out.println("<tr><td>" + rs.getString("sana") + "<td>" + rs.getString("maara") + "<td><input type='radio' name='" + rs.getString("sana") + "' value='1'><td><input type='radio' name='" + rs.getString("sana") + "' value='0' checked></tr>");
				}
				out.println("<tr><td><input type='hidden' name='arviointi' value='tehty'><input type='submit' value='kirjaa'></table></form></body></html>");
			}
































//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	
			if(arviointi.equals("tehty")){
				s = con.prepareStatement("SELECT sana FROM sanat WHERE maara>5 AND eitarkea<1 AND tarkea<1 ORDER BY maara DESC");
				rs = s.executeQuery();
				while(rs.next()){
					String arvioSanasta = req.getParameter( rs.getString("sana") );
					if(arvioSanasta.equals("0")){
						s2 = con.prepareStatement("UPDATE sanat SET tarkea=0,eitarkea=1 WHERE sana=?");	
						s2.setString(1, rs.getString("sana"));	
						s2.execute();
					}					
					if(arvioSanasta.equals("1")){
						s2 = con.prepareStatement("UPDATE sanat SET tarkea=1,eitarkea=0 WHERE sana=?");	
						s2.setString(1, rs.getString("sana"));	
						s2.execute();
					}					
				}
				
				out.println("-- done --");
			}















































//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------	

			if(arviointi.equals("xtm")){


				s = con.prepareStatement("SELECT title,description FROM rss");		
				rs = s.executeQuery();
				
				while(rs.next()){
					
					String sisalto = rs.getString("description").toLowerCase();				
					rssDescription.add(	sisalto );
					rssTitle.add( rs.getString("title") );
					
				}


// sanat, jotka kelpuutettu mukaan
		
				ArrayList finalWords = new ArrayList();
				s = con.prepareStatement("SELECT sana FROM sanat WHERE tarkea>0");
				rs = s.executeQuery();
				while(rs.next()){
					finalWords.add( rs.getString("sana") );
					out.println("<br>" + rs.getString("sana") );
					
				}
				out.println("<br>\n----------------------------------------\n\n\n");


//muodostetaan piirrevektorit
				out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				out.println("<topicMap id=\"1\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

				int piirreVektorit[][] = new int[rssDescription.size()][finalWords.size()];
				for (int i = 0; i < rssDescription.size(); i++){
					String txt = (String)rssDescription.get(i);
					
					int nodeTehty=0;
					for (int j = 0; j < finalWords.size(); j++){
						String kw = (String)finalWords.get(j);
						if(txt.indexOf(kw)>=0){
							piirreVektorit[i][j] = 1;
							if(nodeTehty==0){
								out.println("\n\t<topic id=\"001_" + i + "\"><instanceOf><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#concept\"/></instanceOf><baseName><baseNameString><![CDATA[" + (String)rssTitle.get(i) + "]]></baseNameString></baseName></topic>");
								nodeTehty=1;	
							}
						}else{
							piirreVektorit[i][j] = 0;
						}
	
	
					}
				}
							
				
				
// lasketaan et‰isyydet	ja hyv‰ksyt‰‰n jos n sanaa t‰sm‰‰	
				String vaatimus = req.getParameter("osuma");
				int vaatimus2 = Integer.parseInt(vaatimus);
				
				for (int i = 0; i < rssDescription.size(); i++){
					for (int i2 = i+1; i2 < rssDescription.size(); i2++){
						int samanlaisuus=0;
						for (int j = 0; j < finalWords.size(); j++){
							if(piirreVektorit[i][j]==1){
								if(piirreVektorit[i2][j]==1){
									samanlaisuus++;
								}
							}
		
						}
					
						if(samanlaisuus>vaatimus2){

							out.println("\n\t<topic id=\"002_" + i + "_" + i2 + "\"><instanceOf><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#linkingPhrase\"/></instanceOf><baseName><baseNameString><![CDATA[" + samanlaisuus + "]]></baseNameString></baseName></topic>");
	    					out.println("\n\t<association id=\"a_003_" + i + "_" + i2 + "\"><instanceOf><topicRef xlink:type=\"simple\" xlink:href=\"#002_" + i + "_" + i2 + "\"/></instanceOf><member><roleSpec><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#incoming\"/></roleSpec><topicRef xlink:type=\"simple\" xlink:href=\"#001_" + i + "\"/></member><member><roleSpec><subjectIndicatorRef xlink:type=\"simple\" xlink:href=\"http://cmap.coginst.uwf.edu/#outgoing\"/></roleSpec><topicRef xlink:type=\"simple\" xlink:href=\"#001_" + i2 + "\"/></member></association>"); 
		
						}
					}
				}			
				
				
				out.println("\n</topicMap>");
			}
				
		} catch (Exception e) {
			out = res.getWriter();
			res.setContentType("text/html");
			out.println(e);
			out.flush();
			out.close();
		}finally {
			try{
				if (con != null) {con.close();}
			} catch ( SQLException ignored ) {}
		}
	}	