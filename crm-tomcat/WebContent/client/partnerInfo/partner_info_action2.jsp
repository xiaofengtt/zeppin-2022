<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(1));//1.新增 2.修改
Integer partn_id =  Utility.parseInt(Utility.trimNull(request.getParameter("partn_id")),new Integer(0));
Integer showFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(1));

//渠道\媒体
Integer q_partn_type2_flag = Utility.parseInt(Utility.trimNull(request.getParameter("q_partn_type2_flag")),new Integer(2));
String q_partn_type2_name = "";

if(q_partn_type2_flag.intValue()==1){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.channels",clientLocale);//渠道
}
else if(q_partn_type2_flag.intValue()==2){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.media",clientLocale);//媒体
}

//辅助变量
boolean bSuccess = false;

//声明字段
String partn_name = Utility.trimNull(request.getParameter("partn_name"));
String card_type =Utility.trimNull(request.getParameter("card_type"));
String card_id =Utility.trimNull(request.getParameter("card_id"));
String voc_type = Utility.trimNull(request.getParameter("voc_type"));
String legal_man = Utility.trimNull(request.getParameter("legal_man"));
String contract_man = Utility.trimNull(request.getParameter("contract_man"));
String summary = Utility.trimNull(request.getParameter("summary"));
String touch_type = Utility.trimNull(request.getParameter("touch_type"));
String o_tel = Utility.trimNull(request.getParameter("o_tel"));
String h_tel = Utility.trimNull(request.getParameter("h_tel"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String bp = Utility.trimNull(request.getParameter("bp"));
String fax = Utility.trimNull(request.getParameter("fax"));
String e_mail = Utility.trimNull(request.getParameter("e_mail"));
String post_code = Utility.trimNull(request.getParameter("post_code"));
String post_address = Utility.trimNull(request.getParameter("post_address"));
String report_type = Utility.trimNull(request.getParameter("report_type"));

//获得对象
PartnerLocal partnerLocal = EJBFactory.getPartner();
PartnerVO vo = null;


//设置参数
if (request.getMethod().equals("POST")) {
	vo = new PartnerVO();
		
	vo.setPartn_name(partn_name);
	vo.setCard_type(card_type);
	vo.setCard_id(card_id);
	vo.setVoc_type(voc_type);
	vo.setLegal_man(legal_man);
	vo.setContract_man(contract_man);
	vo.setSummary(summary);
	vo.setTouch_type(touch_type);
	vo.setO_tel(o_tel);
	vo.setH_tel(h_tel);
	vo.setMobile(mobile);
	vo.setBp(bp);
	vo.setFax(fax);
	vo.setE_mail(e_mail);
	vo.setPost_address(post_address);
	vo.setPost_code(post_code);
	vo.setReport_type(report_type);	
	vo.setInput_man(input_operatorCode);
	vo.setPartn_type(new Integer(2));
	vo.setPartn_type2_flag(q_partn_type2_flag);
	
	if(actionFlag.intValue()==1){
		partnerLocal.append(vo);
		bSuccess = true;
	}else if(actionFlag.intValue()==2){
		vo.setPartn_id(partn_id);
		partnerLocal.modi(vo);
		
		bSuccess = true;
	}
}

if(partn_id.intValue()>0){
	vo = new PartnerVO();
	
	vo.setPartn_id(partn_id);
	List modiList = partnerLocal.query(vo);
	Map modiMap = null; 
	
	if(modiList.size()>0){
		modiMap = (Map)modiList.get(0);
	}
	
	if(modiMap!=null){
		partn_name = Utility.trimNull(modiMap.get("PARTN_NAME"));
		card_type =Utility.trimNull(modiMap.get("CARD_TYPE"));
		card_id =Utility.trimNull(modiMap.get("CARD_ID"));
		legal_man = Utility.trimNull(modiMap.get("LEGAL_MAN"));
		voc_type = Utility.trimNull(modiMap.get("VOC_TYPE"));
		contract_man = Utility.trimNull(modiMap.get("CONTACT_MAN"));
		summary =Utility.trimNull(modiMap.get("SUMMARY"));
		touch_type = Utility.trimNull(modiMap.get("TOUCH_TYPE"));
		o_tel =Utility.trimNull(modiMap.get("O_TEL"));
		h_tel =Utility.trimNull(modiMap.get("H_TEL"));
		mobile = Utility.trimNull(modiMap.get("MOBILE"));
		bp = Utility.trimNull(modiMap.get("BP"));
		fax = Utility.trimNull(modiMap.get("FAX"));
		e_mail = Utility.trimNull(modiMap.get("E_MAIL"));
		post_code =Utility.trimNull(modiMap.get("POST_CODE"));
		post_address =Utility.trimNull(modiMap.get("POST_ADDRESS"));
		report_type = Utility.trimNull(modiMap.get("REPORT_TYPE"));
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.OrgMediChannInfoSet",clientLocale)%> </TITLE><!--机构媒体渠道信息设置-->
<BASE TARGET="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
function show(parm){
	document.getElementById("showFlag").value = parm;
   for(i=1;i<3;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 } 
	}
}

/*启动加载*/	
window.onload = function(){
	show('<%=showFlag%>');
	var v_bSuccess = document.getElementById("bSuccess").value;
	var actionFlag = document.getElementById("actionFlag").value;
	var q_partn_type2_flag = document.getElementById("q_partn_type2_flag").value;
		
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");		//保存成功
		CancelAction();
	}
}

/*取消*/
function CancelAction(){
	//var q_partn_type2_flag = document.getElementById("q_partn_type2_flag").value;

	//disableAllBtn(true);
	//if(q_partn_type2_flag==1){
	//	window.location.href = "channel_info_list.jsp"; 
	//}
	//else if(q_partn_type2_flag==2){
	//	window.location.href = "media_info_list.jsp"; 
	//}
	
	window.close();
}

/*验证数据*/
function validateForm(form){
	var saveFlag = true;
	var showFlag = document.getElementById("showFlag").value;
	var i ;
	
	for(folderNo=1;saveFlag&&folderNo<3;folderNo++) {  
		i = folderNo;
		show(i);
		saveFlag = checkfolder(folderNo);	

	}
	
	return saveFlag;
}

function checkfolder(folder_no){
	form = document.theform;
	
	if(folder_no ==1){
		//if(!sl_checkChoice(form.zyhy_type, "行业类别"))		return false;
		//if(!sl_checkChoice(form.card_type, "证件类型"))		return false;	
		if(!sl_check(form.partn_name, "<%=q_partn_type2_name%><%=LocalUtilis.language("class.name",clientLocale)%> ", 100, 1))	return false;//名称
		if((form.card_type.value!=0)&&(!sl_check(form.card_id, '<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ', 40, 1)))		return false;//证件号码
	}
	else if(folder_no ==2){
			if(!sl_checkChoice(form.touch_type, '<%=LocalUtilis.language("class.serviceWay",clientLocale)%> '))	return false;//联系方式
			if(!sl_checkNum(form.post_code, '<%=LocalUtilis.language("class.postcode",clientLocale)%> ', 6, 0))	return false;//邮政编码
			if(form.e_mail.value.length!=0){
				if(!isEmail(form.e_mail,"Email"))	return false;
			}
			
			if(form.touch_type.value=="110901"){	
				<%if(user_id.intValue()==1){%>	
					if(!sl_checkNum(form.o_tel, '<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ', 11, 1))	return false;//公司电话
					if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1))	return false;//手机号码
				<%}
				else{%>
					if(form.h_tel.value.length==0&&form.o_tel.value.length==0&&form.mobile.value.length==0&&form.bp.value.length==0){
						sl_alert('<%=LocalUtilis.language("message.telMoreThanOne",clientLocale)%> ');//请至少填一个联系电话
						return false;
					}
					
					if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;//手机号码
					if(!sl_checkNum(form.bp, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;	//手机号码
				<%}%>	
			}
			
			if(form.touch_type.value=="110902"){
				if(!sl_check(form.post_address, '<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ', 60, 1))	return false;	//邮寄地址
				if(!sl_check(form.post_code, '<%=LocalUtilis.language("class.postcode",clientLocale)%> ', 60, 1))	return false;	  //邮政编码
			}
			
			if(form.touch_type.value=="110903"){
				if(!sl_check(form.e_mail, "Email", 30, 1))	return false;		
			}
			
			if(form.touch_type.value=="110904"){
				if(!sl_check(form.fax, '<%=LocalUtilis.language("class.fax",clientLocale)%> ', 60, 1))	return false;//传真
			}
			
			if(form.touch_type.value=="110905"){
				if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1))	return false;//手机号码
			}
	}
	
	return true;
}

