<%@page contentType="text/html; charset=GBK"%>
<%@page import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
try{
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer sub_flag = Utility.parseInt(Utility.trimNull(request.getParameter("sub_flag")),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String q_relation_type = Utility.trimNull(request.getParameter("q_relation_type"));
int isDk = Utility.parseInt(Utility.trimNull(request.getParameter("isDk")),0);

IntrustEntCustomerLocal cust = EJBFactory.getIntrustEntCustomer();
EntRelationVO vo = new EntRelationVO();

boolean bSuccess = false; 
//新增
if(sub_flag!=null&&sub_flag.intValue()==1){
	vo = new EntRelationVO();
	
	vo.setEnt_cust_id(cust_id);
	vo.setRelation_type(Utility.trimNull(request.getParameter("relation_type")));
	vo.setRelation_cust_id(Utility.parseInt(Utility.trimNull(request.getParameter("ent_cust_id")),new Integer(0)));
	if(vo.getRelation_type().equals("21430102")){
		vo.setKg_flag(Utility.parseInt(Utility.trimNull(request.getParameter("kg_flag")), new Integer(0)));
		vo.setCg_rate(Utility.parseDecimal(request.getParameter("cg_rate"), new BigDecimal(0.00), 8, "0.01"));
	}else{
		vo.setKg_flag(new Integer(0));
		vo.setCg_rate(new BigDecimal(0.00));
	}
	vo.setBook_code(input_bookCode);
	vo.setInput_man(input_operatorCode);
	
	cust.addEntRelation(vo);
	bSuccess = true;
}
else if(sub_flag!=null&&sub_flag.intValue()==2){
	vo = new EntRelationVO();
	
	vo.setSerial_no(Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0)));
	vo.setInput_man(input_operatorCode);
	
	cust.delEntRelation(vo);
	bSuccess = true;
}
//列表
vo = new EntRelationVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setEnt_cust_id(cust_id);
vo.setRelation_type(q_relation_type);

IPageList pageList = cust.queryEntRelation(vo,1,-1);
sUrl = sUrl +"&cust_id="+cust_id+"&cust_name="+cust_name+"&q_relation_type="+q_relation_type;

String ent_cust_array = "";
int iCurrent = 0;
List rsList=pageList.getRsList();
Map rsMap = new HashMap(0);
Iterator it = rsList.iterator();

//获取股东数量
int gd_count = 0;
for(int i=0; i<rsList.size(); i++){	
	if(Utility.trimNull(((Map)rsList.get(i)).get("RELATION_TYPE")).equals("21430102"))
		gd_count = gd_count + 1;
}
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.custRelationInfo",clientLocale)%> </TITLE><!--企业客户股东定义-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/ext2.0/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 

<%if(language.equals("en")){ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_en.js"></SCRIPT>
<%}else{ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%}%>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
window.name="win_test";
window.onload = function(){
	initQueryCondition();
	var bSuccess = document.theform.bSuccess.value;
	var sub_flag = document.theform.sub_flag.value;
	
	if(bSuccess=="true"&&sub_flag==1){
		alert('<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！');//保存成功
	};
	if(bSuccess=="true"&&sub_flag==2){
		alert('<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！');//删除成功
	};
};

function refreshPage(){
	var form = document.getElementsByName("theform")[0];
	var url = '<%=sUrlEx%>&sub_flag=0&cust_id=' + document.theform.cust_id.value
				+ "&cust_name=" + document.theform.cust_name.value
				+ "&q_relation_type=" + document.theform.q_relation_type.value;
	form.action = url;
	form.submit();
};

function saveAction(){	
	var form = document.getElementsByName("theform")[0];
	if(!sl_checkChoice(document.theform.relation_type, '<%=LocalUtilis.language("class.relationType",clientLocale)%> '))	return false;//股东关系
	var relation_type = document.theform.relation_type.value;
	var ent_cust_id = document.theform.ent_cust_id.value;
	
	if(ent_cust_id==""){
		sl_alert('<%=LocalUtilis.language("message.selectPartnerTip",clientLocale)%> ！');//请选择股东
		return false;
	}
	
	if(!checkCustId(ent_cust_id)){
		alert('<%=LocalUtilis.language("message.selectPartnerTip2",clientLocale)%> ！');//该客户已是股东
		return false;
	}

	if(document.getElementById("relation_type").value == '21430102'){
		if(!sl_checkDecimal(document.theform.cg_rate, '持股比例 ', 3, 4, 1))		return false;
		if(document.theform.cg_rate.value < 0){
			sl_alert("持股比例不能小于零！");	return false;
		}
	}
	
	//是否可以选自身为股东 暂不限制
	if(sl_confirm("保存")){
		var url = '<%=sUrlEx%>&cust_id=' + document.theform.cust_id.value
					+ "&cust_name=" + document.theform.cust_name.value
					+ "&q_relation_type=&relation_type=" + document.theform.relation_type.value
					+ "&ent_cust_id=" + document.theform.ent_cust_id.value
					+ "&sub_flag=1"
					+ "&kg_flag=" + document.theform.kg_flag.value
					+ "&cg_rate=" + document.theform.cg_rate.value;
		form.action = url;
		form.submit();
	}
};

