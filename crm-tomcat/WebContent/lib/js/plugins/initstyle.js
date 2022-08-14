var styleSCN="style"; //styleSwith cookie name;

j$(".body").ready(function(){
  //严格按照此执行顺序IntPointer();IntObject();在执行所有方法前必须先执行这三个公共方法。
  //IntImgSwitch();
  //IntTableList();
  //IntTableForm()
	
  //IntSideNav();//20170828注释
  //IntPointer();
  IntObject2();
  //IntPopup();
});

function IntLoading(act){
    //if(j$(".loading").length==0){j$("body").prepend("<div class='loading'/>");}
    var load=j$(".pageLoading");
    if(j$(load).length==0){return;}
    if(act=="show"){
        //j$(imgs).hide(0);
        j$(load).show(0);
        j$(load).fadeTo(0,0.9);
        j$(load).height(j$(document).height());
        //j$("html").css("overflow","hidden");
    }else if(act=="hide"){
        //j$(imgs).show(0);
        j$(load).fadeOut(1000,function(){j$("html").css("overflow","");});
    }
}
j$(function(){IntLoading("hide")});
//j$().ready(function(){IntLoading("show");});
//j$("body").ready(function(){setTimeout(function(){},1000);});
//j$(window).load()
function IntObject(){//已废弃，被intObject2取代；
	IntLabel();IntBtnWIcon();IntLabelCute();
	var btnG=j$(".button");
	j$(btnG).each(function(i,btnG2){
			var btnChi=j$(btnG2).children();
			var inputBtn=j$(":button");
			var singleBtn=j$(".button");
			var button=j$(btnChi).add(inputBtn).add(singleBtn);
			
			j$(button).each(function(j,button2){
				if(j$(button2).hasClass("noInt")==false){
					j$(button2).addClass("btn");
					j$(button2).bind("mouseenter",function(){j$(this).addClass("hover");});
					j$(button2).bind("mouseleave",function(){j$(this).removeClass("hover");});
				}
			});
			
			j$(btnChi).each(function(j,btnChi2){
				if(j==0&&j$(btnChi).length>1){
					j$(btnChi2).addClass("first");
				}else if(j==j$(btnChi).length-1&&j$(btnChi).length>1){
					j$(btnChi2).addClass("last");
				}
			});
			if(j$(btnG2).hasClass("overAction")==false){
				if(j$(btnG2).hasClass("clickAction")){
					j$(btnChi).bind("click",function(){
						var eobj=this;
						var parent=j$(this).parent();
						var pchild=j$(parent).children();
						j$(pchild).each(function(i,pchild2){
							if(pchild2==eobj){j$(pchild2).addClass("active")}
							else{j$(pchild2).removeClass("active")}
						});
					});
				}
			}else if(j$(btnG2).hasClass("overAction")){
				j$(btnChi).bind("mouseenter",function(){
					var eobj=this;
					var parent=j$(this).parent();
					var pchild=j$(parent).children();
					j$(pchild).each(function(i,pchild2){
						if(pchild2==eobj){j$(pchild2).addClass("active")}
						else{j$(pchild2).removeClass("active")}
					});
				});
			}
	});
}

function IntObject2(){
    var btn=j$(".button");
    var rab=j$(".radioBtn");
    var ckb=j$(".checkBtn");
    var ibtn=j$("input:button");
    var btnNC=j$(".buttonNC")
    var all=j$(btn).add(rab).add(ckb).add(btnNC);//.add(ibtn);
    j$(all).each(function(x,all2){
        var judgType=function(){if(j$(all2).hasClass("button")){return "button"}else if(j$(all2).hasClass("radioBtn")){return "radio"}else if(j$(all2).hasClass("checkBtn")){return "check"}}
        var li=j$(all2).children("li");
        var oldClass=function(obj){
            if(j$(obj).attr("class")){
                j$(obj).data("oldclass",j$.trim(j$(obj).attr("class").replace(/buttonNC|checkBtn|radioBtn|button/g,"")));
            }
        }
        if(j$(li).length>0){
            j$(li).each(function(i,obj){
                oldClass(obj);
                //if(i==0){j$(obj).addClass("frist")}else 
                //if(i==j$(li).length-1){j$(obj).addClass("last")}
                j$(obj).addClass("btn");
                if(j$(obj).hasClass('noInt')==false){actions(j$(obj),judgType());}
            });
        }else{oldClass(all2);actions(j$(all2),judgType());}
       
    });

    function actions(obj,what){
        j$(obj).bind("mouseenter",function(){j$(this).addClass("hover");});
        j$(obj).bind("mouseleave",function(){j$(this).removeClass("hover");});
        if(what=="radio"){
            var judgeAction=function(){if(j$(obj).parent().hasClass("overAction")){return "mouseover"}else{return "click"}}
            j$(obj).bind(judgeAction(),function(){
                var eobj=this;var bro=j$(this).parent().children();
                j$(bro).each(function(i,bro2){if(bro2==eobj){j$(bro2).addClass("active");}else{j$(bro2).removeClass("active");}});
            });
        }else if(what=="check"){j$(obj).bind("click",function(){if(j$(this).hasClass("active")){j$(this).removeClass("active")}else{j$(this).addClass("active")}});
        }else if(what=="button"){j$(obj).bind("click",function(){j$(this).addClass("active");});}
    }
    
    var inputText=j$(":text");
    var inputPass=j$(":password");
    var inputTP=j$(inputText).add(inputPass);
    j$(inputTP).bind("focus",function(){j$(this).removeClass("blur");j$(this).addClass("focus");});
    j$(inputTP).bind("blur",function(){
        if(j$(this).val()==""){
            j$(this).removeClass("focus");
            j$(this).removeClass("blur");
        }else{
            j$(this).removeClass("focus");
            j$(this).addClass("blur");
        }
    });

}

function IntSelect(){
    var select=j$(".selectWrap");
    if(j$(select).length==0){return;}
    j$(select).each(function(x,select2){
        var choose=j$(select2).children(".selectChoose");
        var menu=j$(select2).children(".selectSub");
        j$(menu).width(j$(menu).width());
        var mli=j$(menu).children("li");
        j$(select2).mouseenter(function(){j$(this).addClass("hover");});
        j$(select2).mouseleave(function(){j$(this).removeClass("hover")});
        j$(select2).bind("click",function(){if(j$(menu).css("display")=="none"){j$(menu).stop().fadeIn();}else{j$(menu).stop().fadeOut();}});
        j$(mli).mouseenter(function(){j$(this).addClass("hover");});
        j$(mli).mouseleave(function(){j$(this).removeClass("hover")});
        j$(mli).bind("click",function(){
            EventCutStr(j$(choose),j$(this).text(),7,j$(select2))
            j$(select2).addClass("end");
        });
        j$(menu).css("top",j$(select2).height());
        if(j$(menu).width()<j$(select2).width()){j$(menu).width(j$(select2).width()-2);}
    });
}
j$(function(j$){IntSelect()});

