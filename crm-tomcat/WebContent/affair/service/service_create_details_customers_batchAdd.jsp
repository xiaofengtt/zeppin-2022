<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ò���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer needFeedback = Utility.parseInt(request.getParameter("needFeedback"), new Integer(0));
Integer action_flag = Utility.parseInt(request.getParameter("action_flag"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
//��ѯ����
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
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//��͹�����
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//��߹�����
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//���������
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//���������
String touch_type = Utility.trimNull(request.getParameter("touch_type"));//��ϵ��ʽ
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//��ϵ�绰
String post_address = Utility.trimNull(request.getParameter("post_address"));//��ϵ��ַ
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(-1));//��ƷID
Utility.debug(request.getParameter("product_id"));
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));//�Ƿ񰴹�������ѯ
Integer onlyemail = Utility.parseInt(request.getParameter("onlyemail"), new Integer(0));//�Ƿ�����ʼ�
String family_name = Utility.trimNull(request.getParameter("family_name"));//��ͥ����
String sort_name = Utility.trimNull(request.getParameter("sort_name"));//�����ֶ�
String prov_level = Utility.trimNull(request.getParameter("prov_level"));//���漶��
Integer wtr_flag = Utility.parseInt(Utility.trimNull(request.getParameter("wtr_flag")),new Integer(0));//ί���˱�־
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
String q_country = Utility.trimNull(request.getParameter("q_country"));
String q_money_source = Utility.trimNull(request.getParameter("q_money_source"));
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),new Integer(0));
String referee = Utility.trimNull(request.getParameter("referee"));
String classEs = Utility.trimNull(request.getParameter("classEs"));
String telephone = Utility.trimNull(request.getParameter("telephone"));
Integer start_age = Utility.parseInt(request.getParameter("start_age"),null);
Integer end_age = Utility.parseInt(request.getParameter("end_age"),new Integer(0));
String productTo = Utility.trimNull(request.getParameter("productTo"));
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),null);
int cell_flag = Utility.parseInt(request.getParameter("cell_flag"),1);
Integer birthday_end = Utility.parseInt(request.getParameter("birthday_end"),new Integer(0));
Integer rg_begin_date = Utility.parseInt(request.getParameter("rg_begin_date"),new Integer(0));
Integer rg_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(0));

StringBuffer sortlist = Argument.newStringBuffer();//�����ֶ��б�"<option value=\"\">��ѡ��</option>");
Argument.appendOptions(sortlist, "CUST_NAME", enfo.crm.tools.LocalUtilis.language("class.customerName",clientLocale), sort_name);//�ͻ�����
Argument.appendOptions(sortlist, "TOTAL_MONEY", enfo.crm.tools.LocalUtilis.language("message.amountPurchased",clientLocale), sort_name);//�ѹ����
Argument.appendOptions(sortlist, "LAST_RG_DATE", enfo.crm.tools.LocalUtilis.language("message.lastRgDate",clientLocale), sort_name);//�������ʱ��
Argument.appendOptions(sortlist, "CURRENT_MONEY", enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale), sort_name);//�������
Argument.appendOptions(sortlist, "BEN_AMOUNT", enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale), sort_name);//����ݶ�

String tempView = Argument.getMyMenuViewStr(menu_id,v_op_code);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}
//��ö���
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
Map fieldsMap = Argument.getMenuViewMap("20301",viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}

//��������
vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setCust_type(cust_type);
vo.setCust_level(cust_level);
vo.setCust_source(cust_source);
vo.setInput_man(input_operatorCode);
vo.setCard_type(card_type);
vo.setCard_id(card_id);
vo.setVip_card_id(vip_card_id);
vo.setHgtzr_bh(hgtzr_bh);
vo.setBirthday(birthday);
vo.setMin_times(start_rg_times);
vo.setMax_times(end_rg_times);
vo.setMin_total_money(min_total_money);
vo.setMax_total_money(max_total_money);
vo.setBen_amount_min(ben_amount_min);
vo.setBen_amount_max(ben_amount_max);
vo.setTouch_type(touch_type);
//vo.setCust_tel(cust_tel);
vo.setPost_address(post_address);
vo.setProduct_id(product_id);
vo.setIs_link(is_link);
vo.setOnlyemail(onlyemail);
vo.setFamily_name(family_name);
vo.setOrderby(sort_name);
vo.setProv_level(prov_level);
vo.setWt_flag(wtr_flag);
vo.setGroupID(group_id);
vo.setIs_deal(is_deal);
vo.setMoney_source(q_money_source);
vo.setCountry(q_country);
vo.setRecommended(referee);
vo.setService_man(accountManager);
vo.setClassEs(classEs);
vo.setProductTo(productTo);
vo.setStart_age(start_age);
vo.setEnd_age(end_age);
vo.setH_tel(telephone);
vo.setCell_id(cell_id);
vo.setBirthday_end(birthday_end);
vo.setRg_begin_date(rg_begin_date);
vo.setRg_end_date(rg_end_date);

