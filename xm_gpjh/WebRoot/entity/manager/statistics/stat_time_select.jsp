
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
	String pageInt = fixnull(request.getParameter("pageInt1"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id ="";
	if(request.getParameter("enterprise_id")!=null){
	    enterprise_id = fixnull(request.getParameter("enterprise_id").trim());
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String bsql ="select pb.start_time as stime , pb.end_time as etime from pe_bzz_batch pb where pb.id = '"+batch_id+"'";
	rs = db.executeQuery(bsql);
	String stime = "";
	String etime = ""; 
	if(rs!=null&&rs.next()){
		stime = rs.getString("stime").substring(0,10);
		etime = rs.getString("etime").substring(0,10);
	}
	db.close(rs);
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/calender.js"></script>	
<script type="text/javascript">
	function check(){
		var sDate  = document.getElementById("sDate").value;
		var eDate  = document.getElementById("eDate").value;
		var ss ="";
		if(sDate.length>0){
			ss = sDate.split("-");
		}
		var ee ="";
		if(eDate.length>0){
			ee = eDate.split("-");
		}
		
		var flag = true;
		
		var bsdate = "<%=stime%>";
		var bb ="";
		if(bsdate.length>0){
		  bb= bsdate.split("-");
		}
		var bstem ="";
		for(k =0;k<bb.length;k++){
			bstem+=bb[k];
		}
		
		var bedate = "<%=etime%>";
		var dd = "";
		if(bedate.length<1){
			dd = bedate.split("-");
		}
		var betem ="";
		for(k =0;k<dd.length;k++){
			betem+=dd[k];
		}
		
		var etem="";
		for(k =0;k<ee.length;k++){
			etem+=ee[k];
		}
		var stem="";
		for(k =0;k<ss.length;k++){
			stem+=ss[k];
		}
		/*if(!(etem-stem>=0)){
			window.alert("时间范围无效!");
			return false;
		}
		
		if(!(stem-bstem>=0)){
			window.alert("时间范围无效!");
			return false;
		}
		
		if(!(betem-etem>=0)){
			window.alert("时间范围无效!");
			return false;
		}*/
		if(flag){
			document.gotoform.submit();
		}
		
	}
	
	function online(){
		var action="";
		var  en = "<%=enterprise_id%>";
		if(en.length<1){
			action="/entity/manager/statistics/stat_period.jsp";
		}else{
			action="/entity/manager/statistics/stat_enterprise_period.jsp";
		}
		document.gotoform.action = action;
	}
</script>		
</head>
	<body leftmargin="0" topmargin="0" class="scllbar" onload="online()">
	<form name ="gotoform" method="post">
		<table width="100%" height="40%" border="0" cellpadding="0" cellspacing="0" align="center" >
			<tr align="center">
			<td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText"></td>
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>	
			</tr>
			
			
			<tr>
			<td valign="top" class="pageBodyBorder">
<div class="border">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			</td>
			<td class='misc' style='white-space: nowrap;'></td>
		  </tr>
	</table>
		<table width='98%' align="center" cellpadding='1' cellspacing='0' class='list' border="2">
            <tr>
            	<td  scope="col" align="center">
            		<table height="100%" align="center" >
            			<tr>
            				<td height="28">
					<table width="100%" height="28" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<h5>请选择要查询的时间范围</H5>
						</tr>
						<tr>
							<td>请选择开始时间</td>
							<td> <input type="text" name="sDate" size ="12" id="sDate" value="<%
              	java.text.SimpleDateFormat bartDateFormat = new  java.text.SimpleDateFormat("yyyy-MM-dd"); 
              	Date date = new Date(); 				
				out.print(bartDateFormat.format(date));
               %>" readonly>&nbsp;&nbsp;
              <img src="../js/calendar/img.gif" id="f_trigger_b" style="border: 1px solid red; cursor: pointer;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"></td>
						</tr>
						<tr>
							<td>请选择结束时间</td>
							<td><input type="text" name="eDate" size ="12" id="eDate" value="<%
              	Date date1 = new Date(); 				
				out.print(bartDateFormat.format(date1));
               %>" readonly>&nbsp;&nbsp;
              <img src="../js/calendar/img.gif" id="f_trigger_a" style="border: 1px solid red; cursor: pointer;" title="Date selector" onmouseover="this.style.background='red';" onmouseout="this.style.background=''"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="hidden" name ="batch_id" value="<%=batch_id%>" /></td>
							<td><input type="hidden" name ="enterprise_id" value="<%=enterprise_id%>" /></td>
						</tr>
					</table>
				</td>
            			</tr>
            		</table>
            	</td>
            </tr>
          </table>
    </div>
    </td>      
  </tr>
 
  		<tr>
          <td align="center" valign="middle" class="pageBottomBorder">
          <table>
          	<tr>
				<td align="center"><span class="norm" style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text" onclick="javascript:check();">提交</span></span></td>	          	
          		<td align="center"><span class="norm" style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text" onclick="javascript:window.history.back();">返回</span></span></td>	          	
          	</tr>
          </table>
          </td>
		</tr>
		</table>
		</form>
<script type="text/javascript">
    Calendar.setup({
    	inputField     :    "eDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup(
    	{
    	 inputField     :    "sDate",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    	}
    );
	</script>
		</body>
	</html>
