<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递参数
String task = Utility.trimNull(request.getParameter("task"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"), null);
String cust_no = Utility.trimNull(request.getParameter("cust_no"), null);
String card_id = Utility.trimNull(request.getParameter("card_id"), null);
Integer rating_date=Utility.parseInt(request.getParameter("rating_date"), new Integer(Utility.getCurrentDate()));//评分日期
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer subject_id = Utility.parseInt(request.getParameter("subject_id"), new Integer(0));
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
Integer cust_all_source = Utility.parseInt(request.getParameter("cust_all_source"),new Integer(0));

//获得对象及结果集
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
RatingVO rating_vo = new RatingVO();
RatingLocal rating = EJBFactory.getRating();
ScoreOperandLocal score_operand = EJBFactory.getScoreOperand();

//参数声明
List cust_list = new ArrayList();
Map cust_map = new HashMap();
List rating_list = new ArrayList();
Map rating_map = new HashMap();
List operand_list = new ArrayList();
Map operand_map = new HashMap();
String [] operand_ids = null;
String [] scores = null;
Integer operandId = new Integer(0);
Integer score = new Integer(0);
boolean bSuccess = false; 

//选择评分体系时获得所有客户以评级标准细则
if ("list".equals(task)) {
	if (Argument.getSyscontrolValue_1("SL_CUST") == 1) {
		cust_vo.setBook_code(input_bookCode);
		cust_vo.setInput_man(input_operatorCode);
		cust_vo.setCust_no(cust_no);
		cust_vo.setCard_id(card_id);
		cust_vo.setCust_name(cust_name);	
		cust_list = cust_local.listProcAll(cust_vo);
	}
	rating_vo.setCust_no(cust_no);
	rating_vo.setCust_name(cust_name);
	rating_vo.setSubject_id(subject_id);
	rating_vo.setRating_no("");
	rating_vo.setRating_name("");
	rating_vo.setRating_date(new Integer(0));
	rating_vo.setInput_man(input_man);
	rating_list = rating.list_tcustscoredetail(rating_vo);

	rating_vo.setOperand_id(new Integer(0));
	operand_list = score_operand.list_subjectoperand(rating_vo);
}


//保存评级信息
if(request.getMethod().equals("POST")){
	operand_ids = pageContext.getRequest().getParameterValues("operand_id");
	scores = pageContext.getRequest().getParameterValues("score");
	
	rating_vo.setCust_id(cust_id);
	rating_vo.setSubject_id(subject_id);
	rating_vo.setRating_date(rating_date);

	if(operand_ids.length >0){
		for (int i = 0; i < operand_ids.length; i++) {
			if (operand_ids[i] != null && operand_ids[i].length() > 0) {
				operandId = Utility.parseInt(Utility.trimNull(operand_ids[i]),new Integer(0));
			}
			if (scores[i] != null && scores[i].length() > 0) {
				score = Utility.parseInt(Utility.trimNull(scores[i]),new Integer(0));
			}
			rating_vo.setOperand_id(operandId);
			rating_vo.setScore(score);
			rating.custRating(rating_vo);
		}
	}
	bSuccess = true;
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript">
//表单验证
function validateForm(form)
{
		
	
}

//评分计算
function confirmNum(element)
{
	
	if(!sl_checkChoice(form.subject_id, "<%=LocalUtilis.language("class.crmGrade",clientLocale)%> "))	return false;//请选择评分体系
	if(!sl_checkChoice(form.cust_id, "<%=LocalUtilis.language("message.customer",clientLocale)%> "))	return false;	//请选择客户

	if(!sl_checkDate(document.theform.rating_date_picker,"<%=LocalUtilis.language("class.trade_date",clientLocale)%> "))return false;//评分日期
	syncDatePicker(form.rating_date_picker, form.rating_date);

	if (element == null)
	{
		sl_alert("未选定任何评分条件！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("请选定要评分的条件！");
		return false;
	}
	
	var cs = document.getElementsByName('operand_id');
	var cust_id = document.theform.cust_id.value;
	values = 0;
	for(i=0;i<cs.length;i++)
	{
		if(cs[i].checked)
		{
			var arrayV = cs[i].value.split('&');
			if(arrayV[1]==1)//系统打分
			{	
				utilityService.srocingCustomer(arrayV[0],cust_id,{callback: function(data){
					values = parseIn(values) + parseInt(data);
				}});
			}else if(arrayV[1]==2){	//人工-选择
				values = parseInt(values) + parseInt(document.getElementById('score_change_'+i).value);	
			}else if(arrayV[1]==3){	//人工-输入			
				values = parseInt(values) + parseInt(document.getElementById('score_input_'+i).value);
			}
		}	
	}
	document.theform.cust_all_source.value = values;
	return false;
	return sl_check_update();
}

//选择评级体系获得评级标准及客户信息
function queryInfo()
{
	if(document.theform.subject_id.value=="0"){
		alert("请选择评分体系!");return false;}
	document.theform.method = "get";
	document.theform.task.value = "list";
	disableAllBtn(true);
	syncDatePicker(document.theform.rating_date_picker,document.theform.rating_date);	
	document.theform.submit();
}

<%if (bSuccess) {%>
	window.returnValue = 1;
	window.close();
<%}%>
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="cust_rating_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="task" value="<%=task%>">
<input type="hidden" name="cust_all_source" value="<%=cust_all_source%>">
<TABLE cellSpacing=0 cellPadding=1 width="100%">
	<TBODY>
		<TR>
			<TD>

			<table border="0" width="100%" cellspacing="0" cellpadding="1">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%>:</td><!--评分体系-->
					<td align="left">
						<select name="subject_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px" onchange="javascript:queryInfo();">
						<%=Argument.getScoreSubjectOptions(subject_id)%>
						</select>
					</td>
					<td align="right"><%=LocalUtilis.language("class.trade_date",clientLocale)%> :</td><!--评分日期-->
						<td>
						<INPUT TYPE="text" NAME="rating_date_picker" class=selecttext value="<%=Format.formatDateLine(rating_date)%>">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rating_date_picker,theform.rating_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="rating_date"   value="<%=Format.formatDateLine(rating_date)%>">
					</td>
				</tr>
				<tr>
					<td align=right><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
					<td><input onkeydown="javascript:nextKeyPress(this)"
						type="text"
						name="card_id" size="25"
						value="<%=Utility.trimNull(card_id)%>"></td>
					<td align=right width="10%"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text"
						name="cust_no" size="20"
						value="<%=Utility.trimNull(cust_no)%>"></td>
				</tr>
				<tr>
					<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!-- 客户名称 -->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="cust_name" size="25" value="<%=Utility.trimNull(cust_name)%>">
					</td>
					<td>
					<button type="button"  class="xpbutton3" accessKey=q
						onclick="javascript:queryInfo();"><%=LocalUtilis.language("message.search",clientLocale)%> (<u>Q</u>)</button><!--搜索-->
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
				</tr>
				<tr>
					<td colspan="4">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table align="center" id="table3"  border="1" width="90%" style="border:1px double #cccccc" cellspacing="5" cellpadding="1">
				<tr><td align="center" colspan="5"><b><font size="2">客户打分明细</font></b></td></tr>
				<%if(rating_list.size()>0){ %>
				<tr>
					<td align="center"><b><%=LocalUtilis.language("class.operandName",clientLocale)%></b></td><!--操作数名称-->
					<td align="center"><b><%=LocalUtilis.language("class.scoringDate",clientLocale)%></b></td><!--打分日期-->
					<td align="center"><b><%=LocalUtilis.language("class.score",clientLocale)%></b></td><!--分值-->
					<td align="center"><b><%=LocalUtilis.language("class.increaseDecrease",clientLocale)%></b></td><!--比上期增减-->
					<td align="center"><b><%=LocalUtilis.language("class.endDate3",clientLocale)%></b></td><!--截止日期-->
				</tr>
				<%
				int iCount = 0;
				for (int j=0; j<rating_list.size(); j++){
					rating_map = (Map)rating_list.get(j);
				%>
				<tr>
					<td align="left">&nbsp;<%=Utility.trimNull(rating_map.get("OPERAND_NAME"))%></td>
					<td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(rating_map.get("SCORING_DATE")),new Integer(0))) %></td>
					<td align="right"><%=Utility.trimNull(rating_map.get("CUST_SOURCE")) %>&nbsp;</td>
					<td align="right"><%=Utility.trimNull(rating_map.get("REGULATION")) %>&nbsp;</td>
					<td align="center"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(rating_map.get("END_DATE")),new Integer(0)))%></td>
				</tr>
				
				<%
				iCount++;}
				}
				%>
			</table>
			<br>
			<table align="center" id="table3"  border=0 width="90%" class="tablelinecolor" cellspacing="1" cellpadding="2">
			
				<tr class="tr1"><td align="center" colspan="5"><b><font size="2">计分操作数</font></b></td></tr>
				<%if(operand_list.size()>0){ %>
				<tr class="tr1">
					<td>
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.operand_id,this);">
						<b><%=LocalUtilis.language("class.operandNumber",clientLocale)%></b>
					</td><!--操作数编号-->
					<td align="center"><b><%=LocalUtilis.language("class.operandName",clientLocale)%></b></td><!--操作数名称-->
					<td align="center"><b><%=LocalUtilis.language("class.scoringPeople",clientLocale)%></b></td><!--打分人-->
					<td align="center"><b><%=LocalUtilis.language("class.atrificialOpposesSource",clientLocale)%></b></td><!--人工打分来源-->
					<td align="center"><b><%=LocalUtilis.language("class.topicValue2",clientLocale)%></b></td><!--选项值-->
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				Integer operand_id = new Integer(0);
				Integer scoring = new Integer(0);
				Integer source = new Integer(0);
				String scoringStr = "";
				String sourceStr = "";
				String operand_no = "";
				String operand_name = "";
				int sourceStr_Flag = 0;
				for (int j=0; j<operand_list.size(); j++){
					operand_map = (Map)operand_list.get(j);

					operand_id = Utility.parseInt(Utility.trimNull(operand_map.get("OPERAND_ID")),new Integer(0));
					scoring = Utility.parseInt(Utility.trimNull(operand_map.get("SCORING")),new Integer(0));
					source = Utility.parseInt(Utility.trimNull(operand_map.get("SOURCE")),new Integer(0));
					operand_no = Utility.trimNull(operand_map.get("OPERAND_NO"));
					operand_name = Utility.trimNull(operand_map.get("OPERAND_NAME"));

					if(scoring.intValue()==2)
						scoringStr = LocalUtilis.language("class.system",clientLocale);
					else if(scoring.intValue()==1)
						scoringStr = LocalUtilis.language("class.aritificial",clientLocale);
					else
						scoringStr = "";
				
					if(source.intValue()==2)
						sourceStr = LocalUtilis.language("class.calculationScoring",clientLocale);
					else if(source.intValue()==1)
						sourceStr = LocalUtilis.language("class.manualScoring",clientLocale);
					else
						sourceStr = "";
						
					if(scoring.intValue()==1&&source.intValue()==2)//人工--选择
						sourceStr_Flag = 2;
					else if(scoring.intValue()==1&&source.intValue()==1)//人工--输入
						sourceStr_Flag = 3;	
					else if(scoring.intValue()==2)//系统打分
						sourceStr_Flag = 1;		
					System.out.println("source:"+source+"--scoring:"+scoring+"--sourceStr_Flag"+sourceStr_Flag)	;
				%>
				<tr class="tr1">
					<td align="left">
						  <input type="checkbox" name="operand_id" value="<%=operand_id%>&<%=sourceStr_Flag%>" class="flatcheckbox">
						  &nbsp;&nbsp;<%=operand_no%>
					</td>
					<td align="left">&nbsp;<%=operand_name %></td>
					<td align="center">&nbsp;<%=scoringStr%>&nbsp;</td>
					<td align="center">&nbsp;<%=sourceStr %>&nbsp;</td>
					<td align="center">&nbsp;
					<%if(scoring.intValue()==1&&source.intValue()==2) {//人工打分 选择%>
						<select id="score_change_<%=j %>" name="score_<%=j %>" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 200px">
							<%=Argument.getManualScoring(operand_id,"") %>
						</select>
					<%}else if(scoring.intValue()==1&&source.intValue()==1){ //人工 手工%>
						<input type="text" id="score_input_<%=j %>" name="score_<%=j %>" name="score" size="20" value="0">
					<%}else{%>
						<input type="hidden" id="score_<%=j %>" name="score_<%=j %>" value="">
					<%} %>
					</td>
				</tr>
				
				<%
				iCurrent++;}
				}
				%>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<!--评级-->
					<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
                        onclick="javascript:if(confirmNum(document.theform.operand_id)){document.theform.submit();}"><%=LocalUtilis.language("message.rating",clientLocale)%> (<u>S</u>)</button>
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
<%cust_local.remove(); 
rating.remove();
score_operand.remove();
%>
