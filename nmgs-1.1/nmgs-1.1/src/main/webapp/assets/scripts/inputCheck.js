$(function() {
	//验证字符长度
	$(".input-text").blur(function(){
		getByteLen($(this).val(),$(this).attr("length"),this);
	})
	//验证字符长度
	$(".input-text-nullable").blur(function(){
		checkNullableLen($(this).val(),$(this).attr("length"),this);
	})
	//验证为数字且长度限制
	$(".input-number").blur(function() {
		checkNumber($(this).val(),$(this).attr("length"),this);
	})
	//验证url
	$(".input-url").blur(function(){
		checkUrl($(this).val(),$(this).attr("length"),this);		
	})
})
//验证字符长度
function getByteLen(val,length,obj) {
	var len = 0;
    var val=val.replace(/(^\s*)|(\s*$)/g, "");
    if(val==""){
		$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("内容不能为空");
	    return false;
	}else{
	    for (var i = 0; i < val.length; i++) {
	      var a = val.charAt(i);
	      if (a.match(/[^\x00-\xff]/ig) != null) {
	        len += 2;
	      }
	      else {
	        len += 1;
	      }
	    }
	    if(len<=length){
	    	$(obj).removeAttr("style").parent().parent().find("b").html("");
	    	return true;
	    }else{
	    	$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("输入内容过长");
	    	return false;
	    }
	}
 }

function checkNullableLen(val,length,obj) {
	var len = 0;
    var val=val.replace(/(^\s*)|(\s*$)/g, "");
    for (var i = 0; i < val.length; i++) {
      var a = val.charAt(i);
      if (a.match(/[^\x00-\xff]/ig) != null) {
        len += 2;
      }
      else {
        len += 1;
      }
    }
    if(len<=length){
    	$(obj).removeAttr("style").parent().parent().find("b").html("");
    	return true;
    }else{
    	$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("输入内容过长");
    	return false;
    }
}
//校验数字
function checkNumber(val,length,obj){
	var val = val.replace(/(^\s*)|(\s*$)/g, "");
	var reg = /^\d+(?=\.\d+$|$)/; 
	var len=0;
	if (reg.test(val) == true) {
		for (var i = 0; i < val.length; i++) {
		    len += 1;
		}
		 if(len<=length){
	    	$(obj).removeAttr("style").parent().parent().find("b").html("");
	    	return true;
	    }else{
	    	$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("输入内容过长");
	    	return false;
	    }
	} else {
		if(val==""){
			$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("内容不能为空");
		    return false;
		}else{
			for (var i = 0; i < val.length; i++) {
			    len += 1;
			}
			$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("请输入数字");
	    	return false;
		}
	}
}

//校验url
function checkUrl(val,length,obj){
	var val = val.replace(/(^\s*)|(\s*$)/g, "");
	var len=0;
	var url=/(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
	url = new RegExp(url);
	if(val==""){
		$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("内容不能为空");
	    return false;
	}else{
		if(url.test(val) == true){
			$(obj).removeAttr("style").parent().parent().find("b").html("");
			return true;
		}else{
			for (var i = 0; i < val.length; i++) {
				var a = val.charAt(i);
				if (a.match(/[^\x00-\xff]/ig) != null) {
					len += 2;
				}
				else {
					len += 1;
				}
			}
			$(obj).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("URL格式不正确");
			return false;
		}
	}
}