function IntBtnWIcon(){
	var btnWI=j$(".buttonWIcon");
	if(j$(btnWI).length==0){return;}
	j$(btnWI).each(function(i,btnWI2){
		var btn=j$(btnWI2).children();
		j$(btn).each(function(j,btn2){
			var span=j$(btn2).children("span");
			var desc=j$(span).eq(1);
			j$(span).eq(0).addClass("icon");
			j$(desc).addClass("desc");
			EventCutStr(j$(desc),j$(desc).text(),7,j$(btn2))
		});
	});
}
j$(function(j$){IntBtnWIcon()});
function IntLabel(){
	var labT=j$(".labelTitle");
	if(j$(labT).length==0){return;}
	j$(labT).each(function(x,labT2){
		var btnli=j$(labT2).children("li");
		var border=0;
		j$(btnli).each(function(j,btnli2){
			if(j==0){j$(btnli2).addClass("lab1st");}else 
			if(j==j$(btnli).length-1){j$(btnli2).addClass("labEnd");}
			var judgeBorder=function(side){
				if(side=="left"){
					var leftB=parseInt(j$(btnli2).css("border-left-width"));
					if(leftB.toString()!="NaN"){return leftB;}else{return 0;}}else 
				if(side=="right"){
					var rightB=parseInt(j$(btnli2).css("border-right-width"));
					if(rightB.toString()!="NaN"){return rightB;}else{return 0;}}
			}
			var sureBorder=judgeBorder("right");
			border=border+judgeBorder("left")+judgeBorder("right");
		});
		if(j$(labT2).hasClass("noInt")==false){
			var noLastWidth=0;
			j$(btnli).each(function(j,btnli2){
				if(j<j$(btnli).length-1){
					j$(btnli2).width(function(){return parseInt((j$(labT2).width()-border)/j$(btnli).length);});
					noLastWidth=noLastWidth+j$(btnli2).width();}
				else{j$(btnli2).width(function(){return parseInt(j$(labT2).width()-noLastWidth-border);});}
			});
		}
		
		var labw;
        if(j$(labT2).parent().has(".labelCon").length==1){labw=j$(labT2).parent();}
        else{labw=j$(labT2).closest(".labelWrap");}
		//var labT2=j$(labw).find(".labelTitle");
        if(j$(labT2).hasClass("overAction")==false){j$(labT2).addClass("clickAction")}
        var ltli=j$(labT2).children("li");
        var lcon=j$(labw).find(".labelCon");
        if(j$(lcon).length==0){return;}
        if(j$(lcon).parent().hasClass("scrollWrap")==false){j$(lcon).wrap("<div class='scrollWrap'\/>")}
        var sw=j$(labw).children(".scrollWrap");
        var lcli=j$(lcon).children("li");
        j$(ltli).first().addClass("active");
        j$(lcli).addClass("labCLi");
        j$(lcli).width(j$(labw).width());
        j$(lcon).width(j$(lcli).width()*j$(lcli).length);
        j$(lcli).css("float","left");
        if(j$.browser.msie&&parseInt(j$.browser.version)<=7 ){j$(sw).width(j$(lcli).width());}//修正ie8以下的版本
        j$(lcli).each(function(i,lcli2){
            if(j$(lcli2).children().length==0){j$(lcli2).append("<div class='tipNoCon'><\/div>");}
        });
        var judgeAct=function(){
            if(j$(labT2).hasClass("overAction")){
                return "mouseover";}else{return "click";}
        }
        j$(ltli).bind(judgeAct(),function(){
            //alert(judgeAct())延时执行！！！！
            var eObj=this;
            j$(ltli).each(function(i,obj){if(obj==eObj){j$(sw).stop().animate({scrollLeft:i*j$(sw).width()},300);}});
        });
	});
	IntLabelCute();
}

function IntLabelCute(){
	var btnlw=j$(".labelCute");
	if(j$(btnlw).length==0){return;}
	j$(btnlw).each(function(i,btnlw2){
		var btnli=j$(btnlw2).children("li");
		if(j$(btnlw2).parent().is(".labelCuteWrap")==false){j$(btnlw2).wrap("<div class='labelCuteWrap'/>")}
		j$(btnli).each(function(j,btnli2){
			if(j==0){
				j$(btnli2).addClass("btn1st");
				j$(btnli2).append("<span class='btnCorner'><\/span>")
			}else if(j==j$(btnli).length-1){
				j$(btnli2).addClass("btnEnd");
				j$(btnli2).append("<span class='btnCorner'><\/span>")
			}
			if(j!=0){j$(btnli2).append("<span class='split'><\/span>")}
		});
	});
}

function EventCutStr(obj,str,num,title,relNum,dot){
    j$(title).attr("title",str);
    var re=str.replace(/[^\x00-\xff]|\s*/img,"");
    var cnum=num+parseInt((re.length)/4);
    var dotjudge=function(){if(dot=="noDot"){return ""}else{return "..."}}
    var numjudge=function(){if(relNum=="RN"){return num}else{return cnum}}
    if(str.length > numjudge()&&str.length>5){
         j$(obj).text(str.substring(0,numjudge())+dotjudge());
    }else{j$(obj).text(str);}
}


function EventCutStr3(obj,str,num,title,api){
    var x2="off";
    var fixNum=0;
    if(api!=null&&api!=""){
        var sApi=api.split(" ");
        j$.map(sApi,function(n){
            if(n=="x2"){x2="on"}else
            if(n.replace(/\d\s*/img,"")=="-"){fixNum=-n.replace(/\-\s*/img,"")}else
            if(n.replace(/\d\s*/img,"")=="+"){fixNum=n.replace(/\+\s*/img,"")}
        })
    }
    if(str==""){str=j$(obj).text();}
    if(title==null||title==""){title=obj}
    if(j$(title).attr("title")==null){j$(title).attr("title",str);}
    var fixBold=function(){if(j$(obj).css("font-weight")=="bold"||j$(obj).css("font-weight")>400){return 4}else{return 2}}
    if(num==null||num==""){num=j$(obj).width()/(parseInt(j$(obj).css("font-size"))+fixBold());}
    else{if(j$.type(num)=="object"){num=j$(num).width()/(parseInt(j$(num).css("font-size"))+fixBold());}}
    var re=str.replace(/[^\x00-\xff]|\s*/img,"");
    var cnum=num+parseInt((re.length)/2);
    if(cnum>num*2){cnum=num*2-1}
    if(x2=="on"){num=2*num;cnum=2*cnum;}
    var done=str.substring(0,cnum);
    var numjudge=function(){
        if(done.replace(/[^\x00-\xff]|\s*/img,"").length/done.length<0.4){return num;}
        else{return num;}
    }
    if(str.length>cnum&&str.length>num){
        j$(obj).text(str.substring(0,numjudge()+parseInt(fixNum))+"...");
    }else{j$(obj).text(str);}
}


function EventCutStr2(obj,str,num,title,api){
    var x2="off";
    var fixNum=0;
    if(api!=null&&api!=""){
        var sApi=api.split(" ");
        j$.map(sApi,function(n){
            if(n=="x2"){x2="on"}else
            if(n.replace(/\d\s*/img,"")=="-"){fixNum=-n.replace(/\-\s*/img,"")}else
            if(n.replace(/\d\s*/img,"")=="+"){fixNum=n.replace(/\+\s*/img,"")}
        })
    }
    if(str==""){str=j$(obj).text();}
    if(title==null||title==""){title=obj}
    if(j$(title).attr("title")==null){j$(title).attr("title",str);}
    var fixBold=function(){if(j$(obj).css("font-weight")=="bold"||j$(obj).css("font-weight")>400){return 4}else{return 2}}
    if(num==null||num==""){num=j$(obj).width()/(parseInt(j$(obj).css("font-size"))+fixBold());}
    else{if(j$.type(num)=="object"){num=j$(num).width()/(parseInt(j$(num).css("font-size"))+fixBold());}}
    var re=str.replace(/[^\x00-\xff]|\s*/img,"");
    var cnum=num+parseInt((re.length)/2);
    if(cnum>num*2){cnum=num*2-1}
    if(x2=="on"){num=2*num;cnum=2*cnum;}
    var done=str.substring(0,cnum);
    var numjudge=function(){
        if(done.replace(/[^\x00-\xff]|\s*/img,"").length/done.length<0.4){return num;}
        else{return num;}
    }
    if(str.length>cnum&&str.length>num){
        j$(obj).text(str.substring(0,numjudge()+parseInt(fixNum))+"...");
    }
}


