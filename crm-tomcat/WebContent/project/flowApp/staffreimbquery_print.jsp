<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.project.*,enfo.crm.tools.*,enfo.crm.system.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
	
<%
	ConfigLocal configLocal = EJBFactory.getConfig();
	BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
	String form_code = "InvoiceTicket010";
	//初始化参数
	String object_id = Utility.trimNull(request.getParameter("object_id"));	
	String object_type = Utility.trimNull(request.getParameter("object_type"));	
	String manAll = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.[HEBING]('"+object_id+"') "));
	String[] mans=manAll.split("@@");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/print.css" type=text/css rel=stylesheet>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="content">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language="javascript">

</script>
</HEAD>
<BODY leftMargin=20 topMargin=0 rightmargin=20>
<form name="theform" method="post">
<%if(1==1){  //团队报销单
	String[] realIds = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.[HEBINGID]('"+object_id+"',"+"-1"+") ")).split("@@");
	String form_codeAll = "InvoiceTicket020";
	List queryFieldList = configLocal.queryPrintContentList(form_codeAll);
	Map map = null;
	String[][] showContent=new String[queryFieldList.size()][13];
	String selectField="",fromTable="",whereCondition="",sql="",tempValue="",returnValue="";
	int iNumber=0,iCount=0,iShow=0;
	for(int i=0;i<queryFieldList.size();i++)
	{
	    map = (Map)queryFieldList.get(i);
	    fromTable  = Utility.trimNull(map.get("FROM_TABLE"));
	    whereCondition  = Utility.trimNull(map.get("WHERE_CONDITION"));	    
	    showContent[i][0]  = Utility.trimNull(map.get("SHOW_NAME"));
	    showContent[i][1]  = Utility.trimNull(map.get("SHOW_PLACE"));
	    showContent[i][2]  = Utility.trimNull(map.get("WIDTH"));
	    showContent[i][3]  = Utility.trimNull(map.get("HEIGHT"));
	    showContent[i][4]  = Utility.trimNull(map.get("ALIGN"));	
	    showContent[i][5]  = Utility.trimNull(map.get("COLSPAN"));
	    showContent[i][6]  = Utility.trimNull(map.get("ROWSPAN"));
	    showContent[i][7]  = Utility.trimNull(map.get("TABLE_FLAG"));
	    showContent[i][8]  = Utility.trimNull(map.get("BORDER_FLAG"));
	    showContent[i][9]  = Utility.trimNull(map.get("ORDER_NO"));
	    showContent[i][10]  = Utility.trimNull(map.get("MIX_FLAG"));
	    showContent[i][11]  = Utility.trimNull(map.get("FIXUP_FLAG"));
	    showContent[i][12]  = Utility.trimNull(map.get("SHOW_VALUE"));
		//表单取数sql语句处理
		showContent[i][12] = showContent[i][12].replaceAll("#OBJECT_ID#",object_id);	
	    if(!(showContent[i][12]==null || "".equals(showContent[i][12])) && "Fix".equals(showContent[i][11])){
		    if(i==iCount){
		    	selectField=showContent[i][12];
		    	iCount=iCount+1;    	
		    }else{
		     	selectField=selectField+"@@"+showContent[i][12];	
		    }
	    }	
	    iCount=iCount+1;
	}	
	//获取对应取数结果
	String[] fieldValue=selectField.split("@@");	
	String[] showValue=new String[fieldValue.length];
	//表单取数sql语句处理
	whereCondition = whereCondition.replaceAll("#OBJECT_ID#","'"+object_id+"'");
	selectField = selectField.replaceAll("@@",",");	
	sql="select "+selectField+" from "+fromTable+" "+whereCondition;
	List showValueList = businessLogicLocal.listWorkCount(sql);
	//初始化对应值
	for(int i=0;i<showValue.length;i++){
		showValue[i]="&nbsp;";
	}
  	//取数结果初始化处理
  	for(int i=0;i<showValueList.size();i++){ 
    	map = (Map)showValueList.get(i);
    	for(int j=0;j<fieldValue.length;j++){ 
    		if(fieldValue[j].indexOf(" AS ")>=0){//替换字段取数存在别名情况
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(" AS ")+4,fieldValue[j].length());
    		}else if(fieldValue[j].indexOf(".")>=0){//替换字段取数存在多表关联情况
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(".")+1,fieldValue[j].length());
    		}else{
    			tempValue=fieldValue[j];
    		}
    		//System.out.println("tempValue:"+tempValue);
    		showValue[i*j+j]=Utility.trimNull(map.get(tempValue.trim()));
    	}
  	}	
	
	
	
