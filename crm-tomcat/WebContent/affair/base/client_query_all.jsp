<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String viewStr = "CUST_NO$CUST_NAME$CARD_ID$TOTAL_MONEY$EXCHANGE_AMOUNT$BACK_AMOUNT$BEN_AMOUNT$LAST_RG_DATE";
Integer v_op_code = Utility.parseInt(Utility.trimNull(request.getParameter("v_op_code")),input_operatorCode);
Integer v_setView = Utility.parseInt(Utility.trimNull(request.getParameter("v_setView")),new Integer(0));
//��ò�ѯ����
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(0));
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
String cust_source = Utility.trimNull(request.getParameter("cust_source"));
String card_type = Utility.trimNull(request.getParameter("card_type"));//֤������
String card_id = Utility.trimNull(request.getParameter("card_id"));//֤������
String vip_card_id = Utility.trimNull(request.getParameter("vip_card_id"));//VIP�����
String hgtzr_bh = Utility.trimNull(request.getParameter("hgtzr_bh"));//�ϸ�Ͷ���˱��
Integer birthday = Utility.parseInt(request.getParameter("birthday"), new Integer(0));//��������
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//��ʼ�������
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//�����������
Integer start_current_score =Utility.parseInt(request.getParameter("start_current_score"), new Integer(0));//��ʼ�ͻ��÷�
Integer end_current_score = Utility.parseInt(request.getParameter("end_current_score"), new Integer(0));//�����ͻ��÷�
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//��͹�����
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//��߹�����
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//���������
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//���������
String touch_type = Utility.trimNull(request.getParameter("touch_type"));//��ϵ��ʽ
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//��ϵ�绰
String post_address = Utility.trimNull(request.getParameter("post_address"));//��ϵ��ַ
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//��ƷID
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));//�Ƿ񰴹�������ѯ
Integer onlyemail = Utility.parseInt(request.getParameter("onlyemail"), new Integer(0));//�Ƿ�����ʼ�
String family_name = Utility.trimNull(request.getParameter("family_name"));//��ͥ����
String sort_name = Utility.trimNull(request.getParameter("sortName"));//�����ֶ�
String prov_level = Utility.trimNull(request.getParameter("prov_level"));//���漶��
Integer wtr_flag = Utility.parseInt(Utility.trimNull(request.getParameter("wtr_flag")),new Integer(0));//ί���˱�־
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
String q_country = Utility.trimNull(request.getParameter("q_country"));
String q_money_source = Utility.trimNull(request.getParameter("q_money_source"));
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),new Integer(0));
String referee = Utility.trimNull(request.getParameter("referee"));

String productTo = Utility.trimNull(request.getParameter("productTo"));
Integer start_age = Utility.parseInt(request.getParameter("start_age"),null);
Integer end_age = Utility.parseInt(request.getParameter("end_age"),null);
String telephone = Utility.trimNull(request.getParameter("telephone"));
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),null);
int cell_flag = Utility.parseInt(request.getParameter("cell_flag"),1);
Integer birthday_end = Utility.parseInt(request.getParameter("birthday_end"),new Integer(0));
Integer rg_begin_date = Utility.parseInt(request.getParameter("rg_begin_date"),new Integer(0));
Integer rg_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(0));

StringBuffer sortlist = Argument.newStringBufferBit();//�����ֶ��б�;
Argument.appendOptions(sortlist, "CUST_NAME", enfo.crm.tools.LocalUtilis.language("class.customerName",clientLocale), sort_name);//�ͻ�����
Argument.appendOptions(sortlist, "TOTAL_MONEY", enfo.crm.tools.LocalUtilis.language("message.amountPurchased",clientLocale), sort_name);//�ѹ����
Argument.appendOptions(sortlist, "LAST_RG_DATE", enfo.crm.tools.LocalUtilis.language("message.lastRgDate",clientLocale), sort_name);//�������ʱ��
Argument.appendOptions(sortlist, "CURRENT_MONEY", enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale), sort_name);//�������
Argument.appendOptions(sortlist, "BEN_AMOUNT", enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale), sort_name);//����ݶ�

String tempView = Argument.getMyMenuViewStr(menu_id,v_op_code);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}

StringBuffer custIdSAll = new StringBuffer();


