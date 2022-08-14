<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer serial_no =  Utility.parseInt(request.getParameter("serial_no"),null);
Integer team_id =  Utility.parseInt(request.getParameter("team_id"),null);

String tempUrl = "";
TmanagerteammembersVO vo = new TmanagerteammembersVO();
TmanagerteamsLocal tmanagerteammembers_Bean = EJBFactory.getTmanagerteams();

vo.setSerial_no(serial_no);
vo.setTeam_id(team_id);

String[] totalColumn = new String[0];

List list = null;
int flag = 0;
list = tmanagerteammembers_Bean.list_query1(team_id);
if(list == null || list.size( )== 0){
	list = tmanagerteammembers_Bean.list_query(vo);
	flag = 1;
}



tempUrl = tempUrl+ "&serial_no=" + serial_no+ "&team_id=" + team_id;

sUrl = sUrl + tempUrl;
//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;

%>
<HTML>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.teamMemberManage",clientLocale)%> </title><!-- 团队成员管理 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	var oState = {	
	}
	window.onload = function(){
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
		document.getElementsByName("refresh_flag")[0].setAttribute("value","refresh");
		var url = "<%=request.getContextPath()%>/affair/base/team_number_new.jsp?serial_no="+'<%=Utility.trimNull(serial_no)%>'+"&team_id="+'<%=Utility.trimNull(team_id)%>';
		oState.newlist_flag = 0;
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			if(oState.newlist_flag == 1){ //refresh
				theform.submit();
			}
		}
	}
	
	function ModiAction(){
		var _event = window.event.srcElement;
		oState.data = eval("("+_event.getAttribute("lds")+")");
		oState.newlist_flag = 0;
		var url = "<%=request.getContextPath()%>/affair/base/team_number_edit.jsp?serial_no="+oState.data.serial_no+"&team_id="+oState.data.team_id+"&managerid="+oState.data.managerid+"&description="+oState.data.description;
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			if(oState.newlist_flag == 1){ //refresh
				theform.submit();
			}
		}		
	}
	
	function DelSelfAction(){
		var url = "<%=request.getContextPath()%>/affair/base/team_number_remove.jsp?number="+arguments[0]+ "&team_id=" + '<%=Utility.trimNull(team_id)%>'+"&serial_no="+'<%=Utility.trimNull(serial_no)%>';

		document.getElementsByName("theform")[0].setAttribute("action",url);
		if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
			document.getElementsByName("theform")[0].submit();
		}
	}
</script>
</head>

<body class="BODY body-nox">
<form id="theform" method="post" action="<%=request.getContextPath()%>/affair/base/team_number.jsp">

<input type="hidden" name="refresh_flag" />
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="team_id" value="<%=team_id%>"/>

<table cellSpacing=0 cellPadding=0 width="100%"  border=0>
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<%if(flag==1){%>
	<tr>
		<td align="right" >
		<div class="btn-wrapper">
            <!-- 新建 -->
			<button  class="xpbutton3" accessKey=a id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>&emsp;
			<!-- 关闭 -->
            <button type="button" class="xpbutton3" accessKey=n id="btnAppend" name="btnClose" title="<%=LocalUtilis.language("message.close",clientLocale)%> " onclick="javascript:window.returnValue=null;window.close();;"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>N</u>)</button>
			<input type="submit" name="ref_btn" style="display: none" value=""/>
		</div>
		</td>
	</tr>
	<%} %>
</table>
<table valign="middle" align="center" cellspacing="0" cellpadding="3" width="100%" >
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trh">	
	         <td style="width: 105px;" align="center"><%=LocalUtilis.language("class.memberName",clientLocale)%> </td><!-- 成员名称 -->
		<%if(flag==1){%>
	         <td  align="center"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> </td><!-- 描述 -->                        
	         <td style="width: 50px;" align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- 修改 -->
	         <td style="width: 50px;" align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- 删除 -->
	         <td style="width: 14px;"></td>
		<%} %>
	    </tr>
    </table>
 <div style="height:210px;  margin: 0 0 0 0; overflow-y: scroll; overflow-x: no;" >   
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>" style="height: 25px;">
			
		<%if(flag==1){ %>
            <td align="center" ><%=Utility.trimNull(map.get("MANAGERNAME"))%></td>		
            <td align="center" ><%=Utility.trimNull(map.get("DESCRIPTION"))%></td> 
	        <td align="center" style="width: 50px;">  
	        	<a href="javascript:void(0);"> 
	        		<button type="button" lds="{'serial_no':'<%=Utility.trimNull(map.get("SERIAL_NO"))%>','team_id':'<%=Utility.trimNull(map.get("TEAM_ID"))%>','team_no':'<%=Utility.trimNull(map.get("TEAM_NO"))%>','team_name':'<%=Utility.trimNull(map.get("TEAM_NAME"))%>','managerid':'<%=Utility.trimNull(map.get("MANAGERID"))%>','managername':'<%=Utility.trimNull(map.get("MANAGERNAME"))%>','description':'<%=Utility.trimNull(map.get("DESCRIPTION"))%>'}"  onclick="ModiAction()">
	        			<!-- 修改 -->
                        <img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center"   title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="15" height="15">
	        		</button>
	        	</a>
	        </td>
	        <td align="center" style="width: 50px;">
	        	<button type="button" onclick="DelSelfAction('<%=Utility.trimNull(map.get("SERIAL_NO"))%>')">
                    <!-- 删除 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="absmiddle" title="<%=LocalUtilis.language("message.delete",clientLocale)%> "  width="15" height="15" />
	        	</button>
	        </td> 
		<%}else{ %>
			<td align="center"><%= Utility.trimNull(map.get("TEAM_NAME"))%></td>
		<%} %>               
         </tr>   
<%}%>   
  	
<%
for(int i=0;i<(8-iCount);i++){
%>      	

         <tr class="tr0" style="height: 25px;">
		<%if(flag==1){%>
            <td align="center" style="width: 105px;" >&nbsp;</td>		
            <td align="center">&nbsp;</td>
            <td align="center" style="width: 50px;">&nbsp;</td>
            <td align="center" style="width: 50px;">&nbsp;</td>    
		<%}else{ %>
			<td align="center"></td>
		<% }%>	
         </tr>           
<%}%> 
	</TABLE>
</div>	
	</td>
	</tr>
</table>

</form>
</body>
</html>