<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");
String name_of_contents = request.getParameter("name_of_contents");

//页面辅助变量
Integer cust_id = new Integer(0);
List list = new ArrayList();
Map map = new HashMap();

//收件人信息
Integer cust_type = new Integer(0);
String contact_man = "";
String cust_tel = "";
String mobile = "";
String o_tel = "";
String h_tel = "";
String cust_name = "";
String post_address = "";
String post_code = "";
String tel = "";
String gov_prov_regional_name = "";
String gov_regional_name = "";

//寄件人信息
String name = Argument.getDictContent("190001");
String telephone = Argument.getDictContent("190002");
String company = Argument.getDictContent("190003");
String address = Argument.getDictContent("190004");
String postcode = Argument.getDictContent("190005");

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<OBJECT classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 id=WB width=0></OBJECT>
<style media="print">
.noprint     { display: none }
</style>


<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>
<style>
td {
	font-size:12px;
}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
function doPrintSetup(){
	//打印设置
	WB.ExecWB(8,1)
}
function doPrintPreview(){
	//打印预览
	WB.ExecWB(7,1)
}
</script>
<body class="body" leftmargin="0" topmargin="20" rightmargin="8" onload="javascript:print();close();"><!-- onload="javascript:print();close();" -->
<form name="theform">
<!---套打方法-->
<%
Calendar c =  Calendar.getInstance();
c.get(Calendar.YEAR);
if(cust_items != null && cust_items.length > 0)
{
	for(int i=0; i<cust_items.length; i++){
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));

		if(!(cust_id.equals(new Integer(0))))
		{
			vo.setCust_id(cust_id);//循环获得客户ID的相关信息
			vo.setInput_man(input_operatorCode);
			list = local.listProcAll(vo);
			Iterator it = list.iterator();
			while(it.hasNext()){
				map = (Map)it.next();
				cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")), new Integer(0));
				if(cust_type.intValue() == 2)
				{
					cust_name = Utility.trimNull(map.get("CUST_NAME"));
					contact_man = Utility.trimNull(map.get("CONTACT_MAN"));
				}
				else{
					cust_name = "";
					contact_man = Utility.trimNull(map.get("CUST_NAME"));
				}
				cust_tel = Utility.trimNull(map.get("CUST_TEL"));
				mobile = Utility.trimNull(map.get("MOBILE"));
				o_tel = Utility.trimNull(map.get("O_TEL"));
				h_tel = Utility.trimNull(map.get("H_TEL"));
				post_address = Utility.trimNull(map.get("POST_ADDRESS"));
				post_code = Utility.trimNull(map.get("POST_CODE"));
				gov_prov_regional_name = Utility.trimNull(map.get("GOV_PROV_REGIONAL_NAME"));
				gov_regional_name = gov_prov_regional_name+" "+Utility.trimNull(map.get("GOV_REGIONAL_NAME"));
				String service_name = Utility.trimNull(map.get("SERVICE_MAN_NAME"));
				if(post_address.equals("")){
					post_address = Utility.trimNull(map.get("POST_ADDRESS2"));
				}
				tel = "";
				if(!cust_tel.equals("")){
					tel = cust_tel;
				}
				if(!h_tel.equals("")){
					tel = h_tel;
				}
				if(!o_tel.equals("")){
					tel = o_tel;
				}
				if(!mobile.equals("")){
					tel = mobile+" / "+tel;
				}
			%>
			<div  style="height:7mm;">&nbsp;</div>
			<table border="0" width="100%" cellspacing="0" cellpadding="4" align="center" style="width:180mm;">
				<tr>
					<td valign="top" style="width:30mm;height:3mm;padding-left:15mm;padding-top:2mm;">&nbsp;</font></td>
					<td valign="top" style="width:40mm;height:3mm;padding-left:17mm;padding-top:1mm;">&nbsp;&nbsp;<%=c.get(Calendar.YEAR)%>&nbsp;&nbsp;&nbsp;<%=c.get(Calendar.MONTH)+1%>&nbsp;&nbsp;&nbsp;<%=c.get(Calendar.DATE)%>&nbsp;&nbsp;&nbsp;<%=c.get(Calendar.HOUR_OF_DAY)%></td>
					<td valign="top" style="width:50mm;height:3mm;padding-left:20mm;padding-top:2mm;">&nbsp;</td>
					<td valign="top" style="width:40mm;height:3mm;padding-left:15mm;padding-top:1mm;">&nbsp;</td>
				</tr>
				<tr>
					<td valign="top" style="width:30mm;height:11mm;padding-left:15mm;padding-top:2mm;"><!--寄件人姓名:-->&nbsp;<font size=2>&nbsp;<%=Utility.trimNull(service_name)%></font></td>
					<td valign="top" style="width:40mm;height:11mm;padding-left:17mm;padding-top:1mm;"><!--电话:--><font size=2><%=telephone%></font></td>
					<td valign="top" style="width:50mm;height:11mm;padding-left:20mm;padding-top:2mm;"><!--收件人姓名:--><font size=2><%=contact_man%></font></td>
					<td valign="top" style="width:40mm;height:11mm;padding-left:15mm;padding-top:1mm;"><!--电话:--><font size=2><%=tel %></font></td>
				</tr>
				<tr>
					<td valign="top" colspan="2" style="width:60mm;heigth:23mm;padding-left:1mm;padding-top:1mm;"><!--单位名称:-->&nbsp;<font size=2><%//=company%></font></td>
					<td valign="top" colspan="2" style="width:60mm;heigth:23mm;padding-left:10mm;padding-top:1mm;"><!--单位名称:--><font size=2><%=cust_name%></font></td>
				</tr>
				<tr>
					<td valign="top" colspan="2" style="width:60mm;height:16mm;padding-left:1mm;padding-top:6mm;"><!--地址:-->&nbsp;<font size=2><%//=address %></font></td>
					<td valign="top" colspan="2" style="width:75mm;height:16mm;padding-left:10mm;padding-top:1mm;"><!--地址:-->&nbsp;<font size=2>　　　<%=gov_regional_name%><br><%=post_address %></font></td>
				</tr>
				<tr>
					<td valign="top" colspan="2" style="width:60mm;height:16mm;padding-left:62mm;padding-top:1mm;"><!--邮编:-->&nbsp;<font size=2 style="letter-spacing:8px;"><%//=postcode%></font></td>
					<td valign="top" colspan="2" style="width:60mm;height:16mm;padding-left:68mm;padding-top:1mm;"><!--邮编:-->&nbsp;<font size=2 style="letter-spacing:8px;"><%=post_code%></font></td>
				</tr>
				<tr>
					<td valign="top" colspan="2" style="width:50mm;height:23mm;padding-left:1mm;padding-top:2mm;"><!--内件品名:--><font size=2><%=name_of_contents %></font></td>
				</tr>
				<tr>
					<td valign="top" colspan="2" style="width:50mm;height:12mm;padding-left:25mm;padding-top:0mm;"><!--寄件人姓名:--><font size=2><%=name%></font></td>
				</tr>
			</table>
			<%if((i+1) < cust_items.length){ %>
			<div style="page-break-after:always"></div>
			<%} %>
	<%}
		}
	}
}
%>
</form>
</body>
</html>
<%
local.remove();
%>