<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.callcenter.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
Integer managerID = Utility.parseInt(request.getParameter("managerID"), new Integer(0));
Integer extension_q = Utility.parseInt(request.getParameter("extension_q"),null);
Integer start_date = Utility.parseInt(request.getParameter("start_date"), new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(0));

//声明辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];
boolean bSuccess = false;

//url设置
String sUrl = "Record_Inspection.jsp?&sPage="+sPage;
String tempUrl = "";
tempUrl = tempUrl+"&managerID="+managerID;
tempUrl = tempUrl+"&extension_q="+extension_q;
tempUrl = tempUrl+"&start_date="+start_date;
tempUrl = tempUrl+"&end_date="+end_date;

sUrl = sUrl + tempUrl;

//获得对象
CallCenterLocal callCenter = EJBFactory.getCallCenter();
CCVO vo = new CCVO();

TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
TcustmanagersVO managerVO = new TcustmanagersVO();

//获得明细列表
vo.setSerial_no(serial_no);
vo.setManagerID(managerID);
vo.setStartDate(start_date);
vo.setEndDate(end_date);
vo.setExtension(extension_q);
vo.setInput_man(input_operatorCode);

IPageList pageList = callCenter.query_cc_detail(vo,totalColumn,t_sPage,t_sPagesize);
IPageList custPageList = null;

//分页辅助参数
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
List custList = null;
Map map = null;

%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>录音质检</title>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">


/*启动加载*/	
window.onload = function(){
	initQueryCondition();
    }

//刷新
function refreshPage(){
	disableAllBtn(true);
	var _pagesize = document.getElementsByName("pagesize")[0];		
	window.location = 'Record_Inspection.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
/*查询功能*/
function QueryAction(){	
			syncDatePicker(document.theform.start_date_picker,document.theform.start_date);			
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);			
		if(document.theform.start_date.value != "" && document.theform.end_date.value != "" ){
		   if(document.theform.start_date.value > document.theform.end_date.value){
			alert("开始日期不能大与结束日期！");
			return;
		   }
		}
	    var _pagesize = document.getElementsByName("pagesize")[0];	
	    var url = "Record_Inspection.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
        url += "&managerID=" + document.theform.managerID.value;
	    url += "&extension_q=" + document.theform.extension_q.value;
	    url += "&start_date="+ document.theform.start_date.value;
		url += "&end_date="+ document.theform.end_date.value;
	
	    disableAllBtn(true);		
	    window.location = url;
}
</script>
</HEAD>
<body class="body body-nox">
<form id="theform" name="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:530px;height:100px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td>查询条件：</td>
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right">客户经理:</td>
			<td  align="left">
				<select name="managerID" style="width:120px">
					<%=Argument.getManager_Value(managerID)%>
				</select>	
			</td>
			<td  align="right" style="width: 90px;">分机号码: </td>
			<td  align="left">
				<input type="text" name="extension_q"/>
			</td>			
		</tr>
		<tr>
	    	<td align="right">开始日期:</td>
	    	<td align="left">
	    	    <input type="text" name="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>"> 
			    <input TYPE="button" value="▼" class=selectbtn
			    onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);"tabindex="13"> 
			    <INPUT type="hidden" name="start_date">
		    </td>
		    <td align="right">结束日期:</td>
		    <td align="left">
		        <input type="text" name="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>"> 
			    <input TYPE="button" value="▼" class=selectbtn
			    onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"tabindex="13"> 
			    <INPUT type="hidden" name="end_date">
		    </td>
	    </tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">确定(<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>
	</table>
</div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
			
	<div align="right" class="btn-wrapper">
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="查询" onclick="javascript:void(0);">查询(<u>Q</u>)</button>
	</div>
	<br/>
<br>
<div>
<table id="table3" border="0" width="100%" cellspacing="1" cellpadding="2" class="tablelinecolor">
	<tr class="trh">
	    <td align="center" width="12.5%">客户</td>
		<td align="center" width="12.5%">联系人</td>
		<td align="center" width="12.5%">对方号码</td>
		<td align="center" width="12.5%">呼叫方式</td>
		<td align="center" width="12.5%">通话起始时间</td>
		<td align="center" width="12.5%">时长(秒)</td>
		<td align="center" width="12.5%">经理/分机</td>
		<td align="center" width="12.5%">回放</td>
	</tr>
