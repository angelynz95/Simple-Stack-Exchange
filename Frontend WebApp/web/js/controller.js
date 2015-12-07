/* 
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */

'use strict';

var stackExchange = angular.module('stackExchange', []);

stackExchange.controller('VoteController', function ($scope, $http, $location) {
    // Jika tombol vote di klik
    
    $scope.voteAnswer = function(id, voteType, token) { 
        // Membuat http request
        $http({
          method: "POST",
          url: "http://localhost:8083/Comment_Vote_Service/VoteController",
          params: { voteType: voteType, 
                  id: id,
                  token: token  },
          dataType: "json",
          headers : {
                'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
                },
          
          
        }).success(function(data, status, headers, config) {
            $scope.voteNumAnswer = data;
           }).error(function(data, status, headers, config) {
             $scope.status = status;
           });
    };
    
    $scope.voteQuestion = function(id, voteType, token) { 
        // Membuat http request
        $http({
          method: "POST",
          url: "http://localhost:8083/Comment_Vote_Service/VoteController",
          params: { voteType: voteType, 
                  id: id,
                  token: token  },
          dataType: "json",
          headers : {
                'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
                },
          
          
        }).success(function(data, status, headers, config) {
            $scope.voteNumQuestion = data;
           }).error(function(data, status, headers, config) {
             $scope.status = status;
           });
    };
});

stackExchange.controller('CommentController', function ($scope, $http, $location) {
  // Menghentikan submit request
  $("#comment-form").submit(function(e) {
    e.preventDefault();
  });
  
  // Memeriksa tombol add comment di klik
  $("#comment-submit").click(function(e) {
    // Ambil form data
    var content = $("#comment-content").val();
    
    // Memperoleh token dan qid dari URL
    var token = $location.search().token;
    var qid = $location.search().qid;

    // Membuat http request
    $http({
      method: "POST",
      url: "http://localhost:8083/Comment___Vote_Service/CommentController",
      dataType: "json",
      data: { content: content,
              token: token,
              qid: qid},

      // Hasil terima response dari server
      success: function(data, textStatus, jqXHR) {
       window.location.href = "http://localhost:8080/Frontend_WebApp/QuestionDetailController?token=" + token + "&qid=" +idQuestion;
      },
      // Tidak ada response dari server
      error: function(jqXHR, textStatus, errorThrown) {
        console.log("Something really bad happened " + textStatus + "<br>Please reload ths page");
        alert(jqXHR.responseText);
      }
    });
  });
});