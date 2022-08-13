/**
 * Created by thanosYao on 2015/7/24.
 */
$("#header").load("header.html");
$("#footer").load("footer.html");
angular.module('evaluation', [])
    .controller('evaluationCtrl',function ($scope,$http) {
        $http.get('../teacher/hsdTest_getTrainingRecords.action').success(function(data) {
            if(data.Result == "EMPTY"){
                allEvaluation.innerHTML = "0";
                hasEvaluation.innerHTML = "0";
                noEvaluation.innerHTML = "0";
                $(".noEvaluation").removeClass("hide");
            }else{
                allEvaluation.innerHTML = data.TotalCount;
                hasEvaluation.innerHTML = data.IsFinished;
                noEvaluation.innerHTML = data.NoFinished;
            }

            $scope.records = data.Records;
            $scope.result = data.Result;
        });

    })

$(".evaluationList").removeClass("hide");

function evaluationBtn(e){
    var projectApplyId = e.getAttribute("data-val");
    $.post( "../paper/paper_choosePaper.action?id=" + projectApplyId, function( data ) {
        $( "html" ).html( data );
    });
}