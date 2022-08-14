<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer showFlag =  Utility.parseInt(request.getParameter("showFlag"),new Integer(1));
Integer q_cust_type =Utility.parseInt(request.getParameter("q_cust_type"),new Integer(0));
Integer q_group_id = Utility.parseInt(request.getParameter("q_group_id"),new Integer(0));
Integer q_class_detail_id = Utility.parseInt(request.getParameter("q_class_detail_id"),new Integer(0));
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),new Integer(0));
Integer q_stat_scope =Utility.parseInt(request.getParameter("q_stat_scope"), new Integer(0));
Integer q_rg_date_start = Utility.parseInt(request.getParameter("q_rg_date_start"),new Integer(0));
Integer q_rg_date_end =Utility.parseInt(request.getParameter("q_rg_date_end"), new Integer(21991231));

String from = Utility.trimNull(request.getParameter("from"));
String[] ss = from.split("~", 11);
String qs = "page="+ss[0]
		   +"&pagesize="+ss[1]
  		   +"&showFlag="+ss[2]
		   +"&q_cust_type="+ss[3]
		   +"&q_group_id="+ss[4]
		   +"&q_class_detail_id="+ss[5]
		   +"&q_productCode="+ss[6]
		   +"&q_productId="+ss[7]
		   +"&q_stat_scope="+ss[8]
		   +"&q_rg_date_start="+ss[9]
		   +"&q_rg_date_end="+ss[10];	

sUrl += "&"+Utility.getQueryString(request, new String[]{"showFlag","q_cust_type","q_group_id","q_class_detail_id","q_productCode"
													 ,"q_productId","q_stat_scope","q_rg_date_start","q_rg_date_end","from"}); 

input_bookCode = new Integer(1);
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

//获得对象
CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
CustomerStatVO vo = new CustomerStatVO();

//设置参数
vo.setClassDetailId(q_class_detail_id);
vo.setGroupId(q_group_id);
vo.setProductId(q_productId);
vo.setCust_type(q_cust_type);
vo.setFunc_id(new Integer(200));
vo.setStatScope(q_stat_scope);
vo.setRgDateStart(q_rg_date_start);
vo.setRgDateEnd(q_rg_date_end);
vo.setInputMan(input_operatorCode);

//cll文件查询参数
/*
String cllParamStr= vo.getCust_type().toString() + "β"
				+ vo.getFunc_id().toString() + "β"
				+ vo.getProductId().toString() + "β"
				+ vo.getGroupId().toString() + "β"
				+ vo.getClassDetailId().toString() + "β";

 SP_STAT_CUST_CONTRACT2(

@P_FUNCTION_ID,
@P_PRODUCT_ID,
@P_CUST_TYPE,
@P_ORDERBY,
@IN_GROUPID,
@IN_CLASSDETAIL_ID,
@IN_SCOPE,
@IN_RG_DATE_START,
@IN_RG_DATE_END)*/

String cllParamStr= vo.getFunc_id().toString() + "β"
				+ vo.getProductId().toString() + "β"
				+ vo.getCust_type().toString() + "β"
				+ ""/*vo.getOrderBy().toString()*/ + "β"
				+ vo.getGroupId().toString() + "β"
				+ vo.getClassDetailId().toString() + "β"
				+ vo.getStatScope().toString() + "β"
				+ vo.getRgDateStart().toString() + "β"
				+ vo.getRgDateEnd().toString() + "β";

IPageList pageList = custStatLocal.getStatCustContract_page2(vo,t_sPage,t_sPagesize);
List list = pageList.getRsList();

custStatLocal.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.rgMoneyAnalyse",clientLocale)%></TITLE><!--客户认购金额分析-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
window.onload = function(){
		initQueryCondition();

		document.getElementById("td<%=q_cust_type%>").bgColor =
			document.getElementById("td_s<%=q_stat_scope%>").bgColor ="#99FFFF";
		show(<%=showFlag%>);
	};

// 列表或图表
function show(parm){
   for (var i=1; i<=2; i++) {    	
      	document.getElementById('d'+i).background = 
			i==parm? '<%=request.getContextPath()%>/images/head_00_01.gif': '<%=request.getContextPath()%>/images/headdark_00_01.gif';
      	if (document.getElementById("r"+i))
			document.getElementById('r'+i).style.display = i==parm? "": 'none';
	}
}

//改变客户类别
function changeCustType(flag){
	document.getElementById("q_cust_type").value=flag;
	refreshPage();
}

