//var styleSCN="style"; //styleSwith cookie name;

$(".body").ready(function(){
  IntObject2();
  IntPopup();
});



function IntObject2(){
    var btn=$(".button");
    var rab=$(".radioBtn");
    var ckb=$(".checkBtn");
    var ibtn=$(":button");
    var btnNC=$(".buttonNC")
    var all=$(btn).add(rab).add(ckb).add(ibtn).add(btnNC);
    $(all).each(function(x,all2){
        var judgType=function(){if($(all2).hasClass("button")){return "button"}else if($(all2).hasClass("radioBtn")){return "radio"}else if($(all2).hasClass("checkBtn")){return "check"}}
        var li=$(all2).children("li");
        var oldClass=function(obj){
            $(obj).data("oldclass",$.trim($(obj).attr("class").replace(/buttonNC|checkBtn|radioBtn|button/g,"")));
        }
        if($(li).length>0){
            $(li).each(function(i,obj){
                oldClass(obj);
                if(i==0){$(obj).addClass("frist")}else 
                if(i==$(li).length-1){$(obj).addClass("last")}
                $(obj).addClass("btn");
                if($(obj).hasClass('noInt')==false){actions($(obj),judgType());}
            });
        }else{oldClass(all2);actions($(all2),judgType());}
       
    });

    function actions(obj,what){
        $(obj).bind("mouseenter",function(){$(this).addClass("hover");if($("html").hasClass("IE6")){$(this).addClass($(this).data("oldclass")+"hover");}});
        $(obj).bind("mouseleave",function(){$(this).removeClass("hover");if($("html").hasClass("IE6")){$(this).removeClass($(this).data("oldclass")+"hover");}});
        if(what=="radio"){
            var judgeAction=function(){if($(obj).parent().hasClass("overAction")){return "mouseover"}else{return "click"}}
            $(obj).bind(judgeAction(),function(){
                var eobj=this;var bro=$(this).parent().children();
                $(bro).each(function(i,bro2){if(bro2==eobj){$(bro2).addClass("active");}else{$(bro2).removeClass("active");}});
            });
        }else if(what=="check"){$(obj).bind("click",function(){if($(this).hasClass("active")){$(this).removeClass("active")}else{$(this).addClass("active")}});
        }else if(what=="button"){$(obj).bind("click",function(){$(this).addClass("active");});}
    }
    
    var inputText=$(":text");
    var inputPass=$(":password");
    var inputTP=$(inputText).add(inputPass);
    $(inputTP).bind("focus",function(){$(this).removeClass("blur");$(this).addClass("focus");});
    $(inputTP).bind("blur",function(){
        if($(this).val()==""){
            $(this).removeClass("focus");
            $(this).removeClass("blur");
        }else{
            $(this).removeClass("focus");
            $(this).addClass("blur");
        }
    });

}


function IntPopup(){
    var ppw=$(".popupWrap");
    $(ppw).each(function(x,ppw2){
       var pp=$(ppw2).children(".popup");
       var ppl=$(pp).children(".popupList");
       var pplc=$(ppl).children();
       $(pplc).each(function(i,pplc2){
           if(i==0){$(pplc2).addClass("li1st");}
           if(i==$(pplc).length-1){$(pplc2).addClass("lilast");}
           $(pplc2).addClass("li");
       });
       $(ppw2).bind("mouseenter",function(){$(pp).stop(true,true).slideDown(200,function(){
           $(ppw2).bind("mouseleave",function(){$(pp).slideUp(200);});
       });});
       
    });
}



function EventCutStr(obj,str,num,title,relNum,dot){
    $(title).attr("title",str);
    var re=str.replace(/[^\x00-\xff]|\s*/img,"");
    var cnum=num+parseInt((re.length)/4);
    var dotjudge=function(){if(dot=="noDot"){return ""}else{return "..."}}
    var numjudge=function(){if(relNum=="RN"){return num}else{return cnum}}
    if(str.length > numjudge()&&str.length>5){
         $(obj).text(str.substring(0,numjudge())+dotjudge());
    }else{$(obj).text(str);}
}



