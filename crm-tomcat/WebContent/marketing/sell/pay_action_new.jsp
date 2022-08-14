<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.web.DocumentFile2"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//��ѯ����
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO md_vo = new MoneyDetailVO();
BigDecimal fee_money = new BigDecimal(0.00);
Integer serial_no = new Integer(0);
Integer list_id = new Integer(0);
String contract_bh = "";
boolean bSuccess = false;
//�ύ����
DocumentFile2 file = null;
if(request.getMethod().equals("POST")){

	file = new DocumentFile2(pageContext);
	file.parseRequest();

	md_vo = new MoneyDetailVO();

	fee_money = Utility.parseDecimal(file.getParameter("fee_money"), null);
	
	serial_no = Utility.parseInt(file.getParameter("serial_no"), new Integer(0));
	product_id = Utility.parseInt(file.getParameter("product_id"), new Integer(0));
	list_id = Utility.parseInt(file.getParameter("list_id"), null);
	contract_bh = file.getParameter("contract_bh");
	
	md_vo.setInput_man(input_operatorCode);
	md_vo.setBook_code(input_bookCode);
	md_vo.setProduct_id(Utility.parseInt(file.getParameter("product_id"), new Integer(0)));
	md_vo.setContract_bh(Utility.trimNull(file.getParameter("contract_bh")));
	md_vo.setList_id(Utility.parseInt(file.getParameter("list_id"), new Integer(0)));
	md_vo.setFee_money(fee_money);
	md_vo.setJk_type(Utility.trimNull(file.getParameter("jk_type")));
	md_vo.setSummary(Utility.trimNull(file.getParameter("summary")));

	try{
		int tableRows = Utility.parseInt(file.getParameter("tableRows"),0);
		for(int i=0;i<tableRows;i++){		
			BigDecimal to_money2 = Utility.parseDecimal(file.getParameter("to_money_"+i), null);
			md_vo.setDz_date(Utility.parseInt(Utility.trimNull(file.getParameter("jk_date_"+i)),new Integer(0)));
			md_vo.setTo_money(to_money2);
			md_vo.setTo_amount(to_money2);
			//�������޸�
			serial_no = moneyDetailLocal.append(md_vo);
			file.uploadAttchment(new Integer(13),serial_no,"",null,null,input_operatorCode);
		}	
		bSuccess = true;
	}catch(Exception e){
		//window.location = "pay_list.jsp?q_check_flag=1&product_id="+product_id;
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		bSuccess = false;
	}
	

}
 %>
 
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.paymentManagermentNew",clientLocale)%> </title><!--�ɿ��������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language="javascript">
var n = 1;

window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;		
	if(v_bSuccess=="true"){	
		sl_alert("<%=LocalUtilis.language("message.paymentSuccessful",clientLocale)%> ��");//�ɿ�ɹ�
		op_return();
	}
}

//ѡ���Ʒ�����غ�ͬ���
function selectProductItem(obj){
	if(obj.value==null||obj.value==0){
		sl_alert("��ѡ���Ʒ");
		return false;
	}

	utilityService.queryListBysqlProduct(obj.value,{callback:function (data){
		if(data!=null){
			document.theform.open_flag.vlaue = data.OPEN_FLAG;
			document.theform.nav_price.value = data.NAV_PRICE;
			document.theform.currency_name.value = data.CURRENCY_NAME;
			if(data.OPEN_FLAG==1){
				$("fee_money_name").style.display = '';
				$("fee_money_value").style.display = '';
			}else{
				$("fee_money_name").style.display = 'none';
				$("fee_money_value").style.display = 'none';
			}
		}
		utilityService.queryJKContractOptions(obj.value,'',{callback:function(data){ //���غ�ͬ�б�
			if(data!=null){
				$("contract_bh_select").innerHTML = "<select style='width:240px;'  name='contract_bh' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:selectContractbhItem(this);'>"
					+data+"</SELECT>";
			}
		}});
	}});
}


