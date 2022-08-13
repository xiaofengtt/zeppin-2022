/*cetv
 version 1.0	
 create 2014-4-4
*/
function leftside(dataid,htmlid) {
	var name = $('#pagename').val();
	$.getJSON('../navLoadRoleFunction?name='+name, function(r) {
		if(r.Status == 'success' && r.Records.length > 0) {
		    var template = $('#'+dataid).html();
		    var html = Mustache.render(template, r);
		    $('#'+htmlid).html(html);
		}else {
			location.href = '../admin/login.html';
		}
	})
}



function loginis() {
	if($.cookie('islogin')) {
		var _ = $.cookie('loginname').split('###');
		$('#loginname').html(_[0]+'老师');
		var photo = '';
		if($.cookie('photo')!=''){
			photo = $.cookie('photo');
		}
		$('#photo').attr('src','../'+photo);
	}else {
		if(url('filename') != 'login') {
			location.href = location.href.replace(url('filename'),'login');
		}else {
			location.href = location.href.replace('index','main');
		}
		
	}
}

$(function(){
	//页面初始化
//	leftside('leftTpl','mainLeftUl');
	
	$(".logOut").click(function(){
		$.getJSON("../admin/ssoLogout", function(r) {
			if(r.Status == 'success') {
				location.href = '../admin/login.html';
			}else {
				alert('服务端出错！请稍后重试！');
			}
		});
	});
	
	if($("#pagename").val() == "main"){
		$(".examManage").css({"background":"#E0615F url(../img/examManageLight.png) 15px center no-repeat","background-size":"22px auto","color": "#FFFFFF"});
		$(".examManage").parent().find('ul').find('a').eq(0).css({"color":"#E0615F"});
	}
	if($("#pagename").val() == "teachersMessage"){
		$(".teacherManage").css({"background":"#E0615F url(../img/teacherManageLight.png) 15px center no-repeat","background-size":"22px auto","color":"#FFFFFF"});
		$(".teacherManage").parent().find("ul").find("a").eq(0).css({"color":"#E0615F"});
	}
	if($("#pagename").val() == "teachersAuditing"){
		$(".teacherManage").css({"background":"#E0615F url(../img/teacherManageLight.png) 15px center no-repeat","background-size":"22px auto","color":"#FFFFFF"});
		$(".teacherManage").parent().find("ul").find("a").eq(1).css({"color":"#E0615F"});
	}
	if($("#pagename").val() == "historyInfo"){
		$(".examHistoryManage").css({"background":"#E0615F url(../img/examHistoryManageLight.png) 15px center no-repeat","background-size":"22px auto","color":"#FFFFFF"});
		$(".examHistoryManage").parent().find("ul").find("a").css({"color":"#E0615F"});
	}
	
	loginis();
	
	//未登录跳转
	function getCurrent(){
		var mUrl = '../admin/examGetCurrent?';
		$.get(mUrl, function(r) {
			if (r.Message == '用户未登录！') {
			   window.location.href="../admin/login.html";
			}
		}).done(function(r) {
			
		});
	}
	getCurrent()
})

//loginis();
function template(url,dataid,htmlid) {
	$.getJSON(url, function(r) {
		if(r.Status == 'success' && r.Records.length > 0) {
		    var template = $('#'+dataid).html();
		    var html = Mustache.render(template, r);
		    $('#'+htmlid).html(html);
		}else {
			alert('服务端出错！请稍后重试！');
		}
	});
}

