<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
//��ȡ����
RedeemLocal redeem = EJBFactory.getRedeem();
RedeemVO vo = new RedeemVO();

//ҳ�渨������
boolean bSuccess = false;

//������ʱ����
input_bookCode = new Integer(1);

//��������
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);
Integer sq_date = new Integer(0);
BigDecimal redeem_amout = new BigDecimal(0);
Integer ben_serail_no = new Integer(0);

//�����Ϣ
if(request.getMethod().equals("POST")){
	sq_date = Utility.parseInt(request.getParameter("sq_date"),null);
	redeem_amout = Utility.parseDecimal(request.getParameter("redeem_amout"),null);
	ben_serail_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));

	vo.setBen_serail_no(ben_serail_no);
	vo.setRedeem_amout(redeem_amout);
	vo.setSq_date(sq_date);
	vo.setInput_man(input_operatorCode);
	if(Utility.trimNull(request.getParameter("has_transfer")).equals("1")){
		vo.setTransfer_product_id(Utility.parseInt(request.getParameter("transfer_product_id"),null));
		vo.setTransfer_sub_product_id(Utility.parseInt(request.getParameter("transfer_sub_product_id"),null));
		vo.setTransfer_money(Utility.parseDecimal(request.getParameter("transfer_money"),null));
	}
	redeem.append(vo);
	bSuccess = true;
}
%>


<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.redemptionAdded",clientLocale)%> </TITLE><!--�������-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>

