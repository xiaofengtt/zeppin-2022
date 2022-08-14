<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递变量
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
Integer reg_data = Utility.parseInt(Utility.trimNull(request.getParameter("reg_date")),new Integer(0));
String invest_type = Utility.trimNull(request.getParameter("invest_type"),"");
String invest_type_name = Utility.trimNull(request.getParameter("invest_type_name"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);//帐套暂时设置
//声明变量
String cust_no = "";
String cust_name = "";
BigDecimal reg_money = new BigDecimal(0);
Integer reg_valid_days = new Integer(0);
String summary = "";
//获取对象
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();
//显示数据
List list = null;
Map map = null;

if(request.getMethod().equals("POST")){
	//保存变量
	String r_cust_no = "";
	String r_cust_name = "";
	BigDecimal r_reg_money = new BigDecimal(0);
	Integer r_reg_valid_days = new Integer(0);
	String r_summary = "";
	//保存数据
	r_cust_no = Utility.trimNull(request.getParameter("cust_no"));
	r_cust_name = Utility.trimNull(request.getParameter("cust_name"));
	r_reg_money = Utility.parseDecimal(request.getParameter("reg_money"), null);
	r_reg_valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("reg_date_picker")),new Integer(0));	
	r_summary = Utility.trimNull(request.getParameter("summary"));
	//dingyj添加获得有效天数
	reg_valid_days = Utility.parseInt(request.getParameter("reg_valid_days"), new Integer(0));
		
	pre_vo.setCust_id(cust_id);
	pre_vo.setBook_code(input_bookCode);	
	pre_vo.setReg_money(r_reg_money);	
	pre_vo.setReg_date(reg_data);
	pre_vo.setReg_valid_days(reg_valid_days);
	pre_vo.setInvest_type(invest_type);	
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setSummary(r_summary);
	pre_vo.setInvest_type_name(invest_type_name);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setLink_man(link_man);
	
	preContract.save_reginfo(pre_vo);		
	bSuccess = true;
}
else{	
}

if(cust_id.intValue()>0){
	pre_vo.setCust_id(cust_id);
	pre_vo.setInput_man(input_operatorCode);
		
	list = preContract.query_reginfo(pre_vo);
	
	if(list.size()>0){
		map = (Map)list.get(0);
	}		
}

if(map!=null){
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	reg_money = Utility.parseDecimal(Utility.trimNull(map.get("REG_MONEY")), new BigDecimal(0.00));
	summary = Utility.trimNull(map.get("SUMMARY"));
	reg_data = Utility.parseInt(Utility.trimNull(map.get("REG_DATE")),new Integer(0));	
	reg_valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));	
	invest_type = Utility.trimNull(map.get("INVEST_TYPE"));
	invest_type_name = Utility.trimNull(map.get("INVEST_TYPE_NAME"));
	customer_cust_source = Utility.trimNull(map.get("CUST_SOURCE"));
	link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
}	

//认购金额中文处理
String reg_money_cn = "";
if (reg_money.intValue()!= 0){
	reg_money_cn =  Utility.numToChinese(reg_money.toString());
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.checkinEdit",clientLocale)%> </TITLE>
<!--预登记修改-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>	
	/*汉化数字*/
	function showCnMoney(value,index){
		temp = value;
		
		if (trim(value) == ""){
			if(index==1)
				last_money_cn.innerText = "";
			else if(index==2)
				rg_money_cn.innerText = "";
			else if(index==3)
				reg_money_cn.innerText = "";
		}
		else{
			if(index==1)
				last_money_cn.innerText = "(" + numToChinese(temp) + ")";
			else if(index==3)
				reg_money_cn.innerText = "(" + numToChinese(temp) + ")";
			else if(index==2)
				rg_money_cn.innerText = "(" + numToChinese(temp) + ")";
		}	
	 }

	function vadidateReg_money(){		
		if(!sl_checkDecimal(document.theform.reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,1))return false;//登记额度
	}

	/*保存*/
	function SaveAction(){
		if(document.getElementsByName("theform")[0].onsubmit()){
			document.getElementsByName("theform")[0].submit();
		}	
	}
	
	/*验证数据*/
	function validateForm(form){
		if(!sl_checkDecimal(form.reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,1))return false;//登记额度
		if(!sl_checkNum(document.theform.reg_valid_days,"<%=LocalUtilis.language("class.validDays",clientLocale)%> ",8,1))return false;//有效天数				
		if(!sl_checkDate(form.reg_date_picker,"<%=LocalUtilis.language("class.regDate",clientLocale)%> "))return false;//登记日期
		if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//联系人
		
		form.reg_money.value=sl_parseFloat(form.reg_money.value);	
		syncDatePicker(form.reg_date_picker, form.reg_date);		
		return sl_check_update();	
	}
	/*页面初始化*/
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		
		if(v_bSuccess=="true"){		
			window.returnValue = 1 ;
			window.close();
		}
	}
	/*多选投向*/
	function chooseAction(){
		var invest_type = document.getElementById("invest_type").value;
		var url = "<%=request.getContextPath()%>/marketing/invest_type_check.jsp?invest_type="+invest_type;
		var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
		
		if(ret!=null){
			document.getElementById("invest_type").value = ret[0];
			document.getElementById("invest_type_name").value = ret[1];
		}
	}
	/*客户信息*/
	function getMarketingCustomer(prefix,readonly){
		cust_id = document.getElementById("cust_id").value;
		var url = '<%=request.getContextPath()%>/marketing/customerInfo_details_view.jsp?readonly=1&cust_id=' + cust_id;
		v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:520px;status:0;help:0;');
	}
