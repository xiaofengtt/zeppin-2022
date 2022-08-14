<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.service.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String sQuery = Utility.trimNull(request.getParameter("sQuery"));

String custNo = Utility.trimNull(request.getParameter("custNo"));
String custName = Utility.trimNull(request.getParameter("custName"));
String custId="";
String serviceTime="";
String serviceInfo=Utility.trimNull(request.getParameter("serviceInfo"));
String serviceSummary="";
String serviceManName="";
String executorName="";
String dataFlag=Utility.trimNull(request.getParameter("dataFlag"));
String serial_no="";
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")),new Integer(0));
Integer executor = Utility.parseInt(Utility.trimNull(request.getParameter("executor")),new Integer(0));
boolean bSuccess=false;

if(sQuery.length()>0){
	String[] condition = Utility.splitString(sQuery,"$");

	if(condition.length==6){
		custNo = condition[0];
		custName = condition[1];
		serviceInfo = condition[2];
		dataFlag = condition[3]; 
		service_man = Utility.parseInt(condition[4],new Integer(0));
		executor =  Utility.parseInt(condition[5],new Integer(0));
	}
}

sQuery = custNo+" $ "+custName+" $ "+serviceInfo+" $"+dataFlag+"$ "+service_man+" $ "+executor;

//用于查询客户维护记录
CustomerVO vo = new CustomerVO();
CustomerLocal local = EJBFactory.getCustomer();
vo.setCust_name(custName);
vo.setCust_no(custNo);
vo.setService_info(serviceInfo);
vo.setData_flag(dataFlag);
vo.setService_man(service_man);
vo.setExecutor(executor);
vo.setInput_man(input_operatorCode);
IPageList pageList = local.getCustomerMaintenanceRecord(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&custNo=" + custNo+"&custName="+custName ;
//用于补全人工维护记录的客户信息
CustomerLocal customer = EJBFactory.getCustomer();//客户
CustomerVO c_vo = new CustomerVO();
//用于删除人工维护记录
CustServiceLogVO custServiceLogVO=new CustServiceLogVO();
CustServiceLogLocal custServiceLogLocal=EJBFactory.getCustServiceLog();
if (request.getMethod().equals("POST")){
	Integer sno=Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
	custServiceLogVO.setSerialNo(sno);
	custServiceLogLocal.delete(custServiceLogVO);
	bSuccess = true;
}

//数据类别选项
StringBuffer sb = new StringBuffer();
sb.append("<option value=\"\">请选择</option>");
Argument.appendOptions(sb, "人工", "人工", dataFlag);
Argument.appendOptions(sb, "系统", "系统", dataFlag);
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
<%if(bSuccess){%>
	sl_alert("删除成功!");  
	location = 'service_list.jsp';
<%}%>
var oState = {
		
	}
function $$(_name){
	return document.getElementsByName(_name)[0];
}


function refreshPage()
{
	disableAllBtn(true);
	
	location = 'service_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
												+'&custNo=' + document.theform.custNo.value
												+'&custName='+document.theform.custName.value
												+'&serviceInfo='+document.theform.serviceInfo.value
												+'&dataFlag='+document.theform.dataFlag.value
												+'&service_man='+document.theform.service_man.value
												+'&executor='+document.theform.executor.value;
}



window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

/*新增*/
function newInfo(){	
	disableAllBtn(true);
	location="service_add.jsp?sQuery=<%=sQuery%>";
}

/*修改*/
function ModiAction(custId,serial_no){
	//var _event = window.event.srcElement;
	var url = "service_edit.jsp?sQuery=<%=sQuery%>&cust_id="+custId+"&serial_no="+serial_no;
	location = url;
	//oState.flag = "edit";
	//oState.data = eval("("+_event.getAttribute("lds")+")");
	//if(showModalDialog(url,oState, 'dialogWidth:800px;dialogHeight:600px;status:0;help:0')){
	//	sl_update_ok();
	//	location.reload();
	//}		
}
//删除
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "bespeak_del.jsp";
		document.theform.submit();
	}
}
function DelSelfAction(serial_no){
	if(sl_check_remove()){
		disableAllBtn(true);
		document.theform.action = "service_list.jsp?serial_no="+serial_no;
		document.theform.submit();
	}
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" >
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
					<td align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> : </td><!--客户编号-->
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="custNo" size="15" value="<%=Utility.trimNull(custNo)%>">&nbsp;&nbsp;
					</td>		
					<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
					<td><input type="text" name="custName" size="15" value="<%=custName%>" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td>
					<td>
						<select name="service_man" style="width: 110px;">
							<%=Argument.getManager_Value(service_man)%>
						</select>
					</td>
					<td align="right">执行人 :</td>
					<td>
						<select name="executor" style="width: 110px;">
							<%=Argument.getManager_Value(executor)%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.ServiceProject",clientLocale)%> :</td><!--服务项目-->
					<td ><INPUT type="text" name="serviceInfo" size="15" value="<%=serviceInfo%>"></td>
					<td align="right">类别 :</td>
					<td>
						<select name="dataFlag" style="width: 110px;">
							<%=sb.toString()%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						&nbsp;&nbsp;<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;			
					</td><!--确认-->
				</tr>			
			</table>
		</div>


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>


	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
							<button type="button"  class="xpbutton3" accessKey=n id="appendButton" onclick="javascript:newInfo();" name="appendButton"<%if(!input_operator.hasFunc(menu_id, 100)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--新建-->
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td><input type="checkbox" name="btnCheckbox" class="selectAllBox"
				onclick="javascript:selectAllBox(document.theform.cust_id,this);"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--客户编号-->
						<td><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
						<td><%=LocalUtilis.language("class.executeTime2",clientLocale)%> </td><!--服务、维护时间-->
						<td><%=LocalUtilis.language("class.ServiceProject",clientLocale)%> </td><!--服务项目-->
						<td><%=LocalUtilis.language("class.taskDescription",clientLocale)%> </td><!--详细描述-->
						<td><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--客户经理-->
						<td><%=LocalUtilis.language("class.executor",clientLocale)%> </td><!--服务实际执行人-->
						<td><%=LocalUtilis.language("class.levelID2",clientLocale)%></td> 
						<td><%=LocalUtilis.language("message.update",clientLocale)%></td>
						<td><%=LocalUtilis.language("message.delete",clientLocale)%></td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	custId=Utility.trimNull(map.get("CUST_ID"));
	custNo = Utility.trimNull(map.get("CUST_NO"));
	custName = Utility.trimNull(map.get("CUST_NAME"));
	serviceTime=Utility.trimNull(map.get("SERVICE_TIME"));
	serviceInfo=Utility.trimNull(map.get("SERVICE_INFO"));
	serviceSummary=Utility.trimNull(map.get("SERVICE_SUMMARY"));
	serviceManName=Utility.trimNull(map.get("SERVICE_MAN_NAME"));
	executorName=Utility.trimNull(map.get("EXECUTOR_NAME"));
	dataFlag=Utility.trimNull(map.get("DATA_FLAG"));
	serial_no=Utility.trimNull(map.get("SERIAL_NO"));
	dataFlag=Utility.trimNull(map.get("DATA_FLAG"));
	if(dataFlag.equals("人工")){
		//客户信息详细
			List rsList_cust = null;
			Map map_cust = null;
			//Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("custId")),new Integer(0));	
			Integer cust_id = (Integer)map.get("CUST_ID");
			//客户单个值		
			c_vo.setCust_id(cust_id);
			c_vo.setInput_man(input_operatorCode);
			rsList_cust = customer.listByControl(c_vo);
					
			if(rsList_cust.size()>0){
				map_cust = (Map)rsList_cust.get(0);
				custName = Utility.trimNull(map_cust.get("CUST_NAME"));
			}
		}
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%"><input type="checkbox" name="cust_id"
										value="<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
									<td width="90%" align="center"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
								</tr>
							</table>
						</td>
						<td align="left"><%=custName%></td>
						<td align="left"><%=serviceTime%></td>
						<td align="left"><%=serviceInfo%></td>
						<td align="left"><%=serviceSummary%></td>
						<td align="left"><%=serviceManName%></td>
						<td align="left"><%=executorName%></td>
						<td align="left"><%=dataFlag%></td>
						<td align="center">
							<%if((input_operator.hasFunc(menu_id, 102))&&dataFlag.equals("人工")){%>
							  <img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" lds="{'serviceTime':'<%=serviceTime%>','serviceInfo':'<%=serviceInfo %>','serviceSummary':'<%=serviceSummary %>','serial_no':'<%=serial_no%>'}"  onclick="javascript:ModiAction('<%=custId%>','<%=serial_no%>');" style="cursor:hand;" title="<%=LocalUtilis.language("message.update",clientLocale)%> " width="20" height="15">
							<%}%> 
						</td>
						<td align="center"><%if((input_operator.hasFunc(menu_id, 101))&&dataFlag.equals("人工")){%>
										  <img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="center"title="<%=LocalUtilis.language("message.delete",clientLocale)%> "  style="cursor:hand;" width="20" height="15" 
	        		 onclick="DelSelfAction('<%=serial_no%>')" />
										<%}%> 
						</td>
					</tr>
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%local.remove();%>

