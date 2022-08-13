<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主干课预约</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<script >
function check(){
        var len = document.vote.checkboxId.length; 
        var checked = false; 
        if(len == null) {
         	if(document.vote.checkboxId.checked == true) {
         		return true;
         	} else {
         		alert("请至少选择一个课程！");
         		return false;
         	}
         }
        for (i = 0; i < len; i++) 
        { 
            if (document.vote.checkboxId[i].checked == true) 
            { 
                checked = true; 
                break; 
            } 
        } 
        if (!checked) 
        { 
            alert("请至少选择一个课程！"); 
            return false;
        } 
        return true;
}
</script>
</head>
  
<body>
  <form name="vote" method=post action="/entity/workspaceStudent/mainCourseReserver_courseReserver.action" onsubmit="return check();">
<div id="main_content">
	<div class="content_title">您当前的位置：学生<s:property value="peStudent.trueName"/>的工作室 > 考试预约 > 主干课考试预约</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp;主干课考试预约&nbsp;&nbsp;
	<s:date name="peSemester.mainCourseStartDate" format="yyyy-MM-dd" /> - <s:date name="peSemester.mainCourseEndDate" format="yyyy-MM-dd" />
	&nbsp;<s:property value="message"/></td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="25%"  align=center>选择</td>
      <td height=25px width="25%"  align=center>课程编号</td>
      <td height=25px width="50%" align=center>课程名称</td>

    </tr>

 <s:iterator value="courseList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="25%"  align=center><input type="checkbox" id="checkboxId" name="checkboxId" value="<s:property value="id"/>" /></td>
      <td height=25px width="25%"  align=center><s:property value="peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="50%" align=center><s:property value="peTchCourse.name"/>&nbsp;</td>
     
  </tr>
 </s:iterator>


         
</table>
<s:if test="disobey==true">
    <s:if test="message == null">
   <s:if test="courseList.size > 0">
    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    	<tr>
    	<td align="center">
    	 <input name="Submit1" type="submit" id="Submit1" value="预约考试" />
    	</td>
    	</tr>
    </table>
    </s:if>	
    </s:if>	
</s:if><s:else>
    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
    	<tr>
    	<td align="center">
    	 期末考试有违纪或者作弊记录，不能参加主干课程考试。
    	</td>
    	</tr>
    </table>
</s:else>        
</div>
</div>

</div>
 </form>
</body>

</html>
