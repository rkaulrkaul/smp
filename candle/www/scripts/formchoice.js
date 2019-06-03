$(document).ready(function() {
  $('input:radio[name = "choice"]').change(function() {
    if(this.value == 'message' && this.checked) {
      $('#message').removeClass("hidden");
      $('.submit').removeClass("hidden");
      $('#review').addClass("hidden");
    }
    else if(this.value == 'review' && this.checked) {
      $('#review').removeClass("hidden");
      $('.submit').removeClass("hidden");
      $('#message').addClass("hidden");
    }
  });
});
