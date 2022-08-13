<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>学生成绩录入</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/score/ajax/ajax.js"></script>
		<script type="text/javascript" src="/score/inputcheck.js"></script>
		<script type="text/javascript">
		Ext.onReady(function(){
			 var courseExamRoomDataStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
				<s:if test='#session.flag.equals("a")'>
		            url: "/test/myList.action?sql=select distinct peTchCourse.Id||'-'||peExamRoom.Id id,peTchCourse.Name||'__'||peSite.Name||'__'||peExamNo.Name||'__'||peExamRoom.Classroom name from pe_site peSite,pe_tch_course peTchCourse, pe_exam_no peExamNo, pe_exam_room peExamRoom, pr_exam_booking prExamBooking, pe_semester peSemester, pr_tch_stu_elective prTchStuElective, pr_tch_opencourse prTchOpencourse where peSemester.Flag_Active = '1' and peExamNo.Fk_Semester_Id = peSemester.Id and prExamBooking.Fk_Tch_Stu_Elective_Id = prTchStuElective.Id and prTchStuElective.Fk_Tch_Opencourse_Id = prTchOpencourse.Id and prTchOpencourse.Fk_Course_Id = peTchCourse.Id and prExamBooking.Fk_Exam_Room_Id = peExamRoom.Id and peExamRoom.Fk_Site_Id = peSite.Id and peExamRoom.Fk_Exam_No = peExamNo.Id and prExamBooking.Score_Exam_a is null and peTchCourse.Fk_Exam_Score_Input_Usera_Id='<s:property value="#session.user.id"/>' order by name"
		        </s:if><s:else>
		        	url: "/test/myList.action?sql=select distinct peTchCourse.Id||'-'||peExamRoom.Id id,peTchCourse.Name||'__'||peSite.Name||'__'||peExamNo.Name||'__'||peExamRoom.Classroom name from pe_site peSite,pe_tch_course peTchCourse, pe_exam_no peExamNo, pe_exam_room peExamRoom, pr_exam_booking prExamBooking, pe_semester peSemester, pr_tch_stu_elective prTchStuElective, pr_tch_opencourse prTchOpencourse where peSemester.Flag_Active = '1' and peExamNo.Fk_Semester_Id = peSemester.Id and prExamBooking.Fk_Tch_Stu_Elective_Id = prTchStuElective.Id and prTchStuElective.Fk_Tch_Opencourse_Id = prTchOpencourse.Id and prTchOpencourse.Fk_Course_Id = peTchCourse.Id and prExamBooking.Fk_Exam_Room_Id = peExamRoom.Id and peExamRoom.Fk_Site_Id = peSite.Id and peExamRoom.Fk_Exam_No = peExamNo.Id and prExamBooking.Score_Exam_b is null and peTchCourse.Fk_Exam_Score_Input_Userb_Id='<s:property value="#session.user.id"/>' order by name"
		        </s:else>
		        }),
		
				reader: new Ext.data.JsonReader({
				            root: 'models'
				        },
				        [{name:'id'}, {name:'name'}]),
		        remoteSort: true
		    });
		    courseExamRoomDataStore.load();
		    
		    var courseExamRoomCombo = new Ext.form.ComboBox({
				store: courseExamRoomDataStore,
				hiddenName:'courseExamRoom',
				displayField:'name',
				valueField: 'id' ,
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'',
				applyTo: 'combo-box-courseExamRoom',
				editable: true,
				forceSelection: true,	
				selectOnFocus:true,
				width:800
			});
			
			courseExamRoomCombo.on('select', function() {
				document.getElementById('combo-box-courseExamRoom-id').value=courseExamRoomCombo.getValue();
				var names = courseExamRoomCombo.getRawValue();
				var course = names.substr(0,names.indexOf('__'));
				var names2 = names.substr(names.indexOf('__')+2,names.length);
				document.getElementById('course').innerHTML='<b>'+course+'</b>';
				var site = names2.substr(0,names2.indexOf('__'));
				names2 = names2.substr(names2.indexOf('__')+2,names2.length);
				document.getElementById('site').innerHTML='<b>'+site+'</b>';
				var examno = names2.substr(0,names2.indexOf('__'));
				names2 = names2.substr(names2.indexOf('__')+2,names2.length);
				document.getElementById('examno').innerHTML='<b>'+examno+'</b>';
				document.getElementById('examroom').innerHTML='<b>'+names2+'</b>';
				submitRequest(document.getstulist,"post","text",ajaxgetlist,document.getElementById("scoreinput"));
			});
			courseExamRoomCombo.on('blur', function() {
				Ext.apply(courseExamRoomDataStore.baseParams, {});
			  	courseExamRoomDataStore.load();
			});
			Ext.get('mysubmit').on('click',function(){
				submitRequest(document.scoresubmit,"post","text",ajaxUpdate2,document.getElementById("info"));
				Ext.apply(courseExamRoomDataStore.baseParams, {});
			  	courseExamRoomDataStore.load();
			});
		});	
		
		function ajaxgetlist(count,oDiv){
			submitRequest(document.scoresubmit,"post","text",ajaxUpdate1,document.getElementById("info"));
			oDiv.innerHTML = count;
			var next = window.document.getElementById("sc1_0");
			if(next!=null&&next!=undefined){
		 		next.focus();
		 		next.select();
		 	}
		}
		function ajaxUpdate1(count,oDiv){
		}
		function ajaxUpdate2(count,oDiv){
			document.getElementById("info").innerHTML = "<font color='red'>"+count+"</font><font color='blue'>保存成功</font>";
			window.document.getElementById("combo-box-courseExamRoom").focus();
		}	
		</script>
	</head>
	<body>
		<div id="main_content">
			<div class="content_title">选择考试信息</div>
			<div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%" class="postFormBox" >
			   		<tr class="postFormBox" >
			   			<td align="left" nowrap="nowrap" colspan="2">
			   			<input type="text" id="combo-box-courseExamRoom" />
			   <form name="getstulist" action="/score/input/scoreinput_getstulist.action">
			   		<input type="hidden" name="courseExamRoom" id="combo-box-courseExamRoom-id"/>
			   </form>
			   			</td>		   			
			   		</tr>
			   		<tr class="postFormBox" >
			   			<td align="left" nowrap="nowrap"><span class="name"><label>课程:</label></span>&nbsp;<span id="course"></span></td>
			   			<td align="left" nowrap="nowrap"><span class="name"><label>考试服务站:</label></span>&nbsp;<span id="site"></span></td>
			   		</tr>
			   		<tr>
			   			<td align="left" nowrap="nowrap"><span class="name"><label>考试场次:</label></span>&nbsp;<span id="examno"></span></td>
			   			<td align="left" nowrap="nowrap"><span class="name"><label>考试教室:</label></span>&nbsp;<span id="examroom"></span></td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   <div class="content_title">成绩录入(/:缺考，*：违纪，-：作弊，+：正常)</div>
			<div class="cntent_k" align="center">
		   	  <form name="scoresubmit" action="/score/input/scoreinput_updateScore.action">
			   <div class="k_cc" id="scoreinput">
			   </div>
	   		   <div align="center"><input type="button" id="mysubmit" value="提交" /></div>
		   	  </form>
		   </div>
		   </div>
	</body>
</html>