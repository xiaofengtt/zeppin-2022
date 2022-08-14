<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String viewStr = "CUST_NO$CUST_NAME$CARD_ID$TOTAL_MONEY$EXCHANGE_AMOUNT$BACK_AMOUNT$BEN_AMOUNT$LAST_RG_DATE";
Integer v_op_code = Utility.parseInt(Utility.trimNull(request.getParameter("v_op_code")),input_operatorCode);
Integer v_setView = Utility.parseInt(Utility.trimNull(request.getParameter("v_setView")),new Integer(0));
//获得查询参数
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(0));
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
String cust_source = Utility.trimNull(request.getParameter("cust_source"));
String card_type = Utility.trimNull(request.getParameter("card_type"));//证件类型
String card_id = Utility.trimNull(request.getParameter("card_id"));//证件号码
String vip_card_id = Utility.trimNull(request.getParameter("vip_card_id"));//VIP卡编号
String hgtzr_bh = Utility.trimNull(request.getParameter("hgtzr_bh"));//合格投资人编号
String birthday = Utility.trimNull(request.getParameter("birthday"));
String birthday_end = Utility.trimNull(request.getParameter("birthday_end"));
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
String touch_type = Utility.trimNull(request.getParameter("touch_type"));//联系方式
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//联系电话
String post_address = Utility.trimNull(request.getParameter("post_address"));//联系地址
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//产品ID
Integer is_link = Utility.parseInt(request.getParameter("is_link"), new Integer(0));//是否按关联方查询
Integer onlyemail = Utility.parseInt(request.getParameter("onlyemail"), new Integer(0));//是否存在邮寄
String family_name = Utility.trimNull(request.getParameter("family_name"));//家庭名称
String sort_name = Utility.trimNull(request.getParameter("sort_name"));//排序字段
String prov_level = Utility.trimNull(request.getParameter("prov_level"));//受益级别
Integer wtr_flag = Utility.parseInt(Utility.trimNull(request.getParameter("wtr_flag")),new Integer(0));//委托人标志
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
String q_country = Utility.trimNull(request.getParameter("q_country"));
String q_money_source = Utility.trimNull(request.getParameter("q_money_source"));
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),null);
String referee = Utility.trimNull(request.getParameter("referee"));
String classEs = Utility.trimNull(request.getParameter("classEs"));
String telephone = Utility.trimNull(request.getParameter("telephone"));
Integer start_age = Utility.parseInt(request.getParameter("start_age"),null);
Integer end_age = Utility.parseInt(request.getParameter("end_age"),new Integer(0));
String productTo = Utility.trimNull(request.getParameter("productTo"));
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),null);
int cell_flag = Utility.parseInt(request.getParameter("cell_flag"),1);

Integer rg_begin_date = Utility.parseInt(request.getParameter("rg_begin_date"),new Integer(0));
Integer rg_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(0));
int order_by_flag = Utility.parseInt(request.getParameter("order_by_flag"),0);
String order_by_name = Utility.trimNull(request.getParameter("order_by_name"));
String region = Utility.trimNull(request.getParameter("region"));
String channel = Utility.trimNull(request.getParameter("channel"));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String city_name = Utility.trimNull(request.getParameter("city_name"));
Integer true_flag = Utility.parseInt(request.getParameter("true_flag"),new Integer(0)); //

if(order_by_flag==1){
	if(order_by_name.equals(session.getAttribute("order_by_name"))){
		sort_name = order_by_name +" DESC";
		session.setAttribute("order_by_name",sort_name);
	}else{	
		sort_name = order_by_name;
		session.setAttribute("order_by_name",order_by_name);
	}	
}	
StringBuffer sortlist = Argument.newStringBuffer();//排序字段列表"<option value=\"\">请选择</option>");
Argument.appendOptions(sortlist, "CUST_NAME", enfo.crm.tools.LocalUtilis.language("class.customerName",clientLocale), sort_name);//客户名称
Argument.appendOptions(sortlist, "TOTAL_MONEY", enfo.crm.tools.LocalUtilis.language("message.amountPurchased",clientLocale), sort_name);//已购金额
Argument.appendOptions(sortlist, "LAST_RG_DATE", enfo.crm.tools.LocalUtilis.language("message.lastRgDate",clientLocale), sort_name);//最近购买时间
Argument.appendOptions(sortlist, "CURRENT_MONEY", enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale), sort_name);//存量金额
Argument.appendOptions(sortlist, "BEN_AMOUNT", enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale), sort_name);//受益份额

