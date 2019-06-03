$(document).ready(function() {

  var images = [
  "images/hazelnut.jpg",
  "images/sage.jpg",
  "images/lavender.jpg",
  "images/jasmine.jpg",
  "images/chantilly.jpg",
  "images/blackberry.jpg",
  "images/chamomile.jpg",
  ];

  var titles = ["Hazelnut Chai", "Tobacco and Sage Leaf", "Lavender London Fog", "Jasmine Green Tea", "Chantilly Cream", "Blackberry Green Tea", "Honey Chamomile Tea"];

  var currentIndex = 0;
  $("#slideshowNext").click(function() {
    if (currentIndex == 6){
      $('img[src="' + images[currentIndex] + '"]').attr('src', images[0]);
      document.getElementById("candleTitle").textContent = titles[0];
      currentIndex = 0;
    }
    else {
      $('img[src="' + images[currentIndex] + '"]').attr('src', images[currentIndex+1]);
      currentIndex=currentIndex+1;
      document.getElementById("candleTitle").textContent = titles[currentIndex];
    }
  });


  $("#slideshowBack").click(function() {
    if (currentIndex == 0){
      $('img[src="' + images[currentIndex] + '"]').attr('src', images[6]);
      document.getElementById("candleTitle").textContent = titles[6];
      currentIndex = 6;
    }

    else {
      $('img[src="' + images[currentIndex] + '"]').attr('src', images[currentIndex-1]);
      currentIndex=currentIndex-1;
      document.getElementById("candleTitle").textContent = titles[currentIndex];
    }
  });

});
