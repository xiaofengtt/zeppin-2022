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
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
	String search1 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search1")),"UTF-8"));
	String c_search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("c_search")),"UTF-8"));
		
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String name = fixnull(request.getParameter("name"));
	
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
                 <%
  	  if(!enterprise_id.equals("")){
  	 String sql_total = "select b.name as enterprise_name  from pe_enterprise b where b.id ='"+enterprise_id+"'";
  	 //System.out.println(sql);
  	 rs = db.executeQuery(sql_total);
		while(rs!=null&&rs.next())
		{ 
		   String enterprise_name = fixnull(rs.getString("enterprise_name"));
		   
  %>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看<%=enterprise_name %>达到学时学员列表</td>
             
              </tr>
                <%}
         db.close(rs);
         }
         else{
   %>
         
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看达到学时学员列表</td>
             
              </tr>
              <%} %>
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
<form name="searchForm" action="/entity/manager/statistics/stat_enterprise_completed_num.jsp" method="post">
	<input type="hidden" name="batch_id" value="<%=batch_id%>"/>
	<input type="hidden" name="c_search" value="<%=c_search%>"/>
	<input type="hidden" name="enterprise_id" value="<%=enterprise_id%>"/>
	<input type="hidden" name="pageInt1" value="<%=pageInt1%>"/>
	<input type="hidden" name="search1" value="<%=search1%>"/>
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
				<input type='text' name='search' value='<%=search %>' size='20' maxlength='245' style='vertical-align: bottom;'>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找（学号）</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
          	    <span class="link"><a href = "/entity/manager/statistics/stat_enterprise_completed_excel.jsp?pageInt1=<%=pageInt1%>&search=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id=<%=enterprise_id %>&batch_id=<%=batch_id%>&c_search=<%=java.net.URLEncoder.encode(c_search,"UTF-8")%>&search1=<%=java.net.URLEncoder.encode(search1,"UTF-8") %>">导出统计信息</a></span>
               
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
              <th width='15%' style='white-space: nowrap;'> <span class="link">学号</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">姓名</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在学期</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在企业</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">移动电话</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">电子邮件</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">已学完学时数</span></th>
              
              <!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
            </tr>
<%
				String sql_con="";
				String sql_en="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and e.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				if(!enterprise_id.equals(""))
				{
					sql_en ="and e.id = '"+enterprise_id+"'";
				}
				if(!batch_id.equals(""))
				{
					sql_en +="and b.id = '"+batch_id+"'";
				}
				
       String sql_t ="select enterprise_name,student_id,student_name,student_reg_no,student_phone,student_email,batch_name,total_time from (" +
       				" select "+
                      " e.name as enterprise_name, "+
                      " s.id as student_id, "+
                      " s.true_name as student_name, "+
                      " s.reg_no as student_reg_no, "+
                      " s.mobile_phone as student_phone, "+
                      " s.email as student_email, "+
                      " b.name as batch_name ,sum(c.time) as total_time "+                     
                 " from pe_bzz_batch            b, "+
                 "      pe_bzz_student          s, "+
                 "      pe_enterprise           e, "+
                 "      sso_user                u, "+
                 "      training_course_student cs, "+
                 "      pr_bzz_tch_opencourse   o, "+
                 "      pe_bzz_tch_course       c "+
                " where b.id = s.fk_batch_id "+
                "   and b.id = o.fk_batch_id "+
                "   and s.fk_sso_user_id = u.id "+
                "   and u.id = cs.student_id "+
                "   and s.fk_enterprise_id = e.id "+
                "   and cs.course_id = o.id "+
                "   and o.fk_course_id = c.id "+
                "  and cs.learn_status = 'COMPLETED'"+
                "   "+sql_con+" "+sql_en+" "+sql_con1+
                " and s.reg_no like '%"+search+"%' "+
                " group by  e.name, s.id, s.reg_no, s.true_name,s.mobile_phone,s.email,b.name order by s.reg_no )"+
                " where total_time>=(select nvl(standards,0) from pe_bzz_batch where id='"+batch_id+"') order by student_reg_no";
                //System.out.println(sql_t);
				int totalItems = db.countselect(sql_t);
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
				String link="&batch_id="+batch_id+"&search="+search+"&enterprise_id="+enterprise_id+"&pageInt1="+pageInt1+"&c_pageInt="+c_pageInt+"&search1="+search1+"&c_search="+c_search;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql_t,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					
					String enterprise_name = fixnull(rs.getString("enterprise_name"));
					String student_id = fixnull(rs.getString("student_id"));
					String student_name = fixnull(rs.getString("student_name"));
					String student_phone = fixnull(rs.getString("student_phone"));
					String student_reg_no = fixnull(rs.getString("student_reg_no"));
					String student_email = fixnull(rs.getString("student_email"));
					String batch_name = fixnull(rs.getString("batch_name"));
					String total_time=fixnull(rs.getString("total_time"));
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

              <td style='white-space: nowrap;text-align:center;'><%=student_reg_no%></td>
              <td style='white-space: nowrap;'><%=student_name%></td>
              <td style='white-space: nowrap;'><%=batch_name%></td>
              <td style='white-space: nowrap;'><%=enterprise_name%></td>
              <td style='white-space: nowrap;'><%=student_phone%></td>   
              <td style='white-space: nowrap;'><%=student_email%></td>    
              <td style='white-space: nowrap; text-align:center;'><%=total_time%></td>
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
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
  		<%if(!enterprise_id.equals("")) { %>
          <td align="center" valign="middle" class="pageBottomBorder"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'">
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_enterprise.jsp?pageInt1=<%=pageInt1%>&search1=<%=search1%>&pageInt=<%=c_pageInt%>&search=<%=c_search%>&id=<%=batch_id%>&name=<%=name%>')">返回</span></span></td>
        <%}
         else { %>
         <td align="center" valign="middle" class="pageBottomBorder"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'">
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_student.jsp?pageInt=<%=pageInt1%>&search=<%=search1%>&id=<%=batch_id%>&name=<%=name%>')">返回</span></span></td>
         <%} %>
        </tr>
          </table></td>
  </tr>
</table>
<%
	
%>
</body>
</html>