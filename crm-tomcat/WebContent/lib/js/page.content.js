$(function() {
    $( ".buttonResize" )
        .button({
            icons: {primary: "x4 y23 icon"}
            ,text: false
        })
        .addClass("buttonSimple")
        .click(function(){
            if($(window.top.document).find(".ui-layout-north").length==0){return;}
            window.parent.myLayout.toggle('west');
            window.parent.myLayout.toggle('north');
            $(this).find(".icon").toggleClass("x5")
            if($(this).find(".icon").hasClass("x5")){$(this).attr("title","\u8FD8\u539F")}else{$(this).attr("title","\u6700\u5927\u5316")}
        });
        
        
    $(".buttonPageGoto")
        .button({
            icons: {primary: "x2 y32 icon"}
            ,text: false
        })
        .addClass("buttonSimple");
        
    $( ".buttonPageFrist" )
        .button({
            icons: {primary: "x11 y33 icon"}
            ,text: false
        })
        .addClass("buttonSimple");
        
    $( ".buttonPagePrevious" )
        .button({
            icons: {primary: "x13 y33 icon"}
            ,text: false
        })
        .addClass("buttonSimple");
        
    $( ".buttonPageNext" )
        .button({
            icons: {primary: "x14 y33 icon"}
            ,text: false
        })
        .addClass("buttonSimple");
        
    $( ".buttonPageLast" )
        .button({
            icons: {primary: "x12 y33 icon"}
            ,text: false
        })
        .addClass("buttonSimple");
        
        
    $(".buttonNew")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x13 y28 icon"}
        });
        
    $(".buttonSave")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x13 y8 icon"}
        });
        
    $(".buttonPrint")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x16 y5 icon"}
        }).click(function(){
            $(".fontWrap").css({height:"auto","white-space":"normal"});
        });
        
    $(".buttonExit")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x12 y10 icon"}
        });
        
    $(".buttonReturn")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x5 y32 icon"}
        });
        
    $(".buttonEdit")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x1 y20 icon"}
        });
        
    $(".buttonDelete")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x11 y36 icon"}
        });
        
    $(".buttonSubmit")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x16 y35 icon"}
        });
        
    $(".buttonView")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x8 y3 icon"}
        });
        
    $(".buttonRefresh")
        .addClass("buttonSimple")
        .button({
            icons: {primary: "x11 y32 icon"}
        })
        .click(function(){
            document.location.reload();
        });
        
    $(".buttonSearch")
        .button({
            icons: {
                primary: "x2 y19 icon",
                secondary: "ui-icon-triangle-1-s"
            }
        })
        .click(function(){
            $(".buttonSearchLabel").find(".ui-button-icon-secondary")
            .toggleClass("ui-icon-triangle-1-s")
            .toggleClass("ui-icon-triangle-1-n");
            $(".searchContainer").toggle(0,function(){
                //autoResizeSearchForm()
            });
        });
    $(".buttonFormSubmit").button()
            .next().button({
                    text: false,
                    icons: {
                        primary: "ui-icon-triangle-1-s"
                    }
                })
                .click(function() {
                    var menu = $( this ).parent().next().show().position({
                        my: "left top",
                        at: "left bottom",
                        of: this
                    });
                    $(document).one( "click", function() {
                        menu.hide();
                    });
                    return false;
                })
                .parent().buttonset().next().hide().menu();
    
    $('.searchForm').formly();
    
    $(".tableList").tableList({
        icon:"x1 y29",
        //checkbox:true,
        //radio:true,
        //width:800,
        //height:600,
        cutStr:true,
        tdWidth:{
            tdLast:70,
            //tdFirst:20,
            td2:"20%",
            //td3:70
        }
    });
});

(function($){
    $.fn.autoResizeInput = function(options, callback){
        var settings={}
        var opts=$.extend(settings,options);
        var sf=this;
        resizeAction=function(sf){
            var parent;
            if(sf.data("init")==null&&sf.is(":hidden")){
                parent=sf.parentsUntil(":visible");
                parent.show();
            }
            input=$(sf).find(":input"),
            perRowNum=parseInt((sf.innerWidth()-135)/150);
            if(perRowNum>input.length){perRowNum=input.length}
            var inputW=parseInt((sf.innerWidth()-135)/perRowNum),
                disparity=input.first().outerWidth(true,true)-input.first().width();
            input.width(inputW-disparity);
            if(parent){parent.hide();}
            sf.data("init","true");
        }
        resizeAction(sf);
        $(window).resize(function(){
            $.timer.set("searchForm",function(){resizeAction(sf);},500)
        });
    }
})(jQuery);


