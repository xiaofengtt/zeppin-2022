<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<%@ include file="left.jsp" %>
<script src="js/homework.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="text/javascript"></script>
 <script type="text/javascript" src="js/Validform-min.js"></script>

<div class="cca-main">
			  <div id="cbody" class="inn">
			  	<div class="clearfix" style="padding:10px 0;">
				 	<a href="teacher_myClassInit.action" class="btn  btn-success" type="button"><i class="icon-arrow-left icon-white"></i>返回上层</a>
				 
				 </div>
				   <div class="cca-table cca-table-padding">
				     <div class="cca-table-hd">课程名称：<s:property value="coursedesign.dicAcademy.name"/>--<s:property value="coursedesign.subject.name"/>(开课时间：<s:date name="coursedesign.startTime" format="yyyy-MM-dd " />)</div>
					  <form class="form-horizontal" action="teacher_addHomework.action?coursedesignId=<s:property value="coursedesign.id"/>"  method="post">
						  <div class="clearfix">
							  <div class="">
								  <div class="control-group">
								      <label class="control-label" for="inputEmail">作业标题</label>
								      <div class="controls">
								        <input type="text" id="title" name="homework.title" datatype="*">
								        
								      </div>
								  </div>
								   <div class="control-group">
								      <label class="control-label" for="inputEmail">作业内容（要求）</label>
								      <div class="controls">
								      <textarea name= "homework.content"  rows= "5 " style="width:90%" datatype="*"></textarea>
								      </div>
								  </div>
								   <div class="control-group">
								      <label class="control-label" for="inputEmail">是否大作业</label>
								      <div class="controls">
								      <select name="homework.isEndWork">
								       <option value=false>否</option>
								      <option value=true>是</option>
								     
								      </select>
								      </div>
								  </div>
								    <div class="control-group">
								      <label class="control-label" for="inputEmail">作业提交截止日期</label>
								      <div class="controls">
								        <input type="text" id="endDate" name="homework.endTime" datatype="*">
								      </div>
								  </div>
								  
							  </div>
							  
						
						  </div>
						  <div class="cca-tr"><button id="submit" class="btn btn-success" type="submit">确定</button> </div>
					  </form>
					  <div class="xxxviewinfo">
					
					<s:iterator value='lstHw' id="hw" >
					<div class="mod">
						  <div class="hd">
							  <img class="img-polaroid" src="img/u.gif" width="62" alt="作业">
						  </div>
						  <div class="bd">
							  <h3>标题：<s:property value="#hw.title"/></h3>
							  <h4 style="padding:4x 10px" class="label label-important">是否大作业 ：<s:if test="%{#hw.isEndWork==1}">是</s:if><s:else>否</s:else></h4>
							  <p>作业要求：<s:property value="#hw.content"/>  </p>	
							<div class="info-date"><a href="teacher_studentHomeworkList.action?id=<s:property value="#hw.id"/>&coursedesignId=<s:property value="#hw.coursedesign.id"/>">查看学生作业情况</a></div>
							<span style="cursor:pointer;position:absolute;top:10px;right:10px;" class="xx" >&times;<input type="hidden" value="<s:property value="#hw.id"/>"/></span>
							
							
						  </div>
					  </div>
						</s:iterator>
					  
					 
					  </div>
				  </div>
			  </div>
		  </div>
  </body>
</html>