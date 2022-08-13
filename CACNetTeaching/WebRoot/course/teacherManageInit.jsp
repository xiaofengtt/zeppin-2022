 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="../teacher/academicAdmin/head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/courseteacher.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>

 <div class="cca-main">
			  <div id="cbody" class="inn">
				   
				  <div class="myclass">
    				<!-- <a class="btn btn-primary" style="float:right"  id="addteacher"><i class="icon-plus icon-white"></i>添加学生</a> -->
  					<div id="teacherList">
  					<div class="cca-table">
					<table id="list"></table>
					<div id="pager"></div>			 
				  </div></div>
  					<div class="hd"><a href="course_planManageInit.action" style="float:right" class="btn  btn-success" type="button"><i class="icon-arrow-left icon-white"></i>返回上层</a>课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)</div>
                   <input type="hidden" id="leaderId" value="<s:property value='leaderId'/>">
  					  <div class="cca-table" id="teacherList">
  	                      
  						  <table class="table table-bordered table-hover">
  			                <thead>
  			                  <tr>
  			                   <th></th>
  			                    <th>编号</th>
  			                    <th>姓名</th>
  			                    <th>工号</th>
  								<th>部门</th>
  								<th>职务</th>
  								<th>职称</th>
  								<th>联系方式</th>
  								<th>操作</th>
  			                  </tr>
  			                </thead>
  			                <tbody>
  			               
  			                <s:iterator  value="lstTeachers" id="teacher"  status="st">
  			                 
  			                 <tr>    
  			                 <td class="leaderIcon"></td>                                                                                
  			                    <td><s:property value="#teacher.id"/></td>
  								<td><s:property value="#teacher.name"/></td>
  								<td><s:property value="#teacher.workCode"/></td>
  								<td><s:property value="#teacher.dicAcademy.name"/></td>
   								<td><s:property value="#teacher.dicDuty.name"/></td>
   								<td><s:property value="#teacher.dicTechnicalTiltle.name"/></td>
  								<td><s:property value="#teacher.phone"/></td>
  								<%-- <td><s:property value="teacher.id"/> </td>
  								<td><s:property value="teacher.id"/></td> --%>
  								<td><a href="javascript:void(0)" class="leader">设为组长</a>&nbsp; &nbsp;  <a class="del">删 除</a></td>
           
  			                  </tr>
  			                
  			                </s:iterator>
  			                 
  			                </tbody>
  			              </table>
  						 
  					  </div>
					<!--   <a href="#" class="btn btn-info" style="float:right">返回</a> -->
	
  				  </div>
				  
			  </div>
		  </div>
		  
	

  </body>
</html>