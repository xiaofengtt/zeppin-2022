/**
 * Created by thanosYao on 2015/7/21.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
angular.module('personalDirective', [])
    .controller('personalController',function ($scope,$http) {
        $http.get('../teacher/teacherSpace_initMainPage.action').success(function(data) {
            $scope.records = data.Records;
        });
    });

