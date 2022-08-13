<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
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
	//String search = fixnull(request.getParameter("search"));
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
	String search2 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search2")),"UTF-8"));
	String search1 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search1")),"UTF-8"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String course_id = fixnull(request.getParameter("course_id"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String batch_name = "";
	String course_name = "";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String batch_sql = "select b.name as batch_name,c.name as course_name from pe_bzz_batch b,pe_bzz_tch_course c where b.id = '"+batch_id+"' and c.id='"+course_id+"'";
	rs = db.executeQuery(batch_sql);
	if(rs!=null&&rs.next())
	{
		batch_name = rs.getString("batch_name");
		course_name = rs.getString("course_name");
	}
	db.close(rs);
	
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
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看<%=batch_name+course_name%>选课统计信息</td>
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
	<form name="searchForm" action="/entity/manager/statistics/stat_elective_enterprise.jsp" method="post">
	<input type="hidden" name="batch_id" value="<%=batch_id %>"/>
	<input type="hidden" name="course_id" value="<%=course_id %>"/>
	<input type="hidden" name="pageInt1" value="<%=pageInt1 %>"/>
	<input type="hidden" name="search1" value="<%=search1 %>"/>
	<input type="hidden" name="search2" value="<%=search2 %>"/>
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
				<input type='text' name='search' value='<%=search %>' size='20' maxlength='245' style='vertical-align: bottom;'>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找（请输入企业名称）</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          	     <span class="link"><a href = "/entity/manager/statistics/stat_elective_enterprise_excel.jsp?&search=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&course_id=<%=course_id%>&batch_id=<%=batch_id%>">导出统计信息</a></span>
              
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
              <th width='40%' style='white-space: nowrap;'> <span class="link">企业名称</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">选课人数</span> 
              </th>
            </tr>
<%
				  String sql_con="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and en.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				String sql = "select id,name,num from (select en.id,en.name,count(e.fk_stu_id) as num from pe_bzz_batch b,pe_bzz_tch_course c,pr_bzz_tch_opencourse o,pr_bzz_tch_stu_elective e,pe_bzz_student s,pe_enterprise en "
							+"	where b.id=o.fk_batch_id "+sql_con+" "+sql_con1+"  and c.id=o.fk_course_id and o.id=e.fk_tch_opencourse_id and e.fk_stu_id=s.id and s.fk_enterprise_id=en.id and c.id='"+course_id+"' and b.id='"+batch_id+"' "
							+"	group by en.id,en.name) where name like '%"+search+"%' order by name";
				//System.out.println(sql);
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
				String link="&search="+java.net.URLEncoder.encode(search,"UTF-8")+"&batch_id="+batch_id+"&course_id="+course_id+"&pageInt1="+pageInt1+"&search1="+search1+"&search2="+search2;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String enterprise_id = fixnull(rs.getString("id"));
					String enterprise_name = fixnull(rs.getString("name"));
					String num = fixnull(rs.getString("num"));
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

              <td style='white-space: nowrap;text-align:center;'><%=enterprise_name%></td>
              <td style='white-space: nowrap;'><a href="/entity/manager/statistics/stat_course_total_num.jsp?pageInt1=<%=pageInt1%>&pageInt2=<%=pageInt %>&search2=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&batch_id=<%=batch_id%>&search1=<%=java.net.URLEncoder.encode(search1,"UTF-8")%>&course_id=<%=course_id %>&enterprise_id=<%=enterprise_id %>" class="link"><%=num%></td>
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
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
  		<tr>
          <td align="center" valign="middle" class="pageBottomBorder"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'">
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_elective_exe.jsp?batch_id=<%=batch_id %>&pageInt=<%=pageInt1 %>&search=<%=java.net.URLEncoder.encode(search1,"UTF-8")%>')">返回</span></span></td>
        </tr>
        </table></td>
  </tr>
</table>
<%
	
%>
</body>
</html>