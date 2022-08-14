<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% // 处理分页查询

Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_subbh = Utility.trimNull(request.getParameter("contract_subbh")).trim();
String cust_name = Utility.trimNull(request.getParameter("cust_name")).trim();

boolean ibuss = false;

PostSendLocal local = EJBFactory.getPostSend();
PostSendVO vo = new PostSendVO();
vo.setProduct_id(product_id);
if (request.getMethod().equals("POST")){
	String[] serial_no = request.getParameterValues("serial_no");
	String[] post_date = request.getParameterValues("post_date");
	String[] contract_sub_bh = request.getParameterValues("contract_sub_bh");
	String[] post_no = request.getParameterValues("post_no");
	String[] post_content = request.getParameterValues("post_content");
	String[] summary = request.getParameterValues("summary");
	
	vo.setInput_man(input_operatorCode);
	if(serial_no != null && serial_no.length>0){
		for(int i=0;i<serial_no.length;i++){	
			String s="";
			String s1="";
			String year="";
			String month="";
			String day="";
			int k=0;
			int j=0;
			
			if("".equalsIgnoreCase(post_date[i].trim())||"".equalsIgnoreCase(post_no[i].trim()))continue;
	
			s=post_date[i];
			year=s.substring(0,4);
			k=s.indexOf('-');
			s1=s.substring(k+1,s.length());
			j=s1.indexOf('-');
			month=s1.substring(0,j);
			day=s1.substring(j+1,s1.length());
			if(month.length()==1)
			month='0'+month;
			s=year+month+day;
				
			vo.setSerial_no(Utility.parseInt(serial_no[i],null));
			vo.setInput_date(Utility.parseInt(s,null));
			vo.setContract_sub_bh(contract_sub_bh[i]);
			vo.setPost_no(post_no[i]);
			vo.setPost_content(post_content[i]);
			vo.setSummary(summary[i]);
			
			local.batchModi(vo);
		}
	}

	ibuss = true;
}
vo.setContract_sub_bh(contract_subbh);
vo.setCust_name(cust_name);
String[] totalColumn = new String[0];
int pageSize = Utility.parseInt(sPagesize,10);
IPageList pageList = local.query(vo, totalColumn,Utility.parseInt(sPage,1),pageSize);
List list = pageList.getRsList();
for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);

	String post_content = Utility.trimNull((String)map.get("POST_CONTENT"));
	String[] contents = post_content.split("\\|"); 
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
	map.put("POST_CONTENT1", s);
}

