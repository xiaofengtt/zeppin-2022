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
		sql = "select a.total_20, b.total_30,c.total_40,d.total_50,e.total_60,f.total_70,g.total_80,h.total_10 "+
          "from (select count(si.id) as total_20 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
           "where ((sysdate-si.birthday)/365) > = 10 and  ((sysdate-si.birthday)/365) <20 "+
             "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) a, "+
         "(select count(si.id) as total_30 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
          "where ((sysdate-si.birthday)/365) > = 20 and  ((sysdate-si.birthday)/365) <30 "+
             "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) b, "+
          "(select count(si.id) as total_40 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
          "where ((sysdate-si.birthday)/365) > = 30 and  ((sysdate-si.birthday)/365) <40 "+
             "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) c, "+
          "(select count(si.id) as total_50 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
          "where ((sysdate-si.birthday)/365) > = 40 and  ((sysdate-si.birthday)/365) <50 "+
            " and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) d,"+
          "(select count(si.id) as total_60 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
         " where ((sysdate-si.birthday)/365) > = 50 and  ((sysdate-si.birthday)/365) <60 "+
             "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" )e, "+
          "(select count(si.id) as total_70 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
         " where ((sysdate-si.birthday)/365) > = 60 and  ((sysdate-si.birthday)/365) <70 "+
            " and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) f, "+
          "(select count(si.id) as total_80 "+
           " from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
        "  where ((sysdate-si.birthday)/365) > = 70 and  ((sysdate-si.birthday)/365) <80 "+
            " and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) g,"+
          "(select count(si.id) as total_10 "+
            "from pe_bzz_batch b, pe_bzz_student si, pe_enterprise e "+
           "where ((sysdate-si.birthday)/365) > = 0 and  ((sysdate-si.birthday)/365) <10 "+
             "and si.fk_batch_id = b.id and e.id = si.fk_enterprise_id "+sql_con+" ) h";
        //System.out.println(sql);
        rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{
			String total_10 = fixnull(rs.getString("total_10"));
			String total_20 = fixnull(rs.getString("total_20"));
			String total_30 = fixnull(rs.getString("total_30"));
			String total_40 = fixnull(rs.getString("total_40"));
			String total_50 = fixnull(rs.getString("total_50"));
			String total_60 = fixnull(rs.getString("total_60"));
			String total_70 = fixnull(rs.getString("total_70"));
			String total_80 = fixnull(rs.getString("total_80"));


        
		StringBuffer sqltemp=new StringBuffer();
        String str ="<chart palette='2'  xAxisName='年龄' yAxisName='Number' showValues='0' decimals='0' formatNumberScale='0'>"+
       				"<set label='0~10' value='"+total_10+"' />"+
                    "<set label='10~20' value='"+total_20+"' />"+
                    "<set label='20~30' value='"+total_30+"' />"+
                    "<set label='30~40' value='"+total_40+"' />"+
                    "<set label='40~50' value='"+total_50+"' />"+
                    "<set label='50~60' value='"+total_60+"' />"+
                    "<set label='60~70' value='"+total_70+"' />"+
                    "<set label='70~80' value='"+total_80+"' /></chart>"; 

        
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
