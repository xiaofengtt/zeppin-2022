<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);
ContractVO vo = new ContractVO();
ContractLocal contract =EJBFactory.getContract();
vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setBook_code(new Integer(1));
vo.setInput_man(input_operatorCode);
String[] totalColumn = {"RG_MONEY"};
IPageList contractList = contract.getRecheckContract(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl+"&product_id="+product_id+"contract_sub_bh="+contract_sub_bh;
status=16;

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.recoveryContract",clientLocale)%> </TITLE><!--资产合同审核恢复-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function refreshPage()
{
	StartQuery();
}


function StartQuery()
{
	disableAllBtn(true);
    location = 'subscribe_recheck_list.jsp?page=1&pagesize=' + document.theform.pagesize.value
    					+'&product_id='+document.theform.product_id.value
    					+"&contract_sub_bh="+document.theform.contract_sub_bh.value;
}

function op_check()
{
	if(checkedCount(document.theform.selectbox) == 0)
	{
		sl_alert('<%=LocalUtilis.language("message.restoredTip",clientLocale)%> ！');//请选定要恢复的记录
		return false;
	}
	if (confirm('<%=LocalUtilis.language("message.restoreTip",clientLocale)%> ？'))//确定要恢复吗
	{disableAllBtn(true);
		waitting.style.visibility='visible';
		document.theform.submit();
	}	
}
window.onload = function(){
initQueryCondition()};

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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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
			document.theform.contract_sub_bh.value="";
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
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>	
<form name="theform" method="POST" action="subscribe_recheck_action.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
		<td align=right><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td >
			<input maxlength=24 type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="28" value="<%=contract_sub_bh%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td ><!--产品编号-->
		<td align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);"></button>
		</td >
		<td align="right">产品名称 :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
		<td align="left" colspan=3 id="select_id">
			<SELECT name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<!--确认-->
        <td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button></td>
	</tr>
</table>

</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TR>
		<TD vAlign=top align=left width="100%">
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="6" class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
				    <tr>
				    	<td align=right>
				    		<div class="btn-wrapper">
				     		<!--查询-->
                            <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
								<!--恢复审核-->
                                <button type="button"  class="xpbutton5" name="btnCheck" title='<%=LocalUtilis.language("message.recoveryAudit",clientLocale)%>' onclick="javascript:op_check();"><%=LocalUtilis.language("message.recoveryAudit",clientLocale)%> </button>
				    		</div>
				    		<br/>
				    	</td>
				    </tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
				<tr class="trh">
					<!--合同编号-->
                    <td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.selectbox,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.contractID",clientLocale)%> </td>
					<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
					<td align="center" ><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
					<td align="center" ><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
					<td align="center" ><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--缴款日期-->
					<td align="center" ><%=LocalUtilis.language("class.paymentBank",clientLocale)%> </td><!-- 缴款银行 -->
					<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
					<td align="center" >银行账户名称</td>
					<td align="center" >受益级别</td><!--受益级别-->
					<td align="center" >收益级别</td><!--收益级别-->
					<td align="center" >渠道类别</td><!--渠道类别-->
					<td align="center" >渠道名称</td><!--渠道名称-->
					<td align="center" >渠道合作方式</td><!--渠道合作方式-->
					<td align="center" >用款方是否关联</td><!--用款方是否关联-->
					<td align="center" >合同预计收益率</td><!--合同预计收益率-->
				</tr>
<%int iCurrent = 0;
List rsList = null;
Map rowMap = null;
rsList = contractList.getRsList();
String sub_product_name = "";    
String prov_flag_name = "";
for(int i=0;i<rsList.size();i++){
rowMap = (Map)rsList.get(i);
sub_product_name = Utility.trimNull(rowMap.get("SUB_PRODUCT_NAME"));
String prov_level_name = Utility.trimNull(rowMap.get("PROV_LEVEL_NAME"));

int prov_flag = Utility.parseInt(Utility.trimNull(rowMap.get("PROV_FLAG")),0);
String channel_type_name = Utility.trimNull(rowMap.get("CHANNEL_TYPE_NAME"));
String channel_name = Utility.trimNull(rowMap.get("CHANNEL_NAME"));
String channel_coopertype_name = Utility.trimNull(rowMap.get("CHANNEL_COOPERTYPE_NAME"));

String is_ykgl=Utility.trimNull(rowMap.get("IS_YKGL"));
String xthtyjsyl=Utility.trimNull(rowMap.get("XTHTYJSYL"));
	
String temp="";

	if(is_ykgl.equals("1")){
		temp="是";
	}else{
		temp="否";
	}

if(prov_flag==1)
	prov_flag_name = "优先";
else if(prov_flag==2)
	prov_flag_name = "一般";
else if(prov_flag==3)
	prov_flag_name = "劣后";
		
if(sub_product_name!=null&&!"".equals(sub_product_name))
	sub_product_name = "("+sub_product_name+")";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name="selectbox" value="<%=rowMap.get("SERIAL_NO")%>" class="flatcheckbox"></td>
							<td width="90%" align="left"><%=rowMap.get("CONTRACT_SUB_BH")%></td>
						</tr>
					</table>
					</td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("PRODUCT_NAME"))%><%=sub_product_name %></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("CUST_NAME"))%></td>
					<td align="center" ><%=Utility.trimNull(rowMap.get("CARD_ID"))%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)rowMap.get("RG_MONEY")))%></td>
					<td align="center" ><%=Format.formatDateCn((Integer)rowMap.get("JK_DATE"))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("BANK_NAME"))%><%=Utility.trimNull(rowMap.get("BANK_SUB_NAME"))%></td>
					<td align="center" ><%=Utility.trimNull(rowMap.get("BANK_ACCT"))%></td>
					<td align="center" ><%=Utility.trimNull(rowMap.get("GAIN_ACCT"))%></td>
					<td align="left" ><%=prov_flag_name %></td>   
					<td align="left" ><%=prov_level_name %></td>  
					<td align="left" ><%=Utility.trimNull(channel_type_name) %></td>
					<td align="left" ><%=Utility.trimNull(channel_name) %></td>
					<td align="left" ><%=Utility.trimNull(channel_coopertype_name) %></td>
					<td align="center" ><%=temp %></td>
					<td align="left" ><%=Utility.trimNull(xthtyjsyl) %></td>					

				</tr>
<%iCurrent++;
}
for (int i=0; i < contractList.getBlankSize(); i++)
{
%>
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
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
<%iCurrent++;}
%>
				<tr class="trbottom">
                    <!--合计--><!--项-->
					<td class="tdh" align=left" colspan="1" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=contractList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%>&nbsp;&nbsp; </b></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" ><%=Format.formatMoney(contractList.getTotalValue("RG_MONEY")) %></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
				</tr>
				</table>
				<BR>
				<table border="0" width="100%" cellspacing="1" class="page-link">
					<tr valign="top">
						<td><%=contractList.getPageLink(sUrl)%></td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%contract.remove();%>
