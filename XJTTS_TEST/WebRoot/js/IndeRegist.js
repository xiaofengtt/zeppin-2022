/**
 * Created by thanosYao on 2015/7/19.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
$(function(){
    $(".registList").removeClass("hide");
    $(".footer").removeClass("hide");
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
angular.module('signupListDirective', [])
.controller('signupListController',function ($scope,$http) {
    var idName = getUrlParam("id");
    $http.get('../teacher/trainInfo_initPage.action?id='+idName).success(function(data) {
        $("#trainType").text(data.TrainType);
        $("#projectNameText").text(data.ProjectName);
        $scope.funds = data.Records;
        $("#allCount").text(data.TotalRecordCount);
        $("#alreadyCount").text(data.IsEnrollType);
        $("#preCount").text(data.UnEnrollType);
    });
})

