<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生殖健康咨询师培训网</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
   	<script> 
	function search(){
  	 document.forms[0].submit(); 
   
   }
   </script>
    <style type="text/css">
<!--
.STYLE4 {
	font-size: 16px;
	font-weight: bold;
}
-->
    </style>
</head>
  
 <body >
<table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="1002" height="210" valign="top"><iframe id="top" name="top" frameborder="0" scrolling="no" src="/web/bzz_index/top.jsp" width="1002" height="210"></iframe></td>
  </tr>
  <tr>
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104">
		<!--	<img src="/web/news/images/new01.jpg" width="104" height="29" /> -->
		<span class="newsfontcenter1  STYLE4">
		<s:if test="type=='_xygg'">学院公告</s:if>
		<s:elseif test="type=='_wydt'">网院动态</s:elseif>
		<s:elseif test="type=='_wjdt'">网教动态</s:elseif>
		<s:elseif test="type=='_zcfg'">政策法规</s:elseif>
		<s:else>&nbsp;</s:else><s:property value="newsList.get(0).peInfoNewsType.name"/></span> </td>
            <td width="798" align="right">
			  <form action="/entity/first/firstInfoNews_search.action?type=<s:property value="type"/> " method="post">
			  <table width="280" border="0" cellspacing="0" cellpadding="0" style=" margin-right:5px; margin-bottom:3px;">
              <tr>
                <td width="80" class="orangefont" align="right">文章搜索</td>
                <td width="140"><input name="searchTitle" id="searchTitle" type="text" class="text" value="<s:property value="searchTitle"/>"/></td>
                <td width="60" align="left" style="padding-left:2px;"><img src="/web/news/images/search.jpg" width="39" height="17" border="0" style="cursor: hand"  onclick="search()"/></td>
              </tr>
            </table></form>
            </td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:18px 0 3px 0px;border-bottom:2px solid #F2F2F2;" >
          <tr >
            <td width="80"  class="newstitle rightborder">序号</td>
            <td width="680" class="newstitle rightborder">新闻标题</td>
            <td width="120" class="newstitle rightborder">日期</td>
            <td width="100" class="newstitle">浏览量</td>
          </tr>
        </table>
		<s:iterator value="newsList">
		<table width="980" border="0" cellspacing="0" cellpadding="0" height="25"  class="newslist ">		
          <tr >
            <td width="80"  class="newsfontcenter1 "><s:property value="confirmManagerId"/></td>
            <td width="680" class="newsfontleft newstyle1"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" escape="false"/>   </a> <s:if test="note == 'new'"><img src="/web/images/new.gif" width="26" height="6" /></s:if></td>
            <td width="120" class="newsfontcenter "><s:date name="reportDate" format="yyyy-MM-dd HH:mm:ss" /></td>
            <td width="100" class="newsfontcenter"><s:property value="readCount"/></td>
          </tr>
         </table>  			
		</s:iterator>
		<!-- table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:15px 0px;">
		  <tr>
			<td align="center" class="moreblack" > <a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=1">首页</a>
		<s:if test="curPage == 1">
			<s:if test="totalPage > 1">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="nextPage"/>">下一页</a>  
			</s:if>
		</s:if>
		<s:elseif test="curPage > 1">
			<s:if test="totalPage > curPage">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="prePage"/>">上一页</a>   
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="nextPage"/>">下一页</a>
			</s:if>
			<s:elseif test="curPage == totalPage">
				<a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="prePage"/>">上一页</a>  
			</s:elseif>
		</s:elseif>		
			
			  <a href="/entity/first/firstInfoNews_toNewsList.action?type=<s:property value="type"/>&curPage=<s:property value="totalPage" />">末页</a>   
			共<span class="orange"><s:property value="totalCount" /></span>篇文章 <span class="orange"><s:property value="number" /></span>篇文章/页  
			页次：<span class="orange"><s:property value="curPage" /></span>/<s:property value="totalPage" />页</td>
		  </tr>
		</table-->
		<s:if test="searchTitle == null">		
		<%@ include file="/web/pub/dividepage.jsp" %>
		</s:if>
		<s:else>
		<%@ include file="/web/pub/searchDividepage.jsp" %>
		</s:else>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="1002"  height="68" valign="top">
	<iframe id="footer" name="footer" frameborder="0" scrolling="no" src="/web/footer.jsp" width="1002"  height="68" ></iframe>	</td>
  </tr>
</table>

<map name="Map" id="Map">
  <area shape="rect" coords="272,94,391,127" href="#" />
  <area shape="rect" coords="267,16,386,49" href="#" />
<area shape="rect" coords="73,94,194,128" href="#" />
<area shape="rect" coords="72,15,191,48" href="#" /><area shape="rect" coords="176,55,272,91" href="#" /></map>
</body>
</html>
