<%@page import="java.util.ArrayList"%>
<!-- Tugas 2 IF3110 Pengembangan Aplikasi Berbasis Web
Membuat website tanya jawab seperti Stack Exchange dengan REST dan SOAP dan arsitektur berorientasi servis.
Author: 
- Irene Wiliudarsan (13513002)
- Angela Lynn       (13513002)
- Devina Ekawati    (13513002) -->
<!-- File: question-detail.jsp  -->

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question Detail</title>
    <meta charset="utf-8"/>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="css/main.css"/>

  </head>
  <body ng-app="stackExchange" ng-controller="VoteController">
    <% if ((request.getParameter("token") != null) && (request.getParameter("token") != "not-valid")) { %>
      <!--User Name-->
      <div class="user-identity">
        Hi <span class="user-name blue"></span>
      </div>
      <ul class="nav-bar">
        <li><a href="QuestionDetailController">Logout</a></li>
      </ul>
    <% } %>
    <!-- Title -->
    <div class="title">
      <% if ((request.getParameter("token") == null) || (request.getParameter("token") == "not-valid")) { %>
        <a href="IndexController">
      <% } else { %>
        <a href="IndexController?token=<%= request.getParameter("token") %>">
      <% } %>
        StackExchange
      </a>
    </div>

    <div class="content">
      <!-- Question -->
      <div class="stacked" >
        <div class="subtitle">
          <%
            List<QuestionWS.Question> questions = new ArrayList<QuestionWS.Question>();
            if (request.getAttribute("questions") != null) {
              questions = (ArrayList<QuestionWS.Question>)request.getAttribute("questions");
              for (int i = 0; i < questions.size(); i++) {
          %>
          <%= questions.get(i).getTopic() %>
          
        </div>
        <!-- Questions Content -->
        <div class="same-height-row">
          <div class="vote-number">
              <div ng-click="vote(<%= questions.get(i).getIdQuestion() %>, 'question-up');"><img class="small-icon" src="img/up.png" name="question-up" /><br></div>
            <div class="big-number" ng-init="voteNum=<%= questions.get(i).getVoteNum() %>" ng-model="voteNum">
                {{ voteNum }}
            </div>
              <div ng-click="vote(<%= questions.get(i).getIdQuestion() %>, 'question-up');"><img class="small-icon" src="img/down.png" name="question-down"/><br></div>
          </div>
          <div class="right-position">
            <div class="answer-question-detail">
              <%= questions.get(i).getContent() %>
            </div>
            <!-- Asked by -->
            <%
            UserWS.User u1 = new UserWS.User();
            if (request.getAttribute("u1") != null) {
              u1 = (UserWS.User)request.getAttribute("u1");
            %>
            asked by
            <%= u1.getName() %>
            at
            <%= questions.get(i).getDatetime() %>
            <%
                if (request.getAttribute("userId") != null) {
                    if (u1.getIdUser() == (Integer)request.getAttribute("userId")) {
            %>
            |
            <a class="yellow" href="EditController?token=<%= request.getParameter("token") %>&qid=<%= questions.get(i).getIdQuestion() %>&name=edit">
              edit
            </a>
            |
            <a class="red" href="DeleteController?token=<%= request.getParameter("token") %>&qid=<%= questions.get(i).getIdQuestion() %>" " onclick="return confirm('Do you want to delete this post?')">
              delete
              <%
                        }
                    }
                %>
            </a>
            <%
            }
            %>
            <!-- Comments List -->
            <div class="border-top">
              <div class="comment stacked border-bottom">
                That destroyed any confidence investors had in the stock market, which in those days was perceived to be the economy. Many had invested their life savings and were entirely wiped out.

No wonder retailers wanted to make the name "Black Friday" mean something positive. And, to them, the Friday after Thanksgiving is a very profitable day. To compensate, they decided to follow the adage, "If you can't beat 'em, join 'em."
                <div class="right">
                  commented by Pak Yudis at 2015-11-30 11:27:23.0
                </div>
              </div>
              <div class="comment stacked border-bottom">
                <div class="blue" id="add-comment">Add a comment</div>
                <!-- Comment Form -->
                <div id="comment-part">
                  <div id="comment-title">
                    What's your comment?
                  </div>
                  <form class="right" id="comment-form" action="" method="post" onsubmit="return answerFormValidation()">
                    <input class="full-length" id="comment-name" name="comment-name" type="text" placeholder="Name">
                    <input class="full-length" id="comment-email" name="comment-email" type="email" placeholder="Email">
                    <textarea class="full-length" id="comment-content" name="comment-content" placeholder="Comment" rows="2" cols="50" required></textarea>
                    <input class="button" id="comment-submit" name="comment-submit" type="submit" value="Post">
                  </form>
                </div>
              </div>
              
            </div>
          </div>
            <%
              }
            }
          %>
        </div>
      </div>
      
      <!-- Answers List -->
      <div class="stacked">
        <div class="subtitle">
            <%
                if (request.getAttribute("count") != null) {
                  int count = (Integer)request.getAttribute("count");
            %>
            <%= count %> Answer
            <%
                }
            %>
        </div>

        <!-- Answers -->
        <%
          List<AnswerWS.Answer> answers = new ArrayList<AnswerWS.Answer>();
          List<UserWS.User> u2 = new ArrayList<UserWS.User>();
          if ((request.getAttribute("answers") != null) && (request.getAttribute("u2") != null)) {
            answers = (ArrayList<AnswerWS.Answer>)request.getAttribute("answers");
            u2 = (ArrayList<UserWS.User>)request.getAttribute("u2");
            if (answers.size() > 0) {
              for (int i = 0; i < answers.size(); i++) {
        %>
        <div class="same-height-row border-bottom">
          <div class="vote-number">
              <div ng-click="vote(<%= answers.get(i).getIdAnswer() %>, 'answer-up');"><img class="small-icon" src="img/up.png" onclick=""/><br></div>
            <div class="big-number" id="answer-1" ng-init="voteNum=<%= answers.get(i).getVoteNum() %>" ng-model="voteNum">
                {{ voteNum }}
            </div>
              <div ng-click="vote(<%= answers.get(i).getIdAnswer() %>, 'answer-down');"><img class="small-icon" src="img/down.png" name="answer-up"/><br></div>
          </div>
          <!-- Answers Content -->
          <div class="right-position">
            <div class="answer-question-detail">
            
            <%= answers.get(i).getContent() %>
            </div>
            answered by
            <%= u2.get(i).getName() %>
            at
            <%= answers.get(i).getDatetime() %>
          </div>
        </div>
        <%      
            }
          } else { 
        %>
        <!-- If no answers: -->
        <div class="same-height-row border-bottom">
          Sorry, there are no answers available yet. Know someone who can answer?
        </div>
        <%
              }
            }
        %>
      </div>

      <!-- Answer Form -->
      <% if (request.getParameter("token") != null ) { %>
      <div class="stacked">
        <div id="answer-form-title">
          Your Answer
        </div>
        <form class="right" id="answer-form" name="answer-form" action="AddAnswerController?token=<%= request.getParameter("token") %>&qid=<%= request.getParameter("qid") %>" method="post" onsubmit="return answerFormValidation()">
          <input class="full-length" id="answer-name" name="answer-name" type="text" placeholder="Name">
          <input class="full-length" id="answer-email" name="answer-email" type="text" placeholder="Email">
          <textarea class="full-length" id="answer-content" name="answer-content" placeholder="Content" rows="10" cols="50"></textarea>
          <input class="button" name="answer-submit" type="submit" value="Post">
        </form>
      </div>
      <% } else { %>
      <a class="yellow" href="log-in.jsp">Login</a> or <a class="red" href="register.jsp">register</a> to answer this question
      <% } %>
    </div>

    <!-- JavaScript -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js" type="text/javascript"></script>
    <script src="js/script.js"></script>
    <script src="js/script-user-identity.js"></script>
    <script src="js/controller.js"></script>
  </body>
</html>