function IntTableDouble(){
	var table=j$(".tableList");
	if(j$(table).length==0){return;}
	j$(table).each(function(x,table2){
		if(j$(table2).hasClass("tableDouble")){
			var tr=j$(table2).children("tbody").children("tr");
			j$(tr).each(function(i,objtr){
				var tditr=j$(objtr).children("td");
				j$(tditr).each(function(j,objtd){
					if(j==1){
						var span=j$(objtd).children("span");
						var span1=j$(span).eq(0);
						j$(span1).addClass("spanTitle");
						EventCutStr(j$(span1),j$(span1).text(),22,j$(objtd))
						j$(span).filter(":eq(1)").addClass("spanDate");
					}
				});
			});
		}
	});
}

//Exuberant Table
function IntTableExub(){
	var table=j$(".tableExub");
	if(j$(table).length==0){return;}
	j$(table).each(function(x,table2){
		var tr=j$(table2).children("tbody").children("tr");
		var td=j$(tr).children("td");
		var tdExub=new Array();
        var tdexub=j$(table2).data("tdexub");
		j$(tr).each(function(i,objtr){
			var tditr=j$(objtr).children("td");
			j$(tditr).each(function(j,objtd){if(j+1==tdexub){tdExub[j]="on";}});
		});
		j$(tr).each(function(i,objtr){
			var tditr=j$(objtr).children("td");
			j$(tditr).each(function(j,objtd){
				if(tdExub[j]=="on"){
					var div=j$(objtd).children("div");
					j$(div).eq(0).addClass("header");
					j$(div).eq(1).addClass("article");
					EventCutStr(j$(div).eq(1),j$(div).eq(1).text(),50);
					var span=j$(div).eq(0).children("span");
					j$(span).eq(0).addClass("title");
					EventCutStr(j$(span).eq(0),j$(span).eq(0).text(),20,j$(objtd));
					j$(span).eq(1).addClass("date");
					
				}
			});
		});
	});
}

function IntPointer(){
	var potW=j$(".pointerWrap");
	if(j$(potW).length==0){return;}
	j$(potW).each(function(x,potW2){
		function scrollDir(){
			if(j$(potW2).hasClass("scrollH")){return "sTop";}else{return "sLeft";}}
		function findScrollGroup(){
			if(j$(potW2).parent().find(".scrollGroup").length>0){return j$(potW2).parent().find(".scrollGroup");}
			else if(j$(potW2).parent().find(".scrollCon").length>0){return j$(potW2).parent().find(".scrollCon").children();}
			else if(j$(potW2).prev().length>0){return j$(potW2).prev();}
			else if(j$(potW2).next().length>0){return j$(potW2).next();}}
		var scrollG=findScrollGroup();	if(scrollG==null){return;}
		var sDir=scrollDir();
		if(j$(scrollG).parent().hasClass("scrollCon")==false)
			{j$(scrollG).wrap("<div class='scrollWrap'><div class='scrollCon'><\/div><\/div>");}
		var scrollC=j$(scrollG).parent();
		var scrollW=j$(scrollC).parent();
		var mainGroup=j$(scrollC).children();
		if(j$(mainGroup).length==1){
			function judgeChild(){
				if(j$(mainGroup).is("table")==false){return j$(mainGroup).children();}
				else{return j$(mainGroup).children().children();}}
			var child=judgeChild();
			function findNum(){
				var num=j$(mainGroup).eq(0).data("splitnum");
				if(num!=null&&parseInt(num).toString()!="NaN"){return parseInt(num);}
				else{return 6;}}
			var num=findNum();
			var GLength=Math.round(j$(child).length/num+0.4)-1;
			for(i=0;i<GLength;i++){j$(mainGroup).clone().empty().appendTo(scrollC);}
			var group=j$(mainGroup).parent().children();
			j$(group).each(function(i,group2){
				if(i>0){j$(child).each(function(j,child2){
					if((j)>=(i)*num&&(j+1)<=(i+1)*num){j$(child2).appendTo(j$(group)[i]);
				}});}
			});}
		var group=j$(mainGroup).parent().children();
		j$(group).each(function(i,group2){
			if(i==0&&sDir=="sTop"){j$(scrollW).height(j$(group2).outerHeight(true,true))}
			if(sDir=="sTop"&&i!=j$(group).length-1){j$(group2).height(j$(group).first().outerHeight(true,true));}
			j$(group2).css("float","left");
			j$(group2).width(j$(scrollW).width());
		});
		
		if(sDir=="sLeft"){j$(scrollC).width(j$(group).length*j$(mainGroup).width());}
		else{j$(scrollC).height(j$(group).length*j$(mainGroup).height());}
		
		var pArrow=j$(potW2).children(".arrowList");
		if(j$(potW2).hasClass("arrowWrap")&&pArrow.length==0){j$(potW2).append("<ul class='arrowList button'><li class='btn01 disable'></li><li class='btn02'></li><\/ul>");}
		var plist=j$(potW2).children(".pointerList");
		if(plist.length==0){j$(potW2).append("<ul class='pointerList radioBtn'><\/ul>");}
		var pointer=j$(potW2).children(".pointerList");
		var arrow=j$(potW2).children(".arrowList");
		if(j$(potW2).hasClass("scrollH")){j$(arrow).addClass("arrowH")}else{j$(arrow).addClass("arrowV")}
		var arrow01=j$(arrow).children(".btn01");
		var arrow02=j$(arrow).children(".btn02");
		var arrowBtn=j$(arrow01).add(arrow02);
		
		var judgeAct=function(){if(j$(potW2).hasClass("clickAction")){return "click";}else{return "mouseover";}}
		j$(arrowBtn).bind("click",function(){
			function judgePlMi(obj){if(j$(obj).hasClass("btn01")){return "-";}else{return "+";}}
			if(sDir=="sLeft"&&j$(scrollW).is(":animated")!=true){j$(scrollW).stop().animate({scrollLeft:eval(j$(scrollW).scrollLeft()+judgePlMi(this)+scrollW.width())},300,function(){PoArState()});}
			else if(sDir=="sTop"&&j$(scrollW).is(":animated")!=true){j$(scrollW).stop().animate({scrollTop:eval(j$(scrollW).scrollTop()+judgePlMi(this)+scrollW.height())},300,function(){PoArState()});}
		});		
		
		if(judgeAct()=="mouseover"){j$(pointer).addClass("overAction")}else{j$(pointer).addClass("clickAction")}
		j$(pointer).empty();
		j$(group).each(function(i){j$(pointer).append("<li><\/li>");});
		var pli=j$(pointer).children("li");
		j$(pli).each(function(i,obj){
			if(i==0){j$(obj).addClass("active");}
			j$(obj).attr({title:"\u7B2C "+(i+1)+" \u9875"});
			j$(obj).bind(judgeAct(),function(){
				if(sDir=="sLeft"){
					j$(scrollW).stop().animate({scrollLeft:i*scrollW.width()},400,function(){PoArState()});
				}else if(sDir=="sTop"){
					j$(scrollW).stop().animate({scrollTop:i*scrollW.height()},400,function(){PoArState()});
				}
			});
		});
		if(j$(potW2).hasClass("noPosition")==false){
			j$(pointer).css({left:function(){return (j$(potW2).width()-j$(pointer).width())/2;}});
			j$(pointer).css({top:function(){return (j$(potW2).height()-j$(pointer).height())/2;}});}
			
		function PoArState(){
			if(sDir=="sLeft"){
				if(j$(scrollW).scrollLeft()==0){j$(arrow01).addClass("disable")}else{j$(arrow01).removeClass("disable")}
				if(j$(scrollW).width()+j$(scrollW).scrollLeft()==j$(scrollC).width()){j$(arrow02).addClass("disable")}else{j$(arrow02).removeClass("disable")}
				var count=((j$(scrollC).width()-(j$(scrollC).width()-j$(scrollW).scrollLeft()))/j$(scrollW).width());
				j$(pli).each(function(i,pli2){if(i==count){j$(pli2).addClass("active")}else{j$(pli2).removeClass("active")}});
			}else if(sDir=="sTop"){
				if(j$(scrollW).scrollTop()==0){j$(arrow01).addClass("disable")}else{j$(arrow01).removeClass("disable")}
				if(j$(scrollW).height()+j$(scrollW).scrollTop()==j$(scrollC).height()){j$(arrow02).addClass("disable")}else{j$(arrow02).removeClass("disable")}
				var count=((j$(scrollC).height()-(j$(scrollC).height()-j$(scrollW).scrollTop()))/j$(scrollW).height());
				j$(pli).each(function(i,pli2){if(i==count){j$(pli2).addClass("active")}else{j$(pli2).removeClass("active")}});
			}
		}
	});
}

