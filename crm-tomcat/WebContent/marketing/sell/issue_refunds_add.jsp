<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String flag = Utility.trimNull(request.getParameter("flag"));//��ǣ�new���� edit�༭
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer rebate_flag = Utility.parseInt(request.getParameter("rebate_flag"), new Integer(1));
Integer sq_flag = Utility.parseInt(request.getParameter("sq_flag"),new Integer(31));
Integer product_end_date = Utility.parseInt(request.getParameter("product_end_date"),new Integer(0));

Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//��Ʒ���
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//��ͬ���
BigDecimal fee = Utility.parseDecimal(Utility.trimNull(request.getParameter("fee")), null);//�˿�������
Integer sq_date = Utility.parseInt(request.getParameter("sq_date"),null);//�˿�����
Integer t_rg_fee =Utility.parseInt(request.getParameter("t_rg_fee"),new Integer(0));//�Ƿ����Ϲ���
BigDecimal ht_fee =Utility.parseDecimal(request.getParameter("ht_fee"),null);//�Ƿ����Ϲ���
String summary = Utility.trimNull(request.getParameter("summary"));//�˿�ԭ��

Integer ht_serial_no = new Integer(0);
String fee_name = "�Ϲ�";
String defaultOptions = "<option value=\"\" selected>"+enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale)+"</option>";//ѡ��Ĭ��ֵ
String contract_bh_list = defaultOptions;

SqstopContractLocal local = EJBFactory.getSqstopContract();//�������˿�����Bean
ContractLocal contract_local = EJBFactory.getContract();//��ͬBean
MoneyDetailLocal money_local = EJBFactory.getMoneyDetail();//�ɿ���ϸ
ApplyreachLocal applyreach_loacl = EJBFactory.getApplyreach();

SqstopContractVO vo = new SqstopContractVO();
ContractVO contract_vo = new ContractVO();
MoneyDetailVO money_vo = new MoneyDetailVO();
ApplyreachVO apply_vo = new ApplyreachVO();

Map contract_map = new HashMap();
Map applyreach_map = new HashMap();
Map money_detail_map = new HashMap();

List applyreach_list = new ArrayList();
List money_detail_list = new ArrayList();

Iterator applyreach_it = applyreach_list.iterator();
Iterator money_detail_it = money_detail_list.iterator();

//��ͬ������Ϣ
String cust_name = "";
String ht_status_name = "";
BigDecimal ht_money = new BigDecimal(0);
BigDecimal fee_money = new BigDecimal(0);
BigDecimal sq_money = new BigDecimal(0);
int fee_jk_type = 0;

if(rebate_flag.intValue()==2){
	fee_name = "�깺";
	sq_flag = new Integer(45);
}

//�༭ʱ��÷������˿���Ϣ
if(serial_no.intValue()!=0 && flag.equals("edit"))
{
	vo.setSerial_no(serial_no);
	vo.setRebate_flag(rebate_flag);
	vo.setInput_man(input_operatorCode);

	List list = local.listBySql(vo);
	Map map = new HashMap();

	if(list != null && list.size() > 0)
	{
		map = (Map)list.get(0);
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0));
		contract_bh = Utility.trimNull(map.get("CONTRACT_BH"));
		fee = Utility.parseDecimal(Utility.trimNull(map.get("FEE")),null);
		sq_date = Utility.parseInt(Utility.trimNull(map.get("SQ_DATE")),null);
		t_rg_fee = Utility.parseInt(Utility.trimNull(map.get("T_RG_FEE")),null);
		summary = Utility.trimNull(map.get("REASON"));
		ht_fee = Utility.parseDecimal(Utility.trimNull(map.get("HT_FEE")),null);
		sq_money = Utility.parseDecimal(Utility.trimNull(map.get("SQ_MONEY")),null).setScale(2,2);
	}
}

