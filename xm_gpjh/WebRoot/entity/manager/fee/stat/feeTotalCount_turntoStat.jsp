<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>交费人数统计</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
		<script>
function isDate(str){ 


var reg =  /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
if (reg.test(str)) return true;
return false;
}
		
		function check(){
		var start =document.getElementById('start').value;
 			 if(start!=null && start!=''){
 			 if(!isDate(start)){
  			alert('请输入正确的日期格式！  例如：2000-01-01！');
  			return false;
  			}
			  }
		var end =document.getElementById('end').value;
 			 if(end.value!=null && end.value !=''){
 			 if(!isDate(start)){
  			alert('请输入正确的日期格式！  例如：2000-01-01！');
  			return false;
  			}
			  }
			  if(start!=null && start!=''&&end.value!=null && end.value !=''){
				var vstart = start.replace('-','').replace('-','');
				var vend = end.replace('-','').replace('-','');
				if(vstart>vend){
					alert('开始时间不能晚于结束时间');
					return false;
				}
			}
			  return true;
		}
		
		</script>		
		<script>
		Ext.onReady(function(){
			
			//交费类型
	var feeTypeDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select id,name from enum_const where namespace='FlagFeeType' and code in ('0','1')"
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    feeTypeDataStore.setDefaultSort('id', 'asc');
		    feeTypeDataStore.load();
			
			var feeTypeCombo =new Ext.form.ComboBox({
				id:'feeType',
				name:'bean.enumConstByFlagFeeType.id',
				store: feeTypeDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'所有交费类型',
				applyTo: 'combo-box-feeType',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});									
			       var startDate = new Ext.form.DateField({
                                fieldLabel: '开始日期',           
                                name: 'startDate',
                                id: 'start',
                                format:'Y-m-d',
                                allowBlank:true,
                              //  validateOnBlur:true,
                                readOnly:true,
                                anchor: '29%'   
                            }); 
                        startDate.render('startDate');
                        
                        var endDate = new Ext.form.DateField({
                                fieldLabel: '截止日期',           
                                name: 'endDate',
                                 id: 'end',
                                format:'Y-m-d',
                                allowBlank:true,
                              //  validateOnBlur:false,
                                readOnly:true,
                                anchor: '29%'   
                            }); 
                            
                        endDate.render('endDate');
	});
	
		</script>		
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/fee/feeTotalCount.action" onsubmit ="return check();">
			<div id="main_content">
			   <div class="content_title">交费人数统计</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table >
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox">交费人数统计---选择条件</div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td align="right">
			   				<div  class="postFormBox"><span class="name"><label>请选择时间段开始时间</label></span>&nbsp;&nbsp;</div>
			   			</td>
			   			<td  align="left" >
			   				<div  class="postFormBox" id = "startDate"></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td align="right">
			   				<div class="postFormBox"><span class="name"><label>请选择时间段结束时间</label></span>&nbsp;&nbsp;</div>
			   			</td>
			   			<td align="left" >
			   				<div  class="postFormBox" id = "endDate"></div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td align="right">
			   				<div class="postFormBox"><span class="name"><label>请&nbsp;&nbsp;选&nbsp;&nbsp;择&nbsp;&nbsp;交&nbsp;&nbsp;费&nbsp;&nbsp;类&nbsp;&nbsp;型</label></span>&nbsp;&nbsp;</div>
			   			</td>
			   			<td  align="left"  class="postFormBox" >
			   				<input type="text" name="feeType"  id="combo-box-feeType" />
			   			</td>
			   		</tr>
                                <tr valign="middle" align="right"> 
                                  <td width="200" height="90" class="postFormBox"><span class="name">请选择您要统计的项目</span>&nbsp;&nbsp;</td>
                                  <td align="left"  class="postFormBox"  >
									<input type="checkbox" name="checkBox" value="site" />&nbsp;&nbsp;学习中心<br/>
                                  	<input type="checkbox" name="checkBox" value="edutype" />&nbsp;&nbsp;层&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次<br/>
                                  	<input type="checkbox" name="checkBox" value="major" />&nbsp;&nbsp;专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业<br/>
                                  	<input type="checkbox" name="checkBox" value="grade" />&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级<br/>
                                  </td>
                          	    </tr>			   		
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" value="提交"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>