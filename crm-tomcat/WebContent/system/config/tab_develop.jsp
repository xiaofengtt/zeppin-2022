<%@ page contentType="text/html; charset=GBK" import="enfo.crm.project.*,enfo.crm.vo.*, enfo.crm.system.*,enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.system.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
	String regionCode = Utility.trimNull(request.getParameter("RegionCode"));
	String showCount = Utility.trimNull(request.getParameter("ShowCount"));
	String addCount = Utility.trimNull(request.getParameter("AddCount"));
	String redCount = Utility.trimNull(request.getParameter("RedCount"));
	String actionFlag = Utility.trimNull(request.getParameter("ActionFlag"));
	//System.out.println("addCount:"+addCount+"<br>");
	//System.out.println("redCount:"+redCount+"<br>");
	if("".equals(actionFlag)){
		if("".equals(addCount)){
		 	addCount="0";
		}else if(Integer.parseInt(addCount)>0){
			if("".equals(redCount)){
				redCount="0";
			}else if(Integer.parseInt(redCount)>0){
				addCount =String.valueOf(Integer.parseInt(addCount)-1);
			}
			if("0".equals(redCount)){
				addCount =String.valueOf(Integer.parseInt(addCount)+1);
			}
		}
	}
	if("".equals(showCount)) showCount="5";
	if("".equals(regionCode)) regionCode="";
	//System.out.println("addCount1:"+addCount+"<br>");
	//System.out.println("redCount1:"+redCount+"<br>");
		
	ConfigLocal configLocal = EJBFactory.getConfig();
	if("delete".equals(actionFlag)){
		if(request.getMethod().equals("POST")){//删除信息
			String[] s = request.getParameterValues("regionID");
			if(s.length>0){
				ConfigUtil.delInfo(configLocal,s,"CONFIG_REGION","REGION_ID");
			}
		}	
	}
	
	String regionName="";
	String sql=" select * from CONFIG_REGION where Region_Code= '"+regionCode+"' order by order_no";
	List menuList = configLocal.showSelectInfo("sql","",sql);	
	String[][] ShowContent = new String[menuList.size()][6];
	//初始化显示菜单信息
	for(int i=0;i<menuList.size();i++)
	{
		Map menuMap = (Map)menuList.get(i);
		ShowContent[i][0]=Utility.trimNull(menuMap.get("ITEM_CODE"));
		ShowContent[i][1]=Utility.trimNull(menuMap.get("ITEM_NAME"));
		ShowContent[i][2]=Utility.trimNull(menuMap.get("REGION_ID"));
		ShowContent[i][4]=Utility.trimNull(menuMap.get("RELATIVE_NAME"));
		ShowContent[i][5]=Utility.trimNull(menuMap.get("ATTRIBUTE_NAME"));		
		regionName=Utility.trimNull(menuMap.get("REGION_NAME"));
	}
	
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST" action="">
<input type="hidden" name="AddCount" value="<%=addCount%>" />
<input type="hidden" name="RedCount" value="<%=redCount%>" />
<input type="hidden" name="ActionFlag" value="<%=actionFlag%>" />
<input type="hidden" name="regionID" value="" />
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>	
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						设置代码：<input type="text" size="20" name="RegionCode" value="<%=regionCode%>" onclick="javascript:queryInfo();">&nbsp;&nbsp;&nbsp; 
						设置个数：<input type="text" size="3" name="ShowCount" value="<%=showCount%>" onclick="javascript:configInfo();">&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=n id="btnNew" name="btnNew" title="新增一个Tab" onclick="javascript:newInfo();">新增一个Tab(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" accessKey=l id="btnDel" name="btnDel" title="删除一个Tab" onclick="javascript:deleteInfo();">删除一个Tab(<u>L</u>)</button>
						&nbsp;&nbsp;&nbsp; 
						<button type="button"  class="xpbutton5" accessKey=t id="btnDel" name="btnDel" title="删除对应Tab" onclick="javascript:delInfo();">删除对应Tab(<u>T</u>)</button>
						&nbsp;&nbsp;&nbsp; 						
					</td>	
				</tr>	
				<tr>&nbsp;&nbsp;&nbsp;</tr>					
			</table>
			<br>
			<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0" class="edline">
             <TBODY>
               <TR>  
               <%if(menuList.size()>0){%>
 	               <%for(int i=0;i<ShowContent.length;i++){%>
					 <TD id='<%="d"+i%>' style="CURSOR: hand ;background-repeat:no-repeat" onclick="javascript:showTab('<%=i%>','<%=ShowContent[i][2]%>','<%=ShowContent[i][4]%>','<%=ShowContent[i][5]%>');" vAlign=top width=120 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=ShowContent[i][1]+"【"+ShowContent[i][0]+"】"%></TD> 
		           <%}%>
               <%}else{%>
	               <%for(int i=0;i<Integer.parseInt(showCount);i++){%>
					 <TD id='<%="d"+i%>' style="CURSOR: hand ;background-repeat:no-repeat" onclick="javascript:showTab('<%=i%>','0');" vAlign=top width=120 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;tab<%=i+1%></TD> 
		           <%}%>
	          <%}%>
	               <%for(int i=1;i<Integer.parseInt(addCount);i++){%>
					 <TD id='<%="d"+(i+9)%>' style="CURSOR: hand ;background-repeat:no-repeat" onclick="javascript:showTab('<%=i+9%>','0');" vAlign=top width=120 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;tab<%=i+9%></TD> 
		           <%}%>	          
	          </TR>			
	        </TBODY>
	       </TABLE>
	       <br>
	    </TD>
	    </TR>			
	</TABLE>
	</TD>
	</TR>
</TABLE>
<script language=javascript>
	//----------------------配置---------------//
	function showTab(orderNo,regionID,jspname,paramname){
		<%if(menuList.size()>0){%>
			<%for(int i=0;i<ShowContent.length;i++){%>
			     if(<%=i%>!= orderNo){  
			        eval("document.getElementById('d" + <%=i%> + "').background = '/images/headdark_00_01.gif'");
				 }else{  
				    eval("document.getElementById('d"+<%=i%>+"').background = '/images/head_00_01.gif'");
				 } 
			<%}%>	
		 <%}else{%>
			<%for(int i=0;i<Integer.parseInt(showCount);i++){%>
			     if(<%=i%>!= orderNo){  
			        eval("document.getElementById('d" + <%=i%> + "').background = '/images/headdark_00_01.gif'");
				 }else{  
				    eval("document.getElementById('d"+<%=i%>+"').background = '/images/head_00_01.gif'");
				 } 
			<%}%>		 
		 <%}%>
		<%for(int i=1;i<Integer.parseInt(addCount);i++){%>
		     if(<%=(i+9)%>!= orderNo){  
		        eval("document.getElementById('d" + <%=i+9%> + "').background = '/images/headdark_00_01.gif'");
			 }else{  
			    eval("document.getElementById('d"+<%=i+9%>+"').background = '/images/head_00_01.gif'");
			 } 
		<%}%>		
		var regionName="<%=regionName%>";
		var regionCode=trim(document.theform.RegionCode.value);
		if(showModalDialog('/system/config/interface_region_info.jsp?interfacetype_code=130&identity_code='+regionID+'&table_code=CONFIG_REGION&region_name='+regionName+'&region_code='+regionCode, '', 'dialogWidth:560px;dialogHeight:550px;status:0;help:0') != null)
		{
			sl_update_ok();
			document.theform.ActionFlag.value="Save";
			document.theform.submit();
		}
		document.theform.regionID.value=regionID;
	}
	//----------------------查询---------------//
	function queryInfo(){
		var regionCode=trim(document.theform.RegionCode.value);
		if(typeof(regionCode)!="undefined" && regionCode!="")
		{
			if(confirm("确定要执行操作吗？")){
				document.theform.submit();
			}
		}
	}
	//----------------------设置---------------//
	function configInfo(){
		var showCount=trim(document.theform.ShowCount.value);
		if(typeof(showCount)!="undefined" && showCount!="")
		{	
			if(confirm("确定要执行操作吗？")){
				document.theform.submit();
			}
		}
	}
	//----------------------新增---------------//
	function newInfo(){
		if("<%=addCount%>"==0){
			document.theform.AddCount.value="1";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}else{
			document.theform.AddCount.value="<%=addCount%>";
			document.theform.RedCount.value="0";
			document.theform.ActionFlag.value="";
		}
		document.theform.submit();
	}
	//----------------------删除---------------//
	function deleteInfo(){
		document.theform.RedCount.value="1";
		document.theform.ActionFlag.value="";
		document.theform.submit();
	}	
	//----------------------删除---------------//
	function delInfo(){
		var RegionID=trim(document.theform.regionID.value);
		if(typeof(RegionID)=="undefined" || RegionID==""){
			alert("请选择对应的tab！");
			return;	
		}
		document.theform.regionID.value=RegionID;
		document.theform.ActionFlag.value="delete";
		if(confirm("确定要执行删除吗？")){
			document.theform.submit();
		}
	}
</script>

<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>


