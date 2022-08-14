<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
IntrustContractLocal contract = EJBFactory.getIntrustContract();
IntrustCustomerInfoLocal customer = EJBFactory.getIntrustCustomerInfo();

Integer contract_id = Utility.parseInt(request.getParameter("serial_no"), null);
boolean bSuccess = false;
String service=request.getParameter("service");
int checkflag=Utility.parseInt(request.getParameter("checkflag"),0);
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
input_bookCode=new Integer(1);
contract.setSerial_no(contract_id);
contract.setInput_man(input_operatorCode);
contract.load();

String busi_id="120388";	
	%>
<%//以下内容来自get_contract_capital_info.inc
if(capital_flag!=null && capital_flag.intValue()==1)
{ 
	Object bhash = session.getAttribute("capitalinfolist"+busi_id); 
	Hashtable capitallistHtb = null;
	int iKey=0;	
	if (bhash == null)
		capitallistHtb = new Hashtable();
	else
		capitallistHtb = (Hashtable) bhash;
	 
	Object bfieldhash = session.getAttribute("capitalfieldlist"+busi_id);
	Hashtable capitalfieldlistHtb = null;
	if (bfieldhash == null)
		capitalfieldlistHtb = new Hashtable();
	else
		capitalfieldlistHtb = (Hashtable) bfieldhash;
	
	IntrustCapitalInfoLocal capitalinfo = EJBFactory.getIntrustCapitalInfo();	
	
	capitalinfo.setProduct_id(contract.getProduct_id());
	capitalinfo.setContract_bh(contract.getContract_bh());
	
	capitalinfo.setBook(input_bookCode);
	capitalinfo.setInput_man(input_operatorCode);
	capitalinfo.setSerial_no(capitalserialno);
	
	capitalinfo.list();
	while(capitalinfo.getNext())
	{		
		java.util.Properties capitalproperty = new java.util.Properties();
		iKey++;
		if(capitalinfo.getSerial_no()==null)
			capitalinfo.setSerial_no(new Integer(0));
		if(capitalinfo.getProduct_id()==null)
			capitalinfo.setProduct_id(new Integer(0));
		if(capitalinfo.getCapital_type()==null)
			capitalinfo.setCapital_type(new Integer(0));
		if(capitalinfo.getCapitaloper_type()==null)
			capitalinfo.setCapitaloper_type(new Integer(0)); 				
		capitalproperty.put("serial_no",capitalinfo.getSerial_no());
		capitalproperty.put("capital_name",Utility.trimNull(capitalinfo.getCapital_name()));
		
		capitalproperty.put("capital_use",Utility.trimNull(capitalinfo.getCapital_use()));
		capitalproperty.put("busi_id",Utility.trimNull(capitalinfo.getBusi_id()));
		capitalproperty.put("contract_bh",Utility.trimNull(capitalinfo.getContract_bh()));
		
		capitalproperty.put("product_id",capitalinfo.getProduct_id());
		capitalproperty.put("capital_type",capitalinfo.getCapital_type());
		capitalproperty.put("capital_type_name",Utility.trimNull(capitalinfo.getCapital_type_name()));
		capitalproperty.put("capitaloper_type",capitalinfo.getCapitaloper_type());
		capitalproperty.put("capitaloper_type_name",Utility.trimNull(capitalinfo.getCapitaloper_type_name()));
		capitalproperty.put("capital_manager",Utility.trimNull(capitalinfo.getCapital_manager()));
		capitalproperty.put("cust_id",capitalinfo.getCust_id());
		capitalproperty.put("start_date",capitalinfo.getStart_date());
		capitalproperty.put("end_date",capitalinfo.getEnd_date());
		
		capitalproperty.put("cust_name",Utility.trimNull(capitalinfo.getCust_name()));
		
		if(capitalinfo.getBtzr_cust_id()==null)
			capitalinfo.setBtzr_cust_id(new Integer(0));
		capitalproperty.put("btzr_cust_id",capitalinfo.getBtzr_cust_id());
		capitalproperty.put("zclb_bh",Utility.trimNull(capitalinfo.getZclb_bh()));
		
		if(capitalinfo.getPrice()==null)
			capitalinfo.setPrice(new java.math.BigDecimal(0)); 
		if(capitalinfo.getMoney()==null)
			capitalinfo.setMoney(new java.math.BigDecimal(0)); 
		if(capitalinfo.getMarket_price()==null)
			capitalinfo.setMarket_price(new java.math.BigDecimal(0)); 
		if(capitalinfo.getMarket_money()==null)
			capitalinfo.setMarket_money(new java.math.BigDecimal(0)); 
		if(capitalinfo.getAmount()==null)
			capitalinfo.setAmount(new java.math.BigDecimal(0)); 		
		if(capitalinfo.getPg_money()==null)
			capitalinfo.setPg_money(new java.math.BigDecimal(0));
		capitalproperty.put("price",Utility.trimNull(capitalinfo.getPrice()));
		
		capitalproperty.put("money",Utility.trimNull(capitalinfo.getMoney()));
		capitalproperty.put("market_price",capitalinfo.getMarket_price());
		capitalproperty.put("market_money",capitalinfo.getMarket_money());
		
		capitalproperty.put("pg_money",capitalinfo.getPg_money());
		
		capitalproperty.put("pg_method",Utility.trimNull(capitalinfo.getPg_method()));
		capitalproperty.put("amount",capitalinfo.getAmount());
		capitalproperty.put("insurance",Utility.trimNull(capitalinfo.getInsurance()));
		capitalproperty.put("summary",Utility.trimNull(capitalinfo.getSummary()));
		capitalproperty.put("unit",Utility.trimNull(capitalinfo.getUnit()));
		
		String keyword=Utility.trimNull(capitalinfo.getCapital_no())+"-"+iKey;
		capitalinfo.setCapital_no(keyword);
		
		capitalproperty.put("capital_no",keyword);
		capitallistHtb.put(Utility.trimNull(capitalinfo.getCapital_no()),capitalproperty);
		
		java.util.Properties otherproperty = new java.util.Properties();
		
		IntrustCapitalAddLocal capital = EJBFactory.getIntrustCapitalAdd();
		capital.setInclude_parent(new Integer(2));
		capital.setBookcode(input_bookCode);
		capital.setCapital_type(capitalinfo.getCapital_type());
		capital.load(input_operatorCode);
		
		while(capital.getNext())
		{
			IntrustCapitalAddLocal capital1 = EJBFactory.getIntrustCapitalAdd();
			capital1.setInput_man(input_operatorCode);
			capital1.setCi_serial_no(capitalinfo.getSerial_no());
			capital1.setField_caption(capital.getField_caption());
	
			capital1.setInput_man(input_operatorCode);
			capital1.listInfo();
			capital1.getNextInfo();	
			
			otherproperty. put(Utility.trimNull(capital1.getField_caption()),Utility.trimNull(capital1.getField_content()));
			capital1.remove();
		}
		capitalfieldlistHtb.put(Utility.trimNull(capitalinfo.getCapital_no()),otherproperty);
		capital.remove();	
	}
	session.setAttribute("capitalinfolist"+busi_id,capitallistHtb);		
	session.setAttribute("capitalfieldlist"+busi_id,capitalfieldlistHtb);
}	
//get_contract_capital_info.inc内容到此为止
%>
<%	

