<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>提交培训日程</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="/js/extjs/examples/Datetime/datetime.css"></link>
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/js/extjs/examples/Datetime/Datetime.js"></script>
		
<script type="text/javascript">
Ext.onReady(function(){
  //生成学科下拉列表
  
		var datefield = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d H:i',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
       			     menu: new DatetimeMenu(),
        			 applyTo: 'trainingBeginTime',
          			blankText:'不能为空，请填写' 
      })  ; 
     var datefield2 = new Ext.form.DateField({   
        			 id: 'diliveryDate',                      
       				  format: 'Y-m-d H:i',                         
        			 maxValue: '2100-01-01',                  
        			 minValue: '1900-01-01',                  
       				// disabledDays: [0, 6],                    
       				// disabledDaysText: '禁止选择该日期',       
       				 fieldLabel: '选择日期*',                  
       				 width:180,                                
       				 showToday:true ,                          
        			 allowBlank:false, 
       			     menu: new DatetimeMenu(),
        			 applyTo: 'trainingEndTime',
          			blankText:'不能为空，请填写' 
      })  ; 
     var provinceDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/entity/first/firstPageCombo_getProvince.action'
		        }),
				reader: new Ext.data.JsonReader({
				            root: 'province'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    provinceDataStore.setDefaultSort('id', 'asc');
		    provinceDataStore.load();
			
			var provinceCombo = new Ext.form.ComboBox({
				store: provinceDataStore,
				displayField:'name',
				valueField: 'id' ,
				//id:'applyno',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'请选择专家所在省份...',
				applyTo: 'combo-box-province',
				editable: false,
				forceSelection: true,
				selectOnFocus:true
			});
		var objtrainplace = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'trainPlace',
   			 allowBlank: true
		});
		var objWorkplace = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'workPlace',
   			 allowBlank: true
		});
		var objprelectWay = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'prelectWay',
   			 allowBlank: true
		});
		var objexpertName = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'expertName',
   			 allowBlank: true,
   			 listeners: {   
                         'blur': function(f){ 
                         //unitCombo.reset();
				Ext.Ajax.request({
					url:'/entity/first/firstPageCombo_getExpertByName.action',
					params: {
                       requestExpertName:objexpertName.getValue()
                    },
					 success: function(response, options) {
                      var responseArray = Ext.util.JSON.decode(response.responseText);
                      objzhicheng.setValue(responseArray.zhicheng);
                     // alert(responseArray.privince);
                      objWorkplace.setValue(responseArray.workPlace);
                      provinceCombo.setValue(responseArray.privince);
					}
			   		
			  	});
				//edutypeDataStore.load();
                         	  
                            }   
                         }
   			 
		});
		
		var objzhicheng = new Ext.form.TextField({
   				// width: 300,
  				  name: 'TextBox',
  				applyTo: 'zhicheng'
		});
	var objtheme = new Ext.form.TextField({
   				// width: 300,
  				  name: 'TextBox',
  				applyTo: 'theme'
		});

	var objcomments = new Ext.form.TextArea({
   				// width: 300,
  				  name: 'TextBox',
  				applyTo: 'comments'
   			 
		});

});
function limitLength(obj, length) {    
    var desc = obj.value;     
    obj.value = substr(obj.value,length);    
}    
function substr(str,length){    
    var l=0,i=0;    
    while(l <length && i<str.length){    
        l+=1;    
        if(str.substring(i,i+1).match(/[\u4e00-\u9fa5]/))l+=1;//一个中文是相当于3个英文    
        i+=1;    
    }    
    return str.substring(0,i);    
} 
function strToDate(str)
{
 	var   strArray=str.split(" ");   
	var   strDate=strArray[0].split("-");   
	var   strTime=strArray[1].split(":");   
	var   a = new   Date(strDate[0],(strDate[1]-parseInt(1)),strDate[2],strTime[0],strTime[1]);
	return a; 
} 

