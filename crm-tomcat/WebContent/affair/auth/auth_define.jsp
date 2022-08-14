<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
String ca_description = Utility.trimNull(request.getParameter("ca_description"));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),new Integer(0));
Integer input_man = input_operatorCode;

String tempUrl = "";
AuthorizationVO  vo = new AuthorizationVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();

vo.setCa_id(ca_id);
vo.setCa_name(ca_name);
vo.setCa_description(ca_description);
vo.setManagerID(managerID);
vo.setInput_man(input_man);

String[] totalColumn = new String[0];

IPageList pageList =authorizationLocal.pagelist_query(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl+ "&ca_id=" + ca_id+ "&ca_name=" + ca_name+ "&ca_description=" + ca_description+ "&managerID=" + managerID;

sUrl = sUrl + tempUrl;
//��ҳ����
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();

//���ڸ���managerID��ѯ��managerName���õ��ı���
TcustmanagersLocal custManagersLocal=EJBFactory.getTcustmanagers();
TcustmanagersVO    custManagersVO=new TcustmanagersVO();
List custManagersList=new ArrayList();
Map  custManagersMap=new HashMap();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.authorizationManagement",clientLocale)%> </title><!-- ��Ȩ���� -->
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
		var url = "<%=request.getContextPath()%>/affair/auth/auth_new.jsp";
		oState.newlist_flag = 0;
		if(showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:320px;status:0;help:0')){
			if(oState.newlist_flag == 1){
				showModalDialog("<%=request.getContextPath()%>/affair/base/team_number.jsp?team_id="+oState.team_id,oState, 'dialogWidth:460px;dialogHeight:400px;status:0;help:0');
				location.reload();
			}else{
				sl_update_ok();
				location.reload();
			}
		}
	}
	
	function ModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/auth/auth_edit.jsp";
		oState.flag = "edit";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		if(showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}

	function queryInfo(ca_id,ca_name)
	{
		if(ca_id == "" || ca_id == "0")
			return;
		location="<%=request.getContextPath()%>/affair/auth/auth_member_list.jsp?ca_id=" + ca_id + "&ca_name="+ca_name;
	}
	
	function DelAction(){
		var url = "<%=request.getContextPath()%>/affair/auth/auth_remove.jsp";
		var bool = false;
		document.getElementsByName("theform")[0].setAttribute("action",url);
		var check = document.getElementsByName("btnCheckbox");
		for(var i=0; i != check.length; i++ ){
			if(check[i].checked){
				bool = true;
			}
		}
		if(bool){
			if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ��")){//ȷ��ɾ��
				document.getElementsByName("theform")[0].submit();
			}	
		}else{
			alert("<%=LocalUtilis.language("message.deleteDate",clientLocale)%> ��");//��ѡ��Ҫɾ��������
		}
	}
	
	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = '<%=request.getContextPath()%>/affair/auth/auth_define.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function QueryAction(){
		var _pagesize = document.getElementsByName("pagesize")[0];
		//url ���
		var url = '<%=request.getContextPath()%>/affair/auth/auth_define.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url = url + "&team_id=" + $$("team_id").getAttribute("value");
		url = url +"&team_no="+$$("team_no").getAttribute("value");
		url = url +"&team_name="+$$("team_name").getAttribute("value");
		url = url +"&begin_date="+ $$("begin_date").getAttribute("value");
		url = url +"&end_date="+$$("end_date").getAttribute("value");
		url = url +"&leader_name="+$$("leader_name").getAttribute("value");
		disableAllBtn(true);
		window.location = url;
	}
</script>
</head>

<body class="body body-nox">
<form id="theform" name="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!-- ��ѯ���� -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.teamName",clientLocale)%> : </td><!-- ��Ȩ������ -->
			<td  align="left">
				<input type="text" name="ca_name" value="<%=ca_name%>"/>
			</td>
			<td align="right"><%=LocalUtilis.language("class.leaderName",clientLocale)%> : </td><!-- ��Ȩ������ -->
			<td align="left" >
				<input type="text" name="ca_description" value="<%=ca_description%>"/>
			</td> 
		</tr>
		<tr>
			<td align="center" colspan="4">
				<!-- ȷ�� -->
                <button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction()"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
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
		<td align="right">
		<div class="btn-wrapper">
			<%if (input_operator.hasFunc(menu_id, 108)) {%>
            <!-- ��ѯ -->
			<button type="button"  class="xpbutton3" style="display: none;" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!-- �½� -->
			<button type="button"  class="xpbutton3" accessKey=a id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%}%>
			<%if (input_operator.hasFunc(menu_id, 101)) {%>
            <!-- ɾ�� -->
			<button type="button" type="buttom" class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
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
         <td width="9%" align="center"><%=LocalUtilis.language("class.CA_ID",clientLocale)%> </td><!-- ��Ȩ����� -->
         <td width="15%"  align="center"><%=LocalUtilis.language("class.CA_Name",clientLocale)%> </td><!-- ��Ȩ������ -->                   
         <td width="*"  align="center"><%=LocalUtilis.language("class.CA_DESCRIPTION",clientLocale)%> </td><!-- ��Ȩ������ -->
         <td width="15%" align="center"><%=LocalUtilis.language("class.custManager",clientLocale)%> </td><!-- �����ͻ����� -->
         <td width="9%"  align="center"><%=LocalUtilis.language("message.membersList",clientLocale)%> </td><!-- ��Ա�б� -->
         <td width="7%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- �޸� -->
    </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
	ca_id  = Utility.parseInt(Utility.trimNull(map.get("CA_ID")),new Integer(0));
    ca_name = Utility.trimNull(Utility.trimNull(map.get("CA_NAME")));
	ca_description = Utility.trimNull(Utility.trimNull(map.get("CA_DESCRIPTION")));
	managerID = Utility.parseInt(Utility.trimNull(map.get("ManagerID")),new Integer(0));
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<input type="checkbox" name="btnCheckbox" class="selectAllBox" value="<%=ca_id%>" onclick="">
            	<%=ca_id%>
            </td>
            <td align="left" style="padding-left: 7px;"><%=ca_name%></td>
            <td align="center"><%=ca_description%></td>
            <td align="center"><%=Argument.getManager_Name(managerID)%></td>
            <td align="center">
            	<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:queryInfo('<%=ca_id%>','<%=ca_name%>');">&gt;&gt;</button>
            </td>   
	        <td align="center">  
	        	<a href="javascript:void(0);"> 
					<%if (input_operator.hasFunc(menu_id, 102)) {%>
                    <!-- �޸� -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center" lds="{'ca_id':'<%=ca_id%>','ca_name':'<%=ca_name%>','ca_description':'<%=ca_description%>','managerID':'<%=managerID %>'}"  onclick="ModiAction()"  title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
	        		<%}%>  
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
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- �ϼ� --><!-- �� -->
			<td align="left" class="tdh" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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