sUrl += "&product_id="+product_id 
	+ "&cust_name"+cust_name
	+ "&contract_subbh="+contract_subbh ;

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
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar1.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">	
	window.onload = function(){
		initQueryCondition();
		if(<%=ibuss%>)alert("保存成功！");
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
		var _pagesize = document.getElementsByName("pagesize")[0];
		var url = '<%=request.getContextPath()%>/affair/message/postsend_batch_edit.jsp?pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url += "&product_id="+$$("product_id").getAttribute("value")||"";
		url += "&cust_name="+$$("cust_name").getAttribute("value")||"";
		url += "&contract_subbh="+$$("contract_subbh").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}

	function back(){

		var _pagesize = document.getElementsByName("pagesize")[0];
		//url 组合
		var url = '<%=request.getContextPath()%>/affair/message/postsend.jsp?pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url += "&product_id="+$$("product_id").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}

	function save(){
		var post_date = document.getElementsByName("post_date");
		var post_no = document.getElementsByName("post_no");
		var post_content = document.getElementsByName("post_content");
		var contract_sub_bh = document.getElementsByName("contract_sub_bh");
		for(var i=0;i<contract_sub_bh.length;i++){
			if(post_date[i].value==""&&post_no[i].value.replace(/(^\s*)|(\s*$)/g,'')==""&&post_content[i].value==""){
				continue;
			}else if(post_date[i].value!=""&&post_no[i].value.replace(/(^\s*)|(\s*$)/g,'')!=""&&post_content[i].value!=""){
				continue;
			}else{
				var contract_sub = contract_sub_bh[i].value;
				var temp="合同号为 " + contract_sub + " 的信息填写有误，日期，单号及内容必须全部填写！"
				alert(temp);return flase;
			}
		}

		if(sl_confirm("批量保存"))document.getElementsByName('theform')[0].submit();
	}

	function valueChange(date){
		var arr = document.getElementsByName('post_date');
		for(var i=0;i<arr.length;i++){
			arr[i].value = date;
		}
	}

	function setPostContent(id){
		var post_id = "post_content"+ id;
		var postContent = $(post_id).value;
		var url="postsend_choose_postcontent.jsp?post_content="+ postContent;
		var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
		var post_content = "";
		if(ret!=null){
			post_content = ret[0];
			for(var i=1;i<ret.length;i++){
				post_content += "|" + ret[i];
			}
			$(post_id).value = post_content;
			if(ret.length==0)$(post_id).value = "";
			var s = "";
			for (var j=0; j<ret.length; j++) {
				if (ret[j]=="1") {
				if (j==0)
					s += "成立公告";
				else if (j<ret.length-1) 
					s += "、成立公告";
				else 
					s += "和成立公告";
				} else if (ret[j]=="2") {
					if (j==0)
						s += "确认单";
					else if (j<ret.length-1) 
						s += "、确认单";
					else 
						s += "和确认单";
				} else if (ret[j]=="3") {
					if (j==0)
						s += "管理报告";
					else if (j<ret.length-1) 
						s += "、管理报告";
					else 
						s += "和管理报告";
				} else if (ret[j]=="4") {
					if (j==0)
						s += "临时信息披露";
					else if (j<ret.length-1) 
						s += "、临时信息披露";
					else 
						s += "和临时信息披露";
				} else if (ret[j]=="5") {
					if (j==0)
						s += "终止公告";
					else if (j<ret.length-1) 
						s += "、终止公告";
					else 
						s += "和终止公告";
				} else if (ret[j]=="6") {
					if (j==0)
						s += "其他";
					else if (j<ret.length-1) 
						s += "、其他";
					else 
						s += "和其他";
				} 
			}
			$(id).value = s;
		}
	}

	function refreshPage() {
		var _pagesize = document.getElementsByName("pagesize")[0];
		var url = '<%=request.getContextPath()%>/affair/message/postsend_batch_edit.jsp?pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
		url += "&product_id="+$$("product_id").getAttribute("value")||"";
		disableAllBtn(true);
		window.location = url;
	}
</script>
</head>

<body class="body">
<form name="theform" action="postsend_batch_edit.jsp" method="post">
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
			<td  align="right" style="width: 90px;">客户名称 : </td>
			<td  align="left">
				<input type="text" name="cust_name" id="cust_name" value="<%=cust_name%>"
					onkeydown="javascript:nextKeyPress(this)" />
			</td>
			<td  align="right" style="width: 90px;">合同编号 : </td>
			<td  align="left">
				<input type="text" name="contract_subbh" value="<%=contract_subbh%>" onkeydown="javascript:nextKeyPress(this)">	
			</td>						
		</tr>
		<tr>			
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
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--确认-->	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing="0" cellPadding="0" width="100%" align="center" border="0">
	<tr>
		<td align="left">
			<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">		
			<%//if (input_operator.hasFunc(menu_id, 108)) {%>
            <!-- 查询 -->
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<%//}%>
			<button type="button" class="xpbutton3" accessKey=s id="backButton" name="backButton" title="保存" onclick="javascript:save();">保存(<u>S</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b id="backButton" name="backButton" title="返回" onclick="javascript:back();">返回(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="5"><hr noshade color="#808080" size="1"></td>
	</tr>
</table>

<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		<td width="" align="center">合同编号</td>
		<td width="" align="center">客户名称</td>
		<td width="9%" align="center">邮寄日期
			<input TYPE="hidden" name="post_date_picker" id="post_date_picker" class=selecttext onpropertychange="javascript:valueChange(this.value);"/>
			<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.post_date_picker,theform.post_date_picker.value,this);" tabindex="13">			
		</td>
		<td width="" align="center">邮寄/快递单号</td>     
		<td width="" align="center">邮寄内容</td> <!-- 1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔 -->
        <td width="" align="center">备注</td>
    </tr>
<%
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	iCount ++;
%>   
         <tr class="tr<%=iCount%2%>">
			<td align="center">
				<input type="hidden" name="serial_no" id="serial_no<%= iCount%>" value="<%=Utility.trimNull(map.get("SERIAL_NO"))%>">
				<input type="hidden" name="contract_sub_bh" id="contract_sub_bh<%= iCount%>" value="<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>">
				<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>
			</td> 
	        <td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td> 
	        <td align="center">
				<input size="10" type="text" name="post_date" id="post_date_picker<%= iCount%>" value="<%=Format.formatDateLine((Integer)map.get("INPUT_DATE"))%>" readonly=readonly>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.post_date_picker<%= iCount%>,theform.post_date_picker<%= iCount%>.value,this);" tabindex="13">
			</td> 
	        <td align="center"><input type="text" name="post_no" value="<%=Utility.trimNull(map.get("POST_NO"))%>" size="15"></td> 		
			<td align="center">
				<input type="hidden" id="post_content<%= iCount%>" name="post_content" value="<%=Utility.trimNull(map.get("POST_CONTENT"))%>">
				<input type="text" id="<%= iCount%>" class="edline" readonly=readonly value="<%=Utility.trimNull(map.get("POST_CONTENT1"))%>" onclick="javascript:setPostContent(this.id);">
			</td> 
	        <td align="center"><input type="text" size="40" name="summary" value="<%=Utility.trimNull(map.get("SUMMARY"))%>"></td>     
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
    		<table border="0" width="100%">
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
