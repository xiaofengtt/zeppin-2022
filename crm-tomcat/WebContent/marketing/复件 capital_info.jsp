<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
IntrustCapitalInfoLocal capitalinfo = EJBFactory.getIntrustCapitalInfo();

String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
input_bookCode=new Integer(1);
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
int readonly=Utility.parseInt(request.getParameter("readonly"),0);
int check_flag=Utility.parseInt(request.getParameter("check_flag"),1);

capitalinfo.setCust_id(cust_id);

String zclb_bh = Utility.trimNull(request.getParameter("zclb_bh"));
Integer to_serial_no=Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

String sReadonly="";
if((readonly==1 && to_serial_no.intValue()>0) && check_flag==2)
	sReadonly="readonly class='edline'";
else
    readonly=0;
    	
capitalinfo.setBusi_id(Utility.trimNull(request.getParameter("busi_id")));
capitalinfo.setCapital_use(Utility.trimNull(request.getParameter("capital_use"),null));
int edtflag=Utility.parseInt(request.getParameter("edtflag"),0);
int subflag = Utility.parseInt(request.getParameter("subflag"),0);	

String capital_no = Utility.trimNull(request.getParameter("capital_no"),"");
String query_capital_name=Utility.trimNull(request.getParameter("query_capital_name"));
String query_capital_no=Utility.trimNull(request.getParameter("query_capital_no"));
capitalinfo.setContract_bh(contract_bh);
capitalinfo.setProduct_id(product_id);
java.util.Properties otherproperty = new java.util.Properties();
String capitalOptions="";

if(to_serial_no.intValue()>0)
{
	capitalinfo.setInput_man(input_operatorCode);
	capitalinfo.setSerial_no(to_serial_no);
	capitalinfo.list();
	int iCurrent=0,iCount=0;
	capitalinfo.getNext();
	zclb_bh=capitalinfo.getZclb_bh();
}	
else
	capitalOptions=Argument.getCapitalNoOptions_intrust("","",input_operatorCode);

int iCount = 0;

IntrustEntCustomerLocal tzr = EJBFactory.getIntrustEntCustomer();

String btzr_cust_name="";
if(capitalinfo.getBtzr_cust_id()!=null)
{
  if(capitalinfo.getBtzr_cust_id().intValue()>0)
  {
  	tzr.setCust_id(capitalinfo.getBtzr_cust_id());
	tzr.load();
	btzr_cust_name = tzr.getCust_name();
}}	



String cust_name ="";

if(capitalinfo.getCust_id()!=null)
{	if(capitalinfo.getCust_id().intValue()>0)
	{
		tzr.setCust_id(capitalinfo.getCust_id());
		tzr.load();
		 cust_name = tzr.getCust_name();
	}
}		 


boolean bSuccess = false;

if(subflag==4)
{
	capitalinfo.setCapital_type(Utility.parseInt(request.getParameter("capital_type"),new Integer(0)));
	capitalinfo.setCapital_type_name(Utility.trimNull(request.getParameter("capital_type_name")));
	capitalinfo.setCapitaloper_type(Utility.parseInt(request.getParameter("capitaloper_type"),new Integer(0)));
	capitalinfo.setCapitaloper_type_name(Utility.trimNull(request.getParameter("capitaloper_type_name")));
	capitalinfo.setContract_bh(Utility.trimNull(request.getParameter("contract_bh")));
	capitalinfo.setProduct_id(Utility.parseInt(request.getParameter("product_id"),new Integer(0)));
}

