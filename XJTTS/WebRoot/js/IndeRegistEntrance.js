/**
 * Created by thanosYao on 2015/7/19.
 */
$(document).ready(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
    var height=$(window).height();
	$(".main").css("min-height",height-$("#footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("#footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
});
$('[data-toggle="tooltip"]').tooltip();

angular.module('entrance', [])
    .controller('entranceController',function ($scope,$http) {
        var idName = window.location.search.slice(4);
        $http.get('../teacher/trainInfo_initProjectAPI.action').success(function(data) {
            $scope.status = data.Result;
            $scope.message = data.Message;
            if(data.Records)$scope.lists = data.Records;
        });

    })

showPage = function (ele) {
    window.location.href = "IndeRegist.html?id="+ele.value;
}