/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import AnswerWS.AnswerWS_Service;
import UserWS.UserWS_Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author User
 */
public class AddAnswerController extends HttpServlet {
  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/UserWS.wsdl")
  private UserWS_Service service_1;
  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/AnswerWS.wsdl")
  private AnswerWS_Service service;

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
    int questionId = Integer.parseInt(request.getParameter("qid"));
    String token = request.getParameter("token");
    int userId = getUserByToken(token, "http://localhost:8082/Identity_Service/TokenController");
    if (userId > 0) {
      String answerContent = request.getParameter("answer-content");
      boolean valid = addAnswer(questionId, token, answerContent);
      response.sendRedirect("QuestionDetailController?token=" + token + "&qid="+questionId);
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

  private boolean addAnswer(int qid, java.lang.String token, java.lang.String content) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    AnswerWS.AnswerWS port = service.getAnswerWSPort();
    return port.addAnswer(qid, token, content);
  }

  private int getUserByToken(java.lang.String token, java.lang.String urlString) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    UserWS.UserWS port = service_1.getUserWSPort();
    return port.getUserByToken(token, urlString);
  }
}