if(capitalinfo.getCapital_type()!=null)
{
	IntrustCapitalAddLocal capital = EJBFactory.getIntrustCapitalAdd();
		
	capital.setInclude_parent(new Integer(2));
	capital.setBookcode(input_bookCode);
		
	capital.setCapital_type(capitalinfo.getCapital_type());
	if(capitalinfo.getCapital_type().intValue()>0)
		capital.load(input_operatorCode);
	if(otherproperty==null)
		otherproperty = new java.util.Properties();
	
	while (capital.getNext())
	{
			IntrustCapitalAddLocal capital1 = EJBFactory.getIntrustCapitalAdd();
			capital1.setCi_serial_no(capitalinfo.getSerial_no());
			capital1.setField_caption(capital.getField_caption());
	
			capital1.setInput_man(input_operatorCode);
			capital1.listInfo();
			capital1.getNextInfo();		
			otherproperty.put(Utility.trimNull(capital.getField_caption()),Utility.trimNull(capital1.getField_content()));
			capital1.remove();
	}
	capital.remove();
}

		
if (request.getMethod().equals("POST") && subflag==2)
{
	to_serial_no=Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
	
	String keyword=Utility.trimNull(request.getParameter("capitalno"));
	
		capitalinfo.setCapital_no(Utility.trimNull(request.getParameter("capital_no")));
		
		capitalinfo.setBusi_id(Utility.trimNull(request.getParameter("busi_id"),"120388"));
		capitalinfo.setContract_bh(Utility.trimNull(request.getParameter("contract_bh")));
		capitalinfo.setProduct_id(Utility.parseInt(request.getParameter("product_id"), new Integer(0)));
	
		capitalinfo.setCapital_name(Utility.trimNull(request.getParameter("capital_name")));
		capitalinfo.setCapital_use("191105");
		capitalinfo.setCapital_type(Utility.parseInt(request.getParameter("capital_type"),new Integer(0)));
		capitalinfo.setCapital_type_name(Utility.trimNull(request.getParameter("capital_type_name")));
		capitalinfo.setCapitaloper_type(Utility.parseInt(request.getParameter("capitaloper_type"),new Integer(0)));
		capitalinfo.setCapitaloper_type_name(Utility.trimNull(request.getParameter("capitaloper_type_name")));
	
		capitalinfo.setCapital_manager(Utility.trimNull(request.getParameter("capital_manager")));
		capitalinfo.setCust_id(Utility.parseInt(request.getParameter("cust_id"),new Integer(0)));
	
		capitalinfo.setCust_name(Utility.trimNull(request.getParameter("capital_owner")));
		capitalinfo.setPrice(Utility.parseDecimal(request.getParameter("price"),new java.math.BigDecimal(0)));
		capitalinfo.setMarket_price(Utility.parseDecimal(request.getParameter("market_price"),new java.math.BigDecimal(0)));
		capitalinfo.setStart_date(Utility.parseInt(request.getParameter("start_date"),new Integer(0)));
		capitalinfo.setEnd_date(Utility.parseInt(request.getParameter("end_date"),new Integer(0)));
		
		java.math.BigDecimal tempamount=Utility.parseDecimal(request.getParameter("amount"),new java.math.BigDecimal(0));
		java.math.BigDecimal tempmoney=tempamount.multiply(Utility.parseDecimal(request.getParameter("price"),new java.math.BigDecimal(0)));
		
		capitalinfo.setMoney(Utility.parseDecimal(request.getParameter("money"),tempmoney));
	
		capitalinfo.setMarket_money(Utility.parseDecimal(request.getParameter("market_money"),new java.math.BigDecimal(0)));
		capitalinfo.setAmount(Utility.parseDecimal(request.getParameter("amount"),new java.math.BigDecimal(0)));
		capitalinfo.setPg_money(Utility.parseDecimal(request.getParameter("pg_money"),new java.math.BigDecimal(0)));
		capitalinfo.setPg_method(Utility.trimNull(request.getParameter("pg_method")));
		capitalinfo.setInsurance(Utility.trimNull(request.getParameter("insurance")));
		capitalinfo.setSummary(Utility.trimNull(request.getParameter("summary")));
		capitalinfo.setUnit(Utility.trimNull(request.getParameter("unit")));
		capitalinfo.setBtzr_cust_id(Utility.parseInt(request.getParameter("btzr_cust_id"),null));
		
		capitalinfo.setInput_man(input_operatorCode);
		if(to_serial_no.intValue()>0)
		{
			capitalinfo.setSerial_no(to_serial_no);
			capitalinfo.save();
		}	
		else
			capitalinfo.append();
		to_serial_no=capitalinfo.getSerial_no();
				
		IntrustCapitalAddLocal capital2 = EJBFactory.getIntrustCapitalAdd();
		capital2.setInput_man(input_operatorCode);
		capital2.setCi_serial_no(to_serial_no);
		capital2.deleteAddInfo();
					
		String [] fieldNames=request.getParameterValues("field_name");
		String [] fieldValues=request.getParameterValues("field_value");
		
		if(fieldNames!=null)
		{
			for(int i=0;i<fieldNames.length;i++)
			{
				String fieldName=fieldNames[i];
				String fieldValue=fieldValues[i];
				capital2.setCi_serial_no(to_serial_no);
				capital2.setField_caption(fieldName);
				capital2.setField_content(fieldValue);
				capital2.appendInfo();
			}
		}
		bSuccess = true;
}