//ѡ���Ʒ�����Ӧ�ĺ�ͬ���
if(product_id != null && product_id.intValue()!=0)
{
	//��ú�ͬ���ѡ�������Ӧ�Ŀͻ���Ϣ
	if(contract_bh != null && !"".equals(contract_bh))
	{
		//��ú�ͬ�б�
		contract_bh_list = Argument.getContract2(new Integer(1), product_id, input_operatorCode,contract_bh);
		//��ú�ͬ��ؿͻ���Ϣ
		if(rebate_flag.intValue()==1){
			contract_vo.setProduct_id(product_id);
			contract_vo.setContract_bh(contract_bh);
			contract_vo.setBook_code(new Integer(1));
			contract_vo.setInput_man(input_operatorCode);

			List contract_list = contract_local.listAll(contract_vo);

			if(contract_list!= null && contract_list.size() > 0 )
			{
				contract_map = (Map)contract_list.get(0);

				ht_serial_no = Utility.parseInt(Utility.trimNull(contract_map.get("SERIAL_NO")),new Integer(0));//��Ӧ��ͬ���е�serial_no;
				cust_name = Utility.trimNull(contract_map.get("CUST_NAME"));
				ht_status_name =  Utility.trimNull(contract_map.get("HT_STATUS_NAME"));
				ht_money = Utility.parseDecimal(Utility.trimNull(contract_map.get("RG_MONEY")),new BigDecimal(0)).setScale(2,2);
				fee_money = Utility.parseDecimal(Utility.trimNull(contract_map.get("RG_FEE_MONEY")),new BigDecimal(0)).setScale(2,2);
				fee_jk_type = Utility.parseInt(Utility.trimNull(contract_map.get("FEE_JK_TYPE")),0);
			}
		}
		else if(rebate_flag.intValue()==2){
			apply_vo.setProduct_id(product_id);
			apply_vo.setContract_bh(contract_bh);
			apply_vo.setBook_code(new Integer(1));
			apply_vo.setInput_man(input_operatorCode);

			applyreach_list = applyreach_loacl.listBySql(apply_vo);

			if(applyreach_list!= null && applyreach_list.size() > 0 )
			{
				applyreach_map = (Map)applyreach_list.get(0); 

				ht_serial_no =  Utility.parseInt(Utility.trimNull(applyreach_map.get("SERIAL_NO")),new Integer(0));
				cust_name = Utility.trimNull(applyreach_map.get("CUST_NAME"));
				ht_status_name = Utility.trimNull(applyreach_map.get("HT_STATUS_NAME"));
				ht_money = Utility.parseDecimal(Utility.trimNull(applyreach_map.get("SG_MONEY")),new BigDecimal(0)).setScale(2,2);
				fee_money = Utility.parseDecimal(Utility.trimNull(applyreach_map.get("SG_FEE")),new BigDecimal(0)).setScale(2,2);	
				fee_jk_type = Utility.parseInt(Utility.trimNull(applyreach_map.get("FEE_JK_TYPE")),0);
			}
		}	
				
		//��ýɿ�������Ϣ
		money_vo.setBook_code(new Integer(1));
		money_vo.setProduct_id(product_id);
		money_vo.setContract_bh(contract_bh);
		money_vo.setInput_man(input_operatorCode);
		money_detail_list = money_local.listBySql(money_vo);
		if(money_detail_list !=null && money_detail_list.size() > 0)
			money_detail_it = money_detail_list.iterator();
		
	}else{
		ProductLocal productLocao = EJBFactory.getProduct();
		ProductVO productVO = new ProductVO();
		productVO.setProduct_id(product_id);
		List productList = productLocao.load(productVO);		
		if(productList.size()==1)
		{
			Map productMap = (Map)productList.get(0);
			product_end_date = Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0));
		}
		
		contract_bh_list = Argument.getContract2(new Integer(1), product_id, input_operatorCode,"110203");
	}
}
%>

<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
//ѡ���Ʒ����ͬ���
function selectOthers()
{		
	form=document.theform;
	product_id=form.product_id.value;
	contract_bh=form.contract_bh.value;
	serial_no =form.serial_no.value;
	flag = form.flag.value;
	rebate_flag = form.rebate_flag.value;
	product_end_date=form.product_end_date.value;

	location="issue_refunds_add.jsp?product_id="+product_id+"&contract_bh="+contract_bh+"&serial_no="+serial_no+"&flag="+flag+"&product_end_date="+product_end_date+"&rebate_flag="+rebate_flag;
}

//������֤
function validateForm()
{
	var product_end_date  = <%=product_end_date%>;
	syncDatePicker(theform.change_date_picker, theform.sq_date);
	if(!sl_checkChoice(theform.product_id, '<%=LocalUtilis.language("class.productName",clientLocale)%> '))//��Ʒ����
		return false;
	if(!sl_checkChoice(theform.contract_bh, '<%=LocalUtilis.language("class.contractID",clientLocale)%> '))//��ͬ���
		return false;
	if(!sl_checkDate(theform.change_date_picker, '<%=LocalUtilis.language("class.changeDate2",clientLocale)%> '))//�˿�����
		return false;
	if(!sl_checkChoice(theform.t_rg_fee, '<%=LocalUtilis.language("class.tRgFee",clientLocale)%> '))//�Ƿ����Ϲ���
		return false;
	if(!sl_check(theform.summary, '<%=LocalUtilis.language("class.description",clientLocale)%> ', 200, 0))//��ע
		return false;	
	newDay = parseInt(theform.product_end_date.value) - parseInt(theform.sq_date.value);
		
	return confirm("ϵͳȷ�ϣ�\n\n��Ʒ�ƽ�����գ�"+theform.product_end_date.value+"\n\n������ջ��� "+newDay+" ��!");	
}

