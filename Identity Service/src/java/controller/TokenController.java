/*
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework.
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;
import org.json.simple.parser.*;
import main.TokenExecutor;

/**
 * Kelas yang menghubungkan SOAP web service dengan Identity Service.
 * Digunakan untuk melakukan validasi token.
 */
public class TokenController extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {   
    BufferedReader in = request.getReader();
    String inputLine;
    StringBuffer stringBuffer = new StringBuffer();
    
    while ((inputLine = in.readLine()) != null) {
      stringBuffer.append(inputLine);
    }
    
    JSONParser parser = new JSONParser();
    try {
      Object object = parser.parse(stringBuffer.toString());
      JSONObject req = (JSONObject) object;
      String token = (String)req.get("token");
      
      response.setContentType("application/json; charset=UTF-8");
      PrintWriter writer = response.getWriter();
      // Mendapatkan user agent browser
      String userAgent = request.getHeader("User-Agent");
      
      // Mendapatkan IP Address
      // Memeriksa apakah client terhubung melalui proxy atau load balancer
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {  
        ipAddress = request.getRemoteAddr();
      }
      TokenExecutor executor = new TokenExecutor(token);
      executor.closeConnection();
      
      JSONObject resp = new JSONObject();
      resp.put("is_valid", executor.getIsValid());
      resp.put("id_user", executor.getIdUser());
      writer.println(resp);
    } catch (ParseException ex) {
      Logger.getLogger(TokenController.class.getName()).log(Level.SEVERE, null, ex);
    }   
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