String tempView = Argument.getMyMenuViewStr(menu_id,v_op_code);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}
//获得对象
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
Map fieldsMap = Argument.getMenuViewMap(menu_id,viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap();
	viewStr = "";
}

//参数设置
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
vo.setBirthday(Utility.parseInt(birthday,new Integer(0)));
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
vo.setBirthday_end(Utility.parseInt(birthday_end,new Integer(0)));
vo.setRg_begin_date(rg_begin_date);
vo.setRg_end_date(rg_end_date);
vo.setChannel(channel);
vo.setRegion(region);
vo.setSub_product_id(sub_product_id);
vo.setCity_name(city_name);
vo.setTrue_flag(true_flag);

String totalValueStr = "CURR_TO_AMOUNT$TOTAL_MONEY$CURRENT_MONEY$BEN_AMOUNT$EXCHANGE_AMOUNT$BACK_AMOUNT";
String[] totalValue = Utility.splitString(totalValueStr,"$");
//获得客户信息
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
Iterator it = null;
System.out.println(vo.getService_man());
if(first_flag != 1)
	if(user_id.intValue()==2&&product_id.intValue()!=0){
		pageList = local.listProcAllExt(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, -1));
	}else{
		pageList = local.listProcAllExt(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 10));
	}
if(pageList != null)
	it = pageList.getRsList().iterator();
Map map = new HashMap();
StringBuffer custIdSAll = new StringBuffer();

//URL设置
sUrl = sUrl + "&cust_no=" + cust_no
			+ "&cust_name=" + cust_name
			+ "&cust_source=" + cust_source
			+ "&cust_type=" + cust_type
			+ "&card_type=" + card_type
			+ "&card_id=" + card_id
			+ "&vip_card_id=" + vip_card_id
			+ "&hgtzr_bh=" + hgtzr_bh
			+ "&birthday=" + birthday
			+ "&cust_level=" + cust_level
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
			+ "&start_age="+start_age
			+ "&end_age="+end_age
			+ "&telephone="+telephone
			+ "&cell_id="+cell_id
			+ "&cell_flag="+cell_flag
			+ "&birthday_end="+birthday_end
			+ "&rg_begin_date="+rg_begin_date
			+ "&rg_end_date="+rg_end_date
			+ "&region=" + region
			+ "&channel=" + channel
			+ "&sub_product_id="+sub_product_id
			+ "&city_name="+city_name
			+ "&true_flag="+true_flag;
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
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style type="text/css">

