<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得查询条件
sPage = request.getParameter("page");
sPagesize = request.getParameter("pagesize");
String sQuery = Utility.trimNull(request.getParameter("t1"));
String sQuery1 = Utility.trimNull(request.getParameter("menu_id"));
String sQuery2 = Utility.trimNull(request.getParameter("menu_name"));
Integer role_id = new Integer(Utility.parseInt(request.getParameter("role_id"), 0));

sUrl = sUrl + "&role_id=" + role_id + "&t1=" + sQuery + "&menu_id=" + sQuery1 + "&menu_name=" + sQuery2;

//获得对戏那个
RolerightLocal roleRight = EJBFactory.getRolering();
RolerightVO vo = new RolerightVO();

//取得结果集
vo.setMenu_id(sQuery1);
vo.setMenu_name(sQuery2);

//listProcAll
IPageList pageList = roleRight.listAll2(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function showInfo(menu_id,func_id){
	if(!func_id.checked){	
		var updatehref = 'role_priority_update.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&role_id=<%=role_id%>'+'&menu_id='+menu_id.value+'&func_id='+func_id.value+'&flag=0';
		if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null){
			//sl_update_ok();		
		}
	}
	else{	
		if(!menu_id.checked){menu_id.checked = true;}
		
    	var updatehref = 'role_priority_update.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&role_id=<%=role_id%>'+'&menu_id='+menu_id.value+'&func_id='+func_id.value+'&flag=1';
    	
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null){
			//sl_update_ok();
		}
	}
}

function updateBatch(menu_id, flag){	
	var strconfirm;
	
	if(flag == 1){
		strconfirm="<%=LocalUtilis.language("message.selectAll2",clientLocale)%> ";//全部选中
	}else if(flag == 2){
		strconfirm="<%=LocalUtilis.language("message.cancelAll",clientLocale)%> ";//全部取消
	}
	
	if(sl_confirm(strconfirm)){
		location = 'role_priority_batch.jsp?t1=<%=sQuery%>&page=<%=sPage%>&pagesize=<%=sPagesize%>&role_id=<%=role_id%>' + '&flag=' + flag + '&menu_id=' + menu_id + '&menu_name='+ document.theform.menu_name.value+'&menu_id2='+document.theform.menu_id.value;
	}
}

function updateBatch1(menu_id, flag){	
	var strconfirm="<%=LocalUtilis.language("message.select",clientLocale)%> ";//选中
	
	if (flag){
		flag = 1;
	}
	
	if (!flag){
		flag = 2;
		strconfirm="<%=LocalUtilis.language("message.cancel",clientLocale)%> ";//取消
	}
	if(sl_confirm(strconfirm)){
		location = 'role_priority_batch.jsp?t1=<%=sQuery%>&page=<%=sPage%>&pagesize=<%=sPagesize%>&role_id=<%=role_id%>' + '&flag=' + flag + '&menu_id=' + menu_id + '&menu_name='+ document.theform.menu_name.value+'&menu_id2='+document.theform.menu_id.value;
	}else{
		window.location.reload();
	}
}

function refreshPage(){
	location = 'role_priority_info.jsp?t1=<%=sQuery%>&page=1&pagesize=' + document.theform.pagesize.value+'&role_id='+<%=role_id%>+'&menu_id='+document.theform.menu_id.value+'&menu_name='+document.theform.menu_name.value ;
}

function setiteminfor(tr10,tablePro,flagdisplay,imagex){
	i = flagdisplay.value;

	if(i==0){
		tablePro.style.display="";
		tr10.style.display="";
		flagdisplay.value=1;
	
	}
	else if(i==1){
		tablePro.style.display="none";
		tr10.style.display="none";
		flagdisplay.value=0;
	
	}
}

function StartQuery(){	
		refreshPage();	
}