function IntSideNav_bak(){
	var sn=j$(".sideNav");
	if(j$(sn).parent().hasClass("sideNavWrap")==false){j$(sn).wrap("<div class='sideNavWrap'/>")}
	var snw=j$(sn).parent();
	var p2ndC=j$(snw).parent().children(".p2ndCon");
	var sList=j$(sn).children("li");
	var p2w=j$(snw).parent();
	var allLi;
	j$(sList).each(function(i,sList2){
		j$(sList2).addClass("li1st");
		var snlc=j$(sList2).children();
		var sub=j$(snlc).children();
		allLi=j$(allLi).add(sList).add(sub);
		j$(snlc).addClass("sideNavSub button");
		if(j$(snlc).children("li").length>0){j$(sList2).addClass("withSub")}
		var clearSubA=function(eobj){
			j$(sList).find(".sideNavSub").children().each(function(k,sub3){
				if(sub3!=eobj){j$(sub3).removeClass("active")};
			});}
		j$(sub).each(function(j,sub2){
			j$(sub2).addClass("li2nd");
			j$(sub2).bind("click",function(){var eobj=this;clearSubA(eobj);});
		});
		j$(sList2).bind("click",function(){
			var eobj=this;
			j$(sList).each(function(j,sList3){
				var sl3sub=j$(sList3).find(".sideNavSub");
				if(sList3==eobj){j$(sl3sub).slideDown();}
				else{j$(sl3sub).slideUp();}
			});
			if(j$(this).hasClass("withSub")==false){clearSubA(eobj);}
		});
	});
	
	j$(allLi).each(function(i,allLi2){
		var snlc=j$(allLi2).children();
		j$(snlc).detach();
		j$(allLi2).wrapInner("<div class='liDiv'><span \/><\/div>");
		j$(allLi2).find("span").addClass("desc");
		j$(allLi2).find("span").parent().prepend("<span><\/span>");
		j$(allLi2).find("span").first().addClass("icon2nd");
		j$(allLi2).append(snlc);
	});
}

function IntSideNav(menuId){
    var sn=j$(".sideNav");
    if(j$(sn).parent().hasClass("sideNavWrap")==false){j$(sn).wrap("<div class='sideNavWrap'/>")}
    var snw=j$(sn).parent();
    var p2ndC=j$(snw).parent().children(".p2ndCon");
    var sList=j$(sn).children("li");
    var p2w=j$(snw).parent();
    var allLi;
    j$(sn).each(function(i,sn2){
        var all=j$(sn2).find("*").andSelf();
        j$(all).each(function(j,ano){
           var my=j$(ano).parents("ul").parentsUntil(".sideNav").end();
           var step=j$(my).length+1;
           if(j$(ano).is("ul")){
               j$(ano).addClass("navUl navUl"+step);
               var chi=j$(ano).children();
               j$(chi).addClass("navLi navLi"+step);
                j$(chi).each(function(j,chi2){
                    var snlc=j$(chi2).children("ul");
                    j$(snlc).detach();
                    j$(chi2).wrapInner("<div class='navDiv'><span \/><\/div>");
                    j$(chi2).find("span").addClass("desc");
                    j$(chi2).find("span").parent().prepend("<span><\/span><span><\/span>");
                    j$(chi2).find("span").first().addClass("iconSta");
                    
                    if(menuId == "w"){
                    	j$(chi2).find("span").first().next().addClass("icon-w");
                    }
                    if(menuId == "1"){
                    	j$(chi2).find("span").first().next().addClass("icon-1");
                    }
                    if(menuId == "2"){
                    	j$(chi2).find("span").first().next().addClass("icon-2");
                    }
                    if(menuId == "3"){
                    	j$(chi2).find("span").first().next().addClass("icon-3");
                    }
                    if(menuId == "4"){
                    	j$(chi2).find("span").first().next().addClass("icon-4");
                    }
                    if(menuId == "5"){
                    	j$(chi2).find("span").first().next().addClass("icon-5");
                    }
                    
                    j$(chi2).find("span").first().next().addClass("icon");
                    j$(chi2).append(snlc);
                    var navDiv=j$(chi2).children(".navDiv");
                    j$(navDiv).css("padding-left",15*(step)).addClass("navDiv"+step);
                    var subUl=j$(chi2).children("ul");
                    if(j$(subUl).length>0){
                        j$(navDiv).addClass("withSub");
                        j$(subUl).hide();
                    }
                    j$(chi2).bind("click",function(){
                        var eobj=this;
                        var bro=j$(eobj).parent().children();
                        if(j$(sn2).hasClass("oneActive")){bro=j$(all).find(".navLi");}
                        j$(bro).each(function(k,bro2){
                           var subUl=j$(bro2).children("ul");
                           var navDiv=j$(bro2).children(".navDiv");
                           if(bro2==eobj){
                                if(j$(bro2).has("ul").length>0){
                                    j$(subUl).slideToggle("",function(){
                                        if(j$(subUl).is("ul:visible")==false){
                                            j$(navDiv).removeClass("active open");
                                        }
                                    });
                                    j$(navDiv).toggleClass("active");
                                }else{j$(navDiv).addClass("active");}
                           }else{
                               if(j$(navDiv).hasClass("active")){
                                   j$(navDiv).removeClass("active");
                                   j$(navDiv).addClass("open");
                               }
                           }
                        });
                        var liBro=j$(all).find(".navLi"+step);
                        j$(liBro).each(function(l,liBro2){
                           var navDiv=j$(liBro2).find(".navDiv");
                           var subUl=j$(liBro2).children("ul");
                           var allUl=j$(liBro2).find("ul");
                           if(liBro2!=eobj){
                               j$(navDiv).removeClass("active");
                               j$(navDiv).removeClass("open");
                               j$(subUl).slideUp("",function(){j$(allUl).hide();});
                           }
                        });
                        return false;
                    });
                    j$(navDiv).hover(function(){
                        j$(this).toggleClass("hover");
                    })
                });
           }
        });
    });
    
    j$(".navDiv2").css({
    	"padding-left":"41px"
    });
    j$(".navDiv3").css({
    	"padding-left":"42px"
    });
    j$(".sideNav .navDiv2 .icon").remove();
    j$(".sideNav .navDiv3 .icon").remove();
    
    function position(index,xValue,yValue){
    	 j$(".sideNav .navDiv1 .icon").eq(index).css({
    		 "background-position-x":xValue + "px",
    		 "background-position-y":(yValue?yValue:-10) + "px"
    	 });
    }
    
    if(menuId == "w"){//工作台，信息库
    	position(0,-10,-9);
    	position(1,-35);
    	position(2,-60);
    	position(3,-85);
    	position(4,-110);
    	position(5,-136);
    	position(6,-161,-9);
    }
    if(menuId == "2"){//客户管理
    	position(0,-10);
    	position(1,-36);
    	position(2,-62);
    }
    if(menuId == "3"){//营销管理
    	position(0,-10);
    	position(1,-36);
    	position(2,-61);
    	position(3,-86);
    	position(4,-111);
    	position(5,-137,-11);
    }
    if(menuId == "4"){//经理管理
    	position(0,-10);
    	position(1,-36);
    	position(2,-61);
    	position(3,-85);
    	position(4,-110);
    }
    if(menuId == "5"){//呼叫中心
    	position(0,-9);
    	position(1,-33);
    	position(2,-58);
    }
    if(menuId == "1"){//系统配置
    	position(0,-8);
    	position(1,-33,-9);
    	position(2,-58);
    	
    }
    
}

