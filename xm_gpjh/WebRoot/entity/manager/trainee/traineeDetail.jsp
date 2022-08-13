<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>参训学员详细信息</title>
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">参训学员详细信息</div>
    <div class="cntent_k">
		<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
			<!-- 公共可查看属性开始 -->
 				<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;*：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.name"/>"/>
	                </td>
               </tr>
				<tr valign="middle"> 
					<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
					<td class="postFormBox" style="padding-left:10px">
	              		<input readonly="readonly" value="<s:property value="bean.enumConstByFkGender.name"/>"/>
	              	</td>
				</tr>
				<tr valign="middle"> 
					<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
						<span class="name">&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄&nbsp;*：</span>
					</td>
					<td class="postFormBox" style="padding-left:10px">
						<input readonly="readonly" value="<s:property value="bean.age"/>" />
					</td>
				</tr>
				<tr valign="middle"> 
                	<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                		<span class="name">&nbsp;&nbsp;民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</span>
                	</td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.folk"/>" />
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;工作单位&nbsp;*：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.workPlace"/>" style="width: 300px;"/>
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.education"/>"/>
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.zhiCheng"/>" /> 
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.zhiwu"/>"/>
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;教&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.workyeare"/>" />
	                </td>
               </tr>
               <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.telephone"/>" />
	                </td>
               </tr>
               <tr valign="middle">
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.email"/>"/>
	                </td>
               </tr>
               <!-- 公共可查看属性结束 -->
               <s:if test="getCurrentUserRoleEnumCode(getCurrentSsoUser())==2">
               	<!-- 承办单位特别显示属性开始 -->
               	<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peSubject.name"/>"/>
	                </td>
               	</tr>
               	<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;培&nbsp;训&nbsp;项&nbsp;目：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peProApplyno.name"/>"/>
	                </td>
                </tr>
				<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peProvince.name"/>"/>
	                </td>
               	</tr>
               	<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;办&nbsp;公&nbsp;电&nbsp;话：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.officePhone"/>"/>
	                </td>
                </tr>
               	<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;是&nbsp;否&nbsp;结&nbsp;业：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkGraduted.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;参&nbsp;训&nbsp;状&nbsp;态：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkStatusTraining.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;证&nbsp;书&nbsp;编&nbsp;号：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.certificateNumber"/>"/>
	                </td>
                </tr>
               	<!-- 承办单位特别显示属性结束 -->
			</s:if>
			<s:elseif test="getCurrentUserRoleEnumCode(getCurrentSsoUser())==3">
				<!-- 省厅特别显示属性结束 -->
				<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;学&nbsp;段&nbsp;学&nbsp;科：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.subject"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;培&nbsp;训&nbsp;项&nbsp;目：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peProApplyno.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;办&nbsp;公&nbsp;电&nbsp;话：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.officePhone"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">是否通过审核:</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkCheckedTrainee.name"/>" />
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;参&nbsp;训&nbsp;状&nbsp;态：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkStatusTraining.name"/>"/>
	                </td>
                </tr>
                 <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;培&nbsp;训&nbsp;单&nbsp;位：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peUnitByFkTrainingUnit.name"/>"/>
	                </td>
                </tr>
				<!-- 省厅特别显示属性结束 -->
			</s:elseif>
			<s:else>
				<!-- 管理员显示所有属性 -->
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peSubject.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;学&nbsp;段&nbsp;学&nbsp;科：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.subject"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;培&nbsp;训&nbsp;项&nbsp;目：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peProApplyno.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peProvince.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;办&nbsp;公&nbsp;电&nbsp;话：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.officePhone"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;用&nbsp;&nbsp;户&nbsp;&nbsp;名&nbsp;：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.loginId"/>" />
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">是否通过审核:</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkCheckedTrainee.name"/>" />
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;是&nbsp;否&nbsp;结&nbsp;业：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkGraduted.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;培&nbsp;训&nbsp;单&nbsp;位：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peUnitByFkTrainingUnit.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;填&nbsp;报&nbsp;单&nbsp;位：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.peUnitByFkUnitFrom.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;参&nbsp;训&nbsp;状&nbsp;态：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.enumConstByFkStatusTraining.name"/>"/>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<textarea cols="84" rows="5" readonly="readonly"><s:property value="bean.note"/></textarea>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注2：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<textarea readonly="readonly" cols="84" rows="5"><s:property value="bean.noteSecond"/></textarea>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
	                	<span class="name">&nbsp;&nbsp;证&nbsp;书&nbsp;编&nbsp;号：</span>
	                </td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input readonly="readonly" value="<s:property value="bean.certificateNumber"/>"/>
	                </td>
                </tr>
                <!-- 管理员显示所有属性 -->
               </s:else>
            <tr>
	            <td height="40" align="center" colspan="2">
	              <input type="button" value="&nbsp;关&nbsp;&nbsp;闭" onclick="window.close()" style="width:120px;cursor:pointer" />
	            </td>
			</tr>
       	</table>
    </div>
</div>
</body>
</html>