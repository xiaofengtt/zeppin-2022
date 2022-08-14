<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//String sQuery = "";
//String[] totalValue = Utility.splitString(sQuery,"$");
boolean b = false;
Iterator it = null;
Map map = null;
Map product_map = null;
String product_period_unit = ""; //产品期限单位
BenifitorLocal benifitor = EJBFactory.getBenifitor();
ProductLocal product = EJBFactory.getProduct();
BenifitorVO vo = new BenifitorVO();

Integer ben_date = Utility.parseInt(request.getParameter("ben_date"),new Integer(0));

Integer valid_period = Utility.parseInt(request.getParameter("valid_period"),new Integer(0));
int single = Utility.parseInt(request.getParameter("single"),0);
Integer ben_end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(0));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer input_man=Utility.parseInt(input_operatorCode,new Integer(0));
String cust_acct_name = Utility.trimNull(request.getParameter("cust_acct_name"),"");
String ben_amount = Utility.trimNull(request.getParameter("ben_amount"),"");
String str_serial_no = Utility.trimNull(request.getParameter("serial_no"),"");
String[] serial_no_s = Utility.splitString(str_serial_no,",");
if(request.getMethod().equals("POST")){
	for (int i=0; i<serial_no_s.length; i++){
		vo.setSerial_no(new Integer(Utility.parseInt(serial_no_s[i],0)));
		vo.setValid_period(valid_period);
		vo.setBen_end_date(ben_end_date);
		vo.setInput_man(input_man);
		benifitor.modBenifitorValidPeriod(vo);
	}
	b = true;
}else{//查询产品信息
	ProductVO p_vo = new ProductVO();
	p_vo.setProduct_id(product_id);
	List list = product.load(p_vo);
	product_map = (Map)list.get(0);
	//得到产品期限单位
	int unit = Utility.parseInt((Integer)product_map.get("PERIOD_UNIT"),0).intValue();
	if (unit==0)
		product_period_unit = "无期限";
	else if (unit==1)
		product_period_unit = "天";
	else if (unit==2)
		product_period_unit = "月";
	else
		product_period_unit = "年";
}

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self" />
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
/*保存*/
function SaveAction(){
	if(!sl_checkNum(document.theform.valid_period,'受益期限 '))	return false;//受益期限
	syncDatePicker(document.theform.end_date_picker1, document.theform.end_date);
	disableAllBtn(true);
	document.theform.submit();
}
/*计算结束日期*/
function endDate(){
	
	var valid_period = document.getElementById('valid_period').value;
	var period_unit =  document.getElementById('period_unit').value;
	var end_date=0;
	var ben_date = document.getElementById('ben_date').value
	var y=Number(ben_date.substring(0,4));
	var m=Number(ben_date.substring(4,6));
	var d=Number(ben_date.substring(6,8));
	var date;
	if(period_unit==1){
		 date=new Date(new Date(y,m-1,d).getTime()+valid_period*24*60*60*1000);
		 var m1=Number(date.getMonth())+1;
		 var m2=m1>9?m1:'0'+m1;
		 var d1=Number(date.getDate())>9?Number(date.getDate()):'0'+Number(date.getDate());
		 var end_date1=date.getFullYear()+'-'+m2+'-'+d1;
		 document.getElementById('end_date_picker1').value=end_date1;
	 	document.getElementById('end_date_picker1').disabled=false;
		document.getElementById('end_button').disabled=false;
	}else{
		if(period_unit==0){
			end_date="21000101";
		}
		if(period_unit==2){
			var year=Math.floor(Number(valid_period)/12);
			var month=Number(valid_period)%12;
			if(m+month>12){
				if(d<10){
					if(m+month-12<10){
						end_date=(y+year+1).toString()+'0'+(m+month-12).toString()+'0'+d.toString();
					}else{
						end_date=(y+year+1).toString()+(m+month-12).toString()+'0'+d.toString();
					}
				}else{
					if(m+month-12<10){
						end_date=(y+year+1).toString()+'0'+(m+month-12).toString()+d.toString();
					}else{
						end_date=(y+year+1).toString()+(m+month-12).toString()+d.toString();
					}
				}
			}else{
				end_date=Number(ben_date)+year*10000+month*100;
			}
			
		}
		if(period_unit==3){
			end_date=Number(ben_date)+Number(valid_period)*10000;
		 	document.getElementById('end_date_picker1').disabled=false;
			document.getElementById('end_button').disabled=false;
		}
		if(end_date==0){
			end_date=ben_date;
			end_date=end_date.substring(0,4)+"-"+end_date.substring(4,6)+"-"+end_date.substring(6,8);
		}else{
			end_date=end_date.toString();
			end_date=end_date.substring(0,4)+"-"+end_date.substring(4,6)+"-"+end_date.substring(6,8);
		}
		document.getElementById('end_date_picker1').value=end_date;
	}
}
</script>

