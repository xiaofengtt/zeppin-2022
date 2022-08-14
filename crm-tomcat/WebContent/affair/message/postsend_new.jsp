<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc"%>
<%
boolean bSuccess = false;

Integer input_date = new Integer(0); // �ʼ�����
Integer product_id = new Integer(0);
String contract_sub_bh = "";
String post_no = "";
String post_content = "";
String summary = "";

Map content_map = new HashMap();

//������Ϣ
if (request.getMethod().equals("POST")) {	
	//Integer input_date = Utility.parseDateInt(request.getParameter("input_date"), new Integer(0)); // �ʼ�����
	input_date = Utility.parseInt(request.getParameter("input_date"), new Integer(0)); // �ʼ�����
	product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
	contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh")).trim();
	post_no = Utility.trimNull(request.getParameter("post_no")).trim();
	post_content = Utility.trimNull(request.getParameter("post_content")).trim();
	summary = Utility.trimNull(request.getParameter("summary")).trim();

	String[] contents = post_content.split("\\|"); //
	for (int i=0; i<contents.length; i++) 
		content_map.put(contents[i], new Boolean(true));

	PostSendLocal local = EJBFactory.getPostSend();
	PostSendVO vo = new PostSendVO();
	vo.setInput_date(input_date);
	vo.setProduct_id(product_id);
	vo.setContract_sub_bh(contract_sub_bh);
	vo.setPost_no(post_no);
	vo.setPost_content(post_content);
	vo.setSummary(summary);
	vo.setInput_man(input_operatorCode);

	try {
		local.append(vo);
		bSuccess = true;
	} catch (Exception e) {
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //����ʧ��
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
	}
	local.remove();
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script type='text/javascript' src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.min.js"></script>
<script language=javascript>
var bSuccess = <%=bSuccess%>;
if (bSuccess) { 
	location.href = "<%=request.getContextPath()%>/affair/message/postsend.jsp";
}

window.onload = function() {
	var options = document.theform.product_id.options;
	var i;
	for (i=0; i<options.length; i++) {
		if (options[i].value== "<%=product_id%>") {
			options.selectedIndex = i;
			break;
		}
	}

	if (i!=0 && i<options.length) {
		$.ajax({
			type: 'GET',
			url: "query_contract.jsp",
			data: {"product_id": options[i].value},
			async: false, // ͬ��ִ�лص�����
			dataType: 'json',
			success: function(json) {
				document.theform.contract_sub_bh.options.length = 0;
				document.theform.contract_sub_bh.options.add(new Option("��ѡ��", ""));
				for (var i=0;i<json.length; i++) {
					document.theform.contract_sub_bh.options.add(new Option(json[i].CONTRACT_SUB_BH, json[i].CONTRACT_SUB_BH));;
				}		
			}
		});
	}

	options = document.theform.contract_sub_bh.options;
	for (i=0; i<options.length; i++) {
		if (options[i].value== "<%=contract_sub_bh%>") {
			options.selectedIndex = i;
			break;
		}
	}
};

/*��֤����*/
function validateForm() {	
	syncDatePicker(document.theform.input_date_picker,document.theform.input_date);

	var input_date = trim(document.theform.input_date.value);
	var post_no = trim(document.theform.post_no.value);
	var product_id = document.theform.product_id.value;
	var contract_sub_bh = document.theform.contract_sub_bh.value;
	var errmsg = '';
	var post_content = "";
	var content = document.getElementsByName('content');
	for (var i=0; i<content.length; i++) {
		if (content[i].checked) 
			if (post_content=="") 
				post_content += content[i].value;
			else 
				post_content += "|" + content[i].value;
	}
	document.theform.post_content.value = post_content;

	if (input_date=="") {
		errmsg += 'δ�����ʼ����ڣ���ѡ���ʼ����ڣ�\n';
	}
	if (post_no=='') {
		errmsg += 'δ�����ʼĵ��ţ��������ʼĵ��ţ�\n';
	}
	if (product_id=="0" ) {
		errmsg += 'δѡ���Ʒ����ѡ��һ�����ţ�\n';
	} else if (contract_sub_bh=="") {
		errmsg += 'δ�����ͬ�ţ���ѡ��һ����ͬ�ţ�\n';
	}
	if (post_content == "") {
		errmsg += 'δָ���ʼ����ݣ���ѡ������һ���ʼ����ݣ�\n';
	}

	if (errmsg!='') {
		sl_alert(errmsg);
		return false;
	}

	return sl_check_update();	
}

/*����*/
function SaveAction(){
	if(validateForm()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*ȡ��*/
function CancelAction(){
	location="postsend.jsp";
}

var handle;

function getContractList(product_id) {
	$.ajax({
		type: 'GET',
		url: "query_contract.jsp",
		data: {"product_id": product_id},
		dataType: 'json',
		success: function(json) {		
			document.theform.contract_sub_bh.options.length = 0;
			document.theform.contract_sub_bh.options.add(new Option("��ѡ��", ""));
			for (var i=0;i<json.length; i++) {
				document.theform.contract_sub_bh.options.add(new Option(json[i].CONTRACT_SUB_BH, json[i].CONTRACT_SUB_BH));;
			}	
			clearTimeout(handle); //		
			document.theform.contract_sub_bh.disabled = false;	
			document.getElementById("msg").innerHTML = "";
		}
	});
	handle = setTimeout(function(){
							document.theform.contract_sub_bh.disabled = true;
							document.getElementById("msg").innerHTML = "���ڲ�ѯ�����Ժ�";
						}, 500);

}

function setProduct(value){
	var prodid = "0";
	if (event.keyCode == 13 && value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid=="0") {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			//document.theform.product_id.options[0].selected=true;	
		} else {
			getContractList(prodid);
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
	var prodid = "0";
	if (value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid=="0") {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			//document.theform.product_id.options[0].selected=true;	
		} else {
			getContractList(prodid);
		}
		document.theform.product_id.focus();					
	}	
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="post" action="postsend_new.jsp">
	<div class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<table border="0" style="width: 100%" cellspacing="5" cellpadding="0" class="product-list">
	<tr>
		<td width="200px" align="right">*�ʼ����� ��</td>
		<td align="left">
			<input type="text" name="input_date_picker" class=selecttext style="width: 99px;" readonly="readonly"
				value="<%=input_date.intValue()==0?"":Format.formatDateLine(input_date)%>"/>
			<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.input_date_picker,theform.input_date_picker.value,this);" tabindex="13">
			<input TYPE="hidden" name="input_date" id="input_date" value="<%=input_date%>"/>	
		</td>
	</tr>

	<tr>
		<td width="200px" align="right">*�ʼĵ��� ��</td>
		<td align="left">
			<input type="text" name="post_no" id="post_no" size="30" value="<%=post_no%>"/>
		</td>		
	</tr>

	<tr>
		<td width="200px" align="right">��Ʒ��� ��</td>
		<td  align="left">
				<input type="text" maxlength="16" name="productid" value="" 
					onkeydown="javascript:setProduct(this.value);" maxlength=8 size="22"> &nbsp;
				<button type="button" class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>			
	</tr>
	<tr>
		<td width="200px" align="right">*<%=LocalUtilis.language("class.productNumber",clientLocale)%> ��</td><!-- ��Ʒѡ�� -->
		<td align="left">
			<select name="product_id" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)"
				onchange="javascript:getContractList(this.value)">
				<%=Argument.getProductListOptions(new Integer(1), null, "",input_operatorCode,status)%><%-- status=11 �ƽ鼰�����ڲ�Ʒ--%>				
			</select>
		</td>				
	</tr>

	<tr>	
		<td width="200px" align="right">*��ͬ��� ��</td>
		<td  align="left">
			<SELECT name="contract_sub_bh" style="width: 300px;" onkeydown="javascript:nextKeyPress(this)">		
				<option value="">����ѡ���Ʒ</option>		
			</SELECT>
			<span id="msg"></span>
		</td>	
	</tr>

	<tr>	
		<td width="200px" align="right">*�ʼ����� ��</td>
		<td align="left">
			<table>
				<tr>
					<td><input type="checkbox" name="content" value="1" class="flatcheckbox"
						<%=content_map.get("1")!=null?"checked=\"checked\"":""%>/>��������</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="2" class="flatcheckbox"
						<%=content_map.get("2")!=null?"checked=\"checked\"":""%>/>ȷ�ϵ�</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="3" class="flatcheckbox"
						<%=content_map.get("3")!=null?"checked=\"checked\"":""%>/>������</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="4" class="flatcheckbox"
						<%=content_map.get("4")!=null?"checked=\"checked\"":""%>/>��ʱ��Ϣ��¶</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="5" class="flatcheckbox"
						<%=content_map.get("5")!=null?"checked=\"checked\"":""%>/>��ֹ����</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="6" class="flatcheckbox"
						<%=content_map.get("6")!=null?"checked=\"checked\"":""%>/>����</td></tr>
			</table>
			<input type="hidden" name="post_content" value=""/>
		</td>	
	</tr>

	<tr>
		<td width="200px" align="right">��ע ��</td>
		<td align="left"><textarea rows="3" name="summary"
			onkeydown="javascript:nextKeyPress(this)" cols="62"><%=summary%></textarea></td>
	</tr>
</table>

<div align="right"><br>
<!-- ���� -->
<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
	onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save", clientLocale)%>
(<u>S</u>)</button>
&nbsp;&nbsp;&nbsp;&nbsp; <!-- ���� -->
<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel"
	onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back", clientLocale)%>
(<u>C</u>)</button>
&nbsp;&nbsp;</div>
</form>
</BODY>
</HTML>
