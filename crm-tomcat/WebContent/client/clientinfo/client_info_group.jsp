<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
Integer action_flag = Utility.parseInt(Utility.trimNull(request.getParameter("action_flag")),new Integer(1));//1 新增 2删除
Integer group_id2 =  Utility.parseInt(Utility.trimNull(request.getParameter("group_id2")),new Integer(0));

//获取对象
CustGroupMemberLocal local = EJBFactory.getCustGroupMember();
CustGroupMemberVO vo = new CustGroupMemberVO();
boolean bSuccess = false;
if (request.getMethod().equals("POST")){
	vo = new CustGroupMemberVO();
	vo.setGroup_id(group_id);
	vo.setCust_id(cust_id);
	vo.setInsertMan(input_operatorCode);
	
	if(action_flag.intValue()==1){
		local.appendCustGroupMember(vo);
		bSuccess = true;
	}
	else if(action_flag.intValue()==2){
		vo.setGroup_id(group_id2);
		local.delCustGroupMember(vo);
		bSuccess = true;
	}
	
	
}
vo = new CustGroupMemberVO();
vo.setSerial_no(new Integer(0));
vo.setGroup_id(new Integer(0));
vo.setCust_id(cust_id);
vo.setInsertMan(input_operatorCode);

IPageList pageList = local.queryAll(vo, 1, -1);
List list = pageList.getRsList();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--客户群组-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
<%if(bSuccess){%>
	<%if(action_flag.intValue()==1){%>
		sl_update_ok();
	<%}
	else if(action_flag.intValue()==2){
	%>
		sl_alert("删除成功！");  
	<%	
	}%>
<%}%>
function joinAction(){
	if(!sl_checkChoice(document.theform.group_id, "客户群组"))	return false;//客户群组
	document.theform.action_flag.value = 1;
	document.theform.action = "client_info_group.jsp";
	document.theform.submit();
}

function DelSelfAction(group_id2){
	document.theform.action_flag.value = 2;
	document.theform.group_id2.value = group_id2;
	document.theform.action = "client_info_group.jsp";
	document.theform.submit();
}
</script>
</head>
<body class="body body-nox">
<form id="theform" name="theform" method="post">
	<input type="hidden" name="cust_id" value="<%=cust_id%>" />
	<input type="hidden" name="action_flag" value="<%=action_flag%>" />
	<input type="hidden" name="group_id2" value="<%=group_id2%>" />
	
	<div align="left" class="page-title">
		<font color="#215dc6"><b>客户群组</b></font><!--客户群组-->
	</div>	
	<div  style="margin-top:5px;">
		<font size=2>
			&nbsp;&nbsp;客户群组：&nbsp;&nbsp;		
		</font>
		<select name="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px;" onclick="javascript:">
			<%=Argument.getCustGroupOption2(cust_id, new Integer(0))%>
		</select>
		<!-- 选中 -->&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button"  class="xpbutton2" id="btnChoose" onclick="javascript:joinAction();">加入</button>		
	</div>
	
	<div style="margin-top:5px;">
		<table border="0" width="430px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				 <td width="*" align="center">已加入群组</td>
				 <td style="width: 50px;" align="center"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!-- 删除 -->
		     </tr>
		</table>
		<span id="tableList" style="overflow-y:auto;height:200;">
			<table border="0" width="430px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
				<%
					Iterator it = list.iterator();
					Map map = new HashMap();
					int iCount = 0;
					while(it.hasNext()){
						map = (Map)it.next();
				%>
					<tr class="tr<%=(iCount % 2)%>">
						<td width="*" align="center"><%=Utility.trimNull(map.get("GroupName"))%></td>
						<td align="center" style="width: 50px;">
			                    <!-- 删除 -->
				        		<img border="0" src="<%=request.getContextPath()%>/images/recycle.gif" align="absmiddle" 
				        			 onclick="DelSelfAction('<%=Utility.trimNull(map.get("GroupID"))%>')" style="cursor:hand"
				        			 title="<%=LocalUtilis.language("message.delete",clientLocale)%> "  width="15" height="15" />
				        </td>  
					</tr>
				<%	
						iCount++;
					}
				%>
				<%for (; iCount < 5; iCount++) {%>
					<tr class="tr<%=(iCount % 2)%>">
						<td class="tdh" align="center"></td>
						<td class="tdh" align="center" style="width: 50px;"></td>
					</tr>
				<%}%>
			</table>
		</span>
	</div>
</form>
</body>
</html>