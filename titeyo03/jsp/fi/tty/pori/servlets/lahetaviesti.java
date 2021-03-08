/*
 * lisaaope.java
 *
 * Created on 12. helmikuuta 2005, 21:05
 */

package fi.tty.pori.servlets;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import fi.tty.pori.vo.Viesti;
import fi.tty.pori.dao.ViestiDAO;
import java.sql.*;
/**
 *
 * @author pertti
 * @version
 */
public class lahetaviesti extends HttpServlet {
        
    String connectionURL = "";
    String driver ="";
    public void init(){
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

    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Viesti viesti = new Viesti();
        viesti.setVastaanottaja(Integer.parseInt(request.getParameter("vastaanottaja")));
        viesti.setViesti(request.getParameter("viesti"));
        boolean tiedotOK = true;
        // request.getParameter("openro")
        // muunnos STRING -> int
        // opettaja.setOpenro(nro);
        //
        // null silloin kun parametri (avain) puuttuu kokonaan
        // "" silloin kun avain lytyy mutta siihen ei ole asetettu arvoa
        // 
        if(viesti.getVastaanottaja() == null)  {
            tiedotOK = false;
        }
        if(viesti.getViesti() == null || viesti.getViesti().equalsIgnoreCase("")){
            tiedotOK = false;
        }
            
    
        request.setAttribute("viesti", viesti);
        if (tiedotOK) {
            ViestiDAO dao = new ViestiDAO(getConnection());
            dao.sendiViesti(viesti);
            this.getServletConfig().getServletContext().getRequestDispatcher("/viestiOK.jsp").forward(request, response);
        }else {
            this.getServletConfig().getServletContext().getRequestDispatcher("/lahetaviestierror.jsp").forward(request, response);

        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