function IntPopup(){
    var ppw=j$(".popupWrap");
    j$(ppw).each(function(x,ppw2){
       var pp=j$(ppw2).children(".popup");
       var ppl=j$(pp).children(".popupList");
       var pplc=j$(ppl).children();
       j$(pplc).each(function(i,pplc2){
           if(i==0){j$(pplc2).addClass("li1st");}
           if(i==j$(pplc).length-1){j$(pplc2).addClass("lilast");}
           j$(pplc2).addClass("li");
       });
       j$(ppw2).bind("mouseenter",function(){j$(pp).stop(true,true).slideDown(200,function(){
           j$(ppw2).bind("mouseleave",function(){j$(pp).slideUp(200);});
       });});
       
    });
}



function IntTableList(){
    var table=j$(".tableList");
    if(j$(table).length==0){return;}
    j$(table).each(function(x,table2){
        var tr=j$(table2).children("tbody").children("tr");
        var td=j$(tr).children("td");
        var noCutStr=new Array();
        
        if(j$(table2).data("init")==null){
            j$(table2).attr({"cellSpacing":"0","cellPadding":"0"}).data("init","done");
            j$(tr).bind("mouseover",function(){j$(this).addClass("hover");});
            j$(tr).bind("mouseout",function(){j$(this).removeClass("hover");});
            if(j$(table2).hasClass("withTitle")){
                j$(tr).filter(":gt(0):even").addClass("odd");
                j$(tr).filter(":gt(0):odd").addClass("even");
                j$(tr).first().addClass("trHeader");
                j$(tr).first().unbind("mouseover mouseout");
            }else{
                j$(tr).filter(":even").addClass("odd");
                j$(tr).filter(":odd").addClass("even");
            }
            j$(tr).each(function(i,objtr){
                var tditr=j$(objtr).children("td");
                j$(tditr).each(function(j,objtd){
                    if(j==0){
                        if(j$(objtd).hasClass("tdIcon")==false&&j$(table2).hasClass("noIntIcon")==false){
                            if(j$(objtd).html()==""||(j$(objtd).children("span").length==1&&j$(objtd).children("span").html()=="")){j$(objtd).addClass("tdIcon");}
                            else{j$(objtr).prepend("<td class='tdIcon'><span class='iconTL'><\/span><\/td>");}
                            td=j$(objtr).children("td").first();
                            if(j$(td).children(".iconTL").length==0&&j$(objtr).attr("class")!="trHeader"){
                                if(j$(td).children("span").length==0){
                                    j$(td).append("<span class='iconTL'><\/span>");
                                }else if(j$(td).children("span").text()==""){
                                    j$(td).children("span").addClass("iconTL");
                                }
                            }
                        }
                    }
                });
            });
            var tr0td=j$(tr).filter(":eq(0)").children("td");
            j$(tr).each(function(i,objtr){
                var tditr=j$(objtr).children("td");
                j$(tditr).each(function(j,objtd){
                    j$(objtd).attr("title",j$(objtd).text());
                    j$(objtd).addClass("td"+j);
                    if(j==0){j$(objtd).addClass("td1st");}
                    else if(j==tr0td.length-2&&j$(tditr).length>3){j$(objtd).addClass("tdNTL");}
                    else if(j==tr0td.length-1&&j$(tditr).length>2){j$(objtd).addClass("tdLast");}
                    if(j$(objtd).html()==""){j$(objtd).html("&nbsp;")}
                });
            });
        }
        
        var tdw=new Array(); 
        j$(tr).each(function(i,objtr){
            j$(objtr).addClass("tr"+i);
            j$(objtr).children("td").each(function(j,objtd){
                if(j$(objtd).hasClass("noCutStr")==true){noCutStr[j]="on"}
                if(i==0){tdw[j]=parseInt((j$(objtd).width()/(parseInt(j$(objtd).css("font-size"))+1)));}
                var textobj;
                var child=j$(objtd).children().filter(":first");
                if(child.length>0){textobj=child;}else{textobj=objtd};
                if(noCutStr[j]!="on"&&j$(objtd).children().length==0){
                    if(j$(objtd).attr("title")!=null){
                        EventCutStr3(j$(textobj),j$(objtd).attr("title"),tdw[j])
                    }
                }
            });
            
        });
        j$(window).resize(function(){
        j$.timer.set("portlet",function(){IntTableList();},500)
        })
    });
}

