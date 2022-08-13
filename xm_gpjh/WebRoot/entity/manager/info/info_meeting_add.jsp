<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加会议通知</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="entity/manager/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
	<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="/js/extjs/examples/Datetime/datetime.css"></link>
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/js/extjs/examples/Datetime/Datetime.js"></script>

<script language="javascript" src="/entity/manager/js/check.js"></script>		
<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>

<script language="javascript" src="/entity/manager/info/js/information.js"></script>
	<script>
	Ext.onReady(function(){
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
        			 applyTo: 'meeting_date',
          			blankText:'不能为空，请填写' 
      })  ; 
      var objname = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'peMeeting.name',
   			 allowBlank: true
		});
		var objplace = new Ext.form.TextField({
   			//	 width: 300,
  				  name: 'TextBox',
  				applyTo: 'peMeeting.place',
   			 allowBlank: true
		});
  });
		function pageGuarding(){
			var content;
			if(FCKeditor_IsCompatibleBrowser()){
				var oEditor = FCKeditorAPI.GetInstance("peMeeting.note"); 
				content=oEditor.GetXHTML(); 
			}else{
				alert("表单正在下载");
				return false;
			}
			var name = document.getElementById("peMeeting.name");
			if(name.value.replace(/(^\s*)|(\s*$)/g, "") == "") {
				alert("会议名称不能为空!");
				name.focus();
				return false;
			}
			var meeting_date = document.getElementById("meeting_date");
			if(meeting_date.value == "") {
				alert("会议时间不能为空!");
				meeting_date.focus();
				return false;
			}
			var place = document.getElementById("peMeeting.place");
		    if(place.value.length == "")
			{
				alert("会议地点不能为空!");
				place.focus();
				return false;
			}
		var cb_len =document.getElementsByName("person");
		var n=0;
		for(k=cb_len.length-2;k<cb_len.length;k++){ //只判断项目执行办和师范司项目办
		//alert(cb_len[k]);
			if(cb_len[k].checked){
			 n++;
			}
		}
		var m = theCheckedBoxes();
		if(n==0&&m==0){
			alert("请指定回执填报单位！");
			return false;
		}	
		var cb2_len =document.getElementsByName("person2");
		var n2=0;
		for(j=cb2_len.length-2;j<cb2_len.length;j++){
			if(cb2_len[j].checked){
			 n2++;
			}
		}
		var m2 = theCheckedBoxes2();
		if(n2==0&&m2==0){
			alert("请指定资源上传单位！");
			return false;
		}	
		if(content.length <1){
			alert("会议内容为空!");
			return false;
		}
		
		var s=document.getElementById('information');
		s.submit();
	}
	
function onload_edit(){
		var str = "<s:property value='peMeeting.receiprUnit'/>";
		onloadObject(str);
		var str2 = "<s:property value='peMeeting.resourceUnit'/>";
		onloadObject2(str2);
		var bean_id = "<s:property value='peMeeting.id'/>";
		if(bean_id==null||bean_id.length<=0){
			document.getElementById('zlb_title_start').innerHTML='添加会议通知';
			//window.document.info_news_add.action='/entity/information/peBulletin_addNotice.action';
		}else{
			document.getElementById('zlb_title_start').innerHTML='修改会议通知';
			//window.document.info_news_add.action='/entity/information/peBulletin_editexe.action';
			window.document.getElementById('hiddenBean').innerHTML="<input type='hidden' name='bean.id' value='"+bean_id+"' />";
			window.document.getElementById('back').innerHTML="<a href='#' title='返回' class='button'><span unselectable='on' class='norm'  style='width:46px;height:15px;padding-top:3px' onclick=window.navigate('/entity/information/peMeeting.action') onMouseOver=\"className='over'\" onMouseOut=\"className='norm'\" onMouseDown=\"className='push'\" onMouseUp=\"className='over'\"><span class='text'>返回</span></span></a>";
		}
	}
</script>		
</head>
<body leftmargin="0" topmargin="0" class="scllbar" onLoad="onload_edit();" >
  <form name='information'action="/entity/information/peMeeting_toAddMeeting.action" method='post' class='nomargin'>
