<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
try{
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
int isDk = Utility.parseInt(Utility.trimNull(request.getParameter("isDk")), 0);
Integer id = Utility.parseInt(request.getParameter("id"), new Integer(0));

IntrustEntCustomerLocal cust = EJBFactory.getIntrustEntCustomer();
IntrustEntCustomerLocal customer = EJBFactory.getIntrustEntCustomer();

String  crm_cust_no = Utility.trimNull(request.getParameter("crm_cust_no")); 
 
String prefix = request.getParameter("prefix");
String cust_code = Utility.trimNull(request.getParameter("cust_code"),null);
String cust_name = Utility.trimNull(request.getParameter("cust_name"),null);
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));
Integer ccust_type=Utility.parseInt(request.getParameter("customer_cust_type"),new Integer(0));

String customer_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"),"");

customer.setCust_type(ccust_type);
int zxflag = Argument.getSyscontrolValue("ISZXFLAG");
 
String cust_no = null;
String card_id = null;

//获得客户信息必填的信息
List requiredList = Argument.getRequiredItem(menu_id,"10001",new Integer(1));
List confirmList =  Argument.getRequiredItem(menu_id,"10001",new Integer(3));
requiredList.addAll(confirmList);

if ((cust_code != null || cust_name != null)){		
	cust.setCust_id(new Integer(0));
	cust.setCust_code(cust_code);
	cust.setCust_name(cust_name);
	cust.setOperator(input_operatorCode);
	cust.setBook(input_bookCode);
	cust.setIs_link(is_link);	
	cust.list();	
	cust.getNext();
}
if(id.intValue()>0)
{
	customer.setCust_id(id);
	customer.load();
}

String zyhy_type_text = Utility.trimNull(request.getParameter("zyhy_type_text"),"请选择");
String zyhy_type = Utility.trimNull(request.getParameter("zyhy_type"),customer.getVoc_type());

if(zyhy_type!=null && !"".equals(zyhy_type))
	customer.setVoc_type(zyhy_type);
%>

<HTML>
<HEAD>
<TITLE>企业客户选择</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<link href="<%=request.getContextPath()%>/ext2.0/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<BASE TARGET="_self">

<%if(language.equals("en")){ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_en.js"></SCRIPT>
<%}else{ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%} %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<SCRIPT LANGUAGE="javascript">

