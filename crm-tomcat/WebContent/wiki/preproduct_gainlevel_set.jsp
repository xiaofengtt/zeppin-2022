<%@ page contentType="text/html; charset=GBK" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));	
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"), new Integer(0));	
BigDecimal exp_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("exp_rate")),new BigDecimal(0)).multiply(new BigDecimal(0.01));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"),new Integer(0));	
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String prov_level_name = Utility.trimNull(request.getParameter("prov_level_name"));
BigDecimal lower_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("lower_limit")),new BigDecimal(0));
BigDecimal upper_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("upper_limit")),new BigDecimal(0));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer start_date = Utility.parseInt(request.getParameter("start_date"), new Integer(0));	
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(0));	
Integer rate_serial_no = Utility.parseInt(request.getParameter("rate_serial_no"), new Integer(0));	

boolean bSuccess = false;
String error_mess = "";

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

String product_name = Argument.getPreProduct_name(preproduct_id);

if("POST".equals(request.getMethod())){	
	vo.setProv_level(prov_level);
	vo.setExp_rate(exp_rate.setScale(5,BigDecimal.ROUND_HALF_EVEN));
	vo.setLower_limit(lower_limit);
	vo.setUpper_limit(upper_limit);
	vo.setSummary(summary) ;	
	vo.setInput_man(input_operatorCode) ;
	vo.setProv_flag(prov_flag);
	vo.setPreproduct_id(preproduct_id);
	vo.setSerial_no(serial_no);

	vo.setRate_serial_no(rate_serial_no);
	vo.setStart_date(start_date);
	vo.setEnd_date(end_date);
	
	try{
		if(serial_no.intValue()==0)//新增	
			product.addPreproductProv(vo);
		else {//修改
			product.modiPreproductProv(vo);
			product.modiPreproductProvRate(vo);
		}
		bSuccess = true ;

	}catch(Exception e){
		error_mess = e.getMessage() ;
	}	
}

if(serial_no.intValue()> 0){
	vo.setSerial_no(serial_no) ;

	List list = product.queryPreproductProv(vo, 1, -1).getRsList();
	if (list.size()>0) {
		Map map = (Map) list.get(0);
		product_name = (String)map.get("PRODUCT_NAME");
		prov_flag = (Integer)map.get("PROV_FLAG") ;
		prov_level = (String)map.get("PROV_LEVEL");
		prov_level_name = (String)map.get("PROV_LEVEL_NAME");
		lower_limit = (BigDecimal)map.get("LOWER_LIMIT");
		upper_limit = (BigDecimal)map.get("UPPER_LIMIT");
		summary = (String)map.get("SUMMARY") ;
		exp_rate = ((BigDecimal)map.get("GAIN_RATE")).multiply(new BigDecimal(100.0));

		rate_serial_no = (Integer)map.get("RATE_SERIAL_NO");
		start_date = (Integer)map.get("START_DATE");
		end_date = (Integer)map.get("END_DATE");
	}
}

String qs = Utility.getQueryString(request, new String[]{"page", "pagesize", "status", "class1", "open"});

product.remove();
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>收益级别设置新建</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
<%if (bSuccess && error_mess.equals("")){%>
	sl_alert("保存成功!");
	location.href = "preproduct_gainlevel_list.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>";
<%}else if(!error_mess.equals("")){%>
   	sl_alert("<%=error_mess%>");
<%}%>

function validateForm(form){
	if(!sl_checkDecimal(form.exp_rate, "预期收益率", 7, 4, 0)) return false; // 
	if(!sl_checkChoice(form.prov_flag,"收益优先级"))	return false;	
	if(!sl_checkChoice(form.prov_level,"收益级别"))	return false;
	if(!sl_checkDecimal(form.lower_limit, "份额下限", 16, 2, 0))		return false;
	if(!sl_checkDecimal(form.upper_limit, "份额上限", 16, 2, 0))		return false;
	if(!(sl_parseFloat(form.lower_limit.value)!=0 && sl_parseFloat(form.upper_limit.value)==0)&&(sl_parseFloat(form.lower_limit.value) > sl_parseFloat(form.upper_limit.value))){
		alert("份额上限不能小于份额下限");
		return false;
	}
	return sl_check_update();	
}

