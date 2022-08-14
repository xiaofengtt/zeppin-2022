<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//database
Integer d_op_code = Utility.parseInt(request.getParameter("d_op_code"),new Integer(0));
Integer d_city_id = Utility.parseInt(request.getParameter("d_city_id"),new Integer(0));
Integer d_role_id = Utility.parseInt(request.getParameter("d_role_id"),new Integer(0));
String d_op_name = Utility.trimNull(request.getParameter("d_op_name"));
Integer d_status = Utility.parseInt(request.getParameter("d_status"),new Integer(1)); 
Integer d_depart_id = Utility.parseInt(request.getParameter("d_depart_id"),new Integer(0)); 
Integer flag = Utility.parseInt(request.getParameter("flag"),new Integer(0));

OperatorLocal local = EJBFactory.getOperator();
TOperatorVO vo = new TOperatorVO();

String[] totalColumn = new String[0];
String tempUrl = "";

vo.setOp_code(d_op_code);
vo.setRole_id(d_role_id);
vo.setOp_name(d_op_name);
vo.setStatus(d_status);
vo.setDepart_id(d_depart_id);
IPageList pageList  = local.listProcPage(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
tempUrl = "&d_op_code=" + d_op_code + "&d_role_id=" + d_role_id + "&d_op_name=" + d_op_name + "&d_status=" + d_status+"&d_depart_id"+d_depart_id+"&flag="+flag;
sUrl = sUrl + tempUrl;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
	function $$(_name){
		return document.getElementsByName(_name)[0];
	}
	//新增
	function newInfo(){
		if(showModalDialog('operator_new.jsp', '', 'dialogWidth:520px;dialogHeight:450px;status:0;help:0') != null){
			sl_update_ok();
			location.reload();
		}else{
		    location.reload();
		}
	}
	//编辑
	function showInfo(op_code){	
		if(showModalDialog('operator_update.jsp?newflag=0&op_code=' + op_code, '', 'dialogWidth:550px;dialogHeight:450px;status:0;help:0') != null){
			sl_update_ok();
			location.reload();
		}else{
			location.reload();
		}
	}
	//角色设置
	function setRole(op_code){     
      location='operator_role.jsp?op_code='+op_code;
	}
	//PageList
	function refreshPage(){
		if(!sl_checkNum($$("d_op_code"), "<%=LocalUtilis.language("class.opCode",clientLocale)%> ", 10, 0)) return;//员工编号
		if(!sl_check($$("d_op_name"), "<%=LocalUtilis.language("class.opName",clientLocale)%> ", 20, 0)) return;//员工姓名
		disableAllBtn(true);
		location = 'operator.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value + "&d_op_code=" + $$("d_op_code").getAttribute("value") 
			+ "&d_role_id=" + $$("d_role_id").getAttribute("value") 
			+ "&d_op_name=" + $$("d_op_name").getAttribute("value")
			+ "&d_status=" + $$("d_status").getAttribute("value")
			+ "&d_depart_id=" + $$("d_depart_id").getAttribute("value")
			+ "&flag=" + $$("flag").getAttribute("value");
	}
	//PageList
	function StartQuery(){
		refreshPage();
	}
	
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}

	function sysOperator(){
		if(confirm("系统确认：\n\n该操作将初始化操作员表，您确认要同步操作员数据吗？")){
			document.theform.action = "operator_syschro.jsp";
			document.theform.submit();
		}
	}

	window.onload = function(){
		initQueryCondition();
		<%
			if(d_status.intValue() !=0){
		%>
			selectIndex(document.getElementsByName("d_status")[0],<%=d_status%>);
		<%
			}
		%>
	}

