<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer serial_no =  Utility.parseInt(request.getParameter("serial_no"),null);
Integer team_id =  Utility.parseInt(request.getParameter("team_id"),null);
String tempUrl = "";
List list = null;
boolean success = false;
TmanagerteammembersVO vo = new TmanagerteammembersVO();
TmanagerteamsLocal tmanagerteammembers_Bean = EJBFactory.getTmanagerteams();

vo.setSerial_no(serial_no);
vo.setTeam_id(team_id);

String[] totalColumn = new String[0];

list = tmanagerteammembers_Bean.list_query1(team_id);
if(list==null||list.size()==0){
	list = tmanagerteammembers_Bean.list_query(vo);
	success = true;
}

tempUrl = tempUrl+ "&serial_no=" + serial_no+ "&team_id=" + team_id;

sUrl = sUrl + tempUrl;
//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;
%>

<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("index.menu.teamMemberManage",clientLocale)%> </title><!-- 团队成员管理 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	var oState = {	
	}
	
	window.onload = function(){
	}
	
	function $(_id){
		return document.getElementById(_id);
	}
	
	function $$(_name){
		return document.getElementsByName(_name)[0];
	}
	
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}

</script>
</head>

<body class="body body-nox">
<form id="theform" method="post" action="<%=request.getContextPath()%>/affair/base/team_number.jsp">

<input type="hidden" name="refresh_flag" />
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="team_id" value="<%=team_id%>"/>

<table cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
	<tr>
		<td align="left" class="page-title">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
</table>
<br/>
<table valign="middle" align="center" cellspacing="0" cellpadding="3" width="100%" >
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
		<tr class="trh">	
	         <td style="width: 105px;" align="center"><%=LocalUtilis.language("class.memberName",clientLocale)%> </td><!-- 成员名称 -->
	         <td  align="center"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> </td><!-- 描述 -->                        
	         <td style="width: 14px;"></td>
	    </tr>
    </table>
 <div style="height:210px;  margin: 0 0 0 0; overflow-y: scroll; overflow-x: no;" >   
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
<%
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
	if(success == true){
%>   	
         <tr class="tr<%=iCount%2%>" style="height: 25px;">
            <td align="center" style="width: 105px;"><%=Utility.trimNull(map.get("MANAGERNAME"))%></td>
            <td align="center" ><%=Utility.trimNull(map.get("DESCRIPTION"))%></td>              
         </tr>   
<%}else{%>
		<tr class="tr<%=iCount%2%>" style="height: 25px;">
            <td align="center" style="width: 105px;"><%=Utility.trimNull(map.get("TEAM_NAME"))%></td>
            <td align="center" ><%=Utility.trimNull(map.get("DESCRIPTION"))%></td>              
         </tr> 		   
<%} }%>
<%
for(int i=0;i<(8-iCount);i++){
%>      	
         <tr class="tr0" style="height: 25px;">
            <td align="center" style="width: 105px;" >&nbsp;</td>
            <td align="center">&nbsp;</td> 
         </tr>           
<%}%> 
	</TABLE>
</div>	
	</td>
	</tr>
</table>

</form>
</body>
</html>