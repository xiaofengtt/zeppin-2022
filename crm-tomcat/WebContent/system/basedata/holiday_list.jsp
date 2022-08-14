
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	session.removeAttribute("HolidayCache");
	HolidayLocal local = EJBFactory.getHoliday();
	HolidayVO localVo = new HolidayVO();
	boolean success = false;
	if("POST".equals(request.getMethod().toUpperCase())){
		String[] holiday_ids = request.getParameterValues("holiday_id");
		if(holiday_ids != null){
			localVo.setInput_man(input_operatorCode);
			for(int i = 0; i != holiday_ids.length; i++){
				localVo.setHoliday_id(Integer.valueOf(holiday_ids[i]));
				local.delHoliday(localVo);
			}
			success = true;
		}
	}
	//query
	localVo = new HolidayVO();
	Integer holidayId = Utility.parseInt(request.getParameter("query_holiday_id"), new Integer(0));
	String holidayName = Utility.trimNull(request.getParameter("holiday_name"));
	localVo.setHoliday_id(holidayId);
	localVo.setHoliday_name(holidayName);
	localVo.setInput_man(input_operatorCode);
	IPageList pageList = local.queryHolidays(localVo, Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,8));
 %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>holiday_list.jsp</title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/includes/jQuery/fullcalendar/cupertino/theme.css" />
<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/includes/jQuery/fullcalendar/fullcalendar.css" />
<link rel='stylesheet' type='text/css' href="<%=request.getContextPath()%>/includes/jQuery/fullcalendar/fullcalendar.print.css" media='print' />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css", type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/includes/jQuery/msdropdown/dd.css", type="text/css" rel="stylesheet" />

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/holidayService.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/msdropdown/jquery.dd.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/includes/jQuery/fullcalendar/fullcalendar.js"></script>
<style>
.tip{
	color: red;
}
body {
	margin-top: 1px;
	font-size: 13px;
	font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
}

#calendar {
	width: 900px;
	margin: 0 auto;
}

</style>
</head>
<body class="BODY">
<form action="holiday_list.jsp" method="post" name="theform">

<div>
	<div class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
	<%if(!input_operator.hasFunc(menu_id, 108)){%>
	<!-- <button type="button"  class="xpbutton3" accesskey=f id="queryButton" name="queryButton" style="margin-right: 10px"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button> -->
	<%}if(input_operator.hasFunc(menu_id, 100)){%> 
	<button type="button"  class="xpbutton3"  accesskey=n id="creatButton" name="creatButton" style="margin-right: 10px" ><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
	<%}if(input_operator.hasFunc(menu_id, 101)){%> 
	<button type="button"  class="xpbutton3"  accesskey=d id="btnDelete" name="btnDelete" style="margin-right: 10px"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
	<%}%>
	<button type="button"  class="xpbutton3"  accesskey="c" id="exchange">切换视图</button>
	</div>
	<br/>
</div>
<div id="dd">
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class=trtagsort>
			<td>
				<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.role_id);"/>
				假日名称
			</td>
			<td>日期</td>
			<td>是否创建任务</td>	
			<td>短信问候语</td>
			<td>邮件问候语</td>
			<td>编辑</td>
		</tr>
