<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title> </title>
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
  <s:if test="message=='editOK'">
  <script type="text/javascript">
  				alert("修改成功");
  </script>
  </s:if>
   <div id="main_content">
    <div class="content_title">个人信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" >您的个人资料                            </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.trueName" /><s:property value="peEnterpriseManager.name" /> </td>
                                </tr>
                               <!--  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.enumConstByGender.name" /><s:property value="peEnterpriseManager.enumConstByGender.name" /></td>
                                </tr>  -->
                                <s:if test="peManager!=null">
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    身&nbsp;&nbsp;份&nbsp;&nbsp;证：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.idCard" /><s:property value="peEnterpriseManager.idCard" /></td>
                                </tr>
                                </s:if>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    电子邮件：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.email" /><s:property value="peEnterpriseManager.email" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">固定电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.phone" /><s:property value="peEnterpriseManager.phone" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.mobilePhone" /><s:property value="peEnterpriseManager.mobilePhone" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">企业地址：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.address" /><s:property value="peEnterpriseManager.peEnterprise.name" /></td>
                                </tr>
                            <!--  
                             	<tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">毕业院校及专业层次：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.graduationInfo" /><s:property value="peEnterpriseManager.graduationInfo" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">从事成人教育时间：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.workTime" /><s:property value="peEnterpriseManager.workTime" /></td>
                                </tr>
                            -->
                            <s:if test="peManager!=null">     
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">毕业时间：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:date name="peManager.graduationDate" format="yyyy年MM月"/><s:date name="peEnterpriseManager.graduationDate" format="yyyy年MM月"/> </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peManager.zhiCheng" /><s:property value="peEnterpriseManager.zhiCheng" /></td>
                                </tr>
                             </s:if>  
                           <tr>
                            <td height="50" align="center" colspan="2">
                             <a href="/entity/information/personalInfo_turnToEditInfo.action" ><img src="/entity/teacher/images/xg.gif" width="106" height="19" border="0"></a></td>
						  </tr>
						   <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

	  </div>
    </div>
</div>
<div class="clear"></div>
  </body>
</html>
