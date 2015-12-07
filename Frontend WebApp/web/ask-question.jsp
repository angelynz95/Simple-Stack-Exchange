<!-- Tugas 3 IF3110 Pengembangan Aplikasi Berbasis Web
Membuat website tanya jawab seperti Stack Exchange dengan REST dan SOAP dan arsitektur berorientasi servis.
Author: 
- Irene Wiliudarsan (13513002)
- Angela Lynn       (13513002)
- Devina Ekawati    (13513002) -->
<!-- File: ask-question.jsp  -->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Ask Question</title>
    <meta charset="utf-8"/>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
  </head>
  <body>
    <%
      String questionTopic = (String) request.getAttribute("question-topic");
      String questionContent = (String) request.getAttribute("question-content");
      if (questionTopic == null) {
        questionTopic = "";
      }
      if (questionContent == null) {
        questionContent = "";
      }
    %>
    <% if ((request.getParameter("token") != null) || (request.getParameter("token") != "not-valid")) { %>
      <!--User Name-->
      <div class="user-identity">
        Hi <span class="user-name blue"></span>
      </div>
      <ul class="nav-bar">
        <li><a href="IndexController">Logout</a></li>
      </ul>
    <% } %>
    <!-- Title -->
    <div class="title">
      <a href="IndexController?token=<%= request.getParameter("token") %>">
        StackExchange
      </a>
    </div>

    <!-- Question Form -->
    <div class="content">
      <div class="subtitle">
        What's your question?
      </div>
      <% if (request.getParameter("qid") == null) { %>
      <form class="right" id="question-form" action="AskController?token=<%= request.getParameter("token") %>" method="post" onsubmit="return questionFormValidation()">
      <% } else { %>
      <form class="right" id="question-form" action="EditController?token=<%= request.getParameter("token") %>&qid=<%= request.getParameter("qid") %>" method="post" onsubmit="return answerFormValidation()">
      <% } %>
        <input class="full-length" id="question-name" name="question-name" type="text" placeholder="Name">
        <input class="full-length" id="question-email" name="question-email" type="email" placeholder="Email">
        <input class="full-length" id="question-topic" name="question-topic" type="text" placeholder="Question Topic" value='<%= questionTopic %>'>
        <textarea class="full-length" id="question-content" name="question-content" placeholder="Content" rows="10" cols="50"><%= questionContent %></textarea>
        <input class="button" name="question-submit" type="submit" value="Post">
      </form>
    </div>

    <!-- JavaScript -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <script src="js/script.js"></script>
    <script src="js/script-user-identity.js"></script>
  </body>
</html>
