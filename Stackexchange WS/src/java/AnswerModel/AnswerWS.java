/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnswerModel;

import UserModel.User;
import UserModel.UserWS;
import database.Database;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.*;

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
@WebService(serviceName = "AnswerWS")
public class AnswerWS {
  
  private Database db = new Database();
  /**
   * Web service operation
   */
  @WebMethod(operationName = "getAnswerByQId")
  @WebResult(name = "Answer")
  public ArrayList<Answer> getAnswerByQId(@WebParam(name = "qid") int qid) {
    ArrayList<Answer> answers = new ArrayList<Answer>();
    
    try {
      //TODO write your implementation code here:
      
      Connection conn = db.connectDatabase();
      Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM answer WHERE id_question = ?";
      PreparedStatement dbStatement = conn.prepareStatement(sql);
      dbStatement.setInt(1, qid);
      ResultSet rs = dbStatement.executeQuery();
            
      while (rs.next()) {
        answers.add(new Answer(rs.getInt("id_answer"),rs.getString("content"), rs.getString("datetime"), rs.getInt("id_user"), rs.getInt("id_question"), rs.getInt("vote_num"))); 
      }
      rs.close();
      stmt.close();
      
    } catch (SQLException ex) {
      Logger.getLogger(AnswerWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return answers;
  }
  
  @WebMethod(operationName = "addAnswer")
  public boolean addAnswer(@WebParam(name = "qid") int qid, @WebParam(name = "token") String token, @WebParam(name = "content") String content) {
    //TODO write your implementation code here:
    boolean isAdded = false;
    int userId = 0;
    UserWS u = new UserWS();
    
    try {
      Connection conn = db.connectDatabase();
      
      // Request User ke Identity Service
      String urlString = "http://localhost:8082/Identity_Service/TokenController";
      userId = u.getUserIdByToken(token,urlString);
      if (userId > 0) {
        String sql;
        sql = "INSERT INTO answer (id_question, id_user, content) VALUES (?,?,?)";
        PreparedStatement dbStatement = conn.prepareStatement(sql);
        dbStatement.setInt(1, qid);
        dbStatement.setInt(2, userId);
        dbStatement.setString(3, content);
        dbStatement.executeUpdate();
        isAdded = true;
      }
    } catch (SQLException ex) {
      Logger.getLogger(AnswerWS.class.getName()).log(Level.SEVERE, null, ex);
    }  
    return isAdded;
  }

  /**
   * Web service operation
   */
  @WebMethod(operationName = "getCountAnswerByQId")
  public int getCountAnswerByQId(@WebParam(name = "qid") int qid) {
    int count = 0;
    try {
      
      Connection conn = db.connectDatabase();
      Statement stmt = conn.createStatement();
      String sql;
      sql = "SELECT count(*) FROM answer WHERE id_question = ?";
      PreparedStatement dbStatement = conn.prepareStatement(sql);
      dbStatement.setInt(1, qid);
      ResultSet rs = dbStatement.executeQuery();
      
      while (rs.next()) {
        count = rs.getInt("count(*)");
      }
      
      rs.close();
      stmt.close();
      
    } catch (SQLException ex) {
      Logger.getLogger(AnswerWS.class.getName()).log(Level.SEVERE, null, ex);
    }
    return count;
  }

  @WebMethod(operationName = "voteAnswer")
  public boolean voteAnswer(@WebParam(name = "aid") int aid, @WebParam(name = "vote") String vote, @WebParam(name = "token") String token) {
    boolean isVoted = false;
    int userId = 0;
    UserWS u = new UserWS();
    // Request User ke Identity Service
    String urlString = "http://localhost:8082/Identity_Service/TokenController";
    userId = u.getUserIdByToken(token,urlString);
    if (userId > 0) {
      try {
        Connection conn = db.connectDatabase();
        Statement stmt = conn.createStatement();
        String sql;
        int voteNum=0;
        sql = "SELECT vote_num FROM answer WHERE id_answer = ?";
        PreparedStatement dbStatement1 = conn.prepareStatement(sql);
        dbStatement1.setInt(1, aid);
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
        sql = "UPDATE answer SET vote_num = ? WHERE id_answer = ?";
        PreparedStatement dbStatement2 = conn.prepareStatement(sql);
        dbStatement2.setInt(1, voteNum);
        dbStatement2.setInt(2, aid);
        dbStatement2.executeUpdate();
        isVoted = true;

        rs.close();
        stmt.close();

      } catch (SQLException ex) {
        Logger.getLogger(AnswerWS.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return isVoted;
  }
}
