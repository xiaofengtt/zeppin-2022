<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
 

//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);//帐套暂时设置

String old_bank_name="";//    --修改前银行
String old_bank_sub_name="";//    --修改前支行名称
String old_bank_acct="";     //   --修改前银行帐号
String old_acct_name="";     // --修改前帐户名称
String new_bank_name="";       // 修改后银行
String new_bank_sub_name=""; //  修改后支行名称
String new_bank_acct="";     //  修改后银行帐号
String new_acct_name="";     //  修改后帐户名称
String  modi_man_name ="";         // -修改人
String  modi_time ="";       // 修改时间
String  old_bonus_flag_name ="";      //  修改前默认分红方式
BigDecimal  old_bonus_rate =new BigDecimal(0);        //  修改前转份额比例
String  new_bonus_flag_name ="";      // 修改后默认分红方式
BigDecimal   new_bonus_rate=new BigDecimal(0);       // 修改后转份额比例

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = new BenifitorVO();

List rsList_ben = null;
Map map_ben = null;

if(serial_no.intValue()>0){

	vo_ben.setSerial_no(serial_no);
	vo_ben.setInput_man(input_operatorCode);

	rsList_ben = benifitor.loadBenacct(vo_ben);
	
	
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.accountChangeEdit",clientLocale)%> </TITLE>
<!--受益人账号信息-->
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
window.onload = function(){
	tocheck();
}

		function tocheck(){
				<%if(rsList_ben == null || rsList_ben.size() == 0){	%>
						alert("受益人客户账号未变更！");
						window.close();
				<%}%>
		}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="account_ben.jsp" onsubmit=" "  >
<!--修改成功标志-->
 
<input type="hidden" id="serial_no" name = "serial_no" value="<%=serial_no%>" />

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b>变更明细</b></font>
	<hr noshade color="#808080" size="1">
</div><!--变更明细-->
<%
if(rsList_ben != null && rsList_ben.size() != 0){
		
		for(int i=0; i<rsList_ben.size(); i++){
			map_ben = (Map)rsList_ben.get(i);
				old_bank_name = Utility.trimNull(map_ben.get("OLD_BANK_NAME"),"");
				old_bank_sub_name =Utility.trimNull(map_ben.get("OLD_BANK_SUB_NAME"),"");
				old_bank_acct = Utility.trimNull(map_ben.get("OLD_BANK_ACCT"),"");
				old_acct_name= Utility.trimNull(map_ben.get("OLD_ACCT_NAME"),"");
				new_bank_name= Utility.trimNull(map_ben.get("NEW_BANK_NAME"),"");
				new_bank_sub_name= Utility.trimNull(map_ben.get("NEW_BANK_SUB_NAME"),"");
				new_bank_acct= Utility.trimNull(map_ben.get("NEW_BANK_ACCT"),"");
				new_acct_name= Utility.trimNull(map_ben.get("NEW_ACCT_NAME"),"");
				modi_man_name=Utility.trimNull(map_ben.get("MODI_MAN_NAME"),"");
				modi_time=Utility.trimNull(map_ben.get("MODI_TIME"),"");
				old_bonus_flag_name =Utility.trimNull(map_ben.get("OLD_BONUS_FLAG_NAME"),"");
	 			old_bonus_rate =Utility.parseDecimal(Utility.trimNull(map_ben.get("OLD_BONUS_RATE")),new BigDecimal(0),2,"1");
	 			new_bonus_flag_name =Utility.trimNull(map_ben.get("NEW_BONUS_FLAG_NAME"),"");
				new_bonus_rate=Utility.parseDecimal(Utility.trimNull(map_ben.get("NEW_BONUS_RATE")),new BigDecimal(0),2,"1");
 %>
<div><font color="#215dc6">第<%=(i+1) %>次变更</font></div>
	<table border="0" width="100%" cellspacing="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
		<tr>
			<td align="right">修改时间:</td> 
			<td>
			     <input readonly class="edline" name="modi_time"  value="<%=modi_time%>" size="25" onkeydown="javascript:nextKeyPress(this);">
			</td>
			<td align="right">修改人 :</td> 
			<td>
				<input readonly class="edline" name="modi_man_name" value="<%=modi_man_name%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
		</tr>
		<tr>
			<td align="right">银行 :</td> 
			<td>
				<input readonly class="edline" name="old_bank_name" value="<%=old_bank_name%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
				<input readonly class="edline" name="new_bank_name" value="<%=new_bank_name%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
			
		</tr>
		<tr>
			<td align="right">银行帐号 :</td> 
			<td>
				<input readonly class="edline" name="old_bank_acct" value="<%=old_bank_acct%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
				<input readonly class="edline" name="new_bank_acct" value="<%=new_bank_acct%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
			
		</tr>
		<tr>
			<td align="right">支行:</td> 
			<td>
			     <input readonly class="edline" name="old_bank_sub_name"  value="<%=old_bank_sub_name%>" size="20" onkeydown="javascript:nextKeyPress(this);">
			</td>
			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
			     <input readonly class="edline" name="new_bank_sub_name"  value="<%=new_bank_sub_name%>" size="20" onkeydown="javascript:nextKeyPress(this);">
			</td>
		</tr>
		<tr>
			<td align="right">帐户名称:</td> 
			<td>
			     <input readonly class="edline" name="old_acct_name"  value="<%=old_acct_name%>" size="35" onkeydown="javascript:nextKeyPress(this);">
			</td>
			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
			     <input readonly class="edline" name="new_acct_name"  value="<%=new_acct_name%>" size="35" onkeydown="javascript:nextKeyPress(this);"> 
			</td>
		</tr>
	
		
		<tr>
			<td align="right">默认分红方式 :</td> 
			<td>
				<input readonly class="edline" name="old_bonus_flag_name" value="<%=old_bonus_flag_name%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>

			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
				<input readonly class="edline" name="new_bonus_flag_name" value="<%=new_bonus_flag_name%>" size="20" onkeydown="javascript:nextKeyPress(this);"   >
			</td>
			
		</tr>
		<tr>
			<td align="right">转份额比例 :</td> 
			<td>
			     <input readonly class="edline" name="old_bonus_rate"  value="<%=old_bonus_rate%>" size="20" onkeydown="javascript:nextKeyPress(this);">
			</td>
			<td>======>>&nbsp;&nbsp;&nbsp;</td> 
			<td>
			     <input readonly class="edline" name="new_bonus_rate"  value="<%=new_bonus_rate%>" size="20" onkeydown="javascript:nextKeyPress(this);"> 
			</td>
		</tr>
	</table>
<BR>
<%}} %>
<div align="right" style="margin-top:5px;margin-right:5px">
	<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)
	</button><!--关闭-->
</div>
</form>
<%
benifitor.remove();

%>

</BODY>
</HTML>