String price_cn="";
String amount_cn="";
String market_price_cn="";
String market_money_cn="";
String pg_money_cn="";

String money_cn="";
if(capitalinfo.getMoney()!=null)
	if(capitalinfo.getMoney().doubleValue()!=0)
		money_cn=Utility.numToChinese(capitalinfo.getMoney().toString());



if(capitalinfo.getPrice()!=null)
	if(capitalinfo.getPrice().doubleValue()!=0)
		price_cn=Utility.numToChinese(capitalinfo.getPrice().toString());

if(capitalinfo.getAmount()!=null)
	if(capitalinfo.getAmount().doubleValue()!=0)
		amount_cn=Utility.numToChinese(capitalinfo.getAmount().toString());

if(capitalinfo.getMarket_price()!=null)
	if(capitalinfo.getMarket_price().doubleValue()!=0)
		market_price_cn=Utility.numToChinese(capitalinfo.getMarket_price().toString());

if(capitalinfo.getMarket_money()!=null)
	if(capitalinfo.getMarket_money().doubleValue()!=0)
		market_money_cn=Utility.numToChinese(capitalinfo.getMarket_money().toString());

if(capitalinfo.getPg_money()!=null)
	if(capitalinfo.getPg_money().doubleValue()!=0)
		pg_money_cn=Utility.numToChinese(capitalinfo.getPg_money().toString());
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
</HEAD>
<SCRIPT LANGUAGE="javascript">

<%if (bSuccess){%>
  window.returnValue =1;
  window.close();
<%}%>

function validateForm(form)
{
	if(form.serial_no.value=='null' || form.serial_no.value=='0')
	{
		if(form.capital_no.value!='')
		{
			if(findCount(form.capital_no_options,form.capital_no.value)==1)
			{
				sl_alert('资产编号'+form.capital_no.value+'已经存在了，请重新输入!');
				form.capital_no.focus();
				return;
			}
		}	
	}
	if(!sl_check(form.capital_no, "资产编号", 10, 1))	return false;		
	if(!sl_check(form.cust_name, "资产所有者", 100, 1))	return false;		
	if(!sl_check(form.capital_type_name, "资产类型", 100, 1))	return false;
	
	if(!sl_check(form.capitaloper_type_name, "投资领域", 100, 1))	return false;
	if(!sl_check(form.capital_name, "资产名称", 100, 1))	return false;	
	if(!sl_check(form.cust_name, "资产所有者", 100, 0))	return false;	
	if(!sl_checkDecimal(form.pg_money, "资产评估价值", 13, 3, 0))	return false;
	if(!sl_check(form.capital_manager, "资产管理者", 100, 0))	return false;	
	if(!sl_checkDecimal(form.price, "入帐单价", 13, 3, 1))	return false;
	if(!sl_checkDecimal(form.money, "入帐价值", 13, 3, 1))	return false;
	
	if(!sl_checkDecimal(form.amount, "数量", 13, 3, 1))	return false;
	if(!sl_checkDecimal(form.market_price, "市场单价", 13, 3, 0))	return false;
	if(!sl_checkDecimal(form.market_money, "市场总价", 13, 3, 0))	return false;
	
	
	if(confirm("确认信息修改吗？"))
	{disableAllBtn(true);
		document.theform.subflag.value=2;
		form.submit();	
	}	
}

