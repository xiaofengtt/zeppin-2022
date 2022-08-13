<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=studentinfo_total_exce.xls"); %>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}%>
<%
	String search = fixnull(request.getParameter("search"));
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String enterprise_id = fixnull(request
			.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
	String name = fixnull(java.net.URLDecoder.decode(fixnull(request
			.getParameter("name")), "UTF-8"));

	UserSession us = (UserSession) session
			.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList = us.getPriEnterprises();
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
	String sql_en = "";
	String sql_con1 = "";
	//if(!search.equals(""))
	//sql_con=" and b.id='"+search+"'";
	if (!sql_ent.equals("")) {
		sql_con += "  and ee.id in " + sql_ent;
		sql_con1 = "  and ps.fk_enterprise_id in " + sql_ent;
	}
	if (!enterprise_id.equals("")) {
		sql_en = "and ee.id = '" + enterprise_id + "'";
	}
	if (!batch_id.equals("")) {
		sql_en += "and pb.id = '" + batch_id + "'";
	}
	

		String esql ="select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
			+ "                pe.fax as fax, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,\n"
			+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb,\n"
			+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail,\n"
			+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
			+ "                 p.address, p.zipcode, p.fax, p.fzr_name, p.fzr_xb, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p\n"
			+ "                 where p.fk_parent_id is null and s.fk_enterprise_id = p.id union select s.id as sid, p.id as pid,p.name, p.code, p.industry,\n"
			+ "                 p.address,p.zipcode, p.fax, p.fzr_name, p.fzr_xb, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email, p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p, pe_enterprise p1\n"
			+ "                 where p.fk_parent_id is null and p1.fk_parent_id = p.id and p1.id = s.fk_enterprise_id ) pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
			+ "                 where pe.fk_parent_id is null and pe.id = '"+enterprise_id+"'  group by pe.code,pe.name,pe.industry,\n"
			+ "                 pe.address, pe.zipcode,pe.fax, pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_position,\n"
			+ "               pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
			+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
			+ "                 pe.lxr_email , pe.lxr_address " ;;

			
			System.out.println("esql =======================================>"+esql);

	String sql_t = "select rownum , ps.true_name as name  , ps.reg_no as regno ,  ss.password as passwords ,\n"
			+ "ps.gender as gender ,ps.folk as folk , ps.education as education , ps.birthday as birthday ,\n"
			+ " ee.ename as pename  ,  ee.name as subname , ps.mobile_phone as mobile , ps.phone as phone ,\n"
			+ " ps.email as email  from pe_bzz_student ps inner join sso_user ss on ps.fk_sso_user_id = ss.id\n"
			+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  "
			+ " inner join (select pe.id,pe.name,pes.name as ename from pe_enterprise pe\n"
			+ " left outer join pe_enterprise pes on pe.fk_parent_id= pes.id) ee\n"
			+ " on ps.fk_enterprise_id = ee.id  where 1=1 " + sql_en + sql_con
			+ "  "+ sql_con1 + " and ps.reg_no like '%" + search + "%' order by ps.reg_no";
	 
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:x="urn:schemas-microsoft-com:office:excel"
	xmlns="http://www.w3.org/TR/REC-html40">

	<head>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<meta name=ProgId content=Excel.Sheet>
		<meta name=Generator content="Microsoft Excel 12">
		<link rel=File-List href="ewqeqweqw_files/filelist.xml">
		<style id="31312312_28185_Styles">
<!--
table {
	mso-displayed-decimal-separator: "\.";
	mso-displayed-thousand-separator: "\,";
}

.font528185 {
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
}

.xl1528185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 12.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: general;
	vertical-align: bottom;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6528185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl6628185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: general;
	vertical-align: bottom;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6728185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6828185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl6928185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7028185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: blue;
	font-size: 12.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: underline;
	text-underline-style: single;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7128185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7228185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7328185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: blue;
	font-size: 12.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: underline;
	text-underline-style: single;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7428185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7528185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7628185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 11.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7728185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 11.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7828185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 11.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7928185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8028185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8128185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8228185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8328185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8428185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8528185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 18.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: bottom;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8628185 {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 12.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: General;
	text-align: center;
	vertical-align: bottom;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

ruby {
	ruby-align: left;
}

rt {
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-char-type: none;
}
-->
</style>
	</head>
	
	<body>
		<!--[if !excel]>　　<![endif]-->
		<!--下列信息由 Microsoft Office Excel 的“发布为网页”向导生成。-->
		<!--如果同一条目从 Excel 中重新发布，则所有位于 DIV 标记之间的信息均将被替换。-->
		<!----------------------------->
		<!--“从 EXCEL 发布网页”向导开始-->
		<!----------------------------->

		<div id="31312312_28185" align=center x:publishsource="Excel">

			<table border=0 cellpadding=0 cellspacing=0 width=1183
				style='border-collapse: collapse; table-layout: fixed; width: 888pt'>
				<col width=99
					style='mso-width-source: userset; mso-width-alt: 3168; width: 74pt'>
				<col width=72 style='width: 54pt'>
				<col width=76 span=2
					style='mso-width-source: userset; mso-width-alt: 2432; width: 57pt'>
				<col width=72 style='width: 54pt'>
				<col width=77
					style='mso-width-source: userset; mso-width-alt: 2464; width: 58pt'>
				<col width=91
					style='mso-width-source: userset; mso-width-alt: 2912; width: 68pt'>
				<col width=94
					style='mso-width-source: userset; mso-width-alt: 3008; width: 71pt'>
				<col width=72 span=4 style='width: 54pt'>
				<col width=238
					style='mso-width-source: userset; mso-width-alt: 7616; width: 179pt'>
				<tr height=30 style='height: 22.5pt'>
					<td colspan=13 height=30 class=xl8528185 width=1183
						style='height: 22.5pt; width: 888pt'>
						学员信息登记表
					</td>
				</tr>
				<%
					rs = db.executeQuery(esql);
					String  qyname = "";
					String qyaddress ="";
					int num = 0;
					  String fzrName ="";
				      String fzrXb ="";
				      String fzrPosition ="";
				      String fzrPhone ="";
				      String fzrMobile ="";
				      String fzrEmail ="";
				      String fzrAddress="";
				      String lxrName="";
				      String lxrXb="";
				      String lxrPosition="";
				      String lxrPhone="";
				      String lxrMobile="";
				      String lxrEmail="";
				      String lxrAddress="";
					while(rs!=null&&rs.next()){
						qyname = rs.getString("name");
						qyaddress = rs.getString("address");
						num = rs.getInt("num");
						fzrName = rs.getString("fzrName");
						fzrXb = rs.getString("fzrXb");
						fzrPosition = rs.getString("fzrPosition");
						fzrPhone = rs.getString("fzrPhone");
						fzrMobile = rs.getString("fzrMobile");
						fzrEmail = rs.getString("fzrEmail");
						lxrName = rs.getString("lxrName");
						lxrXb = rs.getString("lxrXb");
						lxrPosition = rs.getString("lxrPosition");
						lxrPhone = rs.getString("lxrPhone");
						lxrMobile = rs.getString("lxrMobile");
						lxrEmail = rs.getString("lxrEmail");
					}
					db.close(rs);
				%>
				
				<tr height=26 style='mso-height-source: userset; height: 19.5pt'>
					<td colspan=13 height=26 class=xl7728185 style='height: 19.5pt'>
						企业名称：<%=qyname%>
					</td>
				</tr>
				<tr height=26 style='mso-height-source: userset; height: 19.5pt'>
					<td colspan=13 height=26 class=xl7728185 style='height: 19.5pt'>
						单位地址：<%=qyaddress%>
					</td>
				</tr>
				<tr height=26 style='mso-height-source: userset; height: 19.5pt'>
					<td colspan=13 height=26 class=xl7628185
						style='border-right: .5pt solid black; height: 19.5pt'>
						学员总人数： <%=num%>
						<span style='mso-spacerun: yes'>&nbsp; </span>人
					</td>
				</tr>
				<tr height=19 style='height: 14.25pt'>
					<td height=19 class=xl6728185
						style='height: 14.25pt; border-top: none'>
					</td>
					<td class=xl6828185 style='border-top: none; border-left: none'>
						姓名
					</td>
					<td colspan=4 class=xl7928185
						style='border-right: .5pt solid black; border-left: none'>
						部门
					</td>
					<td class=xl6828185 style='border-top: none; border-left: none'>
						职务
					</td>
					<td colspan=2 class=xl7928185
						style='border-right: .5pt solid black; border-left: none'>
						电话
					</td>
					<td colspan=2 class=xl7928185
						style='border-right: .5pt solid black; border-left: none'>
						手机
					</td>
					<td class=xl6828185 style='border-top: none; border-left: none'>
						传真
					</td>
					<td class=xl6828185 style='border-top: none; border-left: none'>
						电子邮件
					</td>
				</tr>
				<tr height=19 style='height: 14.25pt'>
					<td height=19 class=xl6728185
						style='height: 14.25pt; border-top: none'>
						企业负责人
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						<%=fzrName%>
					</td>
					<td colspan=4 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						部门1
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						<%=fzrPosition%>
					</td>
					<td colspan=2 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						<%=fzrPhone%>
					</td>
					<td colspan=2 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						<%=fzrMobile%>
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						10222
					</td>
					<td class=xl7028185 style='border-top: none; border-left: none'>
						<a href="mailto:<%=fzrEmail%>" target="_parent"><%=fzrEmail%></a>
					</td>
				</tr>
				<tr height=19 style='height: 14.25pt'>
					<td height=19 class=xl6728185
						style='height: 14.25pt; border-top: none'>
						企业联系人
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						<%=lxrName%>
					</td>
					<td colspan=4 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						部门2
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						<%=lxrPosition%>
					</td>
					<td colspan=2 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						<%=lxrPhone%>
					</td>
					<td colspan=2 class=xl8228185
						style='border-right: .5pt solid black; border-left: none'>
						<%=lxrMobile%>
					</td>
					<td class=xl6928185 style='border-top: none; border-left: none'>
						12333
					</td>
					<td class=xl7028185 style='border-top: none; border-left: none'>
						<a href="mailto:<%=lxrEmail%>" target="_parent"><%=lxrEmail%></a>
					</td>
				</tr>
				<tr height=32 style='mso-height-source: userset; height: 24.0pt'>
					<td rowspan=2 height=51 class=xl6528185 width=99
						style='height: 38.25pt; border-top: none; width: 74pt'>
						学员序号
					</td>
					<td rowspan=2 class=xl6528185 width=72
						style='border-top: none; width: 54pt'>
						姓名
					</td>
					<td rowspan=2 class=xl7428185 width=76
						style='border-bottom: .5pt solid black; border-top: none; width: 57pt'>
						学号
					</td>
					<td rowspan=2 class=xl7428185 width=76
						style='border-bottom: .5pt solid black; border-top: none; width: 57pt'>
						密码
					</td>
					<td rowspan=2 class=xl6528185 width=72
						style='border-top: none; width: 54pt'>
						性别
					</td>
					<td rowspan=2 class=xl6528185 width=77
						style='border-top: none; width: 58pt'>
						民族
					</td>
					<td rowspan=2 class=xl6528185 width=91
						style='border-top: none; width: 68pt'>
						学历
					</td>
					<td rowspan=2 class=xl6528185 width=94
						style='border-top: none; width: 71pt'>
						出生年月日
					</td>
					<td colspan=2 class=xl6528185 width=144
						style='border-left: none; width: 108pt'>
						工作单位
					</td>
					<td colspan=2 class=xl6528185 width=144
						style='border-left: none; width: 108pt'>
						联系电话
					</td>
					<td rowspan=2 class=xl6528185 width=238
						style='border-top: none; width: 179pt'>
						电子邮件
					</td>
				</tr>
				<tr height=19 style='height: 14.25pt'>
					<td height=19 class=xl6528185 width=72
						style='height: 14.25pt; border-top: none; border-left: none; width: 54pt'>
						集团公司
					</td>
					<td class=xl6528185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						分公司
					</td>
					<td class=xl6528185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						手机
					</td>
					<td class=xl6528185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						固定电话
					</td>
				</tr>
				<tr class=xl6628185 height=24
					style='mso-height-source: userset; height: 18.0pt'>
					<td height=24 class=xl7128185 width=99
						style='height: 18.0pt; border-top: none; width: 74pt'>
						1
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						姓名
					</td>
					<td class=xl7228185 width=76
						style='border-top: none; border-left: none; width: 57pt'>
						20093344
					</td>
					<td class=xl7228185 width=76
						style='border-top: none; border-left: none; width: 57pt'>
						DSEW1234
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						男
					</td>
					<td class=xl7228185 width=77
						style='border-top: none; border-left: none; width: 58pt'>
						汉
					</td>
					<td class=xl7228185 width=91
						style='border-top: none; border-left: none; width: 68pt'>
						本科
					</td>
					<td class=xl7228185 width=94
						style='border-top: none; border-left: none; width: 71pt'>
						19343343
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						北京移动
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						北京分公司
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						2121212
					</td>
					<td class=xl7228185 width=72
						style='border-top: none; border-left: none; width: 54pt'>
						1022323
					</td>
					<td class=xl7328185 width=238
						style='border-top: none; border-left: none; width: 179pt'>
						<a href="mailto:dfdsf@ddf.com" target="_parent">dfdsf@ddf.com</a>
					</td>
				</tr>
				<![if supportMisalignedColumns]>
				<tr height=0 style='display: none'>
					<td width=99 style='width: 74pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=76 style='width: 57pt'></td>
					<td width=76 style='width: 57pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=77 style='width: 58pt'></td>
					<td width=91 style='width: 68pt'></td>
					<td width=94 style='width: 71pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=72 style='width: 54pt'></td>
					<td width=238 style='width: 179pt'></td>
				</tr>
				<![endif]>
			</table>
		</div>
		<!----------------------------->
		<!--“从 EXCEL 发布网页”向导结束-->
		<!----------------------------->
	</body>

</html>
