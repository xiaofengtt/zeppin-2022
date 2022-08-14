<%@ page language="java" contentType="text/html;charset=GBK" import="enfo.crm.tools.*"%>
<%
	boolean bSuccess = ((Boolean)request.getAttribute("bSuccess")).booleanValue();
	String grid_name = request.getParameter("grid_name");
	String sPage = request.getParameter("sPage");
	String[] queryFieldColStr = (String[])request.getAttribute("queryFieldColStr");
	String[] queryConditionTypeStr = (String[])request.getAttribute("queryConditionTypeStr");
	String[] queryConditionValueStr = (String[])request.getAttribute("queryConditionValueStr");
	String[] queryValueStr = (String[])request.getAttribute("queryValueStr");
	String[] queryInputTypeStr =(String[])request.getAttribute("queryInputTypeStr");
%>
<script language="javascript">
<%if(bSuccess){%>
	alert("删除成功");
	var url ="<%=grid_name%>?page=<%=sPage%>"
	<%for(int i=0;i<queryFieldColStr.length;i++){%>
		<%if(queryInputTypeStr[i].equals("radio")){%>
				+ "&<%=queryFieldColStr[i]%>=" + radioValue('<%=queryFieldColStr[i]%>')
		<%}else{%>
				+ "&<%=queryFieldColStr[i]%>=" + document.theform.<%=queryFieldColStr[i]%>.value
		<%}%>
		+ "&<%=queryFieldColStr[i]%>type=" + document.theform.<%=queryFieldColStr[i]%>type.value
		<%if(queryConditionTypeStr[i].indexOf("7")!=-1){//存在查询类型为两者之间是的处理%>
			+ "&<%=queryFieldColStr[i]%>one<%=i%>=" + document.theform.<%=queryFieldColStr[i]%>one<%=i%>.value
			+ "&<%=queryFieldColStr[i]%>two<%=i%>=" + document.theform.<%=queryFieldColStr[i]%>two<%=i%>.value
		<%}%>
	<%}%>;
	location =  url;
<%}%>
function refreshPage()
{
	location = "<%=grid_name%>?page=<%=sPage%>"
	<%for(int i=0;i<queryFieldColStr.length;i++){%>
		<%if(queryInputTypeStr[i].equals("radio")){%>
				+ "&<%=queryFieldColStr[i]%>=" + radioValue('<%=queryFieldColStr[i]%>')
		<%}else{%>
				+ "&<%=queryFieldColStr[i]%>=" + document.theform.<%=queryFieldColStr[i]%>.value
		<%}%>
		+ "&<%=queryFieldColStr[i]%>type=" + document.theform.<%=queryFieldColStr[i]%>type.value
		<%if(queryConditionTypeStr[i].indexOf("7")!=-1){//存在查询类型为两者之间是的处理%>
			+ "&<%=queryFieldColStr[i]%>one<%=i%>=" + document.theform.<%=queryFieldColStr[i]%>one<%=i%>.value
			+ "&<%=queryFieldColStr[i]%>two<%=i%>=" + document.theform.<%=queryFieldColStr[i]%>two<%=i%>.value
		<%}%>
		
	<%}%>
	+ "&QueryType=ok&pagesize=" + document.theform.pagesize.value;
}

function newQuery(){
	<%for(int i=0;i<queryFieldColStr.length;i++){%>
		<%if(queryInputTypeStr[i].equals("radio")){%>
			document.theform.<%=queryFieldColStr[i]%>.value=radioValue('<%=queryFieldColStr[i]%>');
		<%}else{%>
			document.theform.<%=queryFieldColStr[i]%>.value=document.theform.<%=queryFieldColStr[i]%>.value;
		<%}%>
		document.theform.<%=queryFieldColStr[i]+"type"%>.value=document.theform.<%=queryFieldColStr[i]+"type"%>.value;
		<%if(queryConditionTypeStr[i].indexOf("7")!=-1){//存在查询类型为两者之间是的处理%>
			document.theform.<%=queryFieldColStr[i]+"one"+i%>.value=document.theform.<%=queryFieldColStr[i]+"one"+i%>.value;
			document.theform.<%=queryFieldColStr[i]+"two"+i%>.value=document.theform.<%=queryFieldColStr[i]+"two"+i%>.value;
		<%}%>
	<%}%>	
	
	document.theform.submit();
}

//查询条件中取radio框值
function radioValue(name){
	var values = document.getElementsByName(name);
	var value ='';
	for(var i=0;i<values.length;i++){
		if(values[i].checked){
		 	value = values[i].value;
		}
	}
 	return value;
}

window.onload = function()
{
	initQueryCondition();
	<%for(int i=0;i<queryConditionTypeStr.length;i++){
	  if(queryConditionValueStr[i].equals("7") || queryConditionTypeStr[i].equals("7")){
	%>
	  document.getElementsByName('<%=queryFieldColStr[i]%>')[0].style.setAttribute("display","none");
	  if(document.getElementById('<%=queryFieldColStr[i]%>_dateButton')){
		document.getElementById('<%=queryFieldColStr[i]%>_dateButton').style.setAttribute("display","none");//日期框箭头按钮
	  }
	  document.getElementById('divbet<%=i%>').style.display='';
	  var conditions ='<%=queryValueStr[i]%>'.split(',');
	  if(conditions[0]==undefined){
	  	conditions[0] ='';
	  }
	  if(conditions[1]==undefined){
	  	conditions[1] ='';
	  }
	  document.theform.<%=queryFieldColStr[i]%>one<%=i%>.value=conditions[0];
	  document.theform.<%=queryFieldColStr[i]%>two<%=i%>.value=conditions[1];
	<%}}%>
};
</script>