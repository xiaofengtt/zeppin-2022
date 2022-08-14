<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>

<%
//声明对象
ApplyreachLocal applyLoal = EJBFactory.getApplyreach();
ApplyreachVO appl_vo = new ApplyreachVO();
//辅助变量
String[] totalColumn = null;
//待审核
appl_vo.setBook_code(new Integer(1));
appl_vo.setCheck_flag(new Integer(1));
appl_vo.setInput_man(input_operatorCode);
IPageList applyreachCheckTaskList = applyLoal.listAll(appl_vo,totalColumn,1,5);

%>

<% 
	List applyCheckList = applyreachCheckTaskList.getRsList();
	Map applyCheckMap = null;
	Integer apply_serial_no = new Integer(0);
	int appl_len = 5<applyCheckList.size()?5:applyCheckList.size();
		
	if(appl_len==0){
%>		
	<div align="left" style="margin-left:20px;margin-top:5px;">
		<%=LocalUtilis.language("main.message.auditingPurchaseContract",clientLocale)%> 。<!--最近没有待审核申购合同-->
	</div>
<%  }else{%>
	<ul>	
<%			
	for(int i=0;i<appl_len;i++){
		applyCheckMap = (Map)applyCheckList.get(i);
		apply_serial_no = Utility.parseInt(Utility.trimNull(applyCheckMap.get("SERIAL_NO")),new Integer(0));
%>
		<li>
			<a href="#" onclick="javascript:applyCheckQuery(<%=apply_serial_no%>);" class="a2"><font color="red">
				[待审核申购合同]<%=Utility.trimNull(applyCheckMap.get("CONTRACT_SUB_BH"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("PRODUCT_NAME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("CUST_NAME"))%></font>
			</a>
		</li>
	<%}%>
	</ul>
<%}%>
<% 
	TaskInfoLocal taskInfoLocal=EJBFactory.getTaskInfo();
	TaskInfoVO taskInfoVO=new TaskInfoVO();
	taskInfoVO.setOp_code(input_operatorCode);
	taskInfoVO.setRead_flag(new Integer(346));
	taskInfoVO.setProduct_id(new Integer(0));
	taskInfoVO.setRead_flag1(new Integer(1));
	IPageList taskInfoList=taskInfoLocal.queryOpinfo(taskInfoVO,1,5);
	List taskInfoList1 = taskInfoList.getRsList();
	Map taskInfoMap = null;
	int taskInfoList_len = 5<taskInfoList1.size()?5:taskInfoList1.size();
	String title="";	
	if(taskInfoList_len==0){
%>		
	<div align="left" style="margin-left:20px;margin-top:5px;"><%=LocalUtilis.language("main.message.noSubscribeToCheck",clientLocale)%> 。</div><!--最近没有待审核的认购和到账信息-->
<%  }else{
%>
	<ul>	
<%								
  for(int i=0;i<taskInfoList_len;i++){
	 taskInfoMap = (Map)taskInfoList1.get(i);
	 title = Utility.trimNull(taskInfoMap.get("TITLE"));
				
			// if(title.length()>61){
			//	title = title.substring(0,60);
			 //}	
%>
	<li>
		[认购、到账审核]<a href="#" onclick="javascript:urlFundsCheck('<%=title%>','<%=title%>');" class="a2">
			<font color="red"><%=title%></font>
		</a>
	</li>
		<%}%>
   </ul>
<%}%>

<script language="javascript">
	//认购、到账审核URL
	function urlFundsCheck(){
		var url = '<%=request.getContextPath()%>/marketing/sell/funds_check_list.jsp?menu_id=30219&first_flag=1&parent_id=0';
		window.location.href=url;
	}

	function applyCheckQuery(serial_no){
		var url ="<%=request.getContextPath()%>/marketing/sell/apply_purchase_check_info.jsp?serial_no="+serial_no;
		window.location.href=url;
	}
</script>