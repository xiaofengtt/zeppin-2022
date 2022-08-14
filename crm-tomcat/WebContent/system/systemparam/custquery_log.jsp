<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
LogListLocal loglocal = EJBFactory.getLogList();
LogListVO vo = new LogListVO();
//��ò�ѯ����
String begin_date_str = Utility.trimNull(request.getParameter("begin_date"));
String end_date_str = Utility.trimNull(request.getParameter("end_date"));
Integer op_code = Utility.parseInt(request.getParameter("op_code"), new Integer(0));//����Ա���

vo.setOp_code(op_code);
vo.setStart_date1(begin_date_str);
vo.setEnd_date1(end_date_str);

IPageList pagelist=loglocal.getCustLog(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pagelist.getRsList();
Iterator it= list.iterator();

//��������
String tempUrl = "";
Map map = null;
//url����
tempUrl = "&begin_date="+begin_date_str+"&end_date="+end_date_str+"&op_code="+op_code;
sUrl = sUrl + tempUrl;

 %>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<title>�ͻ���ѯ��־ </title>

<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
/**�������**/
	window.onload = function(){
	initQueryCondition()};
	
	
/**�ڷ�ҳʱ��ϲ�ѯ������get�ķ�ʽ��ֵ**/
	function refreshPage()
	{
		disableAllBtn(true);
		
		var stdate= document.getElementById("p_begin_date").getAttribute("value");
		var endate=document.getElementById("p_end_date").getAttribute("value")
		
	
		location = 'custquery_log.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +
					'&begin_date=' + stdate+
					'&end_date='+endate+
					'&op_code='+document.getElementById("p_op_code").getAttribute("value");
	}
	/**ȷ����ѯ����ʱ����**/
	function StartQuery()
	{
		refreshPage();
	}
</script>

</HEAD>
<BODY class="body">
<form id="theform" method="post" name="theform">
<%@ include file="/includes/waiting.inc"%>    
<!-- ��Ӳ�ѯ���� start -->
<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   			<td align="right">
   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="4" cellpadding="2">
			<tr>
				<td  align="right" width="70"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--��ʼ����-->
				<td  align="left" width="160">
					<input type="text" name="p_begin_date" id="p_begin_date" value="<%=begin_date_str%>" size="30" onclick="javascript:setday(this);" readOnly/> 	
				</td>
				<td align="right" width="95"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--��������-->
				<td align="left" width="160">
					<input type="text" name="p_end_date" id="p_end_date" value="<%=end_date_str%>" size="30" onclick="javascript:setday(this);" readOnly/> 
				</td> 
			</tr>
			<tr>
				<td align="right" width="70"><%=LocalUtilis.language("class.opName",clientLocale)%> : </td><!--Ա������-->
				<td align="left" width="160" colspan="3">
					<select id="p_op_code" style="width:120px;" name="p_op_code" onkeydown="javascript:nextKeyPress(this)">
	                    <%=Argument.getOperatorOptions(op_code)%>
					</select>
				</td>			
			</tr>
			
			<tr>
			<td align="center" colspan="4">
				<button class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>				
			</td><!--ȷ��-->
		</tr>			
	</table>
</div>
<!-- ��Ӳ�ѯ���� end -->
<!-- ��ͷ start -->
<table cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
	<tr>
		<td align="left">
			<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
		<button class="xpbutton3" style="margin-right: 16px;" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> "><!--��ѯ-->
		    <%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)</button>
		</td>
	</tr>
	<tr>
		<td colspan="5">
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<!-- ��ͷ end -->
<!-- ��ϸ�б���ʾ start -->
<table valign="middle" align="center" cellspacing="0" cellpadding="3" width="100%">
	<tr>
		<td>
			<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trtagsort">
			<td width="5%" align="center" sort="num"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--���-->
			<td width="5%" align="center"><%=LocalUtilis.language("class.opCode",clientLocale)%>  </td><!--Ա�����-->
			<td width="5%" align="center">Ա������ </td>
			<td width="10%" align="center">��ѯʱ�� </td><!--����ʱ��-->
			<td width="5%" align="center">IP��ַ </td>
			<td width="5%" align="center">MAC��ַ </td>
			<td width="10%" align="center">��ѯ���� </td><!--ҵ��˵��-->
			<td width="5%" align="center">��¼�� </td>
		</tr>
		<!-- ѭ��������ʾÿ����Ϣ -->
		<%
			int iCount = 0;
			while(it.hasNext()){
				map = (Map)it.next();
		%>
			<tr class="tr<%=(iCount % 2)%>">
			<td align="center"><%=iCount+1%></td>
			<td align="center"><%=map.get("OP_CODE")%></td>
			<td align="center"><%=map.get("OP_NAME")%></td>
			<td align="center"><%=Utility.getDateFormat(map.get("QUERY_TIME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("IP"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("MAC"))%></td>
			<td align="center"><%=map.get("QUERY_TYPE_NAME")%></td>
			<td align="center"><%=Utility.trimNull(map.get("CUST_NUMBER"))%></td>
			</tr>
		<%
			iCount ++ ;
			}
		%>  
		<!-- ���ʵ�ʼ�¼����Ҫ��ʾ�����������ӡ���� -->
		<%
		for(int i=0;i<(8-iCount);i++){
		%>      
	  	<tr class="tr0" >
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
		</tr>       
		<%}%> 
		<!-- ͳ�����еļ�¼ -->
		<tr class="trbottom" >
			<td align="left" colspan="8">
				<b>&nbsp;
					<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;
						<%= pagelist.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%>  <!--��-->
				</b>
			</td>
		</tr> 
	</TABLE>
		</td>
	</tr>
	<tr>
    	<td>
    		<table border="0" width="100%">
				<tr valign="top">
					<td><%=pagelist.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>
</table>
<!-- ��ϸ�б���ʾ end -->
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
