<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingStudentPriv"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.standard.scorm.operation.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>

<% 
    String courseId=request.getParameter("coursewareId");
	TrainingFactory factory=TrainingFactory.getInstance();
    UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	String userid = "";
	if(usersession!=null){
		userid = usersession.getId(); 
	}
	else
	{
	%>
	<script>
	window.alert("登录超时，为了您的帐户安全，请重新登录。");
	window.top.location="/";
	</script>
	<%
	return;
	}
	
	String search = fixnull(request.getParameter("search"));
	String course_id = fixnull(request.getParameter("course_id"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String id = fixnull(request.getParameter("id"));
	String name = fixnull(request.getParameter("name"));
	String sso_id = fixnull(request.getParameter("sso_id"));
	
	String sql = "";
	dbpool db = new dbpool();
	MyResultSet rs =null;
	
	TrainingStudentPriv includePriv=new OracleTrainingStudentPriv();
	includePriv.setStudentId(sso_id);
	
	TrainingStudentOperationManage stuManage=factory.creatTrainingUserOperationManage(includePriv);
	ScormStudentManage scormStudentManage=stuManage.getScormStudentManage();
	UserCourseData userCourseData=scormStudentManage.getUserCourseData(courseId);
	if(userCourseData == null)
	{
	 	userCourseData = new UserCourseData();
	 }
	List userScos=scormStudentManage.getUserScos(courseId);
	
	int scores = 0;
	for(int i=0;i<userScos.size();i++)
	{
		String val = ((UserScoData)userScos.get(i)).getCore().getScore().getRaw().getValue() ;
		if(val != null)
			scores += Double.valueOf(val).intValue();
	}   
	 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + 'form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'ids')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
</script>
<script  language="javascript">
function doselect(objID)
{
	var tempObj;
		if(document.getElementById(objID))
		{
			tempObj = document.getElementById(objID);
			if(tempObj.checked)
			{
				tempObj.checked=false;
			}
			else
			{
				tempObj.checked=true;
			}
		}
}
</script>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看<%=name %>学习进度信息</td>
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder">
<!-- start:内容区域 -->

<div class="border">
	<form name="searchForm" action="/entity/manager/statistics/stat_study_progress.jsp" method="post">
	<input type="hidden" name="pageInt1" value="<%=pageInt1 %>"/>
	<input type="hidden" name="id" value="<%=id %>"/>
	<input type="hidden" name="name" value="<%=name %>"/>
	<input type="hidden" name="course_id" value="<%=course_id %>"/>
	<input type="hidden" name="sso_id" value="<%=sso_id%>" />
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
				<input type='text' name='search' value='<%=search %>' size='20' maxlength='245' style='vertical-align: bottom;'>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找（请输入课程名称）</a></span>
          	</div>
			</td>
			<td class='misc' style='white-space: nowrap;'>
			</td>
		  </tr>
	</table>
	</form>
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <th width='15%' style='white-space: nowrap;'> <span class="link">课程名称</span> 
              <th width='10%' style='white-space: nowrap;'><span class="link">课程类型</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">学习进度</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">详细学习记录</span></th>
            </tr>
<%
	sql = "select bs.id         as id,"+
          "tc.name       as course_name,"+
          "tc.id       as course_id,"+
          "ec.name       as course_type ,"+
          "cs.PERCENT      as progress "+
          "from pe_bzz_student          bs,"+
          "enum_const              ec,"+
          "pe_bzz_batch            bb,"+
          "pe_enterprise           pe,"+
          "pr_bzz_tch_stu_elective se,"+
          "pe_bzz_tch_course       tc,"+
          "sso_user su,training_course_student cs, "+
          "pr_bzz_tch_opencourse   bo "+
          "where bs.fk_batch_id = bb.id "+
          "and bs.id = '"+id+"'"+
          "and bs.fk_enterprise_id = pe.id "+
          "and ec.id= tc.flag_coursetype "+
          "and se.fk_stu_id = bs.id "+
          "and bo.fk_course_id = tc.id "+
          "and bo.fk_batch_id = bb.id "+
          "and bs.fk_sso_user_id=su.id and su.id=cs.student_id and cs.course_id=bo.id "+
          "and se.fk_tch_opencourse_id = bo.id and tc.name like '%"+search+"%' order by bo.flag_course_type, to_number(tc.suqnum)";
          
   //out.println(sql);
 
					int totalItems = db.countselect(sql);
				//----------分页开始---------------
				String spagesize = (String) session.getValue("pagesize");
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
				String link="&search="+search+"&id="+id+"&name="+name+"&sso_id="+sso_id;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String t_id=fixnull(rs.getString("id"));
					course_id = fixnull(rs.getString("course_id"));
					String course_name = fixnull(rs.getString("course_name"));
					String course_type = fixnull(rs.getString("course_type"));
					String progress = fixnull(rs.getString("progress"));
					
				    

%>
            <tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>' >             
              <td style='white-space: nowrap;text-align:center;'><%=course_name%></td>
              <td style='white-space: nowrap;text-align:center;'><%=course_type%></td>
              
              <td style='white-space: nowrap;text-align:center;'><script language="javascript">var last<%=a%> =new CreatPro("last<%=a%>", <%=progress%>,"2.5",5000,1,10,"../../../training/student/");last<%=a%>.stepPro()</script></td>
              <!-- <td style='white-space: nowrap;'><%=progress%>%</td>  -->  
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_study_note.jsp?pageInt1=<%=pageInt%>&id=<%=id%>&c_course_id=<%=course_id%>&sso_id=<%=sso_id %>" class="link" target = '_blank'>查看详细</a></td>   
                          </tr>
            <%
            	}
            	db.close(rs);
            %>
          </table>
  <!-- end:内容区－列表区域 -->
</div>

<!-- 内容区域结束 -->
</td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <%@ include file="./pub/dividepage.jsp" %>
     </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
  		<tr>
          <td align="center" valign="middle" class="pageBottomBorder"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'">
          	<span class="text" onclick="javascript:window.history.back()">返回</span></span></td>
        </tr>
        </table></td>
  </tr>
</table>
<%
	
%>
</body>
</html>