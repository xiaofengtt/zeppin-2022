<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>统计项</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script>
		Ext.onReady(function(){
		
			//生成学习中心下拉列表
			var siteDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
		            url: '/test/myList.action?bean=PeRecruitplan'
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    siteDataStore.setDefaultSort('id', 'asc');
		    siteDataStore.load();
			
			var siteCombo = new Ext.form.ComboBox({
				store: siteDataStore,
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				name: 'peRecruitplanName',
				id:'peRecruitplanId',
				mode: 'local',
				triggerAction: 'all',
				emptyText:'全部招生考试批次',
				applyTo: 'combo-box-peRecruitplans',
				editable: true,
				forceSelection: true
			});
	});
	
		</script>		
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">入学测试成绩统计</div>
    <div class="cntent_k">
   	  <div class="k_cc">
   	  <form action="/entity/recruit/recruitScoreStat.action">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="26" align="center" valign="middle" ></td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
                  <tr valign="middle">
			   			<td  width="200" height="90" align="left" class="postFormBox">
			   			<span class="name"><label>选择招生考试批次：</label></span>
			   			</td>
			   			<td class="postFormBox" style="padding-left:18px">
			   				
			   					<input type="text" name="peRecruitplanId"  id="combo-box-peRecruitplans" />			
			   			</td>
			   		</tr>
                                <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择您要统计的项目：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  	<input type="checkbox" name="checkBox" value="site" >&nbsp;&nbsp;学习中心<br/>
                                  	<input type="checkbox" name="checkBox" value="edutype" >&nbsp;&nbsp;层次<br/>
                                  	<input type="checkbox" name="checkBox" value="major" >&nbsp;&nbsp;专业<br/>
                                  	<input type="checkbox" name="checkBox" value="piece" >&nbsp;&nbsp;分数段人数<br/>
                                  	<input type="checkbox" name="checkBox" value="ratio" >&nbsp;&nbsp;成绩比率<br/>
                                  </td>
                          	    </tr>
						 
                           <tr valign="middle"> 
                             <td width="200" height="60" align="left" class="postFormBox"></td>
                             <td class="postFormBox" style="padding-left:18px">
                             	<input type="submit" value="确定"><br>
                             </td>
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
</body>
</html>

