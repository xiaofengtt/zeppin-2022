<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer cust1_id = Utility.parseInt(request.getParameter("cust1_id"), new Integer(0));
Integer cust2_id = Utility.parseInt(request.getParameter("cust2_id"), new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));
String op = Utility.trimNull(request.getParameter("op"));
String queryString = "";
if (check_flag.intValue()==0)
	queryString = Utility.getQueryString(request, 
						new String[]{"repeatflag", "repeat_time", "must_contain", "loosely_match", "max_diff"});
else 
	queryString = Utility.getQueryString(request, new String[]{"q_check_flag", "q_cust_name"});

CustomerLocal customer = EJBFactory.getCustomer();

boolean success = false;
if ("POST".equals(request.getMethod())) {
	try{
		if ("merge".equals(op)) {
			Integer from_cust_id = Utility.parseInt(request.getParameter("from_cust_id"), new Integer(0));
			Integer to_cust_id = Utility.parseInt(request.getParameter("to_cust_id"), new Integer(0));
			customer.merge(from_cust_id, to_cust_id, input_operatorCode);
		} else if ("check".equals(op)) {
			customer.checkMerge(serial_no, check_flag, input_operatorCode);
		} else if ("recover".equals(op)) {
			customer.recoverMerge(serial_no, input_operatorCode);
		}
	}catch(BusiException e){
// 		throw new BusiException(e.getMessage());
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		if ("merge".equals(op)) {
			out.println("<script type=\"text/javascript\">location='client_unite_list.jsp?"+queryString+"';</script>");
		} else if ("check".equals(op)) {				
			out.println("<script type=\"text/javascript\">location='client_merge_check_list.jsp?"+queryString+"';</script>");
		} else if ("recover".equals(op)) {
			out.println("<script type=\"text/javascript\">location='client_merge_check_list.jsp?"+queryString+"';</script>");
		}
		return;
	}

	success = true;
}

CustomerVO vo1 = new CustomerVO();
vo1.setCust_id(cust1_id);
vo1.setInput_man(input_operatorCode);
//List cust_from_list = customer.listProcAll(vo1);
// --查询标识:0基本信息1客户现有合同2客户已结束合同3客户其他合同4客户受益信息
List cust1_info = customer.queryCustAllInfo(cust1_id, new Integer(0), input_operatorCode);
if (cust1_info!=null && cust1_info.size()>0) {
	Map cust_from_map = (Map) cust1_info.get(0);
	vo1.setCust_type(Utility.parseInt(Utility.trimNull(cust_from_map.get("CUST_TYPE")),new Integer(0)));
	vo1.setCust_type_name(Utility.trimNull(cust_from_map.get("CUST_TYPE_NAME")));
	vo1.setCust_name(Utility.trimNull(cust_from_map.get("CUST_NAME")));
	vo1.setCust_no(Utility.trimNull(cust_from_map.get("CUST_NO")));
	vo1.setCard_id(Utility.trimNull(cust_from_map.get("CARD_ID")));
	vo1.setCust_source_name(Utility.trimNull(cust_from_map.get("CUST_SOURCE_NAME")));
	vo1.setCard_type_name(Utility.trimNull(cust_from_map.get("CARD_TYPE_NAME")));
	vo1.setAge(Utility.parseInt(Utility.trimNull(cust_from_map.get("AGE")),new Integer(0)));
	vo1.setSex_name(Utility.trimNull(cust_from_map.get("SEX_NAME")));
	vo1.setBirthday(Utility.parseInt(Utility.trimNull(cust_from_map.get("BIRTHDAY")),new Integer(0)));
	vo1.setH_tel(Utility.trimNull(cust_from_map.get("H_TEL")));
	vo1.setO_tel(Utility.trimNull(cust_from_map.get("O_TEL")));
	vo1.setMobile(Utility.trimNull(cust_from_map.get("MOBILE")));
	vo1.setBp(Utility.trimNull(cust_from_map.get("BP")));
	vo1.setE_mail(Utility.trimNull(cust_from_map.get("E_MAIL")));
	vo1.setFax(Utility.trimNull(cust_from_map.get("FAX")));
	vo1.setPost_code(Utility.trimNull(cust_from_map.get("POST_CODE")));
	vo1.setPost_address(Utility.trimNull(cust_from_map.get("POST_ADDRESS")));
	vo1.setPost_code2(Utility.trimNull(cust_from_map.get("POST_CODE2")));
	vo1.setPost_address2(Utility.trimNull(cust_from_map.get("POST_ADDRESS2")));
}

