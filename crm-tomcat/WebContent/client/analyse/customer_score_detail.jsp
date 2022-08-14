<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//客户综合查询页面传过来的值
int rating_falg = Utility.parseInt(request.getParameter("rating_falg"),0);
String cust_id_array = Utility.trimNull(request.getParameter("cust_id_array"));

//获得对象及结果集
SystemValueLocal sysLocal = EJBFactory.getSystemValue();
RatingVO vo = new RatingVO();
RatingLocal rating = EJBFactory.getRating();

if(rating_falg==1){//对客户进行评级处理
	sysLocal.saveTCustScoreDetail(cust_id_array,input_operatorCode);
}
//获得页面传递变量
Integer subject_id = Utility.parseInt(Utility.trimNull(request.getParameter("subject_id")),new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String rating_no = Utility.trimNull(request.getParameter("rating_no"));
String rating_name = Utility.trimNull(request.getParameter("rating_name"));
Integer rating_date = Utility.parseInt(request.getParameter("rating_date"),new Integer(0));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
String px_1_name = Utility.trimNull(request.getParameter("px_1_name"),"CUST_NAME");
if(px_1_name.equals(session.getAttribute("px_1_name")) && !"".equals(px_1_name))
{
	px_1_name = px_1_name+" DESC";	
}else{
	session.setAttribute("px_1_name",Utility.trimNull(request.getParameter("px_1_name")));
}
	

//页面辅助参数
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
List custlist = null;
Map map = null;
Map custmap =null;


vo.setPx_name(px_1_name);
vo.setCust_type(cust_type);
vo.setCust_name(cust_name);
vo.setSubject_id(subject_id);
vo.setRating_no(rating_no);
vo.setRating_name(rating_name);
vo.setRating_date(rating_date);
vo.setInput_man(input_man);
list = rating.queryChangeCustomerScore(vo,cust_id_array,t_sPage, t_sPagesize);

sUrl = sUrl+ "&rating_no =" +rating_no+ "&cust_id_array =" +cust_id_array
		+ "&cust_type =" +cust_type+ "&cust_name =" +cust_name+ "&rating_name =" +rating_name;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<!--计分操作数设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);
	//syncDatePicker(document.theform.rating_date_picker, document.theform.rating_date);
	location = 'customer_score_detail.jsp?cust_type=' + document.theform.cust_type.value
							+ '&cust_name=' + document.theform.cust_name.value
							+ '&rating_no=' + document.theform.rating_no.value
							+ '&rating_name=' + document.theform.rating_name.value
							+ '&px_1_name='+document.theform.px_1_name.value
							+ '&cust_id_array=<%=Utility.trimNull(cust_id_array)%>';
}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
 

