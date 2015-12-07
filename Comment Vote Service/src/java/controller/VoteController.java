/*
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import main.Vote;

/**
 *
 * Servlet untuk koneksi dengan AngularJS untuk operasi vote question dan answer
 */
public class VoteController extends HttpServlet {

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
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With");
    response.setHeader("Access-Control-Max-Age", "86400");
    
    String voteType = request.getParameter("voteType");
    String token = request.getParameter("token");
    String userAgent = request.getHeader("User-Agent");
    Vote vote = new Vote();
    
    
    try (PrintWriter out = response.getWriter()) {
      JSONObject obj = new JSONObject();
      if ("answer-up".equals(voteType)) {
        int idAnswer = Integer.parseInt(request.getParameter("id"));
        int voteNum = vote.voteAnswer(idAnswer, token, userAgent, "up");
        
        obj.put("voteNum", voteNum);
        
      } else if ("answer-down".equals(voteType)) {
        int idAnswer = Integer.parseInt(request.getParameter("id"));
        int voteNum = vote.voteAnswer(idAnswer, token, userAgent, "down");
        
        obj.put("voteNum", voteNum);
        
      } else if ("question-up".equals(voteType)) {
        int idQuestion = Integer.parseInt(request.getParameter("id"));
        int voteSuccess = vote.voteQuestion(idQuestion, token, userAgent, "up");
        
        obj.put("vote_success", voteSuccess);
        
      } else if ("question-down".equals(voteType)) {
        int idQuestion = Integer.parseInt(request.getParameter("id"));
        int voteSuccess = vote.voteQuestion(idQuestion, token, userAgent, "down");
        
        obj.put("vote_success", voteSuccess);
        
      }
      
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
