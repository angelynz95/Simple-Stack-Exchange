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
      url: "http://localhost:8082/Identity_Service/UserNameController",
      data: {token: token},
      dataType: "json",
      // Hasil terima response dari server
      success: function(data, textStatus, jqXHR) {
        $(".user-name").html(data.user_name);
      },
      // Tidak ada response dari server
      error: function(jqXHR, textStatus, errorThrown) {
        alert("Something really bad happened " + textStatus + "<br>Please reload this page");
      }
    });
  }
});


