<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*, enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.webreport.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<jsp:useBean id="cell" scope="page"class="enfo.crm.webreport.CellSet" />
<%
int delflag = Utility.parseInt(request.getParameter("delflag"),0);
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
Integer rpt_id = Utility.parseInt(request.getParameter("rpt_id"),null);
CellVO vo = new CellVO();
List cellList = null;
if(serial_no != null && serial_no.intValue()!=0)
{
	vo.setSerial_no(serial_no);
	vo.setRpt_id(rpt_id);
	cellList = cell.listReportInfo(vo);
}
boolean bSuccess = false;
if (request.getMethod().equals("POST") )
{ if( delflag != 2){
	vo.setSerial_no(Utility.parseInt(request.getParameter("serial_no"),null));
	vo.setRpt_id(Utility.parseInt(request.getParameter("rpt_id"),null));
	vo.setItem_id(Utility.parseInt(request.getParameter("item_id"),null));
	vo.setItem_flag(Utility.parseInt(request.getParameter("item_flag"),null));
	vo.setItem_name(request.getParameter("item_name"));
	vo.setLine_no(request.getParameter("line_no"));
	vo.setFlag(Utility.parseInt(request.getParameter("flag"),null));
	vo.setSum_no(Utility.parseInt(request.getParameter("sum_no"),new Integer(0)));	
	vo.setSub_code1(request.getParameter("sub_code1"));
	vo.setSub_code3(request.getParameter("sub_code3"));	
	vo.setDirection(Utility.parseInt(request.getParameter("direction"),new Integer(0)));	
	vo.setBalance1(Utility.parseDecimal(request.getParameter("balance1"),null));
	vo.setBalance2(Utility.parseDecimal(request.getParameter("balance2"),null));
	vo.setBalance3(Utility.parseDecimal(request.getParameter("balance3"),null));
	vo.setBalance4(Utility.parseDecimal(request.getParameter("balance4"),null));
	vo.setInput_man(input_operatorCode);
	vo.setInsert_flag(Utility.parseInt(request.getParameter("insert_flag"),new Integer(0)));
	
	Utility.debugln("is_common："+request.getParameter("is_common"));
	
	vo.setIs_common(Utility.parseInt(request.getParameter("is_common"),new Integer(0)));
	vo.setIs_positive(Utility.parseInt(request.getParameter("is_positive"),new Integer(1)));
	if(serial_no !=null && serial_no.intValue()!=0)
	    cell.modiReportInfo(vo,input_operatorCode);
	else
	   cell.addReportInfo(vo,input_operatorCode);
	bSuccess = true;
}
	else {
		vo.setSerial_no(Utility.parseInt(request.getParameter("serial_no"),null));
		vo.setInput_man(input_operatorCode);
		cell.delete(vo,input_operatorCode);
		bSuccess = true;
	}
	
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>设置报表</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{if(delflag == 0){
%>
	window.returnValue = 1;
	<%}else{%>
	window.returnValue = 2;	<%}%>
	window.close();
<%}%>
function validateForm(form)
{	
	if(!sl_checkNum1(form.item_id, "行号",10, 1))		return false;
    if(!sl_checkChoice(form.item_flag, "列标志"))		return false;
	if(!sl_check(form.item_name, "项目名称",60, 0))		return false;	
	if(!sl_checkChoice(form.flag, "数据标志"))		return false;
    if(!sl_checkChoice(form.direction, "加减标志"))		return false;
    if(form.is_common.checked==true)
      form.is_common.value=1;
    else
      form.is_common.value=0;
      
   // alert(form.is_common.value);  
	if(form.flag.value =='3')
	{ if(!sl_check(form.sub_code1, "一级科目代码",6, 1))		return false;}
	return sl_check_update();
}

function getIsPositive(is_common){
 if(is_common.checked==true)
    document.theform.is_positive.disabled=false;
  else
    document.theform.is_positive.disabled=true; 
}
</script>
</HEAD>
<BODY class="body" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="setting_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="rpt_id" value="<%=rpt_id%>">
<input type=hidden name="delflag" value="<%=delflag%>">

<%
	 Integer item_id = new Integer(0);
	 Integer item_flag = new Integer(0);;
	 String item_name = "";
	 String line_no = "";
	 Integer flag = new Integer(0); //标志: 1 固定值 2 空值  3 取财务值 4 合计项
	 Integer sum_no = new Integer(0);//合计填充行0 无关 > 0 加到该行,  < 0 减到该行 
	 String sub_code1 = "";
	 String sub_code3 = "";
	 Integer direction = new Integer(0);;
	 java.math.BigDecimal balance1 = new java.math.BigDecimal(0.0);
	 java.math.BigDecimal balance2 = new java.math.BigDecimal(0.0);
	 java.math.BigDecimal balance3 = new java.math.BigDecimal(0.0);
	 java.math.BigDecimal balance4 = new java.math.BigDecimal(0.0);
	 Integer insert_flag = new Integer(0);
	 String is_common_check="";
	 Integer is_positive = new Integer(1);
	 String  is_positive_disabled="";
	 Map cellMap = null;
	 if(cellList!=null&&cellList.size()>0)
	 	cellMap = (Map)cellList.get(0);
	 else
	 	cellMap = new HashMap();
	 	
	serial_no = Utility.parseInt(Utility.trimNull(cellMap.get("SERIAL_NO")),new Integer(0));
	rpt_id	 = Utility.parseInt(Utility.trimNull(cellMap.get("RPT_ID")),new Integer(0));
	item_id = Utility.parseInt(Utility.trimNull(cellMap.get("ITEM_ID")),new Integer(0));
	item_flag = Utility.parseInt(Utility.trimNull(cellMap.get("ITEM_FLAG")),new Integer(0));
	item_name = Utility.trimNull(cellMap.get("ITEM_NAME"));
	line_no = Utility.trimNull(cellMap.get("LINE_NO"));  
	flag = Utility.parseInt(Utility.trimNull(cellMap.get("FLAG")),new Integer(0)); 
	sum_no = Utility.parseInt(Utility.trimNull(cellMap.get("SUM_NO")),new Integer(0));
	sub_code1 = Utility.trimNull(cellMap.get("SUB_CODE1"));
	sub_code3 = Utility.trimNull(cellMap.get("SUB_CODE3"));
	direction = Utility.parseInt(Utility.trimNull(cellMap.get("DIRECTION")),new Integer(0));
	balance1 = Utility.parseDecimal(Utility.trimNull(cellMap.get("BALANCE1")),new java.math.BigDecimal(0));
	balance2 = Utility.parseDecimal(Utility.trimNull(cellMap.get("BALANCE2")),new java.math.BigDecimal(0));
	balance3 = Utility.parseDecimal(Utility.trimNull(cellMap.get("BALANCE3")),new java.math.BigDecimal(0));
	balance4 = Utility.parseDecimal(Utility.trimNull(cellMap.get("BALANCE4")),new java.math.BigDecimal(0));
	insert_flag = Utility.parseInt(Utility.trimNull(cellMap.get("INSERT_FLAG")),new Integer(0));
	//is_common_check=((Boolean)cellMap.get("IS_COMMON")).booleanValue()?"checked":"";
	//is_positive=((Boolean)cellMap.get("IS_POSITIVE")).booleanValue()?new Integer(1):new Integer(0);
	if(!is_common_check.equals("checked"))
	  is_positive_disabled="disabled";
%>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><b>自定义项设置</b></td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
	</table>
	<table border="0" width="100%" cellspacing="3">
		<tr>
			  <td align="right" noWrap><font color="red">*</font>行号</td>
			  <TD>
			      <input onkeydown="javascript:nextKeyPress(this)" size="20" name="item_id" value="<%=Utility.trimNull(item_id)%>">
			  </TD>		
			  <td align="right" noWrap><font color="red">*</font>列标志</td>
			  <TD>
			       <select size="1" name="item_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Utility.trimNull(cell.getitemFlagOptions(item_flag))%>
				   </select>
			  </TD>		
	    </tr>
	    <tr>
		      <td align="right" noWrap>项目名称</td>
		      <TD colspan="3">
		          <input onkeydown="javascript:nextKeyPress(this)" style="width:395px" name="item_name" value="<%=Utility.trimNull(item_name)%>">
		      </TD>		
	    </tr>
	    <tr>
			  <td align="right">行次</td>
		      <TD>
		           <input onkeydown="javascript:nextKeyPress(this)" size="20" name="line_no" value="<%=Utility.trimNull(line_no)%>">
		      </TD>		
			  <td align="right" noWrap><font color="red">*</font>标志</td>
		      <TD>
		          <select size="1" name="flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					  <%=cell.getFlagOptions(flag)%>
				   </select>
			  </TD>		
	     </tr>
		 <tr>
			  <td align="right" noWrap>合计行号</td>
		      <TD>
		         <input onkeydown="javascript:nextKeyPress(this)" size="20" name="sum_no" value="<%=Utility.trimNull(sum_no)%>">
		      </TD>
	          <td align="right"><font color="red">*</font>加减标志</td>
		      <TD>
		          <select size="1" name="direction" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=cell.getDirectOptions(direction)%>
				</select>
			  </TD>		
	     </tr>
	     <tr>
			  <td align="right" noWrap>一级科目代码</td>
		      <TD>
		          <input onkeydown="javascript:nextKeyPress(this)" size="20" name="sub_code1" value="<%=Utility.trimNull(sub_code1)%>">
		      </TD>		
			<td align="right" noWrap>二级科目代码及以上</td>
		    <TD>
		       <input onkeydown="javascript:nextKeyPress(this)" size="20" name="sub_code3" value="<%=Utility.trimNull(sub_code3)%>">
		     </TD>		
	     </tr>
	     <tr>
			<td align="right" noWrap>共同类标志</td>
		    <TD>
		      <INPUT class="flatcheckbox"	type="checkbox" name="is_common" onclick="javascript:getIsPositive(this);" <%=is_common_check%> >
		   </TD>
		    <td align="right" noWrap>体现条件</td>
		      <TD>
		          <select size="1" name="is_positive" <%=is_positive_disabled%> onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=cell.getIsPositiveOptions(is_positive)%>
				</select>
			  </TD>		
	    </tr>
	
	    <tr>
			 <td align="right" noWrap>固定值1</td>
		    <TD>
		        <input onkeydown="javascript:nextKeyPress(this)" size="20" name="balance1" value="<%=Utility.trimNull(balance1)%>">
		    </TD>		
			<td align="right" noWrap>固定值2</td>
		    <TD>
		         <input onkeydown="javascript:nextKeyPress(this)" size="20" name="balance2" value="<%=Utility.trimNull(balance2)%>">
		    </TD>		
	   </tr>
	   <tr>
			<td align="right" noWrap>固定值3</td>
		    <TD>
		         <input onkeydown="javascript:nextKeyPress(this)" size="20" name="balance3" value="<%=Utility.trimNull(balance3)%>">
		    </TD>		
			<td align="right" noWrap>固定值4</td>
		    <TD>
		        <input onkeydown="javascript:nextKeyPress(this)" size="20" name="balance4" value="<%=Utility.trimNull(balance4)%>">
		    </TD>		
	  </tr>
	  <%if(serial_no==null || serial_no.intValue()==0){%>
	  <tr>
			<td align="right" >插入标志</td>
		   <TD>
		      <INPUT class="flatcheckbox"	type="checkbox" name="insert_flag" value="1" >
		   </TD>		
	  </tr>
	  <%}%>
	</table>
	<br>
	<table border="0" width="100%">
		<tr>
			<td align="right">	
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">保存(<u>S</u>)</button>
								&nbsp;&nbsp;
								<%if(serial_no != null && serial_no.intValue()!=0)
{%>
								<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" onclick="javascript:document.theform.delflag.value=2;if(sl_confirm('删除记录'))document.theform.submit();">删除(<u>D</u>)</button>
								&nbsp;&nbsp;<%}%>
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
								&nbsp;&nbsp;
				</td>
		</tr>
	</table>
</form>
</BODY>
</HTML>