window.onload = function(){
initQueryCondition()};
</script>
<BODY class="BODY body-nox">
<form name="theform" method="get" action="role_priority_update.jsp">
<!-- 查询浮动框 -->
<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
	   <td align="right"><button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.menuID",clientLocale)%> : </td><td align="left"><INPUT onkeydown="javascript:nextKeyPress(this)" type="text" name="menu_id" size="10" value="<%=sQuery1%>"></td>
		<!--菜单编号-->
		<td align="right"><%=LocalUtilis.language("class.menuName",clientLocale)%> : </td><td align="left"><INPUT onkeydown="javascript:nextKeyPress(this)" type="text" name="menu_name" size="10" value="<%=sQuery2%>"></td>
		<!--菜单名称-->
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td align="center" colspan="4"><button type="button"   class="xpbutton3" accessKey=s name="btnQuery"
									onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td><!--确认-->
		</tr>						
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%> - <%=LocalUtilis.language("message.menuInfo",clientLocale)%> </b>
	</div><!--权限列表-->
	<div align="right" class="btn-wrapper">
		<button type="button"   class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
		&nbsp;&nbsp;&nbsp;&nbsp;<!--查询-->
		<button type="button"   class="xpbutton4" accessKey=a id="btnCheckAll" name="btnCheckAll" onclick="javascript: updateBatch('<%=sQuery1%>', 1);"><%=LocalUtilis.language("message.selectAll2",clientLocale)%> (<u>A</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--全部选中-->
		<button type="button"   class="xpbutton4" accessKey=c id="btnCancelkAll" name="btnCancelAll" onclick="javascript: updateBatch('<%=sQuery1%>', 2);"><%=LocalUtilis.language("message.cancelAll",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--全部取消-->						
		<button type="button"   class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="<%=LocalUtilis.language("message.backLastPage",clientLocale)%> " onclick="javascript:location='role_priority.jsp?t1=<%=sQuery%>';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--返回上一页--><!--返回-->					
	</div>
	<br/>
</div>

<div width="100%">
	<table sort="multi" id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trtagsort">
			<td width="8%"> <%=LocalUtilis.language("class.menuID2",clientLocale)%> </td><!--菜单项编号-->
			<td width="11%"  ><%=LocalUtilis.language("class.menuName2",clientLocale)%> </td><!--菜单项名称-->
			<td width="11%"  ><%=LocalUtilis.language("class.menuInfo",clientLocale)%> </td><!--菜单项功能说明-->
			<td width="11%"  ><%=LocalUtilis.language("class.parentIDName",clientLocale)%> </td><!--父菜单-->
			<td width="11%"  ><%=LocalUtilis.language("class.rsListMenu",clientLocale)%> </td><!--下属功能项-->
		</tr>

		<%  int iCount = 0;
			int iCurrent = 0;
			String menu_id2;
			String menu_name;
			String parent_id_name;
			Map map = null;
			List funcList = null;
			List list = pageList.getRsList();
			Integer func_id = new Integer(0);
			String  func_name = "";
			
			roleRight.listRoleRight(role_id);			
			
			for(int i=0;i<list.size();i++){	
				map = (Map)list.get(i);
				
				menu_id2 = Utility.trimNull(map.get("MENU_ID"));
				menu_name = Utility.trimNull(map.get("MENU_NAME"));
				menu_info = Utility.trimNull(map.get("MENU_INFO"));
				parent_id_name = Utility.trimNull(map.get("PARENT_ID_NAME"));
				
				funcList = roleRight.listFunc_type(menu_id2);	
				
				String checked = "";
				if (roleRight.hasMenu(menu_id2)){
					checked = " checked ";
				}
			%>
			
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" width="7%" align="center" height="25">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" class="tdh"><input type="checkbox" name="M<%=menu_id2%>" value="<%=menu_id2%>" class="flatcheckbox" <%=checked%> onclick="javascript:updateBatch1('<%=menu_id2%>', this.checked);"></td>
							<td width="90%" class="tdh" align="center"><%=menu_id2%></td>
						<tr>
					</table>
				</td>
				<td width="7%" align="center" height="25"><%=menu_name%></td>
				<td width="7%" align="center" height="25"><%=menu_info%></td>
				<td width="7%" align="center" height="25"><%=parent_id_name%></td>
				<td width="7%" align="center" height="25">	
					<a  id="<%=iCurrent%>" name="btnsetinital" href="javascript:setiteminfor(tr<%=(iCurrent)%>,tablePro<%=(iCurrent)%>,document.theform.flagdisplay<%=(iCurrent)%>);">
						<img src="<%=request.getContextPath()%>/images/icons_events.gif" width="16" height="16" title="<%=LocalUtilis.language("class.function",clientLocale)%> " /><!--功能-->
					</a>
					<input type="hidden" name="flagdisplay<%=(iCurrent)%>" value="0">
				</td>
			</tr>
			<tr id="tr<%=(iCurrent)%>" style="display: none">
				<td bgcolor="#FFFFFF" width="44%" align="center" height="25" colspan="4"></td>
				<td width="11%" align="center" height="25" bgcolor="#FFFFFF">
				<table id="tablePro<%=(iCurrent)%>" style="display: none" border="0" width="100%" bgcolor="#FFFFFF">
				<%				
				List roleList = roleRight.listRole(role_id);	
				String checked2 = "";	
						
				for(int b=0;b<roleList.size();b++){				
					Map roleMap = (Map)roleList.get(b);
					func_id = Utility.parseInt(Utility.trimNull(roleMap.get("FUNC_ID")),new Integer(0));
				}
				
				vo.setMenu_id(menu_id2);
		
				for(int j=0;j<funcList.size();j++){	
					Map funMap = (Map)funcList.get(j);
					func_id = Utility.parseInt(Utility.trimNull(funMap.get("FUNC_ID")),null);
					func_name = Utility.trimNull(funMap.get("FUNC_NAME"));					
					if (roleRight.hasFunc(menu_id2, func_id)){
						checked2 = " checked ";
					}else{
					    checked2 = "";
				    }
				%>
					<tr>
						<td width="100%" bgcolor="#FFFFFF">
							<input type="checkbox" <%=checked2%> name="F<%=menu_id2 + func_id%>" value="<%=func_id%>" class="flatcheckbox" onclick="javascript:showInfo(M<%=menu_id2%>,F<%=menu_id2+func_id%>);">
							<%=func_name%>
						</td>
					</tr>
				<%}%>
				</table>
				</td>
			</tr>

				<%
				iCurrent++;
				iCount++;
				}
				
				for (; iCurrent < pageList.getPageSize(); iCurrent++)
				{
				%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" width="7%" align="center" height="25"></td>
				<td width="7%" align="center" height="25"></td>
				<td width="7%" align="center" height="25"></td>
				<td width="7%" align="center" height="25"></td>
				<td width="7%" align="center" height="25"></td>
			</tr>
			<%}%>			
			<tr class="trbottom">	
					<td class="tdh" width="7%" align="left" height="25" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					<!-- <td width="7%" align="center" height="25"></td>
					<td width="7%" align="center" height="25"></td>
					<td width="7%" align="center" height="25"></td>
					<td width="7%" align="center" height="25"></td>  -->
			</tr>
	</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%roleRight.remove();%>
</form>

</BODY>
</HTML>