List cust1_cur_cons = customer.queryCustAllInfo(cust1_id, new Integer(1), input_operatorCode); //1客户现有合同
List cust1_end_cons = customer.queryCustAllInfo(cust1_id, new Integer(2), input_operatorCode); // 2客户已结束合同
List cust1_other_cons = customer.queryCustAllInfo(cust1_id, new Integer(3), input_operatorCode); // 3客户其他合同
List cust1_bene_info = customer.queryCustAllInfo(cust1_id, new Integer(4), input_operatorCode); // 4 客户受益信息

CustomerVO vo2 = new CustomerVO();
vo2.setCust_id(cust2_id);
vo2.setInput_man(input_operatorCode);
//List cust_to_list = customer.listProcAll(vo2);
List cust2_info = customer.queryCustAllInfo(cust2_id, new Integer(0), input_operatorCode);
if (cust2_info!=null && cust2_info.size()>0) {
	Map cust_to_map = (Map) cust2_info.get(0);
	vo2.setCust_type(Utility.parseInt(Utility.trimNull(cust_to_map.get("CUST_TYPE")),new Integer(0)));
	vo2.setCust_type_name(Utility.trimNull(cust_to_map.get("CUST_TYPE_NAME")));
	vo2.setCust_name(Utility.trimNull(cust_to_map.get("CUST_NAME")));
	vo2.setCust_no(Utility.trimNull(cust_to_map.get("CUST_NO")));
	vo2.setCard_id(Utility.trimNull(cust_to_map.get("CARD_ID")));
	vo2.setCust_source_name(Utility.trimNull(cust_to_map.get("CUST_SOURCE_NAME")));
	vo2.setCard_type_name(Utility.trimNull(cust_to_map.get("CARD_TYPE_NAME")));
	vo2.setAge(Utility.parseInt(Utility.trimNull(cust_to_map.get("AGE")),new Integer(0)));
	vo2.setSex_name(Utility.trimNull(cust_to_map.get("SEX_NAME")));
	vo2.setBirthday(Utility.parseInt(Utility.trimNull(cust_to_map.get("BIRTHDAY")),new Integer(0)));
	vo2.setH_tel(Utility.trimNull(cust_to_map.get("H_TEL")));
	vo2.setO_tel(Utility.trimNull(cust_to_map.get("O_TEL")));
	vo2.setMobile(Utility.trimNull(cust_to_map.get("MOBILE")));
	vo2.setBp(Utility.trimNull(cust_to_map.get("BP")));
	vo2.setE_mail(Utility.trimNull(cust_to_map.get("E_MAIL")));
	vo2.setFax(Utility.trimNull(cust_to_map.get("FAX")));
	vo2.setPost_code(Utility.trimNull(cust_to_map.get("POST_CODE")));
	vo2.setPost_address(Utility.trimNull(cust_to_map.get("POST_ADDRESS")));
	vo2.setPost_code2(Utility.trimNull(cust_to_map.get("POST_CODE2")));
	vo2.setPost_address2(Utility.trimNull(cust_to_map.get("POST_ADDRESS2")));
}
List cust2_cur_cons = customer.queryCustAllInfo(cust2_id, new Integer(1), input_operatorCode); //1客户现有合同
List cust2_end_cons = customer.queryCustAllInfo(cust2_id, new Integer(2), input_operatorCode); // 2客户已结束合同
List cust2_other_cons = customer.queryCustAllInfo(cust2_id, new Integer(3), input_operatorCode); // 3客户其他合同
List cust2_bene_info = customer.queryCustAllInfo(cust2_id, new Integer(4), input_operatorCode); // 4 客户受益信息

customer.remove();
 %>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/jQuery/FormValidate/css/css.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/FormValidate/js/FormValidate.js'></script>
