<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file = "/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
 %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>请选择一个产品</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">	
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

function chosen() {
	if (document.theform.product_id.value=="0") {
		sl_alert("请选择一个产品！");
		document.theform.product_id.focus();
		return;
	}
	window.returnValue = document.theform.product_id.value;
	window.close();
}

function canceled() {
	window.returnValue = null;
	window.close();
}
</script>
</head>
<body class="body">
<form name="theform">
	<table border="0" width="100%" cellspacing="2" cellpadding="2" style="table-layout:fixed">
		<tr>	
			<td align="right" width="20%">产品编号 : </td>
			<td  align="left" width="*">
				<input type="text" maxlength="16" name="productid" value="" 
					onkeydown="javascript:setProduct(this.value);" maxlength=8 size="22"> &nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td>			
		</tr>
		<tr>
			<td align="right" width="20%"><%=LocalUtilis.language("class.productNumber",clientLocale)%> : </td><!-- 产品选择 -->
			<td align="left" width="*">
				<SELECT name="product_id" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1),product_id,"",input_operatorCode,status)%><%-- status=11 推介及正常期产品--%>
				</SELECT>
			</td>	
		</tr>
		<tr>
			<td align="right" colspan="2" width="100%">
				<button type="button"  class="xpbutton2" onclick="javascript:chosen();">确定</button>
				&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:canceled();">取消</button>				
			</td>
		</tr>			
	</table>
</form>
</body>
</html>