<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String viewStr = "CUST_NO$CUST_NAME$CARD_ID$SERVICE_MAN_NAME$TOTAL_MONEY$EXCHANGE_AMOUNT$BACK_AMOUNT$BEN_AMOUNT$LAST_RG_DATE";
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
Integer birthday = Utility.parseInt(request.getParameter("birthday"), new Integer(0));//出生日期
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数
Integer start_current_score =Utility.parseInt(request.getParameter("start_current_score"), new Integer(0));//开始客户得分
Integer end_current_score = Utility.parseInt(request.getParameter("end_current_score"), new Integer(0));//结束客户得分
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
int order_by_flag = Utility.parseInt(request.getParameter("order_by_flag"),0);
String order_by_name = Utility.trimNull(request.getParameter("order_by_name"));

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
vo.setStart_current_score(start_current_score);
vo.setEnd_current_score(end_current_score);
String totalValueStr = "CURR_TO_AMOUNT$TOTAL_MONEY$CURRENT_MONEY$BEN_AMOUNT$EXCHANGE_AMOUNT$BACK_AMOUNT";
String[] totalValue = Utility.splitString(totalValueStr,"$");
//获得客户信息
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
Iterator it = null;

if(first_flag != 1)
	pageList = local.listProcAllExt(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
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
			+ "&start_age="+start_age
			+ "&end_age="+end_age
			+ "&telephone="+telephone
			+ "&cell_id="+cell_id
			+ "&cell_flag="+cell_flag
			+ "&birthday_end="+birthday_end
			+ "&rg_begin_date="+rg_begin_date
			+ "&rg_end_date="+rg_end_date
			+ "&start_current_score="+start_current_score
			+ "&end_current_score="+end_current_score;
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
	var v = showModalDialog('client_query_all.jsp?'+getQueryCondition(),'','dialogWidth:650px;dialogHeight:630px;status:0;help:0');
	if(v!=null)
	{
		location = 'cust_manager_update.jsp?cust_no=' +v[0]
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
								+'&start_current_score='+v[41]
								+'&end_current_score='+v[42];
	}
}
function StartQuery(){
	location = 'cust_manager_update.jsp?cust_no=<%=Utility.trimNull(cust_no)%>'
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
		+'&start_current_score=<%=start_current_score%>'
		+'&end_current_score=<%=end_current_score%>';
}

/*分页*/
function refreshPage()
{
	if (document.theform.pagesize!=null) {
		location = 'cust_manager_update.jsp?page=<%=sPage%>&pagesize='+ document.theform.pagesize.value+getQueryCondition();
	} else {
		location = 'cust_manager_update.jsp?page=<%=sPage%>'+getQueryCondition();
	}
}

//排序查询
function queryOrderBy(value)
{
	if (document.theform.pagesize!=null) {
		location = 'cust_manager_update.jsp?order_by_flag=1&page=<%=sPage%>&order_by_name='+value+'&pagesize='+ document.theform.pagesize.value+getQueryCondition();
	} else {
		location = 'cust_manager_update.jsp?order_by_flag=1&order_by_name='+value+'&page=<%=sPage%>'+getQueryCondition();
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
					+'&start_current_score=<%=start_current_score%>'
					+'&end_current_score=<%=end_current_score%>';

	return sQuery;
}

/*查看客户详细*/
function showInfo(custid)
{
 	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
	location = url;
	return false;
}


function changeCustType(flag){
	document.getElementById("cust_type").value = flag;
	refreshPage();
}


function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}

function setLimits(element)
{
	if (element == null)
	{
		sl_alert("未选定任何记录！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("请选定要修改的记录！");
		return false;
	}
	
	var v = showModalDialog('manager_modi.jsp?cust_id='+checkedValue(element),'','dialogWidth:220px;dialogHeight:180px;status:0;help:0');
	if(v!=null)
	{
		sl_update_ok();
		location.reload();
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="cust_manager_update.jsp">
<input type="hidden" name="input_man" value="<%=input_operatorCode%>">
<input type="hidden" name="titleNames">
<input type="hidden" name="fieldNames">
<input type="hidden" name="fieldTypes">
<input type="hidden" name="cust_type" id="cust_type" value="<%=cust_type%>" />

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
				<TR>
					<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<!--客户分析--><!--客户综合查询-->
                                <td colspan=6 class="page-title"><font color="#215dc6"><b><%=menu_info%> </b></font></td>
							</tr>
							<tr>
								<td align=right>
								<div class="btn-wrapper">
									<!--查询-->
                                    <button type="button"  class="xpbutton3" accessKey=q id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"
										onclick="javascript:advancedQuery();"><%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
									<!--显示设定-->
                                    <%if(v_setView.intValue()!=1){%><button type="button"  class="xpbutton5" onclick="javascript:setView()"><%=LocalUtilis.language("message.displaySet",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<%}%>					
									 <button type="button"  class="xpbutton5" accessKey=s onclick="javascript:setLimits(document.theform.cust_id)">设置<%=LocalUtilis.language("class.accountManager",clientLocale)%>(<u>S</u>) </button>
								</div>
								</td>
							</tr>
					</table>
					<br/>
					<!-- 分类显示 -->
					<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC" class="table-select">
						<tr style="background:F7F7F7;" style="margin-top:5px">
							<td width="50px" align="center" id="td0" <%if (cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
							<!--全部-->
							<td width="50px" align="center" id="td1" <%if (cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
							<!--个人-->
							<td width="50px" align="center" id="td2" <%if (cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
							<!--机构-->
							<td align="center" id="td3">
								<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:StartQuery();">
									<%=Argument.getWTCustOptions(is_deal)%>
								</select>
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
					<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="table-layout:fixed">
					
					<tr  class=trtagsort style="cursor: hand;">
						<td width="22;">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
						</td>
					<%
					String[] fieldsArr =Utility.splitString(viewStr,"$");

					for(int j=0;j<fieldsArr.length;j++){
						String field_name = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
					%>
					<td <%if("邮寄地址".equals(field_name) || "客户名称".equals(field_name)) out.print("width='300px'");else out.print("width='120px'");%> 
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
                    <tr title="<%=LocalUtilis.language("message.doubleClickViewDetails",clientLocale)%> " class="tr<%=(iCurrent % 2)%>" style="cursor:hand;"	onDBlclick="javascript:showInfo(<%=map.get("CUST_ID")%>);">
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
						</td>
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
				<br>
				<table border="0" width="" class="page-link">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>
					</tr>
				</table>
			
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
