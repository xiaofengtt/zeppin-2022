<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.dao.*"  %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<!--Force IE6 into quirks mode with this comment tag-->
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=5"/>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/mstree/deeptree.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
var menus = new Array();
menus[0] = "<%=LocalUtilis.language("message.addCustRating",clientLocale)%> ";//��ӿͻ��ּ�
menus[1] = "<%=LocalUtilis.language("message.modifyCustRating",clientLocale)%> ";//�޸Ŀͻ��ּ�
menus[2] = "<%=LocalUtilis.language("message.deleteCustRating",clientLocale)%> ";//ɾ���ͻ��ּ�
menus[3] = "-";
menus[4] = "<%=LocalUtilis.language("message.refresh",clientLocale)%> ";//ˢ��
menus[5] = "<%=LocalUtilis.language("message.back",clientLocale)%> ";//����
var links = new Array();
links[0] = "javascript: newInfo();";
links[1] = "javascript: showInfo();";
links[2] = "javascript: removeInfo();";
links[3] = "";
links[4] = "javascript: location.reload();";
links[5] = "javascript: history.back();";

var vis = new Array();
vis[0] = true;
vis[1] = true;
vis[2] = true;
vis[3] = true;
vis[4] = true;
vis[5] = true;

var define_id = "";
var define_name = "";
var paremt_id = "";
var level_id = "";//�㼶
var canmodi = "";//�Ƿ���޸�1��2��
var candel = "";//�Ƿ��ɾ��
var canadd = "";
var detail_id = "";

function getPopInfo()
{
	if(window.event.srcElement == null)      
		return false;
	var obj = window.event.srcElement;
	var hrefs = obj.href;
	if (hrefs == null || hrefs == "")
		return false;
	var index = hrefs.indexOf("#") + 1;
	
	var items = hrefs.substring(index, hrefs.length);
	define_id = items.split('|')[0];
	level_id = items.split('|')[1];
	canmodi = items.split('|')[2];
	detail_id = items.split('|')[3];
	candel = items.split('|')[4];
	canadd =  items.split('|')[5];
	define_name = obj.innerText;
	
	return true;
}

/*�༭Ⱥ��*/
function showInfo()
{
    if(define_id == "" || define_id == "0")//depart_id=0����Ϊ�գ��Ҽ��޸�ʧЧ
    	return;
    if(canmodi == "" || canmodi == "2" || canmodi == "0"){
    	sl_alert("����Ϊϵͳ�趨���޷��޸ģ�");
    	return;
    }	
 		
    if(showModalDialog('client_class_define_edit.jsp?define_id='+define_id+'&flag=edit'+'&level_id='+(level_id-1)+'&detail_id='+detail_id+'&canmodi='+canmodi,'','dialogWidth:420px;dialogHeight:310px;status:0;help:0')!= null){
		sl_update_ok();
		location.reload();
	}
}

/*�����ͻ��ּ�����*/
function newInfo()
{
	if(canadd == "" || canadd == "2" || canadd == "0"){
		sl_alert("����Ϊϵͳ�趨���޷��޸ģ�");
		return;
	}	
 		
	if(showModalDialog('client_class_define_new.jsp?define_id='+define_id+'&parent_define_name='+define_name+'&level_id='+level_id+'&detail_id='+detail_id,'', 'dialogWidth:420px;dialogHeight:310px;status:0;help:0')!=null){
		sl_update_ok();
		location.reload();
	}
}

/*ɾ��Ⱥ��*/
function removeInfo(){
 	if(candel == "" || candel == "2" || candel == "0" || define_id == "0"){
 		sl_alert("����Ϊϵͳ�趨���޷�ɾ����");
 		return;
 	}	
 		
	//�ò�����ɾ��   //Ⱥ��   //Ҫ����
    if(!sl_confirm("<%=LocalUtilis.language("message.deleteManager",clientLocale)%>(" + define_name + ")<%=LocalUtilis.language("message.group",clientLocale)%>��<%=LocalUtilis.language("message.toContinue",clientLocale)%>"))
	     return;
	location = 'client_class_define_remove.jsp?define_id='+define_id+'&detail_id='+detail_id+'&level_id='+(level_id-1);
}

/*�鿴Ⱥ��*/
function queryInfo()
{
	if(depart_id == "" || depart_id == "0")
		return;
	location="client_group_member_list.jsp?group_id=" + depart_id + "&group_name="+depart_name;
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform">
<TABLE  height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
	 <TD>
            <TABLE class=flyoutMenu >
      </table>
    </TD>
			<TD vAlign=top align=left width="100%">
			<TABLE  cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%">
							<tr>
								<td colspan="2" class="page-title">
									<font color="#215dc6"><b><%=menu_info%></b></font>
								</td>
							</tr>
							<tr>
                                <!--ˢ��-->
								<td align="right" valign="bottom">
                                <div class="btn-wrapper">
									<button type="button"  class="xpbutton3" accessKey=r name="btnRefresh" title='<%=LocalUtilis.language("message.refresh",clientLocale)%>' onclick="javascript:location.reload();"><%=LocalUtilis.language("message.refresh",clientLocale)%> (<u>R</u>)</button>
								</div>
								</td>
							</tr>
						</table>
						</TD>
					</TR>
					<tr>
						<td>
						<div id="deeptree" class="deeptree" align="left" CfgXMLSrc="client_class_define_tree.xml" style="width: 100%"></div>
						</td>
					</tr>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
