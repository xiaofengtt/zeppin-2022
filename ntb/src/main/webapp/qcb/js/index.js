var dataX = [];
var dataY = [];
$(function() {
    getDate();
    $(".main-left-item:eq(0)").children("a").addClass("color-orange").children("img").attr("src","./img/r-1.png");
});
$(".closeNotice").click(function() {
    $(".noticeBox").slideUp();
});
//获取数据
function getDate() {
    $.ajax({
        url: '../rest/qcb/companyAccount/getInfo',
        type: 'get',
        data: {
        	"timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            if (r.data.authStatus == "unauth") {
                $(".companyName").html(r.data.name + '<span class="color-red">（<a href="companyOperate.jsp" class="color-red unauth">&nbsp;'
                		+r.data.authStatusCN+'&nbsp;</a>）</span>'); //公司名称
            } else if (r.data.authStatus == "uncheck") {
                $(".companyName").html(r.data.name + '<span class="color-orange">（&nbsp;'+r.data.authStatusCN+'&nbsp;）</span>'); //公司名称
            } else if (r.data.authStatus == "normal") {
                $(".companyName").html(r.data.name + '<span class="color-green">（&nbsp;'+r.data.authStatusCN+'&nbsp;）</span>'); //公司名称
            } else if (r.data.authStatus == "nopass") {
                $(".companyName").html(r.data.name + '<span class="color-red">（&nbsp;'+r.data.authStatusCN+'&nbsp;）</span>'); //公司名称
            } else if (r.data.authStatus == "deleted") {
                $(".companyName").html(r.data.name + '<span class="color-red">（&nbsp;'+r.data.authStatusCN+'&nbsp;）</span>'); //公司名称
            }
            $(".companyNumber").html(r.data.countEmployee + "人");
            $(".companyAdmin").html(r.data.accredit); //系统管理员
            $(".accountBalance").html(r.data.accountBalanceCN); //账户余额
            $(".totalInvestment").html(r.data.totalInvestCN); //投资总额
            $(".earnInterest").html(r.data.totalReturnCN); //赚取利息
        	$(".loadingOver").show();
        	$(".loadingDiv").hide();
            $('#containerline').highcharts({
                chart: {
                    type: 'areaspline'
                },
                title: {
                    text: '',
                    x: -20 //center
                },
                subtitle: {
                    text: '',
                    x: -20
                },
                xAxis: {
                    categories: r.data.mapPayInfo.xInfo,
                    gridLineWidth: 0 //x轴网格线宽度
                },
                yAxis: {
                    gridLineWidth: 0, //y轴网格线宽度
                    title: {
                        text: ''
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#ff7e01'
                    }],
                    lineWidth: 1 //y轴
                },
                tooltip: {
                    valueSuffix: '元',
                    crosshairs: [{ // 设置准星线样式
                        width: 2,
                        color: '#fec994'
                    }],
                    //气泡样式
                    backgroundColor: '#ff7e00', // 背景颜色
                    borderColor: 'none', // 边框颜色
                    borderRadius: 3, // 边框圆角
                    borderWidth: 0, // 边框宽度
                    shadow: false, // 是否显示阴影
                    animation: true, // 是否启用动画效果
                    style: { // 文字内容相关样式
                        color: "#ffffff",
                        fontSize: "14px",
                        fontFamily: "Courir new"
                    },
                    shared: true,
                    useHTML: true,
                    headerFormat: '',
                    pointFormat: '<tr><td style="color: {series.color}">{series.name} </td>' +
                        '<td style="text-align: right"><b>{point.y}</b></td></tr>',
                    footerFormat: ''
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0,
                    enabled: false
                },
                plotOptions: {
                    areaspline: {
                        color: '#rgb(255, 250, 244)', //区域背景色
                        lineColor: '#ff7e00', //折线颜色
                        fillOpacity: 1,
                    }
                },
                series: [{
                    name: ' ',
                    data: r.data.mapPayInfo.yInfo,
                    showInLegend: false,
                    marker: {
                        radius: 4, //曲线点半径，默认是4
                        lineWidth: 0,
                        lineColor: '#fd932a',
                        fillColor: '#fd932a',
                        states: {
                            hover: {
                                enabled: false
                            }
                        }

                    }
                }],
                crosshairs: true

            });
        } else {
        	$(".loadingDiv").hide();
        	layer.msg(r.message);
        }
    }).fail(function() {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });
}