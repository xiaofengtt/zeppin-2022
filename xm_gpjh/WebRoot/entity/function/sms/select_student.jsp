<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "不详";
			return str;
	
	}
%>

<%!
	//判断字符串为空的话，赋值为"不详"
	String fixNull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>

<script language="javascript">
function checkForm(form){
	if(form.postalcode.value.length>6){
		alert("邮政编码不应超过6位");
		return false;
	}
	return true;
	
	if(isNull(document.form_xml.name.value))
	{
	}
	else
	{
		alert("输入名称为空!");
		document.form_xml.name.focus();
		document.form_xml.name.select();
		return false;
	}
}
	function doSel() {
		var mobiles = "";
		var obj = document.getElementsByName("ids");
		for(var i=0; i<obj.length; i++) {
			if(obj[i].checked)
				mobiles += obj[i].value + ",";
		}
		if(mobiles.length>0)
			mobiles = mobiles.substring(0,mobiles.length-1);
		var openerObj = opener.sms.mobilePhone;
		if(openerObj.value == "")
			openerObj.value += mobiles;
		else
			openerObj.value += "," + mobiles;
		window.close();
	}
</script>

<%

try
{ 
	BasicActivityManage basicActivityManage = teacherOperationManage.createBasicActivityManage();	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="../images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %> 学生列表</td>
                            <td background="../images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                   </tr>

           <form action="#" method="get">
			<td> 
				根据学号查找:&nbsp;<input type=text name=reg_no  size=10>
				根据姓名查找:&nbsp;<input type=text name=name  size=10>
          		<a href='#' onclick='submit();'>查找</a>
			</td>
			</form>

            <tr> 
          <td valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center" bgcolor="#4582B1"> 
                <td align="center" class="title"></td>
                <td align="center" class="title">学号</td>
                <td align="center" class="title">姓名</td>
                <td align="center" class="title">移动电话号码</td>
                <td align="center" class="title">专业</td>
                <td align="center" class="title">层次</td>
                <td align="center" class="title">年级</td>                
                <td align="center" class="title">站点</td>                                                                
              </tr>
<%
		int totalItems = basicActivityManage.getTeachClassStudentsNum(teachclass_id);
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
		String link = "";
		//----------分页结束---------------
		   String no = request.getParameter("reg_no");
		   String name = request.getParameter("name");
		 
   	       List studentList = basicActivityManage.getTeachClassStudents(pageover, teachclass_id);
	       if(no == null && name == null){
	       		int ii = 0;
	       		for(ii =0; ii<studentList.size();ii++){
	       		Student stu = (Student)studentList.get(ii);
%>
			<tr>
				<td align="center" class="td2"><input type=checkbox name=ids value="<%=stu.getNormalInfo().getMobilePhones()%>"></td>
                <td align="center" class="td2"><%=stu.getStudentInfo().getReg_no()%></td>
                <td align="center" class="td2"><%=stu.getName()%></td>
                <td align="center" class="td2"><%=stu.getNormalInfo().getMobilePhones()%></td>
                <td align="center" class="td2"><%=stu.getStudentInfo().getMajor_name()%></td>
                <td align="center" class="td2"><%=stu.getStudentInfo().getEdutype_name()%></td>                
                <td align="center" class="td2"><%=stu.getStudentInfo().getGrade_name()%></td>  
                <td align="center" class="td2"><%=stu.getStudentInfo().getSite_name()%></td>      
            </tr>  
<%       
				}  
			}else{
				List students =basicActivityManage.getTeachClassStudents(pageover,teachclass_id,no,name);
				for(int j=0;j<students.size();j++){
					Student s = (Student)students.get(j);
%>
			<tr>
				<td align="center" class="td2"><input type=checkbox name=ids value="<%=s.getNormalInfo().getMobilePhones()%>"></td>
                <td align="center" class="td2"><%=s.getStudentInfo().getReg_no()%></td>
                <td align="center" class="td2"><%=s.getName()%></td>
                <td align="center" class="td2"><%=s.getNormalInfo().getMobilePhones()%></td>
                <td align="center" class="td2"><%=s.getStudentInfo().getMajor_name()%></td>
                <td align="center" class="td2"><%=s.getStudentInfo().getEdutype_name()%></td>                
                <td align="center" class="td2"><%=s.getStudentInfo().getGrade_name()%></td>  
                <td align="center" class="td2"><%=s.getStudentInfo().getSite_name()%></td>      
            </tr> 
<%
				}
			}
 %>
              <tr>
				<td colspan=11>
				      <%@ include file="../pub/dividepage.jsp" %>          
				</td>
              </tr>
            </table></td>
                    
	  <td>s</td>
  </tr>
  <tr>
  	<td align=center>
  		<input type=button onclick="doSel()" value="确定"> <input type=button onclick="window.close()" value="关闭"> 
  	</td>
  </tr>
</table>
</body>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>
</html>
