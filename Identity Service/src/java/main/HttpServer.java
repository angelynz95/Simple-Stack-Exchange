package main;

import eu.bitwalker.useragentutils.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.*;
import org.json.simple.JSONObject;

// Kelas untuk menguji program pada package main
public class HttpServer{
 
  public static void main (String[] args) {
    // Test user agent parser
//    UserAgent agent = UserAgent.parseUserAgentString("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
//    Browser browser = agent.getBrowser();
//    Version version = agent.getBrowserVersion();
//    OperatingSystem os = agent.getOperatingSystem();
//    String browserk = browser.toString();
//    System.out.println(browserk + "#" + version + "#" + os);
    // Token
    String userAgent = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";
    String ipAddress = "0:0:0:0:0:0:0:1";
//    TokenExecutor executor = new TokenExecutor("irene@gmail.com", "test", userAgent, ipAddress);
//    String tokenSend = executor.getToken().getAccessToken().substring(0, 36);
    TokenExecutor executor = new TokenExecutor("8b8ffc17-44c5-3b8f-bb28-ab80aa2638de", userAgent, ipAddress);
//    c14f5617-f819-346c-82fa-6ab346c8d98a#Firefox 42#0:0:0:0:0:0:0:1
//    executor.closeConnection();
//    TokenExecutor executor = new TokenExecutor("61618416-7e4c-3861-8f86-f53c83f46c7f");
//      executor.closeConnection();
//    System.out.println(executor.getBrowserName(userAgent));
//    System.out.println(executor.getToken().getRandomString());
    
//    System.out.println(executor.getIdUser());
//    System.out.println(executor.getIsValid());
      System.out.println(executor.getIdUser());
      System.out.println("c14f5617-f819-346c-82fa-6ab346c8d98a#Firefox 42#0:0:0:0:0:0:0:1".substring(0, 36));
  }
 
}