</script>
<BODY class="body" onkeydown="javascript:chachEsc(window.event.keyCode)" onload="javascript:<%if (customer.getIs_link()!=null && customer.getIs_link().intValue()==1){%>displayLinkMsg1();<%} %> 
optimizeForm(); " >
<form name="theform" method="post" action="customer_info2.jsp" >
<input type="hidden" name="prefix" value="<%=Utility.trimNull(prefix)%>">
<input type="hidden" name="task" value="<%=Utility.trimNull(request.getParameter("task"))%>">
<input type="hidden" name="crm_cust_no">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="isDk" value="<%=isDk%>">
<input type="hidden" name="cust_invest_ent_type" value="<%=Utility.trimNull(customer.getEnt_type())%>">
<input type="hidden" name="zyhy_type_text" value="">
<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
	<TBODY>
		<TR>
			<TD>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="32"><b>资料信息选择</b></td>
			</tr>
			<tr>
				<td>
				<hr size="1">
				</td>
			</tr>
		</TABLE>
			<table border="0" width="100%" cellspacing="5" cellpadding="3">
				<tr>
					<td width="15%" align="right">组织机构代码 :</td>
					<td width="18%"><input onkeydown="javascript:nextKeyPress(this)" type="text" id="keyword" name="cust_code" size="18" value="<%=Utility.trimNull(request.getParameter("cust_code"))%>"></td>
					<td width="10%" align="right">客户名称 :</td>
					<td width="17%"><input onkeydown="javascript:nextKeyPress(this)" type="text" id="keyword" name="cust_name" size="18" value="<%=Utility.trimNull(request.getParameter("cust_name"))%>"></td>
					<td width="20%">是否关联方 :<input class="flatcheckbox" onkeydown="javascript:nextKeyPress(this)" type="checkbox"  name="is_link" value="1" <%if(is_link.intValue()==1) out.print("checked");%>></td>
					<td width="20%"><button type="button"  class="xpbutton2" onclick="javascript:queryInfo();">查询 </button></td>
				</tr>
				
				<tr>
					<td align="right">搜索结果 :</td>
					<td colspan="5">
						<select onkeydown="javascript:nextKeyPress(this)" size="1" id="cust_id" name="cust_id" style="width: 320px" onchange="javascript: changeCustomer(this.value);">
							<option value="">请选择</option>								
							<%
							while (cust.getNext()){%>
								<option value="1$<%=cust.getCust_id()%>"><%=Utility.trimNull(cust.getCust_code()) + " - " + Utility.trimNull(cust.getCust_name())+"-"+ Utility.trimNull(cust.getCard_code())%></option>						
							<%}%>
						</select>
					</td>
				</tr>

				<tr>
					<td colspan="6">
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>


			<table border="0" width="100%" cellspacing="5" cellpadding="3">

				<tr>
					<td width="15%" align="right">客户类型 :</td>
					<td width="18%">						
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_cust_type_name" size="18" maxlength="20" value="<%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("个人");else if(customer.getCust_type().intValue()==2) out.print("机构");}%>">	
						<input type="hidden" name="customer_cust_type" value="<%=Utility.trimNull(customer.getCust_type()) %>">
					</td>
					<td width="10%" align="right">客户名称 :</td>
					<%if("".equals(customer_cust_name) || "list".equals(request.getParameter("task"))) {%>
					<td width="57%" colspan="3"><input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="50" value="<%=Utility.trimNull(customer.getCust_name())%>"></td>		
					<%}else{ %>	
					<td width="57%" colspan="3"><input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="50" value="<%=customer_cust_name%>"></td>
					<%} %>
                      <td>
						<input onkeydown="javascript:nextKeyPress(this)" type="hidden"  name="tax_registration_no" size="20" maxlength="25" value="<%=Utility.trimNull(customer.getTax_registration_no())%>">
					</td>
				</tr>
			</table>

			<table border="0" width="100%" cellspacing="5" cellpadding="3">
				<tr>
					<td width="15%" align="right">
						<%if(cust_id==null)	{ %>								
						   <% if(ccust_type.intValue()==1)  {%>
							  <%if(requiredList.contains("VOC_TYPE")) out.print("<font color='red'>*</font>");%>职业 :
						<%} else {%>	  
							  <%if(requiredList.contains("VOC_TYPE")) out.print("<font color='red'>*</font>");%>所属行业 :
						   <%}%>	   											
						<%}
						else {%>							
						<% if(customer.getCust_type().intValue()==1)  {%>
							  <%if(requiredList.contains("VOC_TYPE")) out.print("<font color='red'>*</font>");%>职业 :
						<%} else {%>	  
							  <%if(requiredList.contains("VOC_TYPE")) out.print("<font color='red'>*</font>");%>所属行业 :
						   <%}%>
						<%}%>	
					</td>
					<td width="85%">
		 				<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="30"  value="<%=Utility.trimNull(customer.getVoc_type_name())%>">
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="48"  value="<%=Utility.trimNull(customer.getVoc_type_name())%>">					
					</td>
				</tr>
				<tr>				
				<%if(customer.getCust_type()!=null && customer.getCust_type().intValue()==2){%>	
				
					<td width="15%" align="right">行业类别明细 :</td>
					<td width="85%">
							<div id="comboBoxTree"></div>																	
								<input readonly class='edline' <%if(customer.getType_code()==null||customer.getType_code()=="") out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_type_name" size="45" maxlength="" value="<%=Utility.trimNull(customer.getType_name()) %>">
					</td>	
				</tr>
				<%}else{%>
					<td width="15%"></td>
					<td width="35%"></td>
				</tr>
				<%} %>
			</table>

			<table border="0" width="100%" cellspacing="5" cellpadding="3">
				<tr>
					<td width="15%" align="right" style="display: none;">贷款卡号 :</td>
					<td width="18%" style="display: none;"><input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_card_id" size="20" maxlength="20" value="<%=Utility.trimNull(customer.getCard_id())%>"></td>	
