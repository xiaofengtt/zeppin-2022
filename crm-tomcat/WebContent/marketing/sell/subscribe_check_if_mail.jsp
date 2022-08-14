<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ContractVO vo = new ContractVO();
ContractLocal contract = EJBFactory.getContract();
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer if_mail = Utility.parseInt(request.getParameter("if_mail"),new Integer(0));
Integer contract_type = Utility.parseInt(request.getParameter("contract_type"),new Integer(0));
String options="";

vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_bh);
vo.setCust_name(q_cust_name);
vo.setBook_code(new Integer(1));
//vo.setIf_mail(if_mail);
vo.setContract_type(contract_type);

if(Argument.getSyscontrolValue("PRODUCT_FLAG")==1){
	vo.setCheck_flag(new Integer(11));
	options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,0);
	options = options.replaceAll("\"", "'");
}else{
	vo.setCheck_flag(new Integer(1));
	options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,16);
	options = options.replaceAll("\"", "'");
}
vo.setCheck_flag(new Integer(0));
vo.setInput_man(input_operatorCode);
String[] totalColumn = {"RG_MONEY"};
IPageList contractList = null;
if(user_id.intValue() == 2)
	contractList = contract.getCheckContract(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,-1));
else
	contractList = contract.getCheckContract(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl += "&product_id="+product_id+"&contract_bh="+contract_bh +"&if_mail=" + if_mail +"&contract_type="+ contract_type;

contract.remove();
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
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

function modi_if_mail(serialno) {
	
	var url = 'subscribe_update_if_email.jsp?checkflag=1&serial_no=' + serialno;		
	var result = showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:400px;status:0;help:0');

	if(result){
		window.location.reload();
	}else{
		disableAllBtn(false);
		showWaitting(0);
	}
	
}



function refreshPage(){
	StartQuery();
}

function StartQuery(){
    disableAllBtn(true);
    location = 'subscribe_check_if_mail.jsp?page=1&pagesize=' + document.theform.pagesize.value 
    		 + '&product_id='+document.theform.product_id.value
    		 + '&contract_bh='+document.theform.contract_bh.value
 			 + '&q_cust_name='+document.theform.q_cust_name.value
			+ '&if_mail='+document.theform.if_mail.value
			+ '&contract_type='+document.theform.contract_type.value;
}

function op_check(){
	if(checkedCount(document.theform.serial_no) == 0) {
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ！");//请选定要审核的记录
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function showBenifiter(contract_id){
	disableAllBtn(true);
   location = "benifiter_check_list.jsp?contract_id=" + contract_id+"&product_id="+document.theform.product_id.value+ '&q_cust_name='+document.theform.q_cust_name.value;
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="subscribe_check_action.jsp">
<%@ include file="/includes/waiting.inc"%> 
<div id="queryCondition" class="qcMain" style="display:none;width:490px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td><input name="q_cust_name" value="<%=q_cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="15"/></td>
			 
			<td align=right><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
			<td> 
				<input type="text" name="contract_bh" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract_bh)%>" size="15">
			</td>
		</tr>
		<tr>
			<td align=right><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
			<td>
				<input type="text" name="productid" value="" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
				<button class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td>
			<td align="right">产品名称 :</td>
			<td>
				<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
				<button class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
			</td>
		</tr>
		<tr>
			<td align=right><%=LocalUtilis.language("class.productName",clientLocale)%> : </td><!--产品名称-->
			<td colspan=3 id="select_id">
				<%if(Argument.getSyscontrolValue("PRODUCT_FLAG")==1){%>	
					<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,0)%>
					</select>
				<%}else{%>
					<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,16)%>
					</select>
				<%}%>
			</td>
		</tr>

		<tr>
			<td align=right>合同分类 : </td>
			<td colspan="3">
				<select size="1" name="contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
					<option value="0" <%if(contract_type.intValue() == 0){%>selected<%}%> >请选择</option>
					<option value="1" <%if(contract_type.intValue() == 1){%>selected<%}%> >前台销售人员合同</option>
					<option value="2" <%if(contract_type.intValue() == 2){%>selected<%}%> >产品部门合同</option>
					<option value="3" <%if(contract_type.intValue() == 3){%>selected<%}%> >代销合同</option>
				</select>
			</td>
		</tr>

		<tr>
			<td align=right>合同是否寄回 : </td>
			<td colspan="3">
				<select size="1" name="if_mail" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
					<option value="0" <%if(if_mail.intValue() == 1){%>selected<%}%> >请选择</option>
					<option value="1" <%if(if_mail.intValue() == 1){%>selected<%}%> >是</option>
					<option value="2" <%if(if_mail.intValue() == 2){%>selected<%}%>>否</option>
				</select>
			</td>
		</tr>

		<tr>
			<td align="center" colspan=4>
				<!--确定-->
                <button class="xpButton3"  name="btnQuery" accessKey=o onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="4">
						<IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28">
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
				    <td align=right>
				    	<%if (input_operator.hasFunc(menu_id, 108)){%>
				    	<!--查询-->
                        <button class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
				    	<%}%>
				    	
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--合同编号-->
                    <td align="center" width="*"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td>
					<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
					<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
					<td align="center" width="60px" sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
					<td align="center" width="80px"><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--缴款日期-->
					<td align="center" width="80px"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称-->
					<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
					<td align="center" width="*">银行账户名称 </td>
					<td align="center" >受益级别</td><!--受益级别-->
					<td align="center" >收益级别</td><!--收益级别-->
					<td align="center" >合同是否寄回</td><!--合同是否寄回-->
					<%if(user_id.intValue() != 2) {%>
					<td align="center" >渠道类别</td><!--渠道类别-->
					<td align="center" >渠道名称</td><!--渠道名称-->
					<td align="center" >渠道合作方式</td><!--渠道合作方式-->
					<td align="center" >用款方是否关联</td><!--用款方是否关联-->
					<td align="center" >合同预计收益率</td><!--合同预计收益率-->
					<%} %>
					
					<td align="center" width="50px">修改</td>
				</tr>
<%
int iCurrent = 0;
Integer serial_no;
List rsList = null;
Map rowMap = null;
rsList = contractList.getRsList();
String prov_flag_name = "";
String if_mail_value = "";
for(int i=0;i<rsList.size();i++){
	rowMap = (Map)rsList.get(i);
	serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")),null);
	String prov_level_name = Utility.trimNull(rowMap.get("PROV_LEVEL_NAME"));
	int prov_flag = Utility.parseInt(Utility.trimNull(rowMap.get("PROV_FLAG")),0);
	String channel_type_name = Utility.trimNull(rowMap.get("CHANNEL_TYPE_NAME"));
	String channel_name = Utility.trimNull(rowMap.get("CHANNEL_NAME"));
	String channel_coopertype_name = Utility.trimNull(rowMap.get("CHANNEL_COOPERTYPE_NAME"));
	String product_name = Utility.trimNull(rowMap.get("PRODUCT_NAME"));
	String sub_product_name = Utility.trimNull(rowMap.get("SUB_PRODUCT_NAME"));
	String is_ykgl=Utility.trimNull(rowMap.get("IS_YKGL"));
	String xthtyjsyl=Utility.trimNull(rowMap.get("XTHTYJSYL"));
	int c_if_mail = Utility.parseInt(Utility.trimNull(rowMap.get("IF_MAIL")), new Integer(0));
	
	String temp="";
	if(is_ykgl.equals("1")){
		temp="是";
	}else{
		temp="否";
	}
	if(!"".equals(sub_product_name) && sub_product_name!=null)
			product_name = product_name+"("+sub_product_name+")";

	if(prov_flag==1)
		prov_flag_name = "优先";
	else if(prov_flag==2)
		prov_flag_name = "一般";
	else if(prov_flag==3)
		prov_flag_name = "劣后";
	
	if(c_if_mail==0)
		if_mail_value = "";
	else if(c_if_mail==1)
		if_mail_value = "是";
	else if(c_if_mail==2)
		if_mail_value = "否";
%>

				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name="serial_no" value="<%=rowMap.get("SERIAL_NO")%>" class="flatcheckbox"></td>
							<td width="90%" align="left"><%=Utility.trimNull(product_name)%></td>
						</tr>
					</table>
					</td>
					<td align="left" ><%=rowMap.get("CONTRACT_SUB_BH")%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("CUST_NAME"))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("CARD_ID"))%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)rowMap.get("RG_MONEY")))%></td>
					<td align="center" ><%=Format.formatDateCn((Integer)rowMap.get("JK_DATE"))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("BANK_NAME"))%><%=Utility.trimNull(rowMap.get("BANK_SUB_NAME"))%></td>
					<td align="left" ><%=Utility.ShowBankAcct(Utility.trimNull(rowMap.get("BANK_ACCT")))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("GAIN_ACCT"))%></td>
					<td align="left" ><%=prov_flag_name %></td>
					<td align="left" ><%=prov_level_name %></td>
					<td align="left" ><%=if_mail_value %></td>
					<%if(user_id.intValue() != 2) {%>
					<td align="left" ><%=Utility.trimNull(channel_type_name) %></td>
					<td align="left" ><%=Utility.trimNull(channel_name) %></td>
					<td align="left" ><%=Utility.trimNull(channel_coopertype_name) %></td>
					<td align="center" ><%=temp %></td>
					<td align="left" ><%=Utility.trimNull(xthtyjsyl) %></td>
					<%} %>	
					
					<td align="center" >
						<a href="javascript:disableAllBtn(true);modi_if_mail(<%=serial_no%>)">
	         				<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
	      				</a>
					</td>
				</tr>
<%
	iCurrent++;
}

for (int i=0; i < contractList.getBlankSize(); i++){%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<%if(user_id.intValue() != 2) {%>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<%} %>
					<td align="center" ></td>

				</tr>
<%}%>
				<tr class="trbottom">
                    <!--合计--><!--项-->
					<td class="tdh" align="center" ><b><%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=contractList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="right" ><%=Format.formatMoney(contractList.getTotalValue("RG_MONEY"))%></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" ></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<%if(user_id.intValue() != 2) {%>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<%} %>
					<td align="center" >-</td>
				</tr>
			</table>

			<br>

			<table border="0" width="100%">
				<tr valign="top">
					<td><%=contractList.getPageLink(sUrl)%></td>
				</tr>
			</table>

			</TD>
		</TR>
	</TABLE>
	</TD>

</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>