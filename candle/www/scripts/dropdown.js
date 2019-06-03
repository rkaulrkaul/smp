
// When the document is ready
$(document).ready(function() {

  var DropdownOpen = false;

  // When the products nav item is clicked
  $("#productsNavigationItem").hover(function() {
    if  ($(window).width() > 600) {
      $("#productsDropdown").toggleClass("hidden");
      DropdownOpen = false;
    }
  },
    function() {
      if (DropdownOpen != false) {
        $("#productsDropdown").removeClass("hidden");
        DropdownOpen = true;
      }
    });
});
