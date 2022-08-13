/**
 * Created by thanosYao on 2015/7/15.
 */
$(document).ready(function(){
    init();
});

function init() {
    $.ajax({
        type : "post",
        dataType : "json",
        url : "../teacher/trg_registerInit.action",
        success : function(data) {
            var Result = data.Result;
            if(Result == "OK"){
                records = data.Records;
                var nation     = records[0].lstEthnics;
                var jobDuty    = records[1].lstJobDuties;
                var jobTitle   = records[2].lstJobTitles;
                var background = records[3].lstBackgrounds;
                var politic    = records[4].lstPolitics;
                var zhLang     = records[5].lstChineseLanguageLevels;
                var grade      = records[6].lstGrades;
                var subject    = records[7].lstteacTeachingSubjectExs;
                var language   = records[8].lstLanguages;
                var area       = records[9].area;

                $("#jobDuty").html("");
                $("#jobDuty").append($("<option value=\"-1\">－请选择－</option>"));
                for(var i=0; i<jobDuty.length; i++){
                    $("#jobDuty").append($("<option value=\""+jobDuty[i].jobDutyId+"\">"+jobDuty[i].jobDutyName+"</option>"));
                }

                $("#jobTitle").html("");
                $("#jobTitle").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<jobTitle.length; i++){
                    $("#jobTitle").append($("<option value=\""+jobTitle[i].jobTitleId+"\">"+jobTitle[i].jobTitleName+"</option>"));
                }

                $("#nation").html("");
                $("#nation").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<nation.length; i++){
                    $("#nation").append($("<option value=\""+nation[i].ethnicId+"\">"+nation[i].ethnicName+"</option>"));
                }

                $("#background").html("");
                $("#background").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<background.length; i++){
                    $("#background").append($("<option value=\""+background[i].eBackgroudId+"\">"+background[i].eBackgroudName+"</option>"));
                }

                $("#politic").html("");
                $("#politic").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<politic.length; i++){
                    $("#politic").append($("<option value=\""+politic[i].politicsId+"\">"+politic[i].politicsName+"</option>"));
                }

                $("#zhLang").html("");
                $("#zhLang").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<zhLang.length; i++){
                    $("#zhLang").append($("<option value=\""+zhLang[i].cLanguageLevelId+"\">"+zhLang[i].cLanguageLevelName+"</option>"));
                }

                $("#subject").html("");
                $("#subject").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<subject.length; i++){
                    $("#subject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }

                $("#otherSubject").html("");
                $("#otherSubject").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<subject.length; i++){
                    $("#otherSubject").append($("<option value=\""+subject[i].subjectId+"\">"+subject[i].subjectName+"</option>"));
                }

                $("#language").html("");
                $("#language").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<language.length; i++){
                    $("#language").append($("<option value=\""+language[i].languageId+"\">"+language[i].languageName+"</option>"));
                }

                $("#otherLanguage").html("");
                $("#otherLanguage").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<language.length; i++){
                    $("#otherLanguage").append($("<option value=\""+language[i].languageId+"\">"+language[i].languageName+"</option>"));
                }

                $("#grade").html("");
                $("#grade").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<grade.length; i++){
                    $("#grade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }

                $("#otherGrade").html("");
                $("#otherGrade").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<grade.length; i++){
                    $("#otherGrade").append($("<option value=\""+grade[i].gradeId+"\">"+grade[i].gradeName+"</option>"));
                }

                $("#province").html("");
                $("#province").append($("<option value=\"0\">－请选择－</option>"));
                for(var i=0; i<area.length; i++){
                    var province = area[i].lstProvince
                    for(var i=0; i<province.length; i++){
                        $("#province").append($("<option value=\""+province[i].provinceId+"\">"+province[i].provinceName+"</option>"));
                    }
                }
            }
            else{
                console.log(data.Message);
            }
        }
    })

}
$("#form1").submit(function(){
    $.ajax({
        url: "../teacher/trg_register.action",
        method: "POST",
        data: {
            name: $("#inputName").val(),
            idCard: $("#inputIdCard").val(),
            email: $("#inputEmail").val(),
            mobile:$("#inputMobile").val(),
            code:$("#inputVeriyCode").val(),
            step:"first"
        },
        success : function(data) {
            console.log(data.Step1[0].Message);
            $(".errorInfo").hide();
            if (data.Step1[0].Result == "OK") {
                $("span[name='box1Error']").hide();
                $(".box1").hide();
                $(".box2").show();
                $(".progress-bar").css("width","40%");
                $("#statusIcon1").attr("src","../images/signup/iconSuccess.png");
                $("#statusText1").addClass("successStatusText");
            }
            else {
                for(var i = 0; i < data.Step1.length; i++){
                    switch(data.Step1[i].Result){
                        case "ERRORIDCARD":
                            $("#inputIdCardError")[0].innerHTML=data.Step1[i].Message;
                            $("#inputIdCardError").show();
                            break;
                        case "ERROREMAIL":
                            $("#inputEmailError")[0].innerHTML=data.Step1[i].Message;
                            $("#inputEmailError").show();
                            break;
                        case "ERRORMOBILE":
                            $("#inputMobileError")[0].innerHTML=data.Step1[i].Message;
                            $("#inputMobileError").show();
                            break;
                        case "ERRORCODE":
                            $("#inputVeriyCodeError")[0].innerHTML=data.Step1[i].Message;
                            $("#inputVeriyCodeError").show();
                            break;
                        default:
                            console.log(data);
                    }
                }
            }
        }
    })
    return false;
    event.preventDefault();
})
$(".btn2").click(function(){
    if($("#organization").val()=="") {
        $("#organizationError").show();
        return false;
    }
    else{
        $("#organizationError").hide();
    }
    if($("#jobDuty").val()=="-1") {
        $("#jobDutyError").show();
        return false;
    }
    else{
        $("#jobDutyError").hide();
    }
    if($("#jobTitle").val()=="0") {
        $("#jobTitleError").show();
        return false;
    }
    else{
        $("#jobTitleError").hide();
    }
    if($("#nation").val()=="0") {
        $("#nationError").show();
        return false;
    }
    else{
        $("#nationError").hide();
    }
    if($("#background").val()=="0") {
        $("#backgroundError").show();
        return false;
    }
    else{
        $("#backgroundError").hide();
    }
    if($("#politic").val()=="0") {
        $("#politicError").show();
        return false;
    }
    else{
        $("#politicError").hide();
    }
    if($("#zhLang").val()=="0") {
        $("#zhLangError").show();
        return false;
    }
    else{
        $("#zhLangError").hide();
    }
    if($("select[name='provinceId']").val()=="0") {
        $("#locationError").show();
        return false;
    }
    else{
        $("#locationError").hide();
    }
    if($("select[name='cityId']").val()=="0") {
        $("#locationError").show();
        return false;
    }
    else{
        $("#locationError").hide();
    }
    if($("select[name='countyId']")=="0") {
        $("#locationError").show();
        return false;
    }
    else{
        $("#locationError").hide();
    }
    $.ajax({
        url: "../teacher/trg_register.action",
        method: "POST",
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
            countyId:$("select[name='countyId']").val(),
            step:"second"
        },
        success : function(data) {
            console.log(data.Step2.Message);
            if (data.Step2.Result == "OK") {
                $(".box2").hide();
                $(".box3").show();
                $(".progress-bar").css("width","60%");
                $("#statusIcon2").attr("src","../images/signup/iconSuccess.png");
                $("#statusText2").addClass("successStatusText");
                $("span[name='box2Error']").hide();
            }
            else {
                $(".modal-body").html(data.Step2.Message);
            	$('#myModal').modal('show');
            }
        }
    })
    return false;
    event.preventDefault();
})
$(".btn3").click(function(){
    if($("#teachingAge").val()=="") {
        $("#teachingAgeError").show();
        return false;
    }
    else{
        $("#teachingAgeError").hide();
    }
    if($("#grade").val()=="0") {
        $("#gradeError").show();
        return false;
    }
    else{
        $("#gradeError").hide();
    }
    if($("#subject").val()=="0") {
        $("#subjectError").show();
        return false;
    }
    else{
        $("#subjectError").hide();
    }
    if($("#language").val()=="0") {
        $("#languageError").show();
        return false;
    }
    else{
        $("#languageError").hide();
    }
    $.ajax({
        url: "../teacher/trg_register.action",
        method: "POST",
        data: {
            teachingAge:$("#teachingAge").val(),
            mainTeachingGrades:$("#grade").val(),
            mainTeachingSubject:$("#subject").val(),
            mainTeachingLanguage:$("#language").val(),
            multiLanguage:$('input:radio:checked').val(),
            unMainTeachingGrades:$("#otherGrade").val(),
            unMainTeachingSubject:$("#otherSubject").val(),
            unMainTeachingLanguage:$("#otherLanguage").val(),
            step:"third"

        },
        success : function(data) {
            console.log(data.Step3.Message);
            if (data.Step3.Result == "OK") {
                $(".box3").hide();
                $(".box4").show();
                $(".progress-bar").css("width","80%");
                $("#statusIcon3").attr("src","../images/signup/iconSuccess.png");
                $("#statusText3").addClass("successStatusText");
            }
            else {
                $(".modal-body").html(data.Step3.Message);
            	$('#myModal').modal('show');
            }
        }
    })
    return false;
    event.preventDefault();
})
$(".btn4").click(function(){
    if($("#inputPassword").val().length<6) {
        $("#PasswordError1").show();
        return false;
    }
    else{
        $("#PasswordError1").hide();
    }
    if($("#inputPassword").val() !== $("#inputRepeatPassword").val()) {
        $("#PasswordError2").show();
        return false;
    }
    else{
        $("#PasswordError2").hide();
    }

    $.ajax({
        url: "../teacher/trg_regist.action",
        method: "POST",
        data: {
            name: $("#inputName").val(),
            idCard: $("#inputIdCard").val(),
            email: $("#inputEmail").val(),
            mobile:$("#inputMobile").val(),
            code:$("#inputVeriyCode").val(),

            organization: $("#organization").val(),
            jobDuty: $("#jobDuty").val(),
            jobTitle: $("#jobTitle").val(),
            ethnic: $("#nation").val(),
            eductionBackground: $("#background").val(),
            politics: $("#politic").val(),
            chineseLanguageLevel: $("#zhLang").val(),
            provinceId:$("select[name='provinceId']").val(),
            cityId:$("select[name='cityId']").val(),
            countyId:$("select[name='countyId']").val(),

            teachingAge:$("#teachingAge").val(),
            mainTeachingGrades:$("#grade").val(),
            mainTeachingSubject:$("#subject").val(),
            mainTeachingLanguage:$("#language").val(),
            multiLanguage:$('input:radio:checked').val(),
            unMainTeachingGrades:$("#otherGrade").val(),
            unMainTeachingSubject:$("#otherSubject").val(),
            unMainTeachingLanguage:$("#otherLanguage").val(),
            password:$("#inputPassword").val(),
            checkPWD:$("#inputRepeatPassword").val(),
        },
        success : function(data) {
            if (data.Result == "OK") {
                $(".box4").hide();
                $.ajax({
                    url: "../teacher/trg_review.action",
                    method: "POST",
                    success: function (data){
                        console.log(data.Result);
                        for(var i=0;i<data.Records.length;i++){
                            $("tbody").append( "<tr><th scope='row'>"+data.Records[i].name+"</th><td>"
                                +data.Records[i].organization+"</td><td>"+data.Records[i].department+"</td><td>"
                                +data.Records[i].jobDuty+"</td><td>"+data.Records[i].phone+"</td><td>"+data.Records[i].mobile
                                +"</tr>" );
                        }
                    }
                })
                $(".box5").show();
                $(".progress-bar").css("width","100%");
                $("#statusIcon4").attr("src","../images/signup/iconSuccess.png");
                $("#statusText4").addClass("successStatusText");
            }
            else {
                $(".modal-body").html(data.Message);
            	$('#myModal').modal('show');
            }
        }
    })
    return false;
    event.preventDefault();
})
$(".btn5").click(function(){
    window.location.href="login.html";
})
$(".pre2").click(function(){
    $(".box2").hide();
    $(".box1").show();
    $(".progress-bar").css("width","20%");
    $("#statusIcon2").attr("src","../images/signup/icon2.png");
    $("#statusText2").removeClass("successStatusText");
})
$(".pre3").click(function(){
    $(".box3").hide();
    $(".box2").show();
    $(".progress-bar").css("width","40%");
    $("#statusIcon3").attr("src","../images/signup/icon3.png");
    $("#statusText3").removeClass("successStatusText");
})
$(".pre4").click(function(){
    $(".box4").hide();
    $(".box3").show();
    $(".progress-bar").css("width","60%");
    $("#statusIcon4").attr("src","../images/signup/icon4.png");
    $("#statusText4").removeClass("successStatusText");
})


