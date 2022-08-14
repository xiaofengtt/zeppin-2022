<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;

//受益人信息
Integer bonus_flag = new Integer(0);//收益分配方式
Integer temp_bonus_flag = new Integer(0);//收益分配方式
Integer modi_time2 = new Integer(0);
String contract_sub_bh = "";
String cust_no = "";
String cust_name ="";
String card_id = "";
String bank_id = "";
String bank_sub_name = "";
String bank_acct ="";
String cust_acct_name ="";
String acct_chg_reason = "";
String temp_bank_acct ="";
String temp_bank_id ="";
String temp_bank_sub_name ="";
String temp_acct_name ="";
String bank_city = "",bank_province = "";
String jk_type_name = "";
Integer temp_df_product_id = new Integer(0);
String temp_df_contract_bh = "";
Integer cust_id = new Integer(0);
List attachmentList = new ArrayList();

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = null;

if (request.getMethod().equals("POST")){
	vo_ben = new BenifitorVO();

	vo_ben.setSerial_no(serial_no);
	vo_ben.setCheck_flag(check_flag);
	vo_ben.setInput_man(input_operatorCode);

	benifitor.check1(vo_ben);
	bSuccess = true;
}

if(serial_no.intValue()>0){
	vo_ben = new BenifitorVO();
	List rsList_ben = null;
	Map map_ben = null;

	vo_ben.setSerial_no(serial_no);
	vo_ben.setBook_code(input_bookCode);

	rsList_ben = benifitor.load(vo_ben);
	if(rsList_ben.size()>0){
		map_ben = (Map)rsList_ben.get(0);
	}

	//获得收益账户变更附件
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(12));
	attachment_vo.setDf_serial_no(serial_no);

	attachmentList = attachmentLocal.load(attachment_vo);

	if(map_ben!=null){
		bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("BONUS_FLAG")),new Integer(0));
		temp_bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("TEMP_BONUS_FLAG")),new Integer(0));
		contract_sub_bh = Utility.trimNull(map_ben.get("CONTRACT_SUB_BH"));
		cust_no =  Utility.trimNull(map_ben.get("CUST_NO"));
		cust_name = Utility.trimNull(map_ben.get("CUST_NAME"));
		card_id = Utility.trimNull(map_ben.get("CARD_ID"));
		bank_id = Utility.trimNull(map_ben.get("BANK_ID"));
		bank_sub_name = Utility.trimNull(map_ben.get("BANK_SUB_NAME"));
		bank_acct = Utility.trimNull(map_ben.get("BANK_ACCT"));
		cust_acct_name = Utility.trimNull(map_ben.get("CUST_ACCT_NAME"));
		acct_chg_reason = Utility.trimNull(map_ben.get("ACCT_CHG_REASON"));
		temp_bank_acct = Utility.trimNull(map_ben.get("TEMP_BANK_ACCT"));
		temp_bank_id = Utility.trimNull(map_ben.get("TEMP_BANK_ID"));
		temp_bank_sub_name = Utility.trimNull(map_ben.get("TEMP_BANK_SUB_NAME"));
		temp_acct_name = Utility.trimNull(map_ben.get("TEMP_ACCT_NAME"));
		modi_time2 = Utility.parseInt(Utility.trimNull(map_ben.get("MODI_BANK_DATE")),new Integer(0));
		check_flag = Utility.parseInt(Utility.trimNull(map_ben.get("CHECK_FLAG")),new Integer(0));
		bank_province = Utility.trimNull(map_ben.get("TEMP_BANK_PROVINCE"));
		bank_city = Utility.trimNull(map_ben.get("TEMP_BANK_CITY"));
		jk_type_name = Utility.trimNull(map_ben.get("JK_TYPE_NAME"));
		temp_df_product_id = Utility.parseInt(Utility.trimNull(map_ben.get("TEMP_DF_PRODUCT_ID")),new Integer(0));
		temp_df_contract_bh = Utility.trimNull(map_ben.get("TEMP_DF_CONTRACT_BH"));
		cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),new Integer(0));
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.accuntChangeCheckEdit",clientLocale)%> </TITLE>
<!--受益人帐户变更审核-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function check(value){
	document.theform.check_flag.value=value;
	document.theform.action = "account_change_check_edit.jsp";

	if(value == 1){
		if(sl_check_pass())
			document.theform.submit();
	}
	if(value == 2){
		if(sl_check_unpass())
			document.theform.submit();
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name = "serial_no" value="<%=serial_no%>" />
<input type="hidden" id="check_flag" name="check_flag" value="<%=check_flag%>" />

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> </b></font><!--受益人信息-->
</div>
<br/>
<div align=left>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="contract_sub_bh" value="<%=contract_sub_bh%>"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.custNO",clientLocale)%> :</td><!--受益人编号-->
		<td><input readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this);" name="cust_no" size="20" value="<%=cust_no%>"></td>
		<td align="right"><%=LocalUtilis.language("class.custName2",clientLocale)%> :</td><!--受益人名称-->
		<td>
		     <input readonly class="edline" name="cust_name" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;
		</td>
	</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
			<td cospan="3"><input readonly class="edline" name="card_code" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=card_id%>"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.fromBankName",clientLocale)%> :</td><!--原受益付款银行-->
			<td colspan="3">
				<input readonly class="edline" name="hbank_name" size="25" onkeydown="javascript:nextKeyPress(this);" value="<%=Argument.getBankName(bank_id)%>">
				<input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="hbank_sub_name" size="30" value="<%=bank_sub_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
			<td colspan="3"><input readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this)" name="hbank_acct" onkeyup="javascript:showAcctNum(this.value)" size="25" value="<%=bank_acct%>"><span id="bank_acct_num" class="span"></span></td>
		</tr>
		<tr>
		   <td align="right"><%=LocalUtilis.language("class.custAcctName",clientLocale)%> :</td><!--原受益人银行帐户名称-->
		   <td><input readonly class="edline"  name="cust_acct" type="text" onkeydown="javascript:nextKeyPress(this)" size=25 value=<%=cust_acct_name%>></td>
		   <td align="right"><%=LocalUtilis.language("class.fromBonusFlag",clientLocale)%> :</td><!--原收益分配方式-->
		   <td>
				<select size="1" name="hbonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
		   </td>
		</tr>

		<tr>
			<td colspan="4">
			<br/>
			</td>
		</tr>

		<tr>
			<td align="right"><%=LocalUtilis.language("class.bankSubName",clientLocale)%> :</td><!--受益付款银行-->
			<td colspan="3"><input readonly class="edline" name="bank_name" size="25" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getBankName(temp_bank_id))%>">
			<input type="hidden" name="bank_id" size="20" value="<%=Utility.trimNull(bank_id)%>">
			<input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="30" value="<%=Utility.trimNull(temp_bank_sub_name)%>">
			</td>
		</tr>
		<tr>
			<td align="right">开户行所在地 :</td><!--开户行所在地-->
			<td colspan="3">
				<select size="1" disabled name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
				</select>	
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city" disabled>
						<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
					</select>
				</span><!-- 省级行政区域过滤出来的相关区域 -->
			</td>
		</tr>
		<tr>
			<td align="right" ><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
			<td ><input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="bank_acct" size="25" value="<%=temp_bank_acct%>"></td>
		    <td align="right" ><%=LocalUtilis.language("class.tempAcctName",clientLocale)%> :</td><!--受益人银行帐户名称-->
		    <td ><input  name="cust_acct_name" readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this)" size=25 value="<%=Utility.trimNull(temp_acct_name)%>" /></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--收益分配方式-->
			<td>
				<select size="1" disabled name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(temp_bonus_flag)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">受益兑付方式:</td>
			<td colspan="3">
				<input readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this)" size=25 value="<%=jk_type_name%>">
			</td>
		</tr>
		<tr>
			<td align="right">转入受益信托产品合同:</td>
			<td>
				<input readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this)" size=65 value=<%=Argument.getProductName(temp_df_product_id) + "-" + temp_df_contract_bh%>>
			</td>
			 <td align="right"><%=LocalUtilis.language("menu.inputDate",clientLocale)%> : </td><!--修改日期-->
			 <td align="left">
				<INPUT TYPE="text" readonly class="edline" NAME="modi_time_picker" class=selecttext  value="<%=Format.formatDateLine(modi_time2)%>">
			</td>
		</tr>
		<tr>
			<td valign="top" align="right"><%=LocalUtilis.language("class.acctChgReason",clientLocale)%> </td><!--修改原因-->
			<td colspan="3"><textarea readonly="readonly" style="border-color: black;" rows="4" name="reason" cols="60" ><%=acct_chg_reason%></textarea></td>
		</tr>
		<tr id="reader1">
          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>附件:<%}%></td>
            <td colspan="3">
			<%
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
            while(attachment_it.hasNext()){
            	attachment_map = (HashMap)attachment_it.next();
            %>
            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
            	<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
            	</div><br>
			<%}	%>
            </td>
         </tr>
	</table>
</div>

<div align="right" style="margin-top:5px;margin-right:5px">
	<button type="button"  class="xpbutton4" accessKey=s onclick="javascript:check(1);"><%=LocalUtilis.language("message.pass",clientLocale)%> (<u>S</u>)</button><!--审核通过-->
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton4" accessKey=c onclick="javascript:check(2);"><%=LocalUtilis.language("message.notPass2",clientLocale)%> (<u>C</u>)</button><!--审核不通过-->
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>B</u>)</button>
     &nbsp;&nbsp;&nbsp;<!--取消-->
</div>

</form>
</BODY>
</HTML>
<%
benifitor.remove();
%>
