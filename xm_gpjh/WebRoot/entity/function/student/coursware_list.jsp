<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>课件列表</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=77%>
                            	<s:iterator value='courseWareList' id='courseWare'>
                            	  <tr>
									<td align="center"><p><a href="#" onclick='window.open("/training/student/course/jumpCourseware.jsp?coursewareId=<s:property value='#courseWare.id'/>","","resizable=yes");return false;' target="_blank"><s:property value="#courseWare.name"/></a>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#courseWare.enumConstByVersion.name"/></p></td>
						  		  </tr>
						  		  </s:iterator>
                            	</table>
							</td>
							</tr>
				     </table>
				     </td>
				    </tr>
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
                  <tr>

			<td align=center>
		    </tr>
                  </table> </td>
              </tr>
              	
            </table></td>
        </tr>
      </table>
</body>
</html>