<%
	int iCurrent = 0;
	List list = pageList.getRsList();
	Map map = null;
	for(int i=0;i<list.size();i++){
		map = (Map)list.get(i);
		holidayId = (Integer)map.get("HOLIDAY_ID");
		holidayName = Utility.trimNull(map.get("HOLIDAY_NAME"));
		String holidayDateCn = Utility.trimNull(map.get("HOLIDAY_DATE_CN"));
		String taskN = Utility.trimNull(map.get("CREATE_TASK_NAME"));
		String smsgreeting = Utility.trimNull(map.get("SMS_GREETING"));
		String emailgreeting = Utility.trimNull(map.get("EMAIL_GREETING"));
%>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td width="100px">
			<input type="checkbox" name="holiday_id" value="<%=holidayId%>" class="flatcheckbox" style="margin-right: 10px"/>
			<%=holidayName %>
		</td>
		<td align="left"><%=holidayDateCn%></td>
		<td align="left"><%=taskN%></td>
		<td align="left"><%=smsgreeting %></td> 
		<td align="left"><%=emailgreeting %></td>
		<td align="center">
		<%if(input_operator.hasFunc(menu_id, 102)){%>
			<a href="javascript:void(0)" class="infoItem" attr="<%=holidayId%>">
				<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
			</a>
		<%}%>	
		</td>
	</tr>
<%
	iCurrent++;}
	for (int i=0;i < pageList.getBlankSize(); i++){
 %>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
<%
	} 
%>
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
	</tr>
	</table>
	<table border="0" width="100%" class="page-link">
		<tr valign="top">
			<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
		</tr>
	</table>
</div>
<div align="center" id="cc" style="position: relative left: 0; top: 0; z-index: 3; display: none">
	<div id='calendar'></div>
</div>
</form>

<div class="floatDiv" style="display: none;">
<div>
	<form>
		<table >
			<tr>
				<td align="right">节日名称 :</td>
				<td width="200px">
					<input type="text" id="holiday_name" name="holiday_name" style="overflow: visible;"/>
				</td>
				<td align="right">农历/公历 :</td>
				<td>
					<select  id="cal_flag" name="cal_flag" style="width: 100px">
						<option value="1">公历</option>
						<option value="2">农历</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">日期 :</td>
				<td>
					<select id="mouth" style="width: 80px">
					</select>
					<select id="day" style="width: 80px">
					</select>
				</td>
				<td align="right">创建服务任务 :</td>
				<td>
					<input type="checkbox" checked="checked" name="creat_task"/>
				</td>
			</tr>
			<tr>
				<td align="right">短信祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="80" name=""></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">邮件祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="80" name=""></textarea>
				</td>
			</tr>
		</table>
	</form>
	<div align="right">
		<button type="button"  class="xpbutton3">确定</button>
		<button type="button"  class="xpbutton3" style="margin-left: 10px;margin-right: 20px">取消</button>
	</div>
</div>
<div style="margin-top: 10px">
	<fieldset>
		<legend>tip</legend>
		<span class="tip">短信祝词：	注：%1代表客户名称</span>
		<span class="tip">邮件祝词：	注：%1代表客户名称</span>
	</fieldset>
</div>
</div>
<div id="showInfoMessage" style="display: none;">
	<table>
		<tr>
			<td align="right">短信祝福内容 :</td>
			<td colspan="3">
					<textarea readonly="readonly" class="edline" style="overflow: auto" cols="60" name="sms"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">邮件祝福内容 :</td>
			<td colspan="3">
				<textarea readonly="readonly" class="edline" style="overflow: auto" cols="60" name="email"></textarea>
			</td>
		</tr>
	</table>	
</div>

<script language=javascript>
<%
	if(success){
%>
alert("删除成功");
location = "holiday_list.jsp";
<%
	}
