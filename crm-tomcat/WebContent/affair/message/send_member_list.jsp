<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得参数

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
Integer birthday = Utility.parseInt(request.getParameter("birthday"), new Integer(0));//出生日期
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
String touch_type = Utility.trimNull(request.getParameter("touch_type"));//联系方式
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//联系电话
String post_address = Utility.trimNull(request.getParameter("post_address"));//联系地址
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(12));//产品ID
Utility.debug(request.getParameter("product_id"));
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
Map fieldsMap = Argument.getMenuViewMap("20301",viewStr);
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

String totalValueStr = "CURR_TO_AMOUNT$TOTAL_MONEY$CURRENT_MONEY$BEN_AMOUNT$EXCHANGE_AMOUNT$BACK_AMOUNT";
String[] totalValue = Utility.splitString(totalValueStr,"$");
//获得客户信息

int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
Iterator it = null;
if(first_flag == 1 && user_id.intValue() == 2 )
	request.getSession().setAttribute("defaultPagesize","");
if(first_flag != 1)
	pageList = local.listProcAllExt(vo,totalValue, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, -1));
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
			+"&start_age="+start_age
			+"&end_age="+end_age
			+"&telephone="+telephone
			+"&cell_id="+cell_id
			+"&cell_flag="+cell_flag
			+ "&birthday_end"+birthday_end
			+ "&rg_begin_date"+rg_begin_date
			+ "&rg_end_date"+rg_end_date;
//添加客户
//获得对象
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo_details = null;
Integer action_flag = Utility.parseInt(request.getParameter("action_flag"), new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
boolean bSuccess = false;
if(action_flag.intValue()==2){
	vo_details = new SendSMSVO();
	//获取主键ID 
	String[] temp_checks = request.getParameterValues("selectbox");
	Integer t_cust_id;
	
	vo_details.setSerial_no_total(serial_no);
	vo_details.setInputOperator(input_operatorCode);
	
	//逐个添加
	if(temp_checks!=null){
		for(int i=0;i<temp_checks.length;i++){
			t_cust_id = Utility.parseInt(temp_checks[i], new Integer(0));
			
			if(t_cust_id.intValue()!=0){	
				vo_details.setCust_id(t_cust_id);
				sendTotal.addMessageCustid(vo_details);	
			}
		}
		
		action_flag = new Integer(0);
		bSuccess = true;
	}
}

%>
<HTML>
<HEAD>
<base target="_self">
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
/*窗体加载*/
window.onload = function(){	
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){		
		window.returnValue = 1;
		window.close();
	}
}
/*查询*/
function advancedQuery(){
	var v = showModalDialog('/client/analyse/client_query_all.jsp?'+getQueryCondition(),'','dialogWidth:600px;dialogHeight:640px;status:0;help:0');
	
	if(v!=null)
	{
		url = '<%=request.getContextPath()%>/affair/message/send_member_list.jsp?cust_no='+v[0]
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
/*分页*/
function refreshPage()
{
	theform.action = "<%=request.getContextPath()%>/affair/message/send_member_list.jsp";
	theform.submit();
}


/*获得查询条件*/
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

function StartQuery()
{
	refreshPage();
}

/*保存*/
function submitAction(){		
	var form = document.getElementsByName("theform")[0];
	document.getElementsByName("action_flag")[0].value = 2;	
	
	if(checkedCount(document.getElementsByName("selectbox")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCustomers",clientLocale)%> ！");//请选定要添加的客户
		return false;
	}
	form.action = "<%=request.getContextPath()%>/affair/message/send_member_list.jsp";
	form.submit();	
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="action_flag" value="<%=action_flag%>"/>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=6><img src="<%=request.getContextPath()%>/images/member.gif" align="absBottom" border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right>
							<button type="button" class="xpbutton3" accessKey=f id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"
								onclick="javascript:advancedQuery();"><%=LocalUtilis.language("message.query",clientLocale)%>(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
							<!--保存-->
							<button type="button" class="xpbutton3" accessKey=s id="saveButton"
                            name="saveButton" onclick="javascript:submitAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
							&nbsp;&nbsp;
					</tr>
					<tr>
						<td colspan=6>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center"><input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> </td><!--证件类型-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
						<td align="center"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!--手机号码-->
						<td align="center"><%=LocalUtilis.language("class.email",clientLocale)%></td><!--EMAIL-->
						<td align="center">最近认购日期</td>
						<td align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--客户经理-->
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
						<!--合计--><!--项-->
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

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%local.remove();
%>
