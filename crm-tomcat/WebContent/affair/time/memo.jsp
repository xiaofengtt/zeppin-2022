<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
Integer input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"),null);
Integer end_date = Utility.parseInt(request.getParameter("end_date"),null);
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),null);

String tempUrl = "";
RemindersVO vo = new RemindersVO();
RemindersLocal local = EJBFactory.getReminders();

vo.setSerial_no(serial_no);
vo.setInput_man(input_man);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
vo.setCheck_flag(check_flag);

String[] totalColumn = new String[0];

IPageList pageList =local.pagelist_query(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl+ "&serial_no=" + serial_no+ "&input_man=" + input_man+ "&begin_date=" + begin_date+ "&end_date=" + end_date+ "&check_flag=" + check_flag;


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
<title><%=LocalUtilis.language("menu.memo",clientLocale)%> </title><!--备忘录-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
<!--
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
		var url = "memo_new.jsp";
		oState.newlist_flag = 0;
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}
	}
	
	function ModiAction(){
		var _event = window.event.srcElement;
		var url = "memo_edit.jsp";
		oState.flag = "edit";
		oState.data = eval("("+_event.getAttribute("lds")+")");
		if(showModalDialog(url,oState, 'dialogWidth:460px;dialogHeight:320px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}		
	}
	
	
	function DelSelfAction(){
		var url = "memo_remove.jsp?number="+arguments[0];

		document.getElementsByName("theform")[0].setAttribute("action",url);
		if(confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
			document.getElementsByName("theform")[0].submit();
		}
	}
	
	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = 'memo.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function QueryAction(){
		if(!sl_checkDate(document.theform.begin_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> ")){//开始日期
			return false;
		}else{
			syncDatePicker(document.theform.begin_date_picker,document.theform.begin_date);			
		}
		
		if(!sl_checkDate(document.theform.end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> ")){//结束日期
			return false;
		}else{
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);			
		}
		
		if(document.theform.begin_date.value > document.theform.end_date.value){
			alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ！");//结束日期不能比开始日期早
		}
	
		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = 'memo.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url = url +"&begin_date="+ $$("begin_date").getAttribute("value")||"";
		url = url +"&end_date="+$$("end_date").getAttribute("value")||"";
		url = url +"&check_flag="+$$("check_flag").getAttribute("value")||"";

		disableAllBtn(true);
		window.location = url;
	}

-->
</script>
</head>

<body class="body body-nox">
<form name="theform" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td  align="left">
				<input type="text" name="begin_date_picker" class=selecttext style="width: 99px;" <%if(begin_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(begin_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="begin_date" id="begin_date" />	
			</td>	
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
			<td  align="left">
				<input type="text" name="end_date_picker" class=selecttext style="width: 99px;" <%if(end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(end_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="end_date" id="end_date" />	
			</td>	
		</tr>		
		<tr>
			<td  align="right" style="width: 90px;"> <%=LocalUtilis.language("class.memoStatus",clientLocale)%> : </td><!--备忘录状态-->
			<td  align="left" colspan="3">
				<select name="check_flag" style="width:120px;">
					<option value="" ><%=LocalUtilis.language("message.all",clientLocale)%> </option><!--全部-->
					<option value="1"><%=LocalUtilis.language("message.new",clientLocale)%> </option><!--新建-->
					<option value="2"><%=LocalUtilis.language("message.read",clientLocale)%> </option><!--已读-->
					<option value="3"><%=LocalUtilis.language("message.finish",clientLocale)%> </option><!--完成-->
				</select>
			</td>						
		</tr>
		
		<tr>
			<td align="center" colspan="4">
				<!--确定-->
                <button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction()"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
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
		<td align="right" >
		<div class="btn-wrapper">
            <!-- 查询 -->
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
            <!-- 新建 -->
			<button type="button" class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
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
		 <td width="7%"  align="center"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--序号-->
         <td width="*"  align="center"><%=LocalUtilis.language("class.content3",clientLocale)%> </td> <!--备忘录内容-->
         <td width="8%"  align="center"><%=LocalUtilis.language("class.scheduleDate",clientLocale)%> </td><!--备忘录日期-->
         <td width="7%" align="center"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!--修改-->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
    </tr>
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center">
            	<span><%= iCount%></span>
            </td>
            <td align="left" style="padding-left:7px;">
            	<% 
            		if(Utility.trimNull(map.get("CONTENT")).length() > 30 ){
            			out.println(Utility.trimNull(map.get("CONTENT")).substring(0,30)+"...");
            		}else{
            			out.println(Utility.trimNull(map.get("CONTENT")));	
            		}
            	%>
            </td>
            <td align="center">
            		<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("SCHEDULE_DATE")),0))%>
            </td>
            <td align="center">
            	<% 
            		switch(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),0)){
            			case 1:
            				out.print(enfo.crm.tools.LocalUtilis.language("message.new", clientLocale));//新建
            				break;
            			case 2:
            				out.print(enfo.crm.tools.LocalUtilis.language("message.read", clientLocale));//已读
            				break;
            			case 3:
            				out.print(enfo.crm.tools.LocalUtilis.language("message.finish", clientLocale));//完成
            				break;
            			default:
            				out.print("");
            				break;		
            		}
            	%>
            </td>  
	        <td align="center">  
	        	<!--修改-->
                <img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="center"  style="cursor:hand"
                	 lds="{'serial_no':'<%=Utility.trimNull(map.get("SERIAL_NO"))%>','schedule_date':'<%=Utility.trimNull(map.get("SCHEDULE_DATE"))%>','op_code':'<%=Utility.trimNull(map.get("OP_CODE"))%>','op_name':'<%=Utility.trimNull(map.get("OP_NAME"))%>','content':'<%=Utility.trimNull(map.get("CONTENT"))%>','check_flag':'<%=Utility.trimNull(map.get("CHECK_FLAG"))%>'}"  
                	 onclick="ModiAction()"  title='<%=LocalUtilis.language("message.update",clientLocale)%>' width="20" height="15" />
	        </td>
	        <td align="center">
	        	<!--删除-->
                <img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="center" style="cursor:hand"
                title='<%=LocalUtilis.language("message.delete",clientLocale)%> ' 
                onclick="DelSelfAction('<%=Utility.trimNull(map.get("SERIAL_NO"))%>')" width="20" height="15" />
	        </td>                
         </tr>   
<%}%>   
  	
<%
for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){
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
			<!--合计--><!--项-->
            <td align="left" class="tdh" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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
<%local.remove();%>
</form>
</body>
</html>