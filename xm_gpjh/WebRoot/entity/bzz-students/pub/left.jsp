<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>国培计划学生工作室</title>
<link href="../css.css" rel="stylesheet" type="text/css" />
<script>
function logout(){
		var url="/sso/login_close.action?loginErrMessage=clear";
		if (window.XMLHttpRequest) {
	        req = new XMLHttpRequest();
	    }
	    else if (window.ActiveXObject) {
	        req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    req.open("Get",url,true);
	    req.onreadystatechange = function(){
	    	if(req.readyState == 4){
		    	window.top.navigate("/");
	    	}
	    };
  		req.send(null);
	}
</script>
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
<table width="237" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="50" align="center"><table width="92%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                
                <td class="twoleftlogo">&nbsp;欢迎访问网络课堂培训<br />
平台，您可以执行以下操作：</td>
              </tr></table>
             </td>
          </tr>
        
        </table></td>
      </tr><!--
      
      <tr>
        <td width="33" rowspan="10"></td>
        <td width="174" height="46" align="right" background="../images/two/two2_r20_c3.jpg" ><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/stuPeBulletinView_getFirstinfo.action'">
            <tr>
              <td align="left" class="twoleft">浏览公告</td>
            </tr>
          </table></td>
        <td width="30" rowspan="10"></td>
      </tr>
      <tr>
        <td height="44" align="right" background="../images/two/two2_r13_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_StudentInfo.action'">
            <tr>
              <td align="left" class="twoleft">个人信息</td>
            </tr>
          </table></td>
        </tr>
         <tr>
        <td height="44" align="right" background="../images/two/two2_r12_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toPlanCourses.action'">
            <tr>
              <td align="left" class="twoleft">课程列表</td>
            </tr>
          </table></td>
        </tr>
         zhaoyuxiao begin 
         <tr>
        <td height="44" align="right" background="../images/two/two2_r12_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/study/peApply_getList.action'">
            <tr>
              <td align="left" class="twoleft">学习申请</td>
            </tr>
          </table></td>
        </tr>
        zhaoyuxiao end 
        
        <tr>
        <td height="44" align="right" background="../images/two/two2_r01.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toliveroom.action'">
            <tr>
              <td align="left" class="twoleft">直播课堂 </td>
            </tr>
          </table></td>
        </tr>   
         <tr>
        <td height="44" align="right" background="../images/two/two2_r16_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toLearntCourses.action'">
            <tr>
              <td align="left" class="twoleft">课程答疑</td>
            </tr>
          </table></td>
        </tr>
		<tr>
        <td width="174" height="44" align="right" background="../images/two/two2_r16_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/basic/messageBoardAction_getMessages.action'">
            <tr>
              <td align="left" class="twoleft">查看提问 </td>
            </tr>
          </table></td>
        </tr>
      <tr>
        <td height="44" align="right" background="../images/two/two2_r19_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toZiceCourses.action'">
            <tr>
              <td align="left" class="twoleft">在线自测 </td>
            </tr>
          </table></td>
        </tr>
         
         <tr>
        <td height="44" align="right" background="../images/two/two2_r18_c3.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/bzz-students/alert_kaoshi.jsp'">
            <tr>
              <td align="left" class="twoleft">考试信息 </td>
            </tr>
          </table></td>
        </tr>
          <tr>
        <td width="174" height="44" align="right" background="../images/two/two2_r02.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toPingguCourses.action'">
            <tr>
              <td align="left" class="twoleft">课程评估 </td>
            </tr>
          </table></td>
        </tr>
         
          <tr>
        <td height="44" align="right" background="../images/two/two2_r03.jpg"><table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_toDownload.action'">
            <tr>
              <td align="left" class="twoleft">资料下载 </td>
            </tr>
          </table></td>
        </tr>
         -->
         <tr>
            <td  height="44" align="left" style="background-repeat: no-repeat;" background="../images/two/two2_r11_c3.jpg" >
              <table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="parent.location.href='/entity/workspaceStudent/bzzstudent_getVoteList.action'">
	            <tr>
	              <td align="center" class="twoleft">调查问卷 </td>
	            </tr>
              </table>
            </td>
         </tr>
	      <tr>
	         <td  height="44" align="left" style="background-repeat: no-repeat;" background="../images/two/two2_r21_c3.jpg">
	           <table width="65%" border="0" cellspacing="0" cellpadding="0" style="cursor:hand" onClick="if(confirm('确定退出平台？')) logout()">
	            <tr>
	              <td align="center" class="twoleft">注销退出</td>
	            </tr>
	           </table>
	          </td>
	        </tr>
    </table>
    </body>
</html>
