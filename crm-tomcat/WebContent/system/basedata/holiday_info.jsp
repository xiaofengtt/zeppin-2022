<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	Integer holiday_id = Utility.parseInt(request.getParameter("holiday_id"), new Integer(0));
	String holidayName = null, sms = null, email = null;
	Integer cal_flag = null,holidayDate = null, create_task = null;
	int mouth = 1, day = 1; 
	boolean success = false;
	
	HolidayLocal local = EJBFactory.getHoliday();
	HolidayVO localvo = new HolidayVO();
	localvo.setHoliday_id(holiday_id);
	localvo.setInput_man(input_operatorCode);
	
	if(request.getMethod().toUpperCase() == "POST"){
		holidayName = Utility.trimNull(request.getParameter("holiday_name"));
		mouth = Utility.parseInt(request.getParameter("mouth"), 2);
		day = Utility.parseInt(request.getParameter("day"), 11);
		create_task = request.getParameter("creat_task") == null ? new Integer(2) : new Integer(1);
		sms = Utility.trimNull(request.getParameter("sms"));
		email = Utility.trimNull(request.getParameter("email"));
		cal_flag = Utility.parseInt(request.getParameter("cal_flag"), new Integer(1));

		localvo.setHoliday_name(holidayName);
		localvo.setHoliday_day(new Integer(mouth * 100 + day));
		localvo.setCal_flag(cal_flag);
		localvo.setCreat_task(create_task);
		localvo.setSms_greeting(sms);
		localvo.setEmail_greeting(email);
		local.modiHoliday(localvo);
		success = true;
	}

	IPageList pageList = local.queryHolidays(localvo, Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,8));
	List resultSet = pageList.getRsList();
	Map resultItem = null;

	if(resultSet.size() != 0){
		resultItem = (Map)resultSet.get(0);
		holidayName = (String)resultItem.get("HOLIDAY_NAME");
		cal_flag = (Integer)resultItem.get("CAL_FLAG");
		holidayDate = (Integer)resultItem.get("HOLIDAY_DAY");
		mouth = holidayDate.intValue() / 100;
		day = holidayDate.intValue() % 100;
		create_task = (Integer)resultItem.get("CREATE_TASK");
		sms = Utility.trimNull((String)resultItem.get("SMS_GREETING"));
		email = Utility.trimNull((String)resultItem.get("EMAIL_GREETING"));
	}


%>
<html>
<head>
<title>节假日管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css", type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/includes/jQuery/msdropdown/dd.css", type="text/css" rel="stylesheet" />

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/holidayService.js'></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/msdropdown/jquery.dd.js"></script>


<style>
.tip{
	color: red;
}
</style>
</head>
<body class="body body-nox">
<form action="holiday_info.jsp" method="post" name="theform">
<input type="hidden" name="holiday_id" value="<%=holiday_id %>" />
<div class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div>
	<form>
		<table class="table-popup" >
			<tr>
				<td align="right">节日名称 :</td>
				<td width="200px">
					<input type="text" id="holiday_name" name="holiday_name" style="overflow: visible;" value="<%=holidayName %>"/>
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
					<select id="mouth" name="mouth" style="width: 60px">
					</select>
					<select id="day" name="day" style="width: 60px">
					</select>
				</td>
				<td align="right">创建服务任务 :</td>
				<td>
					<input type="checkbox" checked="checked" name="creat_task" value="1"/>
				</td>
			</tr>
			<tr>
				<td align="right">短信祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="100" name="sms"><%=sms %></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">邮件祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="100" name="email"><%=email %></textarea>
				</td>
			</tr>
		</table>
	</form>
	<div align="right">
		<button type="button"  class="xpbutton3" id="submitForm">确定</button>
		<button type="button"  class="xpbutton3" style="margin-left: 10px;margin-right: 30px" id="backButton">返回</button>
	</div>
</div>
<div style="margin-top: 10px" class="direct-panel">
	<fieldset>
		<legend>tip</legend>
		<span class="tip">短信祝词：	注：%1代表客户名称</span>
		<span class="tip">邮件祝词：	注：%1代表客户名称</span>
	</fieldset>
</div>

<div>
<p class="title-table"><strong>例外日期</strong></p>
<button type="button"  class="xpbutton3"  accesskey=n id="creatButton" name="creatButton" style="float: right; margin: 10px 30px 0px 0px">新增</button>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trtagsort">
		<td>年份</td>
		<td>日期</td>
		<td>修改</td>
		<td>删除</td>
	</tr>
<% 
	localvo.setSerial_no(new Integer(0));
	pageList = local.queryHolidayDate(localvo, Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,8));
	List rslist = pageList.getRsList();
	Map map = null;
	for(int i=0;i<rslist.size();i++){
		map = (Map)rslist.get(i);
		Integer year = (Integer)map.get("YEARINT");
		Integer mmdd = (Integer)map.get("MMDDINT");
		String mmdd_cn = (String)map.get("MMDD_CN");
		Integer serial_no = (Integer)map.get("SERIAL_NO");
%>
	<tr class="tr<%=(i % 2)%>">
		<td align="center"><%=year %></td>
		<td align="center"><%=mmdd_cn %></td>
		<td align="center">
			<a href="javascript:void(0)" class="infoItem" attr="<%=serial_no%>" year="<%=year%>" mmdd="<%=mmdd%>">
				<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
			</a>
		</td>		
		<td align="center">
			<a href="javascript:void(0)" class="removeItem" attr="<%=serial_no%>">
				<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
			</a>
		</td>
	</tr>
<%} %>
</table>
</div>
</form>
<div id="xNode" style="display: none;">
	<input type="hidden" id="serial_no"/>
	<p>日期: <input type="text" id="datepicker"/></p>
	<div align="right" style="margin-top: 50px">
		<button type="button"  class="xpbutton3"  id="dateSubmit">确定</button>
		<button type="button"  class="xpbutton3"  id="cancelBut">取消</button>
	</div>
