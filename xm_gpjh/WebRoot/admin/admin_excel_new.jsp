<%@ page contentType="application/vnd.ms-excel;charset=utf-8"%>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeManager"/>
<%	response.setHeader("Content-Disposition", "attachment;filename=admin.xls"); %>
<%@ page import="java.util.*,java.net.*"%>
<%@ include file="./pub/priv.jsp"%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="site_list_excel.files/filelist.xml">
<link rel=Edit-Time-Data href="site_list_excel.files/editdata.mso">
<link rel=OLE-Object-Data href="site_list_excel.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2007-06-04T07:53:54Z</o:LastSaved>
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
	font-weight:700;
	text-align:center;}
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
      <x:HorizontalResolution>300</x:HorizontalResolution>
      <x:VerticalResolution>300</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>5</x:ActiveRow>
       <x:ActiveCol>11</x:ActiveCol>
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

<body link=blue vlink=purple leftmargin=0 topmargin=0>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull1(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>
<%

List manager= (List)request.getAttribute("list");
%>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1653 style='border-collapse:
 collapse;table-layout:fixed;width:1243pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
 <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>
<tr class=xl24 height=19 style='height:14.25pt'>
  	<td height=19 class=xl24 width=109 style='height:14.25pt;width:82pt'>登陆ID</td>
	<td class=xl24 width=109 style='width:82pt'>管理员姓名</td>
	<td class=xl24 width=109 style='width:82pt'>管理员类型</td>
	<td class=xl24 width=109 style='width:82pt'>手机号码</td>
	<td class=xl24 width=109 style='width:82pt'>电话</td>
	<td class=xl24 width=109 style='width:82pt'>EMAIL</td>
	<td class=xl24 width=109 style='width:82pt'>权限组</td>
	<td class=xl24 width=109 style='width:82pt'>状态</td>
</tr>
<%
  if(manager!=null){
     for(int i=0;i<manager.size();i++){
     
       PeManager mana=(PeManager) manager.get(i); 
       
       String status=fixnull1(mana.getEnumConstByFlagIsvalid() != null ? mana.getEnumConstByFlagIsvalid().getName():"");
    
     String type=fixnull1(mana.getSsoUser().getEnumConstByUserType() != null ? mana.getSsoUser().getEnumConstByUserType().getName():"");
 
   String longId= java.net.URLEncoder.encode(fixnull(mana.getSsoUser().getLoginId()),"UTF-8");
   String maname= java.net.URLEncoder.encode(fixnull(mana.getName()),"UTF-8");
   String mastatus=java.net.URLEncoder.encode(fixnull(status),"UTF-8");
   String mobile=java.net.URLEncoder.encode(fixnull(mana.getMobilePhone()),"UTF-8");
   String phone = fixnull1(mana.getPhone());
   String email = fixnull1(mana.getEmail());
%>
 <tr height=19 style='height:14.25pt'>
  <td height=19 style='height:14.25pt'><%=longId%></td>
  <td ><%=maname%></td>
  <td ><%=type%></td>
  <td ><%=mobile%></td>
  <td ><%=phone%></td>
  <td ><%=email%></td>
  
  <td ><% if(mana.getSsoUser().getPePriRole()== null) {out.println("<font color='red'>未指定权限组</font>");}else{out.println(fixnull(mana.getSsoUser().getPePriRole().getName()));};%></td>
  <td ><%=status%></td>
 </tr>
<%
	}
	}
%>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=109 style='width:82pt'></td>
  <td width=109 style='width:82pt'></td>
  <td width=109 style='width:82pt'></td>
  <td width=109 style='width:82pt'></td>
  <td width=109 style='width:82pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