function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()&&sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//保存输入信息
				document.getElementsByName('theform')[0].action = "partner_info_action2.jsp";
				document.getElementsByName('theform')[0].submit();
		}	
}

function showAcctNum(value)
{		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="partn_type"	value="2"> 
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="actionFlag"	id="actionFlag" value="<%= actionFlag%>" />
<input type="hidden" name="showFlag"	id="showFlag" value="<%= showFlag%>" />
<input type="hidden" name="partn_id"	id="partn_id" value="<%= partn_id%>" />
<input type="hidden" name="q_partn_type2_flag"	id="q_partn_type2_flag" value="<%= q_partn_type2_flag%>" />

<div class="page-title page-title-noborder">
	<font color="#215dc6"><b><%=q_partn_type2_name%><%=LocalUtilis.language("message.organizationInfo",clientLocale)%>  </b></font><!--机构信息-->
</div>	
<br>

<div class="page-title-select" style="margin-right:20px;margin-top:5px;">
	<font  size="2" face="宋体"><%=q_partn_type2_name%><%=LocalUtilis.language("class.organizationName",clientLocale)%> ：</font><!--机构名称-->
	<input type="text" name="partn_name"  value="<%=partn_name%>"/>
</div>
<div align="left">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
				<TBODY>
					<TR class="tr-tabs">							
						<TD vAlign=top>&nbsp;</TD>					
						<!--基本信息-->
                        <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
						<!--联系方式-->
                        <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> </TD>						
					</TR>
			</TBODY>
	</TABLE>
</div>
<br>

<div  id="r1" align="left"  style="display:none;">
<div align="left">
		<table border="0" width="100%" cellspacing="0" cellpadding="0" class="table-popup">	
			<tr>
				<td><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardType",clientLocale)%> ：</font></td><!--证件类型-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="card_type" style="WIDTH: 120px">
						<%=Argument.getCardTypeJgOptions2(card_type)%>
					</select>
				</td>	
				<td align="right"><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ：</font></td><!--证件号码-->
				<td><INPUT name="card_id" onkeyup="javascript:showAcctNum(this.value)" 
						   maxlength="40" value='<%=card_id%>' size="25">
					<span id="bank_acct_num" class="span"></span>
				</td>
			</tr>	
			
			<tr>
				<%if(q_partn_type2_flag.intValue()==1){%>
					<td><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.industry",clientLocale)%> ：</font></td><!--所属行业-->
					<td>			
						<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type" style="WIDTH: 225px" >
							<%=Argument.getJghyOptions2(voc_type)%>
						</select>			
					</td>
				<%}
				else {%>
				<td ><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.reportType",clientLocale)%> ：</font> </td><!--报道形式-->
				<td  >
					<select name="report_type" id="report_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
						<%=Argument.getDictParamOptions(2011,report_type)%>
					</select>
				</td>	
				<%}%>
				<td align="right"><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.corporate",clientLocale)%> ：</font></td><!--法人姓名-->
				<td ><INPUT name="legal_man" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="40" value='<%=legal_man%>'></td>				
			</tr>	
			
			<TR>
					<TD valign="top"><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.summary",clientLocale)%> ：</font></TD><!--备注信息-->
					<TD colspan="3">
						<TEXTAREA rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"><%= summary%></TEXTAREA>
					</TD>
			</TR>
		</table>
	</div>
<br/>

</div>

<div id="r2" align="left" style="display:none;">
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="table-popup">
			<tr>
				<td>&nbsp;&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.contact",clientLocale)%> ：</font></td><!--联系人-->
				<td><INPUT name="contract_man" onkeydown="javascript:nextKeyPress(this)" size="20" value='<%=Utility.trimNull(contract_man) %>'></td>
		
				<td><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("login.telephone",clientLocale)%> ：</font></td><!--联系电话-->
				<td><input name="h_tel" onkeydown="javascript:nextKeyPress(this)"  maxlength="20" size="20" value="<%= h_tel%>" /></td>		
			</tr>
	
			<tr>
				<td width="15%"><font size="2" face="宋体">&nbsp;&nbsp;*<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!--联系方式-->
				<td width="*">
					<select size="1" name="touch_type" style="WIDTH: 120px" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Argument.getTouchTypeOptions(touch_type)%>
					</select>
				</td>
				
				<td width="15%"><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ：</font></td><!--公司电话-->
				<td width="*">	
					<INPUT name="o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20" value="<%= o_tel%>" />
				</td>
			</tr>
						
			<tr>
				<td>&nbsp;&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> ：</font></td><!--手机-->
				<td>	
					<input name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="20"	value="<%= mobile%>" />
				</td>
				
				<td><font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2：</font></td><!--手机-->
				<td>
					<INPUT name="bp" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%=bp%>" />
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.fax",clientLocale)%> ：</font></td><!--传真-->
				<td>
						<input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="20" value="<%= fax%>" />
				</td>
				<td>&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;E-MAIL：</font></td>
				<td>
						<input name="e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%= e_mail%>" />
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.postcode",clientLocale)%> ：</font></td><!--邮政编码-->
				<td colspan="3">
					<input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%= post_code%>">
				</td>
			</tr>
			
			<tr>
				<td>&nbsp;&nbsp;<font size="2" face="宋体">&nbsp;&nbsp;<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ：</font></td><!--邮寄地址-->
				<td colspan="3">
					<input name="post_address" onkeydown="javascript:nextKeyPress(this)" size="50"  value="<%= post_address%>">
				</td>
			</tr>			
			
		</table>
		<br/>
</div>

<div align="right">
	<button type="button"  class="xpbutton3" accesskey="s" id="btnSave" name="btnSave"
		onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<U>S</U>)</BUTTON><!--保存-->
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn"
		onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<U>B</U>)</BUTTON><!--关闭-->
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<br>
<% partnerLocal.remove();%>

</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>