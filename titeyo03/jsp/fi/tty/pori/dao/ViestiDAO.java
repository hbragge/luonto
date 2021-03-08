/*
 * OpeDAO.java
 *
 * Created on 12. helmikuuta 2005, 21:18
 */

package fi.tty.pori.dao;
import java.sql.*;
import java.util.Vector;
import fi.tty.pori.vo.Viesti;
/**
 *
 * @author pertti
 */
public class ViestiDAO {
    Connection mConnection;
    // Kytetn tll kertaa virheen vlittmiseen
    // voishan tss kytt mys poikkeuksia!!!!
    String mErrorMessage ="";
    /** Creates a new instance of OpeDAO */
    public ViestiDAO(Connection connection) {
        mConnection = connection;
    }
    
    
    public boolean sendiViesti( Viesti viesti) {
	try{
		Connection connection = getConnection();
		String sqlStr = "INSERT INTO VIESTI (VIESTINRO,VASTAANOTTAJA, VIESTI) "
			+ "VALUES(?,?,?)";

			PreparedStatement ps =  connection.prepareStatement(sqlStr);
			ps.setInt(1,viesti.getViestinro());
			ps.setInt(2,viesti.getVastaanottaja());
			ps.setString(3,viesti.getViesti());
			ps.executeUpdate();
			ps.close();
			connection.close();
		}catch(SQLException se){
                        setErrorMessage(se.getMessage());
			return false;
		}
		return true;
    }
    
    public boolean poista( int viestinro) {
	try{
		Connection connection = getConnection();
		String sqlStr = "DELETE FROM VIESTI WHERE VIESTINRO=?";

			PreparedStatement ps =  connection.prepareStatement(sqlStr);
			ps.setInt(1,viestinro);
			ps.executeUpdate();
			ps.close();
			connection.close();
		}catch(SQLException se){
			return false;
		}
		return true;
    }
 
    public Vector haeViestit( ) {
	
        Vector viestiVector = new Vector();
        try{
		Connection connection = getConnection();
		String sqlStr = "SELECT VIESTINRO, VASTAANOTTAJA,VIESTI"
			+ "FROM VIESTI";

			Statement ps =  connection.createStatement();
			ResultSet rs = ps.executeQuery(sqlStr);
                        
                        while(rs.next()) {
                            Viesti viesti = new Viesti();
                            viesti.setViestinro(rs.getInt("VIESTINRO"));
                            viesti.setVastaanottaja(rs.getInt("VASTAANOTTAJA"));
                            viesti.setViesti(rs.getString("VIESTI"));                         
                            viestiVector.add(viesti);
                        }
                        
			ps.close();
			connection.close();
		}catch(SQLException se){
			return new Vector();
		}
		return viestiVector;
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