%>
<TABLE cellSpacing=0  cellPadding=0 align=center width="100%" border=0 style="OVERFLOW: auto;">
	<TR align="center">
		<TD vAlign=top align=center width="100%">	
		<%	 iNumber=0;iShow=0;
			for(int i=0;i<showContent.length;i++){
				String[] showName=showContent[i][0].split("@@");
				String[] showWidth=showContent[i][2].split("@@");
				String[] showAlign=showContent[i][4].split("@@");
				String[] showColspan=showContent[i][5].split("@@"); 
				String[] showRowspan=showContent[i][6].split("@@");
		%>
			<!---------------------------------------------------------打印表单头部内容处理-------------------------------->	
				<%if("Head".equals(showContent[i][1])){%>		
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j]) && "Fix".equals(showContent[i][11])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
								<span contentEditable=true><%=showName[j]%></span>
							</td>
						<%}%>
						</tr>			
					<%if("EndTable".equals(showContent[i][7])){%>
						</table>
					<%}%>
			<!---------------------------------------------------------打印表单中间部分内容处理-------------------------------->		
			<%}else if("Center".equals(showContent[i][1])){%>	
				<%if("HasTable".equals(showContent[i][7])){%>
					<%if("First".equals(showContent[i][10])){%>
						<table style='border-collapse: collapse;' border="1" bordercolor='#000000' cellpadding="0" cellspacing="0" width="93%">
					<%}%>
						<tr>
							<td style="border:1">
							<table style='border-collapse: collapse;' border="<%=showContent[i][8]%>" bordercolor='#000000' cellpadding="00" cellspacing="0" width="100%">
				<%}%>
				<!-------------打印表单中内部固定部分处理-------------------------------->	
					<%if("Fix".equals(showContent[i][11])){%>			
							<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
									<%if(showName[j].indexOf("V_GETTABLE")>-1) {%>
										<table style='border-top-width:0px;border-left-width:0px;border-bottom-width:0px;border-right-width:0px;border-collapse: collapse;' border=1 bordercolor=#000000 cellpadding=0 cellspacing=0 width=100%>
										<%
										for(int nums=0;nums<realIds.length;nums++){
											String invoiceType = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.GETCODENAME('CostType010','"+realIds[nums]+"') "));
											if(nums!=realIds.length-1){
									%>
										
										<tr><td style=" BORDER-TOP: medium none; BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=invoiceType %></span></td></tr>
										<%}else{%>
										<tr><td style=" BORDER-BOTTOM: medium none;BORDER-TOP: medium none;BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=invoiceType %></span></td></tr>

										<%}
										} %>
										
									</table>
									<%}else if(showName[j].indexOf("V_GETVALUE")>-1){	%>
										<table style='border-top-width:0px;border-left-width:0px;border-bottom-width:0px;border-right-width:0px;border-collapse: collapse;' border=1 bordercolor=#000000 cellpadding=0 cellspacing=0 width=100%>
										<%
										for(int nums=0;nums<realIds.length;nums++){
											String money = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.NUM2MONEY(sum(money)) FROM tstaffreimburse WHERE APPLY_ID ='"+object_id+"' AND REIMBURSE_TYPE="+realIds[nums]));
											if(nums!=realIds.length-1){
									%>
										
										<tr><td style=" BORDER-TOP: medium none; BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=money %></span></td></tr>
										<%}else{%>
										<tr><td style=" BORDER-BOTTOM: medium none;BORDER-TOP: medium none;BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=money %></span></td></tr>

										<%}
										} %>
										</table>
									<%}else{ %>
								<span contentEditable=true><%=showName[j]%></span>
									<%} %>
							</td>
						<%}%>
							</tr>
				<!-------------打印表单中内部可变部分处理-------------------------------->			
					<%}else if("Change".equals(showContent[i][11])){	
						returnValue=ConfigUtil.getQueryPrintSql(showContent[i][12],showName.length);
						//System.out.println("returnValue:"+returnValue);
						String[] changeValue=returnValue.split("@@");
						if(returnValue.length()>0){
							for(int j=0;j<changeValue.length/showName.length;j++){
						%>					
								<tr>
							<%
								for(int k=0;k<showName.length;k++){
									iShow=j*showName.length+k;
							%>	
									<td align="center"><span contentEditable=true><%=changeValue[iShow]%></span></td>
								<%}%>
								</tr>
							<%}%>	
						<%}else if(returnValue.length()==0){%>	
							<tr>
							<%for(int j=0;j<showName.length;j++){%>
								<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
									<span contentEditable=true><%=showName[j]%></span>
								</td>
							<%}%>
							</tr>	
						<%}%>					
					<%}%>																																
					<%if("EndTable".equals(showContent[i][7])){%>
							</table>
						    </td>
					    </tr>
					<%if("End".equals(showContent[i][10])){%>	    
				    	</table>
				  	<%}%>
			    <%}%>
			<!---------------------------------------------------------打印表单尾部内容处理-------------------------------->	
			<%}else if("Tail".equals(showContent[i][1])){%>	
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j]) && "Fix".equals(showContent[i][11])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
								<span contentEditable=true><%=showName[j]%></span>
							</td>
						<%}%>
						</tr>			
					<%if("EndTable".equals(showContent[i][7])){%>
						</table>
					<%}%>	
			<%}%>							
	    <%}%>
		</TD>
    </TR>
