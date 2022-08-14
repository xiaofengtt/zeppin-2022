<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.project.*,enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
	
<%
	ConfigLocal configLocal = EJBFactory.getConfig();
	BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
	String form_code = Utility.trimNull(request.getParameter("form_code"));	
	//��ʼ������
	String object_id = "999999";	
	String object_type = "Test";	
	
	//��ʼ��������Ϣ
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
		//��ȡ��sql��䴦��
		showContent[i][12] = showContent[i][12].replaceAll("#OBJECT_ID#",object_id);	
		showContent[i][12] = showContent[i][12].replaceAll("#OBJECT_TYPE#","'"+object_type+"'");    
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
	//��ȡ��Ӧȡ�����
	String[] fieldValue=selectField.split("@@");	
	String[] showValue=new String[fieldValue.length];
	//��ȡ��sql��䴦��
	whereCondition = whereCondition.replaceAll("#OBJECT_ID#",object_id);
	whereCondition = whereCondition.replaceAll("#OBJECT_TYPE#","'"+object_type+"'");
	selectField = selectField.replaceAll("@@",",");	
	sql="select "+selectField+" from "+fromTable+" "+whereCondition;
	System.out.println(selectField);
	List showValueList = businessLogicLocal.listWorkCount(sql);
  	//ȡ�������ʼ������
  	for(int i=0;i<showValueList.size();i++){ 
    	map = (Map)showValueList.get(i);
    	for(int j=0;j<fieldValue.length;j++){ 
    		if(fieldValue[j].indexOf(" AS ")>=0){//�滻�ֶ�ȡ�����ڱ������
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(" AS ")+4,fieldValue[j].length());
    		}else if(fieldValue[j].indexOf(".")>=0){//�滻�ֶ�ȡ�����ڶ��������
    			tempValue=fieldValue[j].substring(fieldValue[j].indexOf(".")+1,fieldValue[j].length());
    		}else{
    			tempValue=fieldValue[j];
    		}
    		//System.out.println("tempValue:"+tempValue);
    		showValue[i*j+j]=Utility.trimNull(map.get(tempValue.trim()));
    	}
  	}
  		
	//��ʼ��ȡ�����
	for(int i=0;i<fieldValue.length;i++){
		showValue[i] = "&nbsp;";
	}
		
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
<TABLE cellSpacing=0  cellPadding=0 align=center width="100%" border=0>
	<TR align="center">
		<TD vAlign=top align=center width="100%">	
		<%
			for(int i=0;i<showContent.length;i++){
				String[] showName=showContent[i][0].split("@@");
				String[] showWidth=showContent[i][2].split("@@");
				String[] showAlign=showContent[i][4].split("@@");
				String[] showColspan=showContent[i][5].split("@@");
				String[] showRowspan=showContent[i][6].split("@@");
		%>
			<!---------------------------------------------------------��ӡ��ͷ�����ݴ���-------------------------------->	
			<%if("Head".equals(showContent[i][1])){%>		
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//����ȡ����ֵ
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
			<!---------------------------------------------------------��ӡ���м䲿�����ݴ���-------------------------------->		
			<%}else if("Center".equals(showContent[i][1])){%>	
				<%if("HasTable".equals(showContent[i][7])){%>
					<%if("First".equals(showContent[i][10])){%>
						<table style='border-collapse: collapse;' border="1" bordercolor='#000000' cellpadding="0" cellspacing="0" width="93%">
					<%}%>
						<tr>
							<td style="border:1">
							<table style='border-collapse: collapse;' border="<%=showContent[i][8]%>" bordercolor='#000000' cellpadding="00" cellspacing="0" width="100%">
				<%}%>
				<!-------------��ӡ�����ڲ��̶����ִ���-------------------------------->	
					<%if("Fix".equals(showContent[i][11])){%>			
							<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//����ȡ����ֵ
								if("&nbsp;".equals(showName[j])) showName[j]=showValue[iNumber++];
						%>	
							<td width="<%=showWidth[j]+"%"%>" align="<%=showAlign[j]%>"  height="<%=showContent[i][3]%>" colspan="<%=showColspan[j]%>" rowspan="<%=showRowspan[j]%>">
								<span contentEditable=true><%=showName[j]%></span>
							</td>
						<%}%>
							</tr>
				<!-------------��ӡ�����ڲ��ɱ䲿�ִ���-------------------------------->			
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
			<!---------------------------------------------------------��ӡ��β�����ݴ���-------------------------------->	
			<%}else if("Tail".equals(showContent[i][1])){%>	
					<%if("HasTable".equals(showContent[i][7])){%>
						<table border="<%=showContent[i][8]%>" width="93%">	
					<%}%>
						<tr>
						<%
							for(int j=0;j<showName.length;j++){
								//����ȡ����ֵ
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
<TABLE border="0" width="93%" id=table99>
	<TR valign="top">
		<TD align="right">
			<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:window.print();table99.style.display = 'none';">��ӡ(<u>P</u>)</button>
				&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:location='/system/systemparam/formprint_catalog.jsp'">����(<u>B</u>)</button>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%businessLogicLocal.remove();%>
<%configLocal.remove();%>
