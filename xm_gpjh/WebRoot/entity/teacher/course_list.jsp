<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临华南师范大学现代远程教育平台</title></title></title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link href="/entity/teacher/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="main_content">
    <div class="content_title">教师课程列表</div>
    <div class="cntent_k">
   	  <div class="k_cc">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                  <tr class='table_bg1'>
                    <td align="center" >课程名</td>
                    <td align="left" >开始授课</td>
                    <td align="left" >所属学期</td>
                    <td align="left" >栏目设置</td>
                  </tr>
                  <s:iterator value="courseList" status="stuts">
                    <tr 
                    <s:if test="#stuts.odd == false"> class="table_bg2" </s:if>
                    >
                    <td align="center" ><s:property value="prTchOpencourse.peTchCourse.name" /></td>
        <td>
          <a href='/sso/other/interaction_InteractioManage.action?open_course_id=<s:property value="prTchOpencourse.id"/>' target="_blank"><div class="study_bt">开始授课</div></a>
        </td>
                      <td align="left" ><s:property value="prTchOpencourse.peExamno.peSemester.name" /></td>
                      <td align="left" ><a href='/entity/teacher/teacher_courseItem.jsp?teachclass_id=<s:property value="prTchOpencourse.id"/>' target='_blank'>
          <div class="study_bt">栏目设置</div>
                      </a></td>
                    </tr>
                  </s:iterator>
                </table>
                
			    </div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>