function changeStatScope(scope) {
	document.getElementById("q_stat_scope").value=scope;
	refreshPage();
}

function changeShowFlag(flag){
	document.getElementById("showFlag").value = flag;
	refreshPage();
}

function refreshPage(page){
	disableAllBtn(true);		
	syncDatePicker(document.theform.start_date_picker, document.theform.q_rg_date_start);
	syncDatePicker(document.theform.end_date_picker, document.theform.q_rg_date_end);
	location.search = "?from=<%=from%>&page="+(page?page:1)+"&pagesize="+ document.theform.pagesize.value
						+ "&q_cust_type=" + document.getElementById("q_cust_type").value
						+ "&q_stat_scope=" + document.getElementById("q_stat_scope").value
						+ "&q_productId=" + document.getElementById("q_productId").value
						+ "&q_group_id=" + document.getElementById("q_group_id").value	
						+ "&q_class_detail_id=" + document.getElementById("q_class_detail_id").value
						+ "&showFlag=" + document.getElementById("showFlag").value
						+ "&q_rg_date_start=" + document.getElementsByName("q_rg_date_start")[0].value
						+ "&q_rg_date_end=" + document.getElementsByName("q_rg_date_end")[0].value;
}						

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*设置产品*/
function setProduct(value){
	var prodid=0;

	if(event.keyCode == 13 && value != ""){
        var j = value.length;

		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				StartQuery();
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;

		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}

		document.theform.q_productCode.focus();
	}
}

function back() {
	location.href= "client_contract_stat.jsp?<%=qs%>";
}
</SCRIPT>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" name="theform" id="theform">	
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />
<input type="hidden" id="q_stat_scope" name="q_stat_scope" value="<%=q_stat_scope%>"/>
<input type="hidden" name="showFlag" id="showFlag" value="<%= showFlag%>" />

<div align="left" class="page-title page-title-noborder">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>

<div id="headDiv" >
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
		<TBODY >
			<TR class="tr-tabs">	
				<td>
				<div class="btn-wrapper">
					<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)<!--查询--></button>
					&nbsp;
				<%if (input_operator.hasFunc(menu_id, 107)) {%>
					<!--报表-->
                    <button type="button"  class="xpbutton3" accessKey=r id="reportButton" name="reportButton" title='<%=LocalUtilis.language("message.report",clientLocale)%>' 
						onclick="javascript:window.open('<%=request.getContextPath()%>/webreport/noparam_init.jsp?filename=/webreport/Cells/106_1.cll&cllParamStr=<%=cllParamStr%>')"><%=LocalUtilis.language("message.report",clientLocale)%> (<u>R</u>)</button>
				<%}%>	
					&nbsp;
					<button type="button"  class="xpbutton3" accessKey=b onclick="javascript:back();">返回 (<u>B</u>)</button>
				</div>
				</TD>													
				<!--列表-->
                <TD id="d1"  style="background-repeat: no-repeat;font-size:13;font-family:微软雅黑" onclick=show(1) vAlign=bottom width=60 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.list",clientLocale)%> </TD>
				<!--图表-->
                <TD id="d2"  style="background-repeat: no-repeat;font-size:13;font-family:微软雅黑" onclick=show(2) vAlign=bottom width=60 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.icon",clientLocale)%> </TD>	
				
					
			</TR>
		</TBODY>
	</TABLE>
</div>

<div id="headDiv" style="margin-left:20px;margin-right:20px;margin-top:5px;">
	<table width="380px">
		<tr style="background:F7F7F7;">
			<td>
				<table cellSpacing="1" cellPadding="2" width="180px" bgcolor="#CCCCCC">
					<tr style="background:F7F7F7;">
						<!--全部-->
	                    <td width="60px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="javascript:changeCustType(0);" class="a2">全部</a></font></td>
						<!--个人-->
	                    <td width="60px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="javascript:changeCustType(1);" class="a2">个人客户</a></font></td>
						<!--机构-->
	                    <td width="60px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="javascript:changeCustType(2);" class="a2">机构客户</a></font></td>
					</tr>
				</table>
			</td>
			<td width="20px">&nbsp;</td>
			<td>
				<table cellSpacing="1" cellPadding="2" width="180px" bgcolor="#CCCCCC">
					<tr style="background:F7F7F7;">				
						<td width="60px" align="center" id="td_s0"><font size="2" face="微软雅黑"><a href="javascript:changeStatScope(0);" class="a2">所有</a></font></td>
						<td width="60px" align="center" id="td_s1"><font size="2" face="微软雅黑"><a href="javascript:changeStatScope(1);" class="a2">本人</a></font></td>
						<td width="60px" align="center" id="td_s2"><font size="2" face="微软雅黑"><a href="javascript:changeStatScope(2);" class="a2">本部门</a></font></td>
					</tr>
				</table>
			</td>		
		</tr> 
	</table>
