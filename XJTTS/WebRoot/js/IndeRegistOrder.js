/**
 * Created by thanosYao on 2015/8/5.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$(function(){
    var projectApplyId = window.location.search.slice(4);
    $.ajax({
        method: "POST",
        url: "../teacher/autoenro_initSginUpPage.action?id="+projectApplyId,
        success: function (data){
            if(data.Result=="OK"){
                var msg = data.Records;
                $("#registFeeText").text(msg.funds);
                $("#orderDate").text(msg.orderDate);
                $("#idName").text(msg.teacherName);
                $("#teacherIdCard").text(msg.teacherIdCard);
                $("#subjectName").text(msg.subjectName);
                $("#companyName").text(msg.trainingCollege);
                $("#project").text(msg.projectName);
            }
            else{
                console.log(data);
            }
        }
    })
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	})
})
$(".confirm").click(function(){
	$(".confirm").attr('disabled','disabled');
	var id=(url('?id')!= null) ? url('?id') : '';
	var isAdvance=(url('?isAdvance')!= null) ? url('?isAdvance') : '';
	$.ajax({
        type: "GET",
        url: "../admin/sizer_signupCheckerSizer.action",
        data: {id:id,isAdvance:isAdvance},
        dataType: "text",
        async: true,
        success: function (data) {
        	$(".confirm").removeAttr('disabled');
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
        			  +""+'<p class="tips text-center">该教师“'+json.Message+'”</p>'
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
    			  '</div><div class="clear"></div></div></div><div class="clear"></div><div class="btnGroup text-right">'
    			  +""+'<a class="btn btn-default btn-myself" onclick="layer.closeAll();">关闭</a></div></div>',
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
        		
        	}else if(json.Result=="OK"){
        		originalAddRecords();
        	}else{
        		layer.confirm(json.Message, {
    				btn : [ '确定' ]
    			//按钮
    			}, function() {
    				layer.closeAll();
    			});
        	}
        }
	})
})
function goBack() {
    window.history.back();
}
function originalAddRecords(){
	$.ajax({
      method: "POST",
      url: "../teacher/autoenro_signup.action?id=" + window.location.search.slice(4),
      success: function (data) {
          if (data.Result == "OK") {
        	  layer.closeAll();
              swal("报名成功", "", "success");
              setTimeout(function () {
                  swal.close();
                  window.location.href = "IndeRegistEntrance.html";
              }, 2000);
          }else if(data.Result == "REPEAT"){
          	layer.confirm(data.Message, {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
				window.location.href = "selectCourse.html";
			});
          }
          else {
          	$(".confirm").removeAttr('disabled');
          	layer.confirm(data.Message, {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
          }
      }
  })
}

function addRecordNext(){
	var cUrl="../admin/sizer_signupCheckerSizer.action?id=" + window.location.search.slice(4)+"&isNext=1";
	$.getJSON(cUrl, function(json) {
		if(json.Result=="OK"){
			originalAddRecords();
    	}else if(json.Result=="FAIL"){
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
    		layer.closeAll();
    		layer.confirm(json.Message, {
				btn : [ '确定' ]
			//按钮
			}, function() {
				layer.closeAll();
			});
    	}
	});
}