function checkContent(){
 	var flag=true;
 	
 	if(document.getElementById('trainingBeginTime').value==''){
		alert("开始时间不能为空！");
		document.getElementById('trainingBeginTime').focus(); 
		return false;
	}
	if(document.getElementById('trainingEndTime').value==''){
		alert("结束时间不能为空！");
		document.getElementById('trainingEndTime').focus(); 
		return false;
	}
	var check_out= document.getElementById('trainingBeginTime').value; 
 	var check_in= document.getElementById('trainingEndTime').value; 
	var days_diff=(strToDate(check_in).valueOf()-strToDate(check_out).valueOf())/86400000;
	if(days_diff<0){
 		 alert("结束时间不能早于开始时间"); 
 		 return false;
 	 } 
	
	
	if(document.getElementById('theme').value==''){
		alert("专题不能为空！");
		document.getElementById('theme').focus(); 
		return false;
	}
	
	if(document.getElementById('trainPlace').value==''){
		alert("培训地点不能为空！");
		document.getElementById('trainPlace').focus(); 
		return false;
	}
	if(document.getElementById('prelectWay').value==''){
		alert("授课方式不能为空！");
		document.getElementById('prelectWay').focus(); 
		return false;
	}
	if(document.getElementById('expertName').value==''){
		alert("专家姓名不能为空！");
		document.getElementById('expertName').focus(); 
		return false;
	}
	if(document.getElementById('zhicheng').value==''){
		alert("职称不能为空！");
		document.getElementById('zhicheng').focus(); 
		return false;
	}
	if(document.getElementById('workPlace').value==''){
		alert("工作单位不能为空！");
		document.getElementById('workPlace').focus(); 
		return false;
	}
	if(document.getElementById('combo-box-province').value==''||document.getElementById('combo-box-province').value=='请选择专家所在省份...'){
		alert("所在省份不能为空！");
		document.getElementById('combo-box-province').focus(); 
		return false;
	}
	
 	return flag;
 }
</script>
  </head>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">

<div id="main_content">
    <div class="content_title">提交培训日程</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/implementation/coursePlanView_addCoursePlan.action" method="post" onsubmit="return checkContent();" >
<table width="850" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" ><!--<div class="" align="center"><s:property value="getMsg()" escape="false"/></div>--></td>
                          </tr>
						 
                                  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">开&nbsp;始&nbsp;时&nbsp;间*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="trainingBeginTime" name="trainingBeginTime"  value="<s:property value="trainingBeginTime"escape="false" />" >  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                                 <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">结&nbsp;束&nbsp;时&nbsp;间*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="trainingEndTime" name="trainingEndTime"  value="<s:property value="trainingEndTime"escape="false" />" >  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="theme" name="peCoursePlan.theme" size="52" value="<s:property value="peCoursePlan.theme"escape="false" />" maxlength="50"/>  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="themeInfo">&nbsp;</td>
                                </tr>
                                
                     			<tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">培&nbsp;训&nbsp;地&nbsp;点*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="trainPlace" name="peCoursePlan.trainPlace" size="52" maxlength="25" value="<s:property value="peCoursePlan.trainPlace"escape="false" />" />  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="trainPlaceInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">培&nbsp;训&nbsp;方&nbsp;式*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="prelectWay" name="peCoursePlan.prelectWay" size="52" maxlength="50" value="<s:property value="peCoursePlan.prelectWay"escape="false" />" />  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">专&nbsp;家&nbsp;姓&nbsp;名*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="expertName" name="peCoursePlan.expertName" size="52" maxlength="25" value="<s:property value="peCoursePlan.expertName"escape="false" />" />  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="zhicheng" name="peCoursePlan.zhicheng" size="52" maxlength="25" value="<s:property value="peCoursePlan.zhicheng"escape="false" />" />  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">工&nbsp;作&nbsp;单&nbsp;位*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="workPlace" name="peCoursePlan.workPlace" size="52" maxlength="25" value="<s:property value="peCoursePlan.workPlace"escape="false" />"/>  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="workPlaceInfo">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份*：</span></td>
                                  <td width="240" class="postFormBox" style="padding-left:10px"><input type="text" id="combo-box-province" name="peCoursePlan.peProvince.name" size="52" value="<s:property value="peCoursePlan.peProvince.name"escape="false" />" />  </td>
                                  <td width="140" class="postFormBox" style="padding-left:10px" id="noticeNameInfo">&nbsp;</td>
                                </tr>

                     			  <tr valign="middle">
                       			 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
                       				<td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea" id="comments" name="peCoursePlan.comments" cols="70" rows="4" onkeyup=limitLength(this,190) ><s:property value="peCoursePlan.comments"/></textarea>  </td>
                       			 	<td width="140" class="postFormBox" style="padding-left:10px" id="noticeContentInfo">&nbsp;
                       			 	<input type="hidden" name="applyIds" value="<s:property value="loadPeProApply().id" escape="false" />">
                       			 	<input type="hidden" name="peCoursePlan.id" value="<s:property value="peCoursePlan.id" escape="false" />">
                       			 	</td>
                     			 </tr>
                     			 
                           <tr>
                            <td height="50" align="center" colspan="3">
                              <input type="submit" value="提交"/></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript"> 
	
	<s:if test="msg!=null">
		alert('<s:property value="msg"/>');
	</s:if>
</script>
</body>
</html>