function htFeeAction(obj){
	var value = obj.value;
	
	if(value==1){
		var sq_money = document.theform.sq_money;
		
		if(!sl_checkDecimal(document.theform.sq_money, '�˿��� ', 13, 2, 1)) return false;
		if(document.theform.sq_money.value<=0){
			sl_alert("�˿����С������߲���Ϊ�� !");
			obj.options[1].selected = true;
		}
		else{
			var ht_money = parseInt(document.theform.ht_money.value);
			var sq_money = parseInt(document.theform.sq_money.value);
			var fee_money = parseInt(document.theform.fee_money.value);
			var fee_jk_type = parseInt(document.theform.fee_jk_type.value);

			if(fee_jk_type==1){
				var i1 = ht_money - fee_money;
				var i2 = ( fee_money * sq_money )/t;
				document.theform.ht_fee.value = FormatNumber(i2,2);			
			}
			else{
				var j = ( fee_money * sq_money )/ht_money;
				document.theform.ht_fee.value = FormatNumber(j,2);;					
			}
			document.theform.ht_fee.style.display = "";
		}
	} 
	else{
		document.theform.ht_fee.style.display = "none";
	}
}

window.onload = function(){
	var obj = document.theform.t_rg_fee;
	htFeeAction(obj);
}
</script>
</head>
<body class="BODY body-nox">
<form name="theform" action="issue_refunds_add_do.jsp" method="post" onsubmit="return validateForm();">
<input type="hidden" name="flag" value="<%=flag%>">
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<input type="hidden" name="ht_serial_no" value="<%=ht_serial_no%>">
<input type="hidden" name="product_end_date" value="<%=product_end_date%>">
<input type="hidden" name="fee_jk_type" value="<%=fee_jk_type%>">
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<tr>
				<!--�������˿�-->
                <td class="page-title"><b><%=LocalUtilis.language("message.issueOfRefund",clientLocale)%> </b></td>
			</tr>
		</table>
