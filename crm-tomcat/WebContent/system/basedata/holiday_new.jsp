<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	//Integer holiday_id = Utility.parseInt(request.getParameter("holiday_id"), new Integer(0));
	
	//例外日期的session格式 "HolidayCache" : => List<Map<String, Object>> my_serial_no, yearInt, MMDD
	boolean success = false;
	if("POST".equals(request.getMethod().toUpperCase())){
		HolidayLocal local = EJBFactory.getHoliday();
		HolidayVO localvo = new HolidayVO();
		
		String holidayName = Utility.trimNull(request.getParameter("holiday_name"));
		int mouth = Utility.parseInt(request.getParameter("mouth"), 2);
		int day = Utility.parseInt(request.getParameter("day"), 11);
		Integer create_task = request.getParameter("creat_task") == null ? new Integer(2) : new Integer(1);
		String sms = Utility.trimNull(request.getParameter("sms"));
		String email = Utility.trimNull(request.getParameter("email"));
		Integer cal_flag = Utility.parseInt(request.getParameter("cal_flag"), new Integer(1));

		localvo.setHoliday_name(holidayName);
		localvo.setHoliday_day(new Integer(mouth * 100 + day));
		localvo.setCal_flag(cal_flag);
		localvo.setCreat_task(create_task);
		localvo.setSms_greeting(sms);
		localvo.setEmail_greeting(email);
		localvo.setInput_man(input_operatorCode);
		Integer holiday_id = local.addHolidays(localvo);

		List resultSet = (List)session.getAttribute("HolidayCache");
		if(resultSet != null && resultSet.size() != 0){
			localvo.setHoliday_id(holiday_id);
			localvo.setSerial_no(new Integer(0));
			for(int i = 0; i != resultSet.size(); i++){
				Map line = (Map) resultSet.get(i);
				localvo.setYearInt((Integer)line.get("yearInt"));
				localvo.setMmddInt((Integer)line.get("MMDD"));
				local.modiHolidayDate(localvo);
			}
		}
		success = true;
		local.remove();
	}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<title>节假日管理</title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css", type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/includes/jQuery/msdropdown/dd.css", type="text/css" rel="stylesheet" />
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
<div class="page-title"> 
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div>
	<form action="holiday_new.jsp" method="post" name="theform">
		<table class="table-popup">
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
			<tr >
				<td align="right">日期 :</td>
				<td>
					<select id="mouth" name="mouth" style="width: 60px">
					</select>
					<select id="day" name="day" style="width: 60px">
					</select>
				</td>
				<td align="right">创建服务任务 :</td>
				<td>
					<input type="checkbox" checked="checked" name="creat_task"/>
				</td>
			</tr>
			<tr >
				<td align="right">短信祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="100" name="sms"></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">邮件祝福内容 :</td>
				<td colspan="3">
					<textarea rows="6" cols="100" name="email"></textarea>
				</td>
			</tr>
		</table>
	</form>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" id="submitForm">确定</button>
		<button type="button"  class="xpbutton3" style="margin-left: 10px;margin-right: 30px" id="backButton">返回</button>
	</div>
</div>
<br/>
<div style="margin-top: 20px">
	<fieldset>
		<legend>tip</legend>
		<span class="tip">短信祝词：	注：%1代表客户名称</span>
		<span class="tip">邮件祝词：	注：%1代表客户名称</span>
	</fieldset>
</div>
<div>
<h3 style="display: inline;float: left;">例外日期</h3>
<button type="button"  class="xpbutton3"  accesskey=n id="creatButton" name="creatButton" style="float: right; margin: 10px 30px 0px 0px">新增</button>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trtagsort">
		<td>年份</td>
		<td>日期</td>
		<td>编辑</td>
		<td>删除</td>
	</tr>
<%
	List cacheList = (List)session.getAttribute("HolidayCache");
	if(cacheList != null){
		for(int i = 0 ; i != cacheList.size(); i++){
			Map result = (Map)cacheList.get(i);
			Integer mySerial_no = (Integer)result.get("my_serial_no");
			Integer myYear = (Integer)result.get("yearInt");
			Integer myMMDD = (Integer)result.get("MMDD");
			String date = (String)result.get("date");
			String MMDD_CN = myMMDD.intValue() / 100 + "月" + myMMDD.intValue() % 100 + "日";
%>
	<tr class="tr<%=(i % 2)%>">
		<td align="center"><%=myYear %></td>
		<td align="center"><%=MMDD_CN %></td>
		<td align="center">
			<a href="javascript:void(0)" class="infoItem" attr="<%=mySerial_no%>" year="<%=myYear%>" mmdd="<%=myMMDD%>", date=<%=date%>>
				<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
			</a>
		</td>
		<td align="center">
			<a href="javascript:void(0)" attr="<%=mySerial_no%>" class="removeItem" >
				<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
			</a>			
		</td>
	</tr>
<%
	 }}
%>
</table>
</div>
<div id="xNode" style="display: none;">
	<input type="hidden" id="serial_no"/>
	<p>日期: <input type="text" id="datepicker"/></p>
	<div align="right" style="margin-top: 50px">
		<button type="button"  class="xpbutton3" id="dateSubmit">确定</button>
		<button type="button"  class="xpbutton3" id="cancelBut">取消</button>
	</div>
</div>
<script type="text/javascript">
<%
	if(success){
%>
alert("保存成功");
location.href = "holiday_list.jsp";
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
		$("#mouth").msDropDown();
		$("#day").msDropDown();


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
				//alert();
				$.ajax({
					type: "POST",
					url : "holiday_ff.jsp",
					data : {'serial_no' : $("#serial_no").val() || -1,'year' : parseInt(items[0],10), 'mmdd': parseInt(items[1] + items[2],10), "wholeDate" : date},
					success: function(){
						location.reload();
					},
					error: function(){
						alert("error");
					}
				});
			}
		});
		
		$("#cancelBut").click(function(){
			$("#xNode").dialog("close");
			$("#datepicker").val("");
		});

		$(".infoItem").click(function(){
			var date = $(this).attr("date");
			$( "#datepicker" ).datepicker("setDate", date);
			$("#serial_no").val($(this).attr("attr"));
			$("#xNode").dialog("open");
		});
		
		$(".removeItem").click(function(){
			if(sl_confirm("删除该条记录")){
				$.ajax({
					type: "POST",
					url : "holiday_ff.jsp",
					data : {'serial_no' :  $(this).attr("attr"), "del" : 2},
					success: function(){
						location.reload();
					},
					error: function(){
						alert("error");
					}
				});
			}
		});
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
</script>
</body>
</html>
