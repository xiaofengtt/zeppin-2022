<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>管理员审批</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
	
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">

<div id="main_content">
    <div class="content_title">管理员审批</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/programApply/viewFirstTrialOpinion_mangerCheckSave.action" method="post"  >
<table width="654" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" ><!--<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>--></td>
                          </tr>
						 
                                  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id="bean.peUnit.name" name="bean.peUnit.name" size="52" value="<s:property value="prProExpertList[0].peProApply.peUnit.name"/>" readonly="readonly"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px" >&nbsp;</td>
                                </tr>
                                  <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">申&nbsp;报&nbsp;学&nbsp;科*：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><input type="text" id="bean.peSubject.name" name="bean.peSubject.name" size="52" value="<s:property value="prProExpertList[0].peProApply.peSubject.name"/>" readonly="readonly" />     </td>
                       			 	<td class="postFormBox" style="padding-left:10px" id="peSubjectInfo">&nbsp;</td>
                     			 </tr>
                               <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">申&nbsp;报&nbsp;批&nbsp;次*：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><input type="text" id="bean.peProApplyno.name" name="bean.peProApplyno.name" size="52" value="<s:property value="prProExpertList[0].peProApply.peProApplyno.name"/>" readonly="readonly" />  </td>
                       			 	<td class="postFormBox" style="padding-left:10px" id="peProApplynoInfo">&nbsp;</td>
                     			 </tr>
                               <s:iterator value="prProExpertList" id="prProExpert">
                               <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">专&nbsp;家&nbsp;评&nbsp;分：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><input type="text" id="score" name="score" size="52" value="<s:property value="#prProExpert.peValuaExpert.name"/>:<s:if test="#prProExpert.resultFirst==null||#prProExpert.resultFirst==''"> 尚未评分 </s:if><s:else><s:property value="#prProExpert.resultFirst"/></s:else>" readonly="readonly" />     </td>
                       			 	<td class="postFormBox" style="padding-left:10px" id="peProApplynoInfo">&nbsp;</td>
                     			 </tr>
                     			 </s:iterator>
                     			<tr valign="middle">
                       			 <td width="150" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">管理员评审*:</span></td>
                       				<td class="postFormBox" style="padding-left:10px">
                       				<input type="radio" id="bean.enumConstByFkCheckFirst.code" name="bean.enumConstByFkCheckFirst.code" value="1011" <s:if test="prProExpertList[0].peProApply.enumConstByFkCheckFirst.code=='1011'||prProExpertList[0].peProApply.enumConstByFkCheckFirst.code=='1010'">checked</s:if> />通过 
                       				<input type="radio" id="bean.enumConstByFkCheckFirst.code" name="bean.enumConstByFkCheckFirst.code" value="1012" <s:if test="prProExpertList[0].peProApply.enumConstByFkCheckFirst.code=='1012'">checked</s:if> />不通过 
                       				<input type="radio" id="bean.enumConstByFkCheckFirst.code" name="bean.enumConstByFkCheckFirst.code" value="1013" <s:if test="prProExpertList[0].peProApply.enumConstByFkCheckFirst.code=='1013'">checked</s:if> />需修改
                       				<input type="radio" id="bean.enumConstByFkCheckFirst.code" name="bean.enumConstByFkCheckFirst.code" value="1014" <s:if test="prProExpertList[0].peProApply.enumConstByFkCheckFirst.code=='1014'">checked</s:if> />需重大修改         </td>
                       			 	<td class="postFormBox" style="padding-left:10px" id="peProApplynoInfo">&nbsp;</td>
                     			 </tr>
                     			  <tr valign="middle">
                       			 <td width="150" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">管理员意见*:</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.noteFirst" cols="70" rows="12" id="bean.noteFirst">
                       				<s:if test="prProExpertList[0].peProApply.noteFirst == null">
	                       				<s:iterator value="prProExpertList" id="prProExpert">
	                       				<p><s:property value="#prProExpert.peValuaExpert.name"/>&nbsp;意见：<s:if test="#prProExpert.noteFirst==null||#prProExpert.noteFirst==''"> 无意见 </s:if><s:else><s:property value="#prProExpert.noteFirst"/></s:else></p>
	                       				</s:iterator>
                       				</s:if>
                       				<s:else>
                       				<s:property value="prProExpertList[0].peProApply.noteFirst"/>
                       				</s:else>
                       				</textarea>  </td>
                       			 	<td class="postFormBox" style="padding-left:10px" id="peProApplynoInfo">&nbsp;</td>
                     			 </tr>
                     			 <input type="hidden" id="bean.id" name="bean.id" size="52" value="<s:property value="prProExpertList[0].peProApply.id"/>" /> 
                           <tr>
                            <td height="50" align="center" colspan="3">
                              <input type="submit" value="确定"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" onclick="javascript:if(confirm('您确定要关闭此窗口吗？'))window.close();"/></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript"> 
	<!-- 
	// Automatically calculates the editor base path based on the _samples directory. 
	// This is usefull only for these samples. A real application should use something like this: 
	// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 
	
	var oFCKeditor1 = new FCKeditor( 'bean.noteFirst' ) ; 
	
	oFCKeditor1.Height = 300 ; 
	oFCKeditor1.Width  = 700 ; 
	
	oFCKeditor1.Config['ImageBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['ImageUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor1.Config['LinkBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor1.Config['LinkUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor1.Config['FlashBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['FlashUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor1.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor1.ReplaceTextarea() ; 
	
	

	//--> 
	<s:if test="msg!=null">
		alert('<s:property value="msg"/>');
	</s:if>
</script>
</body>
</html>
