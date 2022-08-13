<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学生退学设置</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script>
		Ext.onReady(function(){
					
			var inputDate = new Ext.form.DateField({
			        fieldLabel: '变动日期',           
			        name: 'bean.opDate',
			        id:'inputDate',
			        format:'Y-m-d',
			        allowBlank:false, 
			        readOnly:true,
			        anchor: '29%'
			    }); 
			    
			inputDate.on('render',function showvalue(p,_record,_options){
					inputDate.setValue((new Date).format('Y-m-d'));
					inputDate.setRawValue((new Date).format('Y-m-d'));
			},inputDate);
			
			inputDate.render('showinputDate'); 
			
		});
	
		</script>
		<script>
		function checkFeeBalance(){
			<s:if test="bean.peStudent.feeBalance<0">
				if(window.confirm("学生<s:property value='bean.peStudent.trueName'/>欠费，是否继续让其退学？"))
					return true;
				else
					return false;
			</s:if>			
		}
		</script>
	</head>
	<body>
		<form name="quitSchool" method="post">
			<div id="main_content">
			   <div class="content_title">学生退学设置</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="8">
			   				<div align="center" class="postFormBox">学生信息
			   				<input type="hidden" name="bean.enumConstByFlagAbortschoolType.code" value="<s:property value='bean.enumConstByFlagAbortschoolType.code'/>"/>
			   				<input type="hidden" name="bean.id" value="<s:property value='bean.id'/>"/></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="8">
			   				<div align="center" class="postFormBox"><font color=red><s:property value='msg'/></font></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td width="15%">
			   				<div align="center" class="form_table" >学号</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >姓名</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >年级</div>
			   			</td>
			   			<td width="20%">
			   				<div align="center" class="form_table" >专业</div>
			   			</td>
			   			<td width="10%">
			   				<div align="center" class="form_table" >层次</div>
			   			</td>
			   			<td width="20%">
			   				<div align="center" class="form_table" >学习中心</div>
			   			</td>
			   			<td width="20%">
			   				<div align="center" class="form_table" >帐户余额</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.regNo"/></div>
			   				<input type="hidden" name="bean.peStudent.id" value="<s:property value='bean.peStudent.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.regNo" value="<s:property value='bean.peStudent.regNo'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.trueName"/></div>
			   				<input type="hidden" name="bean.peStudent.trueName" value="<s:property value='bean.peStudent.trueName'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peGrade.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peGrade.id" value="<s:property value='bean.peStudent.peGrade.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peGrade.name" value="<s:property value='bean.peStudent.peGrade.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peMajor.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peMajor.id" value="<s:property value='bean.peStudent.peMajor.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peMajor.name" value="<s:property value='bean.peStudent.peMajor.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peEdutype.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peEdutype.id" value="<s:property value='bean.peStudent.peEdutype.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peEdutype.name" value="<s:property value='bean.peStudent.peEdutype.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.peSite.name"/></div>
			   				<input type="hidden" name="bean.peStudent.peSite.id" value="<s:property value='bean.peStudent.peSite.id'/>"/>
			   				<input type="hidden" name="bean.peStudent.peSite.name" value="<s:property value='bean.peStudent.peSite.name'/>"/>
			   			</td>
			   			<td>
			   				<div align="center" class="postFormBox" ><s:property value="bean.peStudent.feeBalance"/></div>
			   				<input type="hidden" name="bean.peStudent.feeBalance" value="<s:property value='bean.peStudent.feeBalance'/>"/>
			   			</td>
			   		</tr>
			   		<tr valign="middle" class="postFormBox"> 
		              <td valign="middle" align="right"  colspan="3" ><span class="name"><label>退学日期*</label></span></td>
		              <td valign="middle" colspan="4"><div align="left" class="postFormBox" id = "showinputDate"></div></td>
		            </tr>
			   		<tr class="postFormBox">
			   			<td colspan="3">
			   				<div align="right" ><span class="name"><label>退学的原因</label></span></div>
			   			</td>
			   			<td colspan="5">
			   				<div align="left" ><textarea name="bean.note" cols="25" rows="4"><s:property value='bean.note'/></textarea></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="8">
			   				<div align="center" class="postFormBox">
			   					<s:if test='msg.indexOf("成功")>=0&&msg.indexOf("取消")<0'><input type="submit" onclick="javascript:document.quitSchool.action='/entity/studentStatus/peStudentStatus_executeCancel.action'" name="Submit" value="取消退学"/>
			   					<input type="button" onclick="window.navigate('/entity/studentStatus/peStudentStatus_turntoDropoutSearch.action')" value="返回"/>
			   					</s:if>
			   					<s:else><input type="submit" onclick="javascript:document.quitSchool.action='/entity/studentStatus/peStudentStatus_executeDropoutChange.action';return checkFeeBalance();" name="Submit" value="提交"/></s:else>
			   				</div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>