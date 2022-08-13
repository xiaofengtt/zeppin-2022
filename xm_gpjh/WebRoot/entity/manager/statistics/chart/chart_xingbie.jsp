<%@ page language="java" import="java.util.*"  contentType="text/xml;charset=gbk"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

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
	 String d=fixnull(request.getParameter("d"));
	   String batch_id="";
	   if(d.indexOf("|")>0)
	   		batch_id=fixnull(d.substring(0,d.indexOf("|")));
	   		
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
				
		String sql_con="";
		if(!sql_ent.equals(""))
		sql_con+=" and e.id in "+sql_ent;	
		
		if(!batch_id.equals(""))
			sql_con+=" and b.id='"+batch_id+"'";

		String sql = "";
		sql = "select a.total_female, b.total_male "+
        	  "from (select count(s.gender) as total_female "+
              "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
              "where s.gender = '女' "+
              "and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) a, "+
              "(select count(si.gender) as total_male "+
              " from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
              "where si.gender = '男' " +
              "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) b ";
       // System.out.println(sql); 
        rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{
			String total_female = fixnull(rs.getString("total_female"));
			String total_male = fixnull(rs.getString("total_male"));
        
		StringBuffer sqltemp=new StringBuffer();
        String str ="<chart palette='4'><set label='男' value='"+total_male+"' /> <set label='女' value='"+total_female+"' />   </chart>";
		sqltemp.append(str);
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		PrintWriter w = response.getWriter();
		w.write(sqltemp.toString());
		w.close();
		}
		db.close(rs);
%>
