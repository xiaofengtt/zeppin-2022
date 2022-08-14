<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.webcall.*,java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ include file="/includes/operator.inc" %>

<%
String webcallId = Utility.trimNull(request.getParameter("webcallId")); 

String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(1));
String card_type = Utility.trimNull(request.getParameter("card_type"));
String card_type2 = Utility.trimNull(request.getParameter("card_type2"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String zyhy_type = Utility.trimNull(request.getParameter("zyhy_type"));
String zyhy_type2 = Utility.trimNull(request.getParameter("zyhy_type2"));
Integer sex = Utility.parseInt(Utility.trimNull(request.getParameter("sex")),new Integer(0));
Integer age = Utility.parseInt(Utility.trimNull(request.getParameter("age")),new Integer(0));
String birthday = Utility.trimNull(request.getParameter("birthday"));
String touch_type = Utility.trimNull(request.getParameter("touch_type"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String e_mail = Utility.trimNull(request.getParameter("e_mail")); 
String post_address = Utility.trimNull(request.getParameter("post_address")); 
String post_code = Utility.trimNull(request.getParameter("post_code")); 

// 获得对象
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
TCrmWebCallLocal webcall_local = EJBFactory.getTCrmWebCall();
TCrmWebCallVO webcall_vo = new TCrmWebCallVO();

Integer new_cust_id = new Integer(0);
int webcallFlag = 0;//访客ID是否需要绑定 标志

if (request.getMethod().equals("POST")){	
	//客户标识
	cust_vo.setBook_code(new Integer(1));
	cust_vo.setIs_deal(new Integer(2));//潜在客户
	cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
	cust_vo.setInput_man(input_operator.getOp_code());
	//客户基本信息
	cust_vo.setCust_name(cust_name);
	cust_vo.setCust_type(cust_type);
	if(cust_type.intValue()==1){
		cust_vo.setCard_type(card_type);
		cust_vo.setVoc_type(zyhy_type);//职业
	}
	else if(cust_type.intValue()==2){
		cust_vo.setCard_type(card_type2);
		cust_vo.setVoc_type(zyhy_type2);//行业
	}
	cust_vo.setCard_id(card_id);
	cust_vo.setSex(sex);
	cust_vo.setAge(age);
	cust_vo.setBirthday(Utility.parseInt(birthday, new Integer(0)));
	//联系方式
	cust_vo.setTouch_type(touch_type);
	cust_vo.setMobile(mobile);
	cust_vo.setE_mail(e_mail);
	cust_vo.setPost_address(post_address);
	cust_vo.setPost_code(post_code);
	cust_vo.setCust_source("111002");
	cust_vo.setService_man(input_operatorCode);
	
	new_cust_id =  cust_local.append2(cust_vo);
	
	webcall_vo.setCust_id(new_cust_id);
	webcall_vo.setWebcallId(webcallId);
	webcall_vo.setInput_man(input_operator.getOp_code());

	//webcall_local.appendCrmWebcall(webcall_vo);
}

//查看访客ID是否需要绑定
webcall_vo = new TCrmWebCallVO();
webcall_vo.setWebcallId(webcallId);

List rsList = webcall_local.listCrmWebcall(webcall_vo);
if(rsList!=null&&rsList.size()>0){
	webcallFlag = 1 ;
}
%>

<HTML>
<HEAD>
<TITLE>快速新建</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/styles/common.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/webcall/webcall.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_zh_CN.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language=javascript>
/*返回*/
function backAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_1.jsp?webcallId=<%=webcallId%>";
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
/*显示设置*/
function showAction(cust_type){
	if(cust_type==1){
		document.getElementById("tr_birthday").style.display = "";
		document.getElementById("tr_age").style.display = "";
		document.getElementById("tr_sex").style.display = "";
		document.getElementById("tr_zyhy_type").style.display = "";
		document.getElementById("card_type").style.display = "";
		document.getElementById("tr_zyhy_type2").style.display = "none";				
		document.getElementById("card_type2").style.display = "none";
	}
	else if(cust_type==2){
		document.getElementById("tr_birthday").style.display = "none";
		document.getElementById("tr_age").style.display = "none";
		document.getElementById("tr_sex").style.display = "none";
		document.getElementById("tr_zyhy_type").style.display = "none";
		document.getElementById("card_type").style.display = "none";
		document.getElementById("tr_zyhy_type2").style.display = "";				
		document.getElementById("card_type2").style.display = "";	
	}
}
/*显示状态*/
function showState(info){
	if(info){
		document.getElementById("tr_state").style.display = "";	
		document.getElementById("span_state").innerText = info;	
	}
	else{
		document.getElementById("tr_state").style.display = "none";	
		document.getElementById("span_state").innerText = "";
	}
}
/*验证数据*/
function validateForm(form){
	//基本信息验证
	if(!sl_check(form.cust_name, "客户名称", 100, 1))	return false;//客户名称
	if(!sl_checkChoice(document.theform.cust_type,"客户类别")){return false;}//客户类别
	var cust_type = document.theform.cust_type.value;

	if((form.card_type.value!=0)&&(!sl_check(form.card_id, "证件号码", 40, 1))) return false;//证件号码
	if(form.card_type.value=="110801"){
		if(!sl_check(form.card_id, "证件号码", 18, 15))	return false;//证件号码
		if(!(form.card_id.value.length==15||form.card_id.value.length==18)){
			sl_alert("身份证号码必须为15或18位");//身份证号码必须为15或18位
			form.card_id.focus();
			return false;
		}
	}
	if((cust_type==1)&&(document.theform.birthday_picker.value!="")){
		if(!sl_checkDate(document.theform.birthday_picker,"出生日期"))return false;//出生日期
		syncDatePicker(form.birthday_picker, form.birthday);
	}
	if((form.age.value!="")&&(!sl_checkNum(form.age, "年龄", 3, 1)))	return false;	//年龄
	if((form.sex.value!="")&&(!sl_checkChoice(form.sex, "性别")))	return false;//性别
	//if((cust_type==1)&&!sl_checkChoice(form.zyhy_type, "职业")) return false;//职业
	//if((cust_type==2)&&!sl_checkChoice(form.zyhy_type2, "行业类别")) return false;//行业类别
	//联系方式
	if(!sl_checkChoice(form.touch_type, "联系方式"))	return false;//联系方式
	if(form.touch_type.value=="110901"){
		<%if(user_id.intValue()==1){%>	
			if(!sl_checkNum(form.mobile, "手机号码 ", 11, 1))	return false;//手机号码
		<%}
		else{%>
			if(form.mobile.value.length==0){
				sl_alert("请至少填一个联系电话");//请至少填一个联系电话
				return false;
			}
			
			if(!sl_checkNum(form.mobile, "手机号码 ", 11, 0))	return false;//手机号码
		<%}%>
	}
		
	if(form.touch_type.value=="110902"){
		if(!sl_check(form.post_address, "邮寄地址", 60, 1))	return false;	//邮寄地址
		if(!sl_check(form.post_code, "邮政编码", 6, 1))	return false;	  //邮政编码
	}
	else{
		if(!sl_checkNum(form.post_code, "邮政编码", 6, 0))	return false;//邮政编码
	}
	if(form.touch_type.value=="110903"||form.e_mail.value.length!=0){
		if(!sl_check(form.e_mail, "Email", 30, 1))	return false;		
	}

	if(form.touch_type.value=="110905"){
		if(!sl_checkNum(form.mobile, "手机号码", 11, 1))	return false;//手机号码
	}
	
	return sl_check_update();
}
/*保存*/
function saveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].action="customer_info_webcall_add.jsp";
		document.getElementsByName('theform')[0].submit();
	}	
} 
/*页面初始化*/
window.onload = function(){
	var new_cust_id = document.getElementById("new_cust_id").value;

	showAction(document.theform.cust_type.value);
	if(new_cust_id!=0){
		showState("保存成功!");
		showCustInfo(new_cust_id);
	}
	else{
		showState();
	}
}
//根据出生日期计算年龄（Tab或回车）
function CalAge(){
	if (event.keyCode == 13||event.keyCode == 9){
		if(document.theform.cust_type.value==1){
			if(document.theform.birthday_picker.value!=""){
				if(!sl_checkDate(document.theform.birthday_picker,"出生日期"))	return false;//出生日期
				var current_date = new Date();					
				document.theform.age.value = current_date.getYear() - document.theform.birthday_picker.value.substring(0,4);			
			}
		}	
	}
}
//失去焦点时计算年龄
function CalAge2(){
	if(document.theform.cust_type.value==1){
		if(document.theform.birthday_picker.value!=""){
			if(!sl_checkDate(document.theform.birthday_picker,"<%=LocalUtilis.language("class.birthday",clientLocale)%> ")){//出生日期
				document.theform.birthday_picker.value="";
				return false;
			}	
			var current_date = new Date();					
			document.theform.age.value = current_date.getYear() - document.theform.birthday_picker.value.substring(0,4);			
		}
	}	
}
/*返回*/
function showCustInfo(cust_id){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_detail.jsp?webcallId=<%=webcallId%>&cust_id="+cust_id;
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
</script>
</HEAD>
<BODY class="BODY2" style="overflow-y: hidden;">
<form name="theform" method="post" action="customer_info_webcall_add.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="new_cust_id" name="new_cust_id" value="<%=new_cust_id%>">
<input type="hidden" id="webcallId" name="webcallId" value="<%=webcallId%>">
<div align="center"  class="topDiv">
	<font color="#208020" size="3">快速新建</font>&nbsp;&nbsp;<br>
	<%if(webcallFlag==0){%><font color="red">该访客无对应客户信息</font><%}%>
</div>

<div align="center">
	<table class="problemInfoTable" cellSpacing="0" cellPadding="2" width="100%" align="center">
		<tbody>
			<tr id="tr_state">
	          <td colspan="2" class="paramTitle">
				<span id="span_state" style="color:red"></span>
			  </td>	         
            </tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap><font color="red">客户名称</font></td>
				<td><input type="text" name= "cust_name" size="20" value="<%=cust_name%>"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>客户类别</td><!-- 客户类别 -->
				<td>
					<select name="cust_type" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:showAction(this.value);">
						<%=Argument.getCustTypeOptions(cust_type) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>证件类型</td><!--证件类型-->
				<td>
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" id="card_type" name="card_type" style="WIDTH: 130px">
					   <%=Argument.getCardTypeOptions2(card_type)%>
					</select>  
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" id="card_type2" name="card_type2" style="WIDTH: 130px">
					   <%=Argument.getCardTypeJgOptions2(card_type2)%>
					</select>  
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>证件号码</td>
				<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="card_id" size="20" value="<%=card_id%>"></td>
			</tr>
			<tr id="tr_birthday">
				<td class="paramTitle" width="60px" nowrap>出生日期 </td><!--出生日期-->
				<td>
					<INPUT TYPE="text" NAME="birthday_picker" class=selecttext onkeydown="javascript:CalAge();nextKeyPress(this);"  size="16"
						   onblur="javascript:CalAge2();"	value='<%=birthday=="" ? "":Format.formatDateLine(Utility.parseInt(birthday,new Integer(0)))%>'>
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.birthday_picker,theform.birthday_picker.value,this);"
						   onkeydown="javascript:CalAge();nextKeyPress(this)"> 
					<INPUT TYPE="hidden" NAME="birthday" value="" />
				</td>
			</tr>
			<tr id="tr_age">
				<td class="paramTitle" width="65px" nowrap>年龄</td><!--年龄-->
				<td><INPUT name="age" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="3" value="<%=age%>" /></td>
			</tr>
			<tr id="tr_sex">
				<td class="paramTitle" width="60px" nowrap>性别</td><!--性别-->
				<td><SELECT onkeydown="javascript:nextKeyPress(this)" name="sex" style="WIDTH: 130px">
						<%=Argument.getSexOptions(sex)%>
					</SELECT>
				</td>	
			</tr>
			<tr id="tr_zyhy_type">
				<td class="paramTitle" width="60px" nowrap>职业</td><!--职业-->
				<td><select onkeydown="javascript:nextKeyPress(this)" name="zyhy_type" style="WIDTH: 130px">
							<%=Argument.getGrzyOptions2(zyhy_type)%>
					</select>
				</td>	
			</tr>
			<tr id="tr_zyhy_type2">
				<td class="paramTitle" width="60px" nowrap > 行业 </td><!--行业-->
				<td><select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type2" style="WIDTH: 130px" >
						<%=Argument.getJghyOptions2(zyhy_type2)%>
					</select>			
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap >联系方式</td><!--联系方式-->
				<td >
					<select name="touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 130px">
						<%=Argument.getTouchTypeOptions(touch_type)%>
					</select>
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>手机</td><!--手机-->
				<td><input name="mobile" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=mobile%>" /></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap >Email</td>
				<td><INPUT name="e_mail" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=e_mail%>"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap >邮寄地址</td><!--邮寄地址-->
				<td><TEXTAREA rows="3" name="post_address" onkeydown="javascript:nextKeyPress(this)" cols="18"><%=post_address%></TEXTAREA></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap >邮政编码</td><!--邮政编码-->
				<td><input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=post_code%>"></td>
			</tr>	
			<tr>
                <td class="paramTitle" align="left">&nbsp;&nbsp;</td>
				<td class="paramTitle" align="right">	
				 <button class="xpbutton2"  id="btnSave" name="btnSave" onclick="javascript:saveAction();">保存&nbsp;</button>
				 &nbsp;&nbsp;
				 <button class="xpbutton2"  id="btnBack" name="btnBack" onclick="javascript:backAction();">返回&nbsp;</button>		
				</td>
            </tr>
		</tbody>
	</table>
</div>

<div class="bottomDiv">
	&nbsp;&nbsp;<font size="2">您好！<%=input_operator.getOp_name()%></font>
</div>
</form>

</BODY>
</HTML>