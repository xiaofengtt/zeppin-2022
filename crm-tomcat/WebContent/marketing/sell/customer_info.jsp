<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0)); 
String  crm_cust_no = Utility.trimNull(request.getParameter("crm_cust_no"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
input_bookCode=new Integer(1); 
IntrustCustomerInfoLocal customer = EJBFactory.getIntrustCustomerInfo();
IntrustCustomerInfoLocal cust = EJBFactory.getIntrustCustomerInfo();
IntrustCrmCustInfoLocal crmCust =  EJBFactory.getIntrustCrmCustInfo();
IntrustCustomerInfoLocal syncCust = EJBFactory.getIntrustCustomerInfo();
IntrustCrmCustInfoLocal crmCustList =  EJBFactory.getIntrustCrmCustInfo();
String sReadonly = "readonly class='edline'";
String task=Utility.trimNull(request.getParameter("task"));

String prefix = request.getParameter("prefix");
String cust_name = Utility.trimNull(request.getParameter("cust_name"),null);
String cust_no = Utility.trimNull(request.getParameter("cust_no"),null);
String card_id = Utility.trimNull(request.getParameter("card_id"),null);
String vip_card_id = Utility.trimNull(request.getParameter("vip_card_id"),null);
String hgtzr_bh = Utility.trimNull(request.getParameter("hgtzr_bh"),null);
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));

int readonly=Utility.parseInt(request.getParameter("readonly"),0);
Integer scust_type = Utility.parseInt(request.getParameter("customer_cust_type"), new Integer(1));
customer.setCust_type(Utility.parseInt(request.getParameter("customer_cust_type"),new Integer(2)));
StringBuffer list = Argument.newStringBuffer();
String crm_cust_no2 = Utility.trimNull(request.getParameter("crm_cust_no2"));


if ("list".equals(request.getParameter("task")))
{
	if ((!cust_no.equals("")) || (!card_id.equals("")) || (!cust_name.equals("")) || (!vip_card_id.equals("")) || (!hgtzr_bh.equals("") || is_link.intValue()>0))
   {
		cust.setBook_code(input_bookCode);
		cust.setInput_man(input_operatorCode);
		cust.setCust_no(cust_no);
		cust.setCard_id(card_id);
		cust.setVip_card_id(vip_card_id);
		cust.setHgtzr_bh(hgtzr_bh);
		cust.setCust_name(cust_name);
		cust.setIs_link(is_link);
		cust.queryByCustNo();
		
		crmCustList.setCard_id(card_id);
		crmCustList.setCust_no(cust_no);
		crmCustList.setCust_name(cust_name);
		crmCustList.listCrmCust();
	}
}
int sync_flag =Utility.parseInt(request.getParameter("sync_flag"),-1);
if("sync".equals(request.getParameter("task"))){
	crmCust.listCrmCust();
	sync_flag = 0;	
	crmCust.setCust_no(crm_cust_no2);
	crmCust.load();		
	syncCust.setBook_code(input_bookCode);
	syncCust.setInput_man(input_operatorCode);
	syncCust.setCust_no(crmCust.getCust_no());
	syncCust.setCust_name(crmCust.getCust_name());
	syncCust.setCard_type(crmCust.getCard_type());
	syncCust.setCard_id(crmCust.getCard_id());
	syncCust.setCust_type(crmCust.getCust_type());			
	syncCust.setLegal_man(crmCust.getLegal_man());
	syncCust.setContact_man(crmCust.getContact_man());
	syncCust.setPost_address(crmCust.getPost_address());
	syncCust.setPost_code(crmCust.getPost_code());
	syncCust.setE_mail(crmCust.getE_mail());
	syncCust.setMobile(crmCust.getMobile());
		
	syncCust.syncCrmCust();//同步
	crmCust.modiFlag(); 
		
	cust.setBook_code(input_bookCode);
	cust.setInput_man(input_operatorCode);
	cust.setCust_no(crm_cust_no2);
	cust.setCard_id(crmCust.getCard_id());
	cust.queryByCustNo(); //同步后取 sql2005数据库中的cust_id
	cust.getNextForList();
	cust_id = cust.getCust_id();

		
	sync_flag = 1;
	
}
boolean bSuccess = false;
int iCount = 0;
IntrustProductAddLocal product = EJBFactory.getIntrustProductAddLocal();
product.setBookcode(input_bookCode);
product.setInput_man(input_operatorCode);

