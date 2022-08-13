<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<style>
.clear{clear:both;}
.select2-container .select2-choice > .select2-chosen{height:34px;line-height:34px;}
.form-group label{float:left;}
.layerContent{padding:20px;}
.layerContent .col-md-6{width:49%;padding:0;border:1px solid #e6e6e6;}
@media (max-width: 992px){
.layerContent .col-md-6{width:100%;border:1px solid #e6e6e6;margin-top:20px;}
.layerContent .col-md-8{width:66.6666%;}
.layerContent .col-md-3{width:25%;}
}
.layerContent .col-md-8{float:left;padding:0;}
.layerContent .col-md-8 div{display:inline-block;marign:5px 3px;padding:3px;}
.layerContent .tips{color:#f00;}
.layerContent div p{font-size:15px;padding-left:40px;font-weight:bold;height:40px;line-height:40px;
					background:#f2f2f2;border-bottom:1px solid #e6e6e6;}
.layerContent .btnGroup{padding-right:100px;/* border-top:1px solid #e6e6e6;margin-top:30px; */}
.layerContent .btnGroup a{margin:15px 5px 0px 5px;}
.layerContent b{display:block;line-height:24px;font-weight:normal;padding:0 20px;text-indent:2em;}
</style>
<div class="main">

	<div class="listwrap">
		<div class="list_bar">添加报送学员</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen"> <span><img
					src="../img/sousuo.png" alt="icon"> <b>查找</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>查找学员</b>
				</p>
			</a> 
		</div>
		<div class="list-content clearfix" style="padding-top:15px;">
			
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
				<div class="fliter-btn-wrap" style="display:none">
					<a href="../admin/ttResult_initOReortAsstPage.action" class="btn btn-primary">&larr;返回报送任务</a>
					<!-- <button class="btn btn-primary fliter-btn"  type="button">查找学员</button> -->
				</div>
			<form id="ttRecordsReport" class="form-horizontal" role="form">
				
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">身份证号</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherIdCard">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">所属学校</label>
							<div class="col-sm-9">
								<input name="organization" id="organization" type="hidden"
									value='<s:property value="organizationp.id"/>'>
							</div>

						</div>

						<!-- <div class="form-group">
							<label class="col-sm-2 control-label" for="">性别</label>
							<div class="col-sm-9">
								<select name="sex" class="form-control">
									<option value="">请选择</option>
									<option value="1">男</option>
									<option value="2">女</option>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">民族</label>
							<div class="col-sm-9">
								<select name="ethnic" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstEthnics" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher. ethnic}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">是否双语教学</label>
							<div class="col-sm-9">
								<input type="radio" name="isMu" value="" checked>&nbsp;&nbsp;全部&nbsp;&nbsp;
								<input type="radio" name="isMu" value="1">&nbsp;&nbsp;是 &nbsp;&nbsp;
								<input type="radio" name="isMu" value="0"> &nbsp;&nbsp;否 &nbsp;&nbsp;
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">职称</label>
							<div class="col-sm-9">
								<select name="jobTitle" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstJobTitles" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher. jobTitle}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">汉语言水平</label>
							<div class="col-sm-9">
								<select name="chineseLanguageLevel" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstChineseLanguageLevels" id="e">
										<option value="<s:property value="#e.id" />"
											<s:if test="%{#e.id==teacher.chineseLanguageLevel}" >selected</s:if>><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学学科</label>
							<div class="col-sm-9">
								<select name="mainGrade" id="mainGrade" class="form-control">
									<option value="">请选择</option>
									<s:iterator value="lstSubjects" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div> -->

					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">姓名</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherName">
							</div>

						</div>
						<!-- <div class="form-group">
							<label class="col-sm-2 control-label" for="">年龄</label>
							<div class="col-sm-9">
								<input type="text" class="form-control col-sm-4" style="width:50px"  name="age1"> <span style="padding:6px 0;text-align:center"  class="col-xs-1">至</span> <input
									type="text"  class="form-control col-sm-4"  style="width:50px"  name="age2">
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">教龄</label>
							<div class="col-sm-9 form-inline">
								<input type="text" class="form-control col-sm-4" name="tage1" style="width:50px"> <span  class="col-xs-1"  style="padding:6px 0;text-align:center">至</span> <input
									class="form-control col-sm-4" type="text" name="tage2" style="width:50px">
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">政治面貌</label>
							<div class="col-sm-9">
								<select class="form-control" name="politics">
									<option value="">请选择</option>
									<s:iterator value="lstPolitics" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">行政职务</label>
							<div class="col-sm-9">
								<select class="form-control" name="jobDuty">
									<option value="">请选择</option>
									<s:iterator value="lstJobDuties" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">最高学历</label>
							<div class="col-sm-9">
								<select class="form-control" name="eductionBackground">
									<option value="">请选择</option>
									<s:iterator value="lstBackgrounds" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学语言</label>
							<div class="col-sm-9">
								<select class="form-control" name="mainTeachingLanguage" id="mainTeachingLanguage"
									class="span3">
									<option value="">请选择</option>
									<s:iterator value="lstLanguages" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">主要教学学段</label>
							<div class="col-sm-9">
								<select class="form-control" name="mainTeachingGrades" id="mainTeachingGrades"
									class="span3">
									<option value="">请选择</option>
									<s:iterator value="lstGrades" id="e">
										<option value="<s:property value="#e.id" />"><s:property
												value="#e.name" /></option>
									</s:iterator>
								</select>
							</div>

						</div> -->

					</div>
				</div>

				<div class="clearfix" style="margin:0 40px 0 20px">
					<div id="list-content" class="list-content clearfix"></div>
				</div>

				<div class="row actionbar">
					<div style="text-align:center">
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
					</div>
				</div>
			</form>
		</div>
		<div id="list-item-cnt" style="margin-top:10px;"></div>
		<div id="pagination" style="float:right;margin-right:10px" class="pull-right"></div>
		<script id="ttRecordTpl" type="text/x-jsrender">
			<div id="{{:id}}" class="list-item">
				<div class="list-item-hd">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="auto"><span class="text-primary">所属学校：</span>{{:school}}</td>
								<td width="200px" class="text-primary">姓名：<span>{{:name}}</span></td>
								
								<td width="160px" class="text-center"><span id="status_{{:id}}">
									{{if record_status}}
									<a onclick="addRecords(this)" teacherId={{:teacherId}} data-id="{{:id}}" data-url="../admin/ttRecord_addTeacherReport.action?projectId={{:projectId}}&subjectId={{:subjectId}}&collegeId={{:tcid}}&gorganizationId={{:gorganizationId}}&porganizationId={{:porganizationId}}&teacherId={{:teacherId}}" href="javascript:void(0)">添加该学员</a>
									{{else}}
									添加成功
									{{/if}}
									</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="list-item-bd clearfix">
		
					<div class="list-item-col list-5-05" style="width:100px;text-align:center">
						<img src="../img/u{{:sexIndex}}.png" width="80">
					</div>
		
					<div class="list-item-col list-5-05">
						<ul>
							<li><span class="text-primary">性别：</span>{{:sex}}</li>
							<li><span class="text-primary">年龄：</span> {{:age}}</li>
							<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
							<li><span class="text-primary">政治面貌：</span> {{:politic}}</li>
							<li><span class="text-primary">状态：</span> {{:status}}</li>
						</ul>
					</div>
					<div class="list-item-col list-5-05" style="width:150px;">
						<ul>
							<li><span class="text-primary">教龄：</span> {{:tage}}</li>
							<li><span class="text-primary">职称：</span> {{:jobTitle}}</li>
							<li><span class="text-primary">行政职务：</span> {{:jobDuty}}</li>
							<li><span class="text-primary">是否双语教学：</span>{{:isMu}}</li>
							<li><span class="text-primary">编制：</span>{{:authorized}}</li>
						</ul>
					</div>
					<div class="list-item-col list-5-1">
						<ul>
							<li><span class="text-primary">主要教学学段：</span>{{:grade}} </li>
							<li><span class="text-primary">主要教学学科：</span>{{:subject}} </li>
							<li><span class="text-primary">主要教学语言：</span>{{:language}} </li>
							<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>

						</ul>
					</div>
					<div class="list-item-col list-5-8">
						<ul>
							<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
							<li><span class="text-primary">汉语言水平：</span> {{:languageLevel}}</li>
							<li><span class="text-primary">身份证: </span>{{:idcard}}</li>
							<li>
								<span class="text-primary">手机：</span>{{:mobile}}
							</li>

						</ul>
					</div>
		

				</div>
			</div>
		</script>





	</div>
	</div>

</div>
	<script>
		$(function() {
			var recordReport = function(o, pp) {
				var pp = (typeof (pp) == 'undefined') ? 1 : pp;
				
				var projectId = url('?projectId');
				var subjectId = url('?subjectId');
				var collegeId = url('?collegeId');
				var gorganizationId = url('?gorganizationId');
				var porganizationId = url('?porganizationId');
				
				var str = $(o).serialize() + '&page=' + pp+'&projectId='+projectId+'&subjectId='+subjectId+'&collegeId='+collegeId+'&gorganizationId='+gorganizationId+'&porganizationId='+porganizationId;
				$('#list-item-cnt').html('<p style="font-size:20px;text-align:center;height:100px;line-height:100px;">数据加载中....</p>');
				$.getJSON('../admin/ttResult_searchReportTeacher.action?' + str,
						function(data) {
							if (data.status == 'OK') {
								var template = $.templates('#ttRecordTpl');
								var html = template.render(data.records);
								$('#list-item-cnt').html(html);
								var options = {
									currentPage : data.page.currentPage,
									totalPages : data.page.totalPage,
									shouldShowPage:function(type, page, current){
						                switch(type)
						                {
						                    default:
						                        return true;
						                }
						            },
									onPageClicked : function(e, originalEvent,
											type, page) {
										var pp = page;
										recordReport('#ttRecordsReport', pp);
									}
								}
								$('#pagination').bootstrapPaginator(options);
								
								$(o).slideUp('slow',function(){
									$('.fliter-btn-wrap').show();
								})
								$('.alert-danger').hide();
							} else {
								$('.alert-danger').html(data.message).show();
								$('#list-item-cnt,#pagination').html('');
								
							}
				}).fail(function(){
					$('#list-item-cnt').html('系统错误请稍后重试');
				});
			}
			$('#ttRecordsReport').submit(function() {
				recordReport(this);
				return false;
			});
			
			$('.btn-screen').click(function(e){
				e.preventDefault();
				$('.fliter-btn-wrap').toggle();
				$('#ttRecordsReport').toggle();
				
			})

			var sort = (url('?sort')) ? url('?sort') : 'year-asc';
			var _ = sort.split('-');
			$('#sorting-' + _[0]).addClass('crt');
			$('#sorting-' + _[0]).find('span').addClass(_[1]);

			$('.sorting-btns a').click(function() {
				var name = $(this).attr('data-name');
				sortasc(name);
				return false;
			})

			//所属学校
			$("#organization").select2({
				placeholder : "请输入学校名称或拼音首字母",
				minimumInputLength : 0,
				quietMillis : 1000,
				allowClear : true,
				ajax : {
					url : "../base/organization_searchSchool.action",
					dataType : 'json',
					data : function(term, page) {
						return {
							search : term, // search term
							page : 10
						};
					},
					results : function(data, page) {
						return {
							results : data.Options
						};

					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					callback(data.Options);
				},
				formatResult : movieFormatResult,
				formatSelection : movieFormatSelection,
				dropdownCssClass : "bigdrop",
				escapeMarkup : function(m) {
					return m;
				}
			});

			function movieFormatResult(Options) {
				var html = Options.DisplayText;
				return html;

			}
			function movieFormatSelection(Options) {
				return Options.DisplayText;
			}

		})
		var projectId = url('?projectId');
		var subjectId = url('?subjectId');
		var collegeId = url('?collegeId');
		var gorganizationId = url('?gorganizationId');
		var porganizationId = url('?porganizationId');
		var flag = true;
		var flag2 = true;
		// 添加学员
		function addRecords(t) {
			if(flag){
				flag = false;
				var obj = $(t);
				var spanId= obj.attr('data-id');
				var cUrl = obj.attr('data-url');
				var teacherId=obj.attr("teacherId");
				$.ajax({
			        type: "GET",
			        url: "../admin/sizer_signupCheckerSizer.action",
			        data: {projectId:projectId,subjectId:subjectId,collegeId:collegeId,gorganizationId:gorganizationId,porganizationId:porganizationId,teacherId:teacherId},
			        dataType: "text",
			        async: true,
			        success: function (data) {
			        	var json = (new Function("", "return " + data.split("&&&")[0]))();
			        	if(json.Result=="WARNING"){
			        		var teacherName=json.Records.teacher.name;
				        	var teacherIdCard=json.Records.teacher.idCard;
				        	var teacherJobTitle=json.Records.teacher.jobTitle;
				        	var teacherEthnic=json.Records.teacher.ethnic;
				        	var teacherPolitics=json.Records.teacher.politics;
				        	var teacherTeacherAge=json.Records.teacher.teacherAge;
				        	var teacherTeachingAge=json.Records.teacher.teachingAge;
				        	var teacherMultiLanguage=json.Records.teacher.multiLanguage;
				        	var teacherMainTeachingLanguage=json.Records.teacher.mainTeachingLanguage;
				        	var teacherMainTeachingGrades=json.Records.teacher.mainTeachingGrades;
				        	var teacherMainTeachingSubject=json.Records.teacher.mainTeachingSubject;
				        	var sizerTeachingGrade="";
				        	for(i=0;i<json.Records.sizer.teachingGrade.length;i++){
				        		sizerTeachingGrade+="<div>"+json.Records.sizer.teachingGrade[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.teachingGrade.length==0){
				        		sizerTeachingGrade="<div>无条件</div>";
				        	}
				        	var sizerTeachingSubject="";
				        	for(i=0;i<json.Records.sizer.teachingSubject.length;i++){
				        		sizerTeachingSubject+="<div>"+json.Records.sizer.teachingSubject[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.teachingSubject.length==0){
				        		sizerTeachingSubject="<div>无条件</div>";
				        	}
				        	var sizerTeachingLanguage="";
				        	for(i=0;i<json.Records.sizer.teachingLanguage.length;i++){
				        		sizerTeachingLanguage+="<div>"+json.Records.sizer.teachingLanguage[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.teachingLanguage.length==0){
				        		sizerTeachingLanguage="<div>无条件</div>";
				        	}
				        	var sizerJobTitle="";
				        	for(i=0;i<json.Records.sizer.jobTitle.length;i++){
				        		sizerJobTitle+="<div>"+json.Records.sizer.jobTitle[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.jobTitle.length==0){
				        		sizerJobTitle="<div>无条件</div>";
				        	}
				        	var sizerEthnic="";
				        	for(i=0;i<json.Records.sizer.ethnic.length;i++){
				        		sizerEthnic+="<div>"+json.Records.sizer.ethnic[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.ethnic.length==0){
				        		sizerEthnic="<div>无条件</div>";
				        	}
				        	var sizerPolitic="";
				        	for(i=0;i<json.Records.sizer.politic.length;i++){
				        		sizerPolitic+="<div>"+json.Records.sizer.politic[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.politic.length==0){
				        		sizerPolitic="<div>无条件</div>";
				        	}
				        	var sizerMulti="";
				        	for(i=0;i<json.Records.sizer.multi.length;i++){
				        		if(json.Records.sizer.multi[i].name=="0"){
				        			sizerMulti+="<div>否</div>";
				        		}else if(json.Records.sizer.multi[i].name=="1"){
				        			sizerMulti+="<div>是</div>";
				        		}
				        	}
				        	if(json.Records.sizer.multi.length==0){
				        		sizerMulti="<div>无条件</div>";
				        	}
				        	var sizerTeachingAge="";
				        	for(i=0;i<json.Records.sizer.teachingAge.length;i++){
				        		sizerTeachingAge+="<div>"+json.Records.sizer.teachingAge[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.teachingAge.length==0){
				        		sizerTeachingAge="<div>无条件</div>";
				        	}
				        	var sizerTeacherAge="";
				        	for(i=0;i<json.Records.sizer.teacherAge.length;i++){
				        		sizerTeacherAge+="<div>"+json.Records.sizer.teacherAge[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.teacherAge.length==0){
				        		sizerTeacherAge="<div>无条件</div>";
				        	}
			        		layer.open({
		        			  type: 1,
		        			  title: false, 
		        			  skin: '', //加上边框
		        			  area: ['80%','80%'], //宽高
		        			  content: '<div class="layerContent"><h4 class="text-center">教师基本信息筛查要求</h4>'
			        			  +""+'<p class="tips text-center">该教师“'+json.Message+'”,请确认是否继续报名</p>'
			        			  +""+'<div class="col-md-6"><p>教师信息</p><div class="form-group">'
			        			  +""+'<label class="text-right col-md-3">姓名：</label>'
		        			  +""+'<div class="col-md-8">'+teacherName+'</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">身份证号：</label><div class="col-md-8">'+teacherIdCard+
		        			  '</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">职称：</label>'
		        			  +""+'<div class="col-md-8">'+teacherJobTitle+'</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">民族：</label><div class="col-md-8">'+teacherEthnic+
		        			  '</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">年龄：</label><div class="col-md-8">'+teacherTeacherAge+
		        			  '岁</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">教龄：</label><div class="col-md-8">'+teacherTeachingAge+
		        			  '年</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">政治面貌：</label>'
		        			  +""+'<div class="col-md-8">'+teacherPolitics+'</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">双语教师：</label>'
		        			  +""+'<div class="col-md-8">'+teacherMultiLanguage+'</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">主要教学语言：</label><div class="col-md-8">'+teacherMainTeachingLanguage+
		        			  '</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">主要教学学段：</label>'
		        			  +""+'<div class="col-md-8">'+teacherMainTeachingGrades+'</div><div class="clear"></div></div>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">主要教学学科：</label><div class="col-md-8">'
		        			  +teacherMainTeachingSubject+'</div><div class="clear"></div></div></div><div class="col-md-6" style="float:right;"><p>报名条件</p>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">职称：</label><div class="col-md-8">'+sizerJobTitle+
		        			  '</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">民族：</label><div class="col-md-8">'+sizerEthnic+'</div><div class="clear"></div>'
		        			  +""+'</div><div class="form-group"><label class="text-right col-md-3">年龄：</label><div class="col-md-8">'+sizerTeacherAge+
		        			  '</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">教龄：</label>'
		        			  +""+'<div class="col-md-8">'+sizerTeachingAge+'</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">政治面貌：</label>'
		        			  +""+'<div class="col-md-8">'+sizerPolitic+'</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">双语教师：</label><div class="col-md-8">'+sizerMulti+
		        			  '</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">主要教学语言：</label>'
		        			  +""+'<div class="col-md-8">'+sizerTeachingLanguage+'</div><div class="clear"></div></div>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">主要教学学段：</label>'
		        			  +""+'<div class="col-md-8">'+sizerTeachingGrade+'</div><div class="clear"></div></div>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">主要教学学科：</label><div class="col-md-8">'+sizerTeachingSubject+
		        			  '</div><div class="clear"></div></div></div><div class="clear"></div><div class="btnGroup text-right"><a class="btn btn-primary btn-myself" onclick="addRecordNext('+teacherId+',\''
		        					  +spanId+'\',\''+cUrl+'\')">确定</a>'
		        			  +""+'<a class="btn btn-default btn-myself" onclick="layer.closeAll();">取消</a></div></div>',
		        			  success: function(){
		        				  var height=0;
		        				  $(".layerContent .col-md-6").each(function(){
		  		        			if($(this).height()>height){
		  		        				height=$(this).height();
		  		        			}else{
		  		        				height=height;
		  		        			}
		  		        		  });
		        				  $(".layerContent .col-md-6").height(height);
		        				  
		        			  }
		        			  
		        			});
			        		flag = true;
			        	}else if(json.Result=="OK"){
			        		originalAddRecords(spanId,cUrl);
			        	}else{
			        		flag = true;
			        		layer.confirm(json.Message, {
			    				btn : [ '确定' ]
			    			//按钮
			    			}, function() {
			    				layer.closeAll();
			    			});
			        	}
			        }
				})
			}
			
		}
		function originalAddRecords(id,cUrl){
			$.getJSON(cUrl, function(ret) {
				if (ret.Result == 'OK') {
					flag = true;
					$('#status_' + id).text('添加成功');
					layer.closeAll();
					//infotip(obj, ret.Message);
				} else if (ret.Result == 'WARNING') {
					if (confirm("人数已经达到上限，是否继续添加?")) {
						var tcUrl = cUrl + "&goon=1";
						$.getJSON(tcUrl, function(ret) {
							flag = true;
							if (ret.Result == 'OK') {
								$('#status_' + id).html('添加成功').addClass('sok');
								layer.closeAll();
							} else {
								layer.confirm('失败,' + ret.Message, {
				    				btn : [ '确定' ]
				    			//按钮
				    			}, function() {
				    				layer.closeAll();
				    			});
							}
						});
					} else {
						flag = true;
					}
				}else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
					flag = true;
					layer.confirm('失败,' + ret.Message, {
	    				btn : [ '确定' ]
	    			//按钮
	    			}, function() {
	    				layer.closeAll();
	    			});
				}
			}) 
			return false;
		}
		function addRecordNext(teacherId,t,s){
			if(flag2){
				flag2 = false;
				$.ajax({
			        type: "GET",
			        url: "../admin/sizer_signupCheckerSizer.action",
			        data: {projectId:projectId,subjectId:subjectId,collegeId:collegeId,gorganizationId:gorganizationId,porganizationId:porganizationId,teacherId:teacherId,isNext:1},
			        dataType: "text",
			        async: true,
			        success: function (data) {
			        	var json = (new Function("", "return " + data.split("&&&")[0]))();
						if(json.Result=="OK"){
			        		originalAddRecords(t,s);
			        	}else if(json.Result=="FAIL"){
			        		flag2 = true;
			        		layer.closeAll();
			        		var trainInformation="";
			        		for(var i=0;i<json.Records.teacher.records.length;i++){
			        			trainInformation+="<b>该教师在"+json.Records.teacher.records[i].year+"年参加了项目级别为\""
			        			+json.Records.teacher.records[i].projectLevel+"\"且项目类型为\""+json.Records.teacher.records[i].projectType+
			        			"\"项目为\""+json.Records.teacher.records[i].project+"\"的\""+json.Records.teacher.records[i].trainingSubject+
			        			"\"的培训；</b>";
			        		}
			        		var teacherProjectLevel=json.Records.teacher.records.projectLevel;
				        	var teacherProjectType=json.Records.teacher.records.projectType;
				        	var teacherProject=json.Records.teacher.records.project;
				        	var teacherTrainingSubject=json.Records.teacher.records.trainingSubject;
				        	var teacherYear=json.Records.teacher.records.year;
				        	var sizerTrainingcount="";
				        	for(i=0;i<json.Records.sizer.trainingcount.length;i++){
				        		sizerTrainingcount+="<div>"+json.Records.sizer.trainingcount[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.trainingcount.length==0){
				        		sizerTrainingcount="<div>无条件</div>";
				        	}
				        	var sizerYears="";
				        	for(i=0;i<json.Records.sizer.years.length;i++){
				        		sizerYears+="<div>"+json.Records.sizer.years[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.years.length==0){
				        		sizerYears="<div>无条件</div>";
				        	}
				        	var sizerProjectLevel="";
				        	for(i=0;i<json.Records.sizer.projectLevel.length;i++){
				        		sizerProjectLevel+="<div>"+json.Records.sizer.projectLevel[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.projectLevel.length==0){
				        		sizerProjectLevel="<div>无条件</div>";
				        	}
				        	var sizerProjectType="";
				        	for(i=0;i<json.Records.sizer.projectType.length;i++){
				        		sizerProjectType+="<div>"+json.Records.sizer.projectType[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.projectType.length==0){
				        		sizerProjectType="<div>无条件</div>";
				        	}
				        	var sizerProject="";
				        	for(i=0;i<json.Records.sizer.project.length;i++){
				        		sizerProject+="<div>"+json.Records.sizer.project[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.project.length==0){
				        		sizerProject="<div>无条件</div>";
				        	}
				        	var sizerSubject="";
				        	for(i=0;i<json.Records.sizer.subject.length;i++){
				        		sizerSubject+="<div>"+json.Records.sizer.subject[i].name+"；</div>";
				        	}
				        	if(json.Records.sizer.subject.length==0){
				        		sizerSubject="<div>无条件</div>";
				        	}
			        		layer.open({
		        			  type: 1,
		        			  title: false, 
		        			  skin: '', //加上边框
		        			  area: ['80%','80%'], //宽高
		        			  content: '<div class="layerContent"><h4 class="text-center">教师重复培训筛查要求</h4>'
			        			  +""+'<p class="tips text-center">“'+json.Message+'”</p>'
			        			  +""+'<div class="col-md-6"><p>教师信息</p><div class="form-group">'+trainInformation+
			        			  '</div></div><div class="col-md-6" style="float:right;"><p>重复筛查条件</p>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">年份：</label><div class="col-md-8">'+sizerYears+
		        			  '</div><div class="clear"></div></div><div class="form-group">'
		        			  +""+'<label class="text-right col-md-3">培训次数：</label><div class="col-md-8">'+sizerTrainingcount+
		        			  '</div><div class="clear"></div>'
		        			  +""+'</div><div class="form-group"><label class="text-right col-md-3">项目级别：</label><div class="col-md-8">'
		        			  +sizerProjectLevel+
		        			  '</div><div class="clear"></div></div><div class="form-group"><label class="text-right col-md-3">项目类型：</label>'
		        			  +""+'<div class="col-md-8">'+sizerProjectType+'</div><div class="clear"></div></div>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">培训项目：</label>'
		        			  +""+'<div class="col-md-8">'+sizerProject+'</div><div class="clear"></div></div>'
		        			  +""+'<div class="form-group"><label class="text-right col-md-3">培训学科：</label><div class="col-md-8">'
		        			  +sizerSubject+'</div><div class="clear"></div></div></div><div class="clear">'
		        			  +""+'</div><div class="btnGroup text-right"><a class="btn btn-default btn-myself" onclick="layer.closeAll();">'
		        			  +""+'关闭</a></div></div>',
		        			  success: function(){
		        				  var height=0;
		        				  $(".layerContent .col-md-6").each(function(){
		  		        			if($(this).height()>height){
		  		        				height=$(this).height();
		  		        			}else{
		  		        				height=height;
		  		        			}
		  		        		  });
		        				  $(".layerContent .col-md-6").height(height);
		        				  
		        			  }
		        			  
		        			});

			        	}else{
			        		flag2 = true;
			        		layer.closeAll();
			        		layer.confirm(json.Message, {
			    				btn : [ '确定' ]
			    			//按钮
			    			}, function() {
			    				layer.closeAll();
			    			});
			        	}
			        }
				});
			}
		}
	</script>
	<script src="../js/iframe.js"></script>
<body>
</html>