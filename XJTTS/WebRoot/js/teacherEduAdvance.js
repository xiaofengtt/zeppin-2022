/**
 * Created by thanosYao on 2015/7/22.
 */
$(document).ready(function(){
    $("#header").load("header.html");
    $("#footer").load("footer.html");
    var height=$(window).height();
	$(".main").css("min-height",height-$("#footer").outerHeight(true)-$("#header").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("#footer").outerHeight(true)-$("#header").outerHeight(true)-115+"px");	
	});
});
$(window).resize(function(){
	$('.brand-waterfall').waterfall();
})

angular.module('trainingRecordDirective', [])
.controller('trainingRecordController',function ($scope,$http) {
    $http.get('../teacher/tinfo_getEduAdvanceList.action').success(function(data) {
    	if(data.Totalcount==""||data.Totalcount==undefined){
    		data.Totalcount=0;
    		 $('.panel').after(
                     '<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
                     + '您还没有学历提升申请记录，现在申请请点击'+ '<a href="teacherEduAdvanceAdd.html" target="_blank">' +'申请学历提升' + '</a></p>');
    	}
    	if(data.CheckPass==""||data.CheckPass==undefined){
    		data.CheckPass=0;
    	}
    	if(data.CheckNoPass==""||data.CheckNoPass==undefined){
    		data.CheckNoPass=0;
    	}
        $scope.totalRecordCount = data.Totalcount;
        $scope.onTrainCount = data.CheckPass;
        $scope.endCount = data.CheckNoPass;

        $scope.records = data.Records;
        $scope.result = data.Result.length;
    });
})

var display=1;
function moreInfo (ele) {
    if(ele.innerHTML=="查看更多"){
        $(ele.parentElement.parentElement.getElementsByClassName("moreInfo")).removeClass("hide");
        ele.innerHTML="收起";
        display=0;
        $('.brand-waterfall').waterfall();
    }else{
        $(ele.parentElement.parentElement.getElementsByClassName("moreInfo")).addClass("hide");
        ele.innerHTML="查看更多";
        display=1;
    }
}
function checkIsTest(ele){
	var obj = $(ele);
	var cUrl = obj.attr('data-url');
	var ttrId = obj.attr('data-id');
	$.getJSON("../teacher/trainRecords_checkIsTest.action?ttrId="+ttrId, function(ret) {
		if (ret.Result == 'OK') {
//				infotip(obj, ret.Message);
//				$('#adu_'+obj.attr('data-id')).remove();
			window.location.href=cUrl;
			new_window.close();
//			window.open(cUrl); 
		} else if(ret.Result == 'REDIRECT') {
//			alert('失败,sss' + ret.Message);
////			window.location.href = "selectCourse.html";
//			window.open("../HSDTesting/index.html");
			if(confirm('失败,' + ret.Message)){
				open_window('../HSDTesting/index.html');
			}else{
				new_window.close();
			}
		} else {
			alert('失败,' + ret.Message);
			new_window.close();
		}
	})
	return false;
}

var new_window = null;
function open_window(url) {      
    new_window.location.href = url;      
} 
//撤销
function revoke(){	
	var id=$(".revoke").attr("name");
	$.ajax({
        type: "post",
        url: "../teacher/tinfo_delete.action",
        data:{id:id},
        success: function (data) {
            if(data.Result == "OK"){
            	layer.msg("操作成功！");
            	setTimeout('window.top.location.reload()',1000);
            }
            else{
                console.log(data);
            }
        }
    })
}

;(function ($) {
    $.fn.waterfall = function(options) {
        var df = {
            item: '.item',
            margin: 30,
            addfooter: true
        };
        options = $.extend(df, options);
        return this.each(function() {
            var $box = $(this), pos = [],
            _box_width = $box.width(),
            $items = $box.find(options.item),
            _owidth = $items.eq(0).outerWidth() + options.margin,
            _oheight = $items.eq(0).outerHeight() + options.margin,
            _num = Math.floor(_box_width/_owidth);
            (function() {
                var i = 0;
                for (; i < _num; i++) {
                    pos.push([i*_owidth,0]);
                } 
            })();
            $items.each(function() {
                var _this = $(this),
                _temp = 0,
                _height = _this.outerHeight() + options.margin;

                _this.hover(function() {
                    _this.addClass('hover');
                },function() {
                    _this.removeClass('hover');
                });

                for (var j = 0; j < _num; j++) {
                    if(pos[j][1] < pos[_temp][1]){
                        //暂存top值最小那列的index
                        _temp = j;
                    }
                }
                if(pos[_temp] != undefined){
	                $(this).css({"left":+pos[_temp][0]+"px", "top":+pos[_temp][1]+"px"});
	                //插入后，更新下该列的top值
	                pos[_temp][1] = pos[_temp][1] + _height;
                }
            });

            // 计算top值最大的赋给外围div
            (function() {
                var i = 0, tops = [];
                for (; i < _num; i++) {
                    tops.push(pos[i][1]);
                }
                tops.sort(function(a,b) {
                    return a-b;
                });
                $box.height(tops[_num-1]);
                //增加尾部填充div
//              if(options.addfooter){
//                  addfooter(tops[_num-1]);
//              }
            })();
            function addfooter(max) {
                var addfooter = document.createElement('div');
                addfooter.className = 'item additem';
                for (var i = 0; i < _num; i++) {
                    if(max != pos[i][1]){
                        var clone = addfooter.cloneNode(),
                        _height = max - pos[i][1] - options.margin;
                        clone.style.cssText = 'left:'+pos[i][0]+'px; top:'+pos[i][1]+'px; height:'+_height+'px;';
                        $box[0].appendChild(clone);
                    }
                }
            }
        });
    }
})(jQuery);

