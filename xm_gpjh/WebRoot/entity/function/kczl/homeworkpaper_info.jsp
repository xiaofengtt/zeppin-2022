<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/function/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>参考资料</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=90%>
                            	<tr>
                            <td class="neirong" width=20%>
							<font size=2>题目：</font>
							</td>
							<td class="neirong" valign=top>
							<font size=2><s:property value="interTchInfo.title"/>
							</td>
						  </tr>
                          		<tr>
                          		<td class="neirong" valign=top width=20%>
							<font size=2>内容：</font>
							</td>
							<td class="neirong" valign=top>
							<s:property value="interTchInfo.content" escape="false"/>
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=button value="返回" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" onclick="window.location='/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value="course_id"/>'"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>