<table width="86%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">添加会议通知</div></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
          <table width="85%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="140" valign="bottom" nowrap><span class="name">会议名称*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            <td colspan="5"> <input name="peMeeting.name"   value="<s:property value='peMeeting.name'/>" type="text" class="selfScale" id="bean.name" maxlength="50" size="50">            </td>        </tr>
            <tr valign="bottom" class="postFormBox" >
			  <td width="140" valign="bottom" nowrap><span class="name">会议时间*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
			<td colspan="5">
			  <input  name="meeting_date"  id="meeting_date" type="text" value="<s:property value='meeting_date'/>" />			  </td>
			</tr>
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="140" valign="bottom" nowrap><span class="name">举办地点*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            <td colspan="5"><input name="peMeeting.place"   value="<s:property value='peMeeting.place'/>" type="text" class="selfScale" id="bean.place" maxlength="50"  size="50">            </tr>
           <tr valign="bottom" class="postFormBox"> 
              <td width="140" rowspan="2" valign="middle"><span class="name">指定回执填报单位:*&nbsp;</span></td> 
             <td width="18%" valign="top">
			  示范性承办单位
			    <input type="checkbox" value="cbunit_" id="CBS" name="person" onClick="clickCB()">
		       <input id="CB_button" type="button" name="cb" onClick="showDiv1('cnt1')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none;z-index:2000 ">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb_id_all" type="checkbox" name="cb_all" value="" onclick=listSelectCB();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb_">
                	<tr>
                    <td height="22"><input id="cb_id_check" type="checkbox" name="cb_id" value="<s:property value="#cb_[0]"/>"><s:property value="#cb_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('cnt1')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	  		  </td>
              <td width="18%" valign="top">
			  中西部承办单位
              <input type="checkbox" value="zxbunit_" id="ZXB" name="person" onClick="clickZXB()">
			   <input id="zxb_button" type="button" name="zxb" onClick="showDiv1('zxbDiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="zxbDiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none;z-index:2000">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxb_id_all" type="checkbox" name="zxb_all" value="" onclick=listSelectZXB();>全选</td>
              </tr>
              	<s:iterator value="zxbScop"  id="zxb_">
                	<tr>
                    <td height="22"><input id="zxb_id_check" type="checkbox" name="zxb_id" value="<s:property value="#zxb_[0]"/>"><s:property value="#zxb_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('zxbDiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>			  </td>
              <td width="16%" valign="top">中西部师范处
                <input type="checkbox" value="zxbst_" id="ZXSTS" name="person" onClick="clickST()">
			   <input id="st_button" type="button" name="st" onClick="showDiv1('cnt2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none;z-index:2000">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st_id_all" type="checkbox" name="st_all" value="" onclick=listSelectST();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="st_">
                	<tr>
                    <td height="22"><input id="st_id_check" type="checkbox" name="st_id" value="<s:property value="#st_[0]"/>"><s:property value="#st_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('cnt2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div> </td>
              <td width="15%" valign="top">其他师范处
                <input type="checkbox" value="qtst_" id="QTSTS" name="person" onClick="clickST2()">
			   <input id="st2_button" type="button" name="st2" onClick="showDiv1('cnt3')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cnt3" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none;z-index:2000">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="st2_id_all" type="checkbox" name="st2_all" value="" onclick=listSelectST2();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst_">
                	<tr>
                    <td height="22"><input id="st2_id_check" type="checkbox" name="st2_id" value="<s:property value="#qtst_[0]"/>"><s:property value="#qtst_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('cnt3')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>				  </td>
              <td width="33%" valign="top">
			  其他
                <input type="checkbox" value="other_" id="OTHER" name="person" onClick="clickOther()">
			   <input id="other_button" type="button" name="other" onClick="showDiv1('otherDiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="otherDiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none;z-index:2000">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="other_id_all" type="checkbox" name="other_all" value="" onclick=listSelectOther();>全选</td>
              </tr>
              	<s:iterator value="otherScop"  id="other_">
                	<tr>
                    <td height="22"><input id="other_id_check" type="checkbox" name="other_id" value="<s:property value="#other_[0]"/>"><s:property value="#other_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('otherDiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
			<tr valign="bottom" class="postFormBox">
			  <!--td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评审专家<input type="checkbox" value="valueExpert"  id="PSS" name="person"></td-->
		      <td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目执行办<input type="checkbox" value="xmzxb_" id="XMS" name="person" ></td>
			  <td valign="top">师范司项目办 <input type="checkbox" value="sfsxmb_" id="SFS" name="person" ></td>
			  <td valign="top">&nbsp;
			  子项目办
                <input type="checkbox" value="jspx_" id="JSPX" name="person" onClick="clickJSPX()">
			   <input id="jiaoshi_button" type="button" name="jiaoshi" onClick="showDiv1('jsdiv')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="jsdiv" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="jiaoshi_id_all" type="checkbox" name="jiaoshi_all" value="" onclick=listSelectJSPX();>全选</td>
              </tr>
              	<s:iterator value="jiaoshiScop"  id="jiaospx_">
                	<tr>
                    <td height="22"><input id="jiaoshi_id_check" type="checkbox" name="jiaoshi_id" value="<s:property value="#jiaospx_[0]"/>"><s:property value="#jiaospx_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('jsdiv')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	
			  </td>
			  <td valign="top">&nbsp;</td>
				<td valign="top">&nbsp;</td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="140" rowspan="2" valign="middle"><span class="name">指定上传资源单位:*&nbsp;</span></td> 
             <td width="20%" valign="top">
			  示范性承办单位
			    <input type="checkbox" value="cbunit_" id="CBS2" name="person2" onClick="clickCB2()">
		       <input id="CB2_button" type="button" name="cb2" onClick="showDiv1('cbDiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="cbDiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="cb2_id_all" type="checkbox" name="cb2_all" value="" onclick=listSelectCB2();>全选</td>
              </tr>
              	<s:iterator value="cbScop"  id="cb2_">
                	<tr>
                    <td height="22"><input id="cb2_id_check" type="checkbox" name="cb2_id" value="<s:property value="#cb2_[0]"/>"><s:property value="#cb2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('cbDiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	  		  </td>
              <td width="20%" valign="top">
			  中西部承办单位
              <input type="checkbox" value="zxbunit_" id="ZXB2" name="person2" onClick="clickZXB2()">
			   <input id="zxb2_button" type="button" name="zxb2" onClick="showDiv1('zxbDiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="zxbDiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxb2_id_all" type="checkbox" name="zxb2_all" value="" onclick=listSelectZXB2();>全选</td>
              </tr>
              	<s:iterator value="zxbScop"  id="zxb2_">
                	<tr>
                    <td height="22"><input id="zxb2_id_check" type="checkbox" name="zxb2_id" value="<s:property value="#zxb2_[0]"/>"><s:property value="#zxb2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('zxbDiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>			  </td>
              <td width="20%" valign="top">中西部师范处
                <input type="checkbox" value="zxbst_" id="ZXSTS2" name="person2" onClick="clickZXST2()">
			   <input id="zxst2_button" type="button" name="zxst2" onClick="showDiv1('zxstDiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="zxstDiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="zxst2_id_all" type="checkbox" name="zxst2_all" value="" onclick=listSelectZXST2();>全选</td>
              </tr>
              	<s:iterator value="stScop"  id="zxst2_">
                	<tr>
                    <td height="22"><input id="zxst2_id_check" type="checkbox" name="zxst2_id" value="<s:property value="#zxst2_[0]"/>"><s:property value="#zxst2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('zxstDiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div> </td>
              <td width="20%" valign="top">其他师范处
                <input type="checkbox" value="qtst_" id="QTSTS2" name="person2" onClick="clickQTST2()">
			   <input id="qtst2_button" type="button" name="qtst2" onClick="showDiv1('qtstDiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="qtstDiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="qtst2_id_all" type="checkbox" name="qtst2_all" value="" onclick=listSelectQTST2();>全选</td>
              </tr>
              	<s:iterator value="qtstScop"  id="qtst2_">
                	<tr>
                    <td height="22"><input id="qtst2_id_check" type="checkbox" name="qtst2_id" value="<s:property value="#qtst2_[0]"/>"><s:property value="#qtst2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('qtstDiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>				  </td>
              <td width="20%" valign="top">
			  其他
                <input type="checkbox" value="other_" id="OTHER2" name="person2" onClick="clickOther2()">
			   <input id="other2_button" type="button" name="other2" onClick="showDiv1('otherDiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="otherDiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="other2_id_all" type="checkbox" name="other2_all" value="" onclick=listSelectOther2();>全选</td>
              </tr>
              	<s:iterator value="otherScop"  id="other2_">
                	<tr>
                    <td height="22"><input id="other2_id_check" type="checkbox" name="other2_id" value="<s:property value="#other2_[0]"/>"><s:property value="#other2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv1('otherDiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>
			  </td>
            </tr>
			<tr valign="bottom" class="postFormBox">
			  <!-- td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;评审专家<input type="checkbox" value="valueExpert"  id="PSS" name="person2"></td> -->
		      <td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目执行办<input type="checkbox" value="xmzxb_" id="XMS" name="person2" ></td>
			  <td valign="top">师范司项目办 <input type="checkbox" value="sfsxmb_" id="SFS" name="person2" ></td>
			  <td valign="top">
			   子项目办
                <input type="checkbox" value="jspx_" id="JSPX2" name="person2" onClick="clickJSPX2()">
			   <input id="jiaoshi2_button" type="button" name="jiaoshi2" onClick="showDiv1('jsdiv2')" value="请选择" style="display:none;position:relative;font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
              <div id="jsdiv2" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
              
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="22"><input id="jiaoshi2_id_all" type="checkbox" name="jiaoshi2_all" value="" onclick=listSelectJSPX2();>全选</td>
              </tr>
              	<s:iterator value="jiaoshiScop"  id="jiaospx2_">
                	<tr>
                    <td height="22"><input id="jiaoshi2_id_check" type="checkbox" name="jiaoshi2_id" value="<s:property value="#jiaospx2_[0]"/>"><s:property value="#jiaospx2_[1]"/></td>
                    </tr>
                </s:iterator>
                <tr><td><input type='button' onClick="showDiv('jsdiv2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;"></td></tr>
                </table>
              </div>	
			  </td>
			  <td valign="top">&nbsp;</td>
				<td valign="top">&nbsp;</td>
            </tr>
             <tr valign="bottom" class="postFormBox"> 
              <td width="140" valign="top"><span class="name">会议内容&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
              <td colspan="5"> 
              <textarea class="smallarea"  name="peMeeting.note" cols="70" rows="12" id="peMeeting.note"><s:property value='peMeeting.note'/></textarea>			  </td>
            </tr>
   <tr valign="bottom" class="postFormBox"> 
	<td  valign="bottom" colspan="6">
		<span class="name">是否给联系人发邮件：</span>
	&nbsp;&nbsp;&nbsp;&nbsp;
		<select name="send" id="send" class="input6303">
			<option value="0" selected="selected">
				否			</option>
			<option value="1">
				是			</option>
		</select> 
&nbsp;&nbsp;&nbsp;	</td>
</tr>
          </table> 
      </div>
   </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
       <td  align="center" valign="middle">
       	<a href="#" title="提交" class="button">
       		<span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" " onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'" onClick="javascript:pageGuarding();">
       			<span class="text">提交</span>       			
       		</span>
       	</a>
       	</td>
       <td  align="center" id="back" valign="middle">
       	</td>            
       	</tr>
      </table></td>
  </tr>
</table>
<div id="hiddenBean"></div>
</form>

<script type="text/javascript"> 


var oFCKeditor = new FCKeditor( 'peMeeting.note' ) ; 
oFCKeditor.Height = 300 ; 
oFCKeditor.Width  = 700 ; 

oFCKeditor.Config['ImageBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
oFCKeditor.Config['ImageUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';

oFCKeditor.Config['LinkBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
oFCKeditor.Config['LinkUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';

oFCKeditor.Config['FlashBrowserURL']=oFCKeditor.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
oFCKeditor.Config['FlashUploadURL']=oFCKeditor.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';


oFCKeditor.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
oFCKeditor.ReplaceTextarea() ; 
//--> 
</script> 
<script>bLoaded=true;</script>

</body>

</html>



