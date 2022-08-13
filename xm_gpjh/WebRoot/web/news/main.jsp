
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<?xml version="1.0" encoding="utf-8"?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>生殖健康咨询师培训网</title>
<link href="/web/news/images/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="../js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
	function turngo(){
		var now = document.getElementById("curPage").value;
		if(now==""||now.length<1||now=="0" ||now<0){
			alert("跳转页面数不能为空、'0'或负数!");
		}else{
		var now = document.getElementById("curPage").value;
		var goto =<s:property value='totalPage'/>;
		var got = parseInt(goto);
		var flag =(got-now)>=0;
		if(flag){
			var action ="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value='type'/>";
			document.news.action = action;	
			document.news.submit();	
		}else{
			document.getElementById("curPage").value="";
			document.getElementById("curPage").focus;
			alert("您要跳转页面不存在!");	
		}
	}
	}
	
	function setpagerows(){
		var number = document.getElementById("num").value;
		if(!(number==""||number=="0" || number<0)){
			var action = "/entity/first/firstInfoNews_toNewsList.action?type=<s:property value='type'/>";
			document.news.action = action;	
			document.news.submit();	
		}else{
			window.alert("每页显示条数不能为空、'0'或负数");
		}	
	}
	
	function sreachnews(){
			var action ="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value='type'/>";
			document.news.action = action;	
			document.news.submit();	
	}
	
</script>


</head>

<body bgcolor="#FFFFFF">
<s:form name="news">
<table width="100%" border="0" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#FFFFFF" class="boder">
	<tr>
    	<td colspan="2" align="left" class="font02">新闻标题: &nbsp; <input id="searchTitle" type="text" name="searchTitle" value='<s:property value='searchTitle'/>' /> &nbsp; <a href="#" onclick="sreachnews();"><img src="/web/news/images/GO_03.jpg" width="28" height="21" border="0" align="absmiddle"></a> &nbsp; </td>
  	</tr>
  <tr>
    <td width="80%" height="32" align="center" bgcolor="#869200" class="font01">新闻标题</td>
    <td width="20%" align="center" bgcolor="#869200" class="font01">发布时间</td>
  </tr>
  <s:if test="newsList.size > 0">
  <s:iterator value="newsList">
  <tr>
    <td class="font">◎<a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:if test="title.length()>20"><s:property value="title.substring(0,20)" />...</s:if><s:else><s:property value="title" /></s:else></a></td>
    <td align="center" class="font"><s:date name="reportDate" format="yyyy-MM-dd"/></td>
  </tr>
  </s:iterator>
  </s:if><s:else>
  	 <tr>
  	 	<td colspan="2" align="center" class="font02">没有任何符合条件信息</td>
 	 </tr>
  </s:else>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" align="center" class="font02"><s:property value="curPage"/>/<s:property value="totalPage"/>　<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=1&number=<s:property value='number'/><s:if test ="searchTitle.length() > 0">&searchTitle=<s:property value="searchTitle"/></s:if>">首　页</a> <s:if test="totalPage > 1"> <s:if test="curPage > 1">　<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="prePage"/>&number=<s:property value='number'/><s:if test ="searchTitle.length() > 0">&searchTitle=<s:property value="searchTitle"/></s:if>" >上一页</a> </s:if> <s:if test="totalPage-curPage > 0"><a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="nextPage"/>&number=<s:property value='number'/><s:if test ="searchTitle.length() > 0">&searchTitle=<s:property value="searchTitle"/></s:if>">下一页</a></s:if> </s:if>　<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="totalPage" />&number=<s:property value='number'/><s:if test ="searchTitle.length() > 0">&searchTitle=<s:property value="searchTitle"/></s:if>">末　页</a>
    　	 跳转到<input id="curPage" name="curPage" type="text" class="input">页 <a href="#" onclick="turngo();"><img src="/web/news/images/GO_03.jpg" width="28" height="21" border="0" align="absmiddle"></a> &nbsp; 每页显示:<input id="num" name="number" type="text" class="input" value="<s:property value="number"/>">条 <a href="#" onclick="setpagerows();"><img src="/web/news/images/GO_03.jpg" width="28" height="21" border="0" align="absmiddle"></a> &nbsp; 共有<s:property value="totalCount"/>篇文章  &nbsp; 共<s:property value="totalPage" />页
    </td>
  </tr>
</table>
</s:form>
</body>
</html>