//所在学校初始化
$(function() {
    $("#organization").select2({
        placeholder : "请输入学校名称或拼音首字母",
        minimumInputLength : 1,
        quietMillis : 1000,
        allowClear : true,
        ajax : {
            url : "../teacher/trg_searchSchool.action",
            dataType : 'json',
            data : function(term, page) {
                return {
                    search : term, // search term
                    page_limit : 10
                };
            },
            results : function(data, page) {
                return {
                    results : data.Options
                };

            }
        },

        initSelection : function(element, callback) {
            element = $(element);
            var data = {
                id : element.val(),
                DisplayText : element.attr('data-name')
            };
            callback(data);
        },
        formatResult : movieFormatResult,
        formatSelection : movieFormatSelection,
        dropdownCssClass : "bigdrop",
        escapeMarkup : function(m) {
            return m;
        }
    })

    function movieFormatResult(Options) {
        var html = Options.DisplayText;
        return html;

    }
    function movieFormatSelection(Options) {
        return Options.DisplayText;
    }

    //datapicker
    $('.datepicker').datepicker();
    $('#otherGrade,#otherSubject,#otherLanguage').select2({
        allowClear: true
    });
})

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

//verify
function verifyIdCard(value){
    var url = "../teacher/trg_register.action?idCard" + value;
    $.getJSON(url,function(data){
        console.log(data);
    })
}

