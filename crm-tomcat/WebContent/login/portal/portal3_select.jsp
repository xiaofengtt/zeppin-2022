<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>
<%input_bookCode = new Integer(1); %>

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet">

<script type="text/javascript">
	function saveSate(){
		var protalCodes = 	document.getElementsByName("portalCode");
		var ss = [];

		if(protalCodes){
			for(var i=0; i<protalCodes.length; i++){
				if(protalCodes[i].checked){
	 				ss.push("1#"+protalCodes[i].value);
				}else{
					ss.push("2#"+protalCodes[i].value);
				}
				
			}
		}
	
		$.post("<%=request.getContextPath()%>/login/portal/portal3_do.jsp", 
			{type:"add",portalCode :ss+"",opCode: <%=input_operatorCode%> },
		   	function(data) {
		    	if(1 != data){
					alert(data);
				}
				window.close();
				window.returnValue = true;
		   });
	}

	function coseWindow(){
		window.close();
		window.returnValue = false;
	}
</script>
 
<%
	PortalLocal local = EJBFactory.getPortal();
	List list =  local.queryMyPortal(input_bookCode,input_operatorCode);
%>
<body style="background:#fafafa url('images/bg.gif') repeat-x left bottom;">
<table style="width: 90%">
<% 
	try {
		for(int i=0; i<list.size(); i++){
			Map map  = (Map)list.get(i);
			String productCode = (String)map.get("PORTAL_CODE");
			String PORTAL_NAME = (String)map.get("PORTAL_NAME");

			if(!"PRTL_WAITPROBLEM".equals(productCode)){
				int VISIBLE = Utility.parseInt((Integer)map.get("VISIBLE"),new Integer(0)).intValue();
		%>
	<tr >
		<td>
			<input type="checkbox" class="flatcheckbox" name="portalCode" value="<%=productCode%>"  <%=VISIBLE==1?"checked='checked'":"" %> > <%=PORTAL_NAME %>
		</td>
	</tr>
		<%
		}
	}
%>
	<tr>
		<TD align="right">
			<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveSate();">
			<%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--±£´æ-->
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:coseWindow();">
			<%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--È¡Ïû-->
		</TD>
	</tr>
</table>
</body>
<%
}finally{
	local.remove();
}
%>