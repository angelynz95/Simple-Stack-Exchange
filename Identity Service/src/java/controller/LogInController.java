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
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import main.*;

/**
 * Kelas yang menerima request servlet dari front-end saat melakukan log in.
 */
public class LogInController extends HttpServlet {

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
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    try (PrintWriter out = response.getWriter()) {
      // Mendapatkan user agent browser
      String userAgent = request.getHeader("User-Agent");
      
      // Mendapatkan IP Address
      // Memeriksa apakah client terhubung melalui proxy atau load balancer
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {  
        ipAddress = request.getRemoteAddr();
      }
      
      TokenExecutor executor = new TokenExecutor(email, password, userAgent, ipAddress);
      
      // Set cookie
      Cookie browserNameCookie = new Cookie ("user-agent", userAgent);
      Cookie ipAddressCookie = new Cookie ("ip-address", ipAddress);
      response.addCookie(browserNameCookie);
      response.addCookie(ipAddressCookie);
      
      JSONObject obj = new JSONObject();
      String tokenSend = executor.getToken().getAccessToken().substring(0, 36);
      obj.put("access_token", tokenSend);
      out.print(obj);
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
