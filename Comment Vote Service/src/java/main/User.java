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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * Kelas untuk menyimpan dan memperoleh informasi mengenai user
 */
public class User {
  // Atribut
  private int idUser;
  private String name;
  private String email;
  
  // Konstruktor
  public User() {
    idUser = 0;
  }
  
  public User(int idUser, String name, String email) {
    this.idUser = idUser;
    this.name = name;
    this.email = email;
  }
  
  // Getter
  public int getIdUser() {
    return idUser;
  }
  
  public String getName() {
    return name;
  }
  
  public String getEmail() {
    return email;
  }
  
  // Setter
  public void setUser(int idUser, String name, String email) {
    this.idUser = idUser;
    this.name = name;
    this.email = email;
  }
  
  // Method
  public User getUserFromIS(String token, String userAgent, String urlString) {
    User user = new User();
    
    try {
      URL url = new URL(urlString);
      HttpURLConnection connection;
      
      try {
        // Mengubah token ke dalam bentuk JSON
        JSONObject request = new JSONObject();
        try {
          request.put("token", token);
          request.put("userAgent", userAgent);
        } catch (JSONException ex) {
          Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Setting koneksi
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(request.toString().getBytes(StandardCharsets.UTF_8).length));
        connection.setUseCaches(false);

        // Mengirim token, ip address, dan user agent ke Identity Service
        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
        writer.write(request.toString().getBytes(StandardCharsets.UTF_8));
        
        // Menerima response dari Identity Service
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        String response = "";
        while ((inputLine = in.readLine()) != null) {
          response += inputLine;
        }
        try {
          JSONObject tokenResponse = new JSONObject(response);
          int userId = tokenResponse.getInt("id_user");
          String userName = tokenResponse.getString("name");
          String userEmail = tokenResponse.getString("email");
          
          user.setUser(userId, userName, userEmail);
        } catch (JSONException ex) {
          Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
      } catch (IOException ex) {
        Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
      }
    } catch (MalformedURLException ex) {
      Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return user;
  }
}