.tdCLASS {cursor:hand;ie2:expression(onclick=sortTable);ie3:expression(onmouseover=overTable)

</style>
<script language=javascript>
/*窗体加载*/
window.onload =function(){

	var cust_type = document.getElementById("cust_type").value;
	var tdId = "td"+cust_type;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
	
}

/*查询*/
function advancedQuery(){
	var v = showModalDialog('client_query_all.jsp?'+getQueryCondition(),'','dialogWidth:650px;dialogHeight:680px;status:0;help:0');
	if(v!=null)
	{
		location = 'client_query_list.jsp?cust_no=' +v[0]
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
								+'&rg_end_date='	+v[40]
								+'&region='			+v[41]
								+'&channel='		+v[42]
								+'&sub_product_id=' +v[43]
								+'&city_name='      +v[44]
								+'&true_flag='		+v[45];
	}
}
function StartQuery(){
	location = 'client_query_list.jsp?cust_no=<%=Utility.trimNull(cust_no)%>'
		+'&cust_name=<%=Utility.trimNull(cust_name)%>'
		+'&cust_source='+document.theform.cust_source.value
		+'&cust_type='+document.getElementById("cust_type").value
		+'&card_type=<%=Utility.trimNull(card_type)%>'
		+'&card_id=<%=Utility.trimNull(card_id)%>'
		+'&vip_card_id=<%=Utility.trimNull(vip_card_id)%>'
		+'&hgtzr_bh=<%=Utility.trimNull(hgtzr_bh)%>'

		+'&birthday=<%=Utility.trimNull(birthday)%>'
		+'&cust_level='+document.theform.cust_level.value
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
		+'&group_id='+ document.theform.group_id.value
		+'&q_money_source=<%=Utility.trimNull(q_money_source)%>'
		+'&q_country=<%=Utility.trimNull(q_country)%>'
		+'&is_deal='+document.theform.is_deal.value
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
		+'&rg_end_date=<%=rg_end_date%>'
		+'&region=<%=region%>'
		+'&channel=<%=channel%>'
		+'&sub_product_id=<%=sub_product_id%>'
		+'&city_name=<%=city_name%>'
		+'&true_flag=<%=true_flag%>';
}

/*分页*/
function refreshPage()
{

	if (document.theform.pagesize!=null) {
		location = 'client_query_list.jsp?page=<%=sPage%>&pagesize='+ document.theform.pagesize.value +getQueryCondition();
	} else {
		location = 'client_query_list.jsp?page=<%=sPage%>'+getQueryCondition();
	}
}

//排序查询
function queryOrderBy(value)
{
	if (document.theform.pagesize!=null) {
		location = 'client_query_list.jsp?order_by_flag=1&page=<%=sPage%>&order_by_name='+value+'&pagesize='+ document.theform.pagesize.value+getQueryCondition();
	} else {
		location = 'client_query_list.jsp?order_by_flag=1&order_by_name='+value+'&page=<%=sPage%>'+getQueryCondition();
	}
}


/*获得查询条件*/
function getQueryCondition()
{
	var sQuery =   '&cust_no=<%=Utility.trimNull(cust_no)%>'
					+'&cust_name=<%=Utility.trimNull(cust_name)%>'
					+'&cust_source='+document.theform.cust_source.value
					+'&cust_type='+document.getElementById("cust_type").value
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
					+'&rg_end_date=<%=rg_end_date%>'
					+'&region=<%=region%>'
					+'&channel=<%=channel%>'
					+'&sub_product_id=<%=sub_product_id%>'
					+'&city_name=<%=city_name%>'
					+'&true_flag=<%=true_flag%>';
	return sQuery;
}

/*查看客户详细*/
function showInfo(custid)
{
 	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
	location = url;
}

/*导出客户信息Excel*/
function exportExcel()
{
	if(checkedCount(document.theform.cust_id) == 0){
		sl_alert("<%=LocalUtilis.language("message.chooseCustToExport",clientLocale)%>！");//请选择要导出的客户
		return false;
	}else{
		if(sl_confirm("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> "))//确认要导出数据吗
		{
			setWaittingFlag(false);
	    	document.theform.method = "post";
		    document.theform.action = "download_client.jsp?export_flag=1&page=<%=sPage%>&pagesize="+ document.theform.pagesize.value+getQueryCondition();
	        document.theform.submit();
		}
	}
}

/*导出通讯录*/
function exportAddressList()
{
	if(checkedCount(document.theform.cust_id) > 0)
	{
	    var result = showModalDialog('cust_address_book_fields.jsp','','dialogWidth:840px;dialogHeight:450px;status:0;help:0');

	    if(result!=null){
		    setWaittingFlag(false);
		    document.theform.titleNames.value = result[0];
            document.theform.fieldNames.value = result[1];
            document.theform.fieldTypes.value = result[2];
		    document.theform.method = "post";
		    document.theform.action = "download_client_address.jsp?export_flag=3";
	        document.theform.submit();
	    }
	}else{
		sl_alert("<%=LocalUtilis.language("message.chooseCustToExport",clientLocale)%> ！");//请选择要导出的客户
	}
}

/*打印客户对账单*/
function printcustInfo()
{
	var url = "";
	<%if(user_id.intValue() == 2){%>//北国投格式
		url_temp = "printclient_bgt.jsp";
	<%}else{%>
		url_temp = "printclient.jsp";
	<%}%>
    //系统确认//是否打印当前选择记录的对帐单
	if(confirm("<%=LocalUtilis.language("message.systemVailidation",clientLocale)%>：\n\n<%=LocalUtilis.language("message.whetherPrint",clientLocale)%> ？"))
	{
		if(checkedCount(document.theform.cust_id) == 0)
		{
			sl_alert("<%=LocalUtilis.language("message.printCustStatement",clientLocale)%>！");//请选定要打印客户对帐单的记录
			return false;
		}
		syncDatePicker(document.theform.startdate_picker, document.theform.startdate);
		syncDatePicker(document.theform.enddate_picker, document.theform.enddate);
		url = url_temp + '?cust_id='+ checkedValue(document.theform.cust_id)+'&startdate='+document.theform.startdate.value+'&enddate='+document.theform.enddate.value;
	}else
	{
		return false;
	}
	window.open(url ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	//location = url;
}

/*客户对账单发送邮件*/
function emailcustInfo(){

	var url = "";
	url_temp = "sendmail_bgt.jsp";
	if(confirm("系统确认：\n\n是否发送当前选择记录的对帐单 ？")){
		if(checkedCount(document.theform.cust_id) == 0){
			sl_alert("请选择要发送客户对账单的记录");
			return false;
		}
		syncDatePicker(document.theform.startdate_picker, document.theform.startdate);
		syncDatePicker(document.theform.enddate_picker, document.theform.enddate);
		url = url_temp + '?cust_id='+ checkedValue(document.theform.cust_id)+'&startdate='+document.theform.startdate.value+'&enddate='+document.theform.enddate.value;
	}else{
		return false;
	}
	window.open(url ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
}

/*打印信封EMS*/
function printEms()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printEnvelopesTip",clientLocale)%> ！");//请选定要打印信封的记录
		return false;
	}
	<%if(user_id.intValue() == 2){%>//北国投格式
		window.open('print_ems_bgt.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}else{%>
		window.open('print_ems.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}%>
}

/*打印信封SF*/
function printSf()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printEnvelopesTip",clientLocale)%> ！");//请选定要打印信封的记录
		return false;
	}
	<%if(user_id.intValue()==17){%>
		window.open('print_sf_fzdy.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}else{%>
		window.open('print_sf.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}%>
}

/*群发邮件*/
function sendAllMail()
{
	if(showModalDialog('sendmail.jsp?checkflag=yes&checkflag2=yes&product_id=' + <%=product_id%>,'','dialogWidth:800px;dialogHeight:580px;status:0;help:0')!=null)
	{
		sl_alert("<%=LocalUtilis.language("message.mailSendOk",clientLocale)%> ！");//邮件发送成功
	}
}

/*发送邮件*/
function sendMail()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.sendEmailTip",clientLocale)%> ！");//请选定要发送邮件的记录
		return false;
	}
	if(showModalDialog('sendmail.jsp?checkflag=yes&cust_id='+checkedValue(document.theform.cust_id),'','dialogWidth:800px;dialogHeight:580px;status:0;help:0')!=null)
	{
		sl_alert("<%=LocalUtilis.language("message.mailSendOk",clientLocale)%> ！");//邮件发送成功
	}
}