function showname(value){
	//var ops=document.getElementById("leve_id");
	//alert(ops.[value].text);
	//document.theform.prov_level_name.value=ops.[selectedIndex].text;

   var sel_obj = document.getElementById("mycom");
   var index = sel_obj.selectedIndex;
	//alert(index);
  // alert(sel_obj.options[index].value);
  // alert(sel_obj.options[index].text); 
	document.theform.prov_level_name.value=sel_obj.options[index].text;
}

function back(){
	location.href = "preproduct_gainlevel_list.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>";
}

function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		to_money_cn.innerText = "(元)";
	else
		to_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney2(value){
	temp = value;
	if (trim(value) == "")
		to_money_cn2.innerText = " (元)";
	else
		to_money_cn2.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</HEAD>
<BODY class="BODY body-nox" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="preproduct_gainlevel_set.jsp?<%=qs%>" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no %>"/>
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left width="100%">
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle width="100%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"><font color="#215dc6"><b>收益级别设置</b></font></td>
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3" cellpadding="3">
							<tr>
							    <td align="right"><font color="red">*</font>预发行产品:</td >
							    <td align="left" colspan="3">
									<%=product_name%>
								 	<input type="hidden" name="preproduct_id" value="<%=preproduct_id%>"/>
							    </td>
							</tr>
							<tr>
								<td align="right"><font color="red">*</font>预期收益率:</td>
								<td colspan="3" align="left">
									<input type="hidden" name="rate_serial_no" value="<%=rate_serial_no%>"/>
									<input type="hidden" name="start_date" value="<%=start_date%>"/>
									<input type="hidden" name="end_date" value="<%=end_date%>"/>
									<input type="text" name="exp_rate" value="<%=exp_rate.setScale(2,BigDecimal.ROUND_HALF_UP)%>"/>%		
								</td>
							</tr>
							<tr>
								<td align="right"  width="10%"><font color="red">*</font>收益优先级:</td>
								<td  width="40%" align="left">
									<SELECT name="prov_flag" style="width:120px;">
										<%=Argument.getTableOptions2(3003,prov_flag)%>
									</SELECT>
								</td>	
								<td  width="10%" align="right"><font color="red">*</font>收益级别:</td>
								<td  width="40%" align="left"><input type=hidden name="prov_level_name" value="<%=prov_level_name %>">
									<SELECT name="prov_level" id="mycom" onchange="javascript:showname(this.value);" onkeydown="javascript:nextKeyPress(this)" style="width:120px;">
									  	<%=Argument.getProvlevelOptions(prov_level)%>
								    </SELECT>
								</td>
							</tr>
							<tr>
						    	<td align="right"><font color="red">*</font>份额下限:</td >
						        <td align="left">
						        	<input type="text" maxlength="32" name="lower_limit" value="<%=Format.formatMoney2(lower_limit)%>" onkeyup="javascript:showCnMoney(this.value)" onkeydown="javascript:nextKeyPress(this);" style="width:120px;">
									<span id="to_money_cn" class="span">&nbsp;(元)</span>
						        </td>
								<td align="right"><font color="red">*</font>份额上限:</td>
								<td width="" align="left">
									<input type="text" maxlength="32" name="upper_limit" value="<%=Format.formatMoney2(upper_limit)%>" onkeyup="javascript:showCnMoney2(this.value)" onkeydown="javascript:nextKeyPress(this);" style="width:120px;">
									<span id="to_money_cn2" class="span">&nbsp;(元)</span>
								</td>	
						    </tr>
							<tr>
								<td width="" align="right">备&nbsp;注:</td>
								<td width="" colspan="3" align="left"><TEXTAREA cols="132" rows="4" name="summary"><%=Utility.trimNull(summary)%></TEXTAREA></td>
							</tr>
						</table>
						<BR>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}">保存(<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:back();">返回(<u>B</u>)</button>
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