//URL����
sUrl = sUrl + "&cust_no=" + cust_no 
			+ "&cust_name=" + cust_name
			+ "&cust_source=" + cust_source  
			+ "&cust_type=" + cust_type 
			+ "&card_type=" + card_type 
			+ "&card_id=" + card_id 
			+ "&vip_card_id=" + vip_card_id 
			+ "&hgtzr_bh=" + hgtzr_bh 
			+ "&birthday" + birthday 
			+ "&cust_level" + cust_level 
			+ "&start_rg_times=" + start_rg_times 
			+ "&end_rg_times=" + end_rg_times 
			+ "&min_total_money=" + min_total_money 
			+ "&max_total_money=" + max_total_money 
			+ "&ben_amount_min=" + ben_amount_min 
			+ "&ben_amount_max=" + ben_amount_max 
			+ "&touch_type=" + touch_type 
			//+ "&cust_tel=" + cust_tel 
			+ "&post_address=" + post_address
			+ "&product_id=" + product_id 
			+ "&is_link=" + is_link 
			+ "&onlyemail=" + onlyemail 
			+ "&family_name=" + family_name 
			+ "&sort_name=" + sort_name 
			+ "&prov_level=" + prov_level
			+ "&wtr_flag=" + wtr_flag
			+ "&group_id=" + group_id
			+ "&q_money_source=" + q_money_source
			+ "&q_country=" + q_country
			+ "&is_deal=" + is_deal
			+ "&accountManager="+accountManager
			+ "&referee="+referee
			+ "&productTo="+productTo;

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.queryCondition",clientLocale)%> </TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

/*��ò�ѯ����*/
function getQueryCondition()
{

	if(document.theform.cell_flag.value==2){
		if(document.theform.cell_id.value==""||document.theform.cell_id.value==0){
			sl_alert("<%=LocalUtilis.language("message.PleaseSelectAPU",clientLocale)%>��");
			return false;
		}
	}

	if(document.theform.is_link.checked)
		document.theform.is_link.value=1;
	else
		document.theform.is_link.value=0;
	if(document.theform.onlyemail.checked)
		document.theform.onlyemail.value=1;
	else
		document.theform.onlyemail.value=0;
	if(document.theform.sort_name.value!=-1)
	document.theform.sortName.value = document.theform.sort_name.value + document.theform.sortName.value;
	else
	document.theform.sortName.value = "";
	var v = new Array;
	v[0] = document.theform.cust_no.value 
	v[1] = document.theform.cust_name.value
	v[2] = document.theform.cust_source.value
	v[3] = document.theform.cust_type.value
	v[4] = document.theform.card_type.value
	v[5] = document.theform.card_id.value 
	v[6] = document.theform.vip_card_id.value 
	v[7] = document.theform.hgtzr_bh.value 
	v[8] = document.theform.birthday.value
	v[9] = document.theform.cust_level.value
	v[10] = document.theform.start_rg_times.value
	v[11] = document.theform.end_rg_times.value
	v[12] = + document.theform.min_total_money.value
	v[13] = + document.theform.max_total_money.value
	v[14] = + document.theform.ben_amount_min.value
	v[15] = + document.theform.ben_amount_max.value
	v[16] = document.theform.touch_type.value
	v[17] = document.theform.post_address.value
	v[18] = document.theform.product_id.value
	v[19] = document.theform.is_link.value
	v[20] = document.theform.onlyemail.value 
	v[21] = document.theform.family_name.value
	v[22] = document.theform.sortName.value
	v[23] = document.theform.prov_level.value
	v[24] = document.theform.wtr_flag.value
	v[25] = document.theform.group_id.value
	v[26] = document.theform.q_money_source.value
	v[27] = document.theform.q_country.value
	v[28] = document.theform.is_deal.value
	v[29] = document.theform.accountManager.value
	v[30] = document.theform.referee.value
	
	if(document.theform.classEs.value!='' && document.theform.classEs.value2=='')
		v[31] = document.theform.classEs.value;
	else if(document.theform.classEs.value=='' && document.theform.classEs2.value!='')
		v[31] = document.theform.classEs2.value;
	else if(document.theform.classEs.value!='' && document.theform.classEs2.value!='')
		v[31] = document.theform.classEs.value+','+document.theform.classEs2.value;	
	else
		v[31] = document.theform.classEs.value;
	
	v[32] = document.theform.productTo.value;
	v[33] = document.theform.start_age.value;
	v[34] = document.theform.end_age.value;
	v[35] = document.theform.telephone.value;
	v[36] = document.theform.cell_id.value;
	v[37] = document.theform.cell_flag.value;
	v[38] = document.theform.birthday_end.value;
	syncDatePicker(theform.rg_begin_date_picker, theform.rg_begin_date);
	syncDatePicker(theform.rg_end_date_picker, theform.rg_end_date);
	v[39] = document.theform.rg_begin_date.value;
	v[40] = document.theform.rg_end_date.value;
	
	v[41] = document.theform.start_current_score.value;
	v[42] = document.theform.end_current_score.value;
	window.returnValue = v ;
	window.close();
}


