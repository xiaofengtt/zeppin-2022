<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*,enfo.crm.intrust.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
//处理中后期不同的菜单调用相同的页面已解决菜单问题，才添加此参数
String menu_id_v = Utility.trimNull(request.getParameter("menu_id_v"));
if("".equals(menu_id_v))	
	menu_id_v = menu_id;
String jsonStr = Argument.getMenuViewStr(menu_id_v,""); 
String tempView = Argument.getMyMenuViewStr(menu_id_v,input_operatorCode);
MenuInfoLocal menu = EJBFactory.getMenuInfo();
List rsList = menu.listMenuViews(menu_id_v,"");
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
var jsonStr = <%=jsonStr%>;

$(function(){
    $("input[type='checkbox']").click(function(){
    		if(this.id != "isSelectAll") //排除全选框
    		{
            	refreshStyle(this.id);
            	selectAction(this.id);//添加
            }
        });
    var cBox = "<%=tempView%>"; 
    var cBoxArr;
    if(cBox!=""){
    	cBoxArr = cBox.split("$");
    	for(i=0;i<cBoxArr.length;i++){
    		if(cBoxArr[i]!=""){
    			$('#'+cBoxArr[i]).attr("checked","checked");
    			refreshStyle(cBoxArr[i]);
    			selectAction(cBoxArr[i]);//添加
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

/*add by dingyj 20100507全选或反选*/
function setChecked()
{
	var _item = document.getElementById("isSelectAll");
	var _items = document.getElementsByTagName("input");
	//是否选中
	for(var i=0; i<_items.length; i++)
	{
		if(_items[i].type == "checkbox" && _items[i].id != "isSelectAll")
		{
			_items[i].checked = _item.checked;//选中
			//选择添加显示预览
			if(_item.checked)
			{
				if($('#td_'+_items[i].id).size() == 0){
					$('#titleStyle').append("<td id='td_"+_items[i].id+"'>"+eval("jsonStr."+(_items[i].id+".field_name").toLocaleUpperCase())+"</td>"); 
				}
			}   
			else//未选中移除显示预览
			{
				if($('#td_'+_items[i].id).size() != 0){
					$('#td_'+_items[i].id).remove();
				}
			}	 
		}
	}
}

/*add by dingyj 20100507判断*/
function selectAction(strID)
{
	var _items = document.getElementsByTagName("input");
	var _isItem = document.getElementById("isSelectAll");
	var _item =document.getElementById(strID);
	//如果有一个未选中，则全选不选中；反之都选择，则全选选中
	for(var i=0; i<_items.length; i++)
	{
		if(_items[i].type == "checkbox" && _items[i].id != "isSelectAll")
		{
			if(!_items[i].checked)
			{
				_isItem.checked = false;
				return;
			}	
			_isItem.checked = true;
		}
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>menu_view_set_action.jsp">
<input type="hidden" name="viewstr" id="viewstr">
<input type="hidden" name="menu_id_v" value="<%=menu_id_v %>">
<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr>
		    <td class="page-title"><b><%=menu_info%>>><%=LocalUtilis.language("menu.menuViewSet",clientLocale)%> <!--页面显示自定义字段选择--></b></td>
	    </tr>
    </table>
</div>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0" class="table-select">
        <tr>
            <td align="center" colspan="4"><font size="4" color="blue"><%=LocalUtilis.language("class.rsList",clientLocale)%> <!--页面显示自定义可选字段--></font></td>
        </tr>
        <tr>
        	<td colspan="4">
        		<input type="checkbox" class="flatcheckbox" id="isSelectAll" name="isSelectAll" onClick="setChecked()"><%=LocalUtilis.language("message.selectAll",clientLocale)%> <!--全选-->
        	</td>
        </tr>
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
            <td align="center" id="titleName"><font size="4" color="blue"><%=LocalUtilis.language("menu.menuViewStylePreview",clientLocale)%> <!--页面显示自定义样式预览--></font></td>
        </tr>
        <tr>
        	<td>
	            <table border="0" width="100%" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor">
	                <tr class="trh" id="titleStyle">
	                </tr>
	            </table>
            </td>
        </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <button type="button"  class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.ok2",clientLocale)%> <!--确定--></button>&nbsp;&nbsp;&nbsp;
                <!--<button type="button"  class="xpbutton3">默认</button>&nbsp;&nbsp;&nbsp;-->
                <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> <!--取消--></button>&nbsp;&nbsp;&nbsp;
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>