/*查看客户详细*/
function queryDetail(cust_id,cust_name,subject_id)
{
 	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+cust_id;
	if(showModalDialog(url, '', 'dialogWidth:980px;dialogHeight:600px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
	
}

function queryPX(px_name)
{
	document.theform.px_1_name.value = px_name; 
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="customer_score_detail.jsp">
<input type="hidden" name="px_1_name" value="">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
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
				<!--
				<tr>
					<td align="right"><%//=LocalUtilis.language("class.subjectsName",clientLocale)%>:</td><!--科目名称--
					<td align="left">
						<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%//=Argument.getScoreSubjectOptions(subject_id)%>
						</select>
					</td>
					<td align="right"><%//=LocalUtilis.language("class.ratingDate",clientLocale)%> :</td><!--评级日期--
					<td>
						<input TYPE="text" style="width:120" NAME="rating_date_picker" class=selecttext value="<%//=Format.formatDateLine(rating_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.rating_date_picker,theform.rating_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="hidden" NAME="rating_date" value="">
					</td>
				</tr>
				-->
				<tr>
					<td align="right">客户类别:</td>
					<td align="left">
						<select name="cust_type" style="width: 130px;">
							<%=Argument.getCustTypeOptions(cust_type)%>
						</select> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%>: </td><!--客户名称-->
					<td align="left" >
						<input type="text" name="cust_name" value='<%=cust_name%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.ratingNumbers",clientLocale)%>:</td><!--评级编号-->
					<td align="left">
						<input type="text" name="rating_no" value="<%=rating_no %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
					<td align="right"><%=LocalUtilis.language("class.ratingName",clientLocale)%>:</td><!--评级名称-->
					<td align="left" >
						<input type="text" name="rating_name" value='<%=rating_name%>' onkeydown="javascript:nextKeyPress(this)" size="20">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
							<!--返回-->
							<button type="button"  class="xpbutton3" accessKey=b id="btnSave" name="btnSave"
                   				 onclick="javascript:window.returnValue=null;history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort style="cursor: hand;">
						<td align="center"><%=LocalUtilis.language("class.ratingName",clientLocale)%></td><!--评级名称-->
						<td align="center" onclick="javascript:queryPX('CUST_NAME');"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!--客户名称-->
						<td align="center" onclick="javascript:queryPX('SEX');">性别</td><!--性别-->
						<td align="center" onclick="javascript:queryPX('AGE');">年龄</td>
						<td align="center" onclick="javascript:queryPX('TOTAL_MONEY');">累计投资金额</td>
						<td align="center" onclick="javascript:queryPX('NUM');">累计投资项目数量</td>
						<td align="center" onclick="javascript:queryPX('ALL_MONEY');">累计投资金额分值</td>
						<td align="center" onclick="javascript:queryPX('ITEM_NUM');">投资项目数量分值</td>
						<%if(cust_type.intValue()==1 ||cust_type.intValue()==0){ %>
						<td align="center" onclick="javascript:queryPX('FX_LEVEL');">投资项目风险分值</td>						
						<td align="center" onclick="javascript:queryPX('AGE_LEVEL');">年龄阶段分值</td>
						<%}if(cust_type.intValue()==2 ||cust_type.intValue()==0){%>
						<td align="center" onclick="javascript:queryPX('FREE_OF_FLOW');">自由现金流分值</td>
						<td align="center" onclick="javascript:queryPX('BUREND_OF_DEBT');">债务压力分值</td>
						<%}%>
						<td align="center" onclick="javascript:queryPX('CURRENT_SOURCE');">合计分值</td>
						<td align="center" onclick="javascript:queryPX('RATING_DATE');"><%=LocalUtilis.language("class.ratingDate",clientLocale)%></td><!--评级日期-->
					</tr>
<%
for(int i=0;i<list.size(); i++)
{
	//读数据
	map = (Map)list.get(i);
	if(map!=null){
	String custId = Utility.trimNull(map.get("CUST_ID"));
	subject_id = Utility.parseInt(Utility.trimNull(map.get("SUBJECT_ID")),new Integer(0));
	cust_name = Utility.trimNull(map.get("CUST_NAME")) ;
%>
					<tr class="tr<%=(iCurrent % 2)%>" style="cursor:hand;" onDBlclick="javascript:queryDetail(<%=custId %>,'<%=cust_name %>',<%=subject_id %>);">
						<td align="left">&nbsp;<%=Utility.trimNull(map.get("RATING_NAME")) %></td>
						<td align="left">&nbsp;<%=Utility.trimNull(cust_name) %></td>
						<td align="CENTER">&nbsp;<%=Utility.trimNull(map.get("SEX")) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("AGE")) %></td>
						<td align="RIGHT">&nbsp;<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY")),new BigDecimal(0.00))) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("NUM")) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("ALL_MONEY")) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("ITEM_NUM")) %></td>
						<%if(cust_type.intValue()==1 ||cust_type.intValue()==0){ %>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("FX_LEVEL")) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("AGE_LEVEL")) %></td>
						<%}if(cust_type.intValue()==2 ||cust_type.intValue()==0){%>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("FREE_OF_FLOW")) %></td>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("BUREND_OF_DEBT")) %></td>
						<%} %>
						<td align="RIGHT">&nbsp;<%=Utility.trimNull(map.get("CURRENT_SOURCE")) %></td>
						<td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("RATING_DATE")),new Integer(0))) %></td>
					</tr>
<%
	iCurrent++;
	iCount++;
	}
}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="14"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=list.size() %>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
rating.remove();
%>