/*���ò�Ʒ*/
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

/*��ѯ��Ʒ*/
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

/*ѡ���Ʒ��ʾί�б�־*/
function onChangeProduct()
{
	if(document.theform.product_id.selectedIndex != 0)
	{
		document.all.WTRFlag.style.display="";
		document.all.levelsFlag.style.display="";
	}
	else
	{
		document.all.WTRFlag.style.display="none";
		document.all.levelsFlag.style.display="none";
	}	
}

/*ѡ��ί�б�־��ʾ�����˱�־*/
function changerLevels(flag)
{
	if(flag==2)
		document.all.levelsFlag.style.display="none";
	else
		document.all.levelsFlag.style.display="";	
}


//ѡ����ϵ��ʽ��������ʾ��Ӧ��ֵ
function getTouchTypeSlectedText(name){
	var obj=document.getElementById(name);
	for(i=0;i<obj.length;i++){
		if(obj[i].selected==true){
			if(obj[i].innerText == "<%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> "){//��ѡ��
				//document.getElementById("cust_tel").innerText = "<%=LocalUtilis.language("class.tele",clientLocale)%> ";//�绰
			}else{
				//document.getElementById("cust_tel").innerText = obj[i].innerText; //ͨ��option�����innerText���Ի�ȡ��ѡ���ı�
			}
		}
	}
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=20301','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');	
	if(result!=null)
	location.reload();
}

function changeCustType(flag){
	document.getElementById("cust_type").value=flag;
	refreshPage();
}

function getTreeData()
{
	var ret = showModalDialog('/client/appraise/client_query_tree_all.jsp?pageFlag=1','','dialogWidth:440px;dialogHeight:580px;status:0;help:0');	
	if(ret!=null)
	{
		for(var i=0;i<ret.length;i++){
			if(i==0)
				document.theform.classEs.value = ret[i]
			else
				document.theform.classEs.value = document.theform.classEs.value+','+ret[i];
		}
	}
}

function getTreeData2()
{
	var ret = showModalDialog('/client/appraise/client_query_tree_all.jsp?pageFlag=2','','dialogWidth:440px;dialogHeight:580px;status:0;help:0');	
	if(ret!=null)
	{
		for(var i=0;i<ret.length;i++){
			if(i==0)
				document.theform.classEs2.value = ret[i]
			else
				document.theform.classEs2.value = document.theform.classEs.value+','+ret[i];
		}
	}
}

function SelectCellFlag(value){
	if(value == "1")
	{
		tr1.style.display="";
		tr2.style.display="";
		tr3.style.display="none";
		
	}else if(value == "2")
	{
		tr1.style.display="none";
		tr2.style.display="none";
		tr3.style.display="";
	}
	document.theform.cell_flag.value=value;
}

