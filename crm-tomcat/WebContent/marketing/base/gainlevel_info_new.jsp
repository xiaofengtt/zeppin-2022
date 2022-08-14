<%@ page contentType="text/html; charset=GBK" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>

<%
try{
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
String iQuery = request.getParameter("iQuery");

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));	
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
BigDecimal exp_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("exp_rate")),new BigDecimal(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"),new Integer(0));	
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"),new Integer(0));	
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String prov_level_name = Utility.trimNull(request.getParameter("prov_level_name"));
BigDecimal lower_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("lower_limit")),new BigDecimal(0));
BigDecimal upper_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("upper_limit")),new BigDecimal(0));
String summary = Utility.trimNull(request.getParameter("summary"));
Integer s_subProductid = new Integer(0) ;

int flag =0 ;
boolean bSuccess = false;
String error_mess = "";
String display_select ="";
Integer asfund_flag = Utility.parseInt(request.getParameter("asfund_flag"),new Integer(0));	

GainLevelLocal local = EJBFactory.getGainLevel();
Integer product_id = new Integer(0);
Integer sProduct_id = new Integer(0);
String product_code = "";
String product_name = "";

if(serial_no.intValue() != 0 && !request.getMethod().equals("POST")){
	local.setSerial_no(serial_no) ;

	local.query() ;
	while(local.getNextLevel()){
		serial_no = local.getSerial_no() ;
		product_id = local.getProduct_id();
		product_code = local.getProduct_code();
		product_name = local.getProduct_name();
		sub_product_id = local.getSub_product_id();
		prov_flag = local.getProv_flag() ;
		prov_level = local.getProv_level();
		prov_level_name = local.getProv_level_name();
		lower_limit = local.getLower_limit();
		upper_limit = local.getUpper_limit();
		summary = local.getSummary() ;
		asfund_flag = local.getAsfund_flag();
		cust_type = local.getCust_type();

		sProduct_id = product_id ;
		s_subProductid = sub_product_id ;
 	 	display_select = "disabled";
	}
	flag = 1;

}

if("POST".equals(request.getMethod())){
	
	local.setProv_level(prov_level);
	local.setProv_level_name(prov_level_name);
	local.setExp_rate(exp_rate);
	local.setLower_limit(lower_limit);
	local.setUpper_limit(upper_limit);
	local.setSummary(summary) ;	
	local.setInput_man(input_operatorCode) ;
	local.setProv_flag(prov_flag) ;
	local.setCust_type(cust_type);

	try{
		if(serial_no.intValue()==0){//新增
			// product_id  格试: asfund_flag#product_id
			product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));	
			local.setProduct_id(product_id) ;
			local.setSub_product_id(sub_product_id);
	
			local.add();
			bSuccess = true ;
		}else{//修改
			local.setSerial_no(serial_no);
			
			local.update();
			bSuccess = true ;
		}
	}catch(Exception e){
		error_mess = e.getMessage() ;
	}
	
}
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
	tempArray = '<%=iQuery%>'.split('$');
	sl_alert("保存成功!");
	location = "gainlevel.jsp?page="+ tempArray[2] + "&pagesize=" + tempArray[3] +"&product_id="+tempArray[0]+"&sub_product_id="+tempArray[1]+"&prov_level="+tempArray[4]+"&prov_flag="+tempArray[5];
<%}else if(!error_mess.equals("")){%>
   	sl_alert('<%=error_mess%>');
<%}%>