</TABLE>
<BR><BR>
<%} %>

<%for(int m=0;m<mans.length;m++){  //个人报销单
	String objectids= Utility.trimNull(ConfigUtil.getSqlResult("select convert(nvarchar(20),staffreimburse_id) from tstaffreimburse where REIMBURSE_MAN= "+mans[m]+" and  apply_id ='" + object_id+"'"));
	String[] realIds = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.[HEBINGID]('"+object_id+"',"+mans[m]+") ")).split("@@");
	
	List queryFieldList = configLocal.queryPrintContentList(form_code);
	Map map = null;
	String[][] showContent=new String[queryFieldList.size()][13];
	String selectField="",fromTable="",whereCondition="",sql="",tempValue="",returnValue="";
	int iNumber=0,iCount=0,iShow=0;
	for(int i=0;i<queryFieldList.size();i++)
	{
	    map = (Map)queryFieldList.get(i);
	    fromTable  = Utility.trimNull(map.get("FROM_TABLE"));
	    whereCondition  = Utility.trimNull(map.get("WHERE_CONDITION"));	    
	    showContent[i][0]  = Utility.trimNull(map.get("SHOW_NAME"));
	    showContent[i][1]  = Utility.trimNull(map.get("SHOW_PLACE"));
	    showContent[i][2]  = Utility.trimNull(map.get("WIDTH"));
	    showContent[i][3]  = Utility.trimNull(map.get("HEIGHT"));
	    showContent[i][4]  = Utility.trimNull(map.get("ALIGN"));	
	    showContent[i][5]  = Utility.trimNull(map.get("COLSPAN"));
	    showContent[i][6]  = Utility.trimNull(map.get("ROWSPAN"));
	    showContent[i][7]  = Utility.trimNull(map.get("TABLE_FLAG"));
	    showContent[i][8]  = Utility.trimNull(map.get("BORDER_FLAG"));
	    showContent[i][9]  = Utility.trimNull(map.get("ORDER_NO"));
	    showContent[i][10]  = Utility.trimNull(map.get("MIX_FLAG"));
	    showContent[i][11]  = Utility.trimNull(map.get("FIXUP_FLAG"));
	    showContent[i][12]  = Utility.trimNull(map.get("SHOW_VALUE"));
		//表单取数sql语句处理
		showContent[i][12] = showContent[i][12].replaceAll("#OBJECT_ID#",objectids);	
		showContent[i][12] = showContent[i][12].replaceAll("#MAN_ID#","'"+mans[m]+"'");    
	    if(!(showContent[i][12]==null || "".equals(showContent[i][12])) && "Fix".equals(showContent[i][11])){
		    if(i==iCount){
		    	selectField=showContent[i][12];
		    	iCount=iCount+1;    	
		    }else{
		     	selectField=selectField+"@@"+showContent[i][12];	
		    }
	    }	
	    iCount=iCount+1;
	}	
	//获取对应取数结果
	String[] fieldValue=selectField.split("@@");	
	String[] showValue=new String[fieldValue.length];
	//表单取数sql语句处理
	whereCondition = whereCondition.replaceAll("#OBJECT_ID#","'"+objectids+"'");
	whereCondition = whereCondition.replaceAll("#MAN_ID#","'"+mans[m]+"'");
	selectField = selectField.replaceAll("@@",",");	
	sql="select "+selectField+" from "+fromTable+" "+whereCondition;
	List showValueList = businessLogicLocal.listWorkCount(sql);
	//初始化对应值
	for(int i=0;i<showValue.length;i++){
		showValue[i]="&nbsp;";
	}
  	//取数结果初始化处理
  	for(int i=0;i<showValueList.size();i++){ 
    	map = (Map)showValueList.get(i);
    	for(int j=0;j<fieldValue.length;j++){ 
    		if(fieldValue[j].indexOf(" AS ")>=0){//替换字段取数存在别名情况
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(" AS ")+4,fieldValue[j].length());
    		}else if(fieldValue[j].indexOf(".")>=0){//替换字段取数存在多表关联情况
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(".")+1,fieldValue[j].length());
    		}else{
    			tempValue=fieldValue[j];
    		}
    		//System.out.println("tempValue:"+tempValue);
    		showValue[i*j+j]=Utility.trimNull(map.get(tempValue.trim()));
    	}
  	}	
	
	
	
