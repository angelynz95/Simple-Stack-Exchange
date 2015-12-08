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
  
  $scope.init = function(id) { 
        // Membuat http request
        $scope.voteNumAnswer = {};
        $http({
          method: "POST",
          url: "http://localhost:8083/Comment_Vote_Service/GetAnswerNumVoteController",
          params: { id: id},
          dataType: "json",
          headers : {
                'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
                },
        }).success(function(data, status, headers, config) {
            $scope.voteNumAnswer[id] = data;
           }).error(function(data, status, headers, config) {
             $scope.status = status;
           });
           
    };
  
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
            $scope.voteNumAnswer[id] = data;
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
  
    
    $scope.comments = function(id) { 
      // Membuat http request
      $http({
        method: "POST",
        url: "http://localhost:8083/Comment_Vote_Service/CommentController",
        params: { id: id},
        dataType: "json",
        headers : {
              'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
              }
      }).success(function(data, status, headers, config) {
          $scope.comments = data;
         }).error(function(data, status, headers, config) {
           $scope.status = status;
         });
  };
  
  $scope.addComment = function(token, id) { 
      // Membuat http request
      console.log("gila");
      $http({
        method: "POST",
        url: "http://localhost:8083/Comment_Vote_Service/AddCommentController",
        params: { token: token,
                  id: id,
                  content: $scope.commentContent},
        dataType: "json",
        headers : {
              'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
              }
      }).success(function(data, status, headers, config) {
          $scope.comments = data;
         }).error(function(data, status, headers, config) {
           $scope.status = status;
         });
  };
});