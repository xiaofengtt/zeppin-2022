<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<title>生殖健康咨询师网络课程</title>
<link rel="stylesheet" type="text/css" href="/entity/function/teacher/css/style.css">
<link href="/entity/function/teacher/css/admincss.css" rel="stylesheet" type="text/css">

<script>
var abcStatus=1;
function abc()
{
	if(document.getElementById("abc").width=="11")
	{
		document.getElementById("abc").width="12";
		abcStatus=1;
	}
	else
	{
		document.getElementById("abc").width="11";
		abcStatus=0;
	}
}
function aa(id)
{
	if(document.getElementById(id))
	document.getElementById(id).style.display = (document.getElementById(id).style.display=="none")?"block":"none";
}
</script>
</head>

<body scroll=no>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="93" valign="top"><table width="100%" height="93" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#003FA9">
      <tr>
        <td width="342" align="center" style="font-family: 隶书;font-size:24px;color:white;">生殖健康咨询师网络培训课堂<!-- <img src="/entity/function/teacher/images/logo.jpg" width="315" height="60"> --></td>
        <td style="padding-bottom:12px; background-repeat:no-repeat; padding-right:12px" align="right" valign="bottom" background="/entity/function/teacher/images/topbg.jpg"><a href="#"><img src="/entity/function/teacher/images/tuichu.jpg" width="62" height="21" border="0" onclick="if(confirm('您确定要退出工作室吗？')){parent.window.close();}"></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="211" height="100%" valign="top" background="/entity/function/teacher/images/leftbg2.jpg" bgcolor="#004DA9" style="padding-left:4px; background-repeat:repeat-y" id="bj"><table width="208" height="42" border="0" cellpadding="0" cellspacing="0" bgcolor="#E2EEF3" >
          <tr>
            <td ><table style="margin-top:5px;" width="208" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="80" valign="top" bgcolor="#003fa9" class="bai"><p>·当前课程：<br>
                </p></td>
                <td width="128" bgcolor="#003fa9" class="bai"><s:property value="peTchCourse.name"/></td>
              </tr>
            </table>            
           </td>
          </tr>
          <tr>
            <td ><table style="margin-top:15px; margin-left:2px" width="205" border="0" cellpadding="0" cellspacing="0" bgcolor="#e2eef3">
              <!-- <tr>
              	<td width="107" height="45" align="center" valign="top" background="images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/kcjj/coursenote_list.jsp?type =KCJJ&open_course_id=" target="mainer" class="menuzi">课程简介</a></td>
                <td width="107" height="45" align="center" valign="top" background="images/menu2bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/jsjj/teachernote_list.jsp?open_course_id=" target="mainer" class="menuzi">教师简介</a></td>
              </tr> -->
              <tr>
              	<td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu1bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_showIframe.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">课程简介</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/student/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=homework&course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">在线作业</a></td>
              </tr>
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu3bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_ziliaoList.action?course_id=<s:property value='peTchCourse.id'/>" target="mainer" class="menuzi">资料管理</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu4bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_questionList.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">课程答疑</a></td>
              </tr>
              <tr>
              	<td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu9bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_loreList.action?course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">题库管理</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu6bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/studyZone/courseResources_onlineTest.action?onlineType=test&course_id=<s:property value="peTchCourse.id"/>" target="mainer" class="menuzi">在线自测</a></td>
              </tr>
              <!-- 
              <tr>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu7bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/exampaper/alert.jsp" target="mainer" class="menuzi">工具与案例</a></td>
                <td width="107" height="45" align="center" valign="top" background="/entity/function/teacher/images/menu5bg.jpg" style="background-repeat:no-repeat; padding-top:8px; padding-left:14px"><a href="/entity/function/onlinehomeworkpaper/homeworkpaper_list.jsp" target="mainer" class="menuzi">在线作业</a></td>
              </tr> -->
            </table></td>
          </tr>
        </table>
          <table width="209" height="8" border="0" cellpadding="0" cellspacing="0" background="/entity/function/teacher/images/leftcenterbg.jpg">
              <tr>
                <td></td>
              </tr>
            </table>
        
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td valign="top" background="/entity/function/teacher/images/leftbtbg.jpg" style="background-position:bottom; background-repeat:no-repeat;"><table style="margin-top:15px" width="206" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                     <td height="16" colspan="3"><img src="/images/k.gif" width="1" height="16"></td>
                  </tr>
                  <!-- 
                  <tr>
                   <td height="40"   class="bai" style="padding-left:10px"><a href="/entity/function/student_manage.jsp" target="mainer"><img src="/entity/function/teacher/images/help_bt01.gif" alt="学生管理" width="63" height="51" border="0"></a></td>
                   <td width="2" align="center" background="/images/help_bt03.gif"><img src="/images/k.gif" width="2" height="1"></td>
                   <td height="30"   class="bai" style="padding-left:10px"><a href="/entity/function/student_score.jsp" target="mainer"><img src="/entity/function/teacher/images/help_bt02.gif" alt="成绩管理" width="63" height="51" border="0"></a></td>
                   </tr>
                   -->
                </table></td>
              </tr>
          </table></td>
        <td width="12" align="left" background="/entity/function/teacher/images/leftbg2.jpg" style="background-repeat:repeat-y; background-position:right" id="abc"><img id="button" alt="单击收缩/展开右侧功能区"  src="/entity/function/teacher/images/jiaotou.jpg" width="9" height="79" style="margin:0px 3px 0px 0px; cursor:pointer" onClick="abc();if(abcStatus==0) this.src='/entity/function/teacher/images/jiaotou1.jpg';else this.src='/entity/function/teacher/images/jiaotou.jpg';aa('bj');"></td>
        <td height="100%" align="center" valign="top"><iframe src='/entity/studyZone/courseResources_showIframe.action?course_id=<s:property value="peTchCourse.id"/>' width="100%" height="100%" frameborder="0" id="mainer" name="mainer" align="top" scrolling="yes" style="margin:0px 8px 0px 0px;"></iframe></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="47"><table width="100%" height="47" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="5" bgcolor="#003284"></td>
      </tr>
      <tr>
        <td height="42" align="center" background="/entity/function/teacher/images/btbg.jpg" class="baizi">版权所有：生殖健康咨询师培训网</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
