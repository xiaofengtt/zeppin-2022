/**
 * Created by thanosYao on 2015/7/23.
 */
$(document).ready(function(){
    init();
    getAvatar();
    $('[data-toggle="tooltip"]').tooltip();
});
$("#header").load("header.html");
$("#footer").load("footer.html");
function init() {
    $.ajax({
        type: "post",
        dataType: "json",
        url: "../teacher/tinfo_initPage.action",
        success: function (data) {
            var Result = data.Result;
            if (Result == "OK") {
                records = data.Records;
                userName.innerHTML     = records.name;
                idName.innerHTML     = records.name;
                idCard.innerHTML    = records.idCard;
                email.innerHTML   = records.email;
                organizationText.innerHTML = records.organization;
                provinceText.innerHTML = records.teacherArea;
                jobDutyText.innerHTML     = records.jobDuty;
                jobTitleText.innerHTML      = records.jobTitle;
                nationText.innerHTML = records.ethnic;
                backgroundText.innerHTML = records.eductionBackground;
                politicText.innerHTML = records.politics;
                zhLangText.innerHTML = records.chineseLanguageLevel;
                teachingAgeText.innerHTML = records.teachingAge;
                sex.innerHTML = records.sex;
                bilingualText.innerHTML = records.multiLanguage;
                languageText.innerHTML = records.mainTeachingLanguage;
                gradeText.innerHTML = records.mainTeachingGrades;
                subjectText.innerHTML = records.mainTeachingSubject;
                otherLanguageText.innerHTML = records.unMainTeachingLanguage;
                otherGradeText.innerHTML = records.unMainTeachingGrades;
                otherSubjectText.innerHTML = records.unMainTeachingSubject;
            }
            else {
                console.log(data.Message);
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
                console.log(data);
            }
        }
    })
}
$(".editBtn2").click(function () {
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_baseInfoEdit.action",
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
            }
            else {
                console.log(data.Message);
            }
        }
    })
})
$(".editBtn3").click(function () {
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_initTeachInfo.action",
        success: function (data) {
            if(data.Result == "OK"){
                var records = data.Records;
                var grade      = records[1].options[0].lstGrades;
                var subject    = records[1].options[1].lstteacTeachingSubjectExs;
                var language   = records[1].options[2].lstLanguages;

                $("#teachingAge").val(records[0].teacher[0].teachingAge);

                $("#grade").html("");
                $("#grade").append($("<option value="+records[0].teacher[2].mainTeachingGrades.id+">"+records[0].teacher[2].mainTeachingGrades.name+"</option>"));
                for(var i=0; i<grade.length; i++){
                    if(records[0].teacher[2].mainTeachingGrades.id==grade[i].gradeId) continue;
                    $("#grade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }
                $("#otherGrade").html("");
                for(var i=0; i<grade.length; i++){
                    for(var j=0; j<records[0].teacher[3].unMainTeachingGrades.length;j++){
                        if(records[0].teacher[3].unMainTeachingGrades[j].id == grade[i].gradeId){
                            $("#otherGrade").append($("<option selected='selected' value="+grade[i].gradeId+">"+grade[i].gradeName+"</option>"));
                        }
                    }
                    $("#otherGrade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }
                $('#otherGrade').select2();
                $("#subject").html("");
                $("#subject").append($("<option value="+records[0].teacher[4].mainTeachingSubject.id+">"+records[0].teacher[4].mainTeachingSubject.name+"</option>"));
                for(var i=0; i<subject.length; i++){
                    if(records[0].teacher[4].mainTeachingSubject.id==subject[i].subjectId) continue;
                    $("#subject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }
                $("#othersubject").html("");
                for(var i=0; i<subject.length; i++){
                    for(var j=0; j<records[0].teacher[5].unMainTeachingSubject.length;j++){
                        if(records[0].teacher[5].unMainTeachingSubject[j].id == subject[i].subjectId){
                            $("#otherSubject").append($("<option selected='selected' value="+subject[i].subjectId+">"+subject[i].subjectName+"</option>"));
                        }
                    }
                    $("#otherSubject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }
                $('#otherSubject').select2();
                $("#language").html("");
                $("#language").append($("<option value="+records[0].teacher[6].mainTeachingLanguage.id+">"+records[0].teacher[6].mainTeachingLanguage.name+"</option>"));
                for(var i=0; i<language.length; i++){
                    if(records[0].teacher[6].mainTeachingLanguage.id==language[i].languageId) continue;
                    $("#language").append($("<option value=\""+language[i].languageId+"\">"+language[i].languageName+"</option>"));
                }
                $("#otherlanguage").html("");
                for(var i=0; i<language.length; i++){
                    for(var j=0; j<records[0].teacher[7].unMainTeachingLanguage.length;j++){
                        if(records[0].teacher[7].unMainTeachingLanguage[j].id == language[i].languageId){
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
            }
        }
    })
})

$(".saveBtn2").click(function () {
    $.ajax({
        type : "post",
        url: "../teacher/tinfo_saveBaseInfo.action",
        data: {
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
        },
        success: function (data) {
            if(data.Result == "OK"){
                swal("保存成功", "", "success",{showConfirmButton: true});
                setTimeout(function(){
                    swal.close();
                }, 2000);
                organizationText.innerHTML = $("#select2-chosen-1")[0].innerHTML;
                jobDutyText.innerHTML     = $("#jobDuty").find("option:selected").text();
                jobTitleText.innerHTML      = $("#jobTitle").find("option:selected").text();
                nationText.innerHTML = $("#nation").find("option:selected").text();
                backgroundText.innerHTML = $("#background").find("option:selected").text();
                politicText.innerHTML = $("#politic").find("option:selected").text();
                zhLangText.innerHTML = $("#zhLang").find("option:selected").text();
                provinceText.innerHTML = $("#province").find("option:selected").text() + "&gt;&gt;" + $("#areacity").find("option:selected").text() + "&gt;&gt;"+ $("#areacounty").find("option:selected").text();
                $(".basicInfo").toggleClass("hide");
                $(".basicInfoModify").toggleClass("hide");
            }
            else{
                console.log(data.Message);
            }
        }
    })
})

$(".saveBtn3").click(function () {
    if($("#teachingAge").val()=="") {
        $("#teachingAgeError").show();
        return false;
    }
    $.ajax({
        type : "post",
        url: "../teacher/tinfo_saveTeachInfo.action",
        data: {
            teachingAge:$("#teachingAge").val(),
            mainTeachingGrades:$("#grade").val(),
            mainTeachingSubject:$("#subject").val(),
            mainTeachingLanguage:$("#language").val(),
            multiLanguage:$('input:radio:checked').val(),
            unMainTeachingGrades:$("#otherGrade").val(),
            unMainTeachingSubject:$("#otherSubject").val(),
            unMainTeachingLanguage:$("#otherLanguage").val(),
        },
        success: function (data) {
            if(data.Result == "OK"){
                swal("保存成功", "", "success",{showConfirmButton: true});
                setTimeout(function(){
                    swal.close();
                }, 2000);
                teachingAgeText.innerHTML = $("#teachingAge").val();
                gradeText.innerHTML = $("#grade").find("option:selected").text();
                subjectText.innerHTML = $("#subject").find("option:selected").text();
                languageText.innerHTML = $("#language").find("option:selected").text();

                var otherGradeArray = otherGradeInput.getElementsByClassName("select2-search-choice");
                var otherSubjectArray = otherSubjectInput.getElementsByClassName("select2-search-choice");
                var otherLanguageArray = otherLanguageInput.getElementsByClassName("select2-search-choice");
                var otherGradeResult = '';
                var otherSubjectResult = '';
                var otherLanguageResult = '';
                for(var i=0; i<otherGradeArray.length; i++){
                    otherGradeResult += otherGradeArray[i].children[0].innerHTML+"&nbsp";
                }
                for(var i=0; i<otherSubjectArray.length; i++){
                    otherSubjectResult += otherSubjectArray[i].children[0].innerHTML+"&nbsp";
                }
                for(var i=0; i<otherLanguageArray.length; i++){
                    otherLanguageResult += otherLanguageArray[i].children[0].innerHTML+"&nbsp";
                }

                otherGradeText.innerHTML = otherGradeResult;
                otherSubjectText.innerHTML =  otherSubjectResult;
                otherLanguageText.innerHTML =  otherLanguageResult;
                if($('input:radio:checked').val()=="1"){
                    bilingualText.innerHTML = "是";
                }
                else{
                    bilingualText.innerHTML = "否";
                }

                $(".techInfoText").toggleClass("hide");
                $(".techInfo").toggleClass("hide");
            }
            else{
                console.log(data.Message);
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