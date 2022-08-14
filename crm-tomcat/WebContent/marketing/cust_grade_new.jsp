<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer cust_id =
	Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer grade_id =
	Utility.parseInt(request.getParameter("grade_id"), new Integer(1));


//客户对象
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
//评分标准细则对象
GradeIndexLocal gi_local = EJBFactory.getGradIndex();
GradeIndexVO gi_vo = new GradeIndexVO();
//评级得分详细
GradeDetailLocal gd_local = EJBFactory.getGradeDetail();
GradeDetailVO gd_vo = new GradeDetailVO();
//评级得分结果
GradeTotalLocal gt_local = EJBFactory.getGradeTotal();
GradeTotalVO gt_vo = new GradeTotalVO();

String task = Utility.trimNull(request.getParameter("task"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"), null);
String cust_no = Utility.trimNull(request.getParameter("cust_no"), null);
String card_id = Utility.trimNull(request.getParameter("card_id"), null);
Integer product_id2 = Utility.parseInt(request.getParameter("product_id2"),new Integer(0));
Integer valid_flag = Utility.parseInt(request.getParameter("valid_flag"), new Integer(0));//是否有效
Integer trade_date=Utility.parseInt(request.getParameter("trade_date"), new Integer(Utility.getCurrentDate()));//评分日期

//参数声明
List cust_list = new ArrayList();
List gi_list = new ArrayList();
List gi_list1 = new ArrayList();
Map cust_map = new HashMap();
Map gi_map = new HashMap();
Map gi_map1 = new HashMap();


if (Argument.getSyscontrolValue_1("SL_CUST") == 1) {
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	cust_list = cust_local.listCustomerLoad(cust_vo);
}
gi_vo.setGrade_id(grade_id);//评级体系ID
gi_vo.setValid_flag(new Integer(1));//是否有效
gi_list = gi_local.queryGradeIndex(gi_vo);//获得评分标准细则


boolean bSuccess = false; 
//添加评级明细和评级结果
if(request.getMethod().equals("POST"))
{
	GradeIndexLocal gi_local1 = EJBFactory.getGradIndex();
	GradeIndexVO gi_vo1 = new GradeIndexVO();
	gi_vo1.setGrade_id(grade_id);//评级体系ID
	gi_vo1.setValid_flag(new Integer(1));//是否有效
	gi_list1 = gi_local1.queryGradeIndex(gi_vo);//获得评分标准细则
	
	Iterator gi_it = gi_list1.iterator();
	while(gi_it.hasNext()){	
		gi_map1 = (Map)gi_it.next();
		//获得是否有效
		valid_flag=Utility.parseInt(request.getParameter(gi_map1.get("INDEX_ID").toString()), new Integer(0));	
		
		BigDecimal sj_value=null;
		if(Utility.parseInt(Utility.trimNull(gi_map1.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(1))){
			//手工录入
			sj_value=new BigDecimal(request.getParameter("sj_value"+gi_map1.get("INDEX_ID").toString()));
		
			gd_vo.setValid_flag(valid_flag);	
			gd_vo.setSj_value(sj_value);			
		}if(Utility.parseInt(Utility.trimNull(gi_map1.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(2))){
			//通过计算
			
		}if(Utility.parseInt(Utility.trimNull(gi_map1.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(3))){
			//布尔型
			gd_vo.setValid_flag(valid_flag);	
			gd_vo.setSj_value(new BigDecimal(valid_flag.doubleValue()));	
		}
		
		gd_vo.setGrade_id(grade_id);	
		gd_vo.setIndex_id(Utility.parseInt(Utility.trimNull(gi_map1.get("INDEX_ID")),new Integer(0)));
		gd_vo.setTrade_date(trade_date);
		gd_vo.setCust_id(cust_id);
		gd_vo.setInput_man(input_operatorCode);
		gd_vo.setOp_code(input_operatorCode);
		//添加评级明细
		gd_local.appendGradeDetail(gd_vo);
	}
	//添加到结果
	gt_vo.setGrade_id(grade_id);
	gt_vo.setCust_id(cust_id);
	gt_vo.setCheck_flag(new Integer(1));//审批标志：1未审批 2审批通过 3审批未通过
	gt_vo.setInput_man(input_operatorCode);
	gt_vo.setTrade_date(trade_date);
	gt_local.appendGradeTotal(gt_vo);
	gt_local.remove();
	bSuccess = true;
	task = "list";
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerRating",clientLocale)%> </TITLE><!--客户评级-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
//表单验证
function validateForm(form)
{
	if(!sl_checkChoice(form.grade_id, "<%=LocalUtilis.language("class.crmGrade",clientLocale)%> "))	return false;//评分体系
	if(!sl_checkChoice(form.cust_id, "<%=LocalUtilis.language("message.customer",clientLocale)%> "))	return false;	//客户
	if(!sl_checkDate(document.theform.trade_date_picker,"<%=LocalUtilis.language("class.trade_date",clientLocale)%> "))return false;//评分日期
	if(document.getElementById("table3").rows.length<=0){
		alert("<%=LocalUtilis.language("message.ratingSystemTip",clientLocale)%> ！");//请设置评分体系
		return false;
	}
	syncDatePicker(form.trade_date_picker, form.trade_date);	
	
	return sl_check_update();
}

//选择评级体系获得评级标准及客户信息
function queryInfo()
{
	if(document.theform.product_id2.value=="0"){
		alert("请选择产品!");return false;}
	document.theform.method = "get";
	document.theform.task.value = "list";
	disableAllBtn(true);
	syncDatePicker(document.theform.trade_date_picker,document.theform.trade_date);	
	document.theform.submit();
}

//键盘获得产品
function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id2.options.length;i++)
		{
			if(document.theform.product_id2.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id2.options[i].selected=true;
				prodid = document.theform.product_id2.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id2.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

//点击搜索
function searchProduct(value)
{
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id2.options.length;i++)
		{
			if(document.theform.product_id2.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id2.options[i].selected=true;
				prodid = document.theform.product_id2.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id2.options[0].selected=true;	
		}
		document.theform.product_id2.focus();					
	}	
}
<%if (bSuccess) {%>
	window.returnValue = 1;
	window.close();
<%}%>
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="cust_grade_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="task" value="<%=task%>">
<TABLE cellSpacing=0 cellPadding=1 width="100%" border=0>
	<TBODY>
		<TR>
			<TD>

			<table border="0" width="100%" cellspacing="0" cellpadding="1">
				<tr>
					<td></td>
				</tr>
				<br>
				<tr>
					<td align="right" noWrap><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!-- 请选择--><!--评分体系-->
					<td><select size="1" name="grade_id"
						onkeydown="javascript:nextKeyPress(this)"
						onchange="javascript:queryInfo()" style="width: 120px">
						<%=Argument.getCrmGradeIDOptions(grade_id)%>
					</select></td>
					<td align="right"><%=LocalUtilis.language("class.trade_date",clientLocale)%> :</td><!--评分日期-->
						<td>
						<INPUT TYPE="text" NAME="trade_date_picker" class=selecttext value="<%=Format.formatDateLine(trade_date)%>">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.trade_date_picker,theform.trade_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="trade_date"   value="<%=Format.formatDateLine(trade_date)%>">
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.selectCust",clientLocale)%> :</td><!--选择客户-->
					<td colspan="3"><select onkeydown="javascript:nextKeyPress(this)" size="1" name="cust_id">
						<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
						<%for(int i=0;i<cust_list.size();i++){
							cust_map = (Map)cust_list.get(i);
						%>	
							<option <%if(new Integer(cust_map.get("CUST_ID").toString()).equals(cust_id)) out.print("selected");%> 
								value='<%=cust_map.get("CUST_ID")%>'><%=cust_map.get("CUST_NAME") + "-" +cust_map.get("CARD_TYPE_NAME") + "-" +cust_map.get("CARD_ID")%>
							</option>
						<%}%>
					</td>				
				<tr>
					<td colspan="4">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table align="center" id="table3"  border="1" width="90%" style="border:1px double #cccccc" cellspacing="5" cellpadding="1">
				<%
				int iCount = 0;
				int iCurrent = 0;
				for (int j=0; j<gi_list.size(); j++){
					gi_map = (Map)gi_list.get(j);
				%>
				<tr>
					<td align="right" width="15%"><%=Utility.trimNull(gi_map.get("INDEX_TYPE_NAME"))%></td>
					<!--值计算说明-->
                    <td align="left">&nbsp;&nbsp;<%=iCount + 1%>、<%=Utility.trimNull(gi_map.get("INDEX_NAME"))%>(<%=Format.formatMoney0(new BigDecimal(gi_map.get("ST_VALUE").toString()))+Utility.trimNull(gi_map.get("VALUE_UNIT"))%>)&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.valueInfo",clientLocale)%> :<%=Utility.trimNull(gi_map.get("DF_INFO"))%></td>
				</tr>
				<%if(new Integer(gi_map.get("VALUE_FLAG").toString()).equals(new Integer(1))){%>
				<tr>
					<td align="right"><%=LocalUtilis.language("message.manual Entry",clientLocale)%> :</td><!--手工录入-->
					<td><INPUT type="text" name="sj_value<%=gi_map.get("INDEX_ID")%>">(<%=LocalUtilis.language("message.onlyNumber",clientLocale)%> )</td><!--只能是数字-->
				</tr>
				<%}if(new Integer(gi_map.get("VALUE_FLAG").toString()).equals(new Integer(2))){%>
				<tr>					
					<td align="right"><%=LocalUtilis.language("message.calculate",clientLocale)%> </td><!--通过计算-->
					<td></td>
				</tr>				
				<%}if(new Integer(gi_map.get("VALUE_FLAG").toString()).equals(new Integer(3))){%>
				<tr>					
					<td align="right">&nbsp;&nbsp;</td>
					<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<INPUT type="radio" name="<%=gi_map.get("INDEX_ID")%>"
						class="flatcheckbox" value="1"
						><%=LocalUtilis.language("message.yes",clientLocale)%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--是--><INPUT
						type="radio" name="<%=gi_map.get("INDEX_ID")%>"
						class="flatcheckbox" value="0"
						checked><%=LocalUtilis.language("message.no3",clientLocale)%> </td><!--否-->
				</tr>
				
				<%}
				iCurrent++;
				iCount++;
				}
				%>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<!--评级-->
					<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
                        onclick="javascript:if(document.theform.onsubmit()){document.theform.submit();}"><%=LocalUtilis.language("message.rating",clientLocale)%> (<u>S</u>)</button>
					&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" accessKey=c id="btnCancel"
						name="btnCancel"
						onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--取消-->
					&nbsp;&nbsp;</td>
				</tr>
			</table>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>

</HTML>

