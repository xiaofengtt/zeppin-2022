<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//页面传递变量
//getTransactionCustomer2 方法传入信息
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
String prefix = Utility.trimNull(request.getParameter("prefix"));
int readonly=Utility.parseInt(Utility.trimNull(request.getParameter("readonly")),0);
String task=Utility.trimNull(request.getParameter("task"));//操作任务

//客户选择 条件信息
String q_cust_no = Utility.trimNull(request.getParameter("q_cust_no"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_card_id = Utility.trimNull(request.getParameter("q_card_id"));
String q_vip_card_id = Utility.trimNull(request.getParameter("q_vip_card_id"));
String q_hgtzr_bh = Utility.trimNull(request.getParameter("q_hgtzr_bh"));
Integer q_is_link = Utility.parseInt(request.getParameter("q_is_link"), new Integer(0));

//辅助变量
StringBuffer list = Argument.newStringBuffer();
String sReadonly = "readonly class='edline'";
String sDisabled= "disabled='disabled'";
Map map = null;
List rsList = null;//返回结果集
//单个值
Map map_single = null;
List rsList_single = null;//返回结果集

//获取对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

if(task.equals("list")){
	// 搜索功能
	vo.setBook_code(new Integer(1));
	vo.setInput_man(input_operatorCode);
	vo.setCust_no(q_cust_no);
	vo.setCust_name(q_cust_name);
	vo.setCard_id(q_card_id);
	vo.setVip_card_id(q_vip_card_id);
	vo.setHgtzr_bh(q_hgtzr_bh);
	vo.setIs_link(q_is_link);
	
	rsList = customer.queryByCustNo(vo);	
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </TITLE><!--客户信息-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript">

function queryInfo(){
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.action = "customer_info2.jsp";
	disableAllBtn(true);
	document.theform.submit();
}

function changeCustomer(){	
	document.theform.cust_id.value = document.theform.id.value;
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.action = "customer_info2.jsp";
	
	document.theform.submit();
}

function okInfo(){
	var v = new Array();
	
	v[0] = document.theform.customer_cust_name.value;
	v[1] = document.theform.customer_cust_type_name.value;
	v[2] = document.theform.customer_card_id.value;
	v[3] = document.theform.customer_h_tel.value;
	v[4] = document.theform.customer_mobile.value;
	v[5] = document.theform.customer_post_address.value;
	v[6] = document.theform.customer_post_code.value;
	
	if(document.theform.readonly.value==1){
		v[7] = <%=cust_id%>;
	}
	else{
		v[7] = document.theform.cust_id.value;
	}
	
	v[8] = document.getElementsByName("customer_service_man_value")[0].value;	
	v[9] = document.getElementsByName("customer_service_man")[0].value;
	v[10]=document.theform.customer_o_tel.value;
	v[11]=document.theform.customer_bp.value;
	
	if(document.theform.customer_is_link.checked){
		v[12] = 1;
	}
	else{
		v[12] = 0;
	}
	
	v[13] = document.getElementsByName("customer_cust_type")[0].value;
	v[14] = document.getElementsByName("customer_cust_on")[0].value;
	v[15] = document.getElementsByName("customer_card_type")[0].value;	
	v[16] = document.getElementById("customer_legal_man").value;	
	v[17] = document.getElementById("customer_contact_man").value;
	v[18] = document.getElementsByName("customer_e_mail")[0].value;
	
	window.returnValue = v;		
	window.close();
}

function validateForm(form){		
		okInfo();		
}

</SCRIPT>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">

<input type="hidden" name="prefix" value="<%=Utility.trimNull(prefix)%>">
<input type="hidden" name="task" value="<%=task%>">
<input type="hidden" name="is_dialog" value="1">
<input type="hidden" name="readonly" value="<%=readonly%>">
<input type="hidden" name="crm_cust_no">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="strvalue"> 
<input type="hidden" name="strserial"> 

<div align="left">
	<br>
	<div>
		<!--客户信息-->
        &nbsp;&nbsp;<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="32"><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> </b>
	</div>

	<div>
		<table border="0" width="100%" cellspacing="0" cellpadding="1">
			<tr<%if(readonly==1) out.print("style='display:none'");%>>
				<td align=right><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td>
                    <!--客户如果是个人，输入完成后回车自动显示客户对应的性别，生日，年龄-->
					<input onkeydown="javascript:nextKeyPress(this)" 	title="<%=LocalUtilis.language("class.custTip",clientLocale)%> " 
						type="text" name="q_card_id" size="25"  value="<%=q_card_id%>">
				</td>
				<td align=right><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
				<td>
					<input onkeydown="javascript:nextKeyPress(this)" type="text"  name="q_cust_no" size="20" value="<%=q_cust_no%>">
				</td>
			</tr>
			<tr <%if(readonly==1) out.print("style='display:none'");%>>
				<td align=right><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP卡编号-->
				<td><input onkeydown="javascript:nextKeyPress(this)" type="text"  name="q_vip_card_id" size="25" value="<%=q_vip_card_id%>"></td>
				<td align=right><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--合格投资人编号-->
				<td><input onkeydown="javascript:nextKeyPress(this)" type="text"  name="q_hgtzr_bh" size="20" value="<%=q_hgtzr_bh%>"></td>
			</tr>
			<tr>
				<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
				<td>			
					<input onkeydown="javascript:nextKeyPress(this);" type="text"  name="q_cust_name" size="25" value="<%=q_cust_name%>">
				</td>
				<td align=right><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--是否关联方-->
				<td><input class="flatcheckbox" onkeydown="javascript:nextKeyPress(this)" type="checkbox"  name="q_is_link" value="1" <%if(q_is_link.intValue()==1) out.print("q_is_link");%>></td>
			</tr>		
			<tr>
				<td align=right>&nbsp;</td>
				<td colspan="3">			
					<button type="button" class="xpbutton3" accessKey=q onclick="javascript:queryInfo();"><%=LocalUtilis.language("message.search",clientLocale)%> (<u>Q</u>)</button>	&nbsp;<!--搜索-->
				</td>					
			</tr>	
			<tr <%if (sReadonly.equals("") || readonly==1 && cust_id.intValue()>0) out.print("style='display:none'");%>>
				<td align="right"><%=LocalUtilis.language("message.searchResults",clientLocale)%> :</td><!--搜索结果-->
				<td colspan="4">
					<select onkeydown="javascript:nextKeyPress(this)" size="1"  name="id"  onchange="javascript: changeCustomer();">
						<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
					<%if ("list".equals(task)){
						Iterator iterator = rsList.iterator();
						
						while(iterator.hasNext()){	
							map = (Map)iterator.next();		
									
							Integer r_cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")), new Integer(0)); 
							String r_cust_name = Utility.trimNull(map.get("CUST_NAME"));
							String r_card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
							String r_card_id = Utility.trimNull(map.get("CARD_ID"));	
					%>
						<option value="<%=r_cust_id%>" <% if(r_cust_id.equals(cust_id)){%>selected<%}%>>							
							<%= (r_cust_name+"-"+r_card_type_name+"-"+r_card_id)%>	
						</option>
					<%
						}					
					}
					%>
					</select>
				</td>
			</tr>		
		</table>		
	</div>
	
	<hr noshade color="#808080" size="1">
</div>

<%


//声明单一客户信息变量
String cust_type_name ="";
Integer cust_type = new Integer(0);
String cust_name="";
String cust_no="";
String cust_source="";
String cust_source_name="";
String vip_card_id="";
String hgtzr_bh="";
Integer vip_date= new Integer(0);
Integer service_man= new Integer(0);
String card_type_name="";
Integer card_type = new Integer(0);
String card_id="";
String legal_man="";
String contact_man="";
Integer birthday= new Integer(0);
Integer age= new Integer(0);
Integer sex= new Integer(0);
String o_tel="";
String h_tel="";
String mobile="";
String bp="";
String fax="";
String e_mail="";
String post_code="";
String post_address="";
String post_code2="";
String post_address2="";
String touch_type="";
String touch_type_name="";
Integer is_link = new Integer(0);
String summary="";
String voc_type="";
String voc_type_name="";
String sex_name="";



//选择单一客户信息。查询详细资料 SQL2005		
if (cust_id.intValue()>0){
	vo = new CustomerVO();
	vo.setCust_id(cust_id);
	vo.setInput_man(input_operatorCode);
	
	rsList_single = customer.listByControl(vo);
	map_single = (Map)rsList_single.get(0);
	
	cust_type_name = Utility.trimNull(map_single.get("CUST_TYPE_NAME"));	
	cust_type = Utility.parseInt(Utility.trimNull(map_single.get("CUST_TYPE")),new Integer(0));
	cust_name = Utility.trimNull(map_single.get("CUST_NAME"));
	cust_no = Utility.trimNull(map_single.get("CUST_NO"));
	cust_source = Utility.trimNull(map_single.get("CUST_SOURCE"));
	cust_source_name = Utility.trimNull(map_single.get("CUST_SOURCE_NAME"));
	vip_card_id = Utility.trimNull(map_single.get("VIP_CARD_ID"));
	hgtzr_bh = Utility.trimNull(map_single.get("HGTZR_BH"));
	vip_date = Utility.parseInt(Utility.trimNull(map_single.get("VIP_DATE")),new Integer(0));
	service_man	= Utility.parseInt(Utility.trimNull(map_single.get("SERVICE_MAN")),new Integer(0));
	card_type_name = Utility.trimNull(map_single.get("CARD_TYPE_NAME"));
	card_type  = Utility.parseInt(Utility.trimNull(map_single.get("CARD_TYPE")),new Integer(0));
	card_id = Utility.trimNull(map_single.get("CARD_ID"));
	legal_man = Utility.trimNull(map_single.get("LEGAL_MAN"));
	contact_man = Utility.trimNull(map_single.get("CONTACT_MAN"));
	birthday = Utility.parseInt(Utility.trimNull(map_single.get("BIRTHDAY")),new Integer(0));
	age = Utility.parseInt(Utility.trimNull(map_single.get("AGE")),new Integer(0));
	sex = Utility.parseInt(Utility.trimNull(map_single.get("SEX")),new Integer(0));
	o_tel =  Utility.trimNull(map_single.get("O_TEL"));
	h_tel =  Utility.trimNull(map_single.get("H_TEL"));
	mobile  =  Utility.trimNull(map_single.get("MOBILE"));
	bp  =  Utility.trimNull(map_single.get("BP"));
	fax =  Utility.trimNull(map_single.get("FAX"));
	e_mail =  Utility.trimNull(map_single.get("E_MAIL"));
	post_code =  Utility.trimNull(map_single.get("POST_CODE"));
	post_address =  Utility.trimNull(map_single.get("POST_ADDRESS"));
	post_code2 =  Utility.trimNull(map_single.get("POST_CODE2"));
	post_address2 =  Utility.trimNull(map_single.get("POST_ADDRESS2"));
	touch_type = Utility.trimNull(map_single.get("TOUCH_TYPE"));
	touch_type_name = Utility.trimNull(map_single.get("TOUCH_TYPE_NAME"));
	is_link = Utility.parseInt(Utility.trimNull(map_single.get("IS_LINK")),new Integer(0));
	summary = Utility.trimNull(map_single.get("SUMMARY"));
	voc_type =  Utility.trimNull(map_single.get("VOC_TYPE"));
	voc_type_name =  Utility.trimNull(map_single.get("VOC_TYPE_NAME"));
	sex_name =  Utility.trimNull(map_single.get("SEX_NAME"));	
}

%>
<!--客户信息-->
<div>
<input type='hidden' name="customer_service_man_value"  value='<%=service_man%>'>
<input type='hidden' name="customer_cust_type"  value='<%=Utility.trimNull(cust_type)%>'/>
<input type='hidden' name="customer_cust_on"  value='<%=Utility.trimNull(cust_no)%>'/>
<input type='hidden' id="customer_legal_man"  size="25" maxlength="30" value="<%=legal_man%>">
<input type='hidden' id="customer_contact_man"  size="25" maxlength="30" value="<%=legal_man%>">
<input type='hidden' id="customer_card_type"  value='<%=Utility.trimNull(card_type)%>'/>

<table border="0" width="100%" cellspacing="0" cellpadding="1">
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
			<td >
				<input <%if (!sReadonly.equals("")){out.print(sReadonly);} else{out.print("type='hidden'");}%> 
					onkeydown="javascript:nextKeyPress(this)" name="customer_cust_type_name" size="18" maxlength="20" 
					value="<%=cust_type_name%>"/>
				
				<SELECT size="1" <%if (!sReadonly.equals("")){out.print("style='display:none'");}%> 
						onchange="javascript:changeCustType(this.value);"  name="customer_cust_type" 
						onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 120px">
					<%=Argument.getCustTypeOptions2(cust_type)%>
				</SELECT>	
			</td>
				
			<td align="right">*<%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
			<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="40" maxlength="60" value="<%=cust_name%>"></td>
		</tr>
		
		<tr>
			<td align="right">		 
				<% if(cust_type.intValue()==1){%>*<%=LocalUtilis.language("class.profession",clientLocale)%> :<%} //职业
				else {%> *<%=LocalUtilis.language("class.industry",clientLocale)%> :<%}%><!--行业-->
			</td>
			<td colspan=3>
				<input <%if(!sReadonly.equals("")) {out.print(sReadonly);} else {out.print("type='hidden'");}%> onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="18" maxlength="20" value="<%=voc_type_name%>">			
				
				<select size="1" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> 
						onkeydown="javascript:nextKeyPress(this)" name="customer_zyhy_type" style="WIDTH: 238px">
					 <% if(cust_type.intValue()==1)  {%>
					  <%=Argument.getGrzyOptions2(voc_type)%>
					<%} else {%>	  
					   <%=Argument.getJghyOptions2(voc_type)%>
				   <%}%>
				</select>								
			</td>
		</tr>
		
		<tr>	
			<td align="right"><%=LocalUtilis.language("class.customerNumber",clientLocale)%> :</td><!--客户代码-->
			<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_no" size="25" maxlength="25" value="<%=cust_no%>"></td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
			<td>
			<%if (!sReadonly.equals("")){%>
				<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_source_name" size="25" maxlength="25" value="<%=cust_source_name%>">
			<%}else{%>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(cust_source)%>
				</select>
			<%}%>
			</td>	
		</tr>
		
		<tr>	
			<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP卡编号-->
			<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_vip_card_id" size="25" maxlength="25" value="<%=vip_card_id%>"></td>
			<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--合格投资人编号-->
			<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_hgtzr_bh" size="25" maxlength="25" value="<%=hgtzr_bh%>"></td>	
		</tr>
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerVipDatePicker",clientLocale)%> :</td><!--VIP发卡日期-->
			<td>
				<INPUT TYPE="text" <%=sReadonly%> NAME="customer_vip_date_picker" class=selecttext 
				<%if(vip_date==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(vip_date)%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
				<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_vip_date_picker,theform.customer_vip_date_picker.value,this);" tabindex="13">
				<%}%><INPUT TYPE="hidden" NAME="customer_vip_date"   value="">
			</td>
			<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td> <!--客户经理-->
			<td>
				<%if (!sReadonly.equals("")){%>
					<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_service_man" size="25" maxlength="20" value="<%=Utility.trimNull(Argument.getOpName(service_man))%>">
				<%}else{%>	
				<select size="1" name="customer_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getRoledOperatorOptions(input_bookCode, 2, "update".equals(request.getParameter("task"))?service_man:input_operatorCode)%>
				</select>
				<%}%>
			</td>
		</tr>		
		
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型-->
			<td>
				<input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> 
					onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="25" maxlength="25" value="<%=card_type_name%>">
					   
					   <%
						if(cust_type.intValue()==0){
							cust_type=new Integer(2);			
						}			

						if(cust_type.intValue()==1){
						%>				
							<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%>  onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
								<%=Argument.getCardTypeOptions2("0")%>
							</select>
						<%}
						else{
						%>				
							<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
								<%=Argument.getCardTypeJgOptions2("0")%>
							</select>
						<%}%>
					</td>	
					
					<td align="right">*<%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
					<td>
						<input <%=sReadonly%> onkeydown="javascript:calculateAge();nextKeyPress(this)"onblur="javascript:calculateAge2();" 
						onkeyup="javascript:showAcctNum(this.value)" name="customer_card_id" size="25" maxlength="30" value="<%=card_id%>">
						<span id="bank_acct_num" class="span"></span>
					</td>	
				</tr>
				
				<%if(cust_type.intValue()!=1){%>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</td><!--法人姓名-->
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_legal_man"  size="25" maxlength="30" value="<%=legal_man%>"></td>
					<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--联系人-->
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_contact_man"  size="25" maxlength="20" value="<%=contact_man%>"></td>
				</tr>		
				<%}%>
				
			<%if(cust_id.intValue()==0&&cust_type.intValue()==1){	%>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--出生日期-->
					<td colspan="3">
						<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext onblur="javascript:CalAge2();"
						<%if(birthday==null){%> value=""
						<%}else{%>value="<%=Format.formatDateLine(birthday)%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
						<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
						<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
					</td>	
				</tr>
			<%}else if(cust_id.intValue()!=0&&cust_type.intValue()==1){%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--出生日期-->
				<td colspan="3">
					<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext onblur="javascript:CalAge2();"
					<%if(birthday==null){%> value=""
					<%}else{%>value="<%=Format.formatDateLine(birthday)%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
					<%if(sReadonly.equals("")){%>
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
					<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
				</td>
			</tr>
			<%}%>
			
			<%if(cust_type.intValue()==1){%>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.age",clientLocale)%> :</td><!--年龄-->
				<td><INPUT <%=sReadonly%> name="customer_age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=age%>"></td>
				<td align="right"><%=LocalUtilis.language("class.sex",clientLocale)%> :</td><!--性别-->
				<td>				
					<%if(!sReadonly.equals("")){%>
							<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_sex_name" size="25" maxlength="20" value="<%=sex_name%>">
						<%}else{%>	
						<SELECT size="1" onkeydown="javascript:nextKeyPress(this)" name="customer_sex" style="WIDTH: 120px">
						<%=Argument.getSexOptions(sex)%>
					</SELECT>
					<%}%>
				</td>
			</tr>
			<%}%>
			
			<tr>	
				<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td><!--住宅电话-->
				<td><input <%=sReadonly%> name="customer_h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=h_tel%>"></td>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
				<td><INPUT <%=sReadonly%> name="customer_o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=o_tel%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.mobile",clientLocale)%> :</td><!--手机号码-->
				<td><input <%=sReadonly%> name="customer_mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=mobile%>"></td>
				<td align="right"><%=LocalUtilis.language("class.mobile",clientLocale)%> 2:</td><!--手机号码-->
				<td><INPUT <%=sReadonly%> name="customer_bp" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=bp%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :</td><!--传真-->
				<td><input <%=sReadonly%> name="customer_fax" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=fax%>"></td>
				<td align="right">Email:</td>
				<td><INPUT <%=sReadonly%> name="customer_e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="40" value="<%=e_mail%>"></td>
				
			</tr>
			<tr>
				<td align="right">fmt:message key="class.postcode"/>:</td><!--邮政编码-->
				<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code" size="25" maxlength="6" value="<%=post_code%>"></td>	
				<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :</td><!--邮寄地址-->
				<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address" size="40" maxlength="60" value="<%=post_address%>"></td>
				
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2:</td><!--邮政编码-->
				<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code2" size="25" maxlength="6" value="<%=post_code2%>"></td>
				
				<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2:</td><!--邮寄地址-->
				<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address2" size="40" maxlength="60" value="<%=post_address2%>"></td>
			</tr>
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</td><!--联系方式-->
				<td>
				<%if (!sReadonly.equals("")){%>
					<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_touch_type_name" size="25" maxlength="20" value="<%=Utility.trimNull(touch_type_name)%>">
				<%}else{%>	
				<select size="1" name="customer_touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getTouchTypeOptions(touch_type)%>
				</select>
				<%}%>
				</td>
				<td align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--是否关联方-->
				<%%>
				<td ><input <%=sDisabled%> type="checkbox" name="customer_is_link" value="<%=is_link%>" <%if(is_link !=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
				<tr>
			</tr>
			<tr>	
				<td  align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
				<td colspan=3><textarea <%if (!sReadonly.equals("")) out.print("readonly class='edline_textarea'");%> rows="3" name="customer_summary" cols="86"><%=summary%></textarea></td>
			</tr>

</table>
</div>

<div align="right">
	<br>
	<!--保存-->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>

<%@ include file="/includes/foot.inc"%>
<%customer.remove();%>
</BODY>
</HTML>
