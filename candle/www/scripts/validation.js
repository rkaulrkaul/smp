$(document).ready(function() {
  $("#messageForm").on("submit", function() {
    var formValid = true;
    var nameValid = $("#userName").prop("validity").valid;
    var emailValid = $("#userEmail").prop("validity").valid;
    var commentValid = $("#comment").prop("validity").valid;
    var reviewValid = $("#reviewtext").prop("validity").valid;
    var selectValid = $("#select").prop("validity").valid;
    var radioValue = $('input[name= "choice"]:checked').val()
    if (nameValid) {
      $("#nameError").addClass("hidden");
      $("#userName").addClass("toggle");
    } else {
      formValid = false;
      $("#nameError").removeClass("hidden");
      $("#userName").removeClass("toggle");
    }
    if (emailValid) {
      $("#emailError").addClass("hidden");
      $("#userEmail").addClass("toggle");
    } else {
      formValid = false;
      $("#emailError").removeClass("hidden");
      $("#userEmail").removeClass("toggle");
    }
    if (radioValue == 'message') {
      if (commentValid) {
        $("#commentError").addClass("hidden");
        $("#comment").addClass("toggle");
      } else {
        formValid = false;
        $("#commentError").removeClass("hidden");
        $("#comment").removeClass("toggle");
      }
    }
    else if (radioValue == 'review') {
      if (selectValid) {
        $("#selectError").addClass("hidden");
      } else {
        formValid = false;
        $("#selectError").removeClass("hidden");
      }
      if (reviewValid) {
        $("#reviewError").addClass("hidden");
        $("#reviewtext").addClass("toggle");
      } else {
        formValid = false;
        $("#reviewError").removeClass("hidden");
        $("#reviewtext").removeClass("toggle");
      }
    }
    return formValid
  });
});
