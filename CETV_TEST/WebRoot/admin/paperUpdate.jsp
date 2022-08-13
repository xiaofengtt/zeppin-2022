<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<title>试卷</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">	
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/jquery-1.9.1.min.js"></script>

<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/mustache.js"></script>

<script src="../js/jquery.cookie.js"></script>
<script src="../js/app.js"></script>  
<script src="../js/moment.js"></script>
<script src="../js/jquery.serializejson.min.js"></script>
<script src="../js/dialog-min.js"></script> 

<script src="../js/zh-cn.js"></script>
<script src="../js/tinymce/dropzone/lib/dropzone.js"></script>
<script src="../js/tinymce/tinymce.min.js"></script>
<script src="../js/tinymce/tinymce_zhcn.js"></script>
<script src="../js/jquery.cityselect.js"></script>

<script>
$(function(){
	moment.lang('zh-cn');
    function timedUpdate () {
       update();
       setTimeout(timedUpdate, 1000);
   }
   function update(){
          $('#datafomate').html(moment().format('YYYY-MM-DD ,a h:mm:ss'));
      }  
	timedUpdate()
	
})
</script>
 
</head>
<body>

<!--Header-part-->
<div class="navbar" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="logo" title="网络、移动学习测评移动云资源平台" ><img src="../img/logo-jy.png" height="41"></a>
    </div>
	<p class="logout">欢迎<span id="loginname"></span> &nbsp;&nbsp;<span id="datafomate"></span> &nbsp;|&nbsp;  <a href="../admin/passwordEditiframe.html" data-fancybox-type="iframe" class="btn-password cboxElement">修改密码</a> &nbsp;|&nbsp;  <a href="login.html">退出</a> </p>
	
  </div>