/*发送短信*/
function sendSMS()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.noSelectTask",clientLocale)%> ！");//请选定要发送短信的记录
		return false;
	}

	if(showModalDialog('sendsms.jsp?cust_id='+checkedValue(document.theform.cust_id),'','dialogWidth:800px;dialogHeight:580px;status:0;help:0')!=null)
	{
		//sl_alert("<%=LocalUtilis.language("message.mailSendOk",clientLocale)%> ！");//邮件发送成功
	}
}

/*打印信封*/
function printInfo()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printEnvelopesTip",clientLocale)%> ！");//请选定要打印信封的记录
		return false;
	}
	window.open('print.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=250,width=638px,height=300px,scrollbars=yes,resizable=no, ');
}

/*导出手机*/
function writetxtfile()
{
    if(confirm("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> ？"))//确认要导出数据吗
    {
    	setWaittingFlag(false);
    	location = 'download_mobile.jsp?export_flag=2&input_operatorCode=<%=input_operatorCode%>' +getQueryCondition();
	}
}

function exportMobileList()
{
    if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> ？");//请选择
		return;
	}
	if(confirm("<%=LocalUtilis.language("message.exportDataTip",clientLocale)%> ？"))//确认要导出数据吗
    {
    	setWaittingFlag(false);
			document.getElementsByName("theform")[0].setAttribute("action","download_mobile_list.jsp?export_flag=2");
			document.getElementsByName("theform")[0].setAttribute("method","post");
    	document.getElementsByName("theform")[0].submit();
	}
}
/*打印客户收益情况*/
function printDeployInfo()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printCustReceipts",clientLocale)%> ？");//请选择您要打印收益信息的客户
		return;
	}
	window.open("pring_deploy.jsp?cust_id="+checkedValue(document.theform.cust_id),"","left=250px, top=250px, width=588px, height=380px, scrollbars=yes, resizable=no,");
}

