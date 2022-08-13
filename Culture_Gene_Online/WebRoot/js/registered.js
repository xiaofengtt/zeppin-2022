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
	$(".username").blur(function(){
		UserName();
	});
	$(".password").blur(function(){
		Password();
	});
	$(".confimpassword").blur(function(){
		confimpassword();
	});
	$(".email").blur(function(){
		email();
	});
	$(".phone").blur(function(){
		phone();
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
	var val=$.trim($(".username").val()) ;
	if(val==""){
		$(".username").addClass("light");
		return false;
	}else{
		$(".username").removeClass("light");
		return true;
	}
	
}

//验证密码
function Password(){
	var val=$.trim($(".password").val()) ;
	if(val==""){
		$(".password").addClass("light");
		return false;
	}else{
		if($(".confimpassword").val()!=""&&val!=$(".confimpassword").val()){
			$(".confimpassword").addClass("light");
			$(".prompt").text("两次密码不一致");
			return false;
		}else{
			$(".password").removeClass("light");
			return true;
		}
		
	}
	
}

//验证确认密码
function confimpassword(){
	var val=$.trim($(".confimpassword").val());
	if(val==""){
		$(".confimpassword").addClass("light");
		return false;
	}else{
		if(val==$(".password").val()){
			$(".confimpassword").removeClass("light");
			$(".prompt").text("");
			return true;
		}else{
			$(".confimpassword").addClass("light");
			$(".prompt").text("两次密码不一致");
			return false;
		}
		
		
	}
	
}

//验证邮箱
function email(){
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
	var val=$.trim($(".email").val());
	if(val!=""){
		if(myreg.test(val)){
			$(".email").removeClass("light");
			return true;
		}else{
			$(".email").addClass("light");
			return false;
		}
	}
	return true;
	
}

//验证手机号
function phone(){
	var CellPhone = /(^(1)\d{10}$|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
	var val=$.trim($(".phone").val());
	if(CellPhone.test(val)){
		$(".phone").removeClass("light");
		return true;
	}else{
		$(".phone").addClass("light");
		return false;
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

//注册
function submit(){
	$(".warning").html("");
	var rs1 = UserName();
    var rs2 = Password();
    var rs3 = confimpassword();
    var rs4 = email();
    var rs5 = phone();
    var rs6 = code();
    if (rs1 && rs2 && rs3 && rs4 && rs5 && rs6) {
        $.ajax({
            type: "POST",
            url: "../admin/userRegist",
            data: { name: $.trim($(".username").val()),password:$.trim($(".password").val()),confimPassword:$.trim($(".confimpassword").val()),phone:$.trim($(".phone").val()),email:$.trim($(".email").val()),company:$.trim($(".company").val()),job:$.trim($(".job").val()),authcode:$(".code").val()},
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
					$.cookie('loginid',json.Records.name,{ expires: 1, path: '/' });
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







