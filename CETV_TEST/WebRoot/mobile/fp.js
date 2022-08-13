$(function() {
var isiPad = function() {
    return navigator.userAgent.match(/iPad/i) != null;
  }
  $('footer').css("opacity", 0);
  var fullPageOptions = {
    resize: false,
    navigation: true,
    navigationPosition: 'right',
     slidesNavigation: true,
     autoScrolling: true,
     scrollOverflow: false,
	anchors: ['gs1', 'gs2', 'gs3', 'gs4', 'gs5'],
	menu: '#menu',
    css3: true,
    keyboardScrolling: true,
    scrollingSpeed: 777,
    touchSensitivity: 15,
    continuousVertical: false,
    animateAnchor: false,
    //menu: '#navigation',
    paddingTop: '70px',
    onLeave: function(index, nextIndex, direction) {
      changeColorBySlideIndex(nextIndex - 1);
      var animate = getAnimationFunction(nextIndex);
	  var rvanimate = removeAnimationFunction(index);
      if(!isiPad()) {
        animate();
		rvanimate();
		
      }
      
      if((nextIndex) == colorSchemes.length) {
        $('footer').css("opacity", 0.8);
      } else {
        $('footer').css("opacity", 0);
      }
    },
    afterLoad: function(anchorLink, index) {
      var animate = getAnimationFunction(index);
	   var rvanimate = removeAnimationFunction(index);
      if(isiPad()) {
		  
        animate();
		rvanimate();
      }
      $(window).resize(function() {
        animate()
		rvanimate();
      });
    },
	afterRender :  function() {
		$('.heart').stop().animate({
            left: '10px',
            opacity: 1
           
		 }, 500);
 		$('.tree').stop().animate({
             right: '0px',
             opacity: 1
 		 }, 600);
		
	}
  }

  function getAnimationFunction(index) {
      return function() {
        if(getWidthOfWindow() >= 992) {
         
		  if(index == 1) {
    			  $('.heart').stop().animate({
                left: '10px',
                opacity: 1
         }, 450);
        $('.tree').stop().animate({
                 right: '0px',
                 opacity: 1
         }, 600);
		  }
          if(index == 2) {
			 $(".brainicon").addClass('animated zoomInDown'); 
			 //$('.app-code img').addClass('animated zoomInDown')
          }
          if(index == 4) {
            $(".tools").addClass('animated rotateIn');
			
          }
          
          if(index == 3) {
			  $('.colud').stop().delay(500).animate({
		            left: '46px',
		            opacity: 1
				}, 1500,function(){
				 	//$('.heart').addClass('animated bounceIn')
			});
			  $('.car').stop().delay(500).animate({
		            right: '120px',
		            opacity: 1
				}, 1000,function(){
				 	//$('.heart').addClass('animated bounceIn')
			});
			  
          }
          if(index == 5) {
            $(".earth img").addClass('animated zoomIn');
          }
		 
        }
      }
  }
  
  function removeAnimationFunction(index) {
      return function() {
        if(getWidthOfWindow() >= 992) {
         
         if(index ==1) {
              $('.heart').stop().animate({
                left: '-200px',
                    opacity: 0
             }, 450);
            $('.tree').stop().animate({
                     right: '-200px',
                     opacity: 0
             }, 600);
         }
          if(index == 2) {
			 $(".brainicon").removeClass('animated zoomInDown'); 
			 //$('.app-code img').addClass('animated zoomInDown')
          }
          if(index == 4) {
            $(".tools").removeClass('animated rotateIn');
			
          }
          
          if(index == 3) {
			  $('.colud').stop().delay(500).animate({
		            left: '-100px',
		            opacity: 0
				}, 1500,function(){
				 	//$('.heart').addClass('animated bounceIn')
			});
			  $('.car').stop().delay(500).animate({
		            right: '-300px',
		            opacity: 0
				}, 1000,function(){
				 	//$('.heart').addClass('animated bounceIn')
			});
			  
          }
          if(index == 5) {
            $(".earth img").removeClass('animated zoomIn');
          }
		 
        }
      }
  }
  
  
  var colorSchemes = getColorSchemes();
  $('body').css('background', colorSchemes[0].body);
 // $('#menu a').css('color', colorSchemes[0].body);
  $('#fullpage').fullpage(fullPageOptions);

  function isMobileDevice() {
    if(/Android|webOS|iPhone|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
      return true;
    }
    return false;
  }
  if(isMobileDevice()) {
    $('footer').css("position", "fixed");
    $("#fullPage-nav").remove();
    $.fn.fullpage.setAutoScrolling(false);
  }

  function getHeightOfWindow() {
    return $(window).height();
  }

  function getWidthOfWindow() {
    return $(window).width();
  }
  $(window).resize(function() {
    toggleVerticalAlign();
  });

  function toggleVerticalAlign() {
    if(getWidthOfWindow() >= 992 || getHeightOfWindow() >= 800) {
      $(".tableCell").css("vertical-align", "middle");
    } else if(getWidthOfWindow() < 992 && getHeightOfWindow() < 800) {
      $(".tableCell").css("vertical-align", "top");
      $("#section1 .tableCell").css("vertical-align", "middle !important");
    }
  }
  toggleVerticalAlign();
});
var changeColorBySlideIndex = function(index) {
  var colorSchemes = getColorSchemes();
  var currentColorScheme = colorSchemes[index];
  if(currentColorScheme) {
    _.each(currentColorScheme, function(hexColor, selector) {
      changeToColor(selector, hexColor);
    });
  }
}
var changeToColor = function(selector, hexColor) {
  $(selector).stop().css("background", hexColor);
}
var getColorSchemes = function() {
  var slides = $('.section');
  var colorSchemes = [];
  _.each(slides, function(slide) {
    var tempObject = {
      "body": $(slide).attr('mt-data-slide-bg-color')
      
    }
    colorSchemes.push(tempObject);
  })
  return colorSchemes;
}