</script>
</HEAD>

<BODY class="BODY body-nox" leftMargin=0 topMargin=0 rightmargin="0"	bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0"onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="checkin_edit.jsp" onsubmit="javascript: return validateForm(this);">

<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list" >
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :&nbsp;&nbsp;</td><!--客户编号-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" size="27" name="cust_no" readonly class="edline" value="<%=cust_no%>"/> 
				<input id="cust_id"	name="cust_id" type="hidden"value="<%=cust_id%>"/>
			</td>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" size="27"	name="cust_name" readonly class="edline" value="<%=cust_name%>"/>
				<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> "
		        	onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.view",clientLocale)%>
		    	</button><!--客户信息--><!--查看-->
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--登记额度-->
			<td colspan="3">
				<input name="reg_money" size="27" value="<%=Utility.trimZero(reg_money)%>"
					onkeydown="javascript:nextKeyPress(this)"
					onkeyup="javascript:vadidateReg_money();showCnMoney(this.value,3)"> 
			<%if (reg_money!= null){%>
				<span id="reg_money_cn" class="span">(<%=reg_money_cn%>)</span>
			<%} 
			else {%>
				<span id="reg_money_cn" class="span">&nbsp;(<%=LocalUtilis.language("message.10000",clientLocale)%> )</span><!--万-->
			<%}%>
			</td>
		</tr>							
		<tr>
			<td align="right"><%=LocalUtilis.language("class.validDays",clientLocale)%> :&nbsp;&nbsp;</td><!--有效天数-->
			<td>
				<input name="reg_valid_days" type="text" size="27" 
						value="<%=reg_valid_days==null?"0":reg_valid_days.toString()%>"/>
			</td>	
			<td align="right"><%=LocalUtilis.language("class.regDate",clientLocale)%> :&nbsp;&nbsp;</td> <!--登记日期-->     
			<td>
			<input  onkeydown="javascript:nextKeyPress(this)" size="27" name="reg_date_picker" class=selecttext 
			<%if (reg_data.intValue() == 0|| reg_data == null) {%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%} 
			else {%>value="<%=Format.formatDateLine(reg_data)%>"<%}%> />
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.reg_date_picker,theform.reg_date_picker.value,this);" tabindex="13">
			<input type="hidden" name="reg_date" value="">
		</tr>			
		<tr>
			<%if(user_id.intValue() != 2) {%>
			<td align="right"><%=LocalUtilis.language("class.investType",clientLocale)%> :&nbsp;&nbsp;</td><!--预计投向-->
			<td>
				<!-- 
				<select size="1" name="invest_type"	onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getDictParamOptions_intrust(1131,invest_type)%>
				</select>
				-->
				<input type="text" name="invest_type_name" id="invest_type_name" size="27"  value="<%=invest_type_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="invest_type" id="invest_type" value="<%=invest_type%>" /> 
				<!-- 选择 -->&nbsp;&nbsp;&nbsp;
                <button type="button"  class="xpbutton2" id="btnChoInvestType" name="btnChoInvestType" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>	
			<%} %>
		</tr>	
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.contact",clientLocale)%>:</td><!--联系人-->
			<td>
			    <select name="link_man" style="width:160px">
			        <%=Argument.getManager_Value(link_man)%>				    
			    </select>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :&nbsp;&nbsp;</td><!--备注-->
			<td colspan="3"><textarea cols="100" rows="6" name="summary"><%=summary%></textarea></td>
		</tr>
	</table>
</div>	
<br>
<div align="right" style="margin-right:10px;">	
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--关闭-->
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%preContract.remove();%>