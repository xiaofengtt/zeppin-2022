<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.intrust.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.math.*" %>
<%@ page import="enfo.crm.web.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer showFlag = Utility.parseInt(request.getParameter("showFlag"),new Integer(1)); 

// 要返回页面的参数
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));
String _status = Utility.trimNull(request.getParameter("status"));

ProductInfoReposLocal prod_info = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();
boolean success = false;

if ("POST".equals(request.getMethod())) {
	DocumentFile2 file = new DocumentFile2(pageContext);
	file.parseRequest();

	product_id = Utility.parseInt(file.getParameter("product_id"),new Integer(0));
	preproduct_id = Utility.parseInt(file.getParameter("preproduct_id"),new Integer(0)); 
	showFlag = Utility.parseInt(file.getParameter("showFlag"),new Integer(1)); 
	open_flag = Utility.parseInt(file.getParameter("open"),null);
	_status = Utility.trimNull(file.getParameter("status"));
	class1 = Utility.trimNull(file.getParameter("class1"));
	sPage = Utility.trimNull(file.getParameter("page"));
	sPagesize = Utility.trimNull(file.getParameter("pagesize"));

	String product_desc = Utility.trimNull(file.getParameter("product_desc"));
	String notices = Utility.trimNull(file.getParameter("notices"));
	
	vo.setProduct_id(product_id);
	vo.setPreproduct_id(preproduct_id);
	vo.setProduct_desc(product_desc);
	vo.setNotices(notices);
	vo.setInput_man(input_operatorCode);
	prod_info.editIntroMaterial(vo);

	success = file.uploadProductIntroMaterial(product_id, preproduct_id, input_operatorCode);
}

