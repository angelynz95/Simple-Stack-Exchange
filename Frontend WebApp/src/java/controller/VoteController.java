/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import AnswerWS.AnswerWS_Service;
import QuestionWS.QuestionWS_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Devina
 */
public class VoteController extends HttpServlet {
  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/QuestionWS.wsdl")
  private QuestionWS_Service service_1;
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
    if ("answer-up".contains(request.getParameter("name"))) {
      int aid = Integer.parseInt(request.getParameter("aid"));
      boolean voteSuccess = voteAnswer(aid, "up", request.getParameter("token"));
      if (voteSuccess) {
        response.sendRedirect("QuestionDetailController?token=" + request.getParameter("token") + "&qid=" + request.getParameter("qid"));
      } else {
        response.sendRedirect("log-in.jsp");
      }
    } else if ("answer-down".contains(request.getParameter("name"))) {
      int aid = Integer.parseInt(request.getParameter("aid"));
      boolean voteSuccess = voteAnswer(aid, "down", request.getParameter("token"));
      if (voteSuccess) {
        response.sendRedirect("QuestionDetailController?token=" + request.getParameter("token") + "&qid=" + request.getParameter("qid"));
      } else {
        response.sendRedirect("log-in.jsp");
      }
    } else if ("question-up".contains(request.getParameter("name"))) {
      int qid = Integer.parseInt(request.getParameter("qid"));
      int voteSuccess = voteQuestion(qid, request.getParameter("token"), "up");
      if ((voteSuccess == 1) || (voteSuccess == 0)) {
        response.sendRedirect("QuestionDetailController?token=" + request.getParameter("token") + "&qid=" + request.getParameter("qid"));
      } else {
        response.sendRedirect("log-in.jsp");
      }
    } else if ("question-down".contains(request.getParameter("name"))) {
      int qid = Integer.parseInt(request.getParameter("qid"));
      int voteSuccess = voteQuestion(qid, request.getParameter("token"), "down");
      if ((voteSuccess == 1) || (voteSuccess == 0)) {
        response.sendRedirect("QuestionDetailController?token=" + request.getParameter("token") + "&qid=" + request.getParameter("qid"));
      } else {
        response.sendRedirect("log-in.jsp");
      }
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

  private boolean voteAnswer(int aid, java.lang.String vote, java.lang.String token) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    AnswerWS.AnswerWS port = service.getAnswerWSPort();
    return port.voteAnswer(aid, vote, token);
  }

  private int voteQuestion(int qid, java.lang.String token, java.lang.String vote) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    QuestionWS.QuestionWS port = service_1.getQuestionWSPort();
    return port.voteQuestion(qid, token, vote);
  }
}
