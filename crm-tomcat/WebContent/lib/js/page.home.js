var loaded=false;j$(window).load(function(){setTimeout(function(){loaded=true;}),1000});
//Start navTabs;
(function(j$){
    var j$navChildW=0,j$nav,j$navw,j$ncL,j$ncR,j$nkL,j$nkR,j$scrollw;
    j$.navTabs={
        init:function(){
            j$(".nav")
                .addClass("navTabs")
                .wrap("<div class='navTabsWrap'><div class='navScrollWrap'/></div>")
                .parent()
                .before("<div class='navCtrl navCtrlLeft'/><div class='navCtrl navCtrlRight'/><div class='navCrack navCrackLeft'/><div class='navCrack navCrackRight'/>")
                .end()
                .children()
                .wrapInner("<span class='desc'><font class='link'/></span>")
                .prepend("<span class='leftCorner'/>")  
                .append("<span class='rightCorner'/>")
                .first().children(".desc")
                .prepend("<a class='icon'/>")
                .end()
                .addClass("active")
                .end().find("font")
                .find("a")
                .parent()
                .text(function(){return j$(this).text()});
                
                j$nav=j$(".navTabs");
                j$navw=j$(".navTabsWrap");
                j$ncL=j$(".navCtrlLeft");
                j$ncR=j$(".navCtrlRight");
                j$nkL=j$(".navCrackLeft");
                j$nkR=j$(".navCrackRight");
                j$scrollw=j$(j$navw).children(".navScrollWrap");
            
            j$nav.children().each(function(i,navc){
                j$navChildW=j$navChildW+j$(navc).width();
            });
            j$nav.width(j$navChildW);
            j$ncR.click(function(){
                j$ncL.removeClass("blur");
                j$nkL.show();
                j$scrollw.animate({scrollLeft:j$scrollw.scrollLeft()+j$scrollw.width()-100},500,function(){j$.navTabs.ctrlState()});
            });
            j$ncL.click(function(){
                j$ncR.removeClass("blur");
                j$nkR.show();
                j$scrollw.animate({scrollLeft:j$scrollw.scrollLeft()-j$scrollw.width()+100},500,function(){j$.navTabs.ctrlState()});
            });
            j$(".navCtrl").button();
            j$.navTabs.autoResize();
        },
        ctrlState:function(){
            if(j$scrollw[0]==null){return};
            if(j$scrollw.scrollLeft()==j$scrollw[0].scrollWidth-j$scrollw.width()){
                j$ncR.addClass("blur");
                j$ncL.removeClass("blur");
                j$nkR.hide();
            }else if(j$scrollw.scrollLeft()==0){
                j$ncL.addClass("blur");
                j$ncR.removeClass("blur");
                j$nkL.hide();
                if(j$scrollw.width()<j$navChildW){j$nkR.show();}
            }
        },
        autoResize:function(){
            j$ncL.removeClass("blur");
            j$ncR.removeClass("blur");
            if(j$(window).width()-380>j$navChildW){
                j$navw.width(j$navChildW);
                j$ncL.hide();j$ncR.hide();
                j$nkL.hide();j$nkR.hide();
                j$navw.css("right",10);
            }else{
                j$navw.width(j$(window).width()-380);
                //j$ncL.addClass("blur");
                j$ncL.show();j$nkL.show();
                j$ncR.show();j$nkR.show();
                j$navw.css("right",40);
            }
            j$.navTabs.ctrlState();
        }
    }
})(jQuery);
//End navTabs;