<script language=javascript>
/*����*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.q_productId.value = document.theform.product_id.value;
		document.theform.action ="redemption_add.jsp";
		document.theform.submit();
	}
}
function $$(el){
	return document.getElementById(el);
}
function selectOthers(flag,vaue){
	var serial_no = "";
	product_id = document.theform.product_id.value;

	if(flag==1){ //��Ʒ�б��ѯ
		utilityService.getRedeemContractBHMessage(<%=input_bookCode%>,vaue,"",<%=input_operatorCode%>,{callback: function(data){
			$("#select_id_1").html("<SELECT  name='contract_bh' style='width: 240px' onkeydown='javascript:nextKeyPress(this)' onChange='javascript:selectOthers(2,this.value);'>"+data+"</SELECT>");
			
				
		}});


		//������ˢ�²�Ʒ�б��ʱ����պ�ͬ�������˵��б�ֵ
		utilityService.getRedeemMoneyAmount(vaue,{callback: function(data){
			document.theform.money.value = data;
			document.theform.redeem_amout.value = data;
		}});

		utilityService.redeemSyrMessage(<%=input_bookCode%>,product_id,vaue,serial_no,<%=input_operatorCode%>,{callback: function(data){
			$("#select_id_2").html("<SELECT name='serial_no' style='width: 400px' onkeydown='javascript:nextKeyPress(this)' onChange='javascript:selectOthers(3,this.value);'>"+data[1]+"</SELECT>");
			document.theform.qs_date.value = data[0];
		}});
	}

	if(flag==2){ //��ͬ�б�
		utilityService.redeemSyrMessage(<%=input_bookCode%>,product_id,escape(vaue),serial_no,<%=input_operatorCode%>,{callback: function(data){
			$("#select_id_2").html("<SELECT name='serial_no' style='width: 400px' onkeydown='javascript:nextKeyPress(this)' onChange='javascript:selectOthers(3,this.value);'>"+data[1]+"</SELECT>");
				
			document.theform.qs_date.value = data[0];
		}});

		//������ˢ�º�ͬ�б��ʱ����������˵��б�ֵ
		document.theform.money.value = "";
		document.theform.redeem_amout.value = "";
		document.theform.bank_name.value = "";
		document.theform.bank_acct.value = "";

	}

	if(flag==3){ //�������б�
		utilityService.getRedeemMoneyAmount(vaue,{callback: function(data){
			document.theform.money.value = data;
			document.theform.redeem_amout.value = data;
		}});
		utilityService.getQCustBankInfo(vaue,{callback: function(data){
			document.theform.bank_name.value = data[0];
			document.theform.bank_acct.value = data[1];
		}});
		utilityService.getCustomerinfoBySerialNoOfTbenifitor(vaue,"CUST_TYPE",function(data){
			document.theform.cust_type.value = data;
		});
	}
}


//�����������ͻ�ʱ�õ��ĺ���
function validateForm(theform){
	if(!sl_checkChoice(theform.product_id,'<%=LocalUtilis.language("class.productName",clientLocale)%> ')) return false;//��Ʒ����
	if(!sl_checkChoice(theform.contract_bh,'<%=LocalUtilis.language("class.contractID",clientLocale)%> ')) return false;//��ͬ���
	if(!sl_checkChoice(theform.serial_no,'<%=LocalUtilis.language("class.qCustName",clientLocale)%> ')) return false;//����������
	theform.redeem_amout.value = sl_parseFloat(theform.redeem_amout.value);
	if(sl_parseFloat(theform.redeem_amout.value)>sl_parseFloat(theform.money.value)){
		//������طݶ�//���ܴ���//����ؽ��
        sl_alert('<%=LocalUtilis.language("class.redeemAmout",clientLocale)+LocalUtilis.language("massage.notGreater",clientLocale)+LocalUtilis.language("class.money2",clientLocale)%>');
		theform.redeem_amout.focus();
		return false;
	}
	if(!sl_checkDate(document.theform.change_date_picker,'<%=LocalUtilis.language("class.changeDate3",clientLocale)%> ')) return false;//�����������
	syncDatePicker(document.theform.change_date_picker, document.theform.sq_date);
	syncDatePicker(document.theform.qs_date, document.theform.qs_date);
	if(document.theform.sq_date.value <= document.theform.qs_date.value)
	{
        //�����������//��������//��ͬǩ������
		sl_alert('<%=LocalUtilis.language("class.changeDate3",clientLocale)+LocalUtilis.language("message.beLater",clientLocale)+LocalUtilis.language("class.qsDate",clientLocale)%> ');
		return false;
	}

	if(theform.has_transfer.value == 1){
		if(theform.transfer_product_id.value != 0){
			if(!(document.getElementById("tr_transfer2").style.display == "none")){
				if(!sl_checkChoice(theform.transfer_sub_product_id,"�Ӳ�Ʒ"))
					return false;
			}
			if(!sl_checkDecimal(theform.transfer_money,"ת����",13,3,1)) return false;
			if(parseFloat(theform.transfer_money.value) > parseFloat(theform.redeem_amout.value)){
				if(!confirm("ȷ��ת�������������ؽ��!"))
					return false;
			}
		}
	}

	if(theform.coerce_redeem_flag.value == 1){
		var money = theform.money.value.replace(/,/g,"");
		var rest_amout = null;
		if(theform.cust_type.value == 1){
			rest_amout = theform.min_redeem_vol.value;
		}else if(theform.cust_type.value == 2){
			rest_amout = theform.min_redeem_vol2.value;
		}else{
			alert("��ȡ�ͻ�����ʧ�ܣ����߿ͻ�����δ����!");
			return false;
		}
		if((parseFloat(money) - parseFloat(theform.redeem_amout.value)) < parseFloat(rest_amout) && (parseFloat(money) - parseFloat(theform.redeem_amout.value)) != parseFloat(0)){
			var msg = "��ǰ��Ʒ������ǿ����أ�Ŀǰʣ��ݶ�" + (parseFloat(money) - parseFloat(theform.redeem_amout.value)) +
				      "С��ǿ����ص����ٱ����ݶ�" + rest_amout + ",������ǿ����أ��Ƿ��������?";
			if(!confirm(msg)){
				return false;
			}
		}
	}

	return sl_check_update();
}

 //��������������ʾ��Ӧ��������  taochen
function showCnNum(value,name){
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese2(value) + ")";
}

function numToChinese2(input){
	var s1 = "��Ҽ��������½��ƾ�";
	var s4 = "ʰ��Ǫ��ʰ��Ǫ��ʰ��Ǫ";
	var temp = "";
	var result = "";
	var bZero = false;

	if (input == null)
		//�����ִ��������ִ�//ֻ�ܰ��������ַ�//�����ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ
        return '<%=LocalUtilis.language("message.stringTip",clientLocale)+"��"+LocalUtilis.language("message.includeChara",clientLocale)+"([0-9][.])"+LocalUtilis.language("message.numToChineseTip",clientLocale)+"��"%>';

	var temp = trim(input);

	var f = parseFloat("0" + temp);
	var len = 0;
	if (temp.indexOf(".") == -1)
		len = temp.length;
	else
		len = temp.indexOf(".");
	if (len > s4.length)
		return "<%=LocalUtilis.language("message.numToChineseTip",clientLocale)%> ��";//�����ִ����ֻ�ܾ�ȷ��Ǫ�ڣ�С����ֻ����λ

	var n1, n2 = 0;
	var num = "";
	var unit = "";

	for (var i = 0; i < temp.length; i++){
		if (i > len + 2){
			break;
		}
		if (i == len){
			unit="��";
			result = result + unit;
			continue;
		}

		n1 = parseInt(temp.charAt(i));
		num = s1.substring(n1, n1 + 1);
		n2 = len-i-2;
		unit = s4.substring(n2, n2 + 1);
		result = result + num + unit;
	}
	if ((len == temp.length) || (len == temp.length - 1))
		result = result + "��";

	result = trimZero(result)+"��";
	return result;
}
/*������Ʒ*/
function searchProduct(value){
	if(event.keyCode == 13){
		searchProduct2(value);
	}
}
/*������Ʒ2*/
function searchProduct2(value){
	var prodid=0;
	if( value != ""){
        var j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.indexOf(value) >= 0)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��');//����Ĳ�Ʒ��Ų�����
			document.theform.product_id.options[0].selected=true;
		}else{
			selectOthers(1,document.theform.product_id.value);
			getGainFlag();
			getCoerceRedeemInfo();
		}
	}
	
}
/*������ͬ*/
function searchContract(value){
	if(event.keyCode == 13){
		searchContract2(value);
	}
}
/*������ͬ2*/
function searchContract2(value){
	var con_id = 0;
	if( value != ""){
        var j = value.length;
		for(i=0;i<document.theform.contract_bh.options.length;i++){
			if(document.theform.contract_bh.options[i].text.indexOf(value) >= 0)
			{
				document.theform.contract_bh.options[i].selected=true;
				con_id = document.theform.contract_bh.options[i].value;
				break;
			}
		}
		if (con_id==0){
			sl_alert(' �����ĺ�ͬ�����ڣ�');
			document.theform.contract_bh.options[0].selected=true;
		}else{
			selectOthers(2,document.theform.contract_bh.value);
		}
	}
	
}

