<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.contractManage.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer team_id  = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String team_name = Utility.trimNull(request.getParameter("team_name"));
String team_summary = Utility.trimNull(request.getParameter("team_summary"));
Integer team_admin = Utility.parseInt(request.getParameter("team_admin"),new Integer(0));
String team_admin_name = Utility.trimNull(request.getParameter("team_admin_name"));
Integer input_man = Utility.parseInt(request.getParameter("input_man"),new Integer(0));

String tempUrl = "";
TcoTeamInfoVO vo = new TcoTeamInfoVO();

TcoTeamInfoLocal tcoTeamInfoLocal = EJBFactory.getTcoTeamInfo();

vo.setTeam_id(team_id);
vo.setTeam_name(team_name);
vo.setTeam_summary(team_summary);
vo.setTeam_admin(team_admin);
vo.setTeam_admin_name(team_admin_name);
vo.setInput_man(input_man);

String[] totalColumn = new String[0];

IPageList pageList =tcoTeamInfoLocal.queryByPageList(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl+ "&team_id=" + team_id+ "&team_name=" + team_name+ "&team_summary=" + team_summary+ "&team_admin=" + team_admin;
tempUrl = tempUrl+ "&team_admin_name=" + team_admin_name;

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
<title>项目组管理 </title>
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
		var url = "<%=request.getContextPath()%>/contractManagement/tcoTeamInfo_new.jsp";
		oState.newlist_flag = 0;
		if(showModalDialog(url,oState, 'dialogWidth:480px;dialogHeight:520px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	function ModiAction(){
		var _event = window.event.srcElement;
		var url = "tcoTeamInfo_edit.jsp";
		oState.flag = "edit";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}
	
	function showInfo(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/contractManagement/tcoTeamMember.jsp?team_id="+arguments[0];
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:400px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}	
	}
	
	function DelSelfAction(){
		var url = "tcoTeamInfo_remove.jsp?number="+arguments[0]+"&action=self";

		document.getElementsByName("theform")[0].setAttribute("action",url);
		if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
			document.getElementsByName("theform")[0].submit();
		}
	}
	
	function DelAction(){
		var url = "tcoTeamInfo_remove.jsp";
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
		window.location = '<%=request.getContextPath()%>/contractManagement/tteaminfoset.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function QueryAction(){
		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/contractManagement/tteaminfoset.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url = url + "&team_id=" + $$("team_id").getAttribute("value");
		url = url +"&team_name="+$$("team_name").getAttribute("value");
		url = url +"&team_summary="+ $$("team_summary").getAttribute("value");
		url = url +"&team_admin_name="+$$("team_admin_name").getAttribute("value");
		disableAllBtn(true);
		window.location = url;
	}
</script>
</head>

<body class="body">
<form id="theform" method="post">

<input type="hidden" name="team_id" value="<%=Utility.trimNull(team_id)%>">

<div id="queryCondition" class="qcMain" style="display:none;width:240px;">
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
			<td  align="right" style="width: 90px;">项目组名称 : </td><!-- 项目组名称 -->
			<td  align="left">
				<input type="text" name="team_name" value=""/>
			</td>
		</tr>
		<tr>
			<td align="right">项目组说明 : </td><!-- 项目组说明-->
			<td align="left" >
				<input type="text" name="team_summary" value=""/>
			</td> 
		</tr>
		<tr>
			<td align="right">项目组长 : </td><!-- 项目组长-->
			<td align="left" >
				<input type="text" name="team_admin_name" value=""/>
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
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- 新建 -->
			<button type="button" class="xpbutton3" accessKey=a id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- 删除 -->
			<button type="button" class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		</td>
	</tr>
	<tr>
		<td colspan="5"><hr noshade color="#808080" size="1"></td>
	</tr>
</table>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
         <td width="9%" align="center">项目组编号</td><!-- 项目组编号-->
         <td width="15%"  align="center">项目组名称 </td><!-- 项目组名称 -->                   
         <td width="15%"  align="center">项目组说明</td><!-- 项目组说明 -->
         <td width="15%" align="center">项目组长</td><!-- 项目组长 -->
         <td width="9%"  align="center"><%=LocalUtilis.language("message.membersList",clientLocale)%> </td><!-- 成员列表 -->
         <td width="7%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- 修改 -->
         <td width="7%"  align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- 删除 -->
    </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<input type="checkbox" name="btnCheckbox" class="selectAllBox" value="<%=Utility.trimNull(map.get("TEAM_ID"))%>" onclick="">
            	<%=Utility.trimNull(map.get("TEAM_ID"))%>
            </td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("TEAM_NAME"))%></td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("TEAM_SUMMARY"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("TEAM_ADMIN_NAME"))%></td>
            <td align="center">
            	<button type="button" class="xpbutton2" name="btnEdit" onclick="javascript:showInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
            </td>   
	        <td align="center">  
	        	<a href="javascript:void(0);"> 
                    <!-- 修改 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center" lds="{'team_id':'<%=Utility.trimNull(map.get("TEAM_ID"))%>','team_name':'<%=Utility.trimNull(map.get("TEAM_NAME"))%>','team_summary':'<%=Utility.trimNull(map.get("TEAM_SUMMARY"))%>','team_admin':'<%=Utility.trimNull(map.get("TEAM_ADMIN"))%>','team_admin_name':'<%=Utility.trimNull(map.get("TEAM_ADMIN_NAME"))%>','team_summary':'<%=Utility.trimNull(map.get("TEAM_SUMMARY"))%>'}"  onclick="ModiAction()"  title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
	        	</a>
	        </td>
	        <td align="center">
                    <!-- 删除 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="absmiddle" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="DelSelfAction('<%=Utility.trimNull(map.get("TEAM_ID"))%>')" width="20" height="15" />
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
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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