String totalValueStr = "CURR_TO_AMOUNT$TOTAL_MONEY$CURRENT_MONEY$BEN_AMOUNT$EXCHANGE_AMOUNT$BACK_AMOUNT";
String[] totalValue = Utility.splitString(totalValueStr,"$");
//��ÿͻ���Ϣ

int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
Iterator it = null;
if(first_flag != 1)
	pageList = local.listProcAllExt(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, -1));
if(pageList != null)
	it = pageList.getRsList().iterator();
Map map = new HashMap();
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
			+ "&classEs="+classEs
			+ "&productTo="+productTo
			+"&start_age="+start_age
			+"&end_age="+end_age
			+"&telephone="+telephone
			+"&cell_id="+cell_id
			+"&cell_flag="+cell_flag
			+ "&birthday_end"+birthday_end
			+ "&rg_begin_date"+rg_begin_date
			+ "&rg_end_date"+rg_end_date;


//������������
boolean bSuccess = false;
//��ö���
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo_cust = new CustomerVO();
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo_service = new ServiceTaskVO();
//��ӿͻ�
if(action_flag.intValue()==2){
	//��ȡ����ID 
	String[] temp_checks = request.getParameterValues("selectbox");
	Integer t_cust_id;
	
	vo_service.setTaskSerialNO(serial_no);
	vo_service.setNeedFeedBack(needFeedback);	
	vo_service.setInputMan(input_operatorCode);
	
	//������
	if(temp_checks!=null){
System.out.println(temp_checks.length);
		for(int i=0;i<temp_checks.length;i++){
			t_cust_id = Utility.parseInt(temp_checks[i], new Integer(0));
			
			if(t_cust_id.intValue()!=0){				
				vo_service.setTargetCustID(t_cust_id);			
				serviceTask.append_details(vo_service);	
			}
		}
		
		action_flag = new Integer(0);
		bSuccess = true;
	}
}
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> </title><!-- �ͻ��������� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
/*�ͻ���Ϣ��ʾ����*/
function showQueryInfo(){
	var s=5;
	var minheight=1;
	var maxheight=200;	
	var flag = document.getElementById("show_image").title;

	if(document.getElementById("query_info").style.pixelHeight==0){
		document.getElementById("query_info").style.pixelHeight=minheight;
	}
	
	if(flag=="next"){
		 document.getElementById("query_info").style.pixelHeight+=s;
		
		 if(document.getElementById("table_view").style.pixelHeight>200){
		 	document.getElementById("table_view").style.pixelHeight-=s;
		 }
		 
		 if(document.getElementById("query_info").style.pixelHeight<maxheight){
		   setTimeout("showQueryInfo()",1);
		 }else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/previous_down.gif";
		    document.getElementById("show_image").title = "previous_down";
		 }		
		
	}else if(flag=="previous_down"){
		document.getElementById("query_info").style.pixelHeight-=s;
		document.getElementById("tableList").style.pixelHeight+=s;
		//document.getElementById("tableList").style.height=document.getElementById("tableList").style.height+s;
		
		if(document.getElementById("query_info").style.pixelHeight>minheight){
		   setTimeout("showQueryInfo()",1);
		}else{		
		    document.getElementById("show_image").src = "<%=request.getContextPath()%>/images/next.gif";
		    document.getElementById("show_image").title = "next";
		}			
	}
}

function checkCust(){
	var returnV =[];
	var cust_id = window.frames["ifrPage"].document.theform.cust_id;
	for(i=1;i<cust_id.length;i++){
		if(cust_id[i].checked){
			returnV.push(cust_id[i].value);
		}
	}
	return returnV;
}

