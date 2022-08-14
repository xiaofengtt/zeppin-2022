<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>

<%
//��������
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO activityTask_vo = new ActivityTaskVO();
ActivityTaskVO activityTask_vo2 = new ActivityTaskVO();
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO active_vo = new ActivityVO();
//��������
String[] totalColumn = null;
//������Ӫ���
active_vo.setManage_code(input_operatorCode);
active_vo.setActive_flag(new Integer(0));
IPageList activeList = activityLocal.pageList_query(active_vo,totalColumn,1,5);

activityTask_vo.setExecutor(input_operatorCode);
activityTask_vo.setTaskFlag(new Integer(1));
IPageList activeTaskList = activityTaskLocal.pageList_query(activityTask_vo,totalColumn,1,5);

activityTask_vo2.setManagerCode(input_operatorCode);
activityTask_vo2.setTaskFlag(new Integer(2));
IPageList activeTaskList2 = activityTaskLocal.pageList_query(activityTask_vo2,totalColumn,1,5);
%>

<% 
	List activeList_1 = activeList.getRsList();
	Map activeMap = null;
	String start_date = "";
	int active_len = 5<activeList_1.size()?5:activeList_1.size();
		
	if(active_len==0){
%>		
		<div align="left" style="margin-left:20px;margin-top:5px;"><%=LocalUtilis.language("main.message.myActivity",clientLocale)%> ��</div><!--���û�л��¼-->
<%  }else{%>
			<ul>	
<%								
	  for(int i=0;i<active_len;i++){
		 activeMap = (Map)activeList_1.get(i);
		 start_date = Utility.trimNull(activeMap.get("START_DATE"));
			
		 if(start_date.length()>0){
			start_date = start_date.substring(0,16);
		 }	
%>
		<li>[�ҵĻ]<%=start_date%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeMap.get("ACTIVE_THEME"))%></li>
	<%}%>
   </ul>
<%}%>
<% 
	List activeTaskList_1 = activeTaskList.getRsList();
	Map activeTaskMap = null;
	int activeTask_len = 5<activeTaskList_1.size()?5:activeTaskList_1.size();
	
	if(activeTask_len==0){
%>		
	<div align="left" style="margin-left:20px;margin-top:5px;">
		<%=LocalUtilis.language("main.message.yourActivityTask",clientLocale)%> ��
		<!--���û�д���������-->
	</div>
<%  }else{%>
<ul>	
	<%			
		for(int i=0;i<activeTask_len;i++){
			activeTaskMap = (Map)activeTaskList_1.get(i);
	%>
		<li>		
			[����������]<a href="#" onclick="javascript:activeTaskDealAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">					
				<%=Utility.trimNull(activeTaskMap.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap.get("ActiveTaskTitle"))%></font>
			</a>
		</li>
	<%}%>
</ul>
<%}%>
<% 
	List activeTaskList_2 = activeTaskList2.getRsList();
	Map activeTaskMap2 = null;
	int activeTask_len2 = 5<activeTaskList_2.size()?5:activeTaskList_2.size();
	
	if(activeTask_len2==0){
%>		
	<div align="left" style="margin-left:20px;margin-top:5px;"><%=LocalUtilis.language("main.message.auditingActivityTask",clientLocale)%> ��</div>
	<!--���û�д���˻����-->
<%  }else{%>
	<ul>	
<%		
	for(int i=0;i<activeTask_len2;i++){
		activeTaskMap2 = (Map)activeTaskList_2.get(i);
%>
		[����˻����]<li>	
		<a href="#" onclick="javascript:activeTaskDealCheckAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap2.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">								
				<%=Utility.trimNull(activeTaskMap2.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap2.get("ActiveTaskTitle"))%></font>				
			</a>
		</li>
	<%}%>
	   </ul>
	<%}%>

<script language="javascript">
function activeTaskDealCheckAction(serial_no){
	var url ="<%=request.getContextPath()%>/marketing/activity/activity_task_check_action.jsp?transFlag=2&serial_no="+serial_no;
	window.location.href=url;
}
</script>