(function(j$){
    j$.fn.tableList = function(options, callback){
        var settings={
            width:"100%",
            height:"",
            //icon:"x1 y1",
            checkbox:false,
            radio:false,
            cutStr:true,
            tdWidth:{
                tdLast:"10%"
            }
        }
        var opts=j$.extend(settings,options);
        var j$tList=this;
        j$tList.each(function(x,tList){
            var j$tr=j$(tList).find("tr"),
                j$td=j$(j$tr).children("td");
                
            j$(tList).attr({"cellSpacing":"0","cellPadding":"0"}).data("inited","true").addClass("tableList").wrap("<div class='tableListWrap'/>");
            if(opts.width&&opts.width!="100%"){
                j$(".tableListWrap").width(opts.width);
            }
            if(opts.height){
                j$(".tableListWrap").height(opts.height);
            }
            j$tr.bind("mouseover",function(){j$(this).addClass("hover");});
            j$tr.bind("mouseout",function(){j$(this).removeClass("hover");});
            j$tr.filter(":even").addClass("even");
            j$tr.filter(":odd").addClass("odd");
            //alert(opts.tdWidth.length)
            //opts.tdWidth.toString().split().length
            for(var name in opts.tdWidth){
                //alert(opts.tdWidth[name]+" "+name);
            }
            if(j$tr.size()==1){j$(tList).parent().append("<div class='noContent'/>")}
            j$tr.each(function(i,tr){
                var j$td=j$(tr).children("td,th");
                j$td.each(function(j,td){
                    j$(td).attr("title",j$.trim(j$(td).text()));
                    j$(td).addClass("tdData"+(j+1));
                    if(j==0){j$(td).addClass("tdDataFirst");}
                    else if(j==j$td.length-2&&j$(j$td).length>3){j$(td).addClass("tdDataNTL");}
                    else if(j==j$td.length-1&&j$(j$td).length>2){j$(td).addClass("tdDataLast");}
                    if(j$(td).html()==""){j$(td).append("<font>&nbsp;</font>")}
                    
                    if(j$(td).children().size()==1&&(j$(td).children().first().is("a")||j$(td).children().first().is("font"))||j$(td).children().size()==0){
                        if(opts.cutStr){
                            j$(td).wrapInner("<div class='fontWrap'/>");
                        }
                        j$(td).find(".fontWrap").css({
                            //height:parseInt(j$(td).css("font-size"))+2
                        });
                    }
                    if(i==0){
                        for(var name in opts.tdWidth){
                            if(parseInt(name.replace(/^td/img,""))==j+1){
                                j$(td).width(opts.tdWidth[name]);
                            }
                        }
                        if(opts.tdWidth.tdLast&&j$td.length==j+1){
                            j$(td).width(opts.tdWidth.tdLast);
                        }
                        if(opts.tdWidth.tdFirst&&j==0){
                            j$(td).width(opts.tdWidth.tdFirst);
                        }
                    }
                    /*
                    if(i==1&&j==0){
                        for(w=0;w<j$(td).text().size();w++){
                            
                        }
                        alert(j$(td).text()[0].width())
                    }
                    */
                });
            });
            
            j$tr.each(function(i,tr){
                 //icon
                if(opts.icon){
                    if(j$(tr).parent().is("thead")){
                        j$(tr).prepend("<th class='tdIcon'><span class='icon' style='display:none' /></th>");
                    }else{
                        j$(tr).prepend("<td class='tdIcon'><span class='icon'/></td>");
                    }
                    j$(tr).find(".icon").addClass(opts.icon);
                    if(i==0){
                        j$(tr).find(".icon").parent().width(16);
                    }
                    //alert(j$(tr).html())
                }
                
                //checkbox
                if(opts.checkbox){
                    if(j$(tr).parent().is("thead")){
                        j$(tr).prepend("<th class='tdCheckbox'><input class='TL-checkbox' type='checkbox' title='\u5168\u9009'/></th>");
                    }else{
                        j$(tr).prepend("<td class='tdCheckbox'><input class='TL-checkboxSub' type='checkbox'/></td>");
                    }
                    if(i==0){
                        j$(tr).find(".TL-checkbox").parent().width(16).end().click(function(){
                            j$tr.find(".TL-checkboxSub").attr("checked",this.checked);
                            j$tr.find(".TL-checkboxSub").click(function(){
                                j$tr.find(".TL-checkbox").attr("checked",j$tr.find(".TL-checkboxSub").length == j$tr.find(".TL-checkboxSub:checked").length ? true : false);
                            });
                        });
                    }
                }
                //radio
                if(opts.radio){
                    if(j$(tr).parent().is("thead")){
                        j$(tr).prepend("<th class='tdRadio'><input class='TL-radio' type='radio' title='\u7A7A'/></th>");
                    }else{
                        j$(tr).prepend("<td class='tdRadio'><input class='TL-radio' type='radio'/></td>");
                    }
                    if(i==0){
                        j$(tr).find(".TL-radio").parent().width(16);
                    }
                    j$tr.find(".TL-radio").click(function(){
                        var eobj=this;
                        j$tr.find(".TL-radio").each(function(RI,radio){
                            if(eobj==radio){
                                j$(radio).attr("checked",function(){
                                    
                                });
                            }else{
                                j$(radio).attr("checked",false);
                            }
                        });
                    });
                }
               
                
                var j$td=j$(tr).children("td,th");
                j$td.each(function(j,td){
                    if(i==0){
                        if(j==0){
                            j$(td).css({"border-left-width":0});
                        }else if(j==j$td.size()-1){
                            j$(td).css({"border-right-width":0});
                        }
                    }
                });
                
            });
            //var tr0td=j$(j$tr).filter(":eq(0)").children("td");
            
        });
    }
})(jQuery);

