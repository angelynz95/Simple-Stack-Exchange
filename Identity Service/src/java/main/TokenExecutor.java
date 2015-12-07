/*
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework.
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
package main;

import database.Database;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Kelas untuk melakukan validasi terhadap token pada basis data.
 */
public class TokenExecutor {
  // Atribut
  private Token token;
  private int idUser;
  private boolean isValid;
  private Connection connection;
  
  // Konstruktor
  // Konstruktor pada saat pengguna ingin melakukan log in
  public TokenExecutor(String email, String password, String userAgent, String ipAddress) {
    // Periksa apakah email dan password ada di basis data
    try {
      // Connect database
      Database database = new Database();
      connection = database.connectDatabase();
      getIdUser(email, password);
      if (idUser != -999999) {
        // Email dan password pengguna benar
        String browserName = getBrowserName(userAgent);
        token = new Token(email, password, browserName, ipAddress);
        isValid = true;
        LogIn();
      } else {
        // Kirim pesan error
        token = new Token();
        isValid = false;
      }
      connection.close();
    } catch (SQLException ex) {
      // Kirim pesan error
      token = new Token();
      idUser = -999999;
      isValid = false;
      System.out.println(ex.getMessage());
    }
  }
  
  // Konstruktor untuk validasi pada saat pengguna ingin melakukan suatu operasi pada web
  public TokenExecutor(String accessToken) {
    token = new Token();
    token.setAccessToken(accessToken);
    try {
      // Connect database
      Database database = new Database();
      connection = database.connectDatabase();
      checkTokenValid();
    } catch (SQLException ex) {
      // Kirim pesan error
      token = new Token();
      idUser = -999999;
      isValid = false;
      System.out.println(ex.getMessage());
    }
  }
  
  // Getter
  public Token getToken() {
    return token;
  }
  public int getIdUser() {
    return idUser;
  }
  public boolean getIsValid() {
    return isValid;
  }
  
  // Method
  // Menyimpan token dengan ID user yang telah didapatkan dari basis data
  private void LogIn() throws SQLException {
    // Menjalankan query
    try (Statement statement = connection.createStatement()) {
      // Menjalankan query
      String query = "UPDATE user SET token = '" + token.getAccessToken() + "', lifetime = '" + token.getLifetime() + "' WHERE id_user = " + idUser;
      statement.executeUpdate(query);
      statement.close();
    }
  }
  
  private void getIdUser(String email, String password) throws SQLException {
    // Menjalankan query
    String query = "SELECT id_user FROM user WHERE email = ? AND password = ?";
    PreparedStatement dbStatement = connection.prepareStatement(query);
    dbStatement.setString(1, email);
    dbStatement.setString(2, password);
    ResultSet result = dbStatement.executeQuery();
    if (result.next()) {
      // Email dan password sesuai
      idUser = result.getInt("id_user");
    } else {
      idUser = -999999;
    }
    result.close();
  }
  
  // Memeriksa apakah sebuah token terdapat di basis data dan
  // memiliki lifetime yang masih berlaku
  private void checkTokenValid() throws SQLException {
    try (Statement statement = connection.createStatement()) {
      // Menjalankan query
      String query = "SELECT id_user, lifetime FROM user WHERE token = '" + token.getAccessToken() + "'";
      ResultSet result = statement.executeQuery(query);
      if (result.next()) {
        // Token ada, periksa lifetime
        idUser = result.getInt("id_user");
        Timestamp lifetime = result.getTimestamp("lifetime");
        if (lifetime.after(new Date())) {
          // Lifetime valid
          isValid = true;
        } else {
          idUser = -999999;
          isValid = false;
        }
      } else {
        // Token tidak ada pada basis data
        idUser = -999999;
        isValid = false;
      }
      result.close();
      statement.close();
    }
  }
  
  // Mendapatkan nama pengguna untuk ditampilkan pada setiap halaman web dari basis data
  public String getUserName() {
    String name = "not-valid";
    try (Statement statement = connection.createStatement()) {
      // Menjalankan query
      String query = "SELECT name FROM user WHERE token = '" + token.getAccessToken() + "'";
      ResultSet result = statement.executeQuery(query);
      if (result.next()) {
        // Nama user ditemukan
        name = result.getString("name");
      }
      result.close();
      statement.close();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return name;
  }
  
  // Mendapatkan nama dan email pengguna untuk ditampilkan pada form menjawab dan bertanya pada basis data
  public ArrayList<String> getUserIdentity() {
    ArrayList<String> identity = new ArrayList<String>();
    try (Statement statement = connection.createStatement()) {
      // Menjalankan query
      String query = "SELECT name, email FROM user WHERE token = '" + token.getAccessToken() + "'";
      ResultSet result = statement.executeQuery(query);
      if (result.next()) {
        // Nama user ditemukan
        identity.add(result.getString("name"));
        identity.add(result.getString("email"));
      }
      result.close();
      statement.close();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return identity;
  }
  
  // Melakukan parsing pada user agent untuk mendapatkan nama browser
  public String getBrowserName(String userAgent) {
    UserAgent agent = UserAgent.parseUserAgentString(userAgent);
    Browser browser = agent.getBrowser();
    return browser.getName();
  }
  
  // Menutup koneksi pada basis data
  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
