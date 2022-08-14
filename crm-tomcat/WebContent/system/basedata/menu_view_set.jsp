<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String v_menu_id = Utility.trimNull(request.getParameter("v_menu_id"));
boolean edit = request.getParameter("edit")!=null;
String jsonStr = Argument.getMenuViewStr(v_menu_id,""); 
String tempView = Argument.getMyMenuViewStr(v_menu_id,input_operatorCode);
MenuInfoLocal menu = EJBFactory.getMenuInfo();
List rsList = menu.listMenuViews(v_menu_id,"");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<base target="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language=javascript>
//jsonStr应该从fieldsdim表根据menu_id取出，此处写死，有时间再改
var jsonStr = <%=jsonStr%>;

$(function(){
    $("input[type='checkbox']").click(function(){
            refreshStyle(this.id);
        });
    var cBox = "<%=tempView%>"; 
    var cBoxArr;
    if(cBox!=""){
    	cBoxArr = cBox.split("$");
    	for(i=0;i<cBoxArr.length;i++){
    		if(cBoxArr[i]!=""){
    			$('#'+cBoxArr[i]).attr("checked","checked");
    			refreshStyle(cBoxArr[i]);
    		}
    	}
    }
});

function refreshStyle(objId){
    if($('#td_'+objId).size()==0){
        $('#titleStyle').append("<td id='td_"+objId+"'>"+eval("jsonStr."+(objId+".field_name").toLocaleUpperCase())+"</td>");    
    }else{
        $('#td_'+objId).remove();
    }        
}

function styleOk(){
    var obj = $('#titleStyle td');
    var result = "";
    if(obj.size()!=0){
        for(i=0;i<obj.length;i++){
            temp = obj[i].id.substring(3);
            result = result+eval("jsonStr."+(temp+".field").toLocaleUpperCase())+"$";//fieldName    
        }
        $('#viewstr').val(result);
        document.theform.submit();
    }else{
        window.returnValue = null;
        window.close();
    }
    
}
</script>
</HEAD>
<BODY class="BODY body-nox">

<form name="theform" method="post" action="<%=request.getContextPath()%>/system/basedata/menu_view_set_action.jsp">
<input type="hidden" name="viewstr" id="viewstr">
<input type="hidden" name="v_menu_id" value="<%=v_menu_id%>">

<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr>
		    <td class="page-title">
				<font color="#215dc6"><b><%=menu_info%>>><%=edit?"页面可编辑字段选择":LocalUtilis.language("menu.menuViewSet",clientLocale)%> </b></font>
			</td>
	    </tr><!--页面显示自定义字段选择-->
    </table>
</div>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0" >
        <tr>
            <td align="center" colspan="4"><font size="4" color="blue"><%=edit?"页面可编辑字段":LocalUtilis.language("class.rsList",clientLocale)%> </font></td>
        </tr><!--页面显示自定义可选字段-->
		<tr>
        <%
       	Map rowMap =null;
       	if(rsList!=null){
       		for(int i=0;i<rsList.size();i++){
       			rowMap = (Map)rsList.get(i);
       	%>
       		<td>
       			<input type="checkbox" class="flatcheckbox" id="<%=Utility.trimNull(rowMap.get("FIELD"))%>" name="<%=Utility.trimNull(rowMap.get("FIELD"))%>"><%=Utility.trimNull(rowMap.get("FIELD_NAME"))%>
       		</td>			
       	<%
       			if(i%4==3){
       	%>
       	</tr>
       	<tr>
       	<%		
       			}
       			
       		}
       	}
        %>
        </tr>
        <tr>
	        <td colspan="4">
	            <hr/>
	        </td>
	    </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="center" id="titleName"><font size="4" color="blue"><%=edit?"页面可编辑字段预览":LocalUtilis.language("menu.menuViewStylePreview",clientLocale)%> </font></td><!--页面显示自定义样式预览-->
        </tr>
        <tr>
            <table border="0" width="100%" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor">
                <tr class="trh" id="titleStyle">
                </tr>
            </table>
        </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <button type="button"   class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--确定-->
                <!--<button type="button"  class="xpbutton3">默认</button>&nbsp;&nbsp;&nbsp;-->
                <button type="button"   class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--取消-->
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>