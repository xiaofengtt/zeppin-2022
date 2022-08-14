<%@ page contentType="text/html;charset=GBK" import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String  managername_before = Utility.trimNull(request.getParameter("managername_before"));
String  managername_now = Utility.trimNull(request.getParameter("managername_now"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(1));

Integer handover_flag = Utility.parseInt(request.getParameter("handover_flag"),null);

CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

if(request.getMethod().equals("POST")){
	String rt[] = request.getParameterValues("check_serial_no");
	for(int i=0;i<rt.length;i++){
		if(!rt[i].equals("")){
			TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
			vo.setSerial_no(new Integer(rt[i]));
			vo.setCheck_flag(handover_flag);
			vo.setInput_man(input_operatorCode);
			local.check(vo);
		}
	}
}

TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
vo.setManagername_before(managername_before);
vo.setManagername_now(managername_now);
vo.setCheck_flag(check_flag);
vo.setInput_man(input_operatorCode);

IPageList pageList =local.pagelist_query(vo, new String[0], Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
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
<title>客户移交审核</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
function CheckAction(){
	if (confirmRefer(document.theform.check_serial_no)) {
		document.theform.handover_flag.value = 2;
		theform.submit();
	}
}

function NoCheckAction(){
	if (confirmRefer(document.theform.check_serial_no)){
		document.theform.handover_flag.value = 3;
		theform.submit();
	}
}

function refreshPage(){
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value+'<%=tempUrl%>';
}

function QueryAction(){
	disableAllBtn(true);
	location.search = '?page=<%=sPage%>&pagesize='+document.theform.pagesize.value
						+ "&managername_before="+document.theform.managername_before.value
						+ "&managername_now="+document.theform.managername_now.value;
}

window.onload = function(){	
		initQueryCondition();
	};
</script>
</head>

<body class="body body-nox" >
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/client_handover_check.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:570px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width:160px"><%=LocalUtilis.language("class.beforeTurnOverManager",clientLocale)%> : </td><!-- 移交前经理 -->
			<td  align="left">
				<select name="managername_before" style="width:110px">
					<%=Argument.getManager_Names(managername_before)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.afterTurnOverManager",clientLocale)%> : </td> <!-- 移交后经理 -->
			<td align="left">
				<select name="managername_now" style="width:110px">
					<%=Argument.getManager_Names(managername_now)%>
				</select>
			</td> 
		</tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction()"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>
<input type="hidden" name="handover_flag"/>

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
			<button type="button" class="xpbutton5" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;
            <!-- 审核通过 -->
			<button type="button" class="xpbutton5" accessKey=q  title="<%=LocalUtilis.language("message.pass",clientLocale)%>" onclick="javascript:CheckAction();"><%=LocalUtilis.language("message.pass",clientLocale)%> (<u>Y</u>)</button>
			&nbsp;&nbsp;
            <!-- 审核不通过 -->
			<button type="button" class="xpbutton5" accessKey=q  title="<%=LocalUtilis.language("message.notPass2",clientLocale)%>" onclick="javascript:NoCheckAction();"><%=LocalUtilis.language("message.notPass2",clientLocale)%> (<u>N</u>)</button>
		</div>
		<br/>
		</td>
	</tr>
</table>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		 <td width="10%" align="center">
		 	<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.getElementsByName('check_serial_no'),this);">
		 	<%=LocalUtilis.language("class.serialNumber",clientLocale)%> <!-- 序号 -->
		 </td>	
         <td width="25%" align="center"><%=LocalUtilis.language("class.beforeTurnOverManager",clientLocale)%></td><!-- 移交前客户经理 -->
         <td width="25%" align="center"><%=LocalUtilis.language("class.afterTurnOverManager",clientLocale)%></td><!-- 移交后客户经理 --> 
		 <td width="25%" align="center">移交的客户</td>      
         <td width="*" align="center"><%=LocalUtilis.language("class.checkFlag",clientLocale)%></td><!-- 审核标志 -->       
    </tr>
<%
int iCount = 0;
int iCurrent = 0;
for (int i=0; i<list.size(); i++) {
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
            	<input type="checkbox" name="check_serial_no" class="selectAllBox" value="<%=map.get("SERIAL_NO")%>" onclick="">
            	<span><%=iCount%></span>
            </td>
            <td align="center"><%=Utility.trimNull(map.get("MANAGERNAME_BEFORE"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("MANAGERNAME_NOW"))%></td>
		    <td align="center"><%=cust_id.intValue()==0? "全部客户": Argument.getCustomerName(cust_id, input_operatorCode)%></td>
            <td align="center"><%=flagStr%></td>        
         </tr>   
<%}
for (int i=0; i<8-iCount; i++) {%>
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>         
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="5">
				&nbsp;&nbsp;
				<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> 
				</b>
			</td>           
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