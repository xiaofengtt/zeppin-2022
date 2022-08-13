<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="javascript">
function selectone(obj,cd){
	var len =document.getElementsByName(obj);
	for(k=0;k<len.length;k++){
		if(len[k]!=cd){
			len[k].checked=false;
		}else{
			len[k].checked=true;
		}
	}
}

function checknone(){
	var num =0;
	var test = document.all.tags("input");
	for(m=0;m<test.length;m++){
		if(test[m].type=="checkbox"){
			if(test[m].checked){
				num++;
			}
		}
	}
	if(num!=12){
		window.alert("选项不能为空！");
		return false;
	}
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
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style>
</head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<form action="/entity/workspaceStudent/bzzAssess_Assessing.action" name="frm" method="post" onsubmit="return checknone();">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
   <td width="120" valign="top"></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程评估问卷</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="40%" align="left">课程评估调查问卷</td>
            <td width="45%" align="left">课程名称: &lt;&lt; <font color="red"><s:property value="prBzzTchOpencourse.peBzzTchCourse.name"/></font>&gt;&gt; </td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td >
        	<input  type="hidden" name="opencourseid" value="<s:property value="prBzzTchOpencourse.id"/>"/>
        <table width="96%" border="0" cellpadding="0" cellspacing="0">
        	<s:iterator value="FeedBacklist" status="fblist">
        	<s:set value="'itm'+#fblist.Index" name="newitms" />
        		<tr valign="top" id="nbox" >
        			<td height="5" width="60%">&nbsp;</td>
        			<s:set name="tname" value="#fblist.Index+1+'    .     '+name"/>
        			<td width="40%" colspan="4">
        			<s:if test="6>#fblist.getIndex()">
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'很好','好','一般','较差','差'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        		<s:if  test="6==#fblist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'有','无'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        		<s:if  test="7==#fblist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏长','偏短'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        		<s:if  test="8==#fblist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏深','偏浅'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        		<s:if  test="11>#fblist.getIndex()&&#fblist.getIndex()>8">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','不合适'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        		<s:if  test="11==#fblist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏难','偏易'}" onclick="selectone(this.name,this);"/>
        		</s:if>
        			</td>
        		</tr>
        	<tr>
        		<td colspan="10"><img src="/entity/bzz-students/images/two/7.jpg" width="852" height="4" /></td>
        	</tr>
        	</s:iterator>
        	<tr>
        	<td colspan="10" align="left">
        		<textarea cols="70" rows="10" name="yaoqiu">您是否还有相关课程的学习要求，若有，请写出。</textarea><br/>
        	</td>
        	</tr>
        	<tr>
        	<td colspan="10" align="left">
        		<textarea cols="70" rows="10" name="suggestion">您对本门课程和教师的意见和建议</textarea><br/>
        	</td>
        	</tr>
        	<tr>
        		<td align="right"><input type="submit" value="提交"/>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; </td>
        		<td align="left"><input type="reset" value="重填"/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</td>
        	</tr>
          </table>
        </td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
       </table></td>
  </tr>
     
</table>
</form>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>