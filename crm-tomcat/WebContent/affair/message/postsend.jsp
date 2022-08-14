<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% // 处理分页查询
Integer input_date = Utility.parseInt(request.getParameter("input_date"), new Integer(0)); // 邮寄日期
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh")).trim();
String post_no = Utility.trimNull(request.getParameter("post_no")).trim();

PostSendLocal local = EJBFactory.getPostSend();
PostSendVO vo = new PostSendVO();

vo.setInput_date(input_date);
vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setPost_no(post_no);
//vo.setInput_man(input_operatorCode);

String[] totalColumn = new String[0];
int pageSize = Utility.parseInt(sPagesize,10);
IPageList pageList = local.pagelist_query(vo, totalColumn,Utility.parseInt(sPage,1),pageSize);
List list = pageList.getRsList();
for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);

	String post_content = Utility.trimNull((String)map.get("POST_CONTENT"));
	String[] contents = post_content.split("\\|"); //
	String s = "";
	for (int j=0; j<contents.length; j++) {
		if (contents[j].equals("1")) {
			if (j==0)
				s += "成立公告";
			else if (j<contents.length-1) 
				s += "、成立公告";
			else 
				s += "和成立公告";
		} else if (contents[j].equals("2")) {
			if (j==0)
				s += "确认单";
			else if (j<contents.length-1) 
				s += "、确认单";
			else 
				s += "和确认单";
		} else if (contents[j].equals("3")) {
			if (j==0)
				s += "管理报告";
			else if (j<contents.length-1) 
				s += "、管理报告";
			else 
				s += "和管理报告";
		} else if (contents[j].equals("4")) {
			if (j==0)
				s += "临时信息披露";
			else if (j<contents.length-1) 
				s += "、临时信息披露";
			else 
				s += "和临时信息披露";
		} else if (contents[j].equals("5")) {
			if (j==0)
				s += "终止公告";
			else if (j<contents.length-1) 
				s += "、终止公告";
			else 
				s += "和终止公告";
		} else if (contents[j].equals("6")) {
			if (j==0)
				s += "其他";
			else if (j<contents.length-1) 
				s += "、其他";
			else 
				s += "和其他";
		} 
	}
	map.put("POST_CONTENT", s);
}

sUrl += "&input_date="+input_date + "&product_id="+product_id 
	+ "&contract_sub_bh="+contract_sub_bh + "&post_no="+post_no;

//分页参数
int iCount = 0;
int iCurrent = 0;
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.authorizationManagement",clientLocale)%> </title><!-- 授权管理 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">	
	window.onload = function(){
		initQueryCondition();
	};	

	function $(_id){
		return document.getElementById(_id);
	}
	
	function $$(_name){
		return document.getElementsByName(_name)[0];
	}

	function setProduct(value){
		prodid=0;
		if (event.keyCode == 13 && value != "")
		{
	        j = value.length;
			for(i=0;i<document.theform.product_id.options.length;i++)
			{
				if(document.theform.product_id.options[i].text.substring(0,j)==value)
				{
					document.theform.product_id.options[i].selected=true;
					prodid = document.theform.product_id.options[i].value;
					break;
				}	
			}
			if (prodid==0)
			{
				sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			}			
		}
		nextKeyPress(this);
	}

	function searchProduct(value) {
		prodid=0;
		if (value != "") {
	        j = value.length;
			for(i=0;i<document.theform.product_id.options.length;i++) {
				if(document.theform.product_id.options[i].text.substring(0,j)==value) {
					document.theform.product_id.options[i].selected=true;
					prodid = document.theform.product_id.options[i].value;
					break;
				}	
			}
			if (prodid==0) {
				sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			} 
			document.theform.product_id.focus();					
		}	
	}

	function QueryAction(){
		syncDatePicker(document.theform.input_date_picker,document.theform.input_date);

		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/affair/message/postsend.jsp?pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url += "&post_no=" + $$("post_no").getAttribute("value")||"";
		url += "&contract_sub_bh="+$$("contract_sub_bh").getAttribute("value")||"";
		url += "&product_id="+$$("product_id").getAttribute("value")||"";
		url += "&input_date="+ $$("input_date").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}

	function AppendAction(){
		var url = "<%=request.getContextPath()%>/affair/message/postsend_new.jsp";
		window.location.href = url;
	}
	
	function ModiAction(input_date, product_id, contract_sub_bh,serial_no) {
		var url = "<%=request.getContextPath()%>/affair/message/postsend_edit.jsp?input_date="+input_date
			+ "&product_id="+product_id + "&contract_sub_bh="+contract_sub_bh + "&serial_no=" + serial_no;
		window.location.href = url;
	}

	function refreshPage() {
		syncDatePicker(document.theform.input_date_picker,document.theform.input_date);
		document.theform.submit();	
	}

	function batchEdit(){

		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/affair/message/postsend_batch_edit.jsp?pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url += "&product_id="+$$("product_id").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}
