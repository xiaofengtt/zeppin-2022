<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK" import="enfo.crm.vo.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.system.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	String edit_right = Utility.trimNull(request.getParameter("edit_right"));
	int subflag = Utility.parseInt(request.getParameter("subflag"),0);
	String task_id = Utility.trimNull(request.getParameter("task_id"));
	String object_id = Utility.trimNull(request.getParameter("object_id"));
	String object_type = Utility.trimNull(request.getParameter("object_type"));
	String flow_no = Utility.trimNull(request.getParameter("flow_no"));
	String node_no = Utility.trimNull(request.getParameter("node_no"));	
	String show_flag = Utility.trimNull(request.getParameter("show_flag"));
	ConfigLocal configLocal = EJBFactory.getConfig();
	List menuList = null;
	Map menuMap = null;
	
	String firstShowName="";
	int iMax=15;

	String sql=" select ITEM_CODE,ITEM_NAME,CONTROL_CONDITION1,CONTROL_CONDITION2,CONTROL_CONDITION3,RELATIVE_NAME,ATTRIBUTE_NAME "+ 
			   " from CONFIG_REGION "+
			   " where Region_Code= 'InterfaceTab010' and Use_State='1' order by Order_No";
	menuList = configLocal.showSelectInfo("sql","",sql);
	String[][] ShowContent = new String[menuList.size()][7];
	//初始化显示菜单信息
	for(int i=0;i<menuList.size();i++)
	{
		menuMap = (Map)menuList.get(i);
		ShowContent[i][0]=Utility.trimNull(menuMap.get("ITEM_CODE"));
		ShowContent[i][1]=Utility.trimNull(menuMap.get("ITEM_NAME"));
		ShowContent[i][2]=Utility.trimNull(menuMap.get("CONTROL_CONDITION1"));
		ShowContent[i][3]=Utility.trimNull(menuMap.get("CONTROL_CONDITION2"));
		ShowContent[i][6]=Utility.trimNull(menuMap.get("CONTROL_CONDITION3"));
		ShowContent[i][4]=Utility.trimNull(menuMap.get("RELATIVE_NAME"));
		ShowContent[i][5]=Utility.trimNull(menuMap.get("ATTRIBUTE_NAME"));
	
		//判断默认的显示对象		
		if("010".equals(ShowContent[i][0])){
			firstShowName=ShowContent[i][4]+"?"+ShowContent[i][5];
			firstShowName=firstShowName.replaceAll("#OBJECT_ID#",object_id);
		  	firstShowName=firstShowName.replaceAll("#EDIT_RIGHT#",edit_right);
		  	firstShowName=firstShowName.replaceAll("#OBJECT_TYPE#",object_type);
		  	firstShowName=firstShowName.replaceAll("#TASK_ID#",task_id);
		  	subflag=i;			
		}
	}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
function show(parm,flowno,nodeno,jspname,paramname,defaultflag)
{
	<%for(int i=0;i<ShowContent.length;i++){%>
	     if(<%=i%>!= parm){  
	        eval("document.getElementById('d" + <%=i%> + "').background = '/images/headdark_00_01.gif'");
		 }else{  
		    eval("document.getElementById('d"+<%=i%>+"').background = '/images/head_00_01.gif'"); 	
		  	paramname=paramname.replace("#OBJECT_ID#","<%=object_id%>");
			paramname=paramname.replace("#EDIT_RIGHT#","<%=edit_right%>");
			paramname=paramname.replace("#OBJECT_TYPE#","<%=object_type%>");	
			paramname=paramname.replace("#TASK_ID#","<%=task_id%>");
			frameid.location = jspname+'?'+paramname;
		 } 
	<%}%>
}

</script>
</HEAD>
<BODY class="body">
<form name="theform" method="post" ENCTYPE="multipart/form-data" action="" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="edit_right" value="<%=edit_right%>" >
<input type="hidden" name="edit_right" value="<%=object_id%>" >
<input type="hidden" name="object_type" value="<%=object_type%>" >
<input type="hidden" name="flow_no" value="<%=flow_no%>" >
<input type="hidden" name="node_no" value="<%=node_no%>" >
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TBODY>
					<TR>
						<TD align=center>
						<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0" class="edline">
		                  <TBODY>
		                    <TR>
		                  <%for(int i=0;i<ShowContent.length;i++){%>
		                  	<%if(subflag==i){%>
							 <TD id='<%="d"+i%>' style="CURSOR: hand ;background-repeat:no-repeat" onclick="javascript:show('<%=i%>','<%=ShowContent[i][2]%>','<%=ShowContent[i][3]%>','<%=ShowContent[i][4]%>','<%=ShowContent[i][5]%>','<%=ShowContent[i][6]%>');" vAlign=top width=120 height=20 background='/images/head_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=ShowContent[i][1]%></TD> 
		                  	<%}else{%>
							 <TD id='<%="d"+i%>' style="CURSOR: hand ;background-repeat:no-repeat" onclick="javascript:show('<%=i%>','<%=ShowContent[i][2]%>','<%=ShowContent[i][3]%>','<%=ShowContent[i][4]%>','<%=ShowContent[i][5]%>','<%=ShowContent[i][6]%>');" vAlign=top width=120 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=ShowContent[i][1]%></TD> 
		                  	<%}%>
		                  <%}%>
		                  <%for(int i=0;i<iMax-ShowContent.length;i++){%>
		                     <TD vAlign=top width=60>&nbsp;</TD>
		                   <%}%>
				           </TR>			
				        </TBODY>
				       </TABLE>				       
						<table id="r1" width="100%" >
							<tr>
								<td colspan="2">
									<iframe id="frameid" name="context"  height="440px" frameborder="0" style="width:100%;" scrolling="yes" 
										src="<%=firstShowName%>">
									</iframe>
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>