String period_unit="月";
IntrustProductLocal product=EJBFactory.getIntrustProduct();
	product.setProduct_id(contract.getProduct_id());
	product.load();
	period_unit=Argument.getProductUnitName(product.getPeriod_unit());
customer.setCust_id(contract.getCust_id());
customer.setInput_man(input_operatorCode);
customer.load();
String strMessage="保存成功!";
String returnWay="purchase3_check.jsp";
String strproduct_id=Utility.trimNull(request.getParameter("product_id"));
if (request.getMethod().equals("POST"))
{
	try{
		contract.setSerial_no(contract_id);
		
		if(checkflag==1)
		{
				returnWay="purchase3_check.jsp?page="+sPage+"&pagesize="+sPagesize+"&product_id="+strproduct_id;
				contract.setInput_man(input_operatorCode);
				contract.checkEntity();
				bSuccess=true;
				strMessage="审核通过!";
				
		}
		else
		{	
			returnWay="purchase3.jsp?page="+sPage+"&pagesize="+sPagesize+"&product_id="+strproduct_id;	
			contract.setProduct_id(new Integer(0));
			contract.setService_man(Utility.parseInt(request.getParameter("service_man"), null));
			contract.setLink_man(Utility.parseInt(request.getParameter("link_man"), new Integer(0)));
			contract.setCheck_man(input_operatorCode);
			contract.setQs_date(Utility.parseInt(request.getParameter("qs_date"), null));	
			contract.save();
			bSuccess =true;	
			service="1";
			strMessage="保存成功!";	
			
		}	
	}catch(BusiException e){
// 		throw new BusiException(e.getMessage());
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">location='"+returnWay+"';</script>");
		return;
	}

}


String sQuery=strproduct_id+"$ $ $";
String entity_price_cn = "";
if (contract.getEntity_price() != null)
	entity_price_cn = "(" + Utility.numToChinese(contract.getEntity_price().toString()) + ")";
String rg_money_cn = "";
if (contract.getRg_money() != null)
	rg_money_cn = "(" + Utility.numToChinese(contract.getRg_money().toString()) + ")";


	
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
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){
%>
	sl_alert('<%=strMessage%>');	
	location = '<%=returnWay%>';
<%}%>

