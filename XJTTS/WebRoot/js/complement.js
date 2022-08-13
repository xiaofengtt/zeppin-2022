/**
 * Created by thanosYao on 2015/7/23.
 */
$(document).ready(function(){
    init();
    getAvatar();
    $('[data-toggle="tooltip"]').tooltip();
});
//$("#header").load("header.html");
$("#footer").load("footer.html");
function init() {
    
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_initCheckTeacherInfo.action",
        success: function (data) {
            if(data.Result == "OK"){
                var jsonDate = JSON.stringify(data);
                var records = data.Records;
                var area     = records[1].options[0].area;
                var nation     = records[1].options[1].lstEthnics;
                var jobDuty    = records[1].options[2].lstJobDuties;
                var jobTitle   = records[1].options[3].lstJobTitles;
                var background = records[1].options[4].lstBackgrounds;
                var politic    = records[1].options[5].lstPolitics;
                var zhLang     = records[1].options[6].lstChineseLanguageLevels;
                userName.innerHTML     = data.Records[0].teacher[0].name;
                idName.innerHTML     = data.Records[0].teacher[0].name;
                idCard.innerHTML    = data.Records[0].teacher[0].idCard;
                $("#email").val(data.Records[0].teacher[0].email);
                $("#mobile").val(data.Records[0].teacher[0].mobile);
                $("#graduation").val(data.Records[0].teacher[0].graduation);
                $("#major").val(data.Records[0].teacher[0].major);
//                email.innerHTML   = data.Records[0].teacher[0].email;
//                mobile.innerHTML = data.Records[0].teacher[0].mobile;
                $("#organization").html(data.Records[0].teacher[0].organization.name);

                $("#jobDuty").html("");
                $("#jobDuty").append($("<option value="+data.Records[0].teacher[0].jobDuty.id+">"+data.Records[0].teacher[0].jobDuty.name+"</option>"));
                for(var i=0; i<jobDuty.length; i++){
                    if (jobDuty[i].jobDutyId==data.Records[0].teacher[0].jobDuty.id) continue;
                    $("#jobDuty").append($("<option value=\""+jobDuty[i].jobDutyId+"\">"+jobDuty[i].jobDutyName+"</option>"));
                }
                $("#jobTitle").html("");
                $("#jobTitle").append($("<option value="+data.Records[0].teacher[0].jobTitle.id+">"+data.Records[0].teacher[0].jobTitle.name+"</option>"));
                for(var i=0; i<jobTitle.length; i++){
                    if(data.Records[0].teacher[0].jobTitle.id==jobTitle[i].jobTitleId) continue;
                    $("#jobTitle").append($("<option value=\""+jobTitle[i].jobTitleId+"\">"+jobTitle[i].jobTitleName+"</option>"));
                }
                $("#nation").html("");
                $("#nation").append($("<option value="+data.Records[0].teacher[0].ethnic.id+">"+data.Records[0].teacher[0].ethnic.name+"</option>"));
                for(var i=0; i<nation.length; i++){
                    if(data.Records[0].teacher[0].ethnic.id==nation[i].ethnicId)continue;
                    $("#nation").append($("<option value=\""+nation[i].ethnicId+"\">"+nation[i].ethnicName+"</option>"));
                }

                $("#background").html("");
                $("#background").append($("<option value="+data.Records[0].teacher[0].eductionBackground.id+">"+data.Records[0].teacher[0].eductionBackground.name+"</option>"));
                for(var i=0; i<background.length; i++){
                    if(data.Records[0].teacher[0].eductionBackground.id==background[i].eBackgroudId)continue;
                    $("#background").append($("<option value=\""+background[i].eBackgroudId+"\">"+background[i].eBackgroudName+"</option>"));
                }

                $("#politic").html("");
                $("#politic").append($("<option value="+data.Records[0].teacher[0].politics.id+">"+data.Records[0].teacher[0].politics.name+"</option>"));
                for(var i=0; i<politic.length; i++){
                    if(data.Records[0].teacher[0].politics.id==politic[i].politicsId)continue;
                    $("#politic").append($("<option value=\""+politic[i].politicsId+"\">"+politic[i].politicsName+"</option>"));
                }

                $("#zhLang").html("");
                $("#zhLang").append($("<option value="+data.Records[0].teacher[0].chineseLanguageLevel.id+">"+data.Records[0].teacher[0].chineseLanguageLevel.name+"</option>"));
                for(var i=0; i<zhLang.length; i++){
                    if(data.Records[0].teacher[0].chineseLanguageLevel.id==zhLang[i].cLanguageLevelId)continue;
                    $("#zhLang").append($("<option value=\""+zhLang[i].cLanguageLevelId+"\">"+zhLang[i].cLanguageLevelName+"</option>"));
                }

                $("#province").html("");
                $("#province").append($("<option value="+records[0].teacher[0].area[0].province.id+">"+records[0].teacher[0].area[0].province.name+"</option>"));
                if(area[0].province.id!==records[0].teacher[0].area[0].province.id)
                    $("#province").append($("<option value="+area[0].province.id+">"+area[0].province.name+"</option>"));

                $("#areacity").html("");
                $("#areacity").append($("<option value="+records[0].teacher[0].area[0].city.id+">"+records[0].teacher[0].area[0].city.name+"</option>"));
                for(var i=0; i<area[1].lstCity.length; i++){
                    if(data.Records[0].teacher[0].area.id==zhLang[i].cLanguageLevelId)continue;
                    $("#areacity").append($("<option value='"+area[1].lstCity[i].cityId+"'>"+area[1].lstCity[i].cityName+"</option>"));
                }

                $("#areacounty").html("");
                $("#areacounty").append($("<option value="+records[0].teacher[0].area[0].country.id+">"+records[0].teacher[0].area[0].country.name+"</option>"));

                $(".basicInfo").toggleClass("hide");
                $(".basicInfoModify").toggleClass("hide");
                
                var grade      = records[1].options[7].lstGrades;
                var subject    = records[1].options[8].lstteacTeachingSubjectExs;
                var language   = records[1].options[9].lstLanguages;

                $("#teachingAge").val(records[0].teacher[0].teachingAge);

                $("#grade").html("");
                $("#grade").append($("<option value="+records[0].teacher[0].mainTeachingGrades.id+">"+records[0].teacher[0].mainTeachingGrades.name+"</option>"));
                for(var i=0; i<grade.length; i++){
                    if(records[0].teacher[0].mainTeachingGrades.id==grade[i].gradeId) continue;
                    $("#grade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }
                $("#otherGrade").html("");
                for(var i=0; i<grade.length; i++){
                    for(var j=0; j<records[0].teacher[0].unMainTeachingGrades.length;j++){
                        if(records[0].teacher[0].unMainTeachingGrades[j].id == grade[i].gradeId){
                            $("#otherGrade").append($("<option selected='selected' value="+grade[i].gradeId+">"+grade[i].gradeName+"</option>"));
                        }
                    }
                    $("#otherGrade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }
                $('#otherGrade').select2();
                $("#subject").html("");
                $("#subject").append($("<option value="+records[0].teacher[0].mainTeachingSubject.id+">"+records[0].teacher[0].mainTeachingSubject.name+"</option>"));
                for(var i=0; i<subject.length; i++){
                    if(records[0].teacher[0].mainTeachingSubject.id==subject[i].subjectId) continue;
                    $("#subject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }
                $("#othersubject").html("");
                for(var i=0; i<subject.length; i++){
                    for(var j=0; j<records[0].teacher[0].unMainTeachingSubject.length;j++){
                        if(records[0].teacher[0].unMainTeachingSubject[j].id == subject[i].subjectId){
                            $("#otherSubject").append($("<option selected='selected' value="+subject[i].subjectId+">"+subject[i].subjectName+"</option>"));
                        }
                    }
                    $("#otherSubject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }
                $('#otherSubject').select2();
                $("#language").html("");
                $("#language").append($("<option value="+records[0].teacher[0].mainTeachingLanguage.id+">"+records[0].teacher[0].mainTeachingLanguage.name+"</option>"));
                for(var i=0; i<language.length; i++){
                    if(records[0].teacher[0].mainTeachingLanguage.id==language[i].languageId) continue;
                    $("#language").append($("<option value=\""+language[i].languageId+"\">"+language[i].languageName+"</option>"));
                }
                $("#otherlanguage").html("");
                for(var i=0; i<language.length; i++){
                    for(var j=0; j<records[0].teacher[0].unMainTeachingLanguage.length;j++){
                        if(records[0].teacher[0].unMainTeachingLanguage[j].id == language[i].languageId){
                            $("#otherLanguage").append($("<option selected='selected' value="+language[i].languageId+">"+language[i].languageName+"</option>"));
                        }
                    }
                    $("#otherLanguage").append($("<option value=\""+language[i].languageId+"\">"+language[i].languageName+"</option>"));
                }
                $('#otherLanguage').select2();

                $(".techInfoText").toggleClass("hide");
                $(".techInfo").toggleClass("hide");
            }
            else {
                console.log(data.Message);
                window.location.href="login.html";
            }
        }
    })
}
function getAvatar(){
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_getHeadImg.action",
        success: function (data) {
            if(data.Result == "OK"){
                if(data.Records.headImgPath == '0'){
                    $("#bigAvatar").attr("src","../images/userInfomation/default.png");
                }
                else{
                    $("#bigAvatar").attr("src",data.Records.headImgPath);
                }
            }
            else{
            	$("#bigAvatar").attr("src","../images/userInfomation/default.png");
            }
        }
    })
}

$('.quxiao').click(function(){
	window.location.href="login.html";
})

$(".saveBtn3").click(function () {
//	function check(){
//		var a = /^(\d{4})-(\d{2})-(\d{2})$/
//		if (!a.test(document.getElementById("TextBox1").value)) { 
//		alert("日期格式不正确!") 
//		return false 
//		} 
//		else 
//		return true 
//	}
    if($("#teachingAge").val()=="") {
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
        $("#teachingAgeError").show();
        return false;
    }
    var a = /^(\d{4})-(\d{2})-(\d{2})$/;
    var teacherAgestr = $("#teachingAge").val();
//    var teacherAgestr = '2009';
    if(!a.test(teacherAgestr)){
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
    	$("#teachingAgeError").html('时间格式错误（正确格式:yyyy-MM-dd）')
        $("#teachingAgeError").show();
        return false;
    }
    
    if($("#email").val() == ''){
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
    	$("#teachingAgeError").hide();
    	$("#inputEmailError").show();
    	return false;
    }
    if($("#mobile").val() == ''){
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
    	$("#teachingAgeError").hide();
    	$("#inputMobileError").show();
    	return false;
    }
    if($("#graduation").val() == ''){
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
    	$("#teachingAgeError").hide();
    	$("#inputGraduationError").show();
    	return false;
    }
    if($("#major").val() == ''){
    	$("#inputEmailError").hide();
    	$("#inputMobileError").hide();
    	$("#inputMajorError").hide();
    	$("#inputGraduationError").hide();
    	$("#teachingAgeError").hide();
    	$("#inputMajorError").show();
    	return false;
    }
    if($("#grade").val()=="-2") {
        $("#gradeError").show();
        return false;
    }
    else{
        $("#gradeError").hide();
    }
    if($("#subject").val()=="-2") {
        $("#subjectError").show();
        return false;
    }
    else{
        $("#subjectError").hide();
    }
    if($("#language").val()=="-2") {
        $("#languageError").show();
        return false;
    }
    else{
        $("#languageError").hide();
    }
    $.ajax({
        type : "post",
        url: "../teacher/tinfo_saveCheckTeacherInfo.action",
        data: {
            teachingAge:$("#teachingAge").val(),
            mainTeachingGrades:$("#grade").val(),
            mainTeachingSubject:$("#subject").val(),
            mainTeachingLanguage:$("#language").val(),
            multiLanguage:$('input:radio:checked').val(),
            unMainTeachingGrades:$("#otherGrade").val(),
            unMainTeachingSubject:$("#otherSubject").val(),
            unMainTeachingLanguage:$("#otherLanguage").val(),
            email: $("#email").val(),
            mobile:$("#mobile").val(),
            graduation: $("#graduation").val(),
            major:$("#major").val(),
            organization: $("#organization").val(),
            jobDuty: $("#jobDuty").val(),
            jobTitle: $("#jobTitle").val(),
            ethnic: $("#nation").val(),
            eductionBackground: $("#background").val(),
            politics: $("#politic").val(),
            chineseLanguageLevel: $("#zhLang").val(),
            provinceId:$("select[name='provinceId']").val(),
            cityId:$("select[name='cityId']").val(),
            countyId:$("select[name='countyId']").val()
//        	teachingAge:'2015-01-28',
//        	mainTeachingGrades:3,
//        	mainTeachingSubject:3,
//        	mainTeachingLanguage:1,
//        	multiLanguage:1,
//        	unMainTeachingGrades:'',
//        	unMainTeachingSubject:'',
//        	unMainTeachingLanguage:'',
//        	email:'123@123.com',
//        	mobile:'13595109957',
//        	graduation:'阿克苏教育学院',
//        	major:'11111',
//        	organization:'',
//        	jobDuty:28,
//        	jobTitle:32,
//        	ethnic:5,
//        	eductionBackground:5,
//        	politics:1,
//        	chineseLanguageLevel:24,
//        	provinceId:32,
//        	cityId:373,
//        	countyId:16161
        },
        success: function (data) {
            if(data.Result == "OK"){
            	alert('保存成功');
            	window.location.href="personal.html";
            }
            else{
                if(data.Result == "ERROREMAIL"){
                	$("#inputEmailError").hide();
                	$("#inputMobileError").hide();
                	$("#inputMajorError").hide();
                	$("#inputGraduationError").hide();
                	$("#inputEmailError")[0].innerHTML=data.Message;
                    $("#inputEmailError").show();
                }else if(data.Result == "ERRORMOBILE"){
                	$("#inputEmailError").hide();
                	$("#inputMobileError").hide();
                	$("#inputMajorError").hide();
                	$("#inputGraduationError").hide();
                	$("#inputMobileError")[0].innerHTML=data.Message;
                    $("#inputMobileError").show();
                }else if(data.Result == "ERRORMAJOR"){
                	$("#inputEmailError").hide();
                	$("#inputMobileError").hide();
                	$("#inputMajorError").hide();
                	$("#inputGraduationError").hide();
                	$("#inputMajorError")[0].innerHTML=data.Message;
                    $("#inputMajorError").show();
                }else if(data.Result == "ERRORGRADUATION"){
                	$("#inputEmailError").hide();
                	$("#inputMobileError").hide();
                	$("#inputMajorError").hide();
                	$("#inputGraduationError").hide();
                	$("#inputGraduationError")[0].innerHTML=data.Message;
                    $("#inputGraduationError").show();
                }
            }
        }
    })
})
//所在学校初始化
$(function() {

    function movieFormatResult(Options) {
        var html = Options.DisplayText;
        return html;

    }
    function movieFormatSelection(Options) {
        return Options.DisplayText;
    }
    //datapicker
    $('.datepicker').datepicker();
})
$('.datepicker').datepicker({
    language : "zh-CN",
    format : 'yyyy-mm-dd',
    startView : 2,
    endDate : '1d',
    autoclose : true
});
// areacity 所属地区 添加项目管理员
function areacityy(t, name) {
    var id = $(t).val();
    name = (typeof (name) == 'undefined') ? 'city' : name;
    var url = '../base/area_getArea.action?' + name + 'Id=' + id;
    $.getJSON(url, function(data) {
        var cLis = '';

        if (data.Result == 'OK') {
            var cLis = '';
            $.each(data.Options, function(i, c) {
                cLis += '<option value="' + c.Value + '">' + c.DisplayText
                    + '</option>';
            });
            if (name == 'city') {
                var cList2 = '<option value="0">请选择...</option>';
                $('#areacounty').html(cList2);
            }
            $('#area' + name).html(cLis);
        }
    })
}