function setTreeValue(value)
{
	document.theform.subflag.value=4;
	document.theform.method = "get";
	document.theform.submit();			
}
function openTree()
{
	var capital_type_value=document.theform.capital_type_name.value;
	var url = "<%=request.getContextPath()%>/marketing/capital/capitaltype_select1.jsp?from_flag=1&capital_use=191105&leafonly=1&capital_type_name=" + capital_type_value;
	v = showModalDialog(url, '', 'dialogWidth:450px;dialogHeight:500px;status:0;help:0');

	if (v != null)
	{
		document.theform.capital_type_name.value = v[1];
		document.theform.capital_type.value = v[0];
			document.theform.subflag.value=4;
			document.theform.zclb_bh.value=v[3];
			document.theform.method = "get";
			//document.theform.submit();
		
	}		
}
function openTreeType()
{
	v = showModalDialog('<%=request.getContextPath()%>/marketing/capital/opertype_select1.jsp?leafonly=1&capitaloper_type_name=' + document.theform.capitaloper_type_name.value, '', 'dialogWidth:450px;dialogHeight:500px;status:0;help:0');
	if (v != null)
	{
		document.theform.capitaloper_type_name.value = v[1];
		document.theform.capitaloper_type.value = v[0];
	}
}
function getCustomerName(cust_id)
{
	v = showModalDialog('<%=request.getContextPath()%>/marketing/capital/customer_info.jsp?cust_id=0','','dialogWidth:640px;dialogHeight:500px;status:0;help:0;');
	if (v != null)
	{	
		document.theform.cust_name.value=v[1];
		document.theform.cust_id.value=v[0];
	}	
	return true;
}
function getCustomer2Name(cust_id)
{
	v = showModalDialog('/capital/customer_info.jsp?cust_id=0','','dialogWidth:640px;dialogHeight:500px;status:0;help:0;');
	if (v != null)
	{	
		document.theform.btzr_cust_name.value=v[1];
		document.theform.btzr_cust_id.value=v[0];
	}	
	return true;
}
function selectBusiID(value)
{
	document.theform.subflag.value=4;
	document.theform.submit();
}
function selectContractBh(value)
{
	//if (event.keyCode == 13)
	{
		document.theform.subflag.value=3;
		document.theform.method = "get";
		document.theform.submit();
	}
}
function selectProductID()
{
		document.theform.subflag.value=3;
		document.theform.submit();
}

