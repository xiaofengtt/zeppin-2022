<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer LOG0001=(Integer)session.getAttribute("LOG0001");
Integer begin_date = new Integer(Utility.getCurrentDate());
Integer end_date = new Integer(Utility.getCurrentDate());
//声明对象
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
//输入查询参数
vo.setOp_code(input_operatorCode);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
IPageList insideServiceTasksList = local.listInsideServiceTasks(vo,1,10);
%>

<%	
	List insideList = insideServiceTasksList.getRsList();
	Map insideMap = null;
	int inside_len = 5<insideList.size()?5:insideList.size();
	
	if(inside_len==0){
%>		
	<div align="left" style="margin-left:20px;margin-top:5px;"><%=LocalUtilis.language("main.message.customerAffairs",clientLocale)%> 。</div>
	<!--最近没有待处理客户事务-->
<% } else{%>
	<ul class="listclass">
	<% for(int i=0;i<inside_len;i++){
			insideMap = (Map)insideList.get(i);
	%>	
		<li><a href="javascript:loadUrl('<%=insideMap.get("MENU_ID")%>','<%=request.getContextPath()%>'+'<%=insideMap.get("URL")%>'+'&transFlag=3')" class="a2"><font color="red"><%=insideMap.get("ServiceTitle")%></font></a><font color="red">(<%=insideMap.get("CustomerCount")%>)</font></li>
		<%}%>
	</ul>
<%}%>	
<script language="javascript">
function loadUrl(menu_id,url){
	loginService.alertString(menu_id,'<%=LOG0001.toString()%>',<%=input_operatorCode%>,{callback: function(data){location=url;}});
}
</script>