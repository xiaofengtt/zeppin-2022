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
		<script type="text/javascript" src="/FCKeditor/fckeditor.js">
		</script> 
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
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
      <s:if test="peBzzAssesslist.size>0">
      <tr valign="top">
        <td >
        <table width="96%" border="0" cellpadding="0" cellspacing="0">
        	<s:iterator value="peBzzAssesslist" status="pelist">
        	<s:set value="'itm'+#pelist.Index" name="newitms" />
        		<tr valign="bottom" >
        			<s:set name="tname" value="#pelist.Index+1+'    .     '+peBzzCourseFeedback.name"/>
        			<td width="40%" colspan="4" height="10">
        			<s:if test="6>#pelist.getIndex()">
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'很好','好','一般','较差','差'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        		<s:if  test="6==#pelist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'有','无'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        		<s:if  test="7==#pelist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏长','偏短'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        		<s:if  test="8==#pelist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏深','偏浅'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        		<s:if  test="11>#pelist.getIndex()&&#pelist.getIndex()>8">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','不合适'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        		<s:if  test="11==#pelist.getIndex()">	
        			<s:checkboxlist name="%{#newitms}" label="%{#tname}" list="{'合适','偏难','偏易'}" value="assess" onclick="selectone(this.name,this);" disabled="true"/>
        		</s:if>
        			</td>
        		</tr>
        	<tr>
        		<td colspan="10"><img src="/entity/bzz-students/images/two/7.jpg" width="852" height="4" /></td>
        	</tr>
        	</s:iterator>
        	<tr>
        	<td colspan="10" align="left" >
        		<table>
        			<tr>
        				<td valign="top">所提要求:</td>
        				<td>
        					<textarea cols="70" rows="10" name="yaoqiu" disabled="disabled"><s:property value="peBzzSuggestion.yaoQiu"/></textarea>
        				</td>
        			</tr>
        		</table>
        	</td>
        	</tr>
        	<tr>
        	<td colspan="10" align="left">
        	<table>
        	<tr>
        		<td valign="top">所提建议:</td>
        		<td><textarea cols="70" rows="10" name="suggestion" disabled="disabled"><s:property value="peBzzSuggestion.content"/></textarea></td>
        	</tr>
        	</table>
        	</td>
        	</tr>
        	</s:if>
        	<s:else>
        	
        	<tr>
        		<td align="center"><br><br>该课程目前还没有进行评估！<br><br></td>
        	</tr>
        	</s:else>
          </table>
        </td>
      </tr>
      
      <tr>
        		<td align="center"><input type="button" value="返回" onclick="javascript:window.history.back()"/>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; </td>
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