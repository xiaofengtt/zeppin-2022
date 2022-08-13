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
	   String opencourseId="";
	   if(d.indexOf("|")>0)
	   		opencourseId=d.substring(0,d.indexOf("|"));
	   
	   int[] results=new int[5];
	   int total=0;
	   String assess="";
	   dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql="";
		sql="select a.assess as assess, count(a.assess) as num from pe_bzz_assess a ,pe_bzz_course_feedback f "
			 +"where f.px='5' and a.fk_feedback_id=f.id and a.fk_course_id='"+opencourseId+"'"
			 +"group by a.assess";
		rs=db.executeQuery(sql);
		while(rs!=null && rs.next())
		{
			assess=fixnull(rs.getString("assess"));
			if(assess.equals("很好"))
			{
				results[0]=rs.getInt("num");
				total+=results[0];
			}
			else if(assess.equals("好"))
			{
				results[1]=rs.getInt("num");
				total+=results[1];
			}
			else if(assess.equals("一般"))
			{
				results[2]=rs.getInt("num");
				total+=results[2];
			}
			else if(assess.equals("较差"))
			{
				results[3]=rs.getInt("num");
				total+=results[3];
			}
			else if(assess.equals("差"))
			{
				results[4]=rs.getInt("num");
				total+=results[4];
			}
		}
		db.close(rs);
		
		//System.out.println("r0:"+results[0]+",r1:"+results[1]+",r2:"+results[2]+",r3:"+results[3]+",r4:"+results[4]);
	   
	   StringBuffer sqltemp=new StringBuffer();
       String str ="<chart palette='4' caption='总人数："+total+"' showPercentageValues='1'>"+
                    "<set label='很好' value='"+results[0]+"' />"+
                    "<set label='好' value='"+results[1]+"' />"+
                    "<set label='一般' value='"+results[2]+"' />"+
                    "<set label='较差' value='"+results[3]+"' />"+
                    "<set label='差' value='"+results[4]+"' />"+
                   "</chart>"; 
                    
		sqltemp.append(str);
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		PrintWriter w = response.getWriter();
		w.write(sqltemp.toString());
		w.close();
%>
