<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),null);
Integer sourceManagerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_source_manager_id")),new Integer(0));
Integer ManagerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_manager_id")),new Integer(0));
Integer input_man = input_operatorCode;
Integer serial_no; 
Integer shareType;
String shareDescription;
Integer shareStatus;
Integer managerID;
Integer ca_id;

//需要用到的名称变量
String  sourceManagerName="";
String  managerName="";
String  ca_name="";
String  statusName="";

String  tempUrl = "";
AuthorizationShareVO  authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();


authorizationShareVO.setSourceManagerID(sourceManagerID);
authorizationShareVO.setManagerID(ManagerID);
authorizationShareVO.setInput_man(input_man);
authorizationShareVO.setInvalid_time(begin_date==null?"":begin_date.toString());
authorizationShareVO.setShareType(new Integer(3)); //3单客户有时限快捷授权
String[] totalColumn = new String[0];

IPageList pageList =authorizationShareLocal.pagelist_query_share(authorizationShareVO,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,10));

tempUrl = tempUrl+ "&q_source_manager_id=" + sourceManagerID+ "&q_manager_id=" + ManagerID+ "&begin_date=" + begin_date;
sUrl = sUrl + tempUrl;

//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.authorizationManagement",clientLocale)%> </title><!-- 授权管理 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	var oState = {
		
	}
	
	window.onload = function(){
		initQueryCondition();
	}
	
	function $(_id){
		return document.getElementById(_id);
	}
	
	function $$(_name){
		return document.getElementsByName(_name)[0];
	}
	
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}
	
	function AppendAction(){
		var url = "<%=request.getContextPath()%>/affair/auth/auth_rapid_new.jsp";
		window.location.href = url;
	}
	
	function ModiAction(serial_no){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/auth/auth_rapid_edit.jsp?serial_no="+serial_no;
		window.location.href = url;
	}

	function BatchModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/auth/auth_rapid_batch_edit.jsp";
		var query = '';
		var check = document.getElementsByName("btnCheckbox");
		var managers = document.getElementsByName("managerId");
		var managerId = 0;
		var multiCustManSelect = false;
		for (var i=0; i != check.length; i++ ){
			if (check[i].checked){
				if (! multiCustManSelect) {
					if (managerId==0)
						managerId = parseInt(managers[i].value, 10);
					else if (managerId!=parseInt(managers[i].value,10))  
						if (! sl_confirm('选择多个目标客户经理的授权记录（您选择了授权给非同一个目标客户经理的授权记录）'))
							return;
						else 
							multiCustManSelect = true;
				}

				if (query=='')
					query += '?serial='+check[i].value;
				else 
					query += '&serial='+check[i].value;
			}
		}
		
		<%--if (multiCustManSelect) 
			query += '&multi_cust_man_select=true';
		--%>

		if (query=='') {
			sl_alert('请选择要修改的数据！');
			return;
		}

		url += query;
		window.location.href = url;
	}

	function checkAll(checkbox, chkboxArrName) {
		var aChkbox = document.getElementsByName(chkboxArrName);		
		for (var i=0; i<aChkbox.length; i++) 
			aChkbox[i].checked = checkbox.checked;		
	}

	function DelAction(){
		var url = "<%=request.getContextPath()%>/affair/auth/auth_rapid_remove.jsp";
		var bool = false;
		document.getElementsByName("theform")[0].setAttribute("action",url);
		var check = document.getElementsByName("btnCheckbox");
		for(var i=0; i != check.length; i++ ){
			if(check[i].checked){
				bool = true;
			}
		}
		if(bool){
			if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
				document.getElementsByName("theform")[0].submit();
			}	
		}else{
			alert("<%=LocalUtilis.language("message.deleteDate",clientLocale)%> ！");//请选择要删除的数据
		}
		
	}

	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = '<%=request.getContextPath()%>/affair/auth/auth_rapid.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	/*查询功能*/
	function QueryAction(){
		syncDatePicker(document.theform.begin_date_picker,document.theform.begin_date);

		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/affair/auth/auth_rapid.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url = url + "&q_source_manager_id=" + $$("q_source_manager_id").getAttribute("value")||"";
		url = url +"&q_manager_id="+$$("q_manager_id").getAttribute("value")||"";
		url = url +"&begin_date="+ $$("begin_date").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}
</script>
</head>