Map p_map = new HashMap();
Map i_map = new HashMap();
if(preproduct_id.intValue()>0 || product_id.intValue()>0){
	vo.setProduct_id(product_id);
	vo.setPreproduct_id(preproduct_id);
	vo.setInput_man(input_operatorCode);
	List list = prod_info.listBySql(vo);
	if(list.size()>0)
		p_map = (Map)list.get(0); 

	list = prod_info.queryIntroMaterial(vo);
	if (list.size()>0) 
		i_map = (Map)list.get(0);

	String s = Utility.trimNull(i_map.get("FEASSTUDY"));
	if (! s.equals(""))
		i_map.put("FEASSTUDY_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));

	s = Utility.trimNull(i_map.get("FEASSTUDY_EASY"));
	if (! s.equals(""))
		i_map.put("FEASSTUDY_EASY_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));

	s = Utility.trimNull(i_map.get("STUDY_VOICE"));
	if (! s.equals(""))
		i_map.put("STUDY_VOICE_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));
}

prod_info.remove();

String returl = "product_info.jsp?page="+sPage+"&pagesize="+sPagesize+"&open="+open_flag+"&status="+_status+"&class1="+class1
					+"&product_id="+product_id+"&preproduct_id="+preproduct_id;
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
<script type="text/javascript">
var success = <%=success%>;

window.onload = function(){
	show(<%=showFlag%>);

	if (success) {
		sl_alert("已保存录入！");		
	}
}

function show(parm){
   for (var i=1; i<=4; i++) {
	     if(i!= parm){
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 }
	}
	document.theform.showFlag.value = parm;
}

function validateForm(form) {
	if (document.theform.study_voice && 
			! /\.wav$|\.mp3$|\.wma$|\.amr$/i.test(document.theform.study_voice.value)) {
		sl_alert("上传的录音文件格式不对！");
		return;
	}
	form.submit();
}

function confirmRemoveAttach(product_id, preproduct_id, atta_type, filename) {
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？')) {
		location.href = '/system/systemparam/delete_attach.jsp?product_id='+product_id
				+"&preproduct_id="+preproduct_id+"&atta_type="+atta_type+"&filename="+filename+"&showFlag="+document.theform.showFlag.value;
	}
}

function back() {
	location.href = "<%=returl%>";
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="product_intro_material_edit.jsp" enctype="multipart/form-data">
<input type="hidden" name="product_id" value="<%=product_id%>"/>
<input type="hidden" name="preproduct_id" value="<%=preproduct_id%>"/>
<input type="hidden" name="showFlag" value="<%=showFlag%>"/>

<input type="hidden" name="status" value="<%=_status%>"/>
<input type="hidden" name="open" value="<%=open_flag%>"/>
<input type="hidden" name="class1" value="<%=class1%>"/>
<input type="hidden" name="pagesize" value="<%=sPagesize%>"/>
<input type="hidden" name="page" value="<%=sPage%>"/>

<table border="0" width="97%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="6"><br><br></td>
	</tr>
	<tr>
		<td colspan="6" align="center"><font size="4"><b><%=Utility.trimNull(p_map.get("PRODUCT_NAME"))%></b></font></td>
	</tr>

	<tr><td>
<div align="left"  style="margin-left:10px;margin-bottom:10px;">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
		<TBODY>
			<TR>
				<TD vAlign=top>&nbsp;</TD>
				<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;产品说明书 </TD>
				<TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;可研报告 </TD>
                <TD id="d3" style="background-repeat: no-repeat;" onclick=show(3) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
					&nbsp;&nbsp;&nbsp;推介通知单 </TD>
                <TD id="d4" style="background-repeat: no-repeat;" onclick=show(4) vAlign=top width=120 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
					&nbsp;&nbsp;&nbsp;售前培训会录音 </TD>
			</TR>
		</TBODY>
	</TABLE>
</div>
<div id="r1" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="center"><textarea name="product_desc" cols="120" rows="50"><%=Utility.trimNull(i_map.get("PRODUCT_DESC"))%></textarea></td>
		</tr>
	</table>
</div>
<div id="r2" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right" width="30%">可研报告(PPT格式):</td>
			<td align="left" width="*">
			<% if (! Utility.trimNull(i_map.get("FEASSTUDY")).equals("")) { %>
				<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll((String)i_map.get("FEASSTUDY"),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("FEASSTUDY_ORIGIN_NAME"))%>"><%=Utility.trimNull(i_map.get("FEASSTUDY_ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
            		onclick="javascript:confirmRemoveAttach(<%=product_id%>,<%=preproduct_id%>,'FEASSTUDY','<%=Utility.replaceAll((String)i_map.get("FEASSTUDY"),"\\","/")%>');">删除</button>
			<% } else { %>
				<input type="file" name="feasstudy" size="60">
			<% } %>
				</td>
		</tr>
		<tr>
			<td align="right" width="30%">可研报告客户版(PPT格式):</td>
			<td align="left" width="*">
			<% if (! Utility.trimNull(i_map.get("FEASSTUDY_EASY")).equals("")) { %>
				<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll((String)i_map.get("FEASSTUDY_EASY"),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("FEASSTUDY_EASY_ORIGIN_NAME"))%>"><%=Utility.trimNull(i_map.get("FEASSTUDY_EASY_ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
            		onclick="javascript:confirmRemoveAttach(<%=product_id%>,<%=preproduct_id%>,'FEASSTUDY_EASY','<%=Utility.replaceAll((String)i_map.get("FEASSTUDY_EASY"),"\\","/")%>');">删除</button>
			<% } else { %>
				<input type="file" name="feasstudy_easy" size="60">
			<% } %>
			</td>
		</tr>
	</table>
</div>
<div id="r3" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="center"><textarea name="notices" cols="120" rows="50"><%=Utility.trimNull(i_map.get("NOTICES"))%></textarea></td>
		</tr>
	</table>
</div>
<div id="r4" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right" width="30%">售前培训会录音:</td>
			<td align="left" width="*">
			<% if (! Utility.trimNull(i_map.get("STUDY_VOICE")).equals("")) { %>
				<a title="查看附件" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll((String)i_map.get("STUDY_VOICE"),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("STUDY_VOICE_ORIGIN_NAME"))%>"><%=Utility.trimNull(i_map.get("STUDY_VOICE_ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
            		onclick="javascript:confirmRemoveAttach(<%=product_id%>,<%=preproduct_id%>,'STUDY_VOICE','<%=Utility.replaceAll((String)i_map.get("STUDY_VOICE"),"\\","/").replaceAll("'","\\\\'")%>');">删除</button>
			<% } else { %>
				<input type="file" name="study_voice" size="60" accept="audio/*"/>
			<% } %>
			</td>
		</tr>
	</table>
</div>
<div align="right" style="margin-right:5px;margin-top:5px;">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
	</button><!--保存-->
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:back();">返回 (<u>B</u>)
	</button>
</div>
	</td></tr>
</table>
</form>
</BODY>
</HTML>
