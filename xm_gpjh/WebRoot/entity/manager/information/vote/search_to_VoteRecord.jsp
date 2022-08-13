<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript">
		function chk(){
			var applyno=document.getElementById('applyno');
			if(applyno.value==''){
				alert('培训项目为必填项，请确认选择！');
				applyno.focus();
				return false;
			}
			document.getElementById('searchVote').submit();
		}
		
	</script>
</head>
  
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">查看投票记录</div>
   	<div class="k_cc">
		<form id='searchVote' action="/entity/information/prVoteRecord.action" method="get">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:50px">
            <tr valign="middle"> 
				<td width="15%" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 调查问卷：*</span></td>
				<td class="postFormBox" style="padding-left:10px">
					<select  name="applynoId" id='applyno' >
						<option value = "">请选择...</option>
						<s:iterator value="paperType" id="proapply">
							<option value="<s:property value="#proapply[0]"/>"><s:property value="#proapply[1]"/></option>
						</s:iterator>
					</select>
				</td>
             </tr>
             <!-- 
            <tr valign="middle"> 
               <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
					<span class="name"> 培训单位：&nbsp;&nbsp;</span>
               </td>
               <td class="postFormBox" style="padding-left:10px">
               	<select name="peUnit" id='peunit' onchange="initSubject(this.value,'applyno')">
               		<option value = "">请选择...</option>
               	</select>
               </td>
             </tr>
			<tr valign="middle"> 
				<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
					<span class="name"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：&nbsp;&nbsp;</span>
				</td>
				<td class="postFormBox" style="padding-left:10px">
					<select name="peSubject" id='expsubject'>
						<option value = "">请选择...</option>
					</select>
			   </td>
			</tr> -->
			<tr>
				<td height="50" align="center" colspan="2">
					<input type="button" value="查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看" style="width:100" onClick="chk()"/>
				</td>
			</tr>
        </table>
	</form>
	</div>
</div>
</body>
</html>
