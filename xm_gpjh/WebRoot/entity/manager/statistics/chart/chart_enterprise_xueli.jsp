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
	   String enterprise_id="";
	  String[] strArrays=d.split("\\|");
	  enterprise_id=strArrays[0];
	  batch_id=strArrays[1];
	  
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

       sql = "select a.chuzhong, b.gaozhong,c.zhigao,d.zhongzhuan,e.jixiao,f.dazhuan,g.daben,h.shuoshi,i.boshi "+
          "from (select count(s.education) as chuzhong "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
           "where s.education = '初中'"+
             "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) a, "+
         "(select count(s.education) as gaozhong "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
          "where s.education = '高中' "+
             "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) b, "+
          "(select count(s.education) as zhigao "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
          "where s.education = '职高' "+
             "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) c, "+
          "(select count(s.education) as zhongzhuan "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
          "where s.education = '中专' "+
            "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) d,"+
          "(select count(s.education) as jixiao "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
         " where s.education = '技校' "+
             "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" )e, "+
          "(select count(s.education) as dazhuan "+
            "from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
         " where s.education = '大专' "+
            "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) f, "+
          "(select count(s.education) as daben "+
           " from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
        "  where s.education = '大本' "+
            "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) g,"+
        "(select count(s.education) as shuoshi "+
           " from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
        "  where s.education = '硕士' "+
            "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) h, "+
        "(select count(s.education) as boshi "+
           " from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e "+
        "  where s.education = '博士' "+
            "and e.id = '"+enterprise_id+"' and s.fk_batch_id = b.id and e.id = s.fk_enterprise_id "+sql_con+" ) i ";
        rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{
            String chuzhong = fixnull(rs.getString("chuzhong"));
			String gaozhong = fixnull(rs.getString("gaozhong"));
			String zhigao = fixnull(rs.getString("zhigao"));
			String zhongzhuan = fixnull(rs.getString("zhongzhuan"));
			String jixiao = fixnull(rs.getString("jixiao"));
			String dazhuan = fixnull(rs.getString("dazhuan"));
			String daben = fixnull(rs.getString("daben"));
			String shuoshi = fixnull(rs.getString("shuoshi"));
			String boshi  = fixnull(rs.getString("boshi"));
			
			
			
			
		   StringBuffer sqltemp=new StringBuffer();
	       String str ="<chart palette='2'  xAxisName='学历' yAxisName='Number' showValues='0' decimals='0' formatNumberScale='0'>"+
	                    "<set label='初中' value='"+chuzhong+"' />"+
	                    "<set label='高中' value='"+gaozhong+"' />"+
	                    "<set label='职高' value='"+zhigao+"' />"+
	                    "<set label='中专' value='"+zhongzhuan+"' />"+
	                    "<set label='技校' value='"+jixiao+"' />"+
	                    "<set label='大专' value='"+dazhuan+"' />"+
	                    "<set label='大本' value='"+daben+"' />"+
	                    "<set label='硕士' value='"+shuoshi+"' />"+
	                    "<set label='博士' value='"+boshi+"' />"+
	                   "</chart>"; 
	                    
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