//Start navIcons;
(function($){
    var navWidth=0;
    //公用var！！！！修正！！！
    $.navIcons={
        init:function(){
            $(".nav")
                .addClass("navIcons")
                .wrap("<div class='navIconWrap'><div class='navScrollWrap'/></div>")
                .parent().before("<ul class='navPointer'/><div class='navCtrl navCtrlLeft'/><div class='navCtrl navCtrlRight'/>")
                .end().children()
                .wrapInner("<span/>")
                .first().addClass("active")
                .end()
                .each(function(i,obj){
                    navWidth=navWidth+$(obj).outerWidth(true,true);
                }).find("a").parent().text(function(){return $(this).text()});
             $(".navCtrl").button();
             $.navIcons.autoResize();
        },
        ctrlState:function(){
            var navSW=$(".navScrollWrap"),
                navIcon=$(".navIcons"),
                navPot=$(".navPointer"),
                sLeft=navSW.scrollLeft(),
                navCtrlL=$(".navCtrlLeft"),
                navCtrlR=$(".navCtrlRight"),
                addDis=function(obj){$(obj).addClass("blur");},
                remDis=function(obj){$(obj).removeClass("blur");};
        
            navCtrlL.add(navCtrlR).each(function(i,obj){
                if($(this).hasClass("navCtrlLeft")){
                    if($(navSW).scrollLeft()==0){addDis(navCtrlL);remDis(navCtrlR);}
                    else{remDis($(navCtrlR));}
                }else{
                    if($(navSW).width()==$(".navIcons").width()-$(navSW).scrollLeft()){addDis(navCtrlR);remDis(navCtrlL);}
                    else{if($(navSW).scrollLeft()!=0){remDis(navCtrlL);}}
                }
            });
            var num=((navIcon.width()-(navIcon.width()-$(navSW).scrollLeft()))/$(navSW).width())+1;
            $(navPot).children().each(function(j,obj1){
                if(num==j+1){
                    var EventObj=obj1;
                    $.each($(".navPointer>li"),function(k,obj2){
                        if(obj2==EventObj){$(obj2).addClass("active");}
                        else{$(obj2).removeClass("active");}
                    });
                }
            });
        },
        autoResize:function(){
            var navIW=$(".navIconWrap"),
                navSW=$(".navScrollWrap"),
                navIcon=$(".navIcons"),
                navLi=$(".navIcons>li"),
                navCtrl=$(".navCtrl"),
                navCtrlL=$(".navCtrlLeft"),
                navCtrlR=$(".navCtrlRight"),
                navPot=$(".navPointer");
                logoW=280;
            
            if(navIcon.length==0){return;}
            $(navSW).width(0);
            var c1st=$(window).width()-logoW;//except logo width;
            var liWidth=navLi.first().outerWidth(true,true);
            var c2nd=parseInt(c1st/liWidth-2)*liWidth-600;
            //navscrollwrap full icon width;
            if(c2nd<=0){return;}//fix ie6 dead;
            $(navSW).width(c2nd).scrollLeft(0);
            var c3rd=Math.ceil(navLi.length/(c2nd/liWidth));//page number;
            $(".navIcons").width(c3rd*c2nd);
            
            if(navIcon.width()>navSW.width()){$(navCtrl).add(navPot).show();}
            else{$(navCtrl).add(navPot).hide();}
            
            $(navIW).css({
                left:350,
                width:$(navSW).width()
            });
            
            
            $(navPot).empty();
            if($(navPot).children().length<c3rd){
                var num=c3rd-$(navPot).children().length;
                for(i=0;i<num;i++){
                    $(navPot).append("<li/>")
                }
            }
            if(c3rd<=3){navPot.css({
                height:"24px",
                width:$(navPot).children().length*$(navPot).children().first().outerWidth(true,true),
                top:""
            });}
            else{navPot.css({height:"",width:"60px",top:2});}
            
            $(navPot).css({right:-($(navPot).width()+10)});
            $(navCtrlR).css({right:parseInt($(navPot).css("right"))-26});
            
            $(navPot).children()
                .first().addClass("active")
                .end()
                .each(function(i,navPotLi){
                    $(navPotLi).attr({title:"\u7B2C "+(i+1)+" \u9875"});
                    $(navPotLi).bind("click",function(){
                        $(navPotLi).addClass("active");
                        $(navSW).stop().animate({scrollLeft:i*c2nd},500,function(){$.navIcons.ctrlState()});
                        $.each($(".navPointer>li"),function(i,navPotLi2){
                            if(navPotLi2==navPotLi){
                                $(navPotLi2).addClass("active");
                            }else{
                                $(navPotLi2).removeClass("active");
                            }
                        });
                    }).hover(function(){
                        $(navPotLi).addClass("hover");
                    },function(){
                        $(navPotLi).removeClass("hover");
                    });
                });
            if($(navSW).scrollLeft()==0){navCtrlL.addClass("blur");navCtrlR.removeClass("blur");}
            navCtrlL.add(navCtrlR).click(function(obj){
                var eobj=this;
                var operation=function(){
                    var sLeft=$(navSW).scrollLeft();
                    if($(eobj).hasClass("navCtrlLeft")==true){return sLeft-$(navSW).width();}
                    else{return sLeft+$(navSW).width();}
                }
                if($(navSW).is(":animated")==false){
                    $(navSW).stop().animate({scrollLeft:operation()},500,function(){$.navIcons.ctrlState()});
                }
            });
        }
    }
    
})(jQuery);
//End navIcons;

//Start navDesc;
j$(function(){
    j$(".nav>li").add(".userConfig").each(function(i,obj){
       j$(obj).bind("click",function(event){
           if(j$(obj).hasClass("userConfig")){
               j$(".nav li").removeClass("active");
           }
           j$("#contentFrame").attr("data-desc",j$(obj).text());
        }); 
    });
})


