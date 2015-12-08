/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import QuestionWS.QuestionWS_Service;
import UserWS.UserWS_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author angelynz95
 */
public class DeleteController extends HttpServlet {

  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/QuestionWS.wsdl")
  private QuestionWS_Service service_1;

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
        response.setContentType("text/html;charset=UTF-8");
        
        // Mendapatkan user agent browser
        String userAgent = request.getHeader("User-Agent");

        // Mendapatkan IP Address
        // Memeriksa apakah client terhubung melalui proxy atau load balancer
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {  
          ipAddress = request.getRemoteAddr();
        }

        // Set cookie
        Cookie browserNameCookie = new Cookie ("user-agent", userAgent);
        Cookie ipAddressCookie = new Cookie ("ip-address", ipAddress);
        response.addCookie(browserNameCookie);
        response.addCookie(ipAddressCookie);
        
        boolean deleteQuestion = deleteQuestion(Integer.parseInt(request.getParameter("qid")), request.getParameter("token"), userAgent, ipAddress);
        if (deleteQuestion) {
          response.sendRedirect("IndexController?token="+request.getParameter("token"));
        } else {
          response.sendRedirect("log-in.jsp");
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

  private boolean deleteQuestion(int idQuestion, java.lang.String token, java.lang.String userAgent, java.lang.String ipAddress) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    QuestionWS.QuestionWS port = service_1.getQuestionWSPort();
    return port.deleteQuestion(idQuestion, token, userAgent, ipAddress);
  }

}
