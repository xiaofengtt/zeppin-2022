<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%try{ %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/fundInfoService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript">
/*��������*/
function validateForm(form)
{
	if(!sl_checkChoice(form.product_id, "ѡ���Ʒ"))			return false;

	if(!sl_checkDecimal(form.redem_ption, "��ر���", 3, 2, 1))		return false;
	if(form.redem_ption.value <= 0)
	{
		sl_alert("��ر�������С�ڵ����㣡");
		form.redem_ption.select();
		return false;
	}
	if(form.redem_ption.value >100)
	{
		sl_alert("��ر������ܴ���100��");
		form.redem_ption.select();
		return false;
	}

	if(sl_confirm("����"))
	{
		var url = "large_redeem_query_do.jsp?product_id="+form.product_id.value+"&redem_ption="+form.redem_ption.value+"&open_date="+document.getElementById("OPEN_DATE_NO").innerText+"&lflag="+document.getElementById("LARGE_REDEEM_FLAG_NO").innerText;
		location = url;
	}
}

/*ѡ���Ʒ*/
function setProductInfo(product_id){
	if(product_id != "" && product_id != 0)
	{
		fundInfoService.getProductInfo(<%=input_operatorCode%>, <%=input_bookCode%>, product_id, callbackContractInfo);
		fundInfoService.getSqredeemLarge(product_id, callbackContractInfo1);
	}
	else{
		document.getElementById("product_name").innerText = "";
		document.getElementById("currency_id").innerText = "";
		document.getElementById("product_jc").innerText = "";
		document.getElementById("pre_start_date").innerText = "";
		document.getElementById("pre_end_date").innerText = "";
		document.getElementById("open_flag").innerText = "";
		document.getElementById("start_date").innerText = "";
		document.getElementById("end_date").innerText = "";
		document.getElementById("manager_man").innerText = "";

		document.getElementById("OPEN_DATE").innerText = "";
		document.getElementById("NAV").innerText = "";
		document.getElementById("PURCHASE_NUM").innerText = "";
		document.getElementById("PURCHASE_MONEY").innerText = "";
		document.getElementById("PURCHASE_AMOUNT").innerText = "";
		document.getElementById("REDEEM_NUM").innerText = "";
		document.getElementById("REDEEM_AMOUNT").innerText = "";
		document.getElementById("NETREDEEM_AMOUNT").innerText = "";
		document.getElementById("NETREDEEM_PERCENT").innerText = "";
		document.getElementById("REDEEM_PERCENT").innerText = "";
		document.getElementById("LAST_TOTAL_AMOUNT").innerText = "";
		document.getElementById("LARGE_REDEEM_PERCENT").innerText = "";
		document.getElementById("LARGE_REDEEM_CONDITION").innerText = "";
		document.getElementById("LARGE_REDEEM_FLAG").innerText = "";
		document.getElementById("LARGE_REDEEM_FLAG_NO").innerText = "";
		document.getElementById("LARGE_REDEEM_RATE").innerHTML = "";
	}
}

/*�ص�����*/
function callbackContractInfo(data){
	for(var i=0; i<data.length; i++){
		var map = data[i];
		document.getElementById("product_name").innerText = map.product_name;
		document.getElementById("currency_id").innerText = map.currency_id;
		document.getElementById("product_jc").innerText = map.product_jc;
		document.getElementById("pre_start_date").innerText = map.pre_start_date;
		document.getElementById("pre_end_date").innerText = map.pre_end_date;
		document.getElementById("open_flag").innerText = map.open_flag;
		document.getElementById("start_date").innerText = map.start_date;
		document.getElementById("end_date").innerText = map.end_date;
		document.getElementById("manager_man").innerText = map.manager_man;
	}
}

/*�ص�����*/
function callbackContractInfo1(data){
	for(var i=0; i<data.length; i++){
		var map = data[i];
		document.getElementById("OPEN_DATE").innerText = map.OPEN_DATE;
		document.getElementById("OPEN_DATE_NO").innerText = map.OPEN_DATE_NO;
		document.getElementById("NAV").innerText = map.NAV;
		document.getElementById("PURCHASE_NUM").innerText = map.PURCHASE_NUM;
		document.getElementById("PURCHASE_MONEY").innerText = map.PURCHASE_MONEY;
		document.getElementById("PURCHASE_AMOUNT").innerText = map.PURCHASE_AMOUNT;
		document.getElementById("REDEEM_NUM").innerText = map.REDEEM_NUM;
		document.getElementById("REDEEM_AMOUNT").innerText = map.REDEEM_AMOUNT;
		document.getElementById("NETREDEEM_AMOUNT").innerText = map.NETREDEEM_AMOUNT;
		document.getElementById("NETREDEEM_PERCENT").innerText = map.NETREDEEM_PERCENT;
		document.getElementById("REDEEM_PERCENT").innerText = map.REDEEM_PERCENT;
		document.getElementById("LAST_TOTAL_AMOUNT").innerText = map.LAST_TOTAL_AMOUNT;
		document.getElementById("LARGE_REDEEM_PERCENT").innerText = map.LARGE_REDEEM_PERCENT;
		document.getElementById("LARGE_REDEEM_CONDITION").innerText = map.LARGE_REDEEM_CONDITION;
		document.getElementById("LARGE_REDEEM_FLAG").innerHTML = map.LARGE_REDEEM_FLAG;
		document.getElementById("LARGE_REDEEM_FLAG_NO").innerHTML = map.LARGE_REDEEM_FLAG_NO;

		if(map.LARGE_REDEEM_CONDITION_NO == 1)
			document.getElementById("REDEEM_PERCENT_NAME").style.display = "none";
		else
			document.getElementById("REDEEM_PERCENT_NAME").style.display = "";

		if(map.LARGE_DEAL_FLAG == 1)
		{
			document.getElementById("redem_ption").style.display = "none";
			document.getElementById("redem_ption_fh").style.display = "none";
			document.getElementById("LARGE_REDEEM_RATE").innerHTML = map.LARGE_REDEEM_RATE;
			document.getElementById("buttontr").style.display = "none";
		}
		else
		{
			document.getElementById("redem_ption").style.display = "";
			document.getElementById("redem_ption_fh").style.display = "";
			if(map.LARGE_REDEEM_FLAG_NO != 1)
				document.getElementById("redem_ption").value = "100";
			else
				document.getElementById("redem_ption").value = "";
			document.getElementById("LARGE_REDEEM_RATE").innerHTML = "";
			document.getElementById("buttontr").style.display = "";
		}
	}
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('����Ĳ�Ʒ��Ų����ڣ�');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}

function searchProduct(value)
{
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('����Ĳ�Ʒ��Ų����ڣ�');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

/*����ʱ�ж�ȫ�ֲ�Ʒ�Ƿ����*/
function isExitProductInfo(product_id){
	var product_obj = document.theform.product_id;
	if(product_id != "" && product_id != 0 && product_obj != null)
	{
		for(var i=0; i<product_obj.length; i++)
		{
			if(product_obj.options[i].value == product_id)
				setProductInfo(product_id);
		}
	}
}
</SCRIPT>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin=0 onload="javascript:isExitProductInfo(<%=overall_product_id%>);">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TBODY>
					<TR>
						<TD align=center>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><IMG src="/images/member.gif" align="Bottom" border=0 width="32" height="28"><b><%=menu_info%></b></td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" cellpadding="3" cellspacing="1" width="95%" align="center" class="tatablecolor">
						   	<tr class="tatitle">
						    	<td colspan="6">��Ʒ��Ϣ</td>
						   	</tr>
						   	<tr class="tacontent">
								<td align="right">��Ʒ���:</td>
								<td colspan="5">
									<input type="text" name="productid" value="" onkeydown="javascript:setProduct(this.value);" size="15">
										&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
								</td>
							</tr>
						   	<tr class="tacontent">
								<td align="right">��Ʒ����:</td >
								<td align="left" colspan="5">
									<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width:338px" onchange="javascript:setProductInfo(this.value);">
										<%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,51)%>
									</select>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right" width="15%">��Ʒ����:</td>
								<td align="left" width="17%">
									<span id="product_name"></span>
								</td>
								<td align="right" width="15%">��Ʒ���:</td>
								<td align="left" width="17%">
									<span id="product_jc"></span>
								</td>
								<td align="right" width="15%">����:</td>
								<td align="left" width="17%">
									<span id="currency_id"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
								<td align="right" width="15%">�ƽ�����ʼ����:</td>
								<td align="left" width="17%">
									<span id="pre_start_date"></span>
								</td>
								<td align="right" width="15%">�ƽ��ڽ�������:</td>
								<td align="left" width="17%">
									<span id="pre_end_date"></span>
								</td>
								<td align="right" width="15%">����ʽ��־:</td>
								<td align="left" width="17%">
									<span id="open_flag"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right">������ʼ����:</td>
								<td align="left">
									<span id="start_date"></span>
								</td>
								<td align="right">���н�������:</td>
								<td align="left">
									<span id="end_date"></span>
								</td>
								<td align="right">��Ʒ����:</td>
								<td align="left">
									<span id="manager_man"></span>
								</td>
						   	</tr>

							<tr class="tatitle">
						    	<td colspan="6">�����Ϣ</td>
						   	</tr>
							<tr class="tacontent">
								<td align="right" width="15%">������:</td>
								<td align="left" width="17%">
									<span id="OPEN_DATE"></span>
									<span id="OPEN_DATE_NO" style="display: none;"></span>
								</td>
								<td align="right" width="15%">�����վ�ֵ:</td>
								<td align="left" width="17%">
									<span id="NAV"></span>
								</td>
								<td align="right" width="15%"></td>
								<td align="left" width="17%">
									<span id="pre_money"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
								<td align="right">�����깺��ͬ��:</td>
								<td align="left">
									<span id="PURCHASE_NUM"></span>
								</td>
								<td align="right">�����깺���:</td>
								<td align="left">
									<span id="PURCHASE_MONEY"></span>
								</td>
								<td align="right">�����깺�ݶ�:</td>
								<td align="left">
									<span id="PURCHASE_AMOUNT"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right">������غ�ͬ��:</td>
								<td align="left">
									<span id="REDEEM_NUM"></span>
								</td>
								<td align="right">������طݶ�:</td>
								<td align="left">
									<span id="REDEEM_AMOUNT"></span>
								</td>
								<td align="right"></td>
								<td align="left">
									<span id="manager_man"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right">����طݶ�:</td>
								<td align="left">
									<span id="NETREDEEM_AMOUNT"></span>
								</td>
								<td align="right">����ر���:</td>
								<td align="left">
									<span id="NETREDEEM_PERCENT"></span>
								</td>
								<td align="right"><span id="REDEEM_PERCENT_NAME">����ر���:</span></td>
								<td align="left">
									<span id="REDEEM_PERCENT"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right">�����ܷݶ�:</td>
								<td align="left">
									<span id="LAST_TOTAL_AMOUNT"></span>
								</td>
								<td align="right">�����ܷݶ�ٷݱ�:</td>
								<td align="left">
									<span id="LARGE_REDEEM_PERCENT"></span>
								</td>
								<td align="right">�޶�����ж�����:</td>
								<td align="left">
									<span id="LARGE_REDEEM_CONDITION"></span>
								</td>
						   	</tr>
							<tr class="tacontent">
						   		<td align="right">�Ƿ񹹳ɾ޶����:</td>
								<td align="left">
									<span id="LARGE_REDEEM_FLAG"></span>
									<span id="LARGE_REDEEM_FLAG_NO" style="display: none;"></span>
								</td>
								<td align="right">������ر���:</td>
								<td align="left">
									<input type="text" name="redem_ption" id="redem_ption" onkeydown="javascript:setProduct(this.value);"><span id="redem_ption_fh">%</span>
									<span id="LARGE_REDEEM_RATE"></span>
								</td>
								<td align="right"></td>
								<td align="left">
									
								</td>
						   	</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr id="buttontr">
								<td align="right">
									<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){	document.theform.submit(); disableAllBtn(true);}">ȷ��(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
}catch(Exception e){
	throw e;
}
%>