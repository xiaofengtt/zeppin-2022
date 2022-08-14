<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);

boolean bSuccess = false;
MessageLocal inform = EJBFactory.getMessage();
String title = Utility.trimNull(request.getParameter("title"));
String info_str = Utility.trimNull(request.getParameter("info_str"));
Integer end_date =
	new Integer(
		Utility.parseInt(
			request.getParameter("end_date"),
			Utility.getCurrentDate()));

String codeList = "";
String nameList = "";

MessageVO informVo=new MessageVO();

if (serial_no != null && !request.getMethod().equals("POST")) {
    MessageVO listVo=new MessageVO();
	listVo.setSerial_no(serial_no);
	List list=inform.listPrivateMessage1(listVo);
	Map rowMap=null;
	if(list.size()>0){
	  rowMap=(Map)list.get(0);
	  title = Utility.trimNull(rowMap.get("TITLE"));  
	  info_str = Utility.trimNull(rowMap.get("INFO_STR")); 
	  end_date = Utility.parseInt(Utility.trimNull(rowMap.get("END_DATE")), new Integer(0));      
	}


    //查询阅读人信息
	List readList=inform.listReaders(serial_no);
	Map readMap=null;
	
	//Utility.debugln("serial_no:"+serial_no+"readList.size()--"+readList.size());
	
	
	for(int i=0;i<readList.size();i++){
	  readMap=(Map)(readList.get(i));
	  nameList += Utility.trimNull(readMap.get("OP_NAME")) + ",";
	  codeList += Utility.trimNull(readMap.get("OP_CODE")) + ",";
	}
	
	if (!nameList.equals(""))
		nameList = nameList.substring(0, nameList.length() - 1);
	if (!codeList.equals(""))
		codeList = codeList.substring(0, codeList.length() - 1);
}

if (request.getMethod().equals("POST")) 
{
	informVo.setTitle(title);
	informVo.setInfo_STR(info_str);
	informVo.setEnd_date(end_date);
	informVo.setInput_man(input_operatorCode);	
	informVo.setTast_type(new Integer(1));
	informVo.setBook_code(input_bookCode);
	if (serial_no != null) {
		informVo.setSerial_no(serial_no);
		inform.modiMessage(informVo);
	}else{	
		serial_no=inform.appendMessage(informVo);
	}	
	bSuccess = true;
	
	//删除消息旧阅读人
	inform.delReaders(serial_no);

	//添加消息阅读人
	String paras_str = request.getParameter("codelist");
	if (paras_str != null && paras_str.length() != 0) 
	{
		String[] paras = Utility.splitString(paras_str, ",");

		for (int i = 0; i < paras.length; i++) {		
			inform.appendReaders(serial_no,new Integer(Utility.parseInt(paras[i], 0)),input_operatorCode);
		}
	}
	else
	{
		inform.appendReaders(serial_no,input_operatorCode,input_operatorCode);
	}
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.messageUpdate",clientLocale)%> </title>
<!--通知信息-->
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
<script type="text/javascript">
var oFCKeditor = new FCKeditor( 'info_str' ) ;
oFCKeditor.BasePath = '/webEditor/' ;
//oFCKeditor.ToolbarSet = 'Basic' ;
oFCKeditor.Width = '100%' ;
oFCKeditor.Height = '260' ;										
</script>	
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess) {
%>
	window.returnValue = 1;
	window.close();
<%}%>
function validateForm(form)
{	
	if(!sl_check(form.title, "<%=LocalUtilis.language("class.Title",clientLocale)%> ", 100, 1))	return false;//标题	
	
	syncDatePicker(form.end_date_picker, form.end_date);	
	return sl_check_update();
}
function showReader()
{
	ret = showModalDialog("document_reader_info.jsp?readerInfo="+document.theform.readerInfo.value+"&serial_no="+document.theform.serial_no.value, "", "dialogWidth:360px;dialogHeight:360px;status:0;help:0");
	var op_names = "";
	var op_codes = "";
	var temp = "";
	if (ret == null)	return;

	theform.readerInfo.value = ret;
	while(ret.indexOf("$") != -1)
	{
		temp = ret.substring(0, ret.indexOf("$"));
		op_codes += temp.substring(0, temp.indexOf(",")) + ",";
		op_names += temp.substring(temp.indexOf(",") + 1) + ",";
		ret = ret.substring(ret.indexOf("$") + 1);
	}
	op_codes = op_codes.substring(0, op_codes.length - 1);
	op_names = op_names.substring(0, op_names.length - 1);
            
	theform.codelist.value = op_codes;
	theform.namelist.value = op_names;
}

function setReader(obj)
{
   if(obj.value=='1')
   {      
      document.all.reader.style.display="";
      document.all.trProduct1.style.display="";
   }   
   else 
   {      
      document.all.reader.style.display="none";
      document.all.trProduct1.style.display="none";
   }   
}
function setProductDis(obj)
{
	if(obj.checked){
		trProduct.style.display=""
		
	}else{	
		trProduct.style.display="none";
		
	}
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0"
	onkeydown="javascript:chachEsc(window.event.keyCode)" class="body">
<form name="theform" method="post" action="message_update.jsp"
	onsubmit="javascript:return validateForm(this);"><input type=hidden
	name="codelist" value="<%=Utility.trimNull(codeList)%>"> <input
	type=hidden name="serial_no" value="<%=Utility.trimNull(serial_no)%>">
<input type=hidden name="readerInfo" value="">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6">
								    <b><%=LocalUtilis.language("menu.messageUpdate",clientLocale)%> </b></font>
								</td><!--通知信息-->
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="2">
					
							<tr>
						
								<td align="right"><%=LocalUtilis.language("class.Title",clientLocale)%> :</td><!--标题-->
								<td><input type="text" name="title"
									onkeydown="javascript:nextKeyPress(this)" size="70"
									value="<%=title%>"></td>
							</tr>
							<tr>
								<td align="right"><%=LocalUtilis.language("class.content2",clientLocale)%> :</td><!--内容-->
								<td><textarea rows="18" name="info_str"
									 cols="80"><%=info_str%></textarea>
									 <script type="text/javascript">
										oFCKeditor.ReplaceTextarea() ;										
									</script>
								</td>
							</tr>
							<tr>
								<td align="right"><%=LocalUtilis.language("class.endDate4",clientLocale)%> :</td><!--到期日期-->
								<td><INPUT TYPE="text" NAME="end_date_picker" READONLY
									class=selecttext 
									value="<%=Format.formatDateLine(end_date)%>" >
								<INPUT TYPE="button" value="▼" class=selectbtn
									onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"
									tabindex="13"> <INPUT TYPE="hidden" NAME="end_date" value=""></td>
							</tr>
							
							
							<tr id="reader" ><!--style="display:none"-->
								<td align="right"><%=LocalUtilis.language("class.nameList",clientLocale)%> :</td><!--阅读人-->
								<td><input name="namelist" size="70" readonly
									value="<%=nameList%>">&nbsp;
								<button type="button"  class="xpbutton3" accessKey=r id="btnSave"
									name="btnSave" onclick="javascript:showReader();"><%=LocalUtilis.language("index.msg.set",clientLocale)%> <!--设置-->(<u>R</u>)</button>
								</td>
							</tr>
							
						</table>
						<table border="0" width="100%">
							<tr>
							<br>
						
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave"
									name="btnSave"
									onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;<!--保存-->
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel"
									name="btnCancel"
									onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--取消-->
								&nbsp;&nbsp;</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>

</HTML>
<%inform.remove();
%>

