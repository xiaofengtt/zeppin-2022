<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

MessageLocal inform = EJBFactory.getMessage();

String title = Utility.trimNull(request.getParameter("title"));
String info_str = Utility.trimNull(request.getParameter("info_str"));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), null);

MessageVO vo=new MessageVO();
vo.setBook_code(input_bookCode);
vo.setTitle(title);
vo.setInfo_STR(info_str);
vo.setEnd_date(new Integer(0));
vo.setEnd_date(end_date);

IPageList pageList=inform.listPrivateMessage1(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

 sUrl =
	"taskinfo.jsp?pagesize="
		+ sPagesize
		+ "&title="
		+ title
		+ "&info_str="
		+ info_str
		+ "&end_date="
		+ end_date;

boolean bSuccess = false;		
if(request.getMethod().equals("POST")){
	String[] s = request.getParameterValues("serial_no");
	int serial_no;
	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{
			serial_no = Utility.parseInt(s[i], 0);
			if(serial_no != 0)
			{
				inform.delMessage(new Integer(serial_no),input_operatorCode,new Integer(1));
			}
		}
	}
	bSuccess = true;
}

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
</HEAD>

<script language=javascript>
<%if(bSuccess){%>
 sl_alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
 location="message.jsp";
<%}%>
function showInfo(serial_no)
{
	if(showModalDialog('message_update.jsp?serial_no=' + serial_no, '', 'dialogWidth:680px;dialogHeight:548px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function newInfo()
{
	if(showModalDialog('message_update.jsp', '', 'dialogWidth:680px;dialogHeight:548px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	//if(!sl_checkDate(document.theform.end_date_picker,"到期日期"))	return false;
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	disableAllBtn(true);
	location = 'message.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&title=' + document.theform.title.value +'&info_str=' + document.theform.info_str.value
	+'&end_date=' + document.theform.end_date.value;
}

function StartQuery()
{	
	refreshPage();
} 

window.onload = function(){
initQueryCondition()};

</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0"
	bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0">
<%@ include file="/includes/waiting.inc"%>		
<form name="theform" method="post" action="message.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:280px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
									onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>			
				<table border="0" width="100%" cellspacing="2" cellpadding="2">					
					<tr>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.Title",clientLocale)%> :</td><!--标题-->
						<td> <input type="text"
							name="title" onkeydown="javascript:nextKeyPress(this)" size="20"
							value=<%=title%>></td>
					</tr>
					<tr>						
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.content2",clientLocale)%> :</td><!--内容-->
						<td> <input type="text" name="info_str" onkeydown="javascript:nextKeyPress(this)"
							 size="20"
							value=<%=info_str%>>						
						</td>
					</tr>
					<tr>						
						<td valign="bottom" align="right"> <%=LocalUtilis.language("class.endDate4",clientLocale)%> :</td><!--到期日期-->
						<td> <INPUT TYPE="text" onkeydown="javascript:nextKeyPress(this)"
							NAME="end_date_picker" class=selecttext	value="<%if (end_date != null) { out.print(Format.formatDateLine(end_date)); }%>">							
							
						<INPUT TYPE="button" value="▼" class=selectbtn
							onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"
							tabindex="13"> <INPUT TYPE="hidden" NAME="end_date"
							value="<%=end_date%>"> &nbsp;
						
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2"><button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
								onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
						</td><!--确定-->
					</tr>					
				</table>				
</div>	


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				
				
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0
							width="32" height="28"><b><%=menu_info%></b></td>													
					</tr>
					<tr>											
						<td valign="bottom" align="right">
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)
						</button>&nbsp;&nbsp;&nbsp;<!--查询-->
						<%if (input_operator.hasFunc(menu_id, 100)) {%>
						<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
						</button><!--新建记录--><!--新建-->
						&nbsp;&nbsp;&nbsp; <%}%> <%if (input_operator.hasFunc(menu_id, 101)) {%>
						<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> "
							onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
						</button><!--删除所选记录--><!--删除-->
						<%}%>
						
						</td>
					</tr>
					<tr>
						<td>
							<hr noshade color="#808080" size="1">
						</td>											
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="left" width="10%"><input type="checkbox" name="btnCheckbox" class="flatcheckbox" onclick="javascript:selectAll(document.theform.serial_no,this);">&nbsp;<%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td>
						<!--序号-->
						<td width="45%"><%=LocalUtilis.language("class.Title",clientLocale)%> </td><!--标题-->						
						<td width="25%"><%=LocalUtilis.language("class.inputTime",clientLocale)%> </td><!--输入日期-->
						<td width="14%"><%=LocalUtilis.language("class.inputMan",clientLocale)%> </td><!--录入人-->
						<td width="6%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
					</tr>
<%
    int iCount = 0;
    int iCurrent = 0;
    List rsList=pageList.getRsList();
    Map rowMap=null;
    Integer serial_no=new Integer(0);   
    String  mes_title="";
    java.sql.Timestamp input_time=null;   
    Integer input_man=new Integer(0);  
  
	for (int i=0;i<rsList.size();i++) {  
     rowMap=(Map)rsList.get(i);
     serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")), new Integer(0));     
     mes_title = Utility.trimNull(rowMap.get("TITLE"));  
     input_time=Utility.parseTimestamp(Utility.trimNull(rowMap.get("INPUT_TIME")));   
     input_man = Utility.parseInt(Utility.trimNull(rowMap.get("INPUT_MAN")), new Integer(0));     
          
                             
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20%"><input type="checkbox" name="serial_no"
									value="<%=serial_no%>" class="flatcheckbox"></td>
								<td width="80%"><%=serial_no%></td>
							</tr>
						</table>
						</td>					
						<td align="left"><%=mes_title%></td>					
						<td align="left"><%=input_time%></td>						
						<td align="left"><%=Utility.trimNull(Argument.getOpName(input_man))%></td>
						<td align="center"><%if (input_operator.hasFunc(menu_id, 102)) {%>													
							<a href="javascript:#" onclick="javascript:showInfo(<%=serial_no%>);return false;">
							<img src="<%=request.getContextPath()%>/styles/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> ",clientLocale)%> <!--编辑-->
						</a>
						<%}%></td>
					</tr>
					<%
  iCurrent++;
   iCount++;
}
%>
	
<%
for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
							<tr class="tr<%=(i % 2)%>">
					<td class="tdh" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					<td  align="center" ></td>
					
				</tr>
				<%}
%>
					<tr class="tr1">
						<td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%>  <!--项--></b></td>
						<td align="center">-</td>
						<td align="center">-</td>
						
						<td align="center"></td>
						<td align="center">-</td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td> 	
						<td align="right"></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%inform.remove();%>
