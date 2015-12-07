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
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kelas untuk operasi voting pada question and answer
 */
public class Vote {
  // Atribut
  private Connection connection;
  
  // Konstruktor
  public Vote() {
    
  }
  
  public boolean hasUserVoteQuestion(int idQuestion, int idUser) {
    boolean hasVote = false;
    int count = 0;
    
    try {
      // Melakukan koneksi ke database
      Database database = new Database();
      connection = database.connectDatabase();
      Statement stmt = connection.createStatement();
      String sql;
      
      sql = "SELECT count(*) FROM vote WHERE id_question = ? AND id_user = ?";
      PreparedStatement dbStatement = connection.prepareStatement(sql);
      
      dbStatement.setInt(1, idQuestion);
      dbStatement.setInt(2, idUser);
      
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
      Logger.getLogger(Vote.class.getName()).log(Level.SEVERE, null, ex);
    }
    return hasVote;
  }
  
  public int voteQuestion(int idQuestion, String token, String userAgent, String voteType) {
    int isVoted = -1;
    User user = new User();
    
    // Request User ke Identity Service
    String urlString = "http://localhost:8082/Identity_Service/TokenController";
    user = user.getUserFromIS(token, userAgent, urlString);
    int userId = user.getIdUser();
    
    
    try {
      Database database = new Database();
      Connection conn = database.connectDatabase();
      Statement stmt = conn.createStatement();
      String sql;
      int voteNum=0;
      sql = "SELECT vote_num FROM question WHERE id_question = ?";
      PreparedStatement dbStatement1 = conn.prepareStatement(sql);
      dbStatement1.setInt(1, idQuestion);
      ResultSet rs = dbStatement1.executeQuery();

      while (rs.next()) {
        voteNum = rs.getInt("vote_num");
      }
      
      if (userId > 0) {
        if (!hasUserVoteQuestion(idQuestion, userId)) {
          if ("up".equals(voteType)) {
            voteNum++;
          } else {
            voteNum--;
          }

          // Mengubah vote number
          sql = "UPDATE question SET vote_num = ? WHERE id_question = ?";
          PreparedStatement dbStatement2 = conn.prepareStatement(sql);
          dbStatement2.setInt(1, voteNum);
          dbStatement2.setInt(2, idQuestion);
          dbStatement2.executeUpdate();

          sql = "INSERT INTO vote (id_question, id_user) VALUES (?, ?)";
          PreparedStatement dbStatement3 = conn.prepareStatement(sql);
          dbStatement3.setInt(1, idQuestion);
          dbStatement3.setInt(2, userId);
          dbStatement3.executeUpdate();

          isVoted = 1;
          rs.close();
          stmt.close();
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(Vote.class.getName()).log(Level.SEVERE, null, ex);
    }
    return isVoted;
  }
  
  public int voteAnswer(int idAnswer, String token, String userAgent, String voteType) {
    User user = new User();
    int voteNum=0;
    // Request User ke Identity Service
    String urlString = "http://localhost:8082/Identity_Service/TokenController";
    user = user.getUserFromIS(token, userAgent, urlString);
    int userId = user.getIdUser();
    
    
      try {
        Database database = new Database();
        Connection conn = database.connectDatabase();
        Statement stmt = conn.createStatement();
        String sql;
        
        sql = "SELECT vote_num FROM answer WHERE id_answer = ?";
        PreparedStatement dbStatement1 = conn.prepareStatement(sql);
        dbStatement1.setInt(1, idAnswer);
        ResultSet rs = dbStatement1.executeQuery();

        while (rs.next()) {
          voteNum = rs.getInt("vote_num");
        }
        if (userId > 0) {
          if ("up".equals(voteType)) {
            voteNum++;
          } else {
            voteNum--;
          }

          // Mengubah vote number
          sql = "UPDATE answer SET vote_num = ? WHERE id_answer = ?";
          PreparedStatement dbStatement2 = conn.prepareStatement(sql);
          dbStatement2.setInt(1, voteNum);
          dbStatement2.setInt(2, idAnswer);
          dbStatement2.executeUpdate();

          rs.close();
          stmt.close();
        }
      } catch (SQLException ex) {
        Logger.getLogger(Vote.class.getName()).log(Level.SEVERE, null, ex);
      }
    
    return voteNum;
  }
}
