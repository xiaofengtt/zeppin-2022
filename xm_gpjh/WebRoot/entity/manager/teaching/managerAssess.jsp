<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
<script>
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
		Ext.MessageBox.alert("信息提示","选项不能为空！");
		return false;
	}
}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<form action="/entity/teaching/pebzzManagerAssess_Assessing.action" name="frm" method="post" onsubmit="return checknone();">
<table  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center">
        <td height="20" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="40%" align="left">课程评估调查问卷</td>
            <td width="50%" align="center">课程名称: &lt;&lt; <font color="red"><s:property value="prBzzTchOpencourse.peBzzTchCourse.name"/></font>&gt;&gt; </td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td >
        	<input  type="hidden" name="opencourseid" value="<s:property value="prBzzTchOpencourse.id"/>"/>
        <table width="96%" border="0" cellpadding="0" cellspacing="0">
        	<s:iterator value="FeedBacklist" status="fblist">
        	<s:set value="'itm'+#fblist.Index" name="newitms" />
        		<tr valign="bottom" >
        			<s:set name="tname" value="#fblist.Index+1+'    .     '+name"/>
        			<td width="40%" colspan="4" height="10">
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
        		<textarea cols="70" rows="10" name="suggestion">请提出的你的个人建议和看法</textarea><br/>
        	</td>
        	</tr>
        	
          </table>
        </td>
      </tr>
      <tr>
        		<td align="center"><input type="submit" value="提交"/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        		<input type="reset" value="重填"/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        		<input type="button" onclick="javascript:window.history.back()" value="返回"/> </td>
        	</tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
       </table></td>
  </tr>
   
</table>
</form>
</body>
</html>