function selectCpitalID(value)
{
	document.theform.subflag.value=3;
	document.theform.submit();
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
		for(i=0;i<document.theform.product_code.options.length;i++)
		{
			if(document.theform.product_code.options[i].text==value)
			{
				prodid=document.theform.product_code.options[i].value;
				break;
			}	
		}
		if(prodid!=0)
		{
			for(i=0;i<document.theform.product_id.options.length;i++)
			{
				if(document.theform.product_id.options[i].value==prodid)
				{
					document.theform.product_id.options[i].selected=true;
						break;
				}	
			}
		}
		else
		{
			sl_alert('输入的产品编号不存在!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}


function setPrice1(obj,obj2,obj3)
{
	if(obj.value>0 && obj3.value>0)
	{
		if(!sl_checkDecimal(obj,"市场单价", 14,3,0))
			return false;
		if(!sl_checkDecimal(obj3,"购买数量", 14,2,0))
			return false;
		obj2.value=obj.value*obj3.value;
		if(obj2.value.indexOf(".")!=-1){
		obj2.value=obj2.value.substr(0,obj2.value.indexOf(".")+4);}
	}		
}    

function setPrice2(obj,obj2,obj3)
{
	if(obj.value>0 && obj3.value>0)
	{
		if(!sl_checkDecimal(obj,"入账单价", 14,3,0))
			return false;
		if(!sl_checkDecimal(obj3,"购买数量", 14,2,0))
			return false;
		obj2.value=obj.value*obj3.value;
		if(obj2.value.indexOf(".")!=-1){
		obj2.value=obj2.value.substr(0,obj2.value.indexOf(".")+4);}
	}		
}


function setPrice3(obj,obj2,obj3,obj4,obj5)
{
	if(obj.value>0)
	{
		if(!sl_checkDecimal(obj3,"购买单价", 14,3,0))
			return false;
		if(!sl_checkDecimal(obj4,"市场单价", 14,2,0))	
			return false;
		obj2.value=obj.value*obj3.value;	
		obj5.value=obj.value*obj4.value;
		if(obj2.value.indexOf(".")!=-1)
		{
			obj2.value=obj2.value.substr(0,obj2.value.indexOf(".")+4);
		}
		if(obj5.value.indexOf(".")!=-1)
		{
			obj5.value=obj5.value.substr(0,obj5.value.indexOf(".")+4);
		}
	}
}   
</script>

<BODY class="BODY" >
<form name="theform" method="post" action="capital_info.jsp" >
<input type="hidden" name="subflag" value="<%=subflag%>">
<input type="hidden" name="readonly" value="<%=readonly%>">
<input type="hidden" name="serial_no" value="<%=capitalinfo.getSerial_no()%>">
<input type="hidden" name="capitalno" value="<%=capital_no%>">
<input type="hidden" name="zclb_bh" value="<%=zclb_bh%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="32"><b>资产信息</b></td>
			</tr>
			<tr>
				<td>
				<hr size="1">
				</td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
			<%if(readonly==2){%>
			<tr>
				<td align=left>
					
				</td>
				<td align="right">资产编号:
					<input  name="query_capital_no" size="10"  value="<%=Utility.trimNull(query_capital_no)%>">
				</td>
				<td align="right" colspan=2>资产名称:<input  name="query_capital_name" size="20"  value="<%=Utility.trimNull(query_capital_name)%>">
				&nbsp;&nbsp;		<button class="xpbutton2" onclick="javascript:selectContractBh();">查询</button>
				</td>
				
			</tr>
			<tr>
				<td colspan="4">
				<hr noshade color="#808080" size="1">
				</td>
			</tr>
			<tr>
				<td  align="right">*资产列表：</td>
				<td align=left colspan=3>
					<select size="1" name="to_serial_no"  onchange="javascript:selectCpitalID(this.value);" onkeydown="javascript:nextKeyPress(this)"    class="productname">
						<%=capitalOptions%>
					</select>
				</td>
			</tr>
			<%}%>
			<tr>
				<td align="right">*产品名称</td>
				<td align=left colspan=3>
				   <input type="text" readonly class='edline' maxlength="100" name="productname" value="<%=Utility.trimNull(Argument.getProductName(capitalinfo.getProduct_id()))%>" onkeydown="javascript:setProduct(this.value);" size="50">
					<select size="1" style="display:none" name="productid" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductID();"    class="productname">
						<%=Argument.getProductOptions3(input_bookCode,capitalinfo.getProduct_id(),input_operatorCode)%>
					</select>
					<select size="1" name="product_code" onkeydown="javascript:nextKeyPress(this)"  style="width=110;display:none">
							<%=Argument.getPreProductCodeOptions(input_bookCode, new Integer(0), input_operatorCode,0)%>
					</select>
					
					<input type="hidden" name="product_id" value="<%=capitalinfo.getProduct_id()%>">
				</td>
			</tr>
			<tr>
				<td align="right">*业务类别:</td>
				<td>
				<input readonly class='edline' name="busi_id_name" value="委托财产">
				</td>
				<td align="right">*合同编号</td>
				<td >
				<input  readonly class='edline'   name="contract_bh" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getContract_bh())%>">
				</td>
			</tr>
			
			<tr>	
				<td align="right">
				*资产类别名称:</td>
				<td align="left"> <input type="text" <%if(readonly==1) out.print("readonly class='edline'");%>  name="capital_type_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getCapital_type_name())%>">
				<%if(readonly==0){%>
				<input type="hidden" name="capital_type" value="<%=capitalinfo.getCapital_type()%>">
				&nbsp;<button class="xpbutton3" accessKey=b  name="btnBrowse" onclick="javascript:openTree();">浏览(B)...</button>
				<%}%>
				</td>
				<td align="right">
				*投资领域:</td>
				<td align="left"> <input type="text" <%if(readonly==1) out.print("readonly class='edline'");%> name="capitaloper_type_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getCapitaloper_type_name())%>">
				<%if(readonly==0){%>
				<input type="hidden" name="capitaloper_type" value="<%=capitalinfo.getCapitaloper_type()%>">
				&nbsp;
				
				<button class="xpbutton3" accessKey=b  name="btnBrowse" onclick="javascript:openTreeType();">浏览(B)...</button>
				<%}%>
				</td>
			</tr>
			<tr>
				<td align="right" >*资产编号:</td>
				<td ><input type="text" name="capital_no"  <%if(readonly==1) out.print("readonly class='edline'");%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getCapital_no())%>">
				<select size="1" name="capital_no_options"  style="display:none" onkeydown="javascript:nextKeyPress(this)"    class="productname">
						<%=capitalOptions%>
					</select>
				</td>
				<td align="right" >*资产名称:</td>
				<td ><input type="text" name="capital_name" <%if(readonly==1) out.print("readonly class='edline'");%> size="40" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getCapital_name())%>">
				</td>
			</tr>
			
			<tr>	
				<td align="right">*资产所有者:</td>
				<td  colspan=3>
				<input readonly class="customernameline" <%if(readonly==1) out.print("readonly class='edline'");%> onkeydown="javascript:nextKeyPress(this)" name="cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(capitalinfo.getCust_name())%>">
				<%if(readonly==0){%>
				<input type="hidden" name="cust_id" value="<%=Utility.trimNull(capitalinfo.getCust_id())%>">
				<button class="xpbutton3" onclick="javascript:getCustomerName(document.theform.cust_id.value);">请选择...</button>
				<%}%>
				&nbsp;&nbsp;										
				</td>
			</tr> 
			<%if(zclb_bh!=null && zclb_bh.length()>=4){%>
			<%if("1401".equals(zclb_bh.substring(0,4))){%>
			<tr>	
				<td align="right">被投资人:</td>
				<td  colspan=3>
				<input readonly class="customernameline" <%if(readonly==1) out.print("readonly class='edline'");%> onkeydown="javascript:nextKeyPress(this)" name="btzr_cust_name" size="20" maxlength="60" value="<%=Utility.trimNull(btzr_cust_name)%>">
				<%if(readonly==0){%>
				<input type="hidden" name="btzr_cust_id" value="<%=Utility.trimNull(capitalinfo.getBtzr_cust_id())%>">
				<button class="xpbutton3" onclick="javascript:getCustomer2Name(document.theform.btzr_cust_id.value);">请选择...</button>
				<%}%>
				&nbsp;&nbsp;										
				</td>
			</tr> 
			<%}}%>
			<tr>	
				<td align="right">资产评估价值:</td>
				<td><input name="pg_money" size="20" <%if(readonly==1) out.print("readonly class='edline'");%> value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getPg_money()))%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,pg_money_cn)"> <span id="pg_money_cn" class="span"><%=pg_money_cn%></span></td>
				<td align="right">资产评估方法:</td>
				<td><input type="text" name="pg_method" <%if(readonly==1) out.print("readonly class='edline'");%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getPg_method())%>"> </td>
				
			</tr><tr>
			<tr>
				<td align="right">资产管理者:</td>
				<td >
				<input  type="text" <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="capital_manager" size="20" maxlength="60" value="<%=Utility.trimNull(capitalinfo.getCapital_manager())%>">
				</td>
				<td align="right">*数量:</td>
				<td >
				<input name="amount" size="15" <%=sReadonly%> value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getAmount()))%>" onkeydown="javascript:nextKeyPress(this)" onblur="javascript:setPrice3(this,document.theform.money,document.theform.price,document.theform.market_price,document.theform.market_money)">
				单位:<input name="unit" size="10" <%=sReadonly%> value="<%=Utility.trimNull(capitalinfo.getUnit())%>" onkeydown="javascript:nextKeyPress(this)" ></td>
				
			</tr>
			
			<tr>	
				<td align="right">*入帐单价:</td>
				<td><input name="price" size="20" <%=sReadonly%> value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getPrice()))%>" onblur="javascript:setPrice2(this,document.theform.money,document.theform.amount)" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,price_cn)"> <span id="price_cn" class="span"><%=price_cn%></span></td>
					<td align="right">市场单价:</td>
				<td><input name="market_price" <%=sReadonly%> size="20" value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getMarket_price()))%>" onblur="javascript:setPrice1(this,document.theform.market_money,document.theform.amount)" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_price_cn)"> <span id="market_price_cn" class="span"><%=market_price_cn%></span></td>
			
			</tr>
			<tr>
				<td align="right">*入帐价值:</td>
				<td><input name="money" size="20" <%=sReadonly%> value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getMoney()))%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,money_cn)"> <span id="money_cn" class="span"><%=money_cn%></span>
					</td>
				<td align="right">市场总价:</td>
				<td><input name="market_money" <%=sReadonly%> size="20" value="<%=Utility.trimNull(Format.formatMoney(capitalinfo.getMarket_money()))%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,market_money_cn)"> <span id="market_money_cn" class="span"><%=market_money_cn%></span>
					</td>

			</tr>
			
			<tr>
				<td align="right">资产参加保险情况:</td>
				<td><input type="text" name="insurance" <%if(readonly==1) out.print("readonly class='edline'");%> size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getInsurance())%>"></td>
				<tr>
				<td align="right">备注:</td>
				<td colspan="3"><input type="text" <%if(readonly==1) out.print("readonly class='edline'");%> name="summary" size="58" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(capitalinfo.getSummary())%>"></td>
			</tr>
		<%