(function(j$){
    j$.fn.tableForm = function(options, callback){
        var settings={
            width:"100%",
            height:"",
            TDTitleWidth:150,
            inputWidth:"100%",
            inputHeight:30,
            inputResize:true,
            inputFoceResize:true,
            hiddenColon:false,
            autoResize:true,
            className:""
        }
        var opts=j$.extend(settings,options);
        var j$tForm=this;
        j$tForm.each(function(x,tForm){
            var j$tf=j$(tForm),
            j$tbody=j$tf.children(),
            j$tr=j$tbody.children();
            j$tf.addClass(opts.className);
            if(j$tf.parent().is("td")){
                j$tf.wrap("<div class='tableFormWrap'/>");
            }else{
                j$tf.wrap("<div class='tableFormOuterWrap'><div class='tableFormWrap'/></div>");
            }
            if(j$tf.data("inited")!=null){return;}
            j$tf.data("inited","true");
            j$tf.css({height:opts.height}).addClass("tableForm");
            j$tr.each(function(i,tr){
                var j$td=j$(tr).children();
                j$td.each(function(j,td){
                    if(/^.*(:|\uFF1A)$/img.test(j$(td).text().replace(/\s/img,""))&&j$(td).children().size()==0){
                        j$(td)
                            .addClass("TF-Title")
                            .css({width:opts.TDTitleWidth})
                            .next()
                                .addClass("TF-Data");
                         if(/^\*.*$/img.test(j$(td).text())){
                            j$(td).html(j$(td).html().replace(/^\*/img,""));
                            j$(td).prepend("<font class='red'>*</font>");
                         }
                         if(opts.hiddenColon==true){
                            j$(td).html(j$(td).html().replace(/(:|\uFF1A)$/img,""));
                         }
                    }
                    if(j$(td).hasClass("TF-Data")){
                        j$(td).children(":input:not(:radio,:checkbox,:button)")
                             .css("height",opts.inputHeight)
                        .end()
                        .children("textarea").css({width:function(){
                            if(j$(this).attr("cols")!=null){return j$(this).attr("size","");}
                        },height:function(){
                            if(j$(this).attr("rows")==null){return opts.inputHeight*2}
                            else{return opts.inputHeight*parseInt(j$(this).attr("rows"))}
                        }})
                        
                    }
                })
            })
            
            resizeTL=function(j$tf,TDTitleWidth,inputResize,inputFoceResize){
                j$tf.parent().parent().css("height","");
                if(opts.width=="100%"){
                    j$tf.parent().width(j$tf.parent().parent().width());
                    j$tf.width(j$tf.parent().width()-2);
                }
                else{j$tf.width(opts.width);}
                j$tf.parent().show();
                if(j$("html").hasClass("IE8")||j$("html").hasClass("IE7")||j$("html").hasClass("IE6")){
                    j$("html").css("overflow-y","");
                }else{
                    j$("body").css("overflow-y","");
                }
                var j$tbody=j$tf.children(),
                    j$tr=j$tbody.children(),
                    FirstMaxRow=null;
                    RowMaxTD=0;
                var MaxRowTD=j$tr.first().children().size();
                j$tr.first().children().each(function(i,td){
                    if(j$(td).attr("colspan")){
                        MaxRowTD=MaxRowTD+parseInt(j$(td).attr("colspan"))-1;
                    }
                });
                
                j$tr.each(function(i,tr){
                    var j$td=j$(tr).children();
                    if(FirstMaxRow==null&&j$td.size()==MaxRowTD){FirstMaxRow=i;}
                    if(j$td.size()>RowMaxTD){RowMaxTD=j$td.size();}
                });
                if(FirstMaxRow==null){
                    j$tr.each(function(j,tr){
                        var j$td=j$(tr).children();
                        if(FirstMaxRow==null&&j$td.size()==RowMaxTD){FirstMaxRow=j;}
                    });
                }
                
                j$tr.each(function(i,tr){
                    var j$td=j$(tr).children("td");
                    var j$tdData=j$(tr).children(".TF-Data");
                    var j$tdDataSize=j$tdData.size();
                    if(j$tdDataSize>1&&i==FirstMaxRow){
                        j$tdData.each(function(j,tdData){
                            if(j!=j$tdDataSize-1){
                                j$(tdData).width(function(){
                                    return parseInt((j$tf.width()-(MaxRowTD-j$tdDataSize)*TDTitleWidth)/j$tdDataSize)-20;
                                });
                            }
                        });
                    }
                    j$td.each(function(j,td){});
                    j$tdData.each(function(j,tdData){
                        var ipt=j$(tdData).children(":input:not(:radio,:checkbox,:button)")
                            ,iptSize=ipt.size();
                        var iptAttrS=ipt.attr("size"),
                            iptAttrW=ipt.attr("width"),
                            iptStyleW=ipt.attr("style");
                            //alert("1 "+iptAttrS+"2 "+iptAttrW+"3 "+iptStyleW)
                            
                        if(iptSize==1&&((inputResize==true&&j$(tdData).children().size()==1&&iptAttrS==null&&(iptAttrW==null||j$("html").hasClass("IE7")||j$("html").hasClass("IE6"))&&iptStyleW.indexOf("width")==-1)||ipt.data("initwidth")=="true"||inputFoceResize)){
                            iptW=ipt.parent().width()-20;
                            if(iptW<50){iptW=50;}
                            ipt.width(iptW).data("initwidth","true").show();
                        }
                    });
                });
                j$tf.find(".tableFormWrap").each(function(ii,TFW){
                    j$(TFW).width(j$(TFW).parent().width());
                    j$(TFW).show(0);
                })
            }
            clearTLW=function(j$tf,TDTitleWidth,inputResize,inputFoceResize){
                j$tf.parent().parent().height(j$tf.parent().parent().height());
                j$tf.find(".tableFormWrap").hide(0);
                j$tf.parent().hide(0);
                var j$tbody=j$tf.children(),
                    j$tr=j$tbody.children();
                j$tr.each(function(i,tr){
                    var j$td=j$(tr).children("td");
                    var j$tdData=j$(tr).children(".TF-Data");
                    var j$tdDataSize=j$tdData.size();
                    j$tdData.each(function(j,tdData){
                        var ipt=j$(tdData).children(":input:not(:radio,:checkbox,:button)")
                            ,iptSize=ipt.size();
                        var iptAttrS=ipt.attr("size"),
                            iptAttrW=ipt.attr("width"),
                            iptStyleW=ipt.attr("style");
                        if(iptSize==1&&((inputResize==true&&j$(tdData).children().size()==1&&iptAttrS==null&&iptAttrW==null&&iptStyleW.indexOf("width")==-1)||ipt.data("initwidth")=="true"||inputFoceResize)){
                            ipt.width(10).hide();
                        }
                    });
                });
                if(j$("html").hasClass("IE8")||j$("html").hasClass("IE7")||j$("html").hasClass("IE6")){
                    if(j$("html").height()<j$(document).height()){j$("html").css("overflow-y","scroll");}
                }else{
                    if(j$("body").height()<j$(document).height()){j$("body").css("overflow-y","scroll");}
                }
                resizeTL(j$tf,TDTitleWidth,inputResize,inputFoceResize);
            }
            resizeTL(j$tf,opts.TDTitleWidth,opts.inputResize,opts.inputFoceResize);
            
            bindResize=function(j$tf,TDTitleWidth,inputResize,inputFoceResize){
                if(opts.width=="100%"&&j$("html").hasClass("IE6")==false&&opts.autoResize==true){
                    j$(window).one("resize",function(){
                        /*
                        var claSplit=j$tf.attr("class").split(" "),
                            claTheOne;
                        if(claSplit.length!=1){
                            j$.map(claSplit,function(n){
                                if(n!="tableForm"){claTheOne=n}
                            })
                        }else{
                            claTheOne=j$tf.attr("class");
                        }
                        */
                        if(j$tf.is(":hidden")){bindResize(j$tf,opts.TDTitleWidth,opts.inputResize,opts.inputFoceResize);return;}
                        j$tf.data("resizing","ture");
                        j$.timer.set(j$tf.attr("class")+x,function(){
                            clearTLW(j$tf,TDTitleWidth,inputResize,inputFoceResize);
                            setTimeout(function(){bindResize(j$tf,TDTitleWidth,inputResize,inputFoceResize)},300)
                        },300)
                        j$tf.data("resizing",null);
                    })
                }
            }
            bindResize(j$tf,opts.TDTitleWidth,opts.inputResize,opts.inputFoceResize);
        })
    }
})(jQuery);

function IntImgSlider(){
    var isw=j$(".imgSlider");
    j$(isw).wrap("<div class='imgSliderWrap'\/>");
    var li=j$(isw).children("li");
    var li0=j$(li).eq(0);
    var space=Math.round((j$(isw).width()-j$(li0).width())/(j$(li).length-1));
    var sliderAction=function(i,li,way){
        j$(li).each(function(j,li3){
            var fix=function(num){if(num==1){if(j<i){return 2}else if(j>=i){return 1}}else if(num==2){if(j<i){return j$(li3).width()}else if(j>=i){return 0}}}
            var img3=j$(li3).children("img");    var lidiv3=j$(li3).children(".desc").add(j$(li3).children(".trans"));
            if(j==i){j$(img3).fadeTo(200,1);}else{j$(img3).fadeTo(0,0.3);j$(lidiv3).stop(true,true).slideUp(0);}
            j$(li3).stop().animate({right:(j$(li).length-(j+fix(1)))*space+fix(2)},600,function(){
                if(j==i){j$(lidiv3).stop(true,true).delay(500).fadeIn(300);}
                if(way=="over"){if(j==i){j$(li3).addClass("focus");}else{j$(li3).removeClass("focus");}}
            });
        });
    }
    
    j$(li).each(function(i,li2){
        var text=j$(li2).children("div");
        var img=j$(li2).children("img");
        j$(text).addClass("desc");
        
        var h5=j$(text).children("h5");
        j$(h5).detach();
        j$(text).wrapInner("<span\/>");
        j$(text).prepend(h5);
        var det=j$(text).children("span");
        j$(det).text(j$.trim(j$(det).text()));
        EventCutStr(j$(h5),j$(h5).text(),15,j$(h5))
        EventCutStr(j$(det),j$(det).text(),32,j$(det))
        
        j$(li2).prepend("<div class='trans'/>");
        j$(li2).append("<div class='num'/>");
        var trans=j$(li2).children(".trans");
        j$(trans).css({width:j$(li2).width(),height:j$(text).outerHeight(true)})
        j$(trans).fadeTo(0,0.5);
        var num=j$(li2).children(".num");
        j$(num).text(i+1);   
        if(space<30){j$(num).width(space);}else{j$(num).width(30);}
        if(i==j$(li).length-1){j$(li2).css("border","0")}
        var lidiv=j$(text).add(trans);
        if(i!=0){j$(img).fadeTo(0,0.3);j$(lidiv).slideUp(0);}
        j$(li0).addClass("focus");
        j$(li2).css("z-index",(j$(li).length-i)+9);
        
        j$(li2).css("right",function(){return (j$(li).length-(i+1))*space;});
        j$(li2).bind("mouseenter",function(){sliderAction(i,li,"over");});
    });
    
    var iswIntv;
    function setIntv(){
            iswIntv=window.setInterval(function(){
            var i=0;
            var currli=j$(isw).children(".focus");
            j$(li).each(function(j,li2){if(li2==j$(currli)[0]){i=j;}});
            sliderAction(i,li);
            j$(currli).removeClass("focus");
            if(j$(currli).next().length!=0){j$(currli).next().addClass("focus");}else{j$(li0).addClass("focus");}
        },5000);
    }
    setIntv();
    j$(isw).bind("mouseenter",function(){window.clearInterval(iswIntv)});
    j$(isw).bind("mouseleave",function(){setIntv();});
}
j$(".imgSlider").ready(function(){IntImgSlider();});