function selectContractbhItem(obj){
	product_id = document.theform.product_id.value;
	utilityService.queryJKCusomerAndNo('<%=input_bookCode%>',product_id,obj.value,null,{callback:function(data){
		if(data!=null){
			$("list_id_select").innerHTML = "<select name='list_id' onkeydown='javascript:nextKeyPress(this)' style='WIDTH: 240px'>"
				+data+"</SELECT>";
		}
	}});

		utilityService.queryLisBySqlJKContract(product_id,obj.value,{callback:function(data){
		if(data!=null){
			document.theform.rg_money.value = data.RG_MONEY;
			document.theform.old_to_money.value = data.OLD_TO_MONEY;
			document.theform.to_money_0.value = sl_parseFloat(data.RG_MONEY);
		}
	}});
}

//����
function computeMonut(ce){
	//if (event.keyCode == 13){
		//if(document.theform.nav_price.value != 0 ){
			//var v_to_money = document.getElementsByName('to_money')[0].value;
			//var v_nav_price = document.getElementsByName('nav_price')[0].value;
			//document.getElementsByName('to_amount')[0].value= (v_to_money/ v_nav_price);
		//}
	//}
}

function InsertRow(){
	var oTrArr = document.getElementById("jkTable").rows;
	var curRowCount;
	document.getElementById("maxID").value = Number(document.getElementById("maxID").value)+1;
	curRowCount = document.getElementById("maxID").value;

	var oTr = document.getElementById("jkTable").insertRow(oTrArr.length);//�ڵ�ǰ������ĩβ-bottom��֮ǰ����һ��
//���ò����е���ʽ
	oTr.setAttribute("id","tr_"+curRowCount);
//	oTr.setAttribute("className","tr0");

	var oTdTyp = oTr.insertCell(0);//Tr�еĵ�1����Ԫ��-AATypeno�����
	var oTdDet = oTr.insertCell(1);//Tr�еĵ�2����Ԫ��-AADetailno�����
	var oTdCrB = oTr.insertCell(2);//Tr�еĵ�3����Ԫ��-AAOccurBalance�����
	var oTdDrB = oTr.insertCell(3);//Tr�еĵ�4����Ԫ��-AaOccurAmount�����

	
	//Ϊ5����Ԫ����������
	oTdTyp.setAttribute("align","right");
	oTdTyp.setAttribute("with","60");
	oTdDet.setAttribute("align","left");
	oTdDet.setAttribute("with","880");
	oTdCrB.setAttribute("align","right");
	oTdCrB.setAttribute("width","");
	oTdDrB.setAttribute("align","left");
	oTdDrB.setAttribute("width","");

	
	//д�������Ԫ���ڲ��������
	oTdTyp.innerHTML = "�ɿ���"+curRowCount+":";
	oTdDet.innerHTML = "<input type='text' id='to_money_"+curRowCount+"' name='to_money_"+curRowCount+"' size='25' value='' onkeydown='javascript:nextKeyPress(this)';>";
	oTdCrB.innerHTML = "�ɿ�����"+curRowCount+":";
	oTdDrB.innerHTML = "<INPUT TYPE='text' id='jk_date_picker_"+curRowCount+"' NAME='jk_date_picker_"+curRowCount+"' value='<%=Format.formatDateLine(Utility.getCurrentDate())%>'class=selecttext>"
					  +" <INPUT TYPE='button' value='��' class='selectbtn' onclick='javascript:CalendarWebControl.show(theform.jk_date_picker_"+curRowCount+",theform.jk_date_picker_"+curRowCount+".value,this);' tabindex='14'>"
					  +"<INPUT TYPE='hidden' id='jk_date_"+curRowCount+"' NAME='jk_date_"+curRowCount+"' value=''>"
					  +"<a id=\"remove_"+curRowCount+"\" name=\"remove_"+curRowCount+"\" href=\"javascript:removeRow('tr_"+curRowCount+"');\" onkeydown=\"javascript:nextKeyPress(this);\"><img src=\"/images/dev_remove.gif\" width=\"24\" height=\"24\" title=\"ɾ��\"></a>";
}

//ɾ����
function removeRow(ObjID){
	var oTr = document.getElementById(ObjID);
	var oParent = oTr.parentNode;
	oParent.removeChild(oTr);
}

