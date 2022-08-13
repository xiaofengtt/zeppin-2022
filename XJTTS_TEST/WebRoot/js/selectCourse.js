/**
 * Created by thanosYao on 2015/7/24.
 */

$("#header").load("header.html");
$("#footer").load("footer.html");

function hsdTests(page) {

    $.getJSON('../teacher/hsdTest_getHsdTest.action?page=' + page,
        function(data) {

            if (data.status == 'OK') {

                var template = $.templates('#ttRecordAduTpl');
                var html = template.render(data.records);
                $('#list-content').html(html).removeClass('loading');

                $.each(data.records,function(i, v) {

                    var f_data = [];
                    var s_data = [];
                    var id = v.id;

                    var score = v.score
                        .split(",");
                    var jhyzb1 = parseFloat(score[1]); //1计划与准备
                    var zzygl1 = parseFloat(score[2]); //1组织与管理
                    var pgyzd1 = parseFloat(score[3]);//1评估与诊断
                    var jssy1 = parseFloat(score[0]); //1技术素养
                    var xxyfz1 = parseFloat(score[4]);//1学习与发展

                    var jhyzb2 = parseFloat(score[6]); //2计划与准备
                    var zzygl2 = parseFloat(score[7]); //2组织与管理
                    var pgyzd2 = parseFloat(score[8]); //2评估与诊断
                    var jssy2 = parseFloat(score[5]); //2技术素养
                    var xxyfz2 = parseFloat(score[9]); //2学习与发展
                    var str = [ jssy1,jhyzb1, zzygl1,pgyzd1, xxyfz1,jssy2, jhyzb2,zzygl2, pgyzd2,xxyfz2 ];
                    f_data[0] = jhyzb1;
                    f_data[1] = zzygl1;
                    f_data[2] = pgyzd1;
                    f_data[4] = jssy1;
                    f_data[3] = xxyfz1;
                    s_data[0] = jhyzb2;
                    s_data[1] = zzygl2;
                    s_data[2] = pgyzd2;
                    s_data[4] = jssy2;
                    s_data[3] = xxyfz2;

                    for (var i = 1; i <= str.length; i++) {
                        if (str[i - 1] <= 0.4) {
                            document.getElementById("nl"+ i + "_"+ id).style.background = "#ff0000";
                        } else if (str[i - 1] >= 0.8) {
                            document.getElementById("nl"+ i+ "_"+ id).style.background = "#00ff00";
                        } else {
                            document.getElementById("nl"+ i+ "_"+ id).style.background = "#ffff00";
                        }
                    }

                    //雷达图
                    var radarChartData_1 = {
                        labels : [
                            "I计划与准备",
                            "I组织与管理",
                            "I评估与诊断",
                            "I学习与发展",
                            "I技术素养" ],
                        datasets : [ {
                            label : "",
                            fillColor : "rgba(0,102,0,0.2)",
                            strokeColor : "#006600",
                            pointColor : "#006600",
                            pointStrokeColor : "#006600",
                            pointHighlightFill : "#006600",
                            pointHighlightStroke : "#006600",
                            data : f_data

                        } ]
                    };

                    var radarChartData_2 = {
                        labels : [
                            "II计划与准备",
                            "II组织与管理",
                            "II评估与诊断",
                            "II学习与发展",
                            "II技术素养" ],
                        datasets : [ {
                            label : "",
                            fillColor : "rgba(0,51,225,0.2)",
                            strokeColor : "#0033FF",
                            pointColor : "#0033FF",
                            pointStrokeColor : "#0033FF",
                            pointHighlightFill : "#0033FF",
                            pointHighlightStroke : "#0033FF",
                            data : s_data

                        } ]
                    };

                    //雷达图
                    var myRadar = new Chart(document.getElementById("canvas1_"+ id).getContext("2d")).Radar(radarChartData_1,{
                        animation : false,

                        //Boolean - 是否显示出每个点线表
                        scaleShowLine : true,

                        //Boolean - 是否显示角线的雷达
                        angleShowLineOut : true,

                        //Boolean - Whether to show labels on the scale
                        scaleShowLabels : true,

                        // Boolean - 是否显示标尺上的标签
                        scaleBeginAtZero : true,

                        //String - 角线的颜色
                        angleLineColor : "rgba(0,0,0,0.3)",

                        scaleLineColor : "rgba(0,0,0,0.3)",

                        //Number - 像素宽度的线角
                        angleLineWidth : 1,

                        //String - 标签
                        pointLabelFontFamily : "'Arial'",

                        //String - 点标签的字体重量

                        pointLabelFontStyle : "normal",

                        //Number - 在像素点标签的字体大小
                        pointLabelFontSize : 10,

                        //String - 点颜色标签
                        pointLabelFontColor : "#000000",

                        //Boolean - Whether to show a dot for each point
                        pointDot : true,

                        //Number - 每个像素点半径
                        pointDotRadius : 3,

                        //Number - 点点像素宽度
                        pointDotStrokeWidth : 1,

                        //Number - 大量额外添加到半径为迎合命中检测外画点
                        pointHitDetectionRadius : 20,

                        //Boolean - 是否要显示一个中风的数据集
                        datasetStroke : false,

                        //Number - 数据集笔划像素宽度
                        datasetStrokeWidth : 2,

                        //Boolean - 是否有一种颜色填充数据集
                        datasetFill : true,
                        //onAnimationComplete: function () { alert("OK")},
                        scaleOverride : true,
                        scaleSteps : 5,
                        // Number - The value jump in the hard coded scale
                        scaleStepWidth : 0.2,
                        // Number - The scale starting value
                        scaleStartValue : 0
                    });
                    var myRadar_2 = new Chart(document.getElementById("canvas2_"+ id).getContext("2d")).Radar(radarChartData_2,{
                        animation : false,

                        //Boolean - 是否显示出每个点线表
                        scaleShowLine : true,

                        //Boolean - 是否显示角线的雷达
                        angleShowLineOut : true,

                        //Boolean - Whether to show labels on the scale
                        scaleShowLabels : true,

                        // Boolean - 是否显示标尺上的标签
                        scaleBeginAtZero : true,

                        //String - 角线的颜色
                        angleLineColor : "rgba(0,0,0,0.3)",

                        scaleLineColor : "rgba(0,0,0,0.3)",

                        //Number - 像素宽度的线角
                        angleLineWidth : 1,

                        //String - 标签
                        pointLabelFontFamily : "'Arial'",

                        //String - 点标签的字体重量

                        pointLabelFontStyle : "normal",

                        //Number - 在像素点标签的字体大小
                        pointLabelFontSize : 10,

                        //String - 点颜色标签
                        pointLabelFontColor : "#000000",

                        //Boolean - Whether to show a dot for each point
                        pointDot : true,

                        //Number - 每个像素点半径
                        pointDotRadius : 3,

                        //Number - 点点像素宽度
                        pointDotStrokeWidth : 1,

                        //Number - 大量额外添加到半径为迎合命中检测外画点
                        pointHitDetectionRadius : 20,

                        //Boolean - 是否要显示一个中风的数据集
                        datasetStroke : false,

                        //Number - 数据集笔划像素宽度
                        datasetStrokeWidth : 2,

                        //Boolean - 是否有一种颜色填充数据集
                        datasetFill : true,
                        scaleOverride : true,
                        scaleSteps : 5,
                        // Number - The value jump in the hard coded scale
                        scaleStepWidth : 0.2,
                        // Number - The scale starting value
                        scaleStartValue : 0
                    });

                });

                var options = {
                    currentPage : data.page.currentPage,
                    totalPages : data.page.totalPage,
                    shouldShowPage : function(type, page,current) {
                        switch (type) {
                            default:
                                return true;
                        }
                    },
                    onPageClicked : function(e,originalEvent, type, page) {
                        var pp = page;
                        aPage = page;
                        hsdTests(pp);
                    }

                }
                $('#pagination').bootstrapPaginator(options);

            } else {
                $('#list-content').html(
                    '<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
                    + '您还没有测评信息技术能力，现在测评请点击'+ '<a target="_blank" href="../HSDTesting/index.html">' +'去测评' + '</a></p>').removeClass('loading');
                $('#pagination').html('');
            }

        });

}

$(function() {

    hsdTests(1);

});

function gotestClick() {

    var flag = false;

    $.getJSON('../teacher/hsdTest_checkHsdTest.action', function(data) {

        if (data.status == 'OK') {
            window.open("../HSDTesting/index.html","_self");
        } else {
            alert(data.message);
        }

    });

}