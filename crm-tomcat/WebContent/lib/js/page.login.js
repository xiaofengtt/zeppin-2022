function setD(way){
    if(way=="help"){
        $( "#dialog" )
        .dialog({title:"\u5E2E\u52A9","autoOpen":true})
        .html(helpHtml);
    }else if(way=="copyright"){
        $( "#dialog" )
        .dialog({title:"\u7248\u6743\u6240\u6709","autoOpen":true})
        .html(copyrightHtml);
    }else if(way=="download"){
        $( "#dialog" )
        .dialog({title:"\u4E0B\u8F7D","autoOpen":true})
        .html(downloadHtml);
    }else if(way=="wrong"){
        $( "#dialog" )
        .dialog({title:"\u9519\u8BEF","autoOpen":true})
        .html(wrongHtml);
    }else if(way=="notComplete"){
        $( "#dialog" )
        .dialog({title:"\u4FE1\u606F\u4E0D\u5B8C\u6574","autoOpen":true})
        .html(notCompletHtml);
    }else if(way=="IE6"){
        $( "#dialog" )
        .dialog({
            title:"IE8\u66F4\u65B0",
            autoOpen:true,
            buttons: {
                "\u6C38\u4E0D\u63D0\u793A": function() {
                    $.cookie("passie6","pass",{expires:30});
                    setD("setBtn");
                    $( this ).dialog("close");
                },
                "\u786E\u5B9A": function() {
                    setD("setBtn");
                    $( this ).dialog("close");
                }
            }
        })
        .html(thisIsIE6Html);
    }else if(way=="setBtn"){
        $( "#dialog" ).dialog( "option", "buttons", [{text: "\u786E\u5B9A",click: function() { $(this).dialog("close");}}]);
    }else if(way=="enterKey"){
        $("#dialog")
        .dialog({title:"\u56DE\u8F66\u9A8C\u8BC1\u8868\u5355","autoOpen":true,buttons:false,close:function(){setD("setBtn");}})
        .html(enterKeyHtml);
    }
    //$('.ui-button:button').blur();
}


$(function(){
    $( "#dialog" ).dialog({
        autoOpen: false,
        height: 200,
        width: 350,
        modal: true,
        resizable: false,
        show:"fade",
        hide:"fade",
        buttons: {
            "\u786E\u5B9A": function() {
                $( this ).dialog("close");
            }
        },
        close: function() {
            $( "#dialog" ).html("");
        }
    });

    $(".help").click(function() {setD("help");});
    $(".copyright").click(function() {setD("copyright");});
    $(".download").click(function() {setD("download");});
    $(".submit").click(function(){
        if($(".user").val()==""||$(".pass").val()==""){setD("notComplete");}
        else{
            if(typeof(loginSubmit)=="undefined"){setD("wrong");}
            else{loginSubmit();}
        }
    });
    
    function closeD(){$( "#dialog" ).dialog("close");}
       
    $(document).tooltip({ position: {my: "bottom-20", at: "center" }});
    if(($("html").hasClass("IE6")||$("html").hasClass("IE7"))&&$.cookie("passie6")==null){setD("IE6");}
    
});


function IntUserPass(){
    if($.cookie("userid")!=null){
        $(".user").val($.cookie("userid")).addClass("focus");
        $(".pass").val($.cookie("password")).addClass("focus");
        $(".remUP").addClass("active");
    }else{
        $(".user").val("");
    }
    
    $(".remUP").bind("click",function(){
        if($(".user").val()==""||$(".pass").val()==""){
            $(this).removeClass("active");
            setD("notComplete");
        }else{
            if($(this).hasClass("active")){
                $.cookie("userid",$(".user").val(),{expires:30});
                $.cookie("password",$(".pass").val(),{expires:30});
            }else{
                $.cookie("userid",null);
                $.cookie("password",null);
            }
        }
    });
    
    $(".user").change(function(){
        $.cookie("userid",null);
        $.cookie("password",null);
        $(".remUP").removeClass("active");
    });
    $(".pass").keypress(function(e){
        var theEvent = window.event || e; 
        var code = theEvent.keyCode || theEvent.which; 
        if(code==13){
            if(typeof(loginSubmit)=="undefined"){setD("enterKey");}
            else{loginSubmit();}
        };
    });

}
$(function($){IntUserPass()});