<%if(customer.getCust_type()!=null){

	if(customer.getCust_type().intValue()==1){
		%>			
					<td width="10%"></td>
					<td width="17%"></td>
		
		<% 
	}
	else{
		%>			
				    <td width="10%" align="right">集团标志 :</td>
					<td width="17%" >
						<input type="radio" class="flatcheckbox"  name="customer_jt_flag" value="1" disabled class="flatcheckbox" <%if(customer.getJt_flag()!=null && customer.getJt_flag().intValue()==1) out.print("checked");if(customer.getCust_type().intValue()!=2) out.print("disabled");%> />是
						<input type="radio" class="flatcheckbox"  name="customer_jt_flag" value="2" disabled class="flatcheckbox" <%if(customer.getJt_flag()!=null && customer.getJt_flag().intValue()==2) out.print("checked");if(customer.getCust_type().intValue()!=2) out.print("disabled");%> />否
					</td>
		
		<% 		
	}

} else{

	%>
					<td width="10%"></td>
					<td width="17%"></td>		
	<%
	
}%>

					<td width="20%"></td>
					<td width="20%"></td>
				</tr>
 
				<tr>

					<td align="right" <%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("style='display:none'");else out.print("style='display:none'");}%> >组织机构代码 :</td>
					<td <%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("style='display:none'");else out.print("style='display:none'");}%> ><input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_cust_code" size="20" maxlength="20" value="<%=Utility.trimNull(customer.getCust_code())%>"></td>
					
					<td align="right"><%if(requiredList.contains("IS_LINK")) out.print("<font color='red'>*</font>");%>关联标志 :</td>
					<td>	
						<input type="radio" class="flatcheckbox" value="1" disabled <%if(customer.getIs_link()!=null && customer.getIs_link().intValue()==1) out.print("checked");%>
							id="customer_islink01" name="customer_is_link" /><label for="customer_islink01">是</label>
						<input type="radio" class="flatcheckbox" value="2"  disabled <%if(customer.getIs_link()!=null && customer.getIs_link().intValue()==2) out.print("checked");%>
							id="customer_islink02" name="customer_is_link" /><label for="customer_islink02">否</label>	
					</td>
					<td></td>
					<td></td>
				</tr>

				<tr id="linkoption" <%if(customer.getIs_link() !=null && customer.getIs_link().intValue()==1){%>style=""<%}else{%>style="display:none;"<%}%>>
				    <td  align="right">关联类型 :</td>
					<td colspan="5">
				    	<select  disabled onkeydown="javascript:nextKeyPress(this)" size="1" name="link_type" style="width: 250px">
							<%=Argument.getLinkTypeOptions(customer.getLink_type())%>
						</select>
				    </td>
				</tr>
 
				<tr id="linkoption2" <%if(customer.getIs_link() !=null && customer.getIs_link().intValue()==1){%>style=""<%}else{%>style="display:none;"<%}%>>
				    <td align="right">投资信托公司金额 :</td>
					<td>
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,link_gd_money_cn)" name="link_gd_money"   size="20" maxlength="60" value="<%=Format.formatMoney(customer.getLink_gd_money())%>">						
					</td>
					<td colspan="4"><span id="link_gd_money_cn" class="span"></span></td>
				</tr>

			</table>



			<table border="0" width="100%" cellspacing="5" cellpadding="3">
<%if(customer.getCust_type().intValue()==2){%>
				<tr id="gov_flag4">
					<td width="15%" align="right">政府行政区域 :</td>
         			<td colspan="5">
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="gov_prov_regional"   size="20"  value="<%=Utility.trimNull(customer.getGov_prov_regioal_name())%>">
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="gov_regional"   size="20"  value="<%=Utility.trimNull(customer.getGov_regioal_name())%>">
         			</td>
				</tr>
<%} %>
			<tr>
				<td  width="15%" align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型-->
					<td>
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getCard_type_name())%>">
						<%cust_id=customer.getCust_id();
						Integer cust_type=customer.getCust_type(); %>
						<!-- <button type="button"  class="xpbutton3" accessKey=b id="btnCard" name="btnCard" onclick="javascript:setCard();">附加</button> -->

					</td>	
					<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
					<td><input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="customer_card_code" size="30" maxlength="20" value="<%=Utility.trimNull(customer.getCard_code())%>"></td>	
			</tr>

			<tr <%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("style='display:none'");else out.print("style='display:'");}%>>
					<td align="right">
						<%=LocalUtilis.language("class.regisCapital",clientLocale)%> :</td><!--注册资本-->
					<td>
						<input readonly class='edline'  onkeydown="javascript:nextKeyPress(this)" name="regist_capital" size="20" maxlength="16" value="<%=Utility.trimNull(customer.getRegist_capital())%>">
					</td>
					<td align="right">
						<%=LocalUtilis.language("class.totalAssets",clientLocale)%> :</td><!--资产总额-->
					<td>
						<input readonly class='edline' onkeydown="javascript:nextKeyPress(this)" name="total_seset" size="20" maxlength="16" value="<%=Utility.trimNull(customer.getTotal_seset())%>">
					</td>
					<td align="right">负债总额 :</td>
					<td>
						<INPUT readonly class='edline' onkeydown="javascript:nextKeyPress(this)" NAME="fz_total" size="20" maxlength="16" value="<%=Utility.trimNull(customer.getFz_total())%>">
					</td>
				</tr>	