%>
<TABLE cellSpacing=0  cellPadding=0 align=center width="100%" border=0 style="OVERFLOW: auto;">
	<TR align="center">
		<TD vAlign=top align=center width="100%">	
		<%	 iNumber=0;iShow=0;
			for(int i=0;i<showContent.length;i++){
				String[] showName=showContent[i][0].split("@@");
				String[] showWidth=showContent[i][2].split("@@");
				String[] showAlign=showContent[i][4].split("@@");
				String[] showColspan=showContent[i][5].split("@@"); 
				String[] showRowspan=showContent[i][6].split("@@");
		%>
			<!---------------------------------------------------------打印表单头部内容处理-------------------------------->	
				<%if("Head".equals(showContent[i][1])){%>		
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j]) && "Fix".equals(showContent[i][11])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
								<span contentEditable=true><%=showName[j]%></span>
							</td>
						<%}%>
						</tr>			
					<%if("EndTable".equals(showContent[i][7])){%>
						</table>
					<%}%>
			<!---------------------------------------------------------打印表单中间部分内容处理-------------------------------->		
			<%}else if("Center".equals(showContent[i][1])){%>	
				<%if("HasTable".equals(showContent[i][7])){%>
					<%if("First".equals(showContent[i][10])){%>
						<table style='border-collapse: collapse;' border="1" bordercolor='#000000' cellpadding="0" cellspacing="0" width="93%">
					<%}%>
						<tr>
							<td style="border:1">
							<table style='border-collapse: collapse;' border="<%=showContent[i][8]%>" bordercolor='#000000' cellpadding="00" cellspacing="0" width="100%">
				<%}%>
				<!-------------打印表单中内部固定部分处理-------------------------------->	
					<%if("Fix".equals(showContent[i][11])){%>			
							<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
									<%if(showName[j].indexOf("V_GETTABLE")>-1) {%>
										<table style='border-top-width:0px;border-left-width:0px;border-bottom-width:0px;border-right-width:0px;border-collapse: collapse;' border=1 bordercolor=#000000 cellpadding=0 cellspacing=0 width=100%>
										<%
										for(int nums=0;nums<realIds.length;nums++){
											String invoiceType = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.GETCODENAME('CostType010','"+realIds[nums]+"') "));
											if(nums!=realIds.length-1){
									%>
										
										<tr><td style=" BORDER-TOP: medium none; BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=invoiceType %></span></td></tr>
										<%}else{%>
										<tr><td style=" BORDER-BOTTOM: medium none;BORDER-TOP: medium none;BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=invoiceType %></span></td></tr>

										<%}
										} %>
										
									</table>
									<%}else if(showName[j].indexOf("V_GETVALUE")>-1){	%>
										<table style='border-top-width:0px;border-left-width:0px;border-bottom-width:0px;border-right-width:0px;border-collapse: collapse;' border=1 bordercolor=#000000 cellpadding=0 cellspacing=0 width=100%>
										<%
										for(int nums=0;nums<realIds.length;nums++){
											String money = Utility.trimNull(ConfigUtil.getSqlResult("select dbo.NUM2MONEY(sum(money)) FROM tstaffreimburse WHERE APPLY_ID ='"+object_id+"' AND REIMBURSE_TYPE="+realIds[nums]));
											if(nums!=realIds.length-1){
									%>
										
										<tr><td style=" BORDER-TOP: medium none; BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=money %></span></td></tr>
										<%}else{%>
										<tr><td style=" BORDER-BOTTOM: medium none;BORDER-TOP: medium none;BORDER-left: medium none;BORDER-right: medium none;" width="16%" align="center"  height="30" colspan="1" rowspan="1" ><span contentEditable=true><%=money %></span></td></tr>

										<%}
										} %>
										</table>
									<%}else{ %>
								<span contentEditable=true><%=showName[j]%></span>
									<%} %>
							</td>
						<%}%>
							</tr>
				<!-------------打印表单中内部可变部分处理-------------------------------->			
					<%}else if("Change".equals(showContent[i][11])){	
						returnValue=ConfigUtil.getQueryPrintSql(showContent[i][12],showName.length);
						//System.out.println("returnValue:"+returnValue);
						String[] changeValue=returnValue.split("@@");
						if(returnValue.length()>0){
							for(int j=0;j<changeValue.length/showName.length;j++){
						%>					
								<tr>
							<%
								for(int k=0;k<showName.length;k++){
									iShow=j*showName.length+k;
							%>	
									<td align="center"><span contentEditable=true><%=changeValue[iShow]%></span></td>
								<%}%>
								</tr>
							<%}%>	
						<%}else if(returnValue.length()==0){%>	
							<tr>
							<%for(int j=0;j<showName.length;j++){%>
								<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
									<span contentEditable=true><%=showName[j]%></span>
								</td>
							<%}%>
							</tr>	
						<%}%>					
					<%}%>																																
					<%if("EndTable".equals(showContent[i][7])){%>
							</table>
						    </td>
					    </tr>
					<%if("End".equals(showContent[i][10])){%>	    
				    	</table>
				  	<%}%>
			    <%}%>
			<!---------------------------------------------------------打印表单尾部内容处理-------------------------------->	
			<%}else if("Tail".equals(showContent[i][1])){%>	
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//表单中取数赋值
								if("&nbsp;".equals(showName[j]) && "Fix".equals(showContent[i][11])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
								<span contentEditable=true><%=showName[j]%></span>
							</td>
						<%}%>
						</tr>			
					<%if("EndTable".equals(showContent[i][7])){%>
						</table>
					<%}%>	
			<%}%>							
	    <%}%>
		</TD>
    </TR>
</TABLE>
<BR><BR>
<%} %>

<BR><BR>

<TABLE border="0" width="93%" id=table99>
	<TR valign="top">
		<TD align="right">
			<button class="xpbutton3" accessKey=p name="btnPrint" title="打印" onclick="javascript:window.print();table99.style.display = 'none';">打印(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;
			<button class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:self.close();">返回(<u>B</u>)</button>
		</TD>
	</TR>
</TABLE>


</form>
</BODY>
</HTML>
<%businessLogicLocal.remove();%>
<%configLocal.remove();%>
