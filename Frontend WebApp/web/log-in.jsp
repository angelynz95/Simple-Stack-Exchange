<!-- Tugas 2 IF3110 Pengembangan Aplikasi Berbasis Web
Membuat website tanya jawab seperti Stack Exchange dengan REST dan SOAP dan arsitektur berorientasi servis.
Author: 
- Irene Wiliudarsan (13513002)
- Angela Lynn       (13513002)
- Devina Ekawati    (13513002) -->
<!-- File: log-in.jsp -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Log in</title>
    <meta charset="utf-8"/>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
  </head>
  <body>
    <!-- Title -->
    <div class="title">
      <a href="IndexController">
        StackExchange
      </a>
    </div>

    <!-- Question Form -->
    <div class="content">
      <div class="subtitle">
        Log in
      </div>
      <form class="right" id="log-in-form">
        <input class="full-length" id="log-in-email" name="log-in-email" type="email" placeholder="Email">
        <input class="full-length" id="log-in-password" name="log-in-password" type="password" placeholder="Password">
        <input class="button" id="log-in-submit" name="log-in-submit" type="submit" value="Log in">
      </form>
        <div id="result"></div>
    </div>

    <!-- JavaScript -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <script src="js/script.js"></script>
    <script src="js/script-log-in.js"></script>
  </body>
</html>
