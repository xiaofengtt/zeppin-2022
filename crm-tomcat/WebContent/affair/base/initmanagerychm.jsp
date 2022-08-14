<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*"%>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer group_id = Utility.parseInt(request.getParameter("group_id"),new Integer(0));
String cno_number = Utility.trimNull(request.getParameter("cno_number"));
String managername = Utility.trimNull(request.getParameter("managername"));
Integer input_man = Utility.parseInt(request.getParameter("input_man"),input_operatorCode);

String tempUrl = "";
String[] totalColumn = new String[0];
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal local = EJBFactory.getTcustmanagers();

vo.setGroup_id(group_id);
vo.setCno_number(cno_number);
vo.setManagername(managername);
vo.setInput_man(input_man);

IPageList pageList =local.pageManagerYchmList_query(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl + "&group_id=" + group_id
				  + "&cno_number=" + cno_number 
				  + "&managername="+managername;
sUrl = sUrl + tempUrl;
//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();
%>

<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<BASE TARGET="_self">
	<title><%=LocalUtilis.language("index.menu.teamManagement",clientLocale)%> </title><!-- 团队管理 -->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
	<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
	<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	var oState = {
		
	}
	
	window.onload = function(){
		initQueryCondition();
		document.getElementById("btnQuery").attachEvent("onclick",QueryAction);
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
		oState.show  = "true";
		var url = "<%=request.getContextPath()%>/affair/base/initmanagerychm_new.jsp";
		if(showModalDialog(url,oState, 'dialogWidth:500px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	function SelfModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/base/initmanagerychm_role.jsp";
		var data = eval("("+_event.getAttribute("lds")+")");
		url = url + "?serial_no="+data["serial_no"];
		if(showModalDialog(url,'', 'dialogWidth:500px;dialogHeight:340px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}	
	}
	
	function QueryModiAction(){

		var url = "<%=request.getContextPath()%>/affair/service/service_define_template.jsp";
		oState.service_value = document.getElementsByName("provideservices")[0].getAttribute("value")||0;
		oState.flag = "edit";
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:370px;status:0;help:0')){
			document.getElementsByName("provideservices")[0].setAttribute("value",oState.service_value);
		}	
	}
	
	function ModiAction(){
		oState.show  = "true";
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/base/initmanagerychm_update.jsp";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		
		url = url +"?serial_no="+ oState.data["serial_no"];
		if(showModalDialog(url,oState, 'dialogWidth:500px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}
	
	function DelSelfAction(){
		var url = "<%=request.getContextPath()%>/affair/base/initmanagerychm_remove.jsp?&serial_no="+arguments[0];

		document.getElementsByName("theform")[0].setAttribute("action",url);
		if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
			document.getElementsByName("theform")[0].submit();
		}
	}
	
	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = '<%=request.getContextPath()%>/affair/base/initmanager.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function QueryAction(){
		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/affair/base/initmanagerychm.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].value;
		url = url +"&group_id="+$("group_id").value;
		url = url +"&cno_number="+$("cno_number").value;
		url = url +"&managername="+$("managername").value;	
		disableAllBtn(true);
		window.location = url;
	}
	
</script>
</head>

<body class="body">
<form id="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;">群组名称 :</td>
			<td  align="left">
				<select id="group_id" name="group_id" style="width:122px;">
					<%=Argument.getDictParamOptions(3062, Utility.trimNull(group_id))%>
				</select>
			</td>
			<td  align="right" style="width: 90px;">客户经理 : </td>
			<td  align="left">
				<input type="text" id="managername" name="managername" value="<%=Utility.trimNull(managername) %>" />
			</td>
			
		</tr>
		<tr>
			<td align="right">专属号码 : </td>
			<td align="left" colspan="3">
				<input type="text" id="cno_number" name="cno_number" value="<%=Utility.trimNull(cno_number) %>" />
			</td> 				
		</tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
	<tr>
		<td align="left">
			<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
            <!-- 查询 -->
			<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- 新建 -->
			<button class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="新建" onclick="javascript:AppendAction();">新建 (<u>N</u>)</button>
		</td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>

<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		<td width="15%"  align="center">群组名称</td>
		<td width="12%"  align="center">专属号码</td>
		<td width="22%"  align="center">客户经理</td>
		<td width="8%"  align="center">是否使用</td>
		<td width="*" align="center">描述</td>
		<td width="5%"  align="center">设置</td>
		<td width="5%"  align="center">修改</td>
		<td width="5%"  align="center">删除</td>
	</tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center"><%=Utility.trimNull(map.get("GROUP_NAME"))%></td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("CNO_NUMBER"))%></td>
            <td ><%=Utility.trimNull(map.get("OP_NAMES"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("IS_USE")).equals("0")?"未使用":"已使用"%></td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("SUMMARY"))%></td>
            <td align="center">
				<img border="0" src="<%=request.getContextPath()%>/images/FUNC20076.gif" align="center" 
					 title="设置 " width="16"  style="cursor:hand;"
					 height="16" onclick="SelfModiAction()" lds="{'serial_no':'<%=map.get("SERIAL_NO")%>'}" >
            </td>  
	        <td align="center">
                <!-- 修改 -->
        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center" 
        			 onclick="ModiAction()" style="cursor:hand;" width="16" height="16"
        			 lds="{'serial_no':'<%=map.get("SERIAL_NO")%>'}"  title="<%=LocalUtilis.language("message.update",clientLocale)%> ">
        	</td>
	        <td align="center">
                <!-- 删除 -->
	        	<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="center" 
	        		 title="<%=LocalUtilis.language("message.delete",clientLocale)%> "  style="cursor:hand;" width="16" height="16" 
	        		 onclick="DelSelfAction('<%=Utility.trimNull(map.get("SERIAL_NO"))%>')" />
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
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="8">
				&nbsp;
				<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> 
				</b>
			</td>
		</tr>   
	</TABLE>
	</td>
	</tr>
	<tr>
    	<td>
    		<table border="0" width="100%">
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