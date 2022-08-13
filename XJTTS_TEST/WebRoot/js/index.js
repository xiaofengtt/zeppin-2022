/**
 * Created by thanosYao on 2015/7/2.
 */
// Can also be used with $(document).ready()
$(document).ready(function(){
    $('.xs-navlist-toggle').click(function(){
        $('.xs-navlist').toggle();
    });
    $('.flexslider').flexslider({
        animation: "slide",
        slideshowSpeed: 10000
        //controlsContainer: ".flex-container"
    });
    $('.navlist li a.firstmenu').hover(function(){
        $(this).css("border-right","1px solid #fff");
        $(this).parent().prev().children('a').css("border-right","1px solid #fff");
        $('.subnavlist').hide();
        $(this).next('.subnavlist').fadeIn();
    },function(){
        $(this).css("border-right","1px solid #d4d3ce");
        $(this).parent().prev().children('a').css("border-right","1px solid #d4d3ce");
    });
    $('.navbar-nav').hover(function(){
        $('.subnavlist').hide();
    });
    $('.subnavlist').hover(function(){
        $(this).parent().addClass('active');
    }, function(){
        $(this).parent().removeClass('active');
        $(this).fadeOut();
    });
    $(function(){
        // bind change event to select
        $('.selecttogo').bind('change', function () {
            var url = $(this).val(); // get selected value
            if (url) { // require a URL
                window.location = url; // redirect
            }
            return false;
        });
    });
    $('.searchBtn').click(function(){
        //Search code
        console.log("start search");
    });
});