j$(function(){
    //nav style selection
    if(j$.cookie("navStyle")!=null&&j$.cookie("navStyle")=="navStyleTabs"){
        j$.navTabs.init();
        initAssistButton();
        j$("#navStyleSwitch").val("navStyleTabs");
        j$(window).resize(function(){
           j$.timer.set("navIcon",function(){
               j$.navTabs.autoResize();
           },300);
        });
    }else{
        j$.navIcons.init();
        initAssistDrawer();
        j$(window).resize(function(){
           j$.timer.set("navIcon",function(){
               j$.navIcons.autoResize();
           },300);
        });
    }
    j$("#navStyleSwitch").change(function(){
         j$.cookie("navStyle",j$(this).val(),{expires:30});
    });

//ctrl panl dialog initialization    
     var dialogOptions={
            autoOpen: true,
            height: 200,
            width: 350,
            modal: true,
            resizable: false,
            show:"fade",
            hide:"fade",
            overflow:false,
            buttons: {
                "\u786E\u5B9A": function() {
                    j$( this ).dialog("close");
                    j$("#contentFrame").contents().find(".swf-object").show();
                }
            },
            close: function() {
                j$( "#dialog" ).html("");
                j$("#contentFrame").contents().find(".swf-object").show();
            }
        }
     
     j$(".ctrlPanl").click(function(){
    	 
    	 j$("#contentFrame").contents().find(".swf-object").hide();
         j$(".dialogCtrlPanl").dialog(dialogOptions,{
             title:"\u7CFB\u7EDF\u8BBE\u7F6E",
             "autoOpen":true,
             height:"auto",
             width:400,
             close:function(){j$("#contentFrame").contents().find(".swf-object").show();}
         });
     });
     
});
function initAssistButton(){
    j$(".assist").addClass("assistButton");
    EventCutStr2(j$(".userInfo"),"",5);
    j$(".assist li").button();
}

function initAssistDrawer(){
    j$(".assist")
    .addClass("assistDrawer")
    .wrap("<div class='assistWrap'/>")
    .parent()
    .append("<div class='assistArrow'/>");
    
    var assi=j$(".assistWrap"),
        iconUl=j$(assi).children(".assist"),
        icons=j$(iconUl).children(),
        arrow=j$(assi).children(".assistArrow");
    
    j$(iconUl).hide();
    j$(assi).hover(function(){
        j$(this).stop().animate({width:"130px"},300,function(){
            iconUl.fadeIn(j$.ms(300));
            arrow.fadeOut(j$.ms(300));
        });
    },function(){
        j$(this).stop().animate({width:"25px"},300,function(){
            iconUl.fadeOut(j$.ms(300));
            arrow.fadeIn(j$.ms(300));
        });
    });
    j$(".assist li").button();
}

//Start logo animation & logo paus btn;
/////////////修正这里！！！！
j$(function(){
    logoIntv=window.setInterval(function(){
        j$(".logoWrap").children().toggle("fade",1000);
        if(j$(".logoWrap").is(":hidden")){
            j$(".logoWrap").children().first().show().end().last().hide();
        }
    },8000);
});
j$(".pausBtn").live("click",function(){
    if(j$(this).hasClass("active")){
        window.clearInterval(logoIntv);j$(this).attr("title","播放动画");
    }else{
        logoSwitch();j$(this).attr("title","暂停动画");
    }
});
//End logo animation & logo paus btn!

//Start initialization index layout!!
j$(function(){
    if(j$(".ui-layout-north").length==0){return;}
    myLayout = j$('body').layout({
        resizable:false
        ,west__resizable: true
        ,north__size:90
        ,west__size:230
        ,fxName:"none"
        ,spacing_open:8
        ,spacing_closed:8
        ,west__minSize:200
        ,west__maxSize:400
        ,showOverflowOnHover:true
        ,togglerTip_open:"\u6536\u8D77"
        ,togglerTip_closed:"\u5C55\u5F00"
        ,sliderTip:"\u6ED1\u51FA"
        //,maskIframesOnResize: true
        ,maskContents:true
        ,resizeWhileDragging:true
        //,west__maskIframesOnResize: false
        //,west__onclose_start:function(){j$(".ui-layout-west iframe").hide(0)}
        //,west__onopen_end:function(){j$(".ui-layout-west iframe").show(0)}
        /*,north__onresize_end:function(){
            if(j$(".nav").hasClass("navIcons")){j$.navIcons.autoResize("layout");}
            else{j$.navTabs.autoResize();}
        }*/
        //,onresize_start : function(){alert(456)}
        //,west__initClosed:false
        //, west__togglerLength_closed: -1
        //,maskContents:true 
        //,resizeWhileDragging:true
    });
    //j$(".ui-layout-west").hide();
    //myLayout.removePane('west');
    myLayout.options.north.onopen_end=function(){
        if(j$(".nav").hasClass("navIcons")){j$.navIcons.autoResize("layout");}
        else{j$.navTabs.autoResize();}
    }
    //myLayout.onresize(function(){123}); 
    //if(j$("html").hasClass("IE9")||j$("html").hasClass("IE6")||j$("html").hasClass("IE7")){
        //myLayout.options.north.fxName=false;
        //myLayout.resizeAll();
    //}
});
//End index layout!!
//Start initialization frame loading;



//End initialization frame loading;