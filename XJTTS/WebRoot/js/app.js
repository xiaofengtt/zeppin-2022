/*xj
 version 1.0	
 create 2014-4-4
*/
//help function

function toCheck(t){
	if($(t).is(':checked')){
		$(t).parent().css({'background': '#fff'});
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
	$(".clickMore").hover(function(){
		$(this).find("div").toggle();
	})
		$(".btnMyself").hover(function(){
			$(this).find("p").stop().animate({"top":"32px"},1000).fadeIn(10);
		},function(){
			$(this).find("p").stop().fadeOut(30).animate({"top":"56px"},100);
		})
	
	$(".btnMyself").hover(function(){
		$(this).find("p").stop().animate({"top":"43px"},100).fadeIn(10);
	},function(){
		$(this).find("p").stop().fadeOut(30).animate({"top":"60px"},300);
	})
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
	
	//$('.sidebar').height($(window).height()-81);
	/*$.fn.initCntHeight = function() {
		var ch = $('#container').outerHeight(),
			wh = $(window).height(),
			nh = $('.navbar').outerHeight(),
			bh = $('.bs-footer').outerHeight();	
		if(ch < (wh - nh))  $('#container').outerHeight(parseInt(wh - nh - bh));
	}*/
	
	$('.delbtn').delbtn();
	$('.releasebtn').releasebtn();
	$('#searchbox').searchbox();
	//$('#container').initCntHeight();
})

//数字每三位用逗号隔开
function formatNum(str){
	var newStr = "";
	var count = 0;
	str=str.toString();
	if(str.indexOf(".")==-1){
	   for(var i=str.length-1;i>=0;i--){
		 if(count % 3 == 0 && count != 0){
		   newStr = str.charAt(i) + "," + newStr;
		 }else{
		   newStr = str.charAt(i) + newStr;
		 }
		 count++;
	   }
	   //str = newStr + ".00"; //自动补小数点后两位
	   str = newStr;
	}
	else{
	   for(var i = str.indexOf(".")-1;i>=0;i--){
		 if(count % 3 == 0 && count != 0){
		   newStr = str.charAt(i) + "," + newStr;
		 }else{
		   newStr = str.charAt(i) + newStr; //逐个字符相接起来
		 }
		 count++;
	   }
	   str = newStr + (str + "00").substr((str + "00").indexOf("."),4);//小数点后加上小数点保留几位
	 }
	return str;
}

function getCookie(c_name){
	if (document.cookie.length>0){
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1){ 
			c_start=c_start + c_name.length+1 
			c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return ""
}

function setCookie(c_name,value,expiredays){
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}