</div>

<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
		   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>

	<table>
		<tr>
			<td align="right">产品编号:</td>
			<td colspan="3">
				<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />	
			</td>
		</tr>
		<tr>
			<td align="right">产品:</td>
			<td colspan="3">
				<select size="1" name="q_productId" id="q_productId" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:refreshPage();">					
					<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
				</select>
			</td>
		</tr>		
		<tr>
			<td align="right" width="20%">客户群组:</td>
			<td align="left" width="30%">
				<select name="q_group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width:120px;" onchange="javascript:refreshPage();">
					<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
				</select>
			</td>
			<td align="right" width="20%">客户等级:</td>
			<td align="left" width="30%">
				<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width:120px;" onchange="javascript:refreshPage();">
					<option>请选择客户等级</option>
					<%=Argument.getCustClassOption(new Integer(12),q_class_detail_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">认购日期 起:</td>
			<td width="30%">
				<INPUT TYPE="text" NAME="start_date_picker"value="<%=Format.formatDateLine(q_rg_date_start)%>" size="16">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="q_rg_date_start" value=""/>	
			</td>
			<td align="right" width="20%">认购日期 止:</td>
			<td align="left" width="30%">
				<INPUT TYPE="text" NAME="end_date_picker"value="<%=Format.formatDateLine(q_rg_date_end)%>" size="16">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="q_rg_date_end" value=""/>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">				
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确认-->		
			</td>
		</tr>
	</table>
</div>

<div  id="r1"  align="left"  style="display:none;margin-left:20px; margin-top:5px;margin-right:20px;">
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="10%"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--客户编号-->
			<td align="center" width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
			<td align="center" width="10%"><%=LocalUtilis.language("message.rg_times",clientLocale)%> </td><!--认购次数-->			
			<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
			<td align="center" width="*"><%=LocalUtilis.language("class.current_money",clientLocale)%> </td><!--存量金额-->
			<td align="center" width="*"><%=LocalUtilis.language("class.benefitShare",clientLocale)%> </td><!--受益份额-->
		</tr>
	<%
	//声明字段
	Iterator iterator = list.iterator();
	Integer cust_id = new Integer(0);
	String cust_no = "";
	String cust_name="";
	Integer cust_type = new Integer(0);
	Integer rg_times = new Integer(0);
	BigDecimal rg_money = new BigDecimal(0.00);
	BigDecimal current_money = new BigDecimal(0.00);
	BigDecimal ben_amount = new BigDecimal(0.00);
	
	BigDecimal rg_money_all = new BigDecimal(0.00);
	BigDecimal current_money_all = new BigDecimal(0.00);
	BigDecimal ben_amount_all = new BigDecimal(0.00);
	BigDecimal rg_money_all_jg = new BigDecimal(0.00);
	BigDecimal current_money_all_jg = new BigDecimal(0.00);
	BigDecimal ben_amount_all_jg = new BigDecimal(0.00);
	
	int rg_times_total = 0;
	BigDecimal rg_money_all2 = new BigDecimal(0.00);
	BigDecimal current_money_all2 = new BigDecimal(0.00);
	BigDecimal ben_amount_all2 = new BigDecimal(0.00);

	while(iterator.hasNext()){
		iCount++;
		Map map = (Map)iterator.next();
		
		cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		cust_no = Utility.trimNull(map.get("CUST_NO"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		rg_times = Utility.parseInt(Utility.trimNull(map.get("RG_TIMES")),new Integer(0));
		rg_money =Utility.parseDecimal( Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0.00));
		current_money = Utility.parseDecimal( Utility.trimNull(map.get("CURRENT_MONEY")),new BigDecimal(0.00));
		ben_amount = Utility.parseDecimal( Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0.00));
		cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
		
		rg_times_total += rg_times.intValue();
		rg_money_all2 = rg_money_all2.add(rg_money);
		current_money_all2 = current_money_all2.add(current_money);
		ben_amount_all2 = ben_amount_all2.add(ben_amount);
		
		if(q_cust_type.intValue() != 0){
			rg_money_all = rg_money_all.add(rg_money);
			current_money_all = current_money_all.add(current_money);
			ben_amount_all = ben_amount_all.add(ben_amount);
		}else{
			if(cust_type.intValue() != 1){
				rg_money_all_jg = rg_money_all_jg.add(rg_money);
				current_money_all_jg = current_money_all_jg.add(current_money);
				ben_amount_all_jg = ben_amount_all_jg.add(ben_amount);
			}else {
				rg_money_all = rg_money_all.add(rg_money);
				current_money_all = current_money_all.add(current_money);
				ben_amount_all = ben_amount_all.add(ben_amount);
			}
		}
	%>			
			<tr class="tr<%=iCount%2%>">
			  	 <td align="center" width="10%"><%=cust_no%></td>					
				 <td align="left" width="20%"><input type="text" style="width:100%;" class="ednone" value="<%=cust_name%>" readonly></td>             
				 <td align="center" width="10%"><%=rg_times.intValue()>0? rg_times.toString(): ""%></td>        
				 <td align="right" width="*"><%= Format.formatMoney(rg_money)%></td>   
				 <td align="right" width="*"><%= Format.formatMoney(current_money)%></td>    
				 <td align="right" width="*"><%= Format.formatMoney(ben_amount)%></td>    
			</tr>
	<%
	}
	for (int i=0; i<8-iCount; i++) {%>     	
	         <tr class="tr0">
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>       
	            <td align="center"></td>       
	         </tr>           
	<%}%> 
			
			<tr class="trbottom">
				<!--合计--><!--项-->
                <td class="tdh" align="left" colspan="2"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>		
				<td align="center"><%=rg_times_total%></td>
				<td align="right"><%=Format.formatMoney(rg_money_all2)%></td>
	            <td align="right"><%=Format.formatMoney(current_money_all2)%></td>       
	            <td align="right"><%=Format.formatMoney(ben_amount_all2)%></td>
			</tr>
		</table>
		<br>		
	<div><%=pageList.getPageLink(sUrl,clientLocale)%></div>
</div>

<div  id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;display:none; ">
<%
String[] ItemValue = new String[3];
String[] ItemName = new String[3];

ItemValue[0] = rg_money_all.toString();
ItemValue[1] = current_money_all.toString();
ItemValue[2] = ben_amount_all.toString();
	
ItemName[0] = enfo.crm.tools.LocalUtilis.language("class.rg_money",clientLocale);//认购金额
ItemName[1] = enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale);//存量金额
ItemName[2] = enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale);//受益份额