/*打印客户受益情况*/
function printBenifitorInfo()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printCustBenefit'/>",clientLocale)%> ？");//请选择您要打印受益信息的客户
		return;
	}
	window.open("print_benifitor.jsp?cust_id="+checkedValue(document.theform.cust_id),"","left=250px, top=250px, width=680px, height=380px, scrollbars=yes, resizable=no,");
}

//选择联系方式并联动显示相应的值
function getTouchTypeSlectedText(name){
	var obj=document.getElementById(name);
	for(i=0;i<obj.length;i++){
		if(obj[i].selected==true){
			if(obj[i].innerText == "<%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> "){//请选择
				//document.getElementById("cust_tel").innerText = "<%=LocalUtilis.language("class.tele",clientLocale)%> ";//电话
			}else{
				//document.getElementById("cust_tel").innerText = obj[i].innerText; //通过option对象的innerText属性获取到选项文本
			}
		}
	}
}
function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}

function changeCustType(flag){
	document.getElementById("cust_type").value = flag;
	refreshPage();
}

/*打印客户快递单 20110830 交银用*/
function printcustExpressInfo()
{
	var url = "";
		url_temp = "printclient_ems.jsp";
    //系统确认//是否打印当前选择记录的对帐单
	if(confirm("<%=LocalUtilis.language("message.systemVailidation",clientLocale)%>：\n\n<%=LocalUtilis.language("class.printCustExpList",clientLocale)%> ？"))
	{
		if(checkedCount(document.theform.cust_id) == 0)
		{
			sl_alert("<%=LocalUtilis.language("message.printRecord",clientLocale)%>！");//请选定要打印的记录
			return false;
		}
		url = url_temp + '?cust_id='+ checkedValue(document.theform.cust_id);
	}else
	{
		return false;
	}
	location = url;

}

//图表显示
function viewChart()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("请选定记录！");//请选定要打印的记录
		return false;
	}
	location = 'view_chart.jsp?cell_flag=<%=cell_flag%>&product_id=<%=product_id%>&rg_begin_date=<%=rg_begin_date%>&rg_end_date=<%=rg_end_date%>&cust_id='+ checkedValue(document.theform.cust_id)+'&cell_id=<%=cell_id%>';
}