function IntImgSwitch(){
    var imgs=j$(".imgSwitch");
    j$(imgs).each(function(i,imgs2){
        j$(imgs2).wrap("<div class='imgSwitchWrap'/>");
        var imgSW=j$(imgs2).parent();
        j$(imgSW).width(j$(imgs2).outerWidth()).prepend("<ul class='numWrap radioBtn '/>");
        var numWrap=j$(imgSW).children(".numWrap");
        var img=j$(imgs2).children();
        for(ix=0;ix<img.length;ix++){j$(numWrap).append("<li/>");}
        var num=j$(numWrap).children();
        
        var objPosi=function(trans,text,img2,num2){
            j$(trans).fadeIn(300);
            j$(text).fadeIn(300);
            j$(trans).css({width:j$(img2).width()-2,height:j$(text).outerHeight(true)-2,"z-index":9});
            j$(num2).parent().stop().animate({bottom:j$(trans).height()+10},200);
        }
        
        j$(num).each(function(inum,num2){j$(num2)
            .addClass("num"+inum)
            .append(inum+1).fadeTo(0,0.8)
            .bind("click",function(){
                var eobj=this;
                j$(img).each(function(ini,img2){
                    if(ini==inum){
                        j$(img2).fadeIn(500);
                        var trans=j$(img2).children(".trans");
                        var text=j$(img2).children(".desc");
                        objPosi(trans,text,img2,num2);
                    }else{
                        j$(img2).fadeOut(500);
                    }
                });
            });
        });
        
        setTimeout(function(){j$(img).eq(0).fadeIn(300,function(){j$(img).not(":eq(0)").show(0);});},1000);
        j$(img).each(function(j,img2){
            var imgf=j$(img2).children("img");
            j$(imgf).each(function(k,imgf){
               j$(imgf).width(j$(imgs2).width()-2).height(j$(imgs2).height()-2);
            });
            var jj=j+1;
            if(j<9){jj="0"+(j+1)}
            j$(img2).css("z-index",10-j);
            j$(img2).addClass("img"+jj);
            
            var text=j$(img2).children("div");
            if(j$(text).length>0){
                var h5=j$(text).children("h5");
                j$(text).addClass("desc");
                j$(h5).detach();
                j$(text).wrapInner("<span\/>").prepend(h5).hide();
                var det=j$(text).children("span");
                j$(det).text(j$.trim(j$(det).text()));
                //EventCutStr2(h5,j$(img2));
                //EventCutStr2(det,j$(img2),"","x2 +4");
                j$(img2).prepend("<div class='trans'/>");
                var trans=j$(img2).children(".trans");
                j$(trans).fadeTo(0,0.5).hide();
            }
        });
        
        var imgIntv;
        var interval=j$(imgs2).data("interval");
        if(interval==null){interval=5000;}
        function intvImgS(){
            imgIntv=window.setInterval(function(){
                var actnum=j$(numWrap).children(".active");
                if(j$(actnum).next().length!=0){j$(actnum).removeClass("active");j$(actnum).next().addClass("active");}
                else{j$(actnum).removeClass("active");j$(num).eq(0).addClass("active");}
                j$(img).each(function(j,img2){
                    var trans=j$(img2).children(".trans");
                    var text=j$(img2).children(".desc");
                    j$(num).each(function(jnum,num2){
                       if(j$(num2).hasClass("active")){
                           if(jnum==j){
                               j$(img2).fadeIn(500);
                               objPosi(trans,text,img2,num2);
                           }else{
                               j$(img2).fadeOut(500);
                           }
                       }
                    });
                });
            },interval);
        }
        intvImgS();
        j$(imgs2).parent(".imgSwitchWrap").hover(
            function(){window.clearInterval(imgIntv)},
            function(){intvImgS();}
        );
    });
}

function IntBrowserVer(){
    var judgeBE=function(){
        if(j$.browser.msie){
            var bv=parseInt(j$.browser.version);
            if(bv==7&&navigator.appVersion.indexOf("Trident\/4.0")>0){bv=8}
            j$("html").data("bv",bv);
            return "IE "+"IE"+bv;}
        else if(j$.browser.safari){return "safari";}
        else if(j$.browser.chrome){return "chrome";}
        else if(j$.browser.opera){return "opera";}
        else if(j$.browser.mozilla){return "mozilla";}}
    j$("html").addClass(judgeBE()+" initstyle");
}
IntBrowserVer();

function initMarkHidden(){
    findC=function(obj){
        j$(obj).children().each(function(i,obj){
            if(j$(obj).is(":visible")){
                findC(obj);
            }else{
                j$(obj).css({"border":"1px solid red"});
            }
        });
    }
    findC("body");
    
}
//j$(function(){initMarkHidden();});
//jquery Cookie
(function(j$, document) {
    var pluses = /\+/g;
    function raw(s) {return s;}
    function decoded(s) {return decodeURIComponent(s.replace(pluses, ' '));}
    j$.cookie = function(key, value, options) {
        // key and at least value given, set cookie...
        if (arguments.length > 1 && (!/Object/.test(Object.prototype.toString.call(value)) || value == null)) {
            options = j$.extend({}, j$.cookie.defaults, options);
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
        options = value || j$.cookie.defaults || {};
        var decode = options.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
            if (decode(parts.shift()) === key) {return decode(parts.join('='));}}
        return null;};
    j$.cookie.defaults = {};
})(jQuery,document);

//jquery settimeout
(function(j$,document){
    j$.timeout=function(speed,fun,obj){
        if(typeof obj.timeID!="undefined"){clearTimeout(obj.timeID);}
        obj.timeID=setTimeout(fun,speed);}
    j$.clearTimeout=function(obj){if(typeof obj.timeID!="undefined"){clearTimeout(obj.timeID);}}
})(jQuery,document);

(function(j$,document){
j$.timer = {
    data:   {}
,   set:    function(s, fn, ms){j$.timer.clear(s);j$.timer.data[s]=setTimeout(fn, ms);}
,   clear:  function(s){var t=j$.timer.data; if(t[s]){clearTimeout(t[s]);delete t[s];}}
}
})(jQuery,document);
//jquery indexOf
(function(j$,document){j$.indexOf=function(obj,str){if(obj.indexOf(str)>-1){return true}else{return false}}})(jQuery, document);







                   


