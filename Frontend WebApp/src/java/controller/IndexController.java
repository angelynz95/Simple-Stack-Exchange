package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
public class IndexController extends HttpServlet {

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
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    // Memperoleh pertanyaan yang dicari
    java.util.List<QuestionWS.Question> questions;
    if (request.getParameter("keyword") != null) {
      String keyword = request.getParameter("keyword");
      questions = getQuestionsSearched(keyword);
      request.setAttribute("questions", questions);
      
    } else {
      // Memperoleh list semua pertanyaan
      questions = getQuestions();
      request.setAttribute("questions", questions);
    }
    // Memperoleh list jumlah jawaban semua pertanyaan
    int countAnswers[] = new int [questions.size()];
    for (int i = 0; i < questions.size(); i++) {
        countAnswers[i] = getCountAnswerByQId(questions.get(i).getIdQuestion());
    }
    request.setAttribute("countAnswers",countAnswers);
    
    // Memperoleh list user yang menanyakan pertanyaan
    User users[] = new User [questions.size()];
    for (int i = 0; i < questions.size(); i++) {
        users[i] = getUserByIdQuestion(questions.get(i).getIdQuestion());
    }
    request.setAttribute("users", users);
    
    // Memperoleh user id berdasarkan token
    if ((request.getParameter("token") != "not-valid") && (request.getParameter("token") != null)) {
        int userId = getUserByToken(request.getParameter("token"), "http://localhost:8082/Identity_Service/TokenController");
        if (userId > 0) {
            request.setAttribute("userId", userId);
            request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            response.sendRedirect("log-in.jsp");
        }
    } else {
        request.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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

    private java.util.List<QuestionWS.Question> getQuestions() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        QuestionWS.QuestionWS port = service.getQuestionWSPort();
        return port.getQuestions();
    }

    private int getCountAnswerByQId(int qid) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        AnswerWS.AnswerWS port = service_1.getAnswerWSPort();
        return port.getCountAnswerByQId(qid);
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

  private java.util.List<QuestionWS.Question> getQuestionsSearched(java.lang.String keyword) {
    // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
    // If the calling of port operations may lead to race condition some synchronization is required.
    QuestionWS.QuestionWS port = service.getQuestionWSPort();
    return port.getQuestionsSearched(keyword);
  }
}
