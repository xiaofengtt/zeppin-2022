<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer template_id = Utility.parseInt(request.getParameter("template_id"),null);

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();


vo.setTemplate_id(template_id);

IPageList printTemplateSetPageList = dictparam.getPrintTemplate(vo,-1,-1);
List printTemplateSetList = printTemplateSetPageList.getRsList();
Map map = (Map)printTemplateSetList.get(0);
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link rel="stylesheet" href="/includes/ueditor/themes/default/ueditor.css" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type="text/javascript" src="/includes/ueditor/editor_config.js"></script>
<script type="text/javascript" src="/includes/ueditor/editor.js"></script>	
<BODY class="BODY" >    
<form name="theform" method="POST" action="printtemplateset_set_do.jsp">
<input type="hidden" name="template_id" value="<%=Utility.trimNull(map.get("TEMPLATE_ID"))%>">
<input type="hidden" name="catalog_code" value="<%=Utility.trimNull(map.get("CATALOG_CODE"))%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><IMG src="/images/member.gif" align=absBottom border=0
			width="32" height="28"><b><%=menu_info%>>><%=Utility.trimNull(map.get("CATALOG_NAME"))%>_<%=Utility.trimNull(map.get("TEMPLATE_NAME"))%>��ӡ��ʽ����</b></td>
		<td>
	</tr>
	<tr>
		<td align="right">
			<%if (input_operator.hasFunc(menu_id, 100))	{%>
			<button type="button"  class="xpbutton3" accessKey=s onclick="javascript:save();">����(<u>S</u>)</button>
			&nbsp;&nbsp;&nbsp;<%}%>
			<button type="button"  class="xpbutton3" accessKey=b onclick="javascript:history.back();">����(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table id="table3" border="0" cellspacing="1" cellpadding="2" width="100%" >
	<tr>
		<td align="right">ģ�����:</td>        
        <td><input type="text" name="template_code" size="30" value="<%=Utility.trimNull(map.get("TEMPLATE_CODE"))%>"></td>
		<td align="right">ģ������:</td>
		<td><input type="text" name="template_name" size="70" value="<%=Utility.trimNull(map.get("TEMPLATE_NAME"))%>"></td>
	</tr> 
	<tr>
	    <td align="right" valign="top">��ӡ��ʽ:</td>
	    <td colspan="3">
	        <script type="text/plain" id="editor" name="template_content" style="float:left"><%=Utility.trimNull(map.get("TEMPLATE_CONTENT"))%></script>
        </td>
	</tr>
    <tr>
        <td align="right" valign="top">��ע:</td>
        <td colspan="3">
        <textarea style="width:90%;height:50px;" name="summary"><%=Utility.trimNull(map.get("SUMMARY"))%></textarea>
        </td>
    </tr>
</table>

</form>

<%@ include file="/includes/foot.inc"%>	
</BODY>
<%
vo.setCatalog_code(Utility.trimNull(map.get("CATALOG_CODE")));

printTemplateSetPageList = dictparam.getPrintElement(vo,-1,-1);
printTemplateSetList = printTemplateSetPageList.getRsList();
%>
<script type="text/javascript">
    UE.plugins['printelement'] = function() {
        var me = this;
        me.setOpt({ 'printelement':[
            <%            
            for(int i=0;i<printTemplateSetList.size();i++){
                map = (Map)printTemplateSetList.get(i);
            %>
            {tag:'$_<%=map.get("ELEMENT_CODE")%>_$', label:'<%=map.get("ELEMENT_NAME")%>'},
            <%
            }
            %>
            {tag:'', label:''}
            ]});    
    };
    var editor = new UE.ui.Editor({
            UEDITOR_HOME_URL : '/includes/ueditor/'
        });
    editor.commands['printelement']={
        execCommand : function(cmdName,val){ 
            var range = this.selection.getRange();
            var pelement = this.document.createTextNode(val);
            range.collapse(true);
            range.insertNode(pelement).setStartAfter(pelement).setCursor(false,true);
        }    
    }
    
    editor.render('editor');
    
    function save(){
        if(!sl_check(document.theform.template_code,"ģ�����",24)) return false;
        if(!sl_check(document.theform.template_name,"ģ������",200)) return false;
        if(editor.hasContents()){
            if(confirm("ȷ��Ҫ�����趨�Ĵ�ӡģ����")){
                editor.sync(); 
                document.theform.submit();
            }else{
                return false;
            }

        }else{
            alert("��ӡ��ʽΪ�գ���༭�󱣴�");    
        }
    }
</script>
</HTML>
<%
dictparam.remove();
%>