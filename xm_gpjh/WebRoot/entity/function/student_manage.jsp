<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="java.net.URLEncoder" %>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>
<%@ include file="./pub/commonfuction.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!
   String fixnull1(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "不详";
   			return str;
   		}
   		return str;
   }
%>


<%
String name = fixnull(request.getParameter("name"));
String stureg_no = fixnull(request.getParameter("stureg_no"));
String batch_id = fixnull(request.getParameter("batch_id"));

String sql = "";
String sql_name = "";
String sql_reg = "";
String sql_batch = "";
dbpool db = new dbpool();
MyResultSet rs =null;

//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
	
	String sql_con="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and pe.id in "+sql_ent;
					sql_con1=" and bs.fk_enterprise_id in "+sql_ent;
				}
//int totalItems = 0;

if(!name.equals(""))
		sql_name=" and bs.true_name like '%"+name.trim()+"%'";
if(!stureg_no.equals(""))
		sql_reg=" and bs.reg_no like '%"+stureg_no.trim()+"%'";
if(!batch_id.equals(""))
		sql_batch=" and bb.id='"+batch_id.trim()+"'";
sql = "select bs.id         as id,"+
      " bs.true_name       as name,"+
      " bs.reg_no     as reg_no,"+
      " bs.email      as email,"+
      " bs.gender     as gender,"+
      " bs.folk       as folk,"+
      " bs.position   as position,"+
      " bs.department as department,"+
      " bs.title      as title,"+
      " bb.name       as batch_name,"+
      " pe.name       as enterprise_name"+
 " from pe_bzz_student          bs,"+
      " pe_bzz_batch            bb,"+
      " pe_enterprise           pe,"+
      " pr_bzz_tch_stu_elective se,"+
      " pe_bzz_tch_course       tc,"+
      " pr_bzz_tch_opencourse   bo"+
" where bs.fk_batch_id = bb.id"+
 "  and bs.fk_enterprise_id = pe.id"+
 "  and se.fk_stu_id = bs.id"+
 "  and tc.name = '"+openCourse.getBzzCourse().getName()+"'"+
 " and bo.fk_course_id = tc.id"+
 " and bo.fk_batch_id = bb.id"+
 "  and se.fk_tch_opencourse_id = bo.id"+sql_con+sql_con1+sql_name+sql_reg+sql_batch+" order by bs.reg_no" ;

 //totalItems = db.countselect(sql);
//System.out.println(sql);