if (request.getMethod().equals("POST"))
{
	customer.setBook_code(input_bookCode);
	customer.setInput_man(input_operatorCode);
	customer.setProperties(request, "customer");
	customer.setCust_id(cust_id);
	customer.setContact_man(Utility.trimNull(request.getParameter("customer_contact_man")));
	customer.setGrade_level(Utility.trimNull(request.getParameter("grade_level")));
		
	if("new".equals(request.getParameter("task"))){
		customer.append();
		IntrustProductAddLocal product1 = EJBFactory.getIntrustProductAddLocal();
	    product1.setInput_man(input_operatorCode);
	    product1.setProduct_id(product_id);
		product1.setTb_flag(new Integer(2));
	    product1.listInfo();
	    if(product1.getRowCount()!=0)
		product1.deleteAddInfo();
	    product1.remove();
	    String rows[]=Utility.splitString(Utility.trimNull(request.getParameter("strvalue")),"$");
	    String cols[]=null;
	    for(int i=0;i<rows.length;i++)
	    {
		cols=Utility.splitString(rows[i],"#");
		product.setProduct_id(customer.getCust_id());
		product.setField_caption(Utility.trimNull(cols[0]));
		product.setField_value(Utility.trimNull(cols[1]));
		product.setTb_flag(new Integer(2));
		product.appendInfo();
	   } 
		}
	if("update".equals(request.getParameter("task"))){
		customer.save();
		IntrustProductAddLocal product1 = EJBFactory.getIntrustProductAddLocal();
	    product1.setInput_man(input_operatorCode);
	    product1.setProduct_id(product_id);
		product1.setTb_flag(new Integer(2));
	    product1.listInfo();
	    if(product1.getRowCount()!=0)
		product1.deleteAddInfo();
	    product1.remove();
	    String rows[]=Utility.splitString(Utility.trimNull(request.getParameter("strvalue")),"$");
	   String cols[]=null;
	   for(int i=0;i<rows.length;i++)
	   {
		cols=Utility.splitString(rows[i],"#");
		product.setSerial_no(Utility.parseInt(cols[2],new Integer(0)));
		product.setField_caption(Utility.trimNull(cols[0]));
		product.setField_value(Utility.trimNull(cols[1]));
		product.setTb_flag(new Integer(2));
		product.saveInfo();
	   } 	
		}
	cust_id = customer.getCust_id();
	bSuccess = true;
	task="list";
	
}


if ("new".equals(task) || "update".equals(task))
	sReadonly = "";
	
//选择单一客户信息。查询详细资料 SQL2005		
if (cust_id.intValue()>0 && crm_cust_no.equals(""))
{
	customer.setCust_id(cust_id);
	customer.setInput_man(input_operatorCode);
	customer.load();
	
}

//选择单一客户信息。查询详细资料 DB2	
if (!crm_cust_no.equals(""))
{
	crmCust.setCust_no(crm_cust_no);
	crmCust.load(); 	
	if(crmCust.getCust_type()!=null){
		if( crmCust.getCust_type().intValue()==0){
			customer.setCust_type(new Integer(1));
		}else{
			customer.setCust_type(new Integer(2));
		}
	}
	customer.setCust_name(crmCust.getCust_name());
	customer.setCard_type(crmCust.getCard_type());
	customer.setCard_id(crmCust.getCard_id());	
	customer.setContact_man(crmCust.getContact_man());
	customer.setLegal_man(crmCust.getLegal_man());
	customer.setPost_code(crmCust.getPost_code());
	customer.setE_mail(crmCust.getE_mail());
	customer.setMobile(crmCust.getMobile());
	customer.setCust_no(crmCust.getCust_no());	
	customer.setPost_address(crmCust.getPost_address());
	crm_cust_no2 = crmCust.getCust_no();
	sync_flag=2;
}
String sPrint_deploy_bill = "";

if (customer.getPrint_deploy_bill() != null)
{
	if (customer.getPrint_deploy_bill().intValue() == 1)
		sPrint_deploy_bill = "checked";
}

String sPrint_post_info = "";

if (customer.getPrint_post_info() != null)
{
	if (customer.getPrint_post_info().intValue() == 1)
		sPrint_post_info = "checked";
}

%>

<HTML>
<HEAD>
<TITLE>客户选择</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>

<SCRIPT LANGUAGE="javascript">
function validateForm(form)
{
	if (document.theform.task.value == "new" || document.theform.task.value == "update")
	{
		if(!sl_check(form.customer_cust_name, "客户名称", 100, 1))	return false;
		CustomerInfo.findSameCustInfo(document.theform.customer_cust_name.value,1,document.theform.cust_id.value,dwrCallbackInfo);
	}
	else
	{
		if(document.theform.readonly.value!=1)
		{
			if(document.theform.sync_flag.value!=2)
			{
				if(!sl_checkChoice(document.theform.cust_id, "客户"))	return false;
			}
		}
		okInfo();	
	}
}

