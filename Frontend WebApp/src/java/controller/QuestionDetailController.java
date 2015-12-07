/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import AnswerWS.Answer;
import AnswerWS.AnswerWS_Service;
import QuestionWS.QuestionWS_Service;
import UserWS.User;
import UserWS.UserWS_Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import java.util.*;

/**
 *
 * @author Devina
 */
public class QuestionDetailController extends HttpServlet {
  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/UserWS.wsdl")
  private UserWS_Service service_2;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/AnswerWS.wsdl")
    private AnswerWS_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/Stackexchange_WS/QuestionWS.wsdl")
    private QuestionWS_Service service;
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
    String token = request.getParameter("token");
    int idUser = 0;
    if (token != null) {
      idUser = getUserByToken(request.getParameter("token"), "http://localhost:8082/Identity_Service/TokenController");
    }
    if (idUser > 0 || token != null ) {
      if (request.getParameter("qid") != null) {
        String temp = request.getParameter("qid");
        int id = Integer.parseInt(temp);
        java.util.List<QuestionWS.Question> questions = getQuestion(id);
        UserWS.User u1 = getUserByIdQuestion(id);
        java.util.List<AnswerWS.Answer> answers = getAnswerByQId(id);
        java.util.List<UserWS.User> u2 = new ArrayList<UserWS.User>();
        for (Answer answer : answers) {
          u2.add(getUserByIdAnswer(answer.getIdAnswer()));
        }
        int count = getCountAnswerByQId(id);

        if (request.getParameter("token") != null) {
          // Memperoleh user id berdasarkan token
          int userId = getUserByToken(request.getParameter("token"), "http://localhost:8082/Identity_Service/TokenController");
          request.setAttribute("userId", userId);
        }

        request.setAttribute("questions", questions);
        request.setAttribute("u1", u1);
        request.setAttribute("count", count);
        request.setAttribute("answers", answers);
        request.setAttribute("u2", u2);
        request.getServletContext().getRequestDispatcher("/question-detail.jsp").forward(request, response);
      }
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

    private java.util.List<QuestionWS.Question> getQuestion(int idQuestion) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        QuestionWS.QuestionWS port = service.getQuestionWSPort();
        return port.getQuestion(idQuestion);
    }

    private java.util.List<AnswerWS.Answer> getAnswerByQId(int qid) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        AnswerWS.AnswerWS port = service_1.getAnswerWSPort();
        return port.getAnswerByQId(qid);
    }

  private int getCountAnswerByQId(int qid) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    AnswerWS.AnswerWS port = service_1.getAnswerWSPort();
    return port.getCountAnswerByQId(qid);
  }

  private User getUserByIdAnswer(int answerId) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    UserWS.UserWS port = service_2.getUserWSPort();
    return port.getUserByIdAnswer(answerId);
  }

  private User getUserByIdQuestion(int qid) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    UserWS.UserWS port = service_2.getUserWSPort();
    return port.getUserByIdQuestion(qid);
  }

    private int getUserByToken(java.lang.String token, java.lang.String urlString) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        UserWS.UserWS port = service_2.getUserWSPort();
        return port.getUserByToken(token, urlString);
    }    
}
