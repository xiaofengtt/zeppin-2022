Zepto(function($){
    // Write your code below.

    $("a").click(function (event) {
        event.preventDefault();
        window.location = $(this).attr("href");
    });


    $('.list-title,.doticon').on('click',function(){
        var par = $(this).parent();
        par.toggleClass('open');
        if(par.find('ul').is(':visible')) {
            par.find('.doticon').first().removeClass('fa-plus-circle');
            par.find('.doticon').first().addClass('fa-minus-circle');

        }else {
            par.find('.doticon').first().addClass('fa-plus-circle');
            par.find('.doticon').first().removeClass('fa-minus-circle');
        }

    });


})

/**
 * Created by a on 2015/7/9.
 */