</script>
</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="post" action="operator_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<input type="hidden" name="flag" value="<%=flag%>">
<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="4">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.opCode",clientLocale)%> : </td><!--员工编号-->
			<td align="left"><input type="text" name="d_op_code" onkeydown="javascript:nextKeyPress(this)" size="15" value="<%=d_op_code.intValue()==0?"":d_op_code.toString()%>" style="width:120px;"></td>
			<td align="right"><%=LocalUtilis.language("class.opName",clientLocale)%> :</td><!--员工姓名-->
			<td align="left"><input type="text" name="d_op_name" onkeydown="javascript:nextKeyPress(this)" size="15" value="<%=d_op_name%>" style="width:120px;"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.dRoleID",clientLocale)%> :</td><!--员工角色-->
			<td align="left">
			    <select name="d_role_id" style="width:120px">
			        <%=Argument.getRolename(d_role_id)%>
			    </select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.dStatus",clientLocale)%> :</td><!--员工状态-->
			<td align="left">
				 <select id="d_status" name="d_status" style="width:120px">
				 	<option value="0"><%=LocalUtilis.language("message.all",clientLocale)%> </option><!--全部-->
 					<option value="1" selected="selected"><%=LocalUtilis.language("message.normal",clientLocale)%> </option><!--正常-->
 					<option value="2"><%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> </option><!--注销-->
 				</select>
			</td>
		</tr>
		<tr>
		    <td align="right" ><%=LocalUtilis.language("class.departID2",clientLocale)%> :</td><!--所属部门-->
		    <td  align="left">
		        <select id="d_depart_id" name="d_depart_id" style="width:120px">
		            <%=Argument.getDepartOptions1(d_depart_id)%>
		        </select>     
		    </td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"   class="xpbutton3" accessKey=s name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td><!--确认-->
		</tr>						
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
	<%@ include file="menu.inc"%>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" class="page-title">		
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
					<td align="right">
					<div class="btn-wrapper">
						<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
						&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if (input_operator.hasFunc(menu_id, 100)){%>
						<button type="button"   class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;<!--新建记录--><!--新建-->
						<%}%> <%if (input_operator.hasFunc(menu_id, 101)){%>
						<button type="button"   class="xpbutton3" accessKey=d id="btnCancel" name="btnCancel" title="<%=LocalUtilis.language("message.recordsSelectCancel",clientLocale)%> " onclick="javascript:if(confirmCancel(document.theform.op_code)) {  document.theform.submit();}"><%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> (<u>D</u>)</button>
						&nbsp;&nbsp;&nbsp;<!--注销所选记录--><!--注销-->
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 103)){%>
						<button type="button"   class="xpbutton5" accessKey=s id="btnSyschro" name="btnSyschro" title="信托操作员同步" onclick="javascript:sysOperator();"><%=LocalUtilis.language("message.synchronize",clientLocale)%>(<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<%if(flag.intValue()==1){ %>
						<button type="button"   class="xpbutton3" accessKey=b id="btnBack" name="btnBack" title="返回 " onclick="location.href='role.jsp'">返回 (<u>B</u>)</button>	
						<%} %>
						</div>
						<br/>
					</td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trtagsort">
					<td width="60px">
						<input type="checkbox" name="btnCheckbox" 
							class="selectAllBox" onclick="javascript:selectAll(document.theform.op_code);"> 
						<%=LocalUtilis.language("class.ID",clientLocale)%> <!--编号-->
					</td>					
					<td><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
					<td><%=LocalUtilis.language("class.longUser",clientLocale)%> </td><!--登陆名称-->
					<td><%=LocalUtilis.language("class.dStatus",clientLocale)%></td>
					<td><%=LocalUtilis.language("class.loginTime",clientLocale)%> </td><!--最近登录-->
					<td width="6%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
					<td width="6%"><%=LocalUtilis.language("menu.roleSet",clientLocale)%> </td><!--角色设置-->
				</tr>
				<%int iCount = 0;
				int iCurrent = 0;
				Map map = null;
				List list = pageList.getRsList();
				Integer op_code = new Integer(0);
				String opName = "";
				Integer reg_date = new Integer(0);
				String login_time;
				for(int i=0;i<list.size();i++){	
					map = (Map)list.get(i);
					op_code = Utility.parseInt(Utility.trimNull(map.get("OP_CODE")),null);
					opName = Utility.trimNull(map.get("OP_NAME"));
					reg_date = Utility.parseInt(Utility.trimNull(map.get("REG_DATE")),null);
					login_time = Utility.trimNull(map.get("LOGIN_TIME"));
					status =  Utility.parseInt(Utility.trimNull(map.get("STATUS")),0);
					String long_user = Utility.trimNull(map.get("LOGIN_USER"));
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td width="60px">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" ><input type="checkbox" name="op_code" value="<%=op_code%>" class="flatcheckbox"></td>
							<td width="90%" align="center"><%=op_code%></td>
						</tr>
					</table>
					</td>
					<td align="center" style="padding-left: 5px;"><%=opName%></td>
					<td align="center" style="padding-left: 5px;"><%=long_user%></td>
					<td align="center" style="padding-left: 5px;">
						<%if(status==1){%>
							<%=LocalUtilis.language("message.normal",clientLocale)%>
						<%}else{%>
							<%=LocalUtilis.language("index.msg.cancellation",clientLocale)%>
						<%} %>
					</td>
					<td align="center" style="padding-left: 5px;"><%=login_time %></td>
					<td align="center">
					<%if (input_operator.hasFunc(menu_id, 102)){%>						
						<a href="javascript:showInfo(<%=op_code%>)">
							<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
						</a>
					<%}%>
					</td>
					<td align="center" >
					<%if (input_operator.hasFunc(menu_id, 102)){%>					
						<a href="javascript:setRole('<%=op_code%>');">
							<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title="<%=LocalUtilis.language("menu.roleSet",clientLocale)%> " />
							<!--角色设置-->
						</a>
					<%}%>				
					</td>
				</tr>
					<%
					iCurrent++;
					iCount++;
					}
					
					for (int i=0;i < pageList.getBlankSize(); i++)
					{
					%>
				<tr class="tr<%=(i % 2)%>">
					<td width="30px"></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
					<%}%>
				<tr class="trbottom">
					<td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
				</tr>
			</table>

			

			<table border="0" width="100%" class="page-link">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					<td align="right">
					
					<input type="hidden" class="xpbutton3" accessKey=r id="btnRefresh" name="btnRefresh" title="<%=LocalUtilis.language("message.currentPageRefrash",clientLocale)%> " onclick="javascript:refreshPage();"/><!--刷新(<u>R</u>)</button>
					&nbsp;&nbsp;&nbsp;--><!--刷新当前页面-->
					<input type="hidden" class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="<%=LocalUtilis.language("message.backLastPage",clientLocale)%> " onclick="javascript:history.back();"/><!--返回(<u>B</u>)</button>
					&nbsp;&nbsp;&nbsp;--></td><!--返回上一页-->
				</tr>
			</table>

			</TD>
		</TR>
	</TABLE>
	</TD>
	</TR>
</TABLE>
</form>	
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%local.remove(); %>