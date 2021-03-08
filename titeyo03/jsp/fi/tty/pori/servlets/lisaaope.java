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
import fi.tty.pori.vo.Opettaja;
import fi.tty.pori.dao.OpeDAO;
import java.sql.*;
/**
 *
 * @author pertti
 * @version
 */
public class lisaaope extends HttpServlet {
        
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
        Opettaja opettaja = new Opettaja();
        opettaja.setSukunimi(request.getParameter("sukunimi"));
        opettaja.setEtunimi(request.getParameter("etunimi"));
        opettaja.setEmail(request.getParameter("email"));
        opettaja.setHuone(request.getParameter("room"));
        boolean tiedotOK = true;
        // request.getParameter("openro")
        // muunnos STRING -> int
        // opettaja.setOpenro(nro);
        //
        // null silloin kun parametri (avain) puuttuu kokonaan
        // "" silloin kun avain löytyy mutta siihen ei ole asetettu arvoa
        // 
        if(opettaja.getSukunimi() == null || opettaja.getSukunimi().equalsIgnoreCase(""))  {
            tiedotOK = false;
            opettaja.setSukunimi("");
        }
        if(opettaja.getEtunimi() == null || opettaja.getEtunimi().equalsIgnoreCase("")){
            tiedotOK = false;
            opettaja.setEtunimi("");
        }
            
        if(opettaja.getEmail() == null)
            opettaja.setEmail("");
        if(opettaja.getHuone() == null)
            opettaja.setHuone("");
    
        request.setAttribute("opettaja", opettaja);
        if (tiedotOK) {
            OpeDAO dao = new OpeDAO(getConnection());
            dao.talletaTiedot(opettaja);
            this.getServletConfig().getServletContext().getRequestDispatcher("/opeOK.jsp").forward(request, response);
        }else {
            this.getServletConfig().getServletContext().getRequestDispatcher("/lisaaopeerror.jsp").forward(request, response);

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
