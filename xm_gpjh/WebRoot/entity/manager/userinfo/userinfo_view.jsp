<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  </head>
  
 <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
	<div id="main_content">
    <div class="content_title">联系人资料查看</div>
   	  <div class="k_cc">
		<form id='teacherInfo' action="/entity/basic/userInfoEditAction_saveUserInfo.action" method="post">
			<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr>
                 <td colspan="3" height="26" align="center" valign="middle" >&nbsp;</td>
               </tr>
               <tr valign="middle"> 
                  <td width="100" height="30" align="left" class="postFormBox" style="padding-left:50px" width="20%">
                  	<span class="name">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</span>
                  </td>
                  <td class="postFormBox" width="140" style="padding-left:10px"><s:property value="valuaExpert.loginId"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.workplace"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.name"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px">
                  	<s:property value="valuaExpert.enumConstByFkGender.name"/>
                  </td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.age"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.folk"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="bean.zhiwu"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="bean.zhicheng"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="bean.telephone"/></td>
                </tr>
                <tr valign="middle">
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.email"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">身份证号：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.idcard"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">办公电话：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.officePhone"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.fax"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">研究领域：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.researchArea"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">教学领域：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.trainingArea"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.education"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">政治面貌：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.politics"/></td>
                </tr>
                <tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">通讯地址：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.address"/></td>
                </tr>
                <tr valign="middle"> 
                	<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</span>
                	</td>
                	<td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.zip"/></td>
               	</tr>
                <tr valign="middle"> 
                	<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span>
                	</td>
                	<td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.note"/></td>
               	</tr>
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">个人简历：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.personalResume" escape="false"/></td>
                </tr>
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">教学成果：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.trainingResult"escape="false"/></td>
                </tr>
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">教学经验：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.trainingExperience"escape="false"/></td>
                </tr>
                <!-- 
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">单位评价：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.unitComment"/></td>
                </tr>
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">最终意见：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.finalComment"/></td>
                </tr> -->
               	<tr valign="middle"> 
                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
                  	<span class="name">其它事项：</span>
                  </td>
                  <td class="postFormBox" style="padding-left:10px"><s:property value="valuaExpert.otherItems"/></td>
                </tr>
				<tr>
					<td colspan="3" height="26" align="center" valign="middle" >&nbsp;</td>
				</tr>
        	</table>
		</form>
	</div>
</div>
</body>
</html>