<!-- 以下不显示 -->		

				<tr style='display:none' id="customer_jt_cust_id_tr"><td colspan=6><select name="customer_jt_cust_id"></select></td></tr>
				<tr>
					<td align="right" style='display:none'  id="companytitle">登记注册类型 :</td>
					<td id="companycontent" style='display:none'>					
						<input name="customer_ent_type_name" value="/">						
						<input type="hidden" name="customer_jt_ls_flag" value="/"/><!-- 无关 -->
					</td>	

				</tr>
				<tr>
					<td align="right" style='display:none'>开户银行 :</td>
					<td style='display:none'>
						<input name="customer_bank_name" value="/">					
					</td>
					<td align="right" style='display:none'>支行名称 :</td>
					<td style='display:none'><input name="customer_bank_sub_name" value="/"/></td>
				
					<td align="right" style='display:none'>银行账号 :</td>
					<td style='display:none'><input name="customer_bank_acct" value="/"></td>
				</tr>
				<tr>
					<td align="right" style='display:none'>证件类型 :</td>
					<td style='display:none'>
						<input name="customer_card_type_name1" value="/">
					   <%
						cust_id=customer.getCust_id();
						 cust_type=customer.getCust_type();
						if(cust_type==null)
							cust_type=new Integer(2);
						%>				
						<select  name="customer_card_type1">
							<%=Argument.getCardTypeOptions2("0")%>
						</select>
					</td>	
					<td align="right" style='display:none'>证件号码 :</td>
					<td style='display:none'><input name="customer_card_code1" value="/"></td>	
					<td align="right" style='display:none'>信用级别 :</td>
					<td style='display:none'>
						<input name="customer_credit_level_name" value="/">
						<select name="customer_credit_level">
							<%=Argument.getCreditLevelOptions(customer.getCredit_level())%>
						</select>
					</td>
				</tr>
				<tr>
					<td style='display:none'></td>	
					<td style='display:none'></td>
					<td style='display:none'>注册地址 :</td>
					<td style='display:none'><input name="customer_reg_address" value="/"/></td>
					<td style='display:none'>注册邮编 :</td>
					<td style='display:none'><input name="customer_reg_postcode" value="/"/></td>
				</tr>
				<tr>
					<td style='display:none'>通讯地址 :</td>
					<td style='display:none'><input name="customer_address" value="/"/></td>
					<td style='display:none'>邮政编码 :</td>
					<td style='display:none'><input name="customer_postcode" value="/"/></td>
					<td style='display:none'>联系人 :</td>
					<td style='display:none'><input name="customer_link_man" value="/"/></td>
				</tr>
				<tr>
					<td style='display:none'>电话 :</td>
					<td style='display:none'><input name="customer_telphone" value="/"></td>
					<td style='display:none'>传真 :</td>
					<td style='display:none'><input name="customer_fax" value="/"></td>
					<td style='display:none'>Email:</td>
					<td style='display:none'><input name="customer_email" value="/"></td>
				</tr>
				<tr style='display:none'>
		            <td align="right">上市标志 :</td>
			        <td>
			       		<input type="checkbox" name="public_flag" value="1">
					</td>
				</tr>
    			<tr style='display:none' id="publicMsg">
		            <td align="right">上市市场 :</td>
					<td>
						<input name="country_name" value="/">
					  	<select name="seid_id">
							<%=Argument.getSeidOptions(Utility.trimNull(customer.getSeid_id()))%>
						</select>
					</td>
   					<td  align="right">上市名称 :</td>
					<td>
					  	<input name="stock_name" value="/">
					</td>
		            <td align="right">上市代码 :</td>
					<td>
					  	<input name="stock_code" value="/">
					</td>
 				</tr>
                <tr style='display:none'>
                	<td align="right">国别 :</td>
					<td>
						<input name="country_name" value="/">
						<select size="1" name="country">
							<%=Argument.getDictParamOptions(5105,customer.getCountry())%>   
						</select>
					</td>
               		<td align="right">特征 :</td>
					<td>
						<input name="jkr_type_name" value="">
					</td>
				</tr>
				<tr style='display:none'>
					<td valign="top" align="right">备注 :</td>
					<td colspan="4"><textarea name="customer_summary"></textarea></td>
					<td><input type="checkbox" name="complete_flag" value="2"></td>
				</tr>
