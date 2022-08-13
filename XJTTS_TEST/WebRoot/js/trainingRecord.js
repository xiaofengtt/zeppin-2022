/**
 * Created by thanosYao on 2015/7/22.
 */
$(document).ready(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
});

angular.module('trainingRecordDirective', [])
.controller('trainingRecordController',function ($scope,$http) {
    $http.get('../teacher/trainRecords_initPage.action').success(function(data) {
        if(data.Result == "EMPTY"){
            $scope.totalRecordCount = "0";
            $scope.onTrainCount = "0";
            $scope.endCount = "0";
        }else{
            $scope.totalRecordCount = data.TotalRecordCount;
            $scope.onTrainCount = data.OnTrainCount;
            $scope.endCount = data.EndCount;
        }

        if(data.Records){
            for(var i=0;i<data.Records.length;i++){
                data.Records[i].createYear = data.Records[i].createTime.slice(0,4);
                data.Records[i].createDate = data.Records[i].createTime.slice(5,10);
                data.Records[i].trainingStart = data.Records[i].trainingStartTime.slice(0,10);
                data.Records[i].trainingEnd = data.Records[i].trainingEndTime.slice(0,10);
            }
        }
        $scope.records = data.Records;
        $scope.result = data.Result;
    });
})

var display=1;
function moreInfo (ele) {
    if(display==1){
        $(ele.parentElement.parentElement.getElementsByClassName("moreInfo")).removeClass("hide");
        ele.innerHTML="收起";
        display=0;
    }else{
        $(ele.parentElement.parentElement.getElementsByClassName("moreInfo")).addClass("hide");
        ele.innerHTML="查看更多";
        display=1;
    }
}
function checkIsTest(ele){
	var obj = $(ele);
	var cUrl = obj.attr('data-url');
	var ttrId = obj.attr('data-id');
	$.getJSON("../teacher/trainRecords_checkIsTest.action?ttrId="+ttrId, function(ret) {
		if (ret.Result == 'OK') {
//				infotip(obj, ret.Message);
//				$('#adu_'+obj.attr('data-id')).remove();
			window.location.href=cUrl;
			new_window.close();
//			window.open(cUrl); 
		} else if(ret.Result == 'REDIRECT') {
//			alert('失败,sss' + ret.Message);
////			window.location.href = "selectCourse.html";
//			window.open("../HSDTesting/index.html");
			if(confirm('失败,' + ret.Message)){
				open_window('../HSDTesting/index.html');
			}else{
				new_window.close();
			}
		} else {
			alert('失败,' + ret.Message);
			new_window.close();
		}
	})
	return false;
}

var new_window = null;
function open_window(url) {      
    new_window.location.href = url;      
} 