function op_return(s)
{
	location = 'purchase3_check.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&sQuery='+s;
}

function validateForm(form)
{
	if(!sl_checkChoice(form.service_man, "客户经理"))	return false;
	if(!sl_checkDate(form.qs_date_picker,"签署日期"))	return false;
	syncDatePicker(form.qs_date_picker, form.qs_date);
	return sl_check_update();
}

function op_check()
{
	if (sl_check_pass())
	{disableAllBtn(true);
		document.theform.submit();
	}
}
function showCapitalDeatil(value,busi_id,capital_use)
{
	product_id=document.theform.productid.value;
	contractbh=document.theform.contract_bh.value;
	showModalDialog('../../investment/capital.jsp?readonly=1&busi_id=120388&capital_use='+capital_use+'&contract_bh='+contractbh+'&product_id='+product_id, '','dialogWidth:720px;dialogHeight:500px;STATUS=0;help=0;');
}
</script>
</HEAD>
<BODY class="BODY body-nox">

<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="purchase_info3_check.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=Utility.trimNull(contract.getSerial_no())%>">
<input type="hidden" name="productid" value="<%=Utility.trimNull(contract.getProduct_id())%>">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="checkflag" value="<%=checkflag%>">
<input type="hidden" name="product_id" value="<%=Utility.trimNull(request.getParameter("product_id"))%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td class="page-title"><b>认购信息</b></td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
			<tr>
				<td><br/></td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0 class="product-list">
			
			<tr>
				<td align="right">客户编号:</td>
				<td><INPUT readonly class="edline" name="cust_no" size="20" value="<%=customer.getCust_no()%>"></td>
				<td align="right">客户名称:</td>
				<td><INPUT readonly class="edline" name="cust_name" size="20" value="<%=Utility.trimNull(customer.getCust_name())%>"></td>
			</tr>
			<tr>
				<td align="right">证件类型:</td>
				<td><input readonly name="card_type_name" size="20" value="<%=Utility.trimNull(customer.getCard_type_name())%>" class="edline"></td>
				<td align="right">证件号码:</td>
				<td><input readonly name="card_id" size="20" class="edline" value="<%=Utility.trimNull(customer.getCard_id())%>"></td>
			</tr>
			<%if(customer.getCust_type().intValue()==1)  {%>
			<tr>
				<td align="right">年龄:</td>
				<td><input readonly name="age" size="20" class="edline" value="<%=Utility.trimNull(customer.getAge())%>"></td>
				<td align="right">性别:</td>
				<td><input readonly name="sex" size="20" value="<%=Utility.trimNull(customer.getSex_name())%>" class="edline"></td>
			</tr><%}%>
			<tr>
				<td align="right">手机:</td>
				<td><input readonly name="mobile" size="20" value="<%=Utility.trimNull(customer.getMobile())%>" class="edline"></td>
				<td align="right">手机2:</td>
				<td><input readonly name="bp" size="20" class="edline" value="<%=Utility.trimNull(customer.getBp())%>"></td>
			</tr>
			<tr>
				<td align="right">传真:</td>
				<td><input readonly name="fax" size="20" value="<%=Utility.trimNull(customer.getFax())%>" class="edline"></td>
				<td align="right">Email:</td>
				<td><input readonly name="e_mail" size="20" value="<%=Utility.trimNull(customer.getE_mail())%>" class="edline"></td>
			</tr>
			<tr>
				<td align="right">单位电话:</td>
				<td><input readonly name="o_tel" size="20" class="edline" value="<%=Utility.trimNull(customer.getO_tel())%>"></td>
				<td align="right">家庭电话:</td>
				<td><input readonly name="h_tel" size="20" class="edline" value="<%=Utility.trimNull(customer.getH_tel())%>"></td>
			</tr>			
			
			<tr>
				<td align="right">客户类别:</td>
				<td><input readonly name="cust_type_name" size="20" value="<%=Utility.trimNull(customer.getCust_type_name())%>" class="edline"></td>
			</tr>
