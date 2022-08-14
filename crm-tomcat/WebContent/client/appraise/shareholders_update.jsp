<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
AmShareHolderLocal shareHolder = EJBFactory.getAmShareHolder();  
AmlVO vo =	new  AmlVO();
boolean bSuccess = false;
List listAll = null;
Map map = null;
CustomerInfoVO custvo = new CustomerInfoVO();
CustomerInfoLocal cust = EJBFactory.getCustomerInfo();

Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
Integer serial_no = new Integer(Utility.parseInt(request.getParameter("serial_no"), 0));

String cust_name = "";
String card_type = "";
String card_id = "";
String hdnm = Utility.trimNull(request.getParameter("hdnm"));
String hitp = Utility.trimNull(request.getParameter("hitp"));
String hdid = Utility.trimNull(request.getParameter("hdid"));
Integer hivt = Utility.parseInt(request.getParameter("hivt"),null);

if(cust_id.intValue() != 0){
	custvo.setCust_id(cust_id);
	custvo.setInput_man(input_operatorCode);
	listAll = cust.load(custvo);
	map = (Map)listAll.get(0);
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	card_type = Utility.trimNull(map.get("CARD_TYPE"));
	card_id = Utility.trimNull(map.get("CARD_ID"));
}
int editflag = 0; 
if (serial_no.intValue() > 0 && !request.getMethod().equals("POST"))
{
	vo.setCust_id(cust_id);
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	Map rowMap=(Map)shareHolder.list(vo).get(0);;
	hdnm   = Utility.trimNull(rowMap.get("HDNM"));
	hitp  = Utility.trimNull(rowMap.get("HITP"));
	hdid = Utility.trimNull(rowMap.get("HDID"));
	hivt = Utility.parseInt(Utility.trimNull(rowMap.get("HIVT")),null);
}

if (request.getMethod().equals("POST"))
{
	vo.setHdnm(hdnm);
	vo.setHitp(hitp);
	vo.setHdid(hdid);
	vo.setHivt(hivt);
	vo.setInput_man(input_operatorCode);
	if (serial_no.intValue() > 0)
	{
		vo.setSerial_no(serial_no);
		shareHolder.modi(vo);
	}
	else
	{
		vo.setCust_id(cust_id);
		shareHolder.append(vo);
	}
	bSuccess = true;
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>信息维护</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = true;
	window.close();
<%}%>

function validateForm(form)
{
	if(!sl_check(form.hdnm, "股东姓名", 128, 1))	return false;	
	if(!sl_checkChoice(form.hitp, "股东身份证件类型"))	return false;	
	if(!sl_check(form.hdid, "股东身份证件号码", 30, 1))	return false;	
	if(!sl_checkDate(form.hivt_picker,"股东身份证件类型有效期限"))	return false;
	syncDatePicker(form.hivt_picker, form.hivt);
	return sl_check_update();
}
</script>
</HEAD>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="shareholders_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_id" value="<%=cust_id%>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>

			<TD vAlign=top align=center>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="98%">
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="/images/member.gif" align="absbottom" width="32" height="28">
								<font color="#215dc6"><b>客户股东信息维护</b></font></td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="0" cellpadding="4">
							<tr><td colspan="2"><b>客户信息</b></td></tr>
							<tr><td align="right">客户名称:</td>
								<td align="left"><input name="cust_name" class="edline" readonly size="50" value="<%=cust_name%>"></td>
							</tr>
							<tr>
								<td align="right">证件类型:</td>
								<td align="left"><input class="edline" readonly size="25" value="<%=card_type%>"></td>
							</tr>
							<tr>	
								<td align="right">客户证件号码:</td>
								<td align="left"><input name="cust_code" class="edline" readonly  size="25" value="<%=card_id%>"></td>
							</tr>
			
							<tr>
								<td width="35%"><b>股东信息</b></td>
								<td align="left"></td>
							</tr>
							<tr>
								<td align="right">股东姓名:</td>
								<td align="left"><input type="text" name="hdnm" onkeydown="javascript:nextKeyPress(this)" size="25" value="<%=hdnm%>"></td>
							</tr>
							<tr>
								<td align="right">股东身份证件类型:&nbsp;</td>
								<td align="left">
									<select size="1" name="hitp" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
									<%=Argument.getCardTypeOptions2(hitp)%>
									</select>
								</td>
							</tr>
							<tr>
								<td align="right">股东身份证件号码:</td>
								<td align="left"><input type="text" name="hdid" onkeydown="javascript:nextKeyPress(this)" size="25" value="<%=hdid%>"></td>
							</tr>
							<tr>
								<td align="right">股东身份证件有效期限:</td>
								<td align="left">
									<INPUT TYPE="text" id="hivt_picker" NAME="hivt_picker" class=selecttext value="<%=Format.formatDateLine(hivt)%>">
									<INPUT TYPE="hidden" NAME="hivt" value="">
								</td>
							</tr>
							<script language="javascript">
								laydate({elem:'#hivt_picker',format:'YYYY-MM-DD',istime:false});
							</script>
						</table><br>
						<table border="0" width="95%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 
										onclick="javascript: if(document.theform.onsubmit()){ disableAllBtn(true);document.theform.submit(); } ">
									保存(<u>S</u>)</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>

</HTML>
<%shareHolder.remove();
%>