try{
	BasicActivityManage basicActivityManage = teacherOperationManage.createBasicActivityManage();	
%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
</head> 
<script language="javascript">
function doClean(){
		document.search.batch_id.value = "";
		document.search.name.value = "";
		document.search.stureg_no.value = "";
	}
</script>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="./images/tlq_04.gif" width="40" height="44"></td>
                            <td width="220" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getBzzCourse().getName() %> 学生列表</td>
                            <td background="./images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                <form name=search action="student_manage.jsp" method=post>    
          <tr> 
			<td valign="top" class="text3" ><div style="padding-top:25px"> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学期&nbsp;<select name="batch_id">
		                                        <option value="">所有学期</option>
		                                        <% 
		                                               
														String sql_t="select id,name from pe_bzz_batch order by id";
														rs=db.executeQuery(sql_t);
														while(rs!=null && rs.next())
														{
															String id=fixnull(rs.getString("id"));
															String batch_name=fixnull(rs.getString("name"));
															String selected="";
															if(id.equals(batch_id))
																selected="selected";
															%>
															<option value=<%=id %> <%=selected %>><%=batch_name %></option>
															<%
														}
														db.close(rs);
													 %>
													</select>
		                                      
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名&nbsp;<input type=text name="name" value="<%=name%>" size=10>&nbsp;&nbsp;学号&nbsp;<input type=text name="stureg_no" size="25" value="<%=stureg_no%>" >&nbsp;
          		<span class="link"><img src='./images/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='#' onclick='javascript:submit();'>查找</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="link">&nbsp;<a href='#' onclick='javascript:doClean();'>清除</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          		<img src='images/excel.png' alt='Print' width="20" height="20" title='Print'><a href='student_list_excel.jsp?&name=<%=java.net.URLEncoder.encode(name,"utf-8")%>&coursename=<%= openCourse.getBzzCourse().getName()%>&stureg_no=<%=stureg_no%>&batch_id=<%=batch_id%>' target="_blank">导出Excel</a>
          		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		  </tr>
		  </form>
         <td></td> 
         <tr>
		  <br>
            <tr colspan=7> 
          <td valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr align="center" bgcolor="#4582B1"> 
                <td align="center" class="title">学号</td>
                <td align="center" class="title">姓名</td>
                <td align="center" class="title">性别</td>
                <td align="center" class="title">民族</td>
                <td align="center" class="title">所在学期</td>
                <td align="center" class="title">所在企业</td>
              <!-- <td align="center" class="title">具体职务</td>
                <td align="center" class="title">职称</td>
                <td align="center" class="title">工作部门</td>  -->  
                <td align="center" class="title">电子邮件</td>                                                         
              </tr>
<%
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
				String link="&name=" + name + "&stureg_no=" + stureg_no+ "&batch_id=" + batch_id;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
		//----------分页结束---------------
        //----------------取查询条件-----------
        
        
        //----------------------------
   	    
   	   // List studentList = basicActivityManage.getTeachClassStudents(pageover, teachclass_id,stureg_no,name);
        //	       int ii = 0;
	    //   for(ii =0; ii<studentList.size();ii++){
	    //   	Student stu = (Student)studentList.get(ii);
	/*int pagesize = 0;
	String temp_pagesize = (String)session.getValue("pagesize");
	if (temp_pagesize == null || temp_pagesize.length() == 0 || temp_pagesize.equals("null"))
	{
		pagesize = 10;
	}
	else
	{
		pagesize = Integer.parseInt(temp_pagesize);
	}
	
	String temppage = fixnull(request.getParameter("pageInt"));
	if(temppage == null||temppage.equals("")||temppage.equals("null"))  temppage = "1"; 
	int pageInt = java.lang.Integer.parseInt(temppage);
	if (totalItems <= (pageInt - 1) * pagesize)
	{
		pageInt = pageInt - 1;
		if(pageInt < 1)
		{
			pageInt = 1;
		}
	}
	int maxPage = (totalItems + pagesize - 1) / pagesize;
	int pageNext = pageInt + 1;
	int pageLast = pageInt - 1;
	String link = "&name="+name+"&stureg_no="+stureg_no+"&batch_id="+batch_id;
	*/

	//rs = db.executeQuery(sql);
	while(rs!=null && rs.next())
	{
		String id = fixnull1(rs.getString("id"));
		String stu_name=fixnull1(rs.getString("name"));
		String reg_no=fixnull1(rs.getString("reg_no"));
		String email=fixnull1(rs.getString("email"));
		String gender=fixnull1(rs.getString("gender"));
		String folk=fixnull1(rs.getString("folk"));
		String position=fixnull1(rs.getString("position"));
		String department=fixnull1(rs.getString("department"));
		String title=fixnull1(rs.getString("title"));
		String enterprise_name=fixnull1(rs.getString("enterprise_name"));
	    String batch_name=fixnull1(rs.getString("batch_name"));
%>         

			<tr>
                <td align="center" class="td2"><%=reg_no %></td>
                <td align="center" class="td2"><%=stu_name %></td>
                <td align="center" class="td2"><%=gender %></td>
                <td align="center" class="td2"><%=folk %></td>
                <td align="center" class="td2"><%= batch_name%></td>                
                <td align="center" class="td2"><%=enterprise_name %></td>  
             <!--    <td align="center" class="td2"><%=position%></td>
                <td align="center" class="td2"><%=title%></td>
                <td align="center" class="td2"><%=department %></td>   -->
                <td align="center" class="td2"><%=email %></td>      
            </tr>            
<%       
			} 
				db.close(rs); 
%>
              <tr>
				<td colspan=7>
				    <%@ include file="./pub/dividepage.jsp" %>       
				</td>
              </tr>
            </table></td>
                    
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
