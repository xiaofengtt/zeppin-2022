<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.fee.*,com.whaty.platform.entity.fee.deal.*"%>
<%@ include file="../pub/priv.jsp"%>
<% 
	//out.print(includePriv.getFeeByTime);
	//if(includePriv.getFeeByTime == 1) {
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>石油平台</title>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/check.js">
</script>
<script language=javascript src="../js/filter.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
		<script type="text/javascript" src="../js/calendar/calendar.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-zh.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/calender.js"></script>
		<script language="javascript" >
		function ch(){
			var ts = document.coursewareinfo.startDate.value;
			if(ts==null || ts==""){
				alert('请输入开始时间!');
				document.coursewareinfo.startDate.focus();
				return false;
			}
			var te = document.coursewareinfo.endDate.value;
			if(te==null || te==""){
				alert('请输入结束时间!');
				document.coursewareinfo.endDate.focus();
				return false;
			}
			
			return document.coursewareinfo.submit();
		}
		</script>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<form name='coursewareinfo' action='/entity/information/smsStatisticsAction.action' method='get' class='nomargin' onsubmit="return ch();">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">选择短信统计查询时间段</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 

        <div class="cntent_k" id="zlb_content_start">
          <table width="96%" border="0" cellpadding="0" cellspacing="0">
			<tr valign="bottom" class="postFormBox"> 
              <td valign="bottom" width=30%><span class="name">开始时间：</span></td>
              <td><input type=text name="startDate" id="startDate" class=selfScale readonly>&nbsp;<img src="../js/calendar/img.gif" id="f_trigger_a" style="border: 1px solid red; cursor: pointer;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''">&nbsp;</td>
            </tr>
           
            <tr valign="bottom" class="postFormBox">
              <td valign="bottom"><span class="name">结束时间：</span></td>
              <td> <input type=text name=endDate  id="endDate" class=selfScale readonly>&nbsp;<img src="../js/calendar/img.gif" id="f_trigger_b" style="border: 1px solid red; cursor: pointer;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''">&nbsp; </td>
						</tr>
            
			</table>
      </div>

    </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td align="center" valign="middle"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'" onclick="ch();"><span class="text" >提交</span></span></td>
        </tr>
      </table></td>
  </tr>
  
</table>
 </form>
 <script type="text/javascript">
    Calendar.setup({
        inputField     :    "startDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
	<script type="text/javascript">
    Calendar.setup({
        inputField     :    "endDate",      // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
 
</body>
</html>
