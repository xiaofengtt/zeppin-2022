$(function(){
	hotSearch();
	var str="";
	$.get('../admin/categoryList?level=1&Status=2',function(r){
		var nationalHtml ="";
		for ( var i = 0, l = r.Records.length; i < l; i++ ) {
			str+="<li><a href='list.html?parent="+encodeURI(r.Records[i].name)+"&&id="+r.Records[i].id+"'>"+r.Records[i].name+"</a></li>";
			//$(".classify").html().attr({"name":r.Records[i].id,"href":encodeURI("list.html?parent="+r.Records[i].name+"&&id="+r.Records[i].id)});
		}
		$(".classify").html(str);
	});
	$("#banner .banner_inner ul.navList li:eq(0)").css("margin-right","20px")
	$("#banner .banner_inner ul.navList li:eq(1)").css("padding-left","16px").hover(function(){
		$("#banner .banner_inner ul.navList li .classify").css("display","block");
	},function(){
		$("#banner .banner_inner ul.navList li .classify").css("display","none");
	})
	$(".loginname").blur(function(){
		UserName();
	});
	$(".password").blur(function(){
		Password();
	});
	$(".code").blur(function(){
		code();
	});
	
	if($.cookie('islogin')=="true"){
		$("#banner .banner_inner ul.nav").css("display","none");
		$("#banner .banner_inner ul.loggedLn").css("display","block");
		$("#banner .banner_inner ul.loggedLn .userName").text($.cookie('loginusername'));
		$("#banner .banner_inner ul.loggedLn .userName").click(function(){
			window.location.href="PersonalCenter.html";
		});
	}
	$("#banner .banner_inner .Search a.search").click(function(){
		if($("#banner .banner_inner .Search input.search_input").val()==""){
			return false;
		}else{
			window.location.href="Search.html?search="+encodeURI($("#banner .banner_inner .Search input.search_input").val());
		}
	});
})
//刷新验证码
function RefreshCode(obj) {
    $("#" + obj).attr("src", "../admin/ssoAuthImg?" + new Date());
}

//验证用户名
function UserName(){
	var val=$.trim($(".loginname").val()) ;
	if(val==""){
		$(".loginname").addClass("light");
		return false;
	}else{
		$(".loginname").removeClass("light");
		return true;
	}
	
}

//验证密码
function Password(){
	var val=$.trim($(".password").val());
	if(val==""){
		$(".password").addClass("light");
		return false;
	}else{		
		$(".password").removeClass("light");
		return true;
		
	}
	
}

//验证码
function code(){
	var val=$.trim($(".code").val());
	if(val==""){
		$(".code").addClass("light");
		return false;
	}else{
		$(".code").removeClass("light");
		return true;
	}
}


//登录
function submit(){
	$(".warning").html("");
	var rs1 = UserName();
    var rs2 = Password();
    var rs3 = code();
    if (rs1 && rs2 && rs3) {
        $.ajax({
            type: "POST",
            url: "../admin/ssoLogin",
            data: { loginname: $.trim($(".loginname").val()),password:$.trim($(".password").val()),authcode:$(".code").val(),role:"3"},
            dataType: "text",
            async: true,
            success: function (data) {
            	//console.log(data);
            	var json = (new Function("", "return " + data.split("&&&")[0]))();
              	if(json.Status=="fail"){
              		$(".warning").html(json.Message);
              		if(json.Message=="验证码输入不正确！"){
              			$("#validateCode").click();
              		}
              	}else if(json.Status=="success"){
              		$.cookie('islogin','true',{ expires: 1, path: '/' });
					$.cookie('loginusername',json.Records.name,{ expires: 1, path: '/' });
					$.cookie('loginid',json.Records.id,{ expires: 1, path: '/' });
              		window.location.href="index.html";
              		
              	}
            }
        });

    }
}

//退出
function userLogout(){
	$.ajax({
        type: "POST",
        url: "../admin/userLogout",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.Status=="success"){
        		$.cookie('islogin','',{ expires: 0, path: '/' });
				$.cookie('loginusername',"",{ expires: 0, path: '/' });
				$.cookie('loginid',"",{ expires: 0, path: '/' });
				window.location.href=window.location.href;
        	}
        }
    });
}

//获取热门搜索
function hotSearch(){
	var src="";
	$.ajax({
        type: "POST",
        url: "../admin/webGetKeyword?number=4",
        data: { },
        dataType: "text",
        async: true,
        success: function (data) {
        	//console.log(data);
        	var json = (new Function("", "return " + data.split("&&&")[0]))();
        	for(i=0;i<json.Records.length;i++){
        		if(i==json.Records.length-1){
        			src+=json.Records[i];
        		}else{
        			src+=json.Records[i]+"、";
        		}
        		
        	}
        	$(".hotSearch").append(src);
        }
    });
}




