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
</div><table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" valign="middle" align="center" colspan="10">                  <h4>学生信息</h4>                                               </td>
                          </tr>
                          <tr>
                            <td height="8" colspan="10"> </td>
                          </tr>
                           		  <tr valign="middle"> 
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">学号：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.regNo == null ">不详</s:if><s:property value="peBzzStudent.regNo"/></td>
                                  </tr>
                                <tr> 
                                  <td width="12%" height="20" align="left" class="postFormBox"><span class="name"> 姓&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px" align="left"><s:if test="peBzzStudent.trueName == null ">不详</s:if><s:property value="peBzzStudent.trueName"/></td>
                                </tr>
                                   <tr valign="middle">
                                   <td width="12%" height="20" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td width="41%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.gender == null ">不详</s:if><s:property value="peBzzStudent.gender"/></td>
                                </tr>
                                  <tr valign="middle">
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">出生日期：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.birthdayDate == null ">不详</s:if><s:date name="peBzzStudent.birthdayDate" format="yyyy-MM-dd"/></td>
                                </tr>
                                  <tr valign="middle">
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">民族：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.folk == null ">不详</s:if><s:property value="peBzzStudent.folk"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="12%" height="30" align="left" class="postFormBox"><span class="name">学历：</span></td>
                                  <td width="38%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.education == null ">不详</s:if><s:property value="peBzzStudent.education"/></td>
									</tr>
                                <tr valign="middle"> 
                                  <td width="15%" height="30" align="left" class="postFormBox"><span class="name">办公电话：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.phone == null ">不详</s:if><s:property value="peBzzStudent.phone"/></td>
                                  </tr>
                                  <tr valign="middle">
                                  <td width="15%" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td width="35%" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.mobilePhone == null ">不详</s:if><s:property value="peBzzStudent.mobilePhone"/></td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="15%" height="30" align="left" class="postFormBox"><span class="name">电子邮件：</span></td>
                                  <td width="35%" colspan="5" class="postFormBox" style="padding-left:18px"><s:if test="peBzzStudent.email == null ">不详</s:if><s:property value="peBzzStudent.email"/></td>
                                </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
                          <tr>
                          	<td colspan="10" align="center"><input type="button" value="返 回" class="postFormBox" onclick="javascript:window.history.go(-1)" />
                          	<s:if test="type!=1">
                          		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="查看学习进度" class="postFormBox" onclick="javascript:window.navigate('/entity/manager/statistics/stat_study_progress1.jsp?id=<s:property value="peBzzStudent.id"/>&sso_id=<s:property value="peBzzStudent.ssoUser.id"/>')"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                          		<input type="button" value="查看作业自测状况" class="postFormBox"  onclick="javascript:window.navigate('/entity/manager/statistics/stat_study_score1.jsp?id=<s:property value="peBzzStudent.id"/>')"/>
                          	</s:if>
                          	</td>
                          	</tr>
        			</table>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
