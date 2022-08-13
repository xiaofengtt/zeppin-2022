<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
	<head>
		<title>打印考场签到表</title>
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
<script>
		Ext.onReady(function(){
			//学习中心下拉列表
			var site = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeSite'
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '<@s.text name="学习中心"/>',
		         name:'sitename',
		         id:'site',
		        triggerAction: 'all',
		        emptyText:'请选择学习中心',
		        editable: true,
		        selectOnFocus:true
		    });
		    
		    site.render('showsite');		
			//场次下拉列表
			var examNo = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select t.id, t.name from pe_exam_no t, pe_semester semester where t.fk_semester_id = semester.id  and semester.flag_active = '1'"
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '<@s.text name="考试场次"/>',
		         name:'examnoname',
		         id:'examno',
		        triggerAction: 'all',
		        emptyText:'所有场次',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    examNo.render('showExamNo');

			//考场下拉列表
			var examRoom = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: "/test/myList.action?sql=select room.id,room.name from pe_exam_room room,pe_exam_no t, pe_semester semester "
		            + " where t.fk_semester_id = semester.id   and semester.flag_active = '1'  and room.fk_exam_no=t.id"
		        }),
		
		        // create reader that reads the Topic records
		        reader: new Ext.data.JsonReader({
		            root: 'models',
		            id: 'id',
		            fields: [
		               	'id','name' ]
		        }),
		
		        // turn on remote sorting
		        remoteSort: true
		    }),
		        valueField: 'id',
		        displayField:'name',
		        typeAhead: true,
		        fieldLabel: '<@s.text name="考场"/>',
		         name:'examroomname',
		         id:'examroom',
		        triggerAction: 'all',
		        emptyText:'所有考场',
		        editable: true,
		        selectOnFocus:true
		    });
		    
		    examRoom.render('showexamRoom');
		});		
</script>
		<script Language="JavaScript">
			function check() {
				if(document.getElementById("site").value=="请选择学习中心"||document.getElementById("site").value=='') {
					alert("请选择一个学习中心");
					return false;					
				}
				return true;
			}
		</script>	
	</head>
	<body>
			<div id="main_content">
			   <div class="content_title">打印考场签到表</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			      	  <form action="/entity/exam/finalExamPrint_printSignIn.action" onsubmit="return check();" target="_blank" >
			<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="26" align="center" valign="middle" ></td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
                                <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">学习中心：<font color="red">*</font></span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showsite"></div>
                                  </td>
                          	    </tr>
						        <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">考试场次：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showExamNo"></div>
                                  </td>
                          	    </tr>
						        <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">考场名称：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showexamRoom"></div>
                                  </td>
                          	    </tr>                          	                               	                           	    
                           <tr valign="middle"> 
                             <td width="200" height="60" align="left" class="postFormBox"></td>
                             <td class="postFormBox" align="left" style="padding-left:18px">
                             	<input  type="submit" value="确定" />
                             </td>
                      	  </tr>
                              
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

		</form>
	</body>
</html>