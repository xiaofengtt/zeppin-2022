<%@ page contentType="text/html; charset=UTF-8"  import="java.util.*,java.math.*,com.enfo.intrust.tools.*" %>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=UTF-8">
<TITLE>purchase_info_black_filing.jsp</TITLE>
<SCRIPT>
function windowclose(){
window.close();
}
function clearCase(){
document.getElementById("case").value="";
}
function ajax(options) {
        options = options || {};
        options.type = (options.type || "GET").toUpperCase();
        options.dataType = options.dataType || "json";
        var params = formatParams(options.data);

        //创建 - 非IE6 - 第一步
        if (window.XMLHttpRequest) {
            var xhr = new XMLHttpRequest();
        } else { //IE6及其以下版本浏览器
            var xhr = new ActiveXObject('Microsoft.XMLHTTP');
        }

        //接收 - 第三步
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                var status = xhr.status;
                if (status >= 200 && status < 300) {
                    options.success && options.success(xhr.responseText, xhr.responseXML);
                } else {
                    options.fail && options.fail(status);
                }
            }
        }

        //连接 和 发送 - 第二步
        if (options.type == "GET") {
            xhr.open("GET", options.url + "?" + params, true);
            xhr.send(null);
        } else if (options.type == "POST") {
            xhr.open("POST", options.url, true);
            //设置表单提交时的内容类型
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }
    }
    
//格式化参数
function formatParams(data) {
        var arr = [];
        for (var name in data) {
            arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
        }
        arr.push(("v=" + Math.random()).replace(".",""));
        return arr.join("&");
 }

//立案
function filingBlack(){
    alert("已经将黑名单立案!");
	ajax({
        url: "<%=request.getContextPath()%>/marketing/black_filing.jsp",              //请求地址
        type: "GET",                       //请求方式
        data: { lian:"lian"},        //请求参数
        dataType: "json",
        success: function (response, xml) {
			alert("success");
            // 此处放成功后执行的代码
        },
        fail: function (status) {
            // 此处放失败后执行的代码
			
        }
    });
	window.close();
} 
</SCRIPT>
</HEAD>
<BODY>
<div>
<H1 style="text-align: left">筛查案件委派情况</H1>
<form action="<%=request.getContextPath()%>/transaction/contract/black_filing.jsp" method="post">
<SPAN style="font-size:26px;padding-left: 10px">工作组:</SPAN>
<select style="width: 270px">
<option>使用默认人员委派路径</option>
<option>财富组</option>
<option>风险与合规管理部</option>
<option>金融犯罪小组</option>
<option>信托业务托管与客户服务部</option>
<option>信息化管理部</option>
</select>
<br/>
<div style="padding-top: 40px;">
<TABLE>
<tr>
<td valign="middle">
<span style="font-size:26px">
  案件注释:
</span>
</td>
<td><textarea style="width: 250px;height:180px;" id="case"></textarea></td>
</tr>
</TABLE>
&nbsp;&nbsp;<BUTTON id="filing" onclick="filingBlack();">立案</BUTTON>&nbsp;&nbsp;&nbsp;&nbsp;
<BUTTON onclick="windowclose();">取消</BUTTON>&nbsp;&nbsp;&nbsp;&nbsp;
<BUTTON onclick="clearCase();">重置</BUTTON>
</div>
</form>
</div>
</BODY>
</HTML>
