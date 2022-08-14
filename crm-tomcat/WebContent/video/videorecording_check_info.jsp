<%@ page contentType="text/html; charset=GBK" import="java.math.*,java.util.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.contractManage.*,enfo.crm.vo.*,java.net.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contractBH = Utility.trimNull(request.getParameter("contractBH"));
String productname = Utility.trimNull(request.getParameter("productname"));
String custname = Utility.trimNull(request.getParameter("custname"));
Integer checkflag = Utility.parseInt(request.getParameter("checkflag"),new Integer(0));

String from = Utility.trimNull(request.getParameter("from"));
Integer contract_id = Utility.parseInt(request.getParameter("contract_id"),new Integer(0));
VideoRecordingBean vr = new VideoRecordingBean();
List list=vr.getVideoRecordingListByContractId(contract_id, input_operatorCode );
Map map=new HashMap();
Integer checkflag_this = new Integer(0);
if (list!=null && !list.isEmpty()){
	map=(Map)list.get(0);
	checkflag_this=(Integer)map.get("CheckFlag");
	if (checkflag_this==null) checkflag_this= new Integer(0);
}

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script type="text/javascript">
function op_check(flag){
	if (flag==3){
		var comment=document.getElementById("checkcomment").innerHTML;
		if (comment==""){
			alert("请填写审核不合格的说明");
			return false;
		}
	}
	document.theform.checkflag_action.value=flag;
	document.theform.submit();
}
function toback(){
	<%if ("contract".equals(from)){ %>
	location = '<%=request.getContextPath()%>/marketing/sell/subscribe_check_info.jsp?checkflag=1&serial_no=<%=contract_id%>';
	<%}else { %>
	location = 'videorecording_list.jsp?page=1&pagesize='
		+'&contractBH=' + document.theform.contractBH.value
		+'&productname=' + document.theform.productname.value
		+'&custname=' + document.theform.custname.value
		+'&checkflag=' + document.theform.checkflag.value;
	<%} %>
}
</script>
</HEAD>
<BODY class="BODY">
<%//@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="videorecording_check_action.jsp">
<input name="contract_id" type="hidden" value="<%=contract_id %>">
<input name="vid" type="hidden" value="<%=Utility.trimNull(map.get("VID")) %>">
<input name="contractBH" type="hidden" value="<%=contractBH %>">
<input name="productname" type="hidden" value="<%=productname %>">
<input name="custname" type="hidden" value="<%=custname %>">
<input name="checkflag" type="hidden" value="<%=checkflag %>">
<input name="checkflag_action" type="hidden" >
<input name="from" type="hidden" value="<%=from %>">
<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
	<TR>
		<TD>
			<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
				<tr>
					<td class="page-title"><font color="#215dc6"><b>双录查看/审核</b></font></td>
				</tr>
			</TABLE>