<body class="body body-nox">
<form name="theform" method="post">
<!-- 查询条件 -->
<div id="queryCondition" class="qcMain" style="display:none;width:540px;height:120px;">
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
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.sourceManager",clientLocale)%> : </td><!--源客户经理-->
			<td  align="left">
				<select name="q_source_manager_id" id="q_source_manager_id" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getManager_Value(sourceManagerID)%>
				</select>
			</td>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.targetManager",clientLocale)%> : </td><!--目标客户经理-->
			<td  align="left">
				<select name="q_manager_id" id="q_manager_id" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getManager_Value(ManagerID)%>
				</select>
			</td>			
		</tr>
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td  align="left">
				<input type="text" name="begin_date_picker" class=selecttext style="width: 99px;" <%if(begin_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(begin_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="begin_date" id="begin_date" />	
			</td>
			<td  align="right" style="width: 90px;">&nbsp;</td>
			<td  align="left">&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing="0" cellPadding="0" width="100%" align="center" border="0">
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
		<div class="btn-wrapper">
			<%if (input_operator.hasFunc(menu_id, 108)) {%>
            <!-- 查询 -->
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!-- 新建 -->
			<button type="button"  class="xpbutton3" accessKey=a id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if (input_operator.hasFunc(menu_id, 102)) {%>
            <!-- 批量修改 -->
			<button type="button"  class="xpbutton3" accessKey=m id="btnModi" name="btnModi" title="<%=LocalUtilis.language("message.update",clientLocale)%> " onclick="javascript:BatchModiAction();"><%=LocalUtilis.language("message.update",clientLocale)%> (<u>M</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if (input_operator.hasFunc(menu_id, 101)) {%>
            <!-- 删除 -->
			<button type="button"  class="xpbutton3" accessKey=k id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>K</u>)</button>
			<%}%> 
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
         <td width="9%" align="center"><input type="checkbox" class="selectAllBox" onclick="javascript:checkAll(this,'btnCheckbox');"/>序号</td>
		 <td width="9%" align="center">客户</td>
         <td width="15%" align="center"><%=LocalUtilis.language("class.sourceManager",clientLocale)%></td><!-- 源客户经理 -->
         <td width="9%"  align="center">被授权客户经理</td>
		 <td width="15%" align="center">生效时间</td>
		 <td width="15%" align="center">失效时间</td>
		 <td width="9%"  align="center">状态</td>
		 <td width="*"  align="center">授权描述</td>
         <td width="7%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- 修改 -->
    </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
	serial_no= Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0)); 
	shareType=Utility.parseInt(Utility.trimNull(map.get("ShareType")),new Integer(0));
	shareDescription=Utility.trimNull(Utility.trimNull(map.get("SharDescription")));
	shareStatus=Utility.parseInt(Utility.trimNull(map.get("Status")),new Integer(0));
	sourceManagerID=Utility.parseInt(Utility.trimNull(map.get("SourceManagerID")),new Integer(0));
    
	ca_id  = Utility.parseInt(Utility.trimNull(map.get("CA_ID")),new Integer(0));
	managerID = Utility.parseInt(Utility.trimNull(map.get("ManagerID")),new Integer(0));
    
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(-1));
	ca_name = Argument.getAuthorizationName(ca_id);
	sourceManagerName=Argument.getManager_Name(sourceManagerID);
    managerName=Argument.getManager_Name(managerID);
	statusName=Argument.getStatusName(shareStatus);
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<input type="checkbox" name="btnCheckbox" class="selectAllBox" value="<%=serial_no%>" onclick="">
            	<%=serial_no%>
            </td>
	        <td align="center"><%=cust_id.intValue()==0?"所有客户":Utility.trimNull(map.get("CUST_NAME"))%></td> 
            <td align="center"><%=sourceManagerName%></td>
            <td align="center"><%=managerName%><input type="hidden" name="managerId" value="<%=managerID%>"/></td>
	        <td align="center"><%=Utility.trimNull(map.get("START_TIME"))%></td> 
	        <td align="center"><%=Utility.trimNull(map.get("INVALID_TIME"))%></td> 
	        <td align="center"><%=statusName%></td> 
			<td align="center"><%=shareDescription%></td>  
	        <td align="center">  
	        	<a href="javascript:void(0);"> 
					<%if (input_operator.hasFunc(menu_id, 102)) {%>
                    <!-- 修改 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" onclick="javascript:ModiAction(<%=serial_no%>);" title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
					<%} %>
				</a>
	        </td>     
         </tr>   
<%}%>   
  	
<%
for(int i=0;i<(8-iCount);i++){
%>      	

         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>  
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
			<td align="left" class="tdh" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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
<%authorizationShareLocal.remove();%>