if(otherproperty!=null)
{		
		int iIndex = 0;
		int arraySize=otherproperty.size();
	
	
	Object[] keysArray = new Object[arraySize];
	
	java.util.Enumeration elementsFild = otherproperty.propertyNames();
	
	while (elementsFild.hasMoreElements())
	{
		keysArray[iIndex] = elementsFild.nextElement();
		iIndex++;
	}

//elementsFild.

if(otherproperty.size()>0)
{
%>	
			<tr>
				<td colspan=4 align="left"><b>资产自定义信息</b></td>
			</tr>	
			<tr>
			<td colspan=4>
			
			<table id="table3" border="0" cellspacing="1" cellpadding="2" bgcolor="#000000" width="100%">
					<tr class="trh">
						<td  align="center" height="25" >要素名称</td>
						<td  align="center" height="25" >要素值</td>
						
					</tr>
			<%
iCount=0;

for (iIndex = arraySize-1; iIndex >= 0; iIndex--, iCount++)
{
	String fieldName=(String)keysArray[iIndex];

	iCount++;
	String fieldValue=(String)otherproperty.get(fieldName);
	
%>
					<tr class="tr0">
						<td  align="center" height="25"><input readonly class="ednoline" type="text" name="field_name" size="28" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(fieldName)%>"></td>
						<td align="center" height="25"><input <%if(readonly==1) out.print("readonly class='ednoline'");%> type="text" name="field_value" size="80" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(fieldValue)%>">
						</td>
					</tr>
					<%
					}%>
				</table>	
			</td>
			
			</tr>
<%
}
}
%>			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<%if(readonly==0){%>
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);">确定(<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<button class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
</TABLE>
</form>
</BODY>

</HTML>
<%
capitalinfo.remove();
%>