<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<div id="menu">
	<ul class="menuUl">
	</ul>
</div>

<script>

<%	
Boolean checkFlag = false;
String mothods = "";
if(session.getAttribute("methodList") != null){
	List<String> methodList = (List<String>) session.getAttribute("methodList");
	for(String method : methodList){
		mothods = mothods + method + "%%";
		if(method.contains("check")){
			checkFlag = true;
		}
	}
}
%>

var mothods = "<%=mothods%>";
var checkFlag = "<%=checkFlag%>";

//一级菜单
function menu() {
    $.ajax({
        type: "GET",
        url: "../rest/backadmin/menu/list",
        data: {
            level: 1
        },
        dataType: "text",
        async: true,
        success: function(data) {
    		var json = (new Function("", "return " + data.split("&&&")[0]))();
        	if(json.status=="SUCCESS"){
                var str = '<li class="lip" name="008"><a onclick="menuSecond2(\'999\',this)" style="background-color:#f3f3f3;">数据管理<img src="img/aleft.png" style="margin-left:2px;margin-top:-2px;"></a><ul class="ul" style="display: none;"><li class="lic" name="008004"><a href="./crossCheck.jsp" style="padding-left:40px;background-color: #fff" title="勾稽验证">勾稽验证</a></li>';
                str = str+'<li class="lic" name="008007"><a href="./dataUpdate.jsp" style="padding-left:40px;background-color: #fff" title="数据变更列表">数据变更列表</a></li>';
                str = str+'<li class="lic" name="008001"><a href="./inputList.jsp" style="padding-left:40px;background-color: #fff" title="数据导入">数据导入</a></li>';
                if(checkFlag == 'true'){
                	str = str+'<li class="lic" name="008002"><a href="./main.jsp" style="padding-left:40px;background-color: #fff" title="数据审核">数据审核</a></li>';
                	str = str+'<li class="lic" name="008003"><a href="./checkRollback.jsp" style="padding-left:40px;background-color: #fff" title="撤销审核">撤销审核</a></li>';
                }
                str = str + '</ul></li>';
                if (json.status == "SUCCESS") {
                	var strSec='<li class="lip" name="007"><a onclick="menuSecond2(\'999\',this)" style="background-color:#f3f3f3;">数据维护<img src="img/aleft.png" style="margin-left:2px;margin-top:-2px;"></a><ul class="ul" style="display:none;">';
                	for (var i = 0; i < json.data.length; i++) {
                		strSec += '<li class="lip" name="' + json.data[i].scode + '"><a onclick="menuSecond(\'' + json.data[i].scode + '\',this)" style="background-color:#e4f1ff;padding-left:40px;">' + json.data[i].title + '<img src="img/aleft.png" style="margin-left:2px;margin-top:-2px;"></a></li>';
                    }
                	str = strSec+'</ul></li>'+str;
                    $(".menuUl").prepend(str);
                    var scodeSecond = $("#scode").val();
                    var scodeFrist = scodeSecond.substring(0, 3);
                    $("#menu li").each(function() {
                        if ($(this).attr("name") == scodeFrist) {
                            $(this).children('a').click().addClass("lia");
                            if($(this).parent().attr("class")=="menuUl"){
                            	$(this).parent().children(":first").click();
                            }else{
                            	$(this).parent().parent().children(":first").click();
                            }
                            
                        }
                    });
                }
        	}else{
        		layer.msg(json.message, {
                    time: 2000
                },function(){
                	window.location.href="../views/login.jsp";
                });
        	}
            
        }
    });
}
menu();

// ----------------------------

//二级菜单
function menuSecond(scode, e) {
    var _this = $(e);
    if (_this.parent().find('ul').attr('class') == undefined) {
        $.ajax({
            type: "GET",
            url: "../rest/backadmin/menu/list",
            data: {
                level: 2,
                scode: scode
            },
            dataType: "text",
            async: true,
            success: function(data) {
                var json = (new Function("", "return " + data.split("&&&")[0]))();

                if (json.status == "SUCCESS") {
                    var str = '<ul class="ul">';
                    for (var i = 0; i < json.data.length; i++) {
                    	str += '<li class="lic" name="' + json.data[i].scode + '"><a href="../' + json.data[i].url + 'List.jsp" style="padding-left:50px;background-color: #fff" title=' + json.data[i].title + '>' + json.data[i].title + '</a></li>';
                    }
                    str += '</ul>';
                    _this.parent().append(str);
                    _this.parent().find('ul').show();
                    match();
                }else{
                	layer.msg(json.message, {
                        time: 2000
                    },function(){
                    	window.location.href="../views/login.jsp";
                    });
                }
            }
        });
        _this.parent().find("img").attr("src", "img/adown.png");
    } else {
        if (_this.parent().find("ul").css("display") == "block") {
            _this.parent().find("img").attr("src", "img/aleft.png");
            _this.parent().find("ul").css("display", "none");
            event.stopPropagation();
        } else {
            _this.parent().find("img").attr("src", "img/adown.png");
            _this.parent().find("ul").css("display", "block");
            event.stopPropagation();
        }
    }
}
function menuSecond2(scode, e) {
	var _this = $(e);
    if (_this.parent().children('ul').attr('class') == undefined) {       
        _this.parent().children(":first").next().attr("src", "img/adown.png");
    } else {
        if (_this.parent().children("ul").css("display") == "block") {
        	_this.parent().children(":first").find("img").attr("src", "img/aleft.png");
            _this.parent().children("ul").css("display", "none");
            event.stopPropagation();
        } else {
           	_this.parent().children(":first").find("img").attr("src", "img/adown.png");
            _this.parent().children("ul").css("display", "block");
            event.stopPropagation();
        }
    }
}
</script>
