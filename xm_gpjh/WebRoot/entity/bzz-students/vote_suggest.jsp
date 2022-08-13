<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>调查建议</title>
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">
		<script>
		function tosuggest(){
		var id = '<s:property value="peVotePaper.id"/>' ;
		window.location="/entity/first/firstPeVotePaper_toSuggest.action?bean.id=" + id;
		
		}
		</SCRIPT>
  </head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
<table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#FFD9AD" bordercolorlight="#FFFFFF">
  <tr>
    <td align="center" bgcolor="#FF6600" class="16title">调 查 建 议</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding-left:5px"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
        <s:iterator value="prVoteSuggestList" status="num">
        <tr>
          <td class="14title"><s:property value="#num.index+1"/>：</td>
        </tr>        
        <tr>
          <td class="12content"><s:property value="note" escape="false"/></td>
       </tr>
		</s:iterator>
        <tr>
          <td height="40" align="center"> 
            <input name="Submit1" type="submit" id="Submit1" value="关闭窗口" onClick="javascript:window.close()">
          </td>
        </tr>
      </table>
</td>
  </tr>
</table>       
  </body>
</html>
