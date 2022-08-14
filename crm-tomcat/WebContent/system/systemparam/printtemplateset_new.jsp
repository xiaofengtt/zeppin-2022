<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ȡҳ�����
String catalog_code = Utility.trimNull(request.getParameter("catalog_code"));
String catalog_name = Utility.trimNull(request.getParameter("catalog_name"));

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

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
<form name="theform" method="POST" action="printtemplateset_new_do.jsp">
<input type="hidden" name="catalog_code" value="<%=catalog_code%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><IMG src="/images/member.gif" align=absBottom border=0
			width="32" height="28"><b><%=menu_info%>>><%=Utility.trimNull(catalog_name)%>��ӡģ���½�</b></td>
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
        <td><input type="text" value="" name="template_code" size="30"></td>
		<td align="right">ģ������:</td>
		<td><input type="text" size="70" value="" name="template_name"></td>
	</tr> 
	<tr>
	    <td align="right" valign="top">��ӡ��ʽ:</td>
	    <td colspan="3">
	        <script type="text/plain" id="editor" name="template_content" style="float:left"></script>
        </td>
	</tr>
    <tr>
        <td align="right" valign="top">��ע:</td>
        <td colspan="3">
        <textarea style="width:90%;height:50px;" name="summary"></textarea>
        </td>
    </tr>
</table>

</form>

<%@ include file="/includes/foot.inc"%>	
</BODY>
<%
vo.setCatalog_code(Utility.trimNull(catalog_code));

IPageList printTemplateSetPageList = dictparam.getPrintElement(vo,-1,-1);
List printTemplateSetList = printTemplateSetPageList.getRsList();
Map map = null;
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
            return false;  
        }
    }
</script>
</HTML>
<%
dictparam.remove();
%>