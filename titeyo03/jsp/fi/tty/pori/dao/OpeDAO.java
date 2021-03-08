/*
 * OpeDAO.java
 *
 * Created on 12. helmikuuta 2005, 21:18
 */

package fi.tty.pori.dao;
import java.sql.*;
import java.util.Vector;
import fi.tty.pori.vo.Opettaja;
/**
 *
 * @author pertti
 */
public class OpeDAO {
    Connection mConnection;
    // Käytetään tällä kertaa virheen välittämiseen
    // voishan tässä käyttää myös poikkeuksia!!!!
    String mErrorMessage ="";
    /** Creates a new instance of OpeDAO */
    public OpeDAO(Connection connection) {
        mConnection = connection;
    }
    
    
    public boolean talletaTiedot( Opettaja opettaja) {
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
                        setErrorMessage(se.getMessage());
			return false;
		}
		return true;
    }
    
    public boolean update( Opettaja opettaja) {
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
    
    public Opettaja haeOpettaja( int openro) {
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
                            opettaja.setEtunimi(rs.getString("ETUNIMI"));
                            opettaja.setEmail(rs.getString("EMAIL"));
                            opettaja.setHuone(rs.getString("HUONE"));
                        }
                        
			ps.close();
			connection.close();
		}catch(SQLException se){
			return null;
		}
		return opettaja;
    }
  
    public Vector haeOpettajat( ) {
	
        Vector opeVector = new Vector();
        try{
		Connection connection = getConnection();
		String sqlStr = "SELECT OPENRO, SUKUNIMI,ETUNIMI, EMAIL,HUONE "
			+ "FROM OPETTAJA";

			Statement ps =  connection.createStatement();
			ResultSet rs = ps.executeQuery(sqlStr);
                        
                        while(rs.next()) {
                            Opettaja opettaja = new Opettaja();
                            opettaja.setOpenro(rs.getInt("OPENRO"));
                            opettaja.setSukunimi(rs.getString("SUKUNIMI"));
                            opettaja.setEtunimi(rs.getString("ETUNIMI"));
                            opettaja.setEmail(rs.getString("EMAIL"));
                            opettaja.setHuone(rs.getString("HUONE"));
                            opeVector.add(opettaja);
                        }
                        
			ps.close();
			connection.close();
		}catch(SQLException se){
			return new Vector();
		}
		return opeVector;
    }
    Connection getConnection() {
        return mConnection;
    }

    private void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }
    public String setErrorMessage( ) {
        return mErrorMessage;
    }
    
}