FusionCharts Chart = new FusionCharts();
FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
String XMLStr = ChartCreator.ganerateColumn3D(ItemName,ItemValue,"","","","");
int height = 31*10 + 27; 
String chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr,"ENFO",480,height,false);

String chartHTMLCode_jg = "";
if(q_cust_type.intValue() == 0){
	String[] ItemValue_jg = new String[3];
	String[] ItemName_jg = new String[3];

	ItemValue_jg[0] = rg_money_all_jg.toString();
	ItemValue_jg[1] = current_money_all_jg.toString();
	ItemValue_jg[2] = ben_amount_all_jg.toString();
	
	ItemName_jg[0] = enfo.crm.tools.LocalUtilis.language("class.rg_money",clientLocale);//认购金额
	ItemName_jg[1] = enfo.crm.tools.LocalUtilis.language("class.current_money",clientLocale);//存量金额
	ItemName_jg[2] = enfo.crm.tools.LocalUtilis.language("class.benefitShare",clientLocale);//受益份额

	FusionCharts Chart_jg = new FusionCharts();
	FusionChartsGanerate ChartCreator_jg = new FusionChartsGanerate();
	String XMLStr_jg = ChartCreator_jg.ganerateColumn3D(ItemName_jg,ItemValue_jg,"","","","");
	int height_jg = 31*10 + 27; 
	chartHTMLCode_jg = Chart_jg.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr_jg,"ENFO",480,height_jg,false);
}
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr bgcolor="white">
		<td align="left">
			<%=chartHTMLCode%>
		</td>
		<td align="left">
			<%=chartHTMLCode_jg%>
		</td>
	</tr>
<% if(q_cust_type.intValue() == 0){%>
	<tr bgcolor="white">
		<td align="center"><b><font size="2"><%=LocalUtilis.language("class.personal",clientLocale)%></font></b></td><!-- 个人 -->
		<td align="center"><b><font size="2"><%=LocalUtilis.language("message.organization",clientLocale)%></font></b></td><!-- 机构 -->
	</tr>
<%} %>
</table>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>