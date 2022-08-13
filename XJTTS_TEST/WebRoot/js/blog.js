/**
 * Created by thanosYao on 2015/7/2.
 */
jQuery(document).ready(function ($) {
    $('.tabset0').pwstabs({
        effect: 'scale',              // You can change effects of your tabs container: scale / slideleft / slideright / slidetop / slidedown / none
        defaultTab: 1,                // The tab we want to be opened by default
        containerWidth: '100%',     // Set custom container width if not set then 100% is used
        tabsPosition: 'horizontal',   // Tabs position: horizontal / vertical
        horizontalPosition: 'top',    // Tabs horizontal position: top / bottom
        verticalPosition: 'left',     // Tabs vertical position: left / right
        responsive: true,             // Make tabs container responsive: true / false - boolean
        theme: '',
        rtl: false                    // Right to left support: true/ false
    });

    // Colors Demo
    $('.pws_demo_colors a').click(function (e) {
        e.preventDefault();
        $('.pws_tabs_container').removeClass('pws_theme_grey pws_theme_violet pws_theme_green pws_theme_yellow pws_theme_gold pws_theme_orange pws_theme_red pws_theme_purple').addClass('pws_theme_'+$(this).attr('data-demo-color') );
    });

    //init
    $.ajax({
        method: "POST",
        url: "../teacher/trainInfo_initProjectAPI.action",
        success: function (data){
            if(data.Result == "OK"){
                console.log("init success!");
                for(var i=0; i<data.Records.length; i++){
                    var id = data.Records[i].projectId;
                    var name = data.Records[i].projectName;
                    $("#signupEntry").append( "<a class='btn btn-default' onclick='showPage("+ id +")' >" + name + "</a><br/><br/>" );
                }
            }
            else{
                console.log(data);
            }
        }
    })
});

showPage = function (id) {
    alert(id);
//    $.ajax({
//        method: "POST",
//        url: "../teacher/trainInfo_initPage.action?id="+id,
//        success: function (data){
//            if(data.Result == "OK"){
//                console.log(data);
//                $("#signupEntry").html("");
//                for(var i=0; i<data.Records.length; i++){
//                    var funds = data.Records[i].funds;
//                    $("#signupEntry").append('<a>b</a>')
//                }
//            }
//            else{
//                console.log(data);
//            }
//        }
//    })
};

registerBtn = function (ele){
	var a = $(ele).nextNode()
    alert(a);
}

angular.module('signupListDirective', [])
.controller('signupListController',function ($scope,$http) {
    $http.get('../teacher/trainInfo_initPage.action').success(function(data) {
        $scope.funds = data.Records;
    });

})