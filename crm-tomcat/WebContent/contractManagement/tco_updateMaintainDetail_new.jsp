<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
String cust_name=request.getParameter("cust_name");
//返回结果集
Map map = null;
List rsList = null;

//获取对象
TcoContractLocal local = EJBFactory.getTcoContract();
TcoContractVO vo = new TcoContractVO();

// 搜索功能
vo.setInput_man(input_operatorCode);
vo.setCust_name(cust_name);
rsList = local.queryByList(vo);	

boolean bSuccess = false;
Integer maintain_id=Utility.parseInt(request.getParameter("maintain_id"),new Integer(0));
if(request.getMethod().equals("POST")){
	TcoMaintainDetailLocal tcoMaintainDetailLocal = EJBFactory.getTcoMaintainDetail();
	TcoMaintainDetailVO tcoMaintainDetailVO = new TcoMaintainDetailVO();
	tcoMaintainDetailVO.setMaintain_id(maintain_id);
	tcoMaintainDetailVO.setInput_man(input_operatorCode);
	tcoMaintainDetailVO.setCoContract_id(Utility.parseInt(request.getParameter("coContract_id"),new Integer(0)));
	tcoMaintainDetailVO.setMain_pro_name(request.getParameter("main_pro_name"));
	tcoMaintainDetailVO.setXm_ht_money(Utility.parseDecimal(request.getParameter("xm_ht_money"),new BigDecimal(0.00)));
	tcoMaintainDetailVO.setMain_rate(Utility.parseDecimal(request.getParameter("main_rate"),new BigDecimal(0.00)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
	tcoMaintainDetailVO.setMain_money(Utility.parseDecimal(request.getParameter("main_money"),new BigDecimal(0.00)));
	tcoMaintainDetailVO.setInput_man(input_operatorCode);
	tcoMaintainDetailLocal.append(tcoMaintainDetailVO);
	tcoMaintainDetailLocal.remove();
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
	v[0] = document.theform.coContract_id.value;
	v[1] =document.theform.coContract_id.options[document.theform.coContract_id.selectedIndex].text;
	v[2] = document.theform.main_pro_name.value;
	v[3] = document.theform.xm_ht_money.value;
	v[4] = document.theform.main_rate.value;
	v[5] = document.theform.main_money.value;
	window.returnValue = v;
	window.close();
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		xm_ht_money_cn.innerText = "(元)";
	else
		xm_ht_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
/*显示中文钱数*/
function showCnMoney1(value){
	temp = value;
	if (trim(value) == "")
		main_money_cn.innerText = "(元)";
	else
		main_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
function saveAction(){
	document.theform.action="tco_updateMaintainDetail_new.jsp";
	document.theform.submit();
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post">
<input type="hidden" name="maintain_id" value="<%=maintain_id%>"/>
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
		<td  align="right">主合同编号: </td>
		<td  align="left">
			<select id="coContract_id" name="coContract_id">
				<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
				<%	
				    Iterator iterator = rsList.iterator();
					while(iterator.hasNext()){	
						map = (Map)iterator.next();		
								
						Integer r_coContract_id = Utility.parseInt(Utility.trimNull(map.get("COCONTRACT_ID")), new Integer(0)); 
						String r_coContract_sub_bh = Utility.trimNull(map.get("COCONTRACT_SUB_BH"));
				%>
					<option value="<%=r_coContract_id%>">							
						<%=r_coContract_sub_bh%>	
					</option>
				<%} %>
			</select>
			<%if(rsList.size()==0){%>
				<span id="noContractTip">&nbsp;&nbsp;&nbsp;<font color="red">该客户还没有与我司签订过合同</font></span>
			<%}local.remove();%>
		</td>
	</tr>
	<tr>
		<td align="right">维护项目名称:</td>
		<td>
			<INPUT TYPE="text" NAME="main_pro_name" size="25"  value="" >
		</td>
	</tr>
	<tr>
		<td align="right">项目合同金额: </td>
		<td align="left" ><INPUT TYPE="text" NAME="xm_ht_money" size="25"  value="" onkeyup="javascript:sl_checkDecimal(form.xm_ht_money,'项目合同金额',13,3,0);showCnMoney(this.value)">
			<span id="xm_ht_money_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>
		<td align="right">维护费率: </td>
		<td align="left" ><INPUT TYPE="text" NAME="main_rate" size="25"  value="" >&nbsp;&nbsp;%
		</td>
	</tr>
	<tr>
		<td align="right">维护金额: </td>
		<td align="left" ><INPUT TYPE="text" NAME="main_money" size="25"  value="" onkeyup="javascript:sl_checkDecimal(form.main_money,'维护金额',13,3,0);showCnMoney1(this.value)">
			<span id="main_money_cn" class="span">&nbsp;(元)</span>
		</td>
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