</div>
<!--close-Header-part--> 
<div id="container" class="container-fluid">

	<div class="row">
		<div class="sidebar">
			<div class=" nav-sidebar">
				<ul id="leftside" class="nav">
				</ul>
			</div>
			<div class="js-nav-shadow bottom"><img src="../img/round-shadow-bottom.png" style="top: auto; bottom: 0px;"></div>
		</div> <!--end sidebar-->
		<script id="leftTpl" type="text/template">
			{{#Records}}
			<li class="active"><a href="#"><span>{{name}}</span> </a>
				
				<ul class="nav">
					{{#data}}
						<li {{#iscurrent}} class="cur" {{/iscurrent}}>  <a href="{{path}}">{{name}}
						</a></li>
					{{/data}}
			
				</ul>
				
			</li>
			{{/Records}}
		</script>
		<div class="main" style="overflow-y:auto">

			<div class="listwrap" style="margin-right:15px;">
				<div class="paperContainer" data-paperid = "<s:property value='paper.id' />">
					<div class="paper-savebtn btn-group-vertical">
						<button type="button" id="paperSaveBtn" class="btn btn-default  btn-lg">保存</button>
						<button type="button"  id="paperSettingBtn" class="btn btn-default  btn-lg">设置</button>
					</div>
					<div class="unit-box paper-title" id="paper-title" title=""><s:property value='paper.name' /></div>
					<div class="unit-box paper-prititle" id="paper-prititle" title="">
						<span><s:property value='paper.subjectName' /></span> /
						<span><s:property value='paper.gradeName' /></span>  /
						<span><s:property value='paper.sourceType' /></span> /
						<span><s:property value='paper.areaName' /></span> /
						<span ><s:property value='paper.year' />年</span>  
						<span style="color:#aaa" class="fr">满分<s:property value='paper.totalScore' />,时长<s:property value='paper.answerTime' /></span>
					</div>
					<div class="unit-box paper-cover"><s:property value='paper.cover' escape="false" /></div>
					
					<s:iterator value="paper.lstSections" id="section">
						
						<div class="paper-type-box" id="paper-type-box<s:property value='itemTypeId' />" data-setionid="<s:property value='id' />">
							<div class="question-handle-box handle-box">
								<div class="deco-box "><span class="itemtype-num"><s:property value='numString' /></span> 、 <s:property value='itemTypeName' />(<s:property value='name' escape="false"/>) </div>
								<div class="control-box fn-clear">
									<s:if test="%{modeType==4}">
										<a href="../admin/questionAdd4.html?mtype=<s:property value='modeType' />&qtype=<s:property value='itemTypeId' />&paperid=<s:property value='paper.id' />&sectionid=<s:property value='id' />"><span class="add-icon"  data-itemtype="<s:property value='itemTypeId' />">添加</span></a>
									</s:if>
									<s:else>
	                                	<a href="../admin/questionAdd.html?mtype=<s:property value='modeType' />&qtype=<s:property value='itemTypeId' />&paperid=<s:property value='paper.id' />&sectionid=<s:property value='id' />"><span class="add-icon"  data-itemtype="<s:property value='itemTypeId' />">添加</span></a>
									</s:else>
									<!--a><span class="text-icon"  papertype="1">上移</span></a>
									<a><span class="text-icon"  papertype="1">下移</span></a-->
	                            </div>
							</div>
							
							<s:iterator value="#section.lstItem" id="item">
							<div id="q<s:property value='id' />" class="question-item-box">
								<div class="control-box fn-clear">
									<s:if test="%{modeType==4}">
									<a class="btn btn-default btn-sm" href="../admin/questionEdit4.html?id=<s:property value='item' />&mtype=<s:property value='#section.modeType' />&qtype=<s:property value='#section.itemTypeId' />&paperid=<s:property value='paper.id' />&sectionid=<s:property value='#section.id' />"><span class="edit-icon" data-id="<s:property value='id' />">编辑</span></a>	
									</s:if>
									<s:else>
									<a class="btn btn-default btn-sm" href="../admin/questionEdit.html?id=<s:property value='item' />&mtype=<s:property value='#section.modeType' />&qtype=<s:property value='#section.itemTypeId' />&paperid=<s:property value='paper.id' />&sectionid=<s:property value='#section.id' />"><span class="edit-icon" data-id="<s:property value='id' />">编辑</span></a>		
									</s:else>	
									
									<a class="btn btn-default btn-sm" href="javascript:void(0)"><span class="del-icon" data-id="<s:property value='id' />">删除</span></a>				
									<a class="btn btn-default btn-sm" href="javascript:void(0)"><span class="up-icon" data-id="<s:property value='id' />" opt="inner">上移</span></a>
									<a class="btn btn-default btn-sm" href="javascript:void(0)"><span class="down-icon" data-qid="" data-pqid="149313860" opt="inner">下移</span></a> 
								</div>
								<div class="quesiton" data-id="<s:property value='id' />"  data-itemid="<s:property value='item' />">
									<span class="question-index"></span>
									<s:property value='html' escape="false" />
								</div>
							</div>
							</s:iterator>
						
						</div>
						
					</s:iterator>
					
				</div>	
			</div>
		</div>
	</div> <!--end row-->
	<script>
		$(function(){
			paperQuestion.paperInit()
		})
	</script>
	
</div>	<!--end container-->
<div id="tinymceCnt"></div>

<div id="setting-content" class="hidden"> <!--试卷设置-->
	<div class="question_form">
		<form action="#" method="post" class="form-horizontal">
			<div class="row clearfix qustion-title">
				<div class="col-md-12">						
					<div class="form-group">
						
						<label class="col-sm-2 control-label" for="">试卷名称</label>
						<div class="col-sm-10">
							<input type="text" name="name" class="form-control" value="<s:property value='paper.name' />">
						</div>
					</div>
				</div>
				
			</div>
			
			<div class="row clearfix">
				<div class="col-md-6">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">试卷年份</label>
						<div class="col-sm-8">
							<input type="text" name="year" class="form-control" value="<s:property value='paper.year' />" placeholder="年份">
						</div>
						
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">所属学段</label>
						<div class="col-sm-8">
							<div class="dropdown dropbox">
								<a href="#" class="dropdown-toggle"><span id="gradeName"><s:property value='paper.gradeName' /></span> <i class="a-icon a-icon-dropdown"></i></a>
								<div class="gradeFliter dropdown-menu">
									<div id="gradeBox" class="treelist">
										<div id="gradeList" class="list_sub sm_icon">
											<div id="gradeido">
									
											</div>
										</div>
									</div>
								</div>
							</div>

							<input type="hidden" name="grade.id" value="<s:property value='paper.gradeId' />">
						</div>
						
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">考试时长</label>
						<div class="col-sm-8">
							<input type="text" name="answerTime" class="form-control" value="<s:property value='paper.answerTime' />" placeholder="120分钟">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">满分成绩</label>
						<div class="col-sm-8">
							<input type="text" name="totalScore" class="form-control" value="<s:property value='paper.totalScore' />" placeholder="150分">
						</div>
					</div>
					
					
					
				</div>
				
				<div class="col-md-6">
				
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">来源类型</label>
						<div class="col-sm-8">
							<select class="form-control"  name="type">
								<option value="0" <s:if test="%{paper.type==0}">selected</s:if> >真题</option>
								<option value="1" <s:if test="%{paper.type==1}">selected</s:if> >模拟考试题</option>
								<option value="2" <s:if test="%{paper.type==2}">selected</s:if> >为教材配套习题</option>
								<option value="3" <s:if test="%{paper.type==3}">selected</s:if> >个人或机构贡献</option>
								<option value="4" <s:if test="%{paper.type==4}">selected</s:if> >其他</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">所属学科</label>
						<div class="col-sm-8">
							<div id="changeSubject" class="dropdown changeSubject dropbox">
							  <a href="#" class="dropdown-toggle">
								   <span id="subjectName"><s:property value='paper.subjectName' /></span>
								   <i class="a-icon a-icon-dropdown"></i>
							  </a>
							  <div id="changeSubjectCnt" style="width:100%" class="dropdown-menu">

							  </div>
							  <input type="hidden" name="subject.id" value="<s:property value='paper.subjectId' />">
							</div>

							
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">所属地区</label>
						<div id="areaid" class="col-sm-9 form-inline">
					  		<select  name="area.id" class="prov form-control">
					  			
					  		</select> 
						</div>
					</div>
				
				</div>
				
			</div>
			
			<div class="row clearfix">
				<div class="col-md-12">
				
					<div class="form-group">
						<label class="col-sm-2 control-label" for="">填答说明</label>
						<div class="col-md-10">
							<textarea name="cover" class="form-control" rows="3"><s:property value='paper.cover' escape="false" /></textarea>
						</div>
					</div>
				
				</div>
				
			</div>
			
			<div id="paper-set-win" class="paper-set-win">
				<s:iterator value="paper.lstSections" id="section">
					<div class="paper-set-box">
						<table id="win-paper-title1" class="table" data-object={} data-inx="<s:property value='inx' />" data-itemtypeid="<s:property value='itemTypeId' />" data-itemtypename="<s:property value='itemTypeName' />"  data-pid="<s:property value='id' />">
						     <tbody>
						            <tr><td class="title-td"><span class="title-td-num"><s:property value='numString' /></span>、<s:property value='itemTypeName' /></td>
						            <td class="option-td">
						                <ul>
						                    <li class="fn-clear">
												<label>说明：</label>
												<span><textarea class="form-control" name="itemtypedesc" type="text"><s:property value='name'  escape="false" /></textarea></span>
											</li>
						                </ul>
						            </td>
						        </tr>
						    </tbody>
						</table>
					</div>
				</s:iterator>
				
			</div>						
		</form>
		
	</div>
	
</div>

<div class="bs-footer">
  <div>
	  <p>Copyright © 2014 中国教育电视台 京ICP证101020号.     <a target="_blank" href="http://www.guoshi.com/">联系我们</a> <a style="display:none" href="http://www.zeppin.cn" target="_blank">http://www.zeppin.cn</a> </p>
  </div>
</div>

<script>
	function getarea() {
		$.get('../areaSearch',function(r){
			if(r.Records.length) {
				var str = '<option value="">全部</option>';
				var selectAreaId = "<s:property value='paper.areaId' />";
				$.each(r.Records,function(i,v){
					var select = (v.id == selectAreaId) ? "selected" : "";
					str += '<option value="'+ v.id +'"'+select+'>'+ v.name +'</option>';
				})
				$('select[name="area.id"]').html(str);
			}
		})
	}
	$(function(){
		getarea();
	})
</script>
<script src="../js/editor.js"></script>

</body>
</html>
		