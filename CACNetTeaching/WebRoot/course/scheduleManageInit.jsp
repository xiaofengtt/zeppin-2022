<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../teacher/academicAdmin/head.jsp" %>
<%@ include file="left_course.jsp" %>
<script src="js/schedule.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="js/Validform-min.js"></script>

<div class="cca-main">
			  <div id="cbody" class="inn">
				   <div class="cca-table cca-table-padding">
				     <div class="cca-table-hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)<a href="course_planManageInit.action" style="float:right" class="btn  btn-success" type="button"><i class="icon-arrow-left icon-white"></i>返回上层</a></div>
					  <form class="form-horizontal" action="course_scheduleManage.action?id=<s:property value="coursedesign.id"/>&opt=add" method="post">
						  <div class="clearfix">
							  <div class="half">
								<div class="control-group">
								    <label class="control-label" for="inputEmail">开课日期</label>
								    <div class="controls">
								      <input type="text" id="startTime" name="cs.startTime">
								    </div>
								  </div>
								  
								  
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">上课时间</label>
								      <div class="controls">
								       <select id="dateType" name="cs.datetype">
								       <option value="上午">上午</option>
								        <option value="下午">下午</option>
								       </select>
								      </div>
								  </div>
								  
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">周数 </label>
								      <div class="controls">
								        <input type="text" id="weeks" name="cs.weeks" datatype="n1-3">
								      </div>
								  </div>
								  
							  </div>
							  
							  <div class="half">
								 <div class="control-group">
								      <label class="control-label" for="inputEmail">上课地点</label>
								      <div class="controls">
								        <input type="text" id="classRoom" name="cs.classroom" datatype="*">
								      </div>
								  </div>
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">教师</label>
								      <div class="controls">
								        <select id="teacherId"  name="cs.teacherByFkTeacher.id">
								         <s:iterator value="lstCoursedesignTeacherMaps" id="teacher" status="st">
								       <option value="<s:property value="#teacher.teacher.id"/>"><s:property value="#teacher.teacher.name"/></option>
								       </s:iterator>
								       </select>
								      </div>
								  </div>
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">备注</label>
								      <div class="controls">
								        <input type="text" id="remark" name="cs.remark">
								      </div>
								  </div>
							  </div>
						  </div>
						  <div class="cca-tr"><button class="btn btn-success" type="submit">添加</button> </div>
					  </form>
					  <div class="myclass">
					
					  
					  <div class="zuoye clearfix">
					  <s:iterator value='lstCourseschedules' id="schedule" >
						  <div class="grid slight">
							  <p><s:date name="#schedule.StartTime" format="yyyy年MM月dd日" nice="false"/>-<s:property value="#schedule.datetype"/><br>
								  <s:property value="#schedule.classroom"/><br>
								  周数：<s:property value="#schedule.weeks"/><br>
								  任课教师：<s:property value="#schedule.teacherByFkTeacher.name"/><br>
								 备注：<s:property value="#schedule.remark"/>
							  </p>
							  <span  style="cursor:pointer;" class="xx" >&times;<input type="hidden" value="<s:property value="#schedule.id"/>"/></span>
						  </div>
						  </s:iterator>
					  </div>
					  </div>
				  </div>
			  </div>
		  </div>
  </body>
</html>