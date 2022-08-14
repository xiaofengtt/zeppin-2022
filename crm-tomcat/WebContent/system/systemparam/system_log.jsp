<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
LogListLocal loglocal = EJBFactory.getLogList();
LogListVO vo = new LogListVO();

//获得查询条件
Integer begin_date_str = Utility.parseInt(request.getParameter("begin_date"),null);
Integer end_date_str = Utility.parseInt(request.getParameter("end_date"),null);
Integer op_code = Utility.parseInt(request.getParameter("op_code"), new Integer(0));//操作员编号
Integer busi_flag = Utility.parseInt(request.getParameter("busi_flag"), new Integer(0));//业务类别
String busi_flag_str = Utility.trimNull(request.getParameter("busi_flag"));
String summary = Utility.trimNull(request.getParameter("summary"));//备注

//添加查询条件
vo.setBusi_flag(busi_flag);
vo.setSummary(summary);
vo.setOp_code(op_code);
vo.setStart_date(begin_date_str);
vo.setEnd_date(end_date_str);
//获得查询结果
IPageList pageList = loglocal.listLogList(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();
java.util.Iterator it = list.iterator();

//声明参数
String tempUrl = "";
Map map = null;

//url设置
tempUrl = "&begin_date="+begin_date_str+"&end_date="+end_date_str+"&op_code="+op_code+"&busi_flag="+busi_flag+"&summary="+summary;
sUrl = sUrl + tempUrl;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.systemLog",clientLocale)%> </title>
<!--系统日志-->
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script src="<%=request.getContextPath()%>/includes/calendar.js" language="javascript"></script>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
<!--
	/**在分页时组合查询条件以get的方式传值**/
	function refreshPage()
	{
		disableAllBtn(true);
		syncDatePicker(document.theform.begin_date_picker, document.theform.p_begin_date);
	    syncDatePicker(document.theform.end_date_picker, document.theform.p_end_date);
		location = 'system_log.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +
					'&begin_date=' + document.getElementById("p_begin_date").getAttribute("value")+
					'&end_date='+document.getElementById("p_end_date").getAttribute("value")+
					'&op_code='+document.getElementById("p_op_code").getAttribute("value")+
					'&busi_flag='+ document.getElementById("p_busi_flag").getAttribute("value")+
					'&summary='+document.getElementById("p_summary").getAttribute("value");
	}
	
	/**确定查询条件时条用**/
	function StartQuery()
	{
		refreshPage();
	}
	
	/**窗体加载**/
	window.onload = function(){
	initQueryCondition()};
	
-->
</script>
</head>

<body class="body body-nox">
<form id="theform" method="post" name="theform">
<%@ include file="/includes/waiting.inc"%>    
<!-- 添加查询条件 start -->
<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="4" cellpadding="2">
			<tr>
				<td  align="right" width="70"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
				<td  align="left" width="160">
					<INPUT onkeydown="javascript:nextKeyPress(this)" type="text" name="begin_date_picker"  class="selecttext" value='<%=Utility.trimNull(Format.formatDateLine(begin_date_str))%>' > 
					<INPUT type="button" value="▼" class="selectbtn" onclick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.p_begin_date.value,this);" tabindex="13">
					<INPUT type="hidden" name="p_begin_date" value="" id="p_begin_date">
				</td>
				<td align="right" width="95"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
				<td align="left" width="160">
					 <INPUT onkeydown="javascript:nextKeyPress(this)" type="text" name="end_date_picker"  class="selecttext" value='<%=Utility.trimNull(Format.formatDateLine(end_date_str))%>' > 
					 <INPUT type="button" value="▼" class="selectbtn" onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.p_end_date.value,this);" tabindex="13">
					 <INPUT type="hidden" name="p_end_date" value="" id="p_end_date">
					
				</td> 
			</tr>
			<tr>
				<td align="right" width="70"><%=LocalUtilis.language("class.opName",clientLocale)%> : </td><!--员工姓名-->
				<td align="left" width="160">
					<select id="p_op_code" style="width:120px;" name="p_op_code" onkeydown="javascript:nextKeyPress(this)">
	                    <%=Argument.getOperatorOptions(op_code)%>
					</select>
				</td>
				<td align="right" width="95"><%=LocalUtilis.language("class.busiName",clientLocale)%> :</td><!--业务类别-->
				<td align="left" width="160">
					<input type="text" id="p_busi_flag" name="p_busi_flag" onkeydown="javascript:nextKeyPress(this)" value='<%=busi_flag_str!=null?busi_flag_str:""%>'/>
				</td>	
									
			</tr>
			<tr>
				<td  align="right" width="80"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> : </td><!--备注-->
				<td  align="left" colspan="3">
					<input type="text" id="p_summary" size="72" name="p_summary" onkeydown="javascript:nextKeyPress(this)" value='<%=summary%>'/>
				</td>	
			</tr>
			<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>				
			</td><!--确认-->
		</tr>			
	</table>
</div>
<!-- 添加查询条件 end -->
<!-- 标头 start -->
<table cellSpacing=0 cellPadding=4 width="100%" align=center border=0 >
	<tr>
		<td align="left" colspan=2 class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right" >
		<div class="btn-wrapper">
		<button type="button"  class="xpbutton3" style="margin-right: 16px;" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> "><!--查询-->
		    <%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>
		</div>
		</td>
	</tr>
</table>
<!-- 标头 end -->
<!-- 详细列表显示 start -->
<table valign="middle" align="center" cellspacing="0" cellpadding="3" width="100%">
	<tr>
		<td>
			<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trtagsort">
			<td width="5%" align="center" sort="num"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--序号-->
			<td width="5%" align="center"><%=LocalUtilis.language("class.busiName",clientLocale)%> </td><!--业务类别-->
			<td width="5%" align="center"><%=LocalUtilis.language("class.opCode",clientLocale)%> </td><!--员工编号-->
			<td width="5%" align="center">员工名称 </td>
			<td width="10%" align="center"><%=LocalUtilis.language("class.insertTime2",clientLocale)%> </td><!--操作时间-->
			<td width="15%" align="center"><%=LocalUtilis.language("class.busiName2",clientLocale)%> </td><!--业务说明-->
			<td width="15%" align="center"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--备注-->
		</tr>
		<!-- 循环遍历显示每行信息 -->
		<%
			int iCount = 0;
			while(it.hasNext())
			{
				map = (Map)it.next();
		%>
			<tr class="tr<%=(iCount % 2)%>">
			<td align="center"><%=iCount+1%></td>
			<td align="center"><%=map.get("BUSI_FLAG")%></td>
			<td align="center"><%=map.get("OP_CODE")%></td>
			<td align="center"><%=map.get("OP_NAME")%></td>
			<td align="center"><%=Utility.getDateFormat(map.get("TRADE_TIME"))%></td>
			<td align="center"><%=map.get("BUSI_NAME")%></td>
			<td align="left"><input type="text" class="ednone" style="width:98%" value="<%=map.get("SUMMARY")!=null?map.get("SUMMARY"):""%>" readonly></td>
			</tr>
		<%
			iCount ++ ;
			}
		%>  
		<!-- 如果实际记录不足要显示的行数，则打印空行 -->
		<%
		for(int i=0;i<(8-iCount);i++){
		%>      
	  	<tr class="tr0" >
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
		</tr>       
		<%}%> 
		<!-- 统计所有的记录 -->
		<tr class="trbottom" >
			<td align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%= pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%>  <!--项--></td>
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
<!-- 详细列表显示 end -->
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
