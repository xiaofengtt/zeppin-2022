/**
 * Created by thanosYao on 2015/7/21.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$(function(){
	var height=$(window).height();
	$(".main").css("min-height",height-$("#footer").outerHeight(true)-$("#header").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("#footer").outerHeight(true)-$("#header").outerHeight(true)-115+"px");	
	})
})
angular.module('personalDirective', [])
    .controller('personalController',function ($scope,$http) {
        $http.get('../teacher/teacherSpace_initMainPage.action').success(function(data) {
            $scope.records = data.Records;
        });
    });