function showCnMoney(value,name){	
	if (trim(value) == ""){
		name.innerText = "";
		document.getElementById("tr_" + name.id).style.display = "none";
	}else{
		name.innerText = "(" + numToChinese2(value) + ")";
		document.getElementById("tr_" + name.id).style.display = "";
	}
}

function getGainFlag(){
	if(<%=user_id.intValue()%> == 2){
		var product_id = document.theform.product_id.value;
		utilityService.getColumnOfTproductLimit(product_id,"GAIN_FLAG",function(data){
			if(data == 5){
				document.getElementById("tr_other_opration").style.display = "";
				document.getElementById("tr_transfer1").style.display = "";
				document.getElementById("tr_transfer2").style.display = "";
				document.getElementById("tr_transfer3").style.display = "";
				document.theform.has_transfer.value = 1;
				getSubProduct(document.theform.transfer_product_id.value);
			}else{
				document.getElementById("tr_other_opration").style.display = "none";
				document.getElementById("tr_transfer1").style.display = "none";
				document.getElementById("tr_transfer2").style.display = "none";
				document.getElementById("tr_transfer3").style.display = "none";
				document.theform.has_transfer.value = "";
			}	
		})
	}
}

function getSubProduct(product_id){
	DWRUtil.removeAllOptions("transfer_sub_product_id");
	DWRUtil.addOptions("transfer_sub_product_id",{"0":"��ѡ��"});
	utilityService.getIsChildProduct(product_id,function(data){
		if(data==1){
			document.getElementById("tr_transfer2").style.display = "";
		}else{
			document.getElementById("tr_transfer2").style.display = "none";
		}
	});
	if(product_id != 0){
		utilityService.getSubProductJson(product_id,3,function(data){
			var json = eval(data);
			for(i=0;i<json.length;i++){
				DWRUtil.addOptions("transfer_sub_product_id",json[i]);
		  }
	  	});
	}
}

function getCoerceRedeemInfo(){
	var product_id = document.theform.product_id.value;
	utilityService.getColumnOfTproductLimit(product_id,"COERCE_REDEEM_FLAG",function(data){
		document.theform.coerce_redeem_flag.value = data;
	})
	utilityService.getColumnOfTproductLimit(product_id,"MIN_REDEEM_VOL",function(data){
		document.theform.min_redeem_vol.value = data;
	})
	utilityService.getColumnOfTproductLimit(product_id,"MIN_REDEEM_VOL2",function(data){
		document.theform.min_redeem_vol2.value = data;
	})
}

window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;

	if(v_bSuccess=="true"){
		sl_alert("<%=LocalUtilis.language("message.saveRedemptionInfo",clientLocale)%> ��");//�����Ϣ����ɹ�
		location = "redemption_list.jsp?product_id=<%=product_id%>";
	}

	selectOthers(1,document.theform.product_id.value);	
	getGainFlag();
	getCoerceRedeemInfo();
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<FORM name="theform" method="post" onsubmit="javascript:return validateForm(this);" >
<input type="hidden" name="bSuccess" id="bSuccess" value="<%= bSuccess%>"/>
<input type="hidden" name="q_productId" id="q_productId" value="<%= product_id%>"/>
<input type="hidden" id="has_transfer" name="has_transfer">
<input type="hidden" id="coerce_redeem_flag" name="coerce_redeem_flag">
<input type="hidden" id="min_redeem_vol" name="min_redeem_vol">
<input type="hidden" id="min_redeem_vol2" name="min_redeem_vol2">
<input type="hidden" id="cust_type" name="cust_type">
<div align="left" class="page-title">
	<!--���۹���--><!--���-->
    <font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("message.redemption",clientLocale)%> </b></font>