<script type='text/javascript'>
var op = "<%=op%>";
var success = <%=success%>;
var check_flag = <%=check_flag%>;
var cust1_type = <%=vo1.getCust_type()%>;
var cust2_type = <%=vo2.getCust_type()%>;
var query = "<%=queryString%>";

window.onload = function() {
		if (success) {
			if (op=="merge") {
				sl_alert("已新添一条待审核的合并记录！");
				location.href = "client_unite_list.jsp?"+query;
			}　else if (op=="check") {				
				sl_alert(check_flag==2?"已审核通过！":"已否决！");
				location.href = "client_merge_check_list.jsp?"+query;
			}　else if (op=="recover") {
				sl_alert("已恢复！");
				location.href = "client_merge_check_list.jsp?"+query;
			}
		} 

		if (cust1_type == 1) {		
			document.getElementById("cut1_from").style.display="";
			document.getElementById("cut2_from").style.display="";
		} else {
			document.getElementById("cut1_from").style.display="none";
			document.getElementById("cut2_from").style.display="none";
		}

		if (cust2_type == 1) {			
			document.getElementById("cut1_to").style.display="";
			document.getElementById("cut2_to").style.display="";
		} else {
		 	document.getElementById("cut1_to").style.display="none";
			document.getElementById("cut2_to").style.display="none";
		}
	};

function merge1to2() {
	document.theform.op.value = "merge";
	document.theform.from_cust_id.value = document.theform.cust1_id.value;
	document.theform.to_cust_id.value = document.theform.cust2_id.value;
	document.theform.to_cust_id.value = document.theform.cust2_id.value;
	document.theform.to_cust_id.value = document.theform.cust2_id.value;
	if (sl_confirm("将客户A<"+DWRUtil.getValue("custname_from")+"-"+DWRUtil.getValue("cardid_from")+">的信息合并到客户B<"
				+DWRUtil.getValue("custname_to")+"-"+DWRUtil.getValue("cardid_to")+">")) {
		disableAllBtn(true);
		document.theform.submit();
	}
}

function merge2to1() {
	document.theform.op.value = "merge";
	document.theform.from_cust_id.value = document.theform.cust2_id.value;
	document.theform.to_cust_id.value = document.theform.cust1_id.value;
	if (sl_confirm("将客户B<"+DWRUtil.getValue("custname_to")+"-"+DWRUtil.getValue("cardid_to")+">的信息合并到客户A<"
				+DWRUtil.getValue("custname_from")+"-"+DWRUtil.getValue("cardid_from")+">")) {
		disableAllBtn(true);		
		document.theform.submit();
	}
}

function check(check_flag) {
	document.theform.op.value = "check";
	document.theform.check_flag.value = check_flag;
	if (sl_confirm(check_flag==2?"审核通过":"审核否决")) {
		document.theform.submit();
	}
}

