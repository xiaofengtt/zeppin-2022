<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<% String path = request.getContextPath();%>
<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/js/extjs/css/ext-all.css" />
<script type="text/javascript" src="<%=path%>/js/extjs/pub/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/js/extjs/pub/ext-lang-zh_CN.js"></script>
<script type="text/javascript">

<!--
Ext.onReady(function(){
			var inputDate = new Ext.form.DateField({
			        fieldLabel: '退费日期',           
			        name: 'bean.inputDate',
			        id:'inputDate',
			        format:'Y-m-d',
			        allowBlank:false, 
			        readOnly:true,
			        anchor: '29%'
			    }); 
			var date;
			<s:if test='bean.inputDate != null'>
				date='<s:date name="bean.inputDate" format="yyyy-MM-dd" />' ;
			</s:if><s:else>
				date=(new Date).format('Y-m-d');
			</s:else>
			inputDate.on('render',function showvalue(p,_record,_options){
					inputDate.setValue(date);
					inputDate.setRawValue(date);
			},inputDate);
			
			inputDate.render('showinputDate'); 
	});
//--!> 
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "feeform" action="/entity/fee/feeRefund_refundexe.action" method="get" onsubmit="javascript:document.getElementById('onsubmit').innerHTML='';">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">为学生退费</div></td>
  </tr>   
  <tr>
    <td valign="top" class="pageBodyBorder_zlb" align="center">
        <div class="cntent_k" id="zlb_content_start">
          <p><font color="red"><s:property value='msg'/></font></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">          

            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">退费日期*</span></td>
              <td valign="bottom"><div align="left" class="postFormBox" id = "showinputDate"></div></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;</span></td>
              <td valign="bottom"><textarea name="bean.note" rows="4" cols="25"><s:property value='bean.note'></s:property></textarea></td>
            </tr>     
            
            <tr class="postFormBox">
              <td ><input type="hidden" name="ids" value='<s:property value="ids"/>' ></td>
              <td><div id="onsubmit">
              <s:if test='msg.indexOf("成功")>=0'><input type="button" value ="返回" onclick="javascript:window.navigate('/entity/fee/feeRefund.action?search=true');" /></s:if>
              <s:else><input type="submit" value = "提交" /></s:else>
              </div>
              
              </td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>