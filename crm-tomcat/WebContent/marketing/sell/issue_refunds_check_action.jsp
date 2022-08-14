<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String Url = request.getParameter("Url");

Integer serial_no =Utility.parseInt(request.getParameter("serial_no"),null);
Integer rebate_flag = Utility.parseInt(request.getParameter("rebate_flag"),new Integer(1));
Integer product_id = new Integer(0);
boolean bSuccess = false;
String rebate_name = "";
String fee_name = "认购";

//合同基本信息
String cust_name = "";
String ht_status_name = "";
String product_name = "";
String contract_sub_bh = "";
BigDecimal ht_money = new BigDecimal(0);
BigDecimal fee_money = new BigDecimal(0);
BigDecimal sq_money = new BigDecimal(0);

SqstopContractLocal refundment = EJBFactory.getSqstopContract();
ContractLocal contract = EJBFactory.getContract();
ApplyreachLocal applyreach_loacl = EJBFactory.getApplyreach();

SqstopContractVO vo = new SqstopContractVO();
ContractVO conVO = new ContractVO();
ApplyreachVO apply_vo = new ApplyreachVO();

if(rebate_flag.intValue()==1){
	fee_name = "认购";
	rebate_name = "认购期退款";
}
else if(rebate_flag.intValue()==2){
	fee_name = "申购";
	rebate_name = "申购期退款";
}

if(serial_no != null){
	vo.setSerial_no(serial_no);
	vo.setRebate_flag(rebate_flag);
	vo.setInput_man(input_operatorCode);

	List list = refundment.listBySql(vo);
		
	for(int i=0;i<list.size();i++){
		Map map = (Map)list.get(i);
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),null);
		contract_sub_bh = Utility.trimNull(map.get("CONTRACT_BH"));

		vo.setReason(Utility.trimNull(map.get("REASON")));
		vo.setSq_date(Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),null));
		vo.setFee(Utility.parseDecimal(Utility.trimNull(map.get("FEE")),null));
		vo.setT_rg_fee(Utility.parseInt(Utility.trimNull(map.get("T_RG_FEE")),null));
		vo.setSq_money(Utility.parseDecimal(Utility.trimNull(map.get("SQ_MONEY")),null));
		vo.setHt_fee(Utility.parseDecimal(Utility.trimNull(map.get("HT_FEE")),null));

		if(vo.getFee()!=null)
			vo.setFee(vo.getFee().setScale(2));
		
		if(product_id!=null){		
			//获得合同相关客户信息
			if(rebate_flag.intValue()==1){
				conVO.setProduct_id(product_id);
				conVO.setContract_bh(contract_sub_bh);
				conVO.setBook_code(input_bookCode);
				conVO.setInput_man(input_operatorCode);

				List listCon = contract.listAll(conVO);
				if(listCon.size()>0){
					Map mapCon = (Map)listCon.get(0);

					product_name = Utility.trimNull(mapCon.get("PRODUCT_NAME"));
					cust_name = Utility.trimNull(mapCon.get("CUST_NAME"));
					contract_sub_bh = Utility.trimNull(mapCon.get("CONTRACT_SUB_BH"));	
					ht_status_name = Utility.trimNull(mapCon.get("HT_STATUS_NAME"));
					
					if(Utility.trimNull(mapCon.get("RG_MONEY"))!=null)
						ht_money = Utility.parseDecimal(Utility.trimNull(mapCon.get("RG_MONEY")),new BigDecimal(0.00));
				}	
			}
			else if(rebate_flag.intValue()==2){
				apply_vo.setProduct_id(product_id);
				apply_vo.setContract_bh(contract_sub_bh);
				apply_vo.setBook_code(new Integer(1));
				apply_vo.setInput_man(input_operatorCode);
	
				List applyreach_list = applyreach_loacl.listBySql(apply_vo);
	
				if(applyreach_list!= null && applyreach_list.size() > 0 )
				{
					Map applyreach_map = (Map)applyreach_list.get(0); 
	
					product_name = Utility.trimNull(applyreach_map.get("PRODUCT_NAME"));
					cust_name = Utility.trimNull(applyreach_map.get("CUST_NAME"));
					ht_status_name = Utility.trimNull(applyreach_map.get("HT_STATUS_NAME"));
					ht_money = Utility.parseDecimal(Utility.trimNull(applyreach_map.get("SG_MONEY")),new BigDecimal(0)).setScale(2,2);
					fee_money = Utility.parseDecimal(Utility.trimNull(applyreach_map.get("SG_FEE")),new BigDecimal(0)).setScale(2,2);	
				}
			}	
		}
	}
}

if(request.getMethod().equals("POST")){
		vo.setSerial_no(serial_no);
		vo.setCheck_flag(new Integer(2));
		vo.setInput_man(input_operatorCode);
		refundment.check(vo);
		bSuccess = true;	
}

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

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>
function checkinfo(flag)
{
	var str = "";
	if(flag == 1)
		str = "审核通过"
	if(sl_confirm(str)){
		document.theform.flag.value = flag;
	    document.theform.submit();
    }
}

<%if(bSuccess)
{%>
	sl_alert("审核通过");
    location='issue_refunds_check.jsp?firstFlag=1&rebate_flag=<%=rebate_flag%>';
<%}%>