function EventCutStr2(obj,num,title,api){
    var x2="off";
    var fixNum=0;
    if(api!=null&&api!=""){
        var sApi=api.split(" ");
        $.map(sApi,function(n){
            if(n=="x2"){x2="on"}else
            if(n.replace(/\d\s*/img,"")=="-"){fixNum=-n.replace(/\-\s*/img,"")}else
            if(n.replace(/\d\s*/img,"")=="+"){fixNum=n.replace(/\+\s*/img,"")}
        })
    }
    var str=$(obj).text();
    if(title==null||title==""){title=obj}
    $(title).attr("title",str);
    var fixBold=function(){if($(obj).css("font-weight")=="bold"||$(obj).css("font-weight")>400){return 4}else{return 2}}
    if(num==null||num==""){num=$(obj).width()/(parseInt($(obj).css("font-size"))+fixBold());}
    else{if($.type(num)=="object"){num=$(num).width()/(parseInt($(num).css("font-size"))+fixBold());}}
    var re=str.replace(/[^\x00-\xff]|\s*/img,"");
    var cnum=num+parseInt((re.length)/2);
    if(cnum>num*2){cnum=num*2-1}
    if(x2=="on"){num=2*num;cnum=2*cnum;}
    var done=str.substring(0,cnum);
    var numjudge=function(){
        if(done.replace(/[^\x00-\xff]|\s*/img,"").length/done.length<0.4){return num;}
        else{return num;}
    }
    //alert(str.length+" "+num+" "+cnum);
    if(str.length>cnum&&str.length>num){
        $(obj).text(str.substring(0,numjudge()+parseInt(fixNum))+"...");
    }
}


function IntAniSwitch(){
    var aniS=$("#aniSwitch");
    if($.cookie("animation")!="off"){$(aniS).addClass("active");jQuery.fx.off=false;}else{jQuery.fx.off=true;}
    $(aniS).bind("click",function(){
        if($.cookie("animation")!="off"){$.cookie("animation","off",{expires:365});jQuery.fx.off=true;}
        else{$.cookie("animation","on",{expires:365});jQuery.fx.off=false;}
    });    
    if($("html").hasClass("IE6")||$("html").hasClass("IE7")){
       jQuery.fx.off=true;
    }
}


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
            setD("enterKey");
        };
    });

}
$(function($){IntUserPass()});

function IntStyleSwitch(){

    var styleS=$(".styleSwitch");
    var styleSC=$(styleS).children();
    if($.cookie("style")!=null){$("html").addClass($.cookie("style"));$(styleSC).each(function(i,ssc){if($(ssc).hasClass($.cookie("style"))){$(ssc).addClass("active");}});}else{$(styleSC).first().addClass("active")}

    $(styleSC).each(function(i,sSC2){
        var clasG=$(sSC2).attr("class").split(" ");
        var styleName;
        $.map(clasG,function(n){if($.indexOf(n,"style")){styleName=n};});
        $(sSC2).bind("click",function(){
            $.cookie("style",styleName,{expires:30});
            var htmlClass=$("html").attr("class").split(" ");
            $.map(htmlClass,function(n){if($.indexOf(n,"style")){$("html").removeClass(n)};});
            $("html").addClass(styleName);
        });
    });
}
$(function($){IntStyleSwitch()});


function IntBrowserVer(){
    var judgeBE=function(){
        if($.browser.msie){
            var bv=parseInt($.browser.version);
            if(bv==7&&navigator.appVersion.indexOf("Trident\/4.0")>0){bv=8}
            $("html").data("bv",bv);
            return "IE "+"IE"+bv;}
        else if($.browser.safari){return "safari";}
        else if($.browser.opera){return "opera";}
        else if($.browser.mozilla){return "mozilla";}}
    $("html").addClass(judgeBE());
}
IntBrowserVer();


//jquery Cookie
(function($, document) {
    var pluses = /\+/g;
    function raw(s) {return s;}
    function decoded(s) {return decodeURIComponent(s.replace(pluses, ' '));}
    $.cookie = function(key, value, options) {
        // key and at least value given, set cookie...
        if (arguments.length > 1 && (!/Object/.test(Object.prototype.toString.call(value)) || value == null)) {
            options = $.extend({}, $.cookie.defaults, options);
            if (value == null) {options.expires = -1;}
            if (typeof options.expires === 'number') {var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);}
            value = String(value);
            return (document.cookie = [
                encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }
        options = value || $.cookie.defaults || {};
        var decode = options.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
            if (decode(parts.shift()) === key) {return decode(parts.join('='));}}
        return null;};
    $.cookie.defaults = {};
})(jQuery,document);

//jquery settimeout
(function($,document){
    $.timeout=function(speed,fun,obj){
        if(typeof obj.timeID!="undefined"){clearTimeout(obj.timeID);}
        obj.timeID=setTimeout(fun,speed);}
    $.clearTimeout=function(obj){if(typeof obj.timeID!="undefined"){clearTimeout(obj.timeID);}}
})(jQuery,document);

//jquery indexOf
(function($,document){$.indexOf=function(obj,str){if(obj.indexOf(str)>-1){return true}else{return false}}})(jQuery, document);