<%
	
    //声明字段
	Iterator iterator = list.iterator();
	String phoneNumber;
	String fileName = "" ;
	Integer callLength;
	Integer call_type;
	String begin_date;
	String managerName = "" ;
	String direction = "" ;
	String contector = "" ;
	String cust_name = "" ;
    String Extension = "";
	while(iterator.hasNext()){	
	    iCount++;	
		map = (Map)iterator.next();
		managerName = Utility.trimNull(map.get("ManagerName"));
		begin_date = Utility.trimNull(map.get("CallTime")).substring(0,19);
		phoneNumber = Utility.trimNull(map.get("PhoneNumber"));
		callLength = Utility.parseInt(Utility.trimNull(map.get("CallLength")),null);
		contector = Utility.trimNull(map.get("Contactor"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
        if("".equals(cust_name)) {
            //如果客户名为空，通过电话号码取客户名称
            vo.setPhoneNumStr(phoneNumber);
            custPageList = callCenter.listCustByPhone2(vo,t_sPage,t_sPagesize);
            custList  = custPageList.getRsList();
            Iterator custIterator = custList.iterator();
            if (custIterator.hasNext()) {
                Map custMap = (Map)custIterator.next();
                cust_name = Utility.trimNull(custMap.get("CUST_NAME"));
            }
        }
		call_type = Utility.parseInt(Utility.trimNull(map.get("Direction")),null);
		fileName = Utility.trimNull(map.get("URL"));
		if (user_id.intValue()==15){//建信的URL要处理下:把网络映射来的磁盘中前部分字符去掉
			if (fileName.length()>16)
				fileName = fileName.substring(15,fileName.length());
        }
		Extension = Utility.trimNull(map.get("Extension"));
        if("".equals(managerName)) {
            //如果客户经理名称为空，根据分机号码取客户经理名称
            managerVO.setExtension(Extension);
            List managerList = tcustmanagers_Bean.list_query(managerVO);
            Iterator managerIterator = managerList.iterator();
            if (managerIterator.hasNext()) {
                Map managerMap = (Map)managerIterator.next();
                managerName = Utility.trimNull(managerMap.get("ManagerName"));
            }
        }
		if(begin_date == null){
		begin_date = "0";
		}
		if(call_type.intValue()==1){
			 direction ="被叫";
		}else if(call_type.intValue()==2){
			direction ="主叫";
		}
%>
<tr class="tr<%=iCount%2%>">
        <td align="center" width="168"><%=cust_name%></td>
		<td align="center" width="168"><%=contector%></td>
		<td align="center" width="178"><%=phoneNumber%></td>
		<td align="center" width="151"><%=direction%></td>
		<td align="center" width="178"><%=begin_date%></td>
		<td align="center" width="168"><%=callLength%>分钟</td>
		<td align="center" width="259"><%=managerName%>/<%=Extension%></td>		
        <td align="center">
		<a target="_blank" href="playwav.jsp?file_name=<%=fileName%>"> 
		<img border="0"
			src="/images/msg.gif" align="absmiddle" width="16" height="16">
			 </a>
		</td>
</tr>
<%}%>
	
<%for(int i=0;i<(t_sPagesize-iCount);i++){%>
	<tr class="tr0">
	    <td align="center" width="168">&nbsp;</td>
		<td align="center" width="168">&nbsp;</td>
		<td align="center" width="178">&nbsp;</td>
		<td align="center" width="151">&nbsp;</td>
		<td align="center" width="259">&nbsp;</td>
		<td align="center" width="299">&nbsp;</td>
		<td align="center" width="168">&nbsp;</td>
		<td align="center" width="178">&nbsp;</td>
	</tr>
<%}%>
	<tr class="tr1">
		<td class="tdh" align="center" width="168"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
		<td align="center" class="tdh" width="178"></td>
		<td align="center" class="tdh" width="151"></td>
		<td align="center" class="tdh" width="259"></td>
		<td align="center" class="tdh" width="299"></td>
		<td align="center" class="tdh" width="178"></td>
		<td align="center" class="tdh" width="151"></td>
		<td align="center" class="tdh" width="151"></td>

	</tr>
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl)%>
</div>
    <% callCenter.remove();%>
</form>
</BODY>
</HTML>