<br/>
			<table border=0 cellSpacing=0 cellPadding=3 width="100%">
				<tr>
					<td align=right>客户名称 :</td>
					<td><INPUT class=edline value='<%=Utility.trimNull(map.get("CUST_NAME"))%>' readOnly name=cust_name></td>
					<td align=right>客户编号 :</TD>
					<td><input class=edline value='<%=Utility.trimNull(map.get("CUST_NO"))%>' readOnly size="25" name=cust_no>
					</td>
				</tr>
				<tr>
					<td align=right>证件类型 :</TD>
					<td><input class=edline value='<%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%>' readOnly name=card_type_name></td>
					<td align=right>证件号码 :</TD>
					<td><input class=edline value='<%=Utility.trimNull(map.get("CARD_ID"))%>' readOnly size=30 name=card_id></td>
				</tr>
				<tr>
					<td align="right">认购金额 :</td>
					<td><input readonly name="rg_money" size="20" value="<%=Utility.trimNull(map.get("RG_MONEY"))%>" class="edline"></td>
					<td align="right">合同编号 :</td>
					<td><input readonly name="contract_bh" size="20" value="<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>" class="edline"></td>
				</tr>
				<tr>
				<tr>
					<td align="right">产品名称 :</td>
					<td><input readonly class="edline" name="product_name" size="40" readonly value="<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>"></td>
					<td align="right"></td>
					<td></td>
				</tr>
				<tr>	
					<td align="right">客户经理 :</td>
					<td>
						<input readonly class="edline" name="service_man" size="20" value="<%=Utility.trimNull(map.get("SERVICE_MAN_NAME"))%>">
					</td>
					<td align="right">合同销售人 :</td>
					<td>
						<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(map.get("LINK_MAN_NAME"))%>">
					</td>
				</tr>
				<tr><td align="right">双录视频 :</td>
					<td colspan="3" align="center">
						<%if (list!=null && !list.isEmpty()){ %>
						 <!-- <object id="video" width="400" height="200" border="0" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA">
							<param name="ShowDisplay" value="0">
							<param name="ShowControls" value="1">
							<param name="AutoStart" value="1">
							<param name="AutoRewind" value="0">
							<param name="PlayCount" value="0">
							<param name="Appearance value="0 value="">
							<param name="BorderStyle value="0 value="">
							<param name="MovieWindowHeight" value="240">
							<param name="MovieWindowWidth" value="320">
							<param name="FileName" value="<%=request.getContextPath()%>/88117-118-27992-1011.mp4">
							<embed width="400" height="200" border="0" showdisplay="0" showcontrols="1" autostart="1" autorewind="0" playcount="0" moviewindowheight="240" moviewindowwidth="320" filename="88117-118-27992-1011.mp4" src="<%=request.getContextPath()%>/88117-118-27992-1011.mp4">
							</embed>
						</object> -->
						<!-- <embed src="<%//=path_base %><%//=Utility.trimNull(map.get("SaveName")) %>" autostart="true" loop="true" width="200" height="150" >
						<embed src="/88117-118-27992-1011.mp4" autostart="true" loop="true" width="200" height="150" > -->
						<video width="320" height="240" controls="controls">
						   <source src="video.jsp?fname=<%=URLEncoder.encode(Utility.trimNull(map.get("SaveName")),"UTF-8") %>" type="video/mp4" />
						</video>
						<%}else{ %>
						<span style="color:#ff0000">双录视频未录制上传</span>
						<%} %>
					</td>
				</tr>
				<tr>	
					<td align="right">审核时间 :</td>
					<td>
						<input readonly class="edline" name="service_man" size="20" value="<%=Utility.trimNull(map.get("CheckTime"))%>">
					</td>
					<td align="right">审核人 :</td>
					<td>
						<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(map.get("CheckManName"))%>">
					</td>
				</tr>
				<tr>	
					<td align="right">审核说明 :</td>
					<td style="color:#ff0000"><%=Utility.trimNull(map.get("CheckComment"))%>
					</td>
				</tr>
				<tr id="checkcomment2" style="display:<%if (checkflag_this.intValue()!=1){out.print("none"); }%>"><td align="right">审核说明</td>
					<td colspan="3" align="left">
						<textarea name="checkcomment" id="checkcomment" rows="4" cols="160"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<table border="0" width="100%">
						<tr>
							<td align="center">
	                            <%if (checkflag_this.intValue()==1){ %>
								<input type="button" class="xpbutton4"  name="btnCheck" title='审核通过' value="审核通过" onclick="javascript:op_check(2);">&nbsp;&nbsp;&nbsp; 
								<input type="button" class="xpbutton4"  name="btnCheck2" title='审核否决' value="审核否决" onclick="javascript:op_check(3);">&nbsp;&nbsp;&nbsp; 
								<%}else{ %>
								<input type="button" class="xpbutton4"  name="btnCheck2" title='审核撤消' value="审核撤消" onclick="javascript:op_check(1);">&nbsp;&nbsp;&nbsp; 
								<%} %>
								<input type="button" class="xpbutton4"  name="btnBack" title='返回' value="返回" onclick="javascript:toback();">&nbsp;&nbsp;&nbsp; 
							</td>
						</tr>
						</TABLE>
					</td>
				</tr>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>