</div>
<br/>
<div>
<TABLE cellpadding="0" cellspacing="10" width="100%" class="product-list">
	<tr>
		<td align="right">��Ʒ����:</td>
		<td>
			<input type="text" name="productcode" value="" onkeydown="javascript:searchProduct(this.value);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProduct2(document.theform.productcode.value);" />
		</td>
		<td align="right">��ͬ����:</td>
		<td>
			<input type="text" name="contract_code" value="" onkeydown="javascript:searchContract(this.value);" size="15">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchContract2(document.theform.contract_code.value);" />
		</td>
	</tr>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</TD><!--��Ʒѡ��-->
		<TD>
			<SELECT name="product_id" style="width: 400px" onkeydown="javascript:nextKeyPress(this)" onChange="javascript:selectOthers(1,this.value);getGainFlag();getCoerceRedeemInfo();">
				<%=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,28)%>
			</SELECT>
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--��ͬ���-->
		<TD id="select_id_1">
			<select name="contract_bh_test" id = "contract_bh_test" style="width: 200px">
				<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
			</select>
		</TD>
	</TR>
	<TR>
		<TD colspan="4"><font ><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> :</b></font></TD><!--��������Ϣ-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.qCustName",clientLocale)%> :</TD><!--����������-->
		<TD id="select_id_2">
			<select name="cust_name_test" style="width: 125px">
				<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--��ѡ��-->
			</select>
		</TD>
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.money2",clientLocale)%> :</TD><!--����طݶ�-->
		<TD><input type="text" class="edline" readonly name="money" value="" size="25"></TD>
		<TD align="right"><%=LocalUtilis.language("class.qsDate",clientLocale)%> :</TD><!--ǩ������-->
		<TD><input type="text" class="edline" readonly name="qs_date" value="" size="25"></TD>
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</TD><!--��������-->
		<TD><input type="text" class="edline" readonly name="bank_name" value="" size="25"></TD>
		<TD align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</TD><!--�����˻�-->
		<TD><input type="text" class="edline" readonly name="bank_acct" value="" size="25"></TD>
	</TR>
	<TR>
		<TD colspan="4"><font><b><%=LocalUtilis.language("message.redemptionInfo",clientLocale)%> :</b></font></TD><!--�����Ϣ-->
	</TR>
	<TR>
		<TD align="right"><%=LocalUtilis.language("class.redeemAmout",clientLocale)%> :</TD><!--������طݶ�-->
		<TD>
			<input type="text" name="redeem_amout" value="" size="25"
				onkeydown="javascript:nextKeyPress(this)"
				onkeyup="javascript:showCnMoney(this.value,contractsum_cn);">&nbsp;&nbsp;��
		</TD>
		<TD align="right"><%=LocalUtilis.language("class.changeDate3",clientLocale)%> :</TD><!--�����������-->
		<TD>
			<INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13" >
			<INPUT TYPE="hidden" NAME="sq_date"   value="">
		</TD>
	</TR>
	<TR id="tr_contractsum_cn" style="display:none;">
		<TD></TD>
		<TD colspan="3"><span id="contractsum_cn" class="span">&nbsp;</span></TD>
	</TR>
	<tr id="tr_other_opration" style="display:none;">
		<td colspan="4"><font color="red"><b>��������</b></font></td>
	</tr>
	<tr id="tr_transfer1" style="display:none;">	
		<td align="right">ת��������Ʒ:</td>
		<td colspan="3">
			<select id="transfer_product_id" name="transfer_product_id" style="width: 350px;" onchange="javascript:getSubProduct(this.value);">
				<%//=Argument.getProductListOptions(input_bookCode,new Integer(0),"",input_operatorCode,25) %>
				<%=Argument.getPreProductListOptions(new Integer(0),"","110202",input_operatorCode)%>
			</select>
		</td>
	</tr>
	<tr id="tr_transfer2" style="display:none;">	
		<td align="right">�Ӳ�Ʒ:</td>
		<td colspan="3">
			<select id="transfer_sub_product_id" name="transfer_sub_product_id" style="width: 350px;"></select>
		</td>
	</tr>
	<tr id="tr_transfer3" style="display:none;">	
		<td align="right">ת����:</td>
		<td colspan="3">
			<input type="text" id="transfer_money" name="transfer_money" onkeyup="javascript:showCnNum(this.value,transfer_money_cn)">
			<span id="transfer_money_cn" class="span"></span>
		</td>
	</tr>
</TABLE>
</div>

<br>

<div align="right">
	<!--����-->
    <button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;

    <button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.location.href='redemption_list.jsp'"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</FORM>
<%redeem.remove();%>
</BODY>
</HTML>