//客户评级
function custRating()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("请选定要评级的客户");
		return false;
	}	
	location = 'customer_score_detail.jsp?rating_falg=1&cust_id_array='+ checkedValue(document.theform.cust_id);
}
</script>
<style>
.dropdown{position:relative; display:inline-block;}
.dropdown button{}
.dropdown .dropdown-menu{position:absolute; top:32px;display:none; left:-35px; background-color:#187fe7; border-radius:5px;  padding:5px;}
.dropdown .dropdown-menu li{border-bottom:1px solid #1d9efa; color:#fff; cursor: pointer; padding:0 5px; line-height:30px; list-style:none; width:100px; text-align:left;}
.dropdown .dropdown-menu li:hover{background-color:#5db7f6;}
.dropdown .dropdown-menu li:first-child{border-top:1px solid #1d9efa;}
.dropdown:hover .dropdown-menu{display:block;}
</style>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="client_query_list.jsp">
<input type="hidden" name="input_man" value="<%=input_operatorCode%>">
<input type="hidden" name="titleNames">
<input type="hidden" name="fieldNames">
<input type="hidden" name="fieldTypes">
<input type="hidden" name="cust_type" id="cust_type" value="<%=cust_type%>" />

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TR>
					<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<!--客户分析--><!--客户综合查询-->
                                <td colspan=6 class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("message.custAnalysis",clientLocale)%>>><%=LocalUtilis.language("message.custIntegratedQuery",clientLocale)%> </b></font></td>
							</tr>
							<tr>
								<td align=right>
									<div class="btn-wrapper">
									
									<!--查询-->
                                    <button type="button"  class="xpbutton3" accessKey=q id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"
										onclick="javascript:advancedQuery();"><%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
									<!--显示设定-->
                                    <%if(v_setView.intValue()!=1){%><button type="button"  class="xpbutton4" onclick="javascript:setView()"><%=LocalUtilis.language("message.displaySet",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<%}%>
								<span class="dropdown">
				                    <button type="button"  class="xpbutton4" accessKey=p>导出相关&#9660;</button>
									<ul class="dropdown-menu">
										<!--以EXCEL格式导出客户信息--><!--导出客户信息-->
										<%if (input_operator.hasFunc(menu_id, 100)){%>
										<li id="exportButton" name="exportButton"  onclick="javascript:exportExcel();"><%=LocalUtilis.language("message.exportCustInfo",clientLocale)%> </li>
										<!--以EXCEL格式导出通讯录信息--><!--导出通讯录-->
										<li id="exportButton" name="exportButton"  onclick="javascript:exportAddressList();"><%=LocalUtilis.language("message.exportContacts",clientLocale)%></li>
	                        			<!--导出手机-->
										<%}if (input_operator.hasFunc(menu_id, 201)){%>
										<li onClick="javascript:exportMobileList();"><%=LocalUtilis.language("message.exportPhone",clientLocale)%></li>
										<%}%>
				                        <!--导出suoyou手机-->
										<%if (input_operator.hasFunc(menu_id, 201)){%>
										<li id="btnDown" name="btnDown" onClick="javascript:writetxtfile();">导出所有手机</li>
										<%}%>
									</ul>
			                       </span>
								<span class="dropdown">
				                    <button type="button"  class="xpbutton4" accessKey=p>打印相关&#9660;</button>
									<ul class="dropdown-menu">
										<!--打印信封-->
										<li id="btnPrint" name="btnPrint" onClick="javascript:printInfo();"><%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%>(<u>P</u>)</li>
										<%if (input_operator.hasFunc(menu_id, 113)){%>
										<li id="btnPrint" name="btnPrint" onClick="javascript:printEms();"><%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%>EMS</li>
										<%}%>
										<%if (input_operator.hasFunc(menu_id, 114)){%>
										<li id="btnPrint" name="btnPrint" onClick="javascript:printSf();"><%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%>SF</li>
										<%}%>
				                        <!--打印客户收益情况--><!-- 收益情况 -->
				                       	<%if (input_operator.hasFunc(menu_id, 111)){%>
										<li id="btnDeploy" name="btnDeploy" onClick="javascript:printDeployInfo();"><%=LocalUtilis.language("message.incomeSituation",clientLocale)%> (<u>I</u>)</li>
										<%}%>
										<!--打印客户受益情况--><!--受益情况-->
										<%if (input_operator.hasFunc(menu_id, 112)){%>
										<li id="btnBenifitor" name="btnBenifitor" onClick="javascript:printBenifitorInfo();"><%=LocalUtilis.language("message.benefitSituation",clientLocale)%> (<u>O</u>)</li>
										<%}%>
			                    		<!--打印客户对帐单-->
										<li id="btnPrintcust" name="btnPrintcust" onClick="javascript:printcustInfo();"><%=LocalUtilis.language("message.printCustomerStatement",clientLocale)%> </li>
										<!--打印客户快递单-->
										<li id="btnPrintcustExpress" name="btnPrintcustExpress" onClick="javascript:printcustExpressInfo();">EMS<%=LocalUtilis.language("message.print",clientLocale)%> </li>
									</ul>
			                       </span>
                       

                       				<span class="dropdown">
					                    <button type="button"  class="xpbutton4" accessKey=p>发送相关&#9660;</button>
										<ul class="dropdown-menu">
											<%if (input_operator.hasFunc(menu_id, 109)){%>
					                        <!-- 群发邮件 -->
											<li name="btnSendAllMail" onClick="javascript:sendAllMail();"><%=LocalUtilis.language("message.bulkMail",clientLocale)%> (<u>S</u>)</li>
					                        <!-- 发送邮件 -->
											<li name="btnSendMail" onClick="javascript:sendMail();"><%=LocalUtilis.language("message.sendMail",clientLocale)%> (<u>M</u>)</li>
											<!-- 发送短信 -->
											<li name="btnSendSMS" onClick="javascript:sendSMS();"><%=LocalUtilis.language("message.sms",clientLocale)%> (<u>N</u>)</li>
											<%}%>
											<!--给客户发送邮件-->
				                     		<%if(user_id.intValue()==2){ %>
											<li id="btnEmailcust" name="btnEmailcust" onclick="javascript:emailcustInfo();">发送对账单邮件</li>
											<% }%>
										</ul>
				                       </span>
                       
								<button type="button"  class="xpbutton4" title="图表显示" onClick="javascript:viewChart();">图表显示</button>
								<!-- <button type="button"  class="xpbutton4" title="客户评级" onClick="javascript:custRating();">客户评级</button>&nbsp;&nbsp; -->
								</div>
							</td>
						</tr>
						<tr>
							<td>
							<table border="0" width="100%">
					<tr>
						<td align=right>
						<div style="text-align: right;">
						
						<%=LocalUtilis.language("class.startDate",clientLocale)%> :<!--开始日期-->
							<INPUT TYPE="text" NAME="startdate_picker" class=selecttext value="">
							<INPUT TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.startdate_picker,theform.startdate_picker.value,this);" tabindex="13">
							<INPUT TYPE="hidden" NAME="startdate" value="">&nbsp;&nbsp;
						<%=LocalUtilis.language("class.endDate",clientLocale)%> :<!--结束日期-->
							<INPUT TYPE="text" NAME="enddate_picker" class=selecttext value="">
							<INPUT TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.enddate_picker,theform.enddate_picker.value,this);" tabindex="13">
							<INPUT TYPE="hidden" NAME="enddate" value="">&nbsp;&nbsp;
							(客户对账单)
						</div>
						</td>						
					</tr>
				</table>
							</td>
						</tr>
					</table>
					<!-- 分类显示 -->
					<table cellSpacing="1" style="margin-top:5px" cellPadding="2"  bgcolor="#CCCCCC"  class="table-select" >
						<tr style="background:F7F7F7;" style="margin-top:5px">
							<td width="50px" align="center" id="td0" <%if (cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
							<!--全部-->
							<td width="50px" align="center" id="td1" <%if (cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
							<!--个人-->
							<td width="50px" align="center" id="td2" <%if (cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
							<!--机构-->
							<td align="center" id="td3">
<%if (user_id.intValue()==21){/*金汇信托CRM要求的选项*/%>
								<select style="width:147px" name="cust_level" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:StartQuery();">
									<%=Argument.getDictParamOptions2(1111, cust_level)%>
								</select>
								<INPUT type="hidden" name="is_deal">
<%}else{ %>
								<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:StartQuery();">
									<%=Argument.getWTCustOptions(is_deal)%>
								</select>
								<INPUT type="hidden" name="cust_level">
<%} %>
							</td>
							<td align="center" id="td4">
								<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
									<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
								</select>
							</td>
							<td align="center" id="td5">
								<select style="width:120px" name="cust_source" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:StartQuery();">
									<%=Argument.getDictParamOptions2(1110, cust_source)%>
								</select>
							</td>
						</tr>
					</table>
				<%if(user_id.intValue()==16){ %>
				<div style="overflow:auto;width:1100;height: 300;">
				<%} %>
				<table id="table3" style="margin-top:5px" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%"<%if(user_id.intValue()==16){ %> style="table-layout:fixed"<%} %>>
					
					<tr class=trtagsort  <%if(user_id.intValue()==16){ %>style="cursor: hand;" <%} %>>
						<td width="22;">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
						</td>
					<%
					String[] fieldsArr =Utility.splitString(viewStr,"$");

					for(int j=0;j<fieldsArr.length;j++){
						Map map1 = (Map)fieldsMap.get(fieldsArr[j]);
						if (map1==null){
							System.out.println("map1==null===设置显示项["+fieldsArr[j]+"]为空，请重新设置显示项！");
					%>
						<script language=javascript>alert("设置显示项[<%=fieldsArr[j]%>]不存在，请重新设置显示项！");</script>
					<%
							continue;
						}
						String field_name = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
					%>
					<td <%if(user_id.intValue()==16){
							if("邮寄地址".equals(field_name) || "客户名称".equals(field_name)) out.print("width='300px'");else out.print("width='120px'");
						}%> 
						align="center" onclick="javascript:queryOrderBy('<%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD")%>');"><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td>	
					<%
					}
					%>
					</tr>
					<%
					int iCount = 0;
					int iCurrent = 0;
					while(it!= null && it.hasNext())
					{
						map = (Map) it.next();
						custIdSAll.append(Utility.trimNull(map.get("CUST_ID")));//将获得客户ID组合成字符串
						if(iCount + 1 < pageList.getRsList().size())
							custIdSAll.append(",");

					%>
					<!--双击查看详细-->
                    <tr title="<%=LocalUtilis.language("message.doubleClickViewDetails",clientLocale)%> " class="tr<%=(iCurrent % 2)%>" <%if(user_id.intValue()==16){ %> style="cursor:hand;"<%} %>	onDBlclick="javascript:showInfo(<%=map.get("CUST_ID")%>);">
						<td class="tdh">
							<input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>" class="flatcheckbox">
						</td>
					<%
					for(int j=0;j<fieldsArr.length;j++){

					%>
					<%
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							switch (iType){
								case 1:
					%>
							<td align="left" ><%=Utility.trimNull(map.get(fieldsArr[j]))%></td>
					<%
								break;
								case 2:
					%>
							<td align="right" ><%=Format.formatMoney(((BigDecimal)map.get(fieldsArr[j])))%></td>
					<%
								break;
								case 3:
					%>
							<td align="center" ><%=Format.formatDateLine(((Integer)map.get(fieldsArr[j])))%></td>
					<%
								break;
								case 4:
					%>
							<td align="center" ><%=Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])),new Integer(0)).intValue() ==1 ?" Y ":" N "%></td>	
					<%		
								break;
								default:
								break;
							}
					}
					%>
					</tr>
					<%
					iCount++;
					iCurrent++;
					}
					int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 8);
					for (; iCurrent < page_size; iCurrent++)
					{
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" ></td>
					<%for(int j=0;j<fieldsArr.length;j++){
					%>
						<td align="center" ></td>
					<%
					}
					%>
					</tr>
					<%
					}
					%>
					<tr class="trbottom">
					<%
					for(int j=0;j<fieldsArr.length;j++){
						if(j==0){
					%>
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="2"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					<%
						}else{
							if(totalValueStr.indexOf(fieldsArr[j])==-1){
					%>
							<td align="center" >-</td>
					<%
							}else{
					%>
						<td align="right" ><%=(pageList!=null)?Format.formatMoney(pageList.getTotalValue(fieldsArr[j])):""%></td>
					<%
							}
						}
					}
					%>
					</tr>
				</table>
				<%if(user_id.intValue()==16){ %>
				</div>
				<%} %>
				<br>
				<table border="0" width="" class="page-link">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>
					</tr>
				</table>
				<br>
				
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
<input type="hidden" name="t2" value="<%=custIdSAll%>">
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%local.remove();
%>
