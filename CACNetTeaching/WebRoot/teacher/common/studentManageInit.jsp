 <%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/group.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>
 <link rel="stylesheet" href="css/jPages.css">
 <div class="cca-main">
			  <div id="cbody" class="inn">
				  
				  <div class="myclass">
    				<!-- <a class="btn btn-primary" style="float:right"  id="addStudent"><i class="icon-plus icon-white"></i>添加学生</a> -->
  					<div id="studentList">
  					<div class="cca-table">
					<table id="list"></table>
					<div id="pager"></div>			 
				  </div></div>
  					<div class="hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)<div style="float:right">学生人数：<span id="studentCount"><s:property value="studentCount"/></span></div></div>
                    
  					  <div class="cca-table" id="studentList">
  	                    <div class="holder"></div>
  						  <table class="table table-bordered table-hover">
  			                <thead >
  			                  <tr>
  			                    <th>编号</th>
  			                    <th>姓名</th>
  			                    <th>学号</th>
  			                   <!--  <th>学院</th> -->
  								<th>专业</th>
  								<th>班级</th>
  								<th>生源地</th>
  								<th>联系方式</th>
  								<th>操作</th>
  			                  </tr>
  			                </thead>
  			                <tbody id="studentlst">
  			               
  			                <s:iterator  value="lstStudents" id="student"  status="st">
  			                 
  			                 <tr>                                                                                    
  			                    <td><s:property value="#student.id"/></td>
  								<td><s:property value="#student.name"/></td>
  								<td><s:property value="#student.studentCode"/></td>
  								<td><s:property value="#student.dicMajor.name"/></td>
  								<td><s:property value="#student.classes"/></td>
   								<td><s:property value="#student.dicAddress.name"/></td>
  								<td><s:property value="#student.phone"/></td>
  								<%-- <td><s:property value="student.id"/> </td>
  								<td><s:property value="student.id"/></td> --%>
  								<td> <a >删 除</a></td>
           
  			                  </tr>
  			                
  			                </s:iterator>
  			                 
  			                </tbody>
  			              </table>
  						 
  					  </div>
					  <button class="btn btn-info" style="float:right" id="goback">返回</button> 
	
  				  </div>
				  
			  </div>
		  </div>
		  
	  </div>

  </body>
</html>