var set_cookie = function(h, g, e, f) {
    var c = new Date();
    c.setTime(c.getTime() + ((g || 30) * 24 * 60 * 60 * 1000));
    var b = "; expires=" + c.toGMTString();
    for (var d in h) {
        document.cookie = d + "=" + h[d] + b + "; domain=" + (e || "") + "; path=" + (f || "/")
    }
};
function get_cookie(d) {
    var f = d + "=";
    var b = document.cookie.split(";");
    for (var e = 0; e < b.length; e++) {
        var g = b[e];
        while (g.charAt(0) == " ") {
            g = g.substring(1, g.length)
        }
        if (g.indexOf(f) == 0) {
            return g.substring(f.length, g.length).replace(/\"/g, "")
        }
    }
    return null
}


//help function

function toCheck(t){
	if($(t).is(':checked')){
		$(t).parent().css({'background': '#FFC'});
	}
	else{
		$(t).parent().removeAttr('style');
	}
}
function toCheckAll(t) {
	toCheck(t);
	var input = $('input.listcheck');
	if($(t).is(':checked')){
		input.parent().css({'background': '#FFC'});
	}
	else{
		input.parent().removeAttr('style');
	}
	input.prop('checked', $(t).prop('checked'));
}

function updateURLParameter(url, param, paramVal)
{
    var TheAnchor = null;
    var newAdditionalURL = "";
    var tempArray = url.split("?");
    var baseURL = tempArray[0];
    var additionalURL = tempArray[1];
    var temp = "";

    if (additionalURL) 
    {
        var tmpAnchor = additionalURL.split("#");
        var TheParams = tmpAnchor[0];
            TheAnchor = tmpAnchor[1];
        if(TheAnchor)
            additionalURL = TheParams;

        tempArray = additionalURL.split("&");

        for (i=0; i<tempArray.length; i++)
        {
            if(tempArray[i].split('=')[0] != param)
            {
                newAdditionalURL += temp + tempArray[i];
                temp = '&';
            }
        }        
    }
    else
    {
        var tmpAnchor = baseURL.split("#");
        var TheParams = tmpAnchor[0];
            TheAnchor  = tmpAnchor[1];

        if(TheParams)
            baseURL = TheParams;
    }

    if(TheAnchor)
        paramVal += "#" + TheAnchor;

    var rows_txt = temp + "" + param + "=" + paramVal;
    return baseURL + "?" + newAdditionalURL + rows_txt;
}

function sortasc(name) {
	var order = (url('?sort').split('-')[1] == 'asc') ? 'desc' : 'asc';
	window.location = url('protocol') + '://' + url('hostname')
			+ url('path') + '?sort=' + name + '-' + order;
}

function infotip(obj,message) {
	if(obj.offset().top == 0 ) {
		obj = obj.parents('td');
	}
	var tt_offset = obj.offset();
	var tt_top = tt_offset.top;
	var tt_left = tt_offset.left;
    var text_content=obj.text() +'成功';
    if(message!=undefined&&message!='') {
       text_content = message;
    }
	$('body').append('<div class="favWrap"><div class="bg"><div class="inner"><p class="info">'+ text_content+'</p></div></div></div>');
	var new_div_left = tt_left - (($('.favWrap').outerWidth() - obj.outerWidth()) / 2);
	var new_div_top = tt_top - $('.favWrap').outerHeight() + 20;
	$('.favWrap').css({'top':new_div_top+'px', 'left':new_div_left+'px'})
	.animate({'top':'-=20px','opacity':1},function(){$(this).delay(1500).animate({'top':'+=20px','opacity':0},function(){$(this).remove();})});
};


$(function(){
	$.fn.delbtn = function(){
		$(this).click(function(){
	        var obj = $(this);
			var cUrl = $(this).attr('data-url');
			$.getJSON(cUrl,{'id':obj.attr('data-id')},function(ret){
				if(ret.Result == 'OK'){
					$('#status_'+obj.attr('data-id')).text('已删除');
					infotip(obj,ret.Message);
					$('#list_'+obj.attr('data-id')).remove();
				}else {alert('失败,'+ret.Message);}
			})
		})
	};
	$.fn.releasebtn = function(){
		$(this).click(function(){
	        var obj = $(this);
			var cUrl = '../admin/projectBase_changeStatus.action';
			var method = (typeof(obj.attr('data-acttype')) == 'undefined') ? 'ddd' : obj.attr('data-acttype');
			$.getJSON(cUrl,{'id':obj.attr('data-id'),'method': method},function(ret){
				if(ret.Result == 'OK'){
					$('#status_'+obj.attr('data-id')).text(obj.text());
					infotip(obj,ret.Message);
				}else {alert('失败,'+ret.Message);}
			})
		})
	};
	
	
	$.fn.searchbox = function(options) {
		var defaults = {
            placeholder: '.search_form label.placeholder',
            dropdown: '.show_dropdown'
        };
        var opt = $.extend(defaults, options);
		var placeholder = $(opt.placeholder);
		var dropdown = $(opt.dropdown);
		dropdown.hover( function() {
			$( '.search .show_dropdown .dropdown' ).show();
		});
	
		var setActions = function( label ){
			var eventTrackerId = label.attr( 'for' );
			var id = '#' + eventTrackerId;
			var searchInput = $( id );
			label.addClass( 'overlabel' );
			searchInput.blur( function () {
				if ( ! searchInput.val() ) {
					label.removeClass( 'over' );
				}
			});
			searchInput.focus( function () {
				if ( ! searchInput.val() ) {
					label.addClass( 'over' );
				}
			});
			if ( searchInput.val() ) {
				label.addClass( 'over' );
			}
			var labelList = dropdown.find('label');
			var i;
			for ( i = 0; i < labelList.length; i++ ) {
				$(labelList[i]).click( function () {
					label.text($( this ).text());
					$( '#' + $( this ).attr('for') ).click();
					$( '.search .show_dropdown .dropdown' ).hide();
					return false;
				})
			}
		}
		//init
		setActions(placeholder);
	};
	
//	$(window).resize(function () {
//		$('.sidebar,.main').height($(window).height()-110);$('.qtree-bd').height($(window).height()-203);
//	})
//	//$('.sidebar').height($(window).height()-110);
//	
//	$('.sidebar,.main').height($(window).height()-110);
//	$('.qtree-bd').height($(window).height()-203);
	
	
	$('.question_meta_bar').length && $( ".main" ).scroll(function() {
		var th = $(this),top = th.scrollTop(),wd = th.find('.main-inner').width();
		if(top > 0) {
			$('.question_meta_bar').addClass('meta_bar_fixed').css('width',wd);
		}else {
			$('.question_meta_bar').removeClass('meta_bar_fixed').removeAttr('style');
		}
	})
	
	/*返回顶部*/
	$(".cReturnTop").length < 1 && $("body").append('<div class="cReturnTop"><span class="glyphicon glyphicon-chevron-up"></span></div>'), $('.main').scroll(function() {
            $('.main').scrollTop() > 300 ? $(".cReturnTop").show() : $(".cReturnTop").hide();
        }), $(".cReturnTop").click(function() {
            return $(".main").animate({scrollTop: 0}, 1e3), !1
        })
        
        
	$('.delbtn').delbtn();
	$('.releasebtn').releasebtn();
	$('#searchbox').searchbox();
	//$('#container').initCntHeight();
})