window.onload = function(){
	getSubProductOptions(<%=asfund_flag%>+"#"+document.theform.product_id.value,<%=sub_product_id%>);
};

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
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				getSubProductOptions(<%=asfund_flag%>+"#"+prodid,'');//加载子产品
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}		
	}
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
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				getSubProductOptions(<%=asfund_flag%>+"#"+prodid,'');//加载子产品
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function validateForm(form){
	if(!sl_checkChoice(form.product_id,"产品"))	return false;
	if(!sl_checkDecimal(form.exp_rate, "预期收益率", 7, 4, 0)) return false; // 
	if(!sl_checkChoice(form.prov_flag,"收益优先级"))	return false;	
	if(!sl_checkChoice(form.prov_level,"收益级别"))	return false;
	if(!sl_checkDecimal(form.lower_limit, "份额下限", 16, 2, 1))		return false;
	if(!sl_checkDecimal(form.upper_limit, "份额上限", 16, 2, 1))		return false;
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

function o_exit(){
	location = "gainlevel.jsp?page="+ <%=sPage%> + "&pagesize=" + <%=sPagesize%>;
}

//查询条件 加载对应产品的子产品
function getSubProductOptions(value1,value2)
{	
	var v = value1.split("#");//alert(v[0]);
	if(v[1]!=0){
		if(<%=asfund_flag.intValue() %>==2 || v[0]==2){//收益级别核算单位为‘投资人’时不能选择子产品
			document.getElementById('subProduct_style').style.display = 'none';
			return ;
		}
		utilityService.getSubProductOptionS(v[1],value2,{callback: function(data){
			if(data=='<option value="">请选择</option>'){
				document.getElementById('subProduct_style').style.display = 'none';			
			}else if(<%=flag%>==1 && <%=sub_product_id.intValue()%>==0){
				document.getElementById('subProduct_style').style.display = 'none';
			}else 		document.getElementById('subProduct_style').style.display = '';
			$("subProductOptions").innerHTML = "<select name='sub_product_id' <%=display_select%> style='width:321px;'>"+data+"</select>"
		}});
	}else{
		document.getElementById('subProduct_style').style.display = 'none';
	}
}

function showCnMoney(value)
{
	temp = value;
	if (trim(value) == ""){
		to_money_cn.innerText = "(元)";
	}else{
		to_money_cn.innerText = "(" + numToChinese(temp) + ")";
	}
}

function showCnMoney2(value)
{
	temp = value;
	if (trim(value) == ""){
		to_money_cn2.innerText = " (元)";
	}else{
		to_money_cn2.innerText = "(" + numToChinese(temp) + ")";
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox"  leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="gainlevel_info_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no %>">
<input type=hidden name="iQuery" value="<%=iQuery %>">
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
						<table border="0" width="100%" cellspacing="3" cellpadding="3" class="product-list">
							<tr <% if(flag==1) out.print("style='display:none;'"); %> >
						    	<td align="right"  width="10%">产品编号:</td >
						        <td align="left"  colspan="3"  width="90%">
						        	<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="12">&nbsp;
						        	<button type="button"   class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
						        </td >
						    </tr>
							<tr>
							    <td align="right"><font color="red">*</font>产品选择:</td >
							    <td align="left" colspan="3">
							       <SELECT name="product_id" <%=display_select%> onkeydown="javascript:nextKeyPress(this)" onchange="javascript:getSubProductOptions('<%=asfund_flag %>#'+this.value,'');"  class="productname" style="width: 680px">
							       	<%if (serial_no.intValue()==0) { // 新建
										out.print(Argument.getProductListOptions(input_bookCode, sProduct_id,"",input_operatorCode,48));
									} else { // 编辑
										out.print("<option selected value=\""+sProduct_id+"\">"+product_code+"-"+product_name+"</option>");
									}%>
								   </SELECT>
							    </td>
							</tr>
							<tr id="subProduct_style" style="display:none;">
								<td align="right">子产品名称:</td>
								<td colspan="3" id="subProductOptions">
						
								</td>
							</tr>
							<%// 新建(编辑时不用显示) 
							if (serial_no.intValue()==0 ) { 							
							%>						
							<tr>
								<td align="right"><font color="red">*</font>预期收益率:</td>
								<td colspan="3">
									<input type="text" name="exp_rate"/>%									
								</td>
							</tr>
							<%}%>	
							<tr>
								<td align="right"  width="10%"><font color="red">*</font>收益优先级:</td>
								<td align="left" width="40%">
								
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
						        	<input type="text" maxlength="32" name="lower_limit" <%if(flag==0) {%> value="0" <%}else{%> value="<%=Format.formatMoney(lower_limit)%>" <%}%> onkeyup="javascript:showCnMoney(this.value)" onkeydown="javascript:nextKeyPress(this);" style="width:120px;">
									<span id="to_money_cn" class="span">&nbsp;(元)</span>
						        </td>
								<td align="right"><font color="red">*</font>份额上限:</td>
								<td width="" align="left" >
									<input type="text" maxlength="32" name="upper_limit" <%if(flag==0) {%> value="0" <%}else{%> value="<%=Format.formatMoney(upper_limit)%>" <%}%>  onkeyup="javascript:showCnMoney2(this.value)" onkeydown="javascript:nextKeyPress(this);" style="width:120px;">
									<span id="to_money_cn2" class="span">&nbsp;(元)</span>
								</td>	
						    </tr>
							<tr>
								<td width="" align="right">客户类别:</td>
								<td width="" align="left" colspan="3">
									<SELECT name="cust_type" style="width:120px;">
										<OPTION  value="0" >全部</OPTION>
										<%=Argument.getTableOptions2(2009,cust_type)%>
									</SELECT>
								</td>
							</tr>
							<tr>
								<td width="" align="right">备&nbsp;注:</td>
								<td width="" align="left" colspan="3"><TEXTAREA cols="132" rows="4" name="summary"><%=summary==null?"":summary %></TEXTAREA></td>
							</tr>
						</table>
						<BR>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}">保存(<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"   class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
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
<SCRIPT language="javascript">
//getSubProductOptions(<%=asfund_flag %>+"#"+<%=product_id%>,'');//处理子产品下拉列表延迟加载问题
</script>
<%
}catch(Exception e){
	throw e;
}
 %>
