<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.service.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String link_context = "";
Integer serial_no = null;
Integer begin_date = null;
Integer end_date = null;
Integer schedule_type = null;
Integer check_flag = null;
Integer imput_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

CheckService cs = new CheckService();
link_context = cs.getScheDulesList(serial_no,begin_date,end_date,schedule_type,check_flag,imput_man);
link_context = link_context.replaceAll("\\r\\n", "\\\\n");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title><%=LocalUtilis.language("menu.calendar",clientLocale)%></title><!--日历-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.7.2.custom.css' />
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/includes/jQuery/css/redmond/fullcalendar.css' />
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/js/ui.core.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/js/ui.draggable.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/js/ui.resizable.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/fullcalendar_<%=languageType%>.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/checkService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript'>
	var j$ = jQuery.noConflict();
	var oState = {
		new_url:'calendar_new.jsp',
		modi_url:'calendar_edit.jsp'
	};

	//包装JSON
	var json_array = <%=link_context%>||[];
	
	//初始化加载
	j$(document).ready(function() {	
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		j$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay,newBtn'
			},
			editable: true,
			events: json_array,
			eventClick: function(event) {
				if (showModalDialog(oState.modi_url,event,'dialogWidth:460px;dialogHeight:370px;status:0;help:0')){
					alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
					window.location.reload();
				}
				return false;
			},
			eventDrop: function(event, delta) { // 用与Drop
				checkService.updateScheDulesList(
					event.serial_no,
					DateAction(event.start),
					DateAction(event.end),
					event.schedule_type,
					'<%=input_operatorCode%>',
					function(data){
						if(data==1){
							DWRUtil.useLoadingMessage("<%=LocalUtilis.language("message.updateSchedule",clientLocale)%> ");  //更新日程成功
						}else{
							alert(data);
							window.location.reload();
						}
					}
				);
			},
			eventResize:function(event){
				checkService.updateScheDulesList(
					event.serial_no,
					DateAction(event.start),
					DateAction(event.end),
					event.schedule_type,
					'<%=input_operatorCode%>',
					function(data){
						if(data==1){
							DWRUtil.useLoadingMessage("<%=LocalUtilis.language("message.updateSchedule",clientLocale)%> ");  //更新日程成功
						}else{
							alert(data);
							window.location.reload();
						}
					}
				);
			},
			loading: function(bool) {
				if (bool) j$('#loading').show();
				else j$('#loading').hide();
			}
		});
		
	});
	
	//新增事件
	function onnewBtn(){
		if(showModalDialog(oState.new_url,'','dialogWidth:460px;dialogHeight:370px;status:0;help:0')){
			window.location.reload();
		}
	}
	//日期增减
	function DateAction(dateObj){
		var yyyy = dateObj.getFullYear();
		var MM = dateObj.getMonth()+1;
		var d = dateObj.getDate();
		var hh = dateObj.getHours();
		var mm = dateObj.getMinutes();
		var ss = dateObj.getSeconds();
		if(MM<=9){
			MM = "0"+MM;
		}
		if(d<=9){
			d="0"+d;
		}
		if(mm<=9){
			mm="0"+mm;
		}
		if(ss<=9){
			ss="0"+ss;
		}
	    return yyyy+"-"+MM+"-"+d+" "+hh+":"+mm+":"+ss;	
	}
	
</script>
<style type='text/css'>
	body {
		margin-top: 40px;
		text-align: center;
		font-size: 13px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		}

	#calendar {
		width: 900px;
		margin: 0 auto;
		}
</style>
</head>
<body>
<div id='calendar'></div>
</body>
</html>