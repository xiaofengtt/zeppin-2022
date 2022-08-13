/**
 * Created by shenzhi on 2015/7/20.
 */

/*var elem = document.getElementById('questionList');
window.mySwipe = Swipe(elem, {
    // startSlide: 4,
    // auto: 3000,
    speed : 400,
    continuous: false,
    disableScroll: false,
    // stopPropagation: true,
    callback: function(index, element) {
        //console.log(index,element);
    },
    transitionEnd: function(index, element) {
        $('.content').scrollTop(0);
    }
});*/
/* var id=url("?id"); */
angular.module('material',[]).controller('ChooseController',function($scope,$http){
    $http.get('../admin/paperLoadPaperForHtml.action?id=192').success(function(data){
        $scope.records=data.Records;
    });
});

