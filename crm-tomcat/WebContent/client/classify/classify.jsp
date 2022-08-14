<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
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
String touch_type = Utility.trimNull(request.getParameter("touch_type"),"");//联系方式
if("0".equals(touch_type))
	touch_type = "";
//String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));//联系电话
String post_address = Utility.trimNull(request.getParameter("post_address"));//联系地址
if("0".equals(post_address))
	post_address = "";
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

StringBuffer sortlist = Argument.newStringBuffer();//排序字段列表"<option value=\"\">请选择</option>");
Argument.appendOptions(sortlist, "CUST_NAME", enfo.crm.tools.LocalUtilis.language("class.customerName",clientLocale), sort_name);//客户名称
Argument.appendOptions(sortlist, "TOTAL_MONEY", enfo.crm.tools.LocalUtilis.language("message.amountPurchased",clientLocale), sort_name);//已购金额
Argument.appendOptions(sortlist, "LAST_RG_DATE", enfo.crm.tools.LocalUtilis.language("message.lastRgDate",clientLocale), sort_name);//最近购买时间
Argument.appendOptions(sortlist, "CURRENT_MONEY", enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale), sort_name);//存量金额
Argument.appendOptions(sortlist, "BEN_AMOUNT", enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale), sort_name);//受益份额

//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//设置参数
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
//页面变量
Map map = null;
IPageList pageList =
	customer.listProcAllExt(
		vo,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

//url设置
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
			+ "&classEs="+classEs;
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerRating",clientLocale)%> </TITLE><!--client_grade.jsp--><!--客户评级-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language=javascript>
/*客户评级修改 显示ID*/
function setCustClassTable(cust_id){	
	
	var url = "client_class_edit.jsp?cust_id="+cust_id;
	window.location.href = url;
		
	showWaitting(0);
}

/****************/
function refreshPage()
{
	location = 'classify.jsp?page=<%=sPage%>&pagesize='+ document.theform.pagesize.value+getQueryCondition();
}

/**************************************************************************/
function advancedQuery(){
	var v = showModalDialog('/client/analyse/client_query_all.jsp?'+getQueryCondition(),'','dialogWidth:650px;dialogHeight:620px;status:0;help:0');
	if(v!=null)
	{	
		location = 'classify.jsp?cust_no='          +v[0]
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
								+'&classEs='        +v[31];
	}	
}

/*查询产品*/
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}	

/*设置产品*/ 
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
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function batchProcessing(element)
{
	if(element==null)
	{
		sl_alert("未选定");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("未选定记录");
		return false;
	}
	var ret = getCheckedBoxValue(element);
	showModalDialog('client_batch_processing.jsp?custId='+ret,'','dialogWidth:440px;dialogHeight:520px;status:0;help:0');
	
	
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
  
	return sQuery;
}

</script>

</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<input type="hidden" id="tempArray" value=""/>
<input type="hidden" id="tempCustId" value=""/>
<input type="hidden" id="inputMan" value="<%=input_operatorCode%>"/>

<form id="theform" name="theform" method="post" >
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"
			onclick="javascript:advancedQuery();"><%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><%}%>	
			
		&nbsp;&nbsp;
		<!--批量处理-->   
		<button type="button"  class="xpbutton4" accessKey=p id="queryButton" title='<%=LocalUtilis.language("message.batchProcessing",clientLocale)%> ' name="queryButton"
			onclick="javascript:batchProcessing(document.theform.cust_id);">
			<%=LocalUtilis.language("message.batchProcessing",clientLocale)%> (<u>P</u>)</button>
	</div>
	<hr noshade color="#808080" size="1">
</div>

<div>
	<table border="0" cellspacing="1" cellpadding="2"
		class="tablelinecolor" width="100%">
		<tr class="trh">
			<td align="center">
			<input type="checkbox" name="btnCheckbox" class="selectAllBox"
				onclick="javascript:selectAllBox(document.theform.cust_id,this);"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
			<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
			<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
			<td align="center"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> </td><!--风险等级-->
			<td align="center"><%=LocalUtilis.language("message.modifyCustClassification",clientLocale)%> </td><!--修改客户分类-->
		</tr>
<%
	int iCount = 0;
	int iCurrent = 0;
	Integer cust_id = new Integer(0);
	
	while (it.hasNext()) {
		map = (Map) it.next();
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="center">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="cust_id"
						value="<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
					<td width="90%" align="center"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
				</tr>
			</table>
			</td>
			<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>
			<td align="center">
              <a href="javascript:setCustClassTable(<%=cust_id%>);"> 
              	<img border="0" src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16">
              </a>
              <input type="hidden" id="flag_display<%=cust_id%>" name="flag_display<%=cust_id%>" value="0">
            </td>
		</tr>
		
		<tr id="tr<%=cust_id%>" style="display: none">
			<td align="center" bgcolor="#FFFFFF" colspan="8" >
				<div id="div<%=cust_id%>" style="overflow-y:auto;" align=center>
					<table id="tableCustClass<%=cust_id%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
					</table>
				</div>
			</td>
		</tr>
		<%iCurrent++;
		iCount++;
		}%>

		<%for (; iCurrent < pageList.getPageSize(); iCurrent++){%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
		</tr>
		<%}
		%>
		<tr class="trbottom">
			<!--合计--><!--项-->
            <td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr>
	</table>
</div>
<br>
<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();%>
