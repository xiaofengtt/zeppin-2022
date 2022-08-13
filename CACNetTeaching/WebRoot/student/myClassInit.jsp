<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/tMyClass.js" type="text/javascript"></script>
<script type="text/javascript" src="js/tabifier.js"></script>
<script type="text/javascript" src="js/jPages.min.js"></script>
 <link rel="stylesheet" href="css/jPages.css">
<div class="cca-main">
 <div id="cbody" class="inn">
				  <div class="myclass">
					  <div class="hd">我的课程</div>
					    <div class="holder"></div>
					    <div id="courselst">
					  <s:iterator value="lstMc" id="ctm" >
					  <div class="myclass-grid">
						  <div class="grid-h clearfix">
							  <span class="grid-num">课程序号：<s:property value="#ctm.coursedesign.id"/></span>                  
							  <span class="grid-name">课程名称：<s:property value="#ctm.coursedesign.subject.name"/>(<s:property value="#ctm.coursedesign.dicAcademy.name"/>)</span>
							  <span class="grid-action">
								  <a href="student_homeworkInit.action?coursedesignId=<s:property value="#ctm.coursedesign.id"/>">课表及作业</a>
								  <a href="student_coursewareInit.action?coursedesignId=<s:property value="#ctm.coursedesign.id"/>">查看课件</a>
							  </span>
						  </div>
						  <div class="grid-b">
							  <table class="table table-hover">
		  		                <tbody>
		  		                  <tr>                                                                                    
		  		                    <td>开课日期：<s:date name="#ctm.coursedesign.startTime" format="yyyy年MM月dd日"/></td>
		  							<td>开课周数：<s:property value="#ctm.coursedesign.weeks"/></td>
		  							<td>总课时：<s:property value="#ctm.coursedesign.classHour"/></td>
		  							<td>教学组长：<s:property value="#ctm.leader"/></td>
       
		  		                  </tr>
								  
		  		                  <tr>                                                                                    
		  							<td>授课对象：<s:property value="#ctm.coursedesign.studentDescirpt"/></td>
		                            <td>修读方式：<s:property value="#ctm.mode"/></td>
		  							<td>课程学分：<s:property value="#ctm.coursedesign.credit"/></td>
		  							<td>任课教师：<s:property value="#ctm.teacheres"/></td>
		  		                  </tr>
		  		                 <tr>
		  		                 <td>所在小组：<s:property value="#ctm.groupName"/></td>
		  		                   <td>成绩：<s:property value="#ctm.score"/></td>
		  		                    <td colspan="2" >备注：<s:property value="#ctm.coursedesign.remark"/></td>
		  		                 </tr>
		  		                 
		  		                </tbody>
		  		              </table>
						  </div>
					  </div>
					  </s:iterator>
					  </div>
					  
				  </div>
			  </div>
	
		  

		
</div>
  </body>
</html>