</HEAD>
<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<%if(product_map != null){ //提交后就不输出表单了，直接关闭窗口 %>
<form name="theform" method="post" action="benifiter_modi_valid_preiod.jsp" >
<input type="hidden" name="product_id" value="<%=product_id%>">
<input type="hidden" name="serial_no" value="<%=str_serial_no%>">
<table  border="0" width="100%" cellspacing="4" cellpadding="2" class="product-list">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
				<td align="left" >
					<input type="text" name="productcode" readonly="readonly" class="edline" value="<%=product_map.get("PRODUCT_CODE")%>" onkeydown="javascript:setProduct(this.value);" size="30">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品名称-->
				<td  align="left">
					<input type="text" name="productName" readonly="readonly" class="edline" value="<%=product_map.get("PRODUCT_NAME")%>" size="60">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品简称-->
				<td  align="left">
					<input type="text" name="productJC" readonly="readonly" class="edline" value="<%=product_map.get("PRODUCT_JC")%>" size="30">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;起始时间 :&nbsp;&nbsp;</td><!--起始时间-->
				<td  align="left">
					<input type="text" name="start_date" readonly="readonly" class="edline" value="<%=product_map.get("START_DATE")%>" size="30">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;产品期限 :&nbsp;&nbsp;</td><!--期限-->
				<td  align="left">
					<input type="text" name="product_" readonly="readonly" class="edline" value="<%=product_map.get("VALID_PERIOD")%><%=product_period_unit %>" size="30">
				</td>
			</tr>
			
</table>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 class="product-list">
	<TR>
		<TD vAlign=top align=left>
		
		</TD>
	</TR>
	<% if (single == 1){ %> 
	<TR>
		<TD align="right"> 受益人 ：</TD>
		<td  align="left">
			<input type="text" name="cust_acct_name" readonly="readonly" class="edline" value="<%=cust_acct_name%>" size="30">
		</td>
	</TR>
	<TR>
		<TD align="right"> 当前权益 ：</TD>
		<td  align="left">
			<input type="text" name="ben_amount" readonly="readonly" class="edline" value="<%=ben_amount%>" size="30">
		</td>
	</TR>
	<TR>
		<TD align="right">受益起始日 ：</TD>
		<td  align="left">
			<input type="text" name="ben_date" id = "ben_date" readonly="readonly" class="edline" value="<%=ben_date%>" size="30">
		</td>
	</TR>
	<% }else{ %>
		<TR>
		<TD align="right">受益起始日 ：</TD>
		<td  align="left">
			<input type="text" name="ben_date" id = "ben_date" readonly="readonly" class="edline" value="<%=product_map.get("START_DATE")%>" size="30">
		</td>
	<%} %>
	</TR>
	<TR>
		<TD align="right">受益期限 ：</TD>
		<TD><INPUT name="valid_period" id ="valid_period" value="" onblur="javascript:endDate();">
				<SELECT name="period_unit" id ="period_unit" onChange="javascript:endDate();" disabled="disabled">
					<Option value="2" selected>月</Option>
			</SELECT>
		</TD>
	</TR>
	<TR>
		<TD align="right">到期日期 ：</TD>
		<TD><INPUT TYPE="text"  id="end_date_picker1" name="end_date_picker1" class=selecttext value="" >
			<INPUT TYPE="button" name="end_button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker1,theform.end_date_picker1.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="end_date" value="">
		</TD>
	</TR>

</TABLE>
<div align="center">
	<BR/>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<%} %>
<%@ include file="/includes/foot.inc"%>
</BODY>

<%benifitor.remove();
product.remove();
if (b) {
%>
<script language=javascript>
	window.returnValue=0;
	window.close();
</script>

<%}%>
</HTML>