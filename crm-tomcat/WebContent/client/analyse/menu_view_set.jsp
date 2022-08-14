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
	    </tr><!--页面显示自定义字段选择-->
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
        		&nbsp;&nbsp;&nbsp;客户价值名称:
        		<input type="text" id="worth_name" name="worth_name" value="" size="30" onkeydown="javascript:nextKeyPress(this)">
        	</td>
        </tr>
    </table>	
	<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
	    <tr>
        	<td colspan="5">
        		&nbsp;&nbsp;&nbsp;<font size="2"><b>计算值</b></font>
        	</td>
        </tr>
        <tr align="center">
        	<td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="TOTAL_MONEY" name="TOTAL_MONEY">累计购买金额
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="CURRENT_MONEY" name="CURRENT_MONEY">存量金额
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="RECOMMEND_MONEY" name="RECOMMEND_MONEY">推荐金额
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="RG_TIMES" name="RG_TIMES">认购次数
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="FIRT_RG_DATE" name="FIRT_RG_DATE">首次购买时间
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LAST_RG_DATE" name="LAST_RG_DATE">最近购买时间
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LAST_END_DATE" name="LAST_END_DATE">最晚合同到期日
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="LIVE_GRADE" name="LIVE_GRADE">客户活跃度
        	</td>
        </tr>
    </table>		
    <br>
    <table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
	    <tr>
        	<td colspan="5">
        		&nbsp;&nbsp;&nbsp;<font size="2"><b>运算符</b></font>
        	</td>
        </tr>
        <tr align="center">
        	<td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="plus" name="plus">加
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="subtract" name="subtract">减
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="multiply" name="multiply">乘
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="divide" name="divide">除
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="AND" name="AND">与
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="OR" name="OR">或
        	</td>
        	<td width="150px" align="left" colspan="2">
        		<input type="checkbox" class="flatcheckbox" id="NOT" name="NOT">非
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="greatherThan" name="greatherThan">大于
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="greatherThan_equalTo" name="greatherThan_equalTo">大于等于
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="lessThan" name="lessThan">小于
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="lessThan_equalTo" name="lessThan_equalTo">小于等于
        	</td>
        </tr>
        <tr>
            <td width="50px" align="left">
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="equalTo" name="equalTo">等于
        	</td>
        	<td width="150px" align="left">
        		<input type="checkbox" class="flatcheckbox" id="unequalTo" name="unequalTo">不等于
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
        				&nbsp;&nbsp;&nbsp;<font size="2"><b>金额</b></font>
        			</td>
                </tr>
                <tr>
                	<td width="50px" align="left">
        			</td>
                	<td align="left">
		        		<input type="text" id="money" name="money" value="" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();">
		        		&nbsp;&nbsp;<button type="button"  class="xpbutton3" onclick="javascript:window.close();">添加金额</button>&nbsp;&nbsp;&nbsp;
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
            <td align="center" id="titleName"><font size="4" color="blue">公式预览</font></td><!--页面显示自定义样式预览-->
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
                <button type="button"  class="xpbutton3" onclick="javascript:styleOk();"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--确定-->
                <!--<button type="button"  class="xpbutton3">默认</button>&nbsp;&nbsp;&nbsp;-->
                <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--取消-->
           <td>
        </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>