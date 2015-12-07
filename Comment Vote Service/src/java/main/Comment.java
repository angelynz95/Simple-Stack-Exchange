/*
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
package main;

import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Kelas untuk menangani comment pada pertanyaan
 */
public class Comment {
  // Atribut
  Database database;
  Connection connection;
  int idComment;
  int idQuestion;
  int idUser;
  String content;
  String datetime;
  
  // Konstruktor
  public Comment() {
    idComment = 0;
    idQuestion = 0;
    idUser = 0;
    
    // Connect database
    database = new Database();
    try {
      connection = database.connectDatabase();
    } catch (SQLException ex) {
      Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public Comment(int idComment, int idQuestion, int idUser, String content, String datetime) {
    this.idComment = idComment;
    this.idQuestion = idQuestion;
    this.idUser = idUser;
    this.content = content;
    this.datetime = datetime;
  }
  
  // Method
  public boolean addComment(int _idQuestion, String _content, String token, String userAgent) {
    boolean commentAdded = false;
    String urlString = "http://localhost:8082/Identity_Service/TokenController";
    User user = new User();
    
    try {
      Statement statement = connection.createStatement();
      // Request User ke Identity Service
      user = user.getUserFromIS(token, userAgent, urlString);

      if (user.getIdUser() > 0) {
        // Menjalankan query
        String query = "INSERT INTO comment (id_question, id_user, content) VALUES (?, ?, ?)";
        PreparedStatement databaseStatement;
        databaseStatement = connection.prepareStatement(query);
        databaseStatement.setInt(1, _idQuestion);
        databaseStatement.setInt(2, user.getIdUser());
        databaseStatement.setString(3, _content);
        databaseStatement.executeUpdate();
        
        statement.close();
        commentAdded = true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return commentAdded;
  }
  
  public ArrayList<Comment> getComments(int qid) {
    ArrayList<Comment> comments = new ArrayList<Comment>();
    
    try {
      Statement statement = connection.createStatement();
      // Menjalankan query
      String query = "SELECT * FROM comment WHERE id_question = ?";
      PreparedStatement databaseStatement = connection.prepareStatement(query);
      databaseStatement.setInt(1, qid);
      ResultSet result = databaseStatement.executeQuery();
      
      // Mengambil data hasil eksekusi query
      while (result.next()) {
        comments.add(new Comment(   result.getInt("id_comment"),
                                    result.getInt("id_question"),
                                    result.getInt("id_user"),
                                    result.getString("content"),
                                    result.getString("datetime")));
      }
      
      result.close();
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(Comment.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return comments;
  }
}
