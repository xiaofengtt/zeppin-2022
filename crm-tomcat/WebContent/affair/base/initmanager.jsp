<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*"%>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer managerid = Utility.parseInt(request.getParameter("managerid"),new Integer(0));
String managername = Utility.trimNull(request.getParameter("managername"));
String extension = Utility.trimNull(request.getParameter("extension"));
String recordextension = Utility.trimNull(request.getParameter("recordextension"));
String dutyname = Utility.trimNull(request.getParameter("dutyname"));
Integer provideservices = Utility.parseInt(request.getParameter("provideservices"),new Integer(0));
Integer input_man = Utility.parseInt(request.getParameter("input_man"),input_operatorCode);

String tempUrl = "";
String[] totalColumn = new String[0];
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

vo.setManagerid(managerid);
vo.setManagername(managername);
vo.setExtension(extension);
vo.setRecordextension(recordextension);
vo.setDutyname(dutyname);
vo.setProvideservices(provideservices);

IPageList pageList =tcustmanagers_Bean.pagelist_query(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl + "&managername=" + managername
				  + "&extension=" + extension 
				  + "&recordextension="+recordextension
				  + "&dutyname=" + dutyname
				  + "&provideservices=" + provideservices;
sUrl = sUrl + tempUrl;
//分页参数
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
	<title><%=LocalUtilis.language("index.menu.teamManagement",clientLocale)%> </title><!-- 团队管理 -->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
	<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
	<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
<!--
	var oState = {
		
	}
	
	window.onload = function(){
		initQueryCondition();
		/*document.getElementById("btnQuery").attachEvent("onclick",QueryAction);
		try{
			var _managername = document.getElementsByName("managername")[0];
			var _extension = document.getElementsByName("extension")[0];
			var _dutyname = document.getElementsByName("dutyname")[0];
			var _provideservices = document.getElementsByName("provideservices")[0];
			var _recordextension = document.getElementsByName("recordextension")[0];
			_managername.setAttribute("value",'<%=managername%>');
			_extension.setAttribute("value",'<%=extension%>');
			_recordextension.setAttribute("value",'<%=recordextension%>');
			_dutyname.setAttribute("value",'<%=dutyname%>');
			_provideservices.setAttribute("value",'<%=provideservices%>');
		}catch(e){
		
		}
		*/
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
		var url = "<%=request.getContextPath()%>/affair/base/initmanager_new.jsp";
		if(showModalDialog(url,oState, 'dialogWidth:550px;dialogHeight:350px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	function SelfModiAction(){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/affair/service/service_define_template.jsp";
		oState.service_value = _event.getAttribute("lds");
		oState.flag = "edit";
		oState.show  = "false";
		if(showModalDialog(url,oState, 'dialogWidth:500px;dialogHeight:320px;status:0;help:0')){
			//document.getElementsByName("provideservices")[0].setAttribute("value",oState.service_value);
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
		var url = "<%=request.getContextPath()%>/affair/base/initmanager_update.jsp";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		if(showModalDialog(url,oState, 'dialogWidth:500px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}
	
	function DelSelfAction(){
		var url = "<%=request.getContextPath()%>/affair/base/initmanager_remove.jsp?&number="+arguments[0];

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
		var url = '<%=request.getContextPath()%>/affair/base/initmanager.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url = url +"&managername="+$$("managername").getAttribute("value");
		url = url +"&extension="+$$("extension").getAttribute("value");
		url = url +"&recordextension="+$$("recordextension").getAttribute("value");
		url = url +"&dutyname="+$$("dutyname").getAttribute("value");
		//url = url +"&provideservices="+ $$("provideservices").getAttribute("value");		
		disableAllBtn(true);
		window.location = url;
	}

-->
</script>
</head>

<body class="body body-nox">

<form id="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;right:200px;">

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
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.managerName",clientLocale)%> : </td><!-- 经理姓名 -->
			<td  align="left">
				<input type="text" name="managername" />
			</td>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.dutyName",clientLocale)%> : </td><!-- 岗位 -->
			<td  align="left">
				<input type="text" name="dutyname"/>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.extension",clientLocale)%> : </td><!-- 分机号码 -->
			<td align="left" >
				<input type="text" name="extension"/>
			</td> 
			
			<td align="right"><%=LocalUtilis.language("class.recordExtension",clientLocale)%> : </td><!-- 录音机号码 -->
			<td align="left" >
				<input type="text" name="recordextension"/>
			</td>					
		</tr>
		<!--<tr>
			<td  align="right" style="width: 90px;">服务类别: </td>
			<td  align="left" colspan="3"> 
				<a href="javascript:void(0);" onclick="QueryModiAction()"> 
						<img border="0" src="/images/edit.gif" align="center" title="选择" width="20" height="15" ><font color="green" >(单击设置)</font>
				</a>
				<input type="hidden" name="provideservices"/>
			</td>	
		</tr>
		-->
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
            <!-- 查询 -->
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- 新建 -->
			<button type="button" class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
	</div>
	<br/>
</div>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		 <td width="8%"  align="center"><%=LocalUtilis.language("class.managerID",clientLocale)%> </td><!-- 客户经理编号 -->
         <td width="*" align="center"><%=LocalUtilis.language("class.managerName",clientLocale)%> </td><!-- 客户经理名称 -->
         <td width="12%"  align="center"><%=LocalUtilis.language("class.extension",clientLocale)%> </td><!-- 分机号码 -->
         <td width="12%"  align="center"><%=LocalUtilis.language("class.recordExtension",clientLocale)%> </td><!-- 录音机号码 -->
         <td width="20%"  align="center"><%=LocalUtilis.language("class.dutyName",clientLocale)%> </td><!-- 岗位（职位） -->
         <td width="7%" align="center"><%=LocalUtilis.language("message.taskPermissions",clientLocale)%> </td><!-- 任务权限 -->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- 修改 -->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- 删除 -->
     </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<%=Utility.trimNull(map.get("ManagerID"))%>
            </td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("ManagerName"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("Extension"))%></td>
            <td align="center"><%=Utility.trimNull(map.get("RecordExtension"))%></td>
            <td align="left" style="padding-left: 7px;"><%=Utility.trimNull(map.get("DutyName"))%></td>
            <td align="center">
                <!-- 查看 -->
				<img border="0" src="<%=request.getContextPath()%>/images/FUNC20076.gif" align="center" 
					 title="<%=LocalUtilis.language("message.view",clientLocale)%> " width="20"  style="cursor:hand;"
					 height="15" onclick="SelfModiAction()" lds="<%=Utility.trimNull(map.get("ProvideServices"))%>">
            </td>    
	        <td align="center">
                <!-- 修改 -->
        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center" 
        			 onclick="ModiAction()" style="cursor:hand;" width="20" height="15"
        			 lds="{'managerid':'<%=map.get("ManagerID")%>','managername':'<%=map.get("ManagerName")%>','extension':'<%=map.get("Extension")%>','recordextension':'<%=map.get("RecordExtension")%>','dutyname':'<%=map.get("DutyName")%>','provideservices':'<%= map.get("ProvideServices")%>'}"  title="<%=LocalUtilis.language("message.update",clientLocale)%> ">
        	</td>
	        <td align="center">
                <!-- 删除 -->
	        	<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="center" 
	        		 title="<%=LocalUtilis.language("message.delete",clientLocale)%> "  style="cursor:hand;" width="20" height="15" 
	        		 onclick="DelSelfAction('<%=Utility.trimNull(map.get("ManagerID"))%>')" />
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