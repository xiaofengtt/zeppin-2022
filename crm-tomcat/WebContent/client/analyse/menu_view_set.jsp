<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

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

</HEAD>
<BODY class="BODY">

<form name="theform" method="post" action="<%=request.getContextPath()%>/system/basedata/menu_view_set_action.jsp">
<input type="hidden" name="viewstr" id="viewstr">
<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr>
		    <td><img src="<%=request.getContextPath()%>/images/member.gif" border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%>>><%=LocalUtilis.language("menu.menuViewSet",clientLocale)%> </b></font></td>
	    </tr><!--ҳ����ʾ�Զ����ֶ�ѡ��-->
	    <tr>
	        <td>
	            <hr/>
	        </td>
	    </tr>
    </table>
</div>
<div>
	<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 0px; border-style: dashed; border-color: blue;margin-top:5px;">
	    <tr>
        	<td colspan="5">
        		&nbsp;&nbsp;&nbsp;�ͻ���ֵ����:
        		<input type="text" id="worth_name" name="worth_name" value="" size="30" onkeydown="javascript:nextKeyPress(this)">
        	</td>
        </tr>
    </table>	
	<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
	    <tr>
        	<td colspan="5">
        		&nbsp;&nbsp;&nbsp;<font size="2"><b>����ֵ</b></font>
        	</td>
        </tr>
        <tr align="center">
        	<td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="TOTAL_MONEY" name="TOTAL_MONEY">�ۼƹ�����
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="CURRENT_MONEY" name="CURRENT_MONEY">�������
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="RECOMMEND_MONEY" name="RECOMMEND_MONEY">�Ƽ����
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="RG_TIMES" name="RG_TIMES">�Ϲ�����
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="FIRT_RG_DATE" name="FIRT_RG_DATE">�״ι���ʱ��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LAST_RG_DATE" name="LAST_RG_DATE">�������ʱ��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LAST_END_DATE" name="LAST_END_DATE">�����ͬ������
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LIVE_GRADE" name="LIVE_GRADE">�ͻ���Ծ��
        	</td>
        </tr>
    </table>		
    <br>
    <table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
	    <tr>
        	<td colspan="5">
        		&nbsp;&nbsp;&nbsp;<font size="2"><b>�����</b></font>
        	</td>
        </tr>
        <tr align="center">
        	<td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="plus" name="plus">��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="subtract" name="subtract">��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="multiply" name="multiply">��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="divide" name="divide">��
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="AND" name="AND">��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="OR" name="OR">��
        	</td>
        	<td width="150px" align="left" colspan="2">
        		<input type="checkbox" class="flatcheckbox" id="NOT" name="NOT">��
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="greatherThan" name="greatherThan">����
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="greatherThan_equalTo" name="greatherThan_equalTo">���ڵ���
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="lessThan" name="lessThan">С��
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="lessThan_equalTo" name="lessThan_equalTo">С�ڵ���
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="equalTo" name="equalTo">����
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo">������
        	</td>
        </tr>
    </table>		
</div>
<br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
        	<td>
            <table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
            	<tr>
                	<td colspan="2">
        				&nbsp;&nbsp;&nbsp;<font size="2"><b>���</b></font>
        			</td>
                </tr>
                <tr>
                	<td width="50px" align="left">
        			</td>
                	<td align="left">
		        		<input type="text" id="money" name="money" value="" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
		        		&nbsp;&nbsp;<button type="button"  class="xpbutton3" onclick="javascript:window.close();">��ӽ��</button>&nbsp;&nbsp;&nbsp;
		        	</td>
                </tr>
                <tr>
                	<td width="50px" align="left">
        			</td>
                	<td align="left">
		        		<span id="rg_money_cn" class="span"></span>
		        	</td>
                </tr>
            </table>
            </td>
        </tr>
    </table>
</div>
<br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="center" id="titleName"><font size="4" color="blue">��ʽԤ��</font></td><!--ҳ����ʾ�Զ�����ʽԤ��-->
        </tr>
        <tr>
        	<td align="center"><textarea rows="2" name="summary2" onkeydown="javascript:nextKeyPress(this)" cols="100" disabled width="100%"></textarea></td>
        </tr>
    </table>
</div>
<br><br><br>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td align="right">
                <button type="button"  class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--ȷ��-->
                <!--<button type="button"  class="xpbutton3">Ĭ��</button>&nbsp;&nbsp;&nbsp;-->
                <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--ȡ��-->
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>