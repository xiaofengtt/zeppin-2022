<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=gbk"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page import="java.io.PrintWriter" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%!//判断字符串为空的话，赋值为""
    String fixnull(String str) {
	if (str == null || str.equals("null") || str.equals(""))
	    str = "";
	return str;
    }

private int CheckNum(Double dou){
    int num = 0;
    if(dou>=0&&dou<20){
		num=1;
    }
    if(dou>=20&&dou<40){
	num=2;
}
    if(dou>=40&&dou<60){
	num=3;
}
    if(dou>=60&&dou<80){
	num=4;
}
    if(dou>=80&&dou<100){
	num=5;
}
    if(dou>=100&&dou<120){
	num=6;
}
    if(dou>=120&&dou<140){
	num=7;
}
    if(dou>=140&&dou<160){
	num=8;
}
    if(dou>=160&&dou<=180){
	num=9;
}
    return num;
}
    %>
<%
    UserSession us = (UserSession) session
		    .getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
    List entList = us.getPriEnterprises();

	String btsr = fixnull(request.getParameter("d"));
	StringTokenizer strk = new StringTokenizer(btsr,"|");
	String temp[] = new String [strk.countTokens()];
	int k =0;
	while(strk.hasMoreTokens()){
	    temp[k] = strk.nextToken();
	    k++;
	}
	String bath =temp[1];
	String emp =temp[0];
	
	
	
	
	
    String sql_ent = "";
    String sql_entbk = "";
    for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
    }
    sql_entbk = sql_ent;
    if (!sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)
			+ ")";
    }
    dbpool db = new dbpool();
    MyResultSet rs = null;
    if (us.getRoleId().equals("2")
		    || us.getRoleId()
			    .equals("402880a92137be1c012137db62100006")) //表示为一级主管和一级辅导员
    {
		String t_sql = "select id from pe_enterprise where fk_parent_id in "
			+ sql_ent;
		rs = db.executeQuery(t_sql);
		while (rs != null && rs.next()) {
		    sql_entbk += "'" + fixnull(rs.getString("id")) + "',";
		}
		db.close(rs);
		sql_ent = "(" + sql_entbk.substring(0, sql_entbk.length() - 1)
			+ ")";
    }

    String sql_con = "";
    String sql_con1 = "";
    //if(!search.equals(""))
    //sql_con=" and b.id='"+search+"'";
    if (!sql_ent.equals("")) {
		sql_con += " and pe.id in " + sql_ent;
		sql_con1 = " and ps.fk_enterprise_id in " + sql_ent;
    }

    String sql_bath = "";
	String sql_emp = "";
	if(emp!=""){
	    sql_emp =" and ps.fk_enterprise_id  = '"+emp+"'   ";
	}
	if(bath!=""){
	    sql_bath =" and ps.fk_batch_id  ='"+bath+"'   ";
	}
	
	
	String sql=" select ps.id, sum(ce.time*(ts.percent/100)) as stime  from sso_user su ,training_course_student ts , " +
	"pe_bzz_student ps ,pr_bzz_tch_opencourse co ,pe_bzz_tch_course ce , pe_enterprise pe   "+ 
	" where ps.fk_sso_user_id = su.id and  su.id = ts.student_id  and  pe.id = ps.fk_enterprise_id"+ 
	" and co.id = ts.course_id and co.fk_course_id = ce.id and  ps.fk_batch_id = co.fk_batch_id  "
	+sql_bath+"  "+sql_con+"  "+sql_con1+"  "+sql_emp+"   group by ps.id ";
	
	System.out.println("sql===============>"+sql);
    rs = db.executeQuery(sql);
    int total_100 = 0;
    int total_20 = 0;
    int total_120 = 0;
    int total_40 = 0;
    int total_140 = 0;
    int total_60 = 0;
    int total_160 = 0;
    int total_80 = 0;
    int total_180 = 0;
    while (rs != null && rs.next()) {

		Double dou = rs.getDouble("stime");
		switch (this.CheckNum(dou)){
		case 1:
		    total_20  = total_20+1;
			break;
		case 2:
		    total_40  = total_40+1;
			break;
		case 3:
		    total_60  = total_60+1;
			break;
		case 4:
		    total_80  = total_80+1;
			break;
		case 5:
		    total_100  = total_100+1;
			break;
		case 6:
		    total_120  = total_120+1;
			break;
		case 7:
		    total_140  = total_140+1;
			break;
		case 8:
		    total_160  = total_160+1;
			break;
		case 9:
		    total_180  = total_180+1;
			break;
	}
    }
    db.close(rs);
    StringBuffer sqltemp = new StringBuffer();
    String str = "<chart palette='2'  xAxisName='学时' yAxisName='Number' showValues='0' decimals='0' formatNumberScale='0'>"
		    + "<set label='0~20' value='"
		    + total_20
		    + "' />"
		    + "<set label='20~40' value='"
		    + total_40
		    + "' />"
		    + "<set label='40~60' value='"
		    + total_60
		    + "' />"
		    + "<set label='60~80' value='"
		    + total_80
		    + "' />"
		    + "<set label='80~100' value='"
		    + total_100
		    + "' />"
		    + "<set label='100~120' value='"
		    + total_120
		    + "' />"
		    + "<set label='120~140' value='"
		    + total_140
		    + "' />"
		    + "<set label='140~160' value='"
		    + total_160
		    + "' />"
		    + "<set label='160~180' value='"
		    + total_180 + "' /></chart>";

    sqltemp.append(str);

    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    PrintWriter w = response.getWriter();
    w.write(sqltemp.toString());
    w.close();
%>
