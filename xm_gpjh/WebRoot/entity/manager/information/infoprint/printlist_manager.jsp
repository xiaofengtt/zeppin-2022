<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
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
	String enterprisename = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("enterprisename")),"UTF-8"));
	String search1 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search1")),"UTF-8"));
	String c_search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("c_search")),"UTF-8"));
		
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String pageInt2 = fixnull(request.getParameter("pageInt"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String name = fixnull(request.getParameter("name"));
	String bSub=fixnull(request.getParameter("bSub"));
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
	
	String urlexcel="studentinfo_admin_excel.jsp";
	String urlword ="studentinfo_admin_pdf.jsp";
	
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
		urlexcel="studentinfo_subadmin_excel.jsp";
		urlword ="studentinfo_subadmin_pdf.jsp";
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

function getpdf(){
	
	var eninfo = document.getElementById("enterprise_id").value;
	if(eninfo==""){
		alert("请选择要导出的企业管理员信息");
	}else{
		var url ="/entity/manager/information/infoprint/studentinfo_admin_pdf.jsp?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id=<%=enterprise_id %>&batch_id=<%=batch_id%>&bSub=<%=bSub %>";
		window.location=url;
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
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看<%=enterprise_name %>管理员列表</td>
             
              </tr>
                <%}
         db.close(rs);
         }
         else {
   %>    
            <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看管理员列表</td>
             
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
<form name="searchForm" action="/entity/manager/information/infoprint/printlist_manager.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<%
				String sql_batch ="select * from pe_bzz_batch t order by t.start_time" ; 
			 String sql_econ="";
			 if(!sql_ent.equals(""))
				{
				 sql_econ+=" where t.id in "+sql_ent;
				}
				String sql_enter="select * from pe_enterprise t "+sql_econ+"order by t.name asc" ; 
				rs = db.executeQuery(sql_enter);
			%>
				企业: <select id="enterprise_id" name="enterprise_id" >
				 			<option value="">请选择企业</option>
				 			<%
				 				while(rs!=null&&rs.next()){
				 					String selected="";
				 					String t_id=fixnull(rs.getString("id"));
				 					String t_name=fixnull(rs.getString("name"));
				 					if(enterprise_id.equals(t_id))
				 						selected="selected";
				 			%>
				 		<option value="<%=rs.getString("id") %>" <%=selected %>><%=rs.getString("name")%></option>							 					
				 			<%
				 				}
				 			db.close(rs);
				 			%>
					 </select>
          		 
          		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          	    <span class="link"><a href = "/entity/manager/information/infoprint/<%=urlexcel%>?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id=<%=enterprise_id %>&batch_id=<%=batch_id%>&bSub=<%=bSub %>">导出管理员信息</a></span>
				<span class="link"><a id ="getpdf" href = "#" onclick="getpdf();">导出照片模板</a></span>
               
          	</div>
			</td>
			<td class='misc' style='white-space: nowrap;'>
<%--				<div>--%>
<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
<%--                </div>--%>
			</td>
		  </tr>
		  <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			是否包含二级企业管理员:
			<select name="bSub">
			<option value="1" <%if(bSub.equals("1")) out.print("selected");%>>是</option>
			<option value="0" <%if(bSub.equals("0")) out.print("selected");%>>否</option>
			</select>&nbsp;&nbsp;<font color=red>(所选企业为一级企业时，此项才起作用)</font>
			<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
			</div>
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
            <!--    <th width='15%' style='white-space: nowrap;'> <span class="link">管理员</span> 
              </th>
             -->
              <th width='10%' style='white-space: nowrap;'> <span class="link">姓名</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在企业</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">所在职位</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">移动电话</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">电子邮件</span></th>
              
              
              <!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
            </tr>
<%

if(enterprise_id.equals(""))
{
%>
<tr>
<td style='white-space: nowrap;'>请先选择一个企业，进行查找。</td>
</tr>
<%
}

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
					if(bSub.equals("1"))
					{
						sql_en = " and (e.id = '" + enterprise_id + "' or e.fk_parent_id ='"+enterprise_id+"')";
					}
					else
						sql_en =" and e.id = '"+enterprise_id+"'";
				}
				System.out.println("sql_en:"+sql_en);
				String sql_t = "";
		 sql_t =   "select em.id as manager_id ,em.position as manager_position   "+
		 	 " ,em.mobile_phone as manager_phone , e.name as enterprise_name,  " +
			 "   em.email as manager_email ,    em.name  as manager_name  " + 
			 "  from pe_enterprise_manager  em,  " + 
			 "       pe_enterprise           e  " + 
			 " where em.fk_enterprise_id = e.id  "+
              "   "+sql_en+" "+sql_con+" "+sql_con1+" order by e.code";
                //System.out.println(sql_t);
                System.out.println("sql==============>"+sql_t);
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
					String manager_id = fixnull(rs.getString("manager_id"));
					String manager_name = fixnull(rs.getString("manager_name"));
					String manager_phone = fixnull(rs.getString("manager_phone"));
					String manager_email = fixnull(rs.getString("manager_email"));
					String manager_position = fixnull(rs.getString("manager_position"));
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
			
              <td style='white-space: nowrap;text-align:center;'><%=manager_name%></td>
              <!-- 
              	<td style='white-space: nowrap;'><%=manager_name%></td>
               -->
              <td style='white-space: nowrap;'><%=enterprise_name%></td>
              <td style='white-space: nowrap;'><%=manager_position%></td>
              <td style='white-space: nowrap;'><%=manager_phone%></td>   
              <td style='white-space: nowrap;'><%=manager_email%></td>    
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