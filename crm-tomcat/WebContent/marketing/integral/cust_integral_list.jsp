<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
String q_cust_name =Utility.trimNull(request.getParameter("q_cust_name"));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

//获得对象
CustIntegralLocal local = EJBFactory.getCustIntegral();
CustIntegralVO vo = new CustIntegralVO();

vo.setCust_name(q_cust_name);
IPageList pageList = local.queryCustIntegral(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
int iCount = 0;
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
<title></title><!--客户积分卡-->
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
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*查询功能*/
function QueryAction(){		
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url 组合
	var url = "cust_integral_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&q_cust_name=" + document.getElementById("q_cust_name").getAttribute("value");

	window.location = url;
}
//批量修改
function batchModiAction(){
	if(checkedCount(document.getElementsByName("check_cust_id")) == 0){
        //请选定要处理的明细信息
		sl_alert("<%=LocalUtilis.language("message.chooseDetailInfo",clientLocale)%>！");
		return false;
	}
	else{
		var check_cust_id = document.getElementsByName("check_cust_id");
		var checkedValues = "";
		
		for(var i=0;i<check_cust_id.length;i++){			
			if(check_cust_id[i].checked){
				var checkedValues = checkedValues + check_cust_id[i].value + "|"
			}
		}

		checkedValues = checkedValues.substring(0,checkedValues.length-1);		
		modiAction(checkedValues);		
	}
}
//修改积分
function modiAction(cust_id_array){
	var url = "cust_integral_action.jsp?cust_id_array="+cust_id_array;
	var ret = showModalDialog(url,'', 'dialogWidth:500px;dialogHeight:300px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:300px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right">客户名称: </td>
			<td  align="left">
				<input type="text" name="q_cust_name" id="q_cust_name" value="<%= q_cust_name%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
					<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>
	</table>
</div>
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
		<%//if (input_operator.hasFunc(menu_id, 102)) {%>
		<button type="button"  class="xpbutton3" accessKey=u id="updateButton" name="updateButton" title="<%=LocalUtilis.language("message.update",clientLocale)%>" onclick="javascript:batchModiAction();">
		   <%=LocalUtilis.language("message.update",clientLocale)%>(<u>U</u>)</button><!--修改-->
		&nbsp;&nbsp;&nbsp;<%//}%>
	</div>
	<hr noshade color="#808080" size="1" width="98%">
</div>

<div align="center">
	<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="15px">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
					   onclick="javascript:selectAllBox(document.theform.check_cust_id,this);" />
			</td>
			<td align="center" width="*">客户编号</td>
			<td align="center" width="*">客户名称</td>
			<td align="center" width="*">积分级别</td>
			<td align="center" width="*">基础积分</td>
			<td align="center" width="*">合同积分</td>
			<td align="center" width="*">有效总积分</td>
			<td align="center" width="30px"> <%=LocalUtilis.language("message.update",clientLocale)%></td>
		</tr>
		<%
			Iterator iterator = list.iterator();
			Integer cust_id = new Integer(0);
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
				cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="15px"><input type="checkbox" name="check_cust_id" value="<%=cust_id%>" class="flatcheckbox"/> </td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_LEVEL_NAME"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("BASE_INTEGRAL"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("RULE_INTEGAL"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("TOTAL_INTEGAL"))%></td>
			<td align="center" width="30px">
				<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction(<%=cust_id%>);">
			</td>
		</tr>
		<%}%>
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>       	
	         <tr class="tr0">
	            <td align="center" width="15px">&nbsp;</td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>      
	            <td align="center"></td>
	            <td align="center"></td>        
	            <td align="center"></td>               
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="8" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</table>
</div>
<br>
<div >
	&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>