<%if(customer.getCust_type().intValue()==2)  {%>			
			<tr>
				<td align="right">法人姓名:</td>
				<td><input readonly name="legal_man" size="20" value="<%=Utility.trimNull(customer.getLegal_man())%>" class="edline"></td>
				<td align="right">联系地址:</td>
				<td><input readonly name="legal_address" size="50" value="<%=Utility.trimNull(customer.getLegal_address())%>" class="edline"></td>
			</tr>
<%}%>			
			<tr>
				<td align="right">客户级别:</td>
				<td><input readonly name="cust_level_name" size="20" value="<%=Utility.trimNull(customer.getCust_level_name())%>" class="edline"></td>
				<td align="right">联系方式:</td>
				<td><input readonly name="touch_type_name" size="20" value="<%=Utility.trimNull(customer.getTouch_type_name())%>" class="edline"></td>
			</tr>
			<tr>
				<td align="right">邮寄地址:</td>
				<td><input readonly name="post_address" size="58" value="<%=Utility.trimNull(customer.getPost_address())%>" class="edline"></td>
				<td align="right">邮政编码:</td>
				<td><input readonly name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(customer.getPost_code())%>" class="edline"></td>
			</tr>
			<tr>
				<td align="right">备用地址:</td>
				<td><input readonly name="post_address2" size="58" value="<%=Utility.trimNull(customer.getPost_address2())%>" class="edline"></td>
				<td align="right">邮政编码:</td>
				<td><input readonly name="post_code2" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(customer.getPost_code2())%>" class="edline"></td>
			</tr>		
			<tr>
			<td colspan="4"><hr size="1"></td>
			</tr>
			
			<tr>
				<td align="right">合同编号:</td>
				<td><INPUT readonly class="edline" name="contract_bh" size="20" value="<%=contract.getContract_sub_bh()%>"></td>
				<td align="right">合同金额:</td>
				<td><input name="entity_price" size="20" class="edline" value="<%=Utility.trimNull(Format.formatMoney0(contract.getRg_money()))%>" onkeydown="javascript:nextKeyPress(this)"> <span id="entity_price_cn" class="span"><%=rg_money_cn%></span></td>
			</tr>
			<tr>
				<td align="right">产品名称:</td>
				<td><input readonly class="edline" name="product_name" size="40" readonly value="<%=contract.getProduct_name()%>"></td>
			</tr>
			<tr>
				<td align="right">财产名义价格:</td>
				<td><input name="rg_money" size="20" readonly class="edline" value="<%=Utility.trimNull(Format.formatMoney0(contract.getEntity_price()))%>" onkeydown="javascript:nextKeyPress(this)"> <span id="rg_money_cn" class="span"><%=entity_price_cn%></span></td>
				
				<td align="right">财产名称:</td>
				<td><input readonly class="edline" name="entity_name" size="40" readonly value="<%=contract.getEntity_name()%>"></td>
			</tr>
			
			<tr>	
			
				<td align="right">银行名称:</td>
				<td><input readonly class="edline" name="bank_name" size="40" value="<%=Utility.trimNull(contract.getBank_name())%><%=Utility.trimNull(contract.getBank_sub_name())%>"></td>
				<td align="right">财产类别:</td>
				<td><input readonly class="edline" name="entity_type_name" size="20" value="<%=Utility.trimNull(contract.getEntity_type_name())%>">
				</td>
				
			</tr>
			<tr>	
				<td align="right">银行帐号:</td>
				<td><input readonly class="edline" name="bank_acct" size="40" value="<%=Utility.trimNull(contract.getBank_acct())%>"></td>
			
				<td align="right">合同期限:</td>
				<td><input readonly class="edline" type="text" name="valid_period" size="20" value="<%=Utility.trimNull(contract.getValid_period())%>"><%=Utility.trimNull(period_unit)%></td>
			</tr>
			<tr>	
				<td align="right">客户经理:</td>
				<td>

				<input readonly class="edline" name="service_man" size="20" value="<%=Utility.trimNull(Argument.getOpName(contract.getService_man()))%>"></td>
				<td align="right">合同销售人员:</td>
				<td>

				<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(Argument.getOpName(contract.getLink_man()))%>"></td>
			</tr>
			<tr>
				<td align="right">备注:</td>
				<td><input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(contract.getSummary())%>" size="50"></td>
				<td align="right">签署日期:</td>
				<td>		
				<input readonly class="edline" name="qs_date" size="20" value="<%=Utility.trimNull(Format.formatDateCn(contract.getQs_date()))%>">
				</td>
			</tr>
			<tr>
				<td align="right">开始日期:</td>
				<td>					
					<INPUT TYPE="text" readonly class="edline"  NAME="start_date" value="<%=Utility.trimNull(Format.formatDateCn(contract.getStart_date()))%>">
				</td>
				<td align="right">结束日期:</td>
				<td>
					<INPUT TYPE="text" readonly class="edline" NAME="end_date" value="<%=Utility.trimNull(Format.formatDateCn(contract.getEnd_date()))%>">
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						

<%if (request.getParameter("view") == null && input_operator.hasFunc(menu_id, 103))
{%>
					<button type="button"  class="xpbutton4"   name="btnCheck" title="审核" onclick="javascript:op_check();">审核通过</button>
					&nbsp;&nbsp;&nbsp; <%}%>
						<button type="button"  class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%contract.remove();
customer.remove();
%>