function continueCreate()
{
	form = document.theform;
	
	if(!sl_checkChoice(form.customer_cust_source, "客户来源"))	return false;
	if(!sl_checkChoice(form.customer_card_type, "证件类型"))		return false;
	if(!sl_checkChoice(form.customer_touch_type, "联系方式"))		return false;
	if(!sl_checkNum(form.customer_post_code, "邮政编码", 6, 0))	return false;
	if(!sl_check(form.customer_card_id, "证件号码", 40, 1))		return false;
	syncDatePicker(form.customer_vip_date_picker, form.customer_vip_date);
	if(form.customer_cust_type.value==1)
	{
		if(document.theform.customer_birthday_picker.value!="")
		{
			if(!sl_checkDate(document.theform.customer_birthday_picker,"出生日期"))	return false;
					syncDatePicker(form.customer_birthday_picker, form.customer_birthday);
		}
		if(form.customer_age.value != null && form.customer_age.value < 0)
		{
				sl_alert("年龄非法！");
				form.customer_age.focus();
				return false;
		}
	}
	var iCount=form.iCount.value;
 	var strvalue="";
 	for(var i=1;i<=iCount;i++)
 	{
  		if(document.all("fieldvalue_"+String(i)).value!="")
 			strvalue=strvalue+document.all("field_"+String(i)).value+"#"+document.all("fieldvalue_"+String(i)).value+"#"+document.all("fieldno_"+String(i)).value+"$"; 				
 	}
	document.theform.strvalue.value=strvalue.substring(0,strvalue.length-1);
	if(sl_confirm("保存输入信息"))
		document.theform.submit();
}
function dwrCallbackInfo(data)
{
	if(data!="")
	{
		if(confirm("名称为'"+data+"'的客户已经存在，如果确认本客户与已存在客户不是同一客户，请继续！"))
		{
			return continueCreate();
		}
		else
			return false;	
	}
	else
		continueCreate();
}

function dwrCallbackCardInfo(data)
{
	if(data!="")
	{
		sl_alert("证件号"+data+"已经存在，请重新输入！");
		return false;
	}
	else
		continueCreate();
}

function newInfo()
{
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "new";
	document.theform.submit();
}
function updateInfo()
{
	document.theform.method = "get";
	document.theform.task.value = "update";
	document.theform.submit();
}
function queryInfo()
{
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "list";
	disableAllBtn(true);
	document.theform.submit();
}

function okInfo()
{
	/*if(!document.theform.onsubmit()) return false;
	
	document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;
	if (document.theform.task.value == "new" || document.theform.task.value == "update")
	{
		document.theform.submit();
	}
	else*/
	if(document.theform.sync_flag.value==2)
	{
		syncInfo();
	}
	{
		var v = new Array();
		v[0] = document.theform.customer_cust_name.value;
		v[1] = document.theform.customer_cust_type_name.value;
		v[2] = document.theform.customer_card_id.value;
		v[3] = document.theform.customer_h_tel.value;
		v[4] = document.theform.customer_mobile.value;
		v[5] = document.theform.customer_post_address.value;
		v[6] = document.theform.customer_post_code.value;
		if(document.theform.readonly.value==1)
			v[7] = <%=cust_id%>
		else
			v[7] = document.theform.cust_id.value;
		v[9] = document.theform.customer_service_man.value;
		v[8] = document.theform.customer_service_man_value.value;
		v[10]=document.theform.customer_o_tel.value;
		v[11]=document.theform.customer_bp.value;
		if(document.theform.customer_is_link.checked)
			v[12] = 1;
		else
			v[12] = 0;
		window.returnValue = v;

		
		window.close();
	}
}