%>
	$(function(){
//		$(".floatDiv").dialog({
//			width:630
//		});
		$("#showInfoMessage").dialog({
			autoOpen: false,
			width: 500
		});

		$("#creatButton").click(function(){
			
			location.href = "holiday_new.jsp";
			//$(".floatDiv").dialog("open");
		});

		$("#btnDelete").click(function(){
			if(confirmRemove(document.theform.holiday_id)) 
				document.theform.submit();
		});

		$("#exchange").toggle(function(){
			$("#cc").css("display", "block");
			$("#dd").css("display", "none");
			$("#btnDelete").css("display", "none");
		}, function(){
			$("#cc").css("display", "none");
			$("#dd").css("display", "block");
			$("#btnDelete").css("display", "inline");
		});
/*
		var availableTags = [
			"元旦",
			"春节",
			"清明",
			"劳动",
			"端午",
			"中秋",
			"国庆"
		];
		$( "#holiday_name" ).autocomplete({
			source: availableTags
		});

		var abric = {
			"mouth" : ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], 
			"day" : [
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
			]
		};
		var chinese = {
			"mouth" : ["正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "腊月"],
			"day" : [
				"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
				"十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
				"廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
			]
		};

		$("#cal_flag").change(function(){
			if($(this).val() == 1){
				fill(abric);
				$("#mouth").msDropDown();
				$("#day").msDropDown();
			}
			else{
				fill(chinese);
				$("#mouth").msDropDown();
				$("#day").msDropDown();
			}
		});
		fill(abric);
		$("#mouth").msDropDown();
		$("#day").msDropDown();
		$(".floatDiv").dialog("close");
*/
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		var timeCollection;
		
		$('#calendar').fullCalendar({
			theme: true,
			disableResizing : true,
			disableDragging : true,
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month'
			},
			dayNamesShort :['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			editable: true,
			weekMode : 'liquid',
			height: 460,
			dayClick: function(date, allDay, jsEvent, view) {

    		},
			eventClick: function(calEvent, jsEvent, view) {
				alert(calEvent.description);
				
			},
			eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc){
				alert(event.start);
				alert(event.title);
			},
			eventMouseover: function( event, jsEvent, view ) { 
				timeCollection = setTimeout(function(){
					
					$("textarea[name='sms']","#showInfoMessage").val(event.sms)
					$("textarea[name='email']","#showInfoMessage").val(event.email)
					$("#showInfoMessage").dialog("open");
				
				}, 1000)
			},
			eventMouseout: function( event, jsEvent, view ) { 
				clearTimeout(timeCollection);
			},
			loading: function(isLoading, view) {
				//alert('loading');
			},
			events: function(start, end, callback) {

				var events = [];
				var params = {};
				params.startParam = $.fullCalendar.formatDate( start, 'yyyyMMdd');
				params.endParam = $.fullCalendar.formatDate( end, 'yyyyMMdd');
				holidayService.queryHolidays(params, function(data){
					for(next in data){
						var context = {};
						context.title = data[next].HOLIDAY_NAME;
						context.start = format_Date(data[next].DC_DATEINT);
						context.description = data[next].HOLIDAY_DATE_CN;
						context.sms = data[next].SMS_GREETING;
						context.email = data[next].EMAIL_GREETING;
						events.push(context);
					}
					callback(events);
				});
				
			 }
		});


		$(".infoItem").click(function(){
			window.location = "holiday_info.jsp?holiday_id=" + $(this).attr("attr");
		})
	});

function format_Date(date){
	var str = date.toString();
	return str.substr(0, 4) + "-" + str.substr(4, 2) + "-" + str.substr(6,2);
}

function fill(args){
	$("#mouth").empty();
	$("#day").empty();
	var cache;
	var data;
	if(data = $("#mouth").data("cacheM") != undefined){
		cache = $("#mouth").html();
		$("#mouth").html(data);
		$("#mouth").data("cacheM", cache);
		cache = $("#day").html();
		$("#day").html(data);
		$("#day").data("cacheD", cache);
	}else{
		for( next in args['mouth']){
			var str = "0" + (parseInt(next, 10) + 1);
			$("<option>", { "value" : str.substr(str.length - 2, 2), "text" : args['mouth'][next]}).appendTo($("#mouth"));
		}
		for( next in args["day"]){
			var str = "0" + (parseInt(next, 10) + 1);
			$("<option>", { "value" : str.substr(str.length - 2, 2), "text" : args['day'][next]}).appendTo($("#day"));
		}
		$("#mouth").data("cacheM", $(this).html());
		$("#day").data("cacheD", $(this).html());
	}
}

</script>
</body>
</html>
<% local.remove();%>