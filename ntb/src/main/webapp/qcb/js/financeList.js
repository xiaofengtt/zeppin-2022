var pageNum = 1,
    pageSize = 10,
    listType = url("?type");

$(document).ready(function() {
    $(".main-left-item:eq(1)").children("a").addClass("color-orange").children("img").attr("src", "./img/r-2.png");
    $(".main-left-item:eq(1) ul li:eq(3) a").addClass("color-orange");
    ! function() {
    	var endTime = $("#endTime").val();
    	var startTime = $("#startTime").val();
    	$("#startTime").click(function(){
    		laydate({
	            elem: '#startTime',
	            format: 'YYYY-MM-DD',
	            max: $("#endTime").val() ? $("#endTime").val(): "2099-06-16 23:59:59" ,
	            istime: false,
	            choose: function() {
	                if ($("#endTime").val() == "") {
	//                    layer.msg("请选择结束时间");
	                } else {
	                    getList($(".type-bar > a.type-light").attr("type"), '', $("#startTime").val(), $("#endTime").val());
	                }
	            }
	        });
    	});
    	$("#endTime").click(function(){
    		laydate({
                elem: '#endTime',
                format: 'YYYY-MM-DD',
                min: $("#startTime").val() ? $("#startTime").val() : '',
//                max: laydate.now(0, "YYYY/MM/DD"),
                istime: false,
                choose: function() {
                    if ($("#startTime").val() == "") {
                        layer.msg("请选择开始时间");
                    } else {
                        getList($(".type-bar > a.type-light").attr("type"), '', $("#startTime").val(), $("#endTime").val());
                    }
                }
            });
    	});
    }();
    $("#endTime").click(function(){
    	$("#laydate_box").css("left",Number($("#laydate_box").css("left").substring(0,$("#laydate_box").css("left").length-2))-130+"px");
    });

    $(".termbar > a").on("click", function() {
        $(this).addClass("color-orange").siblings('a').removeClass("color-orange");
    });
    $(".type-bar > a").on("click", function() {
        $(this).addClass("type-light").siblings('a').removeClass("type-light");
    });




    //打印
    function printTable() {
        var cssString = ".table{table-layout: fixed;border-collapse: collapse;width:100%;margin-top:34px;border:1px solid #E8E4E4;}.table tr{height:74px;border-bottom:1px solid #E8E4E4;}.table tr th{text-align: left;	font-size:15px;padding:0 4px;}.table .first-tr{background:#f5f5f5;border:none;}.table .first-tr td{vertical-align: middle;padding-top:0;}.table tr td{font-size:14px;padding:0 4px;vertical-align: top;padding-top:16px;}.table tr td a{display: inline-block;margin-right:4px;color:#000000;}.table tr td a:hover{color:#FF9E10;}.table .tr-padding-item{padding-left:20px;}.ui-paging-container ul, .ui-paging-container li{text-align: right;}";
        var tableToPrint = document.getElementById('table'); //将要被打印的表格
        var newWin = window.open(""); //新打开一个空窗口
        newWin.document.write(tableToPrint.outerHTML); //将表格添加进新的窗口
        var c = newWin.document.getElementsByClassName("detail"); //获取新窗口需要隐藏的td
        for (var i = 0; i < c.length; i++) {
            c[i].style.display = "none"; //隐藏td
        }
        var style = newWin.document.createElement("style"); //创建style标签
        style.setAttribute("type", "text/css");

        if (style.styleSheet) { // IE  	//向style标签里添加样式内容
            style.styleSheet.cssText = cssString;
        } else { // w3c
            var cssText = newWin.document.createTextNode(cssString);
            style.appendChild(cssText);
        }

        var heads = newWin.document.getElementsByTagName("head"); //把style标签添加到head里
        if (heads.length) {
            heads[0].appendChild(style);
        } else {
            newWin.document.documentElement.appendChild(style);
        }

        //		var doc = newWin.document;
        //		var link = doc.createElement("link");
        //		link.setAttribute("rel", "stylesheet");
        //		link.setAttribute("type", "text/css");
        //		link.setAttribute("href", "http://192.168.1.198:8020/QCB/css/table.css");
        //
        //		var heads = doc.getElementsByTagName("head");
        //		if(heads.length){
        //			heads[0].appendChild(link);
        //		}else{
        //			doc.documentElement.appendChild(link);
        //		}

        newWin.document.close(); //在IE浏览器中使用必须添加这一句
        newWin.focus(); //在IE浏览器中使用必须添加这一句

        newWin.print(); //打印
        newWin.close(); //关闭窗口
    } //打印函数结束
    $("#stamp").click(function() {
        printTable();
    });
    //入口为提现或充值，默认筛选状态
    if (listType != null) {
        $(".type-bar a[type='" + listType + "']").addClass('type-light').siblings('a').removeClass('type-light');
        getList(listType, $(".termbar a.color-orange").attr("deadline"), $("#startTime").val(), $("#endTime").val());
        return false;
    }
    getList('', '2', '', '');

});
$(".type-bar > a").click(function() {
    $("input[name='type']").val($(this).attr("type"));
    getList($(this).attr("type"), $(".termbar a.color-orange").attr("deadline"), $("#startTime").val(), $("#endTime").val());
});
$(".termbar a").click(function() {
    if ($(this).hasClass("custom")) {
        $(".date-box").show();
        $("input[name='deadline']").val('');
    } else {
        $("input[name='deadline']").val($(this).attr("deadline"));
        $(".date-box").hide();
        getList($(".type-bar > a.type-light").attr("type"), $(this).attr("deadline"), $("#startTime").val(), $("#endTime").val());
    }

});
//获取列表
function getList(type, deadline, starttime, endtime) {
	$(".loadingDiv").show();
	$(".loadingOver").hide();
    $.ajax({
        url: '../rest/qcb/companyAccountHistory/list',
        type: 'get',
        data: {
            "type": type,
            "deadline": deadline,
            "pageNum": pageNum,
            "starttime": starttime,
            "endtime": endtime,
            "pageSize": pageSize,
            "timestamp":new Date().getTime() 
        }
    }).done(function(r) {
        if (r.status == "SUCCESS") {
            var html = '<tr class="first-tr"><th width="14%" class="td-padding-item">入账时间</th>' +
                '<th width="17%">流水号</th><th width="10%">服务类型</th><th width="19%">对方信息</th>' +
                '<th width="13%">交易金额（元）</th><th width="14%">账户余额（元）</th><th width="13%">备注</th></tr>';
            if (r.data.length > 0) {
                for (var i = 0; i < r.data.length; i++) {
                    var bankName;
                    var companyAccount;
                    var cardNum;
                    if (r.data[i].companyAccount == null || r.data[i].companyAccount == "") {
                    	if(r.data[i].type=="payroll"){
                    		companyAccount = "企财宝员工";
                    	}else if(r.data[i].type=="income"){
                    		companyAccount = "企财宝";
                    	}else if(r.data[i].type=="expend"){
                    		companyAccount = "北京知鹏汇仁科技有限公司";
                    	}else{
                    		companyAccount = "无";
                    	}                       
                    } else {
                        companyAccount = r.data[i].companyAccount;
                    }
                    if (r.data[i].bankName == null || r.data[i].bankName == "") {
                    	if(r.data[i].type=="payroll"){
                    		bankName = "";
                    	}else if(r.data[i].type=="income"){
                    		bankName = "";
                    	}else if(r.data[i].type=="expend"){
                    		bankName = "";
                    	}else{
                    		bankName = "无"+ ' (尾号：';
                    	}          
                        
                    } else {
                        bankName = r.data[i].bankName+ ' (尾号：';
                    }
                    if (r.data[i].cardNum == null || r.data[i].cardNum == "") {
                    	if(r.data[i].type=="payroll"){
                    		cardNum = "";
                    	}else if(r.data[i].type=="income"){
                    		cardNum = "";
                    	}else if(r.data[i].type=="expend"){
                    		cardNum = "";
                    	}else{
                    		cardNum = "无)";
                    	}        
                        
                    } else {
                        cardNum = r.data[i].cardNum+")";
                    }
                    if (r.data[i].type == "income" || r.data[i].type == "redeem") {
                        html += '<tr><td class="td-padding-item"><p>' + r.data[i].createtimeCN.substring(0, 10) +
                            '</p><small class="color-gray">' + r.data[i].createtimeCN.substring(11, 19) +
                            '</small></td><td>' + r.data[i].orderNum +
                            '</td><td>' + r.data[i].typeCN + '</td><td><p>' + companyAccount +
                            '</p><small class="color-gray">' + bankName  + cardNum + '</small></td>' +
                            '<td class="color-orange">+' 
                            + r.data[i].price.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('')
                            + '</td><td>' + r.data[i].accountBalanceCN.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') +
                            '</td><td>' + r.data[i].remark + '</td></tr>';
                    } else {
                        html += '<tr><td class="td-padding-item"><p>' + r.data[i].createtimeCN.substring(0, 10) +
                            '</p><small class="color-gray">' + r.data[i].createtimeCN.substring(11, 19) +
                            '</small></td><td>' + r.data[i].orderNum +
                            '</td><td>' + r.data[i].typeCN + '</td><td><p>' + companyAccount +
                            '</p><small class="color-gray">' + bankName + cardNum + '</small></td>' +
                            '<td class="color-green">-' + 
                            r.data[i].price.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') 
                            + '</td><td>' + 
                            r.data[i].accountBalanceCN.split('').reverse().join('').replace(/(\d{3})/g,'$1,').replace(/\,$/,'').split('').reverse().join('') +
                            '</td><td>' + r.data[i].remark + '</td></tr>';
                    }
                }
                

            } else {
                html += '<tr><td colspan="8" style="text-align:center">暂无记录</td></tr>';
            }
            if (r.totalPageCount!=0) {
                $('#pageTool').Paging({
                    prevTpl: "<",
                    nextTpl: ">",
                    pagesize: pageSize,
                    count: r.totalResultCount,
                    current: pageNum,
                    toolbar: true,
                    pageSizeList: [10, 20, 50],
                    callback: function(page, size, count) {
                        pageNum = page;
                        getList($(".type-bar > a.type-light").attr("type"), $(".termbar a.color-orange").attr("deadline"), $("#startTime").val(), $("#endTime").val());
                        flag = false;
                        document.body.scrollTop = document.documentElement.scrollTop = 0;
                    }
                });
                $("#pageTool").show().find(".ui-paging-container:last").siblings().remove();
            }else{
            	$("#pageTool").hide();
            }
            $(".ui-select-pagesize").unbind("change").change(function() {
                pageSize = $(this).val();
                pageNum = 1;
                getList($(".type-bar > a.type-light").attr("type"), $(".termbar a.color-orange").attr("deadline"), $("#startTime").val(), $("#endTime").val());
            });
            $(".table").html(html);
            $(".loadingOver").show();
        	$(".loadingDiv").hide();
        } else {
        	$(".loadingDiv").hide();
            layer.msg(r.message);
        }

    }).fail(function(r) {
    	$(".loadingDiv").hide();
        layer.msg("error", {
            time: 2000
        });
    });

}
//打印
//$("#down-load").click(function(){
//	var form = $("#print");
//	$.post(form.attr("action"),"type="+$(".termbar a.color-orange").attr("deadline")
//			+"&deadline="+$(".type-bar .type-light").attr("type")
//			+"&starttime="+$("#startTime").val()
//			+"&endtime="+$("#endTime").val()
//			+"&CSRFToken="+$('input[name="CSRFToken"]').val(), function(data) {
//		if (data.status == "SUCCESS") {
//
//		} else {
//			layer.msg(data.message);
//		}
//	});
//	return false;
//});