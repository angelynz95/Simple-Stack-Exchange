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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.TokenExecutor;
import org.json.simple.JSONObject;

/**
 * Kelas yang menerima request servlet dari front-end pada setiap page
 * setelah pengguna melakukan log in
 */
public class UserNameController extends HttpServlet {

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
    response.setContentType("application/json");
    response.setHeader("Cache-control", "no-cache, no-store");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "-1");
    
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    response.setHeader("Access-Control-Max-Age", "86400");
    String token = request.getParameter("token");
    try (PrintWriter out = response.getWriter()) {
      // Mendapatkan user agent browser
//      String userAgent = request.getHeader("User-Agent");
//      
//      // Mendapatkan IP Address
//      // Memeriksa apakah client terhubung melalui proxy atau load balancer
//      String ipAddress = request.getHeader("X-FORWARDED-FOR");
//      if (ipAddress == null) {  
//        ipAddress = request.getRemoteAddr();
//      
      // Get cookie
      Cookie[] cookies = null;
      cookies = request.getCookies();
      String userAgent = null;
      String ipAddress = null;
      for (int i=0; i<cookies.length; i++) {
        String cookieName = cookies[i].getName();
        if (cookieName.equals("user-agent")) {
          userAgent = cookies[i].getValue();
        } else if (cookieName.equals("ip-address")) {
          ipAddress = cookies[i].getValue();
        }
      }
      TokenExecutor executor = new TokenExecutor(token, userAgent, ipAddress);
      
      JSONObject obj = new JSONObject();
      obj.put("user_name", executor.getUserName());
      obj.put("token", executor.getToken().getAccessToken());
      out.print(obj);
      executor.closeConnection();
      out.close();
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
