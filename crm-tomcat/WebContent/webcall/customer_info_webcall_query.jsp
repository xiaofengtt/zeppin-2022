<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.webcall.*,java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String webcallId = Utility.trimNull(request.getParameter("webcallId")); 
String queryCondition = Utility.trimNull(request.getParameter("queryCondition"));
//参数设置
String cust_name = "";
String cust_no = "";
Integer cust_type = new Integer(0);
String card_type = "";
String card_id = "";
String telephone = "";
Integer accountManager = new Integer(0);
//访客ID是否需要绑定 标志
int webcallFlag = 0;
//BEAN
TCrmWebCallLocal webcall_local = EJBFactory.getTCrmWebCall();
TCrmWebCallVO webcall_vo = new TCrmWebCallVO();

if(queryCondition.length()>0){
	 String[] conditionArray = Utility.splitString(queryCondition,"|");
	 cust_name = conditionArray[1];
	 cust_no = conditionArray[2];
	 cust_type = Utility.parseInt(conditionArray[3],new Integer(0));
	 card_type = conditionArray[4];
	 card_id = conditionArray[5]; 
	 telephone = conditionArray[6]; 
	 accountManager =  Utility.parseInt(conditionArray[7],new Integer(0));
}

//查看访客ID是否需要绑定
webcall_vo = new TCrmWebCallVO();
webcall_vo.setWebcallId(webcallId);

List rsList = webcall_local.listCrmWebcall(webcall_vo);
if(rsList!=null&&rsList.size()>0){
	webcallFlag = 1 ;
}
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/styles/common.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/webcall/webcall.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_zh_CN.js"></SCRIPT>

<script language=javascript>
function queryAction(){
	var queryCondition = getQueryCondition();
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_list.jsp?flag=1&webcallId=<%=webcallId%>&queryCondition="+queryCondition;
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}

function getQueryCondition(){
	var condition = "1|";//如果cust_name为空 就会不对
	condition = condition + document.theform.cust_name.value+"|"+
				document.theform.cust_no.value+"|"+
				document.theform.cust_type.value+"|"+
				document.theform.card_type.value+"|"+
				document.theform.card_id.value+"|"+
				document.theform.telephone.value+"|"+
				document.theform.accountManager.value+"|";
	return condition;
}

function backAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_1.jsp?webcallId=<%=webcallId%>";
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
</script>
</HEAD>
<BODY class="BODY2" style="overflow-y: hidden;">
<form name="theform">
<div align="center"  class="topDiv">
	<font color="#208020" size="3">客户快搜</font>&nbsp;&nbsp;<br>
	<%if(webcallFlag==0){%><font color="red">该访客无对应客户信息</font><%}%>
</div>
<div align="center">
	<table class="problemInfoTable" cellSpacing="0" cellPadding="2" width="100%" align="center">
		 <tbody>

			<tr>
				<td class="paramTitle" width="60px" nowrap>客户名称</td>
				<td><input type="text" name= "cust_name" size="20" value="<%=cust_name%>"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>客户编号</td>
				<td><input type="text" name="cust_no" onkeydown="javascript:nextKeyPress(this)"
					value="<%=cust_no%>" size="20"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>客户类别</td><!-- 客户类别 -->
				<td>
					<select name="cust_type" style="width: 130px" onkeydown="javascript:nextKeyPress(this)">
						<%=Argument.getCustTypeOptions(cust_type) %>
					</select>
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>证件类型</td><!--证件类型-->
				<td>
					<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="card_type" style="WIDTH: 130px">
					   <%=Argument.getCardTypeJgOrGrOptions(card_type)%>
					</select>  
				</td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>证件号码</td>
				<td><input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="40" name="card_id" size="20" value="<%=card_id%>"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>电话号码</td>
				<td><input type="text" maxlength="100" name="telephone" onkeydown="javascript:nextKeyPress(this)"
					value="<%=telephone%>" size="20"></td>
			</tr>
			<tr>
				<td class="paramTitle" width="60px" nowrap>客户经理</td><!-- 客户类别 -->
				<td>
					<select name="accountManager" style="width: 135px" onkeydown="javascript:nextKeyPress(this)">
						<%=Argument.getOperatorOptions(accountManager) %>
					</select>
				</td>
			</tr>
 			<tr>
	          <td class="paramTitle">&nbsp;</td>
	          <td>  
				 <button class="xpbutton2"  id="btnSave" name="btnSave" onclick="javascript:queryAction();">&nbsp;查询&nbsp;<!--处理--></button>
				 &nbsp;&nbsp;
				 <button class="xpbutton2"  id="btnBack" name="btnBack" onclick="javascript:backAction();">&nbsp;返回&nbsp;<!--返回--></button>			  
				</td>
            </tr>
		</tbody>
	</table>
</div>

<div class="bottomDiv">
	&nbsp;&nbsp;<font size="2">您好！<%=input_operator.getOp_name()%></font>&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>