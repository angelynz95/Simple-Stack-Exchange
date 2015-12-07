/* 
 * Tugas 3 IF3110 Pengembangan Aplikasi Web
 * Website StackExchangeWS Sederhana
 * dengan tambahan web security dan frontend framework
 * 
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */

$(document).ready(function(){
  // ANSWER FORM
  // Get url parameter
  var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
      sURLVariables = sPageURL.split('&'),
      sParameterName,
      i;

    for (i = 0; i < sURLVariables.length; i++) {
      sParameterName = sURLVariables[i].split('=');

      if (sParameterName[0] === sParam) {
        return sParameterName[1] === undefined ? true : sParameterName[1];
      }
    }
  };

  var token = getUrlParameter("token");
  if (token !== undefined) {
    // Buat AJAX request
    $.ajax({
      type: "GET",
      url: "http://localhost:8082/Identity_Service/UserIdentityController",
      data: {token: token},
      dataType: "json",
      // Hasil terima response dari server
      success: function(data, textStatus, jqXHR) {
        $("#answer-name").val(data.user_name);
        $("#answer-email").val(data.user_email);
        $("#answer-name").prop("disabled", true);
        $("#answer-email").prop("disabled", true);
        $("#question-name").val(data.user_name);
        $("#question-email").val(data.user_email);
        $("#question-name").prop("disabled", true);
        $("#question-email").prop("disabled", true);
        $("#comment-name").val(data.user_name);
        $("#comment-email").val(data.user_email);
        $("#comment-name").prop("disabled", true);
        $("#comment-email").prop("disabled", true);
        $(".user-name").append(data.user_name);
      },
      // Tidak ada response dari server
      error: function(jqXHR, textStatus, errorThrown) {
        alert("Something really bad happened " + textStatus + "<br>Please reload this page");
      }
    });
  }
  
  $("#comment-part").hide();
  $("#add-comment").click(function() {
    $("#comment-part").toggle();
    $("#add-comment").toogle();
  });
  $("#comment-form").submit(function() {
    $("#comment-part").toggle();
    $("#add-comment").toggle();
  });
});