<!-- 以上不显示 -->	
				<tr>
					<td align="right" colspan="6">
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:okInfo();"><%=LocalUtilis.language("message.ok2",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;<!--确定-->
							<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
							&nbsp;&nbsp;<!--取消-->
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<script type="text/javascript">
<!--
/*设置证件类型*/
function setCard()
{
	var customer_card_type = document.theform.customer_card_type.value;
	var customer_card_code = document.theform.customer_card_code.value;
	
	var ret_val = document.theform.ret_val.value;
	if(ret_val == "")
		ret_val = customer_card_type +"|"+customer_card_code+"|"+"|"+"|";
	var url = '<%=request.getContextPath()%>/investment/parameter/customer_info_card_new.jsp?customer_card_type='+customer_card_type+'&cust_type=<%=cust_type%>&ret_val='+ret_val+'&customer_card_code='+customer_card_code+'&isDk=<%=isDk%>';
	if(document.theform.task.value == "list" || document.theform.task.value == "")
		url = '<%=request.getContextPath()%>/investment/parameter/customer_info_card_info.jsp?customer_card_type='+customer_card_type+'&cust_type=<%=cust_type%>&ret_val='+ret_val+'&customer_card_code='+customer_card_code;
	var ret = showModalDialog(url, '', 'dialogWidth:600px;dialogHeight:550px;status:0;help:0');
	if(ret != null)
	{
		document.theform.ret_val.value = ret;
		var item = new Array();
		item = ret.split("|");
		for(var g=0; g<item.length; g++)
		{
			//如果本页的证件号没有添加，则将附加页面相同的证件类型的编号赋给主证件编号
			//if(customer_card_type == item[0] && customer_card_code == "")
			//{
				document.theform.customer_card_code.value = item[1];
			//}
		}
		var item1 = new Array();
		item1 = ret.split(",");
		for(var g=0; g<item1.length; g++)
		{
			if(item1[g].split("|")[0] == "210808")
				document.theform.customer_cust_code.value = item1[g].split("|")[1];
			if(item1[g].split("|")[0] == "210803")
				document.theform.tax_registration_no.value = item1[g].split("|")[1];
		}
		sl_update_ok();
	}
}
//-->
</script>
</form>
<script language="javascript">

function queryInfo()
{
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.submit();
}

function changeCustomer(cust_id)
{	
	if (cust_id != "")
	{
		var stringArray = cust_id.split('$');
		if(stringArray[0] == 1)
		{	
			document.theform.id.value = stringArray[1];
			document.theform.method = "get";
			document.theform.task.value = "list";
			document.theform.submit();
		}	
		if(stringArray[0] == 2)
		{
			document.theform.crm_cust_no.value = stringArray[1];
			document.theform.method = "get";
			document.theform.task.value = "list";
			document.theform.submit();
		}
	}else {
			document.theform.id.value = 0;
			document.theform.method = "get";
			document.theform.task.value = "list";
			document.theform.submit();
	}
}

function optimizeForm()
{
	if (document.theform.task.value == "list"){
		document.theform.cust_id.focus();
	}else{
		document.theform.cust_code.focus();}
}

function displayLinkMsg1()
{
     linkoption.style.display='';  
     linkoption2.style.display='';  
}

function okInfo()
{
	var v = new Array();
	var cust_id = document.theform.id.value;
	v[0] = cust_id;
	v[1] = document.theform.customer_cust_name.value;
	v[2] = document.theform.customer_cust_code.value;
	v[3] = document.theform.customer_ent_type_name.value;
	v[4] = document.theform.customer_bank_name.value;				
	v[5] = document.theform.customer_bank_acct.value;
	v[6] = document.theform.customer_card_id.value;
	v[7] = document.theform.customer_reg_address.value;
	v[8] = document.theform.customer_reg_postcode.value;
	v[9] = document.theform.customer_address.value;
	v[10] = document.theform.customer_postcode.value;
	v[11] = document.theform.customer_link_man.value;
	v[12] = document.theform.customer_telphone.value;
	v[13] = document.theform.customer_fax.value;
	v[14] = document.theform.customer_email.value;
	v[15] = document.theform.customer_summary.value;
	v[16] = document.theform.customer_bank_sub_name.value;
	v[17] = document.theform.customer_card_type_name.value;
	v[18] = document.theform.customer_card_code.value;
	v[19] = document.theform.customer_credit_level_name.value;
	if(document.theform.customer_is_link.checked)		
		v[20] = 1;
	else	
		v[20] = 2;
	v[21] = document.theform.customer_voc_type_name.value;
	v[22] = document.theform.cust_invest_ent_type.value;
    v[23] = document.theform.customer_cust_type.value;
	window.returnValue = v;
	
	window.close();
	
}
</script>
</BODY>
</HTML>
<%
cust.remove();
customer.remove();
}catch(Exception e){
	throw e;
}
%>
