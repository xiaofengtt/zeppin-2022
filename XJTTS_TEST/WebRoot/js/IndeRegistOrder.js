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
})
$(".confirm").click(function(){
    $.ajax({
        method: "POST",
        url: "../teacher/autoenro_signup.action?id=" + window.location.search.slice(4),
        success: function (data) {
            if (data.Result == "OK") {
                swal("报名成功", "", "success");
                setTimeout(function () {
                    swal.close();
                    window.location.href = "IndeRegistEntrance.html";
                }, 2000);
            }else if(data.Result == "REPEAT"){
            	alert(data.Message);
            	window.location.href = "selectCourse.html";
            }
            else {
                alert(data.Message);
            }
        }
    })
})
function goBack() {
    window.history.back();
}