function selectAllBox2(){
	var cust_id = window.frames["ifrPage"].document.theform.cust_id;
	
}

/*����*/
function submitAction(action_flag){		
	var form = document.getElementsByName("theform")[0];
	document.getElementsByName("action_flag")[0].value = action_flag;	
	if(checkedCount(document.getElementsByName("selectbox")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCustomers",clientLocale)%> ��");//��ѡ��Ҫ��ӵĿͻ�
		return false;
	}
	form.action = "<%=request.getContextPath()%>/affair/service/service_create_details_customers_batchAdd.jsp";
	form.submit();	
}



function setProduct(value){
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

/*ȡ��*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}
/*��ҳ*/
function refreshPage()
{
	location = 'service_create_details_customers_batchAdd.jsp?page=<%=sPage%>&pagesize='+ document.theform.pagesize.value + getQueryCondition();
}

/*��ѯ*/
function advancedQuery(){
	var v = showModalDialog('/client/analyse/client_query_all.jsp?'+getQueryCondition(),'','dialogWidth:600px;dialogHeight:640px;status:0;help:0');
	
	if(v!=null)
	{
		url = '<%=request.getContextPath()%>/affair/service/service_create_details_customers_batchAdd.jsp?cust_no='+v[0]
								+'&cust_name='      +v[1]
								+'&cust_source='    +v[2]
								+'&cust_type='      +v[3]
								+'&card_type='      +v[4]
								+'&card_id='        +v[5]
								+'&vip_card_id='    +v[6]
								+'&hgtzr_bh='       +v[7]
								+'&birthday='       +v[8]
								+'&cust_level='     +v[9]
								+'&start_rg_times=' +v[10]
								+'&end_rg_times='   +v[11]
								+'&min_total_money='+v[12]
								+'&max_total_money='+v[13]
								+'&ben_amount_min=' +v[14]
								+'&ben_amount_max=' +v[15]
								+'&touch_type='     +v[16]
								//+'&cust_tel='     
								+'&post_address='   +v[17]
								+'&product_id='     +v[18]
								+'&is_link='        +v[19]
								+'&onlyemail='      +v[20]
								+'&family_name='    +v[21]
								+'&sort_name='      +v[22]
								+'&prov_level='     +v[23]
								+'&wtr_flag='       +v[24]
								+'&group_id='       +v[25]
								+'&q_money_source=' +v[26]
								+'&q_country='      +v[27]
								+'&is_deal='        +v[28]
								+'&accountManager=' +v[29]
								+'&referee='        +v[30]
								+'&classEs='        +v[31]
								+'&productTo='		+v[32]
								+'&start_age='		+v[33]
								+'&end_age='		+v[34]
								+'&telephone='		+v[35] 
								+'&cell_id='		+v[36]
								+'&cell_flag='		+v[37]
								+'&birthday_end='   +v[38]
								+'&rg_begin_date='	+v[39]
								+'&rg_end_date='	+v[40];
		var form = document.getElementsByName("theform")[0];
		form.action = url;
		form.submit();
	}	
}
/*��ò�ѯ����*/
function getQueryCondition()
{
	var sQuery =   '&cust_no=<%=Utility.trimNull(cust_no)%>'        
					+'&cust_name=<%=Utility.trimNull(cust_name)%>'      
					+'&cust_source=<%=Utility.trimNull(cust_source)%>'    
					+'&cust_type=<%=Utility.trimNull(cust_type)%>'      
					+'&card_type=<%=Utility.trimNull(card_type)%>'      
					+'&card_id=<%=Utility.trimNull(card_id)%>'        
					+'&vip_card_id=<%=Utility.trimNull(vip_card_id)%>'    
					+'&hgtzr_bh=<%=Utility.trimNull(hgtzr_bh)%>'   
					    
					+'&birthday=<%=Utility.trimNull(birthday)%>'       
					+'&cust_level=<%=Utility.trimNull(cust_level)%>'     
					+'&start_rg_times=<%=Utility.trimNull(start_rg_times)%>' 
					+'&end_rg_times=<%=Utility.trimNull(end_rg_times)%>'   
					+'&min_total_money=<%=Utility.trimNull(min_total_money)%>'
					+'&max_total_money=<%=Utility.trimNull(max_total_money)%>'
					+'&ben_amount_min=<%=Utility.trimNull(ben_amount_min)%>' 
					+'&ben_amount_max=<%=Utility.trimNull(ben_amount_max)%>' 
					+'&touch_type=<%=Utility.trimNull(touch_type)%>'     
					//+'&cust_tel='     
					+'&post_address=<%=Utility.trimNull(post_address)%>'   
					+'&product_id=<%=Utility.trimNull(product_id)%>'     
					+'&is_link=<%=Utility.trimNull(is_link)%>'        
					+'&onlyemail=<%=Utility.trimNull(onlyemail)%>'     
					 
					+'&family_name=<%=Utility.trimNull(family_name)%>'    
					+'&sort_name=<%=Utility.trimNull(sort_name)%>'      
					+'&prov_level=<%=Utility.trimNull(prov_level)%>'     
					+'&wtr_flag=<%=Utility.trimNull(wtr_flag)%>'       
					+'&group_id=<%=Utility.trimNull(group_id)%>'       
					+'&q_money_source=<%=Utility.trimNull(q_money_source)%>' 
					+'&q_country=<%=Utility.trimNull(q_country)%>'      
					+'&is_deal=<%=Utility.trimNull(is_deal)%>'        
					+'&accountManager=<%=Utility.trimNull(accountManager)%>' 
					+'&referee=<%=Utility.trimNull(referee)%>'  
					+'&classEs=<%=Utility.trimNull(classEs)%>'
					+'&productTo=<%=Utility.trimNull(productTo)%>'
					+'&start_age=<%=Utility.trimNull(start_age)%>'
					+'&end_age=<%=Utility.trimNull(end_age)%>'
					+'&telephone=<%=Utility.trimNull(telephone)%>'
					+'&cell_id=<%=Utility.trimNull(cell_id)%>'
					+'&cell_flag=<%=cell_flag%>'
					+'&birthday_end=<%=birthday_end%>'
					+'&rg_begin_date=<%=rg_begin_date%>'
					+'&rg_end_date=<%=rg_end_date%>';      
  
	return sQuery;
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="action_flag" value="<%=action_flag%>"/><!--1 ��ѯ ��û���õ� 2 ������ϸ�ͻ�ID -->
<input type="hidden" name="cust_id" value=""/>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=6 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right class="btn-wrapper">
							<button type="button" class="xpbutton3" accessKey=f id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"
								onclick="javascript:advancedQuery();"><%=LocalUtilis.language("message.query",clientLocale)%>(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center"><input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!--����-->
						<td align="center"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> </td><!--֤������-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--֤������-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--�ͻ����-->
						<td align="center"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!--�ֻ�����-->
						<td align="center"><%=LocalUtilis.language("class.email",clientLocale)%></td><!--EMAIL-->
						<td align="center">����Ϲ�����</td>
						<td align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--�ͻ�����-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						//Integer cust_id = new Integer(0);
						while (it!= null && it.hasNext()) {
							 map = (Map) it.next();
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" width="15%">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%"><input type="checkbox" name="selectbox" value="<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
									<td width="90%" align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
								</tr>
							</table>
						</td>
						<td align="left"><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("MOBILE"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("E_MAIL"))%></td>
						<td align="left"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("LAST_RG_DATE")),new Integer(0)))%></td>
						<td align="left"><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0))))%></td>
					</tr>
					<%iCurrent++;
					iCount++;
					}%>
				
					<%int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 8);
					for (; iCurrent < page_size; iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>		
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--�ϼ�--><!--��-->
                        <td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><%=(pageList!=null)?pageList.getTotalSize():0%><%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
	<tr>
		<td align="left" colspan="3">
			&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.feedback",clientLocale)%> <!--����-->��&nbsp;
			<%=LocalUtilis.language("message.need",clientLocale)%> <!--��Ҫ--><input class="flatcheckbox" type="radio" class="checkcho" name="needFeedback" value="1" />
			<%=LocalUtilis.language("message.no",clientLocale)%> <!--����Ҫ--><input class="flatcheckbox" type="radio" name="needFeedback" value="2" checked/>
		</td>
	</tr> 
</table>
<div align="right">	
    <!-- ȷ�� -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:submitAction(2);"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- �ر� -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>

<script language=javascript>
window.onload = function(){	
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){		
		window.returnValue = 1;
		window.close();
	}
}
</script>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>