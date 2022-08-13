/**
 * Created by thanosYao on 2015/7/19.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$(function(){
    $(".registList").removeClass("hide");
    $(".footer").removeClass("hide");
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
});
function getUrlParam(name) {
    var url = window.location.href;
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
    var paraObj = {}
    var i = 0;
    var j;
    for (i = 0; j = paraString[i]; i++){
        paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
    }

    var returnValue = paraObj[name.toLowerCase()];
    if(typeof(returnValue)=="undefined"){
        return "";
    }else{
        return returnValue;
    }
};

function reset(e){
	var _this = $(e);
	if(confirm("确定要撤销报名吗？")){
		var id = _this.attr('data-id');
		var e = _this.attr('data-id2');
		$.ajax({
	        url: "../teacher/autoenro_reset.action",
	        method: "POST",
	        data: {
	        	id:id,
	        	isAdvance:e
	        },
	        success:function(data){
	            if(data.Result == "OK"){
	            	$(".confirm").removeAttr('disabled');
	              	layer.confirm(data.Message, {
	    				btn : [ '确定' ]
	    			//按钮
	    			}, function() {
	    				layer.closeAll();
	    			});
	              	window.location.reload();
	            }
	            else{
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
	
}
angular.module('signupListDirective', [])
.controller('signupListController',function ($scope,$http) {
    var idName = getUrlParam("id");
    $http.get('../teacher/trainInfo_initPage.action?id='+idName).success(function(data) {
    	if('OK' == data.Result){
    		$("#trainType").text(data.TrainType);
            $("#projectNameText").text(data.ProjectName);
            $scope.funds = data.Records;
            $("#allCount").text(data.TotalRecordCount);
            $("#alreadyCount").text(data.IsEnrollType);
            $("#preCount").text(data.UnEnrollType);
    	} else {
    		$scope.status = data.Result;
            $scope.message = data.Message;
    	}
    });
})

