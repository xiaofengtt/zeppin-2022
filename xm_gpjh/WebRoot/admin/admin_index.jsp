<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
String path = request.getContextPath();   
String basePase = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>国培计划项目管理系统</title>
<link href="css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FFFFFF}
.STYLE4 {
	font-size: 18px;
	font-weight: bold;
}
-->
</style>
</head>

<body leftmargin="0" topmargin="0" >
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="113"><table width="100%" height="113" border="0" cellpadding="0" cellspacing="0" bgcolor="#E6EEF2">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="10"><img src="images/top.gif" width="10" height="28" /></td>
            <td background="images/top1.jpg">&nbsp;</td>
            <td width="10"><img src="images/top2.gif" width="10" height="28" /></td>
          </tr>
        </table></td>
      </tr>
      <tr >
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0"  >
          <tr>
          <td align="center">
            <span class="text4 STYLE4"><font >国培计划项目管理系统</font></span>          </td>
          <!--      <td width="130"><img src="images/top3.jpg" width="130" height="62" /></td>
            <td width="66"><img src="images/top31.jpg" width="66" height="62" /></td>
            <td width="141"><img src="images/top4.gif" width="141" height="62" /></td>
            <td width="140"><img src="images/top5.gif" width="140" height="62" /></td>
            <td background="images/top9.jpg">&nbsp;</td>
            <td width="275"><img src="images/top7.gif" width="275" height="62" /></td>
            <td width="186" background="images/top10.gif"></td>
            <td width="10"><img src="images/top8.gif" width="10" height="62" /></td>
            -->  
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="35"><img src="images/top11.gif" width="35" height="23" /></td>
            <td width="158" background="images/top22.jpg" class="css">您的位置:管理平台</td>
            <td background="images/top33.gif">&nbsp;</td>
            <td width="9"><img src="images/top44.gif" width="9" height="23" /></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#E6EEF2">
      <tr>
        <td width="193" valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="28" background="images/m1-1.gif"></td>
          </tr>
          <tr>
            <td height="28"><img src="images/m4.gif" width="193" height="28" /></td>
          </tr>
          <tr>
            <td valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top" background="images/leftline.gif"><div align="center">
                  <table width="126" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="6" colspan="2" background="images/1231.gif"></td>
                    </tr>
                    <tr>
                      <td width="15" height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="middle.jsp" class="STYLE1" target="main">系统信息</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="manager_password_change.jsp" class="STYLE1" target="main">修改密码</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <!-- tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG" class="title"><div align="center"><a href="manage_setdb.jsp" class="STYLE1" target="main">数据库连接</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="platformparam_set.jsp" class="STYLE1" target="main">平台参数设定</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="entityparam_set.jsp" class="STYLE1" target="main">教务参数设定</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG" class="title"><div align="center"><a href="ssoparam_set.jsp" class="STYLE1" target="main">SSO参数设定</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="infoparam_set.jsp" class="STYLE1" target="main">新闻参数设定</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="coursewareparam_set.jsp" class="STYLE1" target="main">课件参数设定</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr-->
                     <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="right_main.jsp" class="STYLE1" target="main">权限管理</a></div></td>
                    </tr>
                      <!-- 
                     <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="manager_manage.jsp" class="STYLE1" target="main">管理员管理</a></div></td>
                    </tr>
                     -->
                     <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="/sso/admin/log4j.action" class="STYLE1" target="main">操作日志</a></div></td>
                    </tr>
                    <tr>
                      <td height="15" colspan="2" background="images/3.gif"></td>
                    </tr>
                    <tr>
                      <td height="15" background="images/1.JPG"></td>
                      <td height="15" background="images/2.JPG"><div align="center" class="title"><a href="logout.jsp" class="STYLE1">注销退出</a></div></td>
                    </tr>
                     
                  </table>
                </div>                  </td>
              </tr>
            </table></td>
          </tr>
          
          <tr>
            <td background="images/leftline.gif"><p>&nbsp;</p>
              <p>&nbsp;</p>
              <p><br />
              </p></td>
          </tr>
          
          <tr>
            <td><img src="images/m1.gif" width="193" height="23" /></td>
          </tr>
        </table></td>
        <td align="left" valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="29">&nbsp;</td>
            <td valign="top"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="27" valign="top"><table width="100%" height="27" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="4"><img src="images/22.gif" width="4" height="27" /></td>
                        <td background="images/23.gif">&nbsp;</td>
                        <td width="4"><img src="images/21.gif" width="4" height="27" /></td>
                      </tr>
                  </table></td>
                </tr>
                <tr valign="top">
                  <td height="100%"><iframe name="main" width="100%" height="100%" scrolling="auto" frameborder="0" src="middle.jsp" allowtransparency="true"></iframe></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                </tr>
            </table></td>
            <td width="33" background="images/zhongjianbeijing-right.gif">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="62"><table width="100%" height="62" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="14"><img src="images/bot1.gif" width="14" height="62" /></td>
        <td background="images/bot2.gif"><div align="center" class="title">北京师范大学继续教育与教师培训学院版权所有</div></td>
        <td width="46"><img src="images/bot3.gif" width="46" height="62" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>