function delAction(serial_no){
	if(sl_confirm('<%=LocalUtilis.language("message.delete",clientLocale)%> ')){//删除
		var form = document.getElementsByName("theform")[0];
		var url = '<%=sUrlEx%>&sub_flag=2&cust_id=' + document.theform.cust_id.value
					+ "&cust_name=" + document.theform.cust_name.value
					+ '&q_relation_type=&serial_no=' + serial_no;
		form.action = url;
		form.submit();
	}
};

function checkCustId(cust_id){
	var ent_cust_array = document.theform.ent_cust_array.value;	
	var array = ent_cust_array.split("|");
	for(var i=1;i<array.length;i++){
		if(cust_id==array[i]){			
			return false;
		}
	}
	
	return true;
}

function setRelationType(obj){
	if(obj.value == "21430102"){
		document.getElementById("relation1").style.display = "";
		document.getElementById("relation2").style.display = "";
		document.getElementById("relation3").style.display = "";
		document.getElementById("relation4").style.display = "";
	}else{
		document.getElementById("relation1").style.display = "none";
		document.getElementById("relation2").style.display = "none";
		document.getElementById("relation3").style.display = "none";
		document.getElementById("relation4").style.display = "none";
	}
}

function setKgFlag(obj){
	if(obj.checked){
		obj.value = 1;
	}else{
		obj.value = 0;
	}
}

function getCustomer(prefix)
{
	cust_id = getElement(prefix, "cust_id").value;
   
	v = showModalDialog('customer_info2.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&id=' + cust_id+'&isDk=<%=isDk%>','','dialogWidth:850px;dialogHeight:440px;status:0;help:0;');
	if (v != null)
		showCustomer(prefix, v);
	return (v != null);
}

