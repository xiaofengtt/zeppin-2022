<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*, enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.webreport.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<jsp:useBean id="cell" scope="page"
	class="enfo.crm.webreport.CellSet" />

<%String name = CellHelper.trimNull(request.getParameter("name"));
  Integer rpt_id = Utility.parseInt(request.getParameter("rpt_id"), null);
  Integer item_flag = Utility.parseInt(request.getParameter("item_flag"), new Integer(0));

  CellVO vo = new CellVO();
  vo.setRpt_id(rpt_id);
  vo.setItem_flag(item_flag);
  List cell_list = cell.listReportInfo(vo);


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

function refresh(){
  document.theform.submit();
}


function showInfo(i)
{
value = showModalDialog('setting_info.jsp?serial_no='+i+'&rpt_id='+document.theform.rpt_id.value, '', 'dialogWidth:530px;dialogHeight:410px;status:0;help:0');

	if( value != null)
	{	if(value==1){
		sl_update_ok();}
		else{
		sl_remove_ok();}
		document.theform.submit();
	}
}
</script>
</HEAD>
<BODY class="body"
	onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="setting.jsp"><input
	type=hidden name="rpt_id" value="<%=rpt_id%>">

<TABLE height="100%" cellSpacing=0 cellPadding=4 width="100%" border=0 align=center>
  <TR>
	<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
		   	<tr>
				<td align="center" colspan="2">&nbsp;</td>
			</tr>
			<tr>
			    <td align="left">
			    列标志:
			       <select size="1" name="item_flag" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getColsTypeOptions(item_flag)%>
				   </select>&nbsp;
				   <button type="button"  class="xpbutton3" accessKey=Q
					onclick="javascript:refresh();">查询(<u>Q</u>)</button>
			    </td>
				<td align="right">
				<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew"
					title="新建记录" onclick="javascript:showInfo(0);">新建(<u>N</u>)</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c
					onclick="javascript:window.returnValue=null;window.close();">关闭(<u>C</u>)</button>
				&nbsp;&nbsp;</td>
			</tr>
			
			<tr>
				<td align="center" colspan="2"><%=Utility.trimNull(name)%></td>
			</tr>
			<tr>
				<td colspan="2">
				    <hr noshade color="#808080" size="1">
				</td>
			</tr>
		</table>
		<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" align="center">
			<tr class="trh">
				<td noWrap>行号</td>
				<td noWrap>列标志</td>
				<td noWrap>项目名称</td>
				<td noWrap>行次</td>
				<td noWrap>数据标志</td>
				<td noWrap>合计行号</td>
				<td noWrap>加减标志</td>
				<td noWrap>一级科目</td>					
				<td noWrap>二级以上科目</td>
				<td noWrap>共同类</td>
				<td noWrap>体现条件</td>
			</tr>
						<!--从结果集中显示记录-->
		<%
		    
		    int iCurrent = 0;
		    Map rowMap=null;
		    
		    Integer item_id = new Integer(0); //行号
		    String  sitem_flag="";
		    String item_name="";
		    String line_no="";
		    String data_type="";
		    Integer sum_no=new Integer(0);
		    String derection="";
		    String sub_code1="";
		    String sub_code3="";
		    String is_common="";
		    String is_positive="";
			for (int i=0;i<cell_list.size();i++) {  
		     rowMap=(Map)cell_list.get(i);
		     item_id = Utility.parseInt(Utility.trimNull(rowMap.get("ITEM_ID")), new Integer(0));  
		     sitem_flag=(Utility.parseInt(Utility.trimNull(rowMap.get("ITEM_FLAG")), 0))==1?"左列":"右列"; 
		     item_name = Utility.trimNull(rowMap.get("ITEM_NAME")); 
		     line_no = Utility.trimNull(rowMap.get("LINE_NO")); 
		     int flag = Utility.parseInt(Utility.trimNull(rowMap.get("FLAG")), 0);  
		     if(flag==1)
		       data_type="固定值";
		     else  if(flag==2)
		       data_type="空值";
		     else  if(flag==3)
		       data_type="财务值";
		     else  if(flag==4)
		       data_type="合计项";
		     sum_no = Utility.parseInt(Utility.trimNull(rowMap.get("SUM_NO")), new Integer(0));  
		     derection=(Utility.parseInt(Utility.trimNull(rowMap.get("DERECTION")), 0))==1?"正数":"负数";
		     sub_code1 = Utility.trimNull(rowMap.get("SUB_CODE1")); 
		     sub_code3 = Utility.trimNull(rowMap.get("SUB_CODE3")); 
		     //is_common=((Boolean)rowMap.get("IS_COMMON")).booleanValue()?"是":"否";
		     //is_positive=((Boolean)rowMap.get("IS_POSITIVE")).booleanValue()?"是":"否";
		                                  
		%>
		
						<tr class="tr<%=(iCurrent % 2)%>">					
							<td width="10%" align="left" ><%=item_id%></td>
							<td width="10%" align="center" ><%=sitem_flag%></td>
							<td width="10%" align="left" >
							   <a href="javascript:showInfo(<%=Utility.trimNull(rowMap.get("SERIAL_NO"))%>);" class="index-menu-1">
							      <%=item_name%> 
							   </a>   
							</td>
							<td width="10%" align="left" ><%=line_no%></td>
							<td width="10%" align="left" ><%=data_type%></td>
							<td width="10%" align="left" ><%=sum_no%></td>
							<td width="10%" align="center"h ><%=derection%></td>
							<td width="10%" align="left" ><%=sub_code1%></td>								
							<td width="10%" align="left" ><%=sub_code3%></td>								
							<td width="10%" align="left" ><%=is_common%></td>								
							<td width="10%" align="left" ><%=derection%></td>																
							
						</tr>
		<% 
		    iCurrent++;    
		   }
		 %>					
		</table>
	</td>
  </tr>
</table>  		
<br>

</form>
</BODY>
</HTML>

