<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//设置查询条件
Integer q_activitySerialNo = Utility.parseInt(Utility.trimNull(request.getParameter("q_activitySerialNo")),new Integer(0));
String q_feeItems = Utility.trimNull(request.getParameter("q_feeItems"));
BigDecimal q_FeeAmountUp = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_FeeAmountUp")),new BigDecimal(0.00)) ;
BigDecimal q_FeeAmountDown = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_FeeAmountDown")),new BigDecimal(0.00)) ;

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_activitySerialNo="+q_activitySerialNo;
tempUrl = tempUrl+"&q_feeItems="+q_feeItems;
tempUrl = tempUrl+"&q_FeeAmountUp="+q_FeeAmountUp;
tempUrl = tempUrl+"&q_FeeAmountDown="+q_FeeAmountDown;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//获得对象
ActivityFeeLocal activityFeeLocal = EJBFactory.getActivityFee();
ActivityFeeVO vo = new ActivityFeeVO();

//设置查询条件
vo.setActive_serial_no(q_activitySerialNo);
vo.setFee_items(q_feeItems);
vo.setFee_amount_up(q_FeeAmountUp);
vo.setFee_amount_down(q_FeeAmountDown);

//查找信息
IPageList pageList = activityFeeLocal.pageList_query(vo,totalColumn,t_sPage,t_sPagesize);

List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.activityFeeList",clientLocale)%> </title>
<!--活动费用列表-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}

function validate(){
	var q_FeeAmountUp_value = document.getElementById("q_FeeAmountUp").value;
	var q_FeeAmountDown_value = document.getElementById("q_FeeAmountDown").value;
	
	if((q_FeeAmountUp_value!="")&&(! sl_checkDecimal(document.getElementById("q_FeeAmountUp"),"<%=LocalUtilis.language("class.feeAmountUp2",clientLocale)%> "))){ return false;}
	//费用上限
	if((q_FeeAmountDown_value!="")&&(! sl_checkDecimal(document.getElementById("q_FeeAmountDown"),"<%=LocalUtilis.language("class.feeAmountDown2",clientLocale)%> "))){ return false;}
	//费用下限
	if((q_FeeAmountUp_value!="")&&((q_FeeAmountDown_value!=""))&&(q_FeeAmountUp_value<q_FeeAmountDown_value)){
		sl_alert("<%=LocalUtilis.language("message.feeError",clientLocale)%> ");
		//费用上限不能小于费用下限
		return false;
	}
	
	return true;
}

/*刷新*/
function refreshPage(){
	if(validate()){
		disableAllBtn(true);	
		var url = "activity_fee_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
		var url = url + '&q_activitySerialNo=' + document.theform.q_activitySerialNo.value;
		var url = url + '&q_feeItems=' + document.theform.q_feeItems.value;
		var url = url + '&q_FeeAmountUp=' + document.theform.q_FeeAmountUp.value;
		var url = url + '&q_FeeAmountDown=' + document.theform.q_FeeAmountDown.value;
		
		location = url;	
	}	
}
//查询
function QueryAction(){
	refreshPage();
}

/*新增方法*/
function AppendAction(){		
	var url = "activity_fee_action.jsp?actionFlag=1&transFlag=1";	
	window.location.href = url;	
}

/*修改方法*/
function ModiAction(serial_no){				
	var url = "activity_fee_action.jsp?actionFlag=2&transFlag=1&serial_no="+serial_no;	
	window.location.href = url;	
}

/*删除功能*/
function DelAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "activity_fee_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

