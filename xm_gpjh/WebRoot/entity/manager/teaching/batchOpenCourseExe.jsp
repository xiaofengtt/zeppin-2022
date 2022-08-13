<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>批量开课</title>
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
		        emptyText:'所有学习中心',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    site.render('showsite');		
			//层次下拉列表
			var edutype = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeEdutype'
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
		        fieldLabel: '<@s.text name="层次"/>',
		         name:'edutypename',
		         id:'edutype1',
		        triggerAction: 'all',
		        emptyText:'所有层次',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    edutype.render('showedutype');
		    
		    //专业
				var major = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeMajor'
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
		        fieldLabel: '专业',
		         name:'majorname',
		         id:'major1',
		        triggerAction: 'all',
		        emptyText:'所有专业',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    major.render('showmajor');
	    
		    //年级
				var grade = new Ext.form.ComboBox({
		        store: new Ext.data.Store({
		        // load using script tags for cross domain, if the data in on
				// the same domain as
		        // this page, an HttpProxy would be better
		        proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeGrade'
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
		        fieldLabel: '年级',
		         name:'gradename',
		         id:'grade1',
		        triggerAction: 'all',
		        emptyText:'所有年级',
		        editable: false,
		        selectOnFocus:true
		    });
		    
		    grade.render('showgrade'); 
			
		});		
</script>		
	</head>
	<body>
			<div id="main_content">
			   <div class="content_title">批量开课</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			      	  <form action="/entity/teaching/batchConfirmCourse_openCourseExe.action">
			<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="26" align="center" valign="middle" ></td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
                                <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">&nbsp;&nbsp;学习中心：&nbsp;&nbsp;</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showsite"></div>
                                  </td>
                          	    </tr>
						        <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择层次：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showedutype"></div>
                                  </td>
                          	    </tr>
						        <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择专业：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showmajor"></div>
                                  </td>
                          	    </tr>   
						        <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择年级：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  
                                  <div align="left"  class="postFormBox" id = "showgrade"></div>
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