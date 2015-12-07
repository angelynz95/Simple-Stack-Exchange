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

stackExchange.config(['$httpProvider', function ($httpProvider) {
  //Reset headers to avoid OPTIONS request (aka preflight)
  $httpProvider.defaults.headers.common = {};
  $httpProvider.defaults.headers.post = {};
  $httpProvider.defaults.headers.put = {};
  $httpProvider.defaults.headers.patch = {};
}]);

stackExchange.controller('VoteController', function ($scope, $http) {
    // Jika tombol vote di klik
    $scope.vote = function(id, voteType) { 
        // Membuat http request
        $http({
          method: "POST",
          url: "http://localhost:8083/Comment_Vote_Service/VoteController",
          data: { voteType: voteType, 
                  id: id},
          
          success: function(data, status, headers, config) {
            $scope.voteNum = data;
           },
           // Tidak ada response dari server
           error: function(data, status, headers, config) {
             $scope.status = status;
           }
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