</div>
<script type="text/javascript">
<%
	if(success){
%>
	alert("修改成功");location.href = "holiday_list.jsp";
<%
	}
%>

	$(function(){


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
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
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

		$("#mouth").change(function(){
			if($("#cal_flag").val() == 1){
				var me = $(this).val();
				var older = {1:1, 3:3, 5:5, 7:7, 8:8, 10:10, 12:12};
				if(me in older){
					if($("#day").find("option[value='30']").length == 0)
						$("<option>", { "value" : 30, "text" : "30"}).appendTo($("#day"));
					if($("#day").find("option[value='31']").length == 0)
						$("<option>", { "value" : 31, "text" : "31"}).appendTo($("#day"));
				}else if( me == 2){
					$("#day").find("option[value='30']").remove();
					$("#day").find("option[value='31']").remove();
				}else{
					$("#day").find("option[value='31']").remove();
					if($("#day").find("option[value='30']").length == 0)
						$("<option>", { "value" : 30, "text" : "30"}).appendTo($("#day"));
				}
				$("#day").msDropDown();
			}
		});

		fill(abric);


		$("#creatButton").click(function(){
			$("#serial_no").val("");
			$( "#datepicker" ).datepicker("setDate", new Date());
			$("#xNode").dialog("open");
		});
	
		$("#xNode").dialog({
				autoOpen: false,
				title: "新增例外日期",
				width:400,
				resizable : false
		});

		$("#submitForm").click(function(){
		
			if(sl_confirm("保存记录")){
				
				$("#mouth").msDropDown().data("dd").form();
				$("#day").msDropDown().data("dd").form();
				document.theform.submit();
			}
		});

		$("#backButton").click(function(){
			window.history.back();
		});

		$( "#datepicker" ).datepicker({
			dateFormat: 'yy-mm-dd',
			appendText: '(yyyy-mm-dd)'
		});

		$("#dateSubmit").click(function(){
			var date = $("#datepicker").val();
			if(	date || "" != ""){
				var items = date.split("-");
				if(items.length != 3){
					alert("dateformate Error.");
					return false;
				}
				
				//ajaxRequest.
				var params = {};
				params.serial_no = $("#serial_no").val();
				params.holiday_id = document.theform.holiday_id.value;
				params.year = parseInt(items[0],10);
				params.mmdd = parseInt(items[1] + items[2],10);
				params.input_man = <%=input_operatorCode%>;
				
				holidayService.modiHoliday_date(params, function(){
					if($("#serial_no").val() || "" != "")
						alert("修改成功");
					else
						alert("新增成功");
					location.reload();
				});
			}
		});
		
		$("#cancelBut").click(function(){
			$("#xNode").dialog("close");
			$("#datepicker").val("");
		});

		$(".infoItem").click(function(){
			var year = $(this).attr("year");
			var mmdd = parseInt($(this).attr("mmdd"), 10);
			var mouth = (mmdd / 100).toFixed(0);
			var day = mmdd % 100;
			var date = year + "-" + mouth + "-" + day;

			$( "#datepicker" ).datepicker("setDate", date);
			$("#serial_no").val($(this).attr("attr"));
			$("#xNode").dialog("open");
		});

		$(".removeItem").click(function(){
			if(sl_confirm("删除该条记录")){
				var params = {};
				params.serial_no = $(this).attr("attr");
				params.input_man = <%=input_operatorCode%>;
			
				holidayService.delHolidayDate(params, function(){
					alert("删除成功");
					location.reload();
				});
			}
		});

		init();
	});

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
			var str = parseInt(next, 10) + 1;
			$("<option>", { "value" : str, "text" : args['mouth'][next]}).appendTo($("#mouth"));
		}
		for( next in args["day"]){
			var str = parseInt(next, 10) + 1;
			$("<option>", { "value" : str, "text" : args['day'][next]}).appendTo($("#day"));
		}
		$("#mouth").data("cacheM", $(this).html());
		$("#day").data("cacheD", $(this).html());
	}
}

function init(){
	$("#cal_flag option[value=<%=cal_flag%>]").attr("selected", "selected").change();
	$("#mouth option[value=<%=mouth%>]").attr("selected", "selected");
	$("#day option[value=<%=day%>]").attr("selected", "selected");
	$("#mouth").change();
	$("#mouth").msDropDown();
	//$("#day").msDropDown();
	$("input[name='creat_task']").attr("checked", <%=create_task%> == 1? "checked" : false)
}
</script>
</body>
</html>
<%local.remove(); %>