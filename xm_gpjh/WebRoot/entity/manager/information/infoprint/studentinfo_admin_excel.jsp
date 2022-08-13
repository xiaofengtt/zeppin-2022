<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=studentinfo_total_excel.xls"); %>
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
	String bSub=fixnull(request.getParameter("bSub"));
	String erji_id=fixnull(request.getParameter("erji_id"));
	
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
	if (sql_ent!=null && !sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)
				+ ")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	boolean bErji=false;
	//判断是否二级企业
	if(entList!=null && entList.size()>0)
	{
		PeEnterprise e=(PeEnterprise)entList.get(0);
		String e_id=e.getId();
		String temp_sql="select id,code, name,fk_parent_id from pe_enterprise where id='"+e_id+"' and fk_parent_id is not null";
		//System.out.println(temp_sql);
		if(db.countselect(temp_sql)>0)
		{
			bErji=true;
			erji_id=e_id;
			rs=db.executeQuery(temp_sql);
			if(rs!=null && rs.next())
			{	
				enterprise_id=fixnull(rs.getString("fk_parent_id"));
			}
			db.close(rs);
		}
	}
	
	if (us!=null && us.getRoleId().equals("2")
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
	String sql_s="";
	//if(!search.equals(""))
	//sql_con=" and b.id='"+search+"'";
	//System.out.println("erji_id:"+erji_id);
	if(!enterprise_id.equals(""))
	{
		if(erji_id.equals(""))
		{
			sql_en = " and (ee.id = '" + enterprise_id + "' or ee.fk_parent_id ='"+enterprise_id+"')";
		}
		else
			sql_en =" and ee.id = '"+erji_id+"'";
	}
	if (!sql_ent.equals("")) {
		sql_con += "  and ee.id in " + sql_ent;
		sql_con1 = "  and ps.fk_enterprise_id in " + sql_ent;
	}
	if (!batch_id.equals("")) {
		sql_en += "and pb.id = '" + batch_id + "'";
		sql_s=" and s.fk_batch_id='"+batch_id+"'";
	}
		
		String esql="";
		String tmp_sql="select id from pe_enterprise p where p.fk_parent_id is null and p.id='"+enterprise_id+"'";
		if(db.countselect(tmp_sql)>0 && erji_id.equals(""))   //表示所选企业为一级企业
		{
		 esql ="select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
			+ "                pe.fax as fax, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,pe.FZR_DEPART as FZRDEPART,"
			+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb,\n"
			+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail, pe.LXR_DEPART as LXRDEPART,"
			+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
			+ "                 p.address, p.zipcode, p.fax, p.fzr_name, p.fzr_xb, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p\n"
			+ "                 where p.fk_parent_id is null and s.fk_enterprise_id = p.id "+sql_s+"  union select s.id as sid, p.id as pid,p.name, p.code, p.industry,\n"
			+ "                 p.address,p.zipcode, p.fax, p.fzr_name, p.fzr_xb, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email, p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p, pe_enterprise p1\n"
			+ "                 where p.fk_parent_id is null and p1.fk_parent_id = p.id and p1.id = s.fk_enterprise_id "+sql_s+" ) pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
			+ "                 where pe.fk_parent_id is null and pe.id = '"+enterprise_id+"'  group by pe.code,pe.name,pe.industry,\n"
			+ "                 pe.address, pe.zipcode,pe.fax, pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_position,\n"
			+ "               pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
			+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
			+ "                 pe.lxr_email , pe.lxr_address ,pe.fzr_depart,pe.lxr_depart " ;
		}
		else  //二级企业
		{
			esql ="select pe.id as id, pe.name as name, pe.code as code, pe.industry as industry, pe.address as address, pe.zipcode as zipcode,\n"
			+ "                pe.fax as fax, pe.fzr_name as fzrName, pe.fzr_xb as fzrXb, pe.fzr_position as fzrPosition, pe.fzr_phone as fzrPhone,pe.FZR_DEPART as FZRDEPART,"
			+ "                pe.fzr_mobile as fzrMobile,  pe.fzr_email as fzrEmail, pe.fzr_address as fzrAddress, pe.lxr_name as lxrName,pe.lxr_xb as lxrXb,\n"
			+ "                pe.lxr_position as lxrPosition, pe.lxr_phone as lxrPhone, pe.lxr_mobile as lxrMobile, pe.lxr_email as lxrEmail, pe.LXR_DEPART as LXRDEPART,"
			+ "                pe.lxr_address as lxrAddress, count(pp.sid) as num from (select s.id as sid, p.id as pid, p.name, p.code, p.industry,\n"
			+ "                 p.address, p.zipcode, p.fax, p.fzr_name, p.fzr_xb, p.fzr_position, p.fzr_phone, p.fzr_mobile, p.fzr_email,p.fzr_address,\n"
			+ "                 p.lxr_name, p.lxr_xb, p.lxr_position, p.lxr_phone, p.lxr_mobile, p.lxr_email, p.lxr_address from pe_bzz_student s, pe_enterprise p\n"
			+ "                 where s.fk_enterprise_id = p.id  "+sql_s+" ) pp right outer join pe_enterprise pe on pe.id = pp.pid\n"
			+ "                 where  pe.id like '%"+erji_id+"%'  group by pe.code,pe.name,pe.industry,\n"
			+ "                 pe.address, pe.zipcode,pe.fax, pe.id, pe.fzr_name, pe.fzr_xb , pe.fzr_position,\n"
			+ "               pe.fzr_phone , pe.fzr_mobile, pe.fzr_email , pe.fzr_address,\n"
			+ "                 pe.lxr_name ,pe.lxr_xb , pe.lxr_position , pe.lxr_phone, pe.lxr_mobile ,\n"
			+ "                 pe.lxr_email , pe.lxr_address ,pe.fzr_depart,pe.lxr_depart " ;
		}


	String sql_t = "select rownum , ps.true_name as name  , ps.reg_no as regno ,  ss.password as passwords ,\n"
			+ "ps.gender as gender ,ps.folk as folk , ps.education as education , to_char(ps.birthday,'yyyymmdd') as birthday ,\n"
			+ " ee.ename pename  ,  ee.name as subname , ps.mobile_phone as mobile , ps.phone as phone ,\n"
			+ " ps.email as email  from pe_bzz_student ps inner join sso_user ss on ps.fk_sso_user_id = ss.id\n"
			+ " inner join pe_bzz_batch pb on ps.fk_batch_id = pb.id  "
			+ " inner join (select pe.id,pe.name,pes.name as ename,pe.fk_parent_id as fk_parent_id from pe_enterprise pe\n"
			+ " left outer join pe_enterprise pes on pe.fk_parent_id= pes.id) ee\n"
			+ " on ps.fk_enterprise_id = ee.id  " + sql_en + sql_con + sql_con1
			+ " order by reg_no";
	// System.out.println(sql_t);
	// return;
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="平台管理员导出学员表.files/filelist.xml">
<link rel=Edit-Time-Data href="平台管理员导出学员表.files/editdata.mso">
<link rel=OLE-Object-Data href="平台管理员导出学员表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2009-08-14T05:10:35Z</o:LastSaved>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
.style17
	{color:blue;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-style-name:超链接;
	mso-style-id:8;}
a:link
	{color:blue;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
a:visited
	{color:purple;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl25
	{mso-style-parent:style0;
	font-size:10.0pt;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style17;
	color:blue;
	text-decoration:underline;
	text-underline-style:single;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl31
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl32
	{mso-style-parent:style17;
	color:blue;
	text-decoration:underline;
	text-underline-style:single;
	text-align:left;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl33
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl34
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl35
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl36
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl37
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl38
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl39
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl40
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl41
	{mso-style-parent:style0;
	font-size:11.0pt;
	font-weight:700;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl42
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl43
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl44
	{mso-style-parent:style0;
	font-size:10.0pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl45
	{mso-style-parent:style0;
	font-size:10.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>600</x:HorizontalResolution>
      <x:VerticalResolution>600</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>18</x:ActiveRow>
       <x:ActiveCol>1</x:ActiveCol>
       <x:RangeSelection>$B$14:$B$19</x:RangeSelection>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1183 style='border-collapse:
 collapse;table-layout:fixed;width:888pt'>
 <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>
 <col width=72 style='width:54pt'>
 <col width=76 span=2 style='mso-width-source:userset;mso-width-alt:2432;
 width:57pt'>
 <col width=72 style='width:54pt'>
 <col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'>
 <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>
 <col width=94 style='mso-width-source:userset;mso-width-alt:3008;width:71pt'>
 <col width=72 span=4 style='width:54pt'>
 <col width=238 style='mso-width-source:userset;mso-width-alt:7616;width:179pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=13 height=30 class=xl37 width=1183 style='height:22.5pt;
  width:888pt'>学员信息登记表</td>
 </tr>
 
 				<%
					rs = db.executeQuery(esql);
					String  qyname = "";
					String qyaddress ="";
					String qyfax="";
					int num = 0;
					  String fzrName ="";
				      String fzrXb ="";
				      String fzrPosition ="";
				      String fzrPhone ="";
				      String fzrMobile ="";
				      String fzrEmail ="";
				      String fzrAddress="";
				      String fzrDepart ="";
				      String lxrName="";
				      String lxrXb="";
				      String lxrPosition="";
				      String lxrPhone="";
				      String lxrMobile="";
				      String lxrEmail="";
				      String lxrAddress="";
				      String lxrDepart ="";
					while(rs!=null&&rs.next()){
						qyname = fixnull(rs.getString("name"));
						qyfax = fixnull(rs.getString("fax"));						
						qyaddress = fixnull(rs.getString("address"));
						num = rs.getInt("num");
						
						fzrName = fixnull(rs.getString("fzrName"));
						fzrXb = fixnull(rs.getString("fzrXb"));
						fzrPosition = fixnull(rs.getString("fzrPosition"));
						fzrPhone = fixnull(rs.getString("fzrPhone"));
						fzrMobile = fixnull(rs.getString("fzrMobile"));
						fzrEmail = fixnull(rs.getString("fzrEmail"));
						fzrDepart = fixnull(rs.getString("FZRDEPART"));
						lxrName = fixnull(rs.getString("lxrName"));
						lxrXb = fixnull(rs.getString("lxrXb"));
						lxrPosition = fixnull(rs.getString("lxrPosition"));
						lxrPhone = fixnull(rs.getString("lxrPhone"));
						lxrMobile = fixnull(rs.getString("lxrMobile"));
						lxrEmail = fixnull(rs.getString("lxrEmail"));
						lxrDepart = fixnull(rs.getString("LXRDEPART"));
					}
					db.close(rs);
				%>
 
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=13 height=26 class=xl39 style='border-right:.5pt solid black;
  height:19.5pt'>企业名称：<%=qyname%></td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=13 height=26 class=xl39 style='border-right:.5pt solid black;
  height:19.5pt'>单位地址：<%=qyaddress%></td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=13 height=26 class=xl40 style='border-right:.5pt solid black;
  height:19.5pt'>学员总人数：<span style='mso-spacerun:yes'>&nbsp; </span><%=num%><span
  style='mso-spacerun:yes'>&nbsp;&nbsp; </span>人</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>　</td>
  <td class=xl27 style='border-top:none;border-left:none'>姓名</td>
  <td colspan=4 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>部门</td>
  <td class=xl27 style='border-top:none;border-left:none'>职务</td>
  <td colspan=2 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>电话</td>
  <td colspan=2 class=xl33 style='border-right:.5pt solid black;border-left:
  none'>手机</td>
  <td class=xl27 style='border-top:none;border-left:none'>传真</td>
  <td class=xl27 style='border-top:none;border-left:none'>电子邮件</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>企业负责人</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fzrName%></td>
  <td colspan=4 class=xl35 style='border-right:.5pt solid black;border-left:
  none'><%=fzrDepart%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=fzrPosition%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none' ><%=fzrPhone%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none' ><%=fzrMobile%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=qyfax%></td>
  <td class=xl29 style='border-top:none;border-left:none'><a
  href="<%=fzrEmail%>"><%=fzrEmail%></a></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'>企业联系人</td>
  <td class=xl28 style='border-top:none;border-left:none'><%=lxrName%></td>
  <td colspan=4 class=xl35 style='border-right:.5pt solid black;border-left:
  none'><%=lxrDepart%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=lxrPosition%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none' ><%=lxrPhone%></td>
  <td colspan=2 class=xl35 style='border-right:.5pt solid black;border-left:
  none'><%=lxrMobile%></td>
  <td class=xl28 style='border-top:none;border-left:none' ><%=qyfax%></td>
  <td class=xl29 style='border-top:none;border-left:none'><a
  href="mailto:<%=lxrEmail%>"><%=lxrEmail%></a></td>
 </tr>

 <tr height=32 style='mso-height-source:userset;height:24.0pt'>
  <td rowspan=2 height=51 class=xl24 width=99 style='height:38.25pt;border-top:
  none;width:74pt'>学员序号</td>
  <td rowspan=2 class=xl24 width=72 style='border-top:none;width:54pt'>姓名</td>
  <td rowspan=2 class=xl42 width=76 style='border-bottom:.5pt solid black;
  border-top:none;width:57pt'>学号</td>
  <td rowspan=2 class=xl42 width=76 style='border-bottom:.5pt solid black;
  border-top:none;width:57pt'>密码</td>
  <td rowspan=2 class=xl24 width=72 style='border-top:none;width:54pt'>性别</td>
  <td rowspan=2 class=xl24 width=77 style='border-top:none;width:58pt'>民族</td>
  <td rowspan=2 class=xl24 width=91 style='border-top:none;width:68pt'>学历</td>
  <td rowspan=2 class=xl24 width=94 style='border-top:none;width:71pt'>出生年月日</td>
  <td colspan=2 class=xl24 width=144 style='border-left:none;width:108pt'>工作单位</td>
  <td colspan=2 class=xl24 width=144 style='border-left:none;width:108pt'>联系电话</td>
  <td rowspan=2 class=xl24 width=238 style='border-top:none;width:179pt'>电子邮件</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 width=72 style='height:14.25pt;border-top:none;
  border-left:none;width:54pt'>集团公司</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>分公司</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>手机</td>
  <td class=xl24 width=72 style='border-top:none;border-left:none;width:54pt'>固定电话</td>
 </tr>
  
 <%
 	int index=1;
 	rs  =db.executeQuery(sql_t);
 	while(rs!=null&&rs.next()){
 		String pename = fixnull(rs.getString("pename"));
	  	String sname = fixnull(rs.getString("subname"));
 %>
 <tr class=xl25 height=24 style='mso-height-source:userset;height:18.0pt'>
  <td height=24 class=xl30 width=99 style='height:18.0pt;border-top:none;
  width:74pt' x:num><%=index++%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(rs.getString("name"))%></td>
  <td class=xl31 width=76 style='border-top:none;border-left:none;width:57pt'
  ><%=fixnull(rs.getString("regno"))%></td>
  <td class=xl31 width=76 style='border-top:none;border-left:none;width:57pt'><%=fixnull(rs.getString("passwords"))%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=fixnull(rs.getString("gender"))%></td>
  <td class=xl31 width=77 style='border-top:none;border-left:none;width:58pt'><%=fixnull(rs.getString("folk"))%></td>
  <td class=xl31 width=91 style='border-top:none;border-left:none;width:68pt'><%=fixnull(rs.getString("education"))%></td>
  <td class=xl31 width=94 style='border-top:none;border-left:none;width:71pt'
  ><%=fixnull(rs.getString("birthday"))%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'>
  <%
  	if(pename==null || pename.equals("")||pename == ""){
  		pename = fixnull(rs.getString("subname"));
	  	 sname = "";
  	 }%>
 <%=pename%>
 </td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'><%=sname%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'
  ><%=fixnull(rs.getString("mobile"))%></td>
  <td class=xl31 width=72 style='border-top:none;border-left:none;width:54pt'
  ><%=fixnull(rs.getString("phone"))%></td>
  <td class=xl32 width=238 style='border-top:none;border-left:none;width:179pt'><a
  href="mailto:<%=fixnull(rs.getString("email"))%>"><%=fixnull(rs.getString("email"))%></a></td>
 </tr>
 <%
 }
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=99 style='width:74pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=76 style='width:57pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=77 style='width:58pt'></td>
  <td width=91 style='width:68pt'></td>
  <td width=94 style='width:71pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=238 style='width:179pt'></td>
 </tr>
 <![endif]>
</table>
</body>
</html>