</script>
</head>

<body class="body body-nox">
<form name="theform" action="postsend.jsp" method="post">
<!-- 查询条件 -->
<div id="queryCondition" class="qcMain" style="display:none;width:540px;height:160px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;">邮寄单号 : </td>
			<td  align="left">
				<input type="text" name="post_no" id="post_no" value="<%=post_no%>"
					onkeydown="javascript:nextKeyPress(this)" style="width:120px"/>
			</td>
			<td  align="right" style="width: 90px;">邮寄日期 : </td><!--邮寄日期-->
			<td  align="left">
				<input type="text" name="input_date_picker" class=selecttext style="width: 99px;" value="<%=Format.formatDateLine(input_date)%>">
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.input_date_picker,theform.input_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="input_date" id="input_date" />	
			</td>						
		</tr>
		<tr>	
			<td  align="right" style="width: 90px;">合同编号 : </td>
			<td  align="left">
				<input type="text" name="contract_sub_bh" id="contract_sub_bh" value="<%=contract_sub_bh%>"
					onkeydown="javascript:nextKeyPress(this)" style="width:120px"/>
			</td>		
			<td  align="right" style="width: 90px;">产品编号 : </td>
			<td  align="left">
				<input type="text" maxlength="16" name="productid" value="" 
					onkeydown="javascript:setProduct(this.value);" maxlength=8 size="22"> &nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td>			
		</tr>

		<tr>
			<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> : </td><!-- 产品选择 -->
			<td align="left" colspan=3>
				<SELECT name="product_id" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1),product_id,"",input_operatorCode,status)%><%-- status=11 推介及正常期产品--%>
				</SELECT>
			</td>	
		</tr>

		<tr>
			<td align="center" colspan="4">
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing="0" cellPadding="0" width="100%" align="center" border="0">
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right" >
		<div class="btn-wrapper">
			<button type="button"  class="xpbutton4" accessKey=e id="editButton" name="editButton" title="批量编辑" onclick="javascript:batchEdit();">批量编辑 (<u>E</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%//if (input_operator.hasFunc(menu_id, 108)) {%>
            <!-- 查询 -->
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%//}%>
			<%if (input_operator.hasFunc(menu_id, 100)) {%>
            <!-- 新建 -->
			<button type="button"  class="xpbutton3" accessKey=a id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>A</u>)</button>
			<%}%>
			</div>
		</td>
	</tr>
</table>
<br/>
<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		 <td width="" align="center">邮寄日期</td>
		 <td width="" align="center">邮寄/快递单号</td>
         <td width="" align="center">合同</td>
		 <td width="" align="center">客户名称</td>
		 <td width="" align="center">邮寄内容</td> <!-- 1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔 -->
         <td width="6%"  align="center"><%=LocalUtilis.language("message.update",clientLocale)%> </td><!-- 修改 -->
    </tr>
<%
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	iCount ++;
%>   
         <tr class="tr<%=iCount%2%>">
	        <td align="center"><%=Format.formatDateLine((Integer)map.get("INPUT_DATE"))%></td> 
	        <td align="center"><%=Utility.trimNull(map.get("POST_NO"))%></td> 
			<td align="center"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%>[<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>]</td> 
	        <td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td> 
			<td align="center"><%=Utility.trimNull(map.get("POST_CONTENT"))%></td> 
	        <td align="center">  
	        	<a href="javascript:void(0);"> 
					<%if (input_operator.hasFunc(menu_id, 102)) {%>
                    <!-- 修改 -->
	        		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" 
						onclick='javascript:ModiAction(<%=map.get("INPUT_DATE")%>,<%=map.get("PRODUCT_ID")%>,"<%=map.get("CONTRACT_SUB_BH")%>",<%=map.get("SERIAL_NO") %>);' 
						title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
					<%} %>
				</a>
	        </td>     
         </tr>   
<%}%>   
  	
<%
for(int i=0;i<(pageSize-iCount);i++){ %>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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

</form>
</body>
</html>
<%
	local.remove();
 %>
