/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionModel;

import UserModel.User;
import UserModel.UserWS;
import database.Database;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.*;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
@WebService(serviceName = "QuestionWS")
public class QuestionWS {
  // Atribut
  private Database database = new Database();

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getQuestions")
  @WebResult(name="Question")
  public ArrayList<Question> getQuestions() {
    //TODO write your implementation code here:
    ArrayList<Question> questions = new ArrayList<Question>();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Menjalankan query
      String query = "SELECT * FROM question ORDER BY datetime DESC";
      PreparedStatement databaseStatement = connection.prepareStatement(query);
      ResultSet result = databaseStatement.executeQuery();
      
      // Mengambil semua data hasil eksekusi query
      while (result.next()) {
        questions.add(new Question( result.getInt("id_question"),
                                    result.getString("topic"),
                                    result.getString("content"),
                                    result.getString("datetime"),
                                    result.getInt("id_user"),
                                    result.getInt("vote_num")));
      }
      
      result.close();
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return questions;
  }
  
  @WebMethod(operationName = "addQuestion")
  public boolean addQuestion(@WebParam(name = "topic") String topic, @WebParam(name = "content") String content, @WebParam(name = "token") String token) {
    //TODO write your implementation code here:
    boolean questionAdded = false;
    int userId = 0;
    UserWS user = new UserWS();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Request User ke Identity Service dengan token
      userId = user.getUserIdByToken(token, "http://localhost:8082/Identity_Service/TokenController");
      
      if (userId > 0) {
        // Menjalankan query
        String query = "INSERT INTO question (topic, content, id_user) VALUES (?, ?, ?)";
        PreparedStatement databaseStatement = connection.prepareStatement(query);
        databaseStatement.setString(1, topic);
        databaseStatement.setString(2, content);
        databaseStatement.setInt(3, userId);
        databaseStatement.executeUpdate();

        statement.close();
        questionAdded = true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return questionAdded;
  }
  
  @WebMethod(operationName = "editQuestion")
  public boolean editQuestion(@WebParam(name = "question-id") int idQuestion, @WebParam(name = "topic") String topic, @WebParam(name = "content") String content, @WebParam(name = "token") String token) {
    //TODO write your implementation code here:
    boolean questionEdited = false;
    int userId = 0;
    UserWS user = new UserWS();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Memvalidasi token
      userId = user.getUserIdByToken(token, "http://localhost:8082/Identity_Service/TokenController");
      
      if (userId > 0) {
        // Menjalankan query
        String query = "UPDATE question SET topic = ?, content = ? WHERE id_question = ?";
        PreparedStatement databaseStatement = connection.prepareStatement(query);
        databaseStatement.setString(1, topic);
        databaseStatement.setString(2, content);
        databaseStatement.setInt(3, idQuestion);
        databaseStatement.executeUpdate();

        statement.close();
        questionEdited = true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return questionEdited;
  }
  
  @WebMethod(operationName = "deleteQuestion")
  public boolean deleteQuestion(@WebParam(name = "id_question") int idQuestion, @WebParam(name = "token") String token) {
    //TODO write your implementation code here:
    boolean questionDeleted = false;
    int userId = 0;
    UserWS user = new UserWS();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Memvalidasi token
      userId = user.getUserIdByToken(token, "http://localhost:8082/Identity_Service/TokenController");
      
      if (userId > 0) {
        // Menjalankan query
        String query1 = "DELETE FROM question WHERE id_question = ?";
        String query2 = "DELETE FROM answer WHERE id_question = ?";
        String query3 = "DELETE FROM comment WHERE id_question = ?";
        String query4 = "DELETE FROM vote WHERE id_question = ?";
        
        PreparedStatement databaseStatement1 = connection.prepareStatement(query1);
        PreparedStatement databaseStatement2 = connection.prepareStatement(query2);
        PreparedStatement databaseStatement3 = connection.prepareStatement(query3);
        PreparedStatement databaseStatement4 = connection.prepareStatement(query4);
        
        databaseStatement1.setInt(1, idQuestion);
        databaseStatement2.setInt(1, idQuestion);
        databaseStatement3.setInt(1, idQuestion);
        databaseStatement4.setInt(1, idQuestion);
        
        databaseStatement1.executeUpdate();
        databaseStatement2.executeUpdate();
        databaseStatement3.executeUpdate();
        databaseStatement4.executeUpdate();

        statement.close();
        questionDeleted = true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return questionDeleted;
  }
  
  @WebMethod(operationName = "getQuestion")
  public ArrayList<Question> getQuestion(@WebParam(name = "id_question") int idQuestion) {
    //TODO write your implementation code here:
    ArrayList<Question> question = new ArrayList<Question>();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Menjalankan query
      String query = "SELECT * FROM question WHERE id_question = ?";
      PreparedStatement databaseStatement = connection.prepareStatement(query);
      databaseStatement.setInt(1, idQuestion);
      ResultSet result = databaseStatement.executeQuery();
      
      // Mengambil data hasil eksekusi query
      while (result.next()) {
        question.add(new Question( result.getInt("id_question"),
                                    result.getString("topic"),
                                    result.getString("content"),
                                    result.getString("datetime"),
                                    result.getInt("id_user"),
                                    result.getInt("vote_num")));
      }
      
      result.close();
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return question;
  }
  
  
  @WebMethod(operationName = "getQuestionsSearched")
  public ArrayList<Question> getQuestionsSearched(@WebParam(name = "keyword") String keyword) {
    ArrayList<Question> question = new ArrayList<Question>();
    
    try {
      // Connect database
      Connection connection = database.connectDatabase();
      Statement statement = connection.createStatement();
      
      // Menjalankan query
      String query = "SELECT * FROM question WHERE (topic LIKE '%" + keyword + "%' OR content LIKE '%" + keyword + "%')";
      ResultSet result = statement.executeQuery(query);
      
      // Mengambil data hasil eksekusi query
      while (result.next()) {
        question.add(new Question( result.getInt("id_question"),
                                    result.getString("topic"),
                                    result.getString("content"),
                                    result.getString("datetime"),
                                    result.getInt("id_user"),
                                    result.getInt("vote_num")));
      }
      
      result.close();
      statement.close();
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return question;
  }
  
  @WebMethod(operationName = "voteQuestion")
  public int voteQuestion(@WebParam(name = "qid") int qid, @WebParam(name = "token") String token, @WebParam(name = "vote") String vote) {
    int isVoted = -1;
    int uid = 0;
    UserWS user = new UserWS();
    
    // Memvalidasi token
    uid = user.getUserIdByToken(token, "http://localhost:8082/Identity_Service/TokenController");
    
    if (uid > 0) {
      if (!hasUserVoteQuestion(qid, uid)) {
        try {
          Connection conn = database.connectDatabase();
          Statement stmt = conn.createStatement();
          String sql;
          int voteNum=0;
          sql = "SELECT vote_num FROM question WHERE id_question = ?";
          PreparedStatement dbStatement1 = conn.prepareStatement(sql);
          dbStatement1.setInt(1, qid);
          ResultSet rs = dbStatement1.executeQuery();

          while (rs.next()) {
            voteNum = rs.getInt("vote_num");
          }

          if ("up".equals(vote)) {
            voteNum++;
          } else {
            voteNum--;
          }

          // Mengubah vote number
          sql = "UPDATE question SET vote_num = ? WHERE id_question = ?";
          PreparedStatement dbStatement2 = conn.prepareStatement(sql);
          dbStatement2.setInt(1, voteNum);
          dbStatement2.setInt(2, qid);
          dbStatement2.executeUpdate();


          sql = "INSERT INTO vote (id_question, id_user) VALUES (?, ?)";
          PreparedStatement dbStatement3 = conn.prepareStatement(sql);
          dbStatement3.setInt(1, qid);
          dbStatement3.setInt(2, uid);
          dbStatement3.executeUpdate();

          isVoted = 1;
          rs.close();
          stmt.close();

        } catch (SQLException ex) {
          Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
      } else {
        isVoted = 0;
      }
    }
    return isVoted;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "hasUserVoteQuestion")
  public boolean hasUserVoteQuestion(@WebParam(name = "qid") int qid, @WebParam(name = "uid") int uid) {
    //TODO write your implementation code here:
    boolean hasVote = false;
    int count = 0;
    try {
      Connection conn = database.connectDatabase();
      Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT count(*) FROM vote WHERE id_question = ? AND id_user = ?";
      PreparedStatement dbStatement = conn.prepareStatement(sql);
      dbStatement.setInt(1, qid);
      dbStatement.setInt(2, uid);
      ResultSet rs = dbStatement.executeQuery();
      
      while (rs.next()) {
        count = rs.getInt("count(*)");
      }
      
      if (count > 0) {
        hasVote = true;
      }
      
      rs.close();
      stmt.close();
      
    } catch (SQLException ex) {
      Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return hasVote;
  }
}
