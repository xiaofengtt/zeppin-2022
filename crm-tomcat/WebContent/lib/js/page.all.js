
//Start write url function;
(function(j$){
    var j$pPage;
    if(typeof(portletPage)=="undefined"){j$pPage="portlet.html"}else{j$pPage=portletPage;}
    j$.writeURL=function(opts){
        var options=j$.extend({},j$.writeURL.defaults,opts);
        var snav,cont,frameClick=false;
        if(j$("#sideNavLayout").length==1){snav=j$("#sideNavLayout");}
        else{snav=j$(window.top.document).find("#sideNavLayout");frameClick=true;}
        if(j$("#contentLayout").length==1){cont=j$("#contentLayout");}
        else{cont=j$(window.top.document).find("#contentLayout");}
        //if(j$("#framePool").length==1){pool=j$("#framePool");}
        //else{pool=j$(window.top.document).find("#framePool");}
        
            if(frameClick==false){
                /*
                try{
                    snav.find("iframe")[0].contentWindow.document.write("");
                    snav.find("iframe")[0].contentWindow.close();
                }catch(e){};*/
                //snav.find("iframe").attr("src",'');
                //snav.find("iframe").appendTo(pool);
                
                if(options.sidenav!=""){
                    snav.find(".frameLoading").show();
                    if(j$(".ui-layout-west").length==1){myLayout.addPane('west');}
                    //pool.find("#sidenavFrame").appendTo(snav);
                    snav.find("#sidenavFrame").attr("src",options.sidenav);
                }else{
                    if(j$(".ui-layout-west").length==1){myLayout.removePane('west');}
                }
            }
        /*
        try{
            cont.find("iframe")[0].contentWindow.document.write("");
            cont.find("iframe")[0].contentWindow.close();
        }catch(e){};*/
        //cont.find("iframe").attr("src",'');
        cont.find(".frameLoading").show();
        //cont.find("iframe").appendTo(pool);
        //pool.find("#contentFrame").appendTo(cont);
        cont.find("#contentFrame").attr("src",options.content);
        
    };
  
})(jQuery);
//End write url function;


j$(function(){
    //theme selection
    var defaultTheme="themeLightBlue";
    var themeS=j$(".themeSwitch");
    var themeSC=j$(themeS).children();
    if(j$.cookie("theme")!=null){
        j$("html").addClass(j$.cookie("theme"));
        j$(themeSC).each(function(i,ssc){
            if(j$(ssc).hasClass(j$.cookie("theme"))){
                j$(ssc).addClass("active");
            }
        });
    }else{
        if(defaultTheme!=null){
            j$("html").addClass(defaultTheme);
            j$(themeS).find("."+defaultTheme).addClass("active")
        }else{j$(themeSC).first().next().addClass("active");}
    }

    j$(themeSC).each(function(i,sSC2){
        var clasG=j$(sSC2).attr("class").split(" ");
        var styleName;
        j$.map(clasG,function(n){if(j$.indexOf(n,"theme")){styleName=n};});
        j$(sSC2).on("click",function(){
            j$.cookie("theme",styleName,{expires:30});
            var htmlClass=j$("html").attr("class").split(" ");
            j$.map(htmlClass,function(n){if(j$.indexOf(n,"theme")){j$("html").removeClass(n)};});
            j$("html").addClass(styleName);
            //alert(styleName);
            var frameHtmlClass=j$("#sideNavFrame").add("#contentFrame").contents().find("html").attr("class").split(" ");
            j$.map(frameHtmlClass,function(n){if(j$.indexOf(n,"theme")){j$("#sideNavFrame").add("#contentFrame").contents().find("html").removeClass(n)};});
            j$("#sideNavFrame").add("#contentFrame").contents().find("html").addClass(styleName);
        });
    });
    
    //animation switch
    var aniS=j$("#aniSwitch");
    if(j$.cookie("animation")!="off"){j$(aniS).addClass("active");jQuery.fx.off=false;}else{jQuery.fx.off=true;}
    j$(aniS).on("click",function(){
        if(j$.cookie("animation")!="off"){j$.cookie("animation","off",{expires:365});jQuery.fx.off=true;}
        else{j$.cookie("animation","on",{expires:365});jQuery.fx.off=false;}
    });    
    if(j$("html").hasClass("IE6")||j$("html").hasClass("IE7")){
       jQuery.fx.off=true;
       j$(aniS).removeClass("active");
    }
})