function htFeeAction(obj){
	var value = obj.value;
	
	if(value==1){
		var sq_money = document.theform.sq_money;
		
		if(!sl_checkDecimal(document.theform.sq_money, '退款金额 ', 13, 2, 1)) return false;
		if(document.theform.sq_money.value<=0){
			sl_alert("退款金额不能小于零或者不能为空 !");
			obj.options[1].selected = true;
		}
		else{
			var rg_money = parseInt(document.theform.ht_money.value);
			var sq_money = parseInt(document.theform.sq_money.value);
			var fee_money = parseInt(document.theform.fee_money.value);

			document.theform.ht_fee.value = ( fee_money * sq_money )/rg_money;
			document.theform.ht_fee.style.display = "";
		}
	} 
	else{
		document.theform.ht_fee.style.display = "none";
	}
}

window.onload = function(){
	var obj = document.theform.t_rg_fee_value;
	htFeeAction(obj);
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" class="body body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="POST" action="issue_refunds_check_action.jsp" >
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<input type="hidden" name="customer_cust_id" value="">
<input type="hidden" name="rebate_flag" value="<%=rebate_flag%>">
<input type="hidden" name="flag">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
	<TR>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
			<tr>
				<td>

				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td class="page-title"><b>发行期退款审核</b></td>
					</tr>
				</table>
<br/>
				<table border="0" width="100%" cellspacing="1" cellpadding="4" class="product-list">		
					<tr>
						<td align="right">退款期:</td>
						<td colspan="3">
							<input readonly class='edline' name="ht_rebate_flag" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(rebate_name)%>" size="60">
						</td>
					</tr>			
	
					<tr>
						<td align="right">产品名称:</td>
						<td>
							<input readonly class='edline' name="product_name" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(product_name)%>" size="60">
						</td>
						<td align="right">合同编号:</td>
						<td>
							<input readonly class='edline' name="contract_bh" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(contract_sub_bh)%>" size="20">
						</td>
					</tr>

					<tr>
						<td colspan="4"><b>合同基本信息</b></td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
						<td ><input maxlength="100" readonly class='edline'  name="cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_name)%>">&nbsp;&nbsp;&nbsp;
						</td>
						<td align="right"><%=LocalUtilis.language("class.htStatus",clientLocale)%> :</td><!--合同状态-->
						<td><input readonly class='edline' name="ht_status_name" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(ht_status_name)%>" size="20"></td>
					</tr>	
					<tr>
						<td align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</td><!--合同金额-->
						<td >
							<INPUT readonly class='edline' name="ht_money" size="20" value="<%=Utility.trimNull(ht_money)%>" onkeydown="javascript:nextKeyPress(this);" onkeyup="javascript:showCnMoney(this.value,ht_money_name)">
							<span id="ht_money" class="span">
						</td>
						<td align="right"><%=fee_name%>费用:</td>
						<td>
							<INPUT readonly class='edline' name="fee_money" size="20" value="<%=Utility.trimNull(fee_money)%>" onkeydown="javascript:nextKeyPress(this);" onkeyup="javascript:showCnMoney(this.value,fee_money_name)">
							<span id="fee_money" class="span">
						</td>
					</tr>
			
					<tr>
						<td></td>
						<td><span id="ht_money_name" class="span"></span></td>
						<td colspan="2"></td>
					</tr>	
				
					<tr>
						<td colspan="4"><b>退款信息</b></td>
					</tr>

					<tr>
						<td align="right">退款手续费:</td>
						<td>
							<input readonly="readonly" class="edline" onkeydown="javascript:nextKeyPress(this)" name="fee" size="25" value="<%=Utility.trimNull(vo.getFee())%>" onkeyup="javascript:showCnMoney(this.value,fee_name)">			
						</td>
						<td align="right">退款金额:</td>
						<td>
							<input readonly="readonly" class="edline" onkeydown="javascript:nextKeyPress(this)"name="sq_money" size="25" value="<%=Utility.trimNull(vo.getSq_money())%>"onkeyup="javascript:showCnMoney(this.value,sq_money_cn)"><span id="sq_money_cn" class="span"></span>
						</td>						
					</tr>

					<tr>
						<td align="right">退款日期:</td>
						<td>
							<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="fee" size="25" value="<%=Format.formatDateLine(vo.getSq_date())%>">		
						</td>
						<td align="right">是否退认购费:</td>
						<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="t_rg_fee" size="10" 
								value="<%if(vo.getT_rg_fee()!= null && vo.getT_rg_fee().intValue() == 1) out.print("是");else out.print("否");%>">
							<input readonly <%if(vo.getT_rg_fee()!= null && vo.getT_rg_fee().intValue() == 1){%>style="display:"<%} else {%> style="display:none" <%}%> class='edline' name="ht_fee" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(vo.getHt_fee())%>" size="20">
							<input type="hidden" name="t_rg_fee_value" value="<%= vo.getT_rg_fee().intValue()%>">
						</td>
					</tr>				

					<tr>
						<td align="right">退款原因:</td>
						<td colspan="3"><textarea  readonly="readonly" rows="4" onkeydown="javascript:nextKeyPress(this)" name="Summary" cols="100"><%=Utility.trimNull(vo.getReason())%></textarea></td>
					</tr>
				</table>

				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:checkinfo(1)">审核通过(<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:location='issue_refunds_check.jsp';">返回(<u>B</u>)</button>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%
refundment.remove();
%>