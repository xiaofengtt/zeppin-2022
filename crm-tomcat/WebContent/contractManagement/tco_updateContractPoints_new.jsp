<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
boolean bSuccess = false;
//帐套暂时设置
input_bookCode = new Integer(1);

//辅助变量
StringBuffer list = Argument.newStringBuffer();
String sReadonly = "readonly class='edline'";
String sDisabled= "disabled='disabled'";
Map map = null;
List rsList = null;//返回结果集

//获取对象
TcoProductLocal tcoProduct = EJBFactory.getTcoProduct();
TcoProductVO vo = new TcoProductVO();

// 搜索功能
	vo.setInput_man(input_operatorCode);
	vo.setCoProduct_id(new Integer(0));
	vo.setCoProduct_name("");
	rsList = tcoProduct.listProcAllExt(vo);	
	
Integer coContract_id=Utility.parseInt(request.getParameter("coContract_id"),new Integer(0));
if(request.getMethod().equals("POST")){
	TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
	TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();
	Integer coProduct_id=Utility.parseInt(request.getParameter("coProduct_name"),new Integer(0));
	BigDecimal sub_ht_money=Utility.parseDecimal(Utility.trimNull(request.getParameter("sub_ht_money")),new BigDecimal(0.00));
	String point_summary=request.getParameter("point_summary");
	tcoContractPointsVO.setCoContract_id(coContract_id);
	tcoContractPointsVO.setPoint_summary(point_summary);
	tcoContractPointsVO.setCoProduct_id(coProduct_id);
	tcoContractPointsVO.setSub_ht_money(sub_ht_money);
	tcoContractPointsVO.setInput_man(input_operatorCode);
	tcoContractPointsLocal.append(tcoContractPointsVO);
	tcoContractPointsLocal.remove();
	bSuccess = true;
}
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
	<% if(bSuccess){ %>
		window.returnValue = true;
		window.close();
	<%}%>
function validateForm(form)
{

	var v = new Array();
	v[0] = document.theform.coProduct_name.value;
	v[1] =document.theform.coProduct_name.options[document.theform.coProduct_name.selectedIndex].text;
	v[2] = document.theform.sub_ht_money.value;
	v[3] = document.theform.point_summary.value;
	//if confirm("确定要保存吗?"){
		window.returnValue = v;
		//sl_alert("保存成功!");
		window.close();
	//}
}
/*客户信息*/
function getProduct(){	
	var url = '<%=request.getContextPath()%>/contractManagement/tcoProductInfo.jsp';	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');	
		
	if (v!=null){

		document.theform.cust_name.value = v[0]
		document.theform.cust_id.value = v[7];
	}	
	
	return (v != null);
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		sub_ht_money_cn.innerText = "(元)";
	else
		sub_ht_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function saveAction(){
	document.theform.action="tco_updateContractPoints_new.jsp";
	document.theform.submit();
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" >
<input type="hidden" name="coContract_id" value="<%=coContract_id%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>合同要点明细</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td  align="right">产品名称: </td>
		<td  align="left">
			<select id="coProduct_name" name="coProduct_name">
				<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
				<%if (true){
					Iterator iterator = rsList.iterator();
					
					while(iterator.hasNext()){	
						map = (Map)iterator.next();		
								
						Integer r_coProduct_id = Utility.parseInt(Utility.trimNull(map.get("COPRODUCT_ID")), new Integer(0)); 
						String r_coProduct_name = Utility.trimNull(map.get("COPRODUCT_NAME"));
				%>
					<option value="<%=r_coProduct_id%>">							
						<%=r_coProduct_name%>	
					</option>
				<%
					}					
				}
				tcoProduct.remove();
				%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">合同金额:</td>
		<td>
			<INPUT TYPE="text" NAME="sub_ht_money" size="25"  value="" onkeyup="javascript:sl_checkDecimal(form.sub_ht_money,'合同金额',13,3,0);showCnMoney(this.value)">
			<span id="sub_ht_money_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>
		<td align="right">要点明细说明: </td>
		<td align="left" ><textarea cols="25" rows="5" name="point_summary"></textarea></td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
