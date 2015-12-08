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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.Comment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author angelynz95
 */
public class CommentController extends HttpServlet {

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
    
    int idQuestion = Integer.parseInt(request.getParameter("qid"));
    String token = request.getParameter("token");
    String content = request.getParameter("content");
    String userAgent = request.getHeader("user-agent");
    
    Comment comment = new Comment();
    
    // Menambah komentar baru
    boolean commentAdded = comment.addComment(idQuestion, content, token, userAgent);
    // Membuat array list yang menampung semua komentar pada pertanyaan dengan id idQuestion
    ArrayList<Comment> comments = comment.getComments(idQuestion);
    
    try (PrintWriter out = response.getWriter()) {
      // Mengubah array comments ke bentuk JSON
      JSONArray arr = new JSONArray();
      for (int i = 0; i < comments.size(); i++) {
        JSONObject temp = new JSONObject();
        try {
          temp.put("idComment", comments.get(i).getIdComment());
          temp.put("idQuestion", comments.get(i).getIdQuestion());
          temp.put("idUser", comments.get(i).getIdUser());
          temp.put("content", comments.get(i).getContent());
          temp.put("datetime", comments.get(i).getDatetime());
        } catch (JSONException ex) {
          Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        arr.put(temp);
      }
      
      JSONObject obj = new JSONObject();
      try {
        obj.put("commentAdded", commentAdded);
        obj.put("comments", arr);
      } catch (JSONException ex) {
        Logger.getLogger(CommentController.class.getName()).log(Level.SEVERE, null, ex);
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