<br/>
		<table border="0" width="100%" cellspacing="1" cellpadding="4" class="product-list">	
			<tr>
				<td align="right">�˿���ѡ��:</td>
				<td>
				<select size="1" onkeydown="javascript:nextKeyPress(this)" name="rebate_flag" onChange="javascript:selectOthers();" class=productname <%if(flag.equals("edit"))out.print("disabled");%>>
					<option value="1" <%if(rebate_flag.intValue()==1){out.print("selected");}%>>�Ϲ����˿�</option>
					<option value="2" <%if(rebate_flag.intValue()==2){out.print("selected");}%>>�깺���˿�</option>
				</select>
				</td>
			</tr>
				
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--��Ʒѡ��-->
				<td><select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" class=productname onChange="javascript:selectOthers();"  <%if(flag.equals("edit"))out.print("disabled");%>>
						<%=Argument.getProductListOptions(new Integer(1), product_id, "", input_operatorCode, sq_flag.intValue())%>
					</select>
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
				<td><select size="1" onkeydown="javascript:nextKeyPress(this)" name="contract_bh" onChange="javascript:selectOthers();" <%if(flag.equals("edit"))out.print("disabled");%>>
						<%=contract_bh_list%>
				</select>
				</td>
			</tr>
			<tr>
				<td colspan="4"><b><%=LocalUtilis.language("message.contractBasics",clientLocale)%> </b></td><!--��ͬ������Ϣ-->
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_name)%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.htStatus",clientLocale)%> :</td><!--��ͬ״̬-->
				<td><input readonly class='edline' name="ht_status_name" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(ht_status_name)%>" size="20"></td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</td><!--��ͬ���-->
				<td >
					<INPUT readonly class='edline' name="ht_money" size="20" value="<%=Utility.trimNull(ht_money)%>" onkeydown="javascript:nextKeyPress(this);" onkeyup="javascript:showCnMoney(this.value,ht_money_name)">
					<span id="ht_money" class="span">
				</td>
				<td align="right"><%=fee_name%>����:</td>
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
				<td colspan="4"><b><%=LocalUtilis.language("message.refundInfo",clientLocale)%> </b></td><!--�˿���Ϣ-->
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.refundFee",clientLocale)%> :</td><!--�˿�������-->
				<td >
					<input onkeydown="javascript:nextKeyPress(this)" name="fee" size="25" value="<%=Utility.trimNull(fee)%>" onkeyup="javascript:showCnMoney(this.value,fee_name)">			
					<span id="fee_name" class="span"></span>
				</td>
				<td align="right">�˿���:</td>
				<td>
					<input onkeydown="javascript:nextKeyPress(this)" name="sq_money" size="25" value="<%=Utility.trimNull(sq_money)%>"onkeyup="javascript:showCnMoney(this.value,sq_money_cn)"><span id="sq_money_cn" class="span"></span>
				</td>
			</tr>

			<tr>
				<td align="right"><%=LocalUtilis.language("class.changeDate2",clientLocale)%> :</td><!--�˿�����-->
				<td><INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  <%if(sq_date == null){%>value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%>value="<%=Format.formatDateLine(sq_date)%>"<%}%>>
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="sq_date"   value="">
				</td>
				<td align="right">�Ƿ���<%=fee_name%>�� :</td>
				<td><select name="t_rg_fee" onChange="javascript:htFeeAction(this);">
						<option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
						<option value="1" <%if(t_rg_fee.intValue() == 1) out.print("selected");%>><%=LocalUtilis.language("message.yes",clientLocale)%> </option><!--��-->
						<option value="2" <%if(t_rg_fee.intValue() == 2) out.print("selected");%>><%=LocalUtilis.language("message.no3",clientLocale)%> </option>	<!--��-->
					</select>&nbsp;&nbsp;	
					<input readonly style="display:none" class='edline' name="ht_fee" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(ht_fee)%>" size="20">
				</td>
			</tr>				
			<tr>
				<td align="right"><%=LocalUtilis.language("class.summary5",clientLocale)%> :</td><!--�˿�ԭ��-->
				<td colspan="3"><textarea rows="4" onkeydown="javascript:nextKeyPress(this)" name="summary" cols="100"><%=Utility.trimNull(summary)%></textarea></td>	
			</tr>
		</table>

		<table border="0" width="100%" cellspacing="1" cellpadding="4">
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td align="right">
								<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" type="submit"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--����-->
								&nbsp;&nbsp;
								<!--����-->
                                <button type="button"   class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:location='issue_refunds_list.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br><br>

		<%if(money_detail_list.size() > 0){%>
		<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
			<tr class="trh">
				<td align="center"><%=LocalUtilis.language("class.toMoney",clientLocale)%> </td><!--���˽��-->
				<td align="center"><%=LocalUtilis.language("class.feeMoney",clientLocale)%> </td><!--������-->
				<td align="center"><%=LocalUtilis.language("class.feeType",clientLocale)%> </td><!--�ɿʽ-->
				<td align="center"><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--�ɿ�����-->
				<td align="center"><%=LocalUtilis.language("class.toBankDate2",clientLocale)%> </td><!--��������-->
				<td align="center"><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--��ע-->
			</tr>
			<%
			int iCurrent = 0;
			String fee_type_name = "";
			while(money_detail_it.hasNext())
			{
				money_detail_map = (Map)money_detail_it.next();
				if(money_detail_map.get("FEE_MONEY") != null)
				{
					if(Utility.stringToDouble(Utility.trimNull(money_detail_map.get("FEE_MONEY"))).longValue() > 0)
					{
						if(money_detail_map.get("FEE_TYPE") != null)
						{
							if(Utility.parseInt(Utility.trimNull(money_detail_map.get("FEE_TYPE")), new Integer(0)).equals(new Integer(1)))
								fee_type_name = enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//�Ϲ���
							if(Utility.parseInt(Utility.trimNull(money_detail_map.get("FEE_TYPE")), new Integer(0)).equals(new Integer(1)))
								fee_type_name = enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//�깺��
							if(Utility.parseInt(Utility.trimNull(money_detail_map.get("FEE_TYPE")), new Integer(0)).equals(new Integer(1)))
								fee_type_name = enfo.crm.tools.LocalUtilis.language("class.transFee2",clientLocale);//�ݶ�ת����
						}
					}
				}
			%>
			<tr class="tr<%=(iCurrent % 2)%>">	
				<td align="right"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(money_detail_map.get("TO_MONEY")))))%></td>
				<td align="center"><%=Utility.trimNull(fee_type_name)%><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(money_detail_map.get("FEE_MONEY"))))%></td>
				<td align="center"><%=Utility.trimNull(money_detail_map.get("JK_TYPE_NAME"))%></td>
				<td align="center"><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(money_detail_map.get("DZ_DATE")), new Integer(0))))%></td>
				<td align="center"><%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(money_detail_map.get("TO_BANK_DATE")), new Integer(0))))%></td>
				<td align="left"><%=Utility.trimNull(money_detail_map.get("SUMMARY"))%></td>
			</tr>
			<%
			iCurrent ++;
			}
			%>
		</table>
		<%}%>
	</td>
</tr>		
</table>
</form>
</body>
</html>
<%local.remove();%>