function setRelationType(obj){
	if(obj.value == "21430102"){
		document.getElementById("relation1").style.display = "";
		document.getElementById("relation2").style.display = "";
		document.getElementById("relation3").style.display = "";
		document.getElementById("relation4").style.display = "";
	}else{
		document.getElementById("relation1").style.display = "none";
		document.getElementById("relation2").style.display = "none";
		document.getElementById("relation3").style.display = "none";
		document.getElementById("relation4").style.display = "none";
	}
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" target="win_test">
<input type="hidden" name="cust_id" id="cust_id" value="<%=cust_id%>" />
<input type="hidden" name="cust_name" id="cust_name" value="<%=cust_name%>" />
<input type="hidden" name="ent_cust_id" id="ent_cust_id" value="" />
<!-- 提交标志 -->
<input type="hidden" name="sub_flag" id="sub_flag" value="<%=sub_flag%>" />
<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:250px;">
    <table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
       <tr>
         <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
         <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"onclick="javascript:cancelQuery();"></button></td>
       </tr>
    </table>
    <table>	 
    	<tr>
    		<td>
    			<%=LocalUtilis.language("class.relationType",clientLocale)%> ：&nbsp;&nbsp;<!--股东关系-->
				<select size="1"  name="q_relation_type" style="width: 120px">
			          <%=Argument.getDictOptionsWithLevelIntrust(new Integer(2143),"214301",null)%>
			    </select>&nbsp;&nbsp;
    		</td>
    	</tr>
    	<tr>
	       <td align="center" colspan=4>
	       		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onclick="javascript:refreshPage();"><%=LocalUtilis.language("message.ok2",clientLocale)%> </button>
	       </td>
	     </tr>
    </table>
</div>
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/Feichuang5.jpg" width="50" height="50">
		<b><%=cust_name%>-<%=LocalUtilis.language("message.partnerSet",clientLocale)%> </b><!--股东设置-->
	</div>
	<div align="right">
		<button type="button"  class="xpbutton3" id="queryButton" name="queryButton" title='<%=LocalUtilis.language("intrsut.home.filter",clientLocale)%>'><%=LocalUtilis.language("intrsut.home.filter",clientLocale)%> </button><!--过滤-->
		&nbsp;&nbsp;&nbsp;<button type="button"  class="xpbutton3" id="returnButton" name="returnButton" title='关闭股东信息' onclick="javascript:window.returnValue = <%=gd_count%>;window.close();">关闭</button>&nbsp;
	</div>
	<hr color="#808080" size="1">
</div>

<div align="left" style="float: left; height: 30px;">
	<table width="100%" cellpadding="1" cellspacing="1">
		<tr>
			<td	width="95%">
				<table>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.relationType",clientLocale)%>: <!--股东关系--></td>
						<td>
							<select size="1"  id="relation_type" name="relation_type" style="width: 120px"  onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setRelationType(this);">					         
									<%=Argument.getDictOptionsWithLevelIntrust(new Integer(2143),"214301",new Integer(21430102))%>
						    </select>
						</td>
						<td align="right"><%=LocalUtilis.language("class.entCustName",clientLocale)%> <!--股东名称-->:</td>
						<td>
							<input readonly class="edline" name="ent_cust_name" size="20" value="">
							<button type="button"  class="xpbutton4" onclick="javascript:getCustomer('ent');"><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%></button>&nbsp;&nbsp;<!---->	<!--请选择-->
						</td>
						<td style="display: block;" id="relation1" align="right">控股股东标志:</td>
						<td style="display: block;" id="relation2"><input onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" name="kg_flag" checked="checked"  id="kg_flag" onclick="javascript:setKgFlag(this);" value="1">&nbsp;&nbsp;</td>
						<td style="display: block;" id="relation3" align="right"><font color="red">*</font>持股比例:</td>
						<td style="display: block;" id="relation4"><input type="text" name="cg_rate" id="cg_rate" onkeydown="javascript:nextKeyPress(this)" size="15">%</td>
					</tr>
				</table>
			</td>
			<td width="5%" align="right">
				<button type="button"  class="xpbutton3" id="btnSave" name="btnSave" title="<%=LocalUtilis.language("message.save",clientLocale)%>" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> </button>
			</td>
		</tr>
	</table>
</div>

<div style="margin-top:10px;" align="center">
	<table border="0" cellspacing="1" cellpadding="1" class="tablelinecolor" width="100%">
		<tr class="trh">
			<td align="center" height="25" width="20%"><%=LocalUtilis.language("class.relationType",clientLocale)%> </td><!--股东关系-->
			<td align="center" height="25" width="30%"><%=LocalUtilis.language("class.relationCustName",clientLocale)%> </td><!--股东名册-->
			<td align="center" height="25" width="20%">控股股东标志</td>
			<td align="center" height="25" width="20%">持股比例(%)</td>
			<td align="center" height="25" width="10%"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
		</tr>
		<%
			while(it.hasNext()){
				iCurrent++;
				rsMap = (HashMap)it.next();
				ent_cust_array = ent_cust_array+"|"+Utility.trimNull(rsMap.get("RELATION_CUST_ID"));
		%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td align="left" height="25"><%=Utility.trimNull(rsMap.get("RELATION_TYPE_NAME"))%></td>
			<td align="left" height="25"><%=Utility.trimNull(rsMap.get("RELATION_CUST_NAME"))%></td>
			<td align="left" height="25"><%=Utility.trimNull(rsMap.get("KG_FLAG")).equals("0") ? "否" : "是"%></td>
			<td align="right" height="25"><%=Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(rsMap.get("CG_RATE"))), new BigDecimal(0)).multiply(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP)%></td>
			<td align="center" height="25">
				<button type="button"   class="xpbutton2" onclick="javascript:delAction(<%=Utility.trimNull(rsMap.get("SERIAL_NO"))%>);">&gt;&gt;</button>
			</td>
		</tr>
		<%}%>
		<%for(;iCurrent<8; iCurrent++){%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td height="25"></td>
				<td height="25"></td>
				<td height="25"></td>
				<td height="25"></td>
				<td height="25"></td>
			</tr>
		<%}%>
	</table>
</div>
<input type="hidden" name="ent_cust_array" id="ent_cust_array" value="<%=ent_cust_array%>" />
</form>
</BODY>
</HTML>
<%cust.remove();
}catch(Exception e){
	throw e;
}%>