<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>

<html>
<title>录入学生成绩</title>
<link href="../css/css2.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/check.js"></script>
<script language="javascript">
	function cfmdel(url)
	{
		if(confirm("您确定要删除么？"))
			window.navigate(url);
	}
	function open_select(url)
	{
		window.open(url,'select','');
	}
	
	function open_new(url)
	{
		window.open(url,'new','');
	}
</script>

<%

	try
{ 
	BasicUserManage basicUserManage = teacherOperationManage.createBasicUserManage();
%>
<html>
<head>
<meta http-equiv="Content-Type" conte生殖健康咨询师培训网text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<br>
<form name="score_add" action="student_score_addexe.jsp" method="post" onsubmit="return pageGuard()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="./images/top_01.gif"><img src="./images/tlq.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="./images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %> 助教列表</td>
                            <td background="./images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
            <tr> 
          <td valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr bgcolor="#4582B1">
                        <td height=25px align=center class="title">教师姓名</td>
                        <td height=25px align=center class="title">性别</td>
                        <td height=25px align=center  class="title">电话</td>
                        <td height=25px align=center  class="title">E-mail</td>
                        <td height=25px align=center  class="title">教师状态</td>
                        <td height=25px align=center  class="title">删除</td>
                      </tr>
<%
		
		int totalItems = basicUserManage.getAssistanceTeachersNum(teachclass_id);
	//----------分页开始---------------
	String spagesize = (String) session.getAttribute("pagesize");
	String spageInt = request.getParameter("pageInt");
	Page pageover = new Page();
	pageover.setTotalnum(totalItems);
	pageover.setPageSize(spagesize);
	pageover.setPageInt(spageInt);
	int pageNext = pageover.getPageNext();
	int pageLast = pageover.getPagePre();
	int maxPage = pageover.getMaxPage();
	int pageInt = pageover.getPageInt();
	int pagesize = pageover.getPageSize();
	String link = "&teachclass_id=" + teachclass_id ;
	//----------分页结束---------------		
	List assi_teachers = basicUserManage.getAssistanceTeachers(null, teachclass_id);
		for(int i = 0; i < assi_teachers.size(); i++)
		{
			Teacher assiteacher = (Teacher)assi_teachers.get(i);
%>
			<tr>
                       <td height=25px align=center class="td2"><a href="assiteacher_edit.jsp?id=<%=assiteacher.getId()%>" target="_blank" class=c><%=assiteacher.getName()%></a></td>
                        <td height=25px align=center class="td2"><%=assiteacher.getNormalInfo().getGender()%></td>
                        <td height=25px align=center class="td2"><%=assiteacher.getNormalInfo().getPhones()%></td>
                        <td height=25px align=center class="td2">&nbsp;<a href="mailto:<%=assiteacher.getNormalInfo().getEmails()%>"><%=assiteacher.getNormalInfo().getEmails()%></a></td>
                        <td height=25px align=center class="td2"><%=assiteacher.getTeacherInfo().getStatus().equals("0000")?"有效":"无效" %></td>
                        <td height=25px align=center class="td2"> 
				<a href="javascript:cfmdel('../teacher/managestudent/assi_delexe.jsp?teachclass_id=<%=teachclass_id%>&teacher_id=<%=assiteacher.getId()%>')">[删除]</a>          
			</td>
			</tr>
<%       
			}  
%>
<tr align="center">
	<td height=25px colspan="6" class="td">
	</td>
</tr>
              <tr>
				<td colspan=11>
				      <%@ include file="./pub/dividepage.jsp" %>          
				</td>
              </tr>
              <tr>
                  <td height=25px align=center colspan=11>
                    <a href="javascript:open_select('../teacher/managestudent/assi_search.jsp?teachclass_id=<%=teachclass_id%>')">[从已有助教中选择]</a>&nbsp;&nbsp;
                    <a href="javascript:open_new('../teacher/managestudent/assi_add.jsp?teachclass_id=<%=teachclass_id%>')">[添加新助教]</a>
                 </td>
                </tr>
            </table></td>
                    
	  </td>
  </tr>
  </form>
</table>
</body>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>
</html>
