<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>企业信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">


  </head>
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content"><div align="center"> 
    </div><div class="cntent_k"><div align="center"> 
   	  </div><div class="k_cc"><div align="center"> 
</div><table width="780" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" valign="middle" align="center" colspan="10">                  <h4>企业信息</h4>                                               </td>
                          </tr>
                          <tr>
                            <td height="8" colspan="10"> </td>
                          </tr>
						 		<tr>
						 			<td width="100%" height="20" class="postFormBox" align="left" colspan="10"><strong>企业名称 ：<s:property value="peEnterprise.name"/></strong></td>
                                 </td>
						 		</tr>
                                <tr> 
                                	<td colspan="10" align="center">
                                		<table  border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
                                			<tr>
                                			<td width="5%"  height="20" align="left" class="postFormBox"><span class="name">编&nbsp;&nbsp;&nbsp;&nbsp;码</span></td>
                                			<td width="15%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="peEnterprise.code"/></td>
                                			 <td width="5%" height="30" align="left" class="postFormBox"><span class="name"> 
                                    	行&nbsp;&nbsp;&nbsp;&nbsp;业：</span></td>
                                  		<td width="25%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.industry"/></td>
                                			<td width="5%" width="80" height="30" align="left" class="postFormBox"><span class="name"> 
                                     地&nbsp;&nbsp;&nbsp;&nbsp;址：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.address"/></td>
                                			</tr>
                                			<tr>
                                			<td width="10%" height="30" align="left" class="postFormBox"><span class="name">  学员人数：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><font color="red" size=2><b><s:property value="num"/></b></font></td>
                                  <td height="30" align="left" class="postFormBox"><span class="name">  邮&nbsp;&nbsp;&nbsp;&nbsp;编：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.zipcode"/></td>
                                  <td height="30" align="left" class="postFormBox"><span class="name">  传&nbsp;&nbsp;&nbsp;&nbsp;真：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fax"/></td>
                                			</tr>
                                		</table>
                                	</td>
	                            </tr>
	                            <tr>
	                            	<td height="20">&nbsp;</td>
	                            </tr>
	                            <tr> 
                                  <td width="12%" height="20" align="left" class="postFormBox"><span class="name">项&nbsp;目&nbsp;负&nbsp;责&nbsp;人：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="peEnterprise.fzrName"/></td>
                                   <td width="12%" height="20" align="left" class="postFormBox"><span class="name">项&nbsp;目&nbsp;联&nbsp;系&nbsp;人：</span></td>
                                  <td width="41%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrName"/></td>
                                </tr>
                                <tr> 
                                  <td width="12%" height="20" align="left" class="postFormBox"><span class="name">性&nbsp;别：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:property value="peEnterprise.fzrXb"/></td>
                                   <td width="12%" height="20" align="left" class="postFormBox"><span class="name">性&nbsp;别：</span></td>
                                  <td width="41%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrXb"/></td>
                               		
                                </tr>
                                <tr valign="middle"> 
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">职&nbsp;务：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fzrPosition"/></td>
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">职&nbsp;务：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrPosition"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">办公电话：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fzrPhone"/></td>
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">办公电话：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrPhone"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="12%" width="10%" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fzrMobile"/></td>
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrMobile"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">电子邮件：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fzrEmail"/></td>
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">电子邮件：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrEmail"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="15%" height="30" align="left" class="postFormBox"><span class="name">通讯地址：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.fzrAddress"/></td>
                                  <td width="15%" height="30" align="left" class="postFormBox"><span class="name">通讯地址：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:property value="peEnterprise.lxrAddress"/></td>
                                </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
                          <tr>
                          	<td colspan="10" align="center"><input type="button" value="返 回" class="postFormBox" onclick="javascript:window.history.back()" /></td>
                          	</tr>
        			</table>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
