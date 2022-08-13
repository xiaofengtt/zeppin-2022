<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">自定义筛查器管理</s:param>
</s:action>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script type="text/javascript" src="../js/map.js"></script>
<link href="../css/teacherTrainingFilterOptiframe.css" rel="stylesheet">
<link href="../css/iframe.css" rel="stylesheet">
<div class="main">
	<!-- <div class="list_bar" style="height:58px;">添加教师培训筛查器</div> -->
	<div class="container">
		<h3 class="text-center">添加教师培训筛查器</h3>
		<!-- 筛查器基本信息 -->
		<div class="basicInfo">
			<p class="title">筛查器基本信息</p>
			<div class="col-md-9 group">
				<div class="form-group">
					<label class="">名称：</label>
					<div class="col-md-8">
						<input class="form-control" id="filterName" value="" onblur="nameBlur('#filterName')"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="">权重：</label>
					<div class="col-md-8">
						<select class="form-control" id="filterWeight">
							
						</select>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<label class="">状态：</label>
					<div class="col-md-7">
						<select class="form-control" id="filterState">
							<option value="1">运行</option>
							<option value="0">停止</option>
						</select>
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 作用域范围 -->
		<div class="basicInfo rangeInfo">
			<p class="title">定义筛查器作用域范围</p>
			<div class="col-md-6 group">
				<div class="form-group">
					<label class="col-md-2">年份：</label>
					<div class="col-md-9" id="filterYear">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<select class="span3 form-control filterYearStart" name="startyear" onchange="selectEndYearList(this);">
									<option value="0" selected>请选择</option>
									<s:iterator value="yearArray" id="ya">
										<option <s:if test="year==#ya" ></s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
								<span class="select-group-span">至</span>
								<select class="span3 form-control filterYearEnd" name="endyear">
									<option value="0" selected>请选择</option>
								</select>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Year" onclick="addFilterYear(this,'年')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="form-group">
					<label class="col-md-2">项目级别：</label>
					<div class="col-md-9" id="filterLevel">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Level" onclick="addFilters(this,'mapProjectlevel','#filterLevel',mapProjectlevel)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">项目类型：</label>
					<div class="col-md-9" id="filterType">
						<div class="companylocation" style="z-index: 1;width:100%;height:30px;padding:0;border-radius:4px;" >
							<span class="clId clid" style="height: 30px;border-radius:4px;line-height:30px;padding-left:8px;" id="calId"
								onclick="changeToggle()" name="all">全部</span>
							<div id="calListBox" class="listCate" style="margin-top:30px">
								<div id="calList" class="list_sub sm_icon">
									<div id="cabido"></div>
									<div id="menuTree" class="menuTree">
										
									</div>
								</div>
							</div>
						</div>
						
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Type" onclick="addFilterType(this)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">培训项目：</label>
					<div class="col-md-9" id="filterProject">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Project" onclick="addFilter(this,'mapProject','#filterProject',mapProject)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">培训学科：</label>
					<div class="col-md-9" id="filterSubject">
						<select name="trainingSubjectId" class="form-control">
							<option value="-1">请选择</option>
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Subject" onclick="addFilter(this,'mapTrainingsubject','#filterSubject',mapTrainingsubject)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">总学时：</label>
					<div class="col-md-9" id="filterHours">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<input class="form-control filterHoursStart" placeholder="0" id="filterHoursStart"
								onblur="blurs('#filterHoursStart','#filterHoursEnd','#filterHoursStart','#filterHoursEnd')"/>
								<span class="select-group-span">~</span>
								<input class="form-control filterHoursEnd" placeholder="9999" id="filterHoursEnd"
								onblur="blurs('#filterHoursEnd','#filterHoursStart','#filterHoursStart','#filterHoursEnd')"/>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Hours" onclick="addFilterEach(this,'学时')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="col-md-6 rangeRight">
				<div class="years" id="showYear">
					<label>年份：</label>
					<div class="mtcDiv">
						
					</div>					
					<div class="clear"></div>
				</div>
				
				<div class="levels" id="showLevel">
					<label>项目级别：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="types" id="showType">
					<label>项目类型：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="projects" id="showProject">
					<label>培训项目：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="subjects" id="showSubject">
					<label>培训学科：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="hours" id="showHours">
					<label>总学时：</label>
					<div class="mtcDiv">
						
					</div>					
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 重复筛查 -->
		<div class="basicInfo algorithmInfo">
			<p class="title">教师重复培训筛查</p>
			<div class="col-md-6 group">
				<div class="form-group">
					<label class="col-md-2">培训次数：</label>
					<div class="col-md-9" id="filterTimes">
						<select class="form-control col-md-6 filterTimesStart">
							<option value="1">同一项目类型</option>
							<option value="2">同一学科</option>
							<option value="3">同一项目类型同一学科</option>
							<option value="4">同一年同一项目类型</option>
							<option value="5">同一年同一项目类型同一学科</option>
							<option value="6">所有符合以下条件范围的培训</option>
						</select>
						<select class="form-control col-md-3 filterTimesEnd" style="margin:0 1%;">
							<option value="1">不大于</option>
						</select>
						<input class="form-control col-md-3" placeholder="1" style="padding:4px 12px;"/>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Times" onclick="addFilterTimes(this)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">年份：</label>
					<div class="col-md-9" id="filterYears">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<select class="span3 form-control filterYearsStart" name="startyear" onchange="selectEndYearList(this);" id="">
									<option value="0" selected>请选择</option>
									<s:iterator value="yearArray" id="ya">	
										<option <s:if test="year==#ya" ></s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
								<span class="select-group-span">至</span>
								<select class="span3 form-control filterYearsEnd" name="endyear" id="">
									<option value="0" selected>请选择</option>
								</select>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Years" onclick="addFilterYear(this,'年')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="form-group">
					<label class="col-md-2">项目级别：</label>
					<div class="col-md-9" id="filterLevels">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Levels" onclick="addFilters(this,'mapProjectlevels','#filterLevels',mapProjectlevels)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">项目类型：</label>
					<div class="col-md-9" id="filterTypes">
						<div class="companylocation" style="z-index: 1;width:100%;height:30px;padding:0;border-radius:4px;" >
							<span class="clId clid" style="height: 30px;border-radius:4px;line-height:30px;padding-left:8px;" id="calIds"
								onclick="changeToggles()" name="all">全部</span>
							<div id="calListBoxs" class="listCate" style="margin-top:30px">
								<div id="calLists" class="list_sub sm_icon">
									<div id="cabidos"></div>
									<div id="menuTrees" class="menuTree">
										<%-- <div onclick="getName(this)">
											<span>
												<a name="0">全部</a>
											</span>
										</div> --%>
									</div>
								</div>
							</div>
						</div>
						<input type="hidden" id="categorys" name="categorys" value="">
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Types" onclick="addFilterType(this)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">培训项目：</label>
					<div class="col-md-9" id="filterProjects">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Projects" onclick="addFilter(this,'mapProjects','#filterProjects',mapProjects)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">培训学科：</label>
					<div class="col-md-9" id="filterSubjects">
						<select name="trainingSubjectId" class="form-control">
							<option value="-1">请选择</option>
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Subjects" onclick="addFilter(this,'mapTrainingsubject','#filterSubjects',mapTrainingsubject)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">总学时：</label>
					<div class="col-md-9" id="filterHourss">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<input class="form-control filterHourssStart" placeholder="0" id="filterHourssStart" 
								onblur="blurs('#filterHourssStart','#filterHourssEnd','#filterHourssStart','#filterHourssEnd')"/>
								<span class="select-group-span">~</span>
								<input class="form-control filterHourssEnd" placeholder="9999" id="filterHourssEnd" 
								onblur="blurs('#filterHourssEnd','#filterHourssStart','#filterHourssStart','#filterHourssEnd')"/>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Hourss" onclick="addFilterEach(this,'学时')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="col-md-6 rangeRight">
				<div id="showTimes">
					<label>培训次数：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="years" id="showYears">
					<label>年份：</label>
					<div class="mtcDiv">
						
					</div>					
					<div class="clear"></div>
				</div>
				
				<div class="levels" id="showLevels">
					<label>项目级别：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="types" id="showTypes">
					<label>项目类型：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="projects" id="showProjects">
					<label>培训项目：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="subjects" id="showSubjects">
					<label>培训学科：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div class="hours" id="showHourss">
					<label>总学时：</label>
					<div class="mtcDiv">
						
					</div>					
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 算法 -->
		<div class="basicInfo algorithmInfo">
			<p class="title">教师基本信息筛查</p>
			<div class="col-md-6 group">
				<div class="form-group">
					<label class="col-md-2">教学学段：</label>
					<div class="col-md-9" id="filterGrade">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Grade" onclick="addFilter(this,'mapTeachinggrade','#filterGrade',mapTeachinggrade)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">教学学科：</label>
					<div class="col-md-9" id="filterTeachSubject">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="TeachSubject" onclick="addFilter(this,'mapTeachingsubject','#filterTeachSubject',mapTeachingsubject)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">教学语言：</label>
					<div class="col-md-9" id="filterLanguage">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Language" onclick="addFilter(this,'mapTeachingLanguage','#filterLanguage',mapTeachingLanguage)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">双语教师：</label>
					<div class="col-md-9" id="filterMutiLanguage">
					<label class="text-left">
					    <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked text="是">
					    是
					 </label>
					<label class="text-left">
					    <input type="radio" name="optionsRadios" id="optionsRadios2" value="0" text="否">
					    否
					 </label>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="MutiLanguage" onclick="addFilterMutiLanguage(this)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">职称：</label>
					<div class="col-md-9" id="filterJobTitle">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="JobTitle" onclick="addFilter(this,'mapJobtitle','#filterJobTitle',mapJobtitle)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">民族：</label>
					<div class="col-md-9" id="filterNation">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Nation" onclick="addFilter(this,'mapEthnic','#filterNation',mapEthnic)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">教龄：</label>
					<div class="col-md-9" id="filterTeachTime">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<select class="form-control filterTeachTimeStart" id="teachStartTime" 
								onchange="changes('#teachStartTime','#teacherEndTime','#teachStartTime','#teacherEndTime')">
									
								</select>
								<span class="select-group-span">至</span>
								<select class="form-control filterTeachTimeEnd" id="teacherEndTime"
								onchange="changes('#teacherEndTime','#teachStartTime','#teachStartTime','#teacherEndTime')">
									
								</select>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="TeachTime" onclick="addFilterEach(this,'年')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">年龄：</label>
					<div class="col-md-9" id="filterOld">
						<div class="controls" style="min-width:350px;margin-left:0;float:left;">
							<div class="select-group">
								<select class="form-control filterOldStart" id="startOldTime"
								onchange="changes('#startOldTime','#endOldTime','#startOldTime','#endOldTime')">
									
								</select>
								<span class="select-group-span">至</span>
								<select class="form-control filterOldEnd" id="endOldTime"
								onchange="changes('#endOldTime','#startOldTime','#startOldTime','#endOldTime')">
									
								</select>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Old" onclick="addFilterEach(this,'岁')">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group">
					<label class="col-md-2">政治面貌：</label>
					<div class="col-md-9" id="filterPolitice">
						<select class="form-control">
							
						</select>
					</div>
					<div class="col-md-1">
						<a class="addBtn" name="Politice" onclick="addFilter(this,'mapPolitics','#filterPolitice',mapPolitics)">+</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="col-md-6 rangeRight">
				
				<div id="showGrade">
					<label>教学学段：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showTeachSubject">
					<label>教学学科：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showLanguage">
					<label>教学语言：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showMutiLanguage">
					<label>双语教师：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showJobTitle">
					<label>职称：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showNation">
					<label>民族：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showTeachTime">
					<label>教龄：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showOld">
					<label>年龄：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
				<div id="showPolitice">
					<label>政治面貌：</label>
					<div class="mtcDiv">
						
					</div>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="text-center buttonDiv"><button class="btn btn-primary btn-myself" onclick="saveAndadd();">确定</button>
					<button class="btn btn-default btn-myself" onclick="cancleBtn()">取消</button></div>
	</div>

</div>
<script src="../js/teacherTrainingFilterOptiframe.js"></script>
<jsp:include page="foot.jsp"></jsp:include>