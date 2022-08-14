<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<% 
//取得页面查询参数
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
String q_active_type = Utility.trimNull(request.getParameter("q_active_type"));
Integer q_manageCode = Utility.parseInt(Utility.trimNull(request.getParameter("q_manageCode")),new Integer(0));
String q_active_theme = Utility.trimNull(request.getParameter("q_active_theme"));
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");

//url设置
String sUrl = "activity_list.jsp?&sPage="+sPage;
String tempUrl = "";
tempUrl = tempUrl+"&q_active_type="+q_active_type;
tempUrl = tempUrl+"&q_active_theme="+q_active_theme;
tempUrl = tempUrl+"&q_manageCode="+q_manageCode;
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO vo = new ActivityVO();

//获得结果集
vo.setActive_flag(new Integer(3));
vo.setStartDate(q_start_date);
vo.setEndDate(q_end_date);
vo.setActive_type(q_active_type);
vo.setActive_theme(q_active_theme);
vo.setManage_code(q_manageCode);
vo.setInput_man(input_operatorCode);

IPageList pageList = activityLocal.pageList_query(vo,totalColumn,t_sPage,t_sPagesize);

//分页辅助参数
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.activityList2",clientLocale)%> </title>
<!--活动管理-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	/*启动加载*/	
	window.onload = function(){
		initQueryCondition();
	}	

	/*选择*/
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}
	
	/*新增方法*/
	function AppendAction(){		
		var url = "activity_add.jsp";		
		if(showModalDialog(url,'', 'dialogWidth:580px;dialogHeight:400px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	/*修改方法*/
	function ModiAction(serial_no){				
		var url = "activity_edit.jsp?serial_no="+serial_no;	
		
		if(showModalDialog(url,'', 'dialogWidth:580px;dialogHeight:450px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	/*删除功能*/
	function DelAction(){
		if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
			sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
			return false;
		}
		 
		if(sl_check_remove()){			
			var url = "activity_del.jsp";
			document.getElementsByName("theform")[0].setAttribute("action",url);
			document.getElementsByName("theform")[0].submit();	
			return true;		
		}
		
		return false;			
	}
	
	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = 'activity_list.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function validate(){
		if((document.getElementById("q_start_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){
		//开始日期
			return false;
		}
		else{
			syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
		}
		
		if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){
		//结束日期
			return false;			
		}
		else{
			syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);			
		}		
		
		return true;	
	}
	
	/*查询功能*/
	function QueryAction(){		
		if(validate()){
			var _pagesize = document.getElementsByName("pagesize")[0];
			
			//url 组合
			var url = "activity_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
			var url = url + "&q_active_type=" + document.getElementById("q_active_type").getAttribute("value");
			var url = url + "&q_active_theme=" + document.getElementById("q_active_theme").getAttribute("value");
			var url = url +"&q_manageCode="+document.getElementById("q_manageCode").getAttribute("value");
			var url = url +"&q_start_date="+document.getElementById("q_start_date").getAttribute("value");
			var url = url +"&q_end_date="+ document.getElementById("q_end_date").getAttribute("value");		
			
			disableAllBtn(true);		
			window.location = url;
		}
	}
	
	
/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}

</script>
</head>

<body class="body body-nox">
<form id="theform" name="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:570px;height:160px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> : </td><!--活动类别-->
			<td  align="left">
				<select name="q_active_type" id="q_active_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getDictParamOptions(3012,"")%>
				</select>
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.activityTheme",clientLocale)%> : </td><!--活动主题-->
			<td  align="left">
				<input type="text" name="q_active_theme" id="q_active_theme" value="<%= q_active_theme%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td  align="left">
				<input type="text" name="q_start_date_picker"  class=selecttext 
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >	&nbsp;&nbsp;
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
			<td  align="left">
				<input type="text" name="q_end_date_picker" class=selecttext 
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >	&nbsp;&nbsp;
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
				<td  align="right"><%=LocalUtilis.language("class.responsibleMan",clientLocale)%> ：</td><!--负责人-->
				<td  align="left">
					<select name="q_manageCode" id="q_manageCode" onkeydown="javascript:nextKeyPress(this)" style="width:148px">
						<%=Argument.getManager_Value(q_manageCode)%>
					</select>	
				</td>	
			<td  align="right" style="width: 90px;">&nbsp;&nbsp;</td>
			<td  align="left">&nbsp;&nbsp;</td>	
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
	<BR>
</div>

<div>
	<div align="left" class="page-title ">
		<b><%=menu_info%></b>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--查询-->
		<button type="button"  class="xpbutton3" accessKey=s id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.append",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.append",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--新增-->
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<!--删除-->
	</div>
	<br/>
</div>

<div valign="middle" align="center">
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%"  >
		<tr class="trh">
			 <td width="15%" align="center">
				 <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
				 <%=LocalUtilis.language("class.activityTheme",clientLocale)%> 
			 </td><!--活动主题-->
	         <td width="15%" align="center"><%=LocalUtilis.language("class.activityCategories",clientLocale)%> </td><!--活动类别-->	         
	         <td width="*" align="center"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--开始时间-->                         
	         <td width="*" align="center"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--结束时间-->
	         <td width="10%" align="center"><%=LocalUtilis.language("class.responsibleMan2",clientLocale)%> </td><!--主负责人-->
	         <td width="6%" align="center"><%=LocalUtilis.language("message.detailInfo",clientLocale)%> </td><!--详细信息-->	   
	         <td width="6%" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑--> 
	     </tr>
	     
<%
	//声明字段
	Iterator iterator = list.iterator();
	Integer serial_no;	
	String active_type_name;
	String active_theme;
	String  start_date;
	String  end_date;	
	String manage_man;
	String customer_type;
	String active_plan;
	String active_trace;
	String active_result;		
		
	while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		active_type_name = Utility.trimNull(map.get("ACTIVE_TYPE_NAME"));
		active_theme = Utility.trimNull(map.get("ACTIVE_THEME"));
		start_date =Utility.trimNull(map.get("START_DATE"));
		end_date = Utility.trimNull(map.get("END_DATE"));
		manage_man = Utility.trimNull(map.get("MANAGE_MAN"));
		customer_type  = Utility.trimNull(map.get("CUSTOMER_TYPE"));
		active_trace = Utility.trimNull(map.get("ACTIVE_TRACE"));
		active_plan = Utility.trimNull(map.get("ACTIVE_PLAN"));
		active_result = Utility.trimNull(map.get("ACTIVE_RESULT")); 
		
		if(start_date.length()>0){
			start_date = start_date.substring(0,16);
		}	
		if(end_date.length()>0){
			end_date = end_date.substring(0,16);
		}
%> 	     
 		<tr class="tr<%=iCount%2%>">
             <td align="center">
             	<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10%">
							<input type="checkbox" name="check_serial_no" value="<%= serial_no%>" class="flatcheckbox"/> 
						</td>
						<td width="90%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;<%= active_theme%></td>
					</tr>
				</table>
             </td>
             <td align="left">&nbsp;&nbsp;<%= active_type_name%></td>             
             <td align="center"><%= start_date%></td>
             <td align="center"><%= end_date%></td>
             <td align="center"><%= manage_man%></td>             
             <td align="center">
             	<button type="button"   class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
         			<IMG id="activeImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
	         	</button>
	         	<input type="hidden" id="activeFlag_display<%=serial_no%>" name="activeFlag_display<%=serial_no%>" value="0">             
             </td>
             <td align="center">              	
              	<a href="javascript:ModiAction(<%=serial_no%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
               	</a>
             </td>
		</tr>  	
		
		<tr id="activeTr<%=serial_no%>" style="display: none">
		<td align="center" bgcolor="#FFFFFF" colspan="7" >
			<div id="activeDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
				<table id="activeTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">
					<tr style="background:F7F7F7;">
						<td  width="15%">&nbsp;&nbsp;<%=LocalUtilis.language("class.targetCustomers",clientLocale)%> ：</td><!--对象客户群-->
						<td  width="*"><%= customer_type%></td>
					</tr>
				
					<tr style="background:F7F7F7;">
						<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activePlan",clientLocale)%> ：</td><!--活动计划和目标-->
						<td><%= active_plan%></td>
					</tr>
					
					<tr style="background:F7F7F7;">
						<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activeTrace2",clientLocale)%> ：</td><!--活动记录-->
						<td><%= active_trace%></td>
					</tr>
					
					<tr style="background:F7F7F7;">
						<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.activeResult",clientLocale)%> ：</td><!--活动评价-->
						<td><%= active_result%></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>  
<%}%>
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>      	

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
		<tr class="tr1">
			<td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>	
		</tr>
	</TABLE>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl)%>
</div>

<% activityLocal.remove();%>
</form>
</body>
</html>