<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer modi_time2 = Utility.parseInt(request.getParameter("modi_time"),new Integer(Utility.getCurrentDate()));

//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);//帐套暂时设置

//受益人信息
Integer open_flag = new Integer(2);
Integer bonus_flag = new Integer(0);//原收益分配方式
Integer temp_bonus_flag  = new Integer(0);//现收益分配方式
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
String temp_bank_city = "",temp_bank_province = "";
Integer recommend_man = new Integer(0);
String jk_type = "";
String temp_jk_type = "";
Integer df_product_id = new Integer(0);
String df_contract_bh = "";
Integer cust_id = new Integer(0);
List attachmentList = new ArrayList();
String change_times="";

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = new BenifitorVO();

ProductLocal product = EJBFactory.getProduct();//产品
ProductVO p_vo = new ProductVO();

//执行添加操作
DocumentFile2 file = null;

if(request.getMethod().equals("POST")){
	file = new DocumentFile2(pageContext);
	try{
	file.parseRequest();
	}catch(Exception e){
		out.print(e);
		return;
	}
	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	product_id = Utility.parseInt(file.getParameter("product_id"),new Integer(0));
	modi_time2 = Utility.parseInt(file.getParameter("modi_time"),new Integer(Utility.getCurrentDate()));

	String r_bank_id = file.getParameter("bank_id");
	String r_bank_sub_name = file.getParameter("bank_sub_name");
	String r_bank_acct = file.getParameter("bank_acct");
	Integer r_bonus_flag = Utility.parseInt(file.getParameter("bonus_flag"),new Integer(1));
	String r_acct_chg_reason = file.getParameter("reason");
	String r_cust_acct_name = file.getParameter("cust_acct_name");
	bank_province = Utility.trimNull(file.getParameter("bank_province"));
	bank_city = Utility.trimNull(file.getParameter("bank_city"));
	jk_type = Utility.trimNull(file.getParameter("jk_type"));

	vo_ben.setSerial_no(serial_no);
	vo_ben.setInput_man(input_operatorCode);
	vo_ben.setBank_id(r_bank_id);
	vo_ben.setBank_sub_name(r_bank_sub_name);
	vo_ben.setBank_acct(r_bank_acct);
	vo_ben.setBonus_flag(r_bonus_flag);
	if (r_bonus_flag.intValue()==2){ //当转份额时，一定要指定转换比率，目前指定为1
		vo_ben.setBonus_rate(new BigDecimal(1));
	}else{
		vo_ben.setBonus_rate(new BigDecimal(0.00));
	}
	vo_ben.setModi_bank_date(modi_time2);
	vo_ben.setAcct_chg_reason(r_acct_chg_reason);
	vo_ben.setCust_acct_name(r_cust_acct_name);
	vo_ben.setBank_province(bank_province);
	vo_ben.setBank_city(bank_city);
	vo_ben.setJk_type(jk_type);
	if(jk_type.equals("111499")){
		String df_product_contract_value = Utility.trimNull(file.getParameter("df_product_contract"));
		vo_ben.setDf_product_id(Utility.parseInt(Utility.splitString(df_product_contract_value,"$")[0],new Integer(0)));
		vo_ben.setDf_contract_bh(Utility.splitString(df_product_contract_value,"$")[1]);
	}
	benifitor.save1(vo_ben);
	bSuccess = true;

	if(file.uploadAttchment(new Integer(12),serial_no,"受益账户变更",null,null,input_operatorCode))
		bSuccess = true;
}
else{
	//设置受益人信息
	if(serial_no.intValue()>0){
		List rsList_ben = null;
		Map map_ben = null;

		vo_ben.setSerial_no(serial_no);
		vo_ben.setModi_bank_date(modi_time2);
		vo_ben.setBook_code(input_bookCode);

		rsList_ben = benifitor.loadFromCRM(vo_ben);
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
			product_id = Utility.parseInt(Utility.trimNull(map_ben.get("PRODUCT_ID")),new Integer(0));
			contract_sub_bh = Utility.trimNull(map_ben.get("CONTRACT_SUB_BH"));
			cust_no =  Utility.trimNull(map_ben.get("CUST_NO"));
			cust_name = Utility.trimNull(map_ben.get("CUST_NAME"));
			card_id = Utility.trimNull(map_ben.get("CARD_ID"));
			bank_id = Utility.trimNull(map_ben.get("BANK_ID"));
			bank_sub_name = Utility.trimNull(map_ben.get("BANK_SUB_NAME"));
			bank_acct = Utility.trimNull(map_ben.get("BANK_ACCT"));
			cust_acct_name = Utility.trimNull(map_ben.get("CUST_ACCT_NAME"));
			acct_chg_reason = Utility.trimNull(map_ben.get("ACCT_CHG_REASON"));
			temp_bank_acct = Utility.trimNull(map_ben.get("TEMP_BANK_ACCT"),bank_acct);
			temp_bank_id = Utility.trimNull(map_ben.get("TEMP_BANK_ID"),bank_id);
			temp_bank_sub_name = Utility.trimNull(map_ben.get("TEMP_BANK_SUB_NAME"),bank_sub_name);
			temp_acct_name = Utility.trimNull(map_ben.get("TEMP_ACCT_NAME"),cust_acct_name);
			temp_bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("TEMP_BONUS_FLAG")),bonus_flag);
			recommend_man = Utility.parseInt(Utility.trimNull(map_ben.get("RECOMMEND_MAN")),new Integer(0));
					
			bank_province = Utility.trimNull(map_ben.get("BANK_PROVINCE"));
			bank_city = Utility.trimNull(map_ben.get("BANK_CITY"));
			temp_bank_province = Utility.trimNull(map_ben.get("TEMP_BANK_PROVINCE"),bank_province);
			temp_bank_city = Utility.trimNull(map_ben.get("TEMP_BANK_CITY"),bank_city);
			jk_type = Utility.trimNull(map_ben.get("JK_TYPE"));
			temp_jk_type = Utility.trimNull(map_ben.get("TEMP_JK_TYPE"),jk_type);
			df_product_id = Utility.parseInt(Utility.trimNull(map_ben.get("DF_PRODUCT_ID")),new Integer(0));
			df_contract_bh = Utility.trimNull(map_ben.get("DF_CONTRACT_BH"));
			cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),new Integer(0));
			change_times=Utility.trimNull(map_ben.get("CHANGE_TIMES"));
		}
	}

	//产品信息显示
	if(product_id.intValue()>0){
		List rsList_product = null;
		Map map_product = null;

		//取单个产品值
		p_vo.setProduct_id(product_id);
		rsList_product = product.load(p_vo);
		map_product = (Map)rsList_product.get(0);

		if(map_product!=null){
			open_flag = Utility.parseInt(Utility.trimNull(map_product.get("OPEN_FLAG")),new Integer(0));
		}
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.accountChangeEdit",clientLocale)%> </TITLE>
<!--受益人账号信息-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="HideIfrm">

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
var n=1;

/*保存*/
function SaveAction(){
	if(document.getElementsByName("theform")[0].onsubmit()){
		document.getElementById("btnSave").disabled=true;
		document.getElementsByName("theform")[0].submit();
	}
}

/*验证数据*/
function validateForm(form){
	if(!sl_checkChoice(form.bank_id, "<%=LocalUtilis.language("class.bankName2",clientLocale)%> "))		return false;//付款银行
	if(!sl_check(form.bank_acct, "<%=LocalUtilis.language("class.bankAcct",clientLocale)%> ", 30, 0))		return false;//银行账号
	if(!sl_checkDate(document.theform.modi_time_picker,"<%=LocalUtilis.language("menu.inputDate",clientLocale)%> "))	return false; //修改日期
	syncDatePicker(document.theform.modi_time_picker, document.theform.modi_time);
	if(form.jk_type.value == "111499"){
		if(!sl_checkChoice(form.df_product_contract, "转入受益信托产品合同")) return false;
	}
	return sl_check_update();
}

/*显示位数*/
function showAcctNum(value){
	if (trim(value) == "")
		document.getElementById("bank_acct_num").innerText = "";
	else
		document.getElementById("bank_acct_num").innerText = "(" + showLength(value) + " 位)";
}

/*
 *添加附件
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow();
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='60'>"+"<button type=button class='xpbutton3' onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//移除
}


/*
 *删除附件
 */
function removeline(obj)
{
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？'))
	{

		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		if(obj!=null)
			obj.style.display="none";
	}
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(value,flag){
	if(value != "" && value.length > 0){
		utilityService.getGovRegional(9999, value, flag,backGovRegional);
	}
}

/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='bank_city' style='width: 150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

function showProductContract(){
	var value = document.theform.jk_type.value;
	if(value == "111499"){
		document.getElementById("tr_df_product_contract").style.display = "";
		document.getElementById("tr_df_product_contract_tishi").style.display = "";
	}else{
		document.getElementById("tr_df_product_contract").style.display = "none";
		document.getElementById("tr_df_product_contract_tishi").style.display = "none";
	}
}
function getBenifitor(serial_no){
	var url = 'account_ben.jsp?serial_no='+serial_no;
	
	if(showModalDialog(url, '', 'dialogWidth:650px;dialogHeight:300px;status:0;help:0') != null){
		sl_update_ok();
		
	}
}
//Description:  银行卡号Luhm校验

    //Luhm校验规则：16位银行卡号（19位通用）:
    
    // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
    // 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
    // 3.将加法和加上校验位能被 10 整除。
function luhmCheck(bankno){
	bankno = trimString(bankno);
	if (bankno.length < 16 || bankno.length > 19 || bankno.length<0) {
		alert("银行卡号长度必须在16到19之间");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	var num = /^\d*$/;  //全数字
	if (!num.exec(bankno)) {
		alert("银行卡号必须全为数字");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
	//alert("------1------");
	//开头6位
	var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
	if (strBin.indexOf(bankno.substring(0, 2))== -1) {
		alert("银行卡号开头6位不符合规范");
		document.getElementById("bank_acct").style.color ="red";
		return false;
	}
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    //alert("------2------");
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    //alert("------3------");
    //计算Luhm值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    //alert(lastNum+"----"+luhm);
    if(lastNum==luhm){
    //alert("卡号验证通过");
	document.getElementById("bank_acct").style.color ="";
    return true;
    }
    else{
    alert("银行卡号校验不通过");
	document.getElementById("bank_acct").style.color ="red";
    return false;
    }        
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<BODY class="BODY">
<iframe id="HideIfrm" name="HideIfrm" height="0px" width="0px"></iframe>
<form name="theform" method="post" action="account_change_edit.jsp" onsubmit="javascript:return validateForm(this);"  ENCTYPE="multipart/form-data">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name = "serial_no" value="<%=serial_no%>" />

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> </b></font>
</div><!--受益人信息-->
<br/>
<div align=left>

<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="contract_sub_bh" value="<%=contract_sub_bh%>"></td>
		<td align="right"><%=LocalUtilis.language("class.custNO",clientLocale)%> :</td><!--受益人编号-->
		<td><input readonly class="edline" type="text" onkeydown="javascript:nextKeyPress(this);" name="cust_no" size="20" value="<%=cust_no%>"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td><input readonly class="edline" name="card_code" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=card_id%>"></td>
		<td align="right"><%=LocalUtilis.language("class.custName2",clientLocale)%> :</td><!--受益人名称-->
		<td>
		     <input readonly class="edline" name="cust_name" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;
		</td>
	</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.fromBankName",clientLocale)%> :</td><!--原受益付款银行-->
			<td colspan="3">
				<input readonly class="edline" name="hbank_name" size="25" onkeydown="javascript:nextKeyPress(this);" value="<%=Argument.getBankName(bank_id)%>">
				<input type="text" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="hbank_sub_name" size="30" value="<%=bank_sub_name%>">
			</td>
		</tr>
		<tr>
			<td align="right">原开户行所在省 :</td>
			<td colspan="3">
				<select size="1" disabled="disabled" name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),bank_province)%>
				</select>	
				<select disabled="disabled" style="WIDTH: 150px" name="bank_city">
					<%=Argument.getCustodianNameLis(new Integer(9999), bank_province, new Integer(0),bank_city)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
			<td><input readonly class="edline" type="text"  onkeydown="javascript:nextKeyPress(this)" name="hbank_acct"
			     size="25" value="<%=bank_acct%>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.custAcctName",clientLocale)%> :</td><!--原受益人银行帐户名称-->
		   <td><input readonly class="edline"  name="cust_acct" type="text" onkeydown="javascript:nextKeyPress(this)" size=25 value=<%=cust_acct_name%>></td>
		</tr>
		<tr>
		<%if(open_flag.intValue()==1){%>
		   <td align="right"><%=LocalUtilis.language("class.fromBonusFlag",clientLocale)%> :</td><!--原收益分配方式-->
		   <td>
				<select size="1" disabled="disabled" name="hbonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
		   </td>
		<%}%>
		</tr>
		<tr>
			<td align="right">原受益兑付方式 :</td>
			<td colspan="3">
				<select disabled="disabled" size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 148px">
					<%=Argument.getDictParamOptions_intrust(1114,jk_type)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">本受益记录已变更 :</td>
			<td><input readonly class="edline" type="text" name="change_times" size="10" value="<%=change_times%>次">受益银行账号
			</td>
			<td align="right">
			<!--
				<button id="cust_button" class="xpbutton3" accessKey=e name="btnEdit" title="变更明细" 
			   	 onclick="javascript:getBenifitor(<%=serial_no%>);">变更明细
				</button>&nbsp;&nbsp;<!--变更明细-->  
			</td>
		</tr>

		<tr>
			<td colspan="4">
				<br/>
			</td>
		</tr>

		<tr>
			<td align="right"><%=LocalUtilis.language("class.bankSubName",clientLocale)%> :</td><!--受益付款银行-->
			<td colspan="3">
			<select size="1" name="bank_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getBankOptions(temp_bank_id)%>
			</select>
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=temp_bank_sub_name%>" />
			</td>
		</tr>
		<tr>
			<td align="right">开户行所在省 :</td>
			<td colspan="3">
				<select size="1"  name="bank_province" style="width: 130px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional(this.value,'');">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),temp_bank_province)%>
				</select>	
				<span id="gov_regional_span">
					<select style="WIDTH: 150px" name="bank_city">
						<%=Argument.getCustodianNameLis(new Integer(9999), temp_bank_province, new Integer(0),temp_bank_city)%>
					</select>
				</span><!-- 省级行政区域过滤出来的相关区域 -->
			</td>
		</tr>
		<tr>
			<td align="right" ><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
			<td ><input type="text" onkeydown="javascript:nextKeyPress(this)" name="bank_acct" onkeyup="javascript:showAcctNum(this.value)" onblur="luhmCheck(this.value);" size="25" value="<%=temp_bank_acct%>"><span id="bank_acct_num" class="span"><%if (!"".equals(temp_bank_acct)) out.print("("+temp_bank_acct.replaceAll(" ","").length()+" 位)"); %></span></td>
		    <td align="right" ><%=LocalUtilis.language("class.tempAcctName",clientLocale)%> :</td><!--受益人银行帐户名称-->
		    <td ><input   name="cust_acct_name" type="text" onkeydown="javascript:nextKeyPress(this)" size=25 value="<%=Utility.trimNull(temp_acct_name)%>" /></td>
		</tr>
		<tr>
		<%if(open_flag.intValue()==1){%>
			<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--收益分配方式-->
			<td>
				<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(temp_bonus_flag)%>
				</select>
			</td>
		<%}%>
		</tr>
		<tr>
			<td align="right">受益兑付方式 :</td>
			<td colspan="3">
				<select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 148px" onchange="javascript:showProductContract();">
					<%=Argument.getDictParamOptions_intrust(1114,temp_jk_type)%>
				</select>
			</td>
		</tr>
		<tr id="tr_df_product_contract" style="display:<%=jk_type.equals("111499") ? "" : "none"%>;">
			<td align="right">转入受益信托产品合同:</td>
			<td colspan="3">
				<select size="1" name="df_product_contract" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductInFL(input_bookCode,cust_id,df_product_id + "$" + df_contract_bh)%>
				</select>
			</td>
		</tr>
		<tr id="tr_df_product_contract_tishi" style="display:<%=jk_type.equals("111499") ? "" : "none"%>;">
			<td></td>
			<td colspan="3">(产品发行设置页面—>收益计算类别—>必须是<font color="red">按万份收益</font>)</td>
		</tr>
		<tr>
			 <td align="right"><%=LocalUtilis.language("menu.inputDate",clientLocale)%> : </td><!--修改日期-->
			 <td align="left">
				<INPUT TYPE="text" NAME="modi_time_picker" class=selecttext  value="<%=Format.formatDateLine(modi_time2)%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.modi_time_picker,theform.modi_time_picker.value,this);" tabindex="13">
				<INPUT TYPE="HIDDEN" NAME="modi_time" value="">&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td valign="top" align="right"><%=LocalUtilis.language("class.acctChgReason",clientLocale)%> :</td><!--修改原因-->
			<td colspan="3"><textarea rows="4" name="reason" cols="60" ><%=acct_chg_reason%></textarea></td>
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
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">删除</button>
            	</div><br>
			<%}	%>
            </td>
         </tr>
         <tr id="reader2" style="display:">
          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:&nbsp;&nbsp;</td><!-- 添加附件 -->
            <td colspan="4">
            	<table id="test" width="100%">
            		<tr >
            			<td>
		            	<input type="file" name="file_info" size="60">&nbsp;
		            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--选择附加文件-->
		            	</td>
		            </tr>
		        </table>
		        <button type="button"  class="xpbutton6" onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--单击此处添加更多附件--></button>
				<FONT color="#ff0000">(建议上传文件的大小在100M以内，附件越大，处理响应越慢)</FONT>
            </td>
        </tr>
	</table>
</div>

<div align="right" style="margin-top:5px;margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)
	</button><!--关闭-->
</div>

</form>
<%
benifitor.remove();
product.remove();
%>
<script language=javascript>
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;

		if(v_bSuccess=="true"){
			sl_alert("<%=LocalUtilis.language("message.acctChangeOK",clientLocale)%> ！");//受益人账户变更修改成功
			window.close();
		}
	}
</script>
</BODY>
</HTML>