function recover() {
	document.theform.op.value = "recover";
	if (sl_confirm("恢复")) {
		document.theform.submit();
	}
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="client_unite_info.jsp?<%=queryString%>">
<input type="hidden" name="op"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="check_flag"/>

<INPUT type="hidden" name="from_cust_id"/>
<INPUT type="hidden" name="to_cust_id"/>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td colspan=2 class="page-title"><b>客户合并详细页面</b></td>
			</tr>	
		</TABLE>
		</TD>
	</TR>
</TABLE>
<br/>
<style>
.table-merge{ border-collapse:collapse;background-color:#fff; border-spacing:0;}
.table-merge tr td{padding: 5px 10px; border:1px solid #f5f5f5;}
.table-merge tr td input{border:none!important; padding:5px;}
.table-merge-inner{border-collapse:collapse;background-color:#fff; border-spacing:0;}
.table-merge-inner tr td{}
.table-merge-inner tr td input{border-bottom:1px solid #197fe6; width:100%;}
.table-merge-inner tr td table{border:none!important;border-collapse:collapse; }
.table-merge-inner tr td table tr td{border:none; border-bottom:1px solid #ccc;}
</style>
<table width="100%" cellpadding="0" cellspacing="0" class="table-merge" border="0" >
	<tr>
	<td width="50%">
	<table cellpadding="0" cellspacing="0" border="0" class="table-merge-inner"  width="100%" align="left" style="background-color: whitesmoke;">
		<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
			<td colspan="6"><%=check_flag.intValue()==0?"客户A":"被合并客户"%>信息:<input type="hidden" name="cust1_id" value="<%=cust1_id%>"/></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户类型:</td>
			<td><input type="text" name="custtypename_from" value="<%=Utility.trimNull(vo1.getCust_type_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户名称:</td>
			<td><input type="text" name="custname_from" value="<%=Utility.trimNull(vo1.getCust_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户代码:</td>
			<td><input type="text" name="custcode_from" value="<%=Utility.trimNull(vo1.getCust_no()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户来源</td>
			<td><input type="text" name="custsource_from" value="<%=Utility.trimNull(vo1.getCust_source_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">证件类型:</td>
			<td><input type="text" name="cardtype_from" value="<%=Utility.trimNull(vo1.getCard_type_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">证件号码:</td>
			<td><input type="text" name="cardid_from" value="<%=Utility.trimNull(vo1.getCard_id()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr id="cut1_from"style="display:none" bgcolor='#FFFFFF' >
			<td align="right" class="title">年龄:</td>
			<td><input type="text" name="age_from" value="<%=Utility.trimNull(vo1.getAge()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">性别:</td>
			<td><input type="text" name="sexname_from" value="<%=Utility.trimNull(vo1.getSex_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr id="cut2_from" style="display:none" bgcolor='#FFFFFF'>
			<td align="right" class="title">出生日期:</td>
			<td colspan="3"><input type="text" name="birthday_from" value="<%=Format.formatDateCn(vo1.getBirthday())%>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">家庭电话:</td>
			<td><input type="text" name="htel_from" value="<%=Utility.trimNull(vo1.getH_tel()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">公司电话:</td>
			<td><input type="text" name="otel_from" value="<%=Utility.trimNull(vo1.getO_tel()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">手机:</td>
			<td><input type="text" name="mobile_from" value="<%=Utility.trimNull(vo1.getMobile()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">手机2:</td>
			<td><input type="text" name="bp_from" value="<%=Utility.trimNull(vo1.getBp()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">传真:</td>
			<td><input type="text" name="fax_from" value="<%=Utility.trimNull(vo1.getFax()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">Email:</td>
			<td><input type="text" name="email_from" value="<%=Utility.trimNull(vo1.getE_mail()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮政编码:</td>
			<td><input type="text" name="postcode_from" value="<%=Utility.trimNull(vo1.getPost_code()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮寄地址:</td>
			<td><input type="text" name="postaddress_from" value="<%=Utility.trimNull(vo1.getPost_address()) %>" class="edline" readonly size="40"></td>
			
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮政编码2:</td>
			<td><input type="text" name="postcode2_from" value="<%=Utility.trimNull(vo1.getPost_code2()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮寄地址2:</td>
			<td><input type="text" name="postaddress2_from" value="<%=Utility.trimNull(vo1.getPost_address2()) %>" class="edline" readonly size="40"></td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>现有合同:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust1_cur_cons.size(); i++) { 
					Map map = (Map)cust1_cur_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust1_cur_cons.size()<cust2_cur_cons.size()?cust2_cur_cons.size()-cust1_cur_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>已结束合同:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust1_end_cons.size(); i++) { 
					Map map = (Map)cust1_end_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust1_end_cons.size()<cust2_end_cons.size()?cust2_end_cons.size()-cust1_end_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>其他合同:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust1_other_cons.size(); i++) { 
					Map map = (Map)cust1_other_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust1_other_cons.size()<cust2_other_cons.size()?cust2_other_cons.size()-cust1_other_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>受益信息:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>						
						<td align="center" width="12.5%">受益日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">受益金额</td>
						<td align="center" width="15%">受益账户名</td>		
						<td align="center" width="10%">受益状态</td>				
					</tr>
				<%
				for (int i=0; i<cust1_bene_info.size(); i++) { 
					Map map = (Map)cust1_bene_info.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>						
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("BEN_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("BEN_END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("BEN_MONEY"))%></td>
						<td align="center" width="15%" height="50px"><%=map.get("CUST_ACCT_NAME")%></td>	
						<td align="center" width="10%" height="50px"><%=map.get("BEN_STATUS_NAME")%></td>					
					</tr>
				<%
				} 
				for (int i=0; i<(cust1_bene_info.size()<cust2_bene_info.size()?cust2_bene_info.size()-cust1_bene_info.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>						
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
						<td align="center" width="15%" height="50px"></td>
						<td align="center" width="10%" height="50px"></td>	
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>
	</table>
	</td>	
	
	<td width="50%">
	<table cellpadding="0" cellspacing="0" border="0" class="table-merge-inner" width="100%" align="right" style="background-color: whitesmoke;">
		<tr style="text-align: left; COLOR: #0076C8; BACKGROUND-COLOR: #F4FAFF; font-weight: bold; height: 20px;">
			<td colspan="6"><%=check_flag.intValue()==0?"客户B":"合并到客户"%>信息:<input type="hidden" name="cust2_id" value="<%=cust2_id%>"/></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户类型:</td>
			<td><input type="text" name="custtypename_to" value="<%=Utility.trimNull(vo2.getCust_type_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户名称:</td>
			<td><input type="text" name="custname_to" value="<%=Utility.trimNull(vo2.getCust_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户代码:</td>
			<td><input type="text" name="custcode_to" value="<%=Utility.trimNull(vo2.getCust_no()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">客户来源</td>
			<td><input type="text" name="custsource_to" value="<%=Utility.trimNull(vo2.getCust_source_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">证件类型:</td>
			<td><input type="text" name="cardtype_to" value="<%=Utility.trimNull(vo2.getCard_type_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">证件号码:</td>
			<td><input type="text" name="cardid_to" value="<%=Utility.trimNull(vo2.getCard_id()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr id="cut1_to" style="display:none" bgcolor='#FFFFFF'>
			<td align="right" class="title">年龄:</td>
			<td><input type="text" name="age_to" value="<%=Utility.trimNull(vo2.getAge()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">性别:</td>
			<td><input type="text" name="sexname_to" value="<%=Utility.trimNull(vo2.getSex_name()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr id="cut2_to" style="display:none" bgcolor='#FFFFFF'>
			<td align="right" class="title">出生日期:</td>
			<td colspan="3"><input type="text" name="birthday_to" value="<%=Format.formatDateCn(vo2.getBirthday()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">家庭电话:</td>
			<td><input type="text" name="htel_to" value="<%=Utility.trimNull(vo2.getH_tel()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">公司电话:</td>
			<td><input type="text" name="otel_to" value="<%=Utility.trimNull(vo2.getO_tel()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">手机:</td>
			<td><input type="text" name="mobile_to" value="<%=Utility.trimNull(vo2.getMobile()) %>" class="edline"readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">手机2:</td>
			<td><input type="text" name="bp_to" value="<%=Utility.trimNull(vo2.getBp()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">传真:</td>
			<td><input type="text" name="fax_to" value="<%=Utility.trimNull(vo2.getFax()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">Email:</td>
			<td><input type="text" name="email_to" value="<%=Utility.trimNull(vo2.getE_mail()) %>" class="edline" size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮政编码:</td>
			<td><input type="text" name="postcode_to" value="<%=Utility.trimNull(vo2.getPost_code()) %>" class="edline" readonly size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮寄地址:</td>
			<td><input type="text" name="postaddress_to" value="<%=Utility.trimNull(vo2.getPost_address()) %>" class="edline"  size="40" readonly></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮政编码2:</td>
			<td><input type="text" name="postcode2_to" value="<%=Utility.trimNull(vo2.getPost_code2()) %>" class="edline" size="40"></td>
		</tr>
		<tr bgcolor='#FFFFFF'>
			<td align="right" class="title">邮寄地址2:</td>
			<td><input type="text" name="postaddress2_to" value="<%=Utility.trimNull(vo2.getPost_address2()) %>" class="edline"  size="40"></td>
		</tr>			

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>现有合同:</b></td>			
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust2_cur_cons.size(); i++) { 
					Map map = (Map)cust2_cur_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust2_cur_cons.size()<cust1_cur_cons.size()?cust1_cur_cons.size()-cust2_cur_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>已结束合同:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust2_end_cons.size(); i++) { 
					Map map = (Map)cust2_end_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust2_end_cons.size()<cust1_end_cons.size()?cust1_end_cons.size()-cust2_end_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>其他合同:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>
						<td align="center" width="25%">合同编号</td>
						<td align="center" width="12.5%">签署日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">认购金额</td>
					</tr>
				<%
				for (int i=0; i<cust2_other_cons.size(); i++) { 
					Map map = (Map)cust2_other_cons.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>
						<td align="center" width="25%" height="50px"><%=map.get("CONTRACT_SUB_BH")%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("QS_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("RG_MONEY"))%></td>
					</tr>
				<%
				} 
				for (int i=0; i<(cust2_other_cons.size()<cust1_other_cons.size()?cust1_other_cons.size()-cust2_other_cons.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>
						<td align="center" width="25%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>

		<tr bgcolor='white'>
			<td align="left" class="title" colspan="2"><b>受益信息:</b></td>		
		</tr>
		<tr bgcolor="white">
			<td colspan="2">
				<table border="1" cellspacing="0" width="100%" style="border-collapse:collapse">
					<tr>
						<td align="center" width="30%">认购产品</td>						
						<td align="center" width="12.5%">受益日期</td>
						<td align="center" width="12.5%">结束日期</td>
						<td align="center" width="20%">受益金额</td>
						<td align="center" width="15%">受益账户名</td>		
						<td align="center" width="10%">受益状态</td>				
					</tr>
				<%
				for (int i=0; i<cust2_bene_info.size(); i++) { 
					Map map = (Map)cust2_bene_info.get(i);
					%>
					<tr>
						<td align="center" width="30%" height="50px"><%=map.get("PRODUCT_NAME")%></td>						
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("BEN_DATE"))%></td>
						<td align="center" width="12.5%" height="50px"><%=Format.formatDateLine((Integer)map.get("BEN_END_DATE"))%></td>
						<td align="right" width="20%" height="50px"><%=Format.formatMoney((BigDecimal)map.get("BEN_MONEY"))%></td>
						<td align="center" width="15%" height="50px"><%=map.get("CUST_ACCT_NAME")%></td>	
						<td align="center" width="10%" height="50px"><%=map.get("BEN_STATUS_NAME")%></td>					
					</tr>
				<%
				} 
				for (int i=0; i<(cust2_bene_info.size()<cust1_bene_info.size()?cust1_bene_info.size()-cust2_bene_info.size():0); i++) { %>
					<tr>
						<td align="center" width="30%" height="50px"></td>						
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="12.5%" height="50px"></td>
						<td align="center" width="20%" height="50px"></td>
						<td align="center" width="15%" height="50px"></td>
						<td align="center" width="10%" height="50px"></td>	
					</tr>
				<%
				} %>
				</table>
			</td>
		</tr>		
	</table>
	</td>
</tr>
</table>

<table border="0" cellpadding="3" cellspacing="1" width="95%" align="center" style="background-color: whitesmoke;"> 
   <tr>
		<td align="center">
		<%
			if (check_flag.intValue()==0) {
		 %>
			<button type="button"   class="button" onclick="javascript:merge1to2()"><FONT size="3" color="red">合并到客户B&nbsp;--&gt;</FONT></button>
			&nbsp;&nbsp;
			<button type="button"   class="button" onclick="javascript:merge2to1()"><FONT size="3" color="red">&lt;--&nbsp;合并到客户A</FONT></button>
			&nbsp;&nbsp;
			<button type="button"  class="button" onclick="javascript:history.back();"><FONT size="3">返&nbsp;回&nbsp;</FONT></button>
		<% } else if (check_flag.intValue()==1) { //要审核 %>
			<button type="button"  class="xpbutton4" onclick="javascript:check(2)">审核通过</button>
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton4" onclick="javascript:check(3)">审核否决</button>
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" onclick="javascript:history.back();">返回</button>
		<% } else if (check_flag.intValue()==2) { //要恢复%>
			<button type="button"  class="xpbutton2" onclick="javascript:recover()">恢复</button>
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton2" onclick="javascript:history.back();">返回</button>
		<% } %>			
		</td>
	</tr>	
</table>	

</form>
</BODY>
</HTML>