$('.datepicker').datepicker({
    language : "zh-CN",
    format : 'yyyy-mm-dd',
    startView : 2,
    endDate : '1d',
    autoclose : true
});
var i = 60;
function send(){
    i--;
    if (i == -1) {
        document.getElementById("sendCodeBtn").disabled = "";
        i = 60;
        document.getElementById("sendCodeBtn").value = "重新发送";
        return null;
    }
    document.getElementById("sendCodeBtn").disabled = "disabled";
    document.getElementById("sendCodeBtn").value = i + "秒后重发";
    t = setTimeout("send();", 1000);
}
$("#sendCodeBtn").click(function(){
    var smsCode = $("#inputMobile").val();
    if(!(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(smsCode))){
        $("#inputMobileError").show();
        return false;
    }else{
        $("#inputMobileError").hide();
    }
    send();
    $.ajax({
        url: "../teacher/trg_sendSms.action",
        method: "POST",
        data: {
            phone:smsCode,
        },
        success : function(data){
            if(data.Result == "OK"){
                console.log("smsCode send success!");
            }
            else{
                clearTimeout(t);
                document.getElementById("sendCodeBtn").value = "发送验证码";
                document.getElementById("sendCodeBtn").disabled = "";
                i = 60;

                $("#inputMobileError")[0].innerHTML = data.Message;
                $("#inputMobileError").show();
            }
        }
    })
})