<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer team_id  = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String team_no = Utility.trimNull(request.getParameter("team_no"));
String team_name = Utility.trimNull(request.getParameter("team_name"));
Integer create_date = Utility.parseInt(request.getParameter("create_date"),new Integer(0));
Integer leader = Utility.parseInt(request.getParameter("leader"),new Integer(0));
Integer input_man = Utility.parseInt(request.getParameter("input_man"),new Integer(0));

Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
String leader_name = Utility.trimNull(request.getParameter("leader_name"));

String tempUrl = "";
TmanagerteamsVO vo = new TmanagerteamsVO();

TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

vo.setTeam_id(team_id);
vo.setTeam_no(team_no);
vo.setTeam_name(team_name);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
vo.setLeader_name(leader_name);
vo.setInput_man(input_man);

String[] totalColumn = new String[0];

IPageList pageList =tmanagerteams_Bean.pagelist_query(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl+ "&team_id=" + team_id+ "&team_no=" + team_no+ "&team_name=" + team_name+ "&begin_date=" + begin_date+ "&end_date=" + end_date;
tempUrl = tempUrl+ "&leader_name=" + leader_name;

sUrl = sUrl + tempUrl;
//��ҳ����
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();
%>


<HTML>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.teamManagement",clientLocale)%> </title><!-- �Ŷӹ��� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar.js" LANGUAGE="javascript" ></SCRIPT>
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
		var url = "<%=request.getContextPath()%>/affair/base/team_new.jsp";
		oState.newlist_flag = 0;
		if(showModalDialog(url,oState, 'dialogWidth:480px;dialogHeight:320px;status:0;help:0')){
			if(oState.newlist_flag == 1){
				showModalDialog("<%=request.getContextPath()%>/affair/base/team_number.jsp?team_id="+oState.team_id,oState, 'dialogWidth:460px;dialogHeight:400px;status:0;help:0');
				location.reload();
			}else{
				sl_update_ok();
				location.reload();
			}
		}
	}
	
	function ModiAction(mark_flag){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/base/team_edit.jsp?mark_flag="+mark_flag;
		oState.flag = "edit";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		if(showModalDialog(url,oState, 'dialogWidth:500px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}
	
	function showInfo(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/base/team_number.jsp?team_id="+arguments[0];
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:400px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}	
	}
	
	function DelSelfAction(){
		var url = "<%=request.getContextPath()%>/affair/base/team_remove.jsp?number="+arguments[0]+"&action=self";

		document.getElementsByName("theform")[0].setAttribute("action",url);
		if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ��")){//ȷ��ɾ��
			document.getElementsByName("theform")[0].submit();
		}
	}
	
	function DelAction(){
		var url = "<%=request.getContextPath()%>/affair/base/team_remove.jsp";
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
		window.location = '<%=request.getContextPath()%>/affair/base/team.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function QueryAction(){
		var _pagesize = document.getElementsByName("pagesize")[0];
		//url ���
		var url = '<%=request.getContextPath()%>/affair/base/team.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
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

<input type="hidden" name="team_id" value="<%=Utility.trimNull(team_id)%>">
<input type="hidden" name="team_no" value="<%=Utility.trimNull(team_no)%>">

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
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.teamName",clientLocale)%> : </td><!-- �Ŷ����� -->
			<td  align="left">
				<input type="text" name="team_name" value="<%=team_name%>"/>
			</td>
			<td align="right"><%=LocalUtilis.language("class.leaderName",clientLocale)%> : </td><!-- ���������� -->
			<td align="left" >
				<input type="text" name="leader_name" value="<%=leader_name%>"/>
			</td> 
		</tr>
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!-- ��ʼ���� -->
			<td  align="left">
				<input type="text" id="q_start_date_picker" name="begin_date" value="" onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);"/>
			</td>	
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!-- �������� -->
			<td  align="left">
				<input type="text" id="q_end_date_picker" name="end_date" value="" onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);"/>
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
            <!-- ��ѯ -->
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- �½� -->
			<button type="button" class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- ɾ�� -->
			<button type="button" class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
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
         <td width="9%" align="center"><%=LocalUtilis.language("class.teamID",clientLocale)%> </td><!-- �Ŷӱ�� -->
         <td width="*"  align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!-- �Ŷ����� -->   
		 <td width="10%" align="center">�ϼ��Ŷ�����</td>                
         <td width="8%"  align="center"><%=LocalUtilis.language("class.createDate",clientLocale)%> </td><!-- �������� -->
         <td width="15%" align="center"><%=LocalUtilis.language("class.leaderName",clientLocale)%> </td><!-- ���������� -->
         <td width="15%" align="center">�Ƿ�ΪӪ���Ŷ� </td>
         <td width="9%"  align="center"><%=LocalUtilis.language("message.membersList",clientLocale)%> </td><!-- ��Ա�б� -->
         <td width="7%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- �޸� -->
         <td width="7%"  align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- ɾ�� -->
    </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<input type="checkbox" name="btnCheckbox" class="selectAllBox" value="<%=Utility.trimNull(map.get("TEAM_ID"))%>" onclick="">
            	<%=Utility.trimNull(map.get("TEAM_NO"))%>
            </td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("TEAM_NAME"))%></td>
			<td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("PARENT_NAME"))%></td>
            <td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("CREATE_DATE")),new Integer(0)))%></td>
            <td align="center"><%=Utility.trimNull(map.get("LEADER_NAME"))%></td>
            <td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("MARK_FLAG")), new Integer(2)).intValue() == 1 ? "��" : "��"%></td>
            <td align="center">
            	<button type="button" class="xpbutton2" name="btnEdit" onclick="javascript:showInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
            </td>   
	        <td align="center">  
	        	<a href="javascript:void(0);"> 
                    <!-- �޸�  -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center" lds="{'parent_name':'<%=Utility.trimNull(map.get("PARENT_NAME"))%>','team_id':'<%=Utility.trimNull(map.get("TEAM_ID"))%>','team_no':'<%=Utility.trimNull(map.get("TEAM_NO"))%>','team_name':'<%=Utility.trimNull(map.get("TEAM_NAME"))%>','create_date':'<%=Utility.trimNull(map.get("CREATE_DATE"))%>','leader':'<%=Utility.trimNull(map.get("LEADER"))%>','leader_name':'<%=Utility.trimNull(map.get("LEADER_NAME"))%>','description':'<%=Utility.trimNull(map.get("DESCRIPTION"))%>'}"  onclick="ModiAction('<%=Utility.parseInt(Utility.trimNull(map.get("MARK_FLAG")), new Integer(2)) %>')"  title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
	        	</a>
	        </td>
	        <td align="center">
                    <!-- ɾ�� -->
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
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- �ϼ� --><!-- �� -->
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