function showAcctNum(value)
{		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

function changeCustomer(cust_id)
{
	if (cust_id != "")
	{
		var stringArray = cust_id.split('$');
		if(stringArray[0] == 1)
		{
			document.theform.cust_id.value = stringArray[1];
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
	}
}
function changeCustType(value)
{
	document.theform.method = "get";
	document.theform.submit();
}
function optimizeForm()
{
	if(document.theform.readonly.value!=1)
	{
		if (document.theform.task.value == "new")
			document.theform.customer_cust_name.focus();
		else if (document.theform.task.value == "list")
		{
			document.theform.id.focus();
		}	
		else
			document.theform.cust_name.focus();
	}		
}
function setTitle(value)
{
	if(value==1)
	{
		companytitle.style.display='none';
		companycontent.style.display='none';
	}	
	else
	{
		companytitle.style.display='';
		companycontent.style.display='';
	}
	document.theform.method = "get";
	document.theform.task.value = "new";
	document.theform.submit();
}

function getDateByMask(s, m)
{
	if (s.length!=m.length)
	{
		return false;
	}
	var t = m.replace(/d/ig,"\\d").replace(/m/ig,"\\d").replace(/y/ig,"\\d")
	var r = new RegExp("^"+t+"$");
	if (!r.test(s)) return null;
	try
	{
		m=m.replace(/Y/g,"y").replace(/D/g,"d");
		if (m.indexOf("yyyy")>-1)
			return new Date(s.substr(m.indexOf("yyyy"),4),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
		else
			return new Date(s.substr(m.indexOf("yy"),2),s.substr(m.indexOf("MM"),2)-1,s.substr(m.indexOf("dd"),2));
	}catch (e)
	{
		return null;
	}
}

function calculateAge()
{
	if (event.keyCode != 13)	return;
	if (document.theform.customer_card_type.value != '110801')	return;
	var r18  = /^[1-9]\d{5}(\d{8})\d{3}[A-Za-z0-9]$/;
	var r15  = /^[1-9]\d{5}(\d{6})\d{3}$/;
	cardID = cTrim(document.theform.customer_card_id.value,0);
	var current_date = new Date();
	if (r18.test(cardID))
	{
		var t = cardID.match(r18)[1];
		var card_date = getDateByMask(t, "yyyyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear() >= 2000)
				document.theform.customer_age.value =( current_date.getYear() - card_date.getYear());
			else
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(16);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';
		document.theform.customer_birthday_picker.value = cardID.substring(6,10)+"-"+cardID.substring(10,12)+"-"+cardID.substring(12,14);	
	}
	else if (r15.test(cardID))
	{
		
		var t = cardID.match(r15)[1];
		var card_date = getDateByMask(t,"yyMMdd");
		if (card_date != null )
		{
			if(card_date.getYear()>= 2000)
			{
				document.theform.customer_age.value =( current_date.getYear() - (2000+card_date.getYear()));				
				document.theform.customer_birthday_picker.value = (2000+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
			else
			{
				document.theform.customer_age.value =( current_date.getYear() - (1900+card_date.getYear()));
				document.theform.customer_birthday_picker.value = (1900+card_date.getYear())+"-"+cardID.substring(8,10)+"-"+cardID.substring(10,12);				
			}	
		}
		else
			document.theform.customer_age.value = "";
		var flag = cardID.charAt(14);
		if(parseInt(flag) % 2 == 0)
			document.theform.customer_sex.value = '2';
		else
			document.theform.customer_sex.value = '1';		
	}
	else
	{
		sl_alert("身份证号码格式不合法！");
		event.keyCode = 0;
		document.theform.customer_card_id.focus();
	}
}

function CalAge()
{
	if (event.keyCode != 13)	return;
	if(document.theform.customer_cust_type.value==1)
	{
		if(document.theform.customer_birthday_picker.value!="")
		{
			if(!sl_checkDate(document.theform.customer_birthday_picker,"出生日期"))	return false;
			var current_date = new Date();					
			document.theform.customer_age.value = current_date.getYear() - document.theform.customer_birthday_picker.value.substring(0,4);			
		}
	}	
}
//同步客户信息
function syncInfo()
{
	document.theform.method = "get";
	document.theform.task.value = "sync";
	<%
	if(sync_flag==2){
			crmCust.listCrmCust();
	sync_flag = 0;	
	crmCust.setCust_no(crm_cust_no2);
	crmCust.load();		
	syncCust.setBook_code(input_bookCode);
	syncCust.setInput_man(input_operatorCode);
	syncCust.setCust_no(crmCust.getCust_no());
	syncCust.setCust_name(crmCust.getCust_name());
	syncCust.setCard_type(crmCust.getCard_type());
	syncCust.setCard_id(crmCust.getCard_id());
	syncCust.setCust_type(crmCust.getCust_type());			
	syncCust.setLegal_man(crmCust.getLegal_man());
	syncCust.setContact_man(crmCust.getContact_man());
	syncCust.setPost_address(crmCust.getPost_address());
	syncCust.setPost_code(crmCust.getPost_code());
	syncCust.setE_mail(crmCust.getE_mail());
	syncCust.setMobile(crmCust.getMobile());
		
	syncCust.syncCrmCust();//同步
	crmCust.modiFlag(); 
		
	cust.setBook_code(input_bookCode);
	cust.setInput_man(input_operatorCode);
	cust.setCust_no(crm_cust_no2);
	cust.setCard_id(crmCust.getCard_id());
	cust.queryByCustNo(); //同步后取 sql2005数据库中的cust_id
	cust.getNextForList();
	cust_id = cust.getCust_id();		
	sync_flag = 1;
	}%>
	//document.theform.submit();	
}
//检查是否与黑名单内容有匹配
function checkBackList(){
	var url = 'black_list_check.jsp?cust_id=<%=cust_id%>&full_name_c='+ document.theform.customer_cust_name.value
			+ '&card_type='+document.theform.customer_card_type.value+'&card_no='+document.theform.card_id.value;
	var f = showModalDialog(url,'','dialogTop:500;dialogWidth:850px;dialogHeight:400px;status:0;help:0;');
	if(f!= null){
		sl_alert("此客户为限制交易用户");
		if(<%=readonly!=1%>){
			queryInfo();
		}
	}
}

<%if (bSuccess){%>
	sl_update_ok();
<%}%>


</script>
<BODY class="body" onkeydown="javascript:chachEsc(window.event.keyCode)" onload="javascript:optimizeForm()">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="../customer_info2.jsp">
<input type="hidden" name="prefix" value="<%=Utility.trimNull(prefix)%>">
<input type="hidden" name="task" value="<%=task%>">
<input type="hidden" name="is_dialog" value="1">
<input type="hidden" name="readonly" value="<%=readonly%>">
<input type='hidden' name="customer_service_man_value"  value='<%=Utility.trimNull(Argument.getOpName(customer.getService_man()))%>'>
<input type="hidden" name="product_id" value="<%=Utility.trimNull(product_id)%>" > 
<input type="hidden" name="strvalue">
<input type="hidden" name="sync_flag" value="<%=sync_flag%>">
<input type="hidden" name="crm_cust_no">
<input type="hidden" name="crm_cust_no2" value="<%=crm_cust_no2%>">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
	<TBODY>
		<TR>
			<TD>

			<table border="0" width="100%" cellspacing="0" cellpadding="1">
				<tr>
				<td><img border="0" src="/images/ico_area.gif" width="32" height="32"><b>客户信息</b></td>
				</tr>
				<tr  <%if (readonly==1) out.print("style='display:none'");%>>
					<td align=right>证件号码:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" title="客户如果是个人，输入完成后回车自动显示客户对应的性别，生日，年龄" type="text"  name="card_id" size="25" value="<%=Utility.trimNull(request.getParameter("card_id"))%>"></td>
					<td align=right>客户编号:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text"  name="cust_no" size="20" value="<%=Utility.trimNull(request.getParameter("cust_no"))%>"></td>
				</tr>
				<tr  <%if (readonly==1) out.print("style='display:none'");%>>
					<td align=right>VIP卡编号:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text"  name="vip_card_id" size="25" value="<%=Utility.trimNull(request.getParameter("vip_card_id"))%>"></td>
					<td align=right>合格投资人编号:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text"  name="hgtzr_bh" size="20" value="<%=Utility.trimNull(request.getParameter("hgtzr_bh"))%>"></td>
				</tr>
				<tr>
					<td align=right><%if (readonly==0) out.print("客户名称:");%></td>
					<td><%if (readonly==0){%><input onkeydown="javascript:nextKeyPress(this);" type="text"  name="cust_name" size="25" value="<%=Utility.trimNull(request.getParameter("cust_name"))%>"></td>
					<td align=right>是否关联方:</td>
					<td><input class="flatcheckbox" onkeydown="javascript:nextKeyPress(this)" type="checkbox"  name="is_link" value="1" <%if(is_link.intValue()==1) out.print("checked");%>></td>
				</tr>		
				<tr>
					<td align=right>&nbsp;</td>
					<td colspan="3"><button type="button"  class="xpbutton3" accessKey=q onclick="javascript:queryInfo();">搜索(<u>Q</u>)</button>
					<%}if(readonly!=1){%>
					&nbsp;<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
					<%}%>
					<%if(customer.getModi_flag()==1){%>
					&nbsp;<button type="button"  class="xpbutton3" accessKey=u id="btnUpdate" name="btnUpdate" onclick="javascript:updateInfo();">修改(<u>U</u>)</button>
					&nbsp;<button type="button"  class="xpbutton3" accessKey=u id="btnCheck" name="btnCheck" onclick="javascript:checkBackList();">检查(<u>C</u>)</button>
					<%}%>
					<%//if(customer.getModi_flag()==1){%>
					&nbsp;<button type="button"  class="xpbutton3" accessKey=s id="btnSync" name="btnSync" onclick="javascript:syncInfo();" style="display: none;">同步(<u>S</u>)</button>
					<%//}%>
					</td>
					
				</tr>	
				<tr <%if (sReadonly.equals("") || readonly==1) out.print("style='display:none'");%>>
					<td align="right">搜索结果:</td>
					<td colspan="4">
					<select onkeydown="javascript:nextKeyPress(this)" size="1"  name="id"  onchange="javascript: changeCustomer(this.value);">
						<option value="">请选择</option>
<%if (!"list".equals(request.getParameter("task")) && cust_id.intValue()>0){%>
						<option selected value="<%=cust_id%>"><%=Utility.trimNull(Utility.trimNull(customer.getCust_name())+"-"+ Utility.trimNull(customer.getCard_type_name())+"-"+ Utility.trimNull(customer.getCard_id()))%></option>
<%}%>

<%

while (cust.getNextForList())
{
%>
						<option <%if(cust.getCust_id().equals(cust_id)) out.print("selected");%> value="1$<%=cust.getCust_id()%>"><%=cust.getCust_name()+"-"+ cust.getCard_type_name()+"-"+ cust.getCard_id()%></option>
<%}


while (crmCustList.getNext())
{
String card_type = "";
if(crmCustList.getCust_type() !=null && crmCustList.getCust_type().intValue()==0){
	card_type= Argument.getCardTypeName(crmCustList.getCard_type());
}
if(crmCustList.getCust_type() !=null && crmCustList.getCust_type().intValue()==1){
	card_type= Argument.getCardTypeJgName(crmCustList.getCard_type());
}%>
						<option <%if(crmCustList.getCust_no().equals(crm_cust_no)) out.print("selected");%> value="2$<%=crmCustList.getCust_no()%>"><%=crmCustList.getCust_name()+"-"+ card_type+"-"+ crmCustList.getCard_id()%>(CRM)</option>
<%}%>


					</select>
					</td>
				</tr>
<%if ("list".equals(request.getParameter("task"))){
	if(cust.getRowCount()==0 ){%>
	<SCRIPT LANGUAGE="javascript">
		confirm('本地资源没有搜索到满足条件的客户信息，或者该客户你无权访问!');
		//if(confirm('本地资源没有搜索到满足条件的客户信息，或者该客户你无权访问!'))
			//newInfo();
	</SCRIPT>	
<%}
}%>					
				<tr>
					<td colspan="4">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
				<tr>
					<td align="right">*客户类型:</td>
					<td >
					<input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_type_name" size="18" maxlength="20" value="<%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("个人");else out.print("机构");}%>">
					<SELECT size="1"  <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange="javascript:changeCustType(this.value);"  name="customer_cust_type" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 120px">
							<%=Argument.getCustTypeOptions2(customer.getCust_type())%>
						</SELECT>	
					</td>
				
					<td align="right">*客户名称:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="40" maxlength="60" value="<%=Utility.trimNull(customer.getCust_name())%>"></td>
				</tr>
											<tr>
				<td align="right">
				  <%if(cust_id==null)	{ %>								
				   <% if(scust_type.intValue()==1)  {%>
					  职业:
				<%} else {%>	  
					  行业:
				   <%}%>	   											
				<%}
else {%>							
				<% if(customer.getCust_type().intValue()==1)  {%>
					  职业:
				<%} else {%>	  
					  行业:
				   <%}%>
				<%}%>	
				</td>
				<td colspan=3>
<%if(cust_id==null)	{ %>	
			    <input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="18" maxlength="20" value="">
				<select size="1"  onkeydown="javascript:nextKeyPress(this)"  name="customer_zyhy_type" style="WIDTH: 238px" >
				   <% if(scust_type.intValue()==1)  {%>
					  <%=Argument.getGrzyOptions2("0")%>
				<%} else {%>	  
					   <%=Argument.getJghyOptions2("0")%>
				   <%}%>	   
				</select>											
				<%}
else {%>
				<input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_voc_type_name" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getVoc_type_name())%>">			
				<select size="1" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_zyhy_type" style="WIDTH: 238px">
					 <% if(customer.getCust_type().intValue()==1)  {%>
					  <%=Argument.getGrzyOptions2(customer.getVoc_type())%>
				<%} else {%>	  
					   <%=Argument.getJghyOptions2(customer.getVoc_type())%>
				   <%}%>
				</select>
				<%}%>				
				</td>
				</tr>
				<tr>	
					<td align="right">客户代码:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_no" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getCust_no())%>"></td>
					<td align="right">*客户来源:</td>
					<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_source_name" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getCust_source_name())%>">
					<%}else{%>
						<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
							<%=Argument.getCustomerSourceOptions(customer.getCust_source())%>
						</select>
					<%}%>
					</td>	
				</tr>
				<tr>
					<td align="right">风险等级:</td>   
					<td colspan="3">
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_grade_level" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getGrade_level_name())%>">
					<%}else{%>	
						<select name="grade_level" style="WIDTH: 120px"><%=Argument.getRiskGrade(customer.getGrade_level()) %></select>
					<%}%>
					</td>	
				</tr>
				<tr>	
					<td align="right">VIP卡编号:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_vip_card_id" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getVip_card_id())%>"></td>
					<td align="right">合格投资人编号:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_hgtzr_bh" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getHgtzr_bh())%>"></td>	
				</tr>
				<tr>
				<td align="right">VIP发卡日期:</td>
				<td>
					<INPUT TYPE="text" <%=sReadonly%> NAME="customer_vip_date_picker" class=selecttext 
					<%if(customer.getVip_date()==null){%> value=""
					<%}else{%>value="<%=Format.formatDateLine(customer.getVip_date())%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
					<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_vip_date_picker,theform.customer_vip_date_picker.value,this);" tabindex="13">
					<%}%><INPUT TYPE="hidden" NAME="customer_vip_date"   value="">
				</td>
				<td align="right">客户经理:</td> 
				<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_service_man" size="25" maxlength="20" value="<%=Utility.trimNull(Argument.getOpName(customer.getService_man()))%>">
					<%}else{%>	
					<select size="1" name="customer_service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getRoledOperatorOptions(input_bookCode, 2, "update".equals(request.getParameter("task"))?customer.getService_man():input_operatorCode)%>
					</select>
					<%}%>
			</tr>		
				<tr>
					<td align="right">*证件类型:</td>
					<td>
						<input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="25" maxlength="25" value="<%=Utility.trimNull(customer.getCard_type_name())%>">
					   <%
						cust_id=customer.getCust_id();
						Integer cust_type=customer.getCust_type();
						if(cust_type==null)
							cust_type=new Integer(2);
						
						if(cust_id==null)	
						{
							if(cust_type.intValue()==1)  
							{
%>				
				<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%>  onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeOptions2("0")%>
				</select>
				<%}
				else {
				%>				
				<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeJgOptions2("0")%>
				</select>
					<%}}else 
					{
					if(cust_type.intValue()==1)  {
				%>				
				<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeOptions2(customer.getCard_type())%>
				</select>
				<%}
				else {
				%>					
				<select size="1" onkeydown="javascript:nextKeyPress(this)" <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange='javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;' name="customer_card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeJgOptions2(customer.getCard_type())%>
				</select>
				<%}
				}%>		
					<td align="right">*证件号码:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:calculateAge();nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" name="customer_card_id" size="25" maxlength="30" value="<%=Utility.trimNull(customer.getCard_id())%>">
					<span id="bank_acct_num" class="span"></span></td>	
				</tr>
<%if(customer.getCust_type().intValue()!=1){%>
				<tr>
					<td align="right">法人姓名:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_legal_man" size="25" maxlength="30" value="<%=Utility.trimNull(customer.getLegal_man())%>"></td>
					<td align="right">联系人:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_contact_man" size="25" maxlength="20" value="<%=Utility.trimNull(customer.getContact_man())%>"></td>
				</tr>	
				
<%}
if(cust_id==null&&cust_type.intValue()==1) {
%>
			<tr>
				<td align="right">出生日期:</td>
				<td>
					<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext 
					<%if(customer.getBirthday()==null){%> value=""
					<%}else{%>value="<%=Format.formatDateLine(customer.getBirthday())%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
					<%if(sReadonly.equals("")){%><INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
					<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
				</td>
				<td align="right"></td>
				<td></td>
			</tr>
<%}
else if(cust_id!=null&&customer.getCust_type().intValue()==1) {%>
			<tr>
				<td align="right">出生日期:</td>
				<td>
					<INPUT TYPE="text" <%=sReadonly%> NAME="customer_birthday_picker" class=selecttext 
					<%if(customer.getBirthday()==null){%> value=""
					<%}else{%>value="<%=Format.formatDateLine(customer.getBirthday())%>"<%}%> onkeydown="javascript:CalAge();nextKeyPress(this)">
					<%if(sReadonly.equals("")){%>
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.customer_birthday_picker,theform.customer_birthday_picker.value,this);" tabindex="13">
					<%}%><INPUT TYPE="hidden" NAME="customer_birthday"   value="">
				</td>
				<td align="right"></td>
				<td></td>
			</tr>
<%}
if(customer.getCust_type().intValue()==1) {
%>	
			<tr>
				<td align="right">年龄:</td>
				<td><INPUT <%=sReadonly%> name="customer_age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="4" value="<%=Utility.trimNull(customer.getAge())%>"></td>
				<td align="right">性别:</td>
				<td>
				
				<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_sex_name" size="25" maxlength="20" value="<%=Utility.trimNull(customer.getSex_name())%>">
					<%}else{%>	
					<SELECT size="1" onkeydown="javascript:nextKeyPress(this)" name="customer_sex" style="WIDTH: 120px">
					<%=Argument.getSexOptions(customer.getSex())%>
				</SELECT>
					<%}%></td>
			</tr>
<%}%>
				<tr>	
					<td align="right">家庭电话:</td>
					<td><input <%=sReadonly%> name="customer_h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=Utility.trimNull(customer.getH_tel())%>"></td>
					<td align="right">公司电话:</td>
					<td><INPUT <%=sReadonly%> name="customer_o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=Utility.trimNull(customer.getO_tel())%>"></td>
				</tr>
				<tr>
					<td align="right">手机:</td>
					<td><input <%=sReadonly%> name="customer_mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=Utility.trimNull(customer.getMobile())%>"></td>
					<td align="right">手机2:</td>
					<td><INPUT <%=sReadonly%> name="customer_bp" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=Utility.trimNull(customer.getBp())%>"></td>
				</tr>
				<tr>
					<td align="right">传真:</td>
					<td><input <%=sReadonly%> name="customer_fax" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=Utility.trimNull(customer.getFax())%>"></td>
					<td align="right">Email:</td>
					<td><INPUT <%=sReadonly%> name="customer_e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="40" value="<%=Utility.trimNull(customer.getE_mail())%>"></td>
					
				</tr>
				<tr>
					<td align="right">邮政编码:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code" size="25" maxlength="6" value="<%=Utility.trimNull(customer.getPost_code())%>"></td>	
					<td align="right">邮寄地址:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address" size="40" maxlength="60" value="<%=Utility.trimNull(customer.getPost_address())%>"></td>
					
				</tr>
				<tr>
					<td align="right">邮政编码2:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_code2" size="25" maxlength="6" value="<%=Utility.trimNull(customer.getPost_code2())%>"></td>
					
					<td align="right">邮寄地址2:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_post_address2" size="40" maxlength="60" value="<%=Utility.trimNull(customer.getPost_address2())%>"></td>
				</tr>
				<tr>
					<td align="right">*联系方式:</td>
					<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this);" name="customer_touch_type_name" size="25" maxlength="20" value="<%=Utility.trimNull(customer.getTouch_type_name())%>">
					<%}else{%>	
					<select size="1" name="customer_touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getTouchTypeOptions(customer.getTouch_type())%>
					</select>
					<%}%>
					</td>
					<td align="right">关联方:</td>
					<td ><input type="checkbox" name="customer_is_link" value="<%=customer.getIs_link()%>" <%if(customer.getIs_link()!=null){if(customer.getIs_link().intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
					<tr>
				</tr>
				<tr>	
					<td width="27%" align="right">打印客户对帐单:</td>
					<td  ><input class="flatcheckbox" type="checkbox" name="customer_print_deploy_bill" <%=sPrint_deploy_bill%> value="1"></td>
					<td  align="right">打印披露信息:</td>
					<td ><input class="flatcheckbox" type="checkbox" name="customer_print_post_info" <%=sPrint_post_info%> value="1"></td>
				</tr>
				
									<tr <%if(cust_id==null)out.print("style='display:none'");%>>
				 <td colspan=4>
				    <TABLE  cellSpacing=0 cellPadding=4 align=center border=0>
  <TBODY>
       <TR> 
           <TD align="left">
			<table border="0" width="100%">        
			
<%
IntrustProductAddLocal local1 = EJBFactory.getIntrustProductAddLocal();
local1.setBookcode(input_bookCode);
local1.setTb_flag(new Integer(2));
local1.list();
if(local1.getRowCount()>0)
{

%>	  <tr >
           <td colspan=4>
			<b>客户自定义要素设置:<br>&nbsp;</b>
			</td></tr>
			<td  align="center" height="25" ></td>
			<td colspan=3>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh">
						<td  align="center" height="25" >要素名称</td>
						<td  align="center" height="25" >要素值</td>
						<td  align="center" height="25" >要素说明</td>
					</tr>
<% iCount = 0;
  int iCurrent = 0;	 
  int j=0;
while(local1.getNext() && iCurrent < local1.getPageSize()){

    iCount++;	
	IntrustProductAddLocal local2 = EJBFactory.getIntrustProductAddLocal();
	local2.setBookcode(input_bookCode);
	local2.setProduct_id(cust_id);	
	local2.setField_caption(local1.getField_caption());
	local2.setTb_flag(new Integer(2));
	local2.listInfo();
	local2.getNextInfo();	
	String fieldvalue="";
	if(cust_id!= null && cust_id.intValue()!=0)
	  fieldvalue=Utility.trimNull(local2.getField_value());
%>
				<tr class="tr<%=(iCurrent % 2)%>" >
					<td align="right" height="25">
						<input class="ednoline" type="text" readonly name="field_<%=iCount%>" size="28" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(local1.getField_caption())%>">
					</td>	
					<td align="center" height="25">
						<%=Utility.trimNull(local2.getField_value())%>
					</td>
					<td align="left" width="250" height="25">
						<%=Utility.trimNull(local1.getSummary().replace('@',' '))%>
					</td>
				</tr>
				<input type="hidden" name="fieldno_<%=iCount%>" value="<%=Utility.parseInt(local2.getSerial_no(),new Integer(0))%>"> 
<%local2.remove();}%>
		</table>
<%}%>
<input type="hidden" name="iCount" value="<%=iCount%>" >  	              
		</table>
			</TD></TR>
    </TBODY>
</table>
				 </td>
				</tr>
			
				<tr>	
					<td  align="right">备注:</td>
					<td colspan=3><textarea <%if (!sReadonly.equals("")) out.print("readonly class='edline_textarea'");%> rows="3" name="customer_summary" cols="86"><%=Utility.trimNull(customer.getSummary())%></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="4">
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);"><%if ("new".equals(task) || "update".equals(task)){%>保存<%}else{%>确定<%}%>(<u>S</u>)</button>
							&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
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
</form>
<%@ include file="/includes/foot.inc"%>
<script language="javascript">
<%//if (sync_flag == 0){%>
	//sl_alert("暂无可同步的客户信息");
<%//}%>
<%//if (sync_flag == 1){%>
	//sl_alert("同步客户信息成功");
<%//}%>
</script>
</BODY>

</HTML>
<%
cust.remove();
customer.remove();
crmCust.remove();
syncCust.remove();
crmCustList.remove();
%>
