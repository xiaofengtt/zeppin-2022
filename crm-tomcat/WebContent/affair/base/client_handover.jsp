<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String managername_before = Utility.trimNull(request.getParameter("managername_before"));
String managername_now = Utility.trimNull(request.getParameter("managername_now"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));

CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
vo.setManagername_before(managername_before);
vo.setManagername_now(managername_now);
vo.setCheck_flag(check_flag);
vo.setInput_man(input_operatorCode);

IPageList pageList =local.pagelist_query(vo,new String[0],Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();

String tempUrl = "&managername_before=" + managername_before+ "&managername_now=" + managername_now+ "&check_flag=" + check_flag;
sUrl += tempUrl;

local.remove();
%>

<HTML>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>客户移交</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
var oState = {};	
window.onload = function(){
		initQueryCondition();
	};

function selectIndex(obj,value){
	var _obj = obj;
	for(var i=0;i< _obj.length;i++){
		if(_obj[i].getAttribute("value") == value){
			_obj.selectedIndex = i;
		}
	}
}

function AppendAction(){
	/*var url = "<%=request.getContextPath()%>/affair/base/client_handover_new.jsp";
	oState.newlist_flag = 0;
	if(showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:320px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}*/

	location.href = "client_handover_new2.jsp";
}

function ModiAction(){
	var _event = window.event.srcElement;
	oState.data = eval("("+_event.getAttribute("lds")+")");
	var url = "<%=request.getContextPath()%>/affair/base/client_handover_edit.jsp?serial_no="+oState.data.serial_no+"&managerid_before="+oState.data.managerid_before+"&managerid_now="+oState.data.managerid_now;
	if(showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:320px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}		
}

function DelSelfAction(serial_no){
	document.theform.number.value = serial_no;
	if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？"))//你确定要删除吗
		document.theform.submit();
}

function refreshPage(){
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize='+document.theform.pagesize.value + '<%=tempUrl%>';
}

function QueryAction(){
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value
						+"&managername_before="+ document.theform.managername_before.value
						+"&managername_now="+ document.theform.managername_now.value
						+"&check_flag="+ document.theform.check_flag.value;
}
</script>
</head>

<body class="body body-nox">
<form name="theform" action="client_handover_remove.jsp" method="post" >
<input type="hidden" name="number"/>

<div id="queryCondition" class="qcMain" style="display:none;width:570px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();return false;"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width:160px"><%=LocalUtilis.language("class.beforeManagerTurnOver",clientLocale)%> : </td><!-- 经理移交前 -->
			<td  align="left">
				<select name="managername_before" style="width:110px">
					<%=Argument.getManager_Names(managername_before)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.afterManagerTurnOver",clientLocale)%> : </td><!-- 经理移交后 -->
			<td align="left" >
				<select name="managername_now" style="width:110px">
					<%=Argument.getManager_Names(managername_now)%>
				</select>
			</td> 
		</tr>
		<tr>
			<td  align="right" style="width: 160px;">审核状态 : </td>
			<td  align="left">
				<select name="check_flag" style="width: 110px;">
					<option value="0" <%=check_flag.intValue()==0?"selected":""%> ><%=LocalUtilis.language("message.all",clientLocale)%> </option><!-- 全部 -->
					<option value="1" <%=check_flag.intValue()==1?"selected":""%> >未审核 </option>
					<option value="2" <%=check_flag.intValue()==2?"selected":""%> ><%=LocalUtilis.language("message.pass",clientLocale)%> </option><!-- 审核通过 -->
					<option value="3" <%=check_flag.intValue()==3?"selected":""%> ><%=LocalUtilis.language("message.notPass",clientLocale)%> </option><!-- 审核未通过 -->
				</select>
			</td>						
		</tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确定 -->
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction();return false;"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right" >
			<div class="btn-wrapper">
            <!-- 查询 -->
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:;return false;"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;
            <!-- 新建 -->
			<button type="button"  class="xpbutton3" accessKey=n id="appendButton" name="appendButton" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();return false;"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
			</div>
			<br/>
		</td>
</table>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		 <td width="7%"  align="center"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!-- 序号 -->
         <td width="25%" align="center"><%=LocalUtilis.language("class.beforeTurnOverManager",clientLocale)%> </td><!-- 移交前客户经理 -->
         <td width="25%" align="center"><%=LocalUtilis.language("class.afterTurnOverManager",clientLocale)%> </td><!-- 移交后客户经理 --> 
		 <td width="25%" align="center">移交的客户 </td> 
         <td width="12%" align="center"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!-- 审核标志 -->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- 删除 -->
    </tr>
<%
int iCount = 0;
int iCurrent = 0;

for(int i=0; i<list.size(); i++){
	Map map = (Map)list.get(i);	
	iCount++;
	
	int v_checkFlag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),0);	
	String flagStr = v_checkFlag==1? enfo.crm.tools.LocalUtilis.language("message.noCheck", clientLocale) //未审核
					: v_checkFlag==2? enfo.crm.tools.LocalUtilis.language("message.pass", clientLocale) //审核通过
					: v_checkFlag==3? enfo.crm.tools.LocalUtilis.language("message.notPass", clientLocale) //审核未通过
					: "";
	Integer cust_id = Utility.parseInt((Integer)map.get("CUST_ID"), new Integer(0));
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<span><%=iCount%></span>
            </td>
            <td align="center"><%=Utility.trimNull(map.get("MANAGERNAME_BEFORE"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("MANAGERNAME_NOW"))%></td>
			<td align="center"><%=cust_id.intValue()==0? "全部客户": Argument.getCustomerName(cust_id, input_operatorCode)%></td>
            <td width="*" align="center"><%=flagStr%></td>        
	        <td align="center">
	        	<%if(v_checkFlag==1){ %>
                    <!-- 删除 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="absmiddle" 
						title="<%=LocalUtilis.language("message.delete",clientLocale)%> " width="20" height="15" 
						onclick="DelSelfAction(<%=Utility.trimNull(map.get("SERIAL_NO"))%>)"/>
	        	<%}%>
	        </td>                
         </tr>   
<%} 	
for (int i=0; i<8-iCount; i++) { %>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>      
            <td align="center">&nbsp;</td>           
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td> 
		</tr>   
	</TABLE>
	</td>
	</tr>
	<tr>
    	<td>
    		<table border="0" width="100%" class="page-link">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>
</table>

</form>
</body>
</html>