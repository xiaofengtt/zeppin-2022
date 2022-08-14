$(document).ready(function() {
	var chart = "";
	$(".status-tap").tap(function() {
		$(event.target).siblings(".status").addClass("status-light");
		$(event.target).parent().siblings().find(".status").removeClass("status-light");
		$(".item-box").hide();
		$("."+$(event.target).prev().attr("data-status")).show();
	});
	//判断是否转入成功过 显示转出按钮
	if(localStorage.getItem("turnOut")){
		$(".bottom-btn").hide();
		$(".bottom-btn-box").show();
	}else{
		$(".bottom-btn").show();
		$(".bottom-btn-box").hide();
	}

//	getCharts();
//	chart.tooltip.refresh(chart.series[0].points[7]);

	function getCharts() {
		chart = Highcharts.chart('charts', {
			title: {
				text: ''
			},
			chart: {
				//						zoomType:"xy",	//拖动缩放，值：x , y , xy
				events: {
					load: function() {
						//										                        var p = this.series[0].points[7];
						//										                        this.tooltip.refresh(p);
						console.log(this);
					}
				}
			},
			subtitle: {
				text: ''
			},
			xAxis: [{
				//						type: 'datetime',
				//				        dateTimeLabelFormats: {
				//				            day: '%Y-%m-%d'
				//				        },
				label: {

				},
				//                      categories: [1],
				//                      crosshair: true
				//						endOnTick:true,
				//						max:8,
				//						minTickInterval:1 //X轴数据点的最小间隔
			}],
			yAxis: {
				labels: {
					//                          format: '{value}万元',
					style: {
						color: "#E0615F"
					},
					formatter: function() {
						if(this.value > 10000) {
							return this.value / 10000 + "";
						} else {
							return this.value + "";
						}

					}
				},
				title: {
					text: ''
				},
				//						categories:[1,2,3,4,5,6,7],
			},
			legend: {
				layout: 'vertical',
				align: 'right',
				verticalAlign: 'middle'
			},
			tooltip: {
				//						valuePrefix: '￥',	//提示框前修饰
				//  						valueSuffix: '元',	//提示框后修饰
				//						shared: true,
				shadow: false,
				//						shape: 'circle',
				formatter: function() {
					return this.y + "元"
				},
				hideDelay: "3600000", //提示框小时延时
				//						positioner: function() {
				//							return {
				//								x: 10.16666666667,
				//								y: 69.42487499999999
				//							};
				//						},
				backgroundColor: {
					linearGradient: [0, 0, 0, 60],
					stops: [
						[0, '#3D8EED'],
						[1, '#3D8EED'],
					]
				},
				borderColor: '#3D8EED',
				borderRadius: 10,
				style: { // 文字内容相关样式
					color: "#ffffff",
					fontSize: "12px",
					fontWeight: "blod",
				}
			},
			plotOptions: {
				series: {
					label: {
						connectorAllowed: false
					},
					//							marker:{
					//								enabled: false
					//							},
					enableMouseTracking: false, //鼠标经过数据点的效果
					pointStart: 2010, //X轴数据的开始值
					//							pointStart: Date.UTC(2010, 0, 0),//X轴数据的开始值
					//							pointInterval: 24 * 3600 * 1000 // 间隔一天
					fillColor: {
						linearGradient: [0, 0, 0, 200],
						stops: [
//							[0, "#FF7600"],
							[1, Highcharts.Color("#FF7600").setOpacity(0.2).get('rgba')]
						]
					}
				}
			},
			series: [{
				name: '',
				type: "area",
				color: "#FF7600",
				marker: {
					symbol: 'circle', //数据点的样式类型
					enabled: false,
					//							lineColor:"red",
					//							lineWidth:2
				},
				data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, {
					y: 18111,
					//							color:'red',
					marker: {
						enabled: true,
					}
				}]
			}],
			responsive: {
				rules: [{
					condition: {
						maxWidth: 500
					},
					chartOptions: {
						legend: {
							layout: 'horizontal',
							align: 'center',
							verticalAlign: 'bottom',
							enabled: false
						}
					}
				}]
			}
		});
	} //getCharts
});
//转入按钮
$(".free-in").tap(function(){
	window.location.href = "./moneyTurnin.html?openId="+openIdUrl;
});
//转出按钮
$(".free-out").tap(function(){
	window.location.href = "./moneyTurnout.html?openId="+openIdUrl;
});
//交易明细
$(".main-top span").tap(function(){
	window.location.href = "./transactionDetails.html?openId="+openIdUrl;
});
