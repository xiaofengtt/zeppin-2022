<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer startTime =Utility.parseInt(request.getParameter("startTime"),new Integer(0));
Integer endTime = Utility.parseInt(request.getParameter("endTime"),new Integer(0));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),new Integer(0));
Integer flag = Utility.parseInt(request.getParameter("flag"),new Integer(2)); // 1按日 2按月 3按年
Integer q_stat_scope =Utility.parseInt(request.getParameter("q_stat_scope"), new Integer(0));

sUrl += "&startTime="+startTime+"&endTime="+endTime+"&q_productId="+q_productId+"&flag="+flag+"&q_stat_scope="+q_stat_scope;

//页面用辅助变量
input_bookCode = new Integer(1);

CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
CustomerStatVO vo = new CustomerStatVO();

//设置参数
vo.setProductId(q_productId);
vo.setStartTime(startTime);
vo.setEndTime(endTime);
vo.setStatScope(q_stat_scope);
vo.setChangFlag(flag);
vo.setInputMan(input_operatorCode);
String[] totalColumn = new String[0];
IPageList pageList = custStatLocal.getPageVolueStat(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

custStatLocal.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.rgMoneyAnalyse",clientLocale)%> </TITLE><!--客户认购金额分析-->
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
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js'></script>
<SCRIPT LANGUAGE="javascript">
function QueryAction() {
	disableAllBtn(true);		

	syncDatePicker(theform.birthday_picker, theform.startTime);
	syncDatePicker(theform.endTime_picker, theform.endTime);	
	document.theform.submit();
}

function refreshPage() {	
	syncDatePicker(theform.birthday_picker, theform.startTime);
	syncDatePicker(theform.endTime_picker, theform.endTime);

	var _flag;
	for (var i=0; i<document.theform.flag.length; i++)
		if (document.theform.flag[i].checked) {
			_flag = document.theform.flag[i].value;
			break;
		}

	var _q_stat_scope;
	for (var i=0; i<document.theform.q_stat_scope.length; i++)
		if (document.theform.q_stat_scope[i].checked) {
			_q_stat_scope = document.theform.q_stat_scope[i].value;
			break;
		}

	disableAllBtn(true);
	location.href = 'client_volume_stat.jsp?page=1&pagesize=' + document.theform.pagesize.value 
		+ '&flag=' + _flag + '&q_stat_scope=' + _q_stat_scope
		+ '&startTime=' + document.theform.startTime.value
		+ '&startTime=' + document.theform.startTime.value
		+'&q_productId='+document.theform.q_productId.value;
}

var j$ = jQuery.noConflict();
function filterProduct(product_name) {
    if(event.keyCode==13){    
        j$("[name='q_productId']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='q_productId']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='q_productId']").children(":not([text*='"+product_name+"'])").remove();
    }else{
        return false;    
    }           
}

function productFilter(product_name) {    
        j$("[name='q_productId']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='q_productId']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='q_productId']").children(":not([text*='"+product_name+"'])").remove();      
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="client_volume_stat.jsp">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>

<div id="headDiv" style="margin-left:20px;margin-right:20px;margin-top:5px;">
		<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
				<tr style="background:F7F7F7;">
					<td colspan="2">产品名称:
						<input type="text"  name="product_name" value="<%//=product_name %>" size="35" onkeydown="javascript:filterProduct(this.value);nextKeyPress(this)" >
						<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:productFilter(document.theform.product_name.value);" /></button>
					</td>
				</tr>
				<tr style="background:F7F7F7;">
					<td colspan="2">
						<select size="1" name="q_productId" id="q_productId" onkeydown="javascript:nextKeyPress(this)" style="width: 440px;">					
							<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
						</select>
						<SELECT name="all_product_id" style="display:none" style="width: 440px;">
							<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
						</SELECT>						
					</td>
				</tr>
				<tr style="background:F7F7F7;">
					<td>
						<input type="radio" class="flatcheckbox" name="q_stat_scope" value="0" <%=q_stat_scope.intValue()==0?"checked":""%> />全部&nbsp;&nbsp;&nbsp;
						<input type="radio" class="flatcheckbox" name="q_stat_scope" value="1" <%=q_stat_scope.intValue()==1?"checked":""%> />本人&nbsp;&nbsp;&nbsp;
						<input type="radio" class="flatcheckbox" name="q_stat_scope" value="2" <%=q_stat_scope.intValue()==2?"checked":""%> />本部门
					</td>
					<td>
						<input type="radio" class="flatcheckbox" name="flag" value="1" <%=flag.intValue()==1?"checked":""%> />按日&nbsp;&nbsp;&nbsp;
						<input type="radio" class="flatcheckbox" name="flag" value="2" <%=flag.intValue()==2?"checked":""%> />按月&nbsp;&nbsp;&nbsp;
						<input type="radio" class="flatcheckbox" name="flag" value="3" <%=flag.intValue()==3?"checked":""%> />按年				
					</td>
				</tr>						
				<tr style="background:F7F7F7;">
					<td>
						<%=LocalUtilis.language("class.startTime",clientLocale)%>:<!-- 开始时间 -->
						<INPUT TYPE="text" NAME="birthday_picker" class=selecttext size="40" onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(startTime)%>"/>
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.birthday_picker,theform.birthday_picker.value,this);" tabindex="13"/>
						<INPUT TYPE="hidden" NAME="startTime" value=""/>
						
					</td>	
					<td>
						
						<%=LocalUtilis.language("class.endTime",clientLocale)%>:<!-- 结束时间 -->
						<INPUT TYPE="text" NAME="endTime_picker" class=selecttext	 
				      		  onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(endTime) %>">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.endTime_picker,theform.endTime_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="endTime"   value="">
						<button type="button"  class="xpbutton3" accessKey=q id="btnSave" name="btnSave" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
					</td>
				</tr> 
		</table>
</div>

<div id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;">
<table width="100%" border="0">
	<tr>
		<td width="50%">
			<table border="0" width="100%">
			<tr><td align="right"><font color="red">单位:(个)</font><td></tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2"
								class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td align="center">日期</td>
						<td align="center">增加客户量</td>
						<td align="center">事实客户</td>
						<td align="center">潜在客户</td>
					</tr>
			<%
			int iCurrent = 0;
			List list = pageList.getRsList();
			int total_count_all = 0;
			int total_1_all = 0;
			int total_2_all = 0;

			for(int i=0;i<list.size();i++) {
			    Map map = (Map)list.get(i);
				String view_date = Utility.trimNull(map.get("DATA"));
				int total_count = Utility.parseInt(Utility.trimNull(map.get("TOTAL_COUNT")),0);
				int total_1 = Utility.parseInt(Utility.trimNull(map.get("TOTAL_1")),0);
				int total_2 = Utility.parseInt(Utility.trimNull(map.get("TOTAL_2")),0);
				total_count_all += total_count;
				total_1_all	+= total_1;
				total_2_all	+= total_2;
			%>
			
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"><%=view_date %></td>
						<td align="right">&nbsp;&nbsp;<%=total_count%></td>
						<td align="right">&nbsp;&nbsp;<%=total_1%></td>
						<td align="right">&nbsp;&nbsp;<%=total_2%></td>
					</tr>
			<%
			iCurrent++;
			}
			
			for (int i=0;i < pageList.getBlankSize(); i++) {
			%>
				<tr class="tr<%=(i % 2)%>">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			<%}
			%>
				<tr class="trbottom">
					<td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
		</td>
		<td>
			<table border="0" align="left" cellspacing="5" cellpadding="5">
				<tr>
					<td align="left"><font style="background-color: green;">&nbsp;&nbsp;&nbsp;&nbsp;</font>&nbsp;新增客户量</td>
					<td align="left"><font style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;<%=total_count_all%></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="left"><font style="background-color: fuchsia;">&nbsp;&nbsp;&nbsp;&nbsp;</font>&nbsp;事实客户量</td>
					<td align="left"><font style="color: red;">&nbsp;&nbsp;&nbsp;&nbsp;<%=total_1_all%></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="left"><font style="background-color: olive;">&nbsp;&nbsp;&nbsp;&nbsp;</font>&nbsp;潜在客户量</td>
					<td align="left"><font style="color:  red;">&nbsp;&nbsp;&nbsp;&nbsp;<%=total_2_all%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>