/**
 * Created by thanosYao on 2015/7/29.
 */

$(document).ready(function()
{
    $("#header").load("header.html");
    $("#footer").load("footer.html");

    $("#previewupload").uploadFile({
//        url:"demo.json",
    	url:"../teacher/teacherCertificate_upload.action",
        fileName:"myfile",
        acceptFiles:"image/*",
        showPreview:true,
        previewHeight: "100px",
        doneStr: "完成",
        previewWidth: "100px",
        onSuccess: function(e, t, n, r) {
        	if(t.success){
        		$('#certificateModel').attr('data-url',t.sourceUrl);
        	}
        }
    });
});

$(function(){
    $(".certificateList").removeClass("hide");
    $(".footer").removeClass("hide");
    
    $("#saveButton").on('click',function(){
    	var ttrId = $('#certificateModel').attr('data-ttrId');
    	var imageURL = $('#certificateModel').attr('data-url');
		$.getJSON('../teacher/teacherCertificate_addImgCertificate.action?ttrId=' + ttrId + '&imageURL='+imageURL, function(data) {
			
			if(data.Result == 'OK'){
				window.location.href='certificate.html';
			}else{
				alert(data.Message);
			}
			
		});
    });
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
});

angular.module('certificateDirective', []).controller('certificateController',function ($scope,$http) {
	
	$http.get('../teacher/teacherCertificate_getCertificateList.action').success(function(data){
		$scope.funds = data.Records;
		$scope.totalcount = data.TotalCount;
	});
	
})

function upload(e){
	$('#certificateModel').on('shown.bs.modal', function (t) {
    	
    	var ttrId = $(e).attr('data-ttrId');
    	
    	$(this).attr('data-ttrId',ttrId);
//		  if (!data) return e.preventDefault() // stops modal from being shown
	})
}