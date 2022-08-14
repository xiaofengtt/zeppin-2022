function setD(way){
    if(way=="help"){
        $( "#dialog" )
        .dialog({title:"Help","autoOpen":true})
        .html("<div class='help'>help!!!!!<\/div><div class='dialogBg helpBg' /\>");
    }else if(way=="copyright"){
        $( "#dialog" )
        .dialog({title:"\u7248\u6743\u6240\u6709","autoOpen":true})
        .html("<div class='copyright'>copyright<\/div><div class='dialogBg copyrightBg' /\>");
    }else if(way=="download"){
        $( "#dialog" )
        .dialog({title:"\u4E0B\u8F7D","autoOpen":true})
        .html("<div class='download'><div class='desc'>\u4E3A\u4E86\u83B7\u5F97\u66F4\u597D\u7684\u4F53\u9A8C\uFF0C\u6211\u4EEC\u63A8\u8350\u4EE5\u4E0B\u6D4F\u89C8\u5668\uFF01</div><ul class='browser'><li class='loadIE9'></li><li class='loadChrome'></li><li class='loadFirefox'></li><li class='loadOpera'></li><li class='loadSafari'></li></ul><\/div>");
    }else if(way=="wrong"){
        $( "#dialog" )
        .dialog({title:"\u9519\u8BEF","autoOpen":true})
        .html("<div class='error'>\u7528\u6237\u540D\u5BC6\u7801\u9519\u8BEF\uFF0C\u8BF7\u91CD\u8BD5\uFF01<\/div><div class='dialogBg wrongBg' /\>");
    }else if(way=="notComplete"){
        $( "#dialog" )
        .dialog({title:"\u4FE1\u606F\u4E0D\u5B8C\u6574","autoOpen":true})
        .html("<div class='error'>\u8BF7\u586B\u5199\u7528\u6237\u540D\u548C\u5BC6\u7801\uFF01\u7136\u540E\u518D\u8BD5\uFF01<\/div><div class='dialogBg notCompleteBg' /\>");
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
        .html("<div class='download'><div class='desc update'>\u4F60\u7684IE\u7248\u672C\u8FC7\u65E7\uFF0C\u8BF7\u5347\u7EA7\u4EE5\u83B7\u5F97\u66F4\u597D\u7684\u4F53\u9A8C\uFF01</div><div class='loadIE8'>\u4E0B\u8F7DIE8\u6D4F\u89C8\u5668</div><\/div>");
    }else if(way=="setBtn"){
        $( "#dialog" ).dialog( "option", "buttons", [{text: "\u786E\u5B9A",click: function() { $(this).dialog("close");}}]);
    }else if(way=="enterKey"){
        $("#dialog")
        .dialog({title:"\u56DE\u8F66\u9A8C\u8BC1\u8868\u5355","autoOpen":true,buttons:false,close:function(){setD("setBtn");}})
        .html("<div class='error'>\u66FF\u6362\u9875\u9762\u4E2D\u7684\u65B9\u6CD5\u4E3A\u63D0\u4EA4\u8868\u5355\u4E8B\u4EF6\uFF01<\/div>");
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
            else{setD("wrong");}
        });
        
        function closeD(){$( "#dialog" ).dialog("close");}
        
        $(".loadIE8").live("click",function(){
            window.open("http://windows.microsoft.com/zh-CN/internet-explorer/downloads/ie-8");
            closeD();
        });
        $(".loadIE9").live("click",function(){
            window.open("http://windows.microsoft.com/zh-CN/internet-explorer/download-ie");
            closeD();
        });
        $(".loadChrome").live("click",function(){
            window.open("http://www.google.cn/intl/zh-CN/chrome/browser/");
            closeD();
        });
        $(".loadFirefox").live("click",function(){
            window.open("http://firefox.com.cn/download/");
            closeD();
        });
        $(".loadOpera").live("click",function(){
            window.open("http://www.opera.com/");
            closeD();
        });
        $(".loadSafari").live("click",function(){
            window.open("http://www.apple.com/safari/");
            closeD();
        });
        
        $(document).tooltip({ position: {my: "bottom-20", at: "center" }});
        if(($("html").hasClass("IE6")||$("html").hasClass("IE7"))&&$.cookie("passie6")==null){setD("IE6");}
        
    });
    



$(function(){
    //theme selection
    var styleS=$(".themeSwitch");
    var styleSC=$(styleS).children();
    //alert($.cookie("theme"));
    if($.cookie("theme")!=null){
        $("html").addClass($.cookie("theme"));
        $(styleSC).each(function(i,ssc){
            if($(ssc).hasClass($.cookie("theme"))){
                $(ssc).addClass("active");
            }
        });
    }else{
        $(styleSC).first().next().addClass("active")
    }

    $(styleSC).each(function(i,sSC2){
        var clasG=$(sSC2).attr("class").split(" ");
        var styleName;
        $.map(clasG,function(n){if($.indexOf(n,"theme")){styleName=n};});
        $(sSC2).bind("click",function(){
            $.cookie("theme",styleName,{expires:30});
            var htmlClass=$("html").attr("class").split(" ");
            $.map(htmlClass,function(n){if($.indexOf(n,"theme")){$("html").removeClass(n)};});
            $("html").addClass(styleName);
        });
    });
    
})