function showCnMoney(value,obj){
	temp = value;
	to_money_cn_0.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney2(value){
	temp = value;
	fee_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

/*���淽��*/
function saveAction(){
	if(document.theform.onsubmit()){ 
		disableAllBtn(true);document.theform.submit();
	}
}

/*
 *��Ӹ���	
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'><button type='button'  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}


/*
 *ɾ������	
 */
function removeline(obj)
{
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function op_return(){
	var product_id= $("product_id").value;
	window.location = "pay_list.jsp?q_check_flag=1&product_id="+product_id;
}

function validateForm(form){

	var to_money = 0;
	var rg_money=sl_parseFloat(form.rg_money.value);
	var old_to_money = sl_parseFloat(form.old_to_money.value);

	var oTrArr = document.getElementById("jkTable").rows;
	form.tableRows.value = oTrArr.length;
	
	if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.productName",clientLocale)%> "))	return false;//��Ʒ����
	if(!sl_checkChoice(form.list_id, '<%=LocalUtilis.language("class.listID",clientLocale)%> '))	return false;//�ɿ��˼����
	if(!sl_checkChoice(form.contract_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> "))	return false;//��ͬ���
	if(form.open_flag.value==1){
		if(!sl_checkDecimal(form.fee_money, "<%=LocalUtilis.language("class.feeAmount2",clientLocale)%> ", 13, 3, 0))	return false;//���ý��
	}
	if(!sl_checkChoice(form.jk_type, "<%=LocalUtilis.language("class.feeType",clientLocale)%> "))	return false;//�ɿʽ
	if(!sl_checkDecimal(form.to_money_0,"�ɿ���",13,3,1)) return false;
	if(!sl_checkDate(form.jk_date_picker_0,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> "))	return false;//�ɿ�����

	for(i=0;i<oTrArr.length;i++){
		if(oTrArr[i].id != null && oTrArr[i].id != ""){
			idStr = oTrArr[i].id;
			idNum = idStr.substring(idStr.lastIndexOf("_")+1,idStr.length);
			var jk_money = document.getElementById("to_money_"+idNum);
			var jk_date = document.getElementById("jk_date_picker_"+idNum);
			to_money += sl_parseFloat(jk_money.value);
			syncDatePicker(jk_date,document.getElementById("jk_date_"+idNum));
		}
	}

	var total_money = (old_to_money+to_money);
	//�ɿ���//����//�Ϲ����//ȷ��Ҫ������    
	var messager = '';  
	if(total_money > rg_money)
	{
		messager = "�ɿ����ܽ������Ϲ����!";
		//return false;
	}


	
	if(!confirm(messager+"ȷ��Ҫ������?")){
		return false;	
	}
	
	return sl_check_update();
}
</script>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="pay_action_new.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="open_flag" name="open_flag" value="">
<input type="hidden" id="maxID" name="maxID" value="">
<input type="hidden" name="tableRows" value="">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<tr>
				<td  class="page-title">
					<!--���۹���--><!--�ɿ�-->
                    <font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("message.payment",clientLocale)%> </b></font>
				</td>
				<td class="btn-wrapper">
				<font size=3>	���Ӷ�νɿ�:<a href="javascript:InsertRow();"><img src="/images/dev_add.gif" width="24" height="24" title="����"></a></font>
				</td>
			</tr>
		</TABLE>
		<br/>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
			<TR>
				<TD align="right" width="15%"><font color="red">*</font><%=LocalUtilis.language("class.productName",clientLocale)%>:</TD>
				<TD colspan="3" width="85%">
					<select size="1"  name="product_id" onchange="javascript:selectProductItem(this);" onkeydown="javascript:nextKeyPress(this)" style="width: 500px;"> 
							<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,16)%>
					</select> 
				</TD>
			</TR>
			<TR>
				<TD align="right"><font color="red">*</font><%=LocalUtilis.language("class.contractID",clientLocale)%>:</TD>
				<TD id="contract_bh_select" width="35%">
					<select size="1" name="contract_bh_1" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 240px">
						<OPTION value='-1'>��ѡ��</OPTION>
					</select>
				</TD>
				<TD align="right" width="15%"><%=LocalUtilis.language("class.navPrice",clientLocale)%>:</TD><!--��ǰ��ֵ-->
				<TD width="35%"><input readonly class="edline" name="nav_price" size="25" onkeydown="javascript:nextKeyPress(this)" value=""></TD>
			</TR>
			<TR>
				<TD align="right" width="15%"><font color="red">*</font><%=LocalUtilis.language("class.listID",clientLocale)%>:</TD>
				<TD id="list_id_select" width="35%">
					<select name="list_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 240px">
						<OPTION value='-1'>��ѡ��</OPTION>
					</select>
				</TD>				
				<TD align="right"><%=LocalUtilis.language("class.currency_name",clientLocale)%>:</TD><!--����-->
				<TD><input readonly class="edline" name="currency_name" size="25" onkeydown="javascript:nextKeyPress(this)" value=""></TD>
			</TR>
			<TR>
				<TD align="right"><font color="red">*</font><%=LocalUtilis.language("class.rg_money",clientLocale)%>:</TD><!--�Ϲ����-->
				<TD><input readonly class="edline" name="rg_money" size="25" onkeydown="javascript:nextKeyPress(this)" value=""></TD>

				<TD align="right"><font color="red">*</font><%=LocalUtilis.language("class.feeType",clientLocale)%>:</TD><!--�ɿʽ-->
				<TD><select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px">
						<%=Argument.getJkTypeOptions("")%>
					</select>
				</TD>
			</TR>
			<TR> 
				<TD align="right">�ѽɿ���:</TD><!--�Ϲ����-->
				<TD><input readonly class="edline" name="old_to_money" size="25" value=""></TD>
				<TD align="right" id="fee_money_name" style="display: none;"><%=LocalUtilis.language("class.feeAmount2",clientLocale)%>:</TD><!--���ý��-->
				<TD width="" id="fee_money_value" style="display: none;">
					<input name="fee_money" size="25" onkeydown="javascript:computeMonut(this);nextKeyPress(this);" onkeyup="javascript:showCnMoney2(this.value)" value="" >
					<span id="fee_money_cn" class="span">&nbsp;</span>
				</TD>
			</TR>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 id="jkTable" class="product-list">
			<TR id="tr_0">
				<td align="right" width="15%"><font color="red">*</font><%=LocalUtilis.language("class.toMoney3",clientLocale)%>:</td><!--�ɿ���-->
				<td width="35%">
					<input name="to_money_0" size="25" onkeydown="javascript:computeMonut(this);nextKeyPress(this);" onkeyup="javascript:showCnMoney(this.value)" value="">
					<span id="to_money_cn_0" class="span">&nbsp;</span>
				</td>
				<TD align="right" width="15%"><font color="red">*</font><%=LocalUtilis.language("class.dzDate",clientLocale)%>:</td><!--�ɿ�����-->
				<TD width="35%">
					<INPUT TYPE="text" id="jk_date_picker_0" NAME="jk_date_picker_0" class=selecttext value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"/>
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker_0,theform.jk_date_picker_0.value,this);" tabindex="14">
					<INPUT TYPE="hidden" id="jk_date_0" NAME="jk_date_0" value="">
			   </TD>
			</TR>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
			<TR>
				<TD align="right" width="15%"><%=LocalUtilis.language("class.description",clientLocale)%> :</TD><!--��ע-->
				<TD colspan="3"><textarea rows="2" name="summary" cols="70" onkeydown="javascript:nextKeyPress(this)" ></textarea></TD>
			</tr>
			</TR>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
			<TR>
				<TD width="15%" align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:<!-- ��Ӹ��� --></TD>
    			<TD width="85%"><input type="file" name="file_info" size="60">&nbsp;
            		<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--ѡ�񸽼��ļ�-->
            	</TD>
            </TR>
			<TR><TD width="15%" align="right"></TD>		
    			<TD colspan="3" align="left"><button type="button" onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--�����˴���Ӹ��฽��--></button></TD>
			</TR>
		</TABLE>
		<br/>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 >
			<TR>
				<TD align="right">
				<!--����-->
	            <button type="button"   class="xpbutton3"  accessKey=s  id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;
				<!--����-->
	            <button type="button"   class="xpbutton3" accessKey=b id="btnRepeat" name="btnCancel" onclick="javascript:disableAllBtn(true);op_return();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
				&nbsp;&nbsp;
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>