function openReportTree()
{
	var v = showModalDialog('report_tree.jsp', '', 'dialogWidth:530px;dialogHeight:550px;status:0;help:0');
	if (v != null)
	{
		document.theform.cell_id.value = v;
	}
}
//����
function onChangeSort()
{
	
	if(document.theform.sort_name.selectedIndex == 0){
		
		var c=document.getElementById("biaozhi");
	    c.style.display="none";

	}else
	{ 
	    var a=document.getElementById("biaozhi");
	    a.style.display="";
		document.theform.sort_flag.checked = false;
		document.theform.sort_flag.disabled = false;
	}
}
function Selectsort_flag(value){
	if(value == "1")
	{
		document.theform.sortName.value=" DESC";
		
	}else if(value == "2")
	{
		document.theform.sortName.value=" ASC";
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' style='width: 410px' class='productname' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:onChangeProduct();'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
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
<BODY class="BODY" topmargin="1">&nbsp; 
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="client_query_list.jsp">
<input type="hidden" name="input_man" value="<%=input_operatorCode%>">
<input type="hidden" name="titleNames">
<input type="hidden" name="fieldNames">
<input type="hidden" name="fieldTypes">
<input type="hidden" name="classEs" value="">
<input type="hidden" name="sortName" value="">
<input type="hidden" name="classEs2" value="">
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="cell_flag" value="<%=cell_flag %>">

<table align="center">
	<tr>
		<td align=right >��Ʒѡ��ʽ:</td>
		<td colspan="3" >
		<input type="radio" value="1" onclick="javascript:SelectCellFlag(this.value);" checked" onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" <%if(cell_flag==1) out.print("checked");%>>������Ʒ&nbsp;&nbsp;
		<input type="radio" value="2" onclick="javascript:SelectCellFlag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" <%if(cell_flag==2) out.print("checked");%>>��Ʒ��Ԫ

		</td>
	</tr>
	<tr id = "tr1" <%if(cell_flag==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :<!--��Ʒ���--></td>
		<td>	
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
				   size="20" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
			<button type="button" class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" />	
		</td>
		<td align="right">��Ʒ���� :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button" class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
	</tr>
	<tr id = "tr2" <%if(cell_flag==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
		<td colspan=3 align="left" id="select_id">
			<select size="1" name="product_id" style="width: 410px" class="productname" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:onChangeProduct();">
			<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</select>
		</td>
	</tr>
	<tr id = "tr3" <%if(cell_flag==2)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align = "right">��Ʒ��Ԫ:</td >
		<td align="left"  colspan="3">
		<button type="button" class="xpbutton3" name="btnQuery1" onclick="javascript:openReportTree();">��ѡ��...</button>
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.productsTo",clientLocale)%> :</td><!--��ƷͶ��-->
		<td valign="bottom" align="left">
			<select name="productTo" style="width: 147px;">
				<%=Argument.getDictParamOptions_intrust(1139,productTo) %>
			</select>
		</td>
		<td valign="bottom" align="right"></td><!--����-->
		<td valign="bottom" align="left">
		</td>	
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
		<td valign="bottom" align="left"><input type="text" name="cust_no"
			onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(cust_no)%>" size="25">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="cust_name" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(cust_name)%>" size="25">
		</td>	
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--֤������-->
		<td valign="bottom" align="left">
			<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="card_type" style="WIDTH: 147px">
			   <%=Argument.getCardTypeJgOrGrOptions(card_type)%>
			</select>  
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
		<td valign="bottom" align="left">
		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="card_id" size="25" value="<%=card_id%>">
		</td>			
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.AgeGroup",clientLocale)%> :</td><!--�����-->
		<td valign="bottom" align="left">
			��<input type="text" name="start_age"onkeydown="javascript:nextKeyPress(this)"value="<%=Utility.trimNull(start_age)%>" size="7">
			��<input type="text" name="end_age"onkeydown="javascript:nextKeyPress(this)"value="<%=Utility.trimNull(end_age)%>" size="7">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.tel",clientLocale)%> :</td><!--�绰����-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="telephone" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(telephone)%>" size="25">
		</td>	
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
		<td valign="bottom" align="left">
			<select name="accountManager" style="width: 147px">
				<%=Argument.getOperatorOptions(accountManager) %>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.referee",clientLocale)%> :</td><!--�Ƽ���-->
		<td valign="bottom" align="left"><input type="text" maxlength="100"
			name="referee" onkeydown="javascript:nextKeyPress(this)"
			value="<%=Utility.trimNull(referee)%>" size="25">
		</td>	
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.birthday",clientLocale)%> :</td><!--��������-->
		<td>
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="4" name="birthday" size="8" value="<%=Utility.trimNull(birthday)%>">
			<%=LocalUtilis.language("message.to",clientLocale)%>
			<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="4" name="birthday_end" size="8"value="<%=Utility.trimNull(birthday)%>">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--�ͻ�����-->
		<td valign="bottom" align="left">
		<select style="width:147px" name="cust_level" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getDictParamOptions2(1111, cust_level)%>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale) %>:</td><!-- �ͻ���� -->
		<td>
			<select name="cust_type" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustTypeOptions(cust_type) %>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.isTransactions",clientLocale) %>:</td><!-- �Ƿ��� -->
		<td >
			<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width: 147px" >	
				<%=Argument.getWTCustOptions(is_deal)%>
			</select>							
		</td>
	</tr>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.custGroups",clientLocale) %>:</td><!-- �ͻ�Ⱥ�� -->
		<td>
			<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 147px">
				<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale) %>:</td><!-- �ͻ���Դ -->
		<td>
			<select style="width: 147px" name="cust_source" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions2(1110, cust_source)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :</td><!--VIP�����-->
		<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="vip_card_id" size="25"
				value="<%=Utility.trimNull(vip_card_id)%>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :</td><!--�ϸ�Ͷ���˱��-->
		<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="hgtzr_bh" size="25"
			value="<%=Utility.trimNull(hgtzr_bh)%>">
		</td>			
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%>:</td><!-- ���� -->
		<td>
			<select size="1"  name="q_country"  id="q_country" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(9997,q_country)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale) %>:</td><!-- �ʽ���Դ -->
		<td>
			<select size="1"  name="q_money_source"  id="q_money_source" style="width: 147px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(2012,q_money_source)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">�ͻ��÷� :</td><!--������-->
		<!--��-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="start_current_score" size="20"
				<%if(start_current_score.intValue() != 0){%>
                value="<%=Utility.trimNull(start_current_score)%>"<%}%>> <%=LocalUtilis.language("message.end",clientLocale)%> <!-- �� -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="end_current_score" size="20" 
				<%if(end_current_score.intValue() != 0){%>
				value="<%=Utility.trimNull(end_current_score)%>"<%}%>>
		</td>
	</tr>
	<tr>  
		<td align="right"><%=LocalUtilis.language("class.rg_times",clientLocale)%> :</td><!--�������-->
		<!--��-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="start_rg_times" size="20" <%
				if(start_rg_times.intValue() == 0){%>
				  value="" 
				<%}else{%>
				value="<%=Utility.trimNull(start_rg_times)%>"
                <%}%>> <%=LocalUtilis.language("message.end",clientLocale)%> <!-- �� --> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="end_rg_times" size="20" <%
				if(end_rg_times.intValue() == 0){%>
				  value=""
				<%}else{%>
				value="<%=Utility.trimNull(end_rg_times)%>"
				<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.totalMoney",clientLocale)%> :</td><!--������-->
		<!--��-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_total_money" size="20"
				<%if(min_total_money.intValue() != 0){%>
                value="<%=Utility.trimNull(min_total_money)%>"<%}%>> <%=LocalUtilis.language("message.end",clientLocale)%> <!-- �� -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_total_money" size="20" 
				<%if(min_total_money.intValue() != 0){%>
				value="<%=Utility.trimNull(max_total_money)%>"<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.benefitShare",clientLocale)%> :</td><!--����ݶ�-->
		<!--��-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_min" size="20" 
			<%
			if(ben_amount_min.intValue() == 0){%>
			  value=""
			<%}else{%>
			value="<%=Utility.trimNull(ben_amount_min)%>"
			
            <%}%>> <%=LocalUtilis.language("message.end",clientLocale)%>  <input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_max" size="20" 
			<%
			if(ben_amount_max.intValue() == 0){%>
			 value=""
			<%}else{%>
			value="<%=Utility.trimNull(ben_amount_max)%>"
			<%}%>>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.subscriptionDate",clientLocale)%> :</td><!--�Ϲ�����-->
		<!--��-->
        <td colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%>
			<input type="text" name="rg_begin_date_picker" class=selecttext	 onkeydown="javascript:nextKeyPress(this)"  
		        <%if(rg_begin_date.intValue() == 0){%>
				  value=""
				<%}else{ %>
				  value="<%=Format.formatDateLine(rg_begin_date)%>";
				<%}%>>
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_begin_date_picker,theform.rg_begin_date_picker.value,this);">
			<input type="hidden" name="rg_begin_date" value="">
			<%=LocalUtilis.language("message.end",clientLocale)%>
			<input type="text" name="rg_end_date_picker" class=selecttext	 onkeydown="javascript:nextKeyPress(this)"  
		        <%if(rg_end_date.intValue() == 0){%>
				  value=""
				<%}else{ %>
				  value="<%=Format.formatDateLine(rg_end_date)%>";
				<%}%>>
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_end_date_picker,theform.rg_end_date_picker.value,this);">
			<input type="hidden" name="rg_end_date" value="">
			
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</td><!--��ϵ��ʽ-->
		<td><select size="1" name="touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 147px;">
			  <%=Argument.getTouchTypeOptions(touch_type)%>
			</select>
		</td>
		<td align="center">
			<%=LocalUtilis.language("menu.customerRating",clientLocale)%> :<!--�ͻ�����-->
			<button type="button" class="xpbutton4" name="btnQuery" accessKey="q"
			onclick="javascript:getTreeData();"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> (<u>Q</u>)</button><!--��ѯ--></td>
		<td align="center">
			<%=LocalUtilis.language("menu.CustomerClassification",clientLocale)%> :<!--�ͻ�����-->
			<button type="button" class="xpbutton4" name="btnQuery" accessKey="q"
			onclick="javascript:getTreeData2();"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> (<u>Q</u>)</button><!--��ѯ--></td>
		</td>	
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
		<td colspan="3"> <input type="text" onkeydown="javascript:nextKeyPress(this)" name="post_address" size="40"
				value="<%=Utility.trimNull(post_address)%>">
			&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>	
		<td align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--�Ƿ������-->
		<td><input type="checkbox" name="is_link" value="0" class="flatcheckbox"
				<%if(is_link.equals(new Integer(1))){%>
				  checked
				<%}%>>
		</td>
		<td align="right"><%=LocalUtilis.language("class.onlyemail",clientLocale)%> :</td><!--�Ƿ�����ʼ�-->
		<td ><input type="checkbox" name="onlyemail" value="0" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox"
				<%if(onlyemail.equals(new Integer(1))){%>
				  checked
				<%}%>></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.familyName",clientLocale)%> :</td><!--��ͥ����-->
		<td colspan="3"> <input type="text" onkeydown="javascript:nextKeyPress(this)" name="family_name" size="28" 
				value="<%=Utility.trimNull(family_name)%>">
		</td>	
	</tr>
	<tr id="reader2" style="display:">
		<td align="right"><%=LocalUtilis.language("message.sort_name",clientLocale)%> :</td><!--�����ֶ�-->
		<td style='display:'>
        	<select size="1" name="sort_name" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:onChangeSort();" style="WIDTH: 160px" >
				<%=sortlist.toString()%>
			</select>
		</td>
		<td id="biaozhi"align="right" style='display:none'>&nbsp;&nbsp;
			�����־:<input type="radio" value="1" onclick="javascript:Selectsort_flag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="sort_flag" class="flatcheckbox" >
			�����־:<input type="radio" value="2" onclick="javascript:Selectsort_flag(this.value);" onkeydown="javascript:nextKeyPress(this)" name="sort_flag" class="flatcheckbox" >
		</td>
	</tr>
	<tr id="WTRFlag" <%if(product_id.intValue() == 0){%> style="display: none;"<%}%>>
		<td align="right"><%=LocalUtilis.language("class.wtrFlag",clientLocale)%> :</td><!--ί���˱�־-->
		<td><SELECT  size="1" name="wtr_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px" onchange="javascript:changerLevels(this.value);">
				<%=Argument.getClientFlagList(wtr_flag)%>
			</SELECT>
		</td>
	</tr>
	<tr id="levelsFlag" <%if(product_id.intValue() == 0 || wtr_flag.intValue()==2){%> style="display: none;"<%}%>>
		<td align="right"><%=LocalUtilis.language("class.prov_level",clientLocale)%> :</td><!--���漶��-->
		<td  align="left"> 
			<SELECT  size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
			   <%=Argument.getProvlevelOptions(prov_level)%>
			</SELECT>						
		</td>
	</tr>
			
	<tr>
		<td align="right" colspan=4>
		<button type="button" class="xpbutton3" name="btnQuery" accessKey="o"
			onclick="javascript:getQueryCondition();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
		</td>
	</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>

