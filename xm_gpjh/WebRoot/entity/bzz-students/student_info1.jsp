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
</style></head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=169 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=650 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：个人信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left">&lt;&lt; <font color="red"><s:property value="peBzzStudent.getTrueName()"/></font>&gt;&gt; 的个人信息: </td>
          </tr>
        </table></td>
      </tr>
       <tr>
            <td align="center">
            	<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr valign="bottom">
                                <td width="20%"></td>
                                  <td height="30" align="left" width="40%"><div align="left"><input type=button value="学  号："></div></td>
                                  <td class="12texthei" width="20%"><div align="left"><s:property value="peBzzStudent.getRegNo()"/>&nbsp;</div></td>
                               <td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="bottom">
                                  <td width="20%"></td>
                                  <td height="30" width="40%" align="left"><div align="left"><input type=button value="姓  名："></div></td>
                                  <td class="12texthei" width="20%"><div align="left"><s:property value="peBzzStudent.getTrueName()"/>&nbsp;</div></td>
                                  <td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="年  龄："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.age == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.age"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="性  别："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.gender == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.gender"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="民  族："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.folk == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.folk"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="学  历："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.education == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.education"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="所在批次："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.peBzzBatch.name == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.peBzzBatch.name"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="所在企业："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.peEnterprise.name == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.peEnterprise.name"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="职  务："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.position == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.position"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="职  称："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.title == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.title"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="部  门："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.department == null">不详</s:if>
                                  		<s:property value="peBzzStudent.department"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="地  址："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.address == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.address"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="邮  编："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.zipcode == null">不详</s:if>
                                  		<s:property value="peBzzStudent.zipcode"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="办公电话："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.phone == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.phone"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="移动电话："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.mobilePhone == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.mobilePhone"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                <td width="20%"></td>
                                  <td height="30" align="left"><div align="left"><input type=button value="电子邮件："></div></td>
                                  <td class="12texthei" width="20%"><div align="left">
                                  		<s:if test="peBzzStudent.email == null ">不详</s:if>
                                  		<s:property value="peBzzStudent.email"/>&nbsp;
                                  	</div></td>
                                  	<td width="20%"></td>
                                </tr>
                                <tr>
        							<td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
        						</tr>
                                <tr valign="middle"> 
                                  <td colspan="5" height="30" align="center"><div align="center">
                                  	<input type="button" value="修改密码" onclick="javascript:window.location='/entity/workspaceStudent/bzzstudent_toPassword.action'">
                                  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  	<input type="button" value="返回" onclick="javascript:window.history.back()"/>
                                  </div></td>
                                </tr>
                              </table>
            </td>
          </tr>
     
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</body>
</html>