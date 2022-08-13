<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>

<script src="js/studentHomeworkList.js" type="text/javascript"></script>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>
 <link rel="stylesheet" href="css/jPages.css">
<div class="cca-main">
			  <div id="cbody" class="inn">
				
				   <div class="cca-table">
				  	  <div class="holder"></div>
						  <table class="table table-bordered table-hover">
			                <thead>
			                  <tr>
			                    <th>学号</th>
			                    <th>姓名</th>
			                    <th>性别</th>
			                    <th>专业</th>
			                    <th>班级</th>
								<th>所在组</th>
								<th>作业提交时间</th>
								<th>联系方式</th>
								<th>作业状态</th>
								<th>作业</th>
								<th>操作</th>
			                  </tr>
			                </thead>
			                <tbody id="homeworkList">
			               
			                <s:iterator value="lsthaex" id="haex"> 
			                  <tr>                                                                                    
			                    <td><s:property value="#haex.stexExt.student.studentCode"/></td>
								<td><s:property value="#haex.stexExt.student.name"/></td>
								<td><s:property value="#haex.stexExt.sex"/></td>
								<td><s:property value="#haex.stexExt.student.dicMajor.name"/></td>
								<td><s:property value="#haex.stexExt.student.classes"/></td>
								<td><s:property value="#haex.stexExt.group"/></td>
							    <td><s:date name="#haex.ham.accessory.createTime" format="yyyy年MM月dd日"/></td>
								<td><s:property value="#haex.stexExt.student.phone"/></td>
								<td><s:property value="#haex.homeworkState"/></td>
								<td style="width: 147px; "><a href="<s:property value="#haex.ham.accessory.filePath"/>/<s:property value="#haex.ham.accessory.name"/><s:property value="#haex.ham.accessory.fileType"/>"><s:property value="#haex.ham.accessory.oraName"/><s:property value="#haex.ham.accessory.fileType"/></a></td>
								<td><input type="hidden" value="<s:property value="#haex.ham.id"/>"/>
								<select>
								<option value=-1>请选择</option>
								<option value=2>发回修改</option>
								<option value=3>通过</option>
								</select></td>
		                   
			                  </tr>
			               </s:iterator>
			                </tbody>
			              </table>
						 
					  </div>
					  
			  </div>
		  </div>
  </body>
</html>