<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>


<%
//查询条件
Integer grade_id = Utility.parseInt(request.getParameter("grade_id"), new Integer(0));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"), "");
Integer start_trade_date = Utility.parseInt(request.getParameter("start_trade_date"), new Integer(0));
Integer end_trade_date = Utility.parseInt(request.getParameter("end_trade_date"), new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));

//获得Bean及VO
GradeTotalLocal gt_local = EJBFactory.getGradeTotal();
GradeDetailLocal gd_local = EJBFactory.getGradeDetail();
GradeIndexLocal gi_local = EJBFactory.getGradIndex();
GradeTotalVO gt_vo = new GradeTotalVO();
GradeDetailVO gd_vo = new GradeDetailVO();
GradeIndexVO gi_vo = new GradeIndexVO();

//设置参数
gt_vo.setGrade_id(grade_id);
gt_vo.setCust_name(cust_name);
gt_vo.setPre_trade_date(start_trade_date);
gt_vo.setEnd_trade_date(end_trade_date);
gt_vo.setCheck_flag(check_flag);//审核标志
gt_vo.setProduct_id(product_id);
gt_vo.setInput_man(input_operatorCode);

//获得评级等分结果
IPageList pageList = gt_local.queryGradeTotal(gt_vo, Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
Map map = new HashMap();
String tempUrl = "";

//URL设置
tempUrl = "&grade_id=" + grade_id + "&cust_name=" + cust_name + "&start_trade_date=" + start_trade_date
			+ "&end_trade_date=" + end_trade_date + "&check_flag=" + check_flag + "&product_id=" + product_id;
sUrl = sUrl + tempUrl;

int isproject=Argument.getSyscontrolValue("ISPROJECT");	
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
//评级
function newInfo()
{
	if(showModalDialog('client_mark_grade_new.jsp', '', 'dialogWidth:800px;dialogHeight:600px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

//分页查询
function refreshPage()
{	
	disableAllBtn(true);
	waitting.style.visibility='visible';
	location = 'client_mark_grade_list.jsp?page=<%=sPage%>&pagesize='
					+ document.theform.pagesize.value +'&grade_id=' 
					+ document.theform.grade_id.value+'&cust_name=' 
					+ document.theform.cust_name.value+'&start_trade_date=' 
					+ document.theform.start_trade_date.value+'&end_trade_date=' 
					+ document.theform.end_trade_date.value+'&check_flag=' + document.theform.check_flag.value
					+'&product_id=' + document.theform.product_id.value;
}

//查询
function StartQuery()
{	
	syncDatePicker(document.theform.start_trade_date_picker, document.theform.start_trade_date);
	syncDatePicker(document.theform.end_trade_date_picker, document.theform.end_trade_date);	
	refreshPage();	
}

//查看评分详细
function setiteminfor(tr10,tablePro,flagdisplay,imagex)
{
    i= flagdisplay.value;
   
    if(i==0)
    {
      tablePro.style.display="";
      tr10.style.display="";
      flagdisplay.value=1;
      imagex.src='<%=request.getContextPath()%>/images/up_enabled.gif';
    }
    else if(i==1)
    {
       tablePro.style.display="none";
        tr10.style.display="none";
      flagdisplay.value=0;
      imagex.src='<%=request.getContextPath()%>/images/down_enabled.gif';
    }
}
//审批记录
function showprojectinfo(id,project_id,flag){
	 modifyFrameStructure(1);
	location="<%=request.getContextPath()%>/projects/problem_info.jsp?problem_id="+id+"&project_id="+project_id+"&flag="+flag; 
}

//窗体加载
window.onload = function(){
initQueryCondition()
};

//键盘查询产品
function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

//点击查询产品
function searchProduct(value)
{
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="client_mark_grade_remove.jsp">
<%@ include file="/includes/waiting.inc"%>
<div id="queryCondition" class="qcMain"
	style="display: none; width: 510px">
<table id="qcTitle" class="qcTitle" align="center" width="99%"
	cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
			onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.crmGrade",clientLocale)%> :</td><!--评分体系-->
		<td><select size="1" name="grade_id"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCrmGradeIDOptions(grade_id)%>
		</select></td>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td align="left"><input type="text" name="cust_name"
			onkeydown="javascript:nextKeyPress(this)" size="15"
			value="<%=cust_name%>"></td>
	</tr>
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td><select size="1" name="check_flag"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCrmGradeCheckOptions(check_flag)%>
		</select></td>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td ><!--产品编号-->
		<td align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td >
	</tr>
	<tr>			
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--产品选择-->
		<td align="left" colspan=3>
			<SELECT name="product_id" class="productname">
			<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<!-- 评分日期 --><!--从-->
        <td align="right"><%=LocalUtilis.language("class.trade_date",clientLocale)%><%=LocalUtilis.language("message.start",clientLocale)%> :</td>
		<td><INPUT TYPE="text" NAME="start_trade_date_picker" class=selecttext
			value="<%=Format.formatDateLine(start_trade_date)%>"> <INPUT
			TYPE="button" value="▼" class=selectbtn
			onclick="javascript:CalendarWebControl.show(theform.start_trade_date_picker,theform.start_trade_date_picker.value,this);"
			tabindex="13"> <INPUT TYPE="hidden" NAME="start_trade_date"></td>
		<td align="right"><%=LocalUtilis.language("message.end",clientLocale)%> :</td><!--到-->
		<td><INPUT TYPE="text" NAME="end_trade_date_picker" class=selecttext
			value="<%=Format.formatDateLine(end_trade_date)%>"> <INPUT
			TYPE="button" value="▼" class=selectbtn
			onclick="javascript:CalendarWebControl.show(theform.end_trade_date_picker,theform.end_trade_date_picker.value,this);"
			tabindex="13"> <INPUT TYPE="hidden" NAME="end_trade_date"></td>
	</tr>
	<tr>
		<td align="center" colspan="4">
		<button type="button"  class="xpbutton3" accessKey=s name="btnQuery"
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>S</u>)</button><!--确定-->
		&nbsp;&nbsp;</td>
	</tr>
</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="2" class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
						<%if (input_operator.hasFunc(menu_id, 108)) {%>
						<button type="button"  class="xpbutton3" accessKey=q id="queryButton"
							name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
						&nbsp;&nbsp;&nbsp; <%}
						if (input_operator.hasFunc(menu_id, 100)) {%>
						<!--新建评级--><!--评级-->
						<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" 
                            title='<%=LocalUtilis.language("message.newRating",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.rating",clientLocale)%> (<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}%> <%if (input_operator.hasFunc(menu_id, 101)) {%>
                        <!--删除所选记录--><!--删除-->
						<button type="button"  class="xpbutton3" accessKey=d id="btnCancel"
							name="btnCancel" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> '
                            onclick="javascript:if(confirmRemove(document.theform.serial_no)) {  document.theform.submit();}"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
						<%}%>
						</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"class="tablelinecolor" width="100%" sort="multi">
					<tr class="trh">
						<td align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox"
							onclick="javascript:selectAll(document.theform.serial_no);"> <%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--序号-->
						<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
						<td align="center"><%=LocalUtilis.language("class.gradeLevel5",clientLocale)%> </td><!--对应等级-->
						<td><%=LocalUtilis.language("class.oldGradeLevel",clientLocale)%> </td><!--原级别-->
						<td><%=LocalUtilis.language("class.totalValue",clientLocale)%> </td><!--最终得分-->
						<td><%=LocalUtilis.language("class.trade_date",clientLocale)%> </td><!--评分日期-->
						<td><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!--审核标志-->
						<td><%=LocalUtilis.language("message.view",clientLocale)%> </td><!--查看-->
						<TD <%if(isproject!=1) out.print("style='display:none;'");%>><%=LocalUtilis.language("message.auditRecord",clientLocale)%> </TD><!--审批记录-->
					</tr>
					<%
					int iCount = 0;
					int iCurrent = 0;
					while (it.hasNext()) {
						map = (Map)it.next();
						String checkFlag="未审核";
						if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).equals(new Integer(2))){
							checkFlag="审核中";
						}
						if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0)).equals(new Integer(3))){
							checkFlag="审核通过";
						}
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td width="10%">
							<table border="0" width="20%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%"><input type="checkbox" name="serial_no"
									value="<%=map.get("SERIAL_NO")%>" class="flatcheckbox"></td>
									<td width="90%" align="left"><%=map.get("SERIAL_NO")%></td>
								</tr>
							</table>
						</td>
						<td align="left"><%=Utility.trimNull(Argument.getCustomerName(new Integer(map.get("CUST_ID").toString()), input_operatorCode))%></td>
						<td align="left"><%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("OLD_GRADE_LEVEL_NAME"))%></td>
						<td align="left"><%=Format.formatMoney0(Utility.stringToDouble(Utility.trimNull(map.get("TOTAL_VALUE").toString())))%></td>
						<td align="left"><%=Format.formatDateCn(new Integer(Utility.trimNull(map.get("TRADE_DATE"))))%></td>
						<td align="left"><%=Utility.trimNull(checkFlag)%></td>
						<td align="center">
							<button type="button"  class="xpbutton2" name="btnsetinital"	onclick="javascript:setiteminfor(tr<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>,tablePro<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>,document.theform.flag<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>,document.theform.image<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>);return false;">
								<IMG id="image<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif" width="7" height="9"></button>
							<input type="hidden" name="flag<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>" value="0">
						</td>
						<td align="center" <%if(isproject!=1) out.print("style='display:none;'");%>>
							<%if(Utility.parseInt(Utility.trimNull(map.get("PROBLEMID")),0) != 0){
						   	gt_vo.setProblem_id(new Integer(map.get("PROBLEMID").toString()));
						   	gt_vo.setSerial_no(new Integer(map.get("SERIAL_NO").toString()));
						   	//获得事物
						   	List pro_list = gt_local.queryProjectID(gt_vo);
						 	Map pro_map = (Map)list.get(0);
						 	%>
						 	<button type="button"  class="xpbutton2" onclick="javascript:showprojectinfo('<%=map.get("PROBLEMID")%>','<%=pro_map.get("PROJECTID")%>','2');">&gt;&gt;</button>
						 	<%}%>		
						</td>	
					</tr>	
					<!--详细评分-->			 
					<tr id="tr<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>" style="display: none">
							<td align="center" bgcolor="#FFFFFF" colspan="10" >
								<table class="tablelinecolor-inner" id="tablePro<%=map.get("SERIAL_NO")%><%=map.get("TRADE_DATE")%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;">
										<td bgcolor="" align="center"><%=LocalUtilis.language("class.levelID2",clientLocale)%> </td><!--类别-->
										<td bgcolor="" align="center"><%=LocalUtilis.language("class.indexName",clientLocale)%> </td><!--细则名称-->
										<td bgcolor="" align="center"><%=LocalUtilis.language("class.stValue",clientLocale)%> </td><!--标准值-->
										<td bgcolor="" align="center"><%=LocalUtilis.language("class.actualValue",clientLocale)%> </td>	<!--实际值-->
										<td bgcolor="" align="center"><%=LocalUtilis.language("class.deValue",clientLocale)%> </td><!--得分-->																												
									</tr>
									<%
									gd_vo.setCust_id(Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),  new Integer(0)));
									gd_vo.setGrade_id(Utility.parseInt(Utility.trimNull(map.get("GRADE_ID")),  new Integer(0)));
									gd_vo.setTrade_date(Utility.parseInt(Utility.trimNull(map.get("TRADE_DATE")),  new Integer(0)));//评分日期
									//根据结果获得评级等分明细
									List gd_list = gd_local.queryGradeDetail(gd_vo);
									Map gd_map = new HashMap();
									Map gi_map = new HashMap();
									Iterator gd_it = gd_list.iterator();
									BigDecimal total_value = new BigDecimal(0);//总分
									while(gd_it.hasNext())
									{
										gd_map = (Map)gd_it.next();
										gi_vo.setIndex_id(Utility.parseInt(Utility.trimNull(gd_map.get("INDEX_ID")), new Integer(0)));
										gi_vo.setGrade_id(Utility.parseInt(Utility.trimNull(gd_map.get("GRADE_ID")), new Integer(0)));
										gi_vo.setValue_flag(new Integer(0));//值来源
										gi_vo.setValid_flag(new Integer(0));//是否有效
										//获得评分标准细则
										List gi_list = gi_local.queryGradeIndex(gi_vo);
										String value_flag_str = "否";//实际值
										if(gi_list!=null&&gi_list.size()>0){
											gi_map = (Map)gi_list.get(0);
											total_value = total_value.add(gd_map.get("DF_VALUE") == null ? new BigDecimal(0) : Utility.stringToDouble(Utility.trimNull(gd_map.get("DF_VALUE"))));//总得分
											
											if(Utility.parseInt(Utility.trimNull(gi_map.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(1)))
												value_flag_str = Utility.stringToDouble(Utility.trimNull(gd_map.get("SJ_VALUE"))).setScale(2).toString();
											if(Utility.parseInt(Utility.trimNull(gi_map.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(2)))
												value_flag_str = "计算通过";
											if(Utility.parseInt(Utility.trimNull(gi_map.get("VALUE_FLAG")), new Integer(0)).equals(new Integer(3)) && Utility.stringToDouble(Utility.trimNull(gd_map.get("SJ_VALUE"))).signum() == 1)
												value_flag_str= "是";
										}

									%>
									<tr style="background:F7F7F7;">
										<td bgcolor="" align="left"><%=Utility.trimNull(gi_map.get("INDEX_TYPE_NAME"))%></td>
										<td bgcolor="" align="left"><%=Utility.trimNull(gi_map.get("INDEX_NAME"))%></td>										
										<td bgcolor="" align="center"><%=Format.formatDouble(new BigDecimal(Utility.trimNull(gi_map.get("ST_VALUE"))))%></td>
										<td bgcolor="" align="center"><%=Utility.trimNull(value_flag_str)%></td>	
										<td bgcolor="" align="right"><%=Format.formatDouble(new BigDecimal(Utility.trimNull(gd_map.get("DF_VALUE"))))%></td>										
									</tr>
									<%}%>
									<tr class="trbottom">	
										<td  align="center"><%=LocalUtilis.language("message.total",clientLocale)%> </td><!--合计-->
										<td align="center"></td>
										<td  align="right"></td>
										<td  align="right"></td>										
										<td align="right"><%=Format.formatDouble(total_value)%></td>
									</tr>
								</table>
							</td>
					</tr>
					<%
					iCurrent++;
					iCount++;}
					
					for(; iCurrent<pageList.getPageSize(); iCurrent++){%>
					
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center" <%if(isproject!=1) out.print("style='display:none;'");%>></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计-->
                        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td><!--项-->
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>

