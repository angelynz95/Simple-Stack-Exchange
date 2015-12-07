/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;

public class Database extends HttpServlet {
  // JDBC driver name and database URL
  static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
  static final String DB_URL="jdbc:mysql://localhost:3306/stack_exchange?zeroDateTimeBehavior=convertToNull";

  //  DB credentials
  static final String USER = "root";
  static final String PASS = "";
  
  // DB connectoion
  private Connection conn;
  
  public Database() {
    
  }
  
  public Connection connectDatabase() throws SQLException {
    try {
      // Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");
      // Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch(SQLException se){
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
    }
    return conn;
  }
}
