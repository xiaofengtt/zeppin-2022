<%@ page language="java" import="java.util.*"  contentType="text/xml;charset=gbk"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%!
	//�ж��ַ���Ϊ�յĻ�����ֵΪ""
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
	   
	   int[] results=new int[3];
	   int total=0;
	   String assess="";
	   dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql="";
		sql="select a.assess as assess, count(a.assess) as num from pe_bzz_assess a ,pe_bzz_course_feedback f "
			 +"where f.px='8' and a.fk_feedback_id=f.id and a.fk_course_id='"+opencourseId+"'"
			 +"group by a.assess";
		rs=db.executeQuery(sql);
		while(rs!=null && rs.next())
		{
			assess=fixnull(rs.getString("assess"));
			if(assess.equals("����"))
			{
				results[0]=rs.getInt("num");
				total+=results[0];
			}
			else if(assess.equals("ƫ��"))
			{
				results[1]=rs.getInt("num");
				total+=results[1];
			}
			else if(assess.equals("ƫ��"))
			{
				results[2]=rs.getInt("num");
				total+=results[2];
			}
		}
		db.close(rs);
		
		//System.out.println("r0:"+results[0]+",r1:"+results[1]+",r2:"+results[2]+",r3:"+results[3]+",r4:"+results[4]);
	   
	   StringBuffer sqltemp=new StringBuffer();
       String str ="<chart palette='4' caption='��������"+total+"' showPercentageValues='1'>"+
                    "<set label='����' value='"+results[0]+"' />"+
                    "<set label='ƫ��' value='"+results[1]+"' />"+
                    "<set label='ƫ��' value='"+results[2]+"' />"+
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