</script>
<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:470px;height:130px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
		<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td align="right"> <%=LocalUtilis.language("class.feeAmountDown",clientLocale)%> ：</td><!--费用最低-->
			<td  align="left">
				<input type="text" name="q_FeeAmountDown" id="q_FeeAmountDown" value="<%= q_FeeAmountDown%>" onkeydown="javascript:nextKeyPress(this)" style="width:135px">
			</td>	
			<td align="right"> <%=LocalUtilis.language("class.feeAmountUp",clientLocale)%> ：</td><!--费用最高-->
			<td  align="left">
				<input type="text" name="q_FeeAmountUp" id="q_FeeAmountUp" value="<%= q_FeeAmountUp%>" onkeydown="javascript:nextKeyPress(this)" style="width:135px">
			</td>			
		</tr>
		
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.feeItems",clientLocale)%> ： </td><!--费用名目-->
			<td  align="left" colspan="3">
				<input type="text" name="q_feeItems" id="q_feeItems" value="<%= q_feeItems%>" onkeydown="javascript:nextKeyPress(this)" style="width:135px">
			</td>	
		</tr>
		
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				<!--确认-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>	
	 </table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" class="btn-wrapper">
	<font size=3>&nbsp;<%=LocalUtilis.language("class.activityTheme",clientLocale)%>：</font> &nbsp;&nbsp;<!--活动主题-->
	<select name = "q_activitySerialNo" id="q_activitySerialNo" onchange="javascript:QueryAction();"  style="width:250px">
		<%=Argument.getActivityOptions(q_activitySerialNo)%>
	</select>
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"  title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);">
		<%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
		
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
		<button type="button"  class="xpbutton3" accessKey=n id="btnAppend" name="btnAppend"  title="<%=LocalUtilis.language("message.append",clientLocale)%> " onclick="javascript:AppendAction();">
		<%=LocalUtilis.language("message.append",clientLocale)%> (<u>N</u>)</button><!--新增-->
		&nbsp;&nbsp;&nbsp;<%}%>
		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel"  title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();">
		<%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button><!--删除-->
		<%}%>
	</div>	
</div>		

<br/>
<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trh">
					<td align="center" width="10%">
						<input type="checkbox" 
									name="btnCheckbox" 
									class="selectAllBox"	
									onclick="javascript:selectAllBox(document.theform.check_serial_no,this);" />
							<%=LocalUtilis.language("class.feeItems",clientLocale)%> 
					</td><!--费用名目-->
					<td align="center" width="12%"><%=LocalUtilis.language("class.activeThemeList",clientLocale)%> </td><!--所属活动主题-->
					<td align="center" width="12%"><%=LocalUtilis.language("class.feeAmount2",clientLocale)%>（<%=LocalUtilis.language("message.yuan",clientLocale)%> ）</td><!--费用金额--><!--元-->
					<td align="center" width="*"><%=LocalUtilis.language("class.feeRemark",clientLocale)%> </td><!--费用说明-->
					<td align="center" width="8%"><%=LocalUtilis.language("message.edit",clientLocale)%> <!--编辑-->
</td>
				</tr>
				
<%
//声明字段
Iterator iterator = list.iterator();
Integer serial_no;	
String active_code;
String active_theme;
String feeItems;
BigDecimal feeAmount;
String remark;

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
	active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));	
	feeAmount = Utility.parseDecimal( Utility.trimNull(map.get("FeeAmount")),new BigDecimal(0.00),2,"1");
	feeItems = Utility.trimNull(map.get("FeeItems"));	
	remark = Utility.trimNull(map.get("Remark"));	
%>
	<tr class="tr<%=iCount%2%>">
		  <td align="center" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="check_serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
								<td width="90%" align="left">&nbsp;&nbsp;<%=feeItems%></td>
							</tr>
						</table>
		</td>
		 <td align="left">&nbsp;&nbsp;<%= active_theme%></td>        
		 <td align="right">&nbsp;&nbsp;<%= feeAmount%></td>     
		 <td align="left">&nbsp;&nbsp;<%= remark%></td>   
		  <td align="center">              	
              	<a href="javascript:ModiAction(<%=serial_no%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
               	</a>
           </td>    
	</tr>
<%}%>

<%for(int i=0;i<(t_sPagesize-iCount);i++){%>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>               
         </tr>           
<%}%> 
		<tr class="trbottom">
			<td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			<!--合计--><!--项-->

		</tr>
	</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>

<% activityFeeLocal.remove();%>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>





