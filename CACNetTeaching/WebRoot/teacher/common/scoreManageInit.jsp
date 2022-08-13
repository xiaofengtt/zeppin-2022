<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>
 <link rel="stylesheet" href="css/jPages.css">
<script src="js/scoreManage.js" type="text/javascript"></script>
<div class="cca-main">
			  <div id="cbody" class="inn">
				 <div class="holder pagination-centered"></div>
				 <div class="clearfix" style="padding:10px 0;">
				 	<a href="teacher_myClassInit.action" class="btn  btn-success" type="button"><i class="icon-arrow-left icon-white"></i>返回上层</a>
				 <div style="float:right;margin:0 5px;"><button id="submit" class="btn btn-primary" type="button" ><i class="icon-ok icon-white"></i>将成绩提交到教务处</button><input type="hidden" id="isSubmit" value="<s:property value="coursedesign.scoreState"/>"/> </div>
				 </div>
				 
				   <div class="cca-table">
				  	
						  <table class="table table-bordered table-hover">
			                <thead>
			                  <tr>
			                    <th>学号</th>
			                    <th>姓名</th>
			                    <th>性别</th>
			                    <th>专业</th>
			                    <th>班级</th>
								<th>所在组</th>
								<th>联系方式</th>
								<th>分数</th>
								<th>操作</th>
			                  </tr>
			                </thead>
			                <tbody id="list"><!-- <input type="button" value="全部提交" style="float:right" id="inputAll"> -->
			                <s:iterator value="lstStudentExt" id="studentExt"  status="st" >
			                  <tr>                                                                                    
			                    <td><s:property value="#studentExt.student.studentCode"/></td>
								<td><s:property value="#studentExt.student.name"/></td>
								<td><s:property value="#studentExt.sex"/></td>
								<td><s:property value="#studentExt.student.dicMajor.name"/></td>
								<td><s:property value="#studentExt.student.classes"/></td>
								<td><s:property value="#studentExt.group"/></td>
								<td><s:property value="#studentExt.student.phone"/></td>
								<td><input type="text" style="width: 28px; " class="score" value="<s:property value="#studentExt.score"/>"/></td>
								<td><input type="button" value="确定" class="submit btn"><input type="hidden" value="<s:property value="#studentExt.student.id"/>" class="sid"></td>
			                  </tr>
			               </s:iterator>
			                </tbody>
			              </table>
						 
					  </div>
					  
			  </div>
		  </div>
  </body>
</html>