<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@ include file="./pub/priv.jsp"%>
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
	String search = fixnull(request.getParameter("search"));
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
	dbpool db = new dbpool();
	MyResultSet rs = null;
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		//System.out.println(t_sql);
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
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
//	function jiesuo(id,page)
//	{
//		var batch_id = id;
//		var pageInt = page;
//		var link = "pici_jiesuo.jsp?batch_id="+batch_id+"&pageInt="+pageInt;
//		if(confirm("您确定要解锁当前批次吗？")){
//		    window.location.href=link;
//			}
//	}
//	function cfmdel(link)
//	{
//		if(confirm("您确定要删除本批次吗？"))
//			window.navigate(link);
//	}

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
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">学员学习信息跟踪</td>
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
	<form name="searchForm" action="/entity/manager/statistics/study_student.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<select name="search">
			<%
				String t_sql="select id,name from pe_bzz_batch order by name";
				rs=db.executeQuery(t_sql);
				while(rs!=null && rs.next())
				{
					String t_id=fixnull(rs.getString("id"));
					String t_name=fixnull(rs.getString("name"));
					String selected="";
					if(search.equals(t_id))
						selected="selected";
				%>
				<option value="<%=t_id %>" <%=selected %>><%=t_name %></option>
				<%
				}
				db.close(rs);
			 %>
			</select>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
          	</div>
			</td>
			<td class='misc' style='white-space: nowrap;'>
<%--				<div>--%>
<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
<%--                </div>--%>
			</td>
		  </tr>
	</table>
	</form>
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
<%--	<form name='userform' action='pici_delexe.jsp' method='post' class='nomargin' onsubmit="">	--%>
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <!--<th width='0' class='select'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
              </th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="详细信息">F</a></span></div></th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link"><a href='#' title="状态设置">A</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="编辑">E</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="删除">D</a></span></div></th> -->
              <th width='10%' style='white-space: nowrap;'> <span class="link">姓名</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">学号</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在学期</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在企业</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">性别</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">出生年月</span></th>
           <!--    <th width='10%' style='white-space: nowrap;'> <span class="link">职称</span></th>  -->
               <th width='15%' style='white-space: nowrap;'> <span class="link">学习进度</span></th>
                <th width='15%' style='white-space: nowrap;'> <span class="link">作业自测状况</span></th>
              <!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
            </tr>
<%
				String sql_con="";
				String sql_con1="";
				if(!search.equals(""))
					sql_con=" and c.id='"+search+"'";
				if(!sql_ent.equals("")){
					sql_con+=" and b.id in "+sql_ent;
					sql_con1=" and a.fk_enterprise_id in "+sql_ent;
					}
				String sql = 	"select a.id, a.true_name as name, a.reg_no as reg_no, a.gender, a.age, to_char(a.birthday,'yyyy-mm-dd') as birth,a.title,a.fk_sso_user_id as sso_id,\n" +
								" b.name as ent_name,c.name as b_name from pe_bzz_student a, pe_enterprise b, pe_bzz_batch c\n" + 
								" where c.id(+) = a.fk_batch_id\n" + 
								"   and b.id(+) = a.fk_enterprise_id "+sql_con+" "+sql_con1+" order by reg_no";
			   // System.out.println(sql);
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
				String link="&search="+search;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String id=fixnull(rs.getString("id"));
					String name = fixnull(rs.getString("name"));
					String reg_no = fixnull(rs.getString("reg_no"));
					String gender = fixnull(rs.getString("gender"));
					String age = fixnull(rs.getString("age"));
					String birth = fixnull(rs.getString("birth"));
					String title = fixnull(rs.getString("title"));
					String ent_name=fixnull(rs.getString("ent_name"));
					String b_name=fixnull(rs.getString("b_name"));
					String sso_id=fixnull(rs.getString("sso_id"));
%>
            <tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>' >             
              <%-- <td class='select' align='center'> <input type='checkbox' class='checkbox' name='ids' value='<%%>' id='<%%>'> 
              </td>
              <td style='text-align: center; '><span class="link" title='详细信息' onClick="window.open('pici_info.jsp?id=<%%>')"><img src='/entity/manager/statistics/images/buttons/csv.png' alt='详细信息' width="20" height="20" title='详细信息'></span> 
              </td>
              <td style='text-align: center; '><% if(){%> <span class="link" title='锁定' ><img src='/entity/manager/statistics/images/buttons/active.png' alt='锁定' width="20" height="20" title='锁定' onClick="javascript:jiesuo('<%%>','<%%>');"></span> <% }else{ %> <span><img src='/entity/manager/statistics/images/buttons/multi_activate.png' alt='解锁' width="20" height="20" title='解锁'></span><%} %>
              </td>
              <td style='text-align: center; '><span class="link" title='编辑'><a href="pici_edit.jsp?pageInt=<%%>&id=<%%>"><img src='/entity/manager/statistics/images/buttons/edit.png' alt='编辑' width="20" height="20" title='编辑' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='删除'><a href="javascript:cfmdel('pici_delexe.jsp?pageInt=<%%>&id=<%%>')"><img src='/entity/manager/statistics/images/buttons/delete.png' alt='删除' width="20" height="20" title='删除' border=0></a></span> 
              </td> --%>

              <td style='white-space: nowrap;text-align:center;'><%=name%></td>
              <td style='white-space: nowrap;text-align:center;'><%=reg_no%></td>
              <td style='white-space: nowrap;text-align:center;'><%=b_name%></td>
              <td style='white-space: nowrap;text-align:center;'><%=ent_name%></td>
              <td style='white-space: nowrap;text-align:center;'><%=gender%></td>   
              <td style='white-space: nowrap;text-align:center;'><%=birth%></td>   
             <!--   <td style='white-space: nowrap;'><%=title %></td>    -->
               <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_study_progress.jsp?pageInt1=<%=pageInt%>&id=<%=id%>&name=<%=name%>&search1=<%=search %>&sso_id=<%=sso_id %>" class="link"><u><font color = "#0000ff" >查看</font></u></a></td>   
                <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_study_score.jsp?pageInt1=<%=pageInt%>&id=<%=id%>&search1=<%=search %>&name=<%=name%>" class="link"><u><font color = "#0000ff" >查看</font></u></a></td>   
              <%--<td style='white-space: nowrap;'><%%></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="pici_edu_major.jsp?pici=<%%>&pici_id=<%%>&Search=<%%>&pageInt=<%%>">设置</td> --%>
            </tr>
            <%
            	}
            	db.close(rs);
            %>
          </table>
<%--